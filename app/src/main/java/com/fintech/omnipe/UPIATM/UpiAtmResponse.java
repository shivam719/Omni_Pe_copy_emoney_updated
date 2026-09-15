package com.fintech.omnipe.UPIATM;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UpiAtmResponse implements Serializable {
    @SerializedName("statuscode")
    @Expose
    private int statuscode;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("isVersionValid")
    @Expose
    private boolean isVersionValid;
    @SerializedName("isAppValid")
    @Expose
    private boolean isAppValid;
    @SerializedName("data")
    @Expose
    private UpiAtmData data;

    public int getStatuscode() {
        return statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isVersionValid() {
        return isVersionValid;
    }

    public UpiAtmData getData() {
        return data;
    }

    public static class UpiAtmData implements Serializable {
        @SerializedName("transactionId")
        @Expose
        private String transactionId;
        @SerializedName("qrString")
        @Expose
        private String qrString;
        @SerializedName("amount")
        @Expose
        private double amount;
        @SerializedName("expireAt")
        @Expose
        private String expireAt;
        @SerializedName("status")
        @Expose
        private int status;
        @SerializedName("liveId")
        @Expose
        private String liveId;
        @SerializedName("payerName")
        @Expose
        private String payerName;
        @SerializedName("payerVPA")
        @Expose
        private String payerVPA;
        @SerializedName("payerMobile")
        @Expose
        private String payerMobile;

        public String getTransactionId() {
            return transactionId;
        }

        public String getQrString() {
            return qrString;
        }

        public double getAmount() {
            return amount;
        }

        public String getExpireAt() {
            return expireAt;
        }

        public int getStatus() {
            return status;
        }

        public String getLiveId() {
            return liveId;
        }

        public String getPayerName() {
            return payerName;
        }

        public String getPayerVPA() {
            return payerVPA;
        }

        public String getPayerMobile() {
            return payerMobile;
        }
    }
}
