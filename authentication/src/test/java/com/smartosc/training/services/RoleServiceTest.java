package com.smartosc.training.services;

import com.smartosc.training.dtos.RoleDTO;
import com.smartosc.training.dtos.UserDTO;
import com.smartosc.training.entities.Role;
import com.smartosc.training.repositories.RoleRepository;
import com.smartosc.training.services.impls.RoleServiceImpl;
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
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

/**
 * authentication
 *
 * @author Hieupv
 * @created_at 04/06/2020 - 3:55 PM
 * @created_by Hieupv
 * @since 04/06/2020
 */
@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private RoleService roleService= new RoleServiceImpl();

    private Role role;
    private RoleDTO roleDTO;
    private List<RoleDTO> roleDTOList;
    private List<Role> roleList;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        role = new Role("ROLE_USER",null);
        roleDTO = new RoleDTO("ROLE_USER");

        roleDTOList = new ArrayList<>();
        roleDTOList.add(roleDTO);

        roleList = new ArrayList<>();
        roleList.add(role);
    }



    //find by name
    @Test
    public void findByNameSuccess() {
        lenient().when(roleRepository.findByName("ROLE_USER")).thenReturn(role);
        lenient().when(modelMapper.map(any(), any())).thenReturn(roleDTO);
        RoleDTO result = roleService.findByName("ROLE_USER");
        Assertions.assertEquals(result,roleDTO);
    }



    //find by ByUsersUserName
    @Test
    public void findByUsersUserNameSuccess() {
        lenient().when(roleRepository.findByUsersUserName("ROLE_USER")).thenReturn(roleList);
        lenient().when(modelMapper.map(any(), any())).thenReturn(roleDTO);
        List<RoleDTO> result = roleService.findByUsersUserName("ROLE_USER");
        Assertions.assertEquals(result,roleDTOList);
    }


}
