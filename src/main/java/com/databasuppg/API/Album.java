package com.databasuppg.API;
import lombok.Getter;

public class Album {
    @Getter private String name;
    @Getter private String artist;
    @Getter private String url;
    @Getter private String imageReference;

    public Album() {}

    public Album(String name, String artist, String url, String imageReference) {
        this.name = name;
        this.artist = artist;
        this.url = url;
        this.imageReference = imageReference;
    }


    @Override
    public String toString() {
        return "Album{" +
                "name='" + name + '\'' +
                ", artist='" + artist + '\'' +
                ", url='" + url + '\'' +
                ", imageReference='" + imageReference + '\'' +
                '}';
    }
}
