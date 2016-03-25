package com.wyc.manager.controller.api;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wyc.domain.GoodType;
import com.wyc.manager.domain.Article;
import com.wyc.manager.service.SendMessageService;
import com.wyc.service.GoodTypeService;
import com.wyc.smart.service.PushArticleSmartService;

@RestController
public class CustomerServiceAction {
    @Autowired
    private SendMessageService sendMessageService;
    @Autowired
    private GoodTypeService goodTypeService;
    @Autowired
    private PushArticleSmartService pushArticleSmartService;
    
    @RequestMapping("/manager/api/customer_service/test")
    public Object test(HttpServletRequest httpServletRequest)throws Exception{
    	return pushArticleSmartService.getAllNotSendJsonStr();
    }
    @RequestMapping("/manager/api/customer_service/spread")
    public Object spread(HttpServletRequest httpServletRequest)throws Exception{
        String openid = httpServletRequest.getParameter("openid");
        if(openid==null){
            openid = "ozwNjw5j1V2CfXkysb1YvEtTHPqU";
        }
        String goodTypeId = httpServletRequest.getParameter("good_type");
        if(goodTypeId==null){
            goodTypeId = "1";
        }
        GoodType goodTypeEntity = goodTypeService.findOne(goodTypeId);
        
        
        Article article = new Article();
        article.setDescription("风靡全国的拼货商城，精美商品提供，快来一起拼货吧");
        article.setPicurl(goodTypeEntity.getImg());
        article.setTitle(goodTypeEntity.getTitle());
        article.setUrl("http://www.chengxihome.com/main/good_list");
        List<Article> articles = new ArrayList<Article>();
        articles.add(article);
        return sendMessageService.sendImgMessage(openid, articles);
    }
}
