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

import com.databasuppg.springdb.model.UserModel;
import com.databasuppg.springdb.service.UserService;

@Controller
public class RegistrationController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String showForm(WebRequest request, Model model) {
		UserModel user = new UserModel();
		model.addAttribute("user", user);
		return "register";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView registerUSer(@ModelAttribute("user") @Valid UserModel accountDto, BindingResult result,
			WebRequest request, Model model) {
		ModelAndView modelAndView = new ModelAndView("register");
		
		if (!result.hasErrors()) {
			// TODO register user
		}

		if (result.hasGlobalErrors())
			if (result.getGlobalError().getCode().equals("PasswordMatches"))
				result.addError(new FieldError("matchingPassword", "matchingPassword",
						result.getGlobalError().getDefaultMessage()));

		return modelAndView;
	}
}
