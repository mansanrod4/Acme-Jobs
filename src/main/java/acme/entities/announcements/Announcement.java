
package acme.entities.announcements;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.URL;

import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(indexes = {
	@Index(columnList = "moment")
})
public class Announcement extends DomainEntity {

	//Identificador

	private static final long	serialVersionUID	= 1L;

	//Atributos

	@NotBlank
	private String				title;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Past
	private Date				moment;

	@URL
	private String				moreInfo;

	@NotBlank
	@Column(length = 1024)
	private String				text;

	//Atributos Derivados

	//Relaciones

}
