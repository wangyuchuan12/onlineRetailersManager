package com.wyc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity(name="logistics_order_result_record")
public class LogisticsOrderResultRecord {
    @Id
    private String id;
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name="datetime")
    private DateTime datetime;
    @Column
    private String remark;
    @Column
    private String zone;
    @Column(name="record_index")
    private int recordIndex;
    @Column(name="logistics_order_result_id")
    private String logisticsOrderResultId;
    @Column(name = "create_at")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime createAt;
    @Column(name = "update_at")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime updateAt;
    
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getLogisticsOrderResultId() {
        return logisticsOrderResultId;
    }
    public void setLogisticsOrderResultId(String logisticsOrderResultId) {
        this.logisticsOrderResultId = logisticsOrderResultId;
    }
   
    public int getRecordIndex() {
        return recordIndex;
    }
    public void setRecordIndex(int recordIndex) {
        this.recordIndex = recordIndex;
    }
    public DateTime getDatetime() {
        return datetime;
    }
    public void setDatetime(DateTime datetime) {
        this.datetime = datetime;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public String getZone() {
        return zone;
    }
    public void setZone(String zone) {
        this.zone = zone;
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
