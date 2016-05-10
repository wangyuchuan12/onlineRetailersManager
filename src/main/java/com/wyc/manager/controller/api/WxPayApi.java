package com.wyc.manager.controller.api;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.wyc.domain.GroupPartakePayment;
import com.wyc.service.GroupPartakePaymentService;
import com.wyc.service.GroupPartakeService;
import com.wyc.wx.service.WxPayService;
@RestController
public class WxPayApi {
    @Autowired
    private WxPayService wxPayService;
    @Autowired
    private GroupPartakeService groupPartakeServcie;
    @Autowired
    private GroupPartakePaymentService groupPartakePaymentService;
    @RequestMapping("/manager/api/refund")
    @Transactional
    public Object refund(HttpServletRequest httpServletRequest)throws Exception{
        String groupPartakeId = httpServletRequest.getParameter("group_partake_id");
        GroupPartakePayment groupPartakePayment = groupPartakePaymentService.findByGroupPartakeId(groupPartakeId);
        String outTradeNo = groupPartakePayment.getOutTradeNo();
        wxPayService.refund(outTradeNo);
        groupPartakePayment.setStatus(2);
        groupPartakePaymentService.save(groupPartakePayment);
        return groupPartakePayment;
    }
}
