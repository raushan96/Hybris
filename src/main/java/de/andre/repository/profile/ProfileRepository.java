package de.andre.repository.profile;

import de.andre.entity.profile.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {

    @Query("select u from Profile u where u.email = :email")
    Profile findByLogin(@Param("email") String email);

    @Modifying
    @Transactional
    @Query(value = "update hp_profile set password = :newPassword where email = :email", nativeQuery = true)
    void updateProfilePassword(@Param("email") String email, @Param("newPassword") String newPassword);

    @Query("select count(*) from Profile u where u.email = :email")
    int countByEmail(@Param("email") String pEmail);
}
