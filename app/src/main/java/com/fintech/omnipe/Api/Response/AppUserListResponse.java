package com.fintech.omnipe.Api.Response;

import com.fintech.omnipe.Api.Object.IncentiveDetails;
import com.fintech.omnipe.Api.Object.TargetAchieved;
import com.fintech.omnipe.AppUser.dto.FosList;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.fintech.omnipe.Api.Object.ActiveSerive;
import com.fintech.omnipe.Api.Object.Banners;
import com.fintech.omnipe.Api.Object.ChildRoles;
import com.fintech.omnipe.Api.Object.CompanyProfile;
import com.fintech.omnipe.Api.Object.FundRequestForApproval;
import com.fintech.omnipe.Api.Object.NewsContent;
import com.fintech.omnipe.Api.Object.NotificationData;
import com.fintech.omnipe.Api.Object.PGModelForApp;
import com.fintech.omnipe.Api.Object.PaymentGatewayType;
import com.fintech.omnipe.Api.Object.RefundLog;
import com.fintech.omnipe.Api.Object.UserDaybookDMRReport;
import com.fintech.omnipe.Api.Object.UserDaybookReport;
import com.fintech.omnipe.Api.Object.UserList;
import com.fintech.omnipe.PSA.ui.PanDetail;

import java.util.ArrayList;
import java.util.List;

public class AppUserListResponse {

    @SerializedName("fosList")
    @Expose
    private FosList fosList;

    public FosList getFosList() {
        return fosList;
    }

    @SerializedName("incentiveDetails")
    @Expose
    public ArrayList<IncentiveDetails> incentiveDetails = null;

    @SerializedName("targetAchieveds")
    @Expose
    public List<TargetAchieved> targetAchieveds = null;
    @SerializedName("pGModelForApp")
    @Expose
    public PGModelForApp pGModelForApp = null;
    @SerializedName("pGs")
    @Expose
    public ArrayList<PaymentGatewayType> pGs = null;
    @SerializedName("companyProfile")
    @Expose
    public CompanyProfile companyProfile = null;
    @SerializedName("notifications")
    @Expose
    public List<NotificationData> notifications = null;
    @SerializedName("userDaybookReport")
    @Expose
    public List<UserDaybookReport> userDaybookReport = null;
    @SerializedName("userDaybookDMRReport")
    @Expose
    public List<UserDaybookDMRReport> userDaybookDMTReport = null;
    @SerializedName("refundLog")
    @Expose
    public List<RefundLog> refundLog = null;
    @SerializedName("rechargeReport")
    @Expose
    public Object rechargeReport;
    @SerializedName("dmtReport")
    @Expose
    public Object dmtReport;
    @SerializedName("ledgerReport")
    @Expose
    public Object ledgerReport;
    @SerializedName("fundDCReport")
    @Expose
    public Object fundDCReport;
    @SerializedName("fundOrderReport")
    @Expose
    public Object fundOrderReport;
    @SerializedName("slabCommissions")
    @Expose
    public Object slabCommissions;
    @SerializedName("userList")
    @Expose
    public List<UserList> userList = null;
    @SerializedName("childRoles")
    @Expose
    public List<ChildRoles> childRoles = null;
    @SerializedName("fundRequestForApproval")
    @Expose
    public List<FundRequestForApproval> fundRequestForApproval = null;
    @SerializedName("banners")
    @Expose
    public ArrayList<Banners> banners = null;

    @SerializedName("newsContent")
    @Expose
    public NewsContent newsContent = null;

    @SerializedName("statuscode")
    @Expose
    public Integer statuscode;
    @SerializedName("msg")
    @Expose
    public String msg;
    @SerializedName("isVersionValid")
    @Expose
    public Boolean isVersionValid;
    @SerializedName("isAppValid")
    @Expose
    public Boolean isAppValid;

    @SerializedName("getActiveSerive")
    @Expose
    public List<ActiveSerive> activeSerive = null;

    @SerializedName("panDetail")
    @Expose
    private PanDetail panDetail;

    public CompanyProfile getCompanyProfile() {
        return companyProfile;
    }

    public Object getRechargeReport() {
        return rechargeReport;
    }

    public Object getDmtReport() {
        return dmtReport;
    }

    public Object getLedgerReport() {
        return ledgerReport;
    }

    public Object getFundDCReport() {
        return fundDCReport;
    }

    public Object getFundOrderReport() {
        return fundOrderReport;
    }

    public Object getSlabCommissions() {
        return slabCommissions;
    }

    public List<UserList> getUserList() {
        return userList;
    }

    public List<ChildRoles> getChildRoles() {
        return childRoles;
    }

    public Integer getStatuscode() {
        return statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public Boolean getVersionValid() {
        return isVersionValid;
    }

    public List<FundRequestForApproval> getFundRequestForApproval() {
        return fundRequestForApproval;
    }

    public List<UserDaybookDMRReport> getUserDaybookDMTReport() {
        return userDaybookDMTReport;
    }

    public List<RefundLog> getRefundLog() {
        return refundLog;
    }

    public List<UserDaybookReport> getUserDaybookReport() {
        return userDaybookReport;
    }

    public ArrayList<Banners> getBanners() {
        return banners;
    }

    public NewsContent getNewsContent() {
        return newsContent;
    }

    public Boolean getAppValid() {
        return isAppValid;
    }

    public List<NotificationData> getNotifications() {
        return notifications;
    }

    public List<ActiveSerive> getActiveSerive() {
        return activeSerive;
    }

    public PanDetail getPanDetail() {
        return panDetail;
    }

    public PGModelForApp getpGModelForApp() {
        return pGModelForApp;
    }

    public ArrayList<PaymentGatewayType> getpGs() {
        return pGs;
    }

    public List<TargetAchieved> getTargetAchieveds() {
        return targetAchieveds;
    }

    public ArrayList<IncentiveDetails> getIncentiveDetails() {
        return incentiveDetails;
    }

    @SerializedName("userListRoleWise")
    @Expose
    public List<UserListRoleWiseData> userListRoleWise = null;

    public List<UserListRoleWiseData> getUserListRoleWise() {
        return userListRoleWise;
    }
}
