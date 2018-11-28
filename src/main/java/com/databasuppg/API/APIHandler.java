package com.databasuppg.API;

import org.xml.sax.SAXException;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;

public class APIHandler {

	private JsonParser parser;
	private String root = "http://ws.audioscrobbler.com/2.0/";
	private String key;

	public APIHandler(String key) {
		this.key = key;
		parser = new JsonParser();
	}

	public JsonObject searchAlbum(String album, int limit, int page) throws IOException, SAXException {
		album = URLEncoder.encode(album, "UTF-8");
		URL url = new URL(root + String.format("?method=album.search&album=%s&limit=%s&page=%s&api_key=%s&format=json",
				album, limit, page, this.key));
		HttpURLConnection con = (HttpURLConnection) url.openConnection();

		con.setRequestMethod("GET");
		con.connect();
		BufferedReader json = new BufferedReader(new InputStreamReader(con.getInputStream()));

		JsonElement element = parser.parse(json);
		element = element.getAsJsonObject().getAsJsonObject("results").getAsJsonObject("albummatches");
		
		return (JsonObject) element;
	}

	public JsonObject getAlbumInfo(String album, String artist) throws IOException, SAXException {
		album = URLEncoder.encode(album, "UTF-8");
		artist = URLEncoder.encode(artist, "UTF-8");
		URL url = new URL(root + String.format("?method=album.getinfo&artist=%s&album=%s&api_key=%s&format=json",
				artist, album, this.key));
		HttpURLConnection con = (HttpURLConnection) url.openConnection();

		con.setRequestMethod("GET");
		con.connect();
		BufferedReader json = new BufferedReader(new InputStreamReader(con.getInputStream()));

		return parser.parse(json).getAsJsonObject().getAsJsonObject("album");
	}

	public JsonObject getTopAlbums(String genre, int limit, int page) throws IOException, SAXException {
		genre = URLEncoder.encode(genre, "UTF-8");
		URL url = new URL(
				root + String.format("?method=tag.gettopalbums&tag=%s&limit=%s&page=%s&api_key=%s&format=json", genre,
						limit, page, this.key));
		HttpURLConnection con = (HttpURLConnection) url.openConnection();

		con.setRequestMethod("GET");
		con.connect();
		BufferedReader json = new BufferedReader(new InputStreamReader(con.getInputStream()));

		return parser.parse(json).getAsJsonObject().getAsJsonObject("albums");
	}
}
