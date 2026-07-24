package com.fintech.omnipe.Util;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.fintech.omnipe.Api.Object.AssignedOpType;

import java.util.ArrayList;
import java.util.List;

public class DataOpType {
    @SerializedName("assignedOpTypes")
    @Expose
    private List<AssignedOpType> assignedOpTypes = null;

    public List<AssignedOpType> getAssignedOpTypes() {
        return assignedOpTypes;
    }

    public void setAssignedOpTypes(List<AssignedOpType> assignedOpTypes) {
        this.assignedOpTypes = assignedOpTypes;
    }


    public List<AssignedOpType> getRetailerReportOption(boolean isDmtEnable,boolean isAepsEnable,boolean isSubscriptionEnable) {
        List<AssignedOpType> reportServices = new ArrayList<>();
        reportServices.add(new AssignedOpType(101, "Recharge\nReport", true, true));
        if (isDmtEnable)
            reportServices.add(new AssignedOpType(105, "DMT\nReport", true, true));
        if (isAepsEnable)
            reportServices.add(new AssignedOpType(112, "AEPS\nReport", true, true));
        if (isSubscriptionEnable)
            reportServices.add(new AssignedOpType(113, "DTH Subscription\nReport", true, true));

        reportServices.add(new AssignedOpType(102, "Ledger\nReport", true, true));
        reportServices.add(new AssignedOpType(103, "Fund Order\nReport", true, true));
        reportServices.add(new AssignedOpType(104, "Complain\nReport", true, true));
        reportServices.add(new AssignedOpType(107, "Fund Debit Credit", true, true));
        reportServices.add(new AssignedOpType(109, "User Daybook", true, true));
        reportServices.add(new AssignedOpType(110, "Commission\nSlab", true, true));
        reportServices.add(new AssignedOpType(111, "W2R Report", true, true));

        return reportServices;
    }

    public ArrayList<AssignedOpType> getOtherServiceOptions(boolean isDmtEnable,boolean isAepsEnable,boolean isSubscriptionEnable,boolean isAccountStatement) {

        ArrayList<AssignedOpType> reportService = new ArrayList<>();
        reportService.add(new AssignedOpType(116, "App Users", true, true));
        reportService.add(new AssignedOpType(114, "Create\nUser", true, true));
        reportService.add(new AssignedOpType(115, "Create\nFOS", true, true));

        /*reportService.add(new AssignedOpType(110, "Commission\nSlab", true, true));*/
        reportService.add(new AssignedOpType(101, "Recharge\nReport", true, true));
        if (isDmtEnable)
            reportService.add(new AssignedOpType(105, "DMT\nReport", true, true));
        if (isAepsEnable)
            reportService.add(new AssignedOpType(112, "AEPS\nReport", true, true));
        if (isSubscriptionEnable)
            reportService.add(new AssignedOpType(113, "DTH Subscription\nReport", true, true));
        reportService.add(new AssignedOpType(102, "Ledger\nReport", true, true));
        reportService.add(new AssignedOpType(103, "Fund Order\nReport", true, true));
        reportService.add(new AssignedOpType(104, "Complain\nReport", true, true));
        reportService.add(new AssignedOpType(107, "Fund Debit Credit", true, true));
        reportService.add(new AssignedOpType(108, "Fund Order Pending", true, true));
        reportService.add(new AssignedOpType(109, "User DayBook", true, true));
        reportService.add(new AssignedOpType(111, "W2R Report", true, true));

        if(isAccountStatement){
            reportService.add(new AssignedOpType(125, "Voucher Entry", true, true));
            reportService.add(new AssignedOpType(121, "Account Statement Report", true, true));
            reportService.add(new AssignedOpType(122, "FOS Outstanding", true, true));
            reportService.add(new AssignedOpType(123, "Channel Outstanding", true, true));
        }


        return reportService;
    }

    public ArrayList<AssignedOpType> getOtherBankingFundOption(boolean isAddMoneyEnable,boolean isUpiPayment){
        ArrayList<AssignedOpType> dashBoardBankingFundList = new ArrayList<>();
        dashBoardBankingFundList.add(new AssignedOpType(100,"Fund Request",true,true));
        if(isAddMoneyEnable){
            dashBoardBankingFundList.add(new AssignedOpType(37,"Add Money",true,true));
        }
        if(isUpiPayment){
            dashBoardBankingFundList.add(new AssignedOpType(62, "Upi Payment", true, true));
            dashBoardBankingFundList.add(new AssignedOpType(63, "Scan & Pay", true, true));
        }
        return dashBoardBankingFundList;

    }

    public ArrayList<AssignedOpType> getFOSOptions(boolean isAccountStatement) {

        ArrayList<AssignedOpType> fosList = new ArrayList<>();
        fosList.add(new AssignedOpType(117, "FOS UserList", true, true));
        fosList.add(new AssignedOpType(102, "Ledger\nReport", true, true));
        if(isAccountStatement)
        fosList.add(new AssignedOpType(123, "Channel Outstanding", true, true));


        return fosList;
    }

    public ArrayList<AssignedOpType> getOtherReportOption(boolean isDmtEnable,boolean isAepsEnable,boolean isSubscriptionEnable,boolean isAccountStatement) {

        ArrayList<AssignedOpType> reportService = new ArrayList<>();
        reportService.add(new AssignedOpType(101, "Recharge\nReport", true, true));
        if (isDmtEnable)
            reportService.add(new AssignedOpType(105, "DMT\nReport", true, true));
        if (isAepsEnable)
            reportService.add(new AssignedOpType(112, "AEPS\nReport", true, true));
        /*if (isSubscriptionEnable)
            reportService.add(new AssignedOpType(113, "DTH Subscription\nReport", true, true));*/

        reportService.add(new AssignedOpType(102, "Ledger\nReport", true, true));
        reportService.add(new AssignedOpType(103, "Fund Order\nReport", true, true));
        reportService.add(new AssignedOpType(104, "Complain\nReport", true, true));
        reportService.add(new AssignedOpType(107, "Fund Debit Credit", true, true));
        reportService.add(new AssignedOpType(108, "Fund Order Pending", true, true));
        reportService.add(new AssignedOpType(109, "User DayBook", true, true));
        reportService.add(new AssignedOpType(111, "W2R Report", true, true));

        return reportService;
    }
}
