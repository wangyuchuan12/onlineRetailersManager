package com.wyc.service;

import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.domain.SystemGoodType;
import com.wyc.repositories.GoodTypeRepository;

@Service
public class GoodTypeService {
    @Autowired
    private GoodTypeRepository goodTypeRepository;

    public SystemGoodType findOne(String id) {
        return goodTypeRepository.findOne(id);
    }

    public Iterable<SystemGoodType> findAll() {
        return goodTypeRepository.findAll();
    }

    public List<SystemGoodType> findAllByIsDefault(boolean isDefault) {
        return goodTypeRepository.findAllByIsDefault(isDefault);
    }

    public SystemGoodType add(SystemGoodType goodType) {
        goodType.setId(UUID.randomUUID().toString());
        goodType.setUpdateAt(new DateTime());
        goodType.setCreateAt(new DateTime());
        
        return goodTypeRepository.save(goodType);
    }

    public SystemGoodType save(SystemGoodType goodType) {
        goodType.setUpdateAt(new DateTime());
        return goodTypeRepository.save(goodType);
    }
}
