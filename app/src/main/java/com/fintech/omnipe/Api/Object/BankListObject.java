package com.fintech.omnipe.Api.Object;

import java.io.Serializable;

/**
 * Created by Lalit on 12-04-2017.
 */

public class BankListObject implements Serializable {

    private String id;
    private String bankName;
    private String accountLimit;
    private String code;
    private String isIMPS;
    private String isNEFT;
    private String isACVerification;
    private String ifsc;
    private String logo;
    private String ekO_BankID;
    private int bankID;
    private int iin;
    boolean isNotLive;


    public int getBankID() {
        return bankID;
    }

    public int getIin() {
        return iin;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountLimit() {
        return accountLimit;
    }

    public void setAccountLimit(String accountLimit) {
        this.accountLimit = accountLimit;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIsIMPS() {
        return isIMPS;
    }

    public void setIsIMPS(String isIMPS) {
        this.isIMPS = isIMPS;
    }

    public String getIsNEFT() {
        return isNEFT;
    }

    public void setIsNEFT(String isNEFT) {
        this.isNEFT = isNEFT;
    }

    public String getIsACVerification() {
        return isACVerification;
    }

    public void setIsACVerification(String isACVerification) {
        this.isACVerification = isACVerification;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getEkO_BankID() {
        return ekO_BankID;
    }

    public void setEkO_BankID(String ekO_BankID) {
        this.ekO_BankID = ekO_BankID;
    }

    public boolean isNotLive() {
        return isNotLive;
    }
}
