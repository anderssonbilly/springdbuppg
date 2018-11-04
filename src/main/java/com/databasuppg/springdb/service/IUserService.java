package com.databasuppg.springdb.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public interface IUserService {

	default BCryptPasswordEncoder passwordEncoder() {
		return UserServicePrivate.passwordEncoder();
	}
	
	public UsernamePasswordAuthenticationToken validateUser(String username, String password);
	public boolean registerUser(String username, String password);
	public boolean authenticated();
	public String getUsername();
	public Object getUser();
	
	class UserServicePrivate {
		private static BCryptPasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		}
	}
}
