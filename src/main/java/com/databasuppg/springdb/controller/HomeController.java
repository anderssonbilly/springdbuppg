package com.databasuppg.springdb.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	@RequestMapping("/")
	public String index(Model model) {
		
		String psw = "admin";
		BCryptPasswordEncoder enc = new BCryptPasswordEncoder();
		String encpsw = enc.encode(psw);
		System.out.println(encpsw);
		
		return "index";
	}

}
