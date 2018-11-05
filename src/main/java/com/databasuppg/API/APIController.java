package com.databasuppg.API;

import java.io.*;
import java.net.HttpURLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.net.URL;
import java.util.ArrayList;

import com.databasuppg.config.Config;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class APIController {

    private String key;
    private String root = "http://ws.audioscrobbler.com/2.0/";
    private DocumentBuilder documentBuilder;


    APIController(String key) {
        this.key = key;

        try {
            this.documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch(ParserConfigurationException e) {
            e.getMessage();
        }
    }


    public static void main(String[] args) {

        Config conf = new Config();
        APIController c = new APIController(conf.getAPIKey());


        try {
            ArrayList<Album> albumResults = c.searchAlbum("believe", 10);
            for(Track track : c.getTracks(albumResults.get(0))) {
                System.out.println(track.toString());
            }

        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }
    }


    public ArrayList<Album> searchAlbum(String name, int limit) throws IOException, SAXException {
         ArrayList<Album> results = new ArrayList<>();

        // use API method album.search
        // https://www.last.fm/api/show/album.search
        URL url = new URL(root + String.format("?method=album.search&album=%s&limit=%s&api_key=%s", name, limit, this.key));
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        // gets the nodes of the album tag
        Document doc = documentBuilder.parse(con.getInputStream());
        doc.getDocumentElement().normalize();

        // gets the nodes of the album tag
        NodeList result = doc.getElementsByTagName("album");

        // loops through every node in album element
        for(int i=0; i<result.getLength(); i++) {

            // gets the current element
            Node node = result.item(i);

            // gets the value if the node is of type element
            if(node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                Album temp =  new Album(
                                element.getElementsByTagName("name").item(0).getTextContent(),
                                element.getElementsByTagName("artist").item(0).getTextContent(),
                                element.getElementsByTagName("url").item(0).getTextContent(),
                                element.getElementsByTagName("image").item(2).getTextContent()  // Three image sizes are available.
                                                                                                      //  index* 1: small, 2: medium, 3: large
                );
                results.add(temp);
            }
        }
        return results;

    }

    public ArrayList<Track> getTracks(Album album) throws IOException, SAXException {

        ArrayList<Track> results = new ArrayList<>();
        String albumName = album.getName();
        String artistName = album.getArtist();

        // use API method album.getInfo
        // https://www.last.fm/api/show/album.getInfo
        URL url = new URL(root + String.format("?method=album.getinfo&artist=%s&album=%s&api_key=%s",artistName, albumName, this.key));
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        Document doc = documentBuilder.parse(con.getInputStream());
        doc.getDocumentElement().normalize();

        // gets the nodes of the tracks tag
        NodeList result = doc.getElementsByTagName("track");

        //TODO unified function for getting child elements
        for(int i=0; i<result.getLength(); i++) {
            Node node = result.item(i);

            if(node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                Track temp = new Track(
                        element.getElementsByTagName("name").item(0).getTextContent(),
                        Integer.parseInt(element.getElementsByTagName("duration").item(0).getTextContent()),
                        element.getElementsByTagName("url").item(0).getTextContent()
                );

                results.add(temp);

            }


        }

       return results;
    }

    //TODO implement this to prevent redundant code.
//    private Object getChildElements(String tag) {

//        return Object;
//    }



}
