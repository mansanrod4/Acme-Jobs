/*
 * PrincipalHelper.java
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

import java.util.Collection;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import acme.framework.entities.Principal;
import acme.framework.services.AuthenticationService;

@Component
public class PrincipalHelper {

	// Internal state ---------------------------------------------------------

	// TODO: note that this helper is a component because it requires the authentication service.
	// TODO+ it's not difficult to include such service as a named bean in the 'FactoryHelper' class.

	private static AuthenticationService authenticationService;


	public PrincipalHelper(final AuthenticationService authenticationService) {
		assert authenticationService != null;

		PrincipalHelper.authenticationService = authenticationService;
	}

	// Business methods -------------------------------------------------------

	@Transactional(TxType.SUPPORTS)
	public static Principal get() {
		Principal result;
		Collection<GrantedAuthority> authorities;
		Authentication authentication;
		SecurityContext context;

		context = SecurityContextHolder.getContext();
		authentication = context.getAuthentication();
		assert authentication instanceof RememberMeAuthenticationToken || authentication instanceof UsernamePasswordAuthenticationToken || authentication instanceof AnonymousAuthenticationToken;

		if (authentication instanceof RememberMeAuthenticationToken || authentication instanceof UsernamePasswordAuthenticationToken) {
			result = (Principal) authentication.getPrincipal();
		} else {
			result = (Principal) PrincipalHelper.authenticationService.loadUserByUsername("anonymous");
			authorities = result.getAuthorities();
			// TODO: move key to configuration file!
			authentication = new AnonymousAuthenticationToken("$tr0ng-K3y!", result, authorities);
			context.setAuthentication(authentication);
		}

		return result;
	}

	@Transactional(TxType.SUPPORTS)
	public static void handleUpdate() {
		assert PrincipalHelper.get().isAuthenticated();

		SecurityContext context;
		Authentication currentAuthentication, newAuthentication;
		Principal currentPrincipal, newPrincipal;
		Collection<GrantedAuthority> newAuthorities;

		context = SecurityContextHolder.getContext();
		currentAuthentication = context.getAuthentication();
		assert currentAuthentication instanceof RememberMeAuthenticationToken || currentAuthentication instanceof UsernamePasswordAuthenticationToken;
		currentPrincipal = (Principal) currentAuthentication.getPrincipal();
		newPrincipal = (Principal) PrincipalHelper.authenticationService.loadUserByUsername(currentPrincipal.getUsername());
		newAuthorities = newPrincipal.getAuthorities();

		if (currentAuthentication instanceof RememberMeAuthenticationToken) {
			// TODO: move key to configuration file!
			newAuthentication = new RememberMeAuthenticationToken("$tr0ng-K3y!", newPrincipal, newAuthorities);
		} else {
			assert currentAuthentication instanceof UsernamePasswordAuthenticationToken;
			newAuthentication = new UsernamePasswordAuthenticationToken(newPrincipal, null, newAuthorities);
		}

		context.setAuthentication(newAuthentication);
	}

	@Transactional(TxType.SUPPORTS)
	public static void handleSignOut() {
		Principal principal;
		Collection<GrantedAuthority> authorities;
		Authentication authentication;
		SecurityContext context;

		context = SecurityContextHolder.getContext();
		principal = (Principal) PrincipalHelper.authenticationService.loadUserByUsername("anonymous");
		authorities = principal.getAuthorities();
		// TODO: move key to configuration file!
		authentication = new AnonymousAuthenticationToken("$tr0ng-K3y!", principal, authorities);
		context.setAuthentication(authentication);
	}

}
