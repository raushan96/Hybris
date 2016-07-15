package de.andre.entity.util;


import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import java.time.Year;

public class YearConverter implements Converter<String, Year> {
    @Override
    public Year convert(final String source) {
        if (!StringUtils.hasLength(source)) {
            return null;
        }
        return Year.parse(source);
    }
}
