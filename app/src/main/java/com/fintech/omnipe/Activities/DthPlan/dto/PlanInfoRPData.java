package com.fintech.omnipe.Activities.DthPlan.dto;

import java.io.Serializable;
import java.util.ArrayList;

public class PlanInfoRPData implements Serializable {
    String tel;
    String operator;
    PlanInfoRPRecords records;
    int status;


    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public PlanInfoRPRecords getRecords() {
        return records;
    }

    public void setRecords(PlanInfoRPRecords records) {
        this.records = records;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    ArrayList<PlanRPResponse> response=new ArrayList<>();

    public ArrayList<PlanRPResponse> getResponse() {
        return response;
    }
}

