package com.fintech.omnipe.Fragments;

import android.os.Parcel;
import android.os.Parcelable;

import com.fintech.omnipe.Api.Object.BcResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SdkDetail implements Parcelable {

    @SerializedName("apiOutletID")
    @Expose
    private String apiOutletID;
    @SerializedName("apiOutletPassword")
    @Expose
    private String apiOutletPassword;
    @SerializedName("apiPartnerID")
    @Expose
    private String apiPartnerID;
    @SerializedName("apiOutletMob")
    @Expose
    private String apiOutletMob;
    @SerializedName("emailID")
    @Expose
    private String emailID;
    @SerializedName("outletName")
    @Expose
    private String outletName;
    @SerializedName("serviceOutletPIN")
    @Expose
    private String serviceOutletPIN;
    @SerializedName("bcResponse")
    @Expose
    private List<BcResponse> bcResponse = null;

    protected SdkDetail(Parcel in) {
        apiOutletID = in.readString();
        apiOutletPassword = in.readString();
        apiPartnerID = in.readString();
        apiOutletMob = in.readString();
        serviceOutletPIN = in.readString();
        outletName = in.readString();
        emailID = in.readString();
    }

    public static final Creator<SdkDetail> CREATOR = new Creator<SdkDetail>() {
        @Override
        public SdkDetail createFromParcel(Parcel in) {
            return new SdkDetail(in);
        }

        @Override
        public SdkDetail[] newArray(int size) {
            return new SdkDetail[size];
        }
    };

    public List<BcResponse> getBcResponse() {
        return bcResponse;
    }


    public String getApiOutletID() {
        return apiOutletID;
    }

    public void setApiOutletID(String apiOutletID) {
        this.apiOutletID = apiOutletID;
    }

    public String getApiOutletPassword() {
        return apiOutletPassword;
    }

    public void setApiOutletPassword(String apiOutletPassword) {
        this.apiOutletPassword = apiOutletPassword;
    }

    public String getApiPartnerID() {
        return apiPartnerID;
    }

    public void setApiPartnerID(String apiPartnerID) {
        this.apiPartnerID = apiPartnerID;
    }

    public String getApiOutletMob() {
        return apiOutletMob;
    }

    public void setApiOutletMob(String apiOutletMob) {
        this.apiOutletMob = apiOutletMob;
    }

    public String getServiceOutletPIN() {
        return serviceOutletPIN;
    }

    public String getEmailID() {
        return emailID;
    }

    public String getOutletName() {
        return outletName;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(apiOutletID);
        parcel.writeString(apiOutletPassword);
        parcel.writeString(apiPartnerID);
        parcel.writeString(apiOutletMob);
        parcel.writeString(serviceOutletPIN);
        parcel.writeString(outletName);
        parcel.writeString(emailID);
    }
}
