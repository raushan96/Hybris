package de.andre.repository;

import de.andre.entity.core.DpsUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by andreika on 3/15/2015.
 */

public interface UserRepository extends JpaRepository<DpsUser, Integer> {

	@Query("select u from DpsUser u where u.email = :email")
	DpsUser findByLogin(@Param("email") String email);

	@Modifying
	@Transactional
	@Query("update DpsUser u set u.firstName = :firstName, u.lastName = :lastName, u.email = :email, u.dateOfBirth = :dob")
	void editUserUpdate(@Param("firstName") String firstName, @Param("lastName") String lastName, @Param("email") String email,
						@Param("dob") Date dob);
}
