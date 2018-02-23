package com.footlocker.azure;

public class Store {

    String storeNumber;
    Integer costOfGoods;
    Integer companyNumber;
    Integer quantityOnHand;
    Integer quantityOnOrder;
    Integer quantityCommitted;
    Integer quantityReserved;

    public String getStoreNumber() {
        return storeNumber;
    }

    public void setStoreNumber(String storeNumber) {
        this.storeNumber = storeNumber;
    }

    public Integer getCostOfGoods() {
        return costOfGoods;
    }

    public void setCostOfGoods(Integer costOfGoods) {
        this.costOfGoods = costOfGoods;
    }

    public Integer getCompanyNumber() {
        return companyNumber;
    }

    public void setCompanyNumber(Integer companyNumber) {
        this.companyNumber = companyNumber;
    }

    public Integer getQuantityOnHand() {
        return quantityOnHand;
    }

    public void setQuantityOnHand(Integer quantityOnHand) {
        this.quantityOnHand = quantityOnHand;
    }

    public Integer getQuantityOnOrder() {
        return quantityOnOrder;
    }

    public void setQuantityOnOrder(Integer quantityOnOrder) {
        this.quantityOnOrder = quantityOnOrder;
    }

    public Integer getQuantityCommitted() {
        return quantityCommitted;
    }

    public void setQuantityCommitted(Integer quantityCommitted) {
        this.quantityCommitted = quantityCommitted;
    }

    public Integer getQuantityReserved() {
        return quantityReserved;
    }

    public void setQuantityReserved(Integer quantityReserved) {
        this.quantityReserved = quantityReserved;
    }

    @Override
    public String toString() {
        return "Store{" +
                "storeNumber='" + storeNumber + '\'' +
                ", costOfGoods=" + costOfGoods +
                ", companyNumber=" + companyNumber +
                ", quantityOnHand=" + quantityOnHand +
                ", quantityOnOrder=" + quantityOnOrder +
                ", quantityCommitted=" + quantityCommitted +
                ", quantityReserved=" + quantityReserved +
                '}';
    }
}
