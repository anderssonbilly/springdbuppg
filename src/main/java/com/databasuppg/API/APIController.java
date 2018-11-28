package com.databasuppg.API;

import org.xml.sax.SAXException;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.*;
import java.util.ArrayList;

public class APIController {

	private String key;
	private APIHandler api;

	public APIController(String key) {
		this.key = key;
		this.api = new APIHandler(key);
	}

	public ArrayList<Album> getTopAlbums(String genre, int limit, int page) {
		ArrayList<Album> results = new ArrayList<>();

		try {
			JsonObject artistResult = api.getTopAlbums(genre, limit, page);

			JsonArray arr = artistResult.getAsJsonArray("album");
			for (int i = 0; i < arr.size(); i++) {
				String albumName = arr.get(i).getAsJsonObject().get("name").toString();
				String artistName = arr.get(i).getAsJsonObject().get("artist").getAsJsonObject().get("name").toString();
				String url = arr.get(i).getAsJsonObject().get("url").toString();
				String image = arr.get(i).getAsJsonObject().get("image").getAsJsonArray().get(2).getAsJsonObject()
						.get("#text").toString();

				ArrayList<Track> tracks = getTracks(albumName.replaceAll("\"", ""), artistName.replaceAll("\"", ""));

				results.add(new Album(albumName, artistName, url, image, tracks));
			}

		} catch (IOException | SAXException e) {
			System.err.println(e.getMessage());
		}
		return results;
	}

	public ArrayList<Album> searchArtist(String artist, int limit, int page) {
		ArrayList<Album> results = new ArrayList<>();

		try {
			JsonObject artistResult = api.getTopAlbums(artist, limit, page);

			JsonArray arr = artistResult.getAsJsonArray("album");
			for (int i = 0; i < arr.size(); i++) {
				String albumName = arr.get(i).getAsJsonObject().get("name").toString();
				String artistName = arr.get(i).getAsJsonObject().get("artist").getAsJsonObject().get("name").toString();
				String url = arr.get(i).getAsJsonObject().get("url").toString();
				String image = arr.get(i).getAsJsonObject().get("image").getAsJsonArray().get(2).getAsJsonObject()
						.get("#text").toString();

				ArrayList<Track> tracks = getTracks(albumName.replaceAll("\"", ""), artistName.replaceAll("\"", ""));

				results.add(new Album(albumName, artistName, url, image, tracks));
			}

		} catch (IOException | SAXException e) {
			System.err.println(e.getMessage());
		}
		return results;
	}

	public ArrayList<Album> searchAlbum(String album, int limit, int page) {
		ArrayList<Album> results = new ArrayList<>();

		try {
			JsonObject artistResult = api.searchAlbum(album, limit, page);

			JsonArray arr = artistResult.getAsJsonArray("album");
			for (int i = 0; i < arr.size(); i++) {	
				String albumName = arr.get(i).getAsJsonObject().get("name").toString();
				String artistName = arr.get(i).getAsJsonObject().get("artist").toString();
				String url = arr.get(i).getAsJsonObject().get("url").toString();
				String image = arr.get(i).getAsJsonObject().get("image").getAsJsonArray().get(2).getAsJsonObject()
						.get("#text").toString();

				ArrayList<Track> tracks = getTracks(albumName.replaceAll("\"", ""), artistName.replaceAll("\"", ""));

				results.add(new Album(albumName, artistName, url, image, tracks));
			}

		} catch (IOException | SAXException e) {
			System.err.println(e.getMessage());
		}
		return results;
	}

	private ArrayList<Track> getTracks(String album, String artist) throws IOException, SAXException {
        ArrayList<Track> result = new ArrayList<>();
        
        JsonObject albumResult = api.getAlbumInfo(album, artist);
        try {
        	JsonArray arr = albumResult.getAsJsonObject("tracks").getAsJsonArray("track");
            
            for(int i = 0; i < arr.size();i++) {
            	String trackTitle = arr.get(i).getAsJsonObject().get("name").toString();
            	int duration = Integer.parseInt(arr.get(i).getAsJsonObject().get("duration").toString().replaceAll("\"", ""));
            	String url = arr.get(i).getAsJsonObject().get("url").toString();
            			
            	result.add(new Track(trackTitle, artist, duration,url));
            }	
		} catch (Exception e) {
			System.err.println("ERROR: Could not get album info. Album: " + album + ", artist: " + artist);
		}
        
        return result;
    }

	// API methods

}
