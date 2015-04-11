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


}
