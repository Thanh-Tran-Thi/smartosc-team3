package com.smartosc.training.controller;

import com.smartosc.training.apis.UserApi;
import com.smartosc.training.dtos.UserDTO;
import com.smartosc.training.services.impls.JwtUserDetailServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.lenient;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * authentication
 *
 * @author Hieupv
 * @created_at 04/06/2020 - 3:56 PM
 * @created_by Hieupv
 * @since 04/06/2020
 */
@WebMvcTest(controllers = SecurityApiTest.class)
@ActiveProfiles("test")
public class SecurityApiTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtUserDetailServiceImpl userDetailsService;

    private UserDetails userDetails;
    private List<GrantedAuthority> grantList;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.grantList = new ArrayList<>();
        grantList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        this.userDetails = new User("admin", "1234", grantList);
    }
    @Test
    public void authentication(){
        lenient().when(userDetailsService.loadUserByUsername("admin")).thenReturn(this.userDetails);
        this.mockMvc.perform(get("/api/authenticate"))
                .andExpect(status().isOk());
                //.andExpect()
    }
}
