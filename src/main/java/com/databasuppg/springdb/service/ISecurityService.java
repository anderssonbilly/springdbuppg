package com.databasuppg.springdb.service;

public interface ISecurityService {
	boolean isAuthenticated();
	String findLoggedInUsername();
	void autologin(String username, String password);
}
