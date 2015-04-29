package de.andre.web.controller.account;

import de.andre.entity.core.DpsUser;
import de.andre.entity.core.utils.ForgotPasswordForm;
import de.andre.service.account.AccountTools;
import de.andre.utils.validation.DpsUserValidator;
import de.andre.utils.validation.ForgotPasswordFormValidator;
import de.andre.web.beans.cart.OrderHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(value = "/account")
@SessionAttributes(value = "dpsUser")
public class AccountController {
	private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

	private final AccountTools accountTools;
	private final DpsUserValidator dpsUserValidator;
	private final ForgotPasswordFormValidator forgotPasswordFormValidator;

	@Autowired
	private OrderHolder orderHolder;

	@Autowired
	public AccountController(final AccountTools accountTools, final DpsUserValidator dpsUserValidator,
							 final ForgotPasswordFormValidator forgotPasswordFormValidator) {
		this.accountTools = accountTools;
		this.dpsUserValidator = dpsUserValidator;
		this.forgotPasswordFormValidator = forgotPasswordFormValidator;
		logger.info("Account controller initialized..");
	}

	@InitBinder(value = "forgotPasswordForm")
	public void initPasswordBinder(final WebDataBinder dataBinder) {
		dataBinder.setValidator(forgotPasswordFormValidator);
	}

	@InitBinder(value = "dpsUser")
	public void initUserBinder(final WebDataBinder dataBinder) {
		dataBinder.setValidator(dpsUserValidator);
	}

	@RequestMapping("/profile")
	public String showAccount(final Model map, final Principal principal) {
		final DpsUser dpsUser = accountTools.findUserByEmail(principal.getName());
		map.addAttribute("dpsUser", accountTools.findUserByEmail(principal.getName()));
		map.addAttribute("addresses", accountTools.findAddressesByUser(dpsUser));
		map.addAttribute("creditCard", accountTools.findCardByUser(dpsUser));
		return "account/profile";
	}

	@RequestMapping(value = "/editProfile", method = RequestMethod.GET)
	public String editAccount(@RequestParam("userId") final String id, final ModelMap map) {
		if (!map.containsKey("dpsUser")) {
			map.addAttribute("dpsUser", accountTools.getUserById(Integer.valueOf(id)));
		}
		map.addAttribute("forgotPasswordForm", new ForgotPasswordForm());
		return "account/editProfile";
	}

	@RequestMapping(value = "/editProfile", method = RequestMethod.POST)
	public String updateAccount(@Valid final DpsUser dpsUser, final BindingResult result, final SessionStatus status) {
		if (result.hasErrors()) {
			return "account/editProfile";
		} else {
			accountTools.saveUser(dpsUser);
			status.setComplete();
			return "redirect:/account/profile";
		}
	}

	@RequestMapping(value = "/account/resetPassword", method = RequestMethod.POST)
	public String savePassword(@Valid final ForgotPasswordForm forgotPasswordForm, final BindingResult result, final Principal principal) {
		if (result.hasErrors()) {
			return "account/editProfile";
		} else {
			accountTools.updatePassword(principal.getName(), forgotPasswordForm.getEnteredPassword());
			return "redirect:/account/profile";
		}
	}
}
