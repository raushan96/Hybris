package de.andre;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * Created by andreika on 8/30/2015.
 */
public class Spring {

	public static void main(String[] args) {
		ApplicationContext context = new FileSystemXmlApplicationContext(new String[] {
				"file:c:\\Users\\andreika\\IdeaProjects\\Hybris\\src\\main\\java\\de\\andre\\springTests\\services.xml",
				"file:c:\\Users\\andreika\\IdeaProjects\\Hybris\\src\\main\\java\\de\\andre\\springTests\\daos.xml"});
		Andre andre = context.getBean("haha", Andre.class);
		System.out.println(andre.getName());
	}

}
