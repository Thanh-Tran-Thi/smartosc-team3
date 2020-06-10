package com.smartosc.training.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartosc.training.apis.SecurityApi;
import com.smartosc.training.apis.UserApi;
import com.smartosc.training.dtos.UserDTO;
import com.smartosc.training.dtos.request.JwtRequest;
import com.smartosc.training.security.config.JWTAuthenticationEntryPoint;
import com.smartosc.training.security.config.RequestFilter;
import com.smartosc.training.security.utils.JWTUtils;
import com.smartosc.training.services.impls.JwtUserDetailServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.lenient;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * authentication
 *
 * @author Hieupv
 * @created_at 04/06/2020 - 3:56 PM
 * @created_by Hieupv
 * @since 04/06/2020
 */
@WebMvcTest(controllers = SecurityApi.class)
@ActiveProfiles("test")
public class SecurityApiTest {
    private MockMvc mockMvc;

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

    @InjectMocks
    private SecurityApi securityApi;

    private UserDetails userDetails;
    private List<GrantedAuthority> grantList;
    private JwtRequest jwtRequest;

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.grantList = new ArrayList<>();
        grantList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        this.userDetails = new User("admin", "1234", grantList);

        jwtRequest = new JwtRequest("admin", "1234");

        this.mockMvc = MockMvcBuilders.standaloneSetup(securityApi)
                .build();
    }

    @Test
    public void authentication() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        lenient().when(userDetailsService.loadUserByUsername("admin")).thenReturn(this.userDetails);
        MvcResult token = this.mockMvc.perform(post("/api/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(jwtRequest)))
                .andExpect(status().isOk())
                .andReturn();
        LOGGER.info("hahahahahaahaa"+token.getResponse().getContentAsString());
    }
    @Test
    public void authenticationFails() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        lenient().when(userDetailsService.loadUserByUsername("admin")).thenReturn(null);
        this.mockMvc.perform(post("/api/authenticate"))
                .andExpect(status().isBadRequest());



    }
}
