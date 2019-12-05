/*
 * EnvironmentHelper.java
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

import org.springframework.core.env.Environment;

public class EnvironmentHelper {

	// Business methods -------------------------------------------------------

	public static String getRequiredProperty(final String name) {
		assert !StringHelper.isBlank(name);

		String result;
		Environment environment;

		environment = FactoryHelper.getBean(Environment.class);
		result = environment.getRequiredProperty(name);

		return result;
	}

	public static <T> T getRequiredProperty(final String name, final Class<T> clazz) {
		assert !StringHelper.isBlank(name);
		assert clazz != null;

		T result;
		Environment environment;

		environment = FactoryHelper.getBean(Environment.class);
		result = environment.getRequiredProperty(name, clazz);

		return result;
	}

	public static String getProperty(final String name) {
		assert !StringHelper.isBlank(name);

		String result;
		Environment environment;

		environment = FactoryHelper.getBean(Environment.class);
		result = environment.getProperty(name);

		return result;
	}

	public static <T> T getProperty(final String name, final Class<T> clazz) {
		assert !StringHelper.isBlank(name);
		assert clazz != null;

		T result;
		Environment environment;

		environment = FactoryHelper.getBean(Environment.class);
		result = environment.getProperty(name, clazz);

		return result;
	}

	public static String getProperty(final String name, final String defaultValue) {
		assert !StringHelper.isBlank(name);
		// assert defaultValue is nullable

		String result;
		Environment environment;

		environment = FactoryHelper.getBean(Environment.class);
		result = environment.getProperty(name, defaultValue);

		return result;
	}

	public static <T> T getProperty(final String name, final Class<T> clazz, final T defaultValue) {
		assert !StringHelper.isBlank(name);
		assert clazz != null;
		// assert defaultValue is nullable

		T result;
		Environment environment;

		environment = FactoryHelper.getBean(Environment.class);
		result = environment.getProperty(name, clazz, defaultValue);

		return result;
	}

}
