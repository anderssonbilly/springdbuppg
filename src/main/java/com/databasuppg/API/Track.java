package com.databasuppg.API;
import lombok.Getter;

public class Track {
    @Getter String name;
    @Getter int duration;
    @Getter String url;

    public Track(String name, int duration, String url) {
        this.name = name;
        this.duration = duration;
        this.url = url;
    }




}
