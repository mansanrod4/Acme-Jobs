/*
 * ModelKeyComparator.java
 *
 * Copyright (c) 2019 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.framework.utilities;

import java.util.Comparator;

public class ModelKeyComparator implements Comparator<String> {

	// Comparator<String> interface -------------------------------------------

	@Override
	public int compare(final String key1, final String key2) {
		assert key1 != null;
		assert key2 != null;

		int result;
		int i1, j1, i2, j2;
		int d1, d2;
		boolean normal1, indexed1, internal1;
		boolean normal2, indexed2, internal2;
		int index1, index2;

		i1 = key1.indexOf("[");
		j1 = key1.indexOf("]");
		d1 = key1.indexOf("$");
		normal1 = i1 == -1 && d1 == -1;
		indexed1 = i1 != -1;
		internal1 = d1 != -1;

		i2 = key2.indexOf("[");
		j2 = key2.indexOf("]");
		d2 = key2.indexOf("$");
		normal2 = i2 == -1 && d2 == -1;
		indexed2 = i2 != -1;
		internal2 = d2 != -1;

		assert normal1 && normal2 || indexed1 && indexed2 || internal1 && internal2;

		result = 0;
		if (indexed1 && indexed2) {
			assert !normal1 && !normal2;
			index1 = Integer.valueOf(key1.substring(i1 + 1, j1));
			index2 = Integer.valueOf(key2.substring(i2 + 1, j2));
			result = index1 - index2;
		}
		if (result == 0) {
			result = key1.compareTo(key2);
		}

		return result;
	}

}
