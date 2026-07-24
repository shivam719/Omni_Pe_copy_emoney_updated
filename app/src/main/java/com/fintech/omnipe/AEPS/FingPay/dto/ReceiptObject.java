package com.fintech.omnipe.AEPS.FingPay.dto;

public class ReceiptObject {
    String name,value;

    public ReceiptObject(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
