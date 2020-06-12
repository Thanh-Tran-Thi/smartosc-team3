package com.smartosc.training.services.impls;

import com.smartosc.training.dtos.UserDTO;
import com.smartosc.training.dtos.request.UserRequest;
import com.smartosc.training.entities.Role;
import com.smartosc.training.entities.User;
import com.smartosc.training.repositories.RoleRepository;
import com.smartosc.training.repositories.UserRepository;
import com.smartosc.training.security.utils.EncrytedPasswordUtil;
import com.smartosc.training.services.UserService;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * authentication
 *
 * @author Hieupv
 * @created_at 04/06/2020 - 2:19 PM
 * @created_by Hieupv
 * @since 04/06/2020
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDTO findUserByUserName(String name) throws NotFoundException {
        Optional<User> userEntity = userRepository.findByUserName(name);
        if (userEntity.isPresent()) {
            UserDTO dto = new UserDTO();
            dto = modelMapper.map(userEntity.get(), UserDTO.class);
            return dto;
        } else {
            throw new NotFoundException("UnAuthorized");
        }
    }

    @Override
    public List<UserDTO> findAllUser() throws NotFoundException {

        List<UserDTO> results = new ArrayList<>();
        List<User> entities = userRepository.findAll();
        if (entities != null || !entities.isEmpty()) {
            for (User item : entities) {
                UserDTO newDTO = modelMapper.map(item, UserDTO.class);
                results.add(newDTO);
            }
            return results;
        } else {
            throw new NotFoundException("không có dữ liệu");
        }


    }

    @Override
    public UserDTO createNewUser(UserRequest model) {
        if (userRepository.findByUserName(model.getUsername()).isPresent()) {
            throw new DuplicateKeyException("Người dùng đã tồn tại");
        }
        model.setPassword(EncrytedPasswordUtil.encrytePassword(model.getPassword()));
        List<Role> roles = new ArrayList<>();
        model.setStatus(1);
        User newUser = modelMapper.map(model, User.class);
        Role usesRole = roleRepository.findByName("ROLE_USER");

        roles.add(usesRole);
        newUser.setRoles(roles);

        newUser = userRepository.save(newUser);
        return modelMapper.map(newUser, UserDTO.class);

    }

    @Override
    public UserDTO updateUser(UserRequest model) throws NotFoundException {
        Optional<User> oldUser = userRepository.findById(model.getId());
        if (oldUser.isPresent()) {
            if (String.valueOf(model.getStatus()) == null) {
                model.setStatus(0);
            }
            model.setPassword(EncrytedPasswordUtil.encrytePassword(model.getPassword()));
            User user = oldUser.get();
            modelMapper.map(model, user);
            User newUser = userRepository.save(user);
            return modelMapper.map(newUser, UserDTO.class);
        } else {
            throw new NotFoundException("KHông tìm thấy dữ liệu");
        }
    }


    @Override
    public UserDTO findUserById(Long id) throws NotFoundException {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            return modelMapper.map(userOptional.get(), UserDTO.class);
        } else {
            throw new NotFoundException("KHông tìm thấy dữ liệu");
        }
    }
}
