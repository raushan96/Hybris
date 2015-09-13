package de.andre.web.controller.account;

import de.andre.entity.core.DpsUser;
import de.andre.entity.core.utils.ForgotPasswordForm;
import de.andre.service.account.AccountTools;
import de.andre.utils.validation.DpsUserValidator;
import de.andre.utils.validation.ForgotPasswordFormValidator;
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
import java.security.Principal;


@Controller
@RequestMapping(value = "/account")
public class AccountController {
	private static final Logger log = LoggerFactory.getLogger(AccountController.class);

	private final AccountTools accountTools;
	private final DpsUserValidator dpsUserValidator;
	private final ForgotPasswordFormValidator forgotPasswordFormValidator;

	@Autowired
	public AccountController(final AccountTools accountTools, final DpsUserValidator dpsUserValidator,
							 final ForgotPasswordFormValidator forgotPasswordFormValidator) {
		this.accountTools = accountTools;
		this.dpsUserValidator = dpsUserValidator;
		this.forgotPasswordFormValidator = forgotPasswordFormValidator;
		log.info("Account controller initialized..");
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
	public String showAccount(final Model map) {
		final DpsUser dpsUser = accountTools.getCommerceUser();
		map.addAttribute("dpsUser", dpsUser);
		map.addAttribute("addresses", accountTools.findAddressesByUser(dpsUser));
		return "account/profile";
	}

	@RequestMapping(value = "/editProfile", method = RequestMethod.GET)
	public String editAccount() {
		return "account/editProfile";
	}



	@RequestMapping(value = "/editProfile", method = RequestMethod.POST)
	public String updateAccount(@Valid final DpsUser dpsUser, final BindingResult result) {
		if (result.hasErrors()) {
			return "account/editProfile";
		} else {
			accountTools.saveUser(dpsUser);
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

	@ModelAttribute("dpsUser")
	public DpsUser populateUserForm() {
		return accountTools.getCommerceUser();
	}

	@ModelAttribute("forgotPasswordForm")
	public ForgotPasswordForm populatePasswordForm() {
		return new ForgotPasswordForm();
	}
}
