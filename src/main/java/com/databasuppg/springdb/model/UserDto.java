package com.databasuppg.springdb.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.databasuppg.springdb.validation.PasswordMatches;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@PasswordMatches
public class UserDto {

	@Size(min = 4, max = 12, message = "Username size must be 4-12")
	@NotNull(message = "")
	@Pattern(regexp = "^[A-Za-z0-9]*$", message = "Username contains invalid character")
	@NotBlank(message = "")
	@NotEmpty(message = "Username must not be empty")
	private String username;

	@Size(min = 6, max = 16, message = "Password size must be 6-16")
	@Pattern(regexp = "^(?:(?=.*?[A-Z])(?:(?=.*?[0-9])(?=.*?[-!@#$%^&*()_[\\\\]{},.<>+=])|(?=.*?[a-z])(?:(?=.*?[0-9])|(?=.*?[-!@#$%^&*()_[\\\\]{},.<>+=])))|(?=.*?[a-z])(?=.*?[0-9])(?=.*?[-!@#$%^&*()_[\\\\]{},.<>+=]))[A-Za-z0-9!@#$%^&*()_[\\\\]{},.<>+=-]{6,16}$", message = "Password must contain one digit, one lowercase and one uppercase letter")
	@NotNull(message = "")
	@NotBlank(message = "")
	@NotEmpty(message = "Password must not be empty")
	private String password;
	private String matchingPassword;
}
