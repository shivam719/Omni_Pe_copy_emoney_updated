package com.fintech.omnipe.DMRPipe.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.fintech.omnipe.Api.Response.BenisObject;
import com.fintech.omnipe.Api.Response.RechargeReportResponse;
import com.fintech.omnipe.DMRPipe.Adapter.BeneficiaryAdapterNew;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.ActivityActivityMessage;
import com.fintech.omnipe.Util.ApplicationConstant;
import com.fintech.omnipe.Util.GlobalBus;
import com.fintech.omnipe.Util.UtilMethods;
import com.fintech.omnipe.usefull.CustomLoader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

public class BeneficiaryListScreenNew extends AppCompatActivity {

    RecyclerView recycler_view;
    TextView noData;

    RechargeReportResponse beneResponse;

    ArrayList<BenisObject> operator;
    private TextView tvView;
    private String oidIntent, sidIntent;
    private CustomLoader loader;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_beneficiary_list_screen);
        oidIntent = String.valueOf(getIntent().getIntExtra("OID",0));
        sidIntent = getIntent().getStringExtra("SID");
         prefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, Context.MODE_PRIVATE);
        GetId();
    }

    private void GetId() {
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Beneficiary List");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        noData = (TextView) findViewById(R.id.noData);

        getBeneficiaryList();


    }

    public void getBeneficiaryList() {

        if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {
            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);
            UtilMethods.INSTANCE.GetBeneficiaryNew(this, oidIntent, prefs.getString(ApplicationConstant.INSTANCE.senderNumberPref, ""), loader, new UtilMethods.ApiCallBack() {
                @Override
                public void onSucess(Object object) {
                    RechargeReportResponse beneResponse = (RechargeReportResponse) object;
                    if (beneResponse.getBeneficiaries() != null && beneResponse.getBeneficiaries().size() > 0) {
                        operator = beneResponse.getBeneficiaries();
                        recycler_view.setVisibility(View.VISIBLE);
                        if (operator != null && operator.size() > 0) {
                            noData.setVisibility(View.GONE);
                            recycler_view.setAdapter(new BeneficiaryAdapterNew(operator, BeneficiaryListScreenNew.this, oidIntent, sidIntent));
                        } else {
                            noData.setVisibility(View.VISIBLE);
                        }
                    } else {
                        recycler_view.setVisibility(View.GONE);
                        noData.setVisibility(View.VISIBLE);
                        UtilMethods.INSTANCE.Error(BeneficiaryListScreenNew.this, "No Beneficiary found ! please Add Beneficiary");
                    }
                }
            });


        } else {
            UtilMethods.INSTANCE.NetworkError(this,getString(R.string.err_msg_network_title),
                    getString(R.string.err_msg_network));
        }

    }

    @Subscribe
    public void onActivityActivityMessage(ActivityActivityMessage activityFragmentMessage) {
        if (activityFragmentMessage.getMessage().equalsIgnoreCase("transferDoneDialog")) {
            SharedPreferences prefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
            String senderNumber = prefs.getString(ApplicationConstant.INSTANCE.senderNumberPref, null);

            if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {

//                UtilMethods.INSTANCE.GetSender(this, senderNumber, null);

            } else {
                UtilMethods.INSTANCE.NetworkError(this,getString(R.string.err_msg_network_title),
                        getString(R.string.err_msg_network));
            }
            finish();
        } else if (activityFragmentMessage.getFrom().equalsIgnoreCase("beneDeleted")) {
            onBackPressed();
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
        // Unregister the registered event.
        GlobalBus.getBus().unregister(this);
    }

    public void refresh() {
        getBeneficiaryList();
    }
}
