/*
 * DomainEntity.java
 *
 * Copyright (c) 2019 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.framework.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;
import javax.persistence.Version;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class DomainEntity implements Serializable {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int					id;

	@Version
	private int					version;


	// Derived atttributes ----------------------------------------------------

	@Transient
	public boolean isTransient() {
		boolean result;

		result = this.id == 0;

		return result;
	}

	// Object interface -------------------------------------------------------

	@Override
	public int hashCode() {
		return this.getId();
	}

	@Override
	public boolean equals(final Object other) {
		boolean result;

		if (this == other) {
			result = true;
		} else if (other == null) {
			result = false;
		} else if (other instanceof Integer) {
			result = this.getId() == (Integer) other;
		} else if (!this.getClass().isInstance(other)) {
			result = false;
		} else {
			result = this.getId() == ((DomainEntity) other).getId();
		}

		return result;
	}

	@Override
	public String toString() {
		StringBuilder result;

		result = new StringBuilder();
		result.append(this.getClass().getName());
		result.append("{");
		result.append("id=");
		result.append(this.getId());
		result.append(", version=");
		result.append(this.getVersion());
		result.append("}");

		return result.toString();
	}

}
