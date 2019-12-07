
package acme.entities.investor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(indexes = {
	@Index(columnList = "sector"), @Index(columnList = "star")
})
public class Investor extends DomainEntity {

	//Identificador
	private static final long	serialVersionUID	= 1L;
	//Atributos

	@NotBlank
	private String				name;

	@NotBlank
	private String				sector;

	@NotBlank
	@Column(length = 1024)
	private String				investingStatement;

	@Min(0)
	@Max(5)
	private Integer				star;

}
