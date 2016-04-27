package de.andre.repository.profile;

import de.andre.entity.profile.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import javax.persistence.QueryHint;
import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
    @Query("select a from Address a where a.profile.id = :profileId")
    List<Address> findAllAddresses(@Param("profileId") Long profileId);

    @Query("select a from Address a where a.profile.id = :profileId and a.addressName = :name")
    Optional<Address> profileSecondaryAddress(@Param("profileId") Long profileId, @Param("name") String addressName);

    @Modifying
    @Query("delete from Address a where a.profile.id = :profileId and a.addressName = :name")
    int deleteProfileAddress(@Param("profileId") Long profileId, @Param("name") String addressName);
}
