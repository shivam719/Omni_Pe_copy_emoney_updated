package com.fintech.omnipe.UPIPayment.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReqSendMoney {

    @SerializedName("accountNo")
    @Expose
    private String accountNo;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("beneName")
    @Expose
    private String beneName;
    @SerializedName("MobileNo")
    @Expose
    private String MobileNo;
    public ReqSendMoney(String accountNo, String amount, String beneName,String MobileNo) {
        this.accountNo = accountNo;
        this.amount = amount;
        this.beneName = beneName;
        this.MobileNo = MobileNo;
    }
}
