package com.fintech.omnipe.Api.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RoleCommission {
    @SerializedName("roleID")
    @Expose
    public Integer roleID;
    @SerializedName("prefix")
    @Expose
    public String prefix;
    @SerializedName("role")
    @Expose
    public String role;
    @SerializedName("comm")
    @Expose
    public Double comm;
    @SerializedName("commType")
    @Expose
    public Integer commType;
    @SerializedName("amtType")
    @Expose
    public Integer amtType;
    @SerializedName("modifyDate")
    @Expose
    public String modifyDate;

    public Integer getRoleID() {
        return roleID;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getRole() {
        return role;
    }

    public Double getComm() {
        return comm;
    }

    public Integer getCommType() {
        return commType;
    }

    public Integer getAmtType() {
        return amtType;
    }

    public String getModifyDate() {
        return modifyDate;
    }
}
