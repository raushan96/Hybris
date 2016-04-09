package de.andre;

import de.andre.entity.profile.Profile;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.PropertyValue;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created by andreika on 8/6/2015.
 */
public class Tests {
	public static void main(String[] args) {
		for (String str : getList()) {
			System.out.println(str);
		}

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 10);
		Profile person = new Profile();
		BeanWrapper beanWrapper = new BeanWrapperImpl(person);
		beanWrapper.setPropertyValue("email", "lox");
		PropertyValue lastName = new PropertyValue("lastName", "pidr");
		beanWrapper.setPropertyValue(lastName);
		System.out.println(person.getEmail());
		System.out.println(person.getLastName());
		System.out.println(beanWrapper.getPropertyValue("email"));
	}

	public static final List<String> list = Arrays.asList("1", "2", "3");

	public static List<String> getList() {
		System.out.println("ga");
		return list;
	}
}
