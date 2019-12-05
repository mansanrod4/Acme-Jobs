/*
 * Response.java
 *
 * Copyright (c) 2019 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.framework.components;

import acme.framework.helpers.StringHelper;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response<E> {

	// Internal state ---------------------------------------------------------

	private String	view;
	private Model	model;
	private Errors	errors;


	// Constructors -----------------------------------------------------------

	public Response(final String view, final Model model, final Errors errors) {
		assert !StringHelper.isBlank(view);
		assert model != null;
		assert errors != null;

		this.view = view;
		this.model = model;
		this.errors = errors;
	}

	// Business methods -------------------------------------------------------

	public boolean hasErrors() {
		boolean result;

		result = this.errors.hasErrors();

		return result;
	}

}
