package com.wyc.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.wyc.domain.GroupPartakeDeliver;

public interface GroupPartakeDeliverRepository extends CrudRepository<GroupPartakeDeliver, String>{

    GroupPartakeDeliver findByGroupPartakeId(String id);

    GroupPartakeDeliver findByLogisticsNoAndCom(String no, String com);
    
    @Query("select count(*) from com.wyc.domain.GroupPartakeDeliver g where g.logisticsNo=:logisticsNo and g.com=:com")
    int countByLogisticsNoAndCom(@Param("logisticsNo")String logisticsNo,@Param("com")String com);

}
