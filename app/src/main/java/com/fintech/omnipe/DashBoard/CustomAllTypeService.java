package com.fintech.omnipe.DashBoard;

import com.fintech.omnipe.Api.Object.AssignedOpType;

import java.util.ArrayList;

public class CustomAllTypeService {

    private ArrayList<AssignedOpType>  dashBoardRTBillRechargeList = new ArrayList<>();
    private ArrayList<AssignedOpType>  dashBoardRTFundBankingList = new ArrayList<>();
    private ArrayList<AssignedOpType>  dashBoardOtherFundBankingList = new ArrayList<>();
    private ArrayList<AssignedOpType>  dashBoardRTReportList = new ArrayList<>();
    private ArrayList<AssignedOpType>  dashBoardOtherList = new ArrayList<>();
    private ArrayList<AssignedOpType>  dashBoardOtherReportList = new ArrayList<>();
    private ArrayList<AssignedOpType>  dashBoardFosList = new ArrayList<>();



    private boolean isUPIQR,isUPI,isPaymentGateway,isDMTPipe,isECollection,isDMTEnable,isAEPSEnable,isDTHSubscriptionEnable,isMoveToBankEnable;

    public CustomAllTypeService(ArrayList<AssignedOpType> dashBoardRTBillRechargeList, ArrayList<AssignedOpType> dashBoardRTFundBankingList, ArrayList<AssignedOpType> dashBoardRTReportList,ArrayList<AssignedOpType> dashBoardOtherFundBankingList,  ArrayList<AssignedOpType> dashBoardOtherList, ArrayList<AssignedOpType> dashBoardOtherReportList, ArrayList<AssignedOpType> dashBoardFosList, boolean isUPIQR, boolean isUPI, boolean isPaymentGateway, boolean isDMTPipe, boolean isECollection,boolean isDMTEnable,boolean isAEPSEnable,boolean isDTHSubscriptionEnable,boolean isMoveToBankEnable) {
        this.dashBoardRTBillRechargeList = dashBoardRTBillRechargeList;
        this.dashBoardRTFundBankingList = dashBoardRTFundBankingList;
        this.dashBoardOtherFundBankingList = dashBoardOtherFundBankingList;
        this.dashBoardRTReportList = dashBoardRTReportList;
        this.dashBoardOtherList = dashBoardOtherList;
        this.dashBoardOtherReportList = dashBoardOtherReportList;
        this.dashBoardFosList = dashBoardFosList;
        this.isUPIQR = isUPIQR;
        this.isUPI = isUPI;
        this.isPaymentGateway = isPaymentGateway;
        this.isDMTPipe = isDMTPipe;
        this.isECollection = isECollection;
        this.isDMTEnable = isDMTEnable;
        this.isAEPSEnable = isAEPSEnable;
        this.isDTHSubscriptionEnable = isDTHSubscriptionEnable;
        this.isMoveToBankEnable = isMoveToBankEnable;
    }


    public ArrayList<AssignedOpType> getDashBoardRTBillRechargeList() {
        return dashBoardRTBillRechargeList;
    }

    public ArrayList<AssignedOpType> getDashBoardRTFundBankingList() {
        return dashBoardRTFundBankingList;
    }

    public ArrayList<AssignedOpType> getDashBoardOtherFundBankingList() {
        return dashBoardOtherFundBankingList;
    }

    public ArrayList<AssignedOpType> getDashBoardRTReportList() {
        return dashBoardRTReportList;
    }

    public ArrayList<AssignedOpType> getDashBoardOtherList() {
        return dashBoardOtherList;
    }

    public ArrayList<AssignedOpType> getDashBoardOtherReportList() {
        return dashBoardOtherReportList;
    }

    public ArrayList<AssignedOpType> getDashBoardFosList() {
        return dashBoardFosList;
    }

    public boolean isUPIQR() {
        return isUPIQR;
    }

    public boolean isUPI() {
        return isUPI;
    }

    public boolean isPaymentGateway() {
        return isPaymentGateway;
    }

    public boolean isDMTPipe() {
        return isDMTPipe;
    }

    public boolean isECollection() {
        return isECollection;
    }

    public boolean isDMTEnable() {
        return isDMTEnable;
    }

    public boolean isAEPSEnable() {
        return isAEPSEnable;
    }

    public boolean isDTHSubscriptionEnable() {
        return isDTHSubscriptionEnable;
    }

    public boolean isMoveToBankEnable() {
        return isMoveToBankEnable;
    }
}
