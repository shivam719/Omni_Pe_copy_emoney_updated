package com.fintech.omnipe.CommissionSlab.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SlabRangeDetail {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("oid")
    @Expose
    private Integer oid;
    @SerializedName("slabID")
    @Expose
    private Integer slabID;
    @SerializedName("rangeId")
    @Expose
    private Integer rangeId;
    @SerializedName("minRange")
    @Expose
    private Integer minRange;
    @SerializedName("maxRange")
    @Expose
    private Integer maxRange;
    @SerializedName("comm")
    @Expose
    private Double comm;
    @SerializedName("maxComm")
    @Expose
    private Double maxComm;
    @SerializedName("fixedCharge")
    @Expose
    private Double fixedCharge;
    @SerializedName("commType")
    @Expose
    private boolean commType;
    @SerializedName("amtType")
    @Expose
    private boolean amtType;
    @SerializedName("operator")
    @Expose
    private String operator;
    @SerializedName("dmrModelID")
    @Expose
    private Integer dmrModelID;


    public Integer getId() {
        return id;
    }

    public Integer getOid() {
        return oid;
    }

    public Integer getSlabID() {
        return slabID;
    }

    public Integer getRangeId() {
        return rangeId;
    }

    public Integer getMinRange() {
        return minRange;
    }

    public Integer getMaxRange() {
        return maxRange;
    }

    public Double getComm() {
        return comm;
    }

    public Double getMaxComm() {
        return maxComm;
    }

    public Double getFixedCharge() {
        return fixedCharge;
    }

    public boolean isCommType() {
        return commType;
    }

    public boolean isAmtType() {
        return amtType;
    }

    public String getOperator() {
        return operator;
    }

    public Integer getDmrModelID() {
        return dmrModelID;
    }
}
