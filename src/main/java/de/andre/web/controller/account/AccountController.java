package de.andre.web.controller.account;

import de.andre.entity.core.utils.ForgotPasswordForm;
import de.andre.entity.enums.UState;
import de.andre.entity.profile.Profile;
import de.andre.service.account.AccountTools;
import de.andre.utils.validation.ForgotPasswordFormValidator;
import de.andre.utils.validation.ProfileValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


@Controller
@RequestMapping(value = "/account")
public class AccountController {
    private static final Logger log = LoggerFactory.getLogger(AccountController.class);

    private final AccountTools accountTools;
    private final ProfileValidator profileValidator;
    private final ForgotPasswordFormValidator forgotPasswordFormValidator;

    @Autowired
    public AccountController(final AccountTools accountTools, final ProfileValidator profileValidator,
                             final ForgotPasswordFormValidator forgotPasswordFormValidator) {
        this.accountTools = accountTools;
        this.profileValidator = profileValidator;
        this.forgotPasswordFormValidator = forgotPasswordFormValidator;
    }

    @InitBinder(value = "forgotPasswordForm")
    public void initPasswordBinder(final WebDataBinder dataBinder) {
        dataBinder.setValidator(forgotPasswordFormValidator);
    }

    @InitBinder(value = "Profile")
    public void initProfileBinder(final WebDataBinder dataBinder) {
        dataBinder.setValidator(profileValidator);
        DateFormat dateFormat = new SimpleDateFormat("mm/dd/yyyy");
        dataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

    @RequestMapping("/profile")
    public String showAccount(final Model map) {
        final Profile Profile = accountTools.getCommerceProfile();
        map.addAttribute("Profile", Profile);
        map.addAttribute("addresses", accountTools.findAddressesByProfile(Profile));
        return "account/profile";
    }

    @RequestMapping(value = "/editProfile", method = RequestMethod.GET)
    public String editAccount(final Model map) {
        map.addAttribute("Profile", accountTools.getCommerceProfile());
        return "account/editProfile";
    }



    @RequestMapping(value = "/editProfile", method = RequestMethod.POST)
    public String updateAccount(@Valid final Profile profile, final BindingResult result) {
        if (result.hasErrors()) {
            return "account/editProfile";
        } else {
            profile.setId(accountTools.getCommerceProfile().getId());
            accountTools.saveProfile(profile);
            accountTools.updateCommerceProfile(profile);
            return "redirect:/account/profile";
        }
    }

    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    public String updatePassword(@Valid final ForgotPasswordForm forgotPasswordForm, final BindingResult result, final Principal principal) {
        if (result.hasErrors()) {
            return "account/editProfile";
        } else {
            accountTools.updatePassword(principal.getName(), forgotPasswordForm.getEnteredPassword());
            return "redirect:/account/profile";
        }
    }

    @ModelAttribute("forgotPasswordForm")
    public ForgotPasswordForm populatePasswordForm() {
        return new ForgotPasswordForm();
    }

    @ModelAttribute(value = "states")
    public UState[] populateStates() {
        return UState.values();
    }
}
