package com.fintech.omnipe.UPIPayment.QrScannerViewUtils;

public enum VPAStrSuffixValidation {

    INSTANCE;

    public boolean isValidVpAStr(String vpa){

        if(vpa.contains("@ybl"))
            return true;
        else if(vpa.contains("@upi"))
            return true;
        else if(vpa.contains("@okhdfcbank"))
            return true;
        else if(vpa.contains("@BARODAMPAY"))
            return true;
        else if(vpa.contains("@okaxis"))
            return true;
        else if(vpa.contains("@aubank"))
            return true;
        else if(vpa.contains("@oksbi"))
            return true;
        else if(vpa.contains("@okicici"))
            return true;
        else if(vpa.contains("@hsbc"))
            return true;
        else if(vpa.contains("@idbi"))
            return true;
        else if(vpa.contains("@icici"))
            return true;
        else if(vpa.contains("@indianbank"))
            return true;
        else if(vpa.contains("@allbank"))
            return true;
        else if(vpa.contains("@kotak"))
            return true;
        else if(vpa.contains("@ikwik"))
            return true;
        else if(vpa.contains("@dlb"))
            return true;
        else if(vpa.contains("@dbs"))
            return true;
        else if(vpa.contains("@freecharge"))
            return true;
        else if(vpa.contains("@unionbankofindia"))
            return true;
        else if(vpa.contains("@uboi"))
            return true;
        else if(vpa.contains("@unionbank"))
            return true;
        else if(vpa.contains("@paytm"))
            return true;
        else if(vpa.contains("@axl"))
            return true;
        else if(vpa.contains("@ibl"))
            return true;
        else if(vpa.contains("@sib"))
            return true;
        else if(vpa.contains("@aubank"))
            return true;
        else if(vpa.contains("@axisbank"))
            return true;
        else if(vpa.contains("@bandhan"))
            return true;
        else if(vpa.contains("@indus"))
            return true;
        else if(vpa.contains("@kbl"))
            return true;
        else if(vpa.contains("@federal"))
            return true;
        else if(vpa.contains("@sbi"))
            return true;
        else if(vpa.contains("@uco"))
            return true;
        else if(vpa.contains("@yesbank"))
            return true;
        else if(vpa.contains("@citi"))
            return true;
        else if(vpa.contains("@citigold"))
            return true;
        return false;
    }


}
