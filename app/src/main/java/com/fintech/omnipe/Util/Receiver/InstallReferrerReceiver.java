package com.fintech.omnipe.Util.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.fintech.omnipe.Util.UtilMethods;


/**
 * Created by Vishnu Agarwal on 27,January,2020
 */
public class InstallReferrerReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) {
            return;
        }
        if (intent.getAction() != null && intent.getAction().equals("com.android.vending.INSTALL_REFERRER")) {
            String referrer = intent.getStringExtra("referrer");
            if (referrer != null && !referrer.isEmpty() & !referrer.contains("utm")
                    && !referrer.contains("chrome") && !referrer.contains("google")) {
                try {
                    int referrerCode = Integer.parseInt(referrer.trim().replaceAll(" ", ""));
                    UtilMethods.INSTANCE.setReferrerId(context, referrerCode + "");
                    UtilMethods.INSTANCE.setReferrerIdSetData(context, true);
                } catch (NumberFormatException nfe) {

                }

            }
           // Toast.makeText(context, referrer + "", Toast.LENGTH_SHORT).show();
            /*adb shell am broadcast -a com.android.vending.INSTALL_REFERRER -n com.solution.fintech.gorecharge365.co.in/.Util.Receiver.InstallReferrerReceiver --es referrer test_referrer=test
             */
        }
    }
}
