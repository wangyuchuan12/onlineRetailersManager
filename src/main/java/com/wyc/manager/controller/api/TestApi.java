package com.wyc.manager.controller.api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wyc.wx.service.JuheOrderService;

@RestController
public class TestApi {
    @Autowired
    private JuheOrderService juheOrderService;
    
    @RequestMapping("/api/test/jh")
    public Object testJh(HttpServletRequest httpServletRequest)throws Exception{
        String com = httpServletRequest.getParameter("com");
        String no = httpServletRequest.getParameter("no");
        return juheOrderService.getJhOrder(com, no);
    }
}
