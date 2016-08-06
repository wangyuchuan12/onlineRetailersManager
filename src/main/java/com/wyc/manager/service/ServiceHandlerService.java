package com.wyc.manager.service;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.manager.domain.ServiceHandler;
import com.wyc.manager.repositories.ServiceHandlerRepository;

@Service
public class ServiceHandlerService {
    @Autowired
    private ServiceHandlerRepository serviceHandlerRepository;

    public Iterable<ServiceHandler> findAll() {
        return serviceHandlerRepository.findAll();
    }

    public ServiceHandler save(ServiceHandler serviceHandler) {
        serviceHandler.setUpdateAt(new DateTime());
        return serviceHandlerRepository.save(serviceHandler);
        
    }
}
