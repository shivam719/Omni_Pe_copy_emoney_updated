package com.fintech.omnipe.Api.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OperatorOptional {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("oid")
    @Expose
    public Integer oid;
    @SerializedName("optionalType")
    @Expose
    public Integer optionalType;
    @SerializedName("displayName")
    @Expose
    public String displayName;
    @SerializedName("remark")
    @Expose
    public String remark;
    @SerializedName("isList")
    @Expose
    public Boolean isList;
    @SerializedName("isMultiSelection")
    @Expose
    public Boolean isMultiSelection;

    public Integer getId() {
        return id;
    }

    public Integer getOid() {
        return oid;
    }

    public Integer getOptionalType() {
        return optionalType;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getRemark() {
        return remark;
    }

    public Boolean getList() {
        return isList;
    }

    public Boolean getMultiSelection() {
        return isMultiSelection;
    }
}
