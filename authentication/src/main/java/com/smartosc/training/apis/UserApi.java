package com.smartosc.training.apis;

import com.smartosc.training.dtos.APIResponse;
import com.smartosc.training.dtos.UserDTO;
import com.smartosc.training.dtos.request.UserRequest;
import com.smartosc.training.services.UserService;
import javassist.NotFoundException;
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
@RequestMapping(value = "/api/user")
public class UserApi {
    @Autowired
    private UserService userService;


    @GetMapping("/{id}")
    public ResponseEntity<Object> findUserById(@PathVariable("id") Long id) throws NotFoundException {
        UserDTO userDTO = new UserDTO();
        userDTO = userService.findUserById(id);
        return new ResponseEntity<>(new APIResponse<UserDTO>(HttpStatus.OK.value(), "success", userDTO), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> findAllUser() throws NotFoundException {
        List<UserDTO> userList = new ArrayList<>();
        userList = userService.findAllUser();
        return new ResponseEntity<>(new APIResponse<List<UserDTO>>(HttpStatus.OK.value(), "success", userList), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createNewUser(@RequestBody @Valid UserRequest model) throws MethodArgumentNotValidException {
        return new ResponseEntity<>(new APIResponse<>(HttpStatus.OK.value(), "success", userService.createNewUser(model)), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody @Valid UserRequest model) throws NotFoundException {
        return new ResponseEntity<>(new APIResponse<>(HttpStatus.OK.value(), "success", userService.updateUser(model)), HttpStatus.OK);
    }
}
