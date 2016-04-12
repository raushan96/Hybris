package de.andre.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.HashMap;
import java.util.Map;

public enum State {
    MINSK("Minsk"), GR("Grodno"), GO("Gomel"), VI("Vitebsk"), MO("Mogilev"), BR("Brest");

    public static final State[] STATES = State.values();

    private static final Map<String, State> STATES_BY_CODE = new HashMap<>();

    static {
        for (final State state : values()) {
            STATES_BY_CODE.put(state.name(), state);
        }
    }

    private final String name;

    State(final String code) {
        this.name = code;
    }

    public String getName() {
        return name;
    }

    @JsonCreator
    public static State valueOfCode(final String pCode) {
        return STATES_BY_CODE.get(pCode);
    }
}
