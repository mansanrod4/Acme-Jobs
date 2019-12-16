/*
 * PrinterHelper.java
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

import java.io.PrintStream;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PrinterHelper {

	// Business methods -------------------------------------------------------

	public static void print(final Collection<?> objects) {
		// assert objects is nullable and can contain nulls

		PrinterHelper.print(System.out, "", objects);
	}

	public static void print(final Object object) {
		// assert object is nullable

		PrinterHelper.print(System.out, "", object);
	}

	public static void print(final PrintStream writer, final Collection<?> objects) {
		assert writer != null;
		// assert objects is nullable and can contain nulls

		PrinterHelper.print(writer, "", objects);
	}

	public static void print(final PrintStream writer, final Object object) {
		assert writer != null;
		// assert object is nullable

		PrinterHelper.print(writer, "", object);
	}

	public static void print(final PrintStream writer, final String marker, final Collection<?> objects) {
		assert writer != null;
		assert marker != null;
		// assert objects is nullable and can contain nulls

		if (objects == null) {
			PrinterHelper.print(writer, marker, null);
		} else {
			for (final Object object : objects) {
				PrinterHelper.print(writer, marker, object);
			}
		}
	}

	public static void print(final PrintStream writer, final String marker, final Object object) {
		assert writer != null;
		assert marker != null;
		// assert object is nullable

		String text;
		StringBuffer buffer;

		buffer = new StringBuffer();
		if (PrinterHelper.isValue(object)) {
			PrinterHelper.printValue(buffer, object, true);
		} else {
			PrinterHelper.printRecord(buffer, object, false);
		}

		text = buffer.toString();
		if (!marker.isEmpty()) {
			text = marker + text.replace("\n", "\n" + marker);
		}
		writer.println(text);
	}

	public static void printValue(final StringBuffer buffer, final Object value, final boolean summary) {
		assert buffer != null;
		// assert value is nullable

		@SuppressWarnings("unused")
		List<Object> collection;

		if (PrinterHelper.isPrimitive(value) || PrinterHelper.isEnum(value)) {
			PrinterHelper.printPrimitive(buffer, value, summary);
		} else if (PrinterHelper.isObjectArray(value)) {
			PrinterHelper.printObjectArray(buffer, (Object[]) value, summary);
		} else if (PrinterHelper.isPrimitiveArray(value)) {
			PrinterHelper.printPrimitiveArray(buffer, value, summary);
		} else if (PrinterHelper.isCollection(value)) {
			PrinterHelper.printCollection(buffer, (Collection<?>) value, summary);
		} else {
			PrinterHelper.printRecord(buffer, value, summary);
		}
	}

	public static void printRecord(final StringBuffer buffer, final Object object, final boolean summary) {
		assert buffer != null;
		assert object != null;

		List<Class<?>> superClazzes;
		Class<?> clazz;

		buffer.append(object.toString());
		if (!summary) {
			clazz = object.getClass();
			superClazzes = new ArrayList<Class<?>>();
			while (clazz != null) {
				superClazzes.add(clazz);
				clazz = clazz.getSuperclass();
			}
			for (int i = superClazzes.size() - 1; i >= 0; i--) {
				clazz = superClazzes.get(i);
				PrinterHelper.printFieldsInClazz(buffer, clazz, object);
			}
		}
	}

	public static void printFieldsInClazz(final StringBuffer buffer, final Class<?> clazz, final Object object) {
		assert buffer != null;
		assert clazz != null;
		assert object != null;

		Field fields[];
		String name;
		Type type;
		Object value;
		int modifiers;

		fields = clazz.getDeclaredFields();
		AccessibleObject.setAccessible(fields, true);
		buffer.append("\n\t");
		buffer.append("from ");
		buffer.append(clazz.getName());
		buffer.append(":");
		for (final Field field : fields) {
			name = field.getName();
			type = field.getGenericType();
			modifiers = field.getModifiers();

			try {
				value = field.get(object);
			} catch (final Throwable oops) {
				value = String.format("{%s}", oops.getMessage());
			}

			buffer.append("\n\t");
			if (Modifier.isPrivate(modifiers)) {
				buffer.append("-");
			} else if (Modifier.isPublic(modifiers)) {
				buffer.append("+");
			} else if (Modifier.isProtected(modifiers)) {
				buffer.append("#");
			} else {
				buffer.append("@");
			}
			if (Modifier.isTransient(modifiers)) {
				buffer.append("~");
			}
			buffer.append(" ");
			buffer.append(name);
			buffer.append(": ");
			PrinterHelper.printType(buffer, type);
			buffer.append(" = ");
			PrinterHelper.printValue(buffer, value, true);
		}
	}

	public static void printPrimitive(final StringBuffer buffer, final Object value, final boolean summary) {
		assert buffer != null;
		// value is nullable

		String left, right;

		if (value == null) {
			left = right = "";
		} else if (value instanceof String) {
			left = right = "\"";
		} else if (value instanceof Number) {
			left = right = "";
		} else if (value instanceof Character) {
			left = right = "\'";
		} else if (value instanceof Boolean) {
			left = right = "";
		} else {
			left = "<<";
			right = ">>";
		}

		buffer.append(left);
		buffer.append(value == null ? "null" : value);
		buffer.append(right);
	}

	public static void printObjectArray(final StringBuffer buffer, final Object[] value, final boolean summary) {
		assert buffer != null;
		assert value != null;

		String separator;

		separator = "";
		buffer.append("[");
		for (final Object item : value) {
			buffer.append(separator);
			PrinterHelper.printValue(buffer, item, summary);
			separator = ", ";
		}
		buffer.append("]");
	}

	public static void printPrimitiveArray(final StringBuffer buffer, final Object value, final boolean summary) {
		// WARN: note that cannot cast a primitive array like bool[] to Object[].  Thus, we need to get
		// WARN+ the object as Object, and then do some magic to access its components without attempting to
		// WARN+ cast value to anything that resembles an array.
		assert buffer != null;
		assert value != null;
		assert PrinterHelper.isPrimitiveArray(value);

		String separator;
		int length;
		Object item;

		separator = "";
		buffer.append("[");
		length = Array.getLength(value);
		for (int i = 0; i < length; i++) {
			item = Array.get(value, i);
			buffer.append(separator);
			PrinterHelper.printValue(buffer, item, summary);
			separator = ", ";
		}
		buffer.append("]");
	}

	public static void printCollection(final StringBuffer buffer, final Collection<?> value, final boolean summary) {
		assert buffer != null;
		assert value != null;

		String separator;

		separator = "";
		buffer.append("[");
		for (final Object item : value) {
			buffer.append(separator);
			PrinterHelper.printValue(buffer, item, summary);
			separator = ", ";
		}
		buffer.append("]");
	}

	public static void printType(final StringBuffer buffer, final Type type) {
		assert buffer != null;
		assert type != null;

		String name;

		name = type.getTypeName();
		buffer.append(name);
	}

	public static void printType(final StringBuffer buffer, final Object value) {
		assert buffer != null;
		assert value != null;

		final Class<?> clazz;

		if (value == null) {
			clazz = Object.class;
		} else {
			clazz = value.getClass();
		}
		PrinterHelper.printType(buffer, clazz);
	}

	public static boolean isPrimitive(final Object object) {
		// assert object is nullable

		boolean result;

		result = object == null || // 
			object instanceof String || object instanceof Number || //
			object instanceof Character || object instanceof Boolean || //
			object instanceof java.util.Date || object instanceof java.sql.Date || //
			object instanceof Timestamp;

		return result;
	}

	public static boolean isObjectArray(final Object object) {
		// assert object is nullable

		boolean result;

		result = object != null && //
			object.getClass().getName().charAt(0) == '[' && //
			!object.getClass().getComponentType().isPrimitive();

		return result;
	}

	public static boolean isPrimitiveArray(final Object object) {
		// assert object is nullable

		boolean result;

		result = object != null && //
			object.getClass().getName().charAt(0) == '[' && //
			object.getClass().getComponentType().isPrimitive();

		return result;
	}

	public static boolean isEnum(final Object object) {
		// assert object is nullable

		boolean result;

		result = object == null || object instanceof Enum;

		return result;
	}

	public static boolean isCollection(final Object object) {
		// assert object is nullable

		boolean result;

		result = object != null && object instanceof Collection;

		return result;
	}

	public static boolean isValue(final Object object) {
		// assert object is nullable

		boolean result;

		result = PrinterHelper.isPrimitive(object) || PrinterHelper.isObjectArray(object) || PrinterHelper.isEnum(object);

		return result;
	}

	public static boolean isRecord(final Object object) {
		// assert object is nullable

		boolean result;

		result = !PrinterHelper.isValue(object);

		return result;
	}

}
