package com.wyc.service;

import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.domain.SystemPayActivity;
import com.wyc.repositories.PayActivityRepository;

@Service
public class PayActivityService {
    @Autowired
    private PayActivityRepository payActivityRepository;

    public List<SystemPayActivity> findAllByGoodIdAndPayType(String goodId,
            int groupType) {
        return payActivityRepository.findAllByGoodIdAndPayType(goodId,groupType);
    }

    public Iterable<SystemPayActivity> findAllByAdminId(String adminId) {
        return payActivityRepository.findAllByAdminId(adminId);
    }

    public Iterable<SystemPayActivity> findAll() {
        return payActivityRepository.findAll();
    }

    public SystemPayActivity add(SystemPayActivity systemPayActivity) {
        systemPayActivity.setCreateAt(new DateTime());
        systemPayActivity.setUpdateAt(new DateTime());
        systemPayActivity.setId(UUID.randomUUID().toString());
        return payActivityRepository.save(systemPayActivity);
    }

    public SystemPayActivity findOne(String activityId) {
        return payActivityRepository.findOne(activityId);
    }

    public void delete(SystemPayActivity systemPayActivity) {
        payActivityRepository.delete(systemPayActivity);
        
    }
}
