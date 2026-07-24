package com.fintech.omnipe.Api.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.fintech.omnipe.Api.Object.BcResponse;
import com.fintech.omnipe.Fragments.SdkDetail;

import java.util.List;

public class OnboardingResponse {
    public void setoId(String oId) {
        this.oId = oId;
    }
    @SerializedName("oId")
    @Expose
    private String oId;
    @SerializedName("isConfirmation")
    @Expose
    private boolean isConfirmation;
    @SerializedName("isRedirection")
    @Expose
    private boolean isRedirection;
    @SerializedName("isOTPRequired")
    @Expose
    private boolean isOTPRequired;
    @SerializedName("isDown")
    @Expose
    private boolean isDown;
    @SerializedName("isWaiting")
    @Expose
    private boolean isWaiting;
    @SerializedName("isRejected")
    @Expose
    private boolean isRejected;
    @SerializedName("isIncomplete")
    @Expose
    private boolean isIncomplete;
    @SerializedName("isUnathorized")
    @Expose
    private boolean isUnathorized;
    @SerializedName("isShowMsg")
    @Expose
    private boolean isShowMsg;

    @SerializedName("giurl")
    @Expose
    private String giurl;

    @SerializedName("isBioMetricRequired")
    @Expose
    private boolean isBioMetricRequired;
    @SerializedName("bioAuthType")
    @Expose
    private int bioAuthType;

    @SerializedName("isRedirectToExternal")
    @Expose
    private boolean isRedirectToExternal;
    @SerializedName("externalURL")
    @Expose
    private String externalURL;
    @SerializedName("otpRefID")
    @Expose
    private String otpRefID;
    @SerializedName("bcResponse")
    @Expose
    private List<BcResponse> bcResponse = null;
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
    @SerializedName("checkID")
    @Expose
    private int checkID;
    @SerializedName("isPasswordExpired")
    @Expose
    private boolean isPasswordExpired;
    @SerializedName("mobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("emailID")
    @Expose
    private String emailID;
    @SerializedName("panid")
    @Expose
    private String panid;

    @SerializedName("sdkType")
    @Expose
    private Integer sdkType;
    @SerializedName("sdkDetail")
    @Expose
    private SdkDetail sdkDetail;


    public String getoId() {
        return oId;
    }

    public boolean isBioMetricRequired() {
        return isBioMetricRequired;
    }

    public int getBioAuthType() {
        return bioAuthType;
    }

    public boolean isRedirectToExternal() {
        return isRedirectToExternal;
    }

    public String getExternalURL() {
        return externalURL;
    }

    public Integer getSdkType() {
        return sdkType;
    }

    public void setSdkType(Integer sdkType) {
        this.sdkType = sdkType;
    }

    public SdkDetail getSdkDetail() {
        return sdkDetail;
    }

    public void setSdkDetail(SdkDetail sdkDetail) {
        this.sdkDetail = sdkDetail;
    }

    public boolean getIsConfirmation() {
        return isConfirmation;
    }

    public void setIsConfirmation(boolean isConfirmation) {
        this.isConfirmation = isConfirmation;
    }

    public boolean getIsRedirection() {
        return isRedirection;
    }

    public void setIsRedirection(boolean isRedirection) {
        this.isRedirection = isRedirection;
    }

    public boolean getIsOTPRequired() {
        return isOTPRequired;
    }

    public void setIsOTPRequired(boolean isOTPRequired) {
        this.isOTPRequired = isOTPRequired;
    }

    public boolean getIsDown() {
        return isDown;
    }

    public void setIsDown(boolean isDown) {
        this.isDown = isDown;
    }

    public boolean getIsWaiting() {
        return isWaiting;
    }

    public void setIsWaiting(boolean isWaiting) {
        this.isWaiting = isWaiting;
    }

    public boolean getIsRejected() {
        return isRejected;
    }

    public void setIsRejected(boolean isRejected) {
        this.isRejected = isRejected;
    }

    public boolean getIsIncomplete() {
        return isIncomplete;
    }

    public void setIsIncomplete(boolean isIncomplete) {
        this.isIncomplete = isIncomplete;
    }

    public boolean getIsUnathorized() {
        return isUnathorized;
    }
    public boolean getIsShowMsg() {
        return isShowMsg;
    }
    public void setIsUnathorized(boolean isUnathorized) {
        this.isUnathorized = isUnathorized;
    }

    public List<BcResponse> getBcResponse() {
        return bcResponse;
    }

    public void setBcResponse(List<BcResponse> bcResponse) {
        this.bcResponse = bcResponse;
    }

    public int getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(int statuscode) {
        this.statuscode = statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean getIsVersionValid() {
        return isVersionValid;
    }

    public void setIsVersionValid(boolean isVersionValid) {
        this.isVersionValid = isVersionValid;
    }

    public boolean getIsAppValid() {
        return isAppValid;
    }

    public void setIsAppValid(boolean isAppValid) {
        this.isAppValid = isAppValid;
    }

    public int getCheckID() {
        return checkID;
    }

    public void setCheckID(int checkID) {
        this.checkID = checkID;
    }

    public boolean getIsPasswordExpired() {
        return isPasswordExpired;
    }

    public void setIsPasswordExpired(boolean isPasswordExpired) {
        this.isPasswordExpired = isPasswordExpired;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getPanid() {
        return panid;
    }

    public boolean isConfirmation() {
        return isConfirmation;
    }

    public boolean isRedirection() {
        return isRedirection;
    }

    public boolean isOTPRequired() {
        return isOTPRequired;
    }

    public boolean isDown() {
        return isDown;
    }

    public boolean isWaiting() {
        return isWaiting;
    }

    public boolean isRejected() {
        return isRejected;
    }

    public boolean isIncomplete() {
        return isIncomplete;
    }

    public boolean isUnathorized() {
        return isUnathorized;
    }

    public boolean isShowMsg() {
        return isShowMsg;
    }

    public String getGiurl() {
        return giurl;
    }

    public boolean isVersionValid() {
        return isVersionValid;
    }

    public boolean isAppValid() {
        return isAppValid;
    }

    public boolean isPasswordExpired() {
        return isPasswordExpired;
    }

    public String getOtpRefID() {
        return otpRefID;
    }
}
