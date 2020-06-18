package com.smartosc.training.services.impls;


import com.smartosc.training.dtos.APIResponse;
import com.smartosc.training.exception.RestTempalteException;
import com.smartosc.training.services.RestTemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.SQLException;

@Service
public class RestServiceImpl implements RestTemplateService {
	private static final Logger LOOGER = LoggerFactory.getLogger(RestServiceImpl.class);
	@Autowired
	private RestTemplate restTemplate;
	@Override
	public <T> T getSomething(String url, HttpMethod method, HttpHeaders headers, Object body, ParameterizedTypeReference<APIResponse<T>> reponseType) {
		try {
			HttpEntity<Object> entity = new HttpEntity<>(body, headers);
			ResponseEntity<APIResponse<T>> res = restTemplate.exchange(url, method, entity, reponseType);
			if (res.getStatusCode().value() >= HttpStatus.OK.value() && res.getStatusCode().value() < HttpStatus.MULTIPLE_CHOICES.value()) {
				return res.getBody().getData();
			}
			LOOGER.error(res.getBody().getMessage());
			throw new RestTempalteException(res.getBody().getMessage());
		} catch (RestTempalteException e) {
			throw new RestTempalteException(e.getMessage().split("\"")[9]);
		}
	}

	
	public <T> APIResponse<T> exchangePaging(String url, HttpMethod method, HttpHeaders headers, Object body) {
		try {
			HttpEntity<Object> entity = new HttpEntity<>(body, headers);
			ParameterizedTypeReference<APIResponse<T>> reponseType = new ParameterizedTypeReference<APIResponse<T>>() {	};
			ResponseEntity<APIResponse<T>> res = restTemplate.exchange(url, method, entity, reponseType);
			if (res.getStatusCode().value() >= HttpStatus.OK.value() && res.getStatusCode().value() < HttpStatus.MULTIPLE_CHOICES.value()) {
				return res.getBody();
			}
			LOOGER.error(res.getBody().getMessage());
			throw new RestTempalteException(res.getBody().getMessage());
		} catch (Exception e) {
			throw new RestTempalteException(e.getMessage(), e);
		}
	}

	
	@Override
	public String getToken(String url, HttpMethod method, HttpHeaders headers, Object body) {
		try {
			HttpEntity<Object> entity = new HttpEntity<>(body, headers);
			ResponseEntity<String> res = restTemplate.exchange(url, method, entity, String.class);
			if (res.getStatusCode().value() >= HttpStatus.OK.value() && res.getStatusCode().value() < HttpStatus.MULTIPLE_CHOICES.value()) {
				return res.getBody();
			}
			LOOGER.error(res.getBody());
			throw new RestTempalteException(res.getBody());
		} catch (Exception e) {
			throw new RestTempalteException(e.getMessage().split("\"")[13]);
		}
	}

}
