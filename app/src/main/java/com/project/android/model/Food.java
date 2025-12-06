package com.project.android.model;

public class Food {
    private long foodID;
    private String fooType;
    private String quantity;

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getFooType() {

        return fooType;
    }

    public void setFooType(String fooType) {
        this.fooType = fooType;
    }

    public long getFoodID() {

        return foodID;
    }

    public void setFoodID(long foodID) {
        this.foodID = foodID;
    }
}


