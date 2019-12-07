
package acme.entities.banners;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.CreditCardNumber;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class CommercialBanner extends Banner {

	//Serialisation Identifier

	private static final long	serialVersionUID	= 1L;

	//Atributos

	@NotBlank
	private String				accountHolder;

	@NotBlank
	@CreditCardNumber
	private String				creditCardNumber;

	@NotBlank
	@Pattern(regexp = "^(1[0-2]|0[1-9]|\\d)\\/(\\d{2})$", message = "MM/YY")
	private String				expirationDate;

	@NotBlank
	@Pattern(regexp = "^\\d{3}$")
	private String				cvv;

	// Relationships

}
