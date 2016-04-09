package de.andre.web.controller.account;

import com.fasterxml.jackson.databind.node.ObjectNode;
import de.andre.entity.profile.Profile;
import de.andre.service.account.AccountTools;
import de.andre.utils.validation.ProfileValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class LoginController {
    private final AccountTools accountTools;
    private final ProfileValidator profileValidator;

    @Autowired
    public LoginController(final AccountTools accountTools, final ProfileValidator profileValidator) {
        this.accountTools = accountTools;
        this.profileValidator = profileValidator;
    }

    @InitBinder(value = "Profile")
    public void initProfileBinder(final WebDataBinder binder) {
        binder.setValidator(profileValidator);
        binder.setRequiredFields("password");
    }

/*    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() {
        return "account/login";
    }*/

    @RequestMapping("/login-error")
    public String loginError(final Model model) {
        model.addAttribute("loginError", true);
        return "account/login";
    }

    @RequestMapping(value = "/forgotPassword", method = RequestMethod.POST)
    @ResponseBody
    public ObjectNode forgotPassword(@RequestParam("email") final String pEmail) {
        return accountTools.forgotPassword(pEmail);
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerPage(final Model model) {
        model.addAttribute("Profile", new Profile());
        return "account/register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String processRegister(@Valid final Profile profile, final BindingResult result, final HttpServletRequest pRequest) {
        if (result.hasErrors()) {
            return "register";
        } else {
            accountTools.createProfile(profile, pRequest);
            return "redirect:index";
        }
    }
}
