package com.fintech.omnipe.Api.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.fintech.omnipe.Api.Object.Bank;
import com.fintech.omnipe.Util.PaymentMode;

import java.util.List;

public class GetBankAndPaymentModeResponse {

    @SerializedName("banks")
    @Expose
    private List<Bank> banks = null;
    @SerializedName("paymentModes")
    @Expose
    private List<PaymentMode> paymentModes = null;
    @SerializedName("statuscode")
    @Expose
    private String statuscode;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("isVersionValid")
    @Expose
    private boolean isVersionValid;
    @SerializedName("isAppValid")
    @Expose
    private boolean isAppValid;

    public boolean getIsVersionValid() {
        return isVersionValid;
    }

    public boolean getIsAppValid() {
        return isAppValid;
    }

    public List<Bank> getBanks() {
        return banks;
    }

    public void setBanks(List<Bank> banks) {
        this.banks = banks;
    }

    public List<PaymentMode> getPaymentModes() {
        return paymentModes;
    }

    public void setPaymentModes(List<PaymentMode> paymentModes) {
        this.paymentModes = paymentModes;
    }

    public String getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(String statuscode) {
        this.statuscode = statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}