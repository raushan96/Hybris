package de.andre.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.andre.entity.enums.Gender;
import de.andre.entity.profile.Profile;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Created by Andrei on 4/7/2015.
 */
public class ModelTest {
    private static Validator validator;
    private final static ObjectMapper objectMapper = new ObjectMapper();

    @BeforeClass
    public static void setup() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    Profile initProfile() throws ParseException {
        Profile profile = new Profile();
        profile.setFirstName("andre");
        profile.setLastName("evans");
        profile.setId(123L);
        profile.setAcceptEmails(Boolean.FALSE);
        profile.setGender(Gender.MALE);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

//        Profile.setDateOfBirth(df.parse("11/03/1994"));
        return profile;
    }

    @Test
    @Ignore
    public void ProfileTest() throws ParseException {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

        Profile profile = new Profile();
        profile.setFirstName("andre");
        profile.setLastName("evans");
//        Profile.setDateOfBirth(df.parse("11/03/1994"));

        Set<ConstraintViolation<Profile>> constraintViolations = validator.validate(profile);
        Set<ConstraintViolation<Profile>> violation = validator.validateProperty(profile, "email");

        assertEquals(1, constraintViolations.size());
        assertEquals(1, violation.size());
    }

    @Test
    public void JsonTest() throws JsonProcessingException, ParseException, IOException {
        Profile profile = initProfile();

        String profileJson = objectMapper.writeValueAsString(profile);
        Map<String, Object> ProfileMap = objectMapper.readValue(profileJson, Map.class);
        Profile cloneProfile = objectMapper.readValue(profileJson, Profile.class);
        Map<String, Object> cloneProfileGeneric = objectMapper.readValue(profileJson, new TypeReference<Map<String, Object>>() {
        });

        Map<String, Object> ProfileMapCust = new HashMap<>();
        ProfileMapCust.put("firstName", "lox");
        ProfileMapCust.put("lastName", "loxovich");
        ProfileMapCust.put("gender", Gender.MALE);
        String custJson = objectMapper.writeValueAsString(ProfileMapCust);
    }
}
