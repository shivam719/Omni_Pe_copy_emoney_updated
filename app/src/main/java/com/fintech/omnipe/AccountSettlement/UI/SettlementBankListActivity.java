package com.fintech.omnipe.AccountSettlement.UI;

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
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fintech.omnipe.AccountSettlement.API.SettlementAccountData;
import com.fintech.omnipe.AccountSettlement.API.SettlementAccountResponse;
import com.fintech.omnipe.AccountSettlement.Adapter.SettlementAccountAdapter;
import com.fintech.omnipe.Api.Response.BasicResponse;
import com.fintech.omnipe.Auth.dto.LoginResponse;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.UtilMethods;
import com.fintech.omnipe.usefull.CustomLoader;
import com.google.gson.Gson;


import java.util.ArrayList;
import java.util.List;

public class SettlementBankListActivity extends AppCompatActivity {

    private Activity mActivity;
    private RecyclerView settlementBankListRv;
    private AppCompatEditText searchEt;
    private CustomLoader loader;
    private LoginResponse mLoginDataResponse;
    private List<SettlementAccountData>  mBankListObjects=new ArrayList<>();
    private SettlementAccountAdapter mAdapter;
    private AlertDialog alertUTRDialog;
    private Dialog alertDeleteDialog;
    private final static int INTENT_REQUEST_CODE_UPDATE=101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_settelement_bank_list);

        findIds();
        hitApi();
    }

    private void findIds() {
        mActivity=this;
        mLoginDataResponse = new Gson().fromJson(UtilMethods.INSTANCE.getLoginPref(mActivity), LoginResponse.class);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        loader.setCancelable(false);
        loader.setCanceledOnTouchOutside(false);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitle("Settlement Accounts");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());


        settlementBankListRv=findViewById(R.id.settlementBankListRv);
        settlementBankListRv.setLayoutManager(new LinearLayoutManager(mActivity));


        searchEt = findViewById(R.id.searchEt);
        findViewById(R.id.clearIcon).setOnClickListener(v -> searchEt.setText(""));

        findViewById(R.id.addFActionBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(mActivity, AddUpdateSettlementAccountActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP), INTENT_REQUEST_CODE_UPDATE);
            }
        });

        searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence newText, int start, int before, int count) {

                if (mAdapter != null) {
                    mAdapter.filter(newText.toString());
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void hitApi() {
        if (UtilMethods.INSTANCE.isNetworkAvialable(mActivity)) {
            loader.show();

            UtilMethods.INSTANCE.SettlementAccountBankApi(mActivity, mLoginDataResponse, loader, new UtilMethods.ApiResponseCallBack() {
                @Override
                public void onSuccess(Object object) {

                    if(object!=null && object instanceof SettlementAccountResponse){
                        SettlementAccountResponse response=(SettlementAccountResponse)object;
                        if(response!=null && response.getDataList()!=null && response.getDataList().size()>0){
                            mBankListObjects=response.getDataList();
                            mAdapter = new SettlementAccountAdapter(mBankListObjects,response.isSattlemntAccountVerify(), mActivity);
                            settlementBankListRv.setAdapter(mAdapter);
                        }
                    }
                }

                @Override
                public void onError(int error) {
                    UtilMethods.INSTANCE.NetworkError(mActivity);
                }
            });
        }
        else {
            UtilMethods.INSTANCE.NetworkError(mActivity);
        }

    }

    public void verifyAccountUser(int updatedId) {
        try {
            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);

            UtilMethods.INSTANCE.VerifySettlementAccountApi(mActivity, mLoginDataResponse,updatedId,loader, new UtilMethods.ApiResponseCallBack() {
                @Override
                public void onSuccess(Object object) {
                    if(object!=null && object instanceof BasicResponse)
                    {
                        BasicResponse response=(BasicResponse)object;
                        if(response.getData()!=null){
                            if (response.getData().getStatuscode() == 1) {
                                UtilMethods.INSTANCE.Processing(mActivity,response.getData().getMsg()+"");
                                hitApi();
                            }else if (response.getData().getStatuscode() ==2) {
                                UtilMethods.INSTANCE.Successful(mActivity,response.getData().getMsg()+"");
                                hitApi();
                            }else {
                                UtilMethods.INSTANCE.Error(mActivity, response.getData().getMsg() + "");
                            }
                        }
                        else {
                            UtilMethods.INSTANCE.Successful(mActivity, response.getMsg() + "");
                            hitApi();
                        }
                    }
                }

                @Override
                public void onError(int error) {
                    if (loader != null && loader.isShowing()) {
                        loader.dismiss();
                    }

                }
            });

        }
        catch (Exception ex)
        {
            if (loader != null && loader.isShowing()) {
                loader.dismiss();
            }
            UtilMethods.INSTANCE.Error(mActivity, ex.getMessage() + "");
        }
    }

    public void updateUTR(SettlementAccountData dataItem) {
        try {
            if (alertUTRDialog != null && alertUTRDialog.isShowing()) {
                return;
            }


            AlertDialog.Builder dialogBuilder;
            dialogBuilder = new AlertDialog.Builder(this);
            alertUTRDialog = dialogBuilder.create();
            alertUTRDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_update_utr, null);
            alertUTRDialog.setView(dialogView);

            final AppCompatEditText mobileEt = dialogView.findViewById(R.id.mobileEt);
            AppCompatTextView cancelBtn = dialogView.findViewById(R.id.cancelBtn);
            AppCompatTextView sendBtn = dialogView.findViewById(R.id.sendBtn);
            AppCompatImageView closeIv = dialogView.findViewById(R.id.closeIv);
            TextView notice = dialogView.findViewById(R.id.notice);


            String updateUTR = dataItem.getUtr()+"";
            int length=updateUTR.length();
            if (length >4) {
                int midlength = updateUTR.length() - 4;
                updateUTR = updateUTR.substring(0, 2) + createUTRPatternStr(midlength) + updateUTR.substring(length - 2, length);
            } else {
                updateUTR = createUTRPatternStr(length);
            }


            notice.setText("We have sent Rs 1 in Your Bank Account With UTR NO " + updateUTR + ". Please check your Statement and enter complete UTR to Verify your Bank Account\\n\\n\n" +
                    "\n" +
                    "हमने आपके बैंक खाते में यूटीआर नं " + updateUTR + " के साथ 1 रुपये भेजे हैं। कृपया अपना बैंक विवरण जांचें और अपना बैंक खाता सत्यापित करने के लिए पूरा UTR दर्ज करें");
            //mobileEt.setText(data.getUtr() + "");


            closeIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertUTRDialog.dismiss();
                }
            });

            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertUTRDialog.dismiss();
                }
            });

            sendBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mobileEt.getText().toString().isEmpty()) {
                        mobileEt.setError("Please Enter a valid UTR Number");
                        mobileEt.requestFocus();
                        return;
                    }
                    loader.show();
                    UtilMethods.INSTANCE.UpdateUTRSettlementAccountApi(mActivity, mLoginDataResponse, dataItem.getId(), mobileEt.getText().toString(), loader, new UtilMethods.ApiResponseCallBack() {
                        @Override
                        public void onSuccess(Object object) {
                            if(alertUTRDialog!=null && alertUTRDialog.isShowing())
                                alertUTRDialog.dismiss();
                            if (loader != null && loader.isShowing())
                                loader.dismiss();

                            if(object!=null && object instanceof BasicResponse)
                            {
                                BasicResponse response=(BasicResponse)object;
                                if(response.getData()!=null){
                                    if (response.getData().getStatuscode() == 1) {
                                        UtilMethods.INSTANCE.Successful(mActivity,response.getData().getMsg()+"");
                                        hitApi();
                                    }else if (response.getData().getStatuscode() ==2) {
                                        UtilMethods.INSTANCE.Successful(mActivity,response.getData().getMsg()+"");
                                        hitApi();
                                    }else {
                                        UtilMethods.INSTANCE.Error(mActivity, response.getData().getMsg() + "");
                                    }
                                }
                                else {
                                    UtilMethods.INSTANCE.Successful(mActivity, response.getMsg() + "");
                                    hitApi();
                                }
                            }
                        }

                        @Override
                        public void onError(int error) {
                            if(alertUTRDialog!=null && alertUTRDialog.isShowing())
                               alertUTRDialog.dismiss();
                            if (loader != null && loader.isShowing())
                                loader.dismiss();

                        }
                    });

                }
            });


            alertUTRDialog.show();

        } catch (IllegalStateException ise) {
            if(alertUTRDialog!=null && alertUTRDialog.isShowing())
                alertUTRDialog.dismiss();
            if (loader != null && loader.isShowing())
                loader.dismiss();
        } catch (IllegalArgumentException iae) {

        } catch (WindowManager.BadTokenException bde) {
            bde.printStackTrace();
            if(alertUTRDialog!=null && alertUTRDialog.isShowing())
                alertUTRDialog.dismiss();
            if (loader != null && loader.isShowing())
                loader.dismiss();
        } catch (RuntimeException re) {
            re.printStackTrace();
            if(alertUTRDialog!=null && alertUTRDialog.isShowing())
                alertUTRDialog.dismiss();
            if (loader != null && loader.isShowing())
                loader.dismiss();
        } catch (Exception e) {
            if(alertUTRDialog!=null && alertUTRDialog.isShowing())
                alertUTRDialog.dismiss();
            if (loader != null && loader.isShowing())
                loader.dismiss();
        }

    }

    private String createUTRPatternStr(int midlength) {
        String hideStr="";
        for(int pos=0; pos<midlength; pos++){
            hideStr=hideStr+"*";
        }
        return hideStr;
    }

    public void update(SettlementAccountData mItem) {

        startActivityForResult(new Intent(this, AddUpdateSettlementAccountActivity.class)
                .putExtra("SettlementAccountData", mItem)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP), INTENT_REQUEST_CODE_UPDATE);
    }


    public void setDefaultAccount(int updatedId) {
        try {
            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);

            UtilMethods.INSTANCE.SetDefaultSettlementAccountApi(mActivity, mLoginDataResponse,updatedId,loader, new UtilMethods.ApiResponseCallBack() {
                @Override
                public void onSuccess(Object object) {
                    if (loader != null && loader.isShowing()) {
                        loader.dismiss();
                    }
                    if(object!=null && object instanceof BasicResponse)
                    {
                        BasicResponse response=(BasicResponse)object;
                        if(response.getData()!=null){
                            if (response.getData().getStatuscode() == 1) {
                                UtilMethods.INSTANCE.Successful(mActivity,response.getData().getMsg()+"");
                                hitApi();
                            }else {
                                UtilMethods.INSTANCE.Error(mActivity, response.getData().getMsg() + "");
                            }
                        }
                        else {
                            UtilMethods.INSTANCE.Successful(mActivity, response.getMsg() + "");
                            hitApi();
                        }
                    }
                }

                @Override
                public void onError(int error) {
                    if (loader != null && loader.isShowing()) {
                        loader.dismiss();
                    }

                }
            });

        }
        catch (Exception ex)
        {
            if (loader != null && loader.isShowing()) {
                loader.dismiss();
            }
            UtilMethods.INSTANCE.Error(mActivity, ex.getMessage() + "");
        }
    }

    public void confirmationDialog(SettlementAccountData mData) {

        if (alertDeleteDialog != null && alertDeleteDialog.isShowing()) {
            return;
        }
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_beneficiary_delete_confirm, null);

        ((TextView) view.findViewById(R.id.message)).setText(getString(R.string.confirmation_delete_account));
        ((TextView) view.findViewById(R.id.beneName)).setText(mData.getAccountHolder());
        ((TextView) view.findViewById(R.id.beneAccountNumber)).setText(mData.getAccountNumber());
        ((TextView) view.findViewById(R.id.beneBank)).setText(mData.getBankName());
        ((TextView) view.findViewById(R.id.beneIFSC)).setText(mData.getIfsc());
        alertDeleteDialog = new Dialog(mActivity, R.style.alert_dialog_light);
        alertDeleteDialog.setCancelable(false);
        alertDeleteDialog.setContentView(view);
        alertDeleteDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.findViewById(R.id.cancel).setOnClickListener(v -> alertDeleteDialog.dismiss());
        view.findViewById(R.id.ok).setOnClickListener(v -> {
            alertDeleteDialog.dismiss();
            if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {
                loader.show();
                loader.setCancelable(false);
                loader.setCanceledOnTouchOutside(false);
                deleteAccount(mData.getId());

            } else {
                UtilMethods.INSTANCE.NetworkError(this);
            }
        });
        alertDeleteDialog.show();
    }

    public void deleteAccount(int updatedId) {
        try {
            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);

            UtilMethods.INSTANCE.DeleteSettlementAccountApi(mActivity, mLoginDataResponse,updatedId,loader, new UtilMethods.ApiResponseCallBack() {
                @Override
                public void onSuccess(Object object) {
                    if (loader != null && loader.isShowing()) {
                        loader.dismiss();
                    }
                    if(object!=null && object instanceof BasicResponse)
                    {
                        BasicResponse response=(BasicResponse)object;
                        if(response.getData()!=null){
                            if (response.getData().getStatuscode() ==1) {
                                UtilMethods.INSTANCE.Successful(mActivity,response.getData().getMsg()+"");
                                hitApi();
                            }else {
                                UtilMethods.INSTANCE.Error(mActivity, response.getData().getMsg() + "");
                            }
                        }
                        else {
                            UtilMethods.INSTANCE.Successful(mActivity, response.getMsg() + "");
                            hitApi();
                        }
                    }
                }

                @Override
                public void onError(int error) {
                    if (loader != null && loader.isShowing()) {
                        loader.dismiss();
                    }

                }
            });

        }
        catch (Exception ex)
        {
            if (loader != null && loader.isShowing()) {
                loader.dismiss();
            }
            UtilMethods.INSTANCE.Error(mActivity, ex.getMessage() + "");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case INTENT_REQUEST_CODE_UPDATE:
                if(resultCode==RESULT_OK){
                    setResult(RESULT_OK);
                    hitApi();
                }
                break;
        }
    }



}