package de.andre.jpa;

import de.andre.entity.enums.Gender;
import de.andre.entity.profile.Profile;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class JPA {
	private static EntityManager em = null;
//https://github.com/jbaysolutions/jpa2tut-many2many
	@BeforeClass
	public static void setUpClass() throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext("dao.xml");
		em = context.getBean(EntityManager.class);
		em = em.getEntityManagerFactory().createEntityManager();
	}

	@Test
	public void testPersistence() throws ParseException {
		em.getTransaction().begin();

		Profile profile = new Profile();
		profile.setFirstName("andreii");
		profile.setLastName("evansss");
		profile.setPassword("loxsssdfsdfasdfasdf");
		profile.setEmail("loxito@gmail.com");
		profile.setAcceptEmails(Boolean.FALSE);
		profile.setGender(Gender.MALE);
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

//		profile.setDateOfBirth(df.parse("11/03/1994"));

		em.persist(profile);

		em.flush();

//		Address address = new Address();
//		address.setCity("city");
//		address.setPostalCode("12313");
//		address.setCountryCode("US");
//		address.setState(State.AK);
//		address.setAddress("nda");
//		address.setProfile(Profile);
//
//		em.persist(address);

		em.getTransaction().commit();
	}

	@Test
	public void testSimpleQuery() {
		Query query = em.createQuery("select count(*) from Profile u");
		System.out.println("count is: " + query.getSingleResult());
		TypedQuery<Profile> profileQuery = em.createQuery("select u from Profile u where u.profileId = 1", Profile.class);

		em.getTransaction().begin();
		Profile Profile = profileQuery.getSingleResult();
		Profile.setFirstName("loshara");

		em.detach(Profile);
		em.getTransaction().commit();
	}
}
