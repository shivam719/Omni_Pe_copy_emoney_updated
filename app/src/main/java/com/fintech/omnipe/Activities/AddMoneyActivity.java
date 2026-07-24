package com.fintech.omnipe.Activities;

import static com.cashfree.pg.CFPaymentService.PARAM_APP_ID;
import static com.cashfree.pg.CFPaymentService.PARAM_CUSTOMER_EMAIL;
import static com.cashfree.pg.CFPaymentService.PARAM_CUSTOMER_NAME;
import static com.cashfree.pg.CFPaymentService.PARAM_CUSTOMER_PHONE;
import static com.cashfree.pg.CFPaymentService.PARAM_NOTIFY_URL;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_AMOUNT;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_CURRENCY;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_ID;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_NOTE;
import static com.cashfree.pg.CFPaymentService.PARAM_PAYMENT_MODES;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.appcompat.widget.ListPopupWindow;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.cashfree.pg.CFPaymentService;
import com.fintech.omnipe.Api.Object.CashFreeData;
import com.fintech.omnipe.Api.Object.KeyVals;
import com.fintech.omnipe.Api.Object.PayTmSuccessItemResponse;
import com.fintech.omnipe.Api.Object.SlabDetailDisplayLvl;
import com.fintech.omnipe.Api.Object.UPIGatewayRequest;
import com.fintech.omnipe.Api.Request.AggrePayTransactionUpdateRequest;
import com.fintech.omnipe.Api.Request.UpdateUPIRequest;
import com.fintech.omnipe.Api.Response.GatwayStatusCheckResponse;
import com.fintech.omnipe.Api.Response.GatwayTransactionResponse;
import com.fintech.omnipe.Api.Response.SlabCommissionResponse;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.paytm.pgsdk.TransactionManager;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.fintech.omnipe.Activities.Adapter.AddMoneyTypeAdapter;
import com.fintech.omnipe.Activities.Adapter.GatewayTypeAdapter;
import com.fintech.omnipe.Activities.Adapter.ListPopupWindowAdapter;
import com.fintech.omnipe.Api.Object.BalanceType;
import com.fintech.omnipe.Api.Object.PaymentGatewayType;
import com.fintech.omnipe.Api.Object.RequestPTM;
import com.fintech.omnipe.Api.Object.RequestRazorPay;
import com.fintech.omnipe.Api.Object.WalletType;
import com.fintech.omnipe.Api.Request.GatewayTransactionRequest;
import com.fintech.omnipe.Api.Request.PayTMTransactionUpdateRequest;
import com.fintech.omnipe.Api.Response.AppUserListResponse;
import com.fintech.omnipe.Api.Response.BalanceResponse;
import com.fintech.omnipe.Api.Response.BasicResponse;
import com.fintech.omnipe.Api.Response.InitiateUpiResponse;
import com.fintech.omnipe.Api.Response.NumberListResponse;
import com.fintech.omnipe.Api.Response.WalletTypeResponse;
import com.fintech.omnipe.Auth.dto.LoginResponse;
import com.fintech.omnipe.BuildConfig;
import com.fintech.omnipe.Fragments.dto.OperatorList;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.ApiClient;
import com.fintech.omnipe.Util.ApplicationConstant;
import com.fintech.omnipe.Util.EndPointInterface;
import com.fintech.omnipe.Util.UtilMethods;
import com.fintech.omnipe.usefull.CustomLoader;
import com.test.pg.secure.pgsdkv4.PGConstants;
import com.test.pg.secure.pgsdkv4.PaymentGatewayPaymentInitializer;
import com.test.pg.secure.pgsdkv4.PaymentParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import retrofit2.Call;
import retrofit2.Callback;


public class AddMoneyActivity extends AppCompatActivity implements PaymentResultListener, PaytmPaymentTransactionCallback {
    private static final String TAG = AddMoneyActivity.class.getSimpleName();
    View walletView;
    private AddMoneyActivity activity;
    TextView walletTv, walletAmountTv;
    ImageView arrowIv;
    EditText amountEt;
    View bhimLogo, msgView;
    RecyclerView recyclerView;
    CustomLoader loader;
    BalanceResponse balanceCheckResponse;
    private WalletTypeResponse mWalletTypeResponse;
    HashMap<String, Integer> walletIdMap = new HashMap<>();
    ArrayList<BalanceType> mBalanceTypes = new ArrayList<>();
    ArrayList<BalanceType> mActiveBalanceTypes = new ArrayList<>();
    private int selectedWalletId = 1;
    ArrayList<SlabDetailDisplayLvl> operatorArray = new ArrayList<>();
    ArrayList<PaymentGatewayType> pgList = new ArrayList<>();
    private Dialog gatewayDialog;
    private String selectedMethod;
    int selectedOPId;
    LoginResponse mLoginDataResponse;
    boolean isActivityPause;
    private boolean isDialogShowBackground;
    private String dialogMsg;
    private boolean isSucessDialog;
    private RequestPTM ptmRequest;
    private final int INTENT_UPI = 6789;
    private final int INTENT_REQUEST_CODE_PAYTM =101;
    private String upiTID;
    private String tid, bankOrderID, mvpa, terminalID;
    private TextView upiBtn;
    private NumberListResponse mOperatorListResponse;
    private CashFreeData cFItemData;
    private boolean isTransactionCancelledByUser;
    private Dialog uiWebViewDialog;
    private final int INTENT_UPI_WEB =104;
    private String paymentResponse, txnId, status, txnRef, ApprovalRefNo, TrtxnRef, responseCode, bleTxId;
    private boolean isStatus = false;
    private int currentPGId;
    private Dialog pendingTimerDialog;
    private CountDownTimer countDownTimerPendingTxn;
    private boolean isPendingTimerComplete;
    private String requestAmount = "0";
    private boolean isAllUPIStatusCheckRunning,isAllUpiDialogCanceled,isIntentUPIStatusCheckRunning;
    private int sucessDialogStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_add_money);
        activity=this;
        isActivityPause = false;
        String mLoginResponse = UtilMethods.INSTANCE.getLoginPref(this);
        mLoginDataResponse = new Gson().fromJson(mLoginResponse, LoginResponse.class);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitle("Add Money");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        walletView = findViewById(R.id.walletView);
        walletTv = findViewById(R.id.walletTv);
        walletAmountTv = findViewById(R.id.walletAmountTv);
        arrowIv = findViewById(R.id.arrowIv);
        upiBtn = findViewById(R.id.upiBtn);
        amountEt = findViewById(R.id.amountEt);
        bhimLogo = findViewById(R.id.bhimLogo);
        msgView = findViewById(R.id.msg);
        amountEt.setCompoundDrawablesWithIntrinsicBounds(
                AppCompatResources.getDrawable(this, R.drawable.ic_rupee_indian),
                null, null, null);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        UtilMethods.INSTANCE.setAppLogoIconUI(this,findViewById(R.id.appLogoIv));
        walletView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPoupWindow(v);
            }
        });

        upiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedOPId!=0) {
                    if (amountEt.getText().toString().isEmpty()) {
                        amountEt.setError("Please Enter Amount");
                        amountEt.requestFocus();
                        return;
                    }
//                    initUpi();
                    ChoosePaymentGateway(true,50);

                } else {
                    Toast.makeText(AddMoneyActivity.this, "Invalid Operator Id", Toast.LENGTH_SHORT).show();
                }
            }
        });

        balanceCheckResponse = new Gson().fromJson(UtilMethods.INSTANCE.getBalance(this), BalanceResponse.class);
        showWalletListPopupWindow();

        SharedPreferences prefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String response = prefs.getString(ApplicationConstant.INSTANCE.numberListPref, "");
        mOperatorListResponse=new Gson().fromJson(response, NumberListResponse.class);

        SlabCommissionResponse mSlabCommissionResponse=new Gson().fromJson(UtilMethods.INSTANCE.getCommList(this), SlabCommissionResponse.class);
        setUiData(mSlabCommissionResponse);
        HitCommissionApi();

    }



    private void setUiData(SlabCommissionResponse mSlabCommissionResponse) {

        /*if (UtilMethods.INSTANCE.isUPi(this) && !UtilMethods.INSTANCE.isPaymentGatway(this)) {
            upiBtn.setVisibility(View.VISIBLE);
            msgView.setVisibility(View.VISIBLE);
            bhimLogo.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            getOpId(mSlabCommissionResponse, 50);
        }*/
        if (UtilMethods.INSTANCE.isUPi(this) && !UtilMethods.INSTANCE.isPaymentGatway(this)) {
            upiBtn.setVisibility(View.GONE);
            msgView.setVisibility(View.GONE);
            bhimLogo.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            getOperator(mSlabCommissionResponse, 50, -1);
            //Toast.makeText(activity, "only upi", Toast.LENGTH_SHORT).show();
        }
        else if (UtilMethods.INSTANCE.isUPi(this) && UtilMethods.INSTANCE.isPaymentGatway(this)) {
            upiBtn.setVisibility(View.GONE);
            msgView.setVisibility(View.GONE);
            bhimLogo.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            getOperator(mSlabCommissionResponse, 50, 37);
            //Toast.makeText(activity, " upi & payment gateway", Toast.LENGTH_SHORT).show();
        }
        else if (!UtilMethods.INSTANCE.isUPi(this) && UtilMethods.INSTANCE.isPaymentGatway(this)) {
            upiBtn.setVisibility(View.GONE);
            msgView.setVisibility(View.GONE);
            bhimLogo.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            getOperator(mSlabCommissionResponse, 37, -1);

            //Toast.makeText(activity, "only payment gateway", Toast.LENGTH_SHORT).show();
        }
    }

    private void HitCommissionApi() {
        UtilMethods.INSTANCE.MyCommission(this,  loader, new UtilMethods.ApiCallBack() {
            @Override
            public void onSucess(Object object) {
                setUiData((SlabCommissionResponse) object);
            }

        });
    }

    private void showWalletListPopupWindow() {
        if (balanceCheckResponse != null) {
            mBalanceTypes=UtilMethods.INSTANCE.getUserActiveBalance(balanceCheckResponse);
            if (mBalanceTypes != null && mBalanceTypes.size() > 0) {

                setWalletIds();
            }
        }
        else {
            balanceCheckResponse = new Gson().fromJson(UtilMethods.INSTANCE.getBalance(this), BalanceResponse.class);
            if (balanceCheckResponse != null) {
                showWalletListPopupWindow();
            }
            return;
        }

    }

    private void showPoupWindow(View anchor) {
        if (mActiveBalanceTypes != null && mActiveBalanceTypes.size() > 0) {
            final ListPopupWindow listPopupWindow =
                    createListPopupWindow(anchor, ViewGroup.LayoutParams.WRAP_CONTENT, mActiveBalanceTypes);
            listPopupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.rect));
            listPopupWindow.show();
        } else {
            showWalletListPopupWindow();
        }
    }

    private ListPopupWindow createListPopupWindow(View anchor, int width, ArrayList<BalanceType> items) {
        final ListPopupWindow popup = new ListPopupWindow(this);
        ListAdapter adapter = new ListPopupWindowAdapter(items, this, false, R.layout.wallet_list_popup, new ListPopupWindowAdapter.ClickView() {
            @Override
            public void onClickView(String walletName, String amount) {
                walletTv.setText(walletName);
                walletAmountTv.setText(amount);
                selectedWalletId = walletIdMap.get(walletName);
                popup.dismiss();
            }
        });
        // popup.setWidth((int) getResources().getDimension(R.dimen._200sdp));
        popup.setAnchorView(anchor);
        popup.setAdapter(adapter);
        return popup;
    }


    private void setWalletIds() {

        if (mWalletTypeResponse != null && mWalletTypeResponse.getWalletTypes() != null && mWalletTypeResponse.getWalletTypes().size() > 0) {
            int count = 0;
            for (WalletType objectWallet : mWalletTypeResponse.getWalletTypes()) {
                if (objectWallet.getInFundProcess() && objectWallet.getActive()) {
                    for (BalanceType objectBalance : mBalanceTypes) {
                        if (objectBalance.getName().contains(objectWallet.getName())) {
                            walletIdMap.put(objectBalance.getName(), objectWallet.getId());
                            mActiveBalanceTypes.add(objectBalance);
                            if (count == 0) {
                                walletTv.setText(objectBalance.getName());
                                walletAmountTv.setText("\u20B9 " + objectBalance.getAmount());
                                selectedWalletId = objectWallet.getId();
                            }
                            count++;
                        }
                    }
                }
            }

            if(mActiveBalanceTypes!=null && mActiveBalanceTypes.size()>0){
                if (mActiveBalanceTypes.size() == 1) {
                    arrowIv.setVisibility(View.GONE);
                    walletView.setClickable(false);
                } else {
                    arrowIv.setVisibility(View.VISIBLE);
                    walletView.setClickable(true);
                }
            }

        } else {
            mWalletTypeResponse = new Gson().fromJson(UtilMethods.INSTANCE.getWalletType(this), WalletTypeResponse.class);
            if (mWalletTypeResponse != null && mWalletTypeResponse.getWalletTypes() != null && mWalletTypeResponse.getWalletTypes().size() > 0) {
                setWalletIds();
            } else {
                UtilMethods.INSTANCE.WalletType(AddMoneyActivity.this, loader, new UtilMethods.ApiCallBack() {
                    @Override
                    public void onSucess(Object object) {
                        mWalletTypeResponse = (WalletTypeResponse) object;
                        if (mWalletTypeResponse != null && mWalletTypeResponse.getWalletTypes() != null && mWalletTypeResponse.getWalletTypes().size() > 0) {
                            setWalletIds();
                        }
                    }
                });
            }
        }
    }


    private void getOperator(SlabCommissionResponse mSlabCommissionResponse, int op_Type1, int op_Type2) {

        if (mSlabCommissionResponse != null && mSlabCommissionResponse.getSlabDetailDisplayLvl() != null &&
                mSlabCommissionResponse.getSlabDetailDisplayLvl().size() > 0 && mOperatorListResponse != null && mOperatorListResponse.getData()!=null&&
                mOperatorListResponse.getData().getOperators() != null && mOperatorListResponse.getData().getOperators().size() > 0) {
            operatorArray.clear();
            for (SlabDetailDisplayLvl slab : mSlabCommissionResponse.getSlabDetailDisplayLvl()) {
                if (slab.getOpTypeId() == op_Type1 || slab.getOpTypeId() == op_Type2) {

                    for (OperatorList op : mOperatorListResponse.getData().getOperators()) {
                        if (slab.getOid() == op.getOid() && op.isActive()) {
                            operatorArray.add(slab);
                            break;
                        }
                    }
                }
            }

        } else {
            if (mSlabCommissionResponse != null && mSlabCommissionResponse.getSlabDetailDisplayLvl() != null && mSlabCommissionResponse.getSlabDetailDisplayLvl().size() > 0) {
                operatorArray.clear();
                for (SlabDetailDisplayLvl slab : mSlabCommissionResponse.getSlabDetailDisplayLvl()) {
                    if ((slab.getOpTypeId() == op_Type1 || slab.getOpTypeId() == op_Type2)) {
                        operatorArray.add(slab);
                    }

                }
            }
        }

        AddMoneyTypeAdapter addMoneyTypeAdapter = new AddMoneyTypeAdapter(operatorArray, this, mLoginDataResponse.getData().getRoleID());
        recyclerView.setAdapter(addMoneyTypeAdapter);

    }

    private void getOpId(SlabCommissionResponse mSlabCommissionResponse, int op_Type) {
        if (mSlabCommissionResponse != null && mSlabCommissionResponse.getSlabDetailDisplayLvl() != null &&
                mSlabCommissionResponse.getSlabDetailDisplayLvl().size() > 0 && mOperatorListResponse != null &&
                mOperatorListResponse.getData().getOperators() != null && mOperatorListResponse.getData().getOperators().size() > 0) {
            operatorArray.clear();
            for (SlabDetailDisplayLvl slab : mSlabCommissionResponse.getSlabDetailDisplayLvl()) {
                if (slab.getOpTypeId() == op_Type) {
                    for (OperatorList op : mOperatorListResponse.getData().getOperators()) {
                        if (slab.getOid() == op.getOid() && op.isActive()) {
                            selectedOPId = slab.getOid();
                            selectedMethod = slab.getOperator();
                            //selectedOperator = slab;
                            break;
                        }
                    }
                }
            }

        } else {
            if (mSlabCommissionResponse != null && mSlabCommissionResponse.getSlabDetailDisplayLvl() != null && mSlabCommissionResponse.getSlabDetailDisplayLvl().size() > 0) {
                for (SlabDetailDisplayLvl op : mSlabCommissionResponse.getSlabDetailDisplayLvl()) {
                    if (op.getOpTypeId() == op_Type) {
                        selectedOPId = op.getOid();
                        selectedMethod = op.getOperator();
                        //selectedOperator = op;
                    }
                }
            }
        }


    }

    public void viewRangeClick(SlabDetailDisplayLvl operator){
        loader.setCancelable(false);
        loader.show();
        UtilMethods.INSTANCE.viewRangeClick(operator,loader,this);

    }

    public void paymentTypeClick(SlabDetailDisplayLvl operator) {
        if (amountEt.getText().toString().trim().isEmpty()) {
            amountEt.setError("Please Enter Amount");
            amountEt.requestFocus();
            return;
        } else if (operator.getMin() != 0 && Integer.parseInt(amountEt.getText().toString().trim()) < operator.getMin()) {
            amountEt.setError("Amount can't be less then \u20B9 " + operator.getMin());
            amountEt.requestFocus();
            return;
        } else if (operator.getMax() != 0 && Integer.parseInt(amountEt.getText().toString().trim()) > operator.getMax()) {
            amountEt.setError("Amount can't be greater then \u20B9 " + operator.getMax());
            amountEt.requestFocus();
            return;
        }
        selectedMethod = operator.getOperator();
        selectedOPId = operator.getOid();
        //selectedOperator = operator;
        if (operator.getOpTypeId() == 50) {
            //initUpi(0);
            ChoosePaymentGateway(true, 50);
        } else {
            if (pgList != null && pgList.size() > 0) {
                if (pgList.size() == 1) {
                    startGateway(pgList.get(0),false, operator.getOpTypeId());
                } else {
                    showPopupGateWay(false, operator.getOpTypeId());
                }
            } else {
                ChoosePaymentGateway(false,operator.getOpTypeId());
            }
        }
    }

    void initUpi(int upgid) {
        loader.show();
        UtilMethods.INSTANCE.IntiateUPI(this,upgid, selectedWalletId + "", selectedOPId+"", amountEt.getText().toString(), "", loader, new UtilMethods.ApiCallBack() {
            @Override
            public void onSucess(Object object) {
               /* InitiateUpiResponse mInitiateUpiResponse = (InitiateUpiResponse) object;
                upiTID = mInitiateUpiResponse.getTid();
                openUpiIntent(getUPIString(mInitiateUpiResponse.getMvpa(), mLoginDataResponse.getData().getName(),
                        mInitiateUpiResponse.getTerminalID(), mInitiateUpiResponse.getBankOrderID(),
                        getString(R.string.app_name).replaceAll(" ", "") + "UPITransaction",
                        amountEt.getText().toString().trim(),
                        ApplicationConstant.INSTANCE.baseUrl));*/

                if (object instanceof InitiateUpiResponse) {
                    InitiateUpiResponse upiResponse = (InitiateUpiResponse) object;
                    upiTID = upiResponse.getTid() != null ? upiResponse.getTid() : "";
                    if(upiResponse.getQrIntent() != null && !upiResponse.getQrIntent().isEmpty() && upiResponse.getQrIntent().contains("upi://pay")){
                        openUpiIntent(Uri.parse(upiResponse.getQrIntent()));
                    }else{
                        bankOrderID = upiResponse.getBankOrderID() != null ? upiResponse.getBankOrderID() : "";
                        mvpa = upiResponse.getMvpa() != null ? upiResponse.getMvpa() : "";
                        terminalID = upiResponse.getTerminalID() != null ? upiResponse.getTerminalID() : "";
                        openUpiIntent(getUPIString(mvpa, mLoginDataResponse.getData().getName(),
                                terminalID, bankOrderID,
                                getString(R.string.app_name).replaceAll(" ", "") + "UPITransaction",
                                amountEt.getText().toString().trim(),
                                ApplicationConstant.INSTANCE.baseUrl));

                    }
                }
            }
        });
    }

    private Uri getUPIString(String payeeAddress, String payeeName, String merchentCode, String tref,
                             String trxnNote, String payeeAmount, String refUrl) {
        return new Uri.Builder()
                .scheme("upi")
                .authority("pay")
                .appendQueryParameter("pa", payeeAddress)
                .appendQueryParameter("pn", payeeName)
                .appendQueryParameter("mc", merchentCode)
                .appendQueryParameter("tr", tref)
                .appendQueryParameter("tn", trxnNote)
                .appendQueryParameter("am", payeeAmount)
                .appendQueryParameter("cu", "INR")
                .appendQueryParameter("url", refUrl)
                .build();
    }
    public boolean isUpiAppInstalled(Uri upi) {
        Uri uri = Uri.parse(String.valueOf(upi));
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> apps = packageManager.queryIntentActivities(intent, 0);
        return !apps.isEmpty();
    }
    void openUpiIntent(Uri upi) {
        if (!isUpiAppInstalled(upi)) {
            Toast.makeText(AddMoneyActivity.this, "No UPI app found, please install one to continue", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(upi);
        Intent chooser = Intent.createChooser(intent, "Pay with...");
        startActivityForResult(chooser, INTENT_UPI);
    }


    private void ChoosePaymentGateway(boolean isUPI,int opTypeId) {
        try {
            loader.show();
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);

            Call<AppUserListResponse> call = git.ChoosePaymentGateway(new GatewayTransactionRequest(
                    amountEt.getText().toString(),
                    selectedOPId + "",
                    isUPI,
                    mLoginDataResponse.getData().getUserID(),
                    mLoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    UtilMethods.INSTANCE.getIMEI(this),
                    "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(activity), mLoginDataResponse.getData().getSessionID(), mLoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<AppUserListResponse>() {
                @Override
                public void onResponse(Call<AppUserListResponse> call, final retrofit2.Response<AppUserListResponse> response) {

                    try {
                        if (loader.isShowing())
                            loader.dismiss();
                        if (response.body() != null && response.body().getStatuscode() == 1) {

                            if (response.body().getpGs() != null && response.body().getpGs().size() > 0) {
                                pgList = response.body().getpGs();
                                if (response.body().getpGs().size() == 1) {
                                    if(isUPI==true && opTypeId==50){
                                        initUpi(pgList.get(0).getId());
                                    }else {
                                        startGateway(pgList.get(0),isUPI,opTypeId);
                                    }
                                }
                                else {
                                    if (loader != null && loader.isShowing()) {
                                        loader.dismiss();
                                    }
                                    showPopupGateWay(isUPI,opTypeId);
                                }
                            } else {
                                UtilMethods.INSTANCE.Processing(AddMoneyActivity.this, "Service is currently down.");
                                if (loader != null && loader.isShowing()) {
                                    loader.dismiss();
                                }
                            }
                        }
                    } catch (Exception e) {
                        if (loader.isShowing())
                            loader.dismiss();

                        UtilMethods.INSTANCE.Error(activity, e.getMessage() + "");
                    }
                }

                @Override
                public void onFailure(Call<AppUserListResponse> call, Throwable t) {
                    try {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                            if (t.getMessage().contains("No address associated with hostname")) {

                                UtilMethods.INSTANCE.Error(activity, getString(R.string.err_msg_network));


                            } else {

                                UtilMethods.INSTANCE.Error(activity, t.getMessage());
                            }
                        } else {
                            UtilMethods.INSTANCE.Error(activity, getString(R.string.some_thing_error));
                        }
                    } catch (IllegalStateException ise) {
                        loader.dismiss();

                        UtilMethods.INSTANCE.Error(activity, getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader.isShowing())
                loader.dismiss();
            UtilMethods.INSTANCE.Error(activity, e.getMessage() + "");
        }
    }

    private void GatewayTransaction(final PaymentGatewayType paymentGatewayType) {
        try {
            loader.show();
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);

            Call<GatwayTransactionResponse> call = git.GatewayTransaction(new GatewayTransactionRequest(amountEt.getText().toString(), paymentGatewayType.getId() + "", selectedWalletId + "", selectedOPId + "", mLoginDataResponse.getData().getUserID(), mLoginDataResponse.getData().getLoginTypeID(), ApplicationConstant.INSTANCE.APP_ID, UtilMethods.INSTANCE.getIMEI(activity),
                    "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(activity), mLoginDataResponse.getData().getSessionID(), mLoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<GatwayTransactionResponse>() {
                @Override
                public void onResponse(Call<GatwayTransactionResponse> call, final retrofit2.Response<GatwayTransactionResponse> response) {

                    try {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                        if (response.body() != null) {
                            if (response.body().getStatuscode() == 1) {
                                if (response.body().getpGModelForApp() != null) {
                                    if (response.body().getpGModelForApp().getStatuscode() == 1) {
                                        requestAmount = amountEt.getText().toString();
                                        currentPGId = response.body().getpGModelForApp().getPgid();
                                        if (currentPGId == 0) {
                                            currentPGId = paymentGatewayType.getPgType();
                                        }
                                        if (currentPGId== 1) {
                                            if (response.body().getpGModelForApp().getRequestPTM() != null) {
                                                initPaytmSdk(response.body().getpGModelForApp().getRequestPTM());
                                            } else {
                                                UtilMethods.INSTANCE.Processing(activity, response.body().getpGModelForApp().getMsg() + "");
                                            }
                                        } else if (currentPGId== 2) {
                                            if (response.body().getpGModelForApp().getrPayRequest() != null) {
                                                initRazorPaySdk(response.body().getpGModelForApp().getrPayRequest());
                                            } else {
                                                UtilMethods.INSTANCE.Processing(activity, response.body().getpGModelForApp().getMsg() + "");
                                            }
                                        } else if (currentPGId== 3 || currentPGId==15) {

                                            initUpi(currentPGId);

                                        } else if (currentPGId== 4) {
                                            if (response.body().getpGModelForApp().getAggrePayRequest() != null) {
                                                if (response.body().getpGModelForApp().getAggrePayRequest().getStatuscode() == 1) {
                                                    if (response.body().getpGModelForApp().getAggrePayRequest().getKeyVals() != null) {
                                                        initAggrePaySdk(response.body().getpGModelForApp().getAggrePayRequest().getKeyVals());
                                                    } else {
                                                        UtilMethods.INSTANCE.Processing(activity, "Keys values not found.");
                                                    }
                                                    //AggrePay
                                                } else {
                                                    UtilMethods.INSTANCE.Processing(activity, response.body().getpGModelForApp().getAggrePayRequest().getMsg() + "");
                                                }

                                            } else {
                                                UtilMethods.INSTANCE.Processing(activity, response.body().getpGModelForApp().getMsg() + "");
                                            }
                                        } else if (currentPGId== 5) {
                                            if (response.body().getpGModelForApp().getUpiGatewayRequest() != null && response.body().getpGModelForApp().getUpiGatewayRequest().getKeyVals() != null) {
                                                initUPIPayGateWay(amountEt.getText().toString().trim(), response.body().getpGModelForApp().getUpiGatewayRequest());
                                            } else {
                                                UtilMethods.INSTANCE.Processing(activity, response.body().getpGModelForApp().getMsg() + "");
                                            }
                                        } else if (currentPGId== 7) {
                                            if (response.body().getpGModelForApp().getRequestPTM() != null && response.body().getpGModelForApp().getToken() != null && !response.body().getpGModelForApp().getToken().isEmpty()) {
                                                initUpdatePaytmSdk(response.body().getpGModelForApp().getRequestPTM(), response.body().getpGModelForApp().getToken());
                                            } else {
                                                UtilMethods.INSTANCE.Processing(activity, response.body().getpGModelForApp().getMsg() + "");
                                            }
                                        }  else if (currentPGId== 8) {
                                            if (response.body().getpGModelForApp().getCashFreeResponse() != null) {
                                                if (response.body().getpGModelForApp().getCashFreeResponse().getToken() != null && !response.body().getpGModelForApp().getCashFreeResponse().getToken().isEmpty()) {

                                                    if (response.body().getpGModelForApp().getCashFreeResponse().getOrderID() != null && !response.body().getpGModelForApp().getCashFreeResponse().getOrderID().isEmpty()) {
                                                        cFItemData = response.body().getpGModelForApp().getCashFreeResponse();
                                                        initCashFreeSdk();
                                                    } else {
                                                        UtilMethods.INSTANCE.Error(activity, "OrderId Required");
                                                    }
                                                } else {
                                                    UtilMethods.INSTANCE.Error(activity, "Token Required");
                                                }

                                            } else {
                                                UtilMethods.INSTANCE.Processing(activity, "Gateway is not available");
                                            }
                                        }
                                        else if (currentPGId==10) {
                                            if (response.body().getpGModelForApp().getUpiGatewayRequest() != null) {
                                                if (response.body().getpGModelForApp().getUpiGatewayRequest().getUrl() != null
                                                        && URLUtil.isValidUrl(response.body().getpGModelForApp().getUpiGatewayRequest().getUrl())) {
                                                    upiTID = response.body().getpGModelForApp().getTid() + "";
                                                    showUPIWebView(response.body().getpGModelForApp().getUpiGatewayRequest().getUrl());

                                                } else {
                                                    UtilMethods.INSTANCE.Error(AddMoneyActivity.this, "Url is not available");
                                                }

                                            } else {
                                                UtilMethods.INSTANCE.Processing(AddMoneyActivity.this, "Data is not available");
                                            }
                                        }
                                        else if (currentPGId==12) {
                                            if (response.body().getpGModelForApp().getUpiGatewayRequest() != null) {
                                                if (response.body().getpGModelForApp().getUpiGatewayRequest().getStatuscode() == 1) {
                                                    upiTID = response.body().getpGModelForApp().getTid() + "";
                                                    if (response.body().getpGModelForApp().getUpiGatewayRequest().isIntentAllowed() && response.body().getpGModelForApp().getUpiGatewayRequest().getIntentString() != null && response.body().getpGModelForApp().getUpiGatewayRequest().getIntentString().contains("upi://pay?")) {
                                                        openUpiIntent(Uri.parse(response.body().getpGModelForApp().getUpiGatewayRequest().getIntentString()));
                                                    } else {
                                                        if (response.body().getpGModelForApp().getUpiGatewayRequest().getUrl() != null
                                                                && URLUtil.isValidUrl(response.body().getpGModelForApp().getUpiGatewayRequest().getUrl())) {

                                                            showUPIWebView(response.body().getpGModelForApp().getUpiGatewayRequest().getUrl());
                                                            // openUpiWeb(response.body().getpGModelForApp().getUpiGatewayRequest().getUrl());
                                                        } else {
                                                            UtilMethods.INSTANCE.Error(AddMoneyActivity.this, "Url is not available");
                                                        }
                                                    }
                                                } else {
                                                    UtilMethods.INSTANCE.Error(AddMoneyActivity.this, response.body().getpGModelForApp().getUpiGatewayRequest().getMsg() + "");
                                                }

                                            }
                                            else {
                                                UtilMethods.INSTANCE.Error(AddMoneyActivity.this, "Data is not available");
                                            }
                                        }

                                    } else {
                                        UtilMethods.INSTANCE.Error(activity, response.body().getpGModelForApp().getMsg() + "");
                                    }
                                } else {
                                    UtilMethods.INSTANCE.Error(activity, response.body().getMsg() + " " + getString(R.string.some_thing_error));
                                }
                            } else {
                                UtilMethods.INSTANCE.Error(activity, response.body().getMsg() + "");
                            }

                        } else {
                            UtilMethods.INSTANCE.Error(activity, getString(R.string.some_thing_error));
                        }

                    } catch (Exception e) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                        UtilMethods.INSTANCE.Error(activity, e.getMessage() + "");
                    }
                }

                @Override
                public void onFailure(Call<GatwayTransactionResponse> call, Throwable t) {
                    try {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                            if (t.getMessage().contains("No address associated with hostname")) {
                                UtilMethods.INSTANCE.Error(activity, getString(R.string.err_msg_network));
                            } else {
                                UtilMethods.INSTANCE.Error(activity, t.getMessage());
                            }

                        } else {
                            UtilMethods.INSTANCE.Error(activity, getString(R.string.some_thing_error));
                        }
                    } catch (IllegalStateException ise) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                        UtilMethods.INSTANCE.Error(activity, getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader.isShowing()) {
                loader.dismiss();
            }
            UtilMethods.INSTANCE.Error(activity, e.getMessage() + "");
        }
    }


    private void showPopupGateWay(boolean isUPI,int opTypeId) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE); // or (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewMyLayout = inflater.inflate(R.layout.dialog_select_gateway, null);
        RecyclerView recyclerView = viewMyLayout.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        View closeBtn = viewMyLayout.findViewById(R.id.closeBtn);

        GatewayTypeAdapter gatewayTypeAdapter = new GatewayTypeAdapter(pgList, AddMoneyActivity.this,isUPI,opTypeId);
        recyclerView.setAdapter(gatewayTypeAdapter);
        gatewayDialog = new Dialog(this, R.style.Theme_AppCompat_Dialog_Alert);
        gatewayDialog.setCancelable(false);
        gatewayDialog.setContentView(viewMyLayout);
        /* dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));*/
        gatewayDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gatewayDialog.dismiss();
            }
        });

        gatewayDialog.show();
        // Window window = dialog.getWindow();
        //window.setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.MATCH_PARENT);


    }

    public void startGateway(PaymentGatewayType paymentGatewayType, boolean isUPI,int opTypeId) {

        if (gatewayDialog != null && gatewayDialog.isShowing()) {
            gatewayDialog.dismiss();
        }
        if(isUPI==true && opTypeId==50){
            initUpi(paymentGatewayType.getId());
        }else {
            GatewayTransaction(paymentGatewayType);
        }

    }
      /*------PayTM---------------*/
    void initPaytmSdk(final RequestPTM requestPTM) {
        PaytmPGService mService = /*BuildConfig.DEBUG ? PaytmPGService.getStagingService() :*/ PaytmPGService.getProductionService();
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("MID", requestPTM.getMid() + "");
        paramMap.put("ORDER_ID", requestPTM.getOrdeR_ID() + "");
        paramMap.put("CUST_ID", requestPTM.getCusT_ID() + "");
        paramMap.put("MOBILE_NO", requestPTM.getMobilE_NO() + "");
        paramMap.put("EMAIL", requestPTM.getEmail() + "");
        paramMap.put("CHANNEL_ID", requestPTM.getChanneL_ID() + "");
        paramMap.put("TXN_AMOUNT", requestPTM.getTxN_AMOUNT() + "");
        paramMap.put("WEBSITE", requestPTM.getWebsite() + "");
        paramMap.put("INDUSTRY_TYPE_ID", requestPTM.getIndustrY_TYPE_ID() + "");
        //BuildConfig.DEBUG ? "https://securegw-stage.paytm.in/theia/paytmCallback?ORDER_ID=" + data.getNewOrderNo() : "https://securegw.paytm.in/theia/paytmCallback?ORDER_ID=" + data.getNewOrderNo()
        paramMap.put("CALLBACK_URL", requestPTM.getCallbacK_URL() + "");
        paramMap.put("CHECKSUMHASH", requestPTM.getChecksumhash() + "");
        PaytmOrder Order = new PaytmOrder(paramMap);
        mService.initialize(Order, null);

        mService.startPaymentTransaction(this, true, true, new PaytmPaymentTransactionCallback() {
            /*  Call Backs*/
            public void someUIErrorOccurred(String inErrorMessage) {
                paytmCallBackApi(paytmFailedData(requestPTM, 0, "TXN_CANCEL", inErrorMessage));

            }

            public void onTransactionResponse(Bundle inResponse) {
                paytmCallBackApi(inResponse);
            }

            public void networkNotAvailable() {
                paytmCallBackApi(paytmFailedData(requestPTM, 0, "TXN_CANCEL", "Network not available"));

            }

            @Override
            public void onErrorProceed(String s) {

            }

            public void clientAuthenticationFailed(String inErrorMessage) {
                paytmCallBackApi(paytmFailedData(requestPTM, 0, "TXN_CANCEL", inErrorMessage));
                // Toast.makeText(getApplicationContext(), "Authentication failed: Server error" + inErrorMessage, Toast.LENGTH_LONG).show();
            }

            public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) {
                paytmCallBackApi(paytmFailedData(requestPTM, iniErrorCode, "TXN_CANCEL", inErrorMessage));
                // Toast.makeText(getApplicationContext(), "Unable to load webpage " + inErrorMessage, Toast.LENGTH_LONG).show();
            }

            public void onBackPressedCancelTransaction() {
                paytmCallBackApi(paytmFailedData(requestPTM, 0, "TXN_CANCEL", "Transaction canceled by user"));

            }

            public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
                paytmCallBackApi(inResponse);
            }
        });
    }

    /*--------------------------Razor Pay-----------------------*/

    private void initRazorPaySdk(RequestRazorPay mRequestRazorPay) {

        Checkout checkout = new Checkout();
        checkout.setKeyID(mRequestRazorPay.getKey_id());
        checkout.setImage(R.drawable.rnd_logo);

        try {
            JSONObject options = new JSONObject();
            options.put("name", mRequestRazorPay.getPrefill_name());
            options.put("theme.color", "#" + Integer.toHexString(ContextCompat.getColor(this, R.color.colorPrimary) & 0x00ffffff));
            options.put("prefill.contact", mRequestRazorPay.getPrefill_contact());
            options.put("prefill.name", mRequestRazorPay.getPrefill_name());
            options.put("prefill.email", mRequestRazorPay.getPrefill_email());

            if (selectedMethod != null) {
                if (selectedMethod.toLowerCase().contains("card")) {
                    options.put("prefill.method", "card");
                }
                if (selectedMethod.toLowerCase().contains("net banking") || selectedMethod.toLowerCase().contains("netbanking")) {
                    options.put("prefill.method", "netbanking");
                }
                if (selectedMethod.toLowerCase().contains("upi")) {
                    options.put("prefill.method", "upi");
                }
                if (selectedMethod.toLowerCase().contains("wallet")) {
                    options.put("prefill.method", "wallet");
                }
                if (selectedMethod.toLowerCase().contains("emi")) {
                    options.put("prefill.method", "emi");
                }
            }
            if (mRequestRazorPay.getDescription() != null && !mRequestRazorPay.getDescription().isEmpty()) {
                options.put("description", mRequestRazorPay.getDescription());
            }
            options.put("image", ApplicationConstant.INSTANCE.baseUrl + mRequestRazorPay.getImage());
            options.put("order_id", mRequestRazorPay.getOrder_id() + "");
            options.put("currency", "INR");

            /**
             * Amount is always passed in currency subunits
             * Eg: "500" = INR 5.00
             */
            options.put("amount", (mRequestRazorPay.getAmount() * 100));

            checkout.open(AddMoneyActivity.this, options);
        } catch (Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }

    /*---------------Initiate AggrePay Gat way-----------*/

    private void initAggrePaySdk(KeyVals mKeyVals) {
        PaymentParams pgPaymentParams = new PaymentParams();
        pgPaymentParams.setAPiKey(mKeyVals.getApiKey() + "");
        pgPaymentParams.setAmount(mKeyVals.getAmount() + "");
        pgPaymentParams.setEmail(mKeyVals.getEmail() + "");
        pgPaymentParams.setName(mKeyVals.getName() + "");
        pgPaymentParams.setPhone(mKeyVals.getPhone() + "");
        pgPaymentParams.setOrderId(mKeyVals.getOrderId() + "");
        pgPaymentParams.setCurrency(mKeyVals.getCurrency() + "");
        pgPaymentParams.setDescription(mKeyVals.getDescription() + "");
        pgPaymentParams.setCity(mKeyVals.getCity() + "");
        pgPaymentParams.setState(mKeyVals.getState() + "");
        /*pgPaymentParams.setAddressLine1(SampleAppConstants.PG_ADD_1);
        pgPaymentParams.setAddressLine2(SampleAppConstants.PG_ADD_2);*/
        pgPaymentParams.setZipCode(mKeyVals.getZipCode() + "");
        pgPaymentParams.setCountry(mKeyVals.getCountry() + "");
        pgPaymentParams.setReturnUrl(mKeyVals.getReturnUrl() != null &&
                (mKeyVals.getReturnUrl().contains("http") || mKeyVals.getReturnUrl().contains("www.")) ?
                mKeyVals.getReturnUrl() + "" : ApplicationConstant.INSTANCE.baseUrl + mKeyVals.getReturnUrl() + "");
        pgPaymentParams.setMode(mKeyVals.getMode() + "");
        /*pgPaymentParams.setUdf1(SampleAppConstants.PG_UDF1);
        pgPaymentParams.setUdf2(SampleAppConstants.PG_UDF2);
        pgPaymentParams.setUdf3(SampleAppConstants.PG_UDF3);
        pgPaymentParams.setUdf4(SampleAppConstants.PG_UDF4);
        pgPaymentParams.setUdf5(SampleAppConstants.PG_UDF5);*/

        PaymentGatewayPaymentInitializer pgPaymentInitialzer = new PaymentGatewayPaymentInitializer(pgPaymentParams, this);
        pgPaymentInitialzer.initiatePaymentProcess();
    }

    /*---------------Initiate UPIGateway-----------*/
    private void initUPIPayGateWay(String amount, final UPIGatewayRequest upiGatewayRequest) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE); // or (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewMyLayout = inflater.inflate(R.layout.dialog_payment_upi, null);
        View closeBtn = viewMyLayout.findViewById(R.id.closeBtn);
        View btn_pay = viewMyLayout.findViewById(R.id.upiBtn);
        final EditText amountTv = viewMyLayout.findViewById(R.id.amountEt);
        final EditText vpaEt = viewMyLayout.findViewById(R.id.vpaEt);

        amountTv.setText("" + amount);
        amountTv.setEnabled(false);

        gatewayDialog = new Dialog(this, R.style.Theme_AppCompat_Dialog_Alert);
        gatewayDialog.setCancelable(false);
        gatewayDialog.setContentView(viewMyLayout);
        /* dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));*/
        gatewayDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gatewayDialog.dismiss();
            }
        });

        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (amountEt.getText().toString().isEmpty()) {
                    amountEt.setError(getResources().getString(R.string.err_empty_field));
                    amountEt.requestFocus();
                } else if (vpaEt.getText().toString().isEmpty()) {
                    vpaEt.setError(getResources().getString(R.string.err_empty_field));
                    vpaEt.requestFocus();
                } else {
                    if (!isUpiValid(vpaEt.getText().toString().trim())) {
                        Toast.makeText(getApplicationContext(), "Invalid UPI", Toast.LENGTH_LONG).show();
                        return;
                    }

                    openPaymentGatwayUPI(upiGatewayRequest.getKeyVals(), vpaEt);

                }
            }


        });

        gatewayDialog.show();

    }

    private void openPaymentGatwayUPI(KeyVals vals, EditText vpaEt) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("upigateway.com")
                .appendPath("gateway")
                .appendPath("android")
                .appendQueryParameter("key", vals.getKey())
                .appendQueryParameter("client_vpa", vpaEt.getText().toString().trim())
                .appendQueryParameter("client_txn_id", vals.getClientTxnId())
                .appendQueryParameter("amount", vals.getAmount() + "")
                .appendQueryParameter("p_info", vals.getpInfo())
                .appendQueryParameter("client_name", vals.getClientName())
                .appendQueryParameter("client_email", vals.getClientEmail())
                .appendQueryParameter("client_mobile", vals.getClientMobile())
                .appendQueryParameter("udf1", vals.getUdf1())
                .appendQueryParameter("udf2", vals.getUdf2())
                .appendQueryParameter("udf3", vals.getUdf3())
                .appendQueryParameter("redirect_url", vals.getRedirectUrl());

        Intent intent = new Intent(Intent.ACTION_VIEW, builder.build());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setPackage("com.android.chrome");
        gatewayDialog.dismiss();
        amountEt.setText("");
        try {
            getApplicationContext().startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            // Chrome browser presumably not installed so allow user to choose instead
            intent.setPackage(null);
            getApplicationContext().startActivity(intent);
        }
    }

    public boolean isUpiValid(String text) {
        return text.matches("^[\\w-]+@\\w+$");
    }

    /*-----------------------Updated Paytm--------------*/
    private void initUpdatePaytmSdk(RequestPTM requestPTM, String token) {
        String host = "https://securegw.paytm.in/";
        ptmRequest = requestPTM;
        PaytmOrder paytmOrder = new PaytmOrder(requestPTM.getOrdeR_ID(),
                requestPTM.getMid(),
                token, amountEt.getText().toString(),
                requestPTM.getCallbacK_URL());

        TransactionManager transactionManager = new TransactionManager(paytmOrder, this);
        // transactionManager.setAppInvokeEnabled(false);
        transactionManager.setShowPaymentUrl(host + "theia/api/v1/showPaymentPage");
        transactionManager.startTransaction(activity, INTENT_REQUEST_CODE_PAYTM);
    }



    /*----------------PayTM Call Back--------------------------*/
    private Bundle payTmSuccessData(PayTmSuccessItemResponse itemResponse) throws JSONException {
        Bundle inResponse = new Bundle();
        inResponse.putString("STATUS", itemResponse.getStatus());
        inResponse.putString("CHECKSUMHASH", itemResponse.getChecksumhash());
        inResponse.putString("BANKNAME", "");
        inResponse.putString("ORDERID", itemResponse.getOrderid());
        inResponse.putString("TXNAMOUNT", itemResponse.getTxnamount());
        inResponse.putString("MID", itemResponse.getMid());
        inResponse.putString("TXNID", itemResponse.getTxnid());
        inResponse.putString("RESPCODE", itemResponse.getRespcode());
        inResponse.putString("PAYMENTMODE", itemResponse.getPaymentmode());
        inResponse.putString("BANKTXNID", itemResponse.getBanktxnid());
        inResponse.putString("CURRENCY", itemResponse.getCurrency());
        inResponse.putString("GATEWAYNAME", itemResponse.getGatewayname());
        inResponse.putString("TXNDATE", itemResponse.getTxndate());
        inResponse.putString("RESPMSG", itemResponse.getRespmsg());
        return inResponse;
 }
    private Bundle paytmFailedData(RequestPTM requestPTM, int errorCode, String status, String errorMsg) {
        Bundle inResponse = new Bundle();
        inResponse.putString("STATUS", status);
        inResponse.putString("CHECKSUMHASH", requestPTM.getChecksumhash());
        inResponse.putString("BANKNAME", "");
        inResponse.putString("ORDERID", requestPTM.getOrdeR_ID());
        inResponse.putString("TXNAMOUNT", requestPTM.getTxN_AMOUNT());
        inResponse.putString("MID", requestPTM.getMid());
        inResponse.putString("TXNID", "");
        inResponse.putString("RESPCODE", errorCode + "");
        inResponse.putString("PAYMENTMODE", "");
        inResponse.putString("BANKTXNID", "");
        inResponse.putString("CURRENCY", "INR");
        inResponse.putString("GATEWAYNAME", "");
        inResponse.putString("RESPMSG", errorMsg);

        return inResponse;
    }
    private void paytmCallBackApi(Bundle inResponse) {
        JsonObject json = new JsonObject();
        if (inResponse != null) {
            Set<String> keys = inResponse.keySet();
            for (String key : keys) {
                json.addProperty(key, (String) inResponse.get(key));
            }
        }
        PayTMTransactionUpdate(json);
    }
    public void PayTMTransactionUpdate(JsonObject response) {
        try {
            if (!isActivityPause) {
                loader.show();
            }
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<BasicResponse> call = git.PayTMTransactionUpdate(new PayTMTransactionUpdateRequest(response, mLoginDataResponse.getData().getUserID(),
                    mLoginDataResponse.getData().getLoginTypeID(), ApplicationConstant.INSTANCE.APP_ID, UtilMethods.INSTANCE.getIMEI(AddMoneyActivity.this),
                    "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(AddMoneyActivity.this), mLoginDataResponse.getData().getSessionID(),
                    mLoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<BasicResponse>() {
                @Override
                public void onResponse(Call<BasicResponse> call, final retrofit2.Response<BasicResponse> response) {

                    try {
                        if (loader!=null && loader.isShowing()) {
                            loader.dismiss();
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 1) {
                                    amountEt.setText("");
                                    UtilMethods.INSTANCE.BalancecheckNew(AddMoneyActivity.this, loader,null,null, new UtilMethods.ApiCallBack() {
                                        @Override
                                        public void onSucess(Object object) {
                                            balanceCheckResponse = (BalanceResponse) object;
                                            if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null) {
                                                showWalletListPopupWindow();
                                            }
                                        }
                                    });

                                    if (!isActivityPause) {
                                        UtilMethods.INSTANCE.Successful(AddMoneyActivity.this, "Money Added Successfully");
                                    } else {
                                        isDialogShowBackground = true;
                                        dialogMsg = "Money Added Successfully";
                                        isSucessDialog = true;
                                    }
                                } else {

                                    if (!isActivityPause) {
                                        UtilMethods.INSTANCE.Error(AddMoneyActivity.this, response.body().getMsg() + "");
                                    } else {
                                        isDialogShowBackground = true;
                                        dialogMsg = response.body().getMsg() + "";
                                        isSucessDialog = false;
                                    }
                                }

                            } else {

                                if (!isActivityPause) {
                                    UtilMethods.INSTANCE.Error(AddMoneyActivity.this, getString(R.string.some_thing_error));
                                } else {
                                    isDialogShowBackground = true;
                                    dialogMsg = getString(R.string.some_thing_error);
                                    isSucessDialog = false;
                                }
                            }
                        } else {
                            UtilMethods.INSTANCE.apiErrorHandle(AddMoneyActivity.this, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }

                        if (!isActivityPause) {
                            UtilMethods.INSTANCE.Error(AddMoneyActivity.this, e.getMessage() + "");
                        } else {
                            isDialogShowBackground = true;
                            dialogMsg = e.getMessage() + "";
                            isSucessDialog = false;
                        }
                    }
                }

                @Override
                public void onFailure(Call<BasicResponse> call, Throwable t) {
                    try {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }

                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            if (!isActivityPause) {
                                UtilMethods.INSTANCE.NetworkError(AddMoneyActivity.this, getString(R.string.network_error_title), getString(R.string.err_msg_network));

                            } else {
                                isDialogShowBackground = true;
                                dialogMsg = getString(R.string.err_msg_network);
                                isSucessDialog = false;
                            }
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            if (!isActivityPause) {
                                UtilMethods.INSTANCE.ErrorWithTitle(AddMoneyActivity.this, "TIME OUT ERROR", t.getMessage() + "");
                            } else {
                                isDialogShowBackground = true;
                                dialogMsg = t.getMessage();
                                isSucessDialog = false;
                            }
                        } else {
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                if (!isActivityPause) {
                                    UtilMethods.INSTANCE.ErrorWithTitle(AddMoneyActivity.this, "FATAL ERROR", t.getMessage() + "");
                                } else {
                                    isDialogShowBackground = true;
                                    dialogMsg = t.getMessage();
                                    isSucessDialog = false;
                                }
                            } else {
                                if (!isActivityPause) {
                                    UtilMethods.INSTANCE.Error(AddMoneyActivity.this, getString(R.string.some_thing_error));
                                } else {
                                    isDialogShowBackground = true;
                                    dialogMsg = t.getMessage();
                                    isSucessDialog = false;
                                }
                            }
                        }


                    } catch (IllegalStateException ise) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }

                        if (!isActivityPause) {
                            UtilMethods.INSTANCE.Error(AddMoneyActivity.this, getString(R.string.some_thing_error));
                        } else {
                            isDialogShowBackground = true;
                            dialogMsg = getString(R.string.some_thing_error);
                            isSucessDialog = false;
                        }
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader.isShowing()) {
                loader.dismiss();
            }

            if (!isActivityPause) {
                UtilMethods.INSTANCE.Error(AddMoneyActivity.this, e.getMessage() + "");
            } else {
                isDialogShowBackground = true;
                dialogMsg = e.getMessage() + "";
                isSucessDialog = false;
            }
        }
    }

    /*--------------------AggreGate Pay Call Back----------------*/

    public void AggrePayTransactionUpdate(String tid, int amount, String hash) {
        try {
            if (!isActivityPause) {
                loader.show();
            }
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<BasicResponse> call = git.AggrePayTransactionUpdate(new AggrePayTransactionUpdateRequest(tid, amount, hash,
                    mLoginDataResponse.getData().getUserID(),
                    mLoginDataResponse.getData().getLoginTypeID(), ApplicationConstant.INSTANCE.APP_ID,
                    UtilMethods.INSTANCE.getIMEI(activity),
                    "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(activity),
                    mLoginDataResponse.getData().getSessionID(),
                    mLoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<BasicResponse>() {
                @Override
                public void onResponse(Call<BasicResponse> call, final retrofit2.Response<BasicResponse> response) {

                    try {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                        if (response.body() != null) {
                            if (response.body().getStatuscode() == 2) {
                                amountEt.setText("");
                                UtilMethods.INSTANCE.BalancecheckNew(activity, loader, null, null, new UtilMethods.ApiCallBack() {
                                    @Override
                                    public void onSucess(Object object) {
                                        balanceCheckResponse = (BalanceResponse) object;
                                        if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null) {
                                            showWalletListPopupWindow();
                                        }
                                    }
                                });

                                if (!isActivityPause) {
                                    UtilMethods.INSTANCE.Successful(activity, "Money Added Sucessfully");
                                } else {
                                    isDialogShowBackground = true;
                                    dialogMsg = "Money Added Sucessfully";
                                    isSucessDialog = true;
                                }
                            } else if (response.body().getStatuscode() == 1) {
                                amountEt.setText("");

                                if (!isActivityPause) {
                                    UtilMethods.INSTANCE.Processing(activity, "Your transaction under process, please wait 48 Hour to confirmation.");
                                } else {
                                    isDialogShowBackground = true;
                                    dialogMsg = "Your transaction under process, please wait 48 Hour to confirmation.";
                                    isSucessDialog = true;
                                }
                            } else {

                                if (!isActivityPause) {
                                    UtilMethods.INSTANCE.Error(activity, response.body().getMsg() + "");
                                } else {
                                    isDialogShowBackground = true;
                                    dialogMsg = response.body().getMsg() + "";
                                    isSucessDialog = false;
                                }
                            }

                        } else {

                            if (!isActivityPause) {
                                UtilMethods.INSTANCE.Error(activity, getString(R.string.some_thing_error));
                            } else {
                                isDialogShowBackground = true;
                                dialogMsg = getString(R.string.some_thing_error);
                                isSucessDialog = false;
                            }
                        }

                    } catch (Exception e) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }

                        if (!isActivityPause) {
                            UtilMethods.INSTANCE.Error(activity, e.getMessage() + "");
                        } else {
                            isDialogShowBackground = true;
                            dialogMsg = e.getMessage() + "";
                            isSucessDialog = false;
                        }
                    }
                }

                @Override
                public void onFailure(Call<BasicResponse> call, Throwable t) {
                    try {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                            if (t.getMessage().contains("No address associated with hostname")) {

                                if (!isActivityPause) {
                                    UtilMethods.INSTANCE.Error(activity, getString(R.string.err_msg_network));
                                } else {
                                    isDialogShowBackground = true;
                                    dialogMsg = getString(R.string.err_msg_network);
                                    isSucessDialog = false;
                                }
                            } else {

                                if (!isActivityPause) {
                                    UtilMethods.INSTANCE.Error(activity, t.getMessage());
                                } else {
                                    isDialogShowBackground = true;
                                    dialogMsg = t.getMessage();
                                    isSucessDialog = false;
                                }
                            }

                        } else {

                            if (!isActivityPause) {
                                UtilMethods.INSTANCE.Error(activity, getString(R.string.some_thing_error));
                            } else {
                                isDialogShowBackground = true;
                                dialogMsg = getString(R.string.some_thing_error);
                                isSucessDialog = false;
                            }
                        }
                    } catch (IllegalStateException ise) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }

                        if (!isActivityPause) {
                            UtilMethods.INSTANCE.Error(activity, getString(R.string.some_thing_error));
                        } else {
                            isDialogShowBackground = true;
                            dialogMsg = getString(R.string.some_thing_error);
                            isSucessDialog = false;
                        }
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader.isShowing()) {
                loader.dismiss();
            }

            if (!isActivityPause) {
                UtilMethods.INSTANCE.Error(activity, e.getMessage() + "");
            } else {
                isDialogShowBackground = true;
                dialogMsg = e.getMessage() + "";
                isSucessDialog = false;
            }
        }
    }

    /*-------Cash Free SDK Integrations..----*/

    private void initCashFreeSdk() {

        try {
            CFPaymentService cfPaymentService = CFPaymentService.getCFPaymentServiceInstance();
            cfPaymentService.setOrientation(0);
            cfPaymentService.setConfirmOnExit(true);
            String colorValue1 = "#6876F1", colorValue2 = "#FFFFFF";

            try {
                colorValue1 = "#" + Integer.toHexString(ContextCompat.getColor(this, R.color.colorPrimary) & 0x00ffffff);
            } catch (NullPointerException npe) {

            } catch (Exception e) {

            }

            if (selectedMethod.toLowerCase().contains("upi")) {

                Intent upiIntent = new Intent(Intent.ACTION_VIEW);
                upiIntent.setData(Uri.parse("upi://pay"));


                if (upiIntent.resolveActivity(getPackageManager()) != null) {
                    cfPaymentService.upiPayment(this, getInputParams(), cFItemData.getToken(), ApplicationConstant.INSTANCE.CFStage);

                } else {
                    cfPaymentService.doPayment(this, getInputParams(), cFItemData.getToken(), ApplicationConstant.INSTANCE.CFStage, colorValue1, colorValue2, false);

                }

            } else {
                cfPaymentService.doPayment(this, getInputParams(), cFItemData.getToken(), ApplicationConstant.INSTANCE.CFStage, colorValue1, colorValue2, false);

            }

        } catch (Exception exception) {
            UtilMethods.INSTANCE.Error(this, "" + exception.getMessage());
        }

    }

    private Map<String, String> getInputParams() {

        String orderAppId = "", orderAmount = "", orderId = "", orderNote = "", customerName = "", customerPhone = "", customerEmail = "", orderCurrency = "INR", orderNotifyURL = "";

        if (cFItemData.getAppId() != null && !cFItemData.getAppId().isEmpty())
            orderAppId = cFItemData.getAppId();


        if (cFItemData.getOrderAmount() != null)
            orderAmount = String.valueOf(cFItemData.getOrderAmount());

        else
            orderAmount = amountEt.getText().toString();

        if (cFItemData.getOrderID() != null && !cFItemData.getOrderID().isEmpty())
            orderId = cFItemData.getOrderID();

        customerName = mLoginDataResponse.getData().getName();

        if (cFItemData.getCustomerPhone() != null && !cFItemData.getCustomerPhone().isEmpty())
            customerPhone = cFItemData.getCustomerPhone();
        else
            customerPhone = mLoginDataResponse.getData().getMobileNo();

        if (cFItemData.getCustomerEmail() != null && !cFItemData.getCustomerEmail().isEmpty())
            customerEmail = cFItemData.getCustomerEmail();
        else
            customerEmail = mLoginDataResponse.getData().getEmailID();

        if (cFItemData.getOrderCurrency() != null && !cFItemData.getOrderCurrency().isEmpty())
            orderCurrency = cFItemData.getOrderCurrency().trim();

        if (cFItemData.getNotifyUrl() != null)
            orderNotifyURL = cFItemData.getNotifyUrl();

        Map<String, String> params = new HashMap<>();

        params.put(PARAM_APP_ID, orderAppId);
        params.put(PARAM_ORDER_ID, orderId);
        if (selectedMethod.toLowerCase().contains("upi")) {
            params.put(PARAM_PAYMENT_MODES, "upi");
        } else if (selectedMethod.toLowerCase().contains("credit")) {
            params.put(PARAM_PAYMENT_MODES, "cc");
        } else if (selectedMethod.toLowerCase().contains("debit")) {
            params.put(PARAM_PAYMENT_MODES, "dc");
        } else if (selectedMethod.toLowerCase().contains("net")) {
            params.put(PARAM_PAYMENT_MODES, "nb");
        } else if (selectedMethod.toLowerCase().contains("wallet")) {
            params.put(PARAM_PAYMENT_MODES, "wallet");
        }

        params.put(PARAM_ORDER_AMOUNT, orderAmount);
        params.put(PARAM_ORDER_NOTE, orderNote);
        params.put(PARAM_CUSTOMER_NAME, customerName);
        params.put(PARAM_CUSTOMER_PHONE, customerPhone);
        params.put(PARAM_CUSTOMER_EMAIL, customerEmail);
        params.put(PARAM_ORDER_CURRENCY, orderCurrency);
        params.put(PARAM_NOTIFY_URL, orderNotifyURL);

       /* for(Map.Entry entry : params.entrySet()) {
            Log.e("CFSKDRequest", entry.getKey() + " " + entry.getValue());
        }*/
        return params;
    }

    private JSONObject transResponseBundleToJsonOb(Bundle bundle) {
        {
            if (bundle == null) {
                return null;
            }
            JSONObject json = new JSONObject();
            JsonObject json1 = new JsonObject();
            for (String key : bundle.keySet()) {
                if (bundle.getString(key) != null) {
                    Log.e("CashFreeCallBack : ", key + " : " + bundle.getString(key));
                    try {
                        // json.put(key, bundle.get(key)); see edit below
                        json.put(key, JSONObject.wrap(bundle.get(key)));
                        json1.addProperty(key, (String) bundle.get(key));
                    } catch (JSONException e) {
                        //Handle exception here
                    }

                }
            }
            /*return stringBuilder.substring(0, stringBuilder.length() - 1);*/
            return json;

        }
    }

    private Bundle cashFreeSuccessData(CashFreeData cFResponseItem, JSONObject sdkResponseItem, int statusCode) {
        Bundle inResponse = new Bundle();
        try {
            inResponse.putString("STATUS", sdkResponseItem.getString("txStatus"));
        } catch (JSONException e) {
            e.printStackTrace();
            inResponse.putString("STATUS", "");//STATUS
        }
        try {
            /*signature : 4Kt4VfWYu2yRvFqXFrKO5sufD9N5/9fSvXCVaUN0F/4=*/
            inResponse.putString("CHECKSUMHASH", "" + sdkResponseItem.getString("signature"));
        } catch (JSONException e) {
            e.printStackTrace();
            inResponse.putString("CHECKSUMHASH", "");
        }

        inResponse.putString("BANKNAME", "");
        try {
            inResponse.putString("ORDERID", sdkResponseItem.getString("orderId"));
        } catch (JSONException e) {
            e.printStackTrace();
            inResponse.putString("ORDERID", cFResponseItem.getOrderID());//ORDERID
        }

        try {
            inResponse.putString("TXNAMOUNT", String.valueOf(sdkResponseItem.getDouble("orderAmount")));
        } catch (JSONException e) {
            e.printStackTrace();
            inResponse.putString("TXNAMOUNT", amountEt.getText().toString() + "");//TXNAMOUNT
        }
        inResponse.putString("MID", "");

        try {
            inResponse.putString("TXNID", sdkResponseItem.getString("referenceId"));
        } catch (JSONException e) {
            e.printStackTrace();
            inResponse.putString("TXNID", "");//TXNID
        }
        /*if(statusCode==1){

        }*/
        inResponse.putString("RESPCODE", statusCode + "");//RESPCODE
        try {
            inResponse.putString("PAYMENTMODE", sdkResponseItem.getString("paymentMode"));
        } catch (JSONException e) {
            e.printStackTrace();
            inResponse.putString("PAYMENTMODE", "" + cFResponseItem.getPaymentModes());//PAYMENTMODE
        }
        try {
            inResponse.putString("BANKTXNID", sdkResponseItem.getString("referenceId"));
        } catch (JSONException e) {
            e.printStackTrace();
            inResponse.putString("BANKTXNID", "");//BANKTXNID
        }
        inResponse.putString("CURRENCY", "INR");
        try {
            inResponse.putString("GATEWAYNAME", sdkResponseItem.getString("type"));
        } catch (JSONException e) {
            e.printStackTrace();
            inResponse.putString("GATEWAYNAME", "");//GATEWAYNAME
        }
        try {
            inResponse.putString("TXNDATE", sdkResponseItem.getString("txTime"));//txTime
        } catch (JSONException e) {
            e.printStackTrace();
            inResponse.putString("TXNDATE", "");//TXNDATE
        }
        try {
            inResponse.putString("RESPMSG", sdkResponseItem.getString("txMsg"));//txMsg
        } catch (JSONException e) {
            e.printStackTrace();
            inResponse.putString("RESPMSG", "");//txMsg
        }
        return inResponse;
    }

    private Bundle cashFreeFailedData(CashFreeData requestCFData, int errorCode, String status, String errorMsg) {
        Bundle inResponse = new Bundle();
        inResponse.putString("STATUS", status);
        inResponse.putString("CHECKSUMHASH", "");
        inResponse.putString("BANKNAME", "");
        inResponse.putString("ORDERID", requestCFData.getOrderID());
        inResponse.putString("TXNAMOUNT", String.valueOf(requestCFData.getOrderAmount()));
        inResponse.putString("MID", "");
        inResponse.putString("TXNID", "");
        inResponse.putString("RESPCODE", errorCode + "");
        inResponse.putString("PAYMENTMODE", "");
        inResponse.putString("BANKTXNID", "");
        inResponse.putString("CURRENCY", "INR");
        inResponse.putString("GATEWAYNAME", "");
        inResponse.putString("RESPMSG", errorMsg);

        return inResponse;
    }

    private void cashFreeCallBackApi(Bundle inResponse) {
        JsonObject json = new JsonObject();
        if (inResponse != null) {
            Set<String> keys = inResponse.keySet();
            for (String key : keys) {
                json.addProperty(key, (String) inResponse.get(key));
            }
        }
        CashFreeTransactionUpdate(json);
    }

    public void CashFreeTransactionUpdate(JsonObject response) {
        try {
            if (!isActivityPause) {
                loader.show();
            }
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<BasicResponse> call;
            call = git.CashFreeTransactionUpdate(new PayTMTransactionUpdateRequest(response, mLoginDataResponse.getData().getUserID(), mLoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, UtilMethods.INSTANCE.getDeviceId(activity),
                    "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(activity), mLoginDataResponse.getData().getSessionID(),
                    mLoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<BasicResponse>() {
                @Override
                public void onResponse(Call<BasicResponse> call, final retrofit2.Response<BasicResponse> response) {

                    try {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {

                                if (response.body().getStatuscode() == 1 || response.body().getStatuscode() ==2) {

                                    if (isTransactionCancelledByUser) {
                                        UtilMethods.INSTANCE.ErrorWithTitle(activity, "TXN CANCEL", "Cancelled By User");
                                    }
                                    else {
                                        if (response.body().getMsg() != null && response.body().getMsg().equalsIgnoreCase("PENDING")) {
                                            UtilMethods.INSTANCE.Processing(activity, "Your Order" + cFItemData.getOrderID() + " for Amount " + getString(R.string.rupiya) + cFItemData.getOrderAmount() + " is awaited from Bank Side \n" + "We will Update once we get Response From bank Side ");

                                        }
                                        else if (response.body().getMsg() != null && response.body().getMsg().equalsIgnoreCase("Transaction success") ) {
                                            if (!isActivityPause) {
                                                UtilMethods.INSTANCE.Successful(activity, "Money Added Successfully");
                                            } else {
                                                isDialogShowBackground = true;
                                                dialogMsg = "Money Added Successfully";
                                                isSucessDialog = true;
                                            }
                                        }else if (response.body().getMsg()!=null && response.body().getMsg().equalsIgnoreCase("SUCCESS")) {
                                            if (!isActivityPause) {
                                                UtilMethods.INSTANCE.Successful(AddMoneyActivity.this, "Money Added Successfully");
                                            }
                                            else {
                                                isDialogShowBackground = true;
                                                dialogMsg = "Money Added Successfully";
                                                isSucessDialog = true;
                                            }
                                        }
                                    }

                                    amountEt.setText("");
                                    UtilMethods.INSTANCE.Balancecheck(AddMoneyActivity.this, loader, new UtilMethods.ApiCallBack() {
                                        @Override
                                        public void onSucess(Object object) {
                                            balanceCheckResponse = (BalanceResponse) object;
                                            if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null) {
                                                showWalletListPopupWindow();
                                            }
                                        }
                                    });
                                }

                                else if (response.body().getStatuscode() ==-1){

                                    if (!isActivityPause) {
                                        UtilMethods.INSTANCE.Error(activity, response.body().getMsg() + "");
                                    } else {
                                        isDialogShowBackground = true;
                                        dialogMsg = response.body().getMsg() + "";
                                        isSucessDialog = false;
                                    }
                                }

                            } else {

                                if (!isActivityPause) {
                                    UtilMethods.INSTANCE.Error(activity, getString(R.string.some_thing_error));
                                } else {
                                    isDialogShowBackground = true;
                                    dialogMsg = getString(R.string.some_thing_error);
                                    isSucessDialog = false;
                                }
                            }
                        } else {
                            UtilMethods.INSTANCE.apiErrorHandle(activity, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }

                        if (!isActivityPause) {
                            UtilMethods.INSTANCE.Error(activity, e.getMessage() + "");
                        } else {
                            isDialogShowBackground = true;
                            dialogMsg = e.getMessage() + "";
                            isSucessDialog = false;
                        }
                    }
                }

                @Override
                public void onFailure(Call<BasicResponse> call, Throwable t) {
                    try {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }

                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            if (!isActivityPause) {
                                UtilMethods.INSTANCE.NetworkError(activity);

                            } else {
                                isDialogShowBackground = true;
                                dialogMsg = getString(R.string.err_msg_network);
                                isSucessDialog = false;
                            }
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            if (!isActivityPause) {
                                UtilMethods.INSTANCE.ErrorWithTitle(activity, "TIME OUT ERROR", t.getMessage() + "");
                            } else {
                                isDialogShowBackground = true;
                                dialogMsg = t.getMessage();
                                isSucessDialog = false;
                            }
                        } else {
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                if (!isActivityPause) {
                                    UtilMethods.INSTANCE.ErrorWithTitle(activity, "FATAL ERROR", t.getMessage() + "");
                                } else {
                                    isDialogShowBackground = true;
                                    dialogMsg = t.getMessage();
                                    isSucessDialog = false;
                                }
                            } else {
                                if (!isActivityPause) {
                                    UtilMethods.INSTANCE.Error(activity, getString(R.string.some_thing_error));
                                } else {
                                    isDialogShowBackground = true;
                                    dialogMsg = t.getMessage();
                                    isSucessDialog = false;
                                }
                            }
                        }


                    } catch (IllegalStateException ise) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }

                        if (!isActivityPause) {
                            UtilMethods.INSTANCE.Error(activity, getString(R.string.some_thing_error));
                        } else {
                            isDialogShowBackground = true;
                            dialogMsg = getString(R.string.some_thing_error);
                            isSucessDialog = false;
                        }
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader.isShowing()) {
                loader.dismiss();
            }

            if (!isActivityPause) {
                UtilMethods.INSTANCE.Error(activity, e.getMessage() + "");
            } else {
                isDialogShowBackground = true;
                dialogMsg = e.getMessage() + "";
                isSucessDialog = false;
            }
        }
    }


    /*-----End CF--------*/

    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        amountEt.setText("");
        UtilMethods.INSTANCE.Successful(AddMoneyActivity.this, "Money Successfully Added");
        UtilMethods.INSTANCE.Balancecheck(AddMoneyActivity.this, loader, new UtilMethods.ApiCallBack() {
            @Override
            public void onSucess(Object object) {
                balanceCheckResponse = (BalanceResponse) object;
                if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null) {
                    showWalletListPopupWindow();
                }
            }
        });
    }

    @Override
    public void onPaymentError(int code, String response) {
        UtilMethods.INSTANCE.Error(AddMoneyActivity.this, response + "");
    }


    @Override
    protected void onResume() {
        isActivityPause = false;
        super.onResume();

        if (isDialogShowBackground) {
            isDialogShowBackground = false;

            if (sucessDialogStatus == 1) {
                UtilMethods.INSTANCE.Processing(AddMoneyActivity.this, dialogMsg);
            } else if (sucessDialogStatus == 2) {
                UtilMethods.INSTANCE.Successful(AddMoneyActivity.this, dialogMsg);
            } else {
                UtilMethods.INSTANCE.Error(AddMoneyActivity.this, dialogMsg);
            }
        }
    }

    @Override
    protected void onPause() {
        isActivityPause = true;
        super.onPause();
    }

    @Override
    protected void onStart() {
        isActivityPause = false;
        super.onStart();
    }

    @Override
    protected void onStop() {
        isActivityPause = true;
        super.onStop();
    }

    @Override
    public void onTransactionResponse(Bundle inResponse) {
        paytmCallBackApi(inResponse);
    }

    @Override
    public void networkNotAvailable() {
        paytmCallBackApi(paytmFailedData(ptmRequest, 0, "TXN_CANCEL", "Network not available"));
    }

    @Override
    public void clientAuthenticationFailed(String inErrorMessage) {
        paytmCallBackApi(paytmFailedData(ptmRequest, 0, "TXN_CANCEL", inErrorMessage));
    }

    @Override
    public void someUIErrorOccurred(String inErrorMessage) {
        paytmCallBackApi(paytmFailedData(ptmRequest, 0, "TXN_CANCEL", inErrorMessage));
    }

    @Override
    public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) {
        paytmCallBackApi(paytmFailedData(ptmRequest, iniErrorCode, "TXN_CANCEL", inErrorMessage));
    }

    @Override
    public void onBackPressedCancelTransaction() {
        paytmCallBackApi(paytmFailedData(ptmRequest, 0, "TXN_CANCEL", "Transaction canceled by user"));
    }

    @Override
    public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
        paytmCallBackApi(inResponse);
    }

    @Override
    public void onErrorProceed(String string) {
        paytmCallBackApi(paytmFailedData(ptmRequest, 0, "TXN_CANCEL", string));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INTENT_UPI) {
            if (data != null && resultCode == RESULT_OK) {
                showAlertDialog(AddMoneyActivity.this, "INTENTDATA", data+"");
                /*boolean isStatus = false;*/
                try {
                    paymentResponse = data.getStringExtra("response");
                    txnId = data.getStringExtra("txnId");
                    status = data.getStringExtra("Status");
                    txnRef = data.getStringExtra("txnRef");
                    ApprovalRefNo = data.getStringExtra("ApprovalRefNo");
                    TrtxnRef = data.getStringExtra("TrtxnRef");
                    responseCode = data.getStringExtra("responseCode");
                    bleTxId = data.getStringExtra("bleTxId");/*TrtxnRef*/
                    Log.e("UPITransaction", "paymentResponse :" + paymentResponse + " ,txnId :" + txnId + " ,status :" + status+"" + " ,txnRef :" + txnRef);

                    if (currentPGId == 12) {
                        isPendingTimerComplete = false;
                        callAllUPIStatusUpdate(true);
                    } else {
                        callUPIIntentStatusUpdate(true,status);
                    }
                }
                catch (Exception exception){
                    UtilMethods.INSTANCE.Error(this,exception.getMessage());
                }
            }else{
                UtilMethods.INSTANCE.Error(AddMoneyActivity.this,"callBackNotFound");
            }
        }
        else if (requestCode == PGConstants.REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    String paymentResponse = data.getStringExtra(PGConstants.PAYMENT_RESPONSE);
                    System.out.println("paymentResponse: " + paymentResponse);
                    if (paymentResponse == null || paymentResponse.equals("null")) {
                        System.out.println("Transaction Error!");
                        UtilMethods.INSTANCE.Error(this, "Transaction Error!");

                    } else {
                        JSONObject response = new JSONObject(paymentResponse);
                        AggrePayTransactionUpdate(response.optString("order_id"), response.optInt("amount"), response.optString("hash"));


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
                UtilMethods.INSTANCE.Error(this, "Transaction Canceled!");
            }

        }
        else if (requestCode == INTENT_REQUEST_CODE_PAYTM) {
            if (resultCode == RESULT_OK && data != null) {

                if (data.getStringExtra("response") != null && !data.getStringExtra("response").isEmpty()) {
                    Bundle bundle = data.getExtras();
                    if (bundle != null) {
                        for (String key : bundle.keySet()) {
                            Log.e(TAG, key + " : " + (bundle.get(key) != null ? bundle.get(key) : ""));
                        }

                    }


                    try {
                        PayTmSuccessItemResponse itemResponse = new Gson().fromJson(data.getStringExtra("response"), PayTmSuccessItemResponse.class);
                        paytmCallBackApi(payTmSuccessData(itemResponse));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } else {
                    paytmCallBackApi(paytmFailedData(ptmRequest, 0, "TXN_CANCEL", "Transaction canceled"));
                }

            }
            else {
                paytmCallBackApi(paytmFailedData(ptmRequest, 0, "TXN_CANCEL", "Transaction Failed"));
            }
        }
        else if (requestCode == CFPaymentService.REQ_CODE) {
            if (data != null) {
                Bundle bundle = data.getExtras();
                if (bundle != null) {

                    JSONObject responseJson = transResponseBundleToJsonOb(bundle);
                    try {
                        String txnStatus = responseJson.getString("txStatus");
                        //SUCCESS--
                        if (txnStatus != null && !txnStatus.isEmpty() && txnStatus.equalsIgnoreCase("SUCCESS")) {
                            isTransactionCancelledByUser = false;
                            cashFreeCallBackApi(cashFreeSuccessData(cFItemData, responseJson, 2));

                        } else if (txnStatus != null && !txnStatus.isEmpty() && txnStatus.equalsIgnoreCase("PENDING")) {
                            isTransactionCancelledByUser = false;
                            cashFreeCallBackApi(cashFreeSuccessData(cFItemData, responseJson, 1));

                        } else if (txnStatus != null && !txnStatus.isEmpty() && txnStatus.equalsIgnoreCase("CANCELLED")) {
                            isTransactionCancelledByUser = true;
                            cashFreeCallBackApi(cashFreeFailedData(cFItemData, 0, "TXN_CANCEL", "Transaction canceled by user"));

                        } else if (txnStatus != null && !txnStatus.isEmpty() && txnStatus.equalsIgnoreCase("FAILED")) {
                            isTransactionCancelledByUser = false;
                            cashFreeCallBackApi(cashFreeSuccessData(cFItemData, responseJson, 3));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            } else {
                UtilMethods.INSTANCE.Error(this, "Transaction Failed");

            }
        }
        else if (requestCode == INTENT_UPI_WEB) {
            if (data != null && resultCode == RESULT_OK) {
                if (uiWebViewDialog != null && uiWebViewDialog.isShowing()) {
                    uiWebViewDialog.dismiss();
                }
                paymentResponse = data.getStringExtra("response");
                txnId = data.getStringExtra("txnId");
                status = data.getStringExtra("Status");
                txnRef = data.getStringExtra("txnRef");
                ApprovalRefNo = data.getStringExtra("ApprovalRefNo");
                TrtxnRef = data.getStringExtra("TrtxnRef");
                responseCode = data.getStringExtra("responseCode");
                bleTxId = data.getStringExtra("bleTxId");/*TrtxnRef*/
                isStatus = false;
                if (status != null && !status.isEmpty()) {
                    isStatus = true;
                    if (status.toLowerCase().equalsIgnoreCase("success")) {
                        amountEt.setText("");
                        UtilMethods.INSTANCE.SuccessfulWithTitle(activity, "Success", "Transaction Successful.");
                    } else if (status.toLowerCase().equalsIgnoreCase("submitted")) {
                        amountEt.setText("");
                        UtilMethods.INSTANCE.ProcessingWithTitle(activity, "Submitted", "Transaction submitted, Please wait some time for confirmation.");
                    } else {
                        UtilMethods.INSTANCE.ErrorWithTitle(activity, "Failed", "Transaction Failed, Please Try After Some Time.");
                    }

                }

                callUPIWebUpdate(upiTID,isStatus);
            }
        }
    }

    public static void showAlertDialog(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", (dialog, which) -> {
            dialog.dismiss();
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> {
            dialog.dismiss(); // Dismiss the dialog
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void showUPIWebView(String url) {

        if (uiWebViewDialog != null && uiWebViewDialog.isShowing()) {
            return;
        }
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewMyLayout = inflater.inflate(R.layout.dialog_upi_webview, null);
        WebView webView = viewMyLayout.findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportMultipleWindows(true);
        webView.setWebViewClient(new MyWebViewClient());
        webView.loadUrl(url);

        View closeBtn = viewMyLayout.findViewById(R.id.closeIv);
        uiWebViewDialog = new Dialog(this, R.style.Theme_AppCompat_Dialog_Alert);
        uiWebViewDialog.setCancelable(false);
        uiWebViewDialog.setContentView(viewMyLayout);
        uiWebViewDialog.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.MATCH_PARENT);
        uiWebViewDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uiWebViewDialog.dismiss();
                loader.show();
                callUPIWebUpdate(upiTID,false);
            }
        });


        uiWebViewDialog.show();
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.contains("upi://pay")) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    Intent chooser = Intent.createChooser(intent, "Pay with...");
                    if (null != chooser.resolveActivity(getPackageManager())) {
                        startActivityForResult(chooser, INTENT_UPI_WEB);
                    } else {
                        Toast.makeText(AddMoneyActivity.this, "No UPI app found, please install one to continue", Toast.LENGTH_SHORT).show();
                    }
                } catch (ActivityNotFoundException anfe) {
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        Intent chooser = Intent.createChooser(intent, "Pay with...");
                        startActivityForResult(chooser, INTENT_UPI_WEB);
                    } catch (ActivityNotFoundException anfe1) {
                        Toast.makeText(AddMoneyActivity.this, "No UPI app found, please install one to continue", Toast.LENGTH_SHORT).show();
                    }
                }

                return true;
            } else {
                view.loadUrl(url);
                return false;
            }

        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            loader.show();
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            loader.dismiss();

            if (url.toLowerCase().contains("pgcallback/upigatewayredirectnew") ||
                    url.toLowerCase().contains(ApplicationConstant.INSTANCE.Domain + "/allupireturn?")) {

                if (uiWebViewDialog != null && uiWebViewDialog.isShowing()) {
                    uiWebViewDialog.dismiss();
                    loader.show();
                    callUPIWebUpdate(upiTID,false);
                }

            }
        }

    }

    public void callUPIWebUpdate(String tid,boolean isStatus) {
        final boolean finalIsStatus = isStatus;
        try {

            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<InitiateUpiResponse> call = git.CashFreeStatusCheck(tid);

            call.enqueue(new Callback<InitiateUpiResponse>() {

                @Override
                public void onResponse(Call<InitiateUpiResponse> call, retrofit2.Response<InitiateUpiResponse> response) {
                    if (loader != null && loader.isShowing())
                        loader.dismiss();

                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            InitiateUpiResponse apiData = response.body();
                            if (!finalIsStatus) {
                                if (apiData.getStatus() == 0 || apiData.getStatus() == 1) {
                                    UtilMethods.INSTANCE.Processing(activity, "Your transaction is pending");
                                } else if (apiData.getStatus() == 2) {
                                    UtilMethods.INSTANCE.Successful(activity, "Your transaction is successfully completed");
                                } else {
                                    UtilMethods.INSTANCE.Error(activity, "Your transaction failed");
                                }

                            }

                        }
                    }

                }

                @Override
                public void onFailure(Call<InitiateUpiResponse> call, Throwable t) {
                    if (loader != null && loader.isShowing())
                        loader.dismiss();

                }
            });

        } catch (Exception e) {
            if (loader != null && loader.isShowing())
                loader.dismiss();
            e.printStackTrace();
        }

    }


    public void callAllUPIStatusUpdate(boolean isLoaderShow) {
        try {
            if (!isActivityPause && isLoaderShow) {
                loader.show();
            }

            isAllUPIStatusCheckRunning = true;
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<GatwayStatusCheckResponse> call = git.AllUPIStatusCheck(upiTID);

            call.enqueue(new Callback<GatwayStatusCheckResponse>() {
                @Override
                public void onResponse(Call<GatwayStatusCheckResponse> call, final retrofit2.Response<GatwayStatusCheckResponse> response) {

                    try {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {

                                GatwayStatusCheckResponse apiData = response.body();
                                if (apiData.getStatus() != null && !apiData.getStatus().equalsIgnoreCase("0") && apiData.getStatuscode() == 1) {
                                    if (apiData.getStatus().equalsIgnoreCase("2") ||
                                            apiData.getStatus().toLowerCase().equalsIgnoreCase("success")) {
                                        if (pendingTimerDialog != null && pendingTimerDialog.isShowing()) {
                                            pendingTimerDialog.dismiss();
                                        }
                                        amountEt.setText("");
                                        UtilMethods.INSTANCE.BalancecheckNew(activity, loader, null, null, new UtilMethods.ApiCallBack() {
                                            @Override
                                            public void onSucess(Object object) {
                                                balanceCheckResponse = (BalanceResponse) object;
                                                if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null) {
                                                    showWalletListPopupWindow();
                                                }

                                            }
                                        });

                                        dialogMsg = "Your transaction is successfully completed";
                                        if (!isActivityPause) {
                                            UtilMethods.INSTANCE.SuccessfulWithTitle(AddMoneyActivity.this, "TXN SUCCESS", dialogMsg);
                                        } else {
                                            isDialogShowBackground = true;
                                            sucessDialogStatus = 2;
                                        }

                                    }
                                    else if (apiData.getStatus().equalsIgnoreCase("3") ||
                                            apiData.getStatus().toLowerCase().equalsIgnoreCase("failed")) {
                                        if (pendingTimerDialog != null && pendingTimerDialog.isShowing()) {
                                            pendingTimerDialog.dismiss();
                                        }
                                        dialogMsg = "Your transaction failed";
                                        if (!isActivityPause) {
                                            UtilMethods.INSTANCE.ErrorWithTitle(AddMoneyActivity.this, "TXN FAILED", dialogMsg);
                                        } else {
                                            isDialogShowBackground = true;
                                            sucessDialogStatus = 3;
                                        }

                                    }
                                    else {
                                        amountEt.setText("");
                                        if (isAllUpiDialogCanceled) {
                                            isAllUpiDialogCanceled = false;
                                            dialogMsg = "Transaction cancelled by user";
                                            if (!isActivityPause) {
                                                UtilMethods.INSTANCE.ErrorWithTitle(AddMoneyActivity.this, "TXN CANCEL", dialogMsg);
                                            } else {
                                                isDialogShowBackground = true;
                                                sucessDialogStatus = 3;
                                            }
                                        } else {
                                            if (!isPendingTimerComplete && (pendingTimerDialog == null || !pendingTimerDialog.isShowing())) {
                                                showPendingTimerDialog();
                                            } else {
                                                if (isPendingTimerComplete) {
                                                    isPendingTimerComplete = false;
                                                    if (pendingTimerDialog != null && pendingTimerDialog.isShowing()) {
                                                        pendingTimerDialog.dismiss();
                                                    }
                                                    dialogMsg = "Your Order TID-" + upiTID + " for Amount " + getString(R.string.rupiya) + requestAmount + " is awaited from Bank Side \n" + "We will Update once we get Response From bank Side";
                                                    if (!isActivityPause) {
                                                        UtilMethods.INSTANCE.ProcessingWithTitle(AddMoneyActivity.this, "TXN PENDING", dialogMsg);
                                                    } else {
                                                        isDialogShowBackground = true;
                                                        sucessDialogStatus = 1;
                                                    }
                                                }
                                            }

                                        }

                                    }

                                }
                                else if (apiData.getStatuscode() == 0 || apiData.getStatuscode() == 1) {
                                    amountEt.setText("");
                                    if (isAllUpiDialogCanceled) {
                                        isAllUpiDialogCanceled = false;
                                        dialogMsg = "Transaction cancelled by user";
                                        if (!isActivityPause) {
                                            UtilMethods.INSTANCE.ErrorWithTitle(AddMoneyActivity.this, "TXN CANCEL", dialogMsg);
                                        } else {
                                            isDialogShowBackground = true;
                                            sucessDialogStatus = 3;
                                        }
                                    } else {

                                        if (!isPendingTimerComplete && (pendingTimerDialog == null || !pendingTimerDialog.isShowing())) {
                                            showPendingTimerDialog();
                                        } else {
                                            if (isPendingTimerComplete) {
                                                isPendingTimerComplete = false;
                                                if (pendingTimerDialog != null && pendingTimerDialog.isShowing()) {
                                                    pendingTimerDialog.dismiss();
                                                }
                                                dialogMsg = "Your Order TID-" + upiTID + " for Amount " + getString(R.string.rupiya) + requestAmount + " is awaited from Bank Side \n" + "We will Update once we get Response From bank Side";
                                                if (!isActivityPause) {
                                                    UtilMethods.INSTANCE.ProcessingWithTitle(AddMoneyActivity.this, "TXN PENDING", dialogMsg);
                                                } else {
                                                    isDialogShowBackground = true;
                                                    sucessDialogStatus = 1;
                                                }
                                            }
                                        }

                                    }


                                }
                                else if (apiData.getStatuscode() == 2) {
                                    if (pendingTimerDialog != null && pendingTimerDialog.isShowing()) {
                                        pendingTimerDialog.dismiss();
                                    }
                                    amountEt.setText("");
                                    UtilMethods.INSTANCE.BalancecheckNew(activity, loader, null, null, new UtilMethods.ApiCallBack() {
                                        @Override
                                        public void onSucess(Object object) {
                                            balanceCheckResponse = (BalanceResponse) object;
                                            if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null) {
                                                showWalletListPopupWindow();
                                            }

                                        }
                                    });

                                    dialogMsg = "Your transaction is successfully completed";
                                    if (!isActivityPause) {
                                        UtilMethods.INSTANCE.SuccessfulWithTitle(AddMoneyActivity.this, "TXN SUCCESS", dialogMsg);
                                    } else {
                                        isDialogShowBackground = true;
                                        sucessDialogStatus = 2;
                                    }

                                }
                                else if (apiData.getStatuscode() == 3) {
                                    if (pendingTimerDialog != null && pendingTimerDialog.isShowing()) {
                                        pendingTimerDialog.dismiss();
                                    }
                                    dialogMsg = "Your transaction has been failed";
                                    if (!isActivityPause) {
                                        UtilMethods.INSTANCE.ErrorWithTitle(AddMoneyActivity.this, "TXN FAILED", dialogMsg);
                                    } else {
                                        isDialogShowBackground = true;
                                        sucessDialogStatus = 3;
                                    }

                                }
                                else {
                                    if (pendingTimerDialog != null && pendingTimerDialog.isShowing()) {
                                        pendingTimerDialog.dismiss();
                                    }
                                    if (apiData.getStatus() != null && !apiData.getStatus().equalsIgnoreCase("3")) {
                                        dialogMsg = "Your transaction failed";
                                        if (!isActivityPause) {
                                            UtilMethods.INSTANCE.ErrorWithTitle(AddMoneyActivity.this, "TXN FAILED", dialogMsg);
                                        } else {
                                            isDialogShowBackground = true;
                                            sucessDialogStatus = 3;
                                        }
                                    } else {

                                        if (!isActivityPause) {
                                            UtilMethods.INSTANCE.Error(AddMoneyActivity.this, getString(R.string.some_thing_error));
                                        } else {
                                            isDialogShowBackground = true;
                                            dialogMsg = getString(R.string.some_thing_error);
                                            sucessDialogStatus = 3;
                                        }
                                    }
                                }


                            } else {

                                if (!isActivityPause) {
                                    UtilMethods.INSTANCE.Error(AddMoneyActivity.this, getString(R.string.some_thing_error));
                                } else {
                                    isDialogShowBackground = true;
                                    dialogMsg = getString(R.string.some_thing_error);
                                    sucessDialogStatus = 3;
                                }
                            }
                        } else {
                            UtilMethods.INSTANCE.apiErrorHandle(AddMoneyActivity.this, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }

                        if (!isActivityPause) {
                            UtilMethods.INSTANCE.Error(AddMoneyActivity.this, e.getMessage() + "");
                        } else {
                            isDialogShowBackground = true;
                            dialogMsg = e.getMessage() + "";
                            sucessDialogStatus = 3;
                        }
                    }

                    isAllUPIStatusCheckRunning = false;
                }

                @Override
                public void onFailure(Call<GatwayStatusCheckResponse> call, Throwable t) {
                    try {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }

                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            if (!isActivityPause) {
                                UtilMethods.INSTANCE.NetworkError(AddMoneyActivity.this);

                            } else {
                                isDialogShowBackground = true;
                                dialogMsg = getString(R.string.err_msg_network);
                                sucessDialogStatus = 3;
                            }
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            if (!isActivityPause) {
                                UtilMethods.INSTANCE.ErrorWithTitle(AddMoneyActivity.this, "TIME OUT ERROR", t.getMessage() + "");
                            } else {
                                isDialogShowBackground = true;
                                dialogMsg = t.getMessage();
                                sucessDialogStatus = 3;
                            }
                        } else {
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                if (!isActivityPause) {
                                    UtilMethods.INSTANCE.ErrorWithTitle(AddMoneyActivity.this, "FATAL ERROR", t.getMessage() + "");
                                } else {
                                    isDialogShowBackground = true;
                                    dialogMsg = t.getMessage();
                                    sucessDialogStatus = 3;
                                }
                            } else {
                                if (!isActivityPause) {
                                    UtilMethods.INSTANCE.Error(AddMoneyActivity.this, getString(R.string.some_thing_error));
                                } else {
                                    isDialogShowBackground = true;
                                    dialogMsg = t.getMessage();
                                    sucessDialogStatus = 3;
                                }
                            }
                        }


                    } catch (IllegalStateException ise) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }

                        if (!isActivityPause) {
                            UtilMethods.INSTANCE.Error(AddMoneyActivity.this, getString(R.string.some_thing_error));
                        } else {
                            isDialogShowBackground = true;
                            dialogMsg = getString(R.string.some_thing_error);
                            sucessDialogStatus = 3;
                        }
                    }
                    isAllUPIStatusCheckRunning = false;
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            isAllUPIStatusCheckRunning = false;
            if (loader.isShowing()) {
                loader.dismiss();
            }

            if (!isActivityPause) {
                UtilMethods.INSTANCE.Error(AddMoneyActivity.this, e.getMessage() + "");
            } else {
                isDialogShowBackground = true;
                dialogMsg = e.getMessage() + "";
                sucessDialogStatus = 3;
            }
        }
    }

    private void showPendingTimerDialog() {

        if (pendingTimerDialog != null && pendingTimerDialog.isShowing()) {
            return;
        }
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE); // or (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewMyLayout = inflater.inflate(R.layout.dialog_pending_timer, null);

        View closeBtn = viewMyLayout.findViewById(R.id.closeBtn);
        TextView minuteTv = viewMyLayout.findViewById(R.id.minute);
        TextView secondTv = viewMyLayout.findViewById(R.id.second);


        pendingTimerDialog = new Dialog(this, R.style.Theme_AppCompat_Dialog_Alert);
        pendingTimerDialog.setCancelable(false);
        pendingTimerDialog.setContentView(viewMyLayout);
        /* dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));*/
        pendingTimerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        closeBtn.setOnClickListener(v -> {

            isPendingTimerComplete = true;
            pendingTimerDialog.dismiss();
            if(currentPGId==12)
                callAllUPIStatusUpdate(true);
            /*else if (currentPGId ==3 || currentPGId ==15)
                callAllUPIStatusUpdate(true);*/
        });

        pendingTimerDialog.setOnDismissListener(dialogInterface -> {
            if (countDownTimerPendingTxn != null) {
                countDownTimerPendingTxn.cancel();
                countDownTimerPendingTxn = null;
            }
        });
        if (countDownTimerPendingTxn != null) {
            countDownTimerPendingTxn.cancel();
            countDownTimerPendingTxn = null;
        }
        isPendingTimerComplete = false;
        countDownTimerPendingTxn = new CountDownTimer((long) (61 * 1000), 1000) {
            int count = -3;

            public void onTick(long millisUntilFinished) {
                long millis = millisUntilFinished;
                //Convert milliseconds into hour,minute and seconds
               /* String ms = String.format("%02d:%02d:%02d",
                        TimeUnit.MILLISECONDS.toHours(millis),
                        TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                        TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                bonusTime.setText(ms); */
                // String hr = String.format("%02d", TimeUnit.MILLISECONDS.toHours(millis));
                String min = String.format("%02d", TimeUnit.MILLISECONDS.toMinutes(millis) /*- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis))*/);
                String sec = String.format("%02d", TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));

                // hourTv.setText(hr);
                minuteTv.setText(min);
                secondTv.setText(sec);
                if (!isAllUPIStatusCheckRunning) {
                    count = count + 1;
                    if (count == 13) {
                        count = 6;
                        callAllUPIStatusUpdate(false);
                    }
                }
            }

            public void onFinish() {
                isPendingTimerComplete = true;
                pendingTimerDialog.dismiss();
                callAllUPIStatusUpdate(true);
            }
        }.start();
        pendingTimerDialog.show();
        // Window window = dialog.getWindow();
        //window.setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.MATCH_PARENT);


    }

    public void callUPIIntentStatusUpdate(boolean isLoaderShow,String status) {
        try {
            if (!isActivityPause && isLoaderShow) {
                loader.show();
            }
            isIntentUPIStatusCheckRunning = true;
            UpdateUPIRequest upiRequest = new UpdateUPIRequest();
            upiRequest.setBankStatus(status+"");
            upiRequest.setTid(upiTID);
            upiRequest.setUserID(mLoginDataResponse.getData().getUserID());
            upiRequest.setSession(mLoginDataResponse.getData().getSession());
            upiRequest.setSessionID(mLoginDataResponse.getData().getSessionID());
            upiRequest.setImei(UtilMethods.INSTANCE.getIMEI(activity));
            upiRequest.setRegKey("");
            upiRequest.setSerialNo(UtilMethods.INSTANCE.getSerialNo(activity));
            upiRequest.setLoginTypeID(mLoginDataResponse.getData().getLoginTypeID());
            upiRequest.setAppid(ApplicationConstant.INSTANCE.APP_ID);
            upiRequest.setVersion(BuildConfig.VERSION_NAME);

            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<InitiateUpiResponse> call = git.UPIPaymentUpdate(upiRequest);

            call.enqueue(new Callback<InitiateUpiResponse>() {
                @Override
                public void onResponse(Call<InitiateUpiResponse> call, final retrofit2.Response<InitiateUpiResponse> response) {

                    try {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                InitiateUpiResponse apiData = response.body();
                                if (apiData.getStatuscode() == 1 || apiData.getStatuscode() == 2) {
                                    if (pendingTimerDialog != null && pendingTimerDialog.isShowing()) {
                                        pendingTimerDialog.dismiss();
                                    }

                                    UtilMethods.INSTANCE.BalancecheckNew(activity, loader, null, null, new UtilMethods.ApiCallBack() {
                                        @Override
                                        public void onSucess(Object object) {
                                            amountEt.setText("");
                                            balanceCheckResponse = (BalanceResponse) object;
                                            if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null) {
                                                showWalletListPopupWindow();
                                            }

                                        }
                                    });
                                    dialogMsg = apiData.getMsg()==null?"Your transaction is successfully completed": apiData.getMsg().isEmpty()?"Your transaction is successfully completed":apiData.getMsg();
                                    //dialogMsg = "Your transaction is successfully completed";
                                    if (!isActivityPause) {
                                        UtilMethods.INSTANCE.SuccessfulWithTitle(AddMoneyActivity.this, "TXN SUCCESS", dialogMsg);
                                    } else {
                                        isDialogShowBackground = true;
                                        sucessDialogStatus = 2;
                                    }

                                }
                                else {
                                    if (pendingTimerDialog != null && pendingTimerDialog.isShowing()) {
                                        pendingTimerDialog.dismiss();
                                    }
                                    dialogMsg = apiData.getMsg()==null?"Your transaction failed": apiData.getMsg().isEmpty()?"Your transaction failed":apiData.getMsg();
                                    if (!isActivityPause) {
                                        UtilMethods.INSTANCE.ErrorWithTitle(AddMoneyActivity.this, " Status Code :" +apiData.getStatuscode() +" +TXN FAILED", dialogMsg);
                                    } else {
                                        isDialogShowBackground = true;
                                        sucessDialogStatus = 3;
                                    }
                                }
                            } else {

                                if (!isActivityPause) {
                                    UtilMethods.INSTANCE.Error(AddMoneyActivity.this, getString(R.string.some_thing_error));
                                } else {
                                    isDialogShowBackground = true;
                                    dialogMsg = getString(R.string.some_thing_error);
                                    sucessDialogStatus = 3;
                                }
                            }
                        }
                        else {
                            UtilMethods.INSTANCE.apiErrorHandle(AddMoneyActivity.this, response.code(), response.message());
                        }
                    }
                    catch (Exception e) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }

                        if (!isActivityPause) {
                            UtilMethods.INSTANCE.Error(AddMoneyActivity.this, e.getMessage() + "");
                        } else {
                            isDialogShowBackground = true;
                            dialogMsg = e.getMessage() + "";
                            sucessDialogStatus = 3;
                        }
                    }

                    isIntentUPIStatusCheckRunning = false;
                }

                @Override
                public void onFailure(Call<InitiateUpiResponse> call, Throwable t) {
                    try {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }

                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            if (!isActivityPause) {
                                UtilMethods.INSTANCE.NetworkError(AddMoneyActivity.this);

                            } else {
                                isDialogShowBackground = true;
                                dialogMsg = getString(R.string.err_msg_network);
                                sucessDialogStatus = 3;
                            }
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            if (!isActivityPause) {
                                UtilMethods.INSTANCE.ErrorWithTitle(AddMoneyActivity.this, "TIME OUT ERROR", t.getMessage() + "");
                            } else {
                                isDialogShowBackground = true;
                                dialogMsg = t.getMessage();
                                sucessDialogStatus = 3;
                            }
                        } else {
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                if (!isActivityPause) {
                                    UtilMethods.INSTANCE.ErrorWithTitle(AddMoneyActivity.this, "FATAL ERROR", t.getMessage() + "");
                                } else {
                                    isDialogShowBackground = true;
                                    dialogMsg = t.getMessage();
                                    sucessDialogStatus = 3;
                                }
                            } else {
                                if (!isActivityPause) {
                                    UtilMethods.INSTANCE.Error(AddMoneyActivity.this, getString(R.string.some_thing_error));
                                } else {
                                    isDialogShowBackground = true;
                                    dialogMsg = t.getMessage();
                                    sucessDialogStatus = 3;
                                }
                            }
                        }


                    } catch (IllegalStateException ise) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }

                        if (!isActivityPause) {
                            UtilMethods.INSTANCE.Error(AddMoneyActivity.this, getString(R.string.some_thing_error));
                        } else {
                            isDialogShowBackground = true;
                            dialogMsg = getString(R.string.some_thing_error);
                            sucessDialogStatus = 3;
                        }
                    }
                    isIntentUPIStatusCheckRunning = false;
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            isIntentUPIStatusCheckRunning = false;
            if (loader.isShowing()) {
                loader.dismiss();
            }

            if (!isActivityPause) {
                UtilMethods.INSTANCE.Error(AddMoneyActivity.this, e.getMessage() + "");
            } else {
                isDialogShowBackground = true;
                dialogMsg = e.getMessage() + "";
                sucessDialogStatus = 3;
            }
        }
    }

    private void showUPIIntentPendingTimerDialog(String status) {

        if (pendingTimerDialog != null && pendingTimerDialog.isShowing()) {
            return;
        }
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE); // or (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewMyLayout = inflater.inflate(R.layout.dialog_pending_timer, null);

        View closeBtn = viewMyLayout.findViewById(R.id.closeBtn);
        TextView minuteTv = viewMyLayout.findViewById(R.id.minute);
        TextView secondTv = viewMyLayout.findViewById(R.id.second);


        pendingTimerDialog = new Dialog(this, R.style.Theme_AppCompat_Dialog_Alert);
        pendingTimerDialog.setCancelable(false);
        pendingTimerDialog.setContentView(viewMyLayout);
        /* dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));*/
        pendingTimerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        closeBtn.setOnClickListener(v -> {

            isPendingTimerComplete = true;
            pendingTimerDialog.dismiss();
            if (currentPGId ==3 || currentPGId ==15)
                callUPIIntentStatusUpdate(true,status);
        });

        pendingTimerDialog.setOnDismissListener(dialogInterface -> {
            if (countDownTimerPendingTxn != null) {
                countDownTimerPendingTxn.cancel();
                countDownTimerPendingTxn = null;
            }
        });
        if (countDownTimerPendingTxn != null) {
            countDownTimerPendingTxn.cancel();
            countDownTimerPendingTxn = null;
        }
        isPendingTimerComplete = false;
        countDownTimerPendingTxn = new CountDownTimer((long) (61 * 1000), 1000) {
            int count = -3;

            public void onTick(long millisUntilFinished) {
                long millis = millisUntilFinished;
                //Convert milliseconds into hour,minute and seconds
               /* String ms = String.format("%02d:%02d:%02d",
                        TimeUnit.MILLISECONDS.toHours(millis),
                        TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                        TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                bonusTime.setText(ms); */
                // String hr = String.format("%02d", TimeUnit.MILLISECONDS.toHours(millis));
                String min = String.format("%02d", TimeUnit.MILLISECONDS.toMinutes(millis) /*- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis))*/);
                String sec = String.format("%02d", TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));

                // hourTv.setText(hr);
                minuteTv.setText(min);
                secondTv.setText(sec);
                if (!isIntentUPIStatusCheckRunning) {
                    count = count + 1;
                    if (count == 13) {
                        count = 6;
                        callUPIIntentStatusUpdate(false,status);
                    }
                }
            }
            public void onFinish() {
                isPendingTimerComplete = true;
                pendingTimerDialog.dismiss();
                callUPIIntentStatusUpdate(true,status);
            }
        }.start();
        pendingTimerDialog.show();
        // Window window = dialog.getWindow();
        //window.setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.MATCH_PARENT);


    }

}
