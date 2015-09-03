package de.andre.springTests;

import junit.framework.TestCase;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by andreika on 9/1/2015.
 */
public class TerminatorQuoterTest extends TestCase {

	public void testSayQuote() throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("services.xml");
		context.getBean(TerminatorQuoter.class).sayQuote();


	}
}