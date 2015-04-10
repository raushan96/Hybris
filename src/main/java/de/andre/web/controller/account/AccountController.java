package de.andre.web.controller.account;

import de.andre.entity.core.DpsGiftlist;
import de.andre.entity.core.DpsUser;
import de.andre.repository.AddressRepository;
import de.andre.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by andreika on 3/22/2015.
 */

@Controller
@SessionAttributes(types = DpsUser.class)
public class AccountController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	AddressRepository addressRepository;

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
		dateFormat.setLenient(false);
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		webDataBinder.setDisallowedFields("userId");
	}

	@RequestMapping("/account/profile")
	public String showAccount(Model map, Principal principal) {
		DpsUser dpsUser = userRepository.findByLogin(principal.getName());
		map.addAttribute("user", dpsUser);
		map.addAttribute("addresses", addressRepository.findAddressesByCustomer(dpsUser));

		return "account/profile";
	}

	@RequestMapping(value = "/account/editProfile", method = RequestMethod.GET)
	public ModelAndView editAccount(@RequestParam("userId") String id) {
		ModelAndView mav = new ModelAndView("account/editProfile");
		mav.addObject("editUser", userRepository.findOne(Integer.valueOf(id)));
		return mav;
	}

	@RequestMapping(value = "/account/editProfile", method = RequestMethod.POST)
	public String updateAccount(DpsUser editUser, BindingResult result, SessionStatus status) {
		if (result.hasErrors()) {
			return "account/editProfile";
		} else {
			userRepository.editUserUpdate(editUser.getFirstName(), editUser.getLastName(), editUser.getEmail(), editUser.getDateOfBirth());
			status.setComplete();
			return "redirect:/account/profile";
		}
	}
}
