package de.andre.repository.profile;

import de.andre.repository.RepositoryAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class ProfileAdapterRepository extends RepositoryAdapter {
    private final ProfileRepository profileRepository;
    private final AddressRepository addressRepository;
    private final InterestRepository interestRepository;

    public ProfileAdapterRepository(final ProfileRepository profileRepository,
            final AddressRepository addressRepository, final InterestRepository interestRepository) {
        super(profileRepository, addressRepository, interestRepository);
        this.profileRepository = profileRepository;
        this.addressRepository = addressRepository;
        this.interestRepository = interestRepository;
    }

    public AddressRepository getAddressRepository() {
        return addressRepository;
    }

    public ProfileRepository getProfileRepository() {
        return profileRepository;
    }

    public InterestRepository getInterestRepository() {
        return interestRepository;
    }
}
