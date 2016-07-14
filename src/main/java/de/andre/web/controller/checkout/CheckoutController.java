package de.andre.web.controller.checkout;

import de.andre.service.commerce.order.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/checkout")
public class CheckoutController {

    private final PurchaseService purchaseService;

    @Autowired
    public CheckoutController(final PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping(value = "/cart")
    public String viewCart(final Model map) {
        map.addAttribute("order", purchaseService.currentOrder());
        return "checkout/cart";
    }

    @GetMapping(value = "/deleteItem")
    public String deleteItem(@RequestParam("itemId") final Long itemId, final Model map) {
        purchaseService.deleteOrderItem(itemId);
        map.addAttribute("order", purchaseService.currentOrder());
        return "checkout/cart";
    }
}
