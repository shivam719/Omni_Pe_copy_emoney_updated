package com.fintech.omnipe.Api.Response;

import com.fintech.omnipe.Api.Object.RefferalImage;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AppBannerResponse {

    @SerializedName("statuscode")
    @Expose
    private Integer statuscode;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("isVersionValid")
    @Expose
    private boolean isVersionValid;
    @SerializedName("isAppValid")
    @Expose
    private boolean isAppValid;
    @SerializedName("refferalContent")
    @Expose
    private String refferalContent;
    @SerializedName("refferalImage")
    @Expose
    private List<RefferalImage> refferalImage = null;
    @SerializedName("banners")
    @Expose
    private List<RefferalImage> banners = null;


    public List<RefferalImage> getBanners() {
        return banners;
    }

    @SerializedName("appLogoUrl")
    @Expose
    private String appLogoUrl;

    public String getAppLogoUrl() {
        return appLogoUrl;
    }

    public Integer getStatuscode() {
        return statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isVersionValid() {
        return isVersionValid;
    }

    public boolean isAppValid() {
        return isAppValid;
    }

    public String getRefferalContent() {
        return refferalContent;
    }

    public List<RefferalImage> getRefferalImage() {
        return refferalImage;
    }
}
