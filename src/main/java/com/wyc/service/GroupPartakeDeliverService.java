package com.wyc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.domain.GroupPartakeDeliver;
import com.wyc.repositories.GroupPartakeDeliverRepository;

@Service
public class GroupPartakeDeliverService {
    @Autowired
    private GroupPartakeDeliverRepository groupPartakeDeliverRepository;

    public GroupPartakeDeliver findByGroupPartakeId(String id) {
        return groupPartakeDeliverRepository.findByGroupPartakeId(id);
    }

    public GroupPartakeDeliver save(GroupPartakeDeliver groupPartakeDeliver) {
        return groupPartakeDeliverRepository.save(groupPartakeDeliver);
        
    }
}
