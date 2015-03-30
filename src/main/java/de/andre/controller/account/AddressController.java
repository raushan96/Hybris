package de.andre.controller.account;

import de.andre.entity.core.DpsAddress;
import de.andre.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by andreika on 3/29/2015.
 */

@Controller
public class AddressController {

	private final AddressRepository addressRepository;

	@Autowired
	public AddressController(AddressRepository addressRepository) {
		this.addressRepository = addressRepository;
	}

	@RequestMapping(value = "/account/newAddress", method = RequestMethod.GET)
	public String initAddressForm(Model model) {
		model.addAttribute(new DpsAddress());
		return "";
	}
}
