package de.andre.repository.profile;

import de.andre.entity.profile.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    @Query("select pr from Profile pr where pr.email = :email")
    Optional<Profile> findByLogin(@Param("email") String email);

    @Query("select pr from Profile pr join fetch pr.addresses where pr.id = :profileId")
    Optional<Profile> profileWithAddresses(@Param("profileId") Long profileId);

    @Modifying
    @Transactional
    @Query(value = "update hp_profile set password = :newPassword where email = :email", nativeQuery = true)
    void updateProfilePassword(@Param("email") String email, @Param("newPassword") String newPassword);

    @Query("select count(*) from Profile u where u.email = :email")
    int countByEmail(@Param("email") String pEmail);

    @Query("select case when count(pr) > 0 then true else false end from Profile pr " +
            "where pr.email = :email")
    boolean emailExists(@Param("email") String email);
}
