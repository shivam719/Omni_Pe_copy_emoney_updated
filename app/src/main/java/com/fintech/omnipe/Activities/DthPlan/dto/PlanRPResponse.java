package com.fintech.omnipe.Activities.DthPlan.dto;

import java.io.Serializable;

public class PlanRPResponse implements Serializable {
    String opName;
    String rechargeAmount;
    String rechargeValidity;
    String rechargeType;
    String details;
    String packagelanguage;
    int packageId,channelcount;

    public String getPackagelanguage() {
        return packagelanguage;
    }

    public int getPackageId() {
        return packageId;
    }

    public int getChannelcount() {
        return channelcount;
    }

    public String getOpName() {
        return opName;
    }

    public String getRechargeAmount() {
        return rechargeAmount;
    }

    public String getRechargeValidity() {
        return rechargeValidity;
    }

    public String getRechargeType() {
        return rechargeType;
    }

    public String getDetails() {
        return details;
    }
}
