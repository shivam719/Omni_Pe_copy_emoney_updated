package com.fintech.omnipe.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.speech.tts.TextToSpeech;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.fintech.omnipe.Activities.Adapter.BillFetchInfoAdapter;
import com.fintech.omnipe.Activities.Adapter.IncentiveAdapter;
import com.fintech.omnipe.Activities.DthCustomerInfo.dto.DataRN;
import com.fintech.omnipe.Activities.DthCustomerInfo.dto.DthCIData;
import com.fintech.omnipe.Activities.DthCustomerInfo.dto.DthCustomerInfoData;
import com.fintech.omnipe.Activities.DthCustomerInfo.ui.DthInfoRNAdapter;
import com.fintech.omnipe.Activities.DthPlan.UI.DthPlanInfoNewActivity;
import com.fintech.omnipe.Api.Object.BBPSResponse;
import com.fintech.omnipe.Api.Object.IncentiveDetails;
import com.fintech.omnipe.Api.Object.OpOptionalDic;
import com.fintech.omnipe.Api.Object.OperatorParam;
import com.fintech.omnipe.Api.Request.HeavyrefreshRequest;
import com.fintech.omnipe.Api.Response.AppUserListResponse;
import com.fintech.omnipe.Api.Response.OnboardingResponse;
import com.fintech.omnipe.Fragments.dto.OperatorList;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.fintech.omnipe.Activities.DthCustomerInfo.dto.DTHInfoData;
import com.fintech.omnipe.Activities.DthCustomerInfo.dto.DTHInfoRecords;
import com.fintech.omnipe.Activities.DthCustomerInfo.dto.DTHInfoResponse;
import com.fintech.omnipe.Activities.DthPlan.UI.DthPlanInfoActivity;
import com.fintech.omnipe.Activities.DthPlan.dto.DthPlanInfoResponse;
import com.fintech.omnipe.Api.Object.OperatorOptional;
import com.fintech.omnipe.Api.Object.RechargeStatus;
import com.fintech.omnipe.Api.Request.OptionalOperatorRequest;
import com.fintech.omnipe.Api.Response.FetchBillResponse;
import com.fintech.omnipe.Api.Response.OperatorOptionalResponse;
import com.fintech.omnipe.Api.Response.RechargeReportResponse;
import com.fintech.omnipe.Util.CustomAlertDialog;
import com.fintech.omnipe.BuildConfig;
import com.fintech.omnipe.Fragments.Adapter.RechargeReportAdapter;
import com.fintech.omnipe.Auth.dto.LoginResponse;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.ActivityActivityMessage;
import com.fintech.omnipe.Util.ApiClient;
import com.fintech.omnipe.Util.ApplicationConstant;
import com.fintech.omnipe.Util.EndPointInterface;
import com.fintech.omnipe.Util.GetLocation;
import com.fintech.omnipe.Util.GlobalBus;
import com.fintech.omnipe.Util.ROfferRequest;
import com.fintech.omnipe.Util.UtilMethods;
import com.fintech.omnipe.usefull.CustomLoader;

import org.apache.commons.collections4.map.HashedMap;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SecondRechargeActivity extends AppCompatActivity implements View.OnClickListener, TextToSpeech.OnInitListener {

    DTHInfoRecords mDthInfoRecords = new DTHInfoRecords();
    private DthCIData mDthciData;
    private boolean isPlanUpdated;
    // Declare CustomLoader.....
    private CustomLoader loader;
    private ImageView operatorIcon,ivIcon,billLogo;
    private MaterialToolbar toolbar;
    private OperatorList operatorList;
    private TextView cashBackTv,tel, operator, customerName,planname, NextRechargeDate, Balance,heavyRefreshTv,MonthlyRecharge,pdfPlanTv;
    private View dthInfoDetailsRNView,dthInfoDetailsCommonView,ll_RechargeDate, ll_bal_amount, ll_customer_layout, ll_plan_name, dthInfoDetailView,switchView,amountContainerView,plansView,option1ContainerView,option2ContainerView,option3ContainerView,option4ContainerView,customerNumberContainer,amountParentView,historyView;
    private SwitchCompat realApiSwitch;
    private TextInputEditText etNumber,etCustNumber,etAmount;
    private MaterialAutoCompleteTextView etOption1,etOption2,etOption3,etOption4;
    private TextInputLayout txtOption1,txtOption2,txtOption3,txtOption4,txtMobileNo,txtCustomerNo,txtAmount;
    private RequestOptions requestOptions;
    private Double minAmountLength,maxAmountLength;
    private boolean isBilling, isRealAPI,isTakeCustomerNo,isPartial,BBPS,isFetchBill,isAccountNumeric,isCallOnBoardingSuccess;
    private String from, startWith = "",operatorSelected = "",operatorUrl,accountName,accountRemark,regExAccount,msgNumberArror,msgAmtError;
    private OnboardingResponse mOnboardingResponse;
    private ArrayList<String> startWithArray = new ArrayList<>();
    private int operatorSelectedId = 0,fromId,minNumberLength = 0, maxNumberLength = 0,incentiveOperatorSelectedId;
    private AppCompatTextView opName,viewMore,recentRechargeTv;
    private TextView numberError,AcountRemark,option1Error,option1Remark,option2Error,option2Remark,option3Error,option3Remark,option4Error,option4Remark,tvName,mobileNoError,amountError, desc,viewPlan,viewbill,billDetailTitle;
    private RecyclerView recyclerView,dthInfoRNRv;
    private AppCompatButton btRecharge;
    private int INTENT_DTH_INFO = 589;
    private int INTENT_VIEW_PLAN = 634;
    private String fromDateStr, toDateStr;
    private LoginResponse mLoginDataResponse;
    private CustomAlertDialog customAlertDialog;
    private  ArrayList<IncentiveDetails> incentiveList = new ArrayList<>();
    private Dialog incentiveDialog;

    /*--Bill Details--*/

    //Fetch Bill --
    private ArrayAdapter optionalParamsAdapter;
    private List<String> optionalParamsArray1,optionalParamsArray2,optionalParamsArray3,optionalParamsArray4;

    private View billDetailView, billDetailContent,customerNameView,billNumberView,billDateView,dueDateView,billPeriodView,payableAmtView,amountOptionView,additionalInfoBtn;
    private AppCompatTextView consumerName,billNumber,billDate,dueDate,billPeriod,payableAmt,payableAmtMore,errorMsgBilldetail,infoNameTv,infoValueTv;
    private String fetchBillRefId,optionRemarks="",optionRemarkType,regExpress1,regExpress2,regExpress3,regExpress4,optionAccountName1,optionAccountName2,optionAccountName3,optionAccountName4;
    private int minOptionNumberLength1,minOptionNumberLength2,minOptionNumberLength3,minOptionNumberLength4,maxOptionNumberLength1,maxOptionNumberLength2,maxOptionNumberLength3,maxOptionNumberLength4;
    private Integer fetchBillId;
    private int exactness;
    private boolean isParamOptionalCustomerNo,isOptionAccountNumeric1,isOptionAccountNumeric2,isOptionAccountNumeric3,isOptionAccountNumeric4,isPaymentOptionOpen,isEditable;
    private RecyclerView amountOptionListRv;

    private GetLocation mGetLocation;
    private TextToSpeech tts;
    private boolean isTTSInit;
    private String msgSpeak = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_second_recharge);
        customAlertDialog = new CustomAlertDialog(this,true);
        String LoginResponse = UtilMethods.INSTANCE.getLoginPref(this);
        mLoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
        isPlanUpdated=mLoginDataResponse.isPlanServiceUpdated();
        getIntentValues();
        getId();
        setIntentValues();
        checkOnBoarding(false);
        final Calendar myCalendar = Calendar.getInstance();
        String myFormat = "dd-MMM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        fromDateStr = sdf.format(myCalendar.getTime());
        toDateStr = sdf.format(myCalendar.getTime());
        mGetLocation = new GetLocation(this, loader);
        tts = new TextToSpeech(this, SecondRechargeActivity.this);
        if (UtilMethods.INSTANCE.getLattitude == 0 || UtilMethods.INSTANCE.getLongitude == 0) {
            mGetLocation.startLocationUpdatesIfSettingEnable((lattitude, longitude) -> {
                UtilMethods.INSTANCE.getLattitude = lattitude;
                UtilMethods.INSTANCE.getLongitude = longitude;
            });
        }
        HitApi();
    }

    private void setIntentValues() {

        opName.setText(operatorSelected+"");

        if (startWith != null && startWith.length() > 0 && startWith.contains(",")) {
            startWithArray = new ArrayList<>(Arrays.asList(startWith.trim().split(",")));
        } else if (startWith != null && !startWith.isEmpty() && !startWith.equalsIgnoreCase("0")) {
            startWithArray.add(startWith);
        }

        if (isAccountNumeric) {
            etNumber.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else {
            etNumber.setInputType(InputType.TYPE_CLASS_TEXT);
        }

        if (maxNumberLength != 0 && maxNumberLength > 0) {
            InputFilter[] filterArray = new InputFilter[1];
            filterArray[0] = new InputFilter.LengthFilter(maxNumberLength);
            etNumber.setFilters(filterArray);
        }
        if (!accountName.isEmpty()) {
            txtMobileNo.setHint(accountName + "");

        }
        if (!accountRemark.isEmpty()) {
            txtMobileNo.setHelperText(accountRemark);

        }

        requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
        requestOptions.skipMemoryCache(true);
        requestOptions.circleCrop();
        Glide.with(this).load(ApplicationConstant.INSTANCE.baseIconUrl + operatorUrl).
                apply(requestOptions).into(ivIcon);


        if(from.equalsIgnoreCase("DTH") || from.equalsIgnoreCase("FRC DTH")){
            txtMobileNo.setStartIconDrawable(R.drawable.ic_dish);
            billLogo.setVisibility(View.GONE);
            plansView.setVisibility(View.VISIBLE);
            viewPlan.setVisibility(View.VISIBLE);
            amountParentView.setVisibility(View.VISIBLE);
            btRecharge.setText("Recharge");

            if (UtilMethods.INSTANCE.getIncentiveInfoPref(this)) {
                cashBackTv.setVisibility(View.VISIBLE);

            } else {
                cashBackTv.setVisibility(View.GONE);
            }

            if (UtilMethods.INSTANCE.getIsShowPDFPlan(this)) {
                pdfPlanTv.setVisibility(View.VISIBLE);
            } else {
                pdfPlanTv.setVisibility(View.GONE);
            }

            if (mLoginDataResponse.getIsHeavyRefresh()) { //HeavY Refresh
                heavyRefreshTv.setVisibility(View.VISIBLE);
            }else {
                heavyRefreshTv.setVisibility(View.GONE);
            }
            if (UtilMethods.INSTANCE.getDTHInfoPref(this)) { //DTH Info
                viewbill.setVisibility(View.VISIBLE);
                viewbill.setText("DTH \nInfo");
            } else {
                viewbill.setVisibility(View.GONE);
            }

            if (UtilMethods.INSTANCE.getRealApiPref(this)) { //Real API
                switchView.setVisibility(View.VISIBLE);
            } else {
                switchView.setVisibility(View.GONE);
            }

        }
        else {
            plansView.setVisibility(View.GONE);
            pdfPlanTv.setVisibility(View.GONE);
            heavyRefreshTv.setVisibility(View.GONE);
            viewbill.setVisibility(View.GONE);
            viewPlan.setVisibility(View.GONE);
            switchView.setVisibility(View.GONE);
            amountParentView.setVisibility(View.VISIBLE);
            btRecharge.setText("Bill Payment");
            if(isBilling){
                isFetchBill = true;
                btRecharge.setText("Fetch Bill");
                amountContainerView.setVisibility(View.GONE);
                amountParentView.setVisibility(View.GONE);
            }else {
                isFetchBill = false;
                btRecharge.setText("Bill Payment");
                amountContainerView.setVisibility(View.VISIBLE);
                amountParentView.setVisibility(View.VISIBLE);
            }

            if(isTakeCustomerNo && UtilMethods.INSTANCE.isTakeCustomerNumber(this))
                customerNumberContainer.setVisibility(View.VISIBLE);
            else
                customerNumberContainer.setVisibility(View.GONE);

            if (BBPS) {
                billLogo.setVisibility(View.VISIBLE);
                BBPSApi();
            } else {
                billLogo.setVisibility(View.GONE);
            }
        }

    }

    private void getId() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(from + (from.equalsIgnoreCase("DTH") ? " Recharge" : " Bill Payment"));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        /*--View----*/
        realApiSwitch =findViewById(R.id.realApiSwitch);
        switchView = findViewById(R.id.switchView);
        historyView = findViewById(R.id.historyView);
        amountContainerView = findViewById(R.id.amountContainerView);

        option1ContainerView = findViewById(R.id.option1ContainerView);
        option2ContainerView = findViewById(R.id.option2ContainerView);
        option3ContainerView = findViewById(R.id.option3ContainerView);
        option4ContainerView = findViewById(R.id.option4ContainerView);
        amountParentView =findViewById(R.id.ll_customerNum);
        customerNumberContainer =  findViewById(R.id.customerNumberContainer);
        plansView =findViewById(R.id.plansView);
        /*--TextInput --*/
        txtOption1 = findViewById(R.id.til_option1);
        txtOption2 = findViewById(R.id.til_option2);
        txtOption3 = findViewById(R.id.til_option3);
        txtOption4 =  findViewById(R.id.til_option4);
        txtMobileNo =  findViewById(R.id.txt_mobileNo);
        txtCustomerNo =  findViewById(R.id.txt_customerNoView);
        txtAmount =  findViewById(R.id.txt_amt);


        /*---AutoCompleteTextView--*/
        etOption1 = findViewById(R.id.et_option1);
        etOption2 =  findViewById(R.id.et_option2);
        etOption3 = findViewById(R.id.et_option3);
        etOption4 =  findViewById(R.id.et_option4);

        /*--EditText--*/
        etCustNumber =findViewById(R.id.et_customerNo);
        etNumber = findViewById(R.id.et_number);
        etAmount =findViewById(R.id.et_amt);

        /*--TextView--*/
        opName = findViewById(R.id.opName);
        billLogo = findViewById(R.id.bill_logo);
        numberError = findViewById(R.id.numberError);
        AcountRemark = findViewById(R.id.AcountRemark);
        option1Error =  findViewById(R.id.option1_error);
        option1Remark = findViewById(R.id.option1_remark);
        option2Error =findViewById(R.id.option2_error);
        option2Remark = findViewById(R.id.option2_remark);
        option3Error = (TextView) findViewById(R.id.option3_error);
        option3Remark = (TextView) findViewById(R.id.option3_remark);
        option4Error = (TextView) findViewById(R.id.option4_error);
        option4Remark = (TextView) findViewById(R.id.option4_remark);
        customerName =findViewById(R.id.customerName);
        tvName =findViewById(R.id.tv_name);
        mobileNoError =findViewById(R.id.MobileNoError);
        desc = findViewById(R.id.desc);
        amountError =findViewById(R.id.AmountError);
        viewPlan = findViewById(R.id.viewPlan);
        viewbill = findViewById(R.id.viewbill);
        recentRechargeTv = findViewById(R.id.recentRechargeTv);
        viewMore = findViewById(R.id.viewMore);
        operator = findViewById(R.id.operator);
        heavyRefreshTv = findViewById(R.id.heavyRefreshTv);
        pdfPlanTv = findViewById(R.id.pdfPlanTv);
        cashBackTv = findViewById(R.id.cashBackTv);

        /*--ImageView--*/
        ivIcon = findViewById(R.id.iv_icon);
        operatorIcon = findViewById(R.id.operatorIcon);


        /*--RecyclerView--*/
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

            /*--Button--*/
        btRecharge =  findViewById(R.id.bt_recharge);


        /*---Bill Details----*/


        /*Bill Details View*/

        billDetailContent=findViewById(R.id.billDetailContent);
        billDetailView=findViewById(R.id.billDetailView);
        customerNameView=findViewById(R.id.customerNameView);
        billNumberView=findViewById(R.id.billNumberView);
        billDateView=findViewById(R.id.billDateView);
        dueDateView=findViewById(R.id.dueDateView);
        billPeriodView=findViewById(R.id.billPeriodView);
        payableAmtView=findViewById(R.id.payableAmtView);
        amountOptionView=findViewById(R.id.amountOptionView);
        consumerName=findViewById(R.id.consumerName);
        billNumber=findViewById(R.id.consumerNum);
        billDate=findViewById(R.id.billDate);
        dueDate=findViewById(R.id.dueDate);
        infoNameTv=findViewById(R.id.infoNameTv);
        infoValueTv=findViewById(R.id.infoValueTv);
        billPeriod=findViewById(R.id.billPeriod);
        payableAmt=findViewById(R.id.payableAmt);
        payableAmtMore=findViewById(R.id.payableAmtMore);
        additionalInfoBtn=findViewById(R.id.additionalInfoBtn);
        amountOptionListRv = findViewById(R.id.amountOptionListRv);
        errorMsgBilldetail = findViewById(R.id.errorMsgBilldetail);


        amountOptionView.setVisibility(View.GONE);
        payableAmtMore.setVisibility(View.GONE);
        additionalInfoBtn.setVisibility(View.GONE);
        payableAmtMore.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_arrow_down,0);



        /*--Plans-----*/
        tel = findViewById(R.id.tel);
        Balance = findViewById(R.id.Balance);
        planname = findViewById(R.id.planname);
        NextRechargeDate = findViewById(R.id.NextRechargeDate);
        MonthlyRecharge = findViewById(R.id.RechargeAmount);
        ll_RechargeDate = findViewById(R.id.ll_RechargeDate);
        ll_bal_amount = findViewById(R.id.ll_bal_amount);
        ll_customer_layout = findViewById(R.id.ll_customer_layout);
        ll_plan_name = findViewById(R.id.ll_plan_name);
        dthInfoDetailView = findViewById(R.id.rl_DthInfoDetail);
        dthInfoDetailsCommonView = findViewById(R.id.dthInfoDetailsCommonView);
        dthInfoDetailsRNView = findViewById(R.id.dthInfoDetailsRNView);
        dthInfoRNRv = findViewById(R.id.dthInfoRNRv);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);

        realApiSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    realApiSwitch.setTextColor(getResources().getColor(R.color.colorAccent));
                    isRealAPI=true;
                } else {
                    isRealAPI=false;
                }
            }
        });

        etAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (desc.getVisibility() == View.VISIBLE) {
                    desc.setVisibility(View.GONE);
                }
            }
        });

        etNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if ( from.equalsIgnoreCase("DTH") && s.length() == maxNumberLength) {
                    if (UtilMethods.INSTANCE.getIsDTHInfoAutoCall(SecondRechargeActivity.this)) {
                        DTHinfo();
                    }
                }
            }
        });

        setListners();

    }

    private void getIntentValues() {
        try {
            if (getIntent() != null)
                from = getIntent().getExtras().getString("from");
            fromId = getIntent().getExtras().getInt("fromId",0);
            operatorList = (OperatorList) getIntent().getSerializableExtra("operatorList");
            if(operatorList!=null){
                operatorSelected=operatorList.getName();
                operatorSelectedId= operatorList.getOid();
                isAccountNumeric=operatorList.isAccountNumeric();
                isPartial=operatorList.isPartial();
                minNumberLength=operatorList.getLength();
                maxNumberLength=operatorList.getLengthMax();
                minAmountLength= operatorList.getMin();
                maxAmountLength= operatorList.getMax();
                BBPS=operatorList.isBBPS();
                isBilling = operatorList.isBilling();
                isTakeCustomerNo = operatorList.isTakeCustomerNum();
                accountName = operatorList.getAccountName();
                accountRemark =operatorList.getAccountRemak();
                startWith =operatorList.getStartWith();
                operatorUrl =operatorList.getImage();
                regExAccount =operatorList.getRegExAccount();

            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void setListners() {
        btRecharge.setOnClickListener(this);
        viewbill.setOnClickListener(this);
        viewPlan.setOnClickListener(this);
        heavyRefreshTv.setOnClickListener(this);
        pdfPlanTv.setOnClickListener(this);
        viewMore.setOnClickListener(this);
        cashBackTv.setOnClickListener(this);
    }

    private void checkOnBoarding(boolean isLoaderShow) {
        if(BBPS){
            if(isLoaderShow)
                loader.show();
            isCallOnBoardingSuccess=false;
            mOnboardingResponse=null;
            try {
                if (UtilMethods.INSTANCE.getLattitude != 0 && UtilMethods.INSTANCE.getLongitude != 0) {
                    UtilMethods.INSTANCE.CallOnboarding(this,false, operatorSelectedId,"","0","",0,false,null,null,null,null,UtilMethods.INSTANCE.getLattitude, UtilMethods.INSTANCE.getLongitude,loader , new UtilMethods.ApiCallBackOnBoardingMethod() {
                        @Override
                        public void onSuccess(Object object) {
                            if (object != null) {
                                if(loader!=null && loader.isShowing())
                                    loader.dismiss();
                                mOnboardingResponse = (OnboardingResponse) object;
                                if (mOnboardingResponse != null) {
                                    isCallOnBoardingSuccess = true;
                                }
                            }

                        }
                        @Override
                        public void onError(Object object) {
                            mOnboardingResponse = (OnboardingResponse) object;
                            isCallOnBoardingSuccess = false;
                        }

                        @Override
                        public void onOnBoarding(Object object) {

                        }
                    });
                }
                else {
                    if (mGetLocation != null) {
                        mGetLocation.startLocationUpdates((lattitude, longitude) -> {
                            UtilMethods.INSTANCE.getLattitude = lattitude;
                            UtilMethods.INSTANCE.getLongitude = longitude;
                            UtilMethods.INSTANCE.CallOnboarding(this,false, operatorSelectedId,"","0","",0,false,null,null,null,null,UtilMethods.INSTANCE.getLattitude, UtilMethods.INSTANCE.getLongitude,loader , new UtilMethods.ApiCallBackOnBoardingMethod() {
                                @Override
                                public void onSuccess(Object object) {
                                    if (object != null) {
                                        if(loader!=null && loader.isShowing())
                                            loader.dismiss();
                                        mOnboardingResponse = (OnboardingResponse) object;
                                        if (mOnboardingResponse != null) {
                                            isCallOnBoardingSuccess = true;
                                        }
                                    }

                                }

                                @Override
                                public void onError(Object object) {
                                    mOnboardingResponse = (OnboardingResponse) object;
                                    isCallOnBoardingSuccess = false;
                                }

                                @Override
                                public void onOnBoarding(Object object) {

                                }
                            });
                        });
                    } else {
                        mGetLocation = new GetLocation(this, loader);
                        mGetLocation.startLocationUpdates((lattitude, longitude) -> {
                            UtilMethods.INSTANCE.getLattitude = lattitude;
                            UtilMethods.INSTANCE.getLongitude = longitude;
                            UtilMethods.INSTANCE.CallOnboarding(this,false, operatorSelectedId,"","0","",0,false,null,null,null,null,UtilMethods.INSTANCE.getLattitude, UtilMethods.INSTANCE.getLongitude,loader , new UtilMethods.ApiCallBackOnBoardingMethod() {
                                @Override
                                public void onSuccess(Object object) {
                                    if (object != null) {
                                        if(loader!=null && loader.isShowing())
                                            loader.dismiss();
                                        mOnboardingResponse = (OnboardingResponse) object;
                                        if (mOnboardingResponse != null) {
                                            isCallOnBoardingSuccess = true;
                                        }
                                    }

                                }

                                @Override
                                public void onError(Object object) {
                                    mOnboardingResponse = (OnboardingResponse) object;
                                    isCallOnBoardingSuccess = false;
                                }

                                @Override
                                public void onOnBoarding(Object object) {

                                }
                            });
                        });
                    }
                }
            } catch (Exception ex) {
                if(loader!=null && loader.isShowing())
                    loader.dismiss();
                isCallOnBoardingSuccess=false;
            }
        }
    }

    public void HitApi() {
        if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {

            UtilMethods.INSTANCE.LastRechargeReport(this, "0", "0", fromDateStr, toDateStr, "", "", "false", loader, new UtilMethods.ApiCallBack() {
                @Override
                public void onSucess(Object object) {
                    RechargeReportResponse mRechargeReportResponse = (RechargeReportResponse) object;
                    dataParse(mRechargeReportResponse);
                }
            });
        } else {
            UtilMethods.INSTANCE.NetworkError(this, getResources().getString(R.string.err_msg_network_title),
                    getResources().getString(R.string.err_msg_network));
        }
    }

    public void dataParse(RechargeReportResponse response) {

        ArrayList<RechargeStatus> transactionsObjects = response.getRechargeReport();
        if (transactionsObjects!=null && transactionsObjects.size() > 0) {
            historyView.setVisibility(View.VISIBLE);
            RechargeReportAdapter mAdapter = new RechargeReportAdapter(transactionsObjects, this, false);
            recyclerView.setAdapter(mAdapter);
        }
    }

    @Override
    public void onClick(View v) {
        if(v==cashBackTv){
            if (operatorSelectedId != 0) {
                if (incentiveList != null && incentiveList.size() > 0 && incentiveOperatorSelectedId == operatorSelectedId) {
                    showPopupIncentive();
                } else {
                    HitIncentiveApi();
                }
            } else {
                UtilMethods.INSTANCE.Error(this, "Please Select Operator.");
            }
        }
        else if(v==heavyRefreshTv){
            if (!validateNumber()) {
                return;
            }
            HeavyRefresh();
        }
        else if (v == viewMore) {
            Intent intent = new Intent(this, RechargeHistory.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
        else if (v == btRecharge) {
            if(BBPS ){
                if(!isCallOnBoardingSuccess && mOnboardingResponse!=null){
                    customAlertDialog = new CustomAlertDialog(this,true);
                    UtilMethods.INSTANCE.showCallOnBoardingMsgs(this, mOnboardingResponse, customAlertDialog);
                    return;
                }else if(!isCallOnBoardingSuccess && mOnboardingResponse==null){
                    checkOnBoarding(true);
                    return;
                }
            }
            if (isFetchBill) {
                if (!validateNumber()) {
                    return;
                }
                fetchBill();

            } else {
                if (!validateNumber()) {
                    return;
                } else if (!validateAmount()) {
                    return;
                }
                RechargeDialog();

            }
        }
        else if (v == viewbill) {
            if (from.equals("DTH")) {
                if (!validateNumber()) {
                    return;
                }
                DTHinfo();
            }

        }
        else if (v == viewPlan) {
            if (from.equals("DTH")) {

                if(!isPlanUpdated)
                    ViewPlan();
                else
                    ViewPlanRNDTH();
            }
        }
    }

    private void fetchBill() {
        if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {
            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);
            UtilMethods.INSTANCE.FetchBillApi(this, operatorSelectedId + "", etNumber.getText().toString().trim(), etOption1.getText().toString(), etOption2.getText().toString(), etOption3.getText().toString(), etOption4.getText().toString(), UtilMethods.INSTANCE.getLattitude + "," + UtilMethods.INSTANCE.getLongitude, "10", etCustNumber.getText().toString(),loader, new UtilMethods.ApiCallBackTwoMethod() {
                @Override
                public void onSucess(Object object) {
                    try{

                        FetchBillResponse response = (FetchBillResponse) object;
                        setBBPSUI(response);

                    }
                    catch (Exception ex)
                    {
                        billDetailView.setVisibility(View.VISIBLE);
                        billDetailContent.setVisibility(View.GONE);
                        errorMsgBilldetail.setVisibility(View.VISIBLE);
                        errorMsgBilldetail.setText(ex.getMessage());
                        isFetchBill = true;
                        amountContainerView.setVisibility(View.GONE);
                    }

                }

                @Override
                public void onError(Object object) {
                    billDetailView.setVisibility(View.VISIBLE);
                    billDetailContent.setVisibility(View.GONE);
                    errorMsgBilldetail.setVisibility(View.VISIBLE);
                    errorMsgBilldetail.setText((String)object);
                    isFetchBill = true;
                    amountContainerView.setVisibility(View.GONE);
                }

                private void setBBPSUI(FetchBillResponse response) {
                    billDetailView.setVisibility(View.VISIBLE);
                    billDetailContent.setVisibility(View.VISIBLE);
                    errorMsgBilldetail.setVisibility(View.GONE);
                    isFetchBill = false;
                    btRecharge.setText("Bill Payment");
                    BBPSResponse bbpsItem= response.getbBPSResponse();
                    fetchBillRefId = bbpsItem.getRefID()!=null && !bbpsItem.getRefID().isEmpty()?bbpsItem.getRefID():"";
                    fetchBillId=bbpsItem.getFetchBillID()!=null?bbpsItem.getFetchBillID():fetchBillId;

                    etAmount.setText(bbpsItem.getAmount()!=null && !bbpsItem.getAmount().isEmpty()?bbpsItem.getAmount():"");

                    isEditable=bbpsItem.getIseditable();
                    exactness=bbpsItem.getExactness();
                    if(bbpsItem.getCustomerName()!=null && !bbpsItem.getCustomerName().isEmpty())
                        consumerName.setText(bbpsItem.getCustomerName() + "");
                    else
                        customerNameView.setVisibility(View.GONE);

                    if(bbpsItem.getBillNumber()!=null && !bbpsItem.getBillNumber().isEmpty())
                        billNumber.setText(bbpsItem.getBillNumber() + "");
                    else
                        billNumberView.setVisibility(View.GONE);

                    if(bbpsItem.getBillDate()!=null && !bbpsItem.getBillDate().isEmpty())
                        billDate.setText(bbpsItem.getBillDate() + "");
                    else
                        billDateView.setVisibility(View.GONE);

                    if(bbpsItem.getBillPeriod()!=null && !bbpsItem.getBillPeriod().isEmpty())
                        billPeriod.setText(bbpsItem.getBillPeriod() + "");
                    else
                        billPeriodView.setVisibility(View.GONE);

                    if(bbpsItem.getDueDate()!=null && !bbpsItem.getDueDate().isEmpty())
                        dueDate.setText(bbpsItem.getDueDate() + "");
                    else
                        dueDateView.setVisibility(View.GONE);


                    if(bbpsItem.getAmount()!=null && !bbpsItem.getAmount().isEmpty())
                        payableAmt.setText(getString(R.string.rupiya) + bbpsItem.getAmount());
                    else
                    {
                        payableAmtView.setVisibility(View.GONE);
                    }

                    if(bbpsItem.getBillAmountOptions()!=null && bbpsItem.getBillAmountOptions().size()>0){
                        payableAmtView.setVisibility(View.VISIBLE);
                        payableAmtMore.setVisibility(View.VISIBLE);
                        amountOptionListRv.setLayoutManager(new LinearLayoutManager(SecondRechargeActivity.this));
                        amountOptionListRv.setAdapter(new BillFetchInfoAdapter( bbpsItem.getBillAmountOptions(),SecondRechargeActivity.this,false));
                    }else{
                        payableAmtMore.setVisibility(View.GONE);
                    }
                    if(bbpsItem.getBillAdditionalInfo()!=null && bbpsItem.getBillAdditionalInfo().size()>0){
                        additionalInfoBtn.setVisibility(View.VISIBLE);
                    }else{
                        additionalInfoBtn.setVisibility(View.GONE);
                    }

                    if (isEditable && exactness!=1) {
                        amountContainerView.setVisibility(View.VISIBLE);
                        if(amountParentView.getVisibility()==View.GONE)
                          amountParentView.setVisibility(View.VISIBLE);
                        etAmount.setEnabled(true);
                    }
                    else
                    {
                        amountParentView.setVisibility(View.VISIBLE);
                        amountContainerView.setVisibility(View.VISIBLE);
                        etAmount.setEnabled(false);
                        //amountContainerView.setVisibility(View.GONE);
                        //amountParentView.setVisibility(View.GONE);
                    }

                    payableAmtMore.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(isPaymentOptionOpen){
                                isPaymentOptionOpen=false;
                                payableAmtMore.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_arrow_down,0);
                                amountOptionView.setVisibility(View.GONE);
                            }else{
                                isPaymentOptionOpen=true;
                                payableAmtMore.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_arrow_up,0);
                                amountOptionView.setVisibility(View.VISIBLE);
                            }

                        }
                    });

                    additionalInfoBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(SecondRechargeActivity.this, BillFetchInfoScreen.class).putExtra("AdditionalInfo", (Serializable) bbpsItem.getBillAdditionalInfo()).putExtra("number",etNumber.getText().toString()).putExtra("operator",operatorSelected));
                        }
                    });
                }


            });
        }
        else {
            UtilMethods.INSTANCE.NetworkError(this, getResources().getString(R.string.err_msg_network_title),
                    getResources().getString(R.string.err_msg_network));
        }
    }

    private void RechargeDialog() {

        if (UtilMethods.INSTANCE.isVpnConnected(this)) {
            UtilMethods.INSTANCE.ProcessingWithTitle(this,"VPN Enable","Please disable VPN before using this service.");
            Toast.makeText(this, "VPN Enable", Toast.LENGTH_SHORT).show();
            return;
        }

        UtilMethods.INSTANCE.rechargeConfiormDialog(this, UtilMethods.INSTANCE.getDoubleFactorPref(this), ApplicationConstant.INSTANCE.baseIconUrl + operatorUrl, etNumber.getText().toString(), operatorSelected, etAmount.getText().toString(), new UtilMethods.DialogCallBack() {
            @Override
            public void onPositiveClick(String pinPass) {
                btRecharge.setEnabled(false);
                if (UtilMethods.INSTANCE.isNetworkAvialable(SecondRechargeActivity.this)) {
                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(false);


                    UtilMethods.INSTANCE.Recharge(SecondRechargeActivity.this, operatorSelectedId, etNumber.getText().toString().trim(), etAmount.getText().toString().trim(), etOption1.getText().toString(), etOption2.getText().toString(), etOption3.getText().toString(), etOption4.getText().toString(), etCustNumber.getText().toString(), fetchBillRefId != null ? fetchBillRefId : "", UtilMethods.INSTANCE.getLattitude + "," + UtilMethods.INSTANCE.getLongitude, pinPass, fetchBillId,isRealAPI,loader);
                } else {
                    UtilMethods.INSTANCE.NetworkError(SecondRechargeActivity.this, getResources().getString(R.string.err_msg_network_title),
                            getResources().getString(R.string.err_msg_network));
                }
            }

            @Override
            public void onCancelClick() {

            }
        });
    }


    public void DTHinfo() {
        try {
            loader.show();
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<DTHInfoResponse> call;
            if(mLoginDataResponse.isPlanServiceUpdated()){
                call = git.GetRNPDTHCustInfo(new ROfferRequest(operatorSelectedId + "", etNumber.getText().toString().trim(),
                        ApplicationConstant.INSTANCE.APP_ID,
                        UtilMethods.INSTANCE.getIMEI(SecondRechargeActivity.this),
                        "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(SecondRechargeActivity.this),
                        mLoginDataResponse.getData().getUserID(),
                        mLoginDataResponse.getData().getSessionID(),
                        mLoginDataResponse.getData().getSession(),
                        mLoginDataResponse.getData().getLoginTypeID()));
            }else{
                call = git.DTHCustomerInfo(new ROfferRequest(operatorSelectedId + "", etNumber.getText().toString().trim(),
                        ApplicationConstant.INSTANCE.APP_ID,
                        UtilMethods.INSTANCE.getIMEI(SecondRechargeActivity.this),
                        "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(SecondRechargeActivity.this),
                        mLoginDataResponse.getData().getUserID(),
                        mLoginDataResponse.getData().getSessionID(),
                        mLoginDataResponse.getData().getSession(),
                        mLoginDataResponse.getData().getLoginTypeID()));
            }

            call.enqueue(new Callback<DTHInfoResponse>() {
                @Override
                public void onResponse(Call<DTHInfoResponse> call, final retrofit2.Response<DTHInfoResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        if (response.body() != null) {
                            if (response.body().getStatuscode() != null && response.body().getStatuscode().equalsIgnoreCase("1")) {
                                if (response.body().getData() != null && response.body().getData().getRecords() != null && response.body().getData().getRecords().size() > 0) {
                                    parseDthInfoData(response.body().getData());

                                }
                                else if (response.body().getDataPA() != null && response.body().getDataPA().getStatus() == 0 && response.body().getDataPA().getData() != null) {
                                    parseDthInfoData(response.body().getDataPA());
                                }
                                else if (response.body().getDthciData() != null && response.body().getDthciData().getStatusCode()==1) {
                                    parseDthInfoRNData(response.body().getDthciData());
                                }
                            }
                            else {
                                UtilMethods.INSTANCE.Error(SecondRechargeActivity.this, response.body().getMsg() + "");
                            }
                        } else {
                            UtilMethods.INSTANCE.Error(SecondRechargeActivity.this, "DTH Info not found, Please try after some time.");
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                    }

                }

                @Override
                public void onFailure(Call<DTHInfoResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    try {
                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                            if (t.getMessage().contains("No address associated with hostname")) {
                                UtilMethods.INSTANCE.NetworkError(SecondRechargeActivity.this, getString(R.string.err_msg_network_title),
                                        getString(R.string.err_msg_network));
                            } else {
                                UtilMethods.INSTANCE.Error(SecondRechargeActivity.this, t.getMessage());

                            }

                        } else {
                            UtilMethods.INSTANCE.Error(SecondRechargeActivity.this, getString(R.string.some_thing_error));

                        }
                    } catch (IllegalStateException ise) {
                        UtilMethods.INSTANCE.Error(SecondRechargeActivity.this, ise.getMessage());

                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void HeavyRefresh() {
        try {
            loader.show();
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<DthPlanInfoResponse> call;
            if(isPlanUpdated){
                call = git.GetRNPDTHHeavyRefresh(new HeavyrefreshRequest(operatorSelectedId + "", etNumber.getText().toString(), ApplicationConstant.INSTANCE.APP_ID, UtilMethods.INSTANCE.getIMEI(this), "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(this)));
            }else{
                call = git.DTHHeavyRefresh(new HeavyrefreshRequest(operatorSelectedId + "", etNumber.getText().toString(), ApplicationConstant.INSTANCE.APP_ID, UtilMethods.INSTANCE.getIMEI(this),
                        "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(this)));
            }
            call.enqueue(new Callback<DthPlanInfoResponse>() {
                @Override
                public void onResponse(Call<DthPlanInfoResponse> call, Response<DthPlanInfoResponse> response) {
                    try {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 1) {

                                    if (response.body().getDataRP() != null && response.body().getDataRP().getRecords() != null) {
                                        if (response.body().getDataRP().getRecords().getDesc() != null) {
                                            UtilMethods.INSTANCE.Successful(SecondRechargeActivity.this, "" + response.body().getDataRP().getRecords().getDesc() + "");
                                        } else {
                                            UtilMethods.INSTANCE.Error(SecondRechargeActivity.this, "No Record Found");
                                        }
                                    }  else if (response.body().getDthhrData() != null) {
                                        if (response.body().getDthhrData().getStatusCode() == 1) {
                                            if (response.body().getDthhrData().getResponse() != null) {
                                                UtilMethods.INSTANCE.Successful(SecondRechargeActivity.this, response.body().getDthhrData().getResponse() + "");
                                            } else {
                                                UtilMethods.INSTANCE.Successful(SecondRechargeActivity.this, "Dear Customer , HR request is captured , Please Ensure your STB is Switched on");
                                            }
                                        } else {
                                            UtilMethods.INSTANCE.Error(SecondRechargeActivity.this, "No Record Found");
                                        }
                                    }else {
                                        UtilMethods.INSTANCE.Error(SecondRechargeActivity.this, "No Record Found");
                                    }
                                } else {
                                    UtilMethods.INSTANCE.Error(SecondRechargeActivity.this, "No Record Found");
                                }
                            }
                        } else {
                            UtilMethods.INSTANCE.apiErrorHandle(SecondRechargeActivity.this, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                }

                @Override
                public void onFailure(Call<DthPlanInfoResponse> call, Throwable t) {
                    if (loader != null && loader.isShowing()) {
                        loader.dismiss();
                    }
                    try {

                        UtilMethods.INSTANCE.apiFailureError(SecondRechargeActivity.this, t);


                    } catch (IllegalStateException ise) {
                        UtilMethods.INSTANCE.Error(SecondRechargeActivity.this, ise.getMessage());

                    }
                }
            });
        } catch (Exception e) {
            if (loader != null && loader.isShowing()) {
                loader.dismiss();
            }
            UtilMethods.INSTANCE.Error(SecondRechargeActivity.this, e.getMessage() + "");
        }
    }


    public void ViewPlan() {
        try {
            loader.show();
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            ROfferRequest request = new ROfferRequest(operatorSelectedId + "", etNumber.getText().toString().trim(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    UtilMethods.INSTANCE.getIMEI(SecondRechargeActivity.this),
                    "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(SecondRechargeActivity.this),
                    mLoginDataResponse.getData().getUserID(),
                    mLoginDataResponse.getData().getSessionID(),
                    mLoginDataResponse.getData().getSession(),
                    mLoginDataResponse.getData().getLoginTypeID());

            Call<DthPlanInfoResponse> call = git.DTHSimplePlanInfo(request);
            call.enqueue(new Callback<DthPlanInfoResponse>() {
                @Override
                public void onResponse(Call<DthPlanInfoResponse> call, final retrofit2.Response<DthPlanInfoResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        if (response.body() != null && response.body().getStatuscode() != null) {

                            if (response.body().getStatuscode() == 1 && response.body().getData() != null && response.body().getData().getRecords() != null) {
                                Intent planIntent = new Intent(SecondRechargeActivity.this, DthPlanInfoActivity.class);
                                planIntent.putExtra("response", response.body().getData().getRecords());
                                startActivityForResult(planIntent, INTENT_VIEW_PLAN);
                            } else if (response.body().getStatuscode() == 1 && response.body().getDataRP() != null && response.body().getDataRP().getResponse() != null && response.body().getDataRP().getResponse().size() > 0) {
                                Intent planIntent = new Intent(SecondRechargeActivity.this, DthPlanInfoActivity.class);
                                planIntent.putExtra("responseRP", response.body().getDataRP());
                                startActivityForResult(planIntent, INTENT_VIEW_PLAN);
                            } else if (response.body().getStatuscode() == 1 && response.body().getDataPA() != null && response.body().getDataPA().getRecords() != null) {
                                Intent planIntent = new Intent(SecondRechargeActivity.this, DthPlanInfoActivity.class);
                                planIntent.putExtra("responsePA", response.body().getDataPA().getRecords());
                                startActivityForResult(planIntent, INTENT_VIEW_PLAN);
                            }
                            else if (response.body().getStatuscode() == 1 && response.body().getDataRPDTHWithPackage() != null && response.body().getDataRPDTHWithPackage().getResponse() != null && response.body().getDataRPDTHWithPackage().getResponse().size() > 0) {
                                Intent planIntent = new Intent(SecondRechargeActivity.this, DthPlanInfoActivity.class);
                                planIntent.putExtra("responseRPPackage", response.body().getDataRPDTHWithPackage());
                                planIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivityForResult(planIntent, INTENT_VIEW_PLAN);
                            }
                            else if (response.body().getStatuscode() == 1 && response.body().getMyPlanData() != null) {
                                if (response.body().getMyPlanData().getStatus().equalsIgnoreCase("true")) {
                                    if (response.body().getMyPlanData().getResult() != null && response.body().getMyPlanData().getResult().getRecords() != null) {
                                        Intent planIntent = new Intent(SecondRechargeActivity.this, DthPlanInfoActivity.class);
                                        planIntent.putExtra("responseMyPlan", response.body().getMyPlanData().getResult().getRecords());
                                        startActivityForResult(planIntent, INTENT_VIEW_PLAN);
                                    } else {
                                        UtilMethods.INSTANCE.Error(SecondRechargeActivity.this, response.body().getMyPlanData().getMsg() != null ? response.body().getMyPlanData().getMsg() + "" : "Plan not found");
                                    }

                                }
                                else {
                                    UtilMethods.INSTANCE.Error(SecondRechargeActivity.this, "Plan not found");
                                }

                            } else {
                                UtilMethods.INSTANCE.Error(SecondRechargeActivity.this, "Plan not found");
                            }

                        } else {
                            UtilMethods.INSTANCE.Error(SecondRechargeActivity.this, "Something went wrong, try after some time.");
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                    }

                }

                @Override
                public void onFailure(Call<DthPlanInfoResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    try {
                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                            if (t.getMessage().contains("No address associated with hostname")) {
                                UtilMethods.INSTANCE.NetworkError(SecondRechargeActivity.this, getString(R.string.err_msg_network_title), getString(R.string.err_msg_network));
                            } else {
                                UtilMethods.INSTANCE.Error(SecondRechargeActivity.this, t.getMessage());

                            }

                        } else {
                            UtilMethods.INSTANCE.Error(SecondRechargeActivity.this, getString(R.string.some_thing_error));

                        }
                    } catch (IllegalStateException ise) {
                        UtilMethods.INSTANCE.Error(SecondRechargeActivity.this, ise.getMessage());

                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ViewPlanRNDTH() {

        Intent planIntent = new Intent(this, DthPlanInfoNewActivity.class);
        planIntent.putExtra("OperatorSelectedId", operatorSelectedId + "");
        planIntent.putExtra("Number", etNumber.getText().toString().trim());
        planIntent.putExtra("IsPlanServiceUpdated", isPlanUpdated);
        planIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivityForResult(planIntent, INTENT_VIEW_PLAN);
       /* try {
            loader.show();
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<DthPlanInfoAllResponse> call = git.GetDTHSimplePlan(new DTHSimplePlanRequest(operatorSelectedId + "", ApplicationConstant.INSTANCE.APP_ID, UtilMethods.INSTANCE.getIMEI(SecondRechargeActivity.this),
                    "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(SecondRechargeActivity.this),mLoginDataResponse.getData().getLoginTypeID()));;


            call.enqueue(new Callback<DthPlanInfoAllResponse>() {
                @Override
                public void onResponse(Call<DthPlanInfoAllResponse> call, final retrofit2.Response<DthPlanInfoAllResponse> response) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    try {

                        if (response.body() != null && response.body().getStatuscode() != null) {

                            if (response.body().getStatuscode() == 1 && response.body().getDataList() != null && response.body().getDataList().size()>0) {
                                Intent planIntent = new Intent(SecondRechargeActivity.this, DthPlanInfoActivity.class);
                                planIntent.putExtra("responsePlanUpdated", response.body());
                                startActivityForResult(planIntent, INTENT_VIEW_PLAN);
                            } else {
                                UtilMethods.INSTANCE.Error(SecondRechargeActivity.this, "Plan not found");
                            }

                        } else {
                            UtilMethods.INSTANCE.Error(SecondRechargeActivity.this, "Something went wrong, try after some time.");
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                    }

                }

                @Override
                public void onFailure(Call<DthPlanInfoAllResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    try {
                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                            if (t.getMessage().contains("No address associated with hostname")) {
                                UtilMethods.INSTANCE.NetworkError(SecondRechargeActivity.this, getString(R.string.err_msg_network_title),
                                        getString(R.string.err_msg_network));
                            } else {
                                UtilMethods.INSTANCE.Error(SecondRechargeActivity.this, t.getMessage());

                            }

                        } else {
                            UtilMethods.INSTANCE.Error(SecondRechargeActivity.this, getString(R.string.some_thing_error));

                        }
                    } catch (IllegalStateException ise) {
                        UtilMethods.INSTANCE.Error(SecondRechargeActivity.this, ise.getMessage());

                    }

                }
            });

        }
        catch (Exception e) {
            e.printStackTrace();
        }*/
    }


    public void BBPSApi() {
        try {
            loader.show();
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);

            Call<OperatorOptionalResponse> call = git.GetOperatorOptionals(new OptionalOperatorRequest(operatorSelectedId + "",
                    mLoginDataResponse.getData().getUserID(), mLoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    UtilMethods.INSTANCE.getIMEI(this),
                    "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(this), mLoginDataResponse.getData().getSessionID(), mLoginDataResponse.getData().getSession()));


            call.enqueue(new Callback<OperatorOptionalResponse>() {
                @Override
                public void onResponse(Call<OperatorOptionalResponse> call, final retrofit2.Response<OperatorOptionalResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        OperatorOptionalResponse mOperatorOptionalResponse = response.body();
                        if (mOperatorOptionalResponse != null && mOperatorOptionalResponse.getStatuscode() != null) {

                            if (mOperatorOptionalResponse.getStatuscode() == 1) {
                                if (mOperatorOptionalResponse.getData() != null && mOperatorOptionalResponse.getData().getOperatorParams() != null && mOperatorOptionalResponse.getData().getOperatorParams().size() > 0 ) {
                                    hideShowParameter(mOperatorOptionalResponse.getData().getOperatorParams(),mOperatorOptionalResponse.getData().getOpOptionalDic());
                                } else if (mOperatorOptionalResponse.getData() != null && mOperatorOptionalResponse.getData().getOperatorOptionals() != null && mOperatorOptionalResponse.getData().getOperatorOptionals().size() > 0) {
                                    hideShowOptionalParameter(mOperatorOptionalResponse.getData().getOperatorOptionals());
                                }
                            } else {
                                UtilMethods.INSTANCE.Error(SecondRechargeActivity.this, mOperatorOptionalResponse.getMsg() + "");
                            }

                        } else {
                            UtilMethods.INSTANCE.Error(SecondRechargeActivity.this, "Something went wrong, try after some time.");
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                    }

                }

                @Override
                public void onFailure(Call<OperatorOptionalResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    try {
                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                            if (t.getMessage().contains("No address associated with hostname")) {
                                UtilMethods.INSTANCE.NetworkError(SecondRechargeActivity.this, getString(R.string.err_msg_network_title),
                                        getString(R.string.err_msg_network));
                            } else {
                                UtilMethods.INSTANCE.Error(SecondRechargeActivity.this, t.getMessage());

                            }

                        } else {
                            UtilMethods.INSTANCE.Error(SecondRechargeActivity.this, getString(R.string.some_thing_error));

                        }
                    } catch (IllegalStateException ise) {
                        UtilMethods.INSTANCE.Error(SecondRechargeActivity.this, ise.getMessage());

                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void hideShowOptionalParameter(List<OperatorOptional> mOperatorOptionals) {
        for (int i = 0; i < mOperatorOptionals.size(); i++) {
            if (mOperatorOptionals.get(i).getOptionalType() == 1) {
                option1ContainerView.setVisibility(View.VISIBLE);
                txtOption1.setHint(mOperatorOptionals.get(i).getDisplayName() + "");
                txtOption1.setHelperText(mOperatorOptionals.get(i).getRemark() + "");
                txtOption1.setEndIconMode(TextInputLayout.END_ICON_CLEAR_TEXT);

            }
            if (mOperatorOptionals.get(i).getOptionalType() == 2) {
                option2ContainerView.setVisibility(View.VISIBLE);
                txtOption2.setHint(mOperatorOptionals.get(i).getDisplayName() + "");
                txtOption2.setHelperText(mOperatorOptionals.get(i).getRemark() + "");
                txtOption2.setEndIconMode(TextInputLayout.END_ICON_CLEAR_TEXT);
            }
            if (mOperatorOptionals.get(i).getOptionalType() == 3) {
                option3ContainerView.setVisibility(View.VISIBLE);
                txtOption3.setHint(mOperatorOptionals.get(i).getDisplayName() + "");
                txtOption3.setHelperText(mOperatorOptionals.get(i).getRemark() + "");
                txtOption3.setEndIconMode(TextInputLayout.END_ICON_CLEAR_TEXT);
            }
            if (mOperatorOptionals.get(i).getOptionalType() == 4) {
                option4ContainerView.setVisibility(View.VISIBLE);
                txtOption4.setHint(mOperatorOptionals.get(i).getDisplayName() + "");
                txtOption4.setHelperText(mOperatorOptionals.get(i).getRemark() + "");
                txtOption4.setEndIconMode(TextInputLayout.END_ICON_CLEAR_TEXT);
            }
        }
    }

    private void hideShowParameter(List<OperatorParam> mOperatorOptionals,List<OpOptionalDic> opOptionalDic){
        for (int i = 0; i < mOperatorOptionals.size(); i++) {

            if(!isParamOptionalCustomerNo && mOperatorOptionals.get(i).isCustomerNo()){
                isParamOptionalCustomerNo=true;
            }

            if (i==0) {
                option1ContainerView.setVisibility(View.VISIBLE);
                optionalParamsArray1=new ArrayList<>();
                if(mOperatorOptionals.get(i).isDropDown() && opOptionalDic!=null && opOptionalDic.size()>0){
                    for(OpOptionalDic optionalDic:opOptionalDic){
                        if(optionalDic.getOptionalID()==mOperatorOptionals.get(i).getId()){
                            optionalParamsArray1.add(optionalDic.getValue());
                        }
                    }

                    setOptionAdapter(optionalParamsArray1,etOption1,txtOption1);

                }else{
                    txtOption1.setEndIconMode(TextInputLayout.END_ICON_CLEAR_TEXT);
                }

                regExpress1=mOperatorOptionals.get(i).getRegEx()!=null && !mOperatorOptionals.get(i).getRegEx().isEmpty()?mOperatorOptionals.get(i).getRegEx():"";
                minOptionNumberLength1=mOperatorOptionals.get(i).getMinLength();
                maxOptionNumberLength1=mOperatorOptionals.get(i).getMaxLength();
                if(mOperatorOptionals.get(i).getDataType()!=null && !mOperatorOptionals.get(i).getDataType().isEmpty() && mOperatorOptionals.get(i).getDataType().equalsIgnoreCase("NUMERIC")){
                    optionRemarkType=" Digits ";
                    isOptionAccountNumeric1=true;
                }
                else if(mOperatorOptionals.get(i).getDataType()!=null && !mOperatorOptionals.get(i).getDataType().isEmpty()){
                    optionRemarkType=" Characters ";
                    isOptionAccountNumeric1=false;
                }
                txtOption1.setHint(mOperatorOptionals.get(i).getParamName()+"");
                optionAccountName1=mOperatorOptionals.get(i).getParamName();
                if(mOperatorOptionals.get(i).getRemark()!=null && !mOperatorOptionals.get(i).getRemark().isEmpty())
                    optionRemarks=mOperatorOptionals.get(i).getRemark();
                else
                {
                    if(mOperatorOptionals.get(i).getParamName()!=null && ! !mOperatorOptionals.get(i).getParamName().isEmpty()){
                        if(mOperatorOptionals.get(i).getMinLength()!=0 && mOperatorOptionals.get(i).getMaxLength()!=0 && mOperatorOptionals.get(i).getMinLength()==mOperatorOptionals.get(i).getMaxLength()){
                            optionRemarks=mOperatorOptionals.get(i).getParamName()+ " Unit ( " + mOperatorOptionals.get(i).getMaxLength() + " ) " + optionRemarkType;
                        }else if(mOperatorOptionals.get(i).getMinLength()!=0 && mOperatorOptionals.get(i).getMaxLength()!=0 && mOperatorOptionals.get(i).getMinLength()!=mOperatorOptionals.get(i).getMaxLength()){
                            optionRemarks=mOperatorOptionals.get(i).getParamName()+ " Unit ( " + mOperatorOptionals.get(i).getMinLength() + "-" + mOperatorOptionals.get(i).getMaxLength() + " ) " + optionRemarkType;

                        }else if(mOperatorOptionals.get(i).getMinLength()==0 && mOperatorOptionals.get(i).getMaxLength()!=0 ){
                            optionRemarks=mOperatorOptionals.get(i).getParamName()+ " Unit ( " + mOperatorOptionals.get(i).getMaxLength() + " ) " + optionRemarkType;
                        }else if(mOperatorOptionals.get(i).getMinLength()!=0 && mOperatorOptionals.get(i).getMaxLength()==0 ){
                            optionRemarks=mOperatorOptionals.get(i).getParamName()+ " Unit ( " + mOperatorOptionals.get(i).getMinLength() + " ) " + optionRemarkType;
                        }else{
                            optionRemarks=mOperatorOptionals.get(i).getParamName()+ " Unit";
                        }

                    }
                }
                txtOption1.setHelperText(optionRemarks);


            }
            if (/*mOperatorOptionals.get(i).getInd() == 2*/ i==1) {
                option2ContainerView.setVisibility(View.VISIBLE);
                optionalParamsArray1=new ArrayList<>();
                if(mOperatorOptionals.get(i).isDropDown() && opOptionalDic!=null && opOptionalDic.size()>0){
                    for(OpOptionalDic optionalDic:opOptionalDic){
                        if(optionalDic.getOptionalID()==mOperatorOptionals.get(i).getId()){
                            optionalParamsArray1.add(optionalDic.getValue());
                        }
                    }

                    setOptionAdapter(optionalParamsArray1,etOption2,txtOption2);

                }else{
                    txtOption2.setEndIconMode(TextInputLayout.END_ICON_CLEAR_TEXT);
                }

                regExpress2=mOperatorOptionals.get(i).getRegEx()!=null && !mOperatorOptionals.get(i).getRegEx().isEmpty()?mOperatorOptionals.get(i).getRegEx():"";
                minOptionNumberLength2=mOperatorOptionals.get(i).getMinLength();
                maxOptionNumberLength2=mOperatorOptionals.get(i).getMaxLength();
                if(mOperatorOptionals.get(i).getDataType()!=null && !mOperatorOptionals.get(i).getDataType().isEmpty() && mOperatorOptionals.get(i).getDataType().equalsIgnoreCase("NUMERIC")){
                    optionRemarkType=" Digits ";
                    isOptionAccountNumeric2=true;
                }
                else if(mOperatorOptionals.get(i).getDataType()!=null && !mOperatorOptionals.get(i).getDataType().isEmpty()){
                    optionRemarkType=" Characters ";
                    isOptionAccountNumeric2=false;
                }
                txtOption2.setHint(mOperatorOptionals.get(i).getParamName()+"");
                optionAccountName2=mOperatorOptionals.get(i).getParamName();
                if(mOperatorOptionals.get(i).getRemark()!=null && !mOperatorOptionals.get(i).getRemark().isEmpty())
                    optionRemarks=mOperatorOptionals.get(i).getRemark();
                else
                {
                    if(mOperatorOptionals.get(i).getParamName()!=null && ! !mOperatorOptionals.get(i).getParamName().isEmpty()){
                        if(mOperatorOptionals.get(i).getMinLength()!=0 && mOperatorOptionals.get(i).getMaxLength()!=0 && mOperatorOptionals.get(i).getMinLength()==mOperatorOptionals.get(i).getMaxLength()){
                            optionRemarks=mOperatorOptionals.get(i).getParamName()+ " Unit ( " + mOperatorOptionals.get(i).getMaxLength() + " ) " + optionRemarkType;
                        }else if(mOperatorOptionals.get(i).getMinLength()!=0 && mOperatorOptionals.get(i).getMaxLength()!=0 && mOperatorOptionals.get(i).getMinLength()!=mOperatorOptionals.get(i).getMaxLength()){
                            optionRemarks=mOperatorOptionals.get(i).getParamName()+ " Unit ( " + mOperatorOptionals.get(i).getMinLength() + "-" + mOperatorOptionals.get(i).getMaxLength() + " ) " + optionRemarkType;

                        }else if(mOperatorOptionals.get(i).getMinLength()==0 && mOperatorOptionals.get(i).getMaxLength()!=0 ){
                            optionRemarks=mOperatorOptionals.get(i).getParamName()+ " Unit ( " + mOperatorOptionals.get(i).getMaxLength() + " ) " + optionRemarkType;
                        }else if(mOperatorOptionals.get(i).getMinLength()!=0 && mOperatorOptionals.get(i).getMaxLength()==0 ){
                            optionRemarks=mOperatorOptionals.get(i).getParamName()+ " Unit ( " + mOperatorOptionals.get(i).getMinLength() + " ) " + optionRemarkType;
                        }else {
                            optionRemarks=mOperatorOptionals.get(i).getParamName()+ " Unit";
                        }

                    }
                }
                txtOption2.setHelperText(optionRemarks);

            }
            if (/*mOperatorOptionals.get(i).getInd() == 3*/ i==2) {
                option3ContainerView.setVisibility(View.VISIBLE);
                optionalParamsArray1=new ArrayList<>();
                if(mOperatorOptionals.get(i).isDropDown() && opOptionalDic!=null && opOptionalDic.size()>0){
                    for(OpOptionalDic optionalDic: opOptionalDic){
                        if(optionalDic.getOptionalID()==mOperatorOptionals.get(i).getId()){
                            optionalParamsArray1.add(optionalDic.getValue());
                        }
                    }

                    setOptionAdapter(optionalParamsArray1,etOption3,txtOption3);

                }else{
                    txtOption3.setEndIconMode(TextInputLayout.END_ICON_CLEAR_TEXT);
                }
                regExpress3=mOperatorOptionals.get(i).getRegEx()!=null && !mOperatorOptionals.get(i).getRegEx().isEmpty()?mOperatorOptionals.get(i).getRegEx():"";
                minOptionNumberLength3=mOperatorOptionals.get(i).getMinLength();
                maxOptionNumberLength3=mOperatorOptionals.get(i).getMaxLength();
                if(mOperatorOptionals.get(i).getDataType()!=null && !mOperatorOptionals.get(i).getDataType().isEmpty() && mOperatorOptionals.get(i).getDataType().equalsIgnoreCase("NUMERIC")){
                    optionRemarkType=" Digits ";
                    isOptionAccountNumeric3=true;
                }
                else if(mOperatorOptionals.get(i).getDataType()!=null && !mOperatorOptionals.get(i).getDataType().isEmpty()){
                    optionRemarkType=" Characters ";
                    isOptionAccountNumeric3=false;
                }
                txtOption3.setHint(mOperatorOptionals.get(i).getParamName()+"");
                optionAccountName3=mOperatorOptionals.get(i).getParamName();
                if(mOperatorOptionals.get(i).getRemark()!=null && !mOperatorOptionals.get(i).getRemark().isEmpty())
                    optionRemarks=mOperatorOptionals.get(i).getRemark();
                else
                {
                    if(mOperatorOptionals.get(i).getParamName()!=null && ! !mOperatorOptionals.get(i).getParamName().isEmpty()){
                        if(mOperatorOptionals.get(i).getMinLength()!=0 && mOperatorOptionals.get(i).getMaxLength()!=0 && mOperatorOptionals.get(i).getMinLength()==mOperatorOptionals.get(i).getMaxLength()){
                            optionRemarks=mOperatorOptionals.get(i).getParamName()+ " Unit ( " + mOperatorOptionals.get(i).getMaxLength() + " ) " + optionRemarkType;
                        }else if(mOperatorOptionals.get(i).getMinLength()!=0 && mOperatorOptionals.get(i).getMaxLength()!=0 && mOperatorOptionals.get(i).getMinLength()!=mOperatorOptionals.get(i).getMaxLength()){
                            optionRemarks=mOperatorOptionals.get(i).getParamName()+ " Unit ( " + mOperatorOptionals.get(i).getMinLength() + "-" + mOperatorOptionals.get(i).getMaxLength() + " ) " + optionRemarkType;

                        }else if(mOperatorOptionals.get(i).getMinLength()==0 && mOperatorOptionals.get(i).getMaxLength()!=0 ){
                            optionRemarks=mOperatorOptionals.get(i).getParamName()+ " Unit ( " + mOperatorOptionals.get(i).getMaxLength() + " ) " + optionRemarkType;
                        }else if(mOperatorOptionals.get(i).getMinLength()!=0 && mOperatorOptionals.get(i).getMaxLength()==0 ){
                            optionRemarks=mOperatorOptionals.get(i).getParamName()+ " Unit ( " + mOperatorOptionals.get(i).getMinLength() + " ) " + optionRemarkType;
                        }else {
                            optionRemarks=mOperatorOptionals.get(i).getParamName()+ " Unit";
                        }

                    }
                }
                txtOption3.setHelperText(optionRemarks);

            }
            if (/*mOperatorOptionals.get(i).getInd() == 4*/ i==3) {
                option4ContainerView.setVisibility(View.VISIBLE);
                optionalParamsArray1=new ArrayList<>();
                if(mOperatorOptionals.get(i).isDropDown() && opOptionalDic!=null && opOptionalDic.size()>0){
                    for(OpOptionalDic optionalDic: opOptionalDic){
                        if(optionalDic.getOptionalID()==mOperatorOptionals.get(i).getId()){
                            optionalParamsArray1.add(optionalDic.getValue());
                        }
                    }

                    setOptionAdapter(optionalParamsArray1,etOption4,txtOption4);

                }else{
                    txtOption4.setEndIconMode(TextInputLayout.END_ICON_CLEAR_TEXT);
                }
                regExpress4=mOperatorOptionals.get(i).getRegEx()!=null && !mOperatorOptionals.get(i).getRegEx().isEmpty()?mOperatorOptionals.get(i).getRegEx():"";
                minOptionNumberLength4=mOperatorOptionals.get(i).getMinLength();
                maxOptionNumberLength4=mOperatorOptionals.get(i).getMaxLength();
                if(mOperatorOptionals.get(i).getDataType()!=null && !mOperatorOptionals.get(i).getDataType().isEmpty() && mOperatorOptionals.get(i).getDataType().equalsIgnoreCase("NUMERIC")){
                    optionRemarkType=" Digits ";
                    isOptionAccountNumeric4=true;
                }
                else if(mOperatorOptionals.get(i).getDataType()!=null && !mOperatorOptionals.get(i).getDataType().isEmpty()){
                    optionRemarkType=" Characters ";
                    isOptionAccountNumeric4=false;
                }
                txtOption4.setHint(mOperatorOptionals.get(i).getParamName()+"");
                optionAccountName4=mOperatorOptionals.get(i).getParamName();
                if(mOperatorOptionals.get(i).getRemark()!=null && !mOperatorOptionals.get(i).getRemark().isEmpty())
                    optionRemarks=mOperatorOptionals.get(i).getRemark();
                else
                {
                    if(mOperatorOptionals.get(i).getParamName()!=null && ! !mOperatorOptionals.get(i).getParamName().isEmpty()){
                        if(mOperatorOptionals.get(i).getMinLength()!=0 && mOperatorOptionals.get(i).getMaxLength()!=0 && mOperatorOptionals.get(i).getMinLength()==mOperatorOptionals.get(i).getMaxLength()){
                            optionRemarks=mOperatorOptionals.get(i).getParamName()+ " Unit ( " + mOperatorOptionals.get(i).getMaxLength() + " ) " + optionRemarkType;
                        }else if(mOperatorOptionals.get(i).getMinLength()!=0 && mOperatorOptionals.get(i).getMaxLength()!=0 && mOperatorOptionals.get(i).getMinLength()!=mOperatorOptionals.get(i).getMaxLength()){
                            optionRemarks=mOperatorOptionals.get(i).getParamName()+ " Unit ( " + mOperatorOptionals.get(i).getMinLength() + "-" + mOperatorOptionals.get(i).getMaxLength() + " ) " + optionRemarkType;

                        }else if(mOperatorOptionals.get(i).getMinLength()==0 && mOperatorOptionals.get(i).getMaxLength()!=0 ){
                            optionRemarks=mOperatorOptionals.get(i).getParamName()+ " Unit ( " + mOperatorOptionals.get(i).getMaxLength() + " ) " + optionRemarkType;
                        }else if(mOperatorOptionals.get(i).getMinLength()!=0 && mOperatorOptionals.get(i).getMaxLength()==0 ){
                            optionRemarks=mOperatorOptionals.get(i).getParamName()+ " Unit ( " + mOperatorOptionals.get(i).getMinLength() + " ) " + optionRemarkType;
                        }else{
                            optionRemarks=mOperatorOptionals.get(i).getParamName()+ " Unit";
                        }

                    }
                }
                txtOption4.setHelperText(optionRemarks);

            }
        }

         /*if(isParamOptionalCustomerNo)
            customerNumberContainer.setVisibility(View.VISIBLE);
        else
        {
            if(UtilMethods.INSTANCE.isTakeCustomerNumber(this) && isTakeCustomerNo)
            {
                customerNumberContainer.setVisibility(View.VISIBLE);
            }else{
                customerNumberContainer.setVisibility(View.GONE);
            }
        }*/

        if (!isParamOptionalCustomerNo) {
            if (UtilMethods.INSTANCE.isTakeCustomerNumber(this) && isTakeCustomerNo) {
                customerNumberContainer.setVisibility(View.VISIBLE);
            } else {
                customerNumberContainer.setVisibility(View.GONE);
            }
        } else {
            customerNumberContainer.setVisibility(View.VISIBLE);
        }

    }

    private void setOptionAdapter(List<String> optionalParamsArray, MaterialAutoCompleteTextView etOption,TextInputLayout txtOption) {

        /*etOption.setEnabled(false);*/
        //etOption.showDropDown();
        txtOption.setEndIconMode(TextInputLayout.END_ICON_DROPDOWN_MENU);
        etOption.setText(optionalParamsArray.get(0), false);
        optionalParamsAdapter = new ArrayAdapter<String>(this, R.layout.text_input_spinner_item, optionalParamsArray);
        /*optionalParamsAdapter.setDropDownViewResource(R.layout.text_input_spinner_item);*/
        etOption.setAdapter(optionalParamsAdapter);
    }


    private boolean validateNumber() {

        txtMobileNo.setErrorEnabled(false);
        txtOption1.setErrorEnabled(false);
        txtOption2.setErrorEnabled(false);
        txtOption3.setErrorEnabled(false);
        txtOption4.setErrorEnabled(false);
        txtCustomerNo.setErrorEnabled(false);

        if (etNumber.getText().toString().trim().isEmpty()) {
            msgNumberArror = "Please enter a valid " + accountName.trim();
            txtMobileNo.setError(msgNumberArror);
            etNumber.requestFocus();
            return false;
        }
        else if (startWithArray != null && startWithArray.size() > 0 && !checkContains(startWithArray, etNumber.getText().toString().trim())) {
            msgNumberArror = accountName.trim() + " must be start with " + startWith;
            txtMobileNo.setError(msgNumberArror);
            etNumber.requestFocus();
            return false;

        }else if (minNumberLength != 0 && maxNumberLength != 0 && minNumberLength != maxNumberLength && etNumber.getText().length() < minNumberLength) {
            txtMobileNo.setError(accountName.trim() + " should be length of " + minNumberLength + " to " + maxNumberLength);
            etNumber.requestFocus();
            return false;
        }
        else if (minNumberLength != 0 && maxNumberLength != 0 && minNumberLength != maxNumberLength && etNumber.getText().length() > maxNumberLength) {
            txtMobileNo.setError(accountName.trim() + " should be length of " + minNumberLength + " to " + maxNumberLength);
            txtMobileNo.setVisibility(View.VISIBLE);
            etNumber.requestFocus();
            return false;
        }
        else if (minNumberLength != 0 && maxNumberLength != 0 && minNumberLength == maxNumberLength && etNumber.getText().length() != maxNumberLength) {
            txtMobileNo.setError(accountName.trim() + " should be length of " + maxNumberLength);
            txtMobileNo.setVisibility(View.VISIBLE);
            etNumber.requestFocus();
            return false;
        }else if (minNumberLength != 0 && maxNumberLength == 0 && etNumber.getText().length() != minNumberLength) {
            txtMobileNo.setError(accountName.trim() + " should be length of " + minNumberLength);
            txtMobileNo.setVisibility(View.VISIBLE);
            etNumber.requestFocus();
            return false;
        }else if (minNumberLength == 0 && maxNumberLength != 0 && etNumber.getText().length() != maxNumberLength) {
            txtMobileNo.setError(accountName.trim() + " should be length of " + maxNumberLength);
            txtMobileNo.setVisibility(View.VISIBLE);
            etNumber.requestFocus();
            return false;
        } else if(regExAccount!=null && !regExAccount.isEmpty()){
            Pattern pattern = Pattern.compile(regExAccount); // Compile the ReGex
            // Pattern class contains matcher() method // to find matching between given username // and regular expression.
            Matcher matcher= pattern.matcher(etNumber.getText().toString());
            if(!matcher.matches()){ // Return if the username,matched the ReGex
                msgNumberArror ="Please Enter a Valid " + accountName.trim();
                txtMobileNo.setError(msgNumberArror);
                etNumber.requestFocus();
                return false;
            }
        }else if (option1ContainerView.getVisibility() == View.VISIBLE && etOption1.getText().toString().trim().isEmpty()) {
            msgNumberArror = txtOption1.getHint() + " field can't be empty";
            txtOption1.setError(msgNumberArror);
            etOption1.requestFocus();
            return false;
        } else if (option1ContainerView.getVisibility() == View.VISIBLE && minOptionNumberLength1 != 0 && maxOptionNumberLength1 != 0 && minOptionNumberLength1 != maxOptionNumberLength1 && etOption1.getText().length() < minOptionNumberLength1) {
            /*msgNumberArror="Invalid Length Of " + {Account Number} + " Please Enter A Valid " + minOptionNumberLength1+  {Account Number};*/
            msgNumberArror = optionAccountName1.trim() + " must be length of " + minOptionNumberLength1 + " to " + maxOptionNumberLength1 + (isOptionAccountNumeric1 ? " digits" : " characters");
            txtOption1.setError(msgNumberArror);
            etOption1.requestFocus();
            return false;
        } else if (option1ContainerView.getVisibility() == View.VISIBLE && minOptionNumberLength1 != 0 && maxOptionNumberLength1 != 0 && minOptionNumberLength1 != maxOptionNumberLength1 && etOption1.getText().length() > maxOptionNumberLength1) {
            msgNumberArror = optionAccountName1.trim() + " must be length of " + minOptionNumberLength1 + " to " + maxOptionNumberLength1 + (isOptionAccountNumeric1 ? " digits" : " characters");
            txtOption1.setError(msgNumberArror);
            etOption1.requestFocus();
            return false;
        } else if (option1ContainerView.getVisibility() == View.VISIBLE && minOptionNumberLength1 != 0 && maxOptionNumberLength1 != 0 && minOptionNumberLength1 == maxOptionNumberLength1 && etOption1.getText().length() != maxOptionNumberLength1) {
            msgNumberArror = optionAccountName1.trim() + " must be length of " + maxOptionNumberLength1 + (isOptionAccountNumeric1 ? " digits" : " characters");
            txtOption1.setError(msgNumberArror);
            etOption1.requestFocus();
            return false;
        } else if (option1ContainerView.getVisibility() == View.VISIBLE && minOptionNumberLength1 != 0 && maxOptionNumberLength1 == 0 && etOption1.getText().length() != minOptionNumberLength1) {
            msgNumberArror = optionAccountName1.trim() + " must be length of " + minOptionNumberLength1 + (isOptionAccountNumeric1 ? " digits" : " characters");
            txtOption1.setError(msgNumberArror);
            etOption1.requestFocus();
            return false;
        } else if (option1ContainerView.getVisibility() == View.VISIBLE && minOptionNumberLength1 == 0 && maxOptionNumberLength1 != 0 && etOption1.getText().length() != maxOptionNumberLength1) {
            msgNumberArror = optionAccountName1.trim() + " must be length of " + maxOptionNumberLength1 + (isOptionAccountNumeric1 ? " digits" : " characters");
            txtOption1.setError(msgNumberArror);
            etOption1.requestFocus();
            return false;
        }  else if(option1ContainerView.getVisibility() == View.VISIBLE && regExpress1!=null && Pattern.compile(regExpress1).matcher(etOption1.getText().toString()).matches()==false){
            msgNumberArror ="Please Enter a Valid " + optionAccountName1.trim();
            txtOption1.setError(msgNumberArror);
            etOption1.requestFocus();
            return false;
        }  /*----Option 2*/
        else if (option2ContainerView.getVisibility() == View.VISIBLE && etOption2.getText().toString().trim().isEmpty()) {
            msgNumberArror = txtOption2.getHint() + " field can't be empty";
            txtOption2.setError(msgNumberArror);
            etOption2.requestFocus();
            return false;
        } else if (option2ContainerView.getVisibility() == View.VISIBLE && minOptionNumberLength2 != 0 && maxOptionNumberLength2 != 0 && minOptionNumberLength2 != maxOptionNumberLength2 && etOption2.getText().length() < minOptionNumberLength2) {
            msgNumberArror = optionAccountName2.trim() + " must be length of " + minOptionNumberLength2 + " to " + maxOptionNumberLength2 + (isOptionAccountNumeric2 ? " digits" : " characters");
            txtOption2.setError(msgNumberArror);
            etOption2.requestFocus();
            return false;
        } else if (option2ContainerView.getVisibility() == View.VISIBLE && minOptionNumberLength2 != 0 && maxOptionNumberLength2 != 0 && minOptionNumberLength2 != maxOptionNumberLength2 && etOption2.getText().length() > maxOptionNumberLength2) {
            msgNumberArror = optionAccountName2.trim() + " must be length of " + minOptionNumberLength2 + " to " + maxOptionNumberLength2 + (isOptionAccountNumeric2 ? " digits" : " characters");
            txtOption2.setError(msgNumberArror);
            etOption2.requestFocus();
            return false;
        } else if (option2ContainerView.getVisibility() == View.VISIBLE && minOptionNumberLength2 != 0 && maxOptionNumberLength2 != 0 && minOptionNumberLength2 == maxOptionNumberLength2 && etOption2.getText().length() != maxOptionNumberLength2) {
            msgNumberArror = optionAccountName2.trim() + " must be length of " + maxOptionNumberLength2 + (isOptionAccountNumeric2 ? " digits" : " characters");
            txtOption2.setError(msgNumberArror);
            etOption2.requestFocus();
            return false;
        } else if (option2ContainerView.getVisibility() == View.VISIBLE && minOptionNumberLength2 != 0 && maxOptionNumberLength2 == 0 && etOption2.getText().length() != minOptionNumberLength2) {
            msgNumberArror = optionAccountName2.trim() + " must be length of " + minOptionNumberLength2 + (isOptionAccountNumeric2 ? " digits" : " characters");
            txtOption2.setError(msgNumberArror);
            etOption2.requestFocus();
            return false;
        } else if (option2ContainerView.getVisibility() == View.VISIBLE && minOptionNumberLength2 == 0 && maxOptionNumberLength2 != 0 && etOption2.getText().length() != maxOptionNumberLength2) {
            msgNumberArror = optionAccountName2.trim() + " must be length of " + maxOptionNumberLength2 + (isOptionAccountNumeric2 ? " digits" : " characters");
            txtOption2.setError(msgNumberArror);
            etOption2.requestFocus();
            return false;
        }  else if(option2ContainerView.getVisibility() == View.VISIBLE && regExpress2!=null && Pattern.compile(regExpress2).matcher(etOption2.getText().toString()).matches()==false){
            msgNumberArror ="Please Enter a Valid " + optionAccountName2.trim();
            txtOption2.setError(msgNumberArror);
            etOption2.requestFocus();
            return false;
        } /*---Option 3-----*/
        else if (option3ContainerView.getVisibility() == View.VISIBLE && etOption3.getText().toString().trim().isEmpty()) {
            msgNumberArror = txtOption3.getHint() + " field can't be empty";
            txtOption3.setError(msgNumberArror);
            etOption3.requestFocus();
            return false;
        }else if (option3ContainerView.getVisibility() == View.VISIBLE && minOptionNumberLength3 != 0 && maxOptionNumberLength3 != 0 && minOptionNumberLength3 != maxOptionNumberLength3 && etOption3.getText().length() < minOptionNumberLength3) {
            msgNumberArror = optionAccountName3.trim() + " must be length of " + minOptionNumberLength3 + " to " + maxOptionNumberLength3 + (isOptionAccountNumeric3 ? " digits" : " characters");
            txtOption3.setError(msgNumberArror);
            etOption3.requestFocus();
            return false;
        } else if (option3ContainerView.getVisibility() == View.VISIBLE && minOptionNumberLength3 != 0 && maxOptionNumberLength3 != 0 && minOptionNumberLength3 != maxOptionNumberLength3 && etOption3.getText().length() > maxOptionNumberLength3) {
            msgNumberArror = optionAccountName3.trim() + " must be length of " + minOptionNumberLength3 + " to " + maxOptionNumberLength3 + (isOptionAccountNumeric3 ? " digits" : " characters");
            txtOption3.setError(msgNumberArror);
            etOption3.requestFocus();
            return false;
        } else if (option3ContainerView.getVisibility() == View.VISIBLE && minOptionNumberLength3 != 0 && maxOptionNumberLength3 != 0 && minOptionNumberLength3 == maxOptionNumberLength3 && etOption3.getText().length() != maxOptionNumberLength3) {
            msgNumberArror = optionAccountName3.trim() + " must be length of " + maxOptionNumberLength3 + (isOptionAccountNumeric3 ? " digits" : " characters");
            txtOption3.setError(msgNumberArror);
            etOption3.requestFocus();
            return false;
        } else if (option3ContainerView.getVisibility() == View.VISIBLE && minOptionNumberLength3 != 0 && maxOptionNumberLength3 == 0 && etOption3.getText().length() != minOptionNumberLength3) {
            msgNumberArror = optionAccountName3.trim() + " must be length of " + minOptionNumberLength3 + (isOptionAccountNumeric3 ? " digits" : " characters");
            txtOption3.setError(msgNumberArror);
            etOption3.requestFocus();
            return false;
        } else if (option3ContainerView.getVisibility() == View.VISIBLE && minOptionNumberLength3 == 0 && maxOptionNumberLength3 != 0 && etOption3.getText().length() != maxOptionNumberLength3) {
            msgNumberArror = optionAccountName3.trim() + " must be length of " + maxOptionNumberLength3 + (isOptionAccountNumeric3 ? " digits" : " characters");
            txtOption3.setError(msgNumberArror);
            etOption3.requestFocus();
            return false;
        }  else if(option3ContainerView.getVisibility() == View.VISIBLE && regExpress3!=null && Pattern.compile(regExpress3).matcher(etOption3.getText().toString()).matches()==false){
            msgNumberArror ="Please Enter a Valid " + optionAccountName3.trim();
            txtOption3.setError(msgNumberArror);
            etOption3.requestFocus();
            return false;
        }
        /*----Option 4*/
        else if (option4ContainerView.getVisibility() == View.VISIBLE && etOption4.getText().toString().trim().isEmpty()) {
            msgNumberArror = txtOption4.getHint() + " field can't be empty";
            txtOption4.setError(msgNumberArror);
            etOption4.requestFocus();
            return false;
        }
        else if (option4ContainerView.getVisibility() == View.VISIBLE && minOptionNumberLength4 != 0 && maxOptionNumberLength4 != 0 && minOptionNumberLength4 != maxOptionNumberLength4 && etOption4.getText().length() < minOptionNumberLength4) {
            msgNumberArror = optionAccountName4.trim() + " must be length of " + minOptionNumberLength4 + " to " + maxOptionNumberLength4 + (isOptionAccountNumeric4 ? " digits" : " characters");
            txtOption4.setError(msgNumberArror);
            etOption4.requestFocus();
            return false;
        } else if (option4ContainerView.getVisibility() == View.VISIBLE && minOptionNumberLength4 != 0 && maxOptionNumberLength4 != 0 && minOptionNumberLength4 != maxOptionNumberLength4 && etOption4.getText().length() > maxOptionNumberLength4) {
            msgNumberArror = optionAccountName4.trim() + " must be length of " + minOptionNumberLength4 + " to " + maxOptionNumberLength4 + (isOptionAccountNumeric4 ? " digits" : " characters");
            txtOption4.setError(msgNumberArror);
            etOption4.requestFocus();
            return false;
        } else if (option4ContainerView.getVisibility() == View.VISIBLE && minOptionNumberLength4 != 0 && maxOptionNumberLength4 != 0 && minOptionNumberLength4 == maxOptionNumberLength4 && etOption4.getText().length() != maxOptionNumberLength4) {
            msgNumberArror = optionAccountName4.trim() + " must be length of " + maxOptionNumberLength4 + (isOptionAccountNumeric4 ? " digits" : " characters");
            txtOption4.setError(msgNumberArror);
            etOption4.requestFocus();
            return false;
        } else if (option4ContainerView.getVisibility() == View.VISIBLE && minOptionNumberLength4 != 0 && maxOptionNumberLength4 == 0 && etOption4.getText().length() != minOptionNumberLength4) {
            msgNumberArror = optionAccountName4.trim() + " must be length of " + minOptionNumberLength4 + (isOptionAccountNumeric4 ? " digits" : " characters");
            txtOption4.setError(msgNumberArror);
            etOption4.requestFocus();
            return false;
        } else if (option4ContainerView.getVisibility() == View.VISIBLE && minOptionNumberLength4 == 0 && maxOptionNumberLength4 != 0 && etOption4.getText().length() != maxOptionNumberLength4) {
            msgNumberArror = optionAccountName4.trim() + " must be length of " + maxOptionNumberLength4 + (isOptionAccountNumeric4 ? " digits" : " characters");
            txtOption4.setError(msgNumberArror);
            etOption4.requestFocus();
            return false;
        }  else if(option4ContainerView.getVisibility() == View.VISIBLE && regExpress4!=null && Pattern.compile(regExpress4).matcher(etOption4.getText().toString()).matches()==false){
            msgNumberArror ="Please Enter a Valid " + optionAccountName4.trim();
            txtOption4.setError(msgNumberArror);
            etOption4.requestFocus();
            return false;
        }else if (customerNumberContainer.getVisibility() == View.VISIBLE && etCustNumber.getText().toString().trim().isEmpty()) {
            txtCustomerNo.setError(getString(R.string.err_empty_field));
            etCustNumber.requestFocus();
            return false;
        } else if (customerNumberContainer.getVisibility() == View.VISIBLE && etCustNumber.getText().toString().length() != 10) {
            txtCustomerNo.setError(getString(R.string.valid_mobile));
            etCustNumber.requestFocus();
            return false;
        }

        return true;
    }

    boolean checkContains(ArrayList<String> StartWithArray, String value) {
        boolean isPrefixAvailable = false;
        for (String prefix : StartWithArray) {
            if (value.startsWith(prefix)) {
                isPrefixAvailable = true;
                break;
            }
        }
        return isPrefixAvailable;
    }

    private boolean validateAmount() {
        double amount = 0;
        if (!etAmount.getText().toString().trim().isEmpty()) {
            amount = Double.parseDouble(etAmount.getText().toString().trim());
        }

        if (etAmount.getText().toString().trim().isEmpty() || amount == 0) {
            msgAmtError = "Please enter valid amount";
            txtAmount.setError(msgAmtError);
            etAmount.requestFocus();
            return false;
        }else{
            if(isEditable){
                if (exactness ==2 && amount != 0 && maxAmountLength != 0 && amount > maxAmountLength) {
                    msgAmtError = "Amount must be less then \u20B9 " + maxAmountLength;
                    txtAmount.setError(msgAmtError);
                    etAmount.requestFocus();
                    return false;
                } else if (exactness==3 && amount != 0 && minAmountLength != 0 && amount < minAmountLength) {
                    msgAmtError = "Amount can't be less then \u20B9 " + minAmountLength;
                    txtAmount.setError(msgAmtError);
                    etAmount.requestFocus();
                    return false;
                }else if (exactness==5 && amount != 0 && minAmountLength != 0 && maxAmountLength != 0 && !(amount >= minAmountLength && amount <= maxAmountLength)) {
                    msgAmtError = "Amount must be between \u20B9 " + minAmountLength + " to \u20B9 " + maxAmountLength;
                    txtAmount.setError(msgAmtError);
                    etAmount.requestFocus();
                    return false;
                }else {
                    txtAmount.setError(null);

                }
            }
            else {
                if (amount != 0 && maxAmountLength != 0 && amount > maxAmountLength) {
                    msgAmtError = "Amount must be less then \u20B9 " + maxAmountLength;
                    txtAmount.setError(msgAmtError);
                    etAmount.requestFocus();
                    return false;
                }else if (amount != 0 && minAmountLength != 0 && amount < minAmountLength) {
                    msgAmtError = "Amount can't be less then \u20B9 " + minAmountLength;
                    txtAmount.setError(msgAmtError);
                    etAmount.requestFocus();
                    return false;
                } else if (amount != 0 && minAmountLength != 0 && maxAmountLength != 0 && !(amount >= minAmountLength && amount <= maxAmountLength)) {
                    msgAmtError = "Amount must be between \u20B9 " + minAmountLength + " to \u20B9 " + maxAmountLength;
                    txtAmount.setError(msgAmtError);
                    etAmount.requestFocus();
                    return false;
                }else {
                    txtAmount.setError(null);
                }
            }
        }


        return true;

    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            GlobalBus.getBus().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Unregister the registered event.
        GlobalBus.getBus().unregister(this);
    }

    @Subscribe
    public void onActivityActivityMessage(ActivityActivityMessage activityFragmentMessage) {
        if (activityFragmentMessage.getMessage().equalsIgnoreCase("refreshvalue_Sec")) {
            onBackPressed();
        }
        if (activityFragmentMessage.getFrom().equalsIgnoreCase("refreshvalue")) {
            etNumber.setText("");
            etAmount.setText("");
            etCustNumber.setText("");
            etOption1.setText("");
            etOption2.setText("");
            etOption3.setText("");
            etOption4.setText("");
            numberError.setVisibility(View.GONE);
            amountError.setVisibility(View.GONE);
            option1Error.setVisibility(View.GONE);
            option2Error.setVisibility(View.GONE);
            option3Error.setVisibility(View.GONE);
            option4Error.setVisibility(View.GONE);
            mobileNoError.setVisibility(View.GONE);
            btRecharge.setEnabled(true);
            tvName.setVisibility(View.GONE);
            if (UtilMethods.INSTANCE.isNetworkAvialable(SecondRechargeActivity.this)) {
                UtilMethods.INSTANCE.Balancecheck(SecondRechargeActivity.this, loader,null);
            }
        }
        if (activityFragmentMessage.getMessage().equalsIgnoreCase("viewbill")) {
            etAmount.setText(activityFragmentMessage.getFrom());
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == INTENT_DTH_INFO) {
            etAmount.setText(data.getStringExtra("Amount"));
        }
        if (resultCode == RESULT_OK && requestCode == INTENT_VIEW_PLAN) {
            etAmount.setText(data.getStringExtra("Amount"));
            desc.setVisibility(View.VISIBLE);
            amountError.setVisibility(View.GONE);
            desc.setText(data.getStringExtra("desc"));
        }
    }


    public void SetNumber(final String Number) {
        String Number1 = Number.replace("+91", "");
        String Number2 = Number1.replace(" ", "");
        String Number3 = Number2.replace("(", "");
        String Number4 = Number3.replace(")", "");
        String Number5 = Number4.replace("_", "");
        String Number6 = Number5.replace("-", "");
        etCustNumber.setText(Number6);

    }


    private void parseDthInfoData(DTHInfoData responsePlan) {

        if (responsePlan.getRecords() != null && responsePlan.getRecords().size() > 0) {
            mDthInfoRecords = responsePlan.getRecords().get(0);
        } else if (responsePlan.getData() != null) {
            mDthInfoRecords = responsePlan.getData();
        }

        if (mDthInfoRecords != null) {
            dthInfoDetailView.setVisibility(View.VISIBLE);
            Glide.with(this).load(ApplicationConstant.INSTANCE.baseIconUrl + operatorUrl).
                    apply(requestOptions).into(operatorIcon);

            if(responsePlan.getTel()!=null && !responsePlan.getTel().isEmpty()){
                tel.setText(/*AccountName.trim() + " : " +*/ responsePlan.getTel() + "");
            }else{
                tel.setText(/*AccountName.trim() + " : " +*/ etNumber.getText().toString() + "");
            }
            if(responsePlan.getOperator()!=null && !responsePlan.getOperator().isEmpty()){
                operator.setText(responsePlan.getOperator()+"");
            }else{
                operator.setText(operatorSelected+"");
            }

            try {
                if (!mDthInfoRecords.getCustomerName().equalsIgnoreCase(""))
                    customerName.setText(mDthInfoRecords.getCustomerName());
                else
                    ll_customer_layout.setVisibility(View.GONE);
            } catch (Exception e) {
                e.printStackTrace();
                ll_customer_layout.setVisibility(View.GONE);
            }
            try {
                MonthlyRecharge.setText(getResources().getString(R.string.rupiya) + " " + mDthInfoRecords.getMonthlyRecharge());
            } catch (Exception e) {
                e.printStackTrace();
                ll_bal_amount.setVisibility(View.GONE);
            }
            try {
                planname.setText(mDthInfoRecords.getPlanname());
            } catch (Exception e) {
                e.printStackTrace();
                ll_plan_name.setVisibility(View.GONE);
            }
            try {
                NextRechargeDate.setText(mDthInfoRecords.getNextRechargeDate());
            } catch (Exception e) {
                e.printStackTrace();
                ll_RechargeDate.setVisibility(View.GONE);
            }
            try {
                Balance.setText(getResources().getString(R.string.rupiya) + "   " + mDthInfoRecords.getBalance());
            } catch (Exception e) {
                e.printStackTrace();
            }
            MonthlyRecharge.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    etAmount.setText(mDthInfoRecords.getMonthlyRecharge()+"");


                }
            });

        }
    }

    private void parseDthInfoRNData(DthCIData dthciData) {
        if (dthciData != null) {
            mDthciData = dthciData;
        }

        if(mDthciData!=null){

            Glide.with(this)
                    .load(ApplicationConstant.INSTANCE.baseIconUrl + operatorUrl)
                    .apply(requestOptions)
                    .into(operatorIcon);

            operator.setText(operatorSelected);
            tel.setText(etNumber.getText().toString());

            if(mDthciData.getData()!=null && mDthciData.getData().size()>0){
                dthInfoDetailView.setVisibility(View.VISIBLE);
                dthInfoDetailsRNView.setVisibility(View.VISIBLE);
                dthInfoDetailsCommonView.setVisibility(View.GONE);

                List<DthCustomerInfoData> dthInfoList = new ArrayList<>();
                HashedMap<String,String> mHasMp = new HashedMap();
                for (DataRN mItem : mDthciData.getData()) {
                    if (mItem.getKey() != null && !mItem.getKey().isEmpty() && mItem.getValue() != null && !mItem.getValue().isEmpty()) {
                        mHasMp.put(mItem.getKey(),mItem.getValue());
                        if(mItem!=null && mItem.getKey()!=null && !mItem.getKey().isEmpty() && mItem.getKey().contains("MonthlyRecharge") || mItem.getKey().contains("Balance")){
                            dthInfoList.add(new DthCustomerInfoData(mItem.getKey(),mItem.getValue(),true));
                        }else{
                            dthInfoList.add(new DthCustomerInfoData(mItem.getKey(),mItem.getValue(),false));
                        }
                    }
                }

                if(dthInfoList!=null && dthInfoList.size()>0){
                    dthInfoRNRv.setLayoutManager(new LinearLayoutManager(SecondRechargeActivity.this));
                    dthInfoRNRv.setNestedScrollingEnabled(false);
                    dthInfoRNRv.setAdapter(new DthInfoRNAdapter(this,dthInfoList));
                }
            }

        }

    }


    private void HitIncentiveApi(){
        if (UtilMethods.INSTANCE.isNetworkAvialable(SecondRechargeActivity.this)) {
            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);

            UtilMethods.INSTANCE.IncentiveDetail(SecondRechargeActivity.this, operatorSelectedId + "", loader, new UtilMethods.ApiCallBack() {
                @Override
                public void onSucess(Object object) {
                    AppUserListResponse mAppUserListResponse = (AppUserListResponse) object;
                    incentiveOperatorSelectedId = operatorSelectedId;

                    incentiveList = mAppUserListResponse.getIncentiveDetails();
                    showPopupIncentive();
                }
            });



        } else {
            UtilMethods.INSTANCE.NetworkError(SecondRechargeActivity.this, getResources().getString(R.string.err_msg_network_title),
                    getResources().getString(R.string.err_msg_network));
        }
    }

    private void showPopupIncentive() {

        LayoutInflater inflater = (LayoutInflater) SecondRechargeActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE); // or (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewMyLayout = inflater.inflate(R.layout.dialog_incentive, null);
        RecyclerView recyclerView = viewMyLayout.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(SecondRechargeActivity.this, 2));
        View closeBtn = viewMyLayout.findViewById(R.id.closeBtn);

        IncentiveAdapter incentiveAdapter = new IncentiveAdapter(incentiveList, SecondRechargeActivity.this, new IncentiveAdapter.MyInterface() {
            @Override
            public void myAction(IncentiveDetails item) {

                if (incentiveDialog != null && incentiveDialog.isShowing()) {
                    incentiveDialog.dismiss();
                }
                etAmount.setText(item.getDenomination() + "");
                desc.setVisibility(View.VISIBLE);
                desc.setText("You are eligible for " + item.getComm() + (item.isAmtType() ? " \u20B9 Cash Back" : " % Cash Back"));

            }
        });
        recyclerView.setAdapter(incentiveAdapter);
        incentiveDialog = new Dialog(SecondRechargeActivity.this, R.style.Theme_AppCompat_Dialog_Alert);
        incentiveDialog.setCancelable(false);
        incentiveDialog.setContentView(viewMyLayout);
        /* dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));*/
        incentiveDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incentiveDialog.dismiss();
            }
        });

        incentiveDialog.show();



    }

    public void setCashBackAmount(IncentiveDetails item) {
        if (incentiveDialog != null && incentiveDialog.isShowing()) {
            incentiveDialog.dismiss();
        }
        etAmount.setText(item.getDenomination() + "");
        desc.setVisibility(View.VISIBLE);
        amountError.setVisibility(View.GONE);
        desc.setText("You are eligible for " + item.getComm() + (item.isAmtType() ? " \u20B9 Cash Back" : " % Cash Back"));
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            isTTSInit = true;
            if (msgSpeak != null && !msgSpeak.isEmpty()) {
                playVoice();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Init failed", Toast.LENGTH_SHORT).show();
        }
    }
    private void playVoice() {
        if (tts != null && isTTSInit) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                tts.speak(msgSpeak, TextToSpeech.QUEUE_FLUSH, null, null);
            } else {
                tts.speak(msgSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
            msgSpeak = "";
        }
    }

}
