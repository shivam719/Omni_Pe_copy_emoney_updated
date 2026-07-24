package com.fintech.omnipe.AppUser.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fintech.omnipe.AppUser.Adapter.FOSCollectionReportAdapter;
import com.fintech.omnipe.AppUser.dto.AppGetAMResponse;
import com.fintech.omnipe.AppUser.dto.AreaMaster;
import com.fintech.omnipe.AppUser.dto.AscReport;
import com.fintech.omnipe.AppUser.dto.FosAccStmtAndCollReportResponse;
import com.fintech.omnipe.Auth.dto.LoginResponse;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.CustomFilterDialogUtils.CustomFilterDialog;
import com.fintech.omnipe.Util.UtilMethods;
import com.fintech.omnipe.usefull.CustomLoader;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

public class FosReportActivity extends AppCompatActivity {


    RecyclerView recycler_view;
    FOSCollectionReportAdapter fosCollectionReportAdapter;
    ArrayList<AscReport> transactionsObjects = new ArrayList<>();
    EditText search_all;
    CustomLoader loader;
    AreaMaster areaMaster;
    AppGetAMResponse appGetAMResponse;

    private Toolbar toolbar;
    private String filterFromDate = "", filterToDate = "";
    private int filterTopValue = 50;

    private LoginResponse loginPrefResponse;

    int areaId;
    private int INTENT_SELECT_AREA = 4231;
    private String[] ledgerChooseTopArray = {"50", "100", "200", "500", "1000", "1500", "ALL"};
    private CustomFilterDialog mCustomFilterDialog;
    private String filterArea="";
    private TextView refundStatusChooser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_fos_report);

        loginPrefResponse = new Gson().fromJson(UtilMethods.INSTANCE.getLoginPref(this), LoginResponse.class);
        mCustomFilterDialog = new CustomFilterDialog(this);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);

        findViews();
        clickView();


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            areaId = extras.getInt("areaID");

        }


        HitApi();

    }


    void findViews() {
        search_all = findViewById(R.id.search_all);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitle("Fos Area Report");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        recycler_view = findViewById(R.id.recycler_view);
        fosCollectionReportAdapter = new FOSCollectionReportAdapter(transactionsObjects, loader, loginPrefResponse, this, new FOSCollectionReportAdapter.FundTransferCallBAck() {
            @Override
            public void onSucessFund() {
                HitApi();
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
                fosCollectionReportAdapter.filter(newText.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public void HitApi() {
        if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {

            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);

            UtilMethods.INSTANCE.AccStmtAndCollFilterFosClick(this, filterTopValue + "", filterFromDate, filterToDate, "1", loader, loginPrefResponse
                    , areaId, new UtilMethods.ApiCallBack() {
                        @Override
                        public void onSucess(Object object) {
                            FosAccStmtAndCollReportResponse fosAccStmtAndCollReportResponse = (FosAccStmtAndCollReportResponse) object;
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

        int id = item.getItemId();
        if (id == R.id.action_filter) {

            openReportFosFilter();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public void openReportFosFilter() {
        final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(this);

        View sheetView = getLayoutInflater().inflate(R.layout.ledger_filter, null);
        LinearLayout childMobileView = sheetView.findViewById(R.id.childMobileView);
        childMobileView.setVisibility(View.GONE);

        LinearLayout mobileView = sheetView.findViewById(R.id.mobileView);
        mobileView.setVisibility(View.GONE);

        LinearLayout accountNoView = sheetView.findViewById(R.id.accountNoView);
        accountNoView.setVisibility(View.GONE);

        LinearLayout transactionIdView = sheetView.findViewById(R.id.transactionIdView);
        transactionIdView.setVisibility(View.GONE);

        LinearLayout walletTypeView = sheetView.findViewById(R.id.walletTypeView);
        walletTypeView.setVisibility(View.GONE);


        LinearLayout dateTypeChooserView = sheetView.findViewById(R.id.dateTypeChooserView);
        dateTypeChooserView.setVisibility(View.GONE);

        LinearLayout modeChooserView = sheetView.findViewById(R.id.modeChooserView);
        modeChooserView.setVisibility(View.GONE);

        LinearLayout criteriaView = sheetView.findViewById(R.id.criteriaView);
        criteriaView.setVisibility(View.GONE);

        LinearLayout criteriaChooserView = sheetView.findViewById(R.id.criteriaChooserView);
        criteriaChooserView.setVisibility(View.GONE);


        LinearLayout startDateView = sheetView.findViewById(R.id.startDateView);
        final AppCompatTextView startDate = sheetView.findViewById(R.id.startDate);
        startDate.setText(filterFromDate);
        LinearLayout endDateView = sheetView.findViewById(R.id.endDateView);
        final AppCompatTextView endDate = sheetView.findViewById(R.id.endDate);
        endDate.setText(filterToDate);


        LinearLayout topChooserView = sheetView.findViewById(R.id.topChooserView);
        final AppCompatTextView topChooser = sheetView.findViewById(R.id.topChooser);
        topChooser.setText(filterTopValue + "");


        Button filter = sheetView.findViewById(R.id.filter);

        LinearLayout refundStatusChooserView = sheetView.findViewById(R.id.refundStatusChooserView);
        TextView statusTitleTv = sheetView.findViewById(R.id.statusTitleTv);
        refundStatusChooser = sheetView.findViewById(R.id.refundStatusChooser);


        statusTitleTv.setText("Select Area");
        refundStatusChooser.setHint("Select Area");
        refundStatusChooserView.setVisibility(View.VISIBLE);
        refundStatusChooser.setText(filterArea + "");
        refundStatusChooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FosReportActivity.this, FosAreaReportActivity.class);
                startActivityForResult(intent, INTENT_SELECT_AREA);
            }
        });
        topChooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedIndex = 0;
                if (topChooser.getText().toString().length() == 0) {
                    selectedIndex = 0;
                } else {
                    selectedIndex = Arrays.asList(ledgerChooseTopArray).indexOf(topChooser.getText().toString());
                }
                mCustomFilterDialog.showSingleChoiceAlert(ledgerChooseTopArray, selectedIndex, "Select Top", "Choose Top", new CustomFilterDialog.SingleChoiceDialogCallBack() {
                    @Override
                    public void onPositiveClick(int index) {
                        topChooser.setText(ledgerChooseTopArray[index]);
                    }

                    @Override
                    public void onNegativeClick() {

                    }
                });
            }
        });
        startDateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCustomFilterDialog.setDCFromDate(startDate, endDate);
            }
        });
        endDateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCustomFilterDialog.setDCToDate(startDate, endDate);
            }
        });


        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
                filterFromDate = startDate.getText().toString();
                filterToDate =  endDate.getText().toString();
                filterTopValue = topChooser.getText().toString().equalsIgnoreCase("ALL") ? 5000 : Integer.parseInt(topChooser.getText().toString());
                HitApi();

            }
        });
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();

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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == INTENT_SELECT_AREA && resultCode == RESULT_OK) {
            areaId = data.getIntExtra("areaID", 0);
           filterArea = data.getStringExtra("area");

            if (refundStatusChooser != null) {
                refundStatusChooser.setText(filterArea + "");
            }
        }
    }
}
