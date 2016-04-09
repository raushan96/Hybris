package de.andre.repository.profile;

import de.andre.entity.profile.Address;
import de.andre.entity.profile.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Integer> {

//    @Query("select a from Address a where a.profile = :profile")
//    List<Address> findAddressesByCustomer(@Param("Profile") Profile profile);
}
