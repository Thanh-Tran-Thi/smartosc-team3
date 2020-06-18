package com.smartosc.training.apis;

import com.smartosc.training.constants.SystemConstants;
import com.smartosc.training.dtos.APIResponse;
import com.smartosc.training.dtos.UserDTO;
import com.smartosc.training.dtos.request.UserRequest;
import com.smartosc.training.services.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

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
public class UserApi {
    @Autowired
    private UserService userService;


    @GetMapping("/{id}")
    public ResponseEntity<Object> findUserById(@PathVariable("id") Long id) throws NotFoundException {
        UserDTO userDTO = userService.findUserById(id);
        return new ResponseEntity<>(new APIResponse<UserDTO>(HttpStatus.OK.value(), SystemConstants.FIND_USER_BY_ID_SUCCESS, userDTO), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Object> findAllUser() throws NotFoundException {
        List<UserDTO> userList = userService.findAllUser();
        return new ResponseEntity<>(new APIResponse<List<UserDTO>>(HttpStatus.OK.value(), SystemConstants.FIND_ALL_USER_SUCCESS, userList), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> createNewUser(@RequestBody @Valid UserRequest model){
        UserDTO userDTO = userService.createNewUser(model);
        return new ResponseEntity<>(new APIResponse<UserDTO>(HttpStatus.OK.value(), SystemConstants.CREATE_USER_SUCCESS,userDTO), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Object> updateUser(@RequestBody @Valid UserRequest model) throws NotFoundException {
        return new ResponseEntity<>(new APIResponse<UserDTO>(HttpStatus.OK.value(), SystemConstants.UPDATE_USER_SUCCESS, userService.updateUser(model)), HttpStatus.OK);
    }
}
