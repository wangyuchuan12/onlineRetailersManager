package com.wyc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.domain.LogisticsOrderCompany;
import com.wyc.repositories.LogisticsOrderCompanyRepository;

@Service
public class LogisticsOrderCompanyService {
    @Autowired
    private LogisticsOrderCompanyRepository logisticsOrderCompanyRepository;

    public Iterable<LogisticsOrderCompany> findAll() {
        return logisticsOrderCompanyRepository.findAll();
    }
}
