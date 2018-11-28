package com.databasuppg.springdb.dao;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<AlbumEntity, Long>{
	AlbumEntity findByName(String name);
}
