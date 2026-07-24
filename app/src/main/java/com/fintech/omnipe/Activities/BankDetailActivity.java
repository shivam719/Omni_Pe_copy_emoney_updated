package com.fintech.omnipe.Activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.fintech.omnipe.Activities.Adapter.BankDetailAdapter;
import com.fintech.omnipe.Api.Request.BalanceRequest;
import com.fintech.omnipe.Api.Response.GetBankAndPaymentModeResponse;
import com.fintech.omnipe.BuildConfig;
import com.fintech.omnipe.Auth.dto.LoginResponse;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.ApiClient;
import com.fintech.omnipe.Util.ApplicationConstant;
import com.fintech.omnipe.Util.EndPointInterface;
import com.fintech.omnipe.Util.UtilMethods;
import com.fintech.omnipe.usefull.CustomLoader;

import retrofit2.Call;
import retrofit2.Callback;

public class BankDetailActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    BankDetailAdapter mBankDetailAdapter;
    private CustomLoader loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_bank_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitle("Bank Details");
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        UtilMethods.INSTANCE.setAppLogoIconUI(this,findViewById(R.id.logoIv));
        GetBank();
    }


    public void GetBank() {
        try {
            loader.show();
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            String strLoginResponse = UtilMethods.INSTANCE.getLoginPref(this);
            LoginResponse LoginDataResponse = new Gson().fromJson(strLoginResponse, LoginResponse.class);

            BalanceRequest mBalanceRequest = new BalanceRequest(
                    LoginDataResponse.getData().getUserID() + "", LoginDataResponse.getData().getLoginTypeID() + "",
                    ApplicationConstant.INSTANCE.APP_ID,
                    UtilMethods.INSTANCE.getIMEI(this),
                    "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(this), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession());
            String str = new Gson().toJson(mBalanceRequest);
            Call<GetBankAndPaymentModeResponse> call = git.GetBank(mBalanceRequest);
            call.enqueue(new Callback<GetBankAndPaymentModeResponse>() {
                @Override
                public void onResponse(Call<GetBankAndPaymentModeResponse> call, final retrofit2.Response<GetBankAndPaymentModeResponse> response) {
                    Log.e("GetBankAndPayment", "is : " + new Gson().toJson(response.body()));
                    try {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        if (response.body() != null && response.body().getStatuscode() != null) {
                            if (response.body().getStatuscode().equalsIgnoreCase("1")) {
                                if (response.body().getBanks() != null && response.body().getBanks().size() > 0) {
                                    mBankDetailAdapter = new BankDetailAdapter(response.body().getBanks(), BankDetailActivity.this);
                                    mRecyclerView.setAdapter(mBankDetailAdapter);
                                }
                            } else if (response.body().getStatuscode().equalsIgnoreCase("-1")) {
                                if (response.body().getIsVersionValid() == false) {
                                    UtilMethods.INSTANCE.versionDialog(BankDetailActivity.this);
                                } else {
                                    UtilMethods.INSTANCE.Error(BankDetailActivity.this, response.body().getMsg() + "");
                                }
                            }
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                    }

                }

                @Override
                public void onFailure(Call<GetBankAndPaymentModeResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    UtilMethods.INSTANCE.Error(BankDetailActivity.this, getString(R.string.err_something_went_wrong));

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
