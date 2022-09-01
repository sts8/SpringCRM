package com.sts8.springcrm.dto;

import java.util.Date;

public class OrderListingDto {

    private int id;
    private Date timestamp;
    private int customerId;
    private String customerName;

    public OrderListingDto(int id, Date timestamp, int customerId, String customerName) {
        this.id = id;
        this.timestamp = timestamp;
        this.customerId = customerId;
        this.customerName = customerName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
