package com.databasuppg.springdb.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.databasuppg.API.Track;

@Controller
public class CollectionController {

	@RequestMapping(value = "/addToCollection", method = RequestMethod.POST)
	public boolean addToCollection(Model model, String album) {
		System.out.println(album);
		return true;
	}
	
	@RequestMapping(value = "/createPlaylist", method = RequestMethod.POST)
	public ModelAndView createPlayList(Model model, String playlistName) {
		ModelAndView modelAndView = new ModelAndView("/playlists");
		return modelAndView;
	}
	
	@RequestMapping(value = "/addToPlaylist", method = RequestMethod.POST)
	public boolean createPlayList(Model model, String playlistName, String track) {
		System.out.println(playlistName + " " + track);
		return true;
	}

	@RequestMapping(value = "/updatePlaylist", method = RequestMethod.POST)
	public boolean createPlayList(Model model, ArrayList<Track> playlist) {
		System.out.println(playlist);
		return true;
	}
}