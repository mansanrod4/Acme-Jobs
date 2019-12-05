/*
 * StringHelper.java
 *
 * Copyright (c) 2019 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes.  The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.framework.helpers;

import java.util.Collection;
import java.util.Iterator;

public class StringHelper {

	// Business methods -------------------------------------------------------

	public static boolean isBlank(final CharSequence text) {
		// text is nullable

		boolean result;
		char c;

		if (text == null) {
			result = true;
		} else {
			result = true;
			for (int i = 0; result && i < text.length(); i++) {
				c = text.charAt(i);
				result = Character.isWhitespace(c);
			}
		}

		return result;
	}

	public static boolean someBlank(final String[] array) {
		// array is nullable

		boolean result;

		result = array == null;
		for (int index = 0; result == false && index < array.length; index++) {
			result = StringHelper.isBlank(array[index]);
		}

		return result;
	}

	public static boolean someBlank(final Iterable<String> collection) {
		// collection is nullable

		boolean result;
		Iterator<String> iterator;
		String text;

		result = collection == null;
		iterator = collection.iterator();
		while (result == false && iterator.hasNext()) {
			text = iterator.next();
			result = StringHelper.isBlank(text);
		}

		return result;
	}

	public static String makeIndentation(final int size) {
		assert size >= 0;

		StringBuilder result;

		result = new StringBuilder();
		for (int i = 0; i < size; i++) {
			result.append("    ");
		}

		return result.toString();
	}

	public static String capitaliseInitial(final String text) {
		assert !StringHelper.isBlank(text);

		String result;

		if (text.length() >= 1 && Character.isUpperCase(text.charAt(0))) {
			result = text;
		} else {
			result = String.format("%c%s", Character.toUpperCase(text.charAt(0)), text.substring(1));
		}

		return result;
	}

	public static String smallInitial(final String text) {
		assert !StringHelper.isBlank(text);

		String result;

		if (text.length() >= 1 && Character.isLowerCase(text.charAt(0))) {
			result = text;
		} else {
			result = String.format("%c%s", Character.toLowerCase(text.charAt(0)), text.substring(1));
		}

		return result;
	}

	public static String toPrintable(final char character) {
		String result;

		result = String.valueOf(character);
		result = StringHelper.toPrintable(result);

		return result;
	}

	public static String toPrintable(final String text) {
		assert text != null;

		StringBuilder result;
		char character;
		boolean printable;
		String hexadecimal;

		result = new StringBuilder();
		for (int i = 0; i < text.length(); i++) {
			character = text.charAt(i);
			printable = ' ' <= character && character <= '~' || '\u00a1' <= character && character <= '\u024f';
			if (printable) {
				result.append(character);
			} else {
				hexadecimal = String.format("\\0x%02x", (int) character);
				result.append(hexadecimal);
			}
		}

		return result.toString();
	}

	public static String toString(final Object[] values, final String separator, final String finaliser) {
		assert !CollectionHelper.someNull(values);
		assert separator != null;
		assert finaliser != null;

		StringBuilder result;
		String space;

		result = new StringBuilder();
		space = "";
		for (final Object arg : values) {
			result.append(space);
			result.append(arg);
			space = separator;
		}
		result.append(finaliser);

		return result.toString();
	}

	public static String toString(final Collection<?> values, final String separator, final String finaliser) {
		assert !CollectionHelper.someNull(values);
		assert separator != null;
		assert finaliser != null;

		StringBuilder result;
		String space;

		result = new StringBuilder();
		space = "";
		for (final Object arg : values) {
			result.append(space);
			result.append(arg);
			space = separator;
		}
		result.append(finaliser);

		return result.toString();
	}

}
