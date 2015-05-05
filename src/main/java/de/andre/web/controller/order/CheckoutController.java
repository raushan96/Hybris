package de.andre.web.controller.order;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by andreika on 5/4/2015.
 */

@Controller
public class CheckoutController {
	@RequestMapping(value = "/cart")
	public ModelAndView showCart() {
		ModelAndView mav = new ModelAndView("checkout/cart");
		return mav;
	}
}
