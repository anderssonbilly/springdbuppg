package com.databasuppg.springdb.controller;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.xml.sax.SAXException;

import com.databasuppg.API.APIController;
import com.databasuppg.API.Album;

@Controller
public class LastfmController {

	@Autowired
	private APIController apiController;

	@RequestMapping("/lastfm")
	public ModelAndView lastfm(Model model) {
		ModelAndView modelAndView = new ModelAndView("lastfm");
		modelAndView.addObject("title", "Top 25 Albums");
		modelAndView.addObject(apiSearch("top 10"));
		
		return modelAndView;
	}

	@RequestMapping(value = "/lastfm", method = RequestMethod.POST)
	public ModelAndView search(Model model, String search) {
		ModelAndView modelAndView = new ModelAndView("lastfm");
		modelAndView.addObject("title", "Search result for: \"" + search + "\"");
		modelAndView.addObject(apiSearch(search));

		return modelAndView;
	}

	private ArrayList<Album> apiSearch(String term) {
		return apiController.searchAlbum(term, 25);
	}
}
