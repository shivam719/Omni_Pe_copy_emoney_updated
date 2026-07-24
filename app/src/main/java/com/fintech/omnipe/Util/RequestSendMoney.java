package com.fintech.omnipe.Util;

public class RequestSendMoney {

    private String beneID, bankID;
    private String mobileNo, beneMobile;
    private String ifsc;
    private String accountNo;
    private String amount;
    private String channel;
    private String bank;
    private String beneName;
    private String o;
    private String oid;

    public RequestSendMoney(String beneID, String mobileNo, String ifsc, String accountNo, String amount, String channel, String bank, String beneName) {
        this.beneID = beneID;
        this.mobileNo = mobileNo;
        this.ifsc = ifsc;
        this.accountNo = accountNo;
        this.amount = amount;
        this.channel = channel;
        this.bank = bank;
        this.beneName = beneName;
    }


    public RequestSendMoney(String beneID, String mobileNo, String ifsc, String accountNo, String amount, String channel, String bank,
                            String bankID, String beneName, String beneMobile) {
        this.beneID = beneID;
        this.mobileNo = mobileNo;
        this.ifsc = ifsc;
        this.accountNo = accountNo;
        this.amount = amount;
        this.channel = channel;
        this.bank = bank;
        this.bankID = bankID;
        this.beneName = beneName;
        this.beneMobile = beneMobile;
    }

    public RequestSendMoney(String o, String oid, String beneID, String mobileNo, String ifsc, String accountNo, String amount, String channel, String bank,
                            String bankID, String beneName, String beneMobile) {
        this.o = o;
        this.oid = oid;
        this.beneID = beneID;
        this.mobileNo = mobileNo;
        this.ifsc = ifsc;
        this.accountNo = accountNo;
        this.amount = amount;
        this.channel = channel;
        this.bank = bank;
        this.bankID = bankID;
        this.beneName = beneName;
        this.beneMobile = beneMobile;
    }



}
