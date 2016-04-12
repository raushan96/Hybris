package de.andre.repository.profile;

import de.andre.entity.profile.Address;
import de.andre.entity.profile.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
    @Query("select a from Address a where a.profile = :profile")
    List<Address> findAllAddresses(@Param("profile") Profile profile);

    @Query("select a from Address a where a.profile = :profile and a.addressName = :name")
    Optional<Address> profileSecondaryAddress(@Param("profile") Profile profile, @Param("name") String addressName);

    @Modifying
    @Query("delete from Address a where a.profile = :profile and a.addressName = :name")
    int deleteProfileAddress(@Param("profile") Profile profile, @Param("name") String addressName);
}
