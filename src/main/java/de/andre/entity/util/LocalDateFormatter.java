package de.andre.entity.util;

import org.springframework.format.Formatter;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LocalDateFormatter implements Formatter<LocalDate> {
    private final DateTimeFormatter formatter;

    public LocalDateFormatter() {
        formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    }

    public LocalDateFormatter(final String format) {
        formatter = DateTimeFormatter.ofPattern(format);
    }

    @Override
    public LocalDate parse(final String text, final Locale locale) throws ParseException {
        if (!StringUtils.hasText(text)) {
            return null;
        }

        return LocalDate.parse(text, formatter);
    }

    @Override
    public String print(final LocalDate object, final Locale locale) {
        return object != null ? formatter.format(object) : null;
    }
}
