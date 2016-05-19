package com.wyc.wx.service;
import java.io.StringReader;
import java.util.Calendar;
import java.util.Random;
import java.util.TreeMap;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.service.WxPaySuccessService;
import com.wyc.util.MD5Util;
import com.wyc.util.Request;
import com.wyc.util.RequestFactory;
import com.wyc.util.Response;
import com.wyc.wx.domain.WxContext;
import com.wyc.wx.response.domain.PaySuccess;

@Service
public class WxPayService {
    @Autowired
    private WxContext wxContext;
    @Autowired
    private WxPaySuccessService wxPaySuccessService;
    @Autowired
    private RequestFactory requestFactory;
    public PaySuccess refund(String outTradeNo)throws Exception{
        Calendar now = Calendar.getInstance();
        String appid = wxContext.getAppid();
        String mchId = wxContext.getMchId();
        String nonceStr = "1add1a30ac87aa2db72f57a2375d8fec";
        PaySuccess paySuccess = wxPaySuccessService.findByOutTradeNo(outTradeNo);
        String refundFee = paySuccess.getTotalFee();
        String totalFee = paySuccess.getTotalFee();
        String outRefundNo = now.get(Calendar.YEAR)
                +"-"+(now.get(Calendar.MONTH) + 1)
                +"-"+now.get(Calendar.DAY_OF_MONTH)
                +"-"+now.get(Calendar.HOUR_OF_DAY)
                +"-"+now.get(Calendar.MINUTE)
                +"-"+now.get(Calendar.SECOND)
                +"-"+new Random().nextInt(1000)+"";
        TreeMap<String, String> map = new TreeMap<String, String>();
        map.put("appid", appid);
        map.put("mch_id", mchId);
        map.put("nonce_str", nonceStr);
        map.put("op_user_id", mchId);
        map.put("out_refund_no", outRefundNo);
        map.put("out_trade_no", outTradeNo);
        map.put("refund_fee", refundFee);
        map.put("total_fee", totalFee);
      //  map.put("transaction_id","");
        String sign = MD5Util.createMd5Sign(map,wxContext.getKey()).toUpperCase();
        
        
        StringBuffer sb2 = new StringBuffer();
        sb2.append("<xml>");
        sb2.append("<appid>"+appid+"</appid>");
        sb2.append("<mch_id>"+mchId+"</mch_id>");
        sb2.append("<nonce_str>"+nonceStr+"</nonce_str>");
        sb2.append("<op_user_id>"+mchId+"</op_user_id>");
        sb2.append("<out_refund_no>"+outRefundNo+"</out_refund_no>");
        sb2.append("<out_trade_no>"+outTradeNo+"</out_trade_no>");
        sb2.append("<refund_fee>"+refundFee+"</refund_fee>");
        sb2.append("<total_fee>"+totalFee+"</total_fee>");
        
        sb2.append("<transaction_id></transaction_id>");
        sb2.append("<sign>"+sign+"</sign>");
        sb2.append("</xml>");
        
        Request request = requestFactory.getRefundRequest();
        Response response = request.post(sb2.toString());
        String content = response.read();
        SAXBuilder saxBuilder = new SAXBuilder();
        Document document = saxBuilder.build(new StringReader(content));
        Element rootElement = document.getRootElement();
        String resultCode = rootElement.getChildText("result_code");
        if(resultCode.equals("SUCCESS")){
            paySuccess.setIsRefund(1);
            paySuccess = wxPaySuccessService.save(paySuccess);
        }else{
            throw new RuntimeException("退款失败");
        }
        return paySuccess;
    }
}
