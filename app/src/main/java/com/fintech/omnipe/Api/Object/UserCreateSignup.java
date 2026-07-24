package com.fintech.omnipe.Api.Object;

/**
 * Created by Vishnu Agarwal on 20,December,2019
 */
public class UserCreateSignup {

    String address,referalID;
    String mobileNo;
    String name;
    String outletName;
    String emailID;
    int roleID;
    String pincode;

    public UserCreateSignup(String referalID,String address, String mobileNo, String name, String outletName, String emailID, int roleID, String pincode) {
        this.referalID=referalID;
        this.address = address;
        this.mobileNo = mobileNo;
        this.name = name;
        this.outletName = outletName;
        this.emailID = emailID;
        this.roleID = roleID;
        this.pincode = pincode;
    }


}
