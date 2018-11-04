package com.databasuppg.springdb.dao;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<UserEntity, Long>{

}
