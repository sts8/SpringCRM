package com.sts8.springcrm.dto;

import java.util.ArrayList;
import java.util.List;

public class OrderAddDto {

    private final List<Integer> articleIds;
    private final List<Integer> articleAmounts;
    private int customerId;

    public OrderAddDto() {
        this.articleIds = new ArrayList<>();
        this.articleAmounts = new ArrayList<>();
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public List<Integer> getArticleIds() {
        return articleIds;
    }

    public List<Integer> getArticleAmounts() {
        return articleAmounts;
    }

}
