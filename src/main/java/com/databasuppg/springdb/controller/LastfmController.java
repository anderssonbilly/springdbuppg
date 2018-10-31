package com.databasuppg.springdb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LastfmController {
	@RequestMapping("/lastfm")
	public String index(Model model) {
		return "lastfm";
	}
}
