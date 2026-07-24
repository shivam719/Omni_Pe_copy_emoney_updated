package com.fintech.omnipe.Api.Response;

import com.google.gson.annotations.SerializedName;
import com.fintech.omnipe.Api.Object.BCDetail;

import java.util.ArrayList;

/**
 * Created by Lalit on 08-04-2017.
 */

public class RechargeResponse {

    //AEPS 2
    private String APIKey;
    @SerializedName("MESSAGE")
    private String MESSAGE;
    @SerializedName("STATUS")
    private String STATUS;
    @SerializedName("TRANSACTION_ID")
    private String TRANSACTION_ID;
    private ArrayList<BCDetail> Table;
    private String RESPONSESTATUS;
    private String message;
    private ArrayList<OfferDetail> notification;

    public String getAPIKey() {
        return APIKey;
    }

    public void setAPIKey(String APIKey) {
        this.APIKey = APIKey;
    }

    public String getMESSAGE() {
        return MESSAGE;
    }

    public void setMESSAGE(String MESSAGE) {
        this.MESSAGE = MESSAGE;
    }

//Aeps2

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getTRANSACTION_ID() {
        return TRANSACTION_ID;
    }

    public void setTRANSACTION_ID(String TRANSACTION_ID) {
        this.TRANSACTION_ID = TRANSACTION_ID;
    }

    public ArrayList<BCDetail> getTable() {
        return Table;
    }

    public void setTable(ArrayList<BCDetail> table) {
        Table = table;
    }

    public ArrayList<OfferDetail> getNotification() {
        return notification;
    }

    public void setNotification(ArrayList<OfferDetail> notification) {
        this.notification = notification;
    }

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
}
