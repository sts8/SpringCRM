package com.sts8.springcrm.dto;

public class ArticleListingDto {

    private int id;
    private String name;
    private boolean isUsedInOrder;

    public ArticleListingDto(int id, String name, boolean isUsedInOrder) {
        this.id = id;
        this.name = name;
        this.isUsedInOrder = isUsedInOrder;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isUsedInOrder() {
        return isUsedInOrder;
    }

    public void setUsedInOrder(boolean usedInOrder) {
        isUsedInOrder = usedInOrder;
    }

}
