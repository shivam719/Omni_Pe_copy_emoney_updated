package com.fintech.omnipe.DTHSubscription.Activity;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fintech.omnipe.Auth.dto.LoginResponse;
import com.fintech.omnipe.DTHSubscription.Adapter.DthSubscriptionReportAdapter;
import com.fintech.omnipe.DTHSubscription.dto.DthSubscriptionReport;
import com.fintech.omnipe.DTHSubscription.dto.DthSubscriptionReportResponse;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.ApplicationConstant;
import com.fintech.omnipe.Util.CustomFilterDialogUtils.CustomFilterDialog;
import com.fintech.omnipe.Util.UtilMethods;
import com.fintech.omnipe.usefull.CustomLoader;
import com.google.gson.Gson;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DthSubscriptionReportActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recycler_view;
    RelativeLayout childSearchLayout;
    DthSubscriptionReportAdapter mAdapter;
    ArrayList<DthSubscriptionReport> transactionsObjects = new ArrayList<>();
    LinearLayoutManager mLayoutManager;
    ImageView noData;
    TextView fromDate, toDate;
    EditText numberSearch, childSearch;
    RelativeLayout searchLayout;
    RelativeLayout searchContainer;
    AppCompatEditText search_all;
    boolean visibleFlag = false;
    // Declare CustomLoader.....
    CustomLoader loader;
    String todatay;
    int flag = 0;
    long to_Mill;
    long from_mill;

    private CustomFilterDialog mCustomFilterDialog;
    private String filterFromDate = "", filterToDate = "";
    private String filterMobileNo = "";
    String filterTransactionId, filterChildMobileNo, filterAccountNo;
    private int filterTopValue = 50;
    private String filterStatus = "",filterBookingStatu="";
    private int filterStatusId,filterBookingStatusId;
    private Toolbar toolbar;
    private LoginResponse mLoginDataResponse;
    private boolean isRecent;
    private int fromId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.report);
        // Start loading the ad in the background.
        mCustomFilterDialog = new CustomFilterDialog(this);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        /*UtilMethods.INSTANCE.setAppLogoIconUI(this,findViewById(R.id.operatorImage));*/
        recycler_view = findViewById(R.id.recycler_view);
        childSearchLayout = findViewById(R.id.childSearchLayout);
        searchContainer = findViewById(R.id.searchContainer);
        search_all = findViewById(R.id.search_all);
        if(getIntent()!=null) {
            fromId = getIntent().getExtras().getInt("fromId", 0);
            filterBookingStatusId = getIntent().getIntExtra("FILTER_TYPE", 0);
        }

        filterBookingStatu = mCustomFilterDialog.dthSubscriptionBookingStatusArray[filterBookingStatusId];
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitle("DTH Subscription Report");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        noData = findViewById(R.id.noData);
        // OpenRechargeDialog();
        numberSearch = findViewById(R.id.numberSearch);
        childSearch = findViewById(R.id.childSearch);
        SharedPreferences myPrefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNameLoginPref, MODE_PRIVATE);
        String getLoginPref = myPrefs.getString(ApplicationConstant.INSTANCE.LoginPref, "");
        mLoginDataResponse = new Gson().fromJson(getLoginPref, LoginResponse.class);
        if (mLoginDataResponse.getData().getRoleID().equals("3")) {
            childSearch.setVisibility(View.GONE);
        } else {
            childSearch.setVisibility(View.VISIBLE);
        }
        fromDate = findViewById(R.id.fromDate);
        toDate = findViewById(R.id.toDate);
        searchLayout = findViewById(R.id.searchLayout);
        searchLayout.setOnClickListener(this);
        final Calendar myCalendar = Calendar.getInstance();
        String myFormat = "dd-MMM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        flag = 0;
        todatay = sdf.format(myCalendar.getTime());
        filterFromDate = sdf.format(myCalendar.getTime());
        filterToDate = sdf.format(myCalendar.getTime());
        isRecent = true;
        HitApi();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat1 = "dd-MMM-yyyy"; //In which you need put here
                SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
                fromDate.setText(sdf1.format(myCalendar.getTime()));
                Date date1 = myCalendar.getTime();
                from_mill = date1.getTime();
            }
        };
        fromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(DthSubscriptionReportActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        final Calendar myCalendarTo = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener dateTo = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendarTo.set(Calendar.YEAR, year);
                myCalendarTo.set(Calendar.MONTH, monthOfYear);
                myCalendarTo.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat12 = "dd-MMM-yyyy"; //In which you need put here
                SimpleDateFormat sdf12 = new SimpleDateFormat(myFormat12, Locale.US);
                toDate.setText(sdf12.format(myCalendarTo.getTime()));
                Date date1 = myCalendarTo.getTime();
                to_Mill = date1.getTime();
            }
        };
        toDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(DthSubscriptionReportActivity.this, dateTo, myCalendarTo
                        .get(Calendar.YEAR), myCalendarTo.get(Calendar.MONTH),
                        myCalendarTo.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        search_all.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence query, int start, int before, int count) {
                if(mAdapter!=null) {
                    mAdapter.filter(query.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    public void HitApi()
    {
        if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {
            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);
            UtilMethods.INSTANCE.DthSubscriptionReport(this, fromId+"", filterTopValue + "", filterStatusId,filterBookingStatusId, filterFromDate, filterToDate, filterTransactionId, filterAccountNo, filterChildMobileNo, "false", isRecent, loader,mLoginDataResponse, object -> {
                        DthSubscriptionReportResponse mRechargeReportResponse = (DthSubscriptionReportResponse) object;
                        dataParse(mRechargeReportResponse);
                    });
        } else {
            UtilMethods.INSTANCE.NetworkError(this, getResources().getString(R.string.err_msg_network_title), getResources().getString(R.string.err_msg_network));
        }
    }



    public void dataParse(DthSubscriptionReportResponse mRechargeReportResponse) {


        transactionsObjects = mRechargeReportResponse.getDTHSubscriptionReport();

        if (transactionsObjects.size() > 0) {
            noData.setVisibility(View.GONE);
            recycler_view.setVisibility(View.VISIBLE);
            mAdapter = new DthSubscriptionReportAdapter(transactionsObjects, this, mLoginDataResponse.getData().getRoleID());
            mLayoutManager = new LinearLayoutManager(this);
            recycler_view.setLayoutManager(mLayoutManager);
            recycler_view.setItemAnimator(new DefaultItemAnimator());
            recycler_view.setAdapter(mAdapter);


        } else {
            recycler_view.setVisibility(View.GONE);
            noData.setVisibility(View.VISIBLE);
            if(flag==0)
                UtilMethods.INSTANCE.Error(this, "No Record Found ! on \n " + todatay);
            else
                UtilMethods.INSTANCE.Error(this, " No Record Found ! between \n " + filterFromDate + " to " + filterToDate);
        }
    }

    @Override
    public void onClick(View v) {

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
            mCustomFilterDialog.openDthSubscriptionFilter( mLoginDataResponse.getData().getRoleID(),
                    filterFromDate, filterToDate, filterMobileNo, filterTransactionId, filterChildMobileNo,
                    filterAccountNo, filterTopValue, filterStatus,filterBookingStatu, (fromDate, toDate, mobileNo, transactionId,
                                                                                 childMobileNo, accountNo, topValue,statusId,statusValue, bookingStatusId, bookingStatusValue) -> {
                        filterFromDate = fromDate;
                        filterToDate = toDate;
                        filterMobileNo = mobileNo;
                        filterTransactionId = transactionId;
                        filterChildMobileNo = childMobileNo;
                        filterAccountNo = accountNo;
                        filterTopValue = topValue;
                        filterBookingStatusId = bookingStatusId;
                        filterBookingStatu=bookingStatusValue;
                        filterStatus = statusValue;
                        filterStatusId=statusId;
                        isRecent = false;
                        flag=1;
                        HitApi();
                    });

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
