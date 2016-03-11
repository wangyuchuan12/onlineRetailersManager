package com.wyc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
@Entity(name = "group_partake_payment")
public class GroupPartakePayment {
    @Id
    private String id;
    //总共付款
    @Column(name = "cost")
    private float cost;
    
    //退款金额
    @Column(name="refund_amount")
    private float refundAmount;
    
    //退款时间
    @Column(name = "refund_time")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime refundTime;
    
    //付款时间
    @Column(name="pay_time")
    private DateTime payTime;
    
    //0表示未付款 1已付款 2已退款
    @Column
    private int status;
    
    @Column(name="group_partake_id")
    private String groupPartakeId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public float getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(float refundAmount) {
        this.refundAmount = refundAmount;
    }

    public DateTime getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(DateTime refundTime) {
        this.refundTime = refundTime;
    }

    public DateTime getPayTime() {
        return payTime;
    }

    public void setPayTime(DateTime payTime) {
        this.payTime = payTime;
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
    
    
}
