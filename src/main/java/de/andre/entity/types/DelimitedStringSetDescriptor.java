package de.andre.entity.types;

import de.andre.utils.HybrisConstants;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;
import org.hibernate.type.descriptor.java.MutableMutabilityPlan;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DelimitedStringSetDescriptor extends AbstractTypeDescriptor<Set> {
    public static final DelimitedStringSetDescriptor INSTANCE = new DelimitedStringSetDescriptor();

    public static final String DELIMITER = ",";

    @SuppressWarnings("unchecked")
    protected DelimitedStringSetDescriptor() {
        super(Set.class, new MutableMutabilityPlan<Set>() {
            @Override
            protected Set deepCopyNotNull(final Set value) {
                return new HashSet(value);
            }
        });
    }

    @SuppressWarnings("unchecked")
    @Override
    public String toString(final Set value) {
        if (value == null || value.isEmpty()) {
            return HybrisConstants.EMPTY_STRING;
        }
        return ((Set<String>) value).stream()
                .collect(Collectors.joining(DELIMITER));
    }

    @Override
    public Set fromString(final String string) {
        if (!StringUtils.hasLength(string)) {
            return Collections.emptySet();
        }
        return Stream.of(string.split(DELIMITER))
                .map(String::trim)
                .collect(Collectors.toSet());
    }

    @SuppressWarnings("unchecked")
    @Override
    public <X> X unwrap(final Set value, final Class<X> type, final WrapperOptions options) {
        return (X) toString(value);
    }

    @Override
    public <X> Set wrap(final X value, final WrapperOptions options) {
        return fromString((String) value);
    }
}
