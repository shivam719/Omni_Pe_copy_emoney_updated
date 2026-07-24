package com.fintech.omnipe.Api.Object;

import java.io.Serializable;

/**
 * Created by Lalit on 15-02-2017.
 */

public class NumberList  implements Serializable {
    private int oid;
    private String series;
    private String circleCode;

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getCircleCode() {
        return circleCode;
    }

    public void setCircleCode(String circleCode) {
        this.circleCode = circleCode;
    }


}
