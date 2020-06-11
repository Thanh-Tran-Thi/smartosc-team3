package com.smartosc.training.services.impls;

import com.smartosc.training.dtos.APIResponse;
import com.smartosc.training.dtos.UserDTO;
import com.smartosc.training.dtos.UserRequest;
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
public class UserServiceImpl {
    @Value("${services.hostNameAuthentication}")
    private String hostName;
    @Value("${services.userAPI}")
    private String apiName;

    @Autowired
    private RestServiceImpl restemplateService;

    public List<UserDTO> findAllUser(String token){
        String url = hostName.concat(apiName);
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        header.setBearerAuth(token.substring(7));
        return  restemplateService.getSomething(url, HttpMethod.GET, header, null, new ParameterizedTypeReference<APIResponse<List<UserDTO>>>() {});
    }
    public UserDTO createNewUser(UserRequest model, String token){
        String url = hostName + apiName;
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        header.setBearerAuth(token.substring(7));
        return  restemplateService.getSomething(url, HttpMethod.POST, header, model, new ParameterizedTypeReference<APIResponse<UserDTO>>() {});
    }
    public UserDTO updateUser(UserRequest model , String token){
        String url = hostName + apiName;
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        header.setBearerAuth(token.substring(7));
        return restemplateService.getSomething(url, HttpMethod.PUT, header, model, new ParameterizedTypeReference<APIResponse<UserDTO>>() {});
    }
    public UserDTO findUserById(Long id, String token){
        String url = hostName.concat(apiName).concat("/"+id);
        HttpHeaders header = new HttpHeaders();
        header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        header.setContentType(MediaType.APPLICATION_JSON);
        header.setBearerAuth(token.substring(7));
        return  restemplateService.getSomething(url, HttpMethod.GET, header, null, new ParameterizedTypeReference<APIResponse<UserDTO>>() {});
    }
}
