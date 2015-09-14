package de.andre.web.controller.account;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.andre.entity.core.DpsAddress;
import de.andre.service.account.AddressTools;
import de.andre.utils.validation.DpsAddressValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/address")
public class AddressController {
	private final AddressTools addressTools;
	private final ObjectMapper objectMapper;
	private final DpsAddressValidator dpsAddressValidator;

	@Autowired
	public AddressController(final AddressTools addressCardsTools, final ObjectMapper objectMapper,
							 final DpsAddressValidator dpsAddressValidator) {
		this.addressTools = addressCardsTools;
		this.objectMapper = objectMapper;
		this.dpsAddressValidator = dpsAddressValidator;
	}

	@RequestMapping(value = "/getEditAddress", method = RequestMethod.GET)
	public DpsAddress getEditAddress(@RequestParam("addressId") final String addressId) {
		final DpsAddress dpsAddress = addressTools.getAddressById(addressId);
		return dpsAddress;
	}

	@RequestMapping(value = "/deleteAddress", method = RequestMethod.POST)
	public ObjectNode deleteAddress(@RequestParam("addressId") final String addressId) {
		try {
			addressTools.deleteAdressById(addressId);
			final ObjectNode response = objectMapper.createObjectNode();
			response.put("success", true);
			response.put("deletedId", addressId);

			return response;
		} catch (Exception e) {
			return objectMapper.createObjectNode().put("success", false).put("error", e.toString());
		}

	}

	@RequestMapping(value = "/modifyAddress", method = RequestMethod.POST)
	public ObjectNode modifyAddress(final DpsAddress dpsAddress, final BindingResult result) throws JsonProcessingException {
		try {
			dpsAddressValidator.validate(dpsAddress, result);

			if (!result.hasErrors()) {
				final Integer oldAddressId = dpsAddress.getAddressId();
				final Integer newAddressId = addressTools.createAddress(dpsAddress);
				return objectMapper.createObjectNode().put("success", true).put("newAddressId", newAddressId).put("isNew", !newAddressId.equals(oldAddressId));
			} else {
				return objectMapper.createObjectNode().put("success", false).put("error", "invalid");
			}

		} catch (Exception e) {
			return objectMapper.createObjectNode().put("success", false).put("error", e.toString());
		}
	}
}
