package com.wyc.wx.service;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.domain.GroupPartakeDeliver;
import com.wyc.domain.LogisticsOrderResult;
import com.wyc.domain.LogisticsOrderResultRecord;
import com.wyc.service.GroupPartakeDeliverService;
import com.wyc.service.LogisticsOrderResultRecordService;
import com.wyc.service.LogisticsOrderResultService;
import com.wyc.util.Request;
import com.wyc.util.RequestFactory;
import com.wyc.util.Response;
import com.wyc.wx.domain.JhOrder;
import com.wyc.wx.domain.JhOrderResultRecord;

@Service
public class JuheOrderService {
    @Autowired
    private RequestFactory requestFactory;
    @Autowired
    private LogisticsOrderResultRecordService logisticsOrderResultRecordService;
    @Autowired
    private LogisticsOrderResultService logisticsOrderResultService;
    @Autowired
    private GroupPartakeDeliverService groupPartakeDeliverService;
    private String key = "7c98eaca9bab8dbd9d1f5005a3febb0b";

    private SimpleDateFormat mySimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    
    final static Logger logger = LoggerFactory.getLogger(JuheOrderService.class);
    public JhOrder getJhOrder(String com , String no){
        try {
            Request request = requestFactory.getJuhuRequest(key, com, no);
            Response response = request.post("");
            JhOrder jhOrder = response.readObject(JhOrder.class);
            return jhOrder;
        } catch (Exception e) {
        	logger.error("run juheorder task has an error {}",e);
            return null;
        }
        
    }
    
    public JhOrder updateLogistics(JhOrder jhOrder)throws Exception{
        String com = jhOrder.getResult().getCom();
        String no = jhOrder.getResult().getNo();
        LogisticsOrderResult logisticsOrderResult = logisticsOrderResultService.findByComAndNo(com,no);
        List<JhOrderResultRecord> jhOrderResultRecords = jhOrder.getResult().getList();
        if(logisticsOrderResult==null){
            logisticsOrderResult = new LogisticsOrderResult();
            logisticsOrderResult.setCom(com);
            logisticsOrderResult.setNo(no);
            logisticsOrderResult.setCompany(com);
            logisticsOrderResult.setStatus(jhOrder.getResult().getStatus());
            logisticsOrderResult = logisticsOrderResultService.add(logisticsOrderResult);
            
            if(jhOrderResultRecords!=null){
                int index = 0;
                for(JhOrderResultRecord jhOrderResultRecord:jhOrderResultRecords){
                    index++;
                    LogisticsOrderResultRecord logisticsOrderResultRecord = new LogisticsOrderResultRecord();
                    Date date = mySimpleDateFormat.parse(jhOrderResultRecord.getDatetime());
                    logisticsOrderResultRecord.setDatetime(new DateTime(date));
                    logisticsOrderResultRecord.setLogisticsOrderResultId(logisticsOrderResult.getId());
                    logisticsOrderResultRecord.setRecordIndex(index);
                    logisticsOrderResultRecord.setRemark(jhOrderResultRecord.getRemark());
                    logisticsOrderResultRecord.setZone(jhOrderResultRecord.getZone());
                    logisticsOrderResultRecordService.add(logisticsOrderResultRecord);
                }
            }
        }else if (logisticsOrderResult.getStatus().equals("0")) {
            logisticsOrderResult.setStatus(jhOrder.getResult().getStatus());
            logisticsOrderResult = logisticsOrderResultService.save(logisticsOrderResult);
            int maxRecordIndex = logisticsOrderResultRecordService.selectMaxIndexByLogisticsOrderResultId(logisticsOrderResult.getId());
            if(jhOrderResultRecords!=null){
                for(int i = maxRecordIndex;i<jhOrderResultRecords.size();i++){
                    JhOrderResultRecord jhOrderResultRecord = jhOrderResultRecords.get(i);
                    LogisticsOrderResultRecord logisticsOrderResultRecord = new LogisticsOrderResultRecord();
                    Date date = mySimpleDateFormat.parse(jhOrderResultRecord.getDatetime());
                    logisticsOrderResultRecord.setDatetime(new DateTime(date));
                    logisticsOrderResultRecord.setLogisticsOrderResultId(logisticsOrderResult.getId());
                    logisticsOrderResultRecord.setRecordIndex(i+1);
                    logisticsOrderResultRecord.setRemark(jhOrderResultRecord.getRemark());
                    logisticsOrderResultRecord.setZone(jhOrderResultRecord.getZone());
                    logisticsOrderResultRecordService.add(logisticsOrderResultRecord);
                }
            }
        }else if (logisticsOrderResult.getStatus().equals("1")) {
            GroupPartakeDeliver groupPartakeDeliver = groupPartakeDeliverService.findByLogisticsNoAndCom(no,com);
            if(groupPartakeDeliver!=null){
                JhOrderResultRecord jhOrderResultRecord = jhOrderResultRecords.get(jhOrderResultRecords.size()-1);
                Date date = mySimpleDateFormat.parse(jhOrderResultRecord.getDatetime());
                groupPartakeDeliver.setSignTime(new DateTime(date));
                groupPartakeDeliver.setStatus(2);
                groupPartakeDeliverService.save(groupPartakeDeliver);
            }
        }
        return jhOrder;
    }
}
