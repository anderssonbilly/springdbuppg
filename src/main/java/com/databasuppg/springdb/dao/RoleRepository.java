package com.databasuppg.springdb.dao;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity, Long>{
	RoleEntity findByName(String name);
}