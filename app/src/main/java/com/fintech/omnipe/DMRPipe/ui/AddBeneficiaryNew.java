package com.fintech.omnipe.DMRPipe.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fintech.omnipe.Activities.BankListScreen;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.ActivityActivityMessage;
import com.fintech.omnipe.Util.ApplicationConstant;
import com.fintech.omnipe.Util.GlobalBus;
import com.fintech.omnipe.Util.UtilMethods;
import com.fintech.omnipe.usefull.CustomLoader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class AddBeneficiaryNew extends AppCompatActivity implements View.OnClickListener {


    EditText beneficiaryName, beneficiaryNumber, accountNumber, ifsc, ifscCode;
    TextView accVerify,bank;
    Button create;
    CustomLoader loader;
    String bankId, isNeft,
            isImps,
            accLmt,
            ekO_BankID;
    String bankName;
    String accVerification;
    String shortCode;
    String verified = "1";
    String currentSenderNumber = "";
    String fullIfscCode = "";
    private int opTypeIntent;
    private String oidIntent, sidIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_add_beneficiary);
        opTypeIntent = getIntent().getIntExtra("OpType", 0);
        oidIntent = String.valueOf(getIntent().getIntExtra("OID",0));
        sidIntent = getIntent().getStringExtra("SID");
        GetId();
    }

    private void GetId() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Add Beneficiary");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        SharedPreferences prefs = this.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, this.MODE_PRIVATE);
        currentSenderNumber = prefs.getString(ApplicationConstant.INSTANCE.senderNumberPref, null);

        beneficiaryName = (EditText) findViewById(R.id.beneficiaryName);
        beneficiaryNumber = (EditText) findViewById(R.id.beneficiaryNumber);

        beneficiaryNumber.setText(currentSenderNumber);
        bank =  findViewById(R.id.bank);
        bank.setCompoundDrawablesWithIntrinsicBounds(null, null, AppCompatResources.getDrawable(this, R.drawable.ic_keyboard_arrow_down_black_24dp), null);

        accountNumber = (EditText) findViewById(R.id.accountNumber);
        ifsc = (EditText) findViewById(R.id.ifsc);
        ifscCode = (EditText) findViewById(R.id.ifscCode);
        accVerify = (TextView) findViewById(R.id.accVerify);
        create = (Button) findViewById(R.id.create);


        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);

        SetListener();
    }

    private void SetListener() {
        accVerify.setOnClickListener(this);
        bank.setOnClickListener(this);
        create.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == bank) {
            Intent bankIntent = new Intent(this, BankListScreen.class);
            bankIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivityForResult(bankIntent, 4);
        }

        if (v == accVerify) {
            if (validationAddBeneficiary("accVerif") == 0) {

                if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {
                    SharedPreferences prefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, Context.MODE_PRIVATE);
                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(false);

                    UtilMethods.INSTANCE.VerifyAccountNew(this, oidIntent, prefs.getString(ApplicationConstant.INSTANCE.senderNumberPref, ""), ifsc.getText().toString().trim(),
                            accountNumber.getText().toString().trim(),beneficiaryName.getText().toString(), bankName,bankId, loader);

                } else {
                    UtilMethods.INSTANCE.NetworkError(this,getString(R.string.err_msg_network_title),
                            getString(R.string.err_msg_network));
                }
            }
        }

        if (v == create) {
            if (validationAddBeneficiary("") == 0) {
                SharedPreferences prefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, Context.MODE_PRIVATE);

                if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {
                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(false);
                    UtilMethods.INSTANCE.AddBeneficiaryNew(this, oidIntent, sidIntent, "", prefs.getString(ApplicationConstant.INSTANCE.senderNumberPref, ""), beneficiaryNumber.getText().toString().trim(), beneficiaryName.getText().toString().trim(), ifsc.getText().toString().trim(), accountNumber.getText().toString().trim(), bankName, bankId, loader, AddBeneficiaryNew.this);
                } else {
                    UtilMethods.INSTANCE.NetworkError(this,getString(R.string.err_msg_network_title),
                            getString(R.string.err_msg_network));
                }
            }
        }

    }

    public int validationAddBeneficiary(String from) {
        int flag = 0;

        if (accountNumber.getText() != null && accountNumber.getText().toString().trim().length() > 0
                && !(accountNumber.getText().toString().trim().length() < 10)) {
        } else {
            accountNumber.setError(getResources().getString(R.string.bene_acc_error));
            accountNumber.requestFocus();
            flag++;
        }

        if (bank.getText() != null && bank.getText().toString().trim().length() > 0) {
        } else {
            bank.setError(getResources().getString(R.string.bene_bank_error));
            bank.requestFocus();
            flag++;
        }


        return flag;
    }

    @Subscribe
    public void onActivityActivityMessage(ActivityActivityMessage activityFragmentMessage) {
        if (activityFragmentMessage.getMessage().equalsIgnoreCase("AccountVerified")) {

            accVerify.setVisibility(View.GONE);
            beneficiaryName.setText("" + activityFragmentMessage.getFrom());
            verified = "1";
        }
        if (activityFragmentMessage.getFrom().equalsIgnoreCase("beneAdded")) {
            beneficiaryName.setText("");
            beneficiaryNumber.setText(currentSenderNumber);
            accountNumber.setText("");
            ifsc.setText("");
            ifscCode.setText("");
            bank.setText("");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            GlobalBus.getBus().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        GlobalBus.getBus().unregister(this);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 4) {
            if (requestCode == 4) {
                bankId = data.getExtras().getString("bankId");
                bankName = data.getExtras().getString("bankName");
                accVerification = data.getExtras().getString("accVerification");
                shortCode = data.getExtras().getString("shortCode");
                fullIfscCode = data.getExtras().getString("ifsc");
                isNeft = data.getExtras().getString("neft");
                isImps = data.getExtras().getString("imps");
                accLmt = data.getExtras().getString("accLmt");
                ekO_BankID = data.getExtras().getString("ekO_BankID");

                bank.setText("" + bankName);
                if (fullIfscCode != null && fullIfscCode.length() > 0) {
                    ifsc.setText(fullIfscCode);
                    ifscCode.setVisibility(View.GONE);
                } else {
                    ifscCode.setText("");
                    ifscCode.setVisibility(View.GONE);
                }

                if (accVerification.equalsIgnoreCase("true"))
                    accVerify.setVisibility(View.VISIBLE);
                else
                    accVerify.setVisibility(View.GONE);
            }
        }

    }

}
