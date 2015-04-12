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
}
