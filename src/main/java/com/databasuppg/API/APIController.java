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
import java.util.ArrayList;



public class APIController {

    private String key;
    private DocumentBuilder documentBuilder;
    private APIHandler api;


    public APIController(String key) {
        this.key = key;
        this.api = new APIHandler(key);

        try {
            this.documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch(ParserConfigurationException e) {
            e.getMessage();
        }
    }

    public ArrayList<Album> getTopAlbums(String genre, int limit, int page) {
        ArrayList<Album> results = new ArrayList<>();

        try {
            Document artistResults = api.getTopAlbums("genre", limit, page);
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
           Document artistResults = api.getTopAlbums(artist, limit, page);
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
            Document albumResults = api.searchAlbum(albumName, limit, page);
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

        Document doc = api.getAlbumInfo(album, artist);
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
               result.add(new Track(name, artist, duration, url));
            }
        }
        return result;
    }

    // API methods






}



