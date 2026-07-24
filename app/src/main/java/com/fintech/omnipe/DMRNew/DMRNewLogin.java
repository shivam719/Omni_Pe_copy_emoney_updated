package com.fintech.omnipe.DMRNew;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.fintech.omnipe.DMRNew.Data.DMTData;
import com.fintech.omnipe.DMRNew.fragment.BottomSheetCallBack;
import com.fintech.omnipe.DMRNew.fragment.DMTFingerPrintEKycDialogFragment;
import com.fintech.omnipe.DMRNew.response.SenderResponse;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.ApplicationConstant;
import com.fintech.omnipe.Util.GetLocation;
import com.fintech.omnipe.Util.UtilMethods;
import com.fintech.omnipe.usefull.CustomLoader;

public class DMRNewLogin extends AppCompatActivity implements View.OnClickListener{
    EditText sender_number, firstNameEt, lastNameEt, otpEt;
    TextInputLayout firstNameTil, lastNameTil, otpTil;
    Button submit;
    LinearLayout dmr_create, sender_layout, dmr_layout;
    TextView sender_num, sender_name, remaining;
    CustomLoader loader = null;
    ImageView dmr_logout;
    View add_bene, bene_list;
    LinearLayout dmr_report, nameView;

    int opTypeIntent;
    int oidIntent;
    private String sidValue;
    GetLocation mGetLocation;
    private SenderResponse senderResponse;
    private Dialog dialogAADHARValidate;
    private String senderNumberStr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(),
                getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_dmrnew_login);
        UtilMethods.INSTANCE.setDashboardStatus(DMRNewLogin.this, false);
        mGetLocation = new GetLocation(this, loader);
        opTypeIntent = getIntent().getIntExtra("OpType", 0);
        oidIntent = getIntent().getIntExtra("OID", 0);
        Toast.makeText(this, ""+oidIntent, Toast.LENGTH_SHORT).show();
        GetId();
    }
    private void GetId() {
        sender_number=findViewById(R.id.sender_number);
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
        loader = new CustomLoader(DMRNewLogin.this, android.R.style.Theme_Translucent_NoTitleBar);

        sender_number.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {
           }

           @Override
           public void afterTextChanged(Editable s) {
               if (s.length() == 10) {
                   getSenderWithLocation();
               }
                   else {
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

    private void SetListener() {
        submit.setOnClickListener(this);
        add_bene.setOnClickListener(this);
    }


    private void DMRStatus() {
        dmr_layout.setVisibility(View.VISIBLE);
        sender_layout.setVisibility(View.GONE);
        submit.setVisibility(View.GONE);
    }

    private void getSenderWithLocation() {
        if (UtilMethods.INSTANCE.getLattitude != 0 && UtilMethods.INSTANCE.getLongitude != 0) {
            getSender();
        } else {
            if (mGetLocation != null) {
                mGetLocation.startLocationUpdates((lattitude, longitude) -> {
                    UtilMethods.INSTANCE.getLattitude = lattitude;
                    UtilMethods.INSTANCE.getLongitude = longitude;
                    getSender();
                });
            } else {
                mGetLocation = new GetLocation(DMRNewLogin.this, loader);
                mGetLocation.startLocationUpdates((lattitude, longitude) -> {
                    UtilMethods.INSTANCE.getLattitude = lattitude;
                    UtilMethods.INSTANCE.getLongitude = longitude;
                    getSender();
                });
            }
        }
    }

    private void getSender() {
        loader.show();
        loader.setCancelable(false);
        UtilMethods.INSTANCE.getSender(DMRNewLogin.this,oidIntent,sender_number.getText().toString(),sidValue,loader,mGetLocation, new UtilMethods.ApiCallBack() {
            @Override
            public void onSucess(Object object) {
                    senderResponse = (SenderResponse) object;
                    sidValue = senderResponse.getSid();
                    if (senderResponse.getData() != null) {
                        setSenderData(senderResponse.getData(), null, null, sender_number.getText().toString(), null);
                    } else {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }

                        UtilMethods.INSTANCE.Error(DMRNewLogin.this, "Data is not available");
                    }
                }

        });
        }

    private void setSenderData(DMTData dmtData, View otpView, TextView okBtn, String senderMobileNumber, DMTFingerPrintEKycDialogFragment mDMTFingerPrintEKycDialogFragment) {
        senderNumberStr = senderMobileNumber;
        if (dmtData.senderStatus == ApplicationConstant.INSTANCE.DMT_SENDER_EKYC_REQUIRED) {
            if (mDMTFingerPrintEKycDialogFragment != null) {
                Toast.makeText(DMRNewLogin.this, "Sender EKYC Required", Toast.LENGTH_SHORT).show();
                mDMTFingerPrintEKycDialogFragment.hideLoader();
            } else {
                if (loader != null && loader.isShowing()) {
                    loader.dismiss();
                }
                if (dmtData.isBioMetricRequired()) {
                    openBiometricDialog(senderMobileNumber, "18f4CEiXeXcfGXvgWA/blxD+w2pw7hfQPY45JMytkPw=");
                } else {
                    if (dialogAADHARValidate != null && dialogAADHARValidate.isShowing() && otpView != null) {
                        okBtn.setText("CONTINUE");
                        otpView.setVisibility(View.VISIBLE);
                    } else {
                        aadharValidateDialog(senderMobileNumber);
                    }
                }
            }
        } else if (dmtData.senderStatus == ApplicationConstant.INSTANCE.DMT_SENDER_REGISTERATION_REQUIRED) {
            if (mDMTFingerPrintEKycDialogFragment != null) {
                Toast.makeText(DMRNewLogin.this, "Sender Registration Required", Toast.LENGTH_SHORT).show();
                mDMTFingerPrintEKycDialogFragment.dismiss();
            }
            if (loader != null && loader.isShowing()) {
                loader.dismiss();
            }
            if (dialogAADHARValidate != null && dialogAADHARValidate.isShowing() && otpView != null) {
                Toast.makeText(DMRNewLogin.this, "Sender Registration Required", Toast.LENGTH_SHORT).show();
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

            UtilMethods.INSTANCE.setDMTSenderNumber(DMRNewLogin.this, senderMobileNumber);
            UtilMethods.INSTANCE.setDMTSenderData(DMRNewLogin.this, new Gson().toJson(senderResponse));


            String senderName = "";
            if (dmtData.getFirstName() != null && !dmtData.getFirstName().isEmpty()) {
                senderName = dmtData.getFirstName();
            }
            if (dmtData.getLastName() != null && !dmtData.getLastName().isEmpty()) {
                senderName = senderName + " " + dmtData.getLastName();
            }
            //getBeneficiaryWithLocation();
            SetSender(senderMobileNumber + "", senderName, dmtData.getLimit() + "", dmtData.getLimit() + "");
        }
    }
    public void SetSender(String mobileNo, String senderName, String remainigBalance, String totalBalance) {
        sender_number.setText("");
        firstNameEt.setText("");
        lastNameEt.setText("");
        otpEt.setText("");
        setCurrentDetail(mobileNo, senderName, remainigBalance, totalBalance);
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

    private void OtherResponse(DMTData dmtData) {
        dmr_create.setVisibility(View.VISIBLE);
        submit.setVisibility(View.VISIBLE);
        submit.setText("Create Sender");
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
                etAadharNo.setError("Enter valid 12 digits AADHAR number");
                etAadharNo.requestFocus();
                return;
            } else if (otpView.getVisibility() == View.VISIBLE && etOTPNo.getText().length() < 4) {
                etOTPNo.setError("Enter valid OTP");
                etOTPNo.requestFocus();
                return;
            }
            // dialog.dismiss();
            if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {
                setResult(RESULT_OK);
                loader.show();
                if (otpView.getVisibility() == View.VISIBLE) {
//                    doSenderEKYCWithLocation(DMRNewLogin.this, otpView, okBtn, senderNumber, etOTPNo.getText().toString().trim(), etAadharNo.getText().toString().trim(), null, null);
                } else {
//                    doSenderEKYCWithLocation(DMRNewLogin.this, otpView, okBtn, senderNumber, "", etAadharNo.getText().toString().trim(), null, null);
                }
            } else {
                UtilMethods.INSTANCE.NetworkError(this);
            }
        });
        dialogAADHARValidate.show();
    }


    private void openBiometricDialog(String senderNumber, String wadhValue) {
        DMTFingerPrintEKycDialogFragment mDMTFingerPrintEKycDialogFragment = new DMTFingerPrintEKycDialogFragment();
        mDMTFingerPrintEKycDialogFragment.setData(this, senderNumber, wadhValue, new BottomSheetCallBack() {
            @Override
            public void onClickCapture(String aadharNo, String pidData) {
            }
        });
        mDMTFingerPrintEKycDialogFragment.show(getSupportFragmentManager(), "");
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

            if (UtilMethods.INSTANCE.isNetworkAvialable(DMRNewLogin.this)) {
                loader.show();
                loader.setCancelable(false);
                loader.setCanceledOnTouchOutside(false);

                UtilMethods.INSTANCE.createSender(DMRNewLogin.this, oidIntent,
                        sender_number.getText().toString().trim(), sidValue,
                        firstNameEt.getText().toString().trim(),
                        lastNameEt.getText().toString().trim(),
                        otpEt.getText().toString().trim(),
                        (senderResponse!=null && senderResponse.getData()!=null&& senderResponse.getData().getReferenceID()!=null && !senderResponse.getData().getReferenceID().isEmpty())?senderResponse.getData().getReferenceID():sidValue,
                        loader, mGetLocation, new UtilMethods.ApiCallBack() {
                            @Override
                            public void onSucess(Object object) {
                                senderResponse = (SenderResponse) object;
                                sidValue = senderResponse.getSid();
                                if (senderResponse.getData() != null) {
                                    setSenderData(senderResponse.getData(), null, null, sender_name.getText().toString().trim(),
                                            null);
                                } else {
                                    UtilMethods.INSTANCE.Error(DMRNewLogin.this, "Data is not available");
                                }
                            }

                        });

            } else {
                UtilMethods.INSTANCE.NetworkError(DMRNewLogin.this);
            }


        }
        if (v == add_bene) {
            Intent i = new Intent(this, AddBeneficiary.class);
            i.putExtra("OpType", opTypeIntent);
            i.putExtra("OID", oidIntent);
            i.putExtra("SID", sidValue);
            i.putExtra("SenderNumber", senderNumberStr);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        }
    }
}