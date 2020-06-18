package com.smartosc.training.security.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Slf4j
public class EncrytedPasswordUtil {
	 // Encryte Password with BCryptPasswordEncoder
    public static String encrytePassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

    public static void main(String[] args) {
        log.info(encrytePassword("123"));
    }
}
