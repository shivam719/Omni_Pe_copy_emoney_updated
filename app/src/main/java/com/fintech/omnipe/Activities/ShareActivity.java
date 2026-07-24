package com.fintech.omnipe.Activities;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.fintech.omnipe.Auth.dto.LoginResponse;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.ApplicationConstant;
import com.google.gson.Gson;

public class ShareActivity extends AppCompatActivity {

    private LinearLayout msgView, whatsappView, facebookView, twitterView, emailView, shareView;

    private AppCompatImageView closeIv;

    private LoginResponse loginPrefResponse;
    private SharedPreferences myPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());

        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);

        setContentView(R.layout.activity_share);

        initPrefs();
        findViews();
        clickView();
    }

    /* ---------------- INIT PREF ---------------- */

    private void initPrefs() {

        myPrefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNameLoginPref, MODE_PRIVATE);

        String getLoginPref = myPrefs.getString(ApplicationConstant.INSTANCE.LoginPref, "");

        loginPrefResponse = new Gson().fromJson(getLoginPref, LoginResponse.class);
    }

    /* ---------------- FIND VIEWS ---------------- */

    private void findViews() {

        msgView = findViewById(R.id.msgView);
        whatsappView = findViewById(R.id.whatsappView);
        facebookView = findViewById(R.id.facebookView);
        twitterView = findViewById(R.id.twitterView);
        emailView = findViewById(R.id.emailView);
        shareView = findViewById(R.id.shareView);
        closeIv = findViewById(R.id.closeIv);
    }

    /* ---------------- CLICK EVENTS ---------------- */

    private void clickView() {

        msgView.setOnClickListener(v -> shareIt("sms"));

        whatsappView.setOnClickListener(v -> shareIt("com.whatsapp"));

        facebookView.setOnClickListener(v -> shareIt("com.facebook.katana"));

        twitterView.setOnClickListener(v -> shareIt("com.twitter.android"));

        emailView.setOnClickListener(v -> shareIt("email"));

        shareView.setOnClickListener(v -> shareIt(null));

        closeIv.setOnClickListener(v -> finish());
    }

    /* ---------------- SHARE MESSAGE ---------------- */

    private String getShareMessage() {

        String appName = getString(R.string.app_name);

        String userId = "";

        if (loginPrefResponse != null && loginPrefResponse.getData() != null) {

            userId = loginPrefResponse.getData().getUserID();
        }

        return "🚀 Recharge Smarter with " + appName + " – India's No.1 Trusted Recharge Portal!\n\n" +

                "Join me and get seamless mobile, DTH, and utility recharges at your fingertips.\n\n" +

                "Use my referral link to get ₹5 free balance instantly:\n" +

                ApplicationConstant.INSTANCE.inviteUrl + userId +

                "\n\nStart recharging now and enjoy fast, secure, and hassle-free transactions!";
    }

    /* ---------------- SHARE FUNCTION ---------------- */

    private void shareIt(String packageName) {

        try {

            String shareMessage = getShareMessage();

            if ("sms".equalsIgnoreCase(packageName)) {

                Uri uri = Uri.parse("smsto:");

                Intent intent = new Intent(Intent.ACTION_SENDTO, uri);

                intent.putExtra("sms_body", shareMessage);

                startActivity(Intent.createChooser(intent, "Choose App"));

            } else if ("email".equalsIgnoreCase(packageName)) {

                Uri uri = Uri.parse("mailto:");

                Intent intent = new Intent(Intent.ACTION_SENDTO, uri);

                intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));

                intent.putExtra(Intent.EXTRA_TEXT, shareMessage);

                startActivity(Intent.createChooser(intent, "Choose App"));

            } else {

                Intent shareIntent = new Intent(Intent.ACTION_SEND);

                shareIntent.setType("text/plain");

                shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));

                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);

                if (packageName != null) {
                    shareIntent.setPackage(packageName);
                }

                startActivity(Intent.createChooser(shareIntent, "Choose App"));
            }

        } catch (ActivityNotFoundException e) {

            if ("sms".equalsIgnoreCase(packageName)) {

                sendSms();

            } else if ("email".equalsIgnoreCase(packageName)) {

                sendEmail();

            } else {

                Toast.makeText(this, "Sorry, App not found", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    /* ---------------- EMAIL FALLBACK ---------------- */

    void sendEmail() {

        try {

            Intent intent = new Intent(Intent.ACTION_SEND);

            intent.setType("message/rfc822");

            intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));

            intent.putExtra(Intent.EXTRA_TEXT, getShareMessage());

            startActivity(Intent.createChooser(intent, "Choose App"));

        } catch (Exception ignored) {
        }
    }

    /* ---------------- SMS FALLBACK ---------------- */

    @SuppressLint("ObsoleteSdkInt")
    void sendSms() {

        try {

            String shareMessage = getShareMessage();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

                String defaultSmsPackageName = Telephony.Sms.getDefaultSmsPackage(this);

                Intent sendIntent = new Intent(Intent.ACTION_SEND);

                sendIntent.setType("text/plain");

                sendIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);

                if (defaultSmsPackageName != null) {

                    sendIntent.setPackage(defaultSmsPackageName);
                }

                startActivity(Intent.createChooser(sendIntent, "Choose App"));

            } else {

                Intent sendIntent = new Intent(Intent.ACTION_VIEW);

                sendIntent.setData(Uri.parse("sms:"));

                sendIntent.putExtra("sms_body", shareMessage);

                startActivity(Intent.createChooser(sendIntent, "Choose App"));
            }

        } catch (Exception ignored) {
        }
    }
}