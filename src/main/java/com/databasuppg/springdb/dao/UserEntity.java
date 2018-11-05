package com.databasuppg.springdb.dao;

import java.util.Set;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = { "username" }))
public class UserEntity {

	private Long id;

	@Column(unique = true)
	private String username;

	private String password;
	private String passwordConfirm;
	private Set<RoleEntity> roles;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	@Transient
	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	@ManyToMany
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	public Set<RoleEntity> getRoles() {
		return roles;
	}
}
