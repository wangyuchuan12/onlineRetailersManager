package com.wyc.manager.task;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.wyc.domain.GoodGroup;
import com.wyc.domain.GoodOrder;
import com.wyc.domain.GroupPartake;
import com.wyc.domain.GroupPartakePayment;
import com.wyc.domain.OrderDetail;
import com.wyc.domain.OrderRecord;
import com.wyc.service.GoodGroupService;
import com.wyc.service.GoodOrderService;
import com.wyc.service.GroupPartakeDeliverService;
import com.wyc.service.GroupPartakePaymentService;
import com.wyc.service.GroupPartakeService;
import com.wyc.service.OrderDetailService;
import com.wyc.service.OrderRecordService;
import com.wyc.wx.response.domain.PaySuccess;
import com.wyc.wx.service.WxPayService;

public class GroupHandlerTask {
    @Autowired
    private GoodOrderService goodOrderService;
    @Autowired
    private GoodGroupService goodGroupService;
    @Autowired
    private GroupPartakeService groupPartakeService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private GroupPartakePaymentService groupPartakePaymentService;
    @Autowired
    private GroupPartakeDeliverService groupPartakeDeliverService;
    @Autowired
    private OrderRecordService orderRecordService;
    @Autowired
    private WxPayService wxPayService;
    
    final static Logger logger = LoggerFactory.getLogger(GroupHandlerTask.class);
    
    @Autowired
    private EntityManagerFactory   entityManagerFactory;
    
    
    public void run() {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        Iterable<GoodGroup> goodGroups = goodGroupService.findAllByIsDisused(0);
        for(GoodGroup goodGroup:goodGroups){
            try {
                
                boolean flag = checkTimeout(goodGroup);
                if(flag){
                    transaction.begin();
                    goodGroup.setIsDisused(1);
                    goodGroupService.save(goodGroup);
                    transaction.commit();
                }
            } catch (Exception e) {
                logger.error("checkTimeOut has an error :{}",e);
                transaction.rollback();
            }
        }
        em.close();
    }
    
    private boolean checkTimeout(GoodGroup goodGroup)throws Exception{
        if((goodGroup.getResult()==1||goodGroup.getResult()==0)&&groupPartakeService.countByGroupId(goodGroup.getId())<goodGroup.getNum()){
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(goodGroup.getStartTime().toDate());
            calendar.add(Calendar.HOUR, goodGroup.getTimeLong());
            if(calendar.getTime().getTime()<new Date().getTime()){
                goodGroup.setResult(0);
                goodGroup = goodGroupService.save(goodGroup);
                OrderDetail orderDetail = orderDetailService.findByGruopId(goodGroup.getId());
                GoodOrder goodOrder = goodOrderService.findOne(orderDetail.getOrderId());
                goodOrder.setStatus(5);
                goodOrderService.save(goodOrder);
                
                Iterable<GroupPartake> groupPartakes = groupPartakeService.findAllByGroupIdOrderByDateTime(goodGroup.getId());
                
                for(GroupPartake groupPartake:groupPartakes){
                    GroupPartakePayment groupPartakePayment = groupPartakePaymentService.findByGroupPartakeId(groupPartake.getId());
                    
                    if(groupPartakePayment.getStatus()==1||groupPartakePayment.getStatus()==2){
                        groupPartakePayment.setStatus(3);
                        String outTradeNo = groupPartakePayment.getOutTradeNo();
                        PaySuccess paySuccess = wxPayService.refund(outTradeNo);
                        groupPartakePayment.setRefundTime(new DateTime());
                        if(paySuccess!=null){
                            
                            groupPartakePayment.setRefundAmount(Float.parseFloat(paySuccess.getTotalFee()));
                            
                        }
                        groupPartakePaymentService.save(groupPartakePayment);
                        OrderRecord orderRecord = new OrderRecord();
                        orderRecord.setGroupPartakeId(groupPartakePayment.getGroupPartakeId());
                        orderRecord.setHandlerAdmin("system");
                        orderRecord.setRemark("系统操作，自动完成");
                        orderRecord.setWay(OrderRecord.REFUND_HANDLER);
                        orderRecordService.add(orderRecord);
                    }
                }
                return true;
            }
        }
        return false;
    }
}
