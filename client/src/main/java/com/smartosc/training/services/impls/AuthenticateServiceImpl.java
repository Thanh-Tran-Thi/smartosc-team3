package com.smartosc.training.services.impls;

import com.smartosc.training.dtos.JwtRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.Collections;

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
    @Value("${services.hostName}")
    private String hostName;
    @Value("${services.token}")
    private String apiName;

    @Autowired
    private RestServiceImpl restemplateService;

    public String getToken(JwtRequest jwtRequest){
        String url = hostName.concat(apiName);
        HttpHeaders header = new HttpHeaders();
        header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        header.setContentType(MediaType.APPLICATION_JSON);
        //header.setBearerAuth(SecurityUtil.getJWTToken());
        return  restemplateService.getToken(url, HttpMethod.POST, header, jwtRequest);
    }
}
