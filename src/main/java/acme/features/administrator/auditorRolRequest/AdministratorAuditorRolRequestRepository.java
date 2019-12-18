/*
 * AuthenticatedAuditorRepository.java
 *
 * Copyright (c) 2019 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.administrator.auditorRolRequest;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.rolRequests.AuditorRolRequest;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorAuditorRolRequestRepository extends AbstractRepository {

	@Query("select arr from AuditorRolRequest arr where arr.approved = false")
	Collection<AuditorRolRequest> findAllAuditorRolRequestNotApproved();

	@Query("select arr from AuditorRolRequest arr where arr.id = ?1")
	AuditorRolRequest findOneAuditorRolRequestById(int id);
}
