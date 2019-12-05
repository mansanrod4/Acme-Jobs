/*
 * InstantiateMethod.java
 *
 * Copyright (c) 2019 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes.  The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.framework.services;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import acme.framework.components.Request;
import acme.framework.entities.UserRole;

@Transactional(TxType.MANDATORY)
public interface InstantiateMethod<R extends UserRole, E> {

	E instantiate(final Request<E> request);

}
