package de.andre;

import de.andre.entity.core.DpsUser;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.PropertyValue;

import java.util.Calendar;

/**
 * Created by andreika on 8/6/2015.
 */
public class Tests {
	public static void main(String[] args) {

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 10);
		DpsUser person = new DpsUser();
		BeanWrapper beanWrapper = new BeanWrapperImpl(person);
		beanWrapper.setPropertyValue("email", "lox");
		PropertyValue lastName = new PropertyValue("lastName", "pidr");
		beanWrapper.setPropertyValue(lastName);
		System.out.println(person.getEmail());
		System.out.println(person.getLastName());
		System.out.println(beanWrapper.getPropertyValue("email"));
	}
}
