package de.andre.repository;

import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class RepositoryAdapter {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @PersistenceContext
    protected EntityManager em;

    private List<Repository> allRepositories = Collections.emptyList();

    public RepositoryAdapter(final Repository... repositories) {
        if (ArrayUtils.isEmpty(repositories)) {
            logger.warn("Empty repositories list passed, returning");
            return;
        }

        allRepositories = Collections.unmodifiableList(Arrays.asList(repositories));

        logger.debug("{} component startup complete", this.getClass().getSimpleName());
    }

    public Session getSession() {
        return em.unwrap(Session.class);
    }

    public EntityManager getEntityManager() {
        return this.em;
    }

    public List<Repository> getAllRepositories() {
        return allRepositories;
    }
}
