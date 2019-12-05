/*
 * CollectionHelper.java
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

import java.util.Iterator;

public class CollectionHelper {

	// Business methods -------------------------------------------------------

	public static boolean someNull(final Object[] array) {
		// array is nullable 

		boolean result;

		result = array == null;
		for (int index = 0; result == false && index < array.length; index++) {
			result = array[index] == null;
		}

		return result;
	}

	public static boolean someNull(final Iterable<?> collection) {
		// collection is nullable

		boolean result;
		Iterator<?> iterator;

		result = collection == null;
		iterator = collection.iterator();
		while (result == false && iterator.hasNext()) {
			Object object;
			object = iterator.next();
			result = object == null;
		}

		return result;
	}

}
