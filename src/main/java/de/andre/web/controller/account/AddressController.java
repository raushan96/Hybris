package de.andre.web.controller.account;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.andre.entity.profile.Address;
import de.andre.service.account.AddressTools;
import de.andre.utils.validation.AddressValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/address")
public class AddressController {
    private final AddressTools addressTools;
    private final ObjectMapper objectMapper;
    private final AddressValidator addressValidator;

    @Autowired
    public AddressController(final AddressTools addressCardsTools, final ObjectMapper objectMapper,
                             final AddressValidator addressValidator) {
        this.addressTools = addressCardsTools;
        this.objectMapper = objectMapper;
        this.addressValidator = addressValidator;
    }

    @InitBinder
    private void initAddressBinder(final WebDataBinder dataBinder) {
        dataBinder.setValidator(addressValidator);
    }

    @RequestMapping(value = "/getEditAddress", method = RequestMethod.GET)
    public Address getEditAddress(@RequestParam("addressId") final String addressId) {
        final Address Address = addressTools.getAddressById(addressId);
        return Address;
    }

    @RequestMapping(value = "/deleteAddress", method = RequestMethod.POST)
    public ObjectNode deleteAddress(@RequestParam("addressId") final String addressId) {
        try {
            addressTools.deleteAdressById(addressId);
            return objectMapper.createObjectNode()
                    .put("success", true)
                    .put("deletedId", addressId);
        } catch (Exception e) {
            return objectMapper.createObjectNode()
                    .put("success", false)
                    .put("error", e.toString());
        }

    }

    @RequestMapping(value = "/modifyAddress", method = RequestMethod.POST)
    public ObjectNode modifyAddress(@Validated @RequestBody final Address address, final Errors errors) throws JsonProcessingException {
        try {
            addressValidator.validate(address, errors);

            if (!errors.hasErrors()) {
                boolean isNew = address.getId() == null;

                final Long newAddressId = addressTools.createAddress(address);
                return objectMapper.createObjectNode()
                        .put("success", true)
                        .put("newAddressId", newAddressId)
                        .put("isNew", isNew);
//                  todo      .put("state", address.getState().getName());
            } else {
                return objectMapper.createObjectNode()
                        .put("success", false)
                        .put("error", "invalid");
            }

        } catch (Exception e) {
            return objectMapper.createObjectNode()
                    .put("success", false)
                    .put("error", e.toString());
        }
    }
}
