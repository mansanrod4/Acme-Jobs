/*
 * ReflectionHelper.java
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

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.data.util.DirectFieldAccessFallbackBeanWrapper;

public class ReflectionHelper {

	// Business methods -------------------------------------------------------

	public static <T> T instantiate(final Class<T> clazz) {
		assert clazz != null;

		T result;

		result = BeanUtils.instantiateClass(clazz);

		return result;
	}

	public static void setProperty(final Object source, final String property, final Object value) {
		assert source != null;
		assert !StringHelper.isBlank(property);
		// assert value is nullable

		BeanWrapper wrapper;

		wrapper = new DirectFieldAccessFallbackBeanWrapper(source);
		wrapper.setPropertyValue(property, value);
	}

	public static Object getProperty(final Object source, final String property) {
		assert source != null;
		assert !StringHelper.isBlank(property);

		Object result;
		BeanWrapper wrapper;

		wrapper = new DirectFieldAccessFallbackBeanWrapper(source);
		result = wrapper.getPropertyValue(property);

		return result;
	}

	public static boolean supports(final Object object, final Class<?> clazz) {
		assert object != null;
		assert clazz != null;

		boolean result;

		result = clazz.isAssignableFrom(object.getClass());

		return result;
	}

	public static boolean existsClass(final String name, final Class<?> base) {
		assert !StringHelper.isBlank(name);
		assert base != null;

		boolean result;
		Class<?> clazz;

		try {
			clazz = Class.forName(name);
			result = base.isAssignableFrom(clazz);
		} catch (ClassNotFoundException e) {
			result = false;
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	public static <T> Class<T> getClass(final String name, final Class<T> base) {
		assert !StringHelper.isBlank(name);
		assert base != null;
		assert ReflectionHelper.existsClass(name, base);

		Class<T> result;

		try {
			result = (Class<T>) Class.forName(name);
		} catch (ClassNotFoundException e) {
			result = null;
		}

		return result;
	}

}
