package com.fintech.omnipe.Api.Response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.fintech.omnipe.Api.Object.UserList;

import java.util.ArrayList;

public class UserListRoleWiseData implements Parcelable {

    @SerializedName("roleID")
    @Expose
    public int roleID;
    @SerializedName("roleName")
    @Expose
    public String roleName;
    @SerializedName("userList")
    @Expose
    public ArrayList<UserList> userList = null;


    protected UserListRoleWiseData(Parcel in) {
        roleID = in.readInt();
        roleName = in.readString();
        userList = in.createTypedArrayList(UserList.CREATOR);
    }

    public static final Parcelable.Creator<UserListRoleWiseData> CREATOR = new Parcelable.Creator<UserListRoleWiseData>() {
        @Override
        public UserListRoleWiseData createFromParcel(Parcel in) {
            return new UserListRoleWiseData(in);
        }

        @Override
        public UserListRoleWiseData[] newArray(int size) {
            return new UserListRoleWiseData[size];
        }
    };

    public int getRoleID() {
        return roleID;
    }

    public String getRoleName() {
        return roleName;
    }

    public ArrayList<UserList> getUserList() {
        return userList;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(roleID);
        parcel.writeString(roleName);
        parcel.writeTypedList(userList);
    }
}
