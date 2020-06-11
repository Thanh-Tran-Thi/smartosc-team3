package com.smartosc.training.controllers;

import com.smartosc.training.dtos.JwtRequest;
import com.smartosc.training.services.impls.AuthenticateServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

/**
 * authentication
 *
 * @author Hieupv
 * @created_at 05/06/2020 - 9:40 AM
 * @created_by Hieupv
 * @since 05/06/2020
 */
@RestController
@RequestMapping(value = "/api/authenticate")
public class AuthenticateController {
    @Autowired
    private AuthenticateServiceImpl userService;
    
    @PostMapping
    public String createNewUser(@RequestBody JwtRequest model) throws MethodArgumentNotValidException {
        return userService.getToken(model);
    }


}
