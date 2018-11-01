package com.databasuppg.API;


import com.databasuppg.config.Config;
import com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class APIController {

    private String key;
    private String root = "http://ws.audioscrobbler.com/2.0/";


    // For testing
    public static void main(String[] args) {
        Config cfg = new Config();
        cfg.loadConfig();

        APIController controller = new APIController(cfg.getKey());

        try {
            controller.searchAlbum("believe", 1);
        } catch(IOException | JAXBException e) {
            e.getStackTrace();
        }

    }

    APIController(String key) {
        this.key = key;
    }


    public void searchAlbum(String name, int limit) throws JAXBException, IOException {
        Album result;
        URL url = new URL(root + String.format("?method=album.search&album=%s&limit=%s&api_key=%s", name, limit, this.key));
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        JAXBContext jaxbContext = JAXBContext.newInstance(Album.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        result = (Album) unmarshaller.unmarshal(new InputStreamReader(con.getInputStream()));

        System.out.println(result.getName());

    }

}
