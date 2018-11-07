package com.databasuppg.API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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

    @Value("${lastfm.api.key}") private String key;

    private String root = "http://ws.audioscrobbler.com/2.0/";
    private DocumentBuilder documentBuilder;


    public APIController() {
        try {
            this.documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch(ParserConfigurationException e) {
            e.getMessage();
        }
    }


    // testing
    public static void main(String[] args) {
        APIController api = new APIController();

        ArrayList<Album> results = api.getAlbum("believe", 20);

        for (Album album : results) {
            System.out.println(album.toString());
            System.out.println("+---------------------+");
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

                if(node.getNodeType() == Node.ELEMENT_NODE) {

                    String name = element.getElementsByTagName("name").item(0).getTextContent();
                    String artist = element.getElementsByTagName("artist").item(0).getTextContent();
                    String url = element.getElementsByTagName("url").item(0).getTextContent();
                    String image = element.getElementsByTagName("image").item(1).getTextContent();
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


    //TODO Implement page variable
    private Document searchAlbum(String name, int limit) throws IOException, SAXException {
        URL url = new URL(root + String.format("?method=album.search&album=%s&limit=%s&api_key=%s", name, limit, this.key));
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        return documentBuilder.parse(con.getInputStream());

    }

    private Document getAlbumInfo(String album, String artist) throws IOException, SAXException {
        URL url = new URL(root + String.format("?method=album.getinfo&api_key=%s&artist=%s&album=%s", this.key, artist, album));
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        return documentBuilder.parse(con.getInputStream());
    }



}



