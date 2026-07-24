package com.fintech.omnipe.Activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.fintech.omnipe.Activities.Adapter.AEPSReportAdapter;
import com.fintech.omnipe.Util.CustomFilterDialogUtils.CustomFilterDialog;

import com.fintech.omnipe.Util.FindBasicLoginRequest;
import com.fintech.omnipe.Util.UtilMethods;
import com.fintech.omnipe.R;
import com.fintech.omnipe.usefull.CustomLoader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AEPSReportActivity extends AppCompatActivity {

    private Toolbar toolbar;
    RecyclerView recycler_view;
    AEPSReportAdapter mAdapter;
    LinearLayoutManager mLayoutManager;
    TextView fromDate, toDate;
    EditText numberSearch;
    RelativeLayout searchLayout;
    RelativeLayout searchContainer, numberSearchLayout, childSearchLayout;
    int flag = 0;
    SearchView search_all;
    // Declare CustomLoader.....
    CustomLoader loader;
    String todatay;
    private CustomFilterDialog mCustomFilterDialog;
    private String filterFromDate = "", filterToDate = "";
    private String filterAccountNo = "",filterChildNumber="",filterTransctionId="";
    private int filterTopValue = 50;
    AEPSReportRequest aepsReportRequest;
    Boolean isRecent=true,isExport=false;
    private int oId=0,status=0,apiId=0;
    private AEPSResponse aepsResponse;
    private List<AePsDetail> aePsDetailList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_aepsreport);
        mCustomFilterDialog = new CustomFilterDialog(this);

        findViewByIds();
        SimpleDateFormat sdfToday = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        filterFromDate = sdfToday.format(new Date());
        filterToDate = filterFromDate;
        final Calendar myCalendar = Calendar.getInstance();
        String myFormat = "dd-MMM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        todatay = sdf.format(myCalendar.getTime());
        flag = 0;
        HitApi();
    }



    private void findViewByIds() {
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitle("AEPS Report");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        search_all = (SearchView) findViewById(R.id.search_all);
        search_all.setIconified(false);
        searchContainer = (RelativeLayout) findViewById(R.id.searchContainer);
        childSearchLayout = (RelativeLayout) findViewById(R.id.childSearchLayout);
        numberSearchLayout = (RelativeLayout) findViewById(R.id.numberSearchLayout);
        numberSearch = (EditText) findViewById(R.id.numberSearch);
        fromDate = (TextView) findViewById(R.id.fromDate);
        toDate = (TextView) findViewById(R.id.toDate);
        searchLayout = (RelativeLayout) findViewById(R.id.searchLayout);
        /* searchLayout.setOnClickListener(this);*/
        numberSearchLayout.setVisibility(View.GONE);
        childSearchLayout.setVisibility(View.GONE);
    }

    private void HitApi() {
        if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {
            aepsReportRequest=new FindBasicLoginRequest(AEPSReportActivity.this).setBasicLoginData();
            aepsReportRequest.setFromDate(filterFromDate);
            aepsReportRequest.setToDate(filterToDate);
            aepsReportRequest.setAccountNo(filterAccountNo);
            aepsReportRequest.setChildMobile(filterChildNumber);
            aepsReportRequest.setTransactionID(filterTransctionId);
            aepsReportRequest.setTopRows(filterTopValue);
            aepsReportRequest.setOid(oId);
            aepsReportRequest.setApiid(apiId);
            aepsReportRequest.setStatus(status);
            aepsReportRequest.setRecent(isRecent);
            aepsReportRequest.setExport(isExport);

            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);

            try{
                UtilMethods.INSTANCE.AEPSReport(this, aepsReportRequest, loader, new UtilMethods.ApiCallBackTwoMethod() {
                    @Override
                    public void onSucess(Object object) {

                        if(object!=null)
                        {
                            aepsResponse =(AEPSResponse)object;
                            aePsDetailList=aepsResponse.getAePsDetail();

                            if(aePsDetailList!=null && aePsDetailList.size()>0)
                            {
                                recycler_view.setVisibility(View.VISIBLE);
                                mAdapter = new AEPSReportAdapter(aePsDetailList, AEPSReportActivity.this);
                                mLayoutManager = new LinearLayoutManager(AEPSReportActivity.this);
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
                            }
                            else {
                                if (flag == 0)
                                    UtilMethods.INSTANCE.Error(AEPSReportActivity.this, "No Record Found ! on \n " + todatay);
                                else
                                    UtilMethods.INSTANCE.Error(AEPSReportActivity.this, "No Record Found ! between \n " + filterFromDate + " to " + filterToDate);
                            }
                        }


                    }

                    @Override
                    public void onError(Object object) {

                    }

                   /* @Override
                    public void onError(String errorMsg) {

                    }*/
                });
            }
            catch (Exception ex)
            {
             if(loader!=null && loader.isShowing())
                 loader.dismiss();
             UtilMethods.INSTANCE.Error(this,ex.getMessage());
            }

        } else {
            UtilMethods.INSTANCE.NetworkError(this, getResources().getString(R.string.err_msg_network_title),
                    getResources().getString(R.string.err_msg_network));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
           /* if (visibleFlag) {
                visibleFlag = false;
                searchContainer.setVisibility(View.GONE);
            } else {
                visibleFlag = true;
                searchContainer.setVisibility(View.VISIBLE);
            }*/



            mCustomFilterDialog.openAEPSRechargeDMRFilter("AEPS", filterFromDate, filterToDate, filterChildNumber,filterAccountNo,filterTransctionId, filterTopValue,new CustomFilterDialog.AEPSDMRAndRechargeFilterCallBack() {
                @Override
                public void onSubmitClick(String fromDate, String toDate, String childNo, String accountNo, String transactionID, int topRows) {

                    filterFromDate = fromDate;
                    filterToDate = toDate;
                    filterChildNumber = childNo;
                    filterAccountNo = accountNo;
                    filterTransctionId = transactionID;
                    filterTopValue = topRows;
                    flag = 1;
                    isRecent=false;
                    HitApi();
                }
            });
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
//        if (!EventBus.getDefault().isRegistered(this)) {
//            GlobalBus.getBus().register(this);
//        }
    }

    /**
     * Called when returning to the activity
     */
    @Override
    public void onResume() {
        super.onResume();

    }

    /**
     * Called when leaving the activity
     */
    @Override
    public void onPause() {

        super.onPause();
    }


}
