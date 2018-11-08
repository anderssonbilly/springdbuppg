package com.databasuppg.API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
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
        for(Album album : c.getAlbum("Good", 20)) {
            System.out.println(album.toString());
        }
    }

    public ArrayList<Album> getAlbum(String albumName, int limit) {

        ArrayList<Album> results = new ArrayList<>();

        try {
            Document albumResults = searchAlbum(albumName, limit);
            albumResults.normalize();

            Element element = albumResults.getDocumentElement();
            NodeList nodes = element.getElementsByTagName("album");


            for(int i=0; i<nodes.getLength(); i++) {
                Node node = nodes.item(i);
                Element currentElement = (Element) node;
                if(node.getNodeType() == Node.ELEMENT_NODE) {

                    String name = currentElement.getElementsByTagName("name").item(0).getTextContent();
                    String artist = currentElement.getElementsByTagName("artist").item(0).getTextContent();
                    String url = currentElement.getElementsByTagName("url").item(0).getTextContent();
                    String image = currentElement.getElementsByTagName("image").item(1).getTextContent();
                    ArrayList<Track> tracks = getTrack(name, artist);

                    Document temp = getAlbumInfo(name, artist);
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

        Document doc = getAlbumInfo(albumName, artistName);
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
    //TODO Implement page variable
    private Document searchAlbum(String name, int limit) throws IOException, SAXException {
        String urlString = root + String.format("?method=album.search&album=%s&limit=%s&api_key=%s", name, limit, this.key);
        urlString = urlString.replaceAll(" ", "%20");

        URL url = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        return documentBuilder.parse(con.getInputStream());

    }

    private Document getAlbumInfo(String album, String artist) throws IOException, SAXException {
        String urlString = root + String.format("?method=album.getinfo&api_key=%s&artist=%s&album=%s", this.key, artist, album);
        urlString = urlString.replaceAll(" ", "%20");

        URL url = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        return documentBuilder.parse(con.getInputStream());
    }



}



