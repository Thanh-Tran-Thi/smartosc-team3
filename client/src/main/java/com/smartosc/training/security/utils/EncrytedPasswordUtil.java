package com.smartosc.training.security.utils;

import io.jsonwebtoken.Claims;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

public class EncrytedPasswordUtil {
	 // Encryte Password with BCryptPasswordEncoder
    public static String encrytePassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

    public static void main(String[] args) {
        //System.out.println(encrytePassword("123"));

        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU5MTg2NDAxMywiaWF0IjoxNTkxODQ2MDEzLCJ1c2VyIjp7InBhc3N3b3JkIjoiJDJhJDEwJGtkZHJONnJQLmJ0SkF2cWlxNVMuZ2VtazF6ZG1BOTVWTU01VExYT2ZPeW90SVBTWktrYlhpIiwidXNlcm5hbWUiOiJhZG1pbiIsImF1dGhvcml0aWVzIjpbeyJhdXRob3JpdHkiOiJST0xFX0FETUlOIn0seyJhdXRob3JpdHkiOiJST0xFX1VTRVIifV0sImFjY291bnROb25FeHBpcmVkIjp0cnVlLCJhY2NvdW50Tm9uTG9ja2VkIjp0cnVlLCJjcmVkZW50aWFsc05vbkV4cGlyZWQiOnRydWUsImVuYWJsZWQiOnRydWV9fQ.0uygiPS0BJ_rxPnBrqf4FfJU4JDV9mL2yfHv4BTnfHxrJMYaJdExTv3R3cNhOIsPzDRNtvGJNVV_JhVd2yES1w";
        JWTUtils jwtTokenProvider = new JWTUtils();
        Claims claims = jwtTokenProvider.getAllClaimsFromToken(token);
        LinkedHashMap<String,List> linkedHashMap = claims.get("user", LinkedHashMap.class);
        System.out.println(linkedHashMap.get("password"));
        System.out.println(linkedHashMap.get("username"));
        List authorities= linkedHashMap.get("authorities");
        authorities.forEach(a ->{
            System.out.println(a);
        });
        System.out.println(authorities);

    }
}
