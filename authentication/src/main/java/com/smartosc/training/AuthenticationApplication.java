package com.smartosc.training;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 * authentication
 *
 * @author thanhttt
 * @created_at 03/06/2020 - 4:04 PM
 */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class AuthenticationApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthenticationApplication.class, args);
    }
}