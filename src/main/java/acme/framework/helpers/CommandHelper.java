/*
 * CommandHelper.java
 *
 * Copyright (c) 2019 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.framework.helpers;

import acme.components.CustomCommand;
import acme.framework.components.BasicCommand;
import acme.framework.components.Command;

public class CommandHelper {

	// Business methods -------------------------------------------------------

	public static Command parse(final String text) {
		assert !StringHelper.isBlank(text);

		Command result;
		String name;

		name = text.toUpperCase();
		name = name.replace("-", "_");

		try {
			result = BasicCommand.valueOf(name);
		} catch (Throwable oops) {
			result = null;
		}

		if (result == null) {
			try {
				result = CustomCommand.valueOf(name);
			} catch (Throwable oops) {
			}
		}

		assert result != null;

		return result;

	}

	public static String format(final Command command) {
		assert command != null;

		String result;

		result = command.toString();
		result = result.replace("_", "-");
		result = result.toLowerCase();

		return result;
	}

}
