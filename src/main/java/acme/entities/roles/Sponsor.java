
package acme.entities.roles;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.CreditCardNumber;

import acme.framework.entities.UserRole;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Sponsor extends UserRole {

	// Serialisation Identifier-----------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes ------------------------------------------------

	@NotBlank
	private String				orgName;

	private String				accountHolder;

	@CreditCardNumber
	private String				creditCardNumber;

	@Pattern(regexp = "^(1[0-2]|0[1-9]|\\d)\\/(\\d{2})$", message = "MM/YY")
	private String				expirationDate;

	@Pattern(regexp = "^\\d{3}$")
	private String				cvv;

	// Relationships
}
