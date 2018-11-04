package com.databasuppg.springdb.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.databasuppg.springdb.validation.PasswordMatches;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@PasswordMatches
public class UserModel {
	
	@Size(min=4, max=12)
    @NotNull
    @Pattern(regexp="^[A-Za-z0-9]*$")
    @NotEmpty
    private String username;
   
	@Size(min=4, max=16)
    @NotNull
    @NotBlank
    @NotEmpty
    private String password;
    private String matchingPassword;
}
