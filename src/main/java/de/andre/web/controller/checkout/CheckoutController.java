package de.andre.web.controller.checkout;

import de.andre.entity.dto.PaymentForm;
import de.andre.service.account.ProfileTools;
import de.andre.service.commerce.order.PurchaseService;
import de.andre.utils.ProfileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static de.andre.utils.HybrisUtils.logParams;

@Controller
@RequestMapping(value = "/checkout")
public class CheckoutController {
    public static final Logger logger = LoggerFactory.getLogger(CheckoutController.class);

    private final PurchaseService purchaseService;
    private final ProfileTools profileTools;

    @Autowired
    public CheckoutController(final PurchaseService purchaseService, final ProfileTools profileTools) {
        this.purchaseService = purchaseService;
        this.profileTools = profileTools;
    }

    @GetMapping(value = "/cart")
    public String viewCart(final Model map) {
        map.addAttribute("order", purchaseService.currentOrder());
        return "checkout/cart";
    }

    @GetMapping(value = "/deleteItem")
    public String deleteItem(@RequestParam("itemId") final Long itemId, final Model map) {
        logParams(logger, itemId);

        purchaseService.deleteOrderItem(itemId);
        map.addAttribute("order", purchaseService.currentOrder());
        return "checkout/cart";
    }

    @GetMapping(value = "/shipping")
    public String viewShipping(final Model map) {
        map.addAttribute("order", purchaseService.currentOrder());
        map.addAttribute("addresses", profileTools.addressesByProfile(ProfileUtils.currentId()));
        return "checkout/shipping";
    }

    @PostMapping
    public String selectShipping(@RequestParam("addressId") final Long addressId) {
        logParams(logger, addressId);

        purchaseService.selectAddress(addressId);
        return "redirect:payment";
    }

    @GetMapping(value = "/payment")
    public String viewPayment(final Model map) {
        map.addAttribute("paymentForm", new PaymentForm());
        map.addAttribute("order", purchaseService.currentOrder());
        return "checkout/payment";
    }

    @PostMapping(value = "/payment")
    public String proceedOrder(
            @Validated final PaymentForm paymentForm, final BindingResult result, final Model map) {
        logParams(logger, paymentForm);
        if (result.hasErrors()) {
            map.addAttribute("paymentForm", paymentForm);
            map.addAttribute("order", purchaseService.currentOrder());
            return "checkout/payment";
        }
        purchaseService.proceedOrder(paymentForm);
        return "redirect:/";
    }
}
