/*
 * CustomBindingErrorProcessor.java
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

import org.springframework.beans.PropertyAccessException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DefaultBindingErrorProcessor;
import org.springframework.validation.FieldError;

public class CustomBindingErrorProcessor extends DefaultBindingErrorProcessor {

	// DefaultBindingErrorProcessor interface ---------------------------------

	@Override
	public void processPropertyAccessException(final PropertyAccessException oops, final BindingResult bindingResult) {
		assert oops != null;
		assert bindingResult != null;

		String objectName, attributeName;
		Object value;
		String[] codes;
		Object[] arguments;
		FieldError error;

		objectName = bindingResult.getObjectName();
		attributeName = oops.getPropertyName();
		value = oops.getValue();
		codes = new String[] {
			"default.error.conversion"
		};
		arguments = new Object[] {
			attributeName, value
		};

		error = new FieldError(objectName, attributeName, value, true, codes, arguments, "Invalid value");
		bindingResult.addError(error);
	}

}
