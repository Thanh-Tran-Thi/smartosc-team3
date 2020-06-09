package com.smartosc.training.restTemplate.impl;

import com.smartosc.training.dto.request.JwtRequest;
import com.smartosc.training.entities.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import springfox.documentation.spring.web.json.Json;

import java.util.Collections;
import java.util.List;

/**
 * products
 *
 * @author thanhttt
 * @created_at 09/06/2020 - 3:14 PM
 */
@Service
public class AuthenticationTemplate {
    @Value("${services.hotsName}")
    private String hostName;
    @Value("${services.token}")
    private String token;
    @Autowired
    private RestTemplateServiceImpl restemplateService;

    public String getToken(JwtRequest jwtRequest){
        String url = hostName.concat(token);
        HttpHeaders header = new HttpHeaders();
        header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        header.setContentType(MediaType.APPLICATION_JSON);
        //header.setBearerAuth(SecurityUtil.getJWTToken());
        return  restemplateService.getToken(url, HttpMethod.POST, header, jwtRequest);
    }
}
