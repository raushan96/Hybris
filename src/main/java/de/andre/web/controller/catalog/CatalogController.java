package de.andre.web.controller.catalog;

import de.andre.entity.catalog.DcsProduct;
import de.andre.repository.CatalogRepository;
import de.andre.repository.ProductRepository;
import de.andre.service.catalog.CatalogTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by andreika on 4/20/2015.
 */

@Controller
@RequestMapping(value = "/catalog")
public class CatalogController {
	private final CatalogTools catalogTools;

	@Autowired
	public CatalogController(CatalogTools catalogTools) {
		this.catalogTools = catalogTools;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView catalogMenu() {
		ModelAndView mav = new ModelAndView("catalog/catalog");
		mav.addObject("rootCats", catalogTools.getRootChildCategories());
		return mav;
	}

	@RequestMapping(value = "/{catalogLink}", method = RequestMethod.GET)
	public ModelAndView showCategory(@PathVariable("catalogLink") String catalogLink) {
		ModelAndView mav = new ModelAndView("catalog/category");
		String categoryId = catalogLink.split("-")[1];
		List<DcsProduct> dcsProducts = catalogTools.getCatalogProducts(categoryId);
		return mav;
	}
}
