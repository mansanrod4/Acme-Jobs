/*
 * DatabaseExporter.java
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
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.metamodel.EmbeddableType;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.hbm2ddl.SchemaExport.Action;
import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;
import org.hibernate.tool.schema.spi.ScriptTargetOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import acme.framework.helpers.StringHelper;
import acme.framework.helpers.ThrowableHelper;

@Component
public class DatabaseExporter {

	// Internal state ---------------------------------------------------------

	@PersistenceContext
	private EntityManager	entityManager;

	@Autowired
	private Environment		environment;


	// Internal state ---------------------------------------------------------

	public boolean run() {
		boolean result;
		Map<String, String> settings;
		ServiceRegistry registry;
		Metadata metadata;
		String dropSchema, createSchema;

		try {
			System.out.printf("Exporting database scripts...%n");

			System.out.println("Reading settings.");
			settings = this.readSettings();

			System.out.println("Building service registry from settings.");
			registry = this.buildServiceRegistry(settings);

			System.out.println("Building metadata from registry.");
			metadata = this.buildMetadata(registry);

			dropSchema = this.environment.getRequiredProperty("acme.drop-schema");
			System.out.printf("Exporting drop script to `%s'.%n", dropSchema);
			this.exportSchema(metadata, Action.DROP, dropSchema);

			createSchema = this.environment.getRequiredProperty("acme.create-schema");
			System.out.printf("Exporting create script to `%s'.%n", createSchema);
			this.exportSchema(metadata, Action.CREATE, createSchema);

			System.out.println("Everything worked like a charm!");

			result = true;
		} catch (final Throwable oops) {
			System.out.println();
			System.out.println("Something went wrong!  Please, review the following exception trace");
			System.out.println("and make your best to diagnose what the problem is.  Thanks!");
			System.out.println();

			System.out.println();
			ThrowableHelper.print(oops);
			System.out.println();

			result = false;
		}

		return result;
	}

	// Internal methods -------------------------------------------------------

	private Map<String, String> readSettings() {
		Map<String, String> result;

		result = new HashMap<String, String>();
		result.put("connection.driver_class", this.environment.getRequiredProperty("spring.datasource.driver-class-name"));
		result.put("dialect", this.environment.getRequiredProperty("spring.jpa.hibernate.dialect"));
		result.put("hibernate.connection.url", this.environment.getRequiredProperty("spring.datasource.url"));
		result.put("hibernate.connection.username", this.environment.getRequiredProperty("spring.datasource.username"));
		result.put("hibernate.connection.password", this.environment.getRequiredProperty("spring.datasource.password"));
		result.put("hibernate.physical_naming_strategy", this.environment.getRequiredProperty("spring.jpa.hibernate.naming.physical-strategy"));
		result.put("hibernate.implicit_naming_strategy", this.environment.getRequiredProperty("spring.jpa.hibernate.naming.implicit-strategy"));
		result.put("show_sql", this.environment.getRequiredProperty("spring.jpa.hibernate.show-sql"));
		result.put("format_sql", this.environment.getRequiredProperty("spring.jpa.hibernate.format-sql"));
		result.put("hibernate.globally_quoted_identifiers", "true");

		return result;
	}

	private ServiceRegistry buildServiceRegistry(final Map<String, String> settings) {
		assert settings != null;

		StandardServiceRegistryBuilder builder;
		ServiceRegistry result;

		builder = new StandardServiceRegistryBuilder();
		builder.applySettings(settings);
		result = builder.build();

		return result;
	}

	public Metadata buildMetadata(final ServiceRegistry registry) {
		assert registry != null;

		Metadata result;
		MetadataSources metadataBuilder;
		Metamodel metamodel;
		Collection<EntityType<?>> entities;
		Collection<EmbeddableType<?>> embeddables;

		metadataBuilder = new MetadataSources(registry);
		metamodel = this.entityManager.getMetamodel();

		entities = metamodel.getEntities();
		for (final EntityType<?> entity : entities) {
			metadataBuilder.addAnnotatedClass(entity.getJavaType());
		}

		embeddables = metamodel.getEmbeddables();
		for (final EmbeddableType<?> embeddable : embeddables) {
			metadataBuilder.addAnnotatedClass(embeddable.getJavaType());
		}

		result = metadataBuilder.buildMetadata();

		return result;
	}

	private void exportSchema(final Metadata metadata, final Action action, final String fileName) {
		assert metadata != null;
		assert action != null;
		assert !StringHelper.isBlank(fileName);

		SchemaExport exporter;
		File file;
		ScriptTargetOutput target;

		exporter = new SchemaExport();
		exporter.setHaltOnError(true);
		exporter.setFormat(true);
		exporter.setDelimiter(";");

		file = new File(fileName);
		if (file.exists() && !file.canWrite()) {
			throw new RuntimeException("Couldn't write to " + fileName);
		}
		file.delete();

		target = new ScriptTargetOutputToFile(file, "utf-8");
		exporter.perform(action, metadata, target);
	}

}
