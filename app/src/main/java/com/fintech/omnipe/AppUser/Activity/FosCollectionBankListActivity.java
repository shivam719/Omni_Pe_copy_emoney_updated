package com.fintech.omnipe.AppUser.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fintech.omnipe.Activities.Adapter.BankListScreenAdapter;
import com.fintech.omnipe.Api.Object.BankListObject;
import com.fintech.omnipe.Api.Response.BankListResponse;
import com.fintech.omnipe.Auth.dto.LoginResponse;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.UtilMethods;
import com.fintech.omnipe.usefull.CustomLoader;
import com.google.gson.Gson;

import java.util.ArrayList;

public class FosCollectionBankListActivity extends AppCompatActivity {
    RecyclerView recycler_view;
    View noDataView;
    BankListScreenAdapter mAdapter;
    ArrayList<BankListObject> mBankListObjects = new ArrayList<>();
    ArrayList<BankListObject> mSearchBankListObjects = new ArrayList<>();
    EditText search_all;
    private CustomLoader loader;
    private LoginResponse mLoginDataResponse;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_fos_bank_list_screen);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitle("Bank List");
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);

        mLoginDataResponse = new Gson().fromJson(UtilMethods.INSTANCE.getLoginPref(this), LoginResponse.class);

        search_all = findViewById(R.id.search_all);
        findViewById(R.id.clearIcon).setOnClickListener(v -> search_all.setText(""));


        recycler_view = findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new BankListScreenAdapter(mSearchBankListObjects, FosCollectionBankListActivity.this);
        recycler_view.setAdapter(mAdapter);

        noDataView = findViewById(R.id.noData);

        BankListResponse mBankListResponse = new Gson().fromJson(UtilMethods.INSTANCE.getFosBankList(this), BankListResponse.class);

        getOperatorList(mBankListResponse);


        search_all.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mSearchBankListObjects.clear();
                String newText = s.toString().trim().toLowerCase();
                if (newText.length() > 0) {
                    for (BankListObject op : mBankListObjects) {
                        if (op.getBankName().toLowerCase().contains(newText)) {
                            mSearchBankListObjects.add(op);
                        }
                    }
                } else {
                    mSearchBankListObjects.addAll(mBankListObjects);
                }
                if (mAdapter != null) {
                    mAdapter.filter(mSearchBankListObjects);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public void getOperatorList(BankListResponse bankListResponse) {
        if (bankListResponse != null && bankListResponse.getBanks() != null && bankListResponse.getBanks().size() > 0) {
            noDataView.setVisibility(View.GONE);
            mBankListObjects.clear();

            mSearchBankListObjects.clear();
            mBankListObjects.addAll(bankListResponse.getBanks());

            mSearchBankListObjects.addAll(mBankListObjects);
            mAdapter.notifyDataSetChanged();
        } else {

            HitApi();

        }

    }

    private void HitApi() {
        if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {
            loader.show();
            noDataView.setVisibility(View.GONE);
            UtilMethods.INSTANCE.GetASCollectBank(this, loader, mLoginDataResponse,
                    new UtilMethods.ApiCallBack() {
                        @Override
                        public void onSucess(Object object) {
                            BankListResponse mBankListResponse = (BankListResponse) object;
                            if (mBankListResponse != null && mBankListResponse.getBanks() != null && mBankListResponse.getBanks().size() > 0) {
                                getOperatorList((BankListResponse) object);
                            }
                        }

                    });

        } else {
            noDataView.setVisibility(View.GONE);
            UtilMethods.INSTANCE.NetworkError(this);
        }
    }

    public void ItemClick(BankListObject operator) {
        Intent clickIntent = new Intent();
        clickIntent.putExtra("bankName", operator.getBankName());
        clickIntent.putExtra("bankId", operator.getBankID());
        setResult(RESULT_OK, clickIntent);
        finish();

    }

}
