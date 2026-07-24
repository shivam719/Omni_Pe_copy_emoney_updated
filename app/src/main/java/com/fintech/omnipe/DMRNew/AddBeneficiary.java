package com.fintech.omnipe.DMRNew;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.fintech.omnipe.DMRNew.response.SenderResponse;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.ApplicationConstant;
import com.fintech.omnipe.Util.GetLocation;
import com.fintech.omnipe.Util.UtilMethods;
import com.fintech.omnipe.usefull.CustomLoader;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddBeneficiary extends AppCompatActivity implements View.OnClickListener {
    EditText beneficiaryName, beneficiaryNumber, bank, accountNumber, ifsc, ifscCode, dob, address, pincode;
    TextView accVerify;
    Button create;
    CustomLoader loader;
    String isNeft, isImps, accLmt, ekO_BankID;
    String bankId;
    String bankName;
    String accVerification;
    String shortCode;
    String fullIfscCode = "";
    private int opTypeIntent, oidIntent;
    private String sidIntent;
    private GetLocation mGetLocation;
    private String senderNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_add_beneficiery);
        opTypeIntent = getIntent().getIntExtra("OpType", 0);
        oidIntent = getIntent().getIntExtra("OID", 0);
        sidIntent = getIntent().getStringExtra("SID");
        senderNumber = getIntent().getStringExtra("SenderNumber");
        if (senderNumber == null) {
            SharedPreferences prefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, Context.MODE_PRIVATE);
            senderNumber = prefs.getString(ApplicationConstant.INSTANCE.senderNumberPref, "");
        }
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        mGetLocation = new GetLocation(this, loader);
        GetId();
    }

    private void GetId() {

        beneficiaryName = (EditText) findViewById(R.id.beneficiaryName);
        beneficiaryNumber = (EditText) findViewById(R.id.beneficiaryNumber);

        beneficiaryNumber.setText(senderNumber);
        bank = (EditText) findViewById(R.id.bank);
        bank.setCompoundDrawablesWithIntrinsicBounds(null, null, AppCompatResources.getDrawable(this, R.drawable.ic_keyboard_arrow_down_black_24dp), null);
        dob = (EditText) findViewById(R.id.dob);
        dob.setCompoundDrawablesWithIntrinsicBounds(null, null, AppCompatResources.getDrawable(this, R.drawable.ic_calendar_icon), null);
        address = (EditText) findViewById(R.id.address);
        pincode = (EditText) findViewById(R.id.pincode);
        accountNumber = (EditText) findViewById(R.id.accountNumber);
        ifsc = (EditText) findViewById(R.id.ifsc);
        ifscCode = (EditText) findViewById(R.id.ifscCode);
        accVerify = (TextView) findViewById(R.id.accVerify);
        create = (Button) findViewById(R.id.create);
        SetListener();
    }

    private void SetListener() {
        accVerify.setOnClickListener(this);
        bank.setOnClickListener(this);
        dob.setOnClickListener(this);
        create.setOnClickListener(this);
    }

    @Override
    protected void onPause() {
        if (mGetLocation != null) {
            mGetLocation.onPause();
        }
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        if (v == bank) {
            Intent bankIntent = new Intent(this, NewBankListScreen.class);
            bankIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivityForResult(bankIntent, 4);
        }
        if (v == dob) {
            final Calendar myCalendar = Calendar.getInstance();
            final DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd MMM yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                dob.setText(sdf.format(myCalendar.getTime()));
            };
            DatePickerDialog mDatePicker = new DatePickerDialog(this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
            mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis() - (568036800000L + 86400000L));

            mDatePicker.show();

        } else if (v == accVerify) {
            if (validationAddBeneficiary("accVerif") == 0) {

                if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {

                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(false);

                    UtilMethods.INSTANCE.verifyAccount(this, oidIntent, senderNumber, sidIntent, ifsc.getText().toString().trim(),
                            accountNumber.getText().toString().trim(), beneficiaryName.getText().toString(), bankName, Integer.parseInt(bankId), loader, mGetLocation, new UtilMethods.ApiCallBack() {
                                @Override
                                public void onSucess(Object object) {
                                    SenderResponse senderResponse = (SenderResponse) object;
                                    accVerify.setVisibility(View.GONE);
                                    if (senderResponse.getData() != null) {
                                        beneficiaryName.setText(senderResponse.getData().getBeneName());
                                    }
                                }
                            });

                } else {
                    UtilMethods.INSTANCE.NetworkError(this);
                }
            }
        } else if (v == create) {
            if (validationAddBeneficiary("") == 0) {

                if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {
                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(false);
                    UtilMethods.INSTANCE.addBeneficiary(this, oidIntent, sidIntent, senderNumber, dob.getText().toString().trim(),
                            address.getText().toString().trim(), pincode.getText().toString().trim(),
                            beneficiaryName.getText().toString().trim(), ifsc.getText().toString().trim(), accountNumber.getText().toString().trim(),
                            Integer.parseInt(bankId), loader, mGetLocation, new UtilMethods.ApiCallBack() {
                                @Override
                                public void onSucess(Object object) {
                                    beneficiaryName.setText("");
                                    beneficiaryNumber.setText(senderNumber);
                                    accountNumber.setText("");
                                    ifsc.setText("");
                                    ifscCode.setText("");
                                    bank.setText("");

                                }
                            });
                } else {
                    UtilMethods.INSTANCE.NetworkError(this);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (mGetLocation != null) {
            mGetLocation.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 4) {
            if (requestCode == 4) {
                bankId = data.getStringExtra("bankId");
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
                    // ifscCode.setText("" + shortCode);
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
        } else {
            if (mGetLocation != null) {
                mGetLocation.onActivityResult(requestCode, resultCode, data);
            }
        }

    }
}