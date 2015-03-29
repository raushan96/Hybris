package de.andre;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class WelcomeController {

	private static final Logger logger = LoggerFactory.getLogger(WelcomeController.class);
	private final String template = "Ga, %s!";
	private final AtomicLong counter = new AtomicLong();

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView welcome(@RequestParam(value="name", defaultValue = "world") String name) {

		logger.debug("welcome() - name {}", name);

		ModelAndView model = new ModelAndView();
		model.setViewName("index");
		model.addObject("name", String.format(template, name) + "nda");

		Calendar calInstance = Calendar.getInstance();
		model.addObject("date", new SimpleDateFormat().format(calInstance.getTime()));
		model.addObject("name", "lox");

		return model;

	}

	@RequestMapping(value = "/secure", method = RequestMethod.GET)
	public ModelAndView secureTest() {
		ModelAndView model = new ModelAndView();
		model.setViewName("secure");

		return model;

	}

}
