import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class JPA {
	private static EntityManager em = null;
//https://github.com/jbaysolutions/jpa2tut-many2many
	@BeforeClass
	public static void setUpClass() throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext("dao.xml");
		em = context.getBean(EntityManager.class);
	}

	@Test
	public void testSimpleQuery() {
		Query query = em.createQuery("select count(*) from DpsUser u");
		query.getSingleResult();
	}
}
