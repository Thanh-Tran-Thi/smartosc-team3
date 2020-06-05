package com.smartosc.training.controller;

import com.smartosc.training.apis.UserApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

/**
 * authentication
 *
 * @author Hieupv
 * @created_at 04/06/2020 - 3:56 PM
 * @created_by Hieupv
 * @since 04/06/2020
 */
@WebMvcTest(controllers = UserApi.class)
@ActiveProfiles("test")
public class SecurityControllerTest {
    @Autowired
    private MockMvc mockMvc;
}
