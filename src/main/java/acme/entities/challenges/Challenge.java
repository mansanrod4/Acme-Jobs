
package acme.entities.challenges;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import acme.framework.datatypes.Money;
import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Challenge extends DomainEntity {
	//Identificador

	private static final long	serialVersionUID	= 1L;

	//Atributos

	@NotBlank
	private String				title;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Past
	private Date				moment;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				deadline;

	@NotBlank
	@Column(length = 1024)
	private String				description;

	@NotBlank
	private String				goalBronze;

	@NotBlank
	private String				goalSilver;

	@NotBlank
	private String				goalGold;

	@NotNull
	@Valid
	private Money				rewardBronze;

	@NotNull
	@Valid
	private Money				rewardSilver;

	@NotNull
	@Valid
	private Money				rewardGold;

}
