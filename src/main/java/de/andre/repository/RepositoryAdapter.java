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
    private Map<String, Repository> repositoriesMap = Collections.emptyMap();

    public RepositoryAdapter(final Repository... repositories) {
        if (ArrayUtils.isEmpty(repositories)) {
            logger.warn("Empty repositories list passed, returning");
            return;
        }

        allRepositories = Collections.unmodifiableList(Arrays.asList(repositories));
        repositoriesMap = Collections.unmodifiableMap(
                Arrays.stream(repositories)
                        .collect(Collectors.toMap(
                                repository -> repository.getClass().getName(),
                                Function.identity())
                        )
        );

        logger.debug("{} component startup complete", this.getClass().getSimpleName());
    }

    protected Session getSession() {
        return em.unwrap(Session.class);
    }

    public List<Repository> getAllRepositories() {
        return allRepositories;
    }

    public Map<String, Repository> getRepositoriesMap() {
        return repositoriesMap;
    }
}
