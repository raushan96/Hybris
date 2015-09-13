package de.andre.web.controller.catalog;

import de.andre.service.catalog.CatalogTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/catalog")
public class CatalogController {
	private final CatalogTools catalogTools;

	@Autowired
	public CatalogController(final CatalogTools catalogTools) {
		this.catalogTools = catalogTools;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView catalogMenu() {
		final ModelAndView mav = new ModelAndView("catalog/catalog");
		mav.addObject("rootCats", catalogTools.getRootChildCategories());
		return mav;
	}

	@RequestMapping(value = "/{catalogLink}", method = RequestMethod.GET)
	public ModelAndView showCategory(@PathVariable("catalogLink") final String catalogLink) {
		final ModelAndView mav = new ModelAndView("catalog/category");
		final String catId = catalogLink.split("-")[1];
		catalogTools.populateCategoryMap(catId, mav);

		if((Boolean)mav.getModel().get("error")) {
			mav.setViewName("index");
		}
		return mav;
	}
}
