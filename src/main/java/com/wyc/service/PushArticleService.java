package com.wyc.service;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.domain.PushArticle;
import com.wyc.repositories.PushArticleRepository;

@Service
public class PushArticleService {
    @Autowired
    private PushArticleRepository pushArticleRepository;

    public Iterable<PushArticle> findAllByStatusAndPushTimeGreaterThan(
            int notSentStatus, DateTime dateTime) {
        return pushArticleRepository.findAllByStatusAndPushTimeGreaterThan(notSentStatus,dateTime);
    }
}
