package com.fintech.omnipe.Activities.DthCustomerInfo.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DTHInfoRecords implements Serializable {

    @SerializedName(value = "monthlyRecharge", alternate = "Monthly")
    @Expose
    private String monthlyRecharge;

    @SerializedName(value = "balance", alternate = "Balance")
    @Expose
    private String balance;

    @SerializedName(value = "customerName", alternate = "Name")
    @Expose
    private String customerName;

    @SerializedName(value = "status", alternate = "Status")
    @Expose
    private String status;
    @SerializedName(value = "nextRechargeDate", alternate = "Next Recharge Date")
    @Expose
    private String nextRechargeDate;

    @SerializedName(value = "lastrechargedate", alternate = "Last Recharge Date")
    @Expose
    private String lastrechargedate;
    @SerializedName(value = "lastrechargeamount", alternate = "Last Recharge Amount")
    @Expose
    private String lastrechargeamount;
    @SerializedName(value = "planname", alternate = "Plan")
    @Expose
    private String planname;


    @SerializedName(value = "accountNo", alternate = "accountno")
    @Expose
    private String accountNo;

    public String getAccountNo() {
        return accountNo;
    }

    public String getMonthlyRecharge() {
        return monthlyRecharge;
    }

    public String getBalance() {
        return balance;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getStatus() {
        return status;
    }

    public String getNextRechargeDate() {
        return nextRechargeDate;
    }

    public String getLastrechargedate() {
        return lastrechargedate;
    }

    public String getLastrechargeamount() {
        return lastrechargeamount;
    }

    public String getPlanname() {
        return planname;
    }
}
