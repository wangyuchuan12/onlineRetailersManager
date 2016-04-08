package com.wyc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "group_success_activity")
public class GroupSuccessGiveOpenGroupCouponActivity {
    
    @Id
    private String id;
    @Column(name="open_group_coupon_num")
    private String openGroupCouponNum;
    @Column(name="good_id")
    private String goodId;
    @Column(name="time_long")
    private Long timeLong;
    @Column(name="activity_id")
    private String activityId;
    @Column(name = "create_at")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonIgnore
    private DateTime createAt;
    @Column(name = "update_at")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonIgnore
    private DateTime updateAt;
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
    public String getActivityId() {
        return activityId;
    }
    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getOpenGroupCouponNum() {
        return openGroupCouponNum;
    }
    public void setOpenGroupCouponNum(String openGroupCouponNum) {
        this.openGroupCouponNum = openGroupCouponNum;
    }
    public String getGoodId() {
        return goodId;
    }
    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }
    public Long getTimeLong() {
        return timeLong;
    }
    public void setTimeLong(Long timeLong) {
        this.timeLong = timeLong;
    }
}
