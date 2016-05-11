package com.wyc.manager.task;

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
    final static Logger logger = LoggerFactory.getLogger(LogisticsOrderTask.class);
    public void run(){
        Iterable<LogisticsOrderResult> logisticsOrderResults = logisticsOrderResultService.findAllByStatus("0");
        for(LogisticsOrderResult logisticsOrderResult:logisticsOrderResults){
            try {
                JhOrder jhOrder = juheOrderService.getJhOrder(logisticsOrderResult.getCom(), logisticsOrderResult.getNo());
                if(jhOrder!=null&&jhOrder.getResult()!=null){
                    juheOrderService.updateLogistics(jhOrder);
                }
            } catch (Exception e) {
                logger.error("update date logistics has an error:{}",e);
            }
            
        }
    }
}
