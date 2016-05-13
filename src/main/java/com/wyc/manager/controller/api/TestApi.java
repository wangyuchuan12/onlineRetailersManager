package com.wyc.manager.controller.api;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wyc.service.TokenService;
import com.wyc.wx.domain.Token;
import com.wyc.wx.service.JuheOrderService;

@RestController
public class TestApi {
    @Autowired
    private JuheOrderService juheOrderService;
    @Autowired
    private TokenService tokenServcie;
    @Autowired
    private EntityManagerFactory   entityManagerFactory;
    @RequestMapping("/api/test/jh")
    public Object testJh(HttpServletRequest httpServletRequest)throws Exception{
        String com = httpServletRequest.getParameter("com");
        String no = httpServletRequest.getParameter("no");
        return juheOrderService.getJhOrder(com, no);
    }
    
    @Transactional
    @RequestMapping("/api/test/token")
    public Object tokenTest(HttpServletRequest httpServletRequest)throws Exception{
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Token token = new Token();
        token.setStatus(0);
        token.setInvalidDate(new DateTime());
        token.setTokenKey("111");
        token = tokenServcie.add(token);
        if(true){
            transaction.rollback();
            em.close();
            throw new Exception("sdsdf");
        }
        Token token2 = new Token();
        
        token2.setStatus(0);
        token2.setInvalidDate(new DateTime());
        token2.setTokenKey("222");
        token2 = tokenServcie.add(token2);
        
        return token;
    }
}
