package com.fintech.omnipe.UPIPayment.dto;


import com.fintech.omnipe.Api.Request.BasicRequest;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class UPIPaymentRequest extends BasicRequest {

    @SerializedName(value = "reqSendMoney",alternate = {"appSendMoneyReq"})
    @Expose
    private ReqSendMoney appSendMoneyReq;
    @SerializedName("securityKey")
    @Expose
    private String securityKey;

    public UPIPaymentRequest(ReqSendMoney reqSendMoney, String securityKey, String userID, String loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String session, String sessionID){
        this.appSendMoneyReq=reqSendMoney;
        this.securityKey=securityKey;
        this.userID=userID;
        this.loginTypeID=loginTypeID;
        this.appid=appid;
        this.imei=imei;
        this.regKey=regKey;
        this.version=version;
        this.serialNo=serialNo;
        this.session=session;
        this.sessionID=sessionID;
    }


}
