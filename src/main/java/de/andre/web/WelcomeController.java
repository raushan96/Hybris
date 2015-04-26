package de.andre.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class WelcomeController {

	private static final Logger logger = LoggerFactory.getLogger(WelcomeController.class);
	private final String template = "Ga, %s!";
	private final AtomicLong counter = new AtomicLong();

	@RequestMapping(value = {"/index", "/"}, method = RequestMethod.GET)
	public ModelAndView welcome(@RequestParam(value = "name", defaultValue = "world") final String name) {
		logger.debug("welcome() - name {}", name);
		final ModelAndView model = new ModelAndView();
		model.setViewName("index");
		model.addObject("name", String.format(template, name) + "nda");

		final Calendar calInstance = Calendar.getInstance();
		model.addObject("date", new SimpleDateFormat().format(calInstance.getTime()));
		model.addObject("name", "lox");

		return model;

	}

	@RequestMapping(value = "/secure", method = RequestMethod.GET)
	public ModelAndView secureTest() {
		final ModelAndView model = new ModelAndView();
		model.setViewName("secure");

		return model;

	}

}
