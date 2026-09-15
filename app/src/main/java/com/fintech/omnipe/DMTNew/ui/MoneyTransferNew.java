package com.fintech.omnipe.DMTNew.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.WindowInsetsControllerCompat;

import com.roundpay.emoneylib.Utils.Utility;
import com.fintech.omnipe.Api.Response.RechargeReportResponse;
import com.fintech.omnipe.DMTNew.dto.SenderResponse;
import com.fintech.omnipe.DMTNew.networkAPI.UtilsMethodDMTNew;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.ApplicationConstant;
import com.fintech.omnipe.Util.UtilMethods;
import com.fintech.omnipe.usefull.CustomLoader;

public class MoneyTransferNew extends AppCompatActivity implements View.OnClickListener {

    EditText transferAmount, pinPassEt;
    TextView submitButton, closeButton, charge, PinPass;
    String name, bank, bankAccount, beneficiaryCode, beneficiaryMobile, senderNumber, ifsc;
    int bankId;

    RadioButton neftRadio, impsRadio;
    CustomLoader loader;


    LinearLayout charge_layout;
    TextView bankname, accountNo, Namebene, total;
    int flag = 1;
    private TextView tvView, otpLabel;
    private EditText otpEt;
    private String sidIntent;
    private int oidIntent;
    private String otpRefId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_money_transfer_new);

        GetId();
    }

    private void GetId() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Money Transfer");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);

        oidIntent = getIntent().getExtras().getInt("OID", 0);
        sidIntent = getIntent().getExtras().getString("SID", "");
        name = getIntent().getExtras().getString("name", "");
        bank = getIntent().getExtras().getString("bank", "");
        bankId = getIntent().getIntExtra("bankId",0);
        bankAccount = getIntent().getExtras().getString("bankAccount", "");
        beneficiaryCode = getIntent().getExtras().getString("beneficiaryCode", "");
        beneficiaryMobile = getIntent().getExtras().getString("beneficiaryMobile", "");
        ifsc = getIntent().getExtras().getString("ifsc", "");
        senderNumber = getIntent().getStringExtra("SenderNumber");
        if (senderNumber == null) {
            SharedPreferences prefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, Context.MODE_PRIVATE);
            senderNumber = prefs.getString(ApplicationConstant.INSTANCE.senderNumberPref, "");
        }

        neftRadio = (RadioButton) findViewById(R.id.neftRadio);
        impsRadio = (RadioButton) findViewById(R.id.impsRadio);
        transferAmount = (EditText) findViewById(R.id.transferAmount);
        otpLabel = findViewById(R.id.otpLabel);
        otpEt = (EditText) findViewById(R.id.otpEt);
        submitButton = (TextView) findViewById(R.id.submitButton);
        bankname = findViewById(R.id.bankname);
        bankname.setText(bank);
        PinPass = findViewById(R.id.PinPass);
        pinPassEt = findViewById(R.id.pinPassEt);
        accountNo = findViewById(R.id.accountNo);
        accountNo.setText(bankAccount);
        Namebene = findViewById(R.id.Namebene);
        Namebene.setText(name);
        total = findViewById(R.id.total);
        charge = (TextView) findViewById(R.id.charge);
        closeButton = (TextView) findViewById(R.id.closeButton);
        charge_layout = (LinearLayout) findViewById(R.id.charge_layout);


        if (flag == 1) {
            submitButton.setText("Get Charges");
        }
        if (UtilMethods.INSTANCE.getPinRequiredPref(this) || UtilMethods.INSTANCE.getDoubleFactorPref(this)) {
            PinPass.setVisibility(View.VISIBLE);
            pinPassEt.setVisibility(View.VISIBLE);
        } else {
            PinPass.setVisibility(View.GONE);
            pinPassEt.setVisibility(View.GONE);
        }
        SetListener();
    }

    private void SetListener() {
        neftRadio.setOnClickListener(this);
        impsRadio.setOnClickListener(this);
        submitButton.setOnClickListener(this);
        closeButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {


        if (v == closeButton) {
            finish();
        }

        if (v == submitButton) {
            if (validationForm()) {
                if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {

                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(false);

                    //  UtilMethods.INSTANCE.GetChargedAmount(this, senderNumber, bankAccount, amount, beneficiaryCode, channel,senderTableInfo.getRemaining(), loader);
                    if (flag == 1) {
                        UtilsMethodDMTNew.INSTANCE.getChargedAmount(this, oidIntent, transferAmount.getText().toString().trim(), loader, null, new UtilsMethodDMTNew.ApiCallBack() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onSuccess(Object object) {
                                flag = 2;
                                RechargeReportResponse rechargeReportResponse = (RechargeReportResponse) object;
                                double amt = Double.parseDouble(transferAmount.getText().toString().trim());
                                double charged = Double.parseDouble(rechargeReportResponse.getChargedAmount());
                                double totalAmount = amt + charged;
                                charge_layout.setVisibility(View.VISIBLE);
                                if (UtilMethods.INSTANCE.getPinRequiredPref(MoneyTransferNew.this) || UtilMethods.INSTANCE.getDoubleFactorPref(MoneyTransferNew.this)) {
                                    pinPassEt.setVisibility(View.VISIBLE);
                                    PinPass.setVisibility(View.VISIBLE);
                                } else {
                                    pinPassEt.setVisibility(View.GONE);
                                    PinPass.setVisibility(View.GONE);
                                }
                                charge.setText(Utility.INSTANCE.formatedAmountWithRupees(rechargeReportResponse.getChargedAmount()));
                                total.setText(Utility.INSTANCE.formatedAmountWithRupees(totalAmount + ""));
                                submitButton.setText("Continue");
                            }
                        });
                    } else if (flag == 2) {
                        UtilsMethodDMTNew.INSTANCE.sendMoneyOTP(this, oidIntent, senderNumber, sidIntent, "", "",
                                pinPassEt.getText().toString(),
                                ifsc, beneficiaryCode,
                                name, bankId,
                                bank, transferAmount.getText().toString().trim(),
                                bankAccount, impsRadio.isChecked() ? "IMPS" : "NEFT",
                                loader, null,
                                new UtilsMethodDMTNew.ApiCallBack() {
                                    @Override
                                    public void onSuccess(Object object) {
                                        SenderResponse senderResponse = (SenderResponse) object;
                                        otpRefId = senderResponse.getSid();
                                        if (otpRefId == null || otpRefId.isEmpty()) {
                                            if (senderResponse.getData() != null && senderResponse.getData().getReferenceID() != null) {
                                                otpRefId = senderResponse.getData().getReferenceID();
                                            }
                                        }
                                        if (senderResponse.isOTPRequired() || senderResponse.getData() != null && senderResponse.getData().isOTPRequired()) {
                                            flag = 3;
                                            charge_layout.setVisibility(View.VISIBLE);
                                            otpLabel.setVisibility(View.VISIBLE);
                                            otpEt.setVisibility(View.VISIBLE);
                                            /* submitButton.setText("Submit");*/
                                        } else {
                                            UtilsMethodDMTNew.INSTANCE.sendMoney(MoneyTransferNew.this, oidIntent, pinPassEt.getText().toString(), beneficiaryCode, senderNumber, sidIntent,
                                                    ifsc, bankAccount, transferAmount.getText().toString().trim(), impsRadio.isChecked() ? "IMPS" : "NEFT", bank, bankId,
                                                    otpEt.getText().toString().trim(), otpRefId, name, loader, null,
                                                    submitButton);
                                        }
                                    }
                                });
                    } else if (flag == 3) {

                        UtilsMethodDMTNew.INSTANCE.sendMoney(this, oidIntent, pinPassEt.getText().toString(), beneficiaryCode, senderNumber, sidIntent,
                                ifsc, bankAccount, transferAmount.getText().toString().trim(), impsRadio.isChecked() ? "IMPS" : "NEFT", bank, bankId,
                                otpEt.getText().toString().trim(), otpRefId, name, loader, null,
                                submitButton);
                    }
                } else {
                    UtilMethods.INSTANCE.NetworkError(this);
                }
            }
        }
    }

    public boolean validationForm() {


        if (transferAmount.getText().toString().isEmpty()) {

            transferAmount.setError("Please enter amount");
            transferAmount.requestFocus();
            return false;
        } else if (charge_layout.getVisibility() == View.VISIBLE && pinPassEt.getVisibility() == View.VISIBLE && pinPassEt.getText().toString().isEmpty()) {
            pinPassEt.setError("Please Enter Pin Password");
            pinPassEt.requestFocus();
            return false;
        } else if (charge_layout.getVisibility() == View.VISIBLE && otpEt.getVisibility() == View.VISIBLE && otpEt.getText().toString().length() < 4) {
            otpEt.setError("Please Enter OTP");
            otpEt.requestFocus();
            return false;
        }

        return true;
    }

   /* @Subscribe
    public void onActivityActivityMessage(ActivityActivityMessage activityActivityMessage) {
        if (activityActivityMessage.getFrom().equalsIgnoreCase("SendMoney")) {
            String[] str = activityActivityMessage.getMessage().split(",");
            charge_layout.setVisibility(View.VISIBLE);
            charge.setText(str[0]);
            total.setText(str[1]);
            flag = 2;
            if (flag == 2) {
                submitButton.setText("Confirm");
            }

        }
    }*/


}
