package com.smartosc.training.apis;

import com.smartosc.training.dtos.APIResponse;
import com.smartosc.training.dtos.UserDTO;
import com.smartosc.training.services.impls.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
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
@RequestMapping(value = "/user")
public class UserApi {
    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/{id}")
    public ResponseEntity<?> findUserById(@PathVariable("id")Long id) {
        return new ResponseEntity<>(new APIResponse<>(HttpStatus.OK.value(),"success" ,userService.findUserById(id)), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> findAllUser() {
        return new ResponseEntity<>(new APIResponse<List<?>>(HttpStatus.OK.value(),"success" ,userService.findAllUser()), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createNewUser(@RequestBody @Valid UserDTO model) throws MethodArgumentNotValidException {
        return new ResponseEntity<>(new APIResponse<>(HttpStatus.OK.value(),"success", userService.createNewUser(model)), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody @Valid UserDTO model) {
        return new ResponseEntity<>(new APIResponse<>(HttpStatus.OK.value(),"success", userService.updateUser(model)), HttpStatus.OK);
    }
}
