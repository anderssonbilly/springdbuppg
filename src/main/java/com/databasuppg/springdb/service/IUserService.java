package com.databasuppg.springdb.service;

import com.databasuppg.springdb.dao.UserEntity;

public interface IUserService {
	void save(UserEntity user);

	UserEntity findByUsername(String username);
}
