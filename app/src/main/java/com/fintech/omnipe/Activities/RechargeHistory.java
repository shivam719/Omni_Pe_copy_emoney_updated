package com.fintech.omnipe.Activities;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.fintech.omnipe.Api.Object.RechargeStatus;
import com.fintech.omnipe.Api.Response.RechargeReportResponse;
import com.fintech.omnipe.Auth.dto.LoginResponse;
import com.fintech.omnipe.Fragments.Adapter.RechargeReportAdapter;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.ActivityActivityMessage;
import com.fintech.omnipe.Util.ApplicationConstant;
import com.fintech.omnipe.Util.CustomFilterDialogUtils.CustomFilterDialog;
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

public class RechargeHistory extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recycler_view;
    RelativeLayout childSearchLayout;
    String response = "";
    RechargeReportAdapter mAdapter;
    ArrayList<RechargeStatus> transactionsObjects = new ArrayList<>();
    RechargeReportResponse transactions = new RechargeReportResponse();
    LinearLayoutManager mLayoutManager;
    ImageView noData;
    TextView fromDate, toDate;
    EditText numberSearch, childSearch;
    RelativeLayout searchLayout;
    RelativeLayout searchContainer;
    EditText search_all;

    // Declare CustomLoader.....
    CustomLoader loader;
    String todatay;
    int flag = 0;
    long to_Mill;
    long from_mill;
    private CustomFilterDialog mCustomFilterDialog;
    private String filterFromDate = "", filterToDate = "";
    String filterTransactionId="", filterChildMobileNo="", filterAccountNo="";
    private int filterTopValue = 50;
    private String filterDateType = "";
    private String filterStatus = "", filteWalletType = "";
    private String filterChooseCriteria = "", filterEnterCriteria = "";
    private int filterStatusId, filteDateTypeId;


    private Toolbar toolbar;
    boolean isPSA;
    LoginResponse LoginPrefResponse;
    boolean isRecent = true;

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
        recycler_view = findViewById(R.id.recycler_view);
        childSearchLayout = findViewById(R.id.childSearchLayout);
        searchContainer = findViewById(R.id.searchContainer);
        search_all = findViewById(R.id.search_all);

        isPSA = getIntent().getBooleanExtra("PSA", false);
        findViewById(R.id.clearIcon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_all.setText("");
            }
        });
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitle(isPSA ? "PSA Token History" : "Recharge Report");
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
        LoginPrefResponse = new Gson().fromJson(getLoginPref, LoginResponse.class);
        if (LoginPrefResponse.getData().getRoleID().equals("3")) {
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
                new DatePickerDialog(RechargeHistory.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        /////////////////////////////////////////////////////////////////////
        final Calendar myCalendarTo = Calendar.getInstance();
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
                new DatePickerDialog(RechargeHistory.this, dateTo, myCalendarTo
                        .get(Calendar.YEAR), myCalendarTo.get(Calendar.MONTH),
                        myCalendarTo.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    public void HitApi() {
        if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {
            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);
            UtilMethods.INSTANCE.RechargeReport(this,isRecent, isPSA ? UtilMethods.INSTANCE.getPsaId(RechargeHistory.this) : "0", filterTopValue + "", filterStatusId + "", filterFromDate, filterToDate, filterTransactionId, filterAccountNo, filterChildMobileNo, "false", loader);
        } else {
            UtilMethods.INSTANCE.NetworkError(this, getResources().getString(R.string.err_msg_network_title),
                    getResources().getString(R.string.err_msg_network));
        }
    }

    public void dataParse(String response) {
        Gson gson = new Gson();
        transactions = gson.fromJson(response, RechargeReportResponse.class);
        transactionsObjects = transactions.getRechargeReport();

        if (transactionsObjects.size() > 0) {
            recycler_view.setVisibility(View.VISIBLE);
            mAdapter = new RechargeReportAdapter(transactionsObjects, this, isPSA);
            mLayoutManager = new LinearLayoutManager(this);
            recycler_view.setLayoutManager(mLayoutManager);
            recycler_view.setItemAnimator(new DefaultItemAnimator());
            recycler_view.setAdapter(mAdapter);
            search_all.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence newText, int start, int before, int count) {
                    mAdapter.filter(newText.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

        } else {
            if(flag==1)
                UtilMethods.INSTANCE.Error(this, "No Record Found ! between \n " + filterFromDate + " to " + filterToDate);
            else
                UtilMethods.INSTANCE.Error(this, "No Record Found ! on \n " + todatay);
           // UtilMethods.INSTANCE.Error(this, "No Record Found ! between \n " + filterFromDate + " to " + filterToDate);
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
            filterRecharge();
           /* mCustomFilterDialog.openDisputeFilter("Recharge", LoginPrefResponse.getData().getRoleID(),
                    filterFromDate, filterToDate, filterMobileNo, filterTransactionId, filterChildMobileNo,
                    filterAccountNo, filterTopValue, filterStatus, filterDateType, filterModeValue, filterChooseCriteriaId, filterChooseCriteria, filterEnterCriteria, filteWalletType, new CustomFilterDialog.LedgerFilterCallBack() {
                        @Override
                        public void onSubmitClick(String fromDate, String toDate, String mobileNo, String transactionId, String childMobileNo, String accountNo, int topValue, int statusId, String statusValue, int dateTypeId, String dateTypeValue, int modeTypeId, String modeValue, int chooseCriteriaId, String chooseCriteriaValue, String enterCriteriaValue, int walletTypeId, String walletType) {
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
                            filterModeValue = modeValue;
                            filterModeTypeId = modeTypeId;
                            isRecent=false;
                            HitApi();
                        }
                    });
           *//* if (visibleFlag) {
                visibleFlag = false;
                searchContainer.setVisibility(View.GONE);
            } else {
                visibleFlag = true;
                searchContainer.setVisibility(View.VISIBLE);
            }*/

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void filterRecharge() {
        mCustomFilterDialog.openAEPSRechargeDMRFilter("RechargeReport", filterFromDate, filterToDate, filterChildMobileNo,filterAccountNo,filterTransactionId, filterTopValue,new CustomFilterDialog.AEPSDMRAndRechargeFilterCallBack() {
            @Override
            public void onSubmitClick(String fromDate, String toDate, String childNo, String accountNo, String transactionID, int topRows) {

                filterFromDate = fromDate;
                filterToDate = toDate;
                filterChildMobileNo = childNo;
                filterAccountNo = accountNo;
                filterTransactionId = transactionID;
                filterTopValue = topRows;
                flag = 1;
                isRecent=false;
                HitApi();
            }
        });
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
        if (activityFragmentMessage.getMessage().equalsIgnoreCase("recent")) {
            Log.e("abcccccc ", " " + activityFragmentMessage.getFrom());
            dataParse(activityFragmentMessage.getFrom());
        }
    }

}
