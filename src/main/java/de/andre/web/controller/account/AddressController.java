package de.andre.web.controller.account;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.andre.entity.dto.View;
import de.andre.entity.profile.Address;
import de.andre.service.account.AddressTools;
import de.andre.utils.validation.AddressValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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

    @JsonView(View.AddressView.class)
    @RequestMapping(value = "/getEditAddress", method = RequestMethod.GET)
    public Address getEditAddress(@RequestParam("addressName") final String addressName) {
        return addressTools.addressByName(addressName);
    }

    @RequestMapping(value = "/deleteAddress", method = RequestMethod.POST)
    public ObjectNode deleteAddress(@RequestParam("addressName") final String addressName) {
        try {
            final boolean success = addressTools.deleteAddressByName(addressName) > 0;
            return objectMapper.createObjectNode()
                    .put("success", success)
                    .put("deletedName", addressName);
        } catch (Exception e) {
            return objectMapper.createObjectNode()
                    .put("success", false)
                    .put("error", e.toString());
        }

    }

    @RequestMapping(value = "/changeDefault", method = RequestMethod.POST)
    public ObjectNode changeDefaultShipping(@RequestParam("addressName") final String addressName) {
        try {
            final String newNickname = addressTools.changeDefaultShipping(addressName);
            return objectMapper.createObjectNode()
                    .put("success", true)
                    .put("newNickname", newNickname);
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
                boolean update = StringUtils.hasLength(address.getAddressName());

                final String addressNickname = StringUtils.hasLength(address.getAddressName()) ?
                        addressTools.updateSecondaryAddress(address) :
                        addressTools.addSecondaryAddress(address);
                return objectMapper.createObjectNode()
                        .put("success", true)
                        .put("nickname", addressNickname)
                        .put("isNew", !update)
                        .put("state", address.getState().getName());
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
