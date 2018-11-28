package com.databasuppg.springdb.dao;

import java.util.ArrayList;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.databasuppg.API.Track;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "album", uniqueConstraints = @UniqueConstraint(columnNames = { "id" }))
public class AlbumEntity {
    
	@Column(unique = true)
	private Long id;
	
	private String name;
    private String artist;
    private String url;
    private String imageReference;
    private Set<TrackEntity> tracks;

    
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	
	@ManyToMany
	@JoinTable(name = "album_track", joinColumns = @JoinColumn(name = "album_id"), inverseJoinColumns = @JoinColumn(name = "track_id"))
	public Set<TrackEntity> getTracks() {
		return tracks;
	}
}
