package com.fintech.omnipe.Activities;

import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.fintech.omnipe.Activities.Adapter.FundOrderPendingListAdapter;
import com.fintech.omnipe.Api.Object.FundRequestForApproval;
import com.fintech.omnipe.Api.Request.AppUserListRequest;
import com.fintech.omnipe.Api.Request.FundTransferRequest;
import com.fintech.omnipe.Api.Response.AppUserListResponse;
import com.fintech.omnipe.BuildConfig;
import com.fintech.omnipe.Auth.dto.LoginResponse;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.ApiClient;
import com.fintech.omnipe.Util.ApplicationConstant;
import com.fintech.omnipe.Util.EndPointInterface;
import com.fintech.omnipe.Util.UtilMethods;
import com.fintech.omnipe.usefull.CustomLoader;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class FundOrderPendingActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView errorMsg;
    List<FundRequestForApproval> mFundRequestForApprovals = new ArrayList<>();
    CustomLoader loader;
    FundOrderPendingListAdapter mFundOrderPendingListAdapter;
    EditText searchEt;
    ImageView clearIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_fund_order_pending);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Fund Request");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        loader.setCancelable(false);
        loader.setCanceledOnTouchOutside(false);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        errorMsg = findViewById(R.id.errorMsg);
        searchEt = findViewById(R.id.searchEt);
        clearIcon = findViewById(R.id.clearIcon);
        mFundOrderPendingListAdapter = new FundOrderPendingListAdapter(this, mFundRequestForApprovals);
        recyclerView.setAdapter(mFundOrderPendingListAdapter);
        clearIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchEt.setText("");
            }
        });
        searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    clearIcon.setVisibility(View.VISIBLE);
                } else {
                    clearIcon.setVisibility(View.GONE);
                }

                mFundOrderPendingListAdapter.getFilter().filter(s);
            }
        });
        fundRequestApi();
    }


    public void fundRequestApi() {
        try {
            loader.show();
            String LoginResponse = UtilMethods.INSTANCE.getLoginPref(this);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<AppUserListResponse> call = git.FundOrderPending(new AppUserListRequest("", LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    UtilMethods.INSTANCE.getIMEI(this),
                    "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(this), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<AppUserListResponse>() {

                @Override
                public void onResponse(Call<AppUserListResponse> call, retrofit2.Response<AppUserListResponse> response) {
//                     Log.e("Payment response", "hello response : " + new Gson().toJson(response.body()).toString());
                    if (loader.isShowing())
                        loader.dismiss();
                    AppUserListResponse data = response.body();
                    if (data != null) {
                        if (data.getStatuscode() == 1) {
                            mFundRequestForApprovals.clear();
                            if (data.getFundRequestForApproval() != null && data.getFundRequestForApproval().size() > 0) {
                                errorMsg.setVisibility(View.GONE);
                                mFundRequestForApprovals.addAll(data.getFundRequestForApproval());
                            } else {
                                errorMsg.setVisibility(View.VISIBLE);
                                errorMsg.setText("Data not found");
                            }
                            mFundOrderPendingListAdapter.notifyDataSetChanged();

                        } else if (response.body().getStatuscode() == -1) {
                            errorMsg.setVisibility(View.VISIBLE);
                            errorMsg.setText(data.getMsg());
                            UtilMethods.INSTANCE.Error(FundOrderPendingActivity.this, data.getMsg() + "");
                        } else if (response.body().getStatuscode() == 0) {
                            errorMsg.setVisibility(View.VISIBLE);
                            errorMsg.setText(data.getMsg());
                            UtilMethods.INSTANCE.Error(FundOrderPendingActivity.this, data.getMsg() + "");
                        } else {
                            errorMsg.setVisibility(View.VISIBLE);
                            errorMsg.setText(data.getMsg());
                            UtilMethods.INSTANCE.Error(FundOrderPendingActivity.this, data.getMsg() + "");
                        }

                    } else {
                        errorMsg.setVisibility(View.VISIBLE);
                        errorMsg.setText(getString(R.string.some_thing_error));
                        UtilMethods.INSTANCE.Error(FundOrderPendingActivity.this, getString(R.string.some_thing_error));
                    }
                }

                @Override
                public void onFailure(Call<AppUserListResponse> call, Throwable t) {
                    Log.e("response", "error ");

                    try {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                            if (t.getMessage().contains("No address associated with hostname")) {
                                errorMsg.setVisibility(View.VISIBLE);
                                errorMsg.setText(getString(R.string.err_msg_network));
                                UtilMethods.INSTANCE.NetworkError(FundOrderPendingActivity.this,getString(R.string.network_error_title), getString(R.string.err_msg_network));

                            } else {
                                errorMsg.setVisibility(View.VISIBLE);
                                errorMsg.setText(t.getMessage());
                                UtilMethods.INSTANCE.Error(FundOrderPendingActivity.this, t.getMessage());
                            }

                        } else {
                            errorMsg.setVisibility(View.VISIBLE);
                            errorMsg.setText(getString(R.string.some_thing_error));
                            UtilMethods.INSTANCE.Error(FundOrderPendingActivity.this, getString(R.string.some_thing_error));
                        }
                    } catch (IllegalStateException ise) {
                        loader.dismiss();
                        errorMsg.setVisibility(View.VISIBLE);
                        errorMsg.setText(getString(R.string.some_thing_error));
                        UtilMethods.INSTANCE.Error(FundOrderPendingActivity.this, getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            errorMsg.setVisibility(View.VISIBLE);
            errorMsg.setText(getString(R.string.some_thing_error));
            UtilMethods.INSTANCE.Error(FundOrderPendingActivity.this, getString(R.string.some_thing_error));
        }

    }

    public void fundTransferApi(boolean isMarkCredit,String securityKey, int paymentId,int WalletId, int uid, String remark, String amount, final String userName,boolean oType, final AlertDialog alertDialogFundTransfer) {
        try {
            loader.show();
            String LoginResponse = UtilMethods.INSTANCE.getLoginPref(this);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<AppUserListResponse> call = git.AppFundTransfer(new FundTransferRequest(isMarkCredit,securityKey, oType, uid, remark, WalletId, paymentId, amount, LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    UtilMethods.INSTANCE.getIMEI(this),
                    "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(this), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<AppUserListResponse>() {

                @Override
                public void onResponse(Call<AppUserListResponse> call, retrofit2.Response<AppUserListResponse> response) {

                    if (loader.isShowing())
                        loader.dismiss();
                    AppUserListResponse data = response.body();
                    if (data != null) {
                        if (data.getStatuscode() == 1) {
                            fundRequestApi();
                            alertDialogFundTransfer.dismiss();
                            UtilMethods.INSTANCE.Successful(FundOrderPendingActivity.this, data.getMsg().replace("{User}", userName));
                        } else if (response.body().getStatuscode() == -1) {

                            UtilMethods.INSTANCE.Error(FundOrderPendingActivity.this, data.getMsg().replace("{User}", userName));
                        } else if (response.body().getStatuscode() == 0) {

                            UtilMethods.INSTANCE.Error(FundOrderPendingActivity.this, data.getMsg().replace("{User}", userName));
                        } else {

                            UtilMethods.INSTANCE.Error(FundOrderPendingActivity.this, data.getMsg().replace("{User}", userName));
                        }

                    } else {

                        UtilMethods.INSTANCE.Error(FundOrderPendingActivity.this, getString(R.string.some_thing_error));
                    }
                }

                @Override
                public void onFailure(Call<AppUserListResponse> call, Throwable t) {
                    Log.e("response", "error ");

                    try {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                            if (t.getMessage().contains("No address associated with hostname")) {

                                UtilMethods.INSTANCE.NetworkError(FundOrderPendingActivity.this, getString(R.string.network_error_title),getString(R.string.err_msg_network));


                            } else {

                                UtilMethods.INSTANCE.Error(FundOrderPendingActivity.this, t.getMessage());


                            }

                        } else {

                            UtilMethods.INSTANCE.Error(FundOrderPendingActivity.this, getString(R.string.some_thing_error));
                        }
                    } catch (IllegalStateException ise) {
                        loader.dismiss();

                        UtilMethods.INSTANCE.Error(FundOrderPendingActivity.this, getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();

            UtilMethods.INSTANCE.Error(FundOrderPendingActivity.this, getString(R.string.some_thing_error));
        }

    }


    public void rejectApi(String securityKey, int paymentId, int uid, String remark, String amount, final String userName, final AlertDialog alertDialogFundTransfer) {
        try {
            loader.show();
            String LoginResponse = UtilMethods.INSTANCE.getLoginPref(this);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<AppUserListResponse> call = git.AppFundReject(new FundTransferRequest(false,securityKey, false, uid, remark, 1, paymentId, amount, LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(), ApplicationConstant.INSTANCE.APP_ID,
                    UtilMethods.INSTANCE.getIMEI(this),
                    "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(this), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<AppUserListResponse>() {

                @Override
                public void onResponse(Call<AppUserListResponse> call, retrofit2.Response<AppUserListResponse> response) {

                    if (loader.isShowing())
                        loader.dismiss();
                    AppUserListResponse data = response.body();
                    if (data != null) {
                        if (data.getStatuscode() == 1) {
                            fundRequestApi();
                            alertDialogFundTransfer.dismiss();
                            UtilMethods.INSTANCE.Successful(FundOrderPendingActivity.this, data.getMsg().replace("{User}", userName));
                        } else if (response.body().getStatuscode() == -1) {

                            UtilMethods.INSTANCE.Error(FundOrderPendingActivity.this, data.getMsg().replace("{User}", userName));
                        } else if (response.body().getStatuscode() == 0) {

                            UtilMethods.INSTANCE.Error(FundOrderPendingActivity.this, data.getMsg().replace("{User}", userName));
                        } else {

                            UtilMethods.INSTANCE.Error(FundOrderPendingActivity.this, data.getMsg().replace("{User}", userName));
                        }

                    } else {

                        UtilMethods.INSTANCE.Error(FundOrderPendingActivity.this, getString(R.string.some_thing_error));
                    }
                }

                @Override
                public void onFailure(Call<AppUserListResponse> call, Throwable t) {
                    Log.e("response", "error ");

                    try {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                            if (t.getMessage().contains("No address associated with hostname")) {

                                UtilMethods.INSTANCE.NetworkError(FundOrderPendingActivity.this, getString(R.string.network_error_title),getString(R.string.err_msg_network));


                            } else {

                                UtilMethods.INSTANCE.Error(FundOrderPendingActivity.this, t.getMessage());


                            }

                        } else {

                            UtilMethods.INSTANCE.Error(FundOrderPendingActivity.this, getString(R.string.some_thing_error));
                        }
                    } catch (IllegalStateException ise) {
                        loader.dismiss();

                        UtilMethods.INSTANCE.Error(FundOrderPendingActivity.this, getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();

            UtilMethods.INSTANCE.Error(FundOrderPendingActivity.this, getString(R.string.some_thing_error));
        }

    }


}
