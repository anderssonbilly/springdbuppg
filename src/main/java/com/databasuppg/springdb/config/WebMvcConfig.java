package com.databasuppg.springdb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer ;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer  {
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("index");
		registry.addViewController("/home").setViewName("index");
		registry.addViewController("/lastfm").setViewName("lastfm");
		registry.addViewController("/collection").setViewName("collection");
		registry.addViewController("/playlist").setViewName("playlist");
		registry.addViewController("/login").setViewName("login");
		registry.addViewController("/register").setViewName("register");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
		registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
		registry.addResourceHandler("/img/**").addResourceLocations("classpath:/static/img/");
	}
}
