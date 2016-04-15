package com.wyc.manager.controller.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
import com.wyc.wx.domain.UserInfo;

@Controller
public class MessageManagerAction {
    @Autowired
    private DialogSessionService dialogSessionService;
    @Autowired
    private DialogSessionItemService dialogSessionItemService;
    @Autowired
    private DialogSessionItemReadService dialogSessionItemReadService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private WxUserInfoService wxUserInfoService;
    @Autowired
    private BusinessService businessService;
    @RequestMapping("/manager/chat_view")
    public String chatView(HttpServletRequest httpServletRequest){
        String customerId = httpServletRequest.getParameter("customer_id");
        String adminId = httpServletRequest.getParameter("admin_id");
        Business business = businessService.findByAdminId(adminId);
        Customer customer = customerService.findOne(customerId);
        UserInfo userInfo = wxUserInfoService.findByOpenid(customer.getOpenId());
        DialogSession dialogSession = dialogSessionService.findByCustomerIdAndAdminId(customerId,adminId);
        if(dialogSession!=null){
            Iterable<DialogSessionItem> dialogSessionItems = dialogSessionItemService.findAllByDialogSessionIdOrderByRecordIndexAsc(dialogSession.getId());
            httpServletRequest.setAttribute("dialogSession", dialogSession);
            httpServletRequest.setAttribute("dialogSessionItems", dialogSessionItems);
            for(DialogSessionItem dialogSessionItem:dialogSessionItems){
                DialogSessionItemRead dialogSessionItemRead = dialogSessionItemReadService.findByAdminIdAndRoleAndItemId(adminId,DialogSessionItem.ADMIN_ROLE,dialogSessionItem.getId());
                if(dialogSessionItemRead!=null){
                    dialogSessionItemRead.setDateTime(new DateTime());
                    dialogSessionItemRead.setCount(dialogSessionItemRead.getCount()+1);
                    dialogSessionItemReadService.save(dialogSessionItemRead);
                }else{
                    dialogSessionItemRead = new DialogSessionItemRead();
                    dialogSessionItemRead.setDateTime(new DateTime());
                    dialogSessionItemRead.setAdminId(adminId);
                    dialogSessionItemRead.setCustomerId(customerId);
                    dialogSessionItemRead.setRole(DialogSessionItem.ADMIN_ROLE);
                    dialogSessionItemRead.setCount(1);
                    
                    dialogSessionItemRead.setItemId(dialogSessionItem.getId());
                    dialogSessionItemReadService.add(dialogSessionItemRead);
                }
            }
        }
        httpServletRequest.setAttribute("userInfo",userInfo);
        httpServletRequest.setAttribute("business", business);
        httpServletRequest.setAttribute("customerId",customerId);
        return "message/chatView";
    }
    
    @RequestMapping("/manager/messages")
    public String messages(HttpServletRequest httpServletRequest){
        Subject subject = SecurityUtils.getSubject();
        String username = subject.getPrincipal()+"";
        Admin admin = adminService.findByUsername(username);
        List<Map<String, Object>> responseDialogSessions = new ArrayList<Map<String,Object>>();
        Iterable<DialogSession> dialogSessions = dialogSessionService.findAllByAdminId(admin.getId()+"");
        for(DialogSession dialogSession:dialogSessions){
            Map<String, Object> responseDialogSession = new HashMap<String, Object>();
            Customer customer = customerService.findOne(dialogSession.getCustomerId());
            UserInfo userInfo = wxUserInfoService.findByOpenid(customer.getOpenId());
            responseDialogSession.put("customerId", customer.getId());
            responseDialogSession.put("adminId", admin.getId());
            responseDialogSession.put("nickname", userInfo.getNickname());
            responseDialogSession.put("headimgurl", userInfo.getHeadimgurl());
            int receiveCount = dialogSessionItemService.countByDialogSessionIdAndAdminIdAndRole(dialogSession.getId(),admin.getId()+"",DialogSessionItem.ADMIN_ROLE);
            int sendCount = dialogSessionItemService.countByDialogSessionIdAndAdminIdAndRole(dialogSession.getId(),admin.getId()+"",DialogSessionItem.CUSTOMER_ROLE);
            int readCount = dialogSessionItemReadService.countByCustomerIdAndAdminIdAndRoleAndCountGreaterThan(customer.getId(), admin.getId()+"", DialogSessionItem.ADMIN_ROLE, 0);
            responseDialogSession.put("receiveCount", receiveCount);
            responseDialogSession.put("sendCount", sendCount);
            responseDialogSession.put("readCount", readCount);
            responseDialogSessions.add(responseDialogSession);
        }
        httpServletRequest.setAttribute("dialogSessions", responseDialogSessions);
        return "message/messages";
    }
}
