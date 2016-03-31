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

    public Iterable<PushArticle> findAllByStatusAndPushTimeLessThan(
            int notSentStatus, DateTime dateTime) {
        return pushArticleRepository.findAllByStatusAndPushTimeLessThan(notSentStatus,dateTime);
    }

	public PushArticle findOne(String id) {
		return pushArticleRepository.findOne(id);
	}

	public PushArticle save(PushArticle pushArticle) {
		pushArticle.setUpdateAt(new DateTime());
		return pushArticleRepository.save(pushArticle);
		
	}
}
