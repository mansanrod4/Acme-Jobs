/*
 * Launcher.java
 *
 * Copyright (c) 2019 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.web.context.WebApplicationContext;

import acme.framework.helpers.FactoryHelper;
import acme.framework.helpers.ProfileHelper;
import acme.framework.helpers.StringHelper;
import acme.framework.utilities.DatabaseExporter;
import acme.framework.utilities.DatabaseInquirer;
import acme.framework.utilities.DatabasePopulator;
import lombok.extern.java.Log;

@SpringBootApplication(scanBasePackages = "acme")
@Log
public class Launcher extends SpringBootServletInitializer {

	// Entry point ------------------------------------------------------------

	public static void main(final String[] arguments) {
		String command;
		ConfigurableApplicationContext context;

		Launcher.checkArguments(arguments);
		command = arguments.length == 1 ? arguments[0] : "--run";
		Launcher.setProfile(arguments);
		context = SpringApplication.run(Launcher.class, arguments);
		FactoryHelper.initialise(context);
		Launcher.doExtraWork(command, context);
	}

	// SpringBootServletInitializer interface ---------------------------------

	@Override
	public void onStartup(final ServletContext servletContext) throws ServletException {
		Object attribute;
		ApplicationContext context;

		ProfileHelper.setProfiles("deployment");

		super.onStartup(servletContext);

		attribute = servletContext.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		assert attribute instanceof ApplicationContext;
		context = (ApplicationContext) attribute;

		FactoryHelper.initialise(context);

		Launcher.log.info("The application is running (Deployment mode)");
	}

	@Override
	protected SpringApplicationBuilder configure(final SpringApplicationBuilder builder) {
		SpringApplicationBuilder result;

		result = builder.sources(Launcher.class);

		return result;
	}

	// Internal methods -------------------------------------------------------

	private static void checkArguments(final String... arguments) {
		if (arguments.length > 1) {
			Launcher.showUsage();
		}
	}

	private static void showUsage() {
		System.err.println("");
		System.err.println("Usage: launcher (--run | --database:export | --database:populate-initial | --database:populate-sample | --database:inquire)?");
		System.err.println("");
		System.err.println("--run\t\truns the system (default)");
		System.err.println("--database\tperforms an action on the database");
		System.err.println("\t\texport            exports the database schema to create/drop SQL scripts");
		System.err.println("\t\tpopulate-initial  populates the database with initial");
		System.err.println("\t\tpopulate-sample   populates the database with sample");
		System.err.println("\t\tinquire           opens a shell to query the database");
		System.err.println("");
		System.err.println("Note that populating the database requires exporting the create/drop scripts, which is");
		System.err.println("performed automatically.  Note, too, that populating the database with sample data requires");
		System.err.println("populating it with the initial data, which is also performed automatically.");
		System.err.println("");
		System.exit(1);
	}

	private static void setProfile(final String[] arguments) {
		assert !StringHelper.someBlank(arguments);

		String argument;

		argument = arguments.length == 1 ? arguments[0] : "--run";

		switch (argument) {
		case "--run":
			ProfileHelper.setProfiles("default");
			break;
		case "--database:export":
			ProfileHelper.setProfiles("database-exporter");
			break;
		case "--database:populate-initial":
		case "--database:populate-sample":
			ProfileHelper.setProfiles("database-populator");
			break;
		case "--database:inquire":
			ProfileHelper.setProfiles("database-inquirer");
			break;
		default:
			Launcher.showUsage();
		}
	}

	private static void doExtraWork(final String command, final ConfigurableApplicationContext context) {
		assert !StringHelper.isBlank(command);
		assert context != null;

		Environment environment;
		DatabaseExporter databaseExporter;
		DatabasePopulator databasePopulator;
		DatabaseInquirer databaseInquirer;
		String mapFilename, initialFilename, sampleFilename;

		environment = FactoryHelper.getBean(Environment.class);
		databaseExporter = FactoryHelper.getBean(DatabaseExporter.class);
		databasePopulator = FactoryHelper.getBean(DatabasePopulator.class);
		databaseInquirer = FactoryHelper.getBean(DatabaseInquirer.class);

		switch (command) {
		case "--run":
			System.out.printf("The application is running (Development)...%n");
			break;
		case "--database:export":
			databaseExporter.run();
			Launcher.exit(context);
			break;
		case "--database:populate-initial":
			mapFilename = environment.getRequiredProperty("acme.entity-map");
			initialFilename = environment.getRequiredProperty("acme.initial-data");
			databaseExporter = FactoryHelper.getBean(DatabaseExporter.class);
			databasePopulator.run(mapFilename, initialFilename);
			Launcher.exit(context);
			break;
		case "--database:populate-sample":
			mapFilename = environment.getRequiredProperty("acme.entity-map");
			initialFilename = environment.getRequiredProperty("acme.initial-data");
			sampleFilename = environment.getRequiredProperty("acme.sample-data");
			databasePopulator.run(mapFilename, initialFilename, sampleFilename);
			Launcher.exit(context);
			break;
		case "--database:inquire":
			databaseInquirer.run();
			Launcher.exit(context);
			break;
		default:
			Launcher.showUsage();
		}
	}

	private static void exit(final ApplicationContext context) {
		int status;

		status = SpringApplication.exit(context);
		System.exit(status);
	}

}
