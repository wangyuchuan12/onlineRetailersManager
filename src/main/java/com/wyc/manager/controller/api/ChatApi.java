package com.wyc.manager.controller.api;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wyc.domain.Business;
import com.wyc.domain.Customer;
import com.wyc.domain.DialogSession;
import com.wyc.domain.DialogSessionItem;
import com.wyc.domain.DialogSessionItemRead;
import com.wyc.manager.domain.Admin;
import com.wyc.manager.service.AdminService;
import com.wyc.service.BusinessService;
import com.wyc.service.CustomerService;
import com.wyc.service.DialogSessionItemReadService;
import com.wyc.service.DialogSessionItemService;
import com.wyc.service.DialogSessionService;
import com.wyc.service.WxUserInfoService;

@RestController
public class ChatApi {
    @Autowired
    private DialogSessionService dialogSessionService;
    @Autowired
    private DialogSessionItemService dialogSessionItemService;
    @Autowired
    private CustomerService customerService;
    private SimpleDateFormat mySimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    @Autowired
    private DialogSessionItemReadService dialogSessionItemReadService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private WxUserInfoService wxUserInfoService;
    @Autowired
    private BusinessService businessService;
    @RequestMapping("/api/chat/select_not_read")
    public Object selectNotRead(HttpServletRequest httpServletRequest){
        Subject subject = SecurityUtils.getSubject();
        String username = subject.getPrincipal()+"";
        Admin admin = adminService.findByUsername(username);
        String customerId = httpServletRequest.getParameter("customer_id");
        DialogSession dialogSession = dialogSessionService.findByCustomerIdAndAdminId(customerId, admin.getId()+"");
        if(dialogSession!=null){
            Iterable<DialogSessionItem> dialogSessionItems = dialogSessionItemService.findAllByDialogSessionIdOrderByRecordIndexAsc(dialogSession.getId());
           
            List<Object> notReads = new ArrayList<Object>();
            Map<String, Object> responseData = new HashMap<>();
            for(DialogSessionItem dialogSessionItem:dialogSessionItems){
                DialogSessionItemRead dialogSessionItemRead = dialogSessionItemReadService.findByAdminIdAndRoleAndItemId(admin.getId()+"", DialogSessionItem.ADMIN_ROLE, dialogSessionItem.getId());
                if(dialogSessionItemRead==null||dialogSessionItemRead.getCount()==0){
                    
                	dialogSessionItemRead = new DialogSessionItemRead();
                	dialogSessionItemRead.setAdminId(admin.getId()+"");
                	dialogSessionItemRead.setCount(1);
                	dialogSessionItemRead.setDateTime(new DateTime());
                	dialogSessionItemRead.setCustomerId(customerId);
                	dialogSessionItemRead.setItemId(dialogSessionItem.getId());
                	dialogSessionItemRead.setRole(DialogSessionItem.ADMIN_ROLE);
                	dialogSessionItemReadService.add(dialogSessionItemRead);
                	Map<String, Object> dialogSessionItemResponse = new HashMap<String, Object>();
                	dialogSessionItemResponse.put("content", dialogSessionItem.getContent());
                	dialogSessionItemResponse.put("headImg", dialogSessionItem.getHeadImg());
                	dialogSessionItemResponse.put("recordIndex", dialogSessionItem.getRecordIndex());
                	dialogSessionItemResponse.put("role", dialogSessionItem.getRole());
                	dialogSessionItemResponse.put("type", dialogSessionItem.getType());
                	dialogSessionItemResponse.put("dateTime",mySimpleDateFormat.format(dialogSessionItem.getDateTime().toDate()));
                	notReads.add(dialogSessionItemResponse);
                }
            }
            if(notReads.size()==0){
                return null;
            }
            
            responseData.put("notReadItems", notReads);
            responseData.put("notReadCount", notReads.size());
            return responseData;
        }
        return null;
        
    }
    
    @RequestMapping("/api/chat/send_message")
    public Object sendMessage(HttpServletRequest httpServletRequest){
        Subject subject = SecurityUtils.getSubject();
        String username = subject.getPrincipal()+"";
        Admin admin = adminService.findByUsername(username);
        String adminId = admin.getId()+"";
        String content = httpServletRequest.getParameter("content");
        String customerId = httpServletRequest.getParameter("customer_id");
        String goodId = httpServletRequest.getParameter("good_id");
        String orderId = httpServletRequest.getParameter("order_id");
        String type = httpServletRequest.getParameter("type");
        Customer customer = customerService.findOne(customerId);
        Business business = businessService.findByAdminId(admin.getId()+"");
        DialogSession dialogSession = dialogSessionService.findByCustomerIdAndAdminId(customerId, adminId);
        if(dialogSession==null){
            dialogSession = new DialogSession();
            dialogSession.setAdminId(adminId);
            dialogSession.setCustomerId(customerId);
            dialogSession = dialogSessionService.add(dialogSession);
        }
        String headImg = business.getImgUrl();
        DialogSessionItem dialogSessionItem = new DialogSessionItem();
        dialogSessionItem.setContent(content);
        dialogSessionItem.setCustomerId(customer.getId());
        dialogSessionItem.setDateTime(new DateTime());
        dialogSessionItem.setDialogSessionId(dialogSession.getId());
        dialogSessionItem.setGoodId(goodId);
        dialogSessionItem.setAdminId(adminId);
        dialogSessionItem.setHeadImg(headImg);
        dialogSessionItem.setOrderId(orderId);
        dialogSessionItem.setRole(DialogSessionItem.CUSTOMER_ROLE);
        dialogSessionItem.setType(type);
        dialogSessionItem = dialogSessionItemService.add(dialogSessionItem);
        DialogSessionItemRead dialogSessionItemRead = new DialogSessionItemRead();
        dialogSessionItemRead.setCount(1);
        dialogSessionItemRead.setCustomerId(customer.getId());
        dialogSessionItemRead.setAdminId(adminId);
        dialogSessionItemRead.setDateTime(new DateTime());
        dialogSessionItemRead.setItemId(dialogSessionItem.getId());
        dialogSessionItemRead.setRole(DialogSessionItem.ADMIN_ROLE);
        dialogSessionItemReadService.add(dialogSessionItemRead);
        Map<String, String> responseObj = new HashMap<String, String>();
        responseObj.put("content", content);
        responseObj.put("headImg", headImg);
        
        responseObj.put("dateTime", mySimpleDateFormat.format(dialogSessionItem.getDateTime().toDate()));
        responseObj.put("role", dialogSessionItem.getRole()+"");
        return responseObj;
    }
}
