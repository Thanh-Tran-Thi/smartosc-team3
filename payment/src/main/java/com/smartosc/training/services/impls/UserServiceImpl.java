package com.smartosc.training.services.impls;

import com.smartosc.training.dtos.APIResponse;
import com.smartosc.training.dtos.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

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
public class UserServiceImpl {
    @Value("${services.hotsName}")
    private String hostName;
    @Value("${services.userAPI}")
    private String apiName;

    @Autowired
    private RestTemplateServiceImpl restemplateService;

    public UserDTO findUserByUserName(String name){
        String url = hostName.concat(apiName);
        HttpHeaders header = new HttpHeaders();
        //header.setBearerAuth(SecurityUtil.getJWTToken());
        return  restemplateService.getSomething(url, HttpMethod.GET, header, null, new ParameterizedTypeReference<APIResponse<UserDTO>>() {});
    }
    public List<UserDTO> findAllUser(){
        String url = hostName.concat(apiName);
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        //header.setBearerAuth(SecurityUtil.getJWTToken());
        return  restemplateService.getSomething(url, HttpMethod.GET, header, null, new ParameterizedTypeReference<APIResponse<List<UserDTO>>>() {});
    }
    public UserDTO createNewUser(UserDTO model){
        String url = hostName + apiName;
        HttpHeaders header = new HttpHeaders();
        //header.setBearerAuth(SecurityUtil.getJWTToken());
        return  restemplateService.getSomething(url, HttpMethod.GET, header, null, new ParameterizedTypeReference<APIResponse<UserDTO>>() {});
    }
    public UserDTO updateUser(UserDTO model){
        String url = hostName + apiName;
        HttpHeaders header = new HttpHeaders();
        //header.setBearerAuth(SecurityUtil.getJWTToken());
        return restemplateService.getSomething(url, HttpMethod.GET, header, null, new ParameterizedTypeReference<APIResponse<UserDTO>>() {});
    }
    public boolean findUserById(Long id){
        String url = hostName + apiName;
        HttpHeaders header = new HttpHeaders();
        //header.setBearerAuth(SecurityUtil.getJWTToken());
        return  restemplateService.getSomething(url, HttpMethod.GET, header, null, new ParameterizedTypeReference<APIResponse<Boolean>>() {});
    }
}
