package de.andre.web.controller.order;

import de.andre.service.commerce.CommerceItemTools;
import de.andre.web.beans.order.OrderHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by andreika on 5/3/2015.
 */

@RestController
public class CheckoutRestController {
	private final static Logger logger = LoggerFactory.getLogger(CheckoutRestController.class);

	private final CommerceItemTools commerceItemTools;

	@Autowired
	private OrderHolder orderHolder;

	@Autowired
	public CheckoutRestController(final CommerceItemTools commerceItemTools) {
		this.commerceItemTools = commerceItemTools;
	}

	@RequestMapping(value = "catalog/category-{catId}/pId-{pId}")
	public void addProductToCart(@PathVariable("pId") final String pId) {
		commerceItemTools.addItemToOrder(Integer.valueOf(pId), orderHolder.getOrder(), 1);
	}
}
