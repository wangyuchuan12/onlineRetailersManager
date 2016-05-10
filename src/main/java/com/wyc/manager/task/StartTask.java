package com.wyc.manager.task;
import java.lang.reflect.Method;
import java.util.Timer;
import java.util.TimerTask;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;




import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import com.wyc.manager.domain.ServiceHandler;
import com.wyc.manager.service.ServiceHandlerService;


public class StartTask extends Thread{
    @Autowired
    private ServiceHandlerService serviceHandlerService;
    
    @Autowired
    private AutowireCapableBeanFactory factory;
    
    final static Logger logger = LoggerFactory.getLogger(StartTask.class);
    public void run(){
        
        Timer timer = new Timer(true);
        TimerTask timerTask = new TimerTask() {
            
            @Override
            public void run() {
                Iterable<ServiceHandler> serviceHandlers = serviceHandlerService.findAll();
                for(ServiceHandler serviceHandler:serviceHandlers){
                    setIfNum(serviceHandler);
                    if(serviceHandler.getType()==ServiceHandler.ONCE_TYPE){
                        
                        executeOnce(serviceHandler);
                    }else if (serviceHandler.getType()==ServiceHandler.LOOP_TIMER_TYPE) {
                        executeLoopTimer(serviceHandler);
                    }else if (serviceHandler.getType()==ServiceHandler.LOOP_TYPE) {
                        executeLoop(serviceHandler);
                    }
                }
                
            }

            private void executeLoopTimer(ServiceHandler serviceHandler) {
                try {
                    DateTime startDateTime = serviceHandler.getStartTime();
                    DateTime now = DateTime.now(DateTimeZone.forID("Asia/Shanghai"));
                    DateTime lastRunTime = serviceHandler.getLastRunTime();
                    if(startDateTime.toDate().getTime()<now.toDate().getTime()
                            &&now.getHourOfDay()==serviceHandler.getHour()&&
                            lastRunTime!=null&&
                            lastRunTime.getDayOfMonth()!=now.getDayOfMonth()){
                        Class<?> clazz = Class.forName(serviceHandler.getClassPath());
                        Object target = clazz.newInstance();
                        Method method = clazz.getDeclaredMethod("run");
                        factory.autowireBean(target);
                        method.setAccessible(true);
                        method.invoke(target);
                        afterExecute(serviceHandler);
                    }
                } catch (Exception e) {
                    logger.error("run {} task {} has an error","once",serviceHandler.getClassPath());
                    serviceHandler.setErrorCount(serviceHandler.getErrorCount()+1);
                    serviceHandlerService.save(serviceHandler);
                    e.printStackTrace();
                }
                
            }

            private void executeLoop(ServiceHandler serviceHandler) {
                serviceHandler.setCounter(serviceHandler.getCounter()+1);
                try {
                    DateTime startDateTime = serviceHandler.getStartTime();
                    DateTime now = DateTime.now(DateTimeZone.forID("Asia/Shanghai"));
                    if(startDateTime.toDate().getTime()<now.toDate().getTime()){
                        if(serviceHandler.getCounter()%serviceHandler.getLoop()==0){
                            Class<?> clazz = Class.forName(serviceHandler.getClassPath());
                            Object target = clazz.newInstance();
                            Method method = clazz.getDeclaredMethod("run");
                            factory.autowireBean(target);
                            method.setAccessible(true);
                            method.invoke(target);
                            afterExecute(serviceHandler);
                            serviceHandler.setCounter(0l);
                        }
                    }
                } catch (Exception e) {
                    logger.error("run {} task {} has an error","once",serviceHandler.getClassPath());
                    serviceHandler.setErrorCount(serviceHandler.getErrorCount()+1);
                    serviceHandlerService.save(serviceHandler);
                    e.printStackTrace();
                }
                serviceHandlerService.save(serviceHandler);
            }

            private void executeOnce(ServiceHandler serviceHandler) {
                try {
                    if(serviceHandler.getRunCount()==0){

                        DateTime startDateTime = serviceHandler.getStartTime();
                        DateTime now = DateTime.now(DateTimeZone.forID("Asia/Shanghai"));
                        if(startDateTime.toDate().getTime()<now.toDate().getTime()){
                            Class<?> clazz = Class.forName(serviceHandler.getClassPath());
                            Object target = clazz.newInstance();
                            Method method = clazz.getDeclaredMethod("run");
                            factory.autowireBean(target);
                            method.setAccessible(true);
                            method.invoke(target);
                            afterExecute(serviceHandler);
                        }
                    }
                } catch (Exception e) {
                    logger.error("run task {} has an error",serviceHandler.getClassPath());
                    serviceHandler.setErrorCount(serviceHandler.getErrorCount()+1);
                    serviceHandlerService.save(serviceHandler);
                    e.printStackTrace();
                }
                
             
                
                
            }
            
            private void afterExecute(ServiceHandler serviceHandler) {
                serviceHandler.setRunCount(serviceHandler.getRunCount()+1);
                serviceHandler.setLastRunTime(new DateTime());
                serviceHandlerService.save(serviceHandler);
                
            }
        };
        timer.scheduleAtFixedRate(timerTask, 5000, 1000);
    }
    
    private ServiceHandler setIfNum(ServiceHandler serviceHandler){
        if(serviceHandler.getErrorCount()==null){
            serviceHandler.setErrorCount(0l);
        }
        if(serviceHandler.getHour()==null){
            serviceHandler.setHour(2l);
        }
        if(serviceHandler.getRunCount()==null){
            serviceHandler.setRunCount(0l);
        }
        if(serviceHandler.getStartTime()==null){
            serviceHandler.setStartTime(new DateTime());
        }
        if(serviceHandler.getCounter()==null){
            serviceHandler.setCounter(0l);
        }
        if(serviceHandler.getLoop()==null){
            serviceHandler.setLoop(1l);
        }
        return serviceHandler;
    }
}
