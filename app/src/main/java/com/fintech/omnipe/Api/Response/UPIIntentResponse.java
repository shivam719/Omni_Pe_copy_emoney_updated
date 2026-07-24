package com.fintech.omnipe.Api.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UPIIntentResponse {
    @SerializedName("apiResp")
    @Expose
    public ApiResp apiResp;

    public ApiResp getApiResp() {
        return apiResp;
    }
}
