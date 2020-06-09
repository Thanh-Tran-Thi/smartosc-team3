package com.smartosc.training.restTemplate;

import com.smartosc.training.entities.ApiResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

public interface RestTemplateService {
	public <T> T getSomething(String url, HttpMethod method, HttpHeaders headers, Object body, ParameterizedTypeReference<ApiResponse<T>> reponseType);
	public String getToken(String url, HttpMethod method, HttpHeaders headers, Object body);
}