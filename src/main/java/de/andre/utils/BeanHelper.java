package de.andre.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.beans.PropertyDescriptor;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class BeanHelper {
    private static final Logger logger = LoggerFactory.getLogger(BeanHelper.class);

    public static void copyBeanProperties(final Object source, final Object target, final Collection<String> includes) {
        if (source == null || target == null) {
            logger.debug("Cannot copy with null source or target");
            return;
        }

        if (includes == null || includes.isEmpty()) {
            logger.debug("Includes properties are empty");
            return;
        }
        final Set<String> excludes = new HashSet<>();

        final PropertyDescriptor[] propertyDescriptors = BeanUtils.getPropertyDescriptors(source.getClass());
        for (final PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            final String name = propertyDescriptor.getName();
            if (!includes.contains(name)) {
                excludes.add(name);
            }
        }

        BeanUtils.copyProperties(source, target, excludes.toArray(new String[excludes.size()]));
    }
}
