/*
 * UserRole.java
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

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import acme.datatypes.UserIdentity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public abstract class UserRole extends DomainEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long serialVersionUID = 1L;


	// Attributes -------------------------------------------------------------

	public GrantedAuthority getAuthority() {
		GrantedAuthority result;
		String authority;

		authority = String.format("AUTH_%s", this.getClass().getSimpleName());
		result = new SimpleGrantedAuthority(authority);

		return result;
	}

	public String getAuthorityName() {
		String result;

		result = this.getClass().getSimpleName();

		return result;
	}

	public UserIdentity getIdentity() {
		UserIdentity result;

		result = this.userAccount.getIdentity();

		return result;
	}


	// Relationships ----------------------------------------------------------

	@NotNull
	@Valid
	@ManyToOne
	private UserAccount userAccount;

}
