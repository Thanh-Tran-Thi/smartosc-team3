package com.smartosc.training.services.impls;

import com.smartosc.training.dtos.UserDTO;
import com.smartosc.training.entities.Role;
import com.smartosc.training.entities.User;
import com.smartosc.training.repositories.RoleRepository;
import com.smartosc.training.repositories.UserRepository;
import com.smartosc.training.security.utils.EncrytedPasswordUtil;
import com.smartosc.training.services.UserService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
        }else{
            throw new NotFoundException("KHông tìm thấy dữ liệu");
        }
    }

    @Override
    public List<UserDTO> findAllUser() {
        List<UserDTO> results = new ArrayList<>();
        List<User> entities = userRepository.findAll();
        for (User item : entities) {
            UserDTO newDTO = modelMapper.map(item, UserDTO.class);
            results.add(newDTO);
        }
        return results;
    }

    @Override
    public UserDTO createNewUser(UserDTO model) {
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
    public UserDTO updateUser(UserDTO model) {
        if (String.valueOf(model.getStatus()) == null) {
            model.setStatus(0);
        }
        User oldUser = userRepository.findById(model.getId()).get();
        oldUser.setStatus(model.getStatus());
        User newUser = userRepository.save(oldUser);
        return modelMapper.map(newUser, UserDTO.class);

    }


    @Override
    public UserDTO findUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()){
            return modelMapper.map(userOptional.get(), UserDTO.class);
        }
        return null;
    }
}
