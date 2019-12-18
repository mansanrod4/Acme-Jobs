
package acme.entities.Audits;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import acme.entities.jobs.Job;
import acme.entities.roles.Auditor;
import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Audit extends DomainEntity {

	//Serialisation Identifier------------------------------------

	private static final long	serialVersionUID	= 1L;

	//Atributtes--------------------------------------------------

	@NotBlank
	private String				title;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Past
	private Date				moment;

	@NotBlank
	@Column(length = 1024)
	private String				body;

	@NotNull
	@Enumerated
	private Status				status;

	// Relationships ---------------------------------------------

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Auditor				auditor;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Job					job;

}
