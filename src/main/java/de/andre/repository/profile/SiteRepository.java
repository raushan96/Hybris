package de.andre.repository.profile;

import de.andre.entity.site.SiteConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SiteRepository extends JpaRepository<SiteConfiguration, String> {
    @Query("select st.id from SiteConfiguration st where :url member of st.urls and st.enabled = true")
    String siteIdFromUrl(@Param("url") String url);

    @Query("select st from SiteConfiguration st " +
            "left join fetch st.urls " +
            "left join fetch st.attributes " +
            "where st.id = :id and st.enabled = true")
    SiteConfiguration fetchSite(@Param("id") String id);

    @Query("select st from SiteConfiguration st " +
            "fetch all properties left join fetch st.urls " +
            "fetch all properties left join fetch st.attributes " +
            "where st.enabled = true")
    List<SiteConfiguration> fetchSites();
}
