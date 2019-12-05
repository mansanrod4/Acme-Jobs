/*
 * ServiceWrapper.java
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

import java.util.Collection;
import java.util.Map.Entry;

import javax.persistence.EntityManager;

import org.hibernate.engine.spi.EntityEntry;
import org.hibernate.engine.spi.SessionImplementor;

import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.components.Response;
import acme.framework.entities.UserRole;
import acme.framework.helpers.FactoryHelper;
import acme.framework.helpers.ReflectionHelper;
import acme.framework.services.AbstractService;
import acme.framework.services.AuthoriseMethod;
import acme.framework.services.BindMethod;
import acme.framework.services.CreateMethod;
import acme.framework.services.DeleteMethod;
import acme.framework.services.FindManyMethod;
import acme.framework.services.FindOneMethod;
import acme.framework.services.InstantiateMethod;
import acme.framework.services.OnFailureMethod;
import acme.framework.services.OnSuccessMethod;
import acme.framework.services.UnbindMethod;
import acme.framework.services.UpdateMethod;
import acme.framework.services.ValidateMethod;

public class ServiceWrapper<R extends UserRole, E> {

	// Internal state ---------------------------------------------------------

	private AbstractService<R, E> service;


	// Constructors -----------------------------------------------------------

	public ServiceWrapper(final AbstractService<R, E> service) {
		assert service != null;

		this.service = service;
	}

	// Business methods -------------------------------------------------------

	@SuppressWarnings("unchecked")
	public boolean authorise(final Request<E> request) {
		assert request != null;
		assert ReflectionHelper.supports(this.service, AuthoriseMethod.class);

		boolean result;

		result = ((AuthoriseMethod<R, E>) this.service).authorise(request);

		return result;
	}

	@SuppressWarnings("unchecked")
	public void bind(final Request<E> request, final E entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		assert ReflectionHelper.supports(this.service, BindMethod.class);

		((BindMethod<R, E>) this.service).bind(request, entity, errors);
	}

	@SuppressWarnings("unchecked")
	public void unbind(final Request<E> request, final E entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		assert ReflectionHelper.supports(this.service, UnbindMethod.class);

		((UnbindMethod<R, E>) this.service).unbind(request, entity, model);
	}

	@SuppressWarnings("unchecked")
	public E findOne(final Request<E> request) {
		assert request != null;
		assert ReflectionHelper.supports(this.service, FindOneMethod.class);

		E result;

		result = ((FindOneMethod<R, E>) this.service).findOne(request);
		assert result != null;
		this.detachPersistenceContext();

		return result;

	}

	@SuppressWarnings("unchecked")
	public Collection<E> findMany(final Request<E> request) {
		assert request != null;
		assert ReflectionHelper.supports(this.service, FindManyMethod.class);

		Collection<E> result;

		result = ((FindManyMethod<R, E>) this.service).findMany(request);
		assert result != null;
		this.detachPersistenceContext();

		return result;
	}

	@SuppressWarnings("unchecked")
	public E instantiate(final Request<E> request) {
		assert request != null;
		assert ReflectionHelper.supports(this.service, InstantiateMethod.class);

		E result;

		result = ((InstantiateMethod<R, E>) this.service).instantiate(request);
		assert result != null;

		return result;
	}

	@SuppressWarnings("unchecked")
	public void validate(final Request<E> request, final E entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		assert ReflectionHelper.supports(this.service, ValidateMethod.class);

		((ValidateMethod<R, E>) this.service).validate(request, entity, errors);
	}

	@SuppressWarnings("unchecked")
	public void create(final Request<E> request, final E entity) {
		assert request != null;
		assert entity != null;
		assert ReflectionHelper.supports(this.service, CreateMethod.class);

		((CreateMethod<R, E>) this.service).create(request, entity);
	}

	@SuppressWarnings("unchecked")
	public void update(final Request<E> request, final E entity) {
		assert request != null;
		assert entity != null;
		assert ReflectionHelper.supports(this.service, UpdateMethod.class);

		((UpdateMethod<R, E>) this.service).update(request, entity);
	}

	@SuppressWarnings("unchecked")
	public void delete(final Request<E> request, final E entity) {
		assert request != null;
		assert entity != null;
		assert ReflectionHelper.supports(this.service, DeleteMethod.class);

		((DeleteMethod<R, E>) this.service).delete(request, entity);
	}

	@SuppressWarnings("unchecked")
	public void onSuccess(final Request<E> request, final Response<E> response) {
		assert request != null;
		assert response != null;
		assert ReflectionHelper.supports(this.service, OnSuccessMethod.class);

		((OnSuccessMethod<R, E>) this.service).onSuccess(request, response);
	}

	@SuppressWarnings("unchecked")
	public void onFailure(final Request<E> request, final Response<E> response, final Throwable oops) {
		assert request != null;
		// response is nullable
		// oops is nullable
		assert ReflectionHelper.supports(this.service, OnFailureMethod.class);

		((OnFailureMethod<R, E>) this.service).onFailure(request, response, oops);
	}

	// Internal methods -------------------------------------------------------

	private void detachPersistenceContext() {
		EntityManager entityManager;
		SessionImplementor session;
		org.hibernate.engine.spi.PersistenceContext context;
		Entry<Object, EntityEntry>[] entries;
		Object entity;

		entityManager = FactoryHelper.getEntityManager();
		session = entityManager.unwrap(SessionImplementor.class);
		context = session.getPersistenceContext();
		entries = context.reentrantSafeEntityEntries();

		for (final Entry<Object, EntityEntry> entry : entries) {
			entity = entry.getKey();
			entityManager.detach(entity);
		}
	}

}
