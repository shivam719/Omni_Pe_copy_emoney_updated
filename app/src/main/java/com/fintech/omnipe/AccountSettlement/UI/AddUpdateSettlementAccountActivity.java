package com.fintech.omnipe.AccountSettlement.UI;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.fintech.omnipe.AccountSettlement.API.SettlementAccountData;
import com.fintech.omnipe.Api.Object.BankListObject;
import com.fintech.omnipe.Api.Response.BankListResponse;
import com.fintech.omnipe.Api.Response.BasicResponse;
import com.fintech.omnipe.Auth.dto.LoginResponse;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.UtilMethods;
import com.fintech.omnipe.usefull.CustomLoader;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddUpdateSettlementAccountActivity extends AppCompatActivity implements View.OnClickListener{

    private Toolbar toolbar;
    private TextInputLayout til_bankName,til_bankAcctNo,til_bankAcctName,til_bankBranch,til_bankIfsc;
    private TextInputEditText AccountNumberEt,AccountNameEt,branchNameEt,IFSCEt;
    private MaterialAutoCompleteTextView spin_Bank;
    private MaterialButton bankUpdateBtn;
    private AddUpdateSettlementAccountActivity activity;
    private ArrayAdapter<String> bankAdapter;
    private BankListResponse bankListResponse;
    private String[] arrayBank;
    private Map<String, BankListObject> hMapbankId = new HashMap<>();
    private String selectedBank="";
    private int bankId,updatedId;
    private CustomLoader loader;
    private SettlementAccountData mSettlementAccountData;
    private LoginResponse mLoginDataResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_update_bank);
        activity=this;
        mLoginDataResponse = new Gson().fromJson(UtilMethods.INSTANCE.getLoginPref(activity), LoginResponse.class);
        getIDS();

        if(UtilMethods.INSTANCE.getBankList(activity)!=null && UtilMethods.INSTANCE.getBankList(activity).length()>0)
        {
             bankListResponse = new Gson().fromJson(UtilMethods.INSTANCE.getBankList(activity), BankListResponse.class);
            getBankData(bankListResponse);
        }
        HitApi();

        setUserBankData();

        spin_Bank.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedBank=spin_Bank.getText().toString();
                if(selectedBank!=null && !selectedBank.isEmpty() && !selectedBank.equalsIgnoreCase("Choose Bank"))
                {
                    BankListObject listObject=hMapbankId.get(selectedBank);
                    bankId= Integer.parseInt(listObject.getId());
                    selectedBank=spin_Bank.getText().toString().trim();
                    til_bankName.setErrorEnabled(false);
                    IFSCEt.setText(""+listObject.getIfsc());

                }
                else
                {
                     IFSCEt.setText("");
                    selectedBank="";
                }
            }
        });

        bankUpdateBtn.setOnClickListener(this);

    }



    private void getIDS() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitle("Add Account details");
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
            toolbar.setTitle("Update Account details");
            mSettlementAccountData = (SettlementAccountData) getIntent().getSerializableExtra("SettlementAccountData");
        }
        if (mSettlementAccountData != null) {

            updatedId = mSettlementAccountData.getId();
            bankId = mSettlementAccountData.getBankID();

            if (mSettlementAccountData.getBankName() != null && !mSettlementAccountData.getBankName().isEmpty()) {
                selectedBank=mSettlementAccountData.getBankName();
                spin_Bank.setText(selectedBank,false);


            }
            if (mSettlementAccountData.getAccountNumber() != null && !mSettlementAccountData.getAccountNumber().isEmpty()) {
                AccountNumberEt.setText(mSettlementAccountData.getAccountNumber());
            }
            if (mSettlementAccountData.getAccountHolder() != null && !mSettlementAccountData.getAccountHolder().isEmpty()) {
                AccountNameEt.setText(mSettlementAccountData.getAccountHolder());
            }
            /*if (mSettlementAccountData.getBranchName() != null && !mSettlementAccountData.getUserInfo().getBranchName().isEmpty()) {
                branchNameEt.setText(mSettlementAccountData.getUserInfo().getBranchName());
            }*/

            if (mSettlementAccountData.getIfsc() != null && !mSettlementAccountData.getIfsc().isEmpty()) {
                IFSCEt.setText(mSettlementAccountData.getIfsc());
            }
        }
    }

    private void getBankData(BankListResponse bankListResponse) {
        try {

            final ArrayList<BankListObject> Bank=bankListResponse.getBankMasters();
            arrayBank=new String[Bank.size()];
            //arrayBank[0]="Choose Bank";

            for (int i = 0; i < Bank.size(); i++) {
                arrayBank[i]=Bank.get(i).getBankName();
                hMapbankId.put(Bank.get(i).getBankName(), Bank.get(i));
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
    public void onClick(View v) {
        int itemId=v.getId();
        switch (itemId)
        {
            case R.id.btn_bankUpdate:
                if(updateBank()){
                    til_bankName.setErrorEnabled(false);
                    til_bankAcctNo.setErrorEnabled(false);
                    til_bankAcctName.setErrorEnabled(false);
                    til_bankIfsc.setErrorEnabled(false);
                    updateBankApi();
                }
                break;
        }
    }

    private boolean updateBank() {

        if (selectedBank==null ) {
            UtilMethods.INSTANCE.Error(activity,"Please Choose Bank");
            return false;
        }
        else if (selectedBank.length()==0) {
            UtilMethods.INSTANCE.Error(activity,"Please Choose Bank");
            return false;
        }
        else if (AccountNumberEt.getText().toString().isEmpty()) {
            til_bankAcctNo.setError(getString(R.string.err_empty_field));
            AccountNumberEt.requestFocus();
            return false;
        } else if (AccountNameEt.getText().toString().isEmpty()) {
            til_bankAcctName.setError(getString(R.string.err_empty_field));
            AccountNameEt.requestFocus();
            return false;


        }/*else if (branchNameEt.getText().toString().isEmpty()) {
            til_bankBranch.setError(getString(R.string.err_empty_field));
            branchNameEt.requestFocus();
            return false;
        }*/
        else if (IFSCEt.getText().toString().isEmpty()) {
            til_bankIfsc.setError(getString(R.string.err_empty_field));
            IFSCEt.requestFocus();
            return false;
        }
        return true;
    }

    private void updateBankApi() {

        try {
            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);

            UtilMethods.INSTANCE.UpdateSettlementAccountApi(activity, mLoginDataResponse,AccountNameEt.getText().toString().trim(), AccountNumberEt.getText().toString().trim(),selectedBank,IFSCEt.getText().toString().trim(),bankId,updatedId,loader, new UtilMethods.ApiResponseCallBack() {
                @Override
                public void onSuccess(Object object) {
                    if(object!=null && object instanceof BasicResponse)
                    {
                        BasicResponse response=(BasicResponse)object;
                        if(response.getData()!=null){
                            if (response.getData().getStatuscode() == 1) {
                                UtilMethods.INSTANCE.Successfulok(response.getData().getMsg() + "",activity);
                                setResult(RESULT_OK);
                            }else {
                                UtilMethods.INSTANCE.Error(activity, response.getData().getMsg() + "");

                            }
                        }
                        else {
                            UtilMethods.INSTANCE.Successful(activity, response.getMsg() + "");
                            setResult(RESULT_OK);
                        }
                    }
                }

                @Override
                public void onError(int error) {

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