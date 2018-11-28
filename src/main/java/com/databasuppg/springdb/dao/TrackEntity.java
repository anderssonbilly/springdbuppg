package com.databasuppg.springdb.dao;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "track", uniqueConstraints = @UniqueConstraint(columnNames = { "id" }))
public class TrackEntity {

	private Long id;
	
    private String name;
    private String artist;
    private int duration;
    private String url;
	private Set<AlbumEntity> albums;
    
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	
    @ManyToMany(mappedBy = "tracks")
    public Set<AlbumEntity> getAlbums() {
        return albums;
    }
}
