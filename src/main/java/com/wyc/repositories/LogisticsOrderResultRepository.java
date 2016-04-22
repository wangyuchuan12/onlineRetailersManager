package com.wyc.repositories;

import org.springframework.data.repository.CrudRepository;

import com.wyc.domain.LogisticsOrderResult;


public interface LogisticsOrderResultRepository extends CrudRepository<LogisticsOrderResult, String>{

    LogisticsOrderResult findByComAndNo(String com, String no);

}
