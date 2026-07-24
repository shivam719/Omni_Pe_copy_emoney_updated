package com.fintech.omnipe.MoveToWallet.Api;

import java.util.ArrayList;

public class MoveToBankReportResponse {

    private int statuscode;
    private String msg;
    private boolean isVersionValid;

    private ArrayList<MoveToWalletReportData> moveToWalletReport;

    public int getStatuscode() {
        return statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isVersionValid() {
        return isVersionValid;
    }

    public ArrayList<MoveToWalletReportData> getMoveToWalletReport() {
        return moveToWalletReport;
    }
}
