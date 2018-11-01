package com.databasuppg.API;
import lombok.Getter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "album")
public class Album {

    @XmlElement(name = "name")
    @Getter private String name;

    @XmlElement(name = "artist")
    @Getter private String artist;

    @XmlElement(name = "url")
    @Getter private String url;

    @XmlElement(name = "image")
    @Getter private String imageReference;

    public Album() {}

    public Album(String name, String artist, String url, String imageReference) {
        this.name = name;
        this.artist = artist;
        this.url = url;
        this.imageReference = imageReference;
    }

    public String getName() {
        return name;
    }
}
