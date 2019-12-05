/*
 * HttpMethodHelper.java
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

import acme.framework.components.HttpMethod;

public class HttpMethodHelper {

	// Business methods -------------------------------------------------------

	public static HttpMethod parse(final String text) {
		assert !StringHelper.isBlank(text);

		HttpMethod result;
		String name;

		name = text.toUpperCase();
		name = name.replace("-", "_");

		try {
			result = HttpMethod.valueOf(name);
		} catch (Throwable oops) {
			result = null;
		}

		assert result != null;

		return result;

	}

	public static String format(final HttpMethod method) {
		assert method != null;

		String result;

		result = method.toString();
		result = result.toLowerCase();
		result = result.replace("_", "-");

		return result;
	}

}
