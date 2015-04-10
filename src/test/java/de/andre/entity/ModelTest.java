package de.andre.entity;

import de.andre.entity.core.DpsUser;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Created by Andrei on 4/7/2015.
 */
public class ModelTest {
	private static Validator validator;

	@BeforeClass
	public static void setup() {
		validator = Validation.buildDefaultValidatorFactory().getValidator();
	}

	@Test
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
}
