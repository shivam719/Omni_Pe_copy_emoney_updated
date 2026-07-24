package com.fintech.omnipe.Util;

import android.content.Context;

import com.google.gson.Gson;
import com.fintech.omnipe.Activities.AEPSReportRequest;
import com.fintech.omnipe.Auth.dto.LoginResponse;
import com.fintech.omnipe.BuildConfig;

public class FindBasicLoginRequest {

    private Context context;
    AEPSReportRequest aepsReportRequest;

    public FindBasicLoginRequest(Context context)
    {
        this.context=context;

    }

    public AEPSReportRequest setBasicLoginData() {

        String loginPref=UtilMethods.INSTANCE.getLoginPref(context);
        if(loginPref!=null && !loginPref.isEmpty())
        {
            LoginResponse loginResponse= new Gson().fromJson(loginPref, LoginResponse.class);
            if(loginResponse!=null)
            {
                aepsReportRequest=new AEPSReportRequest();
                aepsReportRequest.setLoginTypeID(loginResponse.getData().getLoginTypeID());
                aepsReportRequest.setUserID(loginResponse.getData().getUserID());
                aepsReportRequest.setUid(Integer.parseInt(loginResponse.getData().getUserID()));
                aepsReportRequest.setSession(loginResponse.getData().getSession());
                aepsReportRequest.setSessionID(loginResponse.getData().getSessionID());
                aepsReportRequest.setSerialNo(UtilMethods.INSTANCE.getSerialNo(context));
                aepsReportRequest.setVersion(BuildConfig.VERSION_NAME);
                aepsReportRequest.setRegKey(UtilMethods.INSTANCE.getFCMRegKey(context));
                aepsReportRequest.setImei(UtilMethods.INSTANCE.getIMEI(context));
                aepsReportRequest.setAppid(ApplicationConstant.INSTANCE.APP_ID);
            }

        }
        return aepsReportRequest;
    }



}
