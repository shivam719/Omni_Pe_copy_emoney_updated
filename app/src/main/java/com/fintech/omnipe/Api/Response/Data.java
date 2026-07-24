package com.fintech.omnipe.Api.Response;

import com.fintech.omnipe.Api.Object.OpOptionalDic;
import com.fintech.omnipe.Api.Object.OperatorParam;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.fintech.omnipe.Api.Object.OperatorOptional;
import com.fintech.omnipe.Api.Object.AssignedOpType;

import java.util.List;



public class Data {

    @SerializedName("assignedOpTypes")
    @Expose
    public List<AssignedOpType> assignedOpTypes = null;
    @SerializedName("operatorParams")
    @Expose
    private List<OperatorParam> operatorParams = null;

    @SerializedName("operatorOptionals")
    @Expose
    public List<OperatorOptional> operatorOptionals = null;
    @SerializedName("opOptionalDic")
    @Expose
    public List<OpOptionalDic> opOptionalDic = null;

    @SerializedName("isDoubleFactor")
    @Expose
    public boolean isDoubleFactor;
    @SerializedName("isPN")
    @Expose
    public boolean isPN;
    @SerializedName("isPinRequired")
    @Expose
    public boolean isPinRequired;
    @SerializedName("wid")
    @Expose
    public int wid;

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("mobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("emailID")
    @Expose
    private String emailID;
    @SerializedName("userID")
    @Expose
    private String userID;
    @SerializedName("loginTypeID")
    @Expose
    private String loginTypeID;
    @SerializedName("lt")
    @Expose
    private String lt;
    @SerializedName("sessionID")
    @Expose
    private String sessionID;
    @SerializedName("roleName")
    @Expose
    private String roleName;
    @SerializedName("roleID")
    @Expose
    private String roleID;
    @SerializedName("slabID")
    @Expose
    private String slabID;
    @SerializedName("session")
    @Expose
    private String session;
    @SerializedName("outletID")
    @Expose
    private String outletID;
    @SerializedName(value = "canDebit",alternate = "isDebitAllowed")
    @Expose
    private boolean canDebit;
    @SerializedName("outletName")
    @Expose
    private String outletName;
    @SerializedName("address")
    @Expose
    private String address;
    private String balance;
    private String uBalance;
    private String bBalance;
    private String cBalance;
    private String idbalance;

    public boolean isPN() {
        return isPN;
    }

    public boolean isPinRequired() {
        return isPinRequired;
    }

    public String getLt() {
        return lt;
    }

    public String getSession() {
        if (session != null && !session.isEmpty()) {
            return session;
        } else {
            return "";
        }
    }

    public List<AssignedOpType> getAssignedOpTypes() {
        return assignedOpTypes;
    }

    public String getOutletID() {
        return outletID;
    }

    public void setOutletID(String outletID) {
        this.outletID = outletID;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getLoginTypeID() {
        return loginTypeID;
    }

    public void setLoginTypeID(String loginTypeID) {
        this.loginTypeID = loginTypeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleID() {
        return roleID;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }

    public String getSlabID() {
        return slabID;
    }

    public void setSlabID(String slabID) {
        this.slabID = slabID;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getuBalance() {
        return uBalance;
    }

    public void setuBalance(String uBalance) {
        this.uBalance = uBalance;
    }

    public String getbBalance() {
        return bBalance;
    }

    public void setbBalance(String bBalance) {
        this.bBalance = bBalance;
    }

    public String getcBalance() {
        return cBalance;
    }

    public void setcBalance(String cBalance) {
        this.cBalance = cBalance;
    }

    public String getIdbalance() {
        return idbalance;
    }

    public void setIdbalance(String idbalance) {
        this.idbalance = idbalance;
    }

    public boolean isCanDebit() {
        return canDebit;
    }

    public boolean isDoubleFactor() {
        return isDoubleFactor;
    }

    public List<OperatorOptional> getOperatorOptionals() {
        return operatorOptionals;
    }

    public String getOutletName() {
        return outletName;
    }

    public String getAddress() {
        return address;
    }

    public List<OperatorParam> getOperatorParams() {
        return operatorParams;
    }

    public int getWid() {
        return wid;
    }

    public List<OpOptionalDic> getOpOptionalDic() {
        return opOptionalDic;
    }
}
