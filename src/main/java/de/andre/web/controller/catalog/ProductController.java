package de.andre.web.controller.catalog;

import de.andre.service.catalog.CatalogTools;
import de.andre.service.price.PriceTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "catalog/category-{catId}")
public class ProductController {
    private final CatalogTools catalogTools;
    private final PriceTools priceTools;

    @Autowired
    public ProductController(final CatalogTools catalogTools, final PriceTools priceTools) {
        this.catalogTools = catalogTools;
        this.priceTools = priceTools;
    }

    @RequestMapping(value = "/{productLink}")
    public String showProductDetailsPage(
            @PathVariable("productLink") final String productLink,
            final Model map) {
        final String prdId = productLink.split("-")[1];
        map.addAttribute("product", catalogTools.getProductById(prdId));
        map.addAttribute("prdPrice", priceTools.priceProduct(prdId));
        return "catalog/pdp";
    }

    @RequestMapping(value = "/processItem", method = RequestMethod.POST)
    public String showProductDetailsPage(
            @PathVariable("productId") final String productId,
            @PathVariable("quantity") final Long quantity) {
        return "catalog/pdp";
    }
}
