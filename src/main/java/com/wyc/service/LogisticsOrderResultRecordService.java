package com.wyc.service;

import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.domain.LogisticsOrderResultRecord;
import com.wyc.repositories.LogisticsOrderResultRecordRepository;

@Service
public class LogisticsOrderResultRecordService {
    @Autowired
    private LogisticsOrderResultRecordRepository logisticsOrderResultRecordRepository;

    public Iterable<LogisticsOrderResultRecord> findAllByLogisticsOrderResultIdOrderByRecordIndexAsc(
            String logisticsOrderResultId) {
        return logisticsOrderResultRecordRepository.findAllByLogisticsOrderResultIdOrderByRecordIndexAsc(logisticsOrderResultId);
    }

    public LogisticsOrderResultRecord add(LogisticsOrderResultRecord logisticsOrderResultRecord) {
       logisticsOrderResultRecord.setCreateAt(new DateTime());
       logisticsOrderResultRecord.setUpdateAt(new DateTime());
       logisticsOrderResultRecord.setId(UUID.randomUUID().toString());
       return logisticsOrderResultRecordRepository.save(logisticsOrderResultRecord);
        
    }

    public int selectMaxIndexByLogisticsOrderResultId(
            String logisticsOrderResultId) {
        try {
            return logisticsOrderResultRecordRepository.selectMaxIndexByLogisticsOrderResultId(logisticsOrderResultId);
        } catch (Exception e) {
            
        }
        return 0;
    }
}
