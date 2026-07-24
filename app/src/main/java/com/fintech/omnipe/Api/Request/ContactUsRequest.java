package com.fintech.omnipe.Api.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Vishnu Agarwal on 27,February,2020
 */
public class ContactUsRequest {
    @SerializedName("appid")
    @Expose
    private String appid;
    @SerializedName("imei")
    @Expose
    private String imei;
    @SerializedName("regKey")
    @Expose
    private String regKey;
    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("serialNo")
    @Expose
    private String serialNo;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("emailID")
    @Expose
    private String emailID;

    @SerializedName("mobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("requestPage")
    @Expose
    private String requestPage = "ContactPageApp";

    public ContactUsRequest(String appid, String imei, String regKey, String version, String serialNo, String message, String emailid, String mobileNo, String name, String requestPage) {
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.message = message;
        this.emailID = emailid;
        this.mobileNo = mobileNo;
        this.name = name;
        this.requestPage = requestPage;
    }
}
