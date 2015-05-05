package de.andre.web.controller.order;

import de.andre.repository.AddressRepository;
import de.andre.service.account.AccountTools;
import de.andre.web.beans.account.ProfileHolder;
import de.andre.web.beans.order.OrderHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by andreika on 5/4/2015.
 */

@Controller
public class CheckoutController {
	private final AccountTools accountTools;

	@Autowired
	private OrderHolder orderHolder;

	@Autowired
	private ProfileHolder profileHolder;

	@Autowired
	public CheckoutController (final AccountTools accountTools) {
		this.accountTools = accountTools;
	}

	@RequestMapping(value = "/cart")
	public ModelAndView showCart() {
		final ModelAndView mav = new ModelAndView("checkout/cart");
		mav.addObject("cIs", orderHolder.getOrder().getCommerceItems());
		return mav;
	}

	@RequestMapping(value = "/checkout/shipping")
	public ModelAndView shippingStep() {
		final ModelAndView mav = new ModelAndView("checkout/shipping");
		mav.addObject("addresses", accountTools.findAddressesByUser(profileHolder.getProfile()));
		return mav;
	}

	@RequestMapping(value = "/checkout/selectAddress_{addressId}")
	public String shippingToPaymentStep(@PathVariable("addressId") final String addressId) {
		return "redirect:/checkout/payment";
	}

	@RequestMapping(value = "/checkout/payment")
	public ModelAndView paymentgStep() {
		final ModelAndView mav = new ModelAndView("checkout/payment");
		mav.addObject("cIs", orderHolder.getOrder().getPaymentGroups());
		return mav;
	}

	@RequestMapping(value = "/checkout/success")
	public ModelAndView successStep() {
		final ModelAndView mav = new ModelAndView("checkout/success");
		return mav;
	}
}
