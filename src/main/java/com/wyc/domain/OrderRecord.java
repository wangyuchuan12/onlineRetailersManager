package com.wyc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity(name="order_record")
public class OrderRecord {
    @Id
    private String id;
    //处理方式
  //1申请退款 2 退款发货 3 退款签收 4 发货 5 发货签收
    @Column
    private int way;
    @Column(name="group_partake_id")
    private String groupPartakeId;
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
