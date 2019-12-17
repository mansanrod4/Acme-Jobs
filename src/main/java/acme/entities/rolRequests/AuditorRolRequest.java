
package acme.entities.rolRequests;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import acme.framework.entities.DomainEntity;
import acme.framework.entities.UserAccount;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class AuditorRolRequest extends DomainEntity {

	private static final long	serialVersionUID	= 1L;

	private boolean				approved;

	@NotNull
	@Valid
	@OneToOne
	private UserAccount			user;


	//Atributos info derivados
	@Transient
	public String getUserFullName() {
		return this.user.getIdentity().getFullName();
	}

	@Transient
	public String getUserName() {
		return this.user.getIdentity().getName();
	}

	@Transient
	public String getUserEmail() {
		return this.user.getIdentity().getEmail();
	}

}
