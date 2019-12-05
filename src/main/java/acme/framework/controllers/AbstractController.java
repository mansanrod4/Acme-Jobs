/*
 * AbstractController.java
 *
 * Copyright (c) 2019 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.framework.controllers;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import acme.components.CustomCommand;
import acme.framework.components.BasicCommand;
import acme.framework.components.Command;
import acme.framework.components.CommandManager;
import acme.framework.components.Errors;
import acme.framework.components.HttpMethod;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.components.Response;
import acme.framework.entities.UserRole;
import acme.framework.helpers.Assert;
import acme.framework.helpers.CollectionHelper;
import acme.framework.helpers.CommandHelper;
import acme.framework.helpers.HttpMethodHelper;
import acme.framework.helpers.StringHelper;
import acme.framework.helpers.ValidationHelper;
import acme.framework.services.AbstractService;
import acme.framework.utilities.ServiceWrapper;

@Controller
public abstract class AbstractController<R extends UserRole, E> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AbstractController<R, E>	self;

	private Class<R>					roleClazz;
	private Class<E>					entityClazz;

	private String						listViewName;
	private String						formViewName;

	private CommandManager<R, E>		commandManager;

	// Transaction management ------------------------------------------------.

	@Autowired
	private PlatformTransactionManager	transactionManager;
	private TransactionStatus			transactionStatus;


	private void startTransaction() {
		TransactionDefinition transactionDefinition;

		transactionDefinition = new DefaultTransactionDefinition();
		this.transactionStatus = this.transactionManager.getTransaction(transactionDefinition);
	}

	private void commitTransaction() {
		assert this.isTransactionActive();

		this.transactionManager.commit(this.transactionStatus);
		this.transactionStatus = null;
	}

	private void rollbackTransaction() {
		assert this.isTransactionActive();

		this.transactionManager.rollback(this.transactionStatus);
		this.transactionStatus = null;
	}

	private boolean isTransactionActive() {
		boolean result;

		result = this.transactionStatus != null && !this.transactionStatus.isCompleted();

		return result;
	}

	// Command Management -----------------------------------------------------

	public void addBasicCommand(final BasicCommand basicCommand, final AbstractService<R, E> service) {
		assert basicCommand != null;
		assert !this.commandManager.isRegistered(basicCommand);
		assert service != null;

		this.commandManager.addBasicCommand(basicCommand, service);
	}

	public void addCustomCommand(final CustomCommand customCommand, final BasicCommand baseCommand, final AbstractService<R, E> service) {
		assert customCommand != null;
		assert !this.commandManager.isRegistered(customCommand);
		assert service != null;

		this.commandManager.addCustomCommand(customCommand, baseCommand, service);
	}

	// Initialisation ---------------------------------------------------------

	@SuppressWarnings("unchecked")
	@PostConstruct
	private void initialise() {
		Class<?> types[];
		PropertyNamingStrategy.KebabCaseStrategy translator;
		String roleName, entityName;

		types = GenericTypeResolver.resolveTypeArguments(this.getClass(), AbstractController.class);

		if (types == null || types.length != 2) {
			System.err.printf("I'm sorry, %s cannot be instantiated.%n", this.getClass().getName());
			System.err.printf("I can't resolve its generic types.%n");
			System.exit(1);
		}

		this.roleClazz = (Class<R>) types[0];
		this.entityClazz = (Class<E>) types[1];

		translator = new PropertyNamingStrategy.KebabCaseStrategy();
		roleName = translator.translate(this.roleClazz.getSimpleName());
		entityName = translator.translate(this.entityClazz.getSimpleName());

		this.listViewName = String.format("%s/%s/list", roleName, entityName);
		this.formViewName = String.format("%s/%s/form", roleName, entityName);

		this.commandManager = new CommandManager<R, E>();
	}

	// Handler ----------------------------------------------------------------

	@RequestMapping(value = "{endpoint}", method = {
		RequestMethod.GET, RequestMethod.POST
	})
	public ModelAndView handleRequest( //
		@PathVariable("endpoint") final String endpoint, //
		@RequestParam final Map<String, Object> model, //
		final HttpServletRequest servletRequest, //
		final HttpServletResponse servletResponse, //
		final Locale locale) {

		ModelAndView result;
		String servletMethod;
		HttpMethod method;
		Command command;
		BasicCommand baseCommand;
		Request<E> request;
		Response<E> response;
		ServiceWrapper<R, E> service;

		result = null;
		request = null;
		response = null;
		service = null;

		try {
			// HINT: let's start a new transaction.

			this.startTransaction();

			// HINT: let's deal with CSRF hacking.

			if (model.containsKey("_csrf")) {
				// HINT: Remove it so that a new token is generated automatically
				model.remove("_csrf");
			}

			// HINT: let's make sure that the command is available.

			servletMethod = servletRequest.getMethod();
			method = HttpMethodHelper.parse(servletMethod);
			command = CommandHelper.parse(endpoint);
			Assert.state(this.commandManager.isRegistered(command), locale, "default.error.endpoint-unavailable");
			baseCommand = this.commandManager.getBaseCommand(command);

			// HINT: let's create the request object.

			request = new Request<E>( //
				method, command, baseCommand, //
				model, locale, //
				servletRequest, servletResponse);

			// HINT: let's make sure that the principal has the appropriate role.

			Assert.state(request.getPrincipal().hasRole(this.roleClazz), locale, "default.error.not-authorised");
			request.getPrincipal().setActiveRole(this.roleClazz);

			// HINT: let's request authorisation from the service.

			service = new ServiceWrapper<R, E>(this.commandManager.getService(command));
			Assert.state(service.authorise(request), locale, "default.error.not-authorised");

			// HINT: let's dispatch the request building on the HTTP method used.
			// HINT: realise that the dispatcher method is invoked through the 'self' reference to this
			// HINT+ controller because they must be executed within the current transaction.

			switch (request.getMethod()) {
			case GET:
				response = this.self.doGet(request, service);
				break;
			case POST:
				response = this.self.doPost(request, service);
				break;
			default:
				Assert.state(false, locale, "default.error.endpoint-unavailable");
				break;
			}

			// HINT: let's build the requested view and let's add some predefined attributes to the model.

			result = this.buildRequestedView(request, response);
			result.addObject("command", endpoint);
			result.addObject("principal", request.getPrincipal());

			// HINT: let's commit or rollback the transaction depending on whether there are errors or not in the response.
			// HINT+ note that the 'onSuccess' and the 'onFailure' methods must be executed in fresh transactions.

			if (!response.hasErrors()) {
				this.commitTransaction();
				this.startTransaction();
				service.onSuccess(request, response);
				this.commitTransaction();
			} else {
				this.rollbackTransaction();
				this.startTransaction();
				service.onFailure(request, response, null);
				this.commitTransaction();
			}
		} catch (Throwable oops) {
			// HINT: if a throwable is caught, then the current transaction must be rollbacked, if any,
			// HINT: the service must execute the 'onFailure' method, and the panic view must be returned.

			if (this.isTransactionActive()) {
				this.rollbackTransaction();
			}
			if (service != null) {
				this.startTransaction();
				service.onFailure(request, response, oops);
				this.commitTransaction();
			}
			result = this.buildPanicView(request, response, oops);
		}

		// HINT: must always return a 'ModelAndView' object, be it the user-defined one or a panic one.

		assert result != null;

		return result;
	}

	@Transactional(TxType.MANDATORY)
	public Response<E> doGet(final Request<E> request, final ServiceWrapper<R, E> service) {
		assert request != null;
		assert service != null;

		Response<E> result;
		Collection<E> list;
		E entity;
		String view;
		Model model;
		Errors errors;

		view = null;
		model = null;
		errors = null;

		// HINT: let's dispatch according to the semantics of the base command.

		switch (request.getBaseCommand()) {
		case LIST:
			// HINT: a LIST requests using the 'GET' method is served as follows:
			// HINT+ a) find the many objects to be shown; b) unbind the list into a fresh model.
			// HINT+ Note that no errors are expected, but exceptions might be thrown.
			list = service.findMany(request);
			view = this.listViewName;
			model = new Model();
			this.unbind(request, list, model, service);
			errors = new Errors();
			break;
		case SHOW:
		case UPDATE:
		case DELETE:
			// HINT: a SHOW, UPDATE, or DELETE requests using the 'GET' method are served as follows:
			// HINT+ a) find the object to be shown, updated, or deleted using the data in the request;
			// HINT+ b) unbind that object into a fresh model.  Note that no errors are expected, but
			// HINT+ exceptions might be thrown.
			entity = service.findOne(request);
			view = this.formViewName;
			model = new Model();
			service.unbind(request, entity, model);
			errors = new Errors();
			break;
		case CREATE:
			// HINT: a CREATE request using the 'GET' method are served as follows:
			// HINT+ a) instantiate the object to create using the data in the request; b) unbind that
			// HINT+ object to a fresh model.  Note that no errors are expected, but exceptions might be thrown.
			entity = service.instantiate(request);
			view = this.formViewName;
			model = new Model();
			service.unbind(request, entity, model);
			errors = new Errors();
			break;
		default:
			Assert.state(false, request.getLocale(), "default.error.endpoint-unavailable");
			break;
		}

		// HINT: unless an exception is thrown, the previous statements must produce a view name, a model, and an errors object.

		assert !StringHelper.isBlank(view);
		assert model != null;
		assert errors != null;

		result = new Response<E>(view, model, errors);

		return result;
	}

	@Transactional(TxType.MANDATORY)
	public Response<E> doPost(final Request<E> request, final ServiceWrapper<R, E> service) {
		assert request != null;
		assert service != null;

		Response<E> result;
		E entity;
		String view;
		Model model;
		Errors errors;

		// HINT: handling a POST request requires two independent steps.

		// HINT: the first step fetches the entity to be handled.

		entity = null;

		switch (request.getBaseCommand()) {
		case CREATE:
			// HINT: a CREATE request involves instantiating the appropriate entity from the request.
			entity = service.instantiate(request);
			break;
		case UPDATE:
		case DELETE:
			// HINT: an UPDATE or a DELETE request involves finding the entity to be updated or deleted.
			entity = service.findOne(request);
			break;
		default:
			Assert.state(false, request.getLocale(), "default.error.endpoint-unavailable");
			break;
		}

		assert entity != null;

		// HINT: the second step cares of performing the command on the entity fetched by the previous step.

		errors = new Errors();
		switch (request.getBaseCommand()) {
		case CREATE:
			// HINT: dealing with a CREATE request involves the following steps: a) binding the request onto
			// HINT+ the entity fetched by the previous step; b) performing contraint validation on it;
			// HINT+ c) performing user-defined validation; d) if there are not any errors, then invoking the
			// HINT+ service to create the entity.
			service.bind(request, entity, errors);
			ValidationHelper.validate(request, entity, errors);
			service.validate(request, entity, errors);
			if (!errors.hasErrors()) {
				service.create(request, entity);
			}
			break;
		case UPDATE:
			// HINT: dealing with an UPDATE request involves the following steps: a) binding the request onto
			// HINT+ the entity fetched by the previous step; b) performing contraint validation on it;
			// HINT+ c) performing user-defined validation; d) if there are not any errors, then invoking the
			// HINT+ service to update the entity.
			service.bind(request, entity, errors);
			ValidationHelper.validate(request, entity, errors);
			service.validate(request, entity, errors);
			if (!errors.hasErrors()) {
				service.update(request, entity);
			}
			break;
		case DELETE:
			// HINT: dealing with a DELETE request involves validating that the entity can be deleted
			// HINT+ and then invoking the service to delete the entity.
			service.validate(request, entity, errors);
			if (!errors.hasErrors()) {
				service.delete(request, entity);
			}
			break;
		default:
			Assert.state(false, request.getLocale(), "default.error.endpoint-unavailable");
			break;
		}

		if (!errors.hasErrors()) {
			// HINT: if there aren't any errors, then we must redirect to the referrer view, which cares of
			// HINT+ returning to the apropriate listing or /master/welcome.
			view = "redirect:/master/referrer";
			model = new Model();
		} else {
			// HINT: if there are some errors, then we must redirect to the same view using the same model, so
			// HINT+ that the user may make changes and submit the form again.
			view = this.formViewName;
			model = new Model();
			model.append(request.getModel());
		}

		// HINT: unless an exception is thrown, the previous statements must produce a view name, a model, and an errors object.

		assert !StringHelper.isBlank(view);
		assert model != null;
		assert errors != null;

		result = new Response<E>(view, model, errors);

		return result;
	}

	// Internal methods -------------------------------------------------------

	private void unbind(final Request<E> request, final Collection<E> list, final Model model, final ServiceWrapper<R, E> service) {
		assert request != null;
		assert !CollectionHelper.someNull(list);
		assert service != null;

		Model entityModel;

		for (E entity : list) {
			entityModel = new Model();
			service.unbind(request, entity, entityModel);
			model.append(entityModel);
		}
	}

	private ModelAndView buildRequestedView(final Request<E> request, final Response<E> response) {
		ModelAndView result;
		String name;
		int size;
		Map<String, Object> model;
		String key, fullKey;
		Object value;
		List<String> messages;
		String text;

		result = new ModelAndView();
		result.setStatus(HttpStatus.OK);
		result.setViewName(response.getView());

		size = response.getModel().size();
		result.addObject("model$size", size);
		for (int index = 0; index < size; index++) {
			model = response.getModel().get(index);
			for (Entry<String, Object> entry : model.entrySet()) {
				key = entry.getKey();
				value = entry.getValue();

				if (size == 1) {
					fullKey = key;
				} else {
					fullKey = String.format("%s[%d]", key, index);
				}

				result.addObject(fullKey, value);
			}
		}

		// TODO: errors are not bound if the model is a list, which avoids editing lists.
		if (response.hasErrors()) {
			for (Entry<String, List<String>> entry : response.getErrors()) {
				name = String.format("%s$error", entry.getKey());
				messages = entry.getValue();
				text = StringHelper.toString(messages, ". ", ".");
				result.addObject(name, text);
			}
		}

		return result;
	}

	private ModelAndView buildPanicView(final Request<E> request, final Response<E> response, final Throwable oops) {
		ModelAndView result;

		result = new ModelAndView();
		result.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		result.setViewName("master/panic");
		result.addObject("oops", oops);

		return result;
	}

}
