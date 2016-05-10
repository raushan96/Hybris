package de.andre.utils;

import de.andre.entity.catalog.Category;
import de.andre.entity.catalog.Product;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

public class StreamUtils {
    private StreamUtils() {
    }

    public static Set<String> productsToIds(final Collection<Product> products) {
        if (CollectionUtils.isEmpty(products)) {
            return Collections.emptySet();
        }

        return products
                .stream()
                .map(Product::getId)
                .collect(Collectors.toSet());
    }

    public static Map<String, String> categoriesToSortedIdNames(final Collection<Category> categories) {
        if (CollectionUtils.isEmpty(categories)) {
            return Collections.emptyMap();
        }

        return categories
                .stream()
                .sorted(Comparator.comparing(Category::getDisplayName))
                .collect(Collectors.toMap(
                        Category::getId,
                        Category::getDisplayName,
                        (v1, v2) -> v1,
                        LinkedHashMap::new)
                );
    }
}
