package com.fintech.omnipe.Fragments.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Lalit on 18-01-2017.
 */

public class OperatorList implements Serializable {

    @SerializedName("oid")
    @Expose
    private Integer oid;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("billerID")
    @Expose
    private String billerID;
    @SerializedName("operator")
    @Expose
    private String operator;
    @SerializedName("tollFree")
    @Expose
    private String tollFree;
    @SerializedName("opType")
    @Expose
    private int opType;
    @SerializedName("isBBPS")
    @Expose
    private boolean isBBPS;
    @SerializedName("isBilling")
    @Expose
    private boolean isBilling;
    @SerializedName("inSlab")
    @Expose
    private boolean inSlab;
    @SerializedName("isTakeCustomerNum")
    @Expose
    private boolean isTakeCustomerNum;
    @SerializedName("min")
    @Expose
    private Double min;
    @SerializedName("max")
    @Expose
    private Double max;
    @SerializedName("length")
    @Expose
    private Integer length;
    @SerializedName("lengthMax")
    @Expose
    private Integer lengthMax;
    @SerializedName("startWith")
    @Expose
    private String startWith;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("isPartial")
    @Expose
    private boolean isPartial;
    @SerializedName("accountName")
    @Expose
    private String accountName;
    @SerializedName("accountRemak")
    @Expose
    private String accountRemak;
    @SerializedName("isAccountNumeric")
    @Expose
    private boolean isAccountNumeric;
    @SerializedName("isGroupLeader")
    @Expose
    private boolean isGroupLeader;
    @SerializedName("commSettingType")
    @Expose
    private Integer commSettingType;
    @SerializedName("minRange")
    @Expose
    private Integer minRange;
    @SerializedName("maxRange")
    @Expose
    private Integer maxRange;
    @SerializedName("rangeId")
    @Expose
    private Integer rangeId;
    @SerializedName("charge")
    @Expose
    private Double charge;
    @SerializedName("chargeAmtType")
    @Expose
    private boolean chargeAmtType;
    @SerializedName("stateID")
    @Expose
    private Integer stateID;
    @SerializedName("ind")
    @Expose
    private Integer ind;
    @SerializedName("accountNoKey")
    @Expose
    private String accountNoKey;
    @SerializedName("regExAccount")
    @Expose
    private String regExAccount;
    @SerializedName("customerNoKey")
    @Expose
    private String customerNoKey;
    @SerializedName("planDocName")
    @Expose
    private String planDocName;
    @SerializedName("isAmountValidation")
    @Expose
    private boolean isAmountValidation;
    @SerializedName("allowedChannel")
    @Expose
    private Integer allowedChannel;
    @SerializedName("allowChannel")
    @Expose
    private String allowChannel;
    @SerializedName("serviceID")
    @Expose
    private Integer serviceID;
    @SerializedName("serviceName")
    @Expose
    private String serviceName;
    @SerializedName("packageId")
    @Expose
    private Integer packageId;
    @SerializedName("isActive")
    @Expose
    private boolean isActive;
    @SerializedName("isServiceActive")
    @Expose
    private boolean isServiceActive;
    @SerializedName("isVisible")
    @Expose
    private boolean isVisible;
    @SerializedName("sCode")
    @Expose
    private String sCode;
    @SerializedName("walletTypeID")
    @Expose
    private Integer walletTypeID;
    @SerializedName("selfAssigned")
    @Expose
    private boolean selfAssigned;
    @SerializedName("redirectURL")
    @Expose
    public String  redirectURL;

    public String getRedirectURL() {
        return redirectURL;
    }
    public int getOid() {
        return oid;
    }

    public String getName() {
        return name;
    }

    public String getBillerID() {
        return billerID;
    }

    public String getOperator() {
        return operator;
    }

    public String getTollFree() {
        return tollFree;
    }

    public int getOpType() {
        return opType;
    }

    public boolean isBBPS() {
        return isBBPS;
    }

    public boolean isBilling() {
        return isBilling;
    }

    public boolean isInSlab() {
        return inSlab;
    }

    public boolean isTakeCustomerNum() {
        return isTakeCustomerNum;
    }

    public Double getMin() {
        return min;
    }

    public Double getMax() {
        return max;
    }

    public Integer getLength() {
        return length;
    }

    public Integer getLengthMax() {
        return lengthMax;
    }

    public String getStartWith() {
        return startWith;
    }

    public String getImage() {
        return image;
    }

    public boolean isPartial() {
        return isPartial;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getAccountRemak() {
        return accountRemak;
    }

    public boolean isAccountNumeric() {
        return isAccountNumeric;
    }

    public boolean isGroupLeader() {
        return isGroupLeader;
    }

    public Integer getCommSettingType() {
        return commSettingType;
    }

    public Integer getMinRange() {
        return minRange;
    }

    public Integer getMaxRange() {
        return maxRange;
    }

    public Integer getRangeId() {
        return rangeId;
    }

    public Double getCharge() {
        return charge;
    }

    public boolean isChargeAmtType() {
        return chargeAmtType;
    }

    public Integer getStateID() {
        return stateID;
    }

    public Integer getInd() {
        return ind;
    }

    public String getAccountNoKey() {
        return accountNoKey;
    }

    public String getRegExAccount() {
        return regExAccount;
    }

    public String getCustomerNoKey() {
        return customerNoKey;
    }

    public String getPlanDocName() {
        return planDocName;
    }

    public boolean isAmountValidation() {
        return isAmountValidation;
    }

    public Integer getAllowedChannel() {
        return allowedChannel;
    }

    public String getAllowChannel() {
        return allowChannel;
    }

    public Integer getServiceID() {
        return serviceID;
    }

    public String getServiceName() {
        return serviceName;
    }

    public Integer getPackageId() {
        return packageId;
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean isServiceActive() {
        return isServiceActive;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public String getsCode() {
        return sCode;
    }

    public Integer getWalletTypeID() {
        return walletTypeID;
    }

    public boolean isSelfAssigned() {
        return selfAssigned;
    }
}
