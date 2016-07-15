package de.andre.entity.util;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import java.time.Month;

public class MonthConverter implements Converter<String, Month> {
    @Override
    public Month convert(final String source) {
        if (!StringUtils.hasLength(source)) {
            return null;
        }
        return Month.of(Integer.parseInt(source));
    }
}
