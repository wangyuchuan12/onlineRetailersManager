package com.wyc.repositories;

import org.springframework.data.repository.CrudRepository;

import com.wyc.domain.GroupPartakeDeliver;

public interface GroupPartakeDeliverRepository extends CrudRepository<GroupPartakeDeliver, String>{

    GroupPartakeDeliver findByGroupPartakeId(String id);

}
