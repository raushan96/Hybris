package de.andre.entity.util;

import de.andre.entity.profile.Interest;
import de.andre.repository.profile.InterestRepository;
import org.springframework.core.convert.converter.Converter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class InterestsConverter implements Converter<String[], Set<Interest>> {
    private final InterestRepository interestRepository;

    public InterestsConverter(final InterestRepository interestRepository) {
        this.interestRepository = interestRepository;
    }

    @Override
    public Set<Interest> convert(final String[] source) {
        if (source == null || source.length == 0) {
            return Collections.emptySet();
        }

        final Set<Integer> validCodes = Arrays.stream(source)
                .map(code -> {
                    try {
                        return Integer.parseInt(code);
                    } catch (NumberFormatException ex) {
                        return -1;
                    }
                })
                .collect(Collectors.toSet());

        final List<Interest> interests = interestRepository.findAll();
        return interests.stream()
                .filter(interest -> validCodes.contains(interest.getCode()))
                .collect(Collectors.toSet());
    }
}
