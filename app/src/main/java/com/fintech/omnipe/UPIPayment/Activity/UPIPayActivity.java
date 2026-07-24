package com.fintech.omnipe.UPIPayment.Activity;

import static com.google.android.material.textfield.TextInputLayout.END_ICON_NONE;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.fintech.omnipe.Api.Response.BalanceResponse;
import com.fintech.omnipe.Api.Response.RechargeReportResponse;
import com.fintech.omnipe.Auth.dto.LoginResponse;
import com.fintech.omnipe.BuildConfig;
import com.fintech.omnipe.UPIPayment.QrScannerViewUtils.VPAStrSuffixValidation;
import com.fintech.omnipe.UPIPayment.dto.ReqSendMoney;
import com.fintech.omnipe.UPIPayment.dto.UPIPaymentRequest;
import com.fintech.omnipe.UPIPayment.dto.VPAList;
import com.fintech.omnipe.UPIPayment.dto.VPAListRequest;
import com.fintech.omnipe.UPIPayment.dto.VPAVerifyResponse;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.ApiClient;
import com.fintech.omnipe.Util.ApplicationConstant;
import com.fintech.omnipe.Util.EndPointInterface;
import com.fintech.omnipe.Util.UtilMethods;
import com.fintech.omnipe.usefull.CustomLoader;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;


import org.egram.aepslib.DashboardActivity;

import retrofit2.Call;
import retrofit2.Callback;


public class UPIPayActivity extends AppCompatActivity implements View.OnClickListener{

    private String vpaVal,nameVal,senderNum;
    private TextInputLayout vpaTxt,amountTxt,beneNameTxt,pinPasswordTxt;
    private TextInputEditText vpaEdt,amountEdt,beneNamEdt,pinPasswordEdt;
    private View pinPasswordView;
    private ImageView vpaIv;
    private MaterialButton upiPayBtn,verifyBtn;
    private CustomLoader loader;
    private boolean isAutoVerifyVPA;
    private BalanceResponse mBalanceResponse;
    private LoginResponse mLoginDataResponse;
    private RequestOptions requestOptions;
    private VPAList mVPAData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_u_p_i_pay);
        if(getIntent()!=null){
            vpaVal=getIntent().getStringExtra("UPI");
            nameVal=getIntent().getStringExtra("Name");
            senderNum = getIntent().getStringExtra("SenderNum");
            mVPAData = getIntent().getParcelableExtra("VPAData");
        }
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        vpaIv=findViewById(R.id.vpaIv);
        upiPayBtn=findViewById(R.id.btn_upiPay);
        verifyBtn=findViewById(R.id.btn_verify);
        vpaTxt=findViewById(R.id.txt_vpa);
        amountTxt=findViewById(R.id.txt_amount);
        beneNameTxt=findViewById(R.id.txt_beneName);pinPasswordView=findViewById(R.id.pinPasswordView);
        pinPasswordEdt=findViewById(R.id.edit_pinPassword);
        pinPasswordTxt=findViewById(R.id.txt_pinPassword);
        vpaEdt=findViewById(R.id.edit_vpa);
        amountEdt=findViewById(R.id.edit_amount);
        beneNamEdt=findViewById(R.id.edit_beneName);
        requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.error(R.drawable.ic_upi_icon);
        requestOptions.placeholder(R.drawable.ic_upi_icon);

        if (UtilMethods.INSTANCE.getPinRequiredPref(this) || UtilMethods.INSTANCE.getDoubleFactorPref(this)) {
            pinPasswordView.setVisibility(View.VISIBLE);
        } else {
            pinPasswordView.setVisibility(View.GONE);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitle("UPI Payment");
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mLoginDataResponse = new Gson().fromJson(UtilMethods.INSTANCE.getLoginPref(this), LoginResponse.class);
        SharedPreferences myPreferences = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.balancePref, "");
        mBalanceResponse = new Gson().fromJson(balanceResponse, BalanceResponse.class);

        if (mBalanceResponse != null) {
            isAutoVerifyVPA= mBalanceResponse.isAutoVerifyVPA();
        }

        setVpaData();

        beneNamEdt.setFilters(new InputFilter[] {
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence cs, int start,
                                               int end, Spanned spanned, int dStart, int dEnd) {
                        // TODO Auto-generated method stub
                        if(cs.equals("")){ // for backspace
                            return cs;
                        }
                        if(cs.toString().matches("[a-zA-Z ]+")){
                            return cs;
                        }
                        return "";
                    }
                }
        });

        vpaEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (!beneNamEdt.getText().toString().isEmpty()) {
                    beneNamEdt.setText("");
                    vpaIv.setVisibility(View.GONE);
                    vpaIv.setImageResource(R.drawable.ic_upi_icon);
                    if (isAutoVerifyVPA) {
                        verifyBtn.setVisibility(View.GONE);
                    } else {
                        verifyBtn.setVisibility(View.VISIBLE);
                    }
                    beneNamEdt.setError(null);
                    beneNameTxt.setErrorEnabled(false);
                    beneNamEdt.setFocusable(true);
                    beneNamEdt.setClickable(true);
                    beneNamEdt.setLongClickable(true);
                    beneNamEdt.setFocusableInTouchMode(true);
                    ViewCompat.setBackgroundTintList(beneNamEdt, null);

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(vpaEdt.toString().isEmpty())
                {
                    vpaEdt.requestFocus();
                    vpaTxt.setError(getResources().getString(R.string.err_empty_field));
                }
                else if(!VPAStrSuffixValidation.INSTANCE.isValidVpAStr(vpaEdt.getText().toString())){
                    vpaTxt.setError(getResources().getString(R.string.err_vpa_field));
                    vpaEdt.requestFocus();
                }else{
                    vpaEdt.setError(null);
                    vpaTxt.setErrorEnabled(false);

                }
            }
        });


        upiPayBtn.setOnClickListener(this::onClick);
        verifyBtn.setOnClickListener(this::onClick);

    }

    private void setVpaData() {
        if (isAutoVerifyVPA) {
            verifyBtn.setVisibility(View.GONE);
            beneNamEdt.setOnFocusChangeListener((view, b) -> {
                if (b && !vpaEdt.getText().toString().isEmpty() && VPAStrSuffixValidation.INSTANCE.isValidVpAStr(vpaEdt.getText().toString())) {
                    verifyVPA(this, vpaEdt.getText().toString());
                }
            });
        } else {
            verifyBtn.setVisibility(View.VISIBLE);
        }


        if (mVPAData != null) {
            if (mVPAData.getVpa() != null && !mVPAData.getVpa().isEmpty() && mVPAData.getVpa().contains("@")) {

                setVPA(mVPAData.getVpa());

                if (mVPAData.isVerified()) {
                    verifyBtn.setVisibility(View.GONE);
                    setHandleImage(mVPAData.getVpa());
                } else {
                    if (isAutoVerifyVPA) {
                        if (UtilMethods.INSTANCE.isSpecialCharacter(mVPAData.getAccountHolder())) {
                            verifyBtn.setVisibility(View.GONE);
                            verifyVPA(this, mVPAData.getVpa());
                        } else {
                            verifyBtn.setVisibility(View.VISIBLE);
                        }
                    } else {
                        verifyBtn.setVisibility(View.VISIBLE);
                    }
                }
            }
            if (mVPAData.getAccountHolder() != null && !mVPAData.getAccountHolder().isEmpty()) {
                String filterName=mVPAData.getAccountHolder().replaceAll("[^a-zA-Z0-9]", " ");
                setName(filterName.replaceAll("[0-9]", ""));
            }

        }
        else {
            if (vpaVal != null && !vpaVal.isEmpty()&& vpaVal.contains("@")) {
                setVPA(vpaVal);
                setHandleImage(vpaVal);
            }

            if (nameVal != null && !nameVal.isEmpty()) {

                if (isAutoVerifyVPA) {
                    if (UtilMethods.INSTANCE.isSpecialCharacter(nameVal)) {
                        if (vpaVal != null && !vpaVal.isEmpty()) {
                            verifyBtn.setVisibility(View.GONE);
                            verifyVPA(this, vpaVal);
                        } else {
                            verifyBtn.setVisibility(View.VISIBLE);
                        }
                    } else {
                        verifyBtn.setVisibility(View.VISIBLE);
                    }

                } else {
                    verifyBtn.setVisibility(View.VISIBLE);
                }
                String filterName=nameVal.replaceAll("[^a-zA-Z0-9]", " ");
                setName(filterName.replaceAll("[0-9]", ""));
                //setName(nameVal.replaceAll("[^A-Za-z ]", ""));
            }
        }
    }

    private void setVPA(String vpa) {
        vpaEdt.setText(vpa.trim());
        vpaEdt.setFocusable(false);
        vpaEdt.setClickable(false);
        vpaEdt.setLongClickable(false);
        vpaEdt.setFocusableInTouchMode(false);
        vpaTxt.setEndIconMode(END_ICON_NONE);
        ViewCompat.setBackgroundTintList(vpaEdt, ContextCompat.getColorStateList(this, R.color.devider_color));
    }

    private void setName(String name) {
        beneNamEdt.setText(name.trim());
        beneNamEdt.setFocusable(false);
        beneNamEdt.setClickable(false);
        beneNamEdt.setLongClickable(false);
        beneNamEdt.setFocusableInTouchMode(false);
        beneNameTxt.setEndIconMode(END_ICON_NONE);
        ViewCompat.setBackgroundTintList(beneNamEdt, ContextCompat.getColorStateList(this, R.color.devider_color));
    }
    private void setHandleImage(String vpaStr) {
        String vpaHandle=UtilMethods.INSTANCE.getUPILogoFromVPAStr(vpaStr);
        vpaIv.setVisibility(View.VISIBLE);
        verifyBtn.setVisibility(View.GONE);
        Glide.with(this)
                .load(ApplicationConstant.INSTANCE.UPIIconUrl + vpaHandle.trim() + ".png")
                .apply(requestOptions).into(vpaIv);
    }

    @Override
    public void onClick(View clickView) {

        if(clickView==upiPayBtn){
            if(validView()){
                payWithUPI();
            }
        }
        else if(clickView==verifyBtn){
            if(vpaEdt.getText().toString().isEmpty())
            {
                vpaTxt.setError(getResources().getString(R.string.err_empty_field));
                vpaEdt.requestFocus();
            }else if(!VPAStrSuffixValidation.INSTANCE.isValidVpAStr(vpaEdt.getText().toString())){
                vpaTxt.setError(getResources().getString(R.string.err_vpa_field));
                vpaEdt.requestFocus();
            }else{
                vpaEdt.setError(null);
                vpaTxt.setErrorEnabled(false);
                verifyVPA(this,vpaEdt.getText().toString());
            }
        }
    }

    private boolean validView() {

        if(vpaEdt.getText().toString().isEmpty())
        {
            vpaTxt.setError(getResources().getString(R.string.err_empty_field));
            vpaEdt.requestFocus();
            return false;
        }else if(!VPAStrSuffixValidation.INSTANCE.isValidVpAStr(vpaEdt.getText().toString())){
            vpaTxt.setError(getResources().getString(R.string.err_vpa_field));
            vpaEdt.requestFocus();
        }
        else if(beneNamEdt.getText().toString().isEmpty())
        {
            beneNameTxt.setError(getResources().getString(R.string.err_empty_field));
            beneNamEdt.requestFocus();
            return false;
        }else if(amountEdt.getText().toString().isEmpty())
        {
            amountTxt.setError(getResources().getString(R.string.err_empty_field));
            amountEdt.requestFocus();
            return false;
        }else if(pinPasswordView.getVisibility()==View.VISIBLE && pinPasswordEdt.getText().toString().isEmpty())
        {
            pinPasswordTxt.setError(getResources().getString(R.string.err_empty_field));
            pinPasswordEdt.requestFocus();
            return false;
        }
        vpaTxt.setErrorEnabled(false);
        beneNameTxt.setErrorEnabled(false);
        amountTxt.setErrorEnabled(false);
        pinPasswordTxt.setErrorEnabled(false);
        return true;
    }

    private void payWithUPI() {
        try {
            vpaTxt.setErrorEnabled(false);
            beneNameTxt.setErrorEnabled(false);
            amountTxt.setErrorEnabled(false);
            pinPasswordTxt.setErrorEnabled(false);
            LoginResponse loginResponse = new Gson().fromJson(UtilMethods.INSTANCE.getLoginPref(this), LoginResponse.class);
            UPIPaymentRequest paymentReq=new UPIPaymentRequest(new ReqSendMoney(vpaEdt.getText().toString().trim(),amountEdt.getText().toString(),beneNamEdt.getText().toString().trim(),senderNum),pinPasswordEdt.getText().toString(), loginResponse.getData().getUserID() + "", loginResponse.getData().getLoginTypeID() + "",
                    ApplicationConstant.INSTANCE.APP_ID,
                    UtilMethods.INSTANCE.getIMEI(UPIPayActivity.this),
                    "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(UPIPayActivity.this),loginResponse.getData().getSession(),loginResponse.getData().getSessionID());

            loader.show();
            loader.setCancelable(false);

            UtilMethods.INSTANCE.doUPIPayment(this, loader, paymentReq, new UtilMethods.ApiCallBack() {
                @Override
                public void onSucess(Object object) {
                    if(loader!=null && loader.isShowing())
                        loader.dismiss();
                    if(object instanceof RechargeReportResponse){
                        RechargeReportResponse mResponse=(RechargeReportResponse)object;

                        if (mResponse.getStatuscode().equalsIgnoreCase("1") || mResponse.getStatuscode().equalsIgnoreCase("2")) {
                            if (mVPAData == null) {
                                vpaEdt.setText("");
                            }
                            amountEdt.setText("");
                            // Success
                            if (mResponse.getGroupID() != null && !mResponse.getGroupID().isEmpty()) {
                                UtilMethods.INSTANCE.GetDMTReceipt(UPIPayActivity.this,mResponse.getGroupID(), "All",
                                        loader);

                            } else {
                                UtilMethods.INSTANCE.Successfulok(mResponse.getMsg(), UPIPayActivity.this);
                            }

                        }
                        else if (mResponse.getStatuscode().equalsIgnoreCase("3")) {
                            //Error
                            UtilMethods.INSTANCE.Error(UPIPayActivity.this, mResponse.getMsg() + "");


                        }
                    }

                    vpaEdt.setText("");
                    amountEdt.setText("");
                    pinPasswordEdt.setText("");
                    beneNamEdt.setText("");
                }
            });
        }catch (Exception exception){
            if(loader!=null && loader.isShowing())
                loader.dismiss();
        }
    }

    private void verifyVPA(final Activity context, String vpaStr) {

        try {
            loader.show();
            String deviceId = UtilMethods.INSTANCE.getDeviceId(context);
            String deviceSerialNum =  UtilMethods.INSTANCE.getSerialNo(context);
            VPAListRequest paymentReq=new VPAListRequest(
                    vpaStr,
                    mLoginDataResponse.getData().getUserID() + "",
                    mLoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum,mLoginDataResponse.getData().getSessionID(),mLoginDataResponse.getData().getSession());

            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<VPAVerifyResponse> call = git.VerifyUPI(paymentReq);

            call.enqueue(new Callback<VPAVerifyResponse>() {

                @Override
                public void onResponse(Call<VPAVerifyResponse> call, retrofit2.Response<VPAVerifyResponse> response) {

                    if (loader != null && loader.isShowing())
                        loader.dismiss();
                    if (response.isSuccessful()) {
                        if (response.body() != null) {

                            if (response.body().getStatuscode()==1) {


                                if (response.body().getData() != null) {
                                    if (response.body().getData().getAccountHolder() != null && !response.body().getData().getAccountHolder().isEmpty()) {
                                        nameVal = response.body().getData().getAccountHolder().trim();
                                        setName(nameVal.replaceAll("[^A-Za-z ]", ""));
                                        verifyBtn.setVisibility(View.GONE);
                                        setHandleImage(vpaStr);
                                    } else {
                                        if (response.body().getData().getStatuscode() == -1) {
                                            UtilMethods.INSTANCE.Error(context, response.body().getData().getMsg() + "");
                                        } else {
                                            UtilMethods.INSTANCE.Error(context, "UPI is not available");
                                        }

                                    }
                                }
                                else {
                                    UtilMethods.INSTANCE.Error(context, "UPI is not available");
                                }

                                /*if(response.body().getData()!=null && response.body().getData().getStatuscode()==2){
                                    UtilMethods.INSTANCE.Successful(context,response.body().getMsg());
                                    if(response.body().getData().getAccountHolder()!=null && !response.body().getData().getAccountHolder().isEmpty()){
                                        beneNamEdt.setText(response.body().getData().getAccountHolder());
                                        beneNamEdt.setEnabled(false);

                                    }
                                }
                                else if(response.body().getData()!=null && response.body().getData().getStatuscode()==1){
                                    //verifyVpaBtn.setVisibility(View.VISIBLE);
                                    beneNamEdt.setText("");
                                    UtilMethods.INSTANCE.Processing(context,response.body().getData().getMsg()+"");
                                }else if(response.body().getData()!=null && response.body().getData().getStatuscode()==-1){
                                    //verifyVpaBtn.setVisibility(View.VISIBLE);
                                    beneNamEdt.setText("");
                                    UtilMethods.INSTANCE.Error(context,response.body().getData().getMsg()+"");
                                }else{
                                    //verifyVpaBtn.setVisibility(View.VISIBLE);
                                    beneNamEdt.setText("");
                                    UtilMethods.INSTANCE.Error(context,"No Bene Name found!");
                                }*/
                            }
                            else if (response.body().getStatuscode()==3) {
                                //beneNamEdt.setText("");
                                UtilMethods.INSTANCE.Error(context,response.body().getMsg()+"");

                            } else {
                                //beneNamEdt.setText("");
                                if (!response.body().isVersionValid()) {
                                    UtilMethods.INSTANCE.versionDialog(context);
                                } else {
                                    UtilMethods.INSTANCE.Error(context, response.body().getMsg() + "");
                                }

                            }

                        }
                    } else {
                        //beneNamEdt.setText("");
                        UtilMethods.INSTANCE.apiErrorHandle(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<VPAVerifyResponse> call, Throwable t) {
                    if (loader != null && loader.isShowing())
                        loader.dismiss();
                    //beneNamEdt.setText("");
                    try {
                        UtilMethods.INSTANCE.apiFailureError(context, t);
                    } catch (IllegalStateException ise) {
                        UtilMethods.INSTANCE.Error(context, ise.getMessage());

                    }
                }
            });

        } catch (Exception ise) {
            ise.printStackTrace();
            //beneNamEdt.setText("");
            UtilMethods.INSTANCE.Error(context, ise.getMessage());
            if (loader != null && loader.isShowing())
                loader.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, DashboardActivity.class));

    }
}