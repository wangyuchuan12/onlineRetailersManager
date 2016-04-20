package com.wyc.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.wyc.domain.LogisticsOrderResultRecord;

public interface LogisticsOrderResultRecordRepository extends CrudRepository<LogisticsOrderResultRecord, String>{

    Iterable<LogisticsOrderResultRecord> findAllByLogisticsOrderResultIdOrderByRecordIndexAsc(
            String logisticsOrderResultId);
    
    @Query("select max(recordIndex) from com.wyc.domain.LogisticsOrderResultRecord g where g.logisticsOrderResultId=:logisticsOrderResultId")
    int selectMaxIndexByLogisticsOrderResultId(
            @Param("logisticsOrderResultId") String logisticsOrderResultId);

}
