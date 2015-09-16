package de.andre.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.HashMap;
import java.util.Map;

public enum UState {
	AL("Alabama"), AK("Alaska"), AS("American Samoa"), AZ("Arizona"), AR("Arkansas"),
	CA("California"), CO("Colorado"), CT("Connecticut"), DE("Delaware"), DC("District of Columbia"),
	FM(	"Federated States of Micronesia"), FL("Florida"), GA("Georgia"), GU("Guam"), HAWAII("HI"),
	ID("Idaho"), IL("Illinois"), IN("Indiana"), IA("Iowa"), KS("Kansas");

	private final String name;

	private static final Map<String, UState> STATES_BY_CODE = new HashMap<>();

	static {
		for (final UState state : values()) {
			STATES_BY_CODE.put(state.name(), state);
		}
	}

	UState (final String code) {
		this.name = code;
	}

	public String getName() {
		return name;
	}

	@JsonCreator
	public static UState valueOfCode(final String pCode) {
		return STATES_BY_CODE.get(pCode);
	}
}
