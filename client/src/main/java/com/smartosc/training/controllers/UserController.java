package com.smartosc.training.controllers;

import com.smartosc.training.dtos.APIResponse;
import com.smartosc.training.dtos.UserDTO;
import com.smartosc.training.dtos.UserRequest;
import com.smartosc.training.services.impls.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * authentication
 *
 * @author Hieupv
 * @created_at 05/06/2020 - 9:40 AM
 * @created_by Hieupv
 * @since 05/06/2020
 */
@RestController
@RequestMapping(value = "/api/user")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/{id}")
    public ResponseEntity<?> findUserById(@PathVariable("id")Long id, @RequestHeader(value="Authorization") String token) {
        return new ResponseEntity<>(new APIResponse<>(HttpStatus.OK.value(),"success" ,userService.findUserById(id, token)), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> findAllUser( @RequestHeader(value="Authorization") String token) {
        return new ResponseEntity<>(new APIResponse<List<?>>(HttpStatus.OK.value(),"success" ,userService.findAllUser(token)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createNewUser(@RequestBody @Valid UserRequest model, @RequestHeader(value="Authorization") String token) throws MethodArgumentNotValidException {
        return new ResponseEntity<>(new APIResponse<>(HttpStatus.OK.value(),"success", userService.createNewUser(model, token)), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody @Valid UserRequest model, @RequestHeader(value="Authorization") String token) {
        return new ResponseEntity<>(new APIResponse<>(HttpStatus.OK.value(),"success", userService.updateUser(model, token)), HttpStatus.OK);
    }
}
