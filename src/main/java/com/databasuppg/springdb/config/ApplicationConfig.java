package com.databasuppg.springdb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;
import com.databasuppg.API.*;
import com.databasuppg.config.*;


@Configuration
public class ApplicationConfig {

	@Bean
	public SpringSecurityDialect additionalDialects() {
		return new SpringSecurityDialect();
	}
	
	@Bean
	public APIController apiController(){
		return new APIController(new Config().getAPIKey());
	}
	
}
