
package acme.entities.banners;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.URL;

import acme.entities.roles.Sponsor;
import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public abstract class Banner extends DomainEntity {

	// Serialisation Identifier

	private static final long	serialVersionUID	= 1L;

	// Atributtes

	@NotBlank
	@URL
	private String				picture;

	@NotBlank
	private String				slogan;

	@NotBlank
	@URL
	private String				targetURL;

	// Relationships

	@Valid
	@ManyToOne(optional = false)
	private Sponsor				sponsor;
}
