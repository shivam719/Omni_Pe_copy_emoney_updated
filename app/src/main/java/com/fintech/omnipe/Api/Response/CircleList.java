package com.fintech.omnipe.Api.Response;

import java.io.Serializable;

public class CircleList  implements Serializable {
    private String id;
    private String circle;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCircle() {
        return circle;
    }

    public void setCircle(String circle) {
        this.circle = circle;
    }
}
