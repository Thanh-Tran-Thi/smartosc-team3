package com.smartosc.training.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//	@Autowired
//	private RequestFilter requestFilter;
	
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		
		httpSecurity.csrf().disable()
			.authorizeRequests()
//	    		.antMatchers(HttpMethod.GET, "/api/user/**").hasAnyAuthority("ROLE_ADMIN","ROLE_USER")
//	    		.antMatchers(HttpMethod.POST, "/api/user/**").hasAnyAuthority("ROLE_ADMIN","ROLE_USER")
//				.antMatchers(HttpMethod.PUT, "/api/user/**").hasAnyAuthority("ROLE_ADMIN","ROLE_USER")
	    		.anyRequest().permitAll();

	    	
//	    httpSecurity.addFilterBefore(requestFilter,UsernamePasswordAuthenticationFilter.class);
	}
	
	@Override
	  public void configure(WebSecurity web) throws Exception {
		  web
		  .ignoring()
		  .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
	  }
}