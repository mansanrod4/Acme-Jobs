/*
 * Principal.java
 *
 * Copyright (c) 2019 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.framework.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import acme.framework.helpers.CollectionHelper;
import acme.framework.helpers.ReflectionHelper;
import acme.framework.helpers.StringHelper;

public class Principal implements UserDetails {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	// UserDetails interface --------------------------------------------------

	private String				username;


	@Override
	public String getUsername() {
		return this.username;
	}

	public void setUsername(final String username) {
		assert !StringHelper.isBlank(username);

		this.username = username;
	}


	private String password;


	@Override
	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		assert !StringHelper.isBlank(password);

		this.password = password;
	}


	private boolean enabled;


	@Override
	public boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(final boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}


	private Collection<GrantedAuthority> authorities;


	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	public void setAuthorities(final Collection<UserRole> roles) {
		assert !CollectionHelper.someNull(roles);

		GrantedAuthority authority;

		this.roleMap = new HashMap<Class<? extends UserRole>, Integer>();
		this.authorities = new ArrayList<GrantedAuthority>();
		for (UserRole role : roles) {
			authority = role.getAuthority();
			this.authorities.add(authority);
			this.roleMap.put(role.getClass(), role.getId());
		}
	}


	// Role management --------------------------------------------------------

	private Class<? extends UserRole>				activeRole;
	private Map<Class<? extends UserRole>, Integer>	roleMap;


	public boolean hasRole(final Class<? extends UserRole> clazz) {
		assert clazz != null;
		assert this.getAuthorities() != null;

		boolean result;

		result = this.roleMap.containsKey(clazz);

		return result;
	}

	public boolean hasRole(final String name) {
		assert !StringHelper.isBlank(name);
		assert this.getAuthorities() != null;
		assert ReflectionHelper.existsClass(name, UserRole.class);

		boolean result;
		Class<? extends UserRole> clazz;

		clazz = ReflectionHelper.getClass(name, UserRole.class);
		result = this.hasRole(clazz);

		return result;
	}

	public Class<? extends UserRole> getActiveRole() {
		Class<? extends UserRole> result;

		result = this.activeRole;

		return result;
	}

	public void setActiveRole(final Class<? extends UserRole> roleClazz) {
		assert roleClazz != null;

		this.activeRole = roleClazz;
	}

	public int getActiveRoleId() {
		int result;

		assert this.roleMap.containsKey(this.activeRole);
		result = this.roleMap.get(this.activeRole);

		return result;
	}


	private int accountId;


	public int getAccountId() {
		return this.accountId;
	}

	public void setAccountId(final int accountId) {
		assert accountId != 0;

		this.accountId = accountId;
	}

	// Derived attributes -----------------------------------------------------

	public boolean isAnonynmous() {
		boolean result;

		result = this.username.equals("anonymous");

		return result;
	}

	public boolean isAuthenticated() {
		boolean result;

		result = !this.username.equals("anonymous");

		return result;
	}

}
