package com.wyc.manager.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wyc.domain.PushArticle;
import com.wyc.manager.service.SendMessageService;
import com.wyc.service.PushArticleService;
import com.wyc.service.WxUserInfoService;
import com.wyc.smart.service.PushArticleSmartService;
import com.wyc.wx.domain.UserInfo;

public class ArticleTask {
    @Autowired
    private PushArticleSmartService pushArticleSmartService;
    @Autowired
    private PushArticleService pushArticleService;
    @Autowired
    private SendMessageService sendMessageService;
    @Autowired
    private WxUserInfoService wxUserInfoService;
    public void run()throws Exception{
        List<Map<String, Object>> nativeAritcles = pushArticleSmartService.getAllNotSendJsonStr();
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> sendMap = new HashMap<String, Object>();
        List<Object> okList = new ArrayList<Object>();
        List<Object> errorList = new ArrayList<Object>();
        sendMap.put("ok", okList);
        sendMap.put("error", errorList);
        for(Map<String, Object> nativeAritcle:nativeAritcles){
                PushArticle pushArticle = pushArticleService.findOne(nativeAritcle.get("id")+"");
                String sendStr = objectMapper.writeValueAsString(nativeAritcle);
                Map<String, String> map  = sendMessageService.sendMessage(sendStr);
                Map<String, Object> sendOkMap = new HashMap<String, Object>();
                String openId = nativeAritcle.get("touser")+"";
                UserInfo userInfo = wxUserInfoService.findByOpenid(openId);
                sendOkMap.put("openid", openId);
                sendOkMap.put("nickname", userInfo.getNickname());
                sendOkMap.put("headimgurl", userInfo.getHeadimgurl());
                if(map.get("errmsg")!=null&&map.get("errmsg").equals("ok")){
                    pushArticle.setStatus(PushArticle.BEEN_SEND_STATUS);
                    pushArticleService.save(pushArticle);
                    okList.add(sendOkMap);
                }else{
                    errorList.add(sendOkMap);
                }
        }
    }
}
