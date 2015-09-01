package de.andre.repository;

import de.andre.entity.core.DpsCreditCard;
import de.andre.entity.core.DpsUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by andreika on 3/15/2015.
 */

public interface UserRepository extends JpaRepository<DpsUser, Integer> {

	@Query("select u from DpsUser u where u.email = :email")
	DpsUser findByLogin(@Param("email") String email);

	@Modifying
	@Transactional
	@Query(value = "update dps_user set password = :newPassword where email = :email", nativeQuery = true)
	void updateUserPassword(@Param("email") String email, @Param("newPassword") String newPassword);

	@Query("select c from DpsCreditCard c where c.dpsUser = :dpsUser")
	DpsCreditCard findCardByUser(@Param("dpsUser") DpsUser dpsUser);
}
