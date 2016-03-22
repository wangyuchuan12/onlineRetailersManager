package com.wyc.manager.controller.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wyc.defineBean.MySimpleDateFormat;
import com.wyc.domain.Customer;
import com.wyc.domain.CustomerAddress;
import com.wyc.domain.Good;
import com.wyc.domain.GoodOrder;
import com.wyc.domain.GroupPartake;
import com.wyc.domain.GroupPartakeDeliver;
import com.wyc.domain.GroupPartakePayment;
import com.wyc.domain.OrderDetail;
import com.wyc.domain.OrderRecord;
import com.wyc.service.CustomerAddressService;
import com.wyc.service.CustomerService;
import com.wyc.service.GoodOrderService;
import com.wyc.service.GoodService;
import com.wyc.service.GroupPartakeDeliverService;
import com.wyc.service.GroupPartakePaymentService;
import com.wyc.service.GroupPartakeService;
import com.wyc.service.OrderDetailService;
import com.wyc.service.OrderRecordService;
import com.wyc.service.TokenService;
import com.wyc.service.WxUserInfoService;
import com.wyc.wx.domain.Token;
import com.wyc.wx.domain.UserInfo;

@RestController
public class OrderManagerApi {
    @Autowired
    private GoodOrderService goodOrderService;
    @Autowired
    private GoodService goodService;
    @Autowired
    private MySimpleDateFormat mySimpleDateFormat;
    @Autowired
    private OrderRecordService orderRecordService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private WxUserInfoService userInfoService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private GroupPartakeService groupPartakeService;
    @Autowired
    private MySimpleDateFormat dateFormat;
    @Autowired
    private GroupPartakeDeliverService groupPartakeDeliverService;
    @Autowired
    private GroupPartakePaymentService groupPartakePaymentService;
    @Autowired
    private CustomerAddressService customerAddressService;
    final static Logger logger = LoggerFactory.getLogger(OrderManagerApi.class);
    @RequestMapping("/manager/api/order_handle")
    public Object orderHandle(HttpServletRequest httpServletRequest)throws Exception{
        String groupPartakeId = httpServletRequest.getParameter("group_partake_id");
        GroupPartake groupPartake = groupPartakeService.findOne(groupPartakeId);
        String remarks = httpServletRequest.getParameter("remarks");
        groupPartake.setRemarks(remarks);
        groupPartakeService.save(groupPartake);
        String way = httpServletRequest.getParameter("way");
        String logisticsNo = httpServletRequest.getParameter("logistics_no");
        
        GroupPartakePayment groupPartakePayment = groupPartakePaymentService.findByGroupPartakeId(groupPartakeId);
        GroupPartakeDeliver groupPartakeDeliver = groupPartakeDeliverService.findByGroupPartakeId(groupPartakeId);
        groupPartakePayment.setRemarks(remarks);
        groupPartakeDeliver.setRemarks(remarks);
        if(way.equals("apply_refund")){
            groupPartakePayment.setStatus(2);
            groupPartakePayment.setRefundTime(new DateTime());
            
            groupPartakePaymentService.save(groupPartakePayment);
            OrderRecord orderRecord = new OrderRecord();
            orderRecord.setGroupPartakeId(groupPartakeId);
            orderRecord.setRemark("申请退款,内容："+remarks);
            orderRecord.setWay(1);
            orderRecordService.add(orderRecord);
        }else if (way.equals("refund_complete")) {
            groupPartakePayment.setStatus(3);
            groupPartakePayment.setRefundTime(new DateTime());
            
            groupPartakePaymentService.save(groupPartakePayment);
            OrderRecord orderRecord = new OrderRecord();
            orderRecord.setGroupPartakeId(groupPartakeId);
            orderRecord.setRemark("退款处理,内容："+remarks);
            orderRecord.setWay(1);
            orderRecordService.add(orderRecord);
        }else if (way.equals("deliver")) {
            
            groupPartakeDeliver.setStatus(1);
            groupPartakeDeliver.setDeviceTime(new DateTime());
            groupPartakeDeliver.setLogisticsNo(logisticsNo);
            groupPartakeDeliverService.save(groupPartakeDeliver);
            OrderRecord orderRecord = new OrderRecord();
            orderRecord.setGroupPartakeId(groupPartakeId);
            orderRecord.setRemark("发货,物流单号："+logisticsNo+",内容："+remarks);
            orderRecord.setWay(4);
            orderRecordService.add(orderRecord);
        }else if (way.equals("deliver_sign")) {
            groupPartakeDeliver.setStatus(2);
            groupPartakeDeliver.setSignTime(new DateTime());
            groupPartakeDeliverService.save(groupPartakeDeliver);
            OrderRecord orderRecord = new OrderRecord();
            orderRecord.setGroupPartakeId(groupPartakeId);
            orderRecord.setRemark("发货签收，内容："+remarks);
            orderRecord.setWay(5);
            orderRecordService.add(orderRecord);
        }
        return "{success:true}";
    }
    
    @RequestMapping("/manager/api/order_list")
    public Object orderList(HttpServletRequest httpServletRequest){
       Map<String, Object> response = new HashMap<String, Object>();
       List<Map<String, String>> responseGoodOrders = new ArrayList<Map<String,String>>(); 
       Iterable<GoodOrder> goodOrders = goodOrderService.findAllOrderByCreateTimeDesc();
       
       for(GoodOrder goodOrder:goodOrders){
           Map<String, String> responseGoodOrder = new HashMap<String, String>();
           responseGoodOrder.put("id", goodOrder.getId());
           responseGoodOrder.put("status", goodOrder.getStatus()+"");
           responseGoodOrder.put("good_id", goodOrder.getGoodId());
           Good good = goodService.findOne(goodOrder.getGoodId());
           responseGoodOrder.put("good_name", good.getName());
           responseGoodOrder.put("flow_price", goodOrder.getFlowPrice()+"");
           responseGoodOrder.put("good_price", goodOrder.getGoodPrice()+"");
           responseGoodOrder.put("created_at", dateFormat.format(goodOrder.getCreateAt().toDate()));
           responseGoodOrder.put("type", goodOrder.getType()+"");
           responseGoodOrders.add(responseGoodOrder);
           
       }
       response.put("root",responseGoodOrders);
       return response;
    }
    
    @RequestMapping("/manager/api/get_customerinfo_by_order")
    public Object customerInfo(HttpServletRequest httpServletRequest){
        String orderId = httpServletRequest.getParameter("order_id");
        OrderDetail orderDetail = orderDetailService.findByOrderId(orderId);
        String customerId = orderDetail.getCustomerId();
        Customer customer = customerService.findOne(customerId);
        String openId = customer.getOpenId();
        UserInfo userInfo = userInfoService.findByOpenid(openId);
        Token token = tokenService.findByIdAndInvalidDateGreaterThan(userInfo.getToken(), new DateTime());
        Map<String, String> responseCustomerInfo = new HashMap<String, String>();
        responseCustomerInfo.put("openId", customer.getOpenId());
        responseCustomerInfo.put("phonenumber", customer.getPhonenumber());
        responseCustomerInfo.put("defaultAddress", customer.getDefaultAddress());
        responseCustomerInfo.put("city", userInfo.getCity());
        responseCustomerInfo.put("country", userInfo.getCountry());
        responseCustomerInfo.put("groupid", userInfo.getGroupid());
        responseCustomerInfo.put("headimgurl", userInfo.getHeadimgurl());
        responseCustomerInfo.put("language", userInfo.getLanguage());
        responseCustomerInfo.put("nickname", userInfo.getNickname());
        responseCustomerInfo.put("province", userInfo.getProvince());
        responseCustomerInfo.put("remark", userInfo.getRemark());
        responseCustomerInfo.put("sex", userInfo.getSex());
        responseCustomerInfo.put("token", userInfo.getToken());
        if(token!=null){
            responseCustomerInfo.put("invalidDate", mySimpleDateFormat.format(token.getInvalidDate().toDate()));
        }
        return responseCustomerInfo;
    }
    @RequestMapping("/manager/api/get_orderdetail_by_order")
    public Object orderDetail(HttpServletRequest httpServletRequest){
        
        //fields:['id',"orderId","logisticsOrder","customerName","phoneNumber","payStatus","deliverStatus","remarks"]
        Map<String, Object> response = new HashMap<String, Object>();
        List<Map<String, String>> responseOrderDetails = new ArrayList<Map<String,String>>();
        String orderId = httpServletRequest.getParameter("order_id");
        GoodOrder goodOrder = goodOrderService.findOne(orderId);
        OrderDetail orderDetail = orderDetailService.findByOrderId(goodOrder.getId());
        Iterable<GroupPartake> groupPartakes = groupPartakeService.findAllByOrderIdOrderByDateTimeAsc(orderId);
        for(GroupPartake groupPartake:groupPartakes){
            GroupPartakeDeliver groupPartakeDeliver = groupPartakeDeliverService.findByGroupPartakeId(groupPartake.getId());
            GroupPartakePayment groupPartakePayment = groupPartakePaymentService.findByGroupPartakeId(groupPartake.getId());
            Customer customer = customerService.findOne(groupPartake.getCustomerid());
            Map<String, String> responseOrderDetail = new HashMap<String, String>();
            responseOrderDetail.put("id", groupPartake.getId());
            responseOrderDetail.put("orderId", goodOrder.getId());
            responseOrderDetail.put("remarks", groupPartake.getRemarks());
            if(groupPartakeDeliver!=null){
                responseOrderDetail.put("logisticsOrder",groupPartakeDeliver.getLogisticsNo());
                responseOrderDetail.put("deliverStatus", groupPartakeDeliver.getStatus()+"");
            }
            CustomerAddress customerAddress = customerAddressService.findOne(groupPartake.getCustomerAddress());
            responseOrderDetail.put("customerName", customerAddress.getName());
            responseOrderDetail.put("phoneNumber", customerAddress.getPhonenumber());
            responseOrderDetail.put("address", customerAddress.getContent());
            if(groupPartakePayment!=null){
                responseOrderDetail.put("payStatus", groupPartakePayment.getStatus()+"");
            }
           
            responseOrderDetails.add(responseOrderDetail);
        }
        response.put("root", responseOrderDetails);
        return response;
    }
    
    
    @RequestMapping("/manager/api/get_groupdetail_by_order")
    public Object groupDetail(HttpServletRequest httpServletRequest){
        String orderId = httpServletRequest.getParameter("order_id");
        OrderDetail orderDetail = orderDetailService.findByOrderId(orderId);
        String groupId = orderDetail.getGroupId();
        logger.debug("the groupid is {}"+groupId);
        List<Map<String, Object>> responseGroupDetails = new ArrayList<Map<String,Object>>();
        Map<String, Object> response = new HashMap<String, Object>();
        Iterable<GroupPartake> groupPartakes = groupPartakeService.findAllByOrderIdOrderByDateTimeAsc(orderId);
        for(GroupPartake groupPartake:groupPartakes){
            Map<String, Object> responseGroupDetail = new HashMap<String, Object>();
            String customerId = groupPartake.getCustomerid();
            Customer customer = customerService.findOne(customerId);
            String openId = customer.getOpenId();
            UserInfo userInfo = userInfoService.findByOpenid(openId);
            responseGroupDetail.put("datetime", mySimpleDateFormat.format(groupPartake.getDateTime().toDate()));
            responseGroupDetail.put("id", groupPartake.getId());
            responseGroupDetail.put("role", groupPartake.getRole());
            responseGroupDetail.put("orderId", orderId);
            responseGroupDetail.put("defaultAdress", customer.getDefaultAddress());
            responseGroupDetail.put("phonenumber", customer.getPhonenumber());
            responseGroupDetail.put("city", userInfo.getCity());
            
            responseGroupDetail.put("country", userInfo.getCountry());
            responseGroupDetail.put("groupid", userInfo.getGroupid());
            responseGroupDetail.put("headimgurl", userInfo.getHeadimgurl());
            responseGroupDetail.put("language", userInfo.getLanguage());
            responseGroupDetail.put("userinfoId", userInfo.getId());
            responseGroupDetail.put("nickname", userInfo.getNickname());
            responseGroupDetail.put("province", userInfo.getProvince());
            responseGroupDetail.put("remark", userInfo.getRemark());
            responseGroupDetail.put("sex", userInfo.getSex());
            responseGroupDetail.put("subscribe", userInfo.getSubscribe());
            responseGroupDetail.put("subscribeTime", userInfo.getSubscribe_time());
            responseGroupDetail.put("token", userInfo.getToken());
            responseGroupDetail.put("unionid", userInfo.getUnionid());
            responseGroupDetail.put("type",groupPartake.getType());
            responseGroupDetails.add(responseGroupDetail);
         }
        response.put("root",responseGroupDetails);
        return response;
    }
}
