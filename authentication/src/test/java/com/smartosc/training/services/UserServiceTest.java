package com.smartosc.training.services;

import com.smartosc.training.dtos.UserDTO;
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
    private UserDTO userDTO;

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

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
    }

    @Test
    public void findByUserSuccess() throws NotFoundException {
        LOGGER.info("fake data for function findByUserName");
        lenient().when(userRepository.findByUserName("admin")).thenReturn(user);
        LOGGER.info("fake data for modelMaper");
        lenient().when(modelMapper.map(user.get(), UserDTO.class)).thenReturn(userDTO);

        UserDTO userResult = userService.findUserByUserName("admin");
        Assertions.assertEquals(userResult,userDTO);
    }

    @Test
    public void findByUserFail(){
        lenient().when(userRepository.findByUserName("user")).thenReturn(null);
        Assertions.assertThrows(NullPointerException.class,()->{
                userService.findUserByUserName("user");
        });
    }
}
