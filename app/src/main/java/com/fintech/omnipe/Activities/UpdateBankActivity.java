package com.fintech.omnipe.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.fintech.omnipe.Api.Object.BankListObject;
import com.fintech.omnipe.Api.Request.EditUser;
import com.fintech.omnipe.Api.Request.UpdateBankRequest;
import com.fintech.omnipe.Api.Response.BankListResponse;
import com.fintech.omnipe.Api.Response.BasicResponse;
import com.fintech.omnipe.Auth.dto.LoginResponse;
import com.fintech.omnipe.BuildConfig;
import com.fintech.omnipe.Fragments.dto.GetUserResponse;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.ApplicationConstant;
import com.fintech.omnipe.Util.FragmentActivityMessage;
import com.fintech.omnipe.Util.GlobalBus;
import com.fintech.omnipe.Util.UtilMethods;
import com.fintech.omnipe.usefull.CustomLoader;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UpdateBankActivity extends AppCompatActivity implements View.OnClickListener{

    private TextInputLayout til_bankName,til_bankAcctNo,til_bankAcctName,til_bankBranch,til_bankIfsc;
    private TextInputEditText AccountNumberEt,AccountNameEt,branchNameEt,IFSCEt;
    private MaterialAutoCompleteTextView spin_Bank;
    private MaterialButton bankUpdateBtn;
    private UpdateBankActivity activity;
    private ArrayAdapter<String> bankAdapter;
    private  BankListResponse bankListResponse;
    private String[] arrayBank;
    private Map<String, Integer> hMapbankId = new HashMap<>();
    private String selectedBank=" ";
    private int bankId;
    private CustomLoader loader;
    private GetUserResponse mGetUserResponse;
    private UpdateBankRequest updateBankRequest;
    private EditUser editUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_update_bank);
        activity=this;
        UtilMethods.INSTANCE.setAppLogoIconUI(this,findViewById(R.id.logoIv));
        getIDS();


        if(UtilMethods.INSTANCE.getBankList(activity)!=null && UtilMethods.INSTANCE.getBankList(activity).length()>0)
        {
             bankListResponse = new Gson().fromJson(UtilMethods.INSTANCE.getBankList(activity), BankListResponse.class);
            getBankData(bankListResponse);
        }
        else
        {
            HitApi();
        }

        setUserBankData();

        spin_Bank.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedBank=spin_Bank.getText().toString();
                if(selectedBank!=null && !selectedBank.isEmpty() && !selectedBank.equalsIgnoreCase("Choose Bank"))
                {
                    bankId=hMapbankId.get(selectedBank);
                    selectedBank=spin_Bank.getText().toString().trim();
                    til_bankName.setErrorEnabled(false);

                }
                else
                {
                    // IFSCEt.setText("");
                    selectedBank="";
                }
            }
        });

        bankUpdateBtn.setOnClickListener(this);

    }



    private void getIDS() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitle("Update Bank");
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        til_bankName=findViewById(R.id.til_bankName);
        til_bankAcctNo=findViewById(R.id.til_bankAcctNo);
        til_bankAcctName=findViewById(R.id.til_bankAcctName);
        til_bankBranch=findViewById(R.id.til_bankBranch);
        til_bankIfsc=findViewById(R.id.til_bankIfsc);

        AccountNumberEt=findViewById(R.id.et_bankAcctNo);
        AccountNameEt=findViewById(R.id.et_bankAcctName);
        branchNameEt=findViewById(R.id.et_bankBranch);
        IFSCEt=findViewById(R.id.et_bankIfsc);
        spin_Bank=findViewById(R.id.et_bankName);

        bankUpdateBtn=findViewById(R.id.btn_bankUpdate);

    }

    private void setUserBankData() {
        if(getIntent()!=null)
        {
            mGetUserResponse = (GetUserResponse) getIntent().getSerializableExtra("UserData");
        }
        if (mGetUserResponse != null && mGetUserResponse.getUserInfo() != null) {
            /*------------setting Bank Value---------*/

            if (mGetUserResponse.getUserInfo().getBankName() != null && !mGetUserResponse.getUserInfo().getBankName().isEmpty()) {
                selectedBank=mGetUserResponse.getUserInfo().getBankName();
                spin_Bank.setText(selectedBank,false);
                Log.e("BankName" ,mGetUserResponse.getUserInfo().getBankName());

            }
            if (mGetUserResponse.getUserInfo().getAccountNumber() != null && !mGetUserResponse.getUserInfo().getAccountNumber().isEmpty()) {
                AccountNumberEt.setText(mGetUserResponse.getUserInfo().getAccountNumber());
            }
            if (mGetUserResponse.getUserInfo().getAccountName() != null && !mGetUserResponse.getUserInfo().getAccountName().isEmpty()) {
                AccountNameEt.setText(mGetUserResponse.getUserInfo().getAccountName());
            }
            if (mGetUserResponse.getUserInfo().getBranchName() != null && !mGetUserResponse.getUserInfo().getBranchName().isEmpty()) {
                branchNameEt.setText(mGetUserResponse.getUserInfo().getBranchName());
            }

            if (mGetUserResponse.getUserInfo().getIfsc() != null && !mGetUserResponse.getUserInfo().getIfsc().isEmpty()) {
                IFSCEt.setText(mGetUserResponse.getUserInfo().getIfsc());
            }
        }
    }

    private void getBankData(BankListResponse bankListResponse) {
        try {

            final ArrayList<BankListObject> Bank=bankListResponse.getBankMasters();
            arrayBank=new String[Bank.size()+1];
            arrayBank[0]="Choose Bank";

            for (int i = 0; i < Bank.size(); i++) {
                arrayBank[i+1]=Bank.get(i).getBankName();
                hMapbankId.put(Bank.get(i).getBankName(), Integer.valueOf(Bank.get(i).getId()));
            }

            bankAdapter = new ArrayAdapter<String>(activity, R.layout.text_input_spinner_item, arrayBank);
            bankAdapter.setDropDownViewResource(R.layout.text_input_spinner_item);
            spin_Bank.setAdapter(bankAdapter);

        }
        catch (Exception ex)
        {
            UtilMethods.INSTANCE.Error(this,"Something went wrong!!");
        }
    }

    private void HitApi() {
        if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {
            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);
            UtilMethods.INSTANCE.GetBanklist(this, loader, new UtilMethods.ApiCallBack() {
                @Override
                public void onSucess(Object object) {
                    if(object instanceof  BankListResponse){
                        bankListResponse=(BankListResponse)object;
                        getBankData(bankListResponse);
                    }

                }
            });
        } else {
            UtilMethods.INSTANCE.dialogOk(this, getResources().getString(R.string.network_error_title),
                    getResources().getString(R.string.network_error_message), 2);
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
    @Subscribe

    public void onActivityActivityMessage(FragmentActivityMessage activityFragmentMessage) {

    }

    @Override
    public void onClick(View v) {
        int itemId=v.getId();
        switch (itemId)
        {
            case R.id.btn_bankUpdate:
                updateBank();
                break;
        }
    }

    private void updateBank() {

        if (!spin_Bank.getText().toString().isEmpty() && spin_Bank.getText().toString().equalsIgnoreCase("Choose Bank")) {
            til_bankName.setError("Please Select Bank");
        }
        else if (AccountNumberEt.getText().toString().isEmpty()) {
            til_bankAcctNo.setError(getString(R.string.err_empty_field));
            AccountNumberEt.requestFocus();
        }  else if (AccountNameEt.getText().toString().isEmpty()) {
            til_bankAcctName.setError(getString(R.string.err_empty_field));
            AccountNameEt.requestFocus();

        }else if (branchNameEt.getText().toString().isEmpty()) {
            til_bankBranch.setError(getString(R.string.err_empty_field));
            branchNameEt.requestFocus();
        }
        else if (IFSCEt.getText().toString().isEmpty()) {
            til_bankIfsc.setError(getString(R.string.err_empty_field));
            IFSCEt.requestFocus();
        }else {
            til_bankName.setErrorEnabled(false);
            til_bankAcctNo.setErrorEnabled(false);
            til_bankAcctName.setErrorEnabled(false);
            til_bankBranch.setErrorEnabled(false);
            til_bankIfsc.setErrorEnabled(false);
            updateBankApi();

        }
    }

    private void updateBankApi() {

        try {
            String loginPref = UtilMethods.INSTANCE.getLoginPref(activity);
            LoginResponse LoginDataResponse = new Gson().fromJson(loginPref, LoginResponse.class);

            editUser = new EditUser(
                    selectedBank,
                    AccountNumberEt.getText().toString().trim(),
                    AccountNameEt.getText().toString().trim(),
                    branchNameEt.getText().toString().trim(),
                    IFSCEt.getText().toString().trim());

            updateBankRequest=new UpdateBankRequest(
                    LoginDataResponse.getData().getUserID() + "",
                    LoginDataResponse.getData().getLoginTypeID() + "",
                    ApplicationConstant.INSTANCE.APP_ID,
                    UtilMethods.INSTANCE.getIMEI(activity),
                    "",
                    BuildConfig.VERSION_NAME,
                    UtilMethods.INSTANCE.getSerialNo(activity),
                    LoginDataResponse.getData().getSessionID(),
                    LoginDataResponse.getData().getSession(),
                    editUser);

            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);

            UtilMethods.INSTANCE.updateBank(activity, updateBankRequest, loader, new UtilMethods.ApiCallBack() {
                @Override
                public void onSucess(Object object) {

                    if(object!=null)
                    {
                        BasicResponse response=(BasicResponse)object;
                        UtilMethods.INSTANCE.Successful(activity,  response.getMsg()+ "");
                    }
                }
            });

        }
        catch (Exception ex)
        {
            if (loader != null && loader.isShowing()) {
                loader.dismiss();
            }
            UtilMethods.INSTANCE.Error(activity, ex.getMessage() + "");
        }
    }
}