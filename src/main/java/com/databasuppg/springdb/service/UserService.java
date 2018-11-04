package com.databasuppg.springdb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.databasuppg.springdb.dao.UserDao;
import com.databasuppg.springdb.model.UserModel;

@Service
public class UserService implements IUserService{

	@Autowired
	UserDao userDao;

	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	private UserModel user = null;
	
	@Override
	public UsernamePasswordAuthenticationToken validateUser(String username, String password) {
		return null;
	}

	@Override
	public boolean registerUser(String username, String password) {
		return false;
	}

	@Override
	public boolean authenticated() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return (!(authentication instanceof AnonymousAuthenticationToken)) ? true : false;
	}

	@Override
	public String getUsername() {
		return null;
	}

	@Override
	public Object getUser() {
		return null;
	}

}
