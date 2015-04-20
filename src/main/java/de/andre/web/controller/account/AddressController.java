package de.andre.web.controller.account;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.andre.entity.core.DpsAddress;
import de.andre.service.account.AddressCardsTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by andreika on 3/29/2015.
 */

@RestController
public class AddressController {

	private final AddressCardsTools addressCardsTools;
	private final ObjectMapper objectMapper;

	@Autowired
	public AddressController(AddressCardsTools addressCardsTools, ObjectMapper objectMapper) {
		this.addressCardsTools = addressCardsTools;
		this.objectMapper = objectMapper;
	}

	@RequestMapping(value = "/address/getEditAddress", method = RequestMethod.GET)
	public DpsAddress getEditAddress(@RequestParam("addressId") String addressId) {
		DpsAddress dpsAddress = addressCardsTools.getAddressById(addressId);
		return dpsAddress;
	}

	@RequestMapping(value = "/address/deleteAddress", method = RequestMethod.POST)
	public String deleteAddress(@RequestParam("addressId") String addressId) {
		addressCardsTools.deleteAdressById(addressId);
		return addressId;
	}

	@RequestMapping(value = "/address/modifyAddress", method = RequestMethod.POST)
	public String deleteAddress(DpsAddress dpsAddress, @RequestParam(value = "userId") String userId) throws JsonProcessingException {
		Map<String, String> response = new HashMap<>();
		try {
			addressCardsTools.createAddress(dpsAddress, userId);
			response.put("success", "true");
		} catch (Exception e) {
			response.put("success", "false");
		}
		return objectMapper.writeValueAsString(response);
	}
}
