package com.wyc.smart.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wyc.domain.NewsArticleItem;
import com.wyc.domain.PushArticle;
import com.wyc.domain.TextArticleItem;
import com.wyc.service.NewsArticleItemService;
import com.wyc.service.PushArticleService;
import com.wyc.service.TextArticleItemService;

@Service
public class PushArticleSmartService {
    @Autowired
    private NewsArticleItemService newsArticleItemService;
    @Autowired
    private PushArticleService pushArticleService;
    @Autowired
    private TextArticleItemService textArticleItemService;
    
    public List<Map<String, Object>> getAllNotSendJsonStr(){
        Iterable<PushArticle> notSendArticles = pushArticleService.findAllByStatusAndPushTimeLessThan(PushArticle.NOT_SENT_STATUS,new DateTime());
        List<Map<String, Object>> responseAtricles = new ArrayList<Map<String,Object>>();
        for(PushArticle pushArticle:notSendArticles){
            Map<String, Object> responseAtricle = new HashMap<String, Object>();
            responseAtricle.put("touser", pushArticle.getTouser());
            responseAtricle.put("msgtype", pushArticle.getMsgtype());
            if(pushArticle.getMsgtype().equals("text")){
                Map<String, String> content = new HashMap<String, String>();
                TextArticleItem textArticleItem = textArticleItemService.findByArticleId(pushArticle.getId());
                content.put("content", textArticleItem.getContent());
            }else if (pushArticle.getMsgtype().equals("news")) {
                Map<String, Object> news = new HashMap<String, Object>();
                List<Map<String, String>> articles = new ArrayList<Map<String,String>>();
                Iterable<NewsArticleItem> newsArticleItems = newsArticleItemService.findAllByArticleId(pushArticle.getId());
                for(NewsArticleItem newsArticleItem:newsArticleItems){
                    Map<String, String> article = new HashMap<String, String>();
                    article.put("title", newsArticleItem.getTitle());
                    article.put("description", newsArticleItem.getDescription());
                    article.put("url", newsArticleItem.getUrl());
                    article.put("picurl", newsArticleItem.getPicurl());
                    articles.add(article);
                }
                news.put("articles", articles);
                responseAtricle.put("news", news);
                responseAtricles.add(responseAtricle);
            }
        }
        return responseAtricles;
    }
}
