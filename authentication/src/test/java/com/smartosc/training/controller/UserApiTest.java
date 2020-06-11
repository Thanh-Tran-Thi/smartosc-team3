package com.smartosc.training.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartosc.training.apis.UserApi;
import com.smartosc.training.dtos.UserDTO;
import com.smartosc.training.dtos.request.UserRequest;
import com.smartosc.training.repositories.RoleRepository;
import com.smartosc.training.repositories.UserRepository;
import com.smartosc.training.security.config.JWTAuthenticationEntryPoint;
import com.smartosc.training.security.config.RequestFilter;
import com.smartosc.training.security.utils.JWTUtils;
import com.smartosc.training.services.UserService;
import com.smartosc.training.services.impls.JwtUserDetailServiceImpl;
import com.smartosc.training.services.impls.UserServiceImpl;
import javassist.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.sql.DataSource;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

/**
 * smartosc-team3
 *
 * @author Hieupv
 * @created_at 10/06/2020 - 9:23 AM
 * @created_by Hieupv
 * @since 10/06/2020
 */
@SpringBootTest
public class UserApiTest {
    private MockMvc mockMvc;

    @Mock
    private UserService userService;
    @Mock
    private JwtUserDetailServiceImpl userDetailsService;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JWTUtils jwtTokenUtil;
    @Mock
    DataSource dataSource;
    @Mock
    private JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Mock
    private RequestFilter requestFilter;

    @InjectMocks
    private UserApi userApi;

    private UserDTO userDTO;
    private UserRequest userRequest;
    private List<UserDTO> userDTOList;
    private ObjectMapper objectMapper;
    private String url;

    @BeforeEach
    public void setUp() {
        url = "/api/user";
        userDTO = new UserDTO("admin","123","admin","abc@gmail.com");
        userDTOList = new ArrayList<>();
        userDTOList.add(userDTO);

        userRequest = new UserRequest("admin","123","123", "admin", "abc@gmail.com");

        this.mockMvc = MockMvcBuilders.standaloneSetup(userApi)
                .build();
        objectMapper = new ObjectMapper();
    }

    //findbyid
    @Test
    public void findUserById() throws Exception {
        Long id = 1L;
        lenient().when(userService.findUserById(id)).thenReturn(userDTO);
        this.mockMvc.perform(get(url+"/{id}",id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.username",is(userDTO.getUsername())));

    }

    //findAllUser
    @Test
    public void findAllUser() throws Exception {
        lenient().when(userService.findAllUser()).thenReturn(userDTOList);
        this.mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.size()",is(userDTOList.size())));
    }

    //createNewUser
    @Test
    public void createNewUser() throws Exception {
        lenient().when(userService.createNewUser(any())).thenReturn(userDTO);
        this.mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(userRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.username",is(userDTO.getUsername())));

    }

    //updateUser
    @Test
    public void updateUser() throws Exception {
        lenient().when(userService.updateUser(any())).thenReturn(userDTO);
        this.mockMvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(userRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.username",is(userDTO.getUsername())));

    }
}
