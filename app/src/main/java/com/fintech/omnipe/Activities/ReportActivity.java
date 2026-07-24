package com.fintech.omnipe.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

import com.fintech.omnipe.AppUser.Activity.AppUserListActivity;
import com.fintech.omnipe.usefull.CustomLoader;
import com.google.gson.Gson;
import com.fintech.omnipe.Activities.Adapter.HomeOptionListAdapter;
import com.fintech.omnipe.Activities.Adapter.WalletBalanceAdapter;
import com.fintech.omnipe.Api.Object.BalanceType;
import com.fintech.omnipe.Api.Response.BalanceResponse;
import com.fintech.omnipe.CommissionSlab.ui.CommissionScreen;
import com.fintech.omnipe.Auth.dto.LoginResponse;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.ApplicationConstant;
import com.fintech.omnipe.Util.ChangePassUtils;
import com.fintech.omnipe.Util.CustomAlertDialog;
import com.fintech.omnipe.Util.OpTypeResponse;
import com.fintech.omnipe.Util.UtilMethods;

import java.util.ArrayList;

public class ReportActivity extends AppCompatActivity {

    HomeOptionListAdapter mDashboardOptionListAdapter;
    View profileDetailView;
    private AppCompatImageView userImage;
    private AppCompatTextView nameTv;
    private AppCompatTextView roleTv;
    private AppCompatTextView emailTv;
    private RecyclerView walletRecyclerView;
    private RecyclerView recyclerView;
    private BalanceResponse balanceCheckResponse;
    private OpTypeResponse mActiveServiceData;
    private ChangePassUtils mChangePassUtils;
    private CustomLoader loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_report);
        mChangePassUtils = new ChangePassUtils(this);
        findViews();
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
    }

    private void findViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitle("All Reports");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        userImage = findViewById(R.id.userImage);
        profileDetailView = findViewById(R.id.profileDetailView);
        nameTv = findViewById(R.id.nameTv);
        roleTv = findViewById(R.id.roleTv);
        emailTv = findViewById(R.id.emailTv);
        walletRecyclerView = findViewById(R.id.walletRecyclerView);
        walletRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));

       /* profileDetailView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReportActivity.this, ProfileActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
            }
        });*/
        showBalace();
    }


    @Override
    protected void onStart() {
        super.onStart();
        mActiveServiceData = new Gson().fromJson(UtilMethods.INSTANCE.getActiveService(this), OpTypeResponse.class);
        setDashboardData();

        String strResponse = UtilMethods.INSTANCE.getLoginPref(this);
        LoginResponse loginDataResponse = new Gson().fromJson(strResponse, LoginResponse.class);

        nameTv.setText(loginDataResponse.getData().getName() + "");
        roleTv.setText("Role : " + loginDataResponse.getData().getRoleName());
        emailTv.setText("Email : " + loginDataResponse.getData().getEmailID());
    }

    private void setDashboardData() {


    }


    void openNewScreen(int id) {


        if (id == 101) {
            Intent intent = new Intent(ReportActivity.this, RechargeHistory.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
        if (id == 102) {
            Intent intent = new Intent(ReportActivity.this, LedgerReport.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
        if (id == 103) {
            Intent i = new Intent(ReportActivity.this, FundReqReport.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        }
        if (id == 104) {
            Intent n = new Intent(ReportActivity.this, DisputeReport.class);
            n.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(n);
        }
        if (id == 105) {
            Intent i = new Intent(ReportActivity.this, DMRReport.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);

        }
        if (id == 106) {
            // Fund Transfer Report

        }
        if (id == 107) {
            Intent i = new Intent(ReportActivity.this, FundRecReport.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);

        }
        if (id == 108) {
            Intent i = new Intent(ReportActivity.this, UserDayBookActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);

        }
        if (id == 109) {
            Intent i = new Intent(ReportActivity.this, FundOrderPendingActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        }
        if (id == 110) {
            Intent i = new Intent(ReportActivity.this, PaymentRequest.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        }
        if (id == 111) {
            Intent i = new Intent(ReportActivity.this, CreateUserActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        }
        if (id == 112) {
            Intent i = new Intent(ReportActivity.this, AppUserListActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        }
        if (id == 113) {
            Intent n = new Intent(ReportActivity.this, CommissionScreen.class);
            n.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(n);
        }
        if (id == 114) {
            Intent i = new Intent(ReportActivity.this, BankDetailActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        }
        if (id == 115) {
            mChangePassUtils.changePassword(false, true);
        }
        if (id == 116) {
           // mChangePassUtils.changePassword(true, true);
            UtilMethods.INSTANCE.ChangePinPassword(ReportActivity.this, true, "","0000","0000", loader, null);

        }
        if (id == 117) {
            Intent i = new Intent(ReportActivity.this, DthSupportActivity.class);
            i.putExtra("From", "DTH");
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        }
        if (id == 118) {
            Intent i = new Intent(ReportActivity.this, DthSupportActivity.class);
            i.putExtra("From", "Prepaid");
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        }
        if (id == 119) {

            Intent i = new Intent(ReportActivity.this, SupportActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        }
        if (id == 120) {
            CustomAlertDialog customAlertDialog = new CustomAlertDialog(this, true);
            customAlertDialog.Successfullogout("Do you really want to Logout?", ReportActivity.this);
        }
    }


    private void showBalace() {
        SharedPreferences myPreferences = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.balancePref, "");
        balanceCheckResponse = new Gson().fromJson(balanceResponse, BalanceResponse.class);
        ArrayList<BalanceType> mBalanceTypes = new ArrayList<>();
        walletRecyclerView.setAdapter(new WalletBalanceAdapter(this, mBalanceTypes, R.layout.adapter_wallet_grid));
    }
}
