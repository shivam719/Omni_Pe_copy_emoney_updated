package com.fintech.omnipe.DMTNew.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.WindowInsetsControllerCompat;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.paysprint.onboardinglib.activities.HostActivity;
import com.fintech.omnipe.Activities.DMRReport;
import com.fintech.omnipe.DMTNew.dto.DMTData;
import com.fintech.omnipe.DMTNew.dto.SenderResponse;
import com.fintech.omnipe.DMTNew.fragment.DMTFingerPrintEKycDialogFragment;
import com.fintech.omnipe.DMTNew.fragment.interfaces.BottomSheetCallBack;
import com.fintech.omnipe.DMTNew.networkAPI.UtilsMethodDMTNew;
import com.fintech.omnipe.Fragments.SdkDetail;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.ApplicationConstant;
import com.fintech.omnipe.Util.CustomAlertDialog;
import com.fintech.omnipe.Util.GetLocation;
import com.fintech.omnipe.Util.UtilMethods;
import com.fintech.omnipe.usefull.CustomLoader;

public class DMTLoginNew extends AppCompatActivity implements View.OnClickListener {


    EditText senderNoEt, firstNameEt, lastNameEt, otpEt;
    TextInputLayout firstNameTil, lastNameTil, otpTil;
    Button submit;
    LinearLayout dmr_create, sender_layout, dmr_layout;
    TextView sender_num, sender_name, remaining;
    CustomLoader loader = null;
    ImageView dmr_logout;
    View add_bene, bene_list;
    LinearLayout dmr_report, nameView;


    int opTypeIntent;
    int oidIntent, CASA;
    private String sidValue, wadhData;
    GetLocation mGetLocation;
    private SenderResponse senderResponse;
    private Dialog dialogAADHARValidate;
    private String senderNumberStr;
    private SdkDetail intentSdkDetail;
    private String merchantId, password, mobile, superMerchentId;
    private String apiKey;
    private final ActivityResultLauncher<Intent> hostActivityLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == Activity.RESULT_OK) {
            Intent data = result.getData();
            if (data != null) {
                boolean status = data.getBooleanExtra("status", false);
                int response = data.getIntExtra("response", 0);
                String message = data.getStringExtra("message");
                String detailedResponse = "Status: " + status + ", Response: " + response + ",         Message: " + message;
                //showSnack(detailedResponse);
                UtilMethods.INSTANCE.Successful(DMTLoginNew.this, detailedResponse);
            }
        } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
            UtilMethods.INSTANCE.Error(DMTLoginNew.this, "User cancelled");
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_dmt_new);
        opTypeIntent = getIntent().getIntExtra("OpType", 0);
        oidIntent = getIntent().getIntExtra("OID", 0);
        CASA = getIntent().getIntExtra("CASA", 0);
        intentSdkDetail = (SdkDetail) getIntent().getSerializableExtra("SDKDetails");
        if (intentSdkDetail != null) {
            merchantId = intentSdkDetail.getApiOutletID();
            password = intentSdkDetail.getApiOutletPassword();
            mobile = intentSdkDetail.getApiOutletMob();
            superMerchentId = intentSdkDetail.getApiPartnerID();
            apiKey = intentSdkDetail.getApiOutletPassword();
        }
        loader = new CustomLoader(DMTLoginNew.this, android.R.style.Theme_Translucent_NoTitleBar);
        mGetLocation = new GetLocation(this, loader);
        GetId();
    }


    private void GetId() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Money Transfer");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(v -> finish());
        senderNoEt = findViewById(R.id.sender_number);
        firstNameTil = findViewById(R.id.firstNameTil);
        firstNameEt = findViewById(R.id.first_name);
        lastNameTil = findViewById(R.id.lastNameTil);
        lastNameEt = findViewById(R.id.last_name);
        otpTil = findViewById(R.id.otpTil);
        otpEt = findViewById(R.id.otp);
        nameView = findViewById(R.id.nameview);
        submit = findViewById(R.id.submit);
        dmr_create = findViewById(R.id.dmr_create);
        dmr_layout = findViewById(R.id.dmr_layout);
        sender_layout = findViewById(R.id.sender_layout);
        bene_list = findViewById(R.id.bene_list);
        add_bene = findViewById(R.id.add_bene);
        dmr_report = findViewById(R.id.dmr_report);
        dmr_logout = findViewById(R.id.dmr_logout);
        sender_num = findViewById(R.id.sender_num);
        sender_name = findViewById(R.id.sender_name);
        //kycText =  findViewById(R.id.kycText);
        remaining = findViewById(R.id.remaining);

        submit.setText("Login");
        submit.setVisibility(View.GONE);
        senderNoEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 10) {
                    getSenderWithLocation();
                } else {
                    if (dmr_create.getVisibility() == View.VISIBLE) {
                        dmr_layout.setVisibility(View.VISIBLE);
                        dmr_create.setVisibility(View.GONE);
                        submit.setVisibility(View.GONE);
                    }
                }
            }
        });
        SetListener();
        DMRStatus();
    }


    private void getSenderWithLocation() {
        if (UtilMethods.getLattitude != 0 && UtilMethods.getLongitude != 0) {
            if (CASA != 130) {
                getSender();
            } else {
                getSenderCASA();
            }
        } else {
            if (mGetLocation != null) {
                mGetLocation.startLocationUpdates((lattitude, longitude) -> {
                    UtilMethods.getLattitude = lattitude;
                    UtilMethods.getLongitude = longitude;
                    if (CASA != 130) {
                        getSender();
                    } else {
                        getSenderCASA();
                    }
                });
            } else {
                mGetLocation = new GetLocation(DMTLoginNew.this, loader);
                mGetLocation.startLocationUpdates((lattitude, longitude) -> {
                    UtilMethods.getLattitude = lattitude;
                    UtilMethods.getLongitude = longitude;
                    if (CASA != 130) {
                        getSender();
                    } else {
                        getSenderCASA();
                    }
                });
            }
        }
    }

    private void getSenderCASA() {
        loader.show();
        loader.setCancelable(false);
        UtilsMethodDMTNew.INSTANCE.getSenderCasa(DMTLoginNew.this, oidIntent, senderNoEt.getText().toString(), "", loader, mGetLocation, new UtilsMethodDMTNew.ApiCallBack() {
            @Override
            public void onSuccess(Object object) {
                senderResponse = (SenderResponse) object;
                sidValue = senderResponse.getSid();
                if (senderResponse.getStatuscode() == -1) {
                    Intent intentS = new Intent(DMTLoginNew.this, HostActivity.class);
                    intentS.putExtra("pId", superMerchentId);
                    intentS.putExtra("pApiKey", "UFMwMDU4MDdiYTBkZjhhNzM2ZDJjZmIzYzdiZTkxOWQ3ZTI2OTgzYzE3MzUzODMzMTQ=");
                    intentS.putExtra("mCode", merchantId);
                    intentS.putExtra("mobile", mobile);
                    intentS.putExtra("lat", UtilMethods.getLattitude + "");
                    intentS.putExtra("pipe", "bank2");
                    intentS.putExtra("lng", UtilMethods.getLongitude + "");
                    intentS.putExtra("firm", "Ananya Finance");
                    intentS.putExtra("email", "ramit.mehta@prayasfinance.com");
                    intentS.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivityForResult(intentS, 1111);
                }
                if (senderResponse.getData() != null) {
                    Intent intent = new Intent(DMTLoginNew.this, HostActivity.class);
                    intent.putExtra("encdata", "UFMwMDU4MDdiYTBkZjhhNzM2ZDJjZmIzYzdiZTkxOWQ3ZTI2OTgzYzE3MzUzODMzMTQ=");
                    intent.putExtra("lat", UtilMethods.getLattitude);
                    intent.putExtra("lng", UtilMethods.getLongitude);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    hostActivityLauncher.launch(intent);
                } else {
                    if (loader != null && loader.isShowing()) {
                        loader.dismiss();
                    }
                    UtilMethods.INSTANCE.Error(DMTLoginNew.this, "Data is not available");
                }
            }
        });
    }

    private void getSender() {
        loader.show();
        loader.setCancelable(false);
        UtilsMethodDMTNew.INSTANCE.getSender(DMTLoginNew.this, oidIntent, senderNoEt.getText().toString(), "", loader, mGetLocation, new UtilsMethodDMTNew.ApiCallBack() {
            @Override
            public void onSuccess(Object object) {
                senderResponse = (SenderResponse) object;
                sidValue = senderResponse.getSid();
                wadhData = senderResponse.getWadh();
                if (senderResponse.getData() != null) {
                    setSenderData(senderResponse.getData(), null, null, senderNoEt.getText().toString(), null);
                } else {
                    if (loader != null && loader.isShowing()) {
                        loader.dismiss();
                    }

                    UtilMethods.INSTANCE.Error(DMTLoginNew.this, "Data is not available");
                }
            }
        });
    }

    private void DMRStatus() {
        dmr_layout.setVisibility(View.VISIBLE);
        sender_layout.setVisibility(View.GONE);
        submit.setVisibility(View.GONE);
//        if (IsSenderLogin()) {
//            dmr_layout.setVisibility(View.GONE);
//            sender_layout.setVisibility(View.VISIBLE);
//            submit.setVisibility(View.GONE);
//            SharedPreferences prefs = DMRLogin.this.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, DMRLogin.this.MODE_PRIVATE);
//            setCurrentDetail( prefs.getString(ApplicationConstant.INSTANCE.senderNumberPref, ""),prefs.getString(ApplicationConstant.INSTANCE.senderBalance, ""));
//
//        } else {
//            dmr_layout.setVisibility(View.VISIBLE);
//            sender_layout.setVisibility(View.GONE);
//            submit.setVisibility(View.GONE);
//        }

    }

   /* public boolean IsSenderLogin() {

        SharedPreferences prefs = DMTLoginNew.this.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, DMTLoginNew.this.MODE_PRIVATE);
        String isLogin = prefs.getString(ApplicationConstant.INSTANCE.senderNumberPref, null);

        if (isLogin != null && isLogin.length() > 0) {
            return true;
        } else {
            return false;
        }
    }*/

    private void SetListener() {

        submit.setOnClickListener(this);
        dmr_logout.setOnClickListener(this);
        add_bene.setOnClickListener(this);
        bene_list.setOnClickListener(this);
        dmr_report.setOnClickListener(this);
    }

    public void setCurrentDetail(String number, String name, String sid, String remainingLimit) {
        sender_num.setText(number);
        if (name != null && !name.isEmpty() && !name.equalsIgnoreCase("null")) {
            nameView.setVisibility(View.VISIBLE);
            sender_name.setText(name);
        } else {
            nameView.setVisibility(View.GONE);
        }

        sidValue = sid;
        remaining.setText("Remaining Limit : \n" + UtilMethods.INSTANCE.formatedAmountWithRupees(remainingLimit));
        dmr_layout.setVisibility(View.GONE);
        sender_layout.setVisibility(View.VISIBLE);
        submit.setVisibility(View.GONE);
    }


    @Override
    public void onClick(View v) {


        if (v == submit) {
            firstNameTil.setErrorEnabled(false);
            lastNameTil.setErrorEnabled(false);
            otpTil.setErrorEnabled(false);
            if (firstNameEt.getText().length() == 0) {
                firstNameTil.setError(getString(R.string.err_empty_field));
                firstNameEt.requestFocus();
                return;
            } else if (lastNameEt.getText().length() == 0) {
                firstNameTil.setError(getString(R.string.err_empty_field));
                lastNameEt.requestFocus();
                return;
            } else if (otpEt.getText().length() < 4) {
                otpTil.setError("Enter Valid OTP");
                otpEt.requestFocus();
                return;
            }

            if (UtilMethods.INSTANCE.isNetworkAvialable(DMTLoginNew.this)) {
                loader.show();
                loader.setCancelable(false);
                loader.setCanceledOnTouchOutside(false);

                UtilsMethodDMTNew.INSTANCE.createSender(DMTLoginNew.this, oidIntent,
                        senderNoEt.getText().toString().trim(), sidValue,
                        firstNameEt.getText().toString().trim(),
                        lastNameEt.getText().toString().trim(),
                        otpEt.getText().toString().trim(),
                        (senderResponse != null && senderResponse.getData() != null && senderResponse.getData().getReferenceID() != null && !senderResponse.getData().getReferenceID().isEmpty()) ? senderResponse.getData().getReferenceID() : sidValue,
                        loader, mGetLocation, new UtilsMethodDMTNew.ApiCallBack() {
                            @Override
                            public void onSuccess(Object object) {
                                senderResponse = (SenderResponse) object;
                                sidValue = senderResponse.getSid();
                                if (senderResponse.getData() != null) {
                                    setSenderData(senderResponse.getData(), null, null, senderNoEt.getText().toString().trim(),
                                            null);
                                } else {
                                    UtilMethods.INSTANCE.Error(DMTLoginNew.this, "Data is not available");
                                }
                            }
                        });

            } else {
                UtilMethods.INSTANCE.NetworkError(DMTLoginNew.this);
            }


        }
        if (v == dmr_logout) {
            UtilMethods.INSTANCE.setSenderNumber(DMTLoginNew.this, "", "", "", "");
            UtilMethods.INSTANCE.setSenderInfo(DMTLoginNew.this, "", "", false, null);
            UtilMethods.INSTANCE.setBeneficiaryList(DMTLoginNew.this, "");
            senderNoEt.setText("");
            dmr_create.setVisibility(View.GONE);
            dmr_layout.setVisibility(View.VISIBLE);
            sender_layout.setVisibility(View.GONE);
            submit.setVisibility(View.GONE);
        }
        if (v == add_bene) {
            Intent i = new Intent(this, AddBeneficiaryNew.class);
            i.putExtra("OpType", opTypeIntent);
            i.putExtra("OID", oidIntent);
            i.putExtra("SID", sidValue);
            i.putExtra("SenderNumber", senderNumberStr);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);


        }
        if (v == bene_list) {
            Intent i = new Intent(this, BeneficiaryListScreenNew.class);
            i.putExtra("OID", oidIntent);
            i.putExtra("SID", sidValue);
            i.putExtra("SenderNumber", senderNumberStr);
            startActivity(i);

        }
        if (v == dmr_report) {

            Intent m = new Intent(DMTLoginNew.this, DMRReport.class);
            m.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(m);

        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mGetLocation != null) {
            mGetLocation.onActivityResult(requestCode, resultCode, data);
        }
        if (requestCode == 1111) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    boolean status = data.getBooleanExtra("status", false);
                    int response = data.getIntExtra("response", 0);
                    String message = data.getStringExtra("message");
                    String detailedResponse = "Status:  " + status +
                            "\n\nResponse: " + response +
                            "\n\nMessage: " + message;
                    UtilMethods.INSTANCE.Successful(DMTLoginNew.this, detailedResponse);
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                UtilMethods.INSTANCE.Error(DMTLoginNew.this, "User cancelled");
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (mGetLocation != null) {
            mGetLocation.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onPause() {
        if (mGetLocation != null) {
            mGetLocation.onPause();
        }
        super.onPause();
    }


    private void setSenderData(DMTData dmtData, View otpView, TextView okBtn, String senderMobileNumber, DMTFingerPrintEKycDialogFragment mDMTFingerPrintEKycDialogFragment) {
        senderNumberStr = senderMobileNumber;
        if (dmtData.senderStatus == ApplicationConstant.DMT_SENDER_EKYC_REQUIRED) {
            if (mDMTFingerPrintEKycDialogFragment != null) {
                Toast.makeText(DMTLoginNew.this, "Sender EKYC Required", Toast.LENGTH_SHORT).show();
                mDMTFingerPrintEKycDialogFragment.hideLoader();
            } else {
                if (loader != null && loader.isShowing()) {
                    loader.dismiss();
                }
                if (dmtData.isBioMetricRequired()) {
                    openBiometricDialog(senderMobileNumber, (wadhData != null && !wadhData.isEmpty()) ? wadhData : "E0jzJ/P8UopUHAieZn8CKqS4WPMi5ZSYXgfnlfkWjrc="/*"18f4CEiXeXcfGXvgWA/blxD+w2pw7hfQPY45JMytkPw="*/);
                } else {
                    if (dialogAADHARValidate != null && dialogAADHARValidate.isShowing() && otpView != null) {
                        okBtn.setText("CONTINUE");
                        otpView.setVisibility(View.VISIBLE);
                    } else {
                        aadharValidateDialog(senderMobileNumber);
                    }
                }
            }
        } else if (dmtData.senderStatus == ApplicationConstant.DMT_SENDER_REGISTERATION_REQUIRED) {
            if (mDMTFingerPrintEKycDialogFragment != null) {
                Toast.makeText(DMTLoginNew.this, "Sender Registration Required", Toast.LENGTH_SHORT).show();
                mDMTFingerPrintEKycDialogFragment.dismiss();
            }
            if (loader != null && loader.isShowing()) {
                loader.dismiss();
            }
            if (dialogAADHARValidate != null && dialogAADHARValidate.isShowing() && otpView != null) {
                Toast.makeText(DMTLoginNew.this, "Sender Registration Required", Toast.LENGTH_SHORT).show();
                dialogAADHARValidate.dismiss();
            }
            OtherResponse(dmtData);

        } else {
            if (mDMTFingerPrintEKycDialogFragment != null) {
                mDMTFingerPrintEKycDialogFragment.dismiss();
            }
            if (dialogAADHARValidate != null && dialogAADHARValidate.isShowing() && otpView != null) {
                dialogAADHARValidate.dismiss();
            }
            if (loader != null && loader.isShowing()) {
                loader.dismiss();
            }

            UtilMethods.INSTANCE.setDMTSenderNumber(DMTLoginNew.this, senderMobileNumber);
            UtilMethods.INSTANCE.setDMTSenderData(DMTLoginNew.this, new Gson().toJson(senderResponse));


            String senderName = "";
            if (dmtData.getFirstName() != null && !dmtData.getFirstName().isEmpty()) {
                senderName = dmtData.getFirstName();
            }
            if (dmtData.getLastName() != null && !dmtData.getLastName().isEmpty()) {
                senderName = senderName + " " + dmtData.getLastName();
            }
            //getBeneficiaryWithLocation();
            SetSender(senderMobileNumber, senderName, sidValue, dmtData.getLimit() + "");


        }
    }


    private void OtherResponse(DMTData dmtData) {

        dmr_create.setVisibility(View.VISIBLE);
        submit.setVisibility(View.VISIBLE);
        submit.setText("Create Sender");

        /*senderLayout.setVisibility(View.GONE);
        noticeMsg.setVisibility(View.GONE);
        heading.setText("Create Sender");
        dmrCreate.setVisibility(View.VISIBLE);
        if (dmtData.getFirstName() != null && !dmtData.getFirstName().isEmpty()) {
            nameEt.setText(dmtData.getFirstName());
        }
        if (dmtData.getLastName() != null && !dmtData.getLastName().isEmpty()) {
            lastNameEt.setText(dmtData.getLastName());
        }


        if (dmtData.isOTPRequired()) {
            otpLabel.setVisibility(View.VISIBLE);
            otpView.setVisibility(View.VISIBLE);
        } else {
            otpLabel.setVisibility(View.GONE);
            otpView.setVisibility(View.GONE);
        }*/
    }

    public void SetSender(String mobileNo, String senderName, String remainigBalance, String totalBalance) {
        senderNoEt.setText("");
        firstNameEt.setText("");
        lastNameEt.setText("");
        otpEt.setText("");
        setCurrentDetail(mobileNo, senderName, remainigBalance, totalBalance);
    }

    private void openBiometricDialog(String senderNumber, String wadhValue) {
        DMTFingerPrintEKycDialogFragment mDMTFingerPrintEKycDialogFragment = new DMTFingerPrintEKycDialogFragment();
        mDMTFingerPrintEKycDialogFragment.setData(this, senderNumber, wadhValue, new BottomSheetCallBack() {
            @Override
            public void onClickCapture(String aadharNo, String pidData) {
                doSenderEKYCWithLocation(DMTLoginNew.this, null, null, senderNumber, "", aadharNo, pidData, mDMTFingerPrintEKycDialogFragment);
            }
        });
        mDMTFingerPrintEKycDialogFragment.show(getSupportFragmentManager(), "");
    }

    public void aadharValidateDialog(String senderNumber) {

        if (dialogAADHARValidate != null && dialogAADHARValidate.isShowing()) {
            return;
        }
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_aadhar_validate, null);

        EditText etAadharNo = view.findViewById(R.id.etAadharNo);
        View otpView = view.findViewById(R.id.otpView);
        EditText etOTPNo = view.findViewById(R.id.etOTPNo);
        TextView sender_num = view.findViewById(R.id.sender_num);
        sender_num.setText(senderNumber);

        dialogAADHARValidate = new Dialog(this);
        dialogAADHARValidate.setCancelable(false);
        dialogAADHARValidate.setContentView(view);
        dialogAADHARValidate.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        view.findViewById(R.id.cancel).setOnClickListener(v -> dialogAADHARValidate.dismiss());
        view.findViewById(R.id.closeIv).setOnClickListener(v -> dialogAADHARValidate.dismiss());
        TextView okBtn = view.findViewById(R.id.ok);
        okBtn.setOnClickListener(v -> {

            if (etAadharNo.getText().length() != 12) {
                etAadharNo.setError("Enter valid 12 digits AADHAAR number");
                etAadharNo.requestFocus();
                return;
            } else if (otpView.getVisibility() == View.VISIBLE && etOTPNo.getText().length() < 4) {
                etOTPNo.setError("Enter valid OTP");
                etOTPNo.requestFocus();
                return;
            }
            // dialog.dismiss();
            if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {
                loader.show();
                if (otpView.getVisibility() == View.VISIBLE) {
                    doSenderEKYCWithLocation(DMTLoginNew.this, otpView, okBtn, senderNumber, etOTPNo.getText().toString().trim(), etAadharNo.getText().toString().trim(), null, null);
                } else {
                    doSenderEKYCWithLocation(DMTLoginNew.this, otpView, okBtn, senderNumber, "", etAadharNo.getText().toString().trim(), null, null);
                }
            } else {
                UtilMethods.INSTANCE.NetworkError(this);
            }
        });
        dialogAADHARValidate.show();

    }

    private void doSenderEKYCWithLocation(Activity context, View otpView, TextView okBtn, String mobileNumber, String otp, String aadharNo,
                                          String pidData, DMTFingerPrintEKycDialogFragment mDmtFingerPrintEKycDialogFragment) {
        if (UtilMethods.getLattitude != 0 && UtilMethods.getLongitude != 0) {
            doSenderEKYC(context, otpView, okBtn, mobileNumber, otp, aadharNo,
                    pidData, mDmtFingerPrintEKycDialogFragment);
        } else {
            if (mGetLocation != null) {
                mGetLocation.startLocationUpdates((lattitude, longitude) -> {
                    UtilMethods.getLattitude = lattitude;
                    UtilMethods.getLongitude = longitude;
                    doSenderEKYC(context, otpView, okBtn, mobileNumber, otp, aadharNo,
                            pidData, mDmtFingerPrintEKycDialogFragment);
                });
            } else {
                mGetLocation = new GetLocation(DMTLoginNew.this, loader);
                mGetLocation.startLocationUpdates((lattitude, longitude) -> {
                    UtilMethods.getLattitude = lattitude;
                    UtilMethods.getLongitude = longitude;
                    doSenderEKYC(context, otpView, okBtn, mobileNumber, otp, aadharNo,
                            pidData, mDmtFingerPrintEKycDialogFragment);
                });
            }
        }
    }


    private void doSenderEKYC(Activity context, View otpView, TextView okBtn, String mobileNumber, String otp, String aadharNo,
                              String pidData, DMTFingerPrintEKycDialogFragment mDmtFingerPrintEKycDialogFragment) {

        UtilsMethodDMTNew.INSTANCE.doSenderEKYC(context, oidIntent, mobileNumber, aadharNo, pidData, otp,
                (senderResponse != null && senderResponse.getData() != null && senderResponse.getData().getReferenceID() != null && !senderResponse.getData().getReferenceID().isEmpty()) ? senderResponse.getData().getReferenceID() : sidValue,
                sidValue, loader,
                mGetLocation, new UtilsMethodDMTNew.ApiCallBackTwoMethod() {
                    @Override
                    public void onSuccess(Object object) {
                        senderResponse = (SenderResponse) object;
                        sidValue = senderResponse.getSid();
                        if (senderResponse.getData() != null) {
                            if (dialogAADHARValidate != null && dialogAADHARValidate.isShowing() && senderResponse.getData().getSenderStatus() == 0) {
                                senderResponse.getData().setSenderStatus(ApplicationConstant.DMT_SENDER_EKYC_REQUIRED);
                            }
                            setSenderData(senderResponse.getData(), otpView, okBtn, mobileNumber, mDmtFingerPrintEKycDialogFragment);
                        } else {
                            if (mDmtFingerPrintEKycDialogFragment != null) {
                                mDmtFingerPrintEKycDialogFragment.setError("Data is not available, try after some time");
                            } else {
                                UtilMethods.INSTANCE.Error(context, "Data is not available, try after some time");
                            }
                        }
                    }

                    @Override
                    public void onError(Object object) {
                        if (object instanceof SenderResponse) {
                            senderResponse = (SenderResponse) object;
                            if (!senderResponse.isVersionValid()) {
                                if (mDmtFingerPrintEKycDialogFragment != null) {
                                    mDmtFingerPrintEKycDialogFragment.dismiss();
                                }
                                UtilMethods.INSTANCE.versionDialog(context);
                            } else {
                                if (mDmtFingerPrintEKycDialogFragment != null) {
                                    mDmtFingerPrintEKycDialogFragment.setError(senderResponse.getMsg());
                                } else {
                                    String msg = senderResponse.getMsg() != null ? senderResponse.getMsg().toLowerCase() : "";
                                    if ((msg.contains("lattitude") || msg.contains("latitude")) && msg.contains("longitude")) {
                                        UtilMethods.INSTANCE.ErrorWithDoubleBtnCallBack(context, "Location", senderResponse.getMsg(), "Find Location", "Cancel", true, new CustomAlertDialog.DialogCallBack() {
                                            @Override
                                            public void onPositiveClick() {
                                                doSenderEKYCWithLocation(context, otpView, okBtn, mobileNumber, otp, aadharNo, pidData, mDmtFingerPrintEKycDialogFragment);

                                            }

                                            @Override
                                            public void onNegativeClick() {

                                            }
                                        });
                                    } else {


                                        UtilMethods.INSTANCE.Error(context, senderResponse.getMsg());
                                    }
                                }
                            }
                        }

                    }
                });
    }
    /* public void SetNumber(final String Number) {
        String Number1 = Number.replace("+91", "");
        String Number2 = Number1.replace("(", "");
        String Number3 = Number2.replace(")", "");
        String Number4 = Number3.replace(" ", "");
        String Number5 = Number4.replace("-", "");
        String Number6 = Number5.replace("_", "");
        senderNo.setText(Number6);
    }*/
}
