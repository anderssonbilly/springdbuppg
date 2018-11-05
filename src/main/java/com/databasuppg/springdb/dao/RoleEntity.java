package com.databasuppg.springdb.dao;

import java.util.Set;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
@Entity
@Table(name = "role")
public class RoleEntity {

	private Long id;
	
	@Column(unique=true)
    private String name;
    
	private Set<UserEntity> users;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    @ManyToMany(mappedBy = "roles")
    public Set<UserEntity> getUsers() {
        return users;
    }
}