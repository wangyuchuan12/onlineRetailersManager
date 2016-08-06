package com.wyc.manager.controller.api;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wyc.domain.GoodOrder;
import com.wyc.domain.GroupPartake;
import com.wyc.domain.GroupPartakePayment;
import com.wyc.manager.domain.Admin;
import com.wyc.manager.service.AdminService;
import com.wyc.service.GoodOrderService;
import com.wyc.service.GroupPartakePaymentService;
import com.wyc.service.GroupPartakeService;
import com.wyc.wx.response.domain.PaySuccess;
import com.wyc.wx.service.WxPayService;
@RestController
public class WxPayApi {
    @Autowired
    private WxPayService wxPayService;
    @Autowired
    private GroupPartakeService groupPartakeServcie;
    @Autowired
    private GroupPartakePaymentService groupPartakePaymentService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private GoodOrderService goodOrderService;
    @RequestMapping("/manager/api/refund")
    @Transactional
    public Object refund(HttpServletRequest httpServletRequest)throws Exception{
        Subject subject = SecurityUtils.getSubject();
        String username = subject.getPrincipal()+"";
        Admin admin = adminService.findByUsername(username);
        String groupPartakeId = httpServletRequest.getParameter("group_partake_id");
        GroupPartake groupPartake = groupPartakeServcie.findOne(groupPartakeId);
        GoodOrder goodOrder = goodOrderService.findOne(groupPartake.getOrderId());
        if(!goodOrder.getAdminId().equals(admin.getId()+"")||!subject.hasRole("god")){
            return null;
        }
        GroupPartakePayment groupPartakePayment = groupPartakePaymentService.findByGroupPartakeId(groupPartakeId);
        String outTradeNo = groupPartakePayment.getOutTradeNo();
        PaySuccess paySuccess = wxPayService.refund(outTradeNo);
        if(paySuccess!=null){
            groupPartakePayment.setRefundAmount(Float.parseFloat(paySuccess.getTotalFee()));
        }else{
            groupPartakePayment.setRefundAmount(0f);  
        }
        groupPartakePayment.setStatus(3);
        groupPartakePaymentService.save(groupPartakePayment);
        return groupPartakePayment;
    }
}
