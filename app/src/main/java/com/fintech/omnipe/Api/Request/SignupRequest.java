package com.fintech.omnipe.Api.Request;

import com.fintech.omnipe.Api.Object.UserCreateSignup;

/**
 * Created by Vishnu Agarwal on 20,December,2019
 */
public class SignupRequest {
    String domain;
    String appid;
    String imei;
    String regKey;
    String version;
    String serialNo;
    String referalID;
    UserCreateSignup userCreate;

    public SignupRequest(String referalID,String domain, String appid, String imei, String regKey, String version, String serialNo, UserCreateSignup userCreate) {
        this.referalID=referalID;
        this.domain = domain;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.userCreate = userCreate;
    }
}
