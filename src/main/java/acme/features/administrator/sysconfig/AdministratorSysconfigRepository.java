
package acme.features.administrator.sysconfig;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.sysconfig.Sysconfig;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorSysconfigRepository extends AbstractRepository {

	@Query("Select a from Sysconfig a where a.id = ?1")
	Sysconfig findOneById(int id);

	@Query("Select a from Sysconfig a")
	Collection<Sysconfig> findManyAll();

}
