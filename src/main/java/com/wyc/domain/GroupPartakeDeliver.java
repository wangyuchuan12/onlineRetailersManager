package com.wyc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
@Entity(name = "group_partake_deliver")
public class GroupPartakeDeliver {
    @Id
    private String id;
    //物流编号
    @Column(name="logistics_no")
    private String logisticsNo;
    //0表示未发货，1表示已发货，2表示已签收
    @Column
    private int status;
    @Column(name="group_partake_id")
    private String groupPartakeId;
    @Column(name = "refund_devivery_time")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime refundDeviveryTime;
    @Column(name = "refund_sign_time")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime refundSignTime;
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name="sign_time")
    private DateTime signTime;
    @Column(name="device_time")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime deviceTime;
    
    
    @Column
    private String remarks;
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    public DateTime getDeviceTime() {
        return deviceTime;
    }
    public void setDeviceTime(DateTime deviceTime) {
        this.deviceTime = deviceTime;
    }
    public DateTime getSignTime() {
        return signTime;
    }
    public void setSignTime(DateTime signTime) {
        this.signTime = signTime;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getLogisticsNo() {
        return logisticsNo;
    }
    public void setLogisticsNo(String logisticsNo) {
        this.logisticsNo = logisticsNo;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public String getGroupPartakeId() {
        return groupPartakeId;
    }
    public void setGroupPartakeId(String groupPartakeId) {
        this.groupPartakeId = groupPartakeId;
    }
    public DateTime getRefundDeviveryTime() {
        return refundDeviveryTime;
    }
    public void setRefundDeviveryTime(DateTime refundDeviveryTime) {
        this.refundDeviveryTime = refundDeviveryTime;
    }
    public DateTime getRefundSignTime() {
        return refundSignTime;
    }
    public void setRefundSignTime(DateTime refundSignTime) {
        this.refundSignTime = refundSignTime;
    }
    
}
