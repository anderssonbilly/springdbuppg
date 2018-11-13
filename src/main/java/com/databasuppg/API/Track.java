package com.databasuppg.API;
import lombok.Getter;

public class Track {
    @Getter String name;
    @Getter String artist;
    @Getter int duration;
    @Getter String url;

    public Track(String name, String artist, int duration, String url) {
        this.name = name;
        this.duration = duration;
        this.url = url;
    }

    public Track(String name, String artist, int duration) {
        this.name = name;
        this.artist = artist;
        this.duration = duration;
    }

    public Track(){}


    @Override
    public String toString() {
        return "Track{" +
                "name='" + name + '\'' +
                ", duration=" + duration +
                '}';
    }
}
