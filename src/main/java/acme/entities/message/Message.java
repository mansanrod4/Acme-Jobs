
package acme.entities.message;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import acme.entities.messagethread.Messagethread;
import acme.framework.entities.Authenticated;
import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

public class Message extends DomainEntity {

	//Serialisation Identifier------------------------------------

	private static final long	serialVersionUID	= 1L;

	//Atributtes--------------------------------------------------
	@NotBlank
	private String				title;

	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				moment;

	private String				tags;

	@NotBlank
	@Column(length = 1024)
	private String				body;

	// Relationships ---------------------------------------------

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Messagethread		thread;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Authenticated		author;

}
