package com.fintech.omnipe.Api.Response;

import com.fintech.omnipe.Api.Object.NumberList;
import com.fintech.omnipe.Fragments.dto.OperatorList;

import java.io.Serializable;
import java.util.ArrayList;

public class NumberlistData  implements Serializable {

    private ArrayList<OperatorList> operators;
    private ArrayList<NumberList> numSeries;
    private ArrayList<CircleList> cirlces;

    public ArrayList<NumberList> getNumSeries() {
        return numSeries;
    }

    public void setNumSeries(ArrayList<NumberList> numSeries) {
        this.numSeries = numSeries;
    }

    public ArrayList<OperatorList> getOperators() {
        return operators;
    }

    public void setOperators(ArrayList<OperatorList> operators) {
        this.operators = operators;
    }

    public ArrayList<CircleList> getCirlces() {
        return cirlces;
    }

    public void setCirlces(ArrayList<CircleList> cirlces) {
        this.cirlces = cirlces;
    }


}
