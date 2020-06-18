package com.smartosc.training.services;

import com.smartosc.training.dtos.RoleDTO;
import com.smartosc.training.dtos.UserDTO;
import com.smartosc.training.entities.User;
import com.smartosc.training.services.impls.JwtUserDetailServiceImpl;
import javassist.NotFoundException;
import org.hibernate.service.spi.InjectService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
public class JwtUserDetailServiceTest {

    @Mock
    private RoleService roleService;

    @Mock
    private UserService userService;

    @InjectMocks
    JwtUserDetailServiceImpl jwtUserDetailService;

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private UserDTO userDTO;
    private List<RoleDTO> roleDTOList;
    private RoleDTO roleDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userDTO = new UserDTO("123", "123", "123", "123");
        userDTO.setStatus(1);

        roleDTO = new RoleDTO("ROLE_USER");
        roleDTOList = new ArrayList<>();
        roleDTOList.add(roleDTO);
    }

    @Test
    public void loadUserByUsernameSuccess() throws NotFoundException {
        LOGGER.info("fake data for function findUserByUserName");
        lenient().when(userService.findUserByUserName("admin")).thenReturn(userDTO);
        LOGGER.info("fake data for function findByUsersUserName");
        lenient().when(roleService.findByUsersUserName("admin")).thenReturn(roleDTOList);

        UserDetails userDetails = jwtUserDetailService.loadUserByUsername("admin");
        Assertions.assertEquals(userDetails.getUsername(),userDTO.getUsername());
    }

    @Test
    public void loadUserByUsernameFailsByStatus() throws NotFoundException {
        userDTO.setStatus(0);
        LOGGER.info("fake data for function findUserByUserName");
        lenient().when(userService.findUserByUserName("admin")).thenReturn(userDTO);
        Assertions.assertThrows(UsernameNotFoundException.class,()->{
            jwtUserDetailService.loadUserByUsername("admin");
        });
    }

    @Test
    public void loadUserByUsernameFailsByNullData() throws NotFoundException {
        LOGGER.info("fake data for function findUserByUserName");
        lenient().when(userService.findUserByUserName("admin")).thenThrow(UsernameNotFoundException.class);
        Assertions.assertThrows(UsernameNotFoundException.class,()->{
            jwtUserDetailService.loadUserByUsername("admin");
        });
    }
}
