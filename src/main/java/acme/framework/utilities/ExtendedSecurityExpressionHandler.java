/*
 * ExtendedSecurityExpressionHandler.java
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

import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

public class ExtendedSecurityExpressionHandler extends DefaultWebSecurityExpressionHandler {

	// DefaultWebSecurityExpressionHandler interface --------------------------

	@Override
	protected SecurityExpressionRoot createSecurityExpressionRoot(final Authentication authentication, final FilterInvocation filterInvocation) {
		ExtendedSecurityExpressionRoot result;
		AuthenticationTrustResolverImpl trustResolver;

		trustResolver = new AuthenticationTrustResolverImpl();

		result = new ExtendedSecurityExpressionRoot(authentication, filterInvocation);
		result.setPermissionEvaluator(super.getPermissionEvaluator());
		result.setTrustResolver(trustResolver);
		result.setRoleHierarchy(super.getRoleHierarchy());
		// HINT: it's be nice to remove the prefix, but Spring seems to require one :/
		result.setDefaultRolePrefix("AUTH_");

		return result;
	}

}
