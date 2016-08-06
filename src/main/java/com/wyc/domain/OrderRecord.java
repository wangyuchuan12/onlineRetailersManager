package com.wyc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity(name="order_record")
public class OrderRecord {
    //申请结算
    public static int APPLY_PAY = 0;
    //审核结算
    public static int AUDIT_PAY = 1;
    //发货处理
    public static int DELIVER = 2;
    //申请退款
    public static int APPLY_REFUND = 3;
    //退款操作
    public static int REFUND_HANDLER = 4;
    @Id
    private String id;
    //处理方式
    @Column
    private int way;
    @Column(name="group_partake_id")
    private String groupPartakeId;
    @Column(name="handler_admin")
    private String handlerAdmin;
    @Column
    private String remark;
    @Column(name = "create_at")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonIgnore
    private DateTime createAt;
    @Column(name = "update_at")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonIgnore
    private DateTime updateAt;
    
    public String getHandlerAdmin() {
        return handlerAdmin;
    }
    public void setHandlerAdmin(String handlerAdmin) {
        this.handlerAdmin = handlerAdmin;
    }
    public String getGroupPartakeId() {
        return groupPartakeId;
    }
    public void setGroupPartakeId(String groupPartakeId) {
        this.groupPartakeId = groupPartakeId;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public int getWay() {
        return way;
    }
    public void setWay(int way) {
        this.way = way;
    }
    
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
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
