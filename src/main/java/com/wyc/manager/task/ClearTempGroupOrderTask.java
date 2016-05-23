package com.wyc.manager.task;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import com.wyc.domain.TempGroupOrder;
import com.wyc.domain.TemporaryData;
import com.wyc.service.TempGroupOrderService;
import com.wyc.service.TemporaryDataService;


public class ClearTempGroupOrderTask{
    @Autowired
    private TempGroupOrderService tempGroupOrderService;
    @Autowired
    private TemporaryDataService temporaryDataService;
    public void run() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR, -5);
        Iterable<TempGroupOrder> tempGroupOrders = tempGroupOrderService.findAllByUpdateAtLessThan(new DateTime(calendar.getTime()));
        for(TempGroupOrder tempGroupOrder:tempGroupOrders){
            tempGroupOrderService.delete(tempGroupOrder);
        }
        
        Iterable<TemporaryData> temporaryDatas = temporaryDataService.findAllByUpdateAtLessThan(new DateTime(calendar.getTime()));
        for(TemporaryData temporaryData:temporaryDatas){
            temporaryDataService.delete(temporaryData);
        }
    }
}
