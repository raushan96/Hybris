package de.andre.web.controller.account;

import de.andre.entity.enums.State;
import de.andre.entity.profile.Profile;
import de.andre.entity.util.ForgotPasswordForm;
import de.andre.service.account.ProfileTools;
import de.andre.service.commerce.order.OrderHolder;
import de.andre.utils.validation.ForgotPasswordFormValidator;
import de.andre.utils.validation.ProfileValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/account")
public class AccountController {
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    private final ProfileTools profileTools;
    private final ProfileValidator profileValidator;
    private final ForgotPasswordFormValidator forgotPasswordFormValidator;

    @Autowired
    private OrderHolder orderHolder;

    @Autowired
    public AccountController(final ProfileTools profileTools, final ProfileValidator profileValidator,
                             final ForgotPasswordFormValidator forgotPasswordFormValidator) {
        this.profileTools = profileTools;
        this.profileValidator = profileValidator;
        this.forgotPasswordFormValidator = forgotPasswordFormValidator;
    }

    @InitBinder(value = "forgotPasswordForm")
    public void initPasswordBinder(final WebDataBinder dataBinder) {
        dataBinder.setValidator(forgotPasswordFormValidator);
    }

    @RequestMapping("/profile")
    public String showAccount(final Model map) {
        orderHolder.currentOrder();
        final Profile profile = profileTools.currentProfile();
        map.addAttribute("profile", profile);
        map.addAttribute("addresses", profileTools.addressesByProfile(profile.getId()));
        return "account/profile";
    }

    @RequestMapping(value = "/editProfile", method = RequestMethod.GET)
    public String editAccount(final Model map) {
        map.addAttribute("profile", profileTools.currentProfile());
        return "account/editProfile";
    }

    @RequestMapping(value = "/editProfile", method = RequestMethod.POST)
    public String updateAccount(@Valid final Profile profile, final BindingResult result) {
        profileValidator.validate(profile, result);

        if (result.hasErrors()) {
            return "account/editProfile";
        } else {
            profileTools.updateProfile(profile);
            return "redirect:/account/profile";
        }
    }

    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    public String updatePassword(@Valid final ForgotPasswordForm forgotPasswordForm, final BindingResult result, final Model map) {
        if (result.hasErrors()) {
            map.addAttribute("profile", profileTools.currentProfile());
            return "account/editProfile";
        } else {
            profileTools.updatePassword(forgotPasswordForm.getEnteredPassword());
            return "redirect:/account/profile";
        }
    }

    @ModelAttribute("forgotPasswordForm")
    public ForgotPasswordForm populatePasswordForm() {
        return new ForgotPasswordForm();
    }

    @ModelAttribute(value = "states")
    public State[] populateStates() {
        return State.values();
    }
}
