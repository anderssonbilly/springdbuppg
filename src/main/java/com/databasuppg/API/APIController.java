package com.databasuppg.API;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.
import java.net.URL;

public class APIController {

    private String key;
    private String root = "http://ws.audioscrobbler.com/2.0/";



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
