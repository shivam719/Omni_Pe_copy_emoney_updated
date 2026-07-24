package com.fintech.omnipe.DTHSubscription.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.ListPopupWindow;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.fintech.omnipe.Activities.Adapter.ListPopupWindowAdapter;
import com.fintech.omnipe.Api.Object.BalanceType;
import com.fintech.omnipe.Auth.dto.LoginResponse;
import com.fintech.omnipe.DTHSubscription.Adapter.DthSubscriptionReportAdapter;
import com.fintech.omnipe.DTHSubscription.Adapter.ListAreaPopupWindowAdapter;
import com.fintech.omnipe.DTHSubscription.dto.DthPackage;
import com.fintech.omnipe.DTHSubscription.dto.DthSubscriptionReport;
import com.fintech.omnipe.DTHSubscription.dto.DthSubscriptionReportResponse;
import com.fintech.omnipe.DTHSubscription.dto.GetDthPackageResponse;
import com.fintech.omnipe.DTHSubscription.dto.PincodeArea;
import com.fintech.omnipe.DTHSubscription.dto.PincodeAreaResponse;
import com.fintech.omnipe.Fragments.dto.OperatorList;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.ApplicationConstant;
import com.fintech.omnipe.Util.CustomAlertDialog;
import com.fintech.omnipe.Util.GetLocation;
import com.fintech.omnipe.Util.UtilMethods;
import com.fintech.omnipe.usefull.CustomLoader;
import com.google.gson.Gson;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class DTHSubscriptionActivity extends AppCompatActivity {

    private AppCompatTextView rechargeViewTab;
    private AppCompatTextView historyViewTab;
    private LinearLayout rechargeView;
    private LinearLayout llMain;
    private ImageView ivIcon;
    private AppCompatTextView opName;
    private ImageView billLogo;
    private RelativeLayout rlPackage, rlGender, rlArea;
    private ImageView ivPackage;
    private AppCompatTextView tvPackage, tvGender, tvArea;
    private ImageView ivArrowPackage;
    private TextView packageError, viewChannel;
    private LinearLayout rlName, rlLastName;
    private ImageView ivName;
    private EditText etName, etLastName;
    private TextView nameError;
    private RelativeLayout rlNumber;
    private ImageView ivNumber;
    private LinearLayout numberView;
    private TextView tvName;
    private EditText etNumber;
    private TextView numberError;
    private LinearLayout rlAddress;
    private EditText etAddress;
    private TextView addressError;
    private LinearLayout rlPincode;
    private EditText etPincode;
    private TextView pincodeError, areaError, lastNameError, genderError;
    private RelativeLayout historyView;
    private RelativeLayout historyHeader;
    private AppCompatTextView recentRechargeTv;
    private AppCompatTextView viewMore;
    private RecyclerView recyclerView;
    private LinearLayout llComingSoon;
    private Button btRecharge;
    private String fromDateStr, toDateStr;
    String maxAmountLength = "0";
    String minAmountLength = "0";
    boolean isAcountNumeric;
    int minNumberLength = 0, maxNumberLength = 0;
    String accountName = "", accountRemark = "",startWith,operatorUrl,regExAccount;
    boolean BBPS = false, IsPartial = false;
    boolean isBilling,isTakeCustomerNo;
    ArrayList<String> StartWithArray = new ArrayList<>();
    String operatorSelected = "";
    int operatorSelectedId = 0, incentiveOperatorSelectedId;
    private RequestOptions requestOptions;
    private String from;
    private OperatorList operatorList;
    private int fromId;
    private String number;
    private CustomLoader loader;
    CustomAlertDialog mCustomAlertDialog;
    private String selectedPackageAmount;
    private GetDthPackageResponse mGetDthPackageResponse;
    private int INTENT_PACKAGE = 529;
    private DthPackage mIntentDthPackage;
    private LoginResponse mLoginDataResponse;
    private double getLattitude, getLongitude;
    private GetLocation mGetLocation;
    private String dataOfPincode = "";
    ArrayList<PincodeArea> pincodeAreasArray = new ArrayList<>();
    ArrayList<BalanceType> itemsGender = new ArrayList<>();
    private int selectedAreaId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_dth_subscription);
        itemsGender.add(new BalanceType("Male", "Hide"));
        itemsGender.add(new BalanceType("Female", "Hide"));
        String LoginResponse = UtilMethods.INSTANCE.getLoginPref(this);
        mLoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
        mCustomAlertDialog = new CustomAlertDialog(this, true);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        findViews();
        getDthPackage();

        final Calendar myCalendar = Calendar.getInstance();
        String myFormat = "dd-MMM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        fromDateStr = sdf.format(myCalendar.getTime());
        toDateStr = sdf.format(myCalendar.getTime());
        mGetLocation.startLocationUpdatesIfSettingEnable((lattitude, longitude) -> {
            getLattitude = lattitude;
            getLongitude = longitude;
        });
        HitApi();
    }


    private void findViews() {
        requestOptions = new RequestOptions();
        requestOptions.circleCropTransform();
        requestOptions.placeholder(R.drawable.rnd_placeholder);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
        requestOptions.skipMemoryCache(true);



         if (getIntent() != null)
         {
             from = getIntent().getExtras().getString("from");
             fromId = getIntent().getExtras().getInt("fromId",0);
             operatorList = (OperatorList) getIntent().getSerializableExtra("operatorList");
             if(operatorList!=null){
                 operatorSelected=operatorList.getName();
                 operatorSelectedId= operatorList.getOid();
                 isAcountNumeric=operatorList.isAccountNumeric();
                 IsPartial=operatorList.isPartial();
                 minNumberLength=operatorList.getLength();
                 maxNumberLength=operatorList.getLengthMax();
                 minAmountLength= String.valueOf(operatorList.getMin());
                 maxAmountLength= String.valueOf(operatorList.getMax());
                 BBPS=operatorList.isBBPS();
                 isBilling = operatorList.isBilling();
                 isTakeCustomerNo = operatorList.isTakeCustomerNum();
                 accountName = operatorList.getAccountName();
                 accountRemark =operatorList.getAccountRemak();
                 startWith =operatorList.getStartWith();
                 operatorUrl =operatorList.getImage();
                 regExAccount =operatorList.getRegExAccount();

             }
         }

        Toolbar toolBar = findViewById(R.id.toolbar);
        toolBar.setTitle(from + " Subscriptions");
        toolBar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolBar);
        toolBar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        viewChannel = findViewById(R.id.viewChannel);
        rechargeViewTab = findViewById(R.id.rechargeViewTab);
        historyViewTab = findViewById(R.id.historyViewTab);
        rechargeView = findViewById(R.id.rechargeView);
        llMain = findViewById(R.id.ll_main);
        ivIcon = findViewById(R.id.iv_icon);
        opName = findViewById(R.id.opName);
        billLogo = findViewById(R.id.bill_logo);
        rlPackage = findViewById(R.id.rl_package);
        rlGender = findViewById(R.id.rl_gender);
        rlArea = findViewById(R.id.rl_area);
        ivPackage = findViewById(R.id.iv_package);
        tvPackage = findViewById(R.id.tv_package);
        tvGender = findViewById(R.id.tv_gender);
        tvArea = findViewById(R.id.tv_area);
        ivArrowPackage = findViewById(R.id.iv_arrow_package);
        packageError = findViewById(R.id.package_error);
        rlName = findViewById(R.id.rl_name);
        rlLastName = findViewById(R.id.rl_last_name);
        ivName = findViewById(R.id.iv_name);
        etName = findViewById(R.id.et_name);
        etLastName = findViewById(R.id.et_last_name);
        nameError = findViewById(R.id.name_error);
        rlNumber = findViewById(R.id.rl_number);
        ivNumber = findViewById(R.id.iv_number);
        numberView = findViewById(R.id.numberView);
        tvName = findViewById(R.id.tv_name);
        etNumber = findViewById(R.id.et_number);
        numberError = findViewById(R.id.number_error);
        rlAddress = findViewById(R.id.rl_address);
        etAddress = findViewById(R.id.et_address);
        addressError = findViewById(R.id.address_error);
        rlPincode = findViewById(R.id.rl_pincode);
        etPincode = findViewById(R.id.et_pincode);
        pincodeError = findViewById(R.id.pincode_error);
        areaError = findViewById(R.id.area_error);
        lastNameError = findViewById(R.id.last_name_error);
        genderError = findViewById(R.id.gender_error);
        historyView = findViewById(R.id.historyView);
        historyHeader = findViewById(R.id.historyHeader);
        recentRechargeTv = findViewById(R.id.recentRechargeTv);
        viewMore = findViewById(R.id.viewMore);
        recyclerView = findViewById(R.id.recyclerView);
        llComingSoon = findViewById(R.id.ll_coming_soon);
        btRecharge = findViewById(R.id.bt_recharge);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        opName.setText(operatorSelected);
        rechargeViewTab.setText(from + "");

        Glide.with(this).load(ApplicationConstant.INSTANCE.baseIconUrl + operatorUrl).
                apply(requestOptions).into(ivIcon);

        if (BBPS) {
            billLogo.setVisibility(View.VISIBLE);
        } else {
            billLogo.setVisibility(View.GONE);
        }
        viewMore.setOnClickListener(v -> {
            Intent i = new Intent(DTHSubscriptionActivity.this, DthSubscriptionReportActivity.class);
            i.putExtra("fromId", fromId);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        });
        rechargeViewTab.setOnClickListener(v -> {
            historyView.setVisibility(View.GONE);
            rechargeView.setVisibility(View.VISIBLE);
            btRecharge.setVisibility(View.VISIBLE);
            rechargeViewTab.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            historyViewTab.setBackgroundColor(getResources().getColor(R.color.light_grey));
        });

        historyViewTab.setOnClickListener(v -> {
            historyView.setVisibility(View.VISIBLE);
            rechargeView.setVisibility(View.GONE);
            btRecharge.setVisibility(View.GONE);
            rechargeViewTab.setBackgroundColor(getResources().getColor(R.color.light_grey));
            historyViewTab.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            HitApi();
        });

        rlPackage.setOnClickListener(v -> {
            if (mGetDthPackageResponse != null && mGetDthPackageResponse.getDthPackage() != null && mGetDthPackageResponse.getDthPackage().size() > 0) {
                startActivityForResult(new Intent(DTHSubscriptionActivity.this, DthPackageActivity.class)
                        .putExtra("PACKAGE_DATA_ARRAY", mGetDthPackageResponse.getDthPackage())
                        .putExtra("Title", from + " (" + operatorSelected + ")")
                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), INTENT_PACKAGE);
            } else {
                mCustomAlertDialog.WarningWithDoubleBtnCallBack("Sorry, Package Not Found.", "Retry", true, new CustomAlertDialog.DialogCallBack() {
                    @Override
                    public void onPositiveClick() {
                        getDthPackage();
                    }

                    @Override
                    public void onNegativeClick() {

                    }
                });
            }
        });

        viewChannel.setOnClickListener(v ->
                startActivity(new Intent(DTHSubscriptionActivity.this, DthChannelActivity.class)
                        .putExtra("DTH_PACKAGE", mIntentDthPackage)
                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)));


        etNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (tvName.getVisibility() == View.VISIBLE && s.length() != 10) {
                    tvName.setVisibility(View.GONE);
                }
            }
        });

        rlGender.setOnClickListener(v -> showGenderPoupWindow(tvGender));

        rlArea.setOnClickListener(v -> {
            pincodeError.setVisibility(View.GONE);
            if (TextUtils.isEmpty(etPincode.getText())) {
                pincodeError.setVisibility(View.VISIBLE);
                pincodeError.setText("Please Enter Valid Area Pincode");
                etPincode.requestFocus();
                return;
            } else if (etPincode.getText().toString().replaceAll(" ", "").length() != 6) {
                pincodeError.setVisibility(View.VISIBLE);
                pincodeError.setText("Please Enter 6 Digit Area Pincode");
                etPincode.requestFocus();
                return;
            } else if (pincodeAreasArray == null || pincodeAreasArray.size() == 0) {
                pincodeError.setVisibility(View.VISIBLE);
                pincodeError.setText("Please Enter Valid Area Pincode");
                etPincode.requestFocus();
                return;
            }

            showAreaPoupWindow(v);
        });
        etPincode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (dataOfPincode != null && !dataOfPincode.equalsIgnoreCase(s.toString()) && s.toString().trim().length() == 6) {
                    tvArea.setText("");
                    pincodeAreasArray.clear();
                    PincodeArea();
                }
            }
        });
        btRecharge.setOnClickListener(v -> submitDetail());
    }


    void submitDetail()
    {
        packageError.setVisibility(View.GONE);
        nameError.setVisibility(View.GONE);
        lastNameError.setVisibility(View.GONE);
        genderError.setVisibility(View.GONE);
        numberError.setVisibility(View.GONE);
        addressError.setVisibility(View.GONE);
        pincodeError.setVisibility(View.GONE);
        areaError.setVisibility(View.GONE);
        if (TextUtils.isEmpty(tvPackage.getText())) {
            packageError.setVisibility(View.VISIBLE);
            packageError.setText("Please Select Package First");
            tvPackage.requestFocus();
            return;
        } else if (TextUtils.isEmpty(etName.getText())) {
            nameError.setVisibility(View.VISIBLE);
            nameError.setText("Please Enter Your First Name");
            etName.requestFocus();
            return;
        } else if (TextUtils.isEmpty(etLastName.getText())) {
            lastNameError.setVisibility(View.VISIBLE);
            lastNameError.setText("Please Enter Your Last Name");
            etLastName.requestFocus();
            return;
        } else if (TextUtils.isEmpty(tvGender.getText())) {
            genderError.setVisibility(View.VISIBLE);
            genderError.setText("Please Select Your Gender");
            tvGender.requestFocus();
            return;
        } else if (TextUtils.isEmpty(etNumber.getText())) {
            numberError.setVisibility(View.VISIBLE);
            numberError.setText("Please Enter Valid Mobile Number");
            etNumber.requestFocus();
            return;
        } else if (etNumber.getText().toString().replaceAll(" ", "").length() != 10) {
            numberError.setVisibility(View.VISIBLE);
            numberError.setText("Please Enter 10 Digit Mobile Number");
            etNumber.requestFocus();
            return;
        } else if (TextUtils.isEmpty(etAddress.getText())) {
            addressError.setVisibility(View.VISIBLE);
            addressError.setText("Please Enter Your Address");
            etAddress.requestFocus();
            return;
        } else if (TextUtils.isEmpty(etPincode.getText())) {
            pincodeError.setVisibility(View.VISIBLE);
            pincodeError.setText("Please Enter Valid Area Pincode");
            etPincode.requestFocus();
            return;
        } else if (etPincode.getText().toString().replaceAll(" ", "").length() != 6) {
            pincodeError.setVisibility(View.VISIBLE);
            pincodeError.setText("Please Enter 6 Digit Area Pincode");
            etPincode.requestFocus();
            return;
        } else if (TextUtils.isEmpty(tvArea.getText())) {
            areaError.setVisibility(View.VISIBLE);
            areaError.setText("Please Select Your Area");
            tvArea.requestFocus();
            return;
        }


        ConfirmDialog();
    }


    void ConfirmDialog() {
        String fullAddress = etAddress.getText().toString().contains(etPincode.getText().toString()) ? etAddress.getText().toString() : etAddress.getText().toString() + " - " + etPincode.getText().toString();
        UtilMethods.INSTANCE.dthSubscriptionConfirmDialog(this,
                UtilMethods.INSTANCE.getDoubleFactorPref(this), ApplicationConstant.INSTANCE.baseIconUrl + operatorUrl,
                etNumber.getText().toString(), operatorSelected, UtilMethods.INSTANCE.formatedAmount(mIntentDthPackage.getPackageMRP() + ""),
                tvPackage.getText().toString(), etName.getText().toString(),
                fullAddress, new UtilMethods.DialogCallBack() {
                    @Override
                    public void onPositiveClick(String pinPass)

                    {
                        if (UtilMethods.INSTANCE.isNetworkAvialable(DTHSubscriptionActivity.this)) {
                            DthSubscription(getLattitude, getLongitude, pinPass);
                        } else {
                            UtilMethods.INSTANCE.NetworkError(DTHSubscriptionActivity.this, getResources().getString(R.string.err_msg_network_title),
                                    getResources().getString(R.string.err_msg_network));
                        }
                    }


                    @Override
                    public void onCancelClick() {

                    }


                });

    }


    void DthSubscription(double lati, double longi, String pinPass) {
        loader.show();
        loader.setCancelable(false);
        UtilMethods.INSTANCE.DTHSubscription(DTHSubscriptionActivity.this, false,
                mIntentDthPackage != null ? mIntentDthPackage.getId() : 0, etLastName.getText().toString(),
                tvGender.getText().toString(), selectedAreaId,
                operatorSelected, etName.getText().toString(), etAddress.getText().toString(),
                etPincode.getText().toString().trim(), etNumber.getText().toString().trim(),
                lati + "," + longi
                , pinPass, mIntentDthPackage, loader, new UtilMethods.ApiCallBack() {
                    @Override
                    public void onSucess(Object object) {

                        etLastName.setText("");
                        etName.setText("");
                        etNumber.setText("");
                        etAddress.setText("");
                        etPincode.setText("");
                        tvGender.setText("");
                        tvGender.setHint("Select Gender");

                    }
                });
    }

    void PincodeArea() {
        loader.show();
        UtilMethods.INSTANCE.PincodeArea(DTHSubscriptionActivity.this, etPincode.getText().toString().trim(), loader, new UtilMethods.ApiCallBack() {
            @Override
            public void onSucess(Object object) {
                PincodeAreaResponse mPincodeAreaResponse = (PincodeAreaResponse) object;
                dataOfPincode = etPincode.getText().toString();
                pincodeAreasArray = mPincodeAreaResponse.getAreas();
            }
        });
    }

    public void HitApi() {
        if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {
            UtilMethods.INSTANCE.DthSubscriptionReport(this, fromId+"","20" , 0, 0, fromDateStr, toDateStr, "", "", "", "false", true, loader, mLoginDataResponse, new UtilMethods.ApiCallBack() {
                @Override
                public void onSucess(Object object) {
                    DthSubscriptionReportResponse mRechargeReportResponse = (DthSubscriptionReportResponse) object;
                    dataParse(mRechargeReportResponse);
                }
            });

        } else {
            UtilMethods.INSTANCE.NetworkError(this, getResources().getString(R.string.err_msg_network_title),
                    getResources().getString(R.string.err_msg_network));
        }
    }


    public void dataParse(DthSubscriptionReportResponse response) {

        ArrayList<DthSubscriptionReport> transactionsObjects = response.getDTHSubscriptionReport();
        if (transactionsObjects != null && transactionsObjects.size() > 0) {
            DthSubscriptionReportAdapter mAdapter = new DthSubscriptionReportAdapter(transactionsObjects, this, mLoginDataResponse.getData().getRoleID());
            recyclerView.setAdapter(mAdapter);
            llComingSoon.setVisibility(View.GONE);
        } else {
            llComingSoon.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (reqCode == INTENT_PACKAGE && resultCode == RESULT_OK) {
            mIntentDthPackage = (DthPackage) data.getSerializableExtra("Package");
            if (mIntentDthPackage != null) {
                tvPackage.setText(mIntentDthPackage.getPackageName() + "");
                viewChannel.setVisibility(View.VISIBLE);
            }

        } else {
        }
    }



    void getDthPackage() {
        UtilMethods.INSTANCE.GetDthPackage(DTHSubscriptionActivity.this, operatorSelectedId + "", loader, object -> {
            mGetDthPackageResponse = (GetDthPackageResponse) object;

        });
    }


    private void showGenderPoupWindow(View anchor) {


        final ListPopupWindow popup = new ListPopupWindow(this);
        ListAdapter adapter = new ListPopupWindowAdapter(itemsGender, this, false, R.layout.wallet_list_popup, (name, value) -> {
            tvGender.setText(name);
            popup.dismiss();
        });
        popup.setAnchorView(anchor);
        popup.setAdapter(adapter);
        popup.setBackgroundDrawable(getResources().getDrawable(R.drawable.rect));
        popup.show();
    }

    private void showAreaPoupWindow(View anchor) {

        final ListPopupWindow popup = new ListPopupWindow(this);
        ListAdapter adapter = new ListAreaPopupWindowAdapter(pincodeAreasArray, this, R.layout.wallet_list_popup, (area, id, time) -> {
            if (time == 0) {
                tvArea.setText(area);
            } else {
                tvArea.setText(area + " (Reach Time - " + UtilMethods.INSTANCE.formatedAmount(time + "") + " Hr)");
            }

            selectedAreaId = id;
            popup.dismiss();
        });
        popup.setAnchorView(anchor);
        popup.setAdapter(adapter);
        popup.setBackgroundDrawable(getResources().getDrawable(R.drawable.rect));
        popup.show();
    }

}

