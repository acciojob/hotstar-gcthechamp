package com.driver.EntryDto;

import com.driver.model.SubscriptionType;

import java.util.Date;


public class SubscriptionEntryDto {

    private int userId;
    private SubscriptionType subscriptionType;

    private Date startSubscriptionDate;

    private int noOfScreensRequired;

    public SubscriptionEntryDto(int userId, SubscriptionType subscriptionType, int noOfScreensRequired) {
        this.userId = userId;
        this.subscriptionType = subscriptionType;
        this.noOfScreensRequired = noOfScreensRequired;
    }

    public SubscriptionEntryDto(int userId, SubscriptionType subscriptionType, Date startSubscriptionDate, int noOfScreensRequired) {
        this.userId = userId;
        this.subscriptionType = subscriptionType;
        this.startSubscriptionDate = startSubscriptionDate;
        this.noOfScreensRequired = noOfScreensRequired;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public SubscriptionType getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(SubscriptionType subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public int getNoOfScreensRequired() {
        return noOfScreensRequired;
    }

    public void setNoOfScreensRequired(int noOfScreensRequired) {
        this.noOfScreensRequired = noOfScreensRequired;
    }

    public Date getStartSubscriptionDate() {
        return startSubscriptionDate;
    }

    public void setStartSubscriptionDate(Date startSubscriptionDate) {
        this.startSubscriptionDate = startSubscriptionDate;
    }
}
