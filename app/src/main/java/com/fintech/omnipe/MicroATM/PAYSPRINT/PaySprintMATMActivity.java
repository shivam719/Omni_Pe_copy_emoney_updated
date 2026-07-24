package com.fintech.omnipe.MicroATM.PAYSPRINT;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.core.widget.ImageViewCompat;

import com.fintech.omnipe.Auth.dto.LoginResponse;
import com.fintech.omnipe.BuildConfig;
import com.fintech.omnipe.Fragments.SdkDetail;
import com.fintech.omnipe.MicroATM.dto.InitiateMiniATMReq;
import com.fintech.omnipe.MicroATM.dto.InitiateMiniBankATMRes;
import com.fintech.omnipe.MicroATM.dto.UpdateMiniBankStatusReq;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.ApiClient;
import com.fintech.omnipe.Util.ApplicationConstant;
import com.fintech.omnipe.Util.DropdownDialog.DropDownDialog;
import com.fintech.omnipe.Util.EndPointInterface;
import com.fintech.omnipe.Util.GetLocation;
import com.fintech.omnipe.Util.UtilMethods;
import com.fintech.omnipe.usefull.CustomLoader;
import com.google.gson.Gson;
import com.paysprint.onboardinglib.activities.HostActivity;

import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;

public class PaySprintMATMActivity extends AppCompatActivity {

    private CustomLoader loader;
    private EditText amountEt, remarksEt;
    private Button paySprintPayBtn, historyBtn;
    private View amountView, remarkView;
    private String intentSdkType, intentOid;
    private LoginResponse LoginDataResponse;
    private SdkDetail intentSdkDetail;
    private GetLocation mGetLocation;
    private AlertDialog alertDialogReport;
    private View txnTypeChooserView,deviceTypeChooserView;
    private TextView txnType,deviceType;
    private DropDownDialog mDropDownDialog;
    private int selectedTypePos=-1,selectedDeviceTypePos=-1;
    private ArrayList<String> arrayListType = new ArrayList<>();
    private ArrayList<String> arrayListDeviceType = new ArrayList<>();
    private View btn_kyc;
    private final int PERMISSION_REQUEST_CODE =200;
    private String[] PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.BLUETOOTH};
    private static final int _resultCode = 123;
    private String partnerId,apiKey,merchantCode,transactionType="BE",amount,remarks,mobileNumber,subMerchantId,tid;
    private int deviceManufacturerId=1;
    private String deviceId, deviceSerialNum;
    private SharedPreferences myPrefs;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_paysprint_micro_atm);
        requestPermission();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Mini ATM");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        myPrefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNameLoginPref, MODE_PRIVATE);
        editor = myPrefs.edit();
        LoginDataResponse =  new Gson().fromJson(myPrefs.getString(ApplicationConstant.INSTANCE.LoginPref, ""), LoginResponse.class);
        deviceId = UtilMethods.INSTANCE.getDeviceId(this);
        deviceSerialNum = UtilMethods.INSTANCE.getSerialNo(this);
        mDropDownDialog = new DropDownDialog(this);
        intentSdkType = getIntent().getStringExtra("SDKType");
        intentOid = getIntent().getStringExtra("OID");
        intentSdkDetail = getIntent().getParcelableExtra("SDKDetails");
        if (intentSdkDetail != null) {
            partnerId = intentSdkDetail.getApiPartnerID();
            apiKey = intentSdkDetail.getApiOutletPassword();
            merchantCode = intentSdkDetail.getApiOutletID();
            subMerchantId = intentSdkDetail.getApiOutletID();
            mobileNumber = LoginDataResponse.getData().getMobileNo();
        }
        findViews();

        mGetLocation = new GetLocation(this, loader);
        if (UtilMethods.INSTANCE.getLattitude == 0 || UtilMethods.INSTANCE.getLongitude == 0) {
            mGetLocation.startLocationUpdatesIfSettingEnable((lattitude, longitude) -> {
                UtilMethods.INSTANCE.getLattitude = lattitude;
                UtilMethods.INSTANCE.getLongitude = longitude;
            });
        }

        arrayListDeviceType.add(getString(R.string.device_AF60s));
        arrayListDeviceType.add(getString(R.string.device_MP63));



        deviceTypeChooserView.setOnClickListener(v ->
                mDropDownDialog.showDropDownPopup(v, selectedDeviceTypePos, arrayListDeviceType, (clickPosition, value, object) -> {

                            if (selectedDeviceTypePos != clickPosition) {
                                deviceType.setText(value + "");
                                selectedDeviceTypePos = clickPosition;
                                if(clickPosition==0){
                                    deviceManufacturerId=1;
                                } else {
                                    deviceManufacturerId=2;
                                }
                            }
                        }
                ));

        arrayListType.add(getString(R.string.cash_withdrawal));
        arrayListType.add(getString(R.string.balance_enq));
        txnTypeChooserView.setOnClickListener(v ->
                mDropDownDialog.showDropDownPopup(v, selectedTypePos, arrayListType, (clickPosition, value, object) -> {

                            if (selectedTypePos != clickPosition) {
                                txnType.setText(value + "");
                                selectedTypePos = clickPosition;
                                if(clickPosition==0){
                                    amountView.setVisibility(View.VISIBLE);
                                    remarkView.setVisibility(View.VISIBLE);
                                } else {
                                    amountView.setVisibility(View.GONE);
                                    //remarkView.setVisibility(View.GONE);
                                }
                            }
                        }
                ));


    }

    private void findViews() {
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        amountEt = findViewById(R.id.et_amount);
        remarksEt = findViewById(R.id.et_remarks);
        txnTypeChooserView = findViewById(R.id.txnTypeChooserView);
        deviceTypeChooserView = findViewById(R.id.deviceTypeChooserView);
        deviceType = findViewById(R.id.deviceType);
        txnType = findViewById(R.id.txnType);
        btn_kyc = findViewById(R.id.btn_kyc);
        amountView = findViewById(R.id.amountView);
        remarkView = findViewById(R.id.remarkView);
        paySprintPayBtn = findViewById(R.id.btn_fingpay);
        paySprintPayBtn.setOnClickListener(v -> launch());

        historyBtn = findViewById(R.id.btn_history);


        btn_kyc.setVisibility(View.GONE);
        historyBtn.setVisibility(View.GONE);
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(
                    Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_REQUEST_CODE);
            }
        }
    }

    private void launch() {
        if (selectedDeviceTypePos == -1) {
            Toast.makeText(this, "Please select any device", Toast.LENGTH_SHORT).show();
            return;
        } else if (selectedTypePos == -1) {
            Toast.makeText(this, "Please select any type", Toast.LENGTH_SHORT).show();
            return;
        } else if (selectedTypePos==0 && amountEt.getText().toString().isEmpty()) {
            amountEt.setError("Please enter amount.");
            amountEt.requestFocus();
            return;

        }else if (/*selectedTypePos==1 && */remarksEt.getText().toString().isEmpty()) {
            remarksEt.setError("Please enter Remark");
            remarksEt.requestFocus();
            return;

        }

        if (selectedDeviceTypePos==0) {
            deviceManufacturerId=1;
        }else{
            deviceManufacturerId=2;
        }

        if (selectedTypePos==0) {
            InitiateMiniBank(this, loader);
        } else {
            String tid = "paysprint" + String. valueOf(new Date().getTime());
            getLocation(tid);
        }
    }


    void getLocation(String tid) {
        if (UtilMethods.INSTANCE.getLattitude != 0 && UtilMethods.INSTANCE.getLongitude != 0) {

            paySprintSubmit(tid, UtilMethods.INSTANCE.getLattitude, UtilMethods.INSTANCE.getLongitude);
        } else {
            if (mGetLocation != null) {
                mGetLocation.startLocationUpdates((lattitude, longitude) -> {
                    UtilMethods.INSTANCE.getLattitude = lattitude;
                    UtilMethods.INSTANCE.getLongitude = longitude;
                    paySprintSubmit(tid, lattitude, longitude);
                });
            } else {
                mGetLocation = new GetLocation(this, loader);
                mGetLocation.startLocationUpdates((lattitude, longitude) -> {
                    UtilMethods.INSTANCE.getLattitude = lattitude;
                    UtilMethods.INSTANCE.getLongitude = longitude;
                    paySprintSubmit(tid, lattitude, longitude);
                });
            }
        }
    }

    private void paySprintSubmit(String tid, double lattitude, double longitude) {

        amount = amountEt.getText().toString().trim();
        remarks = remarksEt.getText().toString().trim();

        if (UtilMethods.INSTANCE.isValidString(partnerId)) {
            if (UtilMethods.INSTANCE.isValidString(apiKey)) {
                if (UtilMethods.INSTANCE.isValidString(merchantCode)){
                    Intent intent = new Intent(getApplicationContext(), HostActivity.class);
                    intent.putExtra("partnerId", partnerId);
                    intent.putExtra("apiKey", apiKey);
                    intent.putExtra("merchantCode", merchantCode);  // API PARTNER END UNQIUE MERCHANT CODE used during onboarding
                    intent.putExtra("mobileNumber", mobileNumber) ;  // Customer Mobile Number
                    intent.putExtra("referenceNumber", tid);	 //unqiue txn Reference Number of api partner end
                    intent.putExtra("latitude", lattitude+"");	// Latitude
                    intent.putExtra("longitude", longitude+"") ; // Longitude
                    intent.putExtra("subMerchantId", subMerchantId); 	 // API PARTNER END UNQIUE MERCHANT CODE used during onboarding
                    intent.putExtra("deviceManufacturerId", deviceManufacturerId);  // value should be 1 for AF60s and 2 for MP63


                    switch (selectedTypePos) {
                        case 0:
                            if (amount.isEmpty()) {
                                amountEt.setError("Please enter amount.");
                                amountEt.requestFocus();
                                return;
                            }
                            transactionType="CW";
                            intent.putExtra("transactionType", transactionType); 	// BE for Balance Enquiry and CW for Cash Withdrawal
                            intent.putExtra("amount", amount);					// 0 for Balance Enquiry and Amount for Cash Withdrawal
                            intent.putExtra("remarks", remarks);
                            break;

                        case 1:
                            transactionType="BE";
                            amount="0";
                            intent.putExtra("transactionType", transactionType); 	// BE for Balance Enquiry and CW for Cash Withdrawal
                            intent.putExtra("amount", amount);					// 0 for Balance Enquiry and Amount for Cash Withdrawal
                            intent.putExtra("remarks", remarks);
                            break;


                    }
                    startActivityForResult(intent, _resultCode);
                }else{
                    Toast.makeText(this, "Please Provide the Merchant Code", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Please Provide the Api Key", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Please Provide the Partner Id", Toast.LENGTH_SHORT).show();
        }
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == _resultCode) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    Bundle bundle = data.getExtras();

                    for (String key : bundle.keySet()) {

                        Log.e("OnActivity Result","key : "+key+", Value : "+bundle.get(key) +", Type : " +bundle.get(key).getClass());
                       /*
                       Object value = bundle.get(key);
                       Log.d("PaySprint_Response :",String.format("%s %s (%s)", key,
                                value.toString(), value.getClass().getName()));*/
                    }

                    boolean status;
                    String response_msg = "",bankName="",cardNum="",bankRrn="",balAmount="",txnid="",cardType="";
                    try {
                        status= data.getBooleanExtra("status",false);
                        response_msg = data.getStringExtra("message");
                        bankName = data.getStringExtra("data:bankName");
                        cardNum = data.getStringExtra("data:cardNumber");
                        bankRrn = data.getStringExtra("data:bankRrn");
                        balAmount = data.getStringExtra("data:balAmount");
                        txnid = data.getStringExtra("data:txnid");
                        cardType = data.getStringExtra("data:cardType");
                    }catch (Exception exception){
                        status= Boolean.parseBoolean(data.getStringExtra("status"));
                    }



                    //String type = getIntent().getStringExtra("type");
                    //int txnStatus= getIntent().getIntExtra("txnstatus",0);

                    /* String fullRes= "Status : " + status + " Response Msg : "+ response_msg +"\nTransAmount : " +transAmount +"\nTxnId : "+ txnid +"\nTransType : "+ transType +"\nBalAmount : "+ balAmount +"\nCardType : "+ cardType + "\nBankName : "+ bankName +"\n TerminalId : "+ terminalId + "\nCardNum : "+ cardNum;*/

                    if (UtilMethods.INSTANCE.isValidString(response_msg)){
                        if (selectedTypePos==0) {
                            if (status) {
                                UpdateMiniBankStatus(PaySprintMATMActivity.this, cardNum, bankName, loader, bankRrn, "2", response_msg);

                            } else if(!status){
                                UpdateMiniBankStatus(PaySprintMATMActivity.this, cardNum, bankName, loader, bankRrn, "3", response_msg);
                            }
                        }
                    }else{
                        UtilMethods.INSTANCE.Error(PaySprintMATMActivity.this,"No Message Found!");
                        UpdateMiniBankStatus(PaySprintMATMActivity.this, "", "", loader, "", "3", "Data not found");
                    }



                    if (UtilMethods.INSTANCE.isValidString(response_msg)) {
                        if(selectedTypePos==0){
                            if (data.getExtras().size() >0) {
                                Intent intent = new Intent(PaySprintMATMActivity.this, PaySprintMATMReceiptActivity.class);
                                intent.putExtras(data);
                                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        }else if(selectedTypePos==1){
                            showBalData(status, response_msg, txnid, balAmount, bankRrn, bankName,cardType);
                        }

                    }

                } else {
                    Toast.makeText(this, "Data not found", Toast.LENGTH_SHORT).show();
                    if (selectedTypePos==0) {
                        UpdateMiniBankStatus(PaySprintMATMActivity.this, "", "", loader, "", "3", "Data not found");
                    }
                }
            }
            else if (resultCode == RESULT_CANCELED) {
                if (selectedTypePos==0) {
                    UpdateMiniBankStatus(PaySprintMATMActivity.this, "", "", loader, "", "3", "Canceled");
                }
                Toast.makeText(this, "canceled", Toast.LENGTH_SHORT).show();

            }
        } else {
            if (mGetLocation != null) {
                mGetLocation.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (mGetLocation != null) {
            mGetLocation.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onPause() {
        if (mGetLocation != null) {
            mGetLocation.onPause();
        }
        super.onPause();
    }
    public void InitiateMiniBank(final Activity context, final CustomLoader loader) {
        try {
            loader.show();
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<InitiateMiniBankATMRes> call = git.initiateMiniBank(new InitiateMiniATMReq(intentSdkType, intentOid, amountEt.getText().toString().trim(),
                    LoginDataResponse.getData().getUserID() + "", LoginDataResponse.getData().getLoginTypeID() + "",
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<InitiateMiniBankATMRes>() {

                @Override
                public void onResponse(Call<InitiateMiniBankATMRes> call, retrofit2.Response<InitiateMiniBankATMRes> response) {

                    if (loader.isShowing())
                        loader.dismiss();
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getStatuscode() == 1) {
                                tid = response.body().getTid() + "";
                                getLocation(tid);
                            } else {
                                if (!response.body().isVersionValid()) {
                                    UtilMethods.INSTANCE.versionDialog(context);
                                } else {
                                    UtilMethods.INSTANCE.Error(context, response.body().getMsg() + "");
                                }
                            }

                        }
                    } else {
                        UtilMethods.INSTANCE.apiErrorHandle(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<InitiateMiniBankATMRes> call, Throwable t) {

                    if (loader.isShowing())
                        loader.dismiss();
                    UtilMethods.INSTANCE.apiFailureError(context, t);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    public void UpdateMiniBankStatus(final Activity context, String cardNum, String bankName, final CustomLoader loader, String vendorId, String apiStatus, String remark) {
        try {
            loader.show();
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<InitiateMiniBankATMRes> call = git.updateMiniBankStatus(new UpdateMiniBankStatusReq(UtilMethods.INSTANCE.getLattitude+"", UtilMethods.INSTANCE.getLongitude+"",
                    cardNum, bankName, tid, vendorId, apiStatus, remark,
                    LoginDataResponse.getData().getUserID() + "",
                    LoginDataResponse.getData().getLoginTypeID() + "",
                    ApplicationConstant.INSTANCE.APP_ID, deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    LoginDataResponse.getData().getSessionID(),
                    LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<InitiateMiniBankATMRes>() {

                @Override
                public void onResponse(Call<InitiateMiniBankATMRes> call, retrofit2.Response<InitiateMiniBankATMRes> response) {

                    if (loader.isShowing())
                        loader.dismiss();
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getStatuscode() == 1) {

                            } else {
                                if (!response.body().isVersionValid()) {
                                    UtilMethods.INSTANCE.versionDialog(context);
                                } else {
                                    UtilMethods.INSTANCE.Error(context, response.body().getMsg() + "");
                                }
                            }

                        }
                    } else {
                        UtilMethods.INSTANCE.apiErrorHandle(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<InitiateMiniBankATMRes> call, Throwable t) {

                    if (loader.isShowing())
                        loader.dismiss();
                    UtilMethods.INSTANCE.apiFailureError(context, t);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }


    }



    private void showBalData(boolean status, String response, String txnId, String balAmount, String bankRrn, String bankName,String cardType) {
        try {
            if (alertDialogReport != null && alertDialogReport.isShowing()) {
                return;
            }
            AlertDialog.Builder dialogBuilder;
            dialogBuilder = new AlertDialog.Builder(this);
            alertDialogReport = dialogBuilder.create();
            alertDialogReport.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_paysprint_mini_atm_response, null);
            alertDialogReport.setView(dialogView);

            RelativeLayout statusBg = dialogView.findViewById(R.id.statusBg);
            ImageView closeIv = dialogView.findViewById(R.id.closeIv);
            ImageView statusIcon = dialogView.findViewById(R.id.statusIcon);
            TextView statusTv = dialogView.findViewById(R.id.statusTv);
            TextView statusMsg = dialogView.findViewById(R.id.statusMsg);
            LinearLayout tamtView = dialogView.findViewById(R.id.tamtView);
            TextView txnAmt = dialogView.findViewById(R.id.txnAmt);
            LinearLayout bAmtView = dialogView.findViewById(R.id.bAmtView);
            TextView balAmt = dialogView.findViewById(R.id.balAmt);
            LinearLayout rrnView = dialogView.findViewById(R.id.rrnView);
            TextView rrn = dialogView.findViewById(R.id.rrn);
            LinearLayout bankNameView = dialogView.findViewById(R.id.bankNameView);
            LinearLayout cardTypeView = dialogView.findViewById(R.id.cardTypeView);
            LinearLayout txnIdView = dialogView.findViewById(R.id.txnIdView);
            TextView txnIdTv = dialogView.findViewById(R.id.txnIdTv);
            TextView cardTypeTv = dialogView.findViewById(R.id.cardTypeTv);
            TextView bankNameTv = dialogView.findViewById(R.id.bankNameTv);

            if (status) {
                statusTv.setText("Success");
                statusIcon.setImageResource(R.drawable.ic_check_mark);
                ImageViewCompat.setImageTintList(statusIcon, AppCompatResources.getColorStateList(this, R.color.green));
                ViewCompat.setBackgroundTintList(statusBg, AppCompatResources.getColorStateList(this, R.color.green));
                statusTv.setTextColor(getResources().getColor(R.color.green));

            } else {
                statusTv.setText("Failed");
                statusIcon.setImageResource(R.drawable.ic_cross_mark);
                ImageViewCompat.setImageTintList(statusIcon, AppCompatResources.getColorStateList(this, R.color.color_red));
                ViewCompat.setBackgroundTintList(statusBg, AppCompatResources.getColorStateList(this, R.color.color_red));
                statusTv.setTextColor(getResources().getColor(R.color.color_red));

            }

            /*if (selectedTypePos==0) {
                typeTv.setText("Cash Withdrawl");
            } else if (selectedTypePos==1) {
                typeTv.setText("Balance Enquiry");
            }*/

            if (response != null && !response.isEmpty()) {
                statusMsg.setText(response);
                statusMsg.setVisibility(View.VISIBLE);
            } else {
                statusMsg.setVisibility(View.GONE);
            }
            if (balAmount!=null &&  !balAmount.equalsIgnoreCase("0")) {
                balAmt.setText("Your Balance Amount is : "+"\u20B9 " + UtilMethods.INSTANCE.formatedAmount(balAmount + ""));
                bAmtView.setVisibility(View.VISIBLE);
            } else {
                bAmtView.setVisibility(View.GONE);
            }

            if (UtilMethods.INSTANCE.isValidString(txnId)) {
                txnIdTv.setText(txnId+"");
                txnIdView.setVisibility(View.VISIBLE);
            } else {
                txnIdView.setVisibility(View.GONE);
            }

            if (UtilMethods.INSTANCE.isValidString(bankName)) {
                bankNameTv.setText(bankName+"");
                bankNameView.setVisibility(View.VISIBLE);
            } else {
                bankNameView.setVisibility(View.GONE);
            }

            if (UtilMethods.INSTANCE.isValidString(cardType)) {
                cardTypeTv.setText(cardType+"");
                cardTypeView.setVisibility(View.VISIBLE);
            } else {
                cardTypeView.setVisibility(View.GONE);
            }

            if (bankRrn != null && !bankRrn.isEmpty()) {
                rrn.setText(bankRrn);
                rrnView.setVisibility(View.VISIBLE);
            } else {
                rrnView.setVisibility(View.GONE);

            }


            closeIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialogReport.dismiss();
                }
            });
            alertDialogReport.show();

        } catch (IllegalStateException ise) {

        } catch (IllegalArgumentException iae) {

        } catch (Exception e) {

        }

    }

}
