package com.smartosc.training.services.impls;

import com.smartosc.training.dtos.APIResponse;
import com.smartosc.training.dtos.CategoryDTO;
import com.smartosc.training.dtos.ProductDTO;
import com.smartosc.training.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * client
 *
 * @author thanhttt
 * @created_at 11/06/2020 - 10:19 AM
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Value("${services.hostName}")
    private String preFixUrl;

    @Value("${services.categoryAPI}")
    private String categoryApi;

    @Autowired
    private RestServiceImpl restService;

    @Autowired
    private AuthenticateServiceImpl authenticateService;

    @Override
    public List<CategoryDTO> listAll() {
        String url = preFixUrl.concat(categoryApi);
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        return restService.getSomething(url, HttpMethod.GET, header, null, new ParameterizedTypeReference<APIResponse<List<CategoryDTO>>>() {});    }

    @Override
    public CategoryDTO getCategoryById(Long id) {
        String url = preFixUrl.concat(categoryApi).concat("/" + id).concat("/products");
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        return restService.getSomething(url, HttpMethod.GET, header, null, new ParameterizedTypeReference<APIResponse<CategoryDTO>>() {});    }

    @Override
    public CategoryDTO saveCategory(CategoryDTO category, String token) {
        String url = preFixUrl.concat(categoryApi);
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        String rawToken = token.substring(7);
        header.setBearerAuth(rawToken);
        return restService.getSomething(url, HttpMethod.POST, header, category, new ParameterizedTypeReference<APIResponse<CategoryDTO>>() {});    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO category, String token) {
        String url = preFixUrl.concat(categoryApi);
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        String rawToken = token.substring(7);
        header.setBearerAuth(rawToken);
        return restService.getSomething(url, HttpMethod.PUT, header, category, new ParameterizedTypeReference<APIResponse<CategoryDTO>>() {});    }
}
