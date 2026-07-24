package com.fintech.omnipe.Activities.DthPlan.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DthPlanInfoResponse implements Serializable {
    @SerializedName("dataRPDTHWithPackage")
    @Expose
    private PlanInfoRPWithPackage dataRPDTHWithPackage;
    @SerializedName("dataRPDTHChannelList")
    @Expose
    private DataRPDTHChannelList dataRPDTHChannelList;
    @SerializedName("dthhrData")
    @Expose
    private DthHRData dthhrData;
    @SerializedName("data")
    @Expose
    private PlanInfoData data;
    @SerializedName("dataRP")
    @Expose
    private PlanInfoRPData dataRP;
    @SerializedName("dataPA")
    @Expose
    private PlanInfoData dataPA;
    @SerializedName("myPlanData")
    @Expose
    private PlanInfoData myPlanData;
    @SerializedName("statuscode")
    @Expose
    private Integer statuscode;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("isVersionValid")
    @Expose
    private String isVersionValid;
    @SerializedName("isAppValid")
    @Expose
    private String isAppValid;

    public DthHRData getDthhrData() {
        return dthhrData;
    }

    public PlanInfoData getMyPlanData() {
        return myPlanData;
    }

    public String getIsVersionValid() {
        return isVersionValid;
    }

    public String getIsAppValid() {
        return isAppValid;
    }

    public PlanInfoData getData() {
        return data;
    }

    public void setData(PlanInfoData data) {
        this.data = data;
    }

    public Integer getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(Integer statuscode) {
        this.statuscode = statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public PlanInfoRPData getDataRP() {
        return dataRP;
    }

    public PlanInfoData getDataPA() {
        return dataPA;
    }


    public PlanInfoRPWithPackage getDataRPDTHWithPackage() {
        return dataRPDTHWithPackage;
    }

    public DataRPDTHChannelList getDataRPDTHChannelList() {
        return dataRPDTHChannelList;
    }
}
