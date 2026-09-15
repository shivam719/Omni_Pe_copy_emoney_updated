package com.fintech.omnipe.UPIATM;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class UpiAtmRequest {
    @SerializedName("APPID")
    @Expose
    public String appid;
    @SerializedName("IMEI")
    @Expose
    public String imei;
    @SerializedName("RegKey")
    @Expose
    public String regKey;
    @SerializedName("Version")
    @Expose
    public String version;
    @SerializedName("SerialNo")
    @Expose
    public String serialNo;
    @SerializedName("LoginTypeID")
    @Expose
    public String loginTypeID;
    @SerializedName("ID")
    @Expose
    public int id;
    @SerializedName("UserID")
    @Expose
    public int userID;
    @SerializedName("SessionID")
    @Expose
    public int sessionID;
    @SerializedName("Session")
    @Expose
    public String session;
    @SerializedName("SecurityKey")
    @Expose
    public String securityKey;
    @SerializedName("Name")
    @Expose
    public String name;
    @SerializedName("Mobile")
    @Expose
    public String mobile;
    @SerializedName("Remark")
    @Expose
    public String remark;
    @SerializedName("OID")
    @Expose
    public int oid;
    @SerializedName("categoryId")
    @Expose
    public int categoryId;
    @SerializedName("vendorId")
    @Expose
    public int vendorId;
    @SerializedName("PackageId")
    @Expose
    public int packageId;
    @SerializedName("Type")
    @Expose
    public int type;
    @SerializedName("IsActive")
    @Expose
    public boolean isActive;
    @SerializedName("IsLoginNews")
    @Expose
    public boolean isLoginNews;
    @SerializedName("Param")
    @Expose
    public Map<String, Object> param;

    public UpiAtmRequest() {
    }
}
