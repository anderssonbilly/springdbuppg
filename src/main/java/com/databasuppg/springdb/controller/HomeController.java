package com.databasuppg.springdb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.databasuppg.API.APIController;

@Controller
public class HomeController {

	@Autowired
	APIController apiController;
	
	@RequestMapping("/")
	public String index(Model model) {
		return "index";
	}

}
