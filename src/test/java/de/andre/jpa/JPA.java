package de.andre.jpa;

import de.andre.entity.core.DpsUser;
import de.andre.entity.enums.Gender;
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

		DpsUser dpsUser = new DpsUser();
		dpsUser.setFirstName("andreii");
		dpsUser.setLastName("evansss");
		dpsUser.setPassword("loxsssdfsdfasdfasdf");
		dpsUser.setEmail("loxito@gmail.com");
		dpsUser.setAcceptEmails(Boolean.FALSE);
		dpsUser.setGender(Gender.MALE);
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

		dpsUser.setDateOfBirth(df.parse("11/03/1994"));

		em.persist(dpsUser);

		em.flush();

//		DpsAddress address = new DpsAddress();
//		address.setCity("city");
//		address.setPostalCode("12313");
//		address.setCountryCode("US");
//		address.setState(UState.AK);
//		address.setAddress("nda");
//		address.setDpsUser(dpsUser);
//
//		em.persist(address);

		em.getTransaction().commit();
	}

	@Test
	public void testSimpleQuery() {
		Query query = em.createQuery("select count(*) from DpsUser u");
		System.out.println("count is: " + query.getSingleResult());
		TypedQuery<DpsUser> userQuery = em.createQuery("select u from DpsUser u where u.userId = 1", DpsUser.class);

		em.getTransaction().begin();
		DpsUser user = userQuery.getSingleResult();
		user.setFirstName("loshara");

		em.detach(user);
		em.getTransaction().commit();
	}
}
