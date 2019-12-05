/*
 * ConversionHelper.java
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

import org.springframework.core.convert.ConversionService;

public class ConversionHelper {

	// Business methods -------------------------------------------------------

	public static boolean canConvert(final Object object, final Class<?> clazz) {
		// assert object is nullable
		assert clazz != null;

		boolean result;
		ConversionService conversionService;

		if (object == null) {
			result = true;
		} else {
			conversionService = FactoryHelper.getConversionService();
			result = conversionService.canConvert(object.getClass(), clazz);
		}

		return result;
	}

	public static <T> T convert(final Object source, final Class<T> clazz) {
		// assert object is nullable
		assert clazz != null;
		assert ConversionHelper.canConvert(source, clazz);

		T result;
		ConversionService conversionService;

		conversionService = FactoryHelper.getConversionService();
		result = conversionService.convert(source, clazz);

		return result;
	}

	public static String toString(final Object source) {
		// assert object is nullable
		assert ConversionHelper.canConvert(source, String.class);

		String result;
		ConversionService conversionService;

		conversionService = FactoryHelper.getConversionService();
		result = conversionService.convert(source, String.class);

		return result;
	}

}
