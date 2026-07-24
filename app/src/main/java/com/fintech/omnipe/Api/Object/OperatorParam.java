package com.fintech.omnipe.Api.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OperatorParam {

    @SerializedName("paramName")
    @Expose
    private String paramName;
    @SerializedName("dataType")
    @Expose
    private String dataType;
    @SerializedName("minLength")
    @Expose
    private int minLength;
    @SerializedName("maxLength")
    @Expose
    private int maxLength;
    @SerializedName("ind")
    @Expose
    private int ind;
    @SerializedName("regEx")
    @Expose
    private String regEx;
    @SerializedName("remark")
    @Expose
    private String remark;
    @SerializedName("isOptional")
    @Expose
    private boolean isOptional;
    @SerializedName("isCustomerNo")
    @Expose
    private boolean isCustomerNo;

    @SerializedName("isDropDown")
    @Expose
    private boolean isDropDown;

    @SerializedName("id")
    @Expose
    private int id;



    public boolean isDropDown() {
        return isDropDown;
    }

    public int getId() {
        return id;
    }



    public String getParamName() {
        return paramName;
    }

    public String getDataType() {
        return dataType;
    }

    public int getMinLength() {
        return minLength;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public int getInd() {
        return ind;
    }

    public String getRegEx() {
        return regEx;
    }

    public String getRemark() {
        return remark;
    }

    public boolean isOptional() {
        return isOptional;
    }

    public boolean isCustomerNo() {
        return isCustomerNo;
    }
}
