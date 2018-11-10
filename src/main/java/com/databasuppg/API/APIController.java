package com.databasuppg.API;

import org.springframework.web.util.UriUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;



public class APIController {

    private String key;
    private String root = "http://ws.audioscrobbler.com/2.0/";
    private DocumentBuilder documentBuilder;


    public APIController(String key) {
        this.key = key;

        try {
            this.documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch(ParserConfigurationException e) {
            e.getMessage();
        }
    }

    public ArrayList<Album> getTopAlbums(String genre, int limit, int page) {
        ArrayList<Album> results = new ArrayList<>();

        try {
            Document artistResults = APIgetTopAlbums("genre", limit, page);
            artistResults.normalize();

            Element element = artistResults.getDocumentElement();
            NodeList nodes = element.getElementsByTagName("album");

            for(int i=0; i<nodes.getLength(); i++) {
                Node node = nodes.item(i);

                if(node.getNodeType() == Node.ELEMENT_NODE) {
                    Element currentElement = (Element) node;
                    Element temp = (Element) currentElement.getElementsByTagName("artist").item(0);

                    String artistName = temp.getElementsByTagName("name").item(0).getTextContent();
                    String albumName = currentElement.getElementsByTagName("name").item(0).getTextContent();
                    String url = currentElement.getElementsByTagName("url").item(0).getTextContent();
                    String image = currentElement.getElementsByTagName("image").item(2).getTextContent();
                    ArrayList<Track> tracks = getTrack(
                            UriUtils.encode(albumName,"utf-8"),
                            UriUtils.encode(artistName, "utf-8"));

                    results.add(new Album(albumName, artistName, url, image, tracks));
                }
            }
        } catch(IOException | SAXException e) {
            System.err.println(e.getMessage());
        }
        return results;
    }

    public ArrayList<Album> searchArtist(String artist, int limit, int page) {
        ArrayList<Album> results = new ArrayList<>();

        try {
           Document artistResults = APIgetTopAlbums(artist, limit, page);
           artistResults.normalize();

           Element element = artistResults.getDocumentElement();
           NodeList nodes = element.getElementsByTagName("album");

           // get correct name if typed wrong.
           artist = element.getElementsByTagName("topalbums").item(0).
                   getAttributes().getNamedItem("artist").getTextContent();

           for(int i=0; i<nodes.getLength(); i++) {
               Node node = nodes.item(i);

               if(node.getNodeType() == Node.ELEMENT_NODE) {
                   Element currentElement = (Element) node;
                   String artistName = artist;
                   String albumName = currentElement.getElementsByTagName("name").item(0).getTextContent();
                   String url = currentElement.getElementsByTagName("url").item(0).getTextContent();
                   String image = currentElement.getElementsByTagName("image").item(2).getTextContent();
                   ArrayList<Track> tracks = getTrack(
                           UriUtils.encode(albumName,"utf-8"),
                           UriUtils.encode(artistName, "utf-8"));

                   results.add(new Album(albumName, artistName, url, image, tracks));
               }
           }
        } catch(IOException | SAXException e) {
            System.err.println(e.getMessage());
        }
        return results;
    }

    public ArrayList<Album> searchAlbum(String albumName, int limit, int page) {

        ArrayList<Album> results = new ArrayList<>();

        try {
            Document albumResults = APIsearchAlbum(albumName, limit, page);
            albumResults.normalize();

            Element element = albumResults.getDocumentElement();
            NodeList nodes = element.getElementsByTagName("album");


            for(int i=0; i<nodes.getLength(); i++) {
                Node node = nodes.item(i);

                if(node.getNodeType() == Node.ELEMENT_NODE) {
                    Element currentElement = (Element) node;
                    String album = currentElement.getElementsByTagName("name").item(0).getTextContent();
                    String artist = currentElement.getElementsByTagName("artist").item(0).getTextContent();
                    String url = currentElement.getElementsByTagName("url").item(0).getTextContent();
                    String image = currentElement.getElementsByTagName("image").item(2).getTextContent();
                    ArrayList<Track> tracks = getTrack(
                            UriUtils.encode(album, "utf-8"),
                            UriUtils.encode(artist, "utf-8"));

                    results.add(new Album(album, artist, url, image, tracks));
                }
            }
        } catch(IOException | SAXException e) {
            e.printStackTrace();
        }
        return results;
    }

    private ArrayList<Track> getTrack(String album, String artist) throws IOException, SAXException {
        ArrayList<Track> result = new ArrayList<>();

        Document doc = APIgetAlbumInfo(album, artist);
        doc.normalize();

        Element element = doc.getDocumentElement();
        NodeList nodes = element.getElementsByTagName("track");

        for(int i=0; i<nodes.getLength(); i++) {
            Node node = nodes.item(i);

            if(node.getNodeType() == node.ELEMENT_NODE) {
               Element nodeElement = (Element) node;
               String name = nodeElement.getElementsByTagName("name").item(0).getTextContent();
               int duration = Integer.parseInt(nodeElement.getElementsByTagName("duration").item(0).getTextContent());
               String url = nodeElement.getElementsByTagName("url").item(0).getTextContent();
               result.add(new Track(name, duration, url));
            }
        }
        return result;
    }

    // API methods
    private Document APIsearchAlbum(String album, int limit, int page) throws IOException, SAXException {
        URL url = new URL(root + String.format("?method=album.search&album=%s&limit=%s&page=%s&api_key=%s", album, limit, page, this.key));
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        return documentBuilder.parse(con.getInputStream());
    }

    private Document APIgetAlbumInfo(String album, String artist) throws IOException, SAXException {
        // album = UriUtils.encode(album, "utf-8");
        // artist = UriUtils.encode(artist, "utf-8");
        URL url = new URL(root + String.format("?method=album.getinfo&artist=%s&album=%s&api_key=%s", artist, album, this.key));
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        return documentBuilder.parse(con.getInputStream());
    }

    private Document APIgetTopAlbums(String genre, int limit, int page) throws IOException, SAXException {
        // artist = UriUtils.encode(artist, "utf-8");
        URL url = new URL(root + String.format("?method=tag.gettopalbums&tag=%s&limit=%s&page=%s&api_key=%s", genre, limit, page, this.key));
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        return documentBuilder.parse(con.getInputStream());
    }






}



