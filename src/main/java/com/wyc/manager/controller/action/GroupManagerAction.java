package com.wyc.manager.controller.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sun.net.httpserver.HttpsConfigurator;
import com.wyc.domain.Customer;
import com.wyc.domain.Good;
import com.wyc.domain.GoodGroup;
import com.wyc.domain.GoodOrder;
import com.wyc.domain.SystemGoodType;
import com.wyc.domain.GroupPartake;
import com.wyc.domain.GroupPartakeDeliver;
import com.wyc.domain.GroupPartakePayment;
import com.wyc.domain.LogisticsOrderCompany;
import com.wyc.domain.MyResource;
import com.wyc.manager.domain.Admin;
import com.wyc.manager.service.AdminService;
import com.wyc.service.CustomerService;
import com.wyc.service.GoodGroupService;
import com.wyc.service.GoodOrderService;
import com.wyc.service.GoodService;
import com.wyc.service.GoodTypeService;
import com.wyc.service.GroupPartakeDeliverService;
import com.wyc.service.GroupPartakePaymentService;
import com.wyc.service.GroupPartakeService;
import com.wyc.service.LogisticsOrderCompanyService;
import com.wyc.service.MyResourceService;
import com.wyc.service.WxUserInfoService;
import com.wyc.wx.domain.UserInfo;

@Controller
public class GroupManagerAction {
    @Autowired
    private AdminService adminService;
    @Autowired
    private GoodGroupService goodGroupService;
    @Autowired
    private GoodService goodService;
    @Autowired
    private GoodTypeService goodTypeService;
    @Autowired
    private MyResourceService myResourceService;
    @Autowired
    private GroupPartakeService groupPartakeService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private WxUserInfoService wxUserInfoService;
    @Autowired
    private LogisticsOrderCompanyService logisticsOrderCompanyService;
    @Autowired
    private GoodOrderService goodOrderService;
    @Autowired
    private GroupPartakeDeliverService groupPartakeDeliverService;
    @Autowired
    private GroupPartakePaymentService groupPartakePaymentService;
    public Map<String, Object> responseGroup(GoodGroup goodGroup){
        Map<String, Object> responseGroup = new HashMap<String, Object>();
        Good good = goodService.findOne(goodGroup.getGoodId());
        String headImg = good.getHeadImg();
        MyResource myResource = myResourceService.findOne(headImg);
        responseGroup.put("id",goodGroup.getId());
        responseGroup.put("goodHeadImgUrl",myResource.getUrl());
        responseGroup.put("goodName", good.getName());
        SystemGoodType goodType = goodTypeService.findOne(good.getGoodType());
        responseGroup.put("goodTypeName", goodType.getName());
        responseGroup.put("groupHead", goodGroup.getGroupHead());
        Customer customer = customerService.findOne(goodGroup.getGroupHead());
        UserInfo userInfo = wxUserInfoService.findByOpenid(customer.getOpenId());
        responseGroup.put("groupHeadUserName",userInfo.getNickname());
        responseGroup.put("groupHeadImgUrl",userInfo.getHeadimgurl());
        responseGroup.put("result", goodGroup.getResult());
        responseGroup.put("num", goodGroup.getNum());
        responseGroup.put("startTime", goodGroup.getStartTime());
        responseGroup.put("timeLong", goodGroup.getTimeLong());
        responseGroup.put("totalPrice", goodGroup.getTotalPrice());
        int groupCount = groupPartakeService.countByGroupId(goodGroup.getId());
        responseGroup.put("groupCount", groupCount+"");
        return responseGroup;
    }
    
    @RequestMapping("/manager/derelect_group")
    public String derelectGroup(HttpServletRequest httpServletRequest){
    	String pageParam = httpServletRequest.getParameter("page");
        String sizeParam = httpServletRequest.getParameter("size");
        String statusParam = httpServletRequest.getParameter("status");
        String groupId = httpServletRequest.getParameter("group_id");
        int page = 1;
        int size = 10;
        int status = 1;
        if(pageParam!=null){
            page = Integer.parseInt(pageParam);
        }
        if(sizeParam!=null){
            size = Integer.parseInt(sizeParam);
        }
        
        if(statusParam!=null){
        	status = Integer.parseInt(statusParam);
        }
        GoodGroup goodGroup = goodGroupService.findOne(groupId);
        goodGroup.setResult(GoodGroup.DERELICT_RESULT);
        goodGroup = goodGroupService.save(goodGroup);
        return "redirect:/manager/groups?page="+page+"&size="+size+"&status="+status;
        		
    }
    
    public Map<String, Object> responseOrder (Good good , GroupPartake groupPartake){
        Map<String, Object> responseOrder = new HashMap<String, Object>();
        GroupPartakeDeliver groupPartakeDeliver = groupPartakeDeliverService.findByGroupPartakeId(groupPartake.getId());
        GroupPartakePayment groupPartakePayment = groupPartakePaymentService.findByGroupPartakeId(groupPartake.getId());
        String headImg = good.getHeadImg();
        MyResource myResource = myResourceService.findOne(headImg);
        responseOrder.put("goodHeadImgUrl",myResource.getUrl());
        responseOrder.put("goodName", good.getName());
        SystemGoodType goodType = goodTypeService.findOne(good.getGoodType());
        responseOrder.put("goodTypeName", goodType.getName());
        if(groupPartakePayment!=null){
	        responseOrder.put("payStatus", groupPartakePayment.getStatus());
	        responseOrder.put("payTime", groupPartakePayment.getPayTime());
	        responseOrder.put("refundAmount", groupPartakePayment.getRefundAmount());
	        responseOrder.put("refundTime", groupPartakePayment.getRefundTime());
	        responseOrder.put("cost",groupPartakePayment.getCost());
        }
        if(groupPartakeDeliver!=null){
	        responseOrder.put("deliverStatus", groupPartakeDeliver.getStatus());
	
	        responseOrder.put("deliverTime", groupPartakeDeliver.getDeviceTime());
        }
        
        responseOrder.put("address",groupPartake.getCustomerAddress());
        
        responseOrder.put("status",groupPartake.getStatus());
        responseOrder.put("groupPartakeId", groupPartake.getId());
        responseOrder.put("type", groupPartake.getType());
        responseOrder.put("role", groupPartake.getRole());
        responseOrder.put("dataTime", groupPartake.getDateTime());
        responseOrder.put("customerId", groupPartake.getCustomerid());
        return responseOrder;
    }
    
    @RequestMapping("/manager/groups")
    public String groups(HttpServletRequest httpServletRequest){
        String pageParam = httpServletRequest.getParameter("page");
        String sizeParam = httpServletRequest.getParameter("size");
        String statusParam = httpServletRequest.getParameter("status");
        String groupId = httpServletRequest.getParameter("group_id");
        int page = 1;
        int size = 10;
        int status = 1;
        if(pageParam!=null){
            page = Integer.parseInt(pageParam);
        }
        if(sizeParam!=null){
            size = Integer.parseInt(sizeParam);
        }
        
        if(statusParam!=null){
        	status = Integer.parseInt(statusParam);
        }
        Subject subject = SecurityUtils.getSubject();
        String username = subject.getPrincipal()+"";
        Admin admin = adminService.findByUsername(username);
        Page<GoodGroup> goodGroups = goodGroupService.findAllByAdminIdAndResultOrderByUpdateAtDesc(admin.getId()+"",status,page,size);
        List<Map<String, Object>> responseGroups = new ArrayList<Map<String,Object>>();
        boolean flag = false;
        for(GoodGroup goodGroup:goodGroups.getContent()){
        	if(!flag){
        		flag = true;
        		if(groupId==null){
        			groupId = goodGroup.getId();
        		}
        	}
            Map<String, Object> responseGroup = responseGroup(goodGroup);
            responseGroups.add(responseGroup);
        }
        if(groupId!=null){
	        Iterable<GroupPartake> groupPartakes = groupPartakeService.findAllByGroupIdOrderByDateTime(groupId);
	        
	        List<Map<String, Object>> responsePartakes = new ArrayList<Map<String,Object>>();
	        GoodGroup goodGroup = goodGroupService.findOne(groupId);
	        Good good = goodService.findOne(goodGroup.getGoodId());
	        for(GroupPartake groupPartake:groupPartakes){
	        	Map<String,Object> responsePartake = responseOrder(good, groupPartake);
	            responsePartakes.add(responsePartake);
	        }
	        
	        httpServletRequest.setAttribute("orders", responsePartakes);
        }
        
        
        httpServletRequest.setAttribute("groups", responseGroups);

        httpServletRequest.setAttribute("totalElements", goodGroups.getTotalElements());
        httpServletRequest.setAttribute("isFirst", goodGroups.isFirst());
        httpServletRequest.setAttribute("totalPage", goodGroups.getTotalPages());
        httpServletRequest.setAttribute("page", page);
        httpServletRequest.setAttribute("count", goodGroups.getSize());
        httpServletRequest.setAttribute("status", status);
        
        
        Iterable<LogisticsOrderCompany> logisticsOrderCompanys = logisticsOrderCompanyService.findAll();
    	httpServletRequest.setAttribute("companys", logisticsOrderCompanys);
        return "group/groups";
    }
    
    @RequestMapping("/manager/partakes")
    public String partakes(HttpServletRequest httpServletRequest){
        String groupId = httpServletRequest.getParameter("group_id");
        Iterable<GroupPartake> groupPartakes = groupPartakeService.findAllByGroupIdOrderByDateTime(groupId);
        List<Map<String, Object>> responsePartakes = new ArrayList<Map<String,Object>>();
        for(GroupPartake groupPartake:groupPartakes){
            Map<String, Object> responsePartake = new HashMap<String, Object>();
            Customer customer = customerService.findOne(groupPartake.getCustomerid());
            UserInfo userInfo = wxUserInfoService.findByOpenid(customer.getOpenId());
            responsePartake.put("nickname", userInfo.getNickname());
            responsePartake.put("personName", groupPartake.getPersonName());
            responsePartake.put("address", groupPartake.getCustomerAddress());
            responsePartake.put("phonenumber", groupPartake.getPhonenumber());
            responsePartake.put("role", groupPartake.getRole());
            responsePartake.put("datetime", groupPartake.getDateTime());
            responsePartakes.add(responsePartake);
        }
        httpServletRequest.setAttribute("partakes", responsePartakes);
        return "group/groupInfoGrid";
    }
}
