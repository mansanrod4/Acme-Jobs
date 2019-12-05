/*
 * RememberMeLogoutHandler.java
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import acme.framework.helpers.PrincipalHelper;

public final class RememberMeLogoutHandler implements LogoutHandler {

	// LogoutHandler interface ------------------------------------------------

	@Override
	public void logout(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) {
		assert request != null;
		assert response != null;
		assert authentication != null;

		PrincipalHelper.handleSignOut();
	}
}
