package de.andre.entity.enums;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

public class StateFormatter implements Formatter<UState> {

	@Override
	public UState parse(final String pCode, final Locale pLocale) throws ParseException {
		final UState state = UState.valueOfCode(pCode);
		if (state != null) {
			return state;
		} else {
			throw new ParseException("Invalid state code " + pCode, 0);
		}
	}

	@Override
	public String print(final UState state, final Locale locale) {
		return state.getName();
	}
}
