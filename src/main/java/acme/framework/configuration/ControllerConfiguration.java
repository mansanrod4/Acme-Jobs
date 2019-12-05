/*
 * ControllerConfiguration.java
 *
 * Copyright (c) 2019 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.framework.configuration;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ControllerConfiguration {

	// Handlers ---------------------------------------------------------------

	@ExceptionHandler(Throwable.class)
	public ModelAndView handleException(final Throwable oops) {
		ModelAndView result;

		result = new ModelAndView();
		result.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		result.setViewName("master/panic");
		result.addObject("oops", oops);

		return result;
	}

}
