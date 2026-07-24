package com.fintech.omnipe.DashBoard.UI;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.fintech.omnipe.Auth.dto.LoginResponse;
import com.fintech.omnipe.BuildConfig;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.ApplicationConstant;
import com.fintech.omnipe.Util.UDetailByMobResponse;
import com.fintech.omnipe.Util.UDetailsWTW;
import com.fintech.omnipe.Util.UtilMethods;
import com.fintech.omnipe.usefull.CustomLoader;

public class WalletToWalletTransferActivity extends AppCompatActivity implements View.OnClickListener {

    private Activity activity;
    private View customerNoView,customerDetailsView,amountView,remarkView;
    private TextInputLayout txt_customerNo,txt_amount,txt_remark;
    private TextInputEditText remarkEdit,customerNoEdit,amountEdit;
    private AppCompatTextView customerDetailsTv;
    private AppCompatRadioButton prepaidRadioBtn,utilityRadioBtn,bankRadioBtn;
    private RadioGroup radioGroup;
    private Button transferBtn;

    private CustomLoader loader;
    private int walletID=0,uid,roleId;
    private LoginResponse loginResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_wallet_to_wallet_transfer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Wallet To Wallet");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getIDS();
        String LoginResponse = UtilMethods.INSTANCE.getLoginPref(activity);
        loginResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);

        /*MenuItem closeView =materialToolbar.getMenu().findItem(R.id.item_closeIcon);
        closeView.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });*/

        customerNoEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!customerNoEdit.getText().toString().isEmpty()){
                    if(customerNoEdit.getText().toString().length()==10 || count==10){
                        txt_customerNo.setErrorEnabled(false);

                        getUDetailByMobAPI();

                    }else {
                        txt_customerNo.setErrorEnabled(true);
                        txt_customerNo.setError("Mobile No should be length of 10 digits");
                    }
                }
            }

            private void getUDetailByMobAPI() {


                UDetailByMobRequest uDetailByMobRequest=new UDetailByMobRequest(
                        loginResponse.getData().getUserID(),
                        loginResponse.getData().getLoginTypeID(),
                        ApplicationConstant.INSTANCE.APP_ID,
                        UtilMethods.INSTANCE.getIMEI(activity),
                        "",
                        BuildConfig.VERSION_NAME,
                        UtilMethods.INSTANCE.getSerialNo(activity),
                        loginResponse.getData().getSessionID(),
                        loginResponse.getData().getSession(),
                        customerNoEdit.getText().toString().trim());


                if(uDetailByMobRequest!=null){
                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(false);

                    UtilMethods.INSTANCE.GetUDetailByMob(activity, uDetailByMobRequest, loader, new UtilMethods.ApiCallBack() {
                        @Override
                        public void onSucess(Object object) {

                            if(object!=null && object instanceof UDetailByMobResponse){
                                UDetailByMobResponse uDetailByMobResponse=(UDetailByMobResponse)object;
                                UDetailsWTW uDetailsWTW=uDetailByMobResponse.getuDetailsWTW();

                                if(uDetailsWTW!=null){
                                    setUiForTransfer(uDetailsWTW);

                                }
                            }

                        }
                    });
                }

            }

            private void setUiForTransfer(UDetailsWTW uDetailsWTW) {

                customerNoView.setVisibility(View.GONE);
                customerDetailsView.setVisibility(View.VISIBLE);
                amountView.setVisibility(View.VISIBLE);
                remarkView.setVisibility(View.VISIBLE);
                transferBtn.setVisibility(View.VISIBLE);

                String userName=uDetailsWTW.getOutletName() + "[ " + uDetailsWTW.getMobileNo() +" ] ";

                customerDetailsTv.setText(userName);

                if(uDetailsWTW.isPrepaidB())
                    prepaidRadioBtn.setVisibility(View.VISIBLE);
                else
                    prepaidRadioBtn.setVisibility(View.GONE);

                if(uDetailsWTW.isUtilityB())
                    utilityRadioBtn.setVisibility(View.VISIBLE);
                else
                    utilityRadioBtn.setVisibility(View.GONE);

                if(uDetailsWTW.isBankB())
                    bankRadioBtn.setVisibility(View.VISIBLE);
                else
                    bankRadioBtn.setVisibility(View.GONE);

                //bankRadioBtn.setVisibility(View.GONE);

                uid=uDetailsWTW.getUserID();
                roleId=uDetailsWTW.getRoleID();


            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

    }

    private void getIDS() {
        activity=this;
        loader=new CustomLoader(activity,android.R.style.Theme_Translucent_NoTitleBar);

        customerNoView=findViewById(R.id.customerNoView);
        customerDetailsView=findViewById(R.id.customerDetailsView);
        amountView=findViewById(R.id.amountView);
        remarkView=findViewById(R.id.remarkView);
        txt_customerNo=findViewById(R.id.txt_customerNo);
        txt_amount=findViewById(R.id.txt_amount);
        txt_remark=findViewById(R.id.txt_remark);
        remarkEdit=findViewById(R.id.edit_remark);
        amountEdit=findViewById(R.id.edit_amount);
        customerNoEdit=findViewById(R.id.edit_customerNo);
        customerDetailsTv=findViewById(R.id.customerDetailsTv);
        prepaidRadioBtn=findViewById(R.id.btnRd_prepaid);
        utilityRadioBtn=findViewById(R.id.btnRd_utility);
        bankRadioBtn=findViewById(R.id.btnRd_bank);
        radioGroup=findViewById(R.id.radioGroup);
        transferBtn=findViewById(R.id.btn_transfer);

        prepaidRadioBtn.setOnClickListener(this);
        utilityRadioBtn.setOnClickListener(this);
        bankRadioBtn.setOnClickListener(this);
        transferBtn.setOnClickListener(this);

        if(prepaidRadioBtn.isChecked()){
            walletID=1;
        }else if(utilityRadioBtn.isChecked()){
            walletID=2;
        }

        customerDetailsView.setVisibility(View.GONE);
        amountView.setVisibility(View.GONE);
        remarkView.setVisibility(View.GONE);
        transferBtn.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View clickView) {
        if(clickView==prepaidRadioBtn){
            walletID=1;
        }else if(clickView==utilityRadioBtn){
            walletID=2;
        }else if(clickView==bankRadioBtn){
            walletID=3;
        }else if(clickView==transferBtn){
            if(!validateForm()){
                return;
            }

            callWalletToWalletFTAPI();
        }
    }

    private void callWalletToWalletFTAPI() {
        WalletToWalletFTRequest walletFTRequest=new WalletToWalletFTRequest(
                loginResponse.getData().getUserID(),
                loginResponse.getData().getLoginTypeID(),
                ApplicationConstant.INSTANCE.APP_ID,
                UtilMethods.INSTANCE.getIMEI(activity),
                "",
                BuildConfig.VERSION_NAME,
                UtilMethods.INSTANCE.getSerialNo(activity),
                loginResponse.getData().getSessionID(),
                loginResponse.getData().getSession(),
                uid,
                amountEdit.getText().toString().trim(),
                remarkEdit.getText().toString(),
                walletID
        );

        if(walletFTRequest!=null){
            loader.show();
            loader.setCancelable(false);
            UtilMethods.INSTANCE.walletToWalletFT(activity, walletFTRequest, loader, new UtilMethods.ApiCallBack() {
                @Override
                public void onSucess(Object object) {
                    if(object!=null && object instanceof UDetailByMobResponse){
                        UDetailByMobResponse uDetailByMobResponse=(UDetailByMobResponse)object;
                        UtilMethods.INSTANCE.Successful(activity,uDetailByMobResponse.getMsg()+"");
                        amountEdit.setText("");
                        remarkEdit.setText("");
                    }

                }
            });
        }


    }

    private boolean validateForm() {
        if(roleId!=3){
            UtilMethods.INSTANCE.Error(activity,"User is not a valid for this service");
            return false;
        }else if(walletID==0){
            UtilMethods.INSTANCE.Error(activity,"Choose Wallet Type ");
            return false;
        }else if(amountEdit.getText().toString().isEmpty()){
            txt_amount.setError(getString(R.string.err_empty_field));
            amountEdit.requestFocus();
            return false;
        }
        txt_amount.setErrorEnabled(false);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}