package de.andre.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.andre.entity.core.DpsUser;
import de.andre.entity.core.enums.Gender;
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

	DpsUser initUser() throws ParseException {
		DpsUser dpsUser = new DpsUser();
		dpsUser.setFirstName("andre");
		dpsUser.setLastName("evans");
		dpsUser.setUserId(123);
		dpsUser.setAcceptEmails(Boolean.FALSE);
		dpsUser.setGender(Gender.MALE);
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

		dpsUser.setDateOfBirth(df.parse("11/03/1994"));
		return dpsUser;
	}

	@Test
	@Ignore
	public void DpsUserTest() throws ParseException {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

		DpsUser dpsUser = new DpsUser();
		dpsUser.setFirstName("andre");
		dpsUser.setLastName("evans");
		dpsUser.setDateOfBirth(df.parse("11/03/1994"));

		Set<ConstraintViolation<DpsUser>> constraintViolations = validator.validate(dpsUser);
		Set<ConstraintViolation<DpsUser>> violation = validator.validateProperty(dpsUser, "email");

		assertEquals(1, constraintViolations.size());
		assertEquals(1, violation.size());
	}

	@Test
	public void JsonTest() throws JsonProcessingException, ParseException, IOException {
		DpsUser dpsUser = initUser();

		String userJson = objectMapper.writeValueAsString(dpsUser);
		Map<String, Object> userMap = objectMapper.readValue(userJson, Map.class);
		DpsUser cloneUser = objectMapper.readValue(userJson, DpsUser.class);
		Map<String, Object> cloneUserGeneric = objectMapper.readValue(userJson, new TypeReference<Map<String, Object>>() { });

		Map<String, Object> userMapCust = new HashMap<>();
		userMapCust.put("firstName", "lox");
		userMapCust.put("lastName", "loxovich");
		userMapCust.put("gender", Gender.MALE);
		String custJson = objectMapper.writeValueAsString(userMapCust);
	}
}
