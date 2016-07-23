package com.wyc.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.wyc.domain.LogisticsOrderResult;


public interface LogisticsOrderResultRepository extends CrudRepository<LogisticsOrderResult, String>{

    LogisticsOrderResult findByComAndNo(String com, String no);

    Iterable<LogisticsOrderResult> findAllByStatus(String status);

	Iterable<LogisticsOrderResult> findAllByStatusIn(List<String> statuses);

}
