package com.wyc.service;

import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.domain.GroupSuccessGiveOpenGroupCouponActivity;
import com.wyc.repositories.GroupSuccessGiveOpenGroupCouponActivityRepository;

@Service
public class GroupSuccessGiveOpenGroupCouponActivityService {
    @Autowired
    private GroupSuccessGiveOpenGroupCouponActivityRepository groupSuccessGiveOpenGroupCouponActivityRepository;

    public Iterable<GroupSuccessGiveOpenGroupCouponActivity> findAllByActivityId(
            String activityId) {
        return groupSuccessGiveOpenGroupCouponActivityRepository.findAllByActivityId(activityId);
    }

    public GroupSuccessGiveOpenGroupCouponActivity add(
            GroupSuccessGiveOpenGroupCouponActivity giveOpenGroupCouponActivity) {
        giveOpenGroupCouponActivity.setId(UUID.randomUUID().toString());
        giveOpenGroupCouponActivity.setUpdateAt(new DateTime());
        giveOpenGroupCouponActivity.setCreateAt(new DateTime());
        return groupSuccessGiveOpenGroupCouponActivityRepository.save(giveOpenGroupCouponActivity);
    }

    public void delete(
            GroupSuccessGiveOpenGroupCouponActivity giveOpenGroupCouponActivity) {
        groupSuccessGiveOpenGroupCouponActivityRepository.delete(giveOpenGroupCouponActivity);
        
    }
}
