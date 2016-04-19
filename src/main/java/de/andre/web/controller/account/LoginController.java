package de.andre.web.controller.account;

import de.andre.entity.dto.RegistrationForm;
import de.andre.entity.enums.State;
import de.andre.entity.profile.Profile;
import de.andre.service.account.ProfileTools;
import de.andre.utils.validation.ProfileValidator;
import de.andre.utils.validation.RegistrationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Set;

@Controller
public class LoginController {
    private final ProfileTools profileTools;
    private final RegistrationValidator registrationValidator;

    @Autowired
    public LoginController(final ProfileTools accountTools, final RegistrationValidator registrationValidator) {
        this.profileTools = accountTools;
        this.registrationValidator = registrationValidator;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() {
        return "account/login";
    }

    @RequestMapping("/login-error")
    public String loginError(final Model model) {
        model.addAttribute("loginError", true);
        return "account/login";
    }

    @RequestMapping(value = "/forgotPassword", method = RequestMethod.POST)
    public void forgotPassword(@RequestParam("email") final String pEmail) {
        profileTools.forgotPassword(pEmail);
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerPage(final Model model) {
        model.addAttribute("registrationForm", new RegistrationForm());
        model.addAttribute("states", State.STATES);
        model.addAttribute("interests", profileTools.availableInterests());
        return "account/register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String processRegister(
            final RegistrationForm registrationForm,
            @RequestParam(value = "intCodes", required = false) final String[] interests,
            final BindingResult result,
            final Model model) {
        registrationValidator.validate(registrationForm, result);

        if (result.hasErrors()) {
            model.addAttribute("interests", profileTools.availableInterests());
            model.addAttribute("states", State.STATES);
            return "account/register";
        } else {
            profileTools.createProfile(
                    registrationForm.getProfile(),
                    registrationForm.getShippingAddress(),
                    interests);
            return "redirect:/";
        }
    }
}
