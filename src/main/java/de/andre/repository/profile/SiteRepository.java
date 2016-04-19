package de.andre.repository.profile;

import de.andre.entity.profile.SiteConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SiteRepository extends JpaRepository<SiteConfiguration, String> {
    @Query("select st.id from SiteConfiguration st where :url member of st.urls")
    String siteIdFromUrl(@Param("url") String url);

    @Query("select st from SiteConfiguration st " +
            "left join fetch st.urls " +
            "left join fetch st.attributes " +
            "where st.id = :id")
    Optional<SiteConfiguration> fetchSite(@Param("id") String id);
}
