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
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fintech.omnipe.AEPS.FingPay.Adapter.AEPSMiniStatementAdapter;
import com.fintech.omnipe.AEPS.FingPay.dto.MiniStatements;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class AEPSMiniStatementActivity extends AppCompatActivity {


    TextView address, outletDetail;
    private View rl_cancel;


    private TextView opTv;

    private TextView numTv, balTv;
    private TextView deviceTv;


    View deviceView, tv_share;
    /* private AppCompatButton shareBtn, repetBtn;*/
    LinearLayout shareView;
    private String intentOpName, intentNum, intentDeviceName, intentBalance;

    private ArrayList<MiniStatements> miniStatements;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_aeps_mini_statement);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        intentOpName = getIntent().getStringExtra("OP_NAME");
        miniStatements = (ArrayList<MiniStatements>) getIntent().getSerializableExtra("MINI_STATEMENT");
        intentNum = getIntent().getStringExtra("NUMBER");
        intentDeviceName = getIntent().getStringExtra("Device_NAME");
        intentBalance = getIntent().getStringExtra("BALANCE");
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
        ImageView logoIv = findViewById(R.id.logoIv);
        UtilMethods.INSTANCE.setAppLogoIconUI(this,logoIv);

    }


    private void findViews() {

        outletDetail = findViewById(R.id.outletDetail);
        shareView = findViewById(R.id.shareView);
        rl_cancel = findViewById(R.id.rl_cancel);
        address = findViewById(R.id.address);

        opTv = findViewById(R.id.opTv);
        balTv = findViewById(R.id.balTv);

        numTv = findViewById(R.id.numTv);

        deviceTv = findViewById(R.id.deviceTv);

        deviceView = findViewById(R.id.deviceView);


        tv_share = findViewById(R.id.tv_share);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new AEPSMiniStatementAdapter(miniStatements, this));
        /*repetBtn = findViewById(R.id.repetBtn);*/

    }


    void setUiData() {


        opTv.setText(intentOpName + "");

        numTv.setText(intentNum + "");


        if (intentDeviceName != null && !intentDeviceName.isEmpty()) {
            deviceView.setVisibility(View.VISIBLE);
            deviceTv.setText(intentDeviceName + "");
        } else {
            deviceView.setVisibility(View.GONE);
        }
        balTv.setText(intentBalance + "");

        AppUserListResponse companyData = new Gson().fromJson(UtilMethods.INSTANCE.getCompanyProfile(this),AppUserListResponse.class);
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
