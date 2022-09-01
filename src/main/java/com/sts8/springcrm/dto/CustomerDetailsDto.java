package com.sts8.springcrm.dto;

import java.util.Date;
import java.util.List;

public class CustomerDetailsDto {

    private int id;
    private String firstName;
    private String lastName;
    private List<Integer> orderIds;
    private List<Date> orderTimestamps;

    public CustomerDetailsDto(int id, String firstName, String lastName, List<Integer> orderIds, List<Date> orderTimestamps) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.orderIds = orderIds;
        this.orderTimestamps = orderTimestamps;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Integer> getOrderIds() {
        return orderIds;
    }

    public void setOrderIds(List<Integer> orderIds) {
        this.orderIds = orderIds;
    }

    public List<Date> getOrderTimestamps() {
        return orderTimestamps;
    }

    public void setOrderTimestamps(List<Date> orderTimestamps) {
        this.orderTimestamps = orderTimestamps;
    }
}
