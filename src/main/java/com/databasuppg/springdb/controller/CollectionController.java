package com.databasuppg.springdb.controller;

import java.sql.*;
import java.util.ArrayList;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.StoredProcedureQuery;

import org.apache.coyote.http11.Http11AprProtocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.databasuppg.API.APIController;
import com.databasuppg.API.Track;

@Controller
public class CollectionController {

	@Autowired
	private APIController apiController;
	
	@RequestMapping(value = "/addToCollection", method = RequestMethod.POST)
	public ResponseEntity<Boolean> addToCollection(@RequestBody String album) {
		
		System.out.println(album);
		
		return new ResponseEntity<>(Boolean.TRUE,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/removeFromCollection", method = RequestMethod.POST)
	public boolean removeFromCollection(Model model, String album) {
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
	
	@RequestMapping(value = "/removePlaylist", method = RequestMethod.POST)
	public boolean removePlaylist(Model model, String playlistName) {
		System.out.println(playlistName);
		return true;
	}

	public ArrayList<Track> getTracksFromUser(String username) {
		Connection conn;
		ResultSet result;
		CallableStatement statement;
        ArrayList<Track> tracks = new ArrayList<>();

		try {
			conn = DriverManager.getConnection("jdbc:mysql://217.208.107.210:3301/dbuppgift", "uppgift", "password");

			String query = "CALL getSongsForUser(?)";
			statement = conn.prepareCall(query);
			statement.setString(1, username);

			result = statement.executeQuery();



			while(result.next()) {
                System.out.println(result.getString("artist"));
                tracks.add(new Track(
                        result.getString("title"),
                        result.getString("artist"),
                        result.getInt("length")));

            }

		} catch(SQLException e) {
			System.err.println(e.getMessage());
		}

		return tracks;
	}

	public static void main(String[] args) {
	}
	
	@RolesAllowed("USER")
	@RequestMapping("/collection")
	public ModelAndView collection(Model model) {
		ModelAndView modelAndView = new ModelAndView("albums");
		modelAndView.addObject("title","Collection");
		modelAndView.addObject("collection",true);
		
		modelAndView.addObject(apiController.searchAlbum("test", 25, 1));

		
		
        System.out.println();
        
		return modelAndView;
	}
}