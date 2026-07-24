package com.fintech.omnipe.AppUser.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fintech.omnipe.AppUser.Adapter.VoucherEntryUserListAdapter;
import com.fintech.omnipe.AppUser.dto.AscReport;
import com.fintech.omnipe.Auth.dto.LoginResponse;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.UtilMethods;
import com.fintech.omnipe.usefull.CustomLoader;
import com.google.gson.Gson;


import java.util.ArrayList;

public class VoucherEntryUserListActivity extends AppCompatActivity {
    RecyclerView recycler_view;
    View noDataView;
    VoucherEntryUserListAdapter mAdapter;
    private ArrayList<AscReport> ascReportList = new ArrayList<>();
    EditText search_all;
    TextView bank_collection;
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
        toolbar.setTitle("User List");
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        ascReportList = (ArrayList<AscReport>) getIntent().getSerializableExtra("Data");
        mLoginDataResponse = new Gson().fromJson(UtilMethods.INSTANCE.getLoginPref(this), LoginResponse.class);

        search_all = findViewById(R.id.search_all);
        findViewById(R.id.clearIcon).setOnClickListener(v -> search_all.setText(""));


        recycler_view = findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));


        noDataView = findViewById(R.id.noData);



        getOperatorList();


        search_all.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (mAdapter != null) {
                    mAdapter.getFilter().filter(s);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
/*
        findViewById(R.id.retryBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HitApi();
            }
        });*/
    }

    public void getOperatorList() {
        if (ascReportList != null && ascReportList.size() > 0) {
            mAdapter = new VoucherEntryUserListAdapter(ascReportList, VoucherEntryUserListActivity.this);
            recycler_view.setAdapter(mAdapter);
            recycler_view.setVisibility(View.VISIBLE);
            noDataView.setVisibility(View.GONE);
            mAdapter.notifyDataSetChanged();
        } else {

            noDataView.setVisibility(View.VISIBLE);

        }

    }



    public void selectUser(AscReport operator) {
        Intent clickIntent = new Intent();
        clickIntent.putExtra("Data", operator);
        setResult(RESULT_OK, clickIntent);
        finish();
    }

}
