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
    @Query("select a from Address a where a.profile.id = :profileId")
    List<Address> findAllAddresses(@Param("profileId") Long profileId);

    @Query("select a from Address a where a.profile.id = :profileId and a.addressName = :name")
    Optional<Address> profileSecondaryAddress(@Param("profileId") Long profileId, @Param("name") String addressName);

    @Modifying
    @Query("delete from Address a where a.profile.id = :profileId and a.addressName = :name")
    int deleteProfileAddress(@Param("profileId") Long profileId, @Param("name") String addressName);
}
