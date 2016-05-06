package com.wyc.manager.task;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;

import com.wyc.domain.GoodGroup;
import com.wyc.domain.GoodOrder;
import com.wyc.domain.GroupPartake;
import com.wyc.domain.GroupPartakePayment;
import com.wyc.domain.OrderDetail;
import com.wyc.service.GoodGroupService;
import com.wyc.service.GoodOrderService;
import com.wyc.service.GroupPartakeDeliverService;
import com.wyc.service.GroupPartakePaymentService;
import com.wyc.service.GroupPartakeService;
import com.wyc.service.OrderDetailService;

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
    public void run() {
        Iterable<GoodGroup> goodGroups = goodGroupService.findAllByIsDisused(0);
        for(GoodGroup goodGroup:goodGroups){
            checkTimeout(goodGroup);
        }
    }
    
    private GoodGroup checkTimeout(GoodGroup goodGroup){
        if(goodGroup.getResult()==1){
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
                    
                    if(groupPartakePayment.getStatus()==1){
                        groupPartakePayment.setStatus(2);
                        groupPartakePaymentService.save(groupPartakePayment);
                    }
                }
            }
        }
        return goodGroup;
    }
}
