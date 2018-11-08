package com.databasuppg.API;
import lombok.Getter;

import java.util.ArrayList;

public class Album {
    @Getter private String name;
    @Getter private String artist;
    @Getter private String url;
    @Getter private String imageReference;
    @Getter private ArrayList<Track> tracks;

    public Album() {}


    public Album(String name, String artist, String url, String imageReference, ArrayList<Track> tracks) {
        this.name = name;
        this.artist = artist;
        this.url = url;
        this.imageReference = imageReference;
        this.tracks = tracks;

    }

    @Override
    public String toString() {
        return "Album{" +
                "name='" + name + '\'' +
                ", artist='" + artist + '\'' +
                ", url='" + url + '\'' +
                ", imageReference='" + imageReference + '\'' +
                ", tracks=" + tracks +
                '}';
    }
}
