package com.fintech.omnipe.Activities.DthCustomerInfo.dto;

public class DthCustomerInfoData {

    private String keyName,keyVal;
    private boolean isAmount;

    public DthCustomerInfoData(String keyName, String keyVal, boolean isAmount) {
        this.keyName = keyName;
        this.keyVal = keyVal;
        this.isAmount = isAmount;
    }

    public String getKeyName() {
        return keyName;
    }

    public String getKeyVal() {
        return keyVal;
    }

    public boolean isAmount() {
        return isAmount;
    }
}
