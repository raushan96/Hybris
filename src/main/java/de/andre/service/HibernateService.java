package de.andre.service;

import de.andre.entity.core.DpsAddress;
import de.andre.entity.core.DpsUser;
import de.andre.entity.core.enums.Gender;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by andreika on 4/5/2015.
 */

@Service
public class HibernateService {
	private static final Logger logger = LoggerFactory.getLogger(HibernateService.class);

	@Autowired
	HibernateJpaSessionFactoryBean hibernateSession;

	@Autowired
	LocalEntityManagerFactoryBean entityManagerFactoryBean;

	@Transactional
	public void testHibernate() {
		Session session = hibernateSession.getObject().getCurrentSession();
		session.beginTransaction();
		DpsUser lox = new DpsUser();
		lox.setFirstName("lox");
		lox.setPassword("123");
		lox.setGender(Gender.MALE);
		lox.setEmail("andr@gmail.com");
		session.save(lox);
		session.getTransaction().commit();
	}

	@Transactional
	public void getUsers() {
		Session session = hibernateSession.getObject().getCurrentSession();
		session.beginTransaction();
		List<DpsUser> result = session.createQuery("from DpsUser ").list();
		session.getTransaction().commit();
		logger.debug(result.toString(), "type: " + result.getClass());
	}

	@Transactional
	public void loadUser() {
		Session session = hibernateSession.getObject().getCurrentSession();
		session.beginTransaction();
		DpsUser lox = (DpsUser)session.load(DpsUser.class, 1);
		DpsAddress address = (DpsAddress)session.load(DpsAddress.class, 3);
		address.setDpsUser(lox);
		session.getTransaction().commit();
	}

	@Transactional
	public void detached() {
		Session session = hibernateSession.getObject().getCurrentSession();
		session.beginTransaction();
		DpsUser user = (DpsUser) session.createQuery("from DpsUser u join fetch u.dpsAddresses where u.userId = :uId")
				.setParameter("uId", 1)
				.uniqueResult();
		session.getTransaction().commit();
		user.toString();
	}
}
