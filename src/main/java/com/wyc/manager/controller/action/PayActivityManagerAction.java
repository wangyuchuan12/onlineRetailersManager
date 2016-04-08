package com.wyc.manager.controller.action;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wyc.domain.Good;
import com.wyc.domain.GroupSuccessGiveOpenGroupCouponActivity;
import com.wyc.domain.SystemPayActivity;
import com.wyc.domain.SystemPayHandler;
import com.wyc.manager.domain.Admin;
import com.wyc.manager.service.AdminService;
import com.wyc.service.GoodService;
import com.wyc.service.GroupSuccessGiveOpenGroupCouponActivityService;
import com.wyc.service.PayActivityService;
import com.wyc.service.PayHandlerService;

@Controller
public class PayActivityManagerAction {
    @Autowired
    private AdminService adminService;
    @Autowired
    private GoodService goodService;
    @Autowired
    private PayActivityService payActivityService;
    @Autowired
    private PayHandlerService payHandlerService;
    @Autowired
    private GroupSuccessGiveOpenGroupCouponActivityService groupSuccessGiveOpenGroupCouponActivityService;
    @RequestMapping("/manager/activity_add_do")
    @Transactional
    public String activityAddDo(HttpServletRequest httpServletRequest){
        String activityGood = httpServletRequest.getParameter("activity_good");
        String payHandler = httpServletRequest.getParameter("pay_handler");
        String giveGood = httpServletRequest.getParameter("give_good");
        String timeLong = httpServletRequest.getParameter("time_long");
        String payType = httpServletRequest.getParameter("payType");
        SystemPayActivity systemPayActivity = new SystemPayActivity();
        systemPayActivity.setGoodId(activityGood);
        systemPayActivity.setHandlers(payHandler);
        Subject subject = SecurityUtils.getSubject();
        Admin admin = adminService.findByUsername((String)subject.getPrincipal());
        systemPayActivity.setAdminId(admin.getId()+"");
        systemPayActivity.setUserOpenIds("*");
        systemPayActivity.setPayType(Integer.parseInt(payType));
        systemPayActivity = payActivityService.add(systemPayActivity);
        
        GroupSuccessGiveOpenGroupCouponActivity giveOpenGroupCouponActivity = new GroupSuccessGiveOpenGroupCouponActivity();
        giveOpenGroupCouponActivity.setActivityId(systemPayActivity.getId());
        giveOpenGroupCouponActivity.setGoodId(giveGood);
        giveOpenGroupCouponActivity.setOpenGroupCouponNum("1");
        giveOpenGroupCouponActivity.setTimeLong(Long.parseLong(timeLong));
        giveOpenGroupCouponActivity = groupSuccessGiveOpenGroupCouponActivityService.add(giveOpenGroupCouponActivity);
        return "redirect:/manager/activities";
    }
    
    @RequestMapping("/manager/activity_del_do")
    @Transactional
    public String activityDelDo(HttpServletRequest httpServletRequest){
        String activityId = httpServletRequest.getParameter("id");
        SystemPayActivity systemPayActivity = payActivityService.findOne(activityId);
        Iterable<GroupSuccessGiveOpenGroupCouponActivity> groupSuccessGiveOpenGroupCouponActivity = groupSuccessGiveOpenGroupCouponActivityService.findAllByActivityId(systemPayActivity.getId());
        payActivityService.delete(systemPayActivity);
        for(GroupSuccessGiveOpenGroupCouponActivity giveOpenGroupCouponActivity:groupSuccessGiveOpenGroupCouponActivity){
            groupSuccessGiveOpenGroupCouponActivityService.delete(giveOpenGroupCouponActivity);
        }
        return "redirect:/manager/activities";
    }
    
    @RequestMapping("/manager/activity_add_view")
    public String activityAddView(HttpServletRequest httpServletRequest){
        Subject subject = SecurityUtils.getSubject();
        Admin admin = adminService.findByUsername((String)subject.getPrincipal());
        String[] roles = admin.getRoles().split(",");
        boolean isGod = false;
        for(String role:roles){
            if(role.equals("god")){
                isGod = true;
                break;
            }
        }
        Iterable<Good> goods = null;
        if(isGod){
            goods = goodService.findAll();
        }else{
            goods = goodService.findAllByAdminId(admin.getId()+"");
        }
        httpServletRequest.setAttribute("goods", goods);
        Iterable<SystemPayHandler> payHandlers = payHandlerService.findAll();
        httpServletRequest.setAttribute("handlers", payHandlers);
        return "system/payActivityAdd";
    }
    @RequestMapping("/manager/activities")
    public String activities(HttpServletRequest httpServletRequest){
        Subject subject = SecurityUtils.getSubject();
        Admin admin = adminService.findByUsername((String)subject.getPrincipal());
        String[] roles = admin.getRoles().split(",");
        boolean isGod = false;
        for(String role:roles){
            if(role.equals("god")){
                isGod = true;
                break;
            }
        }
        List<Map<String, Object>> responseActivities = new ArrayList<Map<String,Object>>();
        Iterable<SystemPayActivity> systemPayActivities = null;
        if(!isGod){
            systemPayActivities = payActivityService.findAllByAdminId(admin.getId()+"");
        }else{
            systemPayActivities = payActivityService.findAll();
        }
        for(SystemPayActivity systemPayActivity:systemPayActivities){
            Map<String, Object> responseActivity = new HashMap<String, Object>();
            responseActivity.put("id", systemPayActivity.getId());
            responseActivity.put("userOpenIds", systemPayActivity.getUserOpenIds());
            String goodId = systemPayActivity.getGoodId();
            Good good = goodService.findOne(goodId);
            String handlerIds = systemPayActivity.getHandlers();
            int payType = systemPayActivity.getPayType();
            responseActivity.put("goodName", good.getName());
            responseActivity.put("payType", payType);
            responseActivity.put("handlerIds", handlerIds);
            StringBuffer handlerBuffer = new StringBuffer();
            for(String handlerId:handlerIds.split(",")){
                SystemPayHandler systemPayHandler = payHandlerService.findOne(handlerId);
                handlerBuffer.append(systemPayHandler.getName()+",");
            }
            if(handlerBuffer.toString().contains(",")){
                handlerBuffer.deleteCharAt(handlerBuffer.lastIndexOf(","));
            }
            responseActivity.put("handlerNames", handlerBuffer.toString());
            responseActivities.add(responseActivity);
        }
        httpServletRequest.setAttribute("activites", responseActivities);
        return "system/payActivities";
        
    }
}
