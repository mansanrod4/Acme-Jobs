/*
 * PropertiesHelper.java
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

import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.text.StringEscapeUtils;

public class PropertiesHelper {

	// Business methods -------------------------------------------------------

	public static String findProperty(final Properties properties, final String property) {
		assert properties != null;
		assert !StringHelper.isBlank(property);

		String result;
		Object value;

		value = properties.get(property);
		if (value == null) {
			throw new RuntimeException(String.format("Property `%s' was not found", property));
		}
		if (!(value instanceof String)) {
			throw new RuntimeException(String.format("Property `%s' is not a string", property));
		}
		result = (String) value;
		if (StringHelper.isBlank(result)) {
			throw new RuntimeException(String.format("Property `%s' is blank", property));
		}

		return result;
	}

	public static void print(final Map<String, Object> properties) {
		assert properties != null;

		String name, value;

		for (final Entry<String, Object> entry : properties.entrySet()) {
			name = entry.getKey();
			value = StringEscapeUtils.escapeJava(entry.getValue().toString());
			System.out.println(String.format("%s=%s", name, value));
		}
	}

}
