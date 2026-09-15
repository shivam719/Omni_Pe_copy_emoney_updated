package com.fintech.omnipe.DMTNew.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fintech.omnipe.Api.Response.BenisObject;
import com.fintech.omnipe.Api.Response.RechargeReportResponse;
import com.fintech.omnipe.DMTNew.adapter.BeneficiaryAdapterNew;
import com.fintech.omnipe.DMTNew.dto.GetBeneficiaryResponse;
import com.fintech.omnipe.DMTNew.networkAPI.UtilsMethodDMTNew;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.ApplicationConstant;
import com.fintech.omnipe.Util.GetLocation;
import com.fintech.omnipe.Util.UtilMethods;
import com.fintech.omnipe.usefull.CustomLoader;

import java.util.ArrayList;

public class BeneficiaryListScreenNew extends AppCompatActivity {

    RecyclerView recycler_view;
    TextView noData;

    RechargeReportResponse beneResponse;

    ArrayList<BenisObject> operator;
    private TextView tvView;
    private String sidIntent;
    private int oidIntent;
    private CustomLoader loader;
    private String senderNumber;
    private GetLocation mGetLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_beneficiary_list_screen);
        oidIntent = getIntent().getIntExtra("OID", 0);
        sidIntent = getIntent().getStringExtra("SID");
        senderNumber = getIntent().getStringExtra("SenderNumber");

        if (senderNumber == null) {
            SharedPreferences prefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, Context.MODE_PRIVATE);
            senderNumber = prefs.getString(ApplicationConstant.INSTANCE.senderNumberPref, "");
        }
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        mGetLocation = new GetLocation(this, loader);
        GetId();
    }

    private void GetId() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Beneficiary List");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recycler_view = findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        noData = findViewById(R.id.noData);

        getBeneficiaryList();


    }

    private void getBeneficiaryListWithLocation() {
        if (UtilMethods.getLattitude != 0 && UtilMethods.getLongitude != 0) {
            getBeneficiaryList();
        } else {
            if (mGetLocation != null) {
                mGetLocation.startLocationUpdates((lattitude, longitude) -> {
                    UtilMethods.getLattitude = lattitude;
                    UtilMethods.getLongitude = longitude;
                    getBeneficiaryList();
                });
            } else {
                mGetLocation = new GetLocation(this, loader);
                mGetLocation.startLocationUpdates((lattitude, longitude) -> {
                    UtilMethods.getLattitude = lattitude;
                    UtilMethods.getLongitude = longitude;
                    getBeneficiaryList();
                });
            }
        }
    }

    public void getBeneficiaryList() {

        if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {
            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);
            UtilsMethodDMTNew.INSTANCE.getBeneficiary(this, oidIntent, senderNumber, sidIntent, loader, mGetLocation, new UtilsMethodDMTNew.ApiCallBack() {
                @Override
                public void onSuccess(Object object) {
                    GetBeneficiaryResponse beneResponse = (GetBeneficiaryResponse) object;
                    if (beneResponse.getData() != null && beneResponse.getData().size() > 0) {
                        operator = beneResponse.getData();
                        recycler_view.setVisibility(View.VISIBLE);
                        if (operator != null && operator.size() > 0) {
                            noData.setVisibility(View.GONE);
                            recycler_view.setAdapter(new BeneficiaryAdapterNew(operator, BeneficiaryListScreenNew.this, oidIntent, sidIntent, senderNumber, loader, mGetLocation));
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
            UtilMethods.INSTANCE.NetworkError(this);
        }


        /*SharedPreferences prefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String response = prefs.getString(ApplicationConstant.INSTANCE.beneficiaryListPref, "");
        Gson gson = new Gson();
        beneResponse = gson.fromJson(response, RechargeReportResponse.class);
        if (beneResponse.getBenis() != null && beneResponse.getBenis().size() > 0) {
            operator = beneResponse.getBenis();
            recycler_view.setVisibility(View.VISIBLE);
        } else {
            recycler_view.setVisibility(View.GONE);
            UtilMethods.INSTANCE.Error(this, "No Beneficiary found ! please Add Beneficiary");
        }*/

    }

    /*@Subscribe
    public void onActivityActivityMessage(ActivityActivityMessage activityFragmentMessage) {
        if (activityFragmentMessage.getMessage().equalsIgnoreCase("transferDoneDialog")) {
            SharedPreferences prefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
            String senderNumber = prefs.getString(ApplicationConstant.INSTANCE.senderNumberPref, null);

            if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {

//                UtilMethods.INSTANCE.GetSender(this, senderNumber, null);

            } else {
                UtilMethods.INSTANCE.NetworkError(this);
            }
            finish();
        } else if (activityFragmentMessage.getFrom().equalsIgnoreCase("beneDeleted")) {
            onBackPressed();
        }
    }*/

    /*@Override
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
*/
    public void refresh() {
        getBeneficiaryListWithLocation();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mGetLocation != null) {
            mGetLocation.onActivityResult(requestCode, resultCode, data);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (mGetLocation != null) {
            mGetLocation.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onPause() {
        if (mGetLocation != null) {
            mGetLocation.onPause();
        }
        super.onPause();
    }
}
