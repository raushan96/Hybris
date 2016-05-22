package de.andre.web.controller.catalog;

import de.andre.entity.catalog.Category;
import de.andre.service.catalog.CatalogTools;
import de.andre.utils.HybrisConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Set;

import static de.andre.utils.HybrisConstants.HYPHEN;

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
        final Set<Category> rootCats = catalogTools.getRootChildCategories();
        map.addAttribute("rootCats", rootCats.toArray(new Category[rootCats.size()]));
        return "catalog/catalog";
    }

    @RequestMapping(value = "/{catalogLink}", method = RequestMethod.GET)
    public String showCategory(@PathVariable("catalogLink") final String catalogLink, final Model map) {
        final String catId = catalogLink.contains(HYPHEN) ?
                catalogLink.split(HYPHEN)[1] :
                catalogLink;
        map.addAllAttributes(catalogTools.assembleCategoryContent(catId));

        return "catalog/category";
    }
}
