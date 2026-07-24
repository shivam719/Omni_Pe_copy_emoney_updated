package com.fintech.omnipe.Api.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateBankRequest extends BasicRequest{
    @SerializedName("editUser")
    @Expose
    private EditUser editUser;

    public EditUser getEditUser() {
        return editUser;
    }

    public void setEditUser(EditUser editUser) {
        this.editUser = editUser;
    }

    public UpdateBankRequest(String userID, String loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session, EditUser editUser) {
        this.userID = userID;
        this.loginTypeID = loginTypeID;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.sessionID = sessionID;
        this.session = session;
        this.editUser =editUser;
    }
}


