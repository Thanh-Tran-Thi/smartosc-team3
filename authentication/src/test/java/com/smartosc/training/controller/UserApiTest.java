package com.smartosc.training.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartosc.training.apis.UserApi;
import com.smartosc.training.dtos.UserDTO;
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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.sql.DataSource;

import static org.mockito.Mockito.lenient;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * smartosc-team3
 *
 * @author Hieupv
 * @created_at 10/06/2020 - 9:23 AM
 * @created_by Hieupv
 * @since 10/06/2020
 */
@WebMvcTest(controllers = UserApi.class)
@ActiveProfiles("testUserController")
public class UserApiTest {
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
    @MockBean
    private JwtUserDetailServiceImpl userDetailsService;
    @MockBean
    private AuthenticationManager authenticationManager;
    @MockBean
    private JWTUtils jwtTokenUtil;
    @MockBean
    DataSource dataSource;
    @MockBean
    private JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @MockBean
    private RequestFilter requestFilter;
    @MockBean
    private RoleRepository roleRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private ModelMapper modelMapper;

    @InjectMocks
    private UserApi userApi;

    private UserDTO userDTO;

    @BeforeEach
    public void setUp() {
        userDTO = new UserDTO("admin","123","admin","abc@gmail.com");

        this.mockMvc = MockMvcBuilders.standaloneSetup(userApi)
                .build();
    }

    //findbyid
    @Test
    public void findUserById() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Long id = 1L;
        lenient().when(userService.findUserById(id)).thenReturn(userDTO);
        this.mockMvc.perform(get("/api/user/{id}",id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
