package com.fintech.omnipe.Api.Response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.fintech.omnipe.Api.Object.BalanceData;

public class BalanceResponse implements Parcelable {
    @SerializedName("data")
    @Expose
    public BalanceData balanceData;
    @SerializedName("statuscode")
    @Expose
    public Integer statuscode;
    @SerializedName("msg")
    @Expose
    public String msg;
    @SerializedName("isVersionValid")
    @Expose
    public Boolean isVersionValid;
    @SerializedName("isAppValid")
    @Expose
    public Boolean isAppValid;
    @SerializedName("checkID")
    @Expose
    public Integer checkID;
    @SerializedName("isPasswordExpired")
    @Expose
    private boolean isPasswordExpired;
    @SerializedName("isLookUpFromAPI")
    @Expose
    private boolean isLookUpFromAPI;
    @SerializedName("isShowPDFPlan")
    @Expose
    public boolean isShowPDFPlan;

    @SerializedName("isDTHInfoCall")
    @Expose
    public boolean isDTHInfoCall;

    @SerializedName("isDTHInfo")
    @Expose
    public boolean isDTHInfo;

    @SerializedName("isRoffer")
    @Expose
    public boolean isRoffer;

    @SerializedName("popup")
    @Expose
    private String popup;
    @SerializedName("isMoveToPrepaid")
    @Expose
    private boolean isMoveToPrepaid;
    @SerializedName("isMoveToUtility")
    @Expose
    private boolean isMoveToUtility;
    @SerializedName("isMoveToBank")
    @Expose
    private boolean isMoveToBank;
    @SerializedName("isFlatCommission")
    @Expose
    private boolean isFlatCommission;
    @SerializedName("activeFlatType")
    @Expose
    private Integer activeFlatType;
    @SerializedName("isReferral")
    @Expose
    private boolean isReferral;
    @SerializedName("isBulkQRGeneration")
    @Expose
    public boolean isBulkQRGeneration;
    @SerializedName("isSattlemntAccountVerify")
    @Expose
    private boolean isSattlemntAccountVerify;

    @SerializedName("isDrawOpImage")
    @Expose
    public boolean isDrawOpImage;
    @SerializedName(value = "IsAutoVerifyVPA", alternate = "isAutoVerifyVPA")
    @Expose
    private boolean isAutoVerifyVPA;

    protected BalanceResponse(Parcel in) {
        balanceData = in.readParcelable(BalanceData.class.getClassLoader());
        if (in.readByte() == 0) {
            statuscode = null;
        } else {
            statuscode = in.readInt();
        }
        msg = in.readString();
        byte tmpIsVersionValid = in.readByte();
        isVersionValid = tmpIsVersionValid == 0 ? null : tmpIsVersionValid == 1;
        byte tmpIsAppValid = in.readByte();
        isAppValid = tmpIsAppValid == 0 ? null : tmpIsAppValid == 1;
        if (in.readByte() == 0) {
            checkID = null;
        } else {
            checkID = in.readInt();
        }
        isPasswordExpired = in.readByte() != 0;
        isLookUpFromAPI = in.readByte() != 0;
        isShowPDFPlan = in.readByte() != 0;
        isDTHInfoCall = in.readByte() != 0;
        isDTHInfo = in.readByte() != 0;
        isRoffer = in.readByte() != 0;
        popup = in.readString();
        isMoveToPrepaid = in.readByte() != 0;
        isMoveToUtility = in.readByte() != 0;
        isMoveToBank = in.readByte() != 0;
        isFlatCommission = in.readByte() != 0;
        if (in.readByte() == 0) {
            activeFlatType = null;
        } else {
            activeFlatType = in.readInt();
        }
        isReferral = in.readByte() != 0;
        isBulkQRGeneration = in.readByte() != 0;
        isSattlemntAccountVerify = in.readByte() != 0;
        isDrawOpImage = in.readByte() != 0;
        isAutoVerifyVPA = in.readByte() != 0;
    }

    public static final Creator<BalanceResponse> CREATOR = new Creator<BalanceResponse>() {
        @Override
        public BalanceResponse createFromParcel(Parcel in) {
            return new BalanceResponse(in);
        }

        @Override
        public BalanceResponse[] newArray(int size) {
            return new BalanceResponse[size];
        }
    };

    public boolean isDrawOpImage() {
        return isDrawOpImage;
    }
    public boolean isSattlemntAccountVerify() {
        return isSattlemntAccountVerify;
    }

    public boolean isBulkQRGeneration() {
        return isBulkQRGeneration;
    }

    public boolean isMoveToPrepaid() {
        return isMoveToPrepaid;
    }

    public boolean isMoveToUtility() {
        return isMoveToUtility;
    }

    public boolean isMoveToBank() {
        return isMoveToBank;
    }

    public boolean isFlatCommission() {
        return isFlatCommission;
    }

    public Integer getActiveFlatType() {
        return activeFlatType;
    }

    public boolean isReferral() {
        return isReferral;
    }

    public boolean isShowPDFPlan() {
        return isShowPDFPlan;
    }

    public boolean isDTHInfoCall() {
        return isDTHInfoCall;
    }

    public boolean isDTHInfo() {
        return isDTHInfo;
    }

    public boolean isRoffer() {
        return isRoffer;
    }

    public String getPopup() {
        return popup;
    }

    public BalanceData getBalanceData() {
        return balanceData;
    }

    public Integer getStatuscode() {
        return statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public Boolean getVersionValid() {
        return isVersionValid;
    }

    public Boolean getAppValid() {
        return isAppValid;
    }

    public Integer getCheckID() {
        return checkID;
    }

    public boolean getIsPasswordExpired() {
        return isPasswordExpired;
    }

    public boolean isPasswordExpired() {
        return isPasswordExpired;
    }

    public boolean isLookUpFromAPI() {
        return isLookUpFromAPI;
    }

    public boolean isAutoVerifyVPA() {
        return isAutoVerifyVPA;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(balanceData, i);
        if (statuscode == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(statuscode);
        }
        parcel.writeString(msg);
        parcel.writeByte((byte) (isVersionValid == null ? 0 : isVersionValid ? 1 : 2));
        parcel.writeByte((byte) (isAppValid == null ? 0 : isAppValid ? 1 : 2));
        if (checkID == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(checkID);
        }
        parcel.writeByte((byte) (isPasswordExpired ? 1 : 0));
        parcel.writeByte((byte) (isLookUpFromAPI ? 1 : 0));
        parcel.writeByte((byte) (isShowPDFPlan ? 1 : 0));
        parcel.writeByte((byte) (isDTHInfoCall ? 1 : 0));
        parcel.writeByte((byte) (isDTHInfo ? 1 : 0));
        parcel.writeByte((byte) (isRoffer ? 1 : 0));
        parcel.writeString(popup);
        parcel.writeByte((byte) (isMoveToPrepaid ? 1 : 0));
        parcel.writeByte((byte) (isMoveToUtility ? 1 : 0));
        parcel.writeByte((byte) (isMoveToBank ? 1 : 0));
        parcel.writeByte((byte) (isFlatCommission ? 1 : 0));
        if (activeFlatType == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(activeFlatType);
        }
        parcel.writeByte((byte) (isReferral ? 1 : 0));
        parcel.writeByte((byte) (isBulkQRGeneration ? 1 : 0));
        parcel.writeByte((byte) (isSattlemntAccountVerify ? 1 : 0));
        parcel.writeByte((byte) (isDrawOpImage ? 1 : 0));
        parcel.writeByte((byte) (isAutoVerifyVPA ? 1 : 0));
    }
}
