package de.andre.web.controller.catalog;

import de.andre.service.catalog.CatalogTools;
import de.andre.service.commerce.order.PurchaseService;
import de.andre.service.price.PriceTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/product")
public class ProductController {
    private final CatalogTools catalogTools;
    private final PriceTools priceTools;
    private final PurchaseService purchaseService;

    @Autowired
    public ProductController(final CatalogTools catalogTools, final PriceTools priceTools, final PurchaseService purchaseService) {
        this.catalogTools = catalogTools;
        this.priceTools = priceTools;
        this.purchaseService = purchaseService;
    }

    @RequestMapping(value = "/pId-{productId}")
    public String showProductDetailsPage(
            @PathVariable("productId") final String productId,
            final Model map) {
        map.addAttribute("product", catalogTools.getProductById(productId));
        map.addAttribute("prdPrice", priceTools.priceProduct(productId));
        return "catalog/pdp";
    }

    @RequestMapping(value = "/processItem", method = RequestMethod.POST)
    public String processItem(
            @RequestParam("productId") final String productId,
            @RequestParam("quantity") final Long quantity) {
        purchaseService.modifyOrderItem(productId, quantity);
        return "redirect:/";
    }

    @RequestMapping(value = "/processItemAsync", method = RequestMethod.POST)
    public ResponseEntity<Void> processItemAsync(
            @RequestParam("productId") final String productId,
            @RequestParam("quantity") final Long quantity) {
        purchaseService.modifyOrderItem(productId, quantity);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
