package com.fintech.omnipe.Api.Response;

import com.fintech.omnipe.Api.Object.BankDetailBank;
import com.fintech.omnipe.Api.Object.BankDetailUser;

import java.util.ArrayList;



public class BankDetailResponse {

    private String RESPONSESTATUS;
    private String message;
    private ArrayList<BankDetailUser> Table;
    private ArrayList<BankDetailBank> Table1;

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

    public ArrayList<BankDetailUser> getTable() {
        return Table;
    }

    public void setTable(ArrayList<BankDetailUser> table) {
        Table = table;
    }

    public ArrayList<BankDetailBank> getTable1() {
        return Table1;
    }

    public void setTable1(ArrayList<BankDetailBank> table1) {
        Table1 = table1;
    }
}
