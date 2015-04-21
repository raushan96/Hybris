package de.andre.web.controller.catalog;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by andreika on 4/20/2015.
 */

@Controller
@RequestMapping(value = "/catalog")
public class CatalogController {

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView catalogMenu() {
		ModelAndView mav = new ModelAndView("catalog/catalog");
		return mav;
	}
}
