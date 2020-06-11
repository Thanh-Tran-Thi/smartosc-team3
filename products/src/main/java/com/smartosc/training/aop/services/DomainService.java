package com.smartosc.training.aop.services;

import com.smartosc.training.dto.ProductDTO;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class DomainService {
	public Object getDomainObjectById(Long id) throws Exception
    {
        try {
            Thread.sleep(new Random().nextInt(2000));
        } catch (InterruptedException e) {
            //do some logging
        }
        if (id == 0) throw new NullPointerException("Null ty");
        return id;
    }
}
