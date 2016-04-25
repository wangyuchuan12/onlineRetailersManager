package com.wyc.service;

import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.domain.Business;
import com.wyc.repositories.BusinessRepository;

@Service
public class BusinessService {
    @Autowired
    private BusinessRepository businessRepository;

    public Business findByAdminId(String adminId) {
        return businessRepository.findByAdminId(adminId);
    }

    public Business save(Business business) {
        business.setUpdateAt(new DateTime());
        return businessRepository.save(business);
        
    }

    public Business add(Business business) {
        business.setUpdateAt(new DateTime());
        business.setCreateAt(new DateTime());
        business.setId(UUID.randomUUID().toString());
        return businessRepository.save(business);
        
    }
}
