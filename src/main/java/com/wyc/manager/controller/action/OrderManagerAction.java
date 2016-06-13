package com.wyc.manager.controller.action;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wyc.domain.Business;
import com.wyc.domain.Good;
import com.wyc.domain.GoodGroup;
import com.wyc.domain.GoodOrder;
import com.wyc.domain.LogisticsOrderCompany;
import com.wyc.domain.SystemGoodType;
import com.wyc.domain.GroupPartake;
import com.wyc.domain.GroupPartakeDeliver;
import com.wyc.domain.GroupPartakePayment;
import com.wyc.domain.MyResource;
import com.wyc.domain.OrderRecord;
import com.wyc.manager.domain.Admin;
import com.wyc.manager.service.AdminService;
import com.wyc.service.BusinessService;
import com.wyc.service.CustomerAddressService;
import com.wyc.service.GoodGroupService;
import com.wyc.service.GoodOrderService;
import com.wyc.service.GoodService;
import com.wyc.service.GoodTypeService;
import com.wyc.service.GroupPartakeDeliverService;
import com.wyc.service.GroupPartakePaymentService;
import com.wyc.service.GroupPartakeService;
import com.wyc.service.LogisticsOrderCompanyService;
import com.wyc.service.MyResourceService;
import com.wyc.service.OrderRecordService;
import com.wyc.wx.domain.JhOrder;
import com.wyc.wx.service.JuheOrderService;
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
    @Autowired
    private LogisticsOrderCompanyService logisticsOrderCompanyService;
    @Autowired
    private JuheOrderService juheOrderService;
    @Autowired
    private EntityManagerFactory   entityManagerFactory;
    @Autowired
    private BusinessService businessService;
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
        responseOrder.put("customerId", groupPartake.getCustomerid());
        return responseOrder;
    }
    
    @RequestMapping("/manager/do_settlement")
    @Transactional
    public String doSettlement(HttpServletRequest httpServletRequest){
        Subject subject = SecurityUtils.getSubject();
        String username = subject.getPrincipal()+"";
        Admin admin = adminService.findByUsername(username);
        String groupPartakeId = httpServletRequest.getParameter("group_partake_id");
        String remark = httpServletRequest.getParameter("remark");
        GroupPartake groupPartake = groupPartakeService.findOne(groupPartakeId);
        groupPartake.setStatus(GroupPartake.SUCCESS_STATUS);
        groupPartake = groupPartakeService.save(groupPartake);
        GoodOrder goodOrder = goodOrderService.findOne(groupPartake.getOrderId());
        Admin businessAdmin = adminService.findOne(Long.parseLong(goodOrder.getAdminId()));
        Business business = businessService.findByAdminId(businessAdmin.getId()+"");
        business.setAccount(business.getAccount()+goodOrder.getCost());
        businessService.save(business);
        OrderRecord orderRecord = new OrderRecord();
        orderRecord.setGroupPartakeId(groupPartakeId);
        orderRecord.setHandlerAdmin(admin.getId()+"");
        orderRecord.setRemark(remark);
        orderRecord.setWay(OrderRecord.AUDIT_PAY);
        orderRecordService.add(orderRecord);
        return "redirect:/manager/orders_program";
    }
    
    @RequestMapping("/manager/apply_settlement")
    @Transactional
    public String applySettlement(HttpServletRequest httpServletRequest){
        
        Subject subject = SecurityUtils.getSubject();
        String username = subject.getPrincipal()+"";
        Admin admin = adminService.findByUsername(username);
        String groupPartakeId = httpServletRequest.getParameter("group_partake_id");
        String remark = httpServletRequest.getParameter("remark");
        GroupPartake groupPartake = groupPartakeService.findOne(groupPartakeId);
        groupPartake.setStatus(GroupPartake.PROGRAM_STATUS);
        groupPartake = groupPartakeService.save(groupPartake);
        
        OrderRecord orderRecord = new OrderRecord();
        orderRecord.setGroupPartakeId(groupPartakeId);
        orderRecord.setHandlerAdmin(admin.getId()+"");
        orderRecord.setRemark(remark);
        orderRecord.setWay(OrderRecord.APPLY_PAY);
        orderRecordService.add(orderRecord);
        return "redirect:/manager/orders";
    }

    @RequestMapping("/manager/device_handler")
    @Transactional
    public String deviceHandler(HttpServletRequest httpServletRequest)throws Exception{
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        try {
            String groupPartakeId = httpServletRequest.getParameter("group_partake_id");
            String com = httpServletRequest.getParameter("com");
            String deviceTime = httpServletRequest.getParameter("device_time");
            
            String remarks = httpServletRequest.getParameter("remarks");
            
            String logisticsNo = httpServletRequest.getParameter("logistics_no");
            JhOrder jhOrder = juheOrderService.getJhOrder(com, logisticsNo);
            int deliverCountByLogisticsNoAndCom = groupPartakeDeliverService.countByLogisticsNoAndCom(logisticsNo,com);
            if(deliverCountByLogisticsNoAndCom!=0){
                httpServletRequest.setAttribute("errorcode", "101");
                httpServletRequest.setAttribute("errortitle", "订单号填写错误");
                httpServletRequest.setAttribute("errorMessage", "该订单编号已存在");
                httpServletRequest.setAttribute("returnUrl", "/manager/orders");
                transaction.rollback();
                return "admin/error";
            }
            if(jhOrder!=null&&jhOrder.getError_code().equals("0")){
                GroupPartake groupPartake = groupPartakeService.findOne(groupPartakeId);
                groupPartake.setRemarks(remarks);
                GroupPartakeDeliver groupPartakeDeliver = groupPartakeDeliverService.findByGroupPartakeId(groupPartakeId);
                DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
                DateTime dt = formatter.parseDateTime(deviceTime);
                groupPartakeDeliver.setDeviceTime(dt);
                groupPartakeDeliver.setLogisticsNo(logisticsNo);
                groupPartakeDeliver.setCom(com);
                if(jhOrder.getResult().getStatus().equals("1")){
                    groupPartakeDeliver.setStatus(2);
                }else{
                    groupPartakeDeliver.setStatus(1);
                }
                groupPartakeDeliverService.save(groupPartakeDeliver);
                
                OrderRecord orderRecord = new OrderRecord();
                orderRecord.setGroupPartakeId(groupPartakeId);
                orderRecord.setRemark(remarks);
                orderRecord.setWay(OrderRecord.DELIVER);
                
                orderRecordService.add(orderRecord);
                juheOrderService.updateLogistics(jhOrder);
                
            }else{
                httpServletRequest.setAttribute("errorcode", "101");
                httpServletRequest.setAttribute("errortitle", "订单号填写错误");
                httpServletRequest.setAttribute("errorMessage", "请输入正确的订单号");
                httpServletRequest.setAttribute("returnUrl", "/manager/orders");
                transaction.rollback();
                return "admin/error";
            }
            transaction.commit();
        } catch (Exception e) {
            httpServletRequest.setAttribute("errorcode", "501");
            httpServletRequest.setAttribute("errortitle", "数据发生错误");
            httpServletRequest.setAttribute("errorMessage", "请输入正确的信息");
            httpServletRequest.setAttribute("returnUrl", "/manager/orders");
            transaction.rollback();
            e.printStackTrace();
            return "admin/error";
        }finally{
            em.close();
        }
        return "redirect:/manager/orders";
        
    }
    
    @RequestMapping("/manager/orders_program")
    public String ordersOfProgramStatus(HttpServletRequest httpServletRequest){
        List<Map<String, Object>> responseOrders = new ArrayList<Map<String,Object>>();
        Iterable<GroupPartake> groupPartakeIterable = groupPartakeService.findAllByStatusOrderByDateTimeAsc(GroupPartake.PROGRAM_STATUS);
        for(GroupPartake groupPartake:groupPartakeIterable){
            GoodOrder goodOrder = goodOrderService.findOne(groupPartake.getOrderId());
            Map<String, Object> responseOrder = responseOrder(goodOrder , groupPartake);
            responseOrders.add(responseOrder);
        }
        httpServletRequest.setAttribute("orders", responseOrders);
        return "order/orders";
    }
    
    @RequestMapping("/manager/orders")
    public String order(HttpServletRequest httpServletRequest){
        Subject subject = SecurityUtils.getSubject();
        String username = subject.getPrincipal()+"";
        Admin admin = adminService.findByUsername(username);
	Iterable<GoodOrder> goodOrders = goodOrderService.findAllByAdminId(admin.getId()+"");
	List<Map<String, Object>> responseOrders = new ArrayList<Map<String,Object>>();
	for(GoodOrder goodOrder:goodOrders){
	    Iterable<GroupPartake> groupPartakeIterable = groupPartakeService.findAllByOrderIdAndIsDelOrderByDateTimeAsc(goodOrder.getId(),0);
	    for(GroupPartake groupPartake:groupPartakeIterable){
	        Map<String, Object> responseOrder = responseOrder(goodOrder , groupPartake);
	        responseOrders.add(responseOrder);
	    }
	}
	Iterable<LogisticsOrderCompany> logisticsOrderCompanys = logisticsOrderCompanyService.findAll();
	httpServletRequest.setAttribute("companys", logisticsOrderCompanys);
	httpServletRequest.setAttribute("orders", responseOrders);
	httpServletRequest.setAttribute("adminId", admin.getId());
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
