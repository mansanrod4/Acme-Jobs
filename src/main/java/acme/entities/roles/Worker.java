
package acme.entities.roles;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

import acme.framework.entities.UserRole;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Worker extends UserRole {

	// Serialisation Identifier-----------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes ------------------------------------------------

	@NotBlank
	@Column(length = 1024)
	private String				qualifications;

	@NotBlank
	@Column(length = 1024)
	private String				skills;

	// Derived attributes ----------------------------------------

	// Relationships ---------------------------------------------

}
