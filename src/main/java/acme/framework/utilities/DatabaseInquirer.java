/*
 * DatabaseInquirer.java
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

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import acme.framework.helpers.PasswordHelper;
import acme.framework.helpers.PrinterHelper;
import acme.framework.helpers.StringHelper;
import acme.framework.helpers.ThrowableHelper;

@Component
public class DatabaseInquirer {

	// Internal state ---------------------------------------------------------

	@Autowired
	private DatabaseUtil databaseUtil;


	// Internal state ---------------------------------------------------------

	public boolean run() {
		boolean result;
		ConsoleReader reader;
		String command;
		boolean quit;

		try {
			System.out.printf("Inquiring database...%n");
			reader = new ConsoleReader();
			do {
				command = reader.readCommand();
				quit = this.interpretCommand(command);
			} while (!quit);

			result = true;
		} catch (final Throwable oops) {
			ThrowableHelper.print(oops);
			result = false;
		}

		return result;
	}

	private boolean interpretCommand(final String command) {
		assert !StringHelper.isBlank(command);

		boolean result;
		String verb, parameter;
		List<?> objects;
		int affected;

		result = false;
		try {
			verb = StringUtils.substringBefore(command, " ");
			switch (verb) {
			case "quit":
			case "exit":
				result = true;
				break;
			case "hash":
				parameter = StringUtils.substringAfter(command, " ");
				System.out.printf("hash %s = %s%n", parameter, PasswordHelper.encode(parameter));
				break;
			case "select":
				this.databaseUtil.startTransaction();
				objects = this.databaseUtil.executeSelect(command);
				System.out.printf("%d object%s selected%n", objects.size(), objects.size() == 1 ? "" : "s");
				PrinterHelper.print(objects);
				this.databaseUtil.commitTransaction();
				break;
			case "update":
			case "delete":
				this.databaseUtil.startTransaction();
				affected = this.databaseUtil.executeUpdate(command);
				System.out.printf("%d object%s affected%n", affected, affected == 1 ? "" : "s");
				this.databaseUtil.commitTransaction();
				break;
			default:
				System.err.println("Command not understood");
			}
		} catch (final Throwable oops) {
			ThrowableHelper.print(oops);
			if (this.databaseUtil.isTransactionActive()) {
				this.databaseUtil.rollbackTransaction();
			}
		}

		return result;
	}

}
