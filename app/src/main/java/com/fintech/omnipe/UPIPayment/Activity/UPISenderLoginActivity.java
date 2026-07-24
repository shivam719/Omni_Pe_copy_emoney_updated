package com.fintech.omnipe.UPIPayment.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fintech.omnipe.Activities.Adapter.BalanceTypeAdapter;
import com.fintech.omnipe.Api.Object.BalanceData;
import com.fintech.omnipe.Api.Object.BalanceType;
import com.fintech.omnipe.Api.Response.BalanceResponse;
import com.fintech.omnipe.Auth.dto.LoginResponse;
import com.fintech.omnipe.BuildConfig;
import com.fintech.omnipe.R;
import com.fintech.omnipe.UPIPayment.dto.VPAList;
import com.fintech.omnipe.UPIPayment.dto.VPAListResponse;
import com.fintech.omnipe.UPIPayment.dto.VPAListRequest;
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

public class UPISenderLoginActivity extends AppCompatActivity {

    private EditText senderNumberEt;
    private Toolbar toolbar;
    private TextView senderNumTv;
    private View senderLayout, upiLoginView,buttonAddUPIView, addVPAView, scanPayView, vpaListView;
    private RecyclerView balanceRecyclerView;
    private ArrayList<VPAList> vpaList=new ArrayList<>();
    private CustomLoader loader;
    private final Integer INTENT_ADD_VPA=10;
    private LoginResponse mLoginDataResponse;
    private BalanceResponse balanceCheckResponse;
    private ArrayList<BalanceType> mBalanceTypes = new ArrayList<>();
    private String senderMobNum = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_upi_sender_login);
        new Handler(Looper.getMainLooper()).post(() -> {

            getIds();
            new Handler(Looper.getMainLooper()).post(() -> {
                loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
                mLoginDataResponse = new Gson().fromJson(UtilMethods.INSTANCE.getLoginPref(this), LoginResponse.class);
                balanceCheckResponse =getIntent().getParcelableExtra("BalanceData");
                showBalanceData();

            });
        });


    }


    private void getIds() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitle("UPI Payment");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        upiLoginView = findViewById(R.id.senderNumberContainerView);
        senderNumTv = findViewById(R.id.sender_num);
        senderLayout = findViewById(R.id.senderLayoutView);
        addVPAView = findViewById(R.id.addVPAView);
        scanPayView = findViewById(R.id.scanPayView);
        vpaListView = findViewById(R.id.vpaListView);
        buttonAddUPIView = findViewById(R.id.buttonAddUPIView);

        balanceRecyclerView = findViewById(R.id.balanceRecyclerView);
        balanceRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        senderNumberEt=findViewById(R.id.senderNumberEt);


        senderNumberEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                /*if(s.length()<10){
                    upiPayNewBtn.setVisibility(View.VISIBLE);
                }
                else if (s.length() == 10) {
                    loader.show();
                    loader.setCancelable(false);
                    hitVPAListApi();
                }*/
            }

            @Override
            public void afterTextChanged(Editable editable) {
                senderMobNum = editable.toString().trim();
                if (senderMobNum.length() == 10) {
                    getVpaList(UPISenderLoginActivity.this, senderMobNum);
                }
            }
        });

        findViewById(R.id.dmr_logout).setOnClickListener(view -> {
            senderNumberEt.setText("");
            senderNumTv.setText("");
            senderMobNum = "";
            upiLoginView.setVisibility(View.VISIBLE);
            senderLayout.setVisibility(View.GONE);

        });

        addVPAView.setOnClickListener(view ->
                startActivityForResult(new Intent(this, UPIPayActivity.class)
                        .putExtra("SenderNum", senderMobNum)
                        .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), INTENT_ADD_VPA));

        scanPayView.setOnClickListener(view ->
                startActivityForResult(new Intent(this, QRScannerActivity.class)
                        .putExtra("SenderNum", senderMobNum)
                        .putExtra("FROM_SCANPAY", true)
                        .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), INTENT_ADD_VPA));

        vpaListView.setOnClickListener(view ->
                startActivityForResult(new Intent(this, UPIListActivity.class)
                        .putExtra("SenderNum", senderMobNum)
                        .putParcelableArrayListExtra("VPAList", vpaList)
                        .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), INTENT_ADD_VPA));



    }

    private void showBalanceData() {
        mBalanceTypes.clear();
        if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null) {

            BalanceData mBalanceData = balanceCheckResponse.getBalanceData();
            if (mBalanceData.isBalance() && mBalanceData.isBalanceFund()) {
                String walletName = "Prepaid Wallet";
                if (mBalanceData.getPrepaidWalletName() != null &&
                        !mBalanceData.getPrepaidWalletName().isEmpty()) {
                    walletName = mBalanceData.getPrepaidWalletName() + " Wallet";
                }
                mBalanceTypes.add(new BalanceType(walletName, mBalanceData.getBalance() + ""));
            }
            if (mBalanceData.isUBalance() && mBalanceData.isUBalanceFund()) {
                String walletName = "Utility Wallet";
                if (mBalanceData.getUtilityWalletName() != null &&
                        !mBalanceData.getUtilityWalletName().isEmpty()) {
                    walletName = mBalanceData.getUtilityWalletName() + " Wallet";
                }
                mBalanceTypes.add(new BalanceType(walletName, mBalanceData.getuBalance() + ""));

            }
            if (mBalanceData.isBBalance() && mBalanceData.isBBalanceFund()) {
                String walletName = "Bank Wallet";
                if (mBalanceData.getBankWalletName() != null &&
                        !mBalanceData.getBankWalletName().isEmpty()) {
                    walletName = mBalanceData.getBankWalletName() + " Wallet";
                }
                mBalanceTypes.add(new BalanceType(walletName, mBalanceData.getbBalance() + ""));
            }
            if (mBalanceData.isCBalance() && mBalanceData.isCBalanceFund()) {
                String walletName = "Card Wallet";
                if (mBalanceData.getCardWalletName() != null &&
                        !mBalanceData.getCardWalletName().isEmpty()) {
                    walletName = mBalanceData.getCardWalletName() + " Wallet";
                }
                mBalanceTypes.add(new BalanceType(walletName, mBalanceData.getcBalance() + ""));
            }
            if (mBalanceData.isIDBalance() && mBalanceData.isIDBalanceFund()) {
                String walletName = "Registration Wallet";
                if (mBalanceData.getRegIDWalletName() != null &&
                        !mBalanceData.getRegIDWalletName().isEmpty()) {
                    walletName = mBalanceData.getRegIDWalletName() + " Wallet";
                }
                mBalanceTypes.add(new BalanceType(walletName, mBalanceData.getIdBalnace() + ""));
            }

            if (mBalanceData.isPacakgeBalance() && mBalanceData.isPacakgeBalanceFund()) {
                String walletName = "Package Wallet";
                if (mBalanceData.getPackageWalletName() != null &&
                        !mBalanceData.getPackageWalletName().isEmpty()) {
                    walletName = mBalanceData.getPackageWalletName() + " Wallet";
                }
                mBalanceTypes.add(new BalanceType(walletName, mBalanceData.getPacakgeBalance() + ""));
            }

            if (mBalanceTypes != null && mBalanceTypes.size() > 0) {
                BalanceTypeAdapter mAdapter = new BalanceTypeAdapter(mBalanceTypes,this);
                balanceRecyclerView.setAdapter(mAdapter);
            }
        } else {
            SharedPreferences myPreferences = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
            String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.balancePref, "");
            balanceCheckResponse = new Gson().fromJson(balanceResponse, BalanceResponse.class);
            if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null) {
                showBalanceData();
            } else {


                UtilMethods.INSTANCE.BalancecheckNew(this, null,null,null, object -> {
                    balanceCheckResponse = (BalanceResponse) object;
                    if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null) {

                        showBalanceData();
                    }
                });
            }
        }

    }

    public void getVpaList(final Activity context, String mobNum) {
        try {
            loader.show();


            VPAListRequest paymentReq = new VPAListRequest(new Senderobject(mobNum),
                    mLoginDataResponse.getData().getUserID() + "",
                    mLoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    UtilMethods.INSTANCE.getIMEI(this),
                    "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(this), mLoginDataResponse.getData().getSessionID(),
                    mLoginDataResponse.getData().getSession());

            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<VPAListResponse> call = git.GetVPAListUPIPayment(paymentReq);

            call.enqueue(new Callback<VPAListResponse>() {

                @Override
                public void onResponse(Call<VPAListResponse> call, retrofit2.Response<VPAListResponse> response) {

                    loader.dismiss();
                    if (response.isSuccessful()) {
                        if (response.body() != null) {

                            if (response.body().getStatuscode() == 1) {

                                if (response.body().getVpaList() != null && response.body().getVpaList().size() > 0) {
                                    vpaList = response.body().getVpaList();
                                    senderNumTv.setText(mobNum);
                                    senderLayout.setVisibility(View.VISIBLE);
                                    buttonAddUPIView.setVisibility(View.VISIBLE);
                                    upiLoginView.setVisibility(View.GONE);

                                } else {
                                    senderNumTv.setText(mobNum);
                                    senderLayout.setVisibility(View.VISIBLE);
                                    buttonAddUPIView.setVisibility(View.VISIBLE);
                                    upiLoginView.setVisibility(View.GONE);

                                }
                            } else {
                                senderLayout.setVisibility(View.GONE);
                                buttonAddUPIView.setVisibility(View.VISIBLE);
                                upiLoginView.setVisibility(View.VISIBLE);
                                if (!response.body().isVersionValid()) {
                                    UtilMethods.INSTANCE.versionDialog(context);
                                } else {
                                    UtilMethods.INSTANCE.Error(context, response.body().getMsg() + "");
                                }
                            }

                        }
                    } else {
                        senderLayout.setVisibility(View.GONE);
                        upiLoginView.setVisibility(View.VISIBLE);
                        buttonAddUPIView.setVisibility(View.VISIBLE);
                        UtilMethods.INSTANCE.apiErrorHandle(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<VPAListResponse> call, Throwable t) {
                    upiLoginView.setVisibility(View.VISIBLE);
                    buttonAddUPIView.setVisibility(View.VISIBLE);
                    senderLayout.setVisibility(View.GONE);
                    loader.dismiss();
                    try {
                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            UtilMethods.INSTANCE.NetworkError(context);
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            UtilMethods.INSTANCE.ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage() + "");
                        } else {
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                UtilMethods.INSTANCE.ErrorWithTitle(context, "FATAL ERROR", t.getMessage() + "");
                            } else {
                                UtilMethods.INSTANCE.Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        }

                    } catch (IllegalStateException ise) {
                        UtilMethods.INSTANCE.Error(context, ise.getMessage());

                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            senderLayout.setVisibility(View.GONE);
            upiLoginView.setVisibility(View.VISIBLE);
            buttonAddUPIView.setVisibility(View.VISIBLE);
            loader.dismiss();
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== INTENT_ADD_VPA && resultCode==RESULT_OK){
            if (senderNumberEt.getText().length() == 10) {
                UtilMethods.INSTANCE.BalancecheckNew(this, null,null,null, object -> {
                    balanceCheckResponse = (BalanceResponse) object;
                    if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null) {
                        showBalanceData();
                    }
                });
                getVpaList(UPISenderLoginActivity.this, senderMobNum.trim());
            }
        }
    }
}