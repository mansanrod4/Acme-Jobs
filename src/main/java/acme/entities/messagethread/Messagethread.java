
package acme.entities.messagethread;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import acme.framework.entities.Authenticated;
import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

public class Messagethread extends DomainEntity {

	//Serialisation Identifier------------------------------------

	private static final long					serialVersionUID	= 1L;

	//Atributtes--------------------------------------------------

	@NotBlank
	private String								title;

	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	private Date								moment;

	// Relationships ---------------------------------------------
	@NotEmpty
	@ManyToMany()
	private Collection<@Valid Authenticated>	users;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Authenticated						author;

}
