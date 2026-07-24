package com.fintech.omnipe.MoveToWallet.UI;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fintech.omnipe.Api.Response.NumberListResponse;
import com.fintech.omnipe.Auth.dto.LoginResponse;
import com.fintech.omnipe.Fragments.dto.OperatorList;
import com.fintech.omnipe.MoveToWallet.Api.MoveToBankReportResponse;
import com.fintech.omnipe.MoveToWallet.Api.MoveToWalletReportData;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.CustomFilterDialogUtils.CustomFilterDialog;
import com.fintech.omnipe.Util.UtilMethods;
import com.fintech.omnipe.usefull.CustomLoader;
import com.google.gson.Gson;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MoveToWalletReportActivity extends AppCompatActivity {

    private Activity activity;
    private View noDataView;
    private AppCompatTextView errorMsg;
    private AppCompatEditText search_all;
    private RecyclerView moveToBankReportRv;
    private MoveToBankReportAdapter mAdapter;
    private CustomFilterDialog mCustomFilterDialog;
    private CustomLoader loader;
    private LoginResponse mLoginResponse;
    private int filterStatusId,filterTopRows=50,filterModeId;
    private String filterFromDate,filterToDate,filterModeValue="",filterTransactionId="",filterStatusValue="",filterChildMobileNo="";
    private ArrayList<MoveToWalletReportData> transactionObjectList=new ArrayList<>();
    private ArrayList<OperatorList> mOperatorLists=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_move_towallet_report);

        /*--Initialise View--*/
        activity=this;
        mLoginResponse=new Gson().fromJson(UtilMethods.INSTANCE.getLoginPref(activity),LoginResponse.class);
        moveToBankReportRv=findViewById(R.id.moveToBankReportRv);
        noDataView=findViewById(R.id.noDataView);
        errorMsg=findViewById(R.id.errorMsg);
        search_all=findViewById(R.id.search_all);
        moveToBankReportRv.setLayoutManager(new LinearLayoutManager(activity));
        mAdapter=new MoveToBankReportAdapter(transactionObjectList,activity,mLoginResponse);
        moveToBankReportRv.setAdapter(mAdapter);
        loader=new CustomLoader(activity,android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitle("Move To Bank  Report");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        mCustomFilterDialog = new CustomFilterDialog(activity);
        /*filterChildMobileNo=mLoginResponse.getData().getMobileNo();*/
        final Calendar myCalendar = Calendar.getInstance();
        String myFormat = "dd-MMM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        filterFromDate = sdf.format(myCalendar.getTime());
        filterToDate = filterFromDate;


        hitApi();
        getOperatorMode();

        search_all.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence newText, int start, int before, int count) {
                if (mAdapter != null) {
                    mAdapter.filter(newText.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        findViewById(R.id.clearIcon).setOnClickListener(v -> search_all.setText(""));

    }

    private void hitApi() {
       if(UtilMethods.INSTANCE.isNetworkAvialable(activity))
       {
           loader.show();
           loader.setCancelable(false);
           UtilMethods.INSTANCE.MoveToBankReport(activity, filterStatusId, filterTopRows, filterModeId, filterFromDate, filterToDate, filterTransactionId, filterChildMobileNo, loader, new UtilMethods.ApiCallBackTwoMethod() {
               @Override
               public void onSucess(Object object) {
                   if(object !=null && object instanceof MoveToBankReportResponse){
                       parseData((MoveToBankReportResponse)object);
                   }
               }

               @Override
               public void onError(Object object) {
                   setInfoHideShow((int)object);
               }


           });
       }else{
           UtilMethods.INSTANCE.NetworkError(activity);
           setInfoHideShow(UtilMethods.INSTANCE.ERROR_NETWORK);
       }


    }

    private void parseData(MoveToBankReportResponse moveToBankReportResponse) {

        if(moveToBankReportResponse!=null && moveToBankReportResponse.getMoveToWalletReport()!=null){
            transactionObjectList.clear();
            transactionObjectList.addAll(moveToBankReportResponse.getMoveToWalletReport());
        }
        if (transactionObjectList != null && transactionObjectList.size() > 0 ) {
            noDataView.setVisibility(View.GONE);
            moveToBankReportRv.setVisibility(View.VISIBLE);
            mAdapter.notifyDataSetChanged();
        } else {
            setInfoHideShow(UtilMethods.INSTANCE.ERROR_OTHER);

            moveToBankReportRv.setVisibility(View.GONE);
        }
    }

    private void setInfoHideShow(int errorType) {
        moveToBankReportRv.setVisibility(View.GONE);
        transactionObjectList.clear();
        mAdapter.notifyDataSetChanged();

        if (errorType == UtilMethods.INSTANCE.ERROR_NETWORK) {
            noDataView.setVisibility(View.VISIBLE);
            errorMsg.setText(getString(R.string.network_error_message));
        } else {
            noDataView.setVisibility(View.VISIBLE);
            if (filterFromDate.equalsIgnoreCase(filterToDate)) {
                errorMsg.setText("Record not found for " + filterFromDate);
            } else {
                errorMsg.setText("Record not found between\n" + filterFromDate + " to " + filterToDate);
            }
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

        if (item.getItemId() == R.id.action_filter) {
            filterDataShow();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void filterDataShow() {

        mCustomFilterDialog.openMoveToBankFilterDialog(mLoginResponse.getData().getRoleID(),filterFromDate,filterToDate,filterTransactionId,filterChildMobileNo,filterTopRows,filterModeValue,filterStatusValue,mOperatorLists,42, new CustomFilterDialog.MoveToBankReportFilterCallBack() {
            @Override
            public void onSubmitClick(String fromDate, String toDate, String childMobileNo, String transactionId, int topValue, int statusId, String statusValue, int modeId, String modeValue) {

                filterFromDate=fromDate;
                filterToDate=toDate;
                filterChildMobileNo=childMobileNo;
                filterTransactionId=transactionId;
                filterTopRows=topValue;
                filterStatusId=statusId;
                filterStatusValue=statusValue;
                filterModeId=modeId;
                filterModeValue=modeValue;
                hitApi();
            }
        });
    }

    private void getOperatorMode() {
        NumberListResponse listResponse= new Gson().fromJson(UtilMethods.INSTANCE.getNumberList(activity),NumberListResponse.class);
        if(listResponse!=null){
            mOperatorLists=listResponse.getData().getOperators();

        }
        else{
            loader.show();
            loader.setCancelable(false);

            UtilMethods.INSTANCE.NumberList(activity,  loader, new UtilMethods.ApiCallBack() {
                @Override
                public void onSucess(Object object) {
                    if(loader!=null && loader.isShowing())
                        loader.dismiss();
                    if(object!=null &&  object instanceof NumberListResponse)
                    {
                        NumberListResponse operatorListResponse= (NumberListResponse)object;
                        mOperatorLists=operatorListResponse.getData().getOperators();

                    }

                }
            });
        }

    }
}