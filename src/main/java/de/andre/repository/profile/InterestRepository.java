package de.andre.repository.profile;

import de.andre.entity.profile.Interest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Set;

public interface InterestRepository extends JpaRepository<Interest, Long> {
    @Query("select inter from Interest inter where inter.code in :codes")
    Set<Interest> interestsByCodes(@Param("codes") Collection<Integer> codes);
}
