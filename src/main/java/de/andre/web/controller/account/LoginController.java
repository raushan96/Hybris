package de.andre.web.controller.account;

import com.fasterxml.jackson.databind.node.ObjectNode;
import de.andre.service.account.AccountTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {
	private final AccountTools accountTools;

	@Autowired
	public LoginController(final AccountTools accountTools) {
		this.accountTools = accountTools;
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
	@ResponseBody
	public ObjectNode forgotPassword(@RequestParam("email") final String pEmail) {
		return accountTools.forgotPassword(pEmail);
	}
}
