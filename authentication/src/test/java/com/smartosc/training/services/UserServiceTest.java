package com.smartosc.training.services;

import com.smartosc.training.dtos.UserDTO;
import com.smartosc.training.dtos.request.UserRequest;
import com.smartosc.training.entities.Role;
import com.smartosc.training.entities.User;
import com.smartosc.training.repositories.RoleRepository;
import com.smartosc.training.repositories.UserRepository;
import com.smartosc.training.services.impls.UserServiceImpl;
import javassist.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

/**
 * authentication
 *
 * @author Hieupv
 * @created_at 04/06/2020 - 3:57 PM
 * @created_by Hieupv
 * @since 04/06/2020
 */
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserService userService = new UserServiceImpl();

    private Optional<User> user;
    private List<User> userList;
    private UserDTO userDTO;
    private UserRequest userRequest;
    private Role role;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        this.user = Optional.of(new User());
        this.user.get().setEmail("abc@gmail.com");
        this.user.get().setPassword("12345");
        this.user.get().setUserName("admin");

        this.userDTO = new UserDTO();
        this.userDTO.setEmail("abc@gmail.com");
        this.userDTO.setPassword("12345");
        this.userDTO.setUsername("admin");

        this.userRequest = new UserRequest();
        this.userRequest.setEmail("abc@gmail.com");
        this.userRequest.setPassword("12345");
        this.userRequest.setUsername("admin");

        this.role = new Role("ROLE_USER",null);

        userList = new ArrayList<>();
        userList.add(user.get());
    }

    //function findUserByUserName
    @Test
    public void findUserByUserNameSuccess() throws NotFoundException {
        lenient().when(userRepository.findByUserName("admin")).thenReturn(user);
        lenient().when(modelMapper.map(user.get(), UserDTO.class)).thenReturn(userDTO);

        UserDTO userResult = userService.findUserByUserName("admin");
        Assertions.assertEquals(userResult,userDTO);
    }

    @Test
    public void findUserByUserNameFail(){
        lenient().when(userRepository.findByUserName("user")).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundException.class,()->{
                userService.findUserByUserName("user");
        });
    }

    //function findAllUser
    @Test
    public void findAllUserSuccess() throws NotFoundException {
        lenient().when(userRepository.findAll()).thenReturn(userList);
        lenient().when(modelMapper.map(user.get(), UserDTO.class)).thenReturn(userDTO);

        List<UserDTO> userDTOList = userService.findAllUser();
        Assertions.assertEquals(userDTOList.size(),userList.size());
    }

    @Test
    public void findAllUserFalse() throws NotFoundException {
        lenient().when(userRepository.findAll()).thenReturn(new ArrayList<>());

        Assertions.assertThrows(NotFoundException.class,()->{
            userService.findAllUser();
        });
    }

    //function createNewUser
    @Test
    public void createNewUserSuccess() throws NotFoundException {
        lenient().when(userRepository.findByUserName("admin")).thenReturn(Optional.empty());
        lenient().when(modelMapper.map(any(), any())).thenReturn(user.get()).thenReturn(userDTO);
        lenient().when(roleRepository.findByName("ROLE_USER")).thenReturn(role);

        lenient().when(userRepository.save(user.get())).thenReturn(user.get());

        UserDTO userResult = userService.createNewUser(userRequest);

        Assertions.assertEquals(userResult.getUsername(),userDTO.getUsername());
    }

    @Test
    public void createNewUserFalse() throws NotFoundException {
        lenient().when(userRepository.findByUserName("admin")).thenReturn(Optional.of(new User()));

        Assertions.assertThrows(DuplicateKeyException.class,()->{
            userService.createNewUser(userRequest);
        });
    }

    //function updateUser
    @Test
    public void updateUserSuccess() throws NotFoundException {
        lenient().when(userRepository.findById(user.get().getId())).thenReturn(user);
        lenient().when(modelMapper.map(any(), any())).thenReturn(userDTO).thenReturn(user.get());
        lenient().when(userRepository.save(user.get())).thenReturn(user.get());

        UserDTO userResult = userService.updateUser(userRequest);

        Assertions.assertEquals(userResult.getUsername(),userDTO.getUsername());
    }

    @Test
    public void updateUserFalse() throws NotFoundException {
        lenient().when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class,()->{
            userService.updateUser(userRequest);
        });
    }

    //function findUserById

    @Test
    public void findUserByIdSuccess() throws NotFoundException {
        lenient().when(userRepository.findById(1L)).thenReturn(user);
        lenient().when(modelMapper.map(any(), any())).thenReturn(userDTO);

        UserDTO userResult = userService.findUserById(1L);

        Assertions.assertEquals(userResult.getUsername(),userDTO.getUsername());
    }

    @Test
    public void findUserByIdFalse() throws NotFoundException {
        lenient().when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(new NotFoundException("KHông tìm thấy dữ liệu").getClass(),()->{
            userService.findUserById(1L);
        });
    }
}
