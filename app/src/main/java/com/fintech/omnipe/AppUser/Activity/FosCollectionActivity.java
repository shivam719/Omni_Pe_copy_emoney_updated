package com.fintech.omnipe.AppUser.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.fintech.omnipe.Auth.dto.LoginResponse;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.UtilMethods;
import com.fintech.omnipe.usefull.CustomLoader;
import com.google.gson.Gson;

public class FosCollectionActivity extends AppCompatActivity {

    private AppCompatTextView cashCollectionTv;
    private AppCompatTextView bankCollectionTv;
    private LinearLayout bankView;
    private RelativeLayout bankSelect_ll;
    private TextView bankTv;
    private AppCompatEditText amountEt;
    private AppCompatEditText remarksEt;
    private AppCompatTextView bankUtr_tv;
    private AppCompatEditText bankUtrEt;
    private AppCompatTextView cancelBtn;
    private AppCompatTextView fundCollectBtn;
    private AppCompatImageView closeIv;


    String bankId;
    private int INTENT_SELECT_BANK = 4343;
    private CustomLoader loader;
    private LoginResponse mLoginDataResponse;
    String userListName;
    // String Uid;
    String mobileNumber = " ";
    String bankName;
    int userListid;
    boolean isBank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_distributor_collection_from_fos);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        mLoginDataResponse = new Gson().fromJson(UtilMethods.INSTANCE.getLoginPref(this), LoginResponse.class);
        Bundle bundle1 = getIntent().getExtras();
        userListName = bundle1.getString("outletName");
        userListid = bundle1.getInt("userID");
        mobileNumber = bundle1.getString("mobile");
        cashCollectionTv = (AppCompatTextView) findViewById(R.id.cashCollectionTv);
        bankCollectionTv = (AppCompatTextView) findViewById(R.id.bankCollectionTv);
        bankView = (LinearLayout) findViewById(R.id.bankView);
        bankSelect_ll = (RelativeLayout) findViewById(R.id.bankSelect_ll);
        bankTv = (TextView) findViewById(R.id.bankTv);
        amountEt = (AppCompatEditText) findViewById(R.id.amountEt);
        remarksEt = (AppCompatEditText) findViewById(R.id.remarksEt);
        bankUtr_tv = (AppCompatTextView) findViewById(R.id.bankUtr_tv);
        bankUtrEt = (AppCompatEditText) findViewById(R.id.bankUtrEt);
        cancelBtn = (AppCompatTextView) findViewById(R.id.cancelBtn);
        fundCollectBtn = (AppCompatTextView) findViewById(R.id.fundCollectBtn);
        closeIv = (AppCompatImageView) findViewById(R.id.closeIv);

        closeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        cashCollectionTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bankUtr_tv.setVisibility(View.GONE);
                bankUtrEt.setVisibility(View.GONE);
                bankView.setVisibility(View.GONE);
                cashCollectionTv.setBackgroundResource(R.drawable.rounded_light_green);
                bankCollectionTv.setBackgroundResource(0);
                bankCollectionTv.setTextColor(getResources().getColor(R.color.lightDarkGreen));
                cashCollectionTv.setTextColor(getResources().getColor(R.color.white));
                isBank=false;

            }
        });

        bankCollectionTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bankUtr_tv.setVisibility(View.VISIBLE);
                bankUtrEt.setVisibility(View.VISIBLE);
                bankView.setVisibility(View.VISIBLE);
                bankCollectionTv.setBackgroundResource(R.drawable.rounded_light_green);
                cashCollectionTv.setBackgroundResource(0);
                cashCollectionTv.setTextColor(getResources().getColor(R.color.lightDarkGreen));
                bankCollectionTv.setTextColor(getResources().getColor(R.color.white));
                isBank=true;
            }
        });


        bankSelect_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent bankIntent = new Intent(FosCollectionActivity.this, FosCollectionBankListActivity.class);
                bankIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivityForResult(bankIntent, INTENT_SELECT_BANK);
            }
        });


        fundCollectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (bankView.getVisibility() == View.VISIBLE && bankTv.getText().toString().isEmpty()) {
                    bankTv.setError("This Field Is Empty");
                    bankTv.requestFocus();
                } else if (bankView.getVisibility() == View.VISIBLE && bankUtrEt.getText().toString().isEmpty()) {
                    bankUtrEt.setError("This Field Is Empty");
                    bankUtrEt.requestFocus();
                } else if (amountEt.getText().toString().isEmpty()) {
                    amountEt.setError("This Field Is Empty");
                    amountEt.requestFocus();
                } else if (remarksEt.getText().toString().isEmpty()) {
                    remarksEt.setError("Fill valid Remark");
                    remarksEt.requestFocus();
                } else if (!remarksEt.getText().toString().matches("[a-zA-Z.? ]*")) {
                    remarksEt.setError("Fill valid Remark");
                    remarksEt.requestFocus();
                } else {

                    UtilMethods.INSTANCE.ASPayCollect(FosCollectionActivity.this, userListid,
                            remarksEt.getText().toString(), amountEt.getText().toString(),
                            userListName, loader, isBank ? "Bank" : "Cash", bankUtrEt.getText().toString(), bankTv.getText().toString(),
                            mLoginDataResponse,
                            new UtilMethods.ApiCallBack() {
                                @Override
                                public void onSucess(Object object) {

                                    setResult(RESULT_OK);
                                }
                            });


                }

            }


        });


        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == INTENT_SELECT_BANK && resultCode == RESULT_OK) {
            bankId = String.valueOf(data.getExtras().getInt("bankId"));
            bankName = data.getExtras().getString("bankName");
            bankTv.setText("" + bankName);

        }
    }


}
