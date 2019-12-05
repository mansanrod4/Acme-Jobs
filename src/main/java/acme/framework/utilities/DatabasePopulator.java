/*
 * DatabasePopulator.java
 *
 * Copyright (c) 2019 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.framework.utilities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Component;

import acme.framework.entities.DomainEntity;
import acme.framework.helpers.CollectionHelper;
import acme.framework.helpers.PrinterHelper;
import acme.framework.helpers.StringHelper;
import acme.framework.helpers.ThrowableHelper;

@Component
public class DatabasePopulator {

	// Internal state ---------------------------------------------------------

	@Autowired
	private DatabaseUtil		databaseUtil;

	@Autowired
	private Validator			validator;

	@Autowired
	private DatabaseExporter	databaseExporter;


	// Business methods -------------------------------------------------------

	public boolean run(final String mapFilename, final String... entityFilenames) {
		assert !StringHelper.isBlank(mapFilename);
		assert !StringHelper.someBlank(entityFilenames);

		boolean result;

		try {
			System.out.printf("Populating database...%n");
			this.databaseExporter.run();
			this.createSchema();
			this.deleteEntityMap(mapFilename);
			result = true;
			for (String entityFilename : entityFilenames) {
				result = result && this.populate(mapFilename, entityFilename);
			}
		} catch (final Throwable oops) {
			System.out.println("Something went wrong!  Please, review the following exception trace");
			System.out.println("and make your best to diagnose what the problem is.  Thanks!");
			System.out.println();
			ThrowableHelper.print(oops);
			result = false;
		}

		return result;
	}

	private void createSchema() {
		System.out.printf("Dropping schema...%n");
		this.databaseUtil.dropSchema();
		System.out.printf("Creating schema...%n");
		this.databaseUtil.createSchema();
	}

	public boolean populate(final String mapFilename, final String entityFilename) {
		assert !StringHelper.isBlank(mapFilename);
		assert !StringHelper.isBlank(entityFilename);

		boolean result;
		Map<String, DomainEntity> entityMap;
		LinkedList<Entry<String, DomainEntity>> entityList;

		System.out.printf("Populating entities in file \"%s\"...%n", entityFilename);
		entityList = null;
		try (AbstractXmlApplicationContext context = new FileSystemXmlApplicationContext(entityFilename)) {
			entityMap = context.getBeansOfType(DomainEntity.class);
			entityList = new LinkedList<Entry<String, DomainEntity>>(entityMap.entrySet());
			result = this.validate(entityList);
			result = result && this.persist(entityList);
		} finally {
			if (entityList != null) {
				this.saveEntityMap(mapFilename, entityFilename, entityList);
			}
		}

		if (result) {
			System.out.println("Everything worked like a charm!");
		} else {
			System.out.println("I couldn't persist your entities.  First, mend the validation errors, if any.");
			System.out.println("Then review the entity map and your `mappedBy' attributes. It may well be the");
			System.out.println("case that there is an *:* association in which the entities are not properly linked.");
		}

		return result;
	}

	public boolean validate(final LinkedList<Entry<String, DomainEntity>> entityList) {
		assert !CollectionHelper.someNull(entityList);

		boolean result;
		Set<ConstraintViolation<DomainEntity>> violations;
		String name;
		DomainEntity entity;

		System.out.println("Validating your entities.");

		result = true;
		for (final Entry<String, DomainEntity> entry : entityList) {
			name = entry.getKey();
			entity = entry.getValue();

			System.out.printf("> Validating %s", name);
			violations = this.validator.validate(entity);
			if (violations.isEmpty()) {
				System.out.println(" ... PASS");
			} else {
				result = false;
				System.out.println(" ... FAIL");
				for (final ConstraintViolation<DomainEntity> violation : violations) {
					System.out.printf("\t%s : %s%n", violation.getPropertyPath().toString(), violation.getMessage());
				}
			}
		}

		return result;
	}

	public boolean persist(final LinkedList<Entry<String, DomainEntity>> entityList) {
		assert !CollectionHelper.someNull(entityList);

		boolean result;
		int attemptCounter, persistedCounter;
		String name;
		DomainEntity entity;

		System.out.println("Sorting your entities topologically.");

		result = entityList.isEmpty();
		attemptCounter = 0;
		persistedCounter = 0;
		while (!result && attemptCounter < entityList.size()) {
			persistedCounter = this.tryPersist(entityList, persistedCounter - 1);
			result = persistedCounter == entityList.size();
			attemptCounter++;
		}

		if (result) {
			System.out.println("Persiting your entities.");

			for (final Entry<String, DomainEntity> entry : entityList) {
				name = entry.getKey();
				entity = entry.getValue();
				System.out.printf("> Persisting %s = %s%n", name, entity);
			}
		}

		return result;
	}

	public int tryPersist(final LinkedList<Entry<String, DomainEntity>> entityList, final int threshold) {
		assert !CollectionHelper.someNull(entityList);

		int result;
		boolean sucess;
		Entry<String, DomainEntity> entry;
		String name;
		DomainEntity entity;
		int index;

		this.databaseUtil.startTransaction();
		{
			this.resetEntities(entityList);
			sucess = true;
			result = 0;
			index = 0;
			while (sucess && result < entityList.size()) {
				entry = entityList.get(result);
				name = entry.getKey();
				entity = entry.getValue();
				try {
					this.databaseUtil.persist(entity);
					if (result > threshold) {
						System.out.printf("> Setting index %d for %s.%n", index, name);
						index++;
					}
					result++;
					sucess = true;
				} catch (final Throwable oops) {
					System.out.printf("> Deferring %s.%n", name);
					entityList.remove(result);
					entityList.addLast(entry);
					sucess = false;
				}
			}
		}
		if (sucess) {
			this.databaseUtil.commitTransaction();
		} else {
			this.databaseUtil.rollbackTransaction();
		}

		return result;
	}

	private void deleteEntityMap(final String mapFilename) {
		assert !StringHelper.isBlank(mapFilename);

		File file;

		file = new File(mapFilename);
		file.delete();
	}

	private void saveEntityMap(final String mapFilename, final String entityFilename, final List<Entry<String, DomainEntity>> entities) {
		assert !StringHelper.isBlank(mapFilename);
		assert !StringHelper.isBlank(entityFilename);
		assert !CollectionHelper.someNull(entities);

		String name;
		DomainEntity entity;

		try (PrintStream writer = new PrintStream(new FileOutputStream(mapFilename, true))) {
			writer.println();
			writer.printf("# %s%n", entityFilename);
			writer.println();
			for (final Entry<String, DomainEntity> entry : entities) {
				name = entry.getKey();
				entity = entry.getValue();

				writer.printf("%s=%s%n", name, entity.getId());
				writer.println();
				PrinterHelper.print(writer, "# ", entity);
				writer.println();
			}
		} catch (final Throwable oops) {
			throw new RuntimeException(oops);
		}
	}

	private void resetEntities(final LinkedList<Entry<String, DomainEntity>> result) {
		assert !CollectionHelper.someNull(result);

		DomainEntity entity;

		for (final Entry<String, DomainEntity> entry : result) {
			entity = entry.getValue();
			entity.setId(0);
			entity.setVersion(0);
		}
	}

}
