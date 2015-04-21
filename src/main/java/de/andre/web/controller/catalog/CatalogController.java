package de.andre.web.controller.catalog;

import de.andre.repository.CatalogRepository;
import de.andre.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
	@Autowired
	private CatalogRepository catalogRepository;
	@Autowired
	private ProductRepository productRepository;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView catalogMenu() {
		ModelAndView mav = new ModelAndView("catalog/catalog");
		mav.addObject("rootCats", catalogRepository.getRootChildCategories());
		return mav;
	}

	@RequestMapping(value = "/category", method = RequestMethod.GET)
	public ModelAndView showCategory() {
		ModelAndView mav = new ModelAndView("catalog/category");

		return mav;
	}
}
