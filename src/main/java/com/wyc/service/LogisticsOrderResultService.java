package com.wyc.service;

import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.domain.LogisticsOrderResult;
import com.wyc.repositories.LogisticsOrderResultRepository;

@Service
public class LogisticsOrderResultService {
    @Autowired
    private LogisticsOrderResultRepository logisticsOrderResultRepository;

    public LogisticsOrderResult findByComAndNo(String com, String no) {
        return logisticsOrderResultRepository.findByComAndNo(com,no);
    }

    public LogisticsOrderResult add(LogisticsOrderResult logisticsOrderResult) {
        logisticsOrderResult.setCreateAt(new DateTime());
        logisticsOrderResult.setUpdateAt(new DateTime());
        logisticsOrderResult.setId(UUID.randomUUID().toString());
        return logisticsOrderResultRepository.save(logisticsOrderResult);
    }

    public LogisticsOrderResult save(LogisticsOrderResult logisticsOrderResult) {
        logisticsOrderResult.setUpdateAt(new DateTime());
        return logisticsOrderResultRepository.save(logisticsOrderResult);
    }

    public Iterable<LogisticsOrderResult> findAllByStatus(String status) {
        return logisticsOrderResultRepository.findAllByStatus(status);
    }
}
