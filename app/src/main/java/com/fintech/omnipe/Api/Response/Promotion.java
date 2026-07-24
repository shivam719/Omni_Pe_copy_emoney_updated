package com.fintech.omnipe.Api.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Promotion {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("cityId")
    @Expose
    private int cityId;
    @SerializedName("imgUrl")
    @Expose
    private String imgUrl;
    @SerializedName("linkUrl")
    @Expose
    private String linkUrl;
    @SerializedName("entryOn")
    @Expose
    private String entryOn;
    @SerializedName("modifyOn")
    @Expose
    private String modifyOn;
    @SerializedName("isActive")
    @Expose
    private Boolean isActive;
    @SerializedName("cityName")
    @Expose
    private String cityName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getEntryOn() {
        return entryOn;
    }

    public void setEntryOn(String entryOn) {
        this.entryOn = entryOn;
    }

    public String getModifyOn() {
        return modifyOn;
    }

    public void setModifyOn(String modifyOn) {
        this.modifyOn = modifyOn;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

}