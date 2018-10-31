package com.databasuppg.springdb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/","/home","/register","/login","/lastfm","/css/**","/js/**","/img/**")
			.permitAll()
			.anyRequest().authenticated()
			.and()
		.formLogin()
			.loginPage("/login")
			.permitAll().defaultSuccessUrl("/")
			.and()
		.logout()
			.permitAll();
		http.exceptionHandling().accessDeniedPage("/error");
		http.csrf().disable();
	} 
    
}