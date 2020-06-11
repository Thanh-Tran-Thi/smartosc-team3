package com.smartosc.training.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class SecurityConfigure  extends WebSecurityConfigurerAdapter {
	
	@Override
	  protected void configure(HttpSecurity http) throws Exception {
	    http.csrf().disable().authorizeRequests()
	    .antMatchers("/", "/login", "/logout","/authenticate").permitAll()
	    .anyRequest().hasRole("ADMIN");

	  }
	  
	  @Override
	  public void configure(WebSecurity web) throws Exception {
		  web
		  .ignoring()
		  .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
	  }
}
