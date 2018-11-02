package com.databasuppg.API;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.net.HttpURLConnection;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.net.URL;

import com.databasuppg.config.Config;
import org.junit.jupiter.api.Test;
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

    public Album searchAlbum(String name, int limit) throws IOException, SAXException {

        URL url = new URL(root + String.format("?method=album.search&album=%s&limit=%s&api_key=%s", name, limit, this.key));
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        Document doc = documentBuilder.parse(con.getInputStream());
        doc.getDocumentElement().normalize();
        NodeList result = doc.getElementsByTagName("album");

        // loops through every node in album element
        for(int i=0; i<result.getLength(); i++) {

            // gets the current element
            Node node = result.item(i);
            System.out.println("\nCurrent Element :" + node.getNodeName());

            // gets the value if the node is of type element
            if(node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                return new Album(
                        element.getElementsByTagName("name").item(0).getTextContent(),
                        element.getElementsByTagName("artist").item(0).getTextContent(),
                        element.getElementsByTagName("url").item(0).getTextContent(),
                        element.getElementsByTagName("image").item(2).getTextContent() // Three image sizes are available.
                                                                                             //  index* 1: small, 2: medium, 3: large
                );
            }
        }
        return new Album();
    }

}
