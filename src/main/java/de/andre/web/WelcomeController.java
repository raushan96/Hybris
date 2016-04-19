package de.andre.web;

import de.andre.entity.enums.Gender;
import de.andre.entity.profile.Profile;
import de.andre.repository.profile.ProfileRepository;
import de.andre.repository.profile.SiteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class WelcomeController {

    private static final Logger log = LoggerFactory.getLogger(WelcomeController.class);
    private final String template = "Ga, %s!";
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private SiteRepository siteRepository;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    ServletContext servletContext;

    @Autowired
    private ApplicationContext appContext;

    public MessageSource getMessageSource() {
        return messageSource;
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @RequestMapping(value = {"/index", "/"}, method = RequestMethod.GET)
    public ModelAndView welcome(@RequestParam(value = "name", defaultValue = "world") final String name) {
        log.debug("welcome() - name {}", name);
        final ModelAndView model = new ModelAndView();
        model.setViewName("index");
        model.addObject("name", String.format(template, name) + "nda");

        final Calendar calInstance = Calendar.getInstance();
        model.addObject("date", calInstance.getTime());
        model.addObject("name", "lox");
        final Profile profile = new Profile();
        profile.setEmail("lox@gmail.com");
        profile.setDateOfBirth(LocalDate.now());
        profile.setAcceptEmails(true);
        profile.setGender(Gender.MALE);
        model.addObject("profile", profile);

        return model;

    }

    @Transactional
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public void testHib() {
        profileRepository.findOne(1L);
    }

    @RequestMapping(value = "/secure", method = RequestMethod.GET)
    public ModelAndView secureTest() {
        final ModelAndView model = new ModelAndView();
        model.setViewName("secure");

        return model;

    }

}
