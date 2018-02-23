package com.footlocker.azure;

import java.util.Arrays;

public class Value {
    Integer productNumber;
    String sku;
    String size;
    String status;
    Boolean backorderable;
    Integer[] availableCompanies;
    Store[] stores;
    String key;
    Integer casValue;
    String modelType;

    public Integer getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(Integer productNumber) {
        this.productNumber = productNumber;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getBackorderable() {
        return backorderable;
    }

    public void setBackorderable(Boolean backorderable) {
        this.backorderable = backorderable;
    }

    public Integer[] getAvailableCompanies() {
        return availableCompanies;
    }

    public void setAvailableCompanies(Integer[] availableCompanies) {
        this.availableCompanies = availableCompanies;
    }

    public Store[] getStores() {
        return stores;
    }

    public void setStores(Store[] stores) {
        this.stores = stores;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getCasValue() {
        return casValue;
    }

    public void setCasValue(Integer casValue) {
        this.casValue = casValue;
    }

    public String getModelType() {
        return modelType;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType;
    }

    @Override
    public String toString() {
        return "Value{" +
                "productNumber=" + productNumber +
                ", sku='" + sku + '\'' +
                ", size='" + size + '\'' +
                ", status='" + status + '\'' +
                ", backorderable=" + backorderable +
                ", availableCompanies=" + Arrays.toString(availableCompanies) +
                ", stores=" + Arrays.toString(stores) +
                ", key='" + key + '\'' +
                ", casValue=" + casValue +
                ", modelType='" + modelType + '\'' +
                '}';
    }
}
