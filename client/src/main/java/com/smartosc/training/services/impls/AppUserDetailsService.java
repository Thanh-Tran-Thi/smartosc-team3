package com.smartosc.training.services.impls;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.smartosc.training.security.utils.EncrytedPasswordUtil;
import com.smartosc.training.security.utils.JWTUtils;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.smartosc.training.security.AppUserDetails;

@Service
public class AppUserDetailsService implements UserDetailsService {

	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private HttpServletRequest httpRequest;


	
	@Override
	public UserDetails loadUserByUsername(String token) throws UsernameNotFoundException{
		try {
			Claims claims = new JWTUtils().getAllClaimsFromToken(token);
			LinkedHashMap<String, String> linkedHashMap = claims.get("user", LinkedHashMap.class);
			String username = linkedHashMap.get("username");
			String password = linkedHashMap.get("password");
			List<String> authoritie = Collections.singletonList(linkedHashMap.get("authorities"));
			List<GrantedAuthority> authorities = new ArrayList<>();

//			for(RoleDTO item : roles){
//				authorities.add(new SimpleGrantedAuthority(item.getName()));
//			}
//
			AppUserDetails appUserDetails = new AppUserDetails(username, password, true, true, true, true, authorities);
//			appUserDetails.setJwtToken(token);
			return appUserDetails;
		}catch(Exception e) {
			throw new UsernameNotFoundException("Username not found!", e);
		}
	}
}