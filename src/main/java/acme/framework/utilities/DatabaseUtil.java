/*
 * DatabaseUtil.java
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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.io.FileUtils;
import org.hibernate.Session;
import org.hibernate.engine.spi.EntityEntry;
import org.hibernate.engine.spi.SessionImplementor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import acme.framework.entities.DomainEntity;
import acme.framework.helpers.CollectionHelper;
import acme.framework.helpers.StringHelper;
import acme.framework.helpers.ThrowableHelper;

@Component
public class DatabaseUtil {

	// Internal state ---------------------------------------------------------

	@PersistenceContext
	private EntityManager			entityManager;

	@Autowired
	PlatformTransactionManager		transactionManager;

	@Autowired
	private Environment				environment;

	private TransactionStatus		transactionStatus;
	private TransactionDefinition	transactionDefinition;


	// Transaction management --------------------------------------------------

	public void setReadUncommittedIsolationLevel() {
		this.executeCommand("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;");
	}

	public void setReadCommittedIsolationLevel() {
		this.executeScript("SET TRANSACTION ISOLATION LEVEL READ COMMITTED");
	}

	public void startTransaction() {
		assert !this.isTransactionActive();

		this.transactionDefinition = new DefaultTransactionDefinition();
		this.transactionStatus = this.transactionManager.getTransaction(this.transactionDefinition);
		this.setReadUncommittedIsolationLevel();
		this.clearPersistenceContext();
	}

	public void commitTransaction() {
		assert this.isTransactionActive();

		try {
			this.transactionManager.commit(this.transactionStatus);
		} catch (Throwable oops) {
			ThrowableHelper.print(oops);
		} finally {
			this.transactionStatus = null;
			this.clearPersistenceContext();
		}
	}

	public void rollbackTransaction() {
		assert this.isTransactionActive();

		try {
			this.transactionManager.rollback(this.transactionStatus);
		} catch (Throwable oops) {
			ThrowableHelper.print(oops);
		} finally {
			this.transactionStatus = null;
			this.clearPersistenceContext();
		}
	}

	public boolean isTransactionActive() {
		boolean result;

		result = this.transactionStatus != null;

		return result;
	}

	// Schema management ------------------------------------------------------

	public void dropSchema() {
		String dropSchema;
		File dropFile;
		String[] allCommands;
		List<String> dropCommands;

		dropSchema = this.environment.getRequiredProperty("acme.drop-schema");
		dropFile = new File(dropSchema);
		allCommands = this.readScript(dropFile);
		dropCommands = new ArrayList<String>();
		dropCommands.add("SET FOREIGN_KEY_CHECKS=0");
		for (String command : allCommands) {
			if (command.contains("drop table")) {
				dropCommands.add(command);
			}
		}
		dropCommands.add("SET FOREIGN_KEY_CHECKS=1");
		this.executeScript(dropCommands);
	}

	public void createSchema() {
		String createSchema;
		File createFile;

		createSchema = this.environment.getRequiredProperty("acme.create-schema");
		createFile = new File(createSchema);
		this.executeScript(createFile);
	}

	// Script execution -------------------------------------------------------

	public void executeScript(final List<String> commands) {
		assert !CollectionHelper.someNull(commands);

		try {
			this.startTransaction();
			for (final String command : commands) {
				this.executeCommand(command);
			}
			this.commitTransaction();
		} catch (Throwable oops) {
			if (this.isTransactionActive()) {
				this.rollbackTransaction();
			}
			throw new RuntimeException(oops);
		}
	}

	public void executeScript(final String... commands) {
		assert !StringHelper.someBlank(commands);

		try {
			this.startTransaction();
			for (final String command : commands) {
				this.executeCommand(command);
			}
			this.commitTransaction();
		} catch (Throwable oops) {
			if (this.isTransactionActive()) {
				this.rollbackTransaction();
			}
			throw new RuntimeException(oops);
		}
	}

	public void executeScript(final File file) {
		assert file != null && file.isFile() && file.canRead();

		String[] commands;

		commands = this.readScript(file);
		this.executeScript(commands);
	}

	protected String[] readScript(final File file) {
		assert file != null && file.isFile() && file.canRead();

		String script;
		String[] result;

		try {
			script = FileUtils.readFileToString(file, "utf-8");
			script = script.replaceAll(";\\s*[\r\n]+", ">\\|\\|<");
			script = script.replaceAll("[\r\n]+", " ");
			result = script.split(">\\|\\|<");
		} catch (Throwable oops) {
			throw new RuntimeException(oops);
		}

		return result;
	}

	// Command execution ------------------------------------------------......

	public void executeCommand(final String command) {
		assert !StringHelper.isBlank(command);

		Session session;
		session = this.entityManager.unwrap(Session.class);
		session.doWork(connection -> {
			Statement statement;

			statement = connection.createStatement();
			statement.execute(command);
			connection.commit();
		});
	}

	public int executeUpdate(final String command) {
		assert !StringHelper.isBlank(command);

		int result;
		Query query;

		query = this.entityManager.createQuery(command);
		result = query.executeUpdate();

		return result;
	}

	public List<?> executeSelect(final String command) {
		assert !StringHelper.isBlank(command);

		List<?> result;
		Query query;

		query = this.entityManager.createQuery(command);
		result = query.getResultList();

		return result;
	}

	public void persist(final DomainEntity entity) {
		assert entity != null;
		assert this.isTransactionActive();

		this.entityManager.persist(entity);
	}

	// Miscellaneous ----------------------------------------------------------

	public void printPersistenceContext() {
		SessionImplementor session;
		org.hibernate.engine.spi.PersistenceContext context;
		Entry<Object, EntityEntry>[] entries;

		session = this.entityManager.unwrap(SessionImplementor.class);
		context = session.getPersistenceContext();
		entries = context.reentrantSafeEntityEntries();

		for (final Entry<Object, EntityEntry> entry : entries) {
			System.out.printf("%s%n", entry.getValue());
		}
	}

	public void clearPersistenceContext() {
		this.entityManager.clear();
	}

}
