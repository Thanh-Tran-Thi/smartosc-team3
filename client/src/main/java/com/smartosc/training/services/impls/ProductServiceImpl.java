package com.smartosc.training.services.impls;

import com.smartosc.training.dtos.APIResponse;
import com.smartosc.training.dtos.ProductDTO;
import com.smartosc.training.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

/**
 * client
 *
 * @author thanhttt
 * @created_at 10/06/2020 - 1:44 PM
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Value("${services.hostName}")
    private String preFixUrl;

    @Value("${services.productAPI}")
    private String productApi;

    @Autowired
    private RestServiceImpl restService;

    @Autowired
    private AuthenticateServiceImpl authenticateService;

    @Override
    public List<ProductDTO> getAll() {
        String url = preFixUrl.concat(productApi);
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        return restService.getSomething(url, HttpMethod.GET, header, null, new ParameterizedTypeReference<APIResponse<List<ProductDTO>>>() {});
    }

    @Override
    public ProductDTO getById(Long id) {
        String url = preFixUrl.concat(productApi).concat("/" + id);
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        return restService.getSomething(url, HttpMethod.GET, header, null, new ParameterizedTypeReference<APIResponse<ProductDTO>>() {});
    }

    @Override
    public ProductDTO save(ProductDTO product, String token) {
        String url = preFixUrl.concat(productApi);
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        String rawToken = token.substring(7);
        header.setBearerAuth(rawToken);
        return restService.getSomething(url, HttpMethod.POST, header, product, new ParameterizedTypeReference<APIResponse<ProductDTO>>() {});
    }

    @Override
    public ProductDTO update(ProductDTO product, String token) {
        String url = preFixUrl.concat(productApi);
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        String rawToken = token.substring(7);
        header.setBearerAuth(rawToken);
        return restService.getSomething(url, HttpMethod.PUT, header, product, new ParameterizedTypeReference<APIResponse<ProductDTO>>() {});
    }

    @Override
    public Boolean delete(Long id, String token) {
        String url = preFixUrl.concat(productApi).concat("/" + id);
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        String rawToken = token.substring(7);
        header.setBearerAuth(rawToken);
        return restService.getSomething(url, HttpMethod.DELETE, header, null, new ParameterizedTypeReference<APIResponse<Boolean>>() {});
    }
}
