package com.wyc.manager.controller.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wyc.domain.Customer;
import com.wyc.domain.Good;
import com.wyc.domain.GoodGroup;
import com.wyc.domain.GoodType;
import com.wyc.domain.MyResource;
import com.wyc.manager.domain.Admin;
import com.wyc.manager.service.AdminService;
import com.wyc.service.CustomerService;
import com.wyc.service.GoodGroupService;
import com.wyc.service.GoodService;
import com.wyc.service.GoodTypeService;
import com.wyc.service.GroupPartakeService;
import com.wyc.service.MyResourceService;
import com.wyc.service.WxUserInfoService;
import com.wyc.wx.domain.UserInfo;
import com.wyc.wx.service.UserService;

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
    public Map<String, Object> responseGroup(GoodGroup goodGroup){
        Map<String, Object> responseGroup = new HashMap<String, Object>();
        Good good = goodService.findOne(goodGroup.getGoodId());
        String headImg = good.getHeadImg();
        MyResource myResource = myResourceService.findOne(headImg);
        responseGroup.put("id",goodGroup.getId());
        responseGroup.put("goodHeadImgUrl",myResource.getUrl());
        responseGroup.put("goodName", good.getName());
        GoodType goodType = goodTypeService.findOne(good.getGoodType());
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
    @RequestMapping("/manager/groups")
    public String groups(HttpServletRequest httpServletRequest){
        Subject subject = SecurityUtils.getSubject();
        String username = subject.getPrincipal()+"";
        Admin admin = adminService.findByUsername(username);
        Iterable<GoodGroup> goodGroups = goodGroupService.findAllByAdminId(admin.getId()+"");
        List<Map<String, Object>> responseGroups = new ArrayList<Map<String,Object>>();
        for(GoodGroup goodGroup:goodGroups){
            Map<String, Object> responseGroup = responseGroup(goodGroup);
            responseGroups.add(responseGroup);
        }
        httpServletRequest.setAttribute("groups", responseGroups);
        return "group/groups";
    }
}
