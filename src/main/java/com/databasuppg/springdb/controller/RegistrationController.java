package com.databasuppg.springdb.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.databasuppg.springdb.dao.UserEntity;
import com.databasuppg.springdb.model.UserDto;
import com.databasuppg.springdb.service.SecurityServiceImpl;
import com.databasuppg.springdb.service.UserServiceImpl;

@Controller
public class RegistrationController {

	@Autowired
	private UserServiceImpl userService;

	@Autowired
	private SecurityServiceImpl securityService;

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String showForm(WebRequest request, Model model) {
		if (securityService.isAuthenticated())
			return "index";
		
		UserDto user = new UserDto();
		model.addAttribute("user", user);
		return "register";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView registerUSer(@ModelAttribute("user") @Valid UserDto user, BindingResult result,
			WebRequest request, Model model) {
		if (securityService.isAuthenticated())
			return new ModelAndView("index");
		
		ModelAndView modelAndView = new ModelAndView("register");

		if (result.hasGlobalErrors())
			if (result.getGlobalError().getCode().equals("PasswordMatches"))
				result.addError(new FieldError("matchingPassword", "matchingPassword",
						result.getGlobalError().getDefaultMessage()));

		if (!result.hasErrors()) {
			if (userService.findByUsername(user.getUsername()) == null) {
				UserEntity newUser = new UserEntity();
				newUser.setUsername(user.getUsername());
				newUser.setPassword(user.getPassword());
				newUser.setPasswordConfirm(user.getMatchingPassword());

				try {
					userService.save(newUser);
					securityService.autologin(user.getUsername(), user.getMatchingPassword());
					return new ModelAndView("index");
				} catch (Exception e) {
					result.addError(new FieldError("username", "username", "ERROR: Could not save to database"));
				}

			} else
				result.addError(new FieldError("username", "username", "Username allready exists"));
		}
		return modelAndView;
	}
}
