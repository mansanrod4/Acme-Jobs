/*
 * ValidationHelper.java
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

import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Validator;

import acme.framework.components.Errors;
import acme.framework.components.Request;

public class ValidationHelper {

	// Business methods -------------------------------------------------------

	public static void validate(final Request<?> request, final Object target, final Errors errors) {
		assert request != null;
		assert target != null;
		assert errors != null;

		String name;
		BeanPropertyBindingResult bindingResult;
		Validator validator;

		name = String.format("%s@%d", target.getClass().getName(), target.hashCode());
		bindingResult = new BeanPropertyBindingResult(target, name);
		validator = FactoryHelper.getValidator();
		validator.validate(target, bindingResult);

		ErrorsHelper.transferErrors(request, bindingResult, errors);
	}
}
