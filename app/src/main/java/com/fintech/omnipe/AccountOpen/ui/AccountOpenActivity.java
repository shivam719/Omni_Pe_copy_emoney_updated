package com.fintech.omnipe.AccountOpen.ui;

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


import com.fintech.omnipe.AccountOpen.adapter.AccountOpenListAdapter;
import com.fintech.omnipe.AccountOpen.dto.AccountOpData;
import com.fintech.omnipe.AccountOpen.dto.AccountOpenListResponse;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.UtilMethods;
import com.fintech.omnipe.usefull.CustomLoader;
import com.google.gson.Gson;

import java.util.ArrayList;


public class AccountOpenActivity extends AppCompatActivity {

    RecyclerView recycler_view;
    TextView noData;
    AccountOpenListAdapter mAdapter;
    ArrayList<AccountOpData> operator=new ArrayList<>();
    private CustomLoader loader;
    EditText search_all;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_account_open);

        Toolbar toolbar =  findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_icon);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getIntent().getStringExtra("SERVICE_NAME"));
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        search_all=findViewById(R.id.search_all);
        findViewById(R.id.clearIcon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_all.setText("");
            }
        });


        recycler_view = findViewById(R.id.recycler_view);
        mAdapter = new AccountOpenListAdapter(operator, this);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        recycler_view.setAdapter(mAdapter);
        noData = findViewById(R.id.noData);

        getOperatorList();


        search_all.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String newText = s.toString().toLowerCase();
                ArrayList<AccountOpData> newlist = new ArrayList<>();
                for (AccountOpData op : operator) {

                    if ((op.getName()+"").toLowerCase().contains(newText)||(op.getContent()+"").toLowerCase().contains(newText)) {
                        newlist.add(op);
                    }
                }
                mAdapter.filter(newlist);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void getOperatorList() {

        AccountOpenListResponse mAccountOpenListResponse = new Gson().fromJson(UtilMethods.INSTANCE.getAccountOpenList(this), AccountOpenListResponse.class);
        if (mAccountOpenListResponse != null && mAccountOpenListResponse.getAccountOpeningDeatils() != null &&
                mAccountOpenListResponse.getAccountOpeningDeatils().size() > 0) {
            operator.clear();
            operator.addAll(mAccountOpenListResponse.getAccountOpeningDeatils());
            if (operator != null && operator.size() > 0) {
                noData.setVisibility(View.GONE);
                mAdapter.notifyDataSetChanged();

            } else {
                noData.setVisibility(View.VISIBLE);
            }
        }else {
            UtilMethods.INSTANCE.GetAccountOpenlist(this,getIntent().getIntExtra("SERVICE_ID",0),loader,new UtilMethods.ApiCallBack() {
                        @Override
                        public void onSucess(Object object) {
                            AccountOpenListResponse mAccountOpenListResponse = (AccountOpenListResponse) object;
                            if (mAccountOpenListResponse != null && mAccountOpenListResponse.getAccountOpeningDeatils() != null && mAccountOpenListResponse.getAccountOpeningDeatils().size() > 0) {
                                operator.clear();
                                operator.addAll(mAccountOpenListResponse.getAccountOpeningDeatils());
                                if (operator != null && operator.size() > 0) {
                                    noData.setVisibility(View.GONE);
                                    mAdapter.notifyDataSetChanged();

                                } else {
                                    noData.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    }
            );
        }

    }

}

