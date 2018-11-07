package com.databasuppg.springdb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;

@Configuration
public class ApplicationConfig {

	@Bean
	public SpringSecurityDialect additionalDialects() {
		return new SpringSecurityDialect();
	}
	
}
