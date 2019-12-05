/*
 * Assert.java
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

import java.util.Locale;

public abstract class Assert {

	// Business methods -------------------------------------------------------

	public static void state(final boolean expression, final String code, final String... arguments) {
		assert !StringHelper.isBlank(code);
		assert !StringHelper.someBlank(arguments);

		Locale locale;

		locale = Locale.getDefault();
		Assert.state(expression, locale, code, arguments);
	}

	public static void state(final boolean expression, final Locale locale, final String code, final String... arguments) {
		assert locale != null;
		assert !StringHelper.isBlank(code);
		assert !StringHelper.someBlank(arguments);

		String message;

		if (!expression) {
			message = MessageHelper.getMessage(code, arguments, code, locale);
			throw new IllegalStateException(message);
		}
	}

}
