package com.fintech.omnipe.AppUser.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fintech.omnipe.Api.Object.BalanceData;
import com.fintech.omnipe.Api.Response.AppUserListResponse;
import com.fintech.omnipe.Api.Response.BalanceResponse;
import com.fintech.omnipe.AppUser.Adapter.FosUserListAdapter;
import com.fintech.omnipe.AppUser.dto.FosAppUserListRequest;
import com.fintech.omnipe.AppUser.dto.UserReport;
import com.fintech.omnipe.Auth.dto.LoginResponse;
import com.fintech.omnipe.BuildConfig;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.ApiClient;
import com.fintech.omnipe.Util.ApplicationConstant;
import com.fintech.omnipe.Util.EndPointInterface;
import com.fintech.omnipe.Util.UtilMethods;
import com.fintech.omnipe.usefull.CustomLoader;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class FosUserListActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    String rollId;
    TextView errorMsg;
    EditText searchEt;
    ImageView clearIcon;
    CustomLoader loader;

    private ArrayList<UserReport> mUserLists = new ArrayList<>();
   FosUserListAdapter mAppUserListAdapter;
    private BalanceData mBalanceData;
    private LoginResponse LoginDataResponse;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_fos_user_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("FOS User List");
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
        mRecyclerView = findViewById(R.id.recyclerView);

        searchEt = findViewById(R.id.searchEt);
        clearIcon =findViewById(R.id.clearIcon);

        errorMsg = findViewById(R.id.errorMsg);
        String LoginResponse = UtilMethods.INSTANCE.getLoginPref(this);
         LoginDataResponse = new Gson().fromJson(LoginResponse,  LoginResponse.class);

        SharedPreferences myPreferences = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.balancePref, "");
        BalanceResponse balanceCheckResponse = new Gson().fromJson(balanceResponse, BalanceResponse.class);
        mBalanceData= balanceCheckResponse.getBalanceData();
        if(mUserLists!=null)
        {
            errorMsg.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mAppUserListAdapter = new FosUserListAdapter(this, mUserLists,mBalanceData,
                    LoginDataResponse.isAccountStatement(),new FosUserListAdapter.FundTransferCallBAck() {
                @Override
                public void onSucessFund() {
                    appUserListApi();
                }

                @Override
                public void onSucessEdit() {
                    appUserListApi();
                }
            },new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar),LoginDataResponse);
            mRecyclerView.setAdapter(mAppUserListAdapter);
        }
        else
        {
            mRecyclerView.setVisibility(View.GONE);
            errorMsg.setVisibility(View.VISIBLE);
            errorMsg.setText("Fos User list not found.");
        }


        //rollId = getArguments().getString("Id");
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

                mAppUserListAdapter.getFilter().filter(s);
            }
        });

        appUserListApi();


    }




    public void appUserListApi() {
        try {
            loader.show();
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<AppUserListResponse> call = git.FOSRetailerList(new FosAppUserListRequest(1000,"","",rollId, LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    UtilMethods.INSTANCE.getIMEI(this),
                    "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(this), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<AppUserListResponse>() {

                @Override
                public void onResponse(Call<AppUserListResponse> call, retrofit2.Response<AppUserListResponse> response) {

                    AppUserListResponse data = response.body();
                    if (data != null) {
                        if (data.getStatuscode() == 1) {
                            if (data.getFosList().getUserReports() != null && data.getFosList().getUserReports().size() > 0) {
                                errorMsg.setVisibility(View.GONE);
                                mUserLists.clear();
                                mUserLists.addAll(data.getFosList().getUserReports());
                                mAppUserListAdapter.notifyDataSetChanged();
                            } else {
                                errorMsg.setVisibility(View.VISIBLE);
                            }


                        } else if (response.body().getStatuscode() == -1) {
                            UtilMethods.INSTANCE.Error(FosUserListActivity.this, data.getMsg() + "");
                        } else if (response.body().getStatuscode() == 0) {
                            UtilMethods.INSTANCE.Error(FosUserListActivity.this, data.getMsg() + "");
                        } else {
                            UtilMethods.INSTANCE.Error(FosUserListActivity.this, data.getMsg() + "");
                        }

                    } else {
                        UtilMethods.INSTANCE.Error(FosUserListActivity.this, getString(R.string.some_thing_error));
                    }
                    loader.dismiss();
                }

                @Override
                public void onFailure(Call<AppUserListResponse> call, Throwable t) {
                    //Log.e("response", "error ");
                    if(loader!=null && loader.isShowing())
                        loader.dismiss();
                    try {

                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                            if (t.getMessage().contains("No address associated with hostname")) {
                                UtilMethods.INSTANCE.Error(FosUserListActivity.this, getString(R.string.err_msg_network));


                            } else {
                                UtilMethods.INSTANCE.Error(FosUserListActivity.this, t.getMessage());


                            }

                        } else {
                            UtilMethods.INSTANCE.Error(FosUserListActivity.this, getString(R.string.some_thing_error));
                        }
                    } catch (IllegalStateException ise) {
                        if(loader!=null && loader.isShowing())
                            loader.dismiss();
                        UtilMethods.INSTANCE.Error(FosUserListActivity.this, getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if(loader!=null && loader.isShowing())
                loader.dismiss();
            UtilMethods.INSTANCE.Error(FosUserListActivity.this, getString(R.string.some_thing_error));
        }

    }


    public void fundTransferApi(boolean isMarkCredit,String securityKey, boolean oType, int uid, String remark, int walletType, String amount, String userName, AlertDialog alertDialogFundTransfer) {
 loader.show();
        UtilMethods.INSTANCE.fundTransferApi(this, isMarkCredit,securityKey ,oType, uid, remark, walletType, amount, userName, alertDialogFundTransfer, loader, new UtilMethods.FundTransferCallBAck() {
            @Override
            public void onSucessFund() {

            }

            @Override
            public void onSucessEdit() {

            }
        });



    }

    public interface FundTransferCallBAck {
        void onSucess();
    }
}