
package acme.entities.roles;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import acme.framework.entities.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Auditor extends UserRole {

	//Identificador

	private static final long	serialVersionUID	= 1L;

	//Atributos

	@NotNull
	private String				firm;

	@NotNull
	@Column(length = 1024)
	private String				statement;
}
