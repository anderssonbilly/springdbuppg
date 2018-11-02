package com.databasuppg.API;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name="results")
public class Albums {
    List<Album> albums;

    public Albums() {
       albums = new ArrayList<Album>();
    }

    @XmlElement(name="Album", type=Album.class)
    public List<Album> getAlbum() {
        return albums;
    }




}
