package com.smartosc.training.controllers;


import com.smartosc.training.dto.request.JwtRequest;
import com.smartosc.training.restTemplate.impl.AuthenticationTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * products
 *
 * @author thanhttt
 * @created_at 09/06/2020 - 3:34 PM
 */
@RestController
@RequestMapping(value = "/authenticate")
public class RestTemplateController {
    @Autowired
    private AuthenticationTemplate authenticationTemplate;

    @PostMapping
    public String getToken(@RequestBody JwtRequest jwtRequest) {
        return authenticationTemplate.getToken(jwtRequest);
    }
}
