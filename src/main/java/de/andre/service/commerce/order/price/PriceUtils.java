package de.andre.service.commerce.order.price;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PriceUtils {
    private PriceUtils() {
    }

    public static <T> List<T> mergeLists(final List<List<T>> lists) {
        if (CollectionUtils.isEmpty(lists)) {
            return Collections.emptyList();
        }

        int size = 0;
        for (final List<T> list : lists) {
            size += list.size();
        }

        final List<T> resultList = new ArrayList<>(size);
        for (final List<T> list : lists) {
            resultList.addAll(list);
        }
        return resultList;
    }
}
