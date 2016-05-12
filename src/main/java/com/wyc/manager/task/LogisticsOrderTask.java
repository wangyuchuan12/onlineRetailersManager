package com.wyc.manager.task;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.wyc.domain.LogisticsOrderResult;
import com.wyc.service.LogisticsOrderResultService;
import com.wyc.wx.domain.JhOrder;
import com.wyc.wx.service.JuheOrderService;

public class LogisticsOrderTask {
    @Autowired
    private JuheOrderService juheOrderService;
    @Autowired
    private LogisticsOrderResultService logisticsOrderResultService;
    @Autowired
    private EntityManagerFactory   entityManagerFactory;
    final static Logger logger = LoggerFactory.getLogger(LogisticsOrderTask.class);
    public void run(){
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        Iterable<LogisticsOrderResult> logisticsOrderResults = logisticsOrderResultService.findAllByStatus("0");
        for(LogisticsOrderResult logisticsOrderResult:logisticsOrderResults){
            try {
                transaction.begin();
                JhOrder jhOrder = juheOrderService.getJhOrder(logisticsOrderResult.getCom(), logisticsOrderResult.getNo());
                if(jhOrder!=null&&jhOrder.getResult()!=null){
                    juheOrderService.updateLogistics(jhOrder);
                }
                transaction.commit();
            } catch (Exception e) {
                logger.error("update date logistics has an error:{}",e);
                transaction.rollback();
            }
            
        }
        em.close();
    }
}
