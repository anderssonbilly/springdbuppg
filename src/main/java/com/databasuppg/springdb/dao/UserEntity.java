package com.databasuppg.springdb.dao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity implements Serializable{

	private static final long serialVersionUID = 9037430801165344004L;

	@Id
	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;
	
	@Column(name = "enabled")
	private boolean enabled;
}
