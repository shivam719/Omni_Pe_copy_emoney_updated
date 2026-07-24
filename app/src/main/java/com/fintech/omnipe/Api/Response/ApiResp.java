package com.fintech.omnipe.Api.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiResp {

    @SerializedName("amount")
    @Expose
    public String amount;
    @SerializedName("approvalNumber")
    @Expose
    public String approvalNumber;
    @SerializedName("custRefNo")
    @Expose
    public String custRefNo;
    @SerializedName("npciTransId")
    @Expose
    public String npciTransId;
    @SerializedName("payeeVPA")
    @Expose
    public String payeeVPA;
    @SerializedName("payerVPA")
    @Expose
    public String payerVPA;
    @SerializedName("responseCode")
    @Expose
    public String responseCode;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("statusDesc")
    @Expose
    public String statusDesc;
    @SerializedName("txnAuthDate")
    @Expose
    public String txnAuthDate;
    @SerializedName("upiTransRefNo")
    @Expose
    public Integer upiTransRefNo;

    public String getAmount() {
        return amount;
    }

    public String getApprovalNumber() {
        return approvalNumber;
    }

    public String getCustRefNo() {
        return custRefNo;
    }

    public String getNpciTransId() {
        return npciTransId;
    }

    public String getPayeeVPA() {
        return payeeVPA;
    }

    public String getPayerVPA() {
        return payerVPA;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public String getStatus() {
        return status;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public String getTxnAuthDate() {
        return txnAuthDate;
    }

    public Integer getUpiTransRefNo() {
        return upiTransRefNo;
    }
}
