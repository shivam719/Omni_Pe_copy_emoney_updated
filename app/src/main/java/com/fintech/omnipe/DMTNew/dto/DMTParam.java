package com.fintech.omnipe.DMTNew.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DMTParam {

    @SerializedName("SenderMobile")
    @Expose
    public String SenderMobile;
    @SerializedName("AadhaarNumber")
    @Expose
    public String AadhaarNumber;
    @SerializedName("PIDData")
    @Expose
    public String PIDData;
    @SerializedName("RefferenceID")
    @Expose
    public String RefferenceID;
    @SerializedName("ReferenceID")
    @Expose
    public String ReferenceID;
    @SerializedName("FirstName")
    @Expose
    public String FirstName;
    @SerializedName("LastName")
    @Expose
    public String LastName;
    @SerializedName("Name")
    @Expose
    public String Name;
    @SerializedName("AccountNo")
    @Expose
    public String AccountNo;
    @SerializedName("Ifsc")
    @Expose
    public String Ifsc;
    @SerializedName("Dob")
    @Expose
    public String Dob;
    @SerializedName("Address")
    @Expose
    public String Address;
    @SerializedName("Pincode")
    @Expose
    public String Pincode;
    @SerializedName("OTP")
    @Expose
    public String OTP;
    @SerializedName("BeneId")
    @Expose
    public String BeneId;
    @SerializedName("BankName")
    @Expose
    public String BankName;
    @SerializedName("SecurityKey")
    @Expose
    public String SecurityKey;
    @SerializedName("Amount")
    @Expose
    public String Amount;
    @SerializedName("BeneName")
    @Expose
    public String BeneName;
    @SerializedName("TransMode")
    @Expose
    public String TransMode;
    @SerializedName("BankId")
    @Expose
    public int BankId;
    @SerializedName("OID")
    @Expose
    public int OID;
    @SerializedName("latitude")
    @Expose
    public double latitude;
    @SerializedName("Lattitude")
    @Expose
    public double Lattitude;
    @SerializedName("Longitude")
    @Expose
    public double Longitude;





    public DMTParam(String SenderMobile, int OID, double latitude, double Lattitude, double Longitude) {
        this.SenderMobile = SenderMobile;
        this.OID = OID;
        this.latitude = latitude;
        this.Lattitude = Lattitude;
        this.Longitude = Longitude;
    }

    public DMTParam(String SenderMobile, int OID, double latitude, double Lattitude, double Longitude, String BeneId) {
        this.SenderMobile = SenderMobile;
        this.OID = OID;
        this.latitude = latitude;
        this.Lattitude = Lattitude;
        this.Longitude = Longitude;
        this.BeneId = BeneId;
    }

    public DMTParam(String SenderMobile,String AadhaarNumber,String PIDData, int OID, double latitude, double Lattitude, double Longitude, String OTP, String RefferenceID, String ReferenceID) {
        this.SenderMobile = SenderMobile;
        this.AadhaarNumber = AadhaarNumber;
        this.PIDData = PIDData;
        this.OID = OID;
        this.latitude = latitude;
        this.Lattitude = Lattitude;
        this.Longitude = Longitude;
        this.OTP = OTP;
        this.RefferenceID = RefferenceID;
        this.ReferenceID = ReferenceID;
    }

    public DMTParam(String SenderMobile, int OID, double latitude, double Lattitude, double Longitude,String FirstName, String LastName, String OTP, String RefferenceID, String ReferenceID) {
        this.SenderMobile = SenderMobile;
        this.OID = OID;
        this.latitude = latitude;
        this.Lattitude = Lattitude;
        this.Longitude = Longitude;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.OTP = OTP;
        this.RefferenceID = RefferenceID;
        this.ReferenceID = ReferenceID;
    }


    public DMTParam(String SenderMobile, int OID, double latitude, double Lattitude, double Longitude,String Name, String AccountNo, String Ifsc, String Dob, String Address, String Pincode, int BankId) {
        this.SenderMobile = SenderMobile;
        this.OID = OID;
        this.latitude = latitude;
        this.Lattitude = Lattitude;
        this.Longitude = Longitude;
        this.Name = Name;
        this.AccountNo = AccountNo;
        this.Ifsc = Ifsc;
        this.Dob = Dob;
        this.Address = Address;
        this.Pincode = Pincode;
        this.BankId = BankId;
    }

    public DMTParam(String SenderMobile, int OID, double latitude,double Lattitude, double Longitude, String BeneName, String AccountNo, String Ifsc, int BankId, String  BankName) {
        this.SenderMobile = SenderMobile;
        this.OID = OID;
        this.latitude = latitude;
        this.Lattitude = Lattitude;
        this.Longitude = Longitude;
        this.BeneName = BeneName;
        this.AccountNo = AccountNo;
        this.Ifsc = Ifsc;
        this.BankId = BankId;
        this.BankName = BankName;
    }

    public DMTParam(String SenderMobile, int OID, double latitude,double Lattitude, double Longitude,String BeneId,String BeneName, String AccountNo, String Ifsc, int BankId,
                    String  BankName, String  SecurityKey, String  Amount, String  OTP, String  RefferenceID,String  ReferenceID, String  TransMode) {
        this.SenderMobile = SenderMobile;
        this.OID = OID;
        this.latitude = latitude;
        this.Lattitude = Lattitude;
        this.Longitude = Longitude;
        this.BeneId = BeneId;
        this.BeneName = BeneName;
        this.AccountNo = AccountNo;
        this.Ifsc = Ifsc;
        this.BankId = BankId;
        this.BankName = BankName;
        this.SecurityKey = SecurityKey;
        this.Amount = Amount;
        this.OTP = OTP;
        this.RefferenceID = RefferenceID;
        this.ReferenceID = ReferenceID;
        this.TransMode = TransMode;
    }

     /*"SenderMobile": "1234567890",
    "OID": 10,
    "BeneId": "BENE12345",
    "BeneName": "Jane Doe",
    "BankId": 1001,
    "BankName": "XYZ Bank",
    "AccountNo": "123456789012",
    "Ifsc": "ABCD0123456",
    "SecurityKey": "security_key_value",
    "Amount": 5000.75,
    "Lattitude": 28.6139,
    "Longitude": 77.2090,
    "OTP": "123456",
    "RefferenceID": "ref12345",
    "TransMode": "IMPS"*/
}
