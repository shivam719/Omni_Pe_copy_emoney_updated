package com.fintech.omnipe.Api.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class PGModelForApp {

    @SerializedName("statuscode")
    @Expose
    private Integer statuscode;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("pgid")
    @Expose
    private Integer pgid;
    @SerializedName("tid")
    @Expose
    private Integer tid;
    @SerializedName("transactionID")
    @Expose
    private String transactionID;
    @SerializedName("requestPTM")
    @Expose
    private RequestPTM requestPTM;
    @SerializedName("rPayRequest")
    @Expose
    private RequestRazorPay rPayRequest;

    @SerializedName("aggrePayRequest")
    @Expose
    private AggrePayRequest aggrePayRequest;

    @SerializedName("token")
    @Expose
    public String token;

    @SerializedName("upiGatewayRequest")
    @Expose
    UPIGatewayRequest upiGatewayRequest ;

    @SerializedName("cashFreeResponse")
    @Expose
    private CashFreeData cashFreeResponse;

    public CashFreeData getCashFreeResponse() {
        return cashFreeResponse;
    }

    public Integer getStatuscode() {
        return statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public Integer getPgid() {
        return pgid;
    }

    public Integer getTid() {
        return tid;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public RequestPTM getRequestPTM() {
        return requestPTM;
    }

    public RequestRazorPay getrPayRequest() {
        return rPayRequest;
    }

    public AggrePayRequest getAggrePayRequest() {
        return aggrePayRequest;
    }

    public String getToken() {
        return token;
    }

    public UPIGatewayRequest getUpiGatewayRequest() {
        return upiGatewayRequest;
    }
}
