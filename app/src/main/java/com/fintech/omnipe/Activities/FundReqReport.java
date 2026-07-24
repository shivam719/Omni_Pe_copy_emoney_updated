package com.fintech.omnipe.Activities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.fintech.omnipe.Activities.Adapter.FundReqAdapter;
import com.fintech.omnipe.Api.Response.FundOrderReportObject;
import com.fintech.omnipe.Api.Response.RechargeReportResponse;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.ActivityActivityMessage;
import com.fintech.omnipe.Util.GlobalBus;
import com.fintech.omnipe.Util.UtilMethods;
import com.fintech.omnipe.usefull.CustomLoader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class FundReqReport extends AppCompatActivity implements View.OnClickListener{

    boolean visibleFlag = false;
    ProgressDialog mProgressDialog = null;
    private Toolbar toolbar;
    RecyclerView recycler_view;
    FundReqAdapter mAdapter;
    String response = "";
    LinearLayoutManager mLayoutManager;
    ArrayList<FundOrderReportObject> transactionsObjects = new ArrayList<>();
    RechargeReportResponse transactions = new RechargeReportResponse();
    RadioButton fd_rej, fd_acc, fd_req;
    // Declare CustomLoader.....
    CustomLoader loader;
    SearchView search_all;
    String ServiceID = "0";

    TextView fromDate, toDate;
    EditText numberSearch,childSearch;
    RelativeLayout searchLayout;
    RelativeLayout searchContainer, numberSearchLayout, childSearchLayout;
    int flag = 1;
    int report = 0;
    String today;
    long to_Mill;
    long from_mill;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.fund_rq_report);
        search_all = (SearchView) findViewById(R.id.search_all);
        searchContainer = (RelativeLayout) findViewById(R.id.searchContainer);
        searchContainer.setVisibility(View.VISIBLE);
        childSearchLayout = (RelativeLayout) findViewById(R.id.childSearchLayout);
        numberSearchLayout = (RelativeLayout) findViewById(R.id.numberSearchLayout);
        numberSearch = (EditText) findViewById(R.id.numberSearch);
        childSearch= (EditText) findViewById(R.id.childSearch);
        fromDate = (TextView) findViewById(R.id.fromDate);
        toDate = (TextView) findViewById(R.id.toDate);
        numberSearchLayout.setVisibility(View.GONE);
        childSearchLayout.setVisibility(View.VISIBLE);
        searchLayout = (RelativeLayout) findViewById(R.id.searchLayout);
        searchLayout.setOnClickListener(this);
        final Calendar myCalendarTo = Calendar.getInstance();
        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd-MMM-yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                fromDate.setText(sdf.format(myCalendar.getTime()));
                Date date1 = myCalendar.getTime();
                from_mill = date1.getTime();
            }

        };
        fromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(FundReqReport.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                flag = 2;
            }
        });


        /////////////////////////////////////////////////////////////////////


        final DatePickerDialog.OnDateSetListener dateTo = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendarTo.set(Calendar.YEAR, year);
                myCalendarTo.set(Calendar.MONTH, monthOfYear);
                myCalendarTo.set(Calendar.DAY_OF_MONTH, dayOfMonth);


                String myFormat = "dd-MMM-yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                toDate.setText(sdf.format(myCalendarTo.getTime()));
                Date date1 = myCalendarTo.getTime();
                to_Mill = date1.getTime();
            }

        };
        toDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(FundReqReport.this, dateTo, myCalendarTo
                        .get(Calendar.YEAR), myCalendarTo.get(Calendar.MONTH),
                        myCalendarTo.get(Calendar.DAY_OF_MONTH)).show();
                flag = 2;
            }
        });

        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        mProgressDialog = new ProgressDialog(FundReqReport.this);
        fd_req = findViewById(R.id.fd_req);
        fd_acc = findViewById(R.id.fd_acc);
        fd_rej = findViewById(R.id.fd_rej);
        fd_req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServiceID = "1";
                final Calendar myCalendar = Calendar.getInstance();
                String myFormat = "dd-MMM-yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                if (flag == 1)
                    HitApi(sdf.format(myCalendar.getTime()), sdf.format(myCalendar.getTime()));
                else if (flag == 2) {
                    HitApi(fromDate.getText().toString(), toDate.getText().toString());
                }
            }
        });
        fd_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServiceID = "2";
                final Calendar myCalendar = Calendar.getInstance();
                String myFormat = "dd-MMM-yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                if (flag == 1)
                    HitApi(sdf.format(myCalendar.getTime()), sdf.format(myCalendar.getTime()));
                else if (flag == 2) {
                    HitApi(fromDate.getText().toString(), toDate.getText().toString());
                }
            }
        });
        fd_rej.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServiceID = "3";
                final Calendar myCalendar = Calendar.getInstance();
                String myFormat = "dd-MMM-yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                if (flag == 1)
                    HitApi(sdf.format(myCalendar.getTime()), sdf.format(myCalendar.getTime()));
                else if (flag == 2) {
                    HitApi(fromDate.getText().toString(), toDate.getText().toString());
                }
            }
        });
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitle("Fund Order Report");
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        final Calendar myCalendar2 = Calendar.getInstance();
        String myFormat = "dd-MMM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        today = sdf.format(myCalendar2.getTime());
        report = 0;
        HitApi(sdf.format(myCalendar2.getTime()), sdf.format(myCalendar2.getTime()));
    }

    private void HitApi(String fromdate, String todate) {
        if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {

            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);
            UtilMethods.INSTANCE.FundOrderReport(this, "0", ServiceID, fromdate
                    , todate,
                    "",
                    "",
                    "false", "0", "true", "", loader);
        } else {
            UtilMethods.INSTANCE.NetworkError(this, getResources().getString(R.string.err_msg_network_title),
                    getResources().getString(R.string.err_msg_network));
        }
    }

    public void dataParse(String response) {
        try {
            Gson gson = new Gson();
            transactions = gson.fromJson(response, RechargeReportResponse.class);
            transactionsObjects = transactions.getFundOrderReport();
            if (transactionsObjects.size() > 0 && transactionsObjects != null) {
                recycler_view.setVisibility(View.VISIBLE);
                mAdapter = new FundReqAdapter(transactionsObjects, FundReqReport.this);
                mLayoutManager = new LinearLayoutManager(this.getApplicationContext());
                recycler_view.setLayoutManager(mLayoutManager);
                recycler_view.setItemAnimator(new DefaultItemAnimator());
                recycler_view.setAdapter(mAdapter);
                search_all.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        // TODO Auto-generated method stub

                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        // TODO Auto-generated method stub
                        mAdapter.filter(newText);
                        return false;
                    }
                });
            } else {
                if (report == 0)
                    UtilMethods.INSTANCE.Error(this, "No Record Found ! on \n" + today);
                else
                    UtilMethods.INSTANCE.Error(this, "No Record Found ! between \n" + fromDate.getText().toString() + " to " + toDate.getText().toString());
                recycler_view.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == searchLayout) {
            report = 1;
            if (fromDate.getText().toString().isEmpty() || toDate.getText().toString().isEmpty()) {
                UtilMethods.INSTANCE.Error(this, "Please enter both Date");
                return;
            }
            if (to_Mill < from_mill) {
                UtilMethods.INSTANCE.Error(this, "From Date is not valid Date");
                return;
            }
            if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {
                loader.show();
                loader.setCancelable(false);
                loader.setCanceledOnTouchOutside(false);
                UtilMethods.INSTANCE.FundOrderReport(this, "0", "0", fromDate.getText().toString()
                        , toDate.getText().toString(),
                        "",
                        childSearch.getText().toString()+"",
                        "false", "0", "true", "", loader);
            } else {
                UtilMethods.INSTANCE.NetworkError(this, getResources().getString(R.string.err_msg_network_title),
                        getResources().getString(R.string.err_msg_network));
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_filter) {
            if (visibleFlag) {
                visibleFlag = false;
                searchContainer.setVisibility(View.GONE);
            } else {
                visibleFlag = true;
                searchContainer.setVisibility(View.VISIBLE);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }





    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            GlobalBus.getBus().register(this);
        }
    }

    /**
     * Called when leaving the activity
     */
    @Override
    public void onPause() {

        super.onPause();
    }

    /**
     * Called when returning to the activity
     */
    @Override
    public void onResume() {
        super.onResume();

    }

    /**
     * Called before the activity is destroyed
     */
    @Override
    public void onDestroy() {
        // Unregister the registered event.
        GlobalBus.getBus().unregister(this);

        super.onDestroy();
    }

    @Subscribe
    public void onActivityActivityMessage(ActivityActivityMessage activityFragmentMessage) {
        if (activityFragmentMessage.getMessage().equalsIgnoreCase("fund_receive")) {
            response = activityFragmentMessage.getFrom();
            dataParse(response);


        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        /*getMenuInflater().inflate(R.menu.menu_filter, menu);*/

        return true;
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_filter) {
           *//* if (visibleFlag) {
                visibleFlag = false;
                searchContainer.setVisibility(View.GONE);
            } else {
                visibleFlag = true;
                searchContainer.setVisibility(View.VISIBLE);
            }*//*

           *//* mCustomFilterDialog.openDisputeFilter("FundOrder", "",
                    filterFromDate, filterToDate, filterMobileNo, filterTransactionId, filterChildMobileNo,
                    filterAccountNo, filterTopValue, filterStatus, filterDateType, filterModeValue, filterChooseCriteriaId, filterChooseCriteria, filterEnterCriteria, filteWalletType, new CustomFilterDialog.LedgerFilterCallBack() {
                        @Override
                        public void onSubmitClick(String fromDate, String toDate, String mobileNo, String transactionId, String childMobileNo, String accountNo, int topValue, int statusId, String statusValue, int dateTypeId, String dateTypeValue,int modeTypeId,String modeValue, int chooseCriteriaId, String chooseCriteriaValue, String enterCriteriaValue, int walletTypeId, String walletType) {
                            filterFromDate = fromDate;
                            filterToDate = toDate;
                            filterMobileNo = mobileNo;
                            filterTransactionId = transactionId;
                            filterChildMobileNo = childMobileNo;
                            filterAccountNo = accountNo;
                            filterTopValue = topValue;
                            filterStatusId = statusId;
                            filterStatus = statusValue;
                            filteDateTypeId = dateTypeId;
                            filterDateType = dateTypeValue;
                            filterChooseCriteriaId = chooseCriteriaId;
                            filterChooseCriteria = chooseCriteriaValue;
                            filterEnterCriteria = enterCriteriaValue;
                            filteWalletTypeId = walletTypeId;
                            filteWalletType = walletType;
                            filterModeValue= modeValue;
                            filterModeTypeId=modeTypeId;

                            HitApi();
                        }
                    });*//*

            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/


}