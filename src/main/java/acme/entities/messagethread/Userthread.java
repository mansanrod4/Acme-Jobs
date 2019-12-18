
package acme.entities.messagethread;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import acme.framework.entities.Authenticated;
import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Userthread extends DomainEntity {

	//Serialisation Identifier------------------------------------

	private static final long	serialVersionUID	= 1L;
	//Atributtes--------------------------------------------------

	@NotNull
	private Boolean				creator;

	// Relationships ---------------------------------------------
	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Messagethread		thread;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Authenticated		authenticated;

}
