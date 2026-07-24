package com.fintech.omnipe.AEPS.FingPay.dto;

public class Param {

    public String name;

    public String value;

    public Param(String name, String value) {
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
