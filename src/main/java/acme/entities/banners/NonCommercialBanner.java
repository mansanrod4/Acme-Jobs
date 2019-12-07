
package acme.entities.banners;

import javax.persistence.Entity;

import org.hibernate.validator.constraints.URL;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class NonCommercialBanner extends Banner {

	private static final long	serialVersionUID	= 1L;

	@URL
	private String				jingle;
}
