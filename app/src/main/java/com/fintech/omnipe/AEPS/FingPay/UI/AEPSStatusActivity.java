package com.fintech.omnipe.AEPS.FingPay.UI;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.core.widget.ImageViewCompat;

import com.fintech.omnipe.Api.Response.AppUserListResponse;
import com.fintech.omnipe.Auth.dto.LoginResponse;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.UtilMethods;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AEPSStatusActivity extends AppCompatActivity {


    TextView address, outletDetail;
    private View rl_cancel;
    private AppCompatImageView statusIcon;
    private TextView statusTv;
    private TextView statusMsg;
    private TextView opTv;
    private TextView numTv;
    private TextView amtTv, balTv;
    private TextView deviceTv;
    private TextView liveLabel;
    private TextView liveTv;
    private TextView txnTv;
    private TextView dateTv;
    View deviceView, txnIdView, liveIdView, dateView, tv_share;
    LinearLayout shareView;
    private String intentMsg, intentLiveID, intentTxnId, intentOpName, intentAmt, intentBal, intentNum, intentDeviceName;
    private int intentStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_aeps_slip);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        intentMsg = getIntent().getStringExtra("MESSAGE");
        intentStatus = getIntent().getIntExtra("STATUS", 0);
        intentLiveID = getIntent().getStringExtra("LIVE_ID");
        intentTxnId = getIntent().getStringExtra("TRANSACTION_ID");
        intentOpName = getIntent().getStringExtra("OP_NAME");
        intentAmt = getIntent().getStringExtra("AMOUNT");
        intentBal = getIntent().getStringExtra("BALANCE");
        intentNum = getIntent().getStringExtra("NUMBER");
        intentDeviceName = getIntent().getStringExtra("Device_NAME");
        findViews();
        setUiData();
        rl_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });


        tv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareit();
            }
        });

        /*repetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });*/
        ImageView logoIv = findViewById(R.id.logoIv);

         UtilMethods.INSTANCE.setAppLogoIconUI(this,logoIv);

    }


    private void findViews() {

        outletDetail = findViewById(R.id.outletDetail);
        shareView = findViewById(R.id.shareView);
        rl_cancel = findViewById(R.id.rl_cancel);
        address = findViewById(R.id.address);
        statusIcon = findViewById(R.id.statusIcon);
        statusTv = findViewById(R.id.statusTv);
        statusMsg = findViewById(R.id.statusMsg);
        opTv = findViewById(R.id.opTv);
        numTv = findViewById(R.id.numTv);

        amtTv = findViewById(R.id.amtTv);
        balTv = findViewById(R.id.balTv);
        deviceTv = findViewById(R.id.deviceTv);
        liveTv = findViewById(R.id.liveTv);
        txnTv = findViewById(R.id.txnTv);
        deviceView = findViewById(R.id.deviceView);
        txnIdView = findViewById(R.id.txnIdView);
        liveIdView = findViewById(R.id.liveIdView);
        dateView = findViewById(R.id.dateView);
        dateTv = findViewById(R.id.dateTv);

        tv_share = findViewById(R.id.tv_share);


    }


    void setUiData() {
        if (intentStatus == 1) {
            statusIcon.setImageResource(R.drawable.ic_pending_black_24dp);
            ImageViewCompat.setImageTintList(statusIcon, AppCompatResources.getColorStateList(this, R.color.color_orange));
            statusTv.setTextColor(getResources().getColor(R.color.color_orange));
            statusTv.setText("Processing");
            statusMsg.setText("Transaction with reference id " + intentTxnId + " under process");
            /*repetBtn.setVisibility(View.GONE);*/
        } else if (intentStatus == 2) {
            statusIcon.setImageResource(R.drawable.ic_check_mark);
            ImageViewCompat.setImageTintList(statusIcon, AppCompatResources.getColorStateList(this, R.color.green));
            statusTv.setTextColor(getResources().getColor(R.color.green));
            statusTv.setText("Success");
            statusMsg.setText("Transaction with reference id " + intentTxnId + " Completed successfully");
            /*repetBtn.setVisibility(View.GONE);*/
        } else {
            statusIcon.setImageResource(R.drawable.ic_cross_mark);
            ImageViewCompat.setImageTintList(statusIcon, AppCompatResources.getColorStateList(this, R.color.color_red));
            statusTv.setTextColor(getResources().getColor(R.color.color_red));
            statusTv.setText("Failed");
            liveLabel.setText("Reason");
            statusMsg.setText(intentMsg);
            /*repetBtn.setVisibility(View.VISIBLE);*/
        }
        // statusMsg.setText(intentMsg);


        opTv.setText(intentOpName + "");
        amtTv.setText(getString(R.string.rupiya) + " " + intentAmt);
        liveTv.setText(intentLiveID + "");
        numTv.setText(intentNum + "");
        balTv.setText(intentBal + "");

        if (intentDeviceName != null && !intentDeviceName.isEmpty()) {
            deviceView.setVisibility(View.VISIBLE);
            deviceTv.setText(intentDeviceName + "");
        } else {
            deviceView.setVisibility(View.GONE);
        }
        if (intentTxnId != null && !intentTxnId.isEmpty()) {
            txnIdView.setVisibility(View.VISIBLE);
            txnTv.setText(intentTxnId + "");
        } else {
            txnIdView.setVisibility(View.GONE);
        }
        if (intentLiveID != null && !intentLiveID.isEmpty()) {
            liveIdView.setVisibility(View.VISIBLE);
            liveTv.setText(intentLiveID + "");
        } else {
            liveIdView.setVisibility(View.GONE);
        }


        SimpleDateFormat sdfDate = new SimpleDateFormat("dd MMM yyyy hh:mm aa");

        try {
            String dateStr = sdfDate.format(new Date());
            dateTv.setText(dateStr);
        } catch (Exception e) {

        }
        AppUserListResponse companyData =new Gson().fromJson(UtilMethods.INSTANCE.getCompanyProfile(this),AppUserListResponse.class);
        setCompanyDetail(companyData);

        setOutletDetail();
    }

    void setCompanyDetail(AppUserListResponse companyData) {
        if (companyData != null && companyData.getCompanyProfile() != null) {
            String company = companyData.getCompanyProfile().getName() + "\n" + Html.fromHtml(companyData.getCompanyProfile().getAddress());
            if (companyData.getCompanyProfile().getPhoneNo() != null && !companyData.getCompanyProfile().getPhoneNo().isEmpty()) {
                company = company + "\nLandline No : " + companyData.getCompanyProfile().getPhoneNo();
            }
            if (companyData.getCompanyProfile().getMobileNo() != null && !companyData.getCompanyProfile().getMobileNo().isEmpty()) {
                company = company + "\nMobile No : " + companyData.getCompanyProfile().getMobileNo();
            }
            if (companyData.getCompanyProfile().getEmailId() != null && !companyData.getCompanyProfile().getEmailId().isEmpty()) {
                company = company + "\nEmail : " + companyData.getCompanyProfile().getEmailId();
            }
            address.setText(company);
        } else {
            UtilMethods.INSTANCE.GetCompanyProfile(this, new UtilMethods.ApiCallBack() {
                @Override
                public void onSucess(Object object) {
                    AppUserListResponse data = (AppUserListResponse) object;
                    if (data != null && data.getCompanyProfile() != null) {
                        setCompanyDetail(data);
                    }
                }
            });
        }
    }

    void setOutletDetail() {
        LoginResponse LoginDataResponse = new Gson().fromJson(UtilMethods.INSTANCE.getLoginPref(this), LoginResponse.class);

        String outletDetailStr = "";
        if (LoginDataResponse.getData().getName() != null && !LoginDataResponse.getData().getName().isEmpty()) {
            outletDetailStr = outletDetailStr + "Name : " + LoginDataResponse.getData().getName();
        }
        if (LoginDataResponse.getData().getOutletName() != null && !LoginDataResponse.getData().getOutletName().isEmpty()) {
            outletDetailStr = outletDetailStr + " | Shop Name : " + LoginDataResponse.getData().getOutletName();
        }
        if (LoginDataResponse.getData().getMobileNo() != null && !LoginDataResponse.getData().getMobileNo().isEmpty()) {
            outletDetailStr = outletDetailStr + " | Contact No : " + LoginDataResponse.getData().getMobileNo();
        }
        if (LoginDataResponse.getData().getEmailID() != null && !LoginDataResponse.getData().getEmailID().isEmpty()) {
            outletDetailStr = outletDetailStr + " | Email : " + LoginDataResponse.getData().getEmailID();
        }
        if (LoginDataResponse.getData().getAddress() != null && !LoginDataResponse.getData().getAddress().isEmpty()) {
            outletDetailStr = outletDetailStr + " | Address : " + LoginDataResponse.getData().getAddress();
        }
        outletDetail.setText(outletDetailStr);

    }

    public void shareit() {
        File picFile = null;
        Bitmap myBitmap = null;

        //  View v1 = getWindow().getDecorView().getRootView();
        shareView.setDrawingCacheEnabled(true);
        myBitmap = Bitmap.createBitmap(shareView.getDrawingCache());
        shareView.setDrawingCacheEnabled(false);
        saveBitmap(myBitmap);

    }

    public void saveBitmap(Bitmap bitmap) {
        // Create a media file name
        Log.v("first", "first");
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());

        String filePath = Environment.getExternalStorageDirectory().toString()
                + "/" + timeStamp + ".jpg";
        File imagePath = new File(filePath);
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            Log.v("first", "second");
            sendMail(filePath);
        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }
    }

    public void sendMail(String path) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,
                "AEPS Receipt");
        emailIntent.putExtra(Intent.EXTRA_TEXT,
                "Receipt");
        emailIntent.setType("image/png");
        Uri myUri = Uri.parse("file://" + path);
        emailIntent.putExtra(Intent.EXTRA_STREAM, myUri);
        startActivity(Intent.createChooser(emailIntent, "Share via..."));
    }


}
