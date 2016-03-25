package com.wyc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.domain.NewsArticleItem;
import com.wyc.repositories.NewsArticleItemRepository;

@Service
public class NewsArticleItemService {
    @Autowired
    private NewsArticleItemRepository newsArticleItemRepository;

    public Iterable<NewsArticleItem> findAllByArticleId(String articleId) {
        return newsArticleItemRepository.findAllByArticleId(articleId);
    }
}
