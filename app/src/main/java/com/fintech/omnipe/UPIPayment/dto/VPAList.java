package com.fintech.omnipe.UPIPayment.dto;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VPAList implements Parcelable {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("vpa")
    @Expose
    private String vpa;
    @SerializedName("accountHolder")
    @Expose
    private String accountHolder;
    @SerializedName("senderNo")
    @Expose
    private String senderNo;
    @SerializedName("isVerified")
    @Expose
    private boolean isVerified;

    protected VPAList(Parcel in) {
        id = in.readInt();
        vpa = in.readString();
        accountHolder = in.readString();
        senderNo = in.readString();
        isVerified = in.readByte() != 0;
    }

    public static final Creator<VPAList> CREATOR = new Creator<VPAList>() {
        @Override
        public VPAList createFromParcel(Parcel in) {
            return new VPAList(in);
        }

        @Override
        public VPAList[] newArray(int size) {
            return new VPAList[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getVpa() {
        return vpa;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public String getSenderNo() {
        return senderNo;
    }

    public boolean isVerified() {
        return isVerified;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(vpa);
        parcel.writeString(accountHolder);
        parcel.writeString(senderNo);
        parcel.writeByte((byte) (isVerified ? 1 : 0));
    }
}
