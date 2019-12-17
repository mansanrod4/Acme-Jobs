
package acme.entities.rolRequests;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import acme.framework.entities.DomainEntity;
import acme.framework.entities.UserAccount;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(indexes = {
	@Index(columnList = "approved")
})
public class AuditorRolRequest extends DomainEntity {

	private static final long	serialVersionUID	= 1L;

	private boolean				approved;

	@NotNull
	@Valid
	@OneToOne(optional = false)
	private UserAccount			user;

	//Atributos info derivados
}
