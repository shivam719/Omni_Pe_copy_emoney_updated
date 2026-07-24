package com.fintech.omnipe.Activities.DthPlan.dto;

import com.fintech.omnipe.Activities.DthCustomerInfo.dto.Rs;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


import java.io.Serializable;

public class PDetails implements Serializable {
    @SerializedName("packageName")
    @Expose
    private String packageName;
    @SerializedName("price")
    @Expose
    private Rs price;
    @SerializedName("pDescription")
    @Expose
    private String pDescription;
    @SerializedName("packageId")
    @Expose
    private Integer packageId;
    @SerializedName("pLangauge")
    @Expose
    private String pLangauge;
    @SerializedName("pCount")
    @Expose
    private int pCount;
    @SerializedName("pChannelCount")
    @Expose
    private int pChannelCount;


    public String getPackageName() {
        return packageName;
    }

    public Rs getPrice() {
        return price;
    }

    public String getpDescription() {
        return pDescription;
    }

    public Integer getPackageId() {
        return packageId;
    }

    public String getpLangauge() {
        return pLangauge;
    }

    public int getpCount() {
        return pCount;
    }

    public int getpChannelCount() {
        return pChannelCount;
    }
}
