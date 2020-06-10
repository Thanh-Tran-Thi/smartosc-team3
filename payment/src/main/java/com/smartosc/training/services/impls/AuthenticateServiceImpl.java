package com.smartosc.training.services.impls;

import com.smartosc.training.dtos.APIResponse;
import com.smartosc.training.dtos.JwtRequest;
import com.smartosc.training.dtos.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * smartosc-team3
 *
 * @author Hieupv
 * @created_at 05/06/2020 - 4:55 PM
 * @created_by Hieupv
 * @since 05/06/2020
 */
@Service
public class AuthenticateServiceImpl {
    @Value("${services.hotsName}")
    private String hostName;
    @Value("${services.token}")
    private String apiName;

    @Autowired
    private RestTemplateServiceImpl restemplateService;

    public String getToken(JwtRequest jwtRequest){
        String url = hostName.concat(apiName);
        HttpHeaders header = new HttpHeaders();
        header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        header.setContentType(MediaType.APPLICATION_JSON);
        //header.setBearerAuth(SecurityUtil.getJWTToken());
        return  restemplateService.getToken(url, HttpMethod.POST, header, jwtRequest);
    }
}
