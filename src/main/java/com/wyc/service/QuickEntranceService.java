package com.wyc.service;

import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.domain.SystemQuickEntrance;
import com.wyc.repositories.QuickEntranceRepository;

@Service
public class QuickEntranceService {
    @Autowired
    private QuickEntranceRepository quickEntranceRepository;

    public Iterable<SystemQuickEntrance> findAllOrderByRankAsc() {
        return quickEntranceRepository.findOrderByRankAsc();
    }

    public SystemQuickEntrance add(SystemQuickEntrance systemQuickEntrance) {
        systemQuickEntrance.setId(UUID.randomUUID().toString());
        systemQuickEntrance.setCreateAt(new DateTime());
        systemQuickEntrance.setUpdateAt(new DateTime());
        return quickEntranceRepository.save(systemQuickEntrance);
        
    }

    public void delete(String id) {
        quickEntranceRepository.delete(id);
        
    }
}
