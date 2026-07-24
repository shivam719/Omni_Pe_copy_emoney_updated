package com.fintech.omnipe.Api.Response;

import com.fintech.omnipe.Api.Object.FundRecObject;

import java.util.ArrayList;



public class FundRecResponse {

    private String RESPONSESTATUS;
    private String message;
    private ArrayList<FundRecObject> FundReceive;

    public String getRESPONSESTATUS() {
        return RESPONSESTATUS;
    }

    public void setRESPONSESTATUS(String RESPONSESTATUS) {
        this.RESPONSESTATUS = RESPONSESTATUS;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<FundRecObject> getFundReceive() {
        return FundReceive;
    }

    public void setFundReceive(ArrayList<FundRecObject> fundReceive) {
        FundReceive = fundReceive;
    }
}
