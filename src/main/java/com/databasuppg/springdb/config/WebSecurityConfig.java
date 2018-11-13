package com.databasuppg.springdb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
    private UserDetailsService userDetailsService;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	   @Override
	   public AuthenticationManager authenticationManagerBean() throws Exception {
	       return super.authenticationManagerBean();
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder authenticationMgr) throws Exception {
        authenticationMgr.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers(
						"/", "/home", "/register", "/login", "/collection", "/playlist", "/lastfm",
						"/addToCollection","/removeFromCollection", 
						"/createPlaylist", "/addToPlaylist", "/updatePlaylist", "/removePlaylist", "/getPlaylist",
						"/css/**", "/js/**", "/img/**")
					.permitAll()
					.anyRequest()
					.hasAnyRole("ADMIN","USER")
				.antMatchers("/admin")
					.hasRole("ADMIN")
				.antMatchers("/collection","/playlist", "/addToCollection","/removeFromCollection", "/createPlaylist", "/addToPlaylist","/removeFromPlaylist", "/updatePlaylist", "/removePlaylist", "/getPlaylist")
					.authenticated()
					.and()
				.formLogin()
				.loginPage("/login")
					.permitAll()
					.defaultSuccessUrl("/")
					.and()
				.logout()
					.permitAll()
					.logoutSuccessUrl("/login")
					.and()
				.httpBasic();
		http.exceptionHandling().accessDeniedPage("/error");
		http.csrf().disable();
	}

}