package com.wyc.service;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    public Page<GoodGroup> findAllByAdminIdOrderByUpdateAtDesc(String adminId,int page,int size) {
        PageRequest pageRequest = new PageRequest(page-1, size);
        return goodGroupRepository.findAllByAdminIdOrderByUpdateAtDesc(adminId,pageRequest);
    }

    public Iterable<GoodGroup> findAllByIsDisused(int isDisused) {
        return goodGroupRepository.findAllByIsDisused(isDisused);
    }

	public Page<GoodGroup> findAllByAdminIdAndResultOrderByUpdateAtDesc(String adminId, int status, int page, int size) {
		PageRequest pageRequest = new PageRequest(page-1, size);
		return goodGroupRepository.findAllByAdminIdAndResultOrderByUpdateAtDesc(adminId,status,pageRequest);
	}
}
