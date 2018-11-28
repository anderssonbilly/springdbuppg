package com.databasuppg.springdb.controller;

import java.sql.*;
import java.util.ArrayList;

import javax.annotation.security.RolesAllowed;
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
import com.databasuppg.API.Album;
import com.databasuppg.API.Track;
import com.databasuppg.springdb.dao.AlbumEntity;
import com.databasuppg.springdb.dao.AlbumRepository;

@Controller
public class CollectionController {

	@Autowired
	private APIController apiController;

	@Autowired
	private AlbumRepository albumRepository;

	@RequestMapping(value = "/addToCollection", method = RequestMethod.POST)
	public ResponseEntity<Boolean> addToCollection(@RequestBody String album) {
		album = album.replaceAll("\"", "");

		System.out.println("Add to collection: " + album);

		Album apiAlbum = apiController.searchAlbum(album, 1, 1).get(0);

		if (apiAlbum != null) {
			if (albumRepository.findByName(apiAlbum.getName()) == null) {
				AlbumEntity albumEntity = new AlbumEntity();
				albumEntity.setName(apiAlbum.getName());
				albumEntity.setArtist(apiAlbum.getArtist());
				albumEntity.setImageReference(apiAlbum.getImageReference());
				albumEntity.setUrl(apiAlbum.getUrl());
//				albumEntity.setTracks(); TODO TRACKS ENTITY LIST
				albumRepository.save(albumEntity);
			}
		}
		return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
	}

	@RequestMapping(value = "/removeFromCollection", method = RequestMethod.POST)
	public ResponseEntity<Boolean> removeFromCollection(@RequestBody String album) {

		System.out.println(album);

		return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
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

			while (result.next()) {
				tracks.add(new Track(result.getString("title"), result.getString("artist"), result.getInt("length")));

			}

		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}

		return tracks;
	}

	public ArrayList<Track> getTracksFromPlaylist(int playlistId) {
		Connection conn;
		ResultSet result;
		CallableStatement statement;
		ArrayList<Track> tracks = new ArrayList<>();

		try {
			conn = DriverManager.getConnection("jdbc:mysql://217.208.107.210:3301/dbuppgift", "uppgift", "password");
			String query = "CALL getSongsFromPlaylist(?)";
			statement = conn.prepareCall(query);
			statement.setInt(1, playlistId);

			result = statement.executeQuery();

			while (result.next()) {
				System.out.println(result.getString("artist"));
				tracks.add(new Track(result.getString("title"), result.getString("artist"), result.getInt("length")));

			}

		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}

		return tracks;
	}

	public ArrayList<Track> getTracksFromAlbum(int albumId) {

		Connection conn;
		ResultSet result;
		CallableStatement statement;
		ArrayList<Track> tracks = new ArrayList<>();

		try {
			conn = DriverManager.getConnection("jdbc:mysql://217.208.107.210:3301/dbuppgift", "uppgift", "password");

			String query = "CALL getSongsFromAlbum(?)";
			statement = conn.prepareCall(query);
			statement.setInt(1, albumId);

			result = statement.executeQuery();

			while (result.next()) {
				System.out.println(result.getString("artist"));
				tracks.add(new Track(result.getString("title"), result.getString("artist"), result.getInt("length")));

			}

		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}

		return tracks;
	}

	@RolesAllowed("USER")
	@RequestMapping("/collection")
	public ModelAndView collection(Model model) {
		ModelAndView modelAndView = new ModelAndView("albums");
		modelAndView.addObject("title", "Collection");
		modelAndView.addObject("collection", true);

		modelAndView.addObject(apiController.searchAlbum("test", 25, 1));

		System.out.println();

		return modelAndView;
	}
}