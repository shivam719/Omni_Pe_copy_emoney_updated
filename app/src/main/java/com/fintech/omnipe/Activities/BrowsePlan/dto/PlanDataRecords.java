package com.fintech.omnipe.Activities.BrowsePlan.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Vishnu Agarwal on 21,November,2019
 */
public class PlanDataRecords
{
    @SerializedName(value = "FULLTT", alternate = "fulltt")
    @Expose
    private ArrayList<PlanDataDetails> fulltt = null;
    @SerializedName(value = "3G/4G", alternate = {"3g/4g", "3g 4g"})
    @Expose
    private ArrayList<PlanDataDetails> _3g4gG = null;
    @SerializedName(value = "RATE CUTTER", alternate = {"rate cutter", "ratecutter"})
    @Expose
    private ArrayList<PlanDataDetails> ratecutter = null;
    @SerializedName(value = "2G", alternate = "2g")
    @Expose
    private ArrayList<PlanDataDetails> _2g = null;
    @SerializedName(value = "SMS", alternate = "sms")
    @Expose
    private ArrayList<PlanDataDetails> sms = null;
    @SerializedName(value = "Romaing", alternate = "romaing")
    @Expose
    private ArrayList<PlanDataDetails> romaing = null;
    @SerializedName(value = "COMBO", alternate = "combo")
    @Expose
    private ArrayList<PlanDataDetails> combo = null;

    @SerializedName(value = "frc", alternate = "FRC")
    @Expose
    public ArrayList<PlanDataDetails> frc;
    @SerializedName(value = "isd", alternate = "ISD")
    @Expose
    public ArrayList<PlanDataDetails> isd;

    @SerializedName(value = "talktime", alternate = {"TALK TIME", "talk time"})
    @Expose
    public ArrayList<PlanDataDetails> talktime = null;
    @SerializedName(value = "stv", alternate = "STV")
    @Expose
    public ArrayList<PlanDataDetails> stv = null;

    @SerializedName(value = "data", alternate = "DATA")
    @Expose
    public ArrayList<PlanDataDetails> data = null;

    @SerializedName(value = "international roaming", alternate = "INTERNATIONAL ROAMING")
    @Expose
    public ArrayList<PlanDataDetails> internationalRoaming = null;
    @SerializedName(value = "2G3G DATA", alternate = {"2g3g data", "2g/3g data", "2G/3G DATA"})
    @Expose
    public ArrayList<PlanDataDetails> _2g3gData;
    @SerializedName(value = "validity extension", alternate = "VALIDITY EXTENSION")
    @Expose
    public ArrayList<PlanDataDetails> validityExtension;
    @SerializedName(value = "combo vouchers", alternate = "COMBO VOUCHERS")
    @Expose
    public ArrayList<PlanDataDetails> comboVouchers;
    @SerializedName(value = "data plans", alternate = "DATA PLANS")
    @Expose
    public ArrayList<PlanDataDetails> dataPlans;
    @SerializedName(value = "unlimited plans", alternate = "UNLIMITED PLANS")
    @Expose
    public ArrayList<PlanDataDetails> unlimitedPlans;
    @SerializedName(value = "MBLAZE ULTRA", alternate = "mblaze ultra")
    @Expose
    public ArrayList<PlanDataDetails> mblazeUltra;
    @SerializedName(value = "wifi ultra recharges", alternate = "WIFI ULTRA RECHARGES")
    @Expose
    public ArrayList<PlanDataDetails> wifiUltraRecharges;
    @SerializedName(value = "topup", alternate = "TOPUP")
    @Expose
    public ArrayList<PlanDataDetails> topup;


    @SerializedName("IUC Topup")
    @Expose
    private ArrayList<PlanDataDetails> iUCTopup;
    @SerializedName("NEW ALL-IN-ONE")
    @Expose
    private ArrayList<PlanDataDetails> nEWALLINONE = null;
    @SerializedName("All in One")
    @Expose
    private ArrayList<PlanDataDetails> allInOne;
    @SerializedName("jioPhone")
    @Expose
    private ArrayList<PlanDataDetails> jioPhone = null;
    @SerializedName("jioPrime")
    @Expose
    private ArrayList<PlanDataDetails> jioPrime;
    @SerializedName("Cricket Pack")
    @Expose
    private ArrayList<PlanDataDetails> cricketPack;
    @SerializedName("FRCNon-Prime")
    @Expose
    private ArrayList<PlanDataDetails> fRCNonPrime = null;
    @SerializedName("all")
    @Expose
    private ArrayList<PlanDataDetails> all;
    @SerializedName("unlimited")
    @Expose
    private ArrayList<PlanDataDetails> unlimited;
    @SerializedName("frcsrc")
    @Expose
    private ArrayList<PlanDataDetails> frcsrc;
    @SerializedName("smart recharge")
    @Expose
    private ArrayList<PlanDataDetails> smartRecharge;
    @SerializedName("roaming")
    @Expose
    private ArrayList<PlanDataDetails> roaming;
    @SerializedName("Data Pack")
    @Expose
    private ArrayList<PlanDataDetails> dataPack = null;


    public ArrayList<PlanDataDetails> getIUCTopup() {
        return iUCTopup;
    }

    public void setIUCTopup(ArrayList<PlanDataDetails> iUCTopup) {
        this.iUCTopup = iUCTopup;
    }

    public ArrayList<PlanDataDetails> getNEWALLINONE() {
        return nEWALLINONE;
    }

    public void setNEWALLINONE(ArrayList<PlanDataDetails> nEWALLINONE) {
        this.nEWALLINONE = nEWALLINONE;
    }

    public ArrayList<PlanDataDetails> getAllInOne() {
        return allInOne;
    }

    public void setAllInOne(ArrayList<PlanDataDetails> allInOne) {
        this.allInOne = allInOne;
    }

    public ArrayList<PlanDataDetails> getJioPhone() {
        return jioPhone;
    }

    public void setJioPhone(ArrayList<PlanDataDetails> jioPhone) {
        this.jioPhone = jioPhone;
    }

    public ArrayList<PlanDataDetails> getJioPrime() {
        return jioPrime;
    }

    public void setJioPrime(ArrayList<PlanDataDetails> jioPrime) {
        this.jioPrime = jioPrime;
    }

    public ArrayList<PlanDataDetails> getCricketPack() {
        return cricketPack;
    }

    public void setCricketPack(ArrayList<PlanDataDetails> cricketPack) {
        this.cricketPack = cricketPack;
    }

    public ArrayList<PlanDataDetails> getFRCNonPrime() {
        return fRCNonPrime;
    }

    public void setFRCNonPrime(ArrayList<PlanDataDetails> fRCNonPrime) {
        this.fRCNonPrime = fRCNonPrime;
    }

    public ArrayList<PlanDataDetails> getAll() {
        return all;
    }

    public void setAll(ArrayList<PlanDataDetails> all) {
        this.all = all;
    }

    public ArrayList<PlanDataDetails> getUnlimited() {
        return unlimited;
    }

    public void setUnlimited(ArrayList<PlanDataDetails> unlimited) {
        this.unlimited = unlimited;
    }

    public ArrayList<PlanDataDetails> getFrcsrc() {
        return frcsrc;
    }

    public void setFrcsrc(ArrayList<PlanDataDetails> frcsrc) {
        this.frcsrc = frcsrc;
    }

    public ArrayList<PlanDataDetails> getSmartRecharge() {
        return smartRecharge;
    }

    public void setSmartRecharge(ArrayList<PlanDataDetails> smartRecharge) {
        this.smartRecharge = smartRecharge;
    }


    public ArrayList<PlanDataDetails> getRoaming() {
        return roaming;
    }

    public void setRoaming(ArrayList<PlanDataDetails> roaming) {
        this.roaming = roaming;
    }


    public void setStv(ArrayList<PlanDataDetails> stv) {
        this.stv = stv;
    }


    public void setSms(ArrayList<PlanDataDetails> sms) {
        this.sms = sms;
    }

    public ArrayList<PlanDataDetails> getDataPack() {
        return dataPack;
    }

    public void setDataPack(ArrayList<PlanDataDetails> dataPack) {
        this.dataPack = dataPack;
    }


    public ArrayList<PlanDataDetails> get2g3gData() {
        return _2g3gData;
    }


    public ArrayList<PlanDataDetails> getFulltt() {
        return fulltt;
    }

    public ArrayList<PlanDataDetails> get_3g4gG() {
        return _3g4gG;
    }

    public ArrayList<PlanDataDetails> getRatecutter() {
        return ratecutter;
    }

    public ArrayList<PlanDataDetails> get_2g() {
        return _2g;
    }

    public ArrayList<PlanDataDetails> getSms() {
        return sms;
    }

    public ArrayList<PlanDataDetails> getRomaing() {
        return romaing;
    }

    public ArrayList<PlanDataDetails> getCombo() {
        return combo;
    }

    public ArrayList<PlanDataDetails> getFrc() {
        return frc;
    }

    public ArrayList<PlanDataDetails> getIsd() {
        return isd;
    }

    public ArrayList<PlanDataDetails> getTalktime() {
        return talktime;
    }

    public ArrayList<PlanDataDetails> getStv() {
        return stv;
    }

    public ArrayList<PlanDataDetails> getData() {
        return data;
    }

    public ArrayList<PlanDataDetails> getInternationalRoaming() {
        return internationalRoaming;
    }

    public ArrayList<PlanDataDetails> get_2g3gData() {
        return _2g3gData;
    }

    public ArrayList<PlanDataDetails> getValidityExtension() {
        return validityExtension;
    }

    public ArrayList<PlanDataDetails> getComboVouchers() {
        return comboVouchers;
    }

    public ArrayList<PlanDataDetails> getDataPlans() {
        return dataPlans;
    }

    public ArrayList<PlanDataDetails> getUnlimitedPlans() {
        return unlimitedPlans;
    }

    public ArrayList<PlanDataDetails> getMblazeUltra() {
        return mblazeUltra;
    }

    public ArrayList<PlanDataDetails> getWifiUltraRecharges() {
        return wifiUltraRecharges;
    }

    public ArrayList<PlanDataDetails> getTopup() {
        return topup;
    }
}



   /* @SerializedName(value = "FULLTT",alternate = "fulltt")
    @Expose
    private ArrayList<PlanDataDetails> fulltt = null;
    @SerializedName(value = "3G/4G",alternate ={"3g/4g","3g 4g"})
    @Expose
    private  ArrayList<PlanDataDetails> _3g4gG = null;
    @SerializedName(value = "RATE CUTTER",alternate ={"rate cutter","ratecutter"})
    @Expose
    private  ArrayList<PlanDataDetails> ratecutter = null;
    @SerializedName(value = "2G",alternate = "2g")
    @Expose
    private  ArrayList<PlanDataDetails> _2g = null;
    @SerializedName(value = "SMS",alternate = "sms")
    @Expose
    private  ArrayList<PlanDataDetails> sms = null;
    @SerializedName(value = "Romaing",alternate = "romaing")
    @Expose
    private ArrayList<PlanDataDetails> romaing = null;
    @SerializedName(value = "COMBO",alternate = "combo")
    @Expose
    private  ArrayList<PlanDataDetails> combo = null;

    @SerializedName(value = "frc",alternate ="FRC")
    @Expose
    public ArrayList<PlanDataDetails> frc;
    @SerializedName(value = "isd",alternate ="ISD")
    @Expose
    public ArrayList<PlanDataDetails> isd;

    @SerializedName(value = "talktime",alternate ={"TALK TIME","talk time"})
    @Expose
    public ArrayList<PlanDataDetails> talktime = null;
    @SerializedName(value = "stv",alternate ="STV")
    @Expose
    public ArrayList<PlanDataDetails> stv = null;

    @SerializedName(value = "data",alternate ="DATA")
    @Expose
    public ArrayList<PlanDataDetails> data = null;

    @SerializedName(value = "international roaming",alternate ="INTERNATIONAL ROAMING")
    @Expose
    public ArrayList<PlanDataDetails> internationalRoaming = null;
    @SerializedName(value = "2G3G DATA",alternate ={"2g3g data","2g/3g data","2G/3G DATA"})
    @Expose
    public ArrayList<PlanDataDetails> _2g3gData;
    @SerializedName(value = "validity extension",alternate ="VALIDITY EXTENSION")
    @Expose
    public ArrayList<PlanDataDetails> validityExtension;
    @SerializedName(value = "combo vouchers",alternate ="COMBO VOUCHERS")
    @Expose
    public ArrayList<PlanDataDetails> comboVouchers;
    @SerializedName(value = "data plans",alternate ="DATA PLANS")
    @Expose
    public ArrayList<PlanDataDetails> dataPlans;
    @SerializedName(value = "unlimited plans",alternate ="UNLIMITED PLANS")
    @Expose
    public ArrayList<PlanDataDetails> unlimitedPlans;
    @SerializedName(value = "MBLAZE ULTRA",alternate ="mblaze ultra")
    @Expose
    public ArrayList<PlanDataDetails> mblazeUltra;
    @SerializedName(value = "wifi ultra recharges",alternate ="WIFI ULTRA RECHARGES")
    @Expose
    public ArrayList<PlanDataDetails> wifiUltraRecharges;
    @SerializedName(value = "topup",alternate ="TOPUP")
    @Expose
    public ArrayList<PlanDataDetails> topup;

    public ArrayList<PlanDataDetails> getFulltt() {
        return fulltt;
    }

    public ArrayList<PlanDataDetails> get_3g4gG() {
        return _3g4gG;
    }

    public ArrayList<PlanDataDetails> getRatecutter() {
        return ratecutter;
    }

    public ArrayList<PlanDataDetails> get_2g() {
        return _2g;
    }

    public ArrayList<PlanDataDetails> getSms() {
        return sms;
    }

    public ArrayList<PlanDataDetails> getRomaing() {
        return romaing;
    }

    public ArrayList<PlanDataDetails> getCombo() {
        return combo;
    }

    public ArrayList<PlanDataDetails> getFrc() {
        return frc;
    }

    public ArrayList<PlanDataDetails> getIsd() {
        return isd;
    }

    public ArrayList<PlanDataDetails> getTalktime() {
        return talktime;
    }

    public ArrayList<PlanDataDetails> getStv() {
        return stv;
    }

    public ArrayList<PlanDataDetails> getData() {
        return data;
    }

    public ArrayList<PlanDataDetails> getInternationalRoaming() {
        return internationalRoaming;
    }

    public ArrayList<PlanDataDetails> get_2g3gData() {
        return _2g3gData;
    }

    public ArrayList<PlanDataDetails> getValidityExtension() {
        return validityExtension;
    }

    public ArrayList<PlanDataDetails> getComboVouchers() {
        return comboVouchers;
    }

    public ArrayList<PlanDataDetails> getDataPlans() {
        return dataPlans;
    }

    public ArrayList<PlanDataDetails> getUnlimitedPlans() {
        return unlimitedPlans;
    }

    public ArrayList<PlanDataDetails> getMblazeUltra() {
        return mblazeUltra;
    }

    public ArrayList<PlanDataDetails> getWifiUltraRecharges() {
        return wifiUltraRecharges;
    }

    public ArrayList<PlanDataDetails> getTopup() {
        return topup;
    }
}
*/