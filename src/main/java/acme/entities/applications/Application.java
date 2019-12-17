
package acme.entities.applications;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import acme.entities.jobs.Job;
import acme.entities.roles.Worker;
import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(indexes = {
	@Index(columnList = "status")
})
public class Application extends DomainEntity {
	//Serialisation Identifier------------------------------------

	private static final long	serialVersionUID	= 1L;

	//Atributtes--------------------------------------------------

	@Column(unique = true)
	@NotBlank
	private String				referenceNumber;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Past
	private Date				creationMoment;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Past
	private Date				updateMoment;

	@NotNull
	private ApplicationStatus	status;

	@NotBlank
	@Column(length = 512)
	private String				statement;
	@NotBlank
	@Column(length = 512)
	private String				skills;
	@NotBlank
	@Column(length = 512)
	private String				qualifications;

	//Relationships ---------------------------------------------

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Worker				worker;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Job					job;
}
