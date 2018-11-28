package com.databasuppg.springdb.service;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.databasuppg.springdb.dao.RoleEntity;
import com.databasuppg.springdb.dao.RoleRepository;
import com.databasuppg.springdb.dao.UserEntity;
import com.databasuppg.springdb.dao.UserRepository;

@Service
public class UserServiceImpl implements IUserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(UserEntity user) {
    	HashSet<RoleEntity> userRole = new HashSet<RoleEntity>();
    	
    	if(roleRepository.findByName("USER") == null) {
    		RoleEntity role = new RoleEntity();
    		role.setName("USER");
    		roleRepository.save(role);
    	}
    	
    	userRole.add(roleRepository.findByName("USER"));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(userRole);
        userRepository.save(user);
    }

    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
