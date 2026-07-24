package com.fintech.omnipe.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.speech.tts.TextToSpeech;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.fintech.omnipe.Activities.Adapter.IncentiveAdapter;
import com.fintech.omnipe.Api.Object.IncentiveDetails;
import com.fintech.omnipe.Api.Response.AppUserListResponse;
import com.google.gson.Gson;
import com.fintech.omnipe.Api.Object.RechargeStatus;
import com.fintech.omnipe.Api.Request.GetHLRLookUpRequest;
import com.fintech.omnipe.Api.Response.GetHLRLookUPResponse;
import com.fintech.omnipe.Api.Response.NumberListResponse;
import com.fintech.omnipe.Api.Response.RechargeReportResponse;
import com.fintech.omnipe.BuildConfig;
import com.fintech.omnipe.Fragments.Adapter.RechargeReportAdapter;
import com.fintech.omnipe.Fragments.dto.OperatorList;
import com.fintech.omnipe.Auth.dto.LoginResponse;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.ActivityActivityMessage;
import com.fintech.omnipe.Util.ApiClient;
import com.fintech.omnipe.Util.ApplicationConstant;
import com.fintech.omnipe.Util.ChangePassUtils;
import com.fintech.omnipe.Util.CustomAlertDialog;
import com.fintech.omnipe.Util.EndPointInterface;
import com.fintech.omnipe.Util.GetLocation;
import com.fintech.omnipe.Util.GlobalBus;
import com.fintech.omnipe.Util.UtilMethods;
import com.fintech.omnipe.usefull.CustomLoader;
import com.fintech.omnipe.usefull.Preferences;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;

public class RechargeActivity extends AppCompatActivity implements View.OnClickListener, TextToSpeech.OnInitListener {

    boolean isPostpaid;
    public int operatorSelectedId,incentiveOperatorSelectedId;
    View ll_browse_plan;
    View rl_oprator;
    RadioButton radio_prepaid, radio_postpaid;
    TextView tvName, tv_operator;
    ImageView iv_oprator,cashBackIv;
    TextView tvBrowsePlan;
    TextView tv_roffer_plan;
    TextView noticePlan;
    RecyclerView recyclerView;
    TextView recentRechargeTv, viewMore;
    String OpRefOp = "";
    String OpRefCircleID = "";
    String operatorSelected = "";
    CustomLoader loader;
    Preferences pref;
    TextView AmountError, MobileNoError, OperatorError;
    View  historyView,switchView;
    private SwitchCompat realApiSwitch;
    int intentFromId;
    double minAmountLength,maxAmountLength;
    String  AccountName = "Mobile Number", AccountRemark = "", Icon = "";
    boolean isAcountNumeric,isRealAPI;
    int minNumberLength = 0, maxNumberLength = 0;
    String Startwith;
    TextView tvAmtDesc;

    int opid ;
    String OpCircleCode = "";
    private EditText etNumber;
    private EditText etAmount;
    private Button btRecharge;
    ArrayList<String> StartWithArray = new ArrayList<>();
    private String fromDateStr, toDateStr;
    private LoginResponse mLoginDataResponse;
    private int INTENT_SELECT_OPERATOR = 7291;
    RadioGroup mRadioGroup;
    private  ArrayList<IncentiveDetails> incentiveList = new ArrayList<>();
    private Dialog incentiveDialog;
    private GetLocation mGetLocation;
    private String msgSpeak="";
    private TextToSpeech tts;
    private boolean isTTSInit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_recharge);
        String LoginResponse = UtilMethods.INSTANCE.getLoginPref(this);
        mLoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
        intentFromId = getIntent().getIntExtra("fromId", 0);
        tts=new TextToSpeech(this,RechargeActivity.this);
        getIds();
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        final Calendar myCalendar = Calendar.getInstance();
        String myFormat = "dd-MMM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        fromDateStr = sdf.format(myCalendar.getTime());
        toDateStr = sdf.format(myCalendar.getTime());

        mGetLocation = new GetLocation(this, loader);
        if (UtilMethods.INSTANCE.getLattitude == 0 || UtilMethods.INSTANCE.getLongitude == 0) {
            mGetLocation.startLocationUpdatesIfSettingEnable((lattitude, longitude) -> {
                UtilMethods.INSTANCE.getLattitude = lattitude;
                UtilMethods.INSTANCE.getLongitude = longitude;
            });
        }
        HitApi();
    }

    private void getIds() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        toolbar.setTitle("Phone Recharge");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        pref = new Preferences(this);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        tvName = findViewById(R.id.tv_name);
        realApiSwitch =findViewById(R.id.realApiSwitch);
        switchView = findViewById(R.id.switchView);
        historyView = findViewById(R.id.historyView);
        tvAmtDesc = findViewById(R.id.desc);
        tv_operator = findViewById(R.id.tv_operator);
        iv_oprator = findViewById(R.id.iv_oprator);
        mRadioGroup = findViewById(R.id.radioGroup);
        ll_browse_plan = findViewById(R.id.ll_browse_plan);
        rl_oprator = findViewById(R.id.rl_oprator);
        radio_prepaid = findViewById(R.id.radio_prepaid);
        radio_postpaid = findViewById(R.id.radio_postpaid);
        noticePlan = findViewById(R.id.noticePlan);
        noticePlan.setVisibility(View.GONE);
        tvBrowsePlan = findViewById(R.id.tv_browse_plan);
        tvBrowsePlan.setVisibility(View.VISIBLE);
        tv_roffer_plan = findViewById(R.id.tv_roffer_plan);
        AmountError = findViewById(R.id.Amounterror);
        cashBackIv = findViewById(R.id.cashBackIv);

        MobileNoError = findViewById(R.id.MobileNoError);
        OperatorError = findViewById(R.id.OperatorError);


        recentRechargeTv = findViewById(R.id.recentRechargeTv);
        viewMore = findViewById(R.id.viewMore);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        btRecharge = findViewById(R.id.bt_recharge);

        etNumber = findViewById(R.id.et_number);
        etAmount = findViewById(R.id.et_amount);


        if (mLoginDataResponse.getIsRoffer()) {
            tv_roffer_plan.setVisibility(View.VISIBLE);
        }
        else{
            tv_roffer_plan.setVisibility(View.GONE);
        }

        if (UtilMethods.INSTANCE.getRealApiPref(this)) { //Real API
            switchView.setVisibility(View.VISIBLE);
        } else {
            switchView.setVisibility(View.GONE);
        }

        if (UtilMethods.INSTANCE.getIncentiveInfoPref(this)) {
            cashBackIv.setVisibility(View.VISIBLE);

        } else {
            cashBackIv.setVisibility(View.GONE);
        }


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

        etNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isPostpaid) {
                    if (UtilMethods.INSTANCE.getIsLookUpFromAPI(RechargeActivity.this)) {
                        if (s.length() == 10) {
                            getHLRLookUp();
                        }
                    } else {
                        if (s.length() == 4) {
                            SelectOperator(s.toString());
                        }
                    }
                }

                if (s.length() != 10) {
                    tvName.setText("");
                    tvName.setVisibility(View.GONE);
                }
            }
        });
        
        etAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (tvAmtDesc.getVisibility() == View.VISIBLE) {
                    tvAmtDesc.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                tv_operator.setText("");
                iv_oprator.setImageResource(R.drawable.ic_tower);
                if (checkedId == R.id.radio_prepaid) {
                    isPostpaid = false;
                    ll_browse_plan.setVisibility(View.VISIBLE);
                    btRecharge.setText("Recharge");
                    recentRechargeTv.setText("Recent Recharges");

                } else {
                    isPostpaid = true;
                    ll_browse_plan.setVisibility(View.GONE);
                    btRecharge.setText("Bill Payment");
                    recentRechargeTv.setText("Recent Bill Payments");
                }
            }
        });
        setListners();


    }

    //code for pic the contact from the phonebook...
    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (reqCode == 45 && resultCode == RESULT_OK ) {
            etAmount.setText(data.getStringExtra("amount"));
            tvAmtDesc.setVisibility(View.VISIBLE);
            tvAmtDesc.setText(data.getStringExtra("desc"));
        }
        else if (resultCode == 3) {
            String circle = data.getExtras().getString("selectedCircleName");
            OpRefCircleID = data.getExtras().getString("selectedCircleId");
            if (operatorSelectedId != 0 && !OpRefCircleID.equals("")) {
                if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {
                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(false);
                    UtilMethods.INSTANCE.ViewPlan(this, mLoginDataResponse.isPlanServiceUpdated(),mLoginDataResponse ,operatorSelectedId + "", OpRefCircleID, loader);
                } else {
                    UtilMethods.INSTANCE.NetworkError(this, getResources().getString(R.string.err_msg_network_title),
                            getResources().getString(R.string.err_msg_network));
                }
            } else {
                UtilMethods.INSTANCE.Error(this, "Please select Operator and Circle both");
            }

        }
        else if (reqCode == INTENT_SELECT_OPERATOR && resultCode == RESULT_OK) {
            operatorSelected = data.getExtras().getString("selected");
            operatorSelectedId = data.getExtras().getInt("selectedId");
            minAmountLength = data.getExtras().getDouble("Min");
            maxAmountLength = data.getExtras().getDouble("Max");
            minNumberLength = data.getExtras().getInt("Length", 0);
            maxNumberLength = data.getExtras().getInt("MaxLength", 0);
            isAcountNumeric = data.getExtras().getBoolean("IsAccountNumeric", false);
            AccountName = data.getExtras().getString("AccountName");
            AccountRemark = data.getExtras().getString("AccountRemark");
            Startwith = data.getExtras().getString("StartWith");
            Icon = data.getExtras().getString("Icon");

            if (Startwith != null && Startwith.length() > 0 && Startwith.contains(",")) {
                StartWithArray = new ArrayList<>(Arrays.asList(Startwith.trim().split(",")));
            } else if (Startwith != null && !Startwith.isEmpty() && !Startwith.equalsIgnoreCase("0")) {
                StartWithArray.add(Startwith);
            }

            tv_operator.setText(operatorSelected + "");
            OperatorError.setVisibility(View.GONE);
            Glide.with(this)
                    .load(ApplicationConstant.INSTANCE.baseIconUrl + Icon)
                    .apply(RequestOptions.circleCropTransform())
                    .apply(RequestOptions.placeholderOf(R.mipmap.ic_launcher_round))
                    .into(iv_oprator);

            if (isAcountNumeric == true) {
                etNumber.setInputType(InputType.TYPE_CLASS_NUMBER);
            } else {
                etNumber.setInputType(InputType.TYPE_CLASS_TEXT);
            }

            if (maxNumberLength != 0 && maxNumberLength > 0) {
                InputFilter[] filterArray = new InputFilter[1];
                filterArray[0] = new InputFilter.LengthFilter(maxNumberLength);
                etNumber.setFilters(filterArray);
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
        if (transactionsObjects != null && transactionsObjects.size() > 0) {
            historyView.setVisibility(View.VISIBLE);
            RechargeReportAdapter mAdapter = new RechargeReportAdapter(transactionsObjects, this, false);
            recyclerView.setAdapter(mAdapter);
        }
    }

    // For Setting the Number in the Phonenumber...
    public void SetNumber(final String Number) {
        String Number1 = Number.replace("+91", "");
        String Number2 = Number1.replace(" ", "");
        String Number3 = Number2.replace("(", "");
        String Number4 = Number3.replace(")", "");
        String Number5 = Number4.replace("_", "");
        String Number6 = Number5.replace("-", "");
        etNumber.setText(Number6);
        String snum = etNumber.getText().toString();
        if (snum.length() > 4) { //just checks that there is something. You may want to check that length is greater than or equal to 3
            String bar = snum.substring(0, 4);
            Log.e("bar", bar);
            SelectOperator(bar);
        }
    }

    private boolean validateMobile() {
        if (etNumber.getText().toString().trim().isEmpty()) {

            MobileNoError.setText("Number can't be empty.");
            MobileNoError.setVisibility(View.VISIBLE);
            etNumber.requestFocus();
            return false;
        } else if (StartWithArray != null && StartWithArray.size() > 0 && !StartWithArray.contains(etNumber.getText().toString().substring(0, 1))) {

            MobileNoError.setText(AccountName.trim() + " should start with " + Startwith);
            MobileNoError.setVisibility(View.VISIBLE);
            etNumber.requestFocus();
            return false;

        } else if (minNumberLength == 0 && maxNumberLength == 0 && etNumber.getText().length() != 10) {
            MobileNoError.setText(AccountName + " should be length of " + 10);
            MobileNoError.setVisibility(View.VISIBLE);
            etNumber.requestFocus();
            return false;
        } else if (minNumberLength != 0 && maxNumberLength != 0 && minNumberLength != maxNumberLength) {
            boolean value;
            if (etNumber.getText().length() < minNumberLength) {
                MobileNoError.setText(AccountName + " should be length of " + minNumberLength + " to " + maxNumberLength);
                MobileNoError.setVisibility(View.VISIBLE);
                etNumber.requestFocus();
                value = false;
            } else if (etNumber.getText().length() > maxNumberLength) {
                MobileNoError.setText(AccountName + " should be length of " + minNumberLength + " to " + maxNumberLength);
                MobileNoError.setVisibility(View.VISIBLE);
                etNumber.requestFocus();
                value = false;
            } else {
                value = true;
            }
            return value;

        } else if (minNumberLength != 0 && maxNumberLength != 0 && minNumberLength == maxNumberLength && etNumber.getText().length() != maxNumberLength) {
            MobileNoError.setText(AccountName + " should be length of " + maxNumberLength);
            MobileNoError.setVisibility(View.VISIBLE);
            etNumber.requestFocus();
            return false;
        } else if (minNumberLength != 0 && maxNumberLength == 0 && etNumber.getText().length() != minNumberLength) {
            MobileNoError.setText(AccountName + " should be length of " + minNumberLength);
            MobileNoError.setVisibility(View.VISIBLE);
            etNumber.requestFocus();
            return false;
        } else if (minNumberLength == 0 && maxNumberLength != 0 && etNumber.getText().length() != maxNumberLength) {
            MobileNoError.setText(AccountName + " should be length of " + maxNumberLength);
            MobileNoError.setVisibility(View.VISIBLE);
            etNumber.requestFocus();
            return false;
        } else {
            etAmount.requestFocus();
            MobileNoError.setVisibility(View.GONE);
        }

        return true;
    }

    private boolean validateAmount() {
        double amount = 0;
        if (!etAmount.getText().toString().trim().isEmpty()) {
            amount = Double.parseDouble(etAmount.getText().toString().trim());
        }
        double max = maxAmountLength;
        double min = minAmountLength;

        if (etAmount.getText().toString().trim().isEmpty()) {
            AmountError.setText("Amount can't be empty");
            AmountError.setVisibility(View.VISIBLE);
            etAmount.requestFocus();
            return false;
        } else if (amount == 0) {
            AmountError.setText("Amount can't be 0");
            AmountError.setVisibility(View.VISIBLE);
            etAmount.requestFocus();
            return false;
        } else if (amount != 0 && min != 0 && amount < min) {
            AmountError.setText("Amount can't be less then " + min);
            AmountError.setVisibility(View.VISIBLE);
            etAmount.requestFocus();
            return false;
        } else if (amount != 0 && max != 0 && amount > max) {
            AmountError.setText("Amount can't be more then " + max);
            AmountError.setVisibility(View.VISIBLE);
            etAmount.requestFocus();
            return false;
        } else if (amount != 0 && min != 0 && max != 0 && !(amount >= min && amount <= max)) {
            AmountError.setText("Amount shold be between " + min + " to " + max);
            AmountError.setVisibility(View.VISIBLE);
            etAmount.requestFocus();
            return false;
        } else {
            AmountError.setVisibility(View.GONE);

        }
        return true;
    }

    private void setListners() {
        rl_oprator.setOnClickListener(this);
        tvBrowsePlan.setOnClickListener(this);
        btRecharge.setOnClickListener(this);
        cashBackIv.setOnClickListener(this);

        tv_roffer_plan.setOnClickListener(this);
        viewMore.setOnClickListener(this);
        if (radio_prepaid.isChecked()) {
            btRecharge.setText("Recharge");
            recentRechargeTv.setText("Recent Recharges");
        } else {
            ll_browse_plan.setVisibility(View.GONE);
            btRecharge.setText("Bill Payment");
            recentRechargeTv.setText("Recent Bill Payments");
        }
    }

    @Override
    public void onClick(View v) {
        if(v==cashBackIv){
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
        else if (v == viewMore) {
            Intent intent = new Intent(this, RechargeHistory.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        } /*else if (v == tvCircle) {
            if ((!OpRefOp.equals("") || operatorSelectedId != 0)) {
                Intent planOptionIntent = new Intent(this, SelectZoneOption.class);
                planOptionIntent.putExtra("from", "zoneOp");
                planOptionIntent.putExtra("opList", 1);
                planOptionIntent.putExtra("opListName", operatorSelected);
                planOptionIntent.putExtra("operatorId", OpRefOp);
                startActivityForResult(planOptionIntent, 4);
            } else {
                UtilMethods.INSTANCE.Error(this, "Please Select the Operator.");
            }

        }*/ else if (v == rl_oprator) {
            Intent i = new Intent(this, RechargeProviderActivity.class);

            if (radio_prepaid.isChecked()) {
                i.putExtra("fromId", 1);
                i.putExtra("from", "Prepaid");
            } else {
                i.putExtra("fromId", 2);
                i.putExtra("from", "Postpaid");
            }

            i.putExtra("fromPhoneRecharge", true);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivityForResult(i, INTENT_SELECT_OPERATOR);
        } else if (v == tv_roffer_plan) {
            if (operatorSelectedId != 0 && !etNumber.getText().toString().trim().equals("")) {
                if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {
                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(false);
                    UtilMethods.INSTANCE.ROffer(this,mLoginDataResponse, mLoginDataResponse.isPlanServiceUpdated(),operatorSelectedId + "", etNumber.getText().toString().trim(), loader);
                } else {
                    UtilMethods.INSTANCE.NetworkError(this, getResources().getString(R.string.err_msg_network_title),
                            getResources().getString(R.string.err_msg_network));
                }
            } else {
                UtilMethods.INSTANCE.Error(this, "Please enter Number and select Operator both");
            }
        } else if (v == tvBrowsePlan)
        {
            if ((!OpRefOp.equals("") || operatorSelectedId != 0)) {
                Intent planOptionIntent = new Intent(this, SelectZoneOption.class);
                planOptionIntent.putExtra("from", "zoneOp");
                planOptionIntent.putExtra("opList", 1);
                planOptionIntent.putExtra("opListName", operatorSelected);
                planOptionIntent.putExtra("operatorId", OpRefOp);
                startActivityForResult(planOptionIntent, 3);
            } else {
                UtilMethods.INSTANCE.Error(this, "Please Select the Operator.");
                msgSpeak = "Please select operator first.";
                playVoice();
                return;
            }
        }
        else if (v == btRecharge) {
            AmountError.setVisibility(View.GONE);
            MobileNoError.setVisibility(View.GONE);
            OperatorError.setVisibility(View.GONE);
            if (!validateMobile()) {
                return;
            } else if (tv_operator.getText().toString().isEmpty()) {
                OperatorError.setVisibility(View.VISIBLE);
                return;
            } else if (!validateAmount()) {
                return;
            }


            if (UtilMethods.INSTANCE.isVpnConnected(this)) {
                UtilMethods.INSTANCE.ProcessingWithTitle(this, "VPN Enable", "Please disable VPN before using this service.");

                return;
            }

            UtilMethods.INSTANCE.rechargeConfiormDialog(this, UtilMethods.INSTANCE.getDoubleFactorPref(this), ApplicationConstant.INSTANCE.baseIconUrl + Icon, etNumber.getText().toString(), operatorSelected, etAmount.getText().toString(), new UtilMethods.DialogCallBack() {
                @Override
                public void onPositiveClick(String pinPass) {
                    btRecharge.setEnabled(false);
                    if (UtilMethods.INSTANCE.isNetworkAvialable(RechargeActivity.this)) {
                        loader.show();
                        loader.setCancelable(false);
                        loader.setCanceledOnTouchOutside(false);
                        UtilMethods.INSTANCE.Recharge(RechargeActivity.this, operatorSelectedId, etNumber.getText().toString().trim(),
                                etAmount.getText().toString().trim(), "", "", "", "", "", "",
                                UtilMethods.INSTANCE.getLattitude + "," + UtilMethods.INSTANCE.getLongitude
                                , pinPass,0,isRealAPI,loader);
                    } else {
                        UtilMethods.INSTANCE.NetworkError(RechargeActivity.this, getResources().getString(R.string.err_msg_network_title),
                                getResources().getString(R.string.err_msg_network));
                    }
                }

                @Override
                public void onCancelClick() {

                }
            });
            /*RechargeDialog();*/

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

    private void SelectOperator(String s) {
        String param = UtilMethods.INSTANCE.fetchOperator(this, s);
        String[] parts = param.split(",");
        if (parts.length == 2) {
            opid = Integer.parseInt(parts[0]);
            OpCircleCode = parts[1];
            OpRefCircleID = parts[1];

        } else {
            opid=0;
            OpCircleCode = "";
        }
        ////////////////////////////////////////////////////////

        NumberListResponse numberListResponse = new NumberListResponse();
        SharedPreferences prefs = this.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String response = prefs.getString(ApplicationConstant.INSTANCE.numberListPref, null);
        Gson gson = new Gson();
        numberListResponse = gson.fromJson(response, NumberListResponse.class);

        for (OperatorList op : numberListResponse.getData().getOperators()) {
            if (op.getOid()==opid) {
                operatorSelected = op.getName();
                operatorSelectedId = op.getOid();
                Startwith = op.getStartWith();
                if (Startwith != null && Startwith.length() > 0 && Startwith.contains(",")) {
                    StartWithArray = new ArrayList<>(Arrays.asList(Startwith.trim().split(",")));
                } else if (Startwith != null && !Startwith.isEmpty() && !Startwith.equalsIgnoreCase("0")) {
                    StartWithArray.add(Startwith);
                }

                minAmountLength =op.getMin();
                maxAmountLength = op.getMax();
                minNumberLength = op.getLength();
                maxNumberLength = op.getLengthMax();
                isAcountNumeric = op.isAccountNumeric();
                AccountName = op.getAccountName();
                AccountRemark = op.getAccountRemak();
                if (isAcountNumeric == true) {
                    etNumber.setInputType(InputType.TYPE_CLASS_NUMBER);
                } else {
                    etNumber.setInputType(InputType.TYPE_CLASS_TEXT);
                }

                if (maxNumberLength != 0 && maxNumberLength > 0) {
                    InputFilter[] filterArray = new InputFilter[1];
                    filterArray[0] = new InputFilter.LengthFilter(maxNumberLength);
                    etNumber.setFilters(filterArray);
                }
                RequestOptions requestOptions = new RequestOptions();
                requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);

                Icon = op.getImage();
                tv_operator.setText(operatorSelected + "");
                OperatorError.setVisibility(View.GONE);
                Glide.with(this)
                        .load(ApplicationConstant.INSTANCE.baseIconUrl + Icon)
                        .apply(RequestOptions.circleCropTransform())
                        .apply(RequestOptions.placeholderOf(R.mipmap.ic_launcher_round))
                        .into(iv_oprator);
            }
        }

    }

    private void SelectOperator(int opretorId, int circleId) {


        opid =opretorId;
        OpRefCircleID = String.valueOf(circleId);

        NumberListResponse numberListResponse = new NumberListResponse();
        SharedPreferences prefs = this.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String response = prefs.getString(ApplicationConstant.INSTANCE.numberListPref, null);
        Gson gson = new Gson();
        numberListResponse = gson.fromJson(response, NumberListResponse.class);

        for (OperatorList op : numberListResponse.getData().getOperators()) {
            if (op.getOid()==opid) {
                operatorSelected = op.getName();
                operatorSelectedId = op.getOid();
                Startwith = op.getStartWith();
                if (Startwith != null && Startwith.length() > 0 && Startwith.contains(",")) {
                    StartWithArray = new ArrayList<>(Arrays.asList(Startwith.trim().split(",")));
                } else if (Startwith != null && !Startwith.isEmpty() && !Startwith.equalsIgnoreCase("0")) {
                    StartWithArray.add(Startwith);
                }

                minAmountLength = op.getMin();
                maxAmountLength = op.getMax();
                minNumberLength = op.getLength();
                maxNumberLength = op.getLengthMax();
                isAcountNumeric = op.isAccountNumeric();
                AccountName = op.getAccountName();
                AccountRemark = op.getAccountRemak();
                if (isAcountNumeric == true) {
                    etNumber.setInputType(InputType.TYPE_CLASS_NUMBER);
                } else {
                    etNumber.setInputType(InputType.TYPE_CLASS_TEXT);
                }

                if (maxNumberLength != 0 && maxNumberLength > 0) {
                    InputFilter[] filterArray = new InputFilter[1];
                    filterArray[0] = new InputFilter.LengthFilter(maxNumberLength);
                    etNumber.setFilters(filterArray);
                }
                RequestOptions requestOptions = new RequestOptions();
                requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);

                Icon = op.getImage();
                tv_operator.setText(operatorSelected + "");
                OperatorError.setVisibility(View.GONE);
                Glide.with(this)
                        .load(ApplicationConstant.INSTANCE.baseIconUrl + Icon)
                        .apply(RequestOptions.circleCropTransform())
                        .apply(RequestOptions.placeholderOf(R.mipmap.ic_launcher_round))
                        .into(iv_oprator);
            }
        }

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
        GlobalBus.getBus().unregister(this);
    }

    @Subscribe
    public void onActivityActivityMessage(ActivityActivityMessage activityFragmentMessage) {
        if (activityFragmentMessage.getMessage().equalsIgnoreCase("planSelected")) {
            String[] str = activityFragmentMessage.getFrom().split("_");
            etAmount.setText(str[0] + "");
            try {
                tvAmtDesc.setVisibility(View.VISIBLE);
                tvAmtDesc.setText(str[1] + "");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (activityFragmentMessage.getMessage().equalsIgnoreCase("planSelectedDetail")) {
            noticePlan.setVisibility(View.VISIBLE);
            noticePlan.setText(activityFragmentMessage.getFrom() + "");
        } else if (activityFragmentMessage.getMessage().equalsIgnoreCase("view_bill_post")) {
            String[] data = activityFragmentMessage.getFrom().split(",");
            String balance = data[0];
            String partial = data[1];
            etAmount.setText(balance);
            etAmount.setEnabled(true);
        } else if (activityFragmentMessage.getFrom().equalsIgnoreCase("refreshvalue")) {
            etNumber.setText("");
            etAmount.setText("");
            MobileNoError.setVisibility(View.GONE);
            AmountError.setVisibility(View.GONE);
            btRecharge.setEnabled(true);
            tvName.setVisibility(View.GONE);
            if (UtilMethods.INSTANCE.isNetworkAvialable(RechargeActivity.this)) {
                UtilMethods.INSTANCE.Balancecheck(RechargeActivity.this, loader, null);
            }
        } else if (activityFragmentMessage.getMessage().equalsIgnoreCase("rOffer_Amount")) {
            String[] str = activityFragmentMessage.getFrom().split("_");
            etAmount.setText(str[0] + "");
            try {
                tvAmtDesc.setVisibility(View.VISIBLE);
                tvAmtDesc.setText(str[1] + "");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void getHLRLookUp() {
        try {
            loader.show();
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            String LoginResponse = UtilMethods.INSTANCE.getLoginPref(this);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            Call<GetHLRLookUPResponse> call = git.GetHLRLookUp(new GetHLRLookUpRequest(etNumber.getText().toString(),
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    UtilMethods.INSTANCE.getIMEI(this),
                    "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(this), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<GetHLRLookUPResponse>() {

                @Override
                public void onResponse(Call<GetHLRLookUPResponse> call, retrofit2.Response<GetHLRLookUPResponse> response) {
//                     Log.e("Payment response", "hello response : " + new Gson().toJson(response.body()).toString());
                    if (loader.isShowing())
                        loader.dismiss();
                    GetHLRLookUPResponse mGetHLRLookUPResponse = response.body();
                    if (mGetHLRLookUPResponse != null) {
                        if (mGetHLRLookUPResponse.isPasswordExpired()) {
                            CustomAlertDialog customAlertDialog = new CustomAlertDialog(RechargeActivity.this, true);
                            customAlertDialog.WarningWithCallBack(getString(R.string.password_expired_msg), "Change Password", false, new CustomAlertDialog.DialogCallBack() {
                                @Override
                                public void onPositiveClick() {
                                    new ChangePassUtils(RechargeActivity.this).changePassword(false, false);
                                }

                                @Override
                                public void onNegativeClick() {
                                }
                            });
                        }
                        if (mGetHLRLookUPResponse.getStatuscode() == 1) {
                            if (mGetHLRLookUPResponse.getOid() != 0) {
                                SelectOperator(mGetHLRLookUPResponse.getOid(), mGetHLRLookUPResponse.getCircleID());
                            } else {
                                tv_operator.setText("");
                                iv_oprator.setImageResource(R.drawable.ic_tower);
                            }


                        } else if (response.body() != null && response.body().getStatuscode() == -1) {
                            UtilMethods.INSTANCE.Error(RechargeActivity.this, mGetHLRLookUPResponse.getMsg() + "");
                        }

                    } else {

                        UtilMethods.INSTANCE.Error(RechargeActivity.this, getString(R.string.some_thing_error));
                    }
                }

                @Override
                public void onFailure(Call<GetHLRLookUPResponse> call, Throwable t) {
                    Log.e("response", "error ");

                    try {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                            if (t.getMessage().contains("No address associated with hostname")) {

                                UtilMethods.INSTANCE.NetworkError(RechargeActivity.this, getString(R.string.network_error_title), getString(R.string.err_msg_network));


                            } else {

                                UtilMethods.INSTANCE.Error(RechargeActivity.this, t.getMessage());


                            }

                        } else {

                            UtilMethods.INSTANCE.Error(RechargeActivity.this, getString(R.string.some_thing_error));
                        }
                    } catch (IllegalStateException ise) {
                        loader.dismiss();

                        UtilMethods.INSTANCE.Error(RechargeActivity.this, ise.getMessage() + "");
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();

            UtilMethods.INSTANCE.Error(RechargeActivity.this, e.getMessage() + "");
        }

    }

    private void HitIncentiveApi(){
        if (UtilMethods.INSTANCE.isNetworkAvialable(RechargeActivity.this)) {
            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);

            UtilMethods.INSTANCE.IncentiveDetail(RechargeActivity.this, operatorSelectedId + "", loader, new UtilMethods.ApiCallBack() {
                @Override
                public void onSucess(Object object) {
                    AppUserListResponse mAppUserListResponse = (AppUserListResponse) object;
                    incentiveOperatorSelectedId = operatorSelectedId;

                    incentiveList = mAppUserListResponse.getIncentiveDetails();
                    showPopupIncentive();
                }
            });



        } else {
            UtilMethods.INSTANCE.NetworkError(RechargeActivity.this, getResources().getString(R.string.err_msg_network_title),
                    getResources().getString(R.string.err_msg_network));
        }
    }

    private void showPopupIncentive() {

        LayoutInflater inflater = (LayoutInflater) RechargeActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE); // or (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewMyLayout = inflater.inflate(R.layout.dialog_incentive, null);
        RecyclerView recyclerView = viewMyLayout.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(RechargeActivity.this, 2));
        View closeBtn = viewMyLayout.findViewById(R.id.closeBtn);

        IncentiveAdapter incentiveAdapter = new IncentiveAdapter(incentiveList, RechargeActivity.this, new IncentiveAdapter.MyInterface() {
            @Override
            public void myAction(IncentiveDetails item) {

                if (incentiveDialog != null && incentiveDialog.isShowing()) {
                    incentiveDialog.dismiss();
                }
                etAmount.setText(item.getDenomination() + "");
                tvAmtDesc.setVisibility(View.VISIBLE);
                tvAmtDesc.setText("You are eligible for " + item.getComm() + (item.isAmtType() ? " \u20B9 Cash Back" : " % Cash Back"));

            }
        });
        recyclerView.setAdapter(incentiveAdapter);
        incentiveDialog = new Dialog(RechargeActivity.this, R.style.Theme_AppCompat_Dialog_Alert);
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
        // Window window = dialog.getWindow();
        //window.setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.MATCH_PARENT);


    }

    public void setCashBackAmount(IncentiveDetails item) {
        if (incentiveDialog != null && incentiveDialog.isShowing()) {
            incentiveDialog.dismiss();
        }
        etAmount.setText(item.getDenomination() + "");
        tvAmtDesc.setVisibility(View.VISIBLE);
        AmountError.setVisibility(View.GONE);
        tvAmtDesc.setText("You are eligible for " + item.getComm() + (item.isAmtType() ? " \u20B9 Cash Back" : " % Cash Back"));
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
}
