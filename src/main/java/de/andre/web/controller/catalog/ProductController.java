package de.andre.web.controller.catalog;

import de.andre.service.catalog.CatalogTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "catalog/category-{catId}")
public class ProductController {
    private final CatalogTools catalogTools;

    @Autowired
    public ProductController(final CatalogTools catalogTools) {
        this.catalogTools = catalogTools;
    }

    @RequestMapping(value = "/{productLink}")
    public ModelAndView showProductDetailsPage(@PathVariable("productLink") final String productLink) {
        final String prdId = productLink.split("-")[1];
        ModelAndView mav = new ModelAndView("catalog/pdp");
        mav.addObject("product", catalogTools.getProductById(prdId));
        return mav;
    }
}
