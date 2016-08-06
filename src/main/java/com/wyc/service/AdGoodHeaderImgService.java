package com.wyc.service;

import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.domain.SystemAdGoodHeaderImg;
import com.wyc.repositories.AdGoodHeaderImgRepository;

@Service
public class AdGoodHeaderImgService {
    @Autowired
    private AdGoodHeaderImgRepository adGoodHeaderImgRepository;

    public Iterable<SystemAdGoodHeaderImg> findAllOrderByRankAsc() {
        return adGoodHeaderImgRepository.findAllOrderByRankAsc();
    }

    public SystemAdGoodHeaderImg add(SystemAdGoodHeaderImg systemAdGoodHeaderImg) {
        systemAdGoodHeaderImg.setId(UUID.randomUUID().toString());
        systemAdGoodHeaderImg.setUpdateAt(new DateTime());
        systemAdGoodHeaderImg.setCreateAt(new DateTime());
        return adGoodHeaderImgRepository.save(systemAdGoodHeaderImg);
        
    }

    public void delete(String id) {
        adGoodHeaderImgRepository.delete(id);
        
    }
}
