package com.fintech.omnipe.AppUser.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
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

import com.fintech.omnipe.AppUser.Adapter.FOSChannelReportAdapter;
import com.fintech.omnipe.AppUser.dto.AscReport;
import com.fintech.omnipe.AppUser.dto.FosAccStmtAndCollReportResponse;
import com.fintech.omnipe.Auth.dto.LoginResponse;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.CustomFilterDialogUtils.CustomFilterDialog;
import com.fintech.omnipe.Util.UtilMethods;
import com.fintech.omnipe.usefull.CustomLoader;
import com.google.gson.Gson;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ChannelReportActivity extends AppCompatActivity
{

    RecyclerView recycler_view;
    FOSChannelReportAdapter fosCollectionReportAdapter;
    ArrayList<AscReport> transactionsObjects = new ArrayList<>();
    EditText search_all;
    CustomLoader loader;
    private Toolbar toolbar;
    private CustomFilterDialog mCustomFilterDialog;
    private String filterFromDate = "", filterToDate = "";
    TextView balanceTab, uBalanceTab;
    private int filterTopValue = 50;
    private LoginResponse loginPrefResponse;
    int arId;
    private static final int INTENT_FOS_COLLECTION=101;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView( R.layout.activity_channel_report);

        loginPrefResponse = new Gson().fromJson(UtilMethods.INSTANCE.getLoginPref(this), LoginResponse.class);

        mCustomFilterDialog = new CustomFilterDialog(this);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);

        findViews();
        clickView();

        HitLastApi();

    }


    void findViews() {
        search_all = findViewById(R.id.search_all);
        balanceTab = findViewById(R.id.balanceTab);
        uBalanceTab = findViewById(R.id.uBalanceTab);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitle("Channel Report");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
        toolbar.setNavigationOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        } );

        recycler_view = findViewById(R.id.recycler_view);
        fosCollectionReportAdapter = new FOSChannelReportAdapter(transactionsObjects, loader, loginPrefResponse, this, new FOSChannelReportAdapter.FundTransferCallBAck() {
            @Override
            public void onSucessFund() {
                HitLastApi();
            }
        });

        recycler_view.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        recycler_view.setAdapter(fosCollectionReportAdapter);
        final Calendar myCalendar = Calendar.getInstance();
        String myFormat = "dd-MMM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        String today = sdf.format(myCalendar.getTime());
        filterFromDate = today;
        filterToDate = today;
    }


    void clickView() {
        findViewById(R.id.clearIcon).setOnClickListener(v -> search_all.setText(""));


        search_all.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence newText, int start, int before, int count) {
                fosCollectionReportAdapter.getFilter().filter(newText.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }



    public void HitLastApi() {
        if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {

            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);

            UtilMethods.INSTANCE.AccStmtAndCollFilterFosClick(this,filterTopValue + "",filterFromDate,filterToDate,"2",loader,loginPrefResponse
                    ,arId , new UtilMethods.ApiCallBack() {
                        @Override
                        public void onSucess(Object object) {
                            FosAccStmtAndCollReportResponse fosAccStmtAndCollReportResponse  = (FosAccStmtAndCollReportResponse) object;
                            dataParse(fosAccStmtAndCollReportResponse);
                        }


                    });
        } else {
            UtilMethods.INSTANCE.NetworkError(this);
        }
    }






    public void dataParse(FosAccStmtAndCollReportResponse transactions) {

        if (transactions != null && transactions.getAscReport() != null) {
            transactionsObjects.clear();
            transactionsObjects.addAll(transactions.getAscReport());
        }
        if (transactionsObjects.size() > 0 && transactionsObjects != null) {
            if (transactionsObjects.get(0).isPrepaid()) {
                balanceTab.setVisibility(View.VISIBLE);
            } else {
                balanceTab.setVisibility(View.GONE);
            }
            if (transactionsObjects.get(0).isUtility()) {
                uBalanceTab.setVisibility(View.VISIBLE);
            } else {
                uBalanceTab.setVisibility(View.GONE);
            }
            recycler_view.setVisibility(View.VISIBLE);
            fosCollectionReportAdapter.notifyDataSetChanged();
        } else {
            UtilMethods.INSTANCE.Error(this, "Record not found between\n" + filterFromDate + " to " + filterToDate);
            recycler_view.setVisibility(View.GONE);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_filter, menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_filter) {


            mCustomFilterDialog.openReportFosFilter(filterFromDate, filterToDate, filterTopValue,
                    (fromDate, toDate,topValue) -> {
                        filterFromDate = fromDate;
                        filterToDate = toDate;

                        filterTopValue = topValue;
                        HitLastApi();
                    });
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void collectFund(AscReport operator) {

        Intent i = new Intent(this, FosCollectionActivity.class);
        i.putExtra("userID", operator.getUserID());
        i.putExtra("outletName", operator.getOutletName());
        i.putExtra("mobile", operator.getMobile() );
        startActivityForResult(i,INTENT_FOS_COLLECTION);
    }
    public void AccountStatement(String Mobile, int ServiceId) {
        Intent intent = new Intent(this, AccountStatementServiceWiseActivity.class);
        intent.putExtra("Mobile", Mobile);
        intent.putExtra("ServiceId", ServiceId);
        intent.putExtra("FromDate", filterFromDate);
        intent.putExtra("ToDate", filterToDate);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==INTENT_FOS_COLLECTION && resultCode==RESULT_OK){
            HitLastApi();
        }
    }
}



