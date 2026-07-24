package com.fintech.omnipe.Api.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AssignedOpType {
    @SerializedName("serviceID")
    @Expose
    private int serviceID;
    @SerializedName("parentID")
    @Expose
    private int parentID;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("service")
    @Expose
    private String service;
    @SerializedName("sCode")
    @Expose
    private String sCode;
    @SerializedName("isDisplayService")
    @Expose
    private boolean isDisplayService;
    @SerializedName("isShowMore")
    @Expose
    private boolean isShowMore;
    @SerializedName("upline")
    @Expose
    private String upline;
    @SerializedName("uplineMobile")
    @Expose
    private String uplineMobile;
    @SerializedName("ccContact")
    @Expose
    private String ccContact;
    @SerializedName("isActive")
    @Expose
    private boolean isActive;
    @SerializedName("isServiceActive")
    @Expose
    private boolean isServiceActive;

    @SerializedName("subOpTypeList")
    @Expose
    private ArrayList<AssignedOpType> subOpTypeList;

    public ArrayList<AssignedOpType> getSubOpTypeList() {
        return subOpTypeList;
    }

    public void setSubOpTypeList(ArrayList<AssignedOpType> subOpTypeList) {
        this.subOpTypeList = subOpTypeList;
    }



    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }



    public boolean getIsServiceActive() {
        return isServiceActive;
    }

    public int getParentID() {
        return parentID;
    }

    public String getService() {
        return service;
    }

    public String getsCode() {
        return sCode;
    }

    public boolean getDisplayService() {
        return isDisplayService;
    }

    public boolean getShowMore() {
        return isShowMore;
    }

    public String getUpline() {
        return upline;
    }

    public String getUplineMobile() {
        return uplineMobile;
    }

    public String getCcContact() {
        return ccContact;
    }

    public AssignedOpType(int serviceID, String name, boolean isActive, boolean isServiceActive) {
        this.serviceID = serviceID;
        this.name = name;
        this.isActive = isActive;
        this.isServiceActive = isServiceActive;
    }


    public AssignedOpType(int serviceID, int parentID, boolean isServiceActive, String name, String service, boolean isActive, boolean isDisplayService, ArrayList<AssignedOpType> subOpTypeList) {
        this.serviceID = serviceID;
        this.parentID = parentID;
        this.isServiceActive = isServiceActive;
        this.name = name;
        this.service = service;
        this.isActive = isActive;
        this.isDisplayService = isDisplayService;
        this.subOpTypeList = subOpTypeList;
    }

    public AssignedOpType(int serviceID, String name, boolean isActive,boolean isServiceActive,int parentID,String serviceName) {
        this.serviceID = serviceID;
        this.name = name;
        this.isActive = isActive;
        this.isServiceActive = isServiceActive;
        this.parentID = parentID;
        this.service = serviceName;
    }


    public AssignedOpType(int serviceID, int parentID, String name, String service, boolean isServiceActive, boolean isActive, boolean isDisplayService, String upline, String uplineMobile, String ccContact, ArrayList<AssignedOpType> subOpTypeList) {
        this.serviceID = serviceID;
        this.parentID = parentID;
        this.name = name;
        this.service = service;
        this.isServiceActive = isServiceActive;
        this.isActive = isActive;
        this.isDisplayService = isDisplayService;
        this.subOpTypeList = subOpTypeList;
        this.upline = upline;
        this.uplineMobile = uplineMobile;
        this.ccContact = ccContact;
    }


}
