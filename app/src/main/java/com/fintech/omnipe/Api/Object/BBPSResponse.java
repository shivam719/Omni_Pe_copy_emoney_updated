package com.fintech.omnipe.Api.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class BBPSResponse implements Serializable {

    @SerializedName("statuscode")
    @Expose
    public Integer statuscode;
    @SerializedName("msg")
    @Expose
    public String msg;
    @SerializedName("date")
    @Expose
    public String date;
    @SerializedName("session")
    @Expose
    public String session;
    @SerializedName("error")
    @Expose
    public String error;
    @SerializedName("result")
    @Expose
    public String result;
    @SerializedName("transid")
    @Expose
    public String transid;
    @SerializedName("price")
    @Expose
    public String price;
    //amount
    @SerializedName("billNumber")
    @Expose
    public String billNumber;
    @SerializedName("billDate")
    @Expose
    public String billDate;
    @SerializedName("billPeriod")
    @Expose
    public String billPeriod;
    @SerializedName("dueDate")
    @Expose
    public String dueDate;
    @SerializedName("amount")
    @Expose
    public String amount;
    @SerializedName("customerName")
    @Expose
    public String customerName;
    @SerializedName("authcode")
    @Expose
    public Object authcode;
    @SerializedName("trnxstatus")
    @Expose
    public Object trnxstatus;
    @SerializedName("errorMsg")
    @Expose
    public String errmsg;
    @SerializedName("isEditable")
    @Expose
    public Boolean iseditable;
    @SerializedName(value = "refID",alternate = {"RefID","refferenceID","RefferenceID"})
    @Expose
    public String refID;

    @SerializedName("isShowMsgOnly")
    @Expose
    public boolean isShowMsgOnly;

    @SerializedName("isEnablePayment")
    @Expose
    public boolean isEnablePayment;

    @SerializedName("isHardCoded")
    @Expose
    public Boolean isHardCoded;
    @SerializedName("exactness")
    @Expose
    private int exactness;
    @SerializedName("fetchBillID")
    @Expose
    private Integer fetchBillID;
    @SerializedName("billAdditionalInfo")
    @Expose
    private List<BillAdditionalInfo> billAdditionalInfo = null;
    @SerializedName("billAmountOptions")
    @Expose
    private List<BillAdditionalInfo> billAmountOptions=null;


    public List<BillAdditionalInfo> getBillAdditionalInfo() {
        return billAdditionalInfo;
    }

    public List<BillAdditionalInfo> getBillAmountOptions() {
        return billAmountOptions;
    }

    public int getExactness() {
        return exactness;
    }

    public void setRefID(String refID) {
        this.refID = refID;
    }

    public Boolean getHardCoded() {
        return isHardCoded;
    }

    public void setHardCoded(Boolean hardCoded) {
        isHardCoded = hardCoded;
    }

    //isHardCoded

    public Integer getStatuscode() {
        return statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public String getDate() {
        return date;
    }

    public String getSession() {
        return session;
    }

    public String getError() {
        return error;
    }

    public String getResult() {
        return result;
    }

    public String getTransid() {
        return transid;
    }

    public String getPrice() {
        return price;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public String getBillDate() {
        return billDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getAmount() {
        return amount;
    }

    public String getCustomerName() {
        return customerName;
    }

    public Object getAuthcode() {
        return authcode;
    }

    public Object getTrnxstatus() {
        return trnxstatus;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public Boolean getIseditable() {
        return iseditable;
    }

    public String getRefID() {
        return refID;
    }

    public boolean isEnablePayment() {
        return isEnablePayment;
    }

    public void setEnablePayment(boolean enablePayment) {
        isEnablePayment = enablePayment;
    }

    public boolean isShowMsgOnly() {
        return isShowMsgOnly;
    }

    public void setShowMsgOnly(boolean showMsgOnly) {
        isShowMsgOnly = showMsgOnly;
    }

    public Integer getFetchBillID() {
        return fetchBillID;
    }

    public String getBillPeriod() {
        return billPeriod;
    }
}
