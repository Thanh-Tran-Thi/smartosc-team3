package com.smartosc.training.aop.services;

import com.smartosc.training.entities.ApiLog;
import com.smartosc.training.repositories.ApiLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApiLogService {

	@Autowired
	private ApiLogRepository apiLogRepository;
	
	public List<ApiLog> list() {
        return apiLogRepository.findAll();
    }
	
	public void saveApiLog(ApiLog apiLog) {
		apiLogRepository.save(apiLog);
	}
}
