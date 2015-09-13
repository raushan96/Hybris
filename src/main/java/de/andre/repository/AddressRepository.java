package de.andre.repository;

import de.andre.entity.core.DpsAddress;
import de.andre.entity.core.DpsUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AddressRepository extends JpaRepository<DpsAddress, Integer> {

	@Query("select a from DpsAddress a where a.dpsUser = :dpsUser")
	List<DpsAddress> findAddressesByCustomer(@Param("dpsUser") DpsUser dpsUser);
}
