package com.fintech.omnipe.Api.Object;

import java.io.Serializable;

public class BalanceType implements Serializable {
    String name, amount;

    public BalanceType(String name, String amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
