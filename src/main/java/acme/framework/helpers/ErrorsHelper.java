/*
 * ErrorsHelper.java
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

import java.util.Locale;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import acme.framework.components.Errors;
import acme.framework.components.Request;

public class ErrorsHelper {

	// Business methods -------------------------------------------------------

	public static void transferErrors(final Request<?> request, final BindingResult bindingResult, final Errors errors) {
		assert request != null;
		assert bindingResult != null;
		assert errors != null;

		String attributeName, message;

		for (ObjectError error : bindingResult.getGlobalErrors()) {
			attributeName = "*";
			message = ErrorsHelper.computeMessage(request, error);
			errors.add(attributeName, message);
		}

		for (FieldError error : bindingResult.getFieldErrors()) {
			attributeName = error.getField();
			message = ErrorsHelper.computeMessage(request, error);
			errors.add(attributeName, message);
		}
	}

	private static String computeMessage(final Request<?> request, final ObjectError error) {
		assert request != null;
		assert error != null;

		String code, defaultMessage, result;
		Object[] arguments;
		Locale locale;

		code = error.getCode();
		arguments = error.getArguments();
		defaultMessage = error.getDefaultMessage();
		locale = request.getLocale();
		result = MessageHelper.getMessage(code, arguments, defaultMessage, locale);

		return result;
	}

}
