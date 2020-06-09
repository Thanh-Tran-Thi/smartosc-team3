package com.smartosc.training.services;

import com.smartosc.training.dtos.UserDTO;
import com.smartosc.training.dtos.request.UserRequest;
import javassist.NotFoundException;

import java.util.List;

/**
 * authentication
 *
 * @author Hieupv
 * @created_at 04/06/2020 - 2:19 PM
 * @created_by Hieupv
 * @since 04/06/2020
 */

public interface UserService {
    UserDTO findUserByUserName(String name) throws NotFoundException;
    List<UserDTO> findAllUser() throws NotFoundException;
    UserDTO createNewUser(UserRequest model);
    UserDTO updateUser(UserRequest model) throws NotFoundException;
    UserDTO findUserById(Long id) throws NotFoundException;
}
