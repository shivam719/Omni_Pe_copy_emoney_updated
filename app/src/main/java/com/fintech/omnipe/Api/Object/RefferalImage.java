package com.fintech.omnipe.Api.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RefferalImage {

    @SerializedName("resourceUrl")
    @Expose
    private String resourceUrl;
    @SerializedName("fileName")
    @Expose
    private String fileName;
    @SerializedName("siteResourceUrl")
    @Expose
    private String siteResourceUrl;
    @SerializedName("siteFileName")
    @Expose
    private String siteFileName;
    @SerializedName("popupResourceUrl")
    @Expose
    private String popupResourceUrl;
    @SerializedName("popupFileName")
    @Expose
    private String popupFileName;


    public String getResourceUrl() {
        return resourceUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public String getSiteResourceUrl() {
        return siteResourceUrl;
    }

    public String getSiteFileName() {
        return siteFileName;
    }

    public String getPopupResourceUrl() {
        return popupResourceUrl;
    }

    public String getPopupFileName() {
        return popupFileName;
    }
}
