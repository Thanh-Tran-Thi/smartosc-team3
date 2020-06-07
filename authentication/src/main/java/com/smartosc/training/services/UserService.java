package com.smartosc.training.services;

import com.smartosc.training.dtos.UserDTO;
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
    List<UserDTO> findAllUser();
    UserDTO createNewUser(UserDTO model);
    UserDTO updateUser(UserDTO model);
    UserDTO findUserById(Long id);
}
