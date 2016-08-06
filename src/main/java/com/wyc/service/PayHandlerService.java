package com.wyc.service;

import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.domain.SystemPayHandler;
import com.wyc.repositories.PayHandlerRepository;

@Service
public class PayHandlerService {
    @Autowired
    private PayHandlerRepository payHandlerRepository;

    public Iterable<com.wyc.domain.SystemPayHandler> findAll(Iterable<String> ids) {
        return payHandlerRepository.findAll(ids);
    }

    public Iterable<SystemPayHandler> findAll() {
        return payHandlerRepository.findAll();
    }

    public SystemPayHandler add(SystemPayHandler systemPayHandler) {
        systemPayHandler.setId(UUID.randomUUID().toString());
        systemPayHandler.setUpdateAt(new DateTime());
        systemPayHandler.setCreateAt(new DateTime());
        return payHandlerRepository.save(systemPayHandler);
        
    }

    public SystemPayHandler findOne(String id) {
        return payHandlerRepository.findOne(id);
    }

    public SystemPayHandler save(SystemPayHandler systemPayHandler) {
        systemPayHandler.setUpdateAt(new DateTime());
        return payHandlerRepository.save(systemPayHandler);
        
    }
}
