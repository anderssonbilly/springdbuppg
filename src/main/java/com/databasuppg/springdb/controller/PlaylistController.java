package com.databasuppg.springdb.controller;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.databasuppg.API.APIController;
import com.databasuppg.API.Track;
import com.databasuppg.springdb.model.PlaylistDto;
import com.databasuppg.springdb.model.UserDto;

@Controller
public class PlaylistController {


	@Autowired
	private APIController apiController;
	
	@RequestMapping("/playlist")
	public ModelAndView playlists(Model model, @ModelAttribute("newPlaylist") String newPlaylist) {
		ModelAndView modelAndView = new ModelAndView("playlist");
		
		ArrayList<PlaylistDto> playlists = new ArrayList<PlaylistDto>();
		
		PlaylistDto p1 = new PlaylistDto();
		p1.setId(1);
		p1.setName("ett");
		PlaylistDto p2 = new PlaylistDto();
		p2.setId(2);
		p2.setName("två");
		PlaylistDto p3 = new PlaylistDto();
		p3.setId(3);
		p3.setName("tre");
		PlaylistDto p4 = new PlaylistDto();
		p4.setId(4);
		p4.setName("fyra");
		PlaylistDto p5 = new PlaylistDto();
		p5.setId(5);
		p5.setName("fem");
		PlaylistDto p6 = new PlaylistDto();
		p6.setId(6);
		p6.setName("sex");
		playlists.add(p1);
		playlists.add(p2);
		playlists.add(p3);
		playlists.add(p4);
		playlists.add(p5);
		playlists.add(p6);
		
		modelAndView.addObject("playlists", playlists);
		
		return modelAndView;
	}

	@RequestMapping(value = "/getPlaylist", method = RequestMethod.POST)
	public ResponseEntity<ArrayList<Track>> getPlayList(@RequestBody int id) {
		ArrayList<Track> playlist = new ArrayList<Track>();
		
		System.out.println("Get Playlist: " + id);
		return new ResponseEntity<>(playlist,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/createPlaylist", method = RequestMethod.POST)
	public ResponseEntity<Boolean> createPlayList(@RequestBody String playlistName) {
		ModelAndView modelAndView = new ModelAndView("/playlists");
		System.out.println(playlistName);
		return new ResponseEntity<>(Boolean.TRUE,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/addToPlaylist", method = RequestMethod.POST)
	public ResponseEntity<Boolean> addToPlaylist(@RequestBody String playlist, @RequestBody String track) {
		
		System.out.println(playlist + " " + track);
		
		return new ResponseEntity<>(Boolean.TRUE,HttpStatus.OK);
	}

	@RequestMapping(value = "/updatePlaylist", method = RequestMethod.POST)
	public ResponseEntity<Boolean> createPlayList(Model model, ArrayList<Track> playlist) {
		System.out.println(playlist);
		return new ResponseEntity<>(Boolean.TRUE,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/removeFromPlaylist", method = RequestMethod.POST)
	public ResponseEntity<Boolean> removeFromnPlaylist(@RequestBody String playlist, @RequestBody String track) {
		System.out.println(playlist + " " + track);
		return new ResponseEntity<>(Boolean.TRUE,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/removePlaylist", method = RequestMethod.POST)
	public ResponseEntity<Boolean> removePlaylist(@RequestBody String playlist) {
		System.out.println(playlist);
		return new ResponseEntity<>(Boolean.TRUE,HttpStatus.OK);
	}
	
}
