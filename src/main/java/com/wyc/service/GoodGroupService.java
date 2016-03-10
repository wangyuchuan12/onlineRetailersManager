package com.wyc.service;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.domain.GoodGroup;
import com.wyc.repositories.GoodGroupRepository;

@Service
public class GoodGroupService {
    @Autowired
    private GoodGroupRepository goodGroupRepository;
    public GoodGroup add(GoodGroup goodGroup){
        goodGroup.setId(UUID.randomUUID().toString());
        goodGroup.setCreateAt(new DateTime());
        goodGroup.setUpdateAt(new DateTime());
        return goodGroupRepository.save(goodGroup);
    }
    
    public GoodGroup save(GoodGroup goodGroup){
    	goodGroup.setUpdateAt(new DateTime());
        return goodGroupRepository.save(goodGroup);
    }
    
    public GoodGroup findOne(String id){
        return goodGroupRepository.findOne(id);
    }

    public Iterable<GoodGroup> findAll(Iterable<String> groupIds) {
       return goodGroupRepository.findAll(groupIds);
        
    }
    
    public GoodGroup selectLastestGoodGroupByGroupHead(String groupHead){
        return goodGroupRepository.selectLastestGoodGroupByGroupHead(groupHead);
    }
}
