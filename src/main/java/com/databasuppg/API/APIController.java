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

    public static void main(String args[]) {
        APIController c = new APIController("e785b0c4c020c29bc7a9968603aac839");
        int count = 1;
        for(Album album : c.searchAlbum("Good", 20)) {
            System.out.println(count + ": " + album.toString());
            count++;
        }
    }

    public ArrayList<Album> searchAlbum(String albumName, int limit) {

        ArrayList<Album> results = new ArrayList<>();

        try {
            Document albumResults = APIsearchAlbum(albumName, limit);
            albumResults.normalize();

            Element element = albumResults.getDocumentElement();
            NodeList nodes = element.getElementsByTagName("album");


            for(int i=0; i<nodes.getLength(); i++) {
                Node node = nodes.item(i);

                if(node.getNodeType() == Node.ELEMENT_NODE) {
                    Element currentElement = (Element) node;

                    String name = UriUtils.encode(currentElement.getElementsByTagName("name").item(0).getTextContent(), "utf-8");
                    String artist = UriUtils.encode(currentElement.getElementsByTagName("artist").item(0).getTextContent(), "utf-8");
                    String url = currentElement.getElementsByTagName("url").item(0).getTextContent();
                    String image = currentElement.getElementsByTagName("image").item(1).getTextContent();
                    ArrayList<Track> tracks = getTrack(name, artist);

                    Document temp = APIgetAlbumInfo(name, artist);
                    temp.normalize();

                    results.add(new Album(name, artist, url, image, tracks));

                }
            }


        } catch(IOException | SAXException e) {
            e.printStackTrace();
        }


        return results;
    }

    private ArrayList<Track> getTrack(String albumName, String artistName) throws IOException, SAXException {

        ArrayList<Track> result = new ArrayList<>();

        Document doc = APIgetAlbumInfo(albumName, artistName);
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

               name = name.replaceAll("&", "%26");

               result.add(new Track(name, duration, url));

            }
        }

        return result;
    }

    // API methods
    //TODO Implement page variable
    private Document APIsearchAlbum(String name, int limit) throws IOException, SAXException {
        URL url = new URL(root + String.format("?method=album.search&album=%s&limit=%s&api_key=%s", name, limit, this.key));
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        return documentBuilder.parse(con.getInputStream());
    }

    private Document APIgetAlbumInfo(String album, String artist) throws IOException, SAXException {
        URL url = new URL(root + String.format("?method=album.getinfo&api_key=%s&artist=%s&album=%s", this.key, artist, album));
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        return documentBuilder.parse(con.getInputStream());
    }

    private Document APIsearchArtist(String artist) {

        return documentBuilder.newDocument();
    }




}



