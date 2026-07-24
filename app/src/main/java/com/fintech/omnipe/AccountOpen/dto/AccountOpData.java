package com.fintech.omnipe.AccountOpen.dto;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccountOpData implements Parcelable {
    @SerializedName("oid")
    @Expose
    public int oid;
    @SerializedName("redirectURL")
    @Expose
    public String  redirectURL;
    @SerializedName("content")
    @Expose
    public String content;
    @SerializedName("name")
    @Expose
    public String name;



    protected AccountOpData(Parcel in) {
        oid = in.readInt();
        redirectURL = in.readString();
        content = in.readString();
        name = in.readString();

    }

    public static final Creator<AccountOpData> CREATOR = new Creator<AccountOpData>() {
        @Override
        public AccountOpData createFromParcel(Parcel in) {
            return new AccountOpData(in);
        }

        @Override
        public AccountOpData[] newArray(int size) {
            return new AccountOpData[size];
        }
    };



    public int getOid() {
        return oid;
    }

    public String getRedirectURL() {
        return redirectURL;
    }

    public String getContent() {
        return content;
    }

    public String getName() {
        return name;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(oid);
        dest.writeString(redirectURL);
        dest.writeString(content);
        dest.writeString(name);
    }
}
