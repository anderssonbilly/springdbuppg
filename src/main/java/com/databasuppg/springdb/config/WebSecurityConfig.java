package com.databasuppg.springdb.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder authenticationMgr) throws Exception {
		authenticationMgr.jdbcAuthentication().dataSource(dataSource).passwordEncoder(new BCryptPasswordEncoder())
				.usersByUsernameQuery("select username,password, enabled from users where username=?")
				.authoritiesByUsernameQuery("select username, authority from authorities where username=?");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/", "/home", "/register", "/login", "/lastfm", "/css/**", "/js/**", "/img/**")
					.permitAll()
					.anyRequest()
					.hasAnyRole("ADMIN","USER")
				.antMatchers("/admin")
					.hasRole("ADMIN")
					.and()
				.formLogin()
				.loginPage("/login")
					.permitAll()
					.defaultSuccessUrl("/")
					.and()
				.logout()
					.permitAll()
					.and()
				.httpBasic();
		http.exceptionHandling().accessDeniedPage("/error");
		http.csrf().disable();
	}

}