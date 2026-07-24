package com.fintech.omnipe.Fragments.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.fintech.omnipe.Api.Object.Role;

import java.io.Serializable;
import java.util.List;

public class UserDetailInfo implements Serializable {
    @SerializedName("commRate")
    @Expose
    public int commRate;
    @SerializedName("stateID")
    @Expose
    public int stateID;
    @SerializedName("profilePic")
    @Expose
    public String profilePic;
    @SerializedName("kycStatus")
    @Expose
    public int kycStatus;
    @SerializedName("aadhar")
    @Expose
    public String aadhar;
    @SerializedName("pan")
    @Expose
    public String pan;
    @SerializedName("gstin")
    @Expose
    public String gstin;

    @SerializedName("address")
    @Expose
    public String address;
    @SerializedName("loginID")
    @Expose
    public int loginID;
    @SerializedName("lt")
    @Expose
    public int lt;
    @SerializedName("city")
    @Expose
    public String city;
    @SerializedName("stateName")
    @Expose
    public String stateName;
    @SerializedName("roles")
    @Expose
    public List<Role> roles = null;
    @SerializedName("slabs")
    @Expose
    public String slabs;
    @SerializedName("states")
    @Expose
    public String states;
    @SerializedName("ip")
    @Expose
    public String ip;
    @SerializedName("browser")
    @Expose
    public String browser;
    @SerializedName("resultCode")
    @Expose
    public int resultCode;
    @SerializedName("msg")
    @Expose
    public String msg;
    @SerializedName("userID")
    @Expose
    public int userID;
    @SerializedName("mobileNo")
    @Expose
    public String mobileNo;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("outletName")
    @Expose
    public String outletName;
    @SerializedName("emailID")
    @Expose
    public String emailID;
    @SerializedName("roleID")
    @Expose
    public int roleID;
    @SerializedName("role")
    @Expose
    public String role;
    @SerializedName("referalID")
    @Expose
    public int referalID;
    @SerializedName("slabID")
    @Expose
    public int slabID;
    @SerializedName("isGSTApplicable")
    @Expose
    public Boolean isGSTApplicable;
    @SerializedName("isTDSApplicable")
    @Expose
    public Boolean isTDSApplicable;
    @SerializedName("isCCFGstApplicable")
    @Expose
    public Boolean isCCFGstApplicable;
    @SerializedName("isVirtual")
    @Expose
    public Boolean isVirtual;
    @SerializedName("isWebsite")
    @Expose
    public Boolean isWebsite;
    @SerializedName("isAdminDefined")
    @Expose
    public Boolean isAdminDefined;
    @SerializedName("isSurchargeGST")
    @Expose
    public Boolean isSurchargeGST;
    @SerializedName("prefix")
    @Expose
    public String prefix;
    @SerializedName("outletID")
    @Expose
    public int outletID;
    @SerializedName("pincode")
    @Expose
    public String pincode;
    @SerializedName("wid")
    @Expose
    public int wid;
    @SerializedName("isDoubleFactor")
    @Expose
    public boolean isDoubleFactor;

    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("shoptype")
    @Expose
    private String shoptype;
    @SerializedName("qualification")
    @Expose
    private String qualification;
    @SerializedName("poupulation")
    @Expose
    private String poupulation;
    @SerializedName("locationType")
    @Expose
    private String locationType;
    @SerializedName("landmark")
    @Expose
    private String landmark;
    @SerializedName("alternateMobile")
    @Expose
    private String alternateMobile;
    @SerializedName("bankName")
    @Expose
    private String bankName;
    @SerializedName("ifsc")
    @Expose
    private String ifsc;
    @SerializedName("branchName")
    @Expose
    private String branchName;
    @SerializedName("accountNumber")
    @Expose
    private String accountNumber;
    @SerializedName("accountName")
    @Expose
    private String accountName;


    public void setDoubleFactor(boolean doubleFactor) {
        isDoubleFactor = doubleFactor;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getShoptype() {
        return shoptype;
    }

    public void setShoptype(String shoptype) {
        this.shoptype = shoptype;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getPoupulation() {
        return poupulation;
    }

    public void setPoupulation(String poupulation) {
        this.poupulation = poupulation;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getAlternateMobile() {
        return alternateMobile;
    }

    public void setAlternateMobile(String alternateMobile) {
        this.alternateMobile = alternateMobile;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public int getCommRate() {
        return commRate;
    }

    public int getStateID() {
        return stateID;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public boolean isDoubleFactor() {
        return isDoubleFactor;
    }

    public int getKycStatus() {
        return kycStatus;
    }

    public String getAadhar() {
        return aadhar;
    }

    public String getPan() {
        return pan;
    }

    public String getGstin() {
        return gstin;
    }


    public String getAddress() {
        return address;
    }

    public int getLoginID() {
        return loginID;
    }

    public int getLt() {
        return lt;
    }

    public String getCity() {
        return city;
    }

    public String getStateName() {
        return stateName;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public String getSlabs() {
        return slabs;
    }

    public String getStates() {
        return states;
    }

    public String getIp() {
        return ip;
    }

    public String getBrowser() {
        return browser;
    }

    public int getResultCode() {
        return resultCode;
    }

    public String getMsg() {
        return msg;
    }

    public int getUserID() {
        return userID;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public String getName() {
        return name;
    }

    public String getOutletName() {
        return outletName;
    }

    public String getEmailID() {
        return emailID;
    }

    public int getRoleID() {
        return roleID;
    }

    public String getRole() {
        return role;
    }

    public int getReferalID() {
        return referalID;
    }

    public int getSlabID() {
        return slabID;
    }

    public Boolean getGSTApplicable() {
        return isGSTApplicable;
    }

    public Boolean getTDSApplicable() {
        return isTDSApplicable;
    }

    public Boolean getCCFGstApplicable() {
        return isCCFGstApplicable;
    }

    public Boolean getVirtual() {
        return isVirtual;
    }

    public Boolean getWebsite() {
        return isWebsite;
    }

    public Boolean getAdminDefined() {
        return isAdminDefined;
    }

    public Boolean getSurchargeGST() {
        return isSurchargeGST;
    }

    public String getPrefix() {
        return prefix;
    }

    public int getOutletID() {
        return outletID;
    }

    public String getPincode() {
        return pincode;
    }

    public int getWid() {
        return wid;
    }

    public void setCommRate(int commRate) {
        this.commRate = commRate;
    }

    public void setStateID(int stateID) {
        this.stateID = stateID;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public void setKycStatus(int kycStatus) {
        this.kycStatus = kycStatus;
    }

    public void setAadhar(String aadhar) {
        this.aadhar = aadhar;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public void setGstin(String gstin) {
        this.gstin = gstin;
    }


    public void setAddress(String address) {
        this.address = address;
    }

    public void setLoginID(int loginID) {
        this.loginID = loginID;
    }

    public void setLt(int lt) {
        this.lt = lt;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public void setSlabs(String slabs) {
        this.slabs = slabs;
    }

    public void setStates(String states) {
        this.states = states;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOutletName(String outletName) {
        this.outletName = outletName;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setReferalID(int referalID) {
        this.referalID = referalID;
    }

    public void setSlabID(int slabID) {
        this.slabID = slabID;
    }

    public void setGSTApplicable(Boolean GSTApplicable) {
        isGSTApplicable = GSTApplicable;
    }

    public void setTDSApplicable(Boolean TDSApplicable) {
        isTDSApplicable = TDSApplicable;
    }

    public void setCCFGstApplicable(Boolean CCFGstApplicable) {
        isCCFGstApplicable = CCFGstApplicable;
    }

    public void setVirtual(Boolean virtual) {
        isVirtual = virtual;
    }

    public void setWebsite(Boolean website) {
        isWebsite = website;
    }

    public void setAdminDefined(Boolean adminDefined) {
        isAdminDefined = adminDefined;
    }

    public void setSurchargeGST(Boolean surchargeGST) {
        isSurchargeGST = surchargeGST;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setOutletID(int outletID) {
        this.outletID = outletID;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public void setWid(int wid) {
        this.wid = wid;
    }
}
