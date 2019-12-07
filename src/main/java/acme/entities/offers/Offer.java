
package acme.entities.offers;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import acme.framework.datatypes.Money;
import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

public class Offer extends DomainEntity {

	//Identificador

	private static final long	serialVersionUID	= 1L;

	//Atributos

	@NotBlank
	private String				title;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Past
	private Date				moment;

	//Atributo Deadline
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				deadLine;

	@NotBlank
	@Column(length = 1024)
	private String				text;

	//Atributo Precio Oferta

	@NotNull
	@Valid
	private Money				money;

	//Ticker

	@Pattern(regexp = "O\\p{Lu}{4}-\\d{5}", message = "OXXXX-99999")
	@Column(unique = true)
	@NotNull
	private String				ticker;

}
