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
                }
                
            }

            private void executeLoop(ServiceHandler serviceHandler) {
                try {
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
                } catch (Exception e) {
                    logger.error("run {} task {} has an error","once",serviceHandler.getClassPath());
                    serviceHandler.setErrorCount(serviceHandler.getErrorCount()+1);
                    serviceHandlerService.save(serviceHandler);
                }
                
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
}
