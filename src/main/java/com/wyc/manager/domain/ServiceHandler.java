package com.wyc.manager.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity(name="service_handler")
public class ServiceHandler {
    public final static int ONCE_TYPE = 0;
    public final static int LOOP_TYPE=1;
    public final static int LOOP_TIMER_TYPE=2;
    @Id
    private String id;
    @Column
    private String name;
    @Column(name="class_path")
    private String classPath;
    @Column(name="start_time")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime startTime;
    @Column
    private Long hour;
    @Column
    private int type;
    
    @Column(name = "create_at")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime createAt;
    @Column(name = "update_at")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime updateAt;
    @Column(name="run_count")
    private Long runCount;
    @Column(name="last_run_time")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime lastRunTime;
    @Column(name="error_count")
    private Long errorCount;
    @Column
    private Long counter=0l;
    @Column(name="aloop")
    private Long loop = 1l;
    
    
    
    
    public Long getRunCount() {
        return runCount;
    }
    public void setRunCount(Long runCount) {
        this.runCount = runCount;
    }
    public Long getErrorCount() {
        return errorCount;
    }
    public void setErrorCount(Long errorCount) {
        this.errorCount = errorCount;
    }
    public Long getCounter() {
        return counter;
    }
    public void setCounter(Long counter) {
        this.counter = counter;
    }
    public Long getLoop() {
        return loop;
    }
    public void setLoop(Long loop) {
        this.loop = loop;
    }
    public DateTime getLastRunTime() {
        return lastRunTime;
    }
    public void setLastRunTime(DateTime lastRunTime) {
        this.lastRunTime = lastRunTime;
    }
  
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getClassPath() {
        return classPath;
    }
    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }
    public DateTime getStartTime() {
        return startTime;
    }
    public void setStartTime(DateTime startTime) {
        this.startTime = startTime;
    }
   
    public Long getHour() {
        return hour;
    }
    public void setHour(Long hour) {
        this.hour = hour;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public DateTime getCreateAt() {
        return createAt;
    }
    public void setCreateAt(DateTime createAt) {
        this.createAt = createAt;
    }
    public DateTime getUpdateAt() {
        return updateAt;
    }
    public void setUpdateAt(DateTime updateAt) {
        this.updateAt = updateAt;
    }
    
}
