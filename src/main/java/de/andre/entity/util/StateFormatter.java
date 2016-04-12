package de.andre.entity.util;

import de.andre.entity.enums.State;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

public class StateFormatter implements Formatter<State> {

    @Override
    public State parse(final String pCode, final Locale pLocale) throws ParseException {
        final State state = State.valueOfCode(pCode);
        if (state != null) {
            return state;
        } else {
            throw new ParseException("Invalid state code " + pCode, 0);
        }
    }

    @Override
    public String print(final State state, final Locale locale) {
        return state.getName();
    }
}
