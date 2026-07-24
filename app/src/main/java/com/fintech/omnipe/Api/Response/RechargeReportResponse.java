package com.fintech.omnipe.Api.Response;

import com.fintech.omnipe.Api.Object.LedgerObject;
import com.fintech.omnipe.Api.Object.RechargeStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RechargeReportResponse {

    private String statuscode;
    private String msg;
    private String isVersionValid;
    private String isAppValid;
    private String groupID;
    private String TXN;
    private String beneName;
    private String chargedAmount;
    private ArrayList<RechargeStatus> rechargeReport;
    private ArrayList<BenisObject> benis;
    private ArrayList<BenisObject> beneficiaries;
    private ArrayList<LedgerObject> ledgerReport;
    private ArrayList<FundDCObject> fundDCReport;
    private ArrayList<FundOrderReportObject> fundOrderReport;
    private ArrayList<SlabCommtObject> slabCommissions;
    private ArrayList<DmtReportObject> dmtReport;
    @SerializedName("isOTPRequired")
    @Expose
    private boolean isOTPRequired;


    public boolean isOTPRequired() {
        return isOTPRequired;
    }

    public String getIsVersionValid() {
        return isVersionValid;
    }

    public String getIsAppValid() {
        return isAppValid;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        groupID = groupID;
    }

    public String getTXN() {
        return TXN;
    }

    public void setTXN(String TXN) {
        this.TXN = TXN;
    }

    public String getBeneName() {
        return beneName;
    }

    public void setBeneName(String beneName) {
        this.beneName = beneName;
    }

    public String getChargedAmount() {
        return chargedAmount;
    }

    public void setChargedAmount(String chargedAmount) {
        this.chargedAmount = chargedAmount;
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

    public ArrayList<RechargeStatus> getRechargeReport() {
        return rechargeReport;
    }

    public void setRechargeReport(ArrayList<RechargeStatus> rechargeReport) {
        this.rechargeReport = rechargeReport;
    }

    public ArrayList<BenisObject> getBenis() {
        return benis;
    }

    public void setBenis(ArrayList<BenisObject> benis) {
        this.benis = benis;
    }

    public ArrayList<LedgerObject> getLedgerReport() {
        return ledgerReport;
    }

    public void setLedgerReport(ArrayList<LedgerObject> ledgerReport) {
        this.ledgerReport = ledgerReport;
    }

    public ArrayList<FundDCObject> getFundDCReport() {
        return fundDCReport;
    }

    public void setFundDCReport(ArrayList<FundDCObject> fundDCReport) {
        this.fundDCReport = fundDCReport;
    }

    public ArrayList<FundOrderReportObject> getFundOrderReport() {
        return fundOrderReport;
    }

    public void setFundOrderReport(ArrayList<FundOrderReportObject> fundOrderReport) {
        this.fundOrderReport = fundOrderReport;
    }

    public ArrayList<SlabCommtObject> getSlabCommissions() {
        return slabCommissions;
    }

    public void setSlabCommissions(ArrayList<SlabCommtObject> slabCommissions) {
        this.slabCommissions = slabCommissions;
    }

    public ArrayList<BenisObject> getBeneficiaries() {
        return beneficiaries;
    }

    public ArrayList<DmtReportObject> getDmtReport() {
        return dmtReport;
    }

    public void setDmtReport(ArrayList<DmtReportObject> dmtReport) {
        this.dmtReport = dmtReport;
    }
}
