package com.databasuppg.API;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class APIHandler {

    DocumentBuilder documentBuilder;
    private String root = "http://ws.audioscrobbler.com/2.0/";
    private String key;

    public APIHandler(String key) {
       this.key = key;

       try {
           this.documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
       } catch(ParserConfigurationException e) {
           System.err.println(e.getMessage());
       }

    }


    public Document searchAlbum(String album, int limit, int page) throws IOException, SAXException  {
        URL url = new URL(root + String.format("?method=album.search&album=%s&limit=%s&page=%s&api_key=%s", album, limit, page, this.key));
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        return documentBuilder.parse(con.getInputStream());
    }

    public Document getAlbumInfo(String album, String artist) throws IOException, SAXException {
        // album = UriUtils.encode(album, "utf-8");
        // artist = UriUtils.encode(artist, "utf-8");
        URL url = new URL(root + String.format("?method=album.getinfo&artist=%s&album=%s&api_key=%s", artist, album, this.key));
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        return documentBuilder.parse(con.getInputStream());
    }

    public Document getTopAlbums(String genre, int limit, int page) throws IOException, SAXException {
        // artist = UriUtils.encode(artist, "utf-8");
        URL url = new URL(root + String.format("?method=tag.gettopalbums&tag=%s&limit=%s&page=%s&api_key=%s", genre, limit, page, this.key));
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        return documentBuilder.parse(con.getInputStream());
    }
}
