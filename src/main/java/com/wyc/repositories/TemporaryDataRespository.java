package com.wyc.repositories;

import org.joda.time.DateTime;
import org.springframework.data.repository.CrudRepository;

import com.wyc.domain.TemporaryData;

public interface TemporaryDataRespository extends CrudRepository<TemporaryData, String>{
    public Iterable<TemporaryData> findAllByMykey(String key);

    public TemporaryData findByMykeyAndName(String key, String name);

    public Iterable<TemporaryData> findAllByUpdateAtLessThan(DateTime dateTime);
    
}
