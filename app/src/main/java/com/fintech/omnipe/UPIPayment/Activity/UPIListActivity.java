package com.fintech.omnipe.UPIPayment.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fintech.omnipe.Auth.dto.LoginResponse;
import com.fintech.omnipe.BuildConfig;
import com.fintech.omnipe.UPIPayment.Adapter.VPAListAdapter;
import com.fintech.omnipe.UPIPayment.dto.VPAListRequest;
import com.fintech.omnipe.UPIPayment.dto.VPAListResponse;
import com.fintech.omnipe.UPIPayment.dto.VPAList;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.ApiClient;
import com.fintech.omnipe.Util.ApplicationConstant;
import com.fintech.omnipe.Util.EndPointInterface;
import com.fintech.omnipe.Util.Senderobject;
import com.fintech.omnipe.Util.UtilMethods;
import com.fintech.omnipe.usefull.CustomLoader;
import com.google.gson.Gson;


import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

import retrofit2.Call;
import retrofit2.Callback;

public class UPIListActivity extends AppCompatActivity {

    private RecyclerView vpaListRecyclerView;
    public View noDataView,vpaListContainerView;
    private View clearIcon, noNetworkView, retryBtn;
    private EditText searchEt;
    private VPAListAdapter mVpaListAdapter;
    private int INTENT_ADD_VPA = 7453;
    private LoginResponse mLoginDataResponse;
    private String senderMobNum = "";
    private CustomLoader loader;
    private ArrayList<VPAList> vpaList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_vpalist);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        new Handler(Looper.getMainLooper()).post(() -> {
            findViewsId();
            new Handler(Looper.getMainLooper()).post(() -> {
                loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
                mLoginDataResponse = new Gson().fromJson(UtilMethods.INSTANCE.getLoginPref(this), LoginResponse.class);
                vpaList = getIntent().getParcelableArrayListExtra("VPAList");
                senderMobNum = getIntent().getStringExtra("SenderNum");
                if (vpaList != null && vpaList.size() > 0) {
                    mVpaListAdapter = new VPAListAdapter(vpaList, this);
                    vpaListRecyclerView.setAdapter(mVpaListAdapter);
                } else if (senderMobNum != null && !senderMobNum.isEmpty()) {
                    getVpaList(this, senderMobNum, true);
                }
            });
        });


    }

    void findViewsId() {


        clearIcon = findViewById(R.id.clearIcon);
        vpaListContainerView = findViewById(R.id.vpaListContainerView);
        noDataView = findViewById(R.id.noDataView);
        noNetworkView = findViewById(R.id.noNetworkView);
        searchEt = findViewById(R.id.searchEt);
        retryBtn = findViewById(R.id.retryBtn);
        vpaListRecyclerView = findViewById(R.id.vpaListRv);
        vpaListRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (mVpaListAdapter != null) {
                    mVpaListAdapter.getFilter().filter(charSequence);
                }
                if (charSequence.length() > 0) {
                    clearIcon.setVisibility(View.VISIBLE);


                } else {
                    clearIcon.setVisibility(View.GONE);


                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        clearIcon.setOnClickListener(view -> searchEt.setText(""));


        retryBtn.setOnClickListener(view -> {

            getVpaList(UPIListActivity.this, senderMobNum.trim(), true);

        });


    }


    public void getVpaList(final Activity context, String mobNum, boolean isShowBalance) {
        try {
            loader.show();


            VPAListRequest paymentReq = new VPAListRequest(new Senderobject(mobNum),
                    mLoginDataResponse.getData().getUserID() + "",
                    mLoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    UtilMethods.INSTANCE.getIMEI(this),
                    "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(this),
                    mLoginDataResponse.getData().getSession(),
                    mLoginDataResponse.getData().getSessionID());

            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<VPAListResponse> call = git.GetVPAListUPIPayment(paymentReq);

            call.enqueue(new Callback<VPAListResponse>() {

                @Override
                public void onResponse(Call<VPAListResponse> call, retrofit2.Response<VPAListResponse> response) {


                    if (response.isSuccessful()) {
                        if (response.body() != null) {

                            if (response.body().getStatuscode() == 1) {

                                if (response.body().getVpaList() != null && response.body().getVpaList().size() > 0) {
                                    loader.dismiss();
                                    vpaListContainerView.setVisibility(View.VISIBLE);
                                    noDataView.setVisibility(View.GONE);
                                    noNetworkView.setVisibility(View.GONE);
                                    mVpaListAdapter = new VPAListAdapter(response.body().getVpaList(), context);
                                    vpaListRecyclerView.setAdapter(mVpaListAdapter);

                                } else {
                                    loader.dismiss();
                                    noDataView.setVisibility(View.VISIBLE);
                                    noNetworkView.setVisibility(View.GONE);
                                    vpaListContainerView.setVisibility(View.GONE);
                                }
                            } else {
                                loader.dismiss();
                                noDataView.setVisibility(View.VISIBLE);
                                noNetworkView.setVisibility(View.GONE);
                                vpaListContainerView.setVisibility(View.GONE);
                                if (!response.body().isVersionValid()) {
                                    UtilMethods.INSTANCE.versionDialog(context);
                                } else {
                                    UtilMethods.INSTANCE.Error(context, response.body().getMsg() + "");
                                }
                            }

                        } else {
                            loader.dismiss();
                            noDataView.setVisibility(View.VISIBLE);
                            noNetworkView.setVisibility(View.GONE);
                            vpaListContainerView.setVisibility(View.GONE);
                            UtilMethods.INSTANCE.Error(context, "Something Error, Please try after some time");
                        }
                    } else {
                        loader.dismiss();
                        noDataView.setVisibility(View.VISIBLE);
                        noNetworkView.setVisibility(View.GONE);
                        vpaListContainerView.setVisibility(View.GONE);
                        UtilMethods.INSTANCE.apiErrorHandle(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<VPAListResponse> call, Throwable t) {

                    try {
                        loader.dismiss();
                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            noDataView.setVisibility(View.GONE);
                            vpaListContainerView.setVisibility(View.GONE);
                            noNetworkView.setVisibility(View.VISIBLE);
                            UtilMethods.INSTANCE.NetworkError(context);
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            noDataView.setVisibility(View.GONE);
                            vpaListContainerView.setVisibility(View.GONE);
                            noNetworkView.setVisibility(View.VISIBLE);
                            UtilMethods.INSTANCE.ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage() + "");
                        } else {
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                noDataView.setVisibility(View.VISIBLE);
                                noNetworkView.setVisibility(View.GONE);
                                vpaListContainerView.setVisibility(View.GONE);
                                UtilMethods.INSTANCE.ErrorWithTitle(context, "FATAL ERROR", t.getMessage() + "");
                            } else {
                                noDataView.setVisibility(View.VISIBLE);
                                noNetworkView.setVisibility(View.GONE);
                                vpaListContainerView.setVisibility(View.GONE);
                                UtilMethods.INSTANCE.Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        }

                    } catch (IllegalStateException ise) {
                        loader.dismiss();
                        UtilMethods.INSTANCE.Error(context, ise.getMessage());

                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            loader.dismiss();
            noDataView.setVisibility(View.VISIBLE);
            noNetworkView.setVisibility(View.GONE);
            vpaListContainerView.setVisibility(View.GONE);

        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void sendMoneyClick(VPAList operator) {
        startActivityForResult(new Intent(this, UPIPayActivity.class)
                .putExtra("VPAData", operator)
                .putExtra("SenderNum", operator.getSenderNo() != null && !operator.getSenderNo().isEmpty() ? operator.getSenderNo() : senderMobNum)
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), INTENT_ADD_VPA);
    }


}