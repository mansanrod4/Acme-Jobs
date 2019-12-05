/*
 * CustomPrintStyle.java
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

import org.apache.commons.lang3.builder.ToStringStyle;

import acme.framework.entities.DomainEntity;
import acme.framework.helpers.StringHelper;

public class CustomPrintStyle extends ToStringStyle {

	// Serialisation identifier -----------------------------------------------

	private static final long serialVersionUID = -7153302077570861674L;


	// Constructors -----------------------------------------------------------

	public CustomPrintStyle() {
		super();
		this.setUseShortClassName(false);
		this.setUseIdentityHashCode(false);
		this.setArraySeparator(", ");
		this.setContentStart("{");
		this.setFieldSeparator(System.lineSeparator() + "\t");
		this.setFieldSeparatorAtStart(true);
		this.setContentEnd(System.lineSeparator() + "}");
		this.setArrayContentDetail(true);
		this.setDefaultFullDetail(true);
	}

	// ToStringStyle interface ------------------------------------------------

	@Override
	protected void appendDetail(final StringBuffer buffer, final String fieldName, final Object value) {
		assert buffer != null;
		assert !StringHelper.isBlank(fieldName);
		// assert object is nullable

		String left, right;

		if (value == null) {
			left = right = "";
		} else if (value instanceof String) {
			left = right = "\"";
		} else if (value instanceof Character) {
			left = right = "\'";
		} else if (!(value instanceof DomainEntity) && !(value instanceof Number)) {
			left = "<<";
			right = ">>";
		} else {
			left = right = "";
		}

		buffer.append(left);
		buffer.append(value);
		buffer.append(right);
	}

	// Business methods -------------------------------------------------------

	public void appendObject(final StringBuffer buffer, final Object value) {
		assert buffer != null;
		// assert value is nullable

		this.appendDetail(buffer, null, value);
	}

}
