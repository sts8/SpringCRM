package com.sts8.springcrm.dto;

import java.util.Date;
import java.util.List;

public class OrderDetailsDto {

    private int id;
    private Date timestamp;
    private int customerId;
    private String customerName;
    private List<Integer> articleIds;
    private List<String> articleNames;
    private List<Integer> articleAmounts;

    public OrderDetailsDto(int id, Date timestamp, int customerId, String customerName, List<Integer> articleIds, List<String> articleNames, List<Integer> articleAmounts) {
        this.id = id;
        this.timestamp = timestamp;
        this.customerId = customerId;
        this.customerName = customerName;
        this.articleIds = articleIds;
        this.articleNames = articleNames;
        this.articleAmounts = articleAmounts;
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

    public List<Integer> getArticleIds() {
        return articleIds;
    }

    public void setArticleIds(List<Integer> articleIds) {
        this.articleIds = articleIds;
    }

    public List<String> getArticleNames() {
        return articleNames;
    }

    public void setArticleNames(List<String> articleNames) {
        this.articleNames = articleNames;
    }

    public List<Integer> getArticleAmounts() {
        return articleAmounts;
    }

    public void setArticleAmounts(List<Integer> articleAmounts) {
        this.articleAmounts = articleAmounts;
    }

}
