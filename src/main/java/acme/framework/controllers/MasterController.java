/*
 * MasterController.java
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

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import acme.framework.entities.Administrator;
import acme.framework.helpers.PrincipalHelper;

@Controller
public class MasterController implements ApplicationContextAware {

	// Internal state ---------------------------------------------------------

	private ConfigurableApplicationContext context;


	// ApplicationContextAware interface --------------------------------------

	@Override
	public void setApplicationContext(final ApplicationContext context) throws BeansException {
		assert context != null;

		this.context = (ConfigurableApplicationContext) context;
	}

	// Welcome ----------------------------------------------------------------

	@GetMapping("/")
	public ModelAndView index() {
		ModelAndView result;

		result = new ModelAndView("redirect:/master/welcome");

		return result;
	}

	@GetMapping("/master/welcome")
	public ModelAndView welcome() {
		ModelAndView result;

		result = new ModelAndView("master/welcome");

		return result;
	}

	// Sign in ----------------------------------------------------------------

	@GetMapping("/master/sign-in")
	public ModelAndView signIn(@RequestParam final Map<String, String> params) {
		ModelAndView result;

		result = new ModelAndView("master/sign-in");
		result.addObject("username", "");
		result.addObject("password", "");
		result.addObject("remember", false);

		return result;
	}

	// HINT: Note that there's no POST master/sign-in controller since it's Spring
	// HINT+ that cares of processing the sign in form when it's posted.

	// Sign out ---------------------------------------------------------------

	// HINT: Note that there's no GET/POST master/sign-out controller since the log
	// HINT+ out process is controlled by the WebSecurityConfigurerAdapter

	// Footer -----------------------------------------------------------------

	@GetMapping("/master/company")
	public ModelAndView company() {
		final ModelAndView result;

		result = new ModelAndView();
		result.setViewName("master/company");

		return result;
	}

	@GetMapping("/master/license")
	public ModelAndView license() {
		final ModelAndView result;

		result = new ModelAndView();
		result.setViewName("master/license");

		return result;
	}

	// Panic ------------------------------------------------------------------

	@GetMapping("/master/panic")
	public ModelAndView panic(final HttpServletRequest request, final HttpServletResponse response) {
		ModelAndView result;

		result = new ModelAndView();
		result.setStatus(HttpStatus.valueOf(response.getStatus()));
		result.setViewName("master/panic");

		return result;
	}

	@GetMapping("/master/oops")
	public ModelAndView oops() {
		throw new RuntimeException("This is a test exception!");
	}

	// Referrer ---------------------------------------------------------------

	@GetMapping("/master/referrer")
	public ModelAndView referrer() {
		ModelAndView result;

		result = new ModelAndView();
		result.setViewName("master/referrer");

		return result;
	}

	// Shutdown ---------------------------------------------------------------

	@GetMapping("/master/shutdown")
	public void shutdown() {
		Assert.state(PrincipalHelper.get().hasRole(Administrator.class), "default.error.not-unauthorised");

		int code;

		// TODO: the process typically exits with code 1 instead of 0. Need to dig into this issue.
		code = SpringApplication.exit(this.context, () -> 0);
		System.exit(code);
	}

}
