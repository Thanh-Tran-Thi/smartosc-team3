package com.smartosc.training.security.config;

import com.smartosc.training.services.impls.JwtUserDetailServiceImpl;
import org.modelmapper.ModelMapper;
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
import org.springframework.security.core.userdetails.UserDetailsService;
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
	
	@Autowired
	DataSource dataSource;
	
	@Autowired
	private JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	@Autowired
	private JwtUserDetailServiceImpl userDetailsService;
	
	@Autowired
	private RequestFilter requestFilter;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
		return restTemplate;

	}
	
	@Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();	
	}
	
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		
		httpSecurity.csrf().disable()
			.authorizeRequests()
	    		.antMatchers(HttpMethod.GET, "/api/user/**").hasAnyAuthority("ROLE_ADMIN","ROLE_USER")
	    		.antMatchers(HttpMethod.POST, "/api/user/**").hasAnyAuthority("ROLE_ADMIN","ROLE_USER")
				.antMatchers(HttpMethod.PUT, "/api/user/**").hasAnyAuthority("ROLE_ADMIN","ROLE_USER")
	    		.anyRequest().permitAll()
	    		.and()
	    	.formLogin()
	    		.loginPage("/login")
	    		.loginProcessingUrl("/login-action").permitAll()
	    		.usernameParameter("username")
	    		.passwordParameter("password")
	    		.failureUrl("/login?error=true")
	    		.defaultSuccessUrl("/index")
	    		.and()
	    	.logout()
	    		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
	    		.logoutSuccessUrl("/login")
	    		.and()
	    	.exceptionHandling()
	    		.authenticationEntryPoint(jwtAuthenticationEntryPoint);
	    	
	    httpSecurity.addFilterBefore(requestFilter,UsernamePasswordAuthenticationFilter.class);
	}
	
	@Override
	  public void configure(WebSecurity web) throws Exception {
		  web
		  .ignoring()
		  .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
	  }
}