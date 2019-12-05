/*
 * AbstractUpdateService.java
 *
 * Copyright (c) 2019 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.framework.services;

import org.springframework.stereotype.Service;

import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.components.Response;
import acme.framework.entities.UserRole;

@Service
public interface AbstractUpdateService<R extends UserRole, E> extends //
	AbstractService<R, E>, //
	AuthoriseMethod<R, E>, //
	BindMethod<R, E>, UnbindMethod<R, E>, //
	FindOneMethod<R, E>, //
	ValidateMethod<R, E>, //
	UpdateMethod<R, E>, //
	OnSuccessMethod<R, E>, OnFailureMethod<R, E> {

	@Override
	boolean authorise(final Request<E> request);

	@Override
	void bind(Request<E> request, final E entity, final Errors errors);

	@Override
	void unbind(Request<E> request, final E entity, final Model model);

	@Override
	E findOne(Request<E> request);

	@Override
	void validate(final Request<E> request, final E entity, Errors errors);

	@Override
	void update(final Request<E> request, final E entity);

	@Override
	default void onSuccess(final Request<E> request, final Response<E> response) {
	}

	@Override
	default void onFailure(final Request<E> request, final Response<E> response, final Throwable oops) {
	}

}
