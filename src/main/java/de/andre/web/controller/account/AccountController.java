package de.andre.web.controller.account;

import de.andre.entity.core.DpsUser;
import de.andre.entity.core.utils.ForgotPasswordForm;
import de.andre.service.account.AccountTools;
import de.andre.utils.validation.ForgotPasswordFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.security.Principal;

/**
 * Created by andreika on 3/22/2015.
 */

@Controller
@SessionAttributes(value = "dpsUser")
public class AccountController {

	private final AccountTools accountTools;

	@Autowired
	private ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource;

	@Autowired
	public AccountController(AccountTools accountTools) {
		this.accountTools = accountTools;
	}

	@InitBinder("passwordForm")
	public void initBinder(WebDataBinder webDataBinder) {
/*		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
		dateFormat.setLenient(false);
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));*/
		webDataBinder.setDisallowedFields("userId");
		webDataBinder.setValidator(new ForgotPasswordFormValidator());
	}

	@RequestMapping("/account/profile")
	public String showAccount(Model map, Principal principal) {
		DpsUser dpsUser = accountTools.findUserByEmail(principal.getName());
		map.addAttribute("dpsUser", accountTools.findUserByEmail(principal.getName()));
		map.addAttribute("addresses", accountTools.findAddressesByUser(dpsUser));
		return "account/profile";
	}

	@RequestMapping(value = "/account/editProfile", method = RequestMethod.GET)
	public String editAccount(@RequestParam("userId") String id, ModelMap map) {
		if (!map.containsKey("dpsUser")) {
			map.addAttribute("dpsUser", accountTools.getUserById(Integer.valueOf(id)));
		}
		map.addAttribute("passwordForm", new ForgotPasswordForm());
		return "account/editProfile";
	}

	@RequestMapping(value = "/account/editProfile", method = RequestMethod.POST)
	public String updateAccount(@Valid DpsUser dpsUser, BindingResult result, SessionStatus status) {
		if (result.hasErrors()) {
			return "account/editProfile";
		} else {
			accountTools.saveUser(dpsUser);
			status.setComplete();
			return "redirect:/account/profile";
		}
	}

	@RequestMapping(value = "/account/resetPassword", method = RequestMethod.POST)
	public String savePassword(@Valid ForgotPasswordForm passwordForm, BindingResult result, Principal principal) {
		if (result.hasErrors()) {
			return "account/editProfile";
		} else {
			accountTools.updatePassword(principal.getName(), passwordForm.getEnteredPassword());
			return "redirect:/account/profile";
		}
	}
}
