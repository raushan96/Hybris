package de.andre.web.controller.catalog;

import de.andre.service.catalog.CatalogTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String catalogMenu(final Model map) {
        map.addAttribute("rootCats", catalogTools.getRootChildCategories());
        return "catalog/catalog";
    }

    @RequestMapping(value = "/{catalogLink}", method = RequestMethod.GET)
    public String showCategory(@PathVariable("catalogLink") final String catalogLink, final Model map) {
        final String catId = catalogLink.split("-")[1];
        catalogTools.populateCategoryMap(catId, map);

//        if((Boolean)mav.getModel().get("error")) {
//            mav.setViewName("index");
//        }
        return "catalog/category";
    }
}
