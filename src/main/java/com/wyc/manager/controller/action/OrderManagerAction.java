package com.wyc.manager.controller.action;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.squareup.okhttp.Address;
import com.wyc.domain.CustomerAddress;
import com.wyc.domain.Good;
import com.wyc.domain.GoodGroup;
import com.wyc.domain.GoodOrder;
import com.wyc.domain.SystemGoodType;
import com.wyc.domain.GroupPartake;
import com.wyc.domain.GroupPartakeDeliver;
import com.wyc.domain.GroupPartakePayment;
import com.wyc.domain.MyResource;
import com.wyc.domain.OrderRecord;
import com.wyc.manager.domain.Admin;
import com.wyc.manager.service.AdminService;
import com.wyc.service.CustomerAddressService;
import com.wyc.service.GoodGroupService;
import com.wyc.service.GoodOrderService;
import com.wyc.service.GoodService;
import com.wyc.service.GoodTypeService;
import com.wyc.service.GroupPartakeDeliverService;
import com.wyc.service.GroupPartakePaymentService;
import com.wyc.service.GroupPartakeService;
import com.wyc.service.MyResourceService;
import com.wyc.service.OrderRecordService;
@Controller
public class OrderManagerAction {
    @Autowired
    private GoodOrderService goodOrderService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private GroupPartakeService groupPartakeService;
    @Autowired
    private GroupPartakeDeliverService groupPartakeDeliverService;
    @Autowired
    private GroupPartakePaymentService groupPartakePaymentService;
    @Autowired
    private GoodService goodService;
    @Autowired
    private MyResourceService myResourceService;
    @Autowired
    private CustomerAddressService customerAddressService;
    @Autowired
    private GoodTypeService goodTypeService;
    @Autowired
    private OrderRecordService orderRecordService;
    @Autowired
    private GoodGroupService goodGroupService;
    public Map<String, Object> responseOrder (GoodOrder goodOrder , GroupPartake groupPartake){
        Map<String, Object> responseOrder = new HashMap<String, Object>();
        Good good = goodService.findOne(goodOrder.getGoodId());
        GroupPartakeDeliver groupPartakeDeliver = groupPartakeDeliverService.findByGroupPartakeId(groupPartake.getId());
        GroupPartakePayment groupPartakePayment = groupPartakePaymentService.findByGroupPartakeId(groupPartake.getId());
        String headImg = good.getHeadImg();
        MyResource myResource = myResourceService.findOne(headImg);
        responseOrder.put("goodHeadImgUrl",myResource.getUrl());
        responseOrder.put("goodName", good.getName());
        SystemGoodType goodType = goodTypeService.findOne(good.getGoodType());
        responseOrder.put("goodTypeName", goodType.getName());
        responseOrder.put("payStatus", groupPartakePayment.getStatus());
        responseOrder.put("payTime", groupPartakePayment.getPayTime());
        responseOrder.put("deliverStatus", groupPartakeDeliver.getStatus());
        responseOrder.put("refundAmount", groupPartakePayment.getRefundAmount());
        responseOrder.put("refundTime", groupPartakePayment.getRefundTime());
        responseOrder.put("deliverTime", groupPartakeDeliver.getDeviceTime());
        
        responseOrder.put("address",groupPartake.getCustomerAddress());
        responseOrder.put("cost",groupPartakePayment.getCost());
        responseOrder.put("status",groupPartake.getStatus());
        responseOrder.put("groupPartakeId", groupPartake.getId());
        responseOrder.put("type", groupPartake.getType());
        responseOrder.put("role", groupPartake.getRole());
        responseOrder.put("dataTime", groupPartake.getDateTime());
        return responseOrder;
    }
    
    @RequestMapping("/manager/apply_settlement")
    public String applySettlement(HttpServletRequest httpServletRequest){
        String groupPartakeId = httpServletRequest.getParameter("group_partake_id");
        GroupPartake groupPartake = groupPartakeService.findOne(groupPartakeId);
        groupPartake.setStatus(GroupPartake.PROGRAM_STATUS);
        groupPartakeService.save(groupPartake);
        return "redirect:/manager/orders";
    }
    
    @RequestMapping("/manager/device_handler")
    public String deviceHandler(HttpServletRequest httpServletRequest){
        
        String groupPartakeId = httpServletRequest.getParameter("group_partake_id");
        String deviceTime = httpServletRequest.getParameter("device_time");
        GroupPartake groupPartake = groupPartakeService.findOne(groupPartakeId);
        String remarks = httpServletRequest.getParameter("remarks");
        groupPartake.setRemarks(remarks);
        String logisticsNo = httpServletRequest.getParameter("logistics_no");
        GroupPartakeDeliver groupPartakeDeliver = groupPartakeDeliverService.findByGroupPartakeId(groupPartakeId);
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        DateTime dt = formatter.parseDateTime(deviceTime);
        groupPartakeDeliver.setDeviceTime(dt);
        groupPartakeDeliver.setLogisticsNo(logisticsNo);
        groupPartakeDeliver.setStatus(1);
        groupPartakeDeliverService.save(groupPartakeDeliver);
        
        OrderRecord orderRecord = new OrderRecord();
        orderRecord.setGroupPartakeId(groupPartakeId);
        orderRecord.setRemark(remarks);
        orderRecord.setWay(4);
        
        orderRecordService.add(orderRecord);
        return "redirect:/manager/orders";
        
    }
    
    @RequestMapping("/manager/orders")
    public String order(HttpServletRequest httpServletRequest){
        Subject subject = SecurityUtils.getSubject();
        String username = subject.getPrincipal()+"";
        Admin admin = adminService.findByUsername(username);
	Iterable<GoodOrder> goodOrders = goodOrderService.findAllByAdminId(admin.getId()+"");
	List<Map<String, Object>> responseOrders = new ArrayList<Map<String,Object>>();
	for(GoodOrder goodOrder:goodOrders){
	    Iterable<GroupPartake> groupPartakeIterable = groupPartakeService.findAllByOrderIdOrderByDateTimeAsc(goodOrder.getId());
	    for(GroupPartake groupPartake:groupPartakeIterable){
	        Map<String, Object> responseOrder = responseOrder(goodOrder , groupPartake);
	        responseOrders.add(responseOrder);
	    }
	}
	httpServletRequest.setAttribute("orders", responseOrders);
        return "order/orders";
    }
    
 //   @RequestMapping("/manager/derelect_orders_by_group_id")
    public String derelectOrder(HttpServletRequest httpServletRequest){
        String groupId = httpServletRequest.getParameter("group_id");
        GoodGroup goodGroup = goodGroupService.findOne(groupId);
        goodGroup.setResult(GoodGroup.DERELICT_RESULT);
        goodGroupService.save(goodGroup);
        Iterable<GroupPartake> groupPartakes = groupPartakeService.findAllByGroupIdOrderByDateTime(groupId);
        for(GroupPartake groupPartake:groupPartakes){
            groupPartake.setStatus(GroupPartake.DERELICT_STATUS);
            groupPartakeService.save(groupPartake);
        }
        return "redirect:/manager/orders_by_group_id?group_id="+groupId;
    }
    
    @RequestMapping("/manager/orders_by_group_id")
    public String orderByGroupId(HttpServletRequest httpServletRequest){
        String groupId = httpServletRequest.getParameter("group_id");
        Subject subject = SecurityUtils.getSubject();
        String username = subject.getPrincipal()+"";
        Admin admin = adminService.findByUsername(username);
        List<Map<String, Object>> responseOrders = new ArrayList<Map<String,Object>>();
        Iterable<GoodOrder> goodOrders = goodOrderService.findAllByGroupIdAndAdminId(groupId,admin.getId()+"");
        System.out.println("goodOrders:"+goodOrders);
        System.out.println("groupId:"+groupId);
        System.out.println("adminId:"+admin.getId());
        for(GoodOrder goodOrder:goodOrders){
            System.out.println("goodOrder:"+goodOrder.getId());
            Iterable<GroupPartake> groupPartakeIterable = groupPartakeService.findAllByOrderIdOrderByDateTimeAsc(goodOrder.getId());
            for(GroupPartake groupPartake:groupPartakeIterable){
                Map<String, Object> responseOrder = responseOrder(goodOrder , groupPartake);
                responseOrders.add(responseOrder);
            }
        }
        httpServletRequest.setAttribute("orders", responseOrders);
        return "order/orders";
    }
}
