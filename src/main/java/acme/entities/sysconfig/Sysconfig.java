
package acme.entities.sysconfig;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Sysconfig extends DomainEntity {

	// Identificador

	private static final long	serialVersionUID	= 1L;

	// Atributos

	@NotBlank
	private String				spamwords;

	@NotNull
	private Double				threshold;

	// Atributos derivados

}
