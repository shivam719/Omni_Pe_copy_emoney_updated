package com.fintech.omnipe.Fragments.dto;

/**
 * Created by Lalit on 18-01-2017.
 */

public class Operator {

    private int OPID;
    private String OPNAME;
    private int OPTYPE;
    private String OPIMG;
    private String icon;
    private String PLANCODE;
    private String Displayalue1;
    private String Displayalue2;
    private String Displayalue3;
    private String Displayalue4;


    public Operator(int OPID, String OPNAME, int OPTYPE, String OPIMG, String PLANCODE, String Displayalue1, String Displayalue2,
                    String Displayalue3, String Displayalue4, String icon) {

        this.OPID = OPID;
        this.OPNAME = OPNAME;
        this.OPTYPE = OPTYPE;
        this.OPIMG = OPIMG;
        this.PLANCODE = PLANCODE;

        this.Displayalue1 = Displayalue1;
        this.Displayalue2 = Displayalue2;
        this.Displayalue3 = Displayalue3;
        this.Displayalue4 = Displayalue4;
        this.icon = icon;

    }

    public String getPLANCODE() {
        return PLANCODE;
    }

    public void setPLANCODE(String PLANCODE) {
        this.PLANCODE = PLANCODE;
    }

    public String getDisplayalue1() {
        return Displayalue1;
    }

    public void setDisplayalue1(String displayalue1) {
        Displayalue1 = displayalue1;
    }

    public String getDisplayalue2() {
        return Displayalue2;
    }

    public void setDisplayalue2(String displayalue2) {
        Displayalue2 = displayalue2;
    }

    public String getDisplayalue3() {
        return Displayalue3;
    }

    public void setDisplayalue3(String displayalue3) {
        Displayalue3 = displayalue3;
    }

    public String getDisplayalue4() {
        return Displayalue4;
    }

    public void setDisplayalue4(String displayalue4) {
        Displayalue4 = displayalue4;
    }

    public int getOPID() {
        return OPID;
    }

    public void setOPID(int OPID) {
        this.OPID = OPID;
    }

    public String getOPNAME() {
        return OPNAME;
    }

    public void setOPNAME(String OPNAME) {
        this.OPNAME = OPNAME;
    }

    public int getOPTYPE() {
        return OPTYPE;
    }

    public void setOPTYPE(int OPTYPE) {
        this.OPTYPE = OPTYPE;
    }

    public String getOPIMG() {
        return OPIMG;
    }

    public void setOPIMG(String OPIMG) {
        this.OPIMG = OPIMG;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
