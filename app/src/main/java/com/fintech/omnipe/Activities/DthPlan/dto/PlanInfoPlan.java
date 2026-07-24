package com.fintech.omnipe.Activities.DthPlan.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.fintech.omnipe.Activities.DthCustomerInfo.dto.Rs;

import java.io.Serializable;

public class PlanInfoPlan implements Serializable {
    @SerializedName(value = "desc", alternate = "pDescription")
    @Expose
    private String desc;
    @SerializedName("last_update")
    @Expose
    private String lastUpdate;
    @SerializedName("pLangauge")
    @Expose
    private String pLangauge;
    @SerializedName("packageId")
    @Expose
    private int packageId;
    @SerializedName("pCount")
    @Expose
    private int pCount;
    @SerializedName("pChannelCount")
    @Expose
    private int pChannelCount;
    @SerializedName(value = "plan_name", alternate = "packageName")
    @Expose
    private String planName;
    @SerializedName(value = "rs", alternate = "price")
    @Expose
    private Rs rs;


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public Rs getRs() {
        return rs;
    }

    public void setRs(Rs rs) {
        this.rs = rs;
    }


    public String getpLangauge() {
        return pLangauge;
    }

    public int getPackageId() {
        return packageId;
    }

    public int getpCount() {
        return pCount;
    }

    public int getpChannelCount() {
        return pChannelCount;
    }
}
