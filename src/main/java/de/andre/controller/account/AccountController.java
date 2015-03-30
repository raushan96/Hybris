package de.andre.controller.account;

import de.andre.entity.core.DpsUser;
import de.andre.repository.AddressRepository;
import de.andre.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

/**
 * Created by andreika on 3/22/2015.
 */

@Controller
public class AccountController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;

    @RequestMapping("/account/profile")
    public String acountPage(Model map, Principal principal) {
        DpsUser dpsUser = userRepository.findByLogin(principal.getName());
        map.addAttribute("user", dpsUser);
        map.addAttribute("addresses", addressRepository.findAddressesByCustomer(dpsUser));

        return "account/profile";
    }

}
