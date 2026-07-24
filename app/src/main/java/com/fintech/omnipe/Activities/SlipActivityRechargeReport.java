package com.fintech.omnipe.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.fintech.omnipe.Api.Response.AppUserListResponse;
import com.fintech.omnipe.Auth.dto.LoginResponse;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.UtilMethods;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SlipActivityRechargeReport extends AppCompatActivity {

    public AppCompatImageView operatorImage;
    TextView tvAmount;
    TextView tvRechargeMobileNo;
    TextView tvliveId, tvTxnStatus;
    TextView tvoperatorname;
    TextView tvpdate;
    TextView tvptime;
    TextView tvtxid, outletDetail;
    TextView tvShare, address,accountTitle;

    RelativeLayout rlCancel;

    String amount = "";
    String RechargeMobileNo ,intentTransactionRemark;
    String liveId = "";
    String pdate = "";
    String ptime = "";
    String operatorname = "";
    String txid = "";
    String txStatus = "";
    String typerecharge = "";
    String imageurl = "";
    LinearLayout manin_lin;


    LinearLayout llVia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.recharge_report_print_popup);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        UtilMethods.INSTANCE.setAppLogoIconUI(this,findViewById(R.id.logoIv));
        getIds();
    }

    private void getIds() {
        amount = getIntent().getExtras().getString("amount");
        RechargeMobileNo = getIntent().getExtras().getString("RechargeMobileNo");
        intentTransactionRemark = getIntent().getExtras().getString("transactionRemark");
        liveId = getIntent().getExtras().getString("liveId");
        operatorname = getIntent().getExtras().getString("operatorname");
        pdate = getIntent().getExtras().getString("pdate");
        ptime = getIntent().getExtras().getString("ptime");
        txid = getIntent().getExtras().getString("txid");
        txStatus = getIntent().getExtras().getString("txStatus");
        typerecharge = getIntent().getExtras().getString("typerecharge");
        imageurl = getIntent().getExtras().getString("imageurl");

        outletDetail = (TextView) findViewById(R.id.outletDetail);
        setOutletDetail();
        manin_lin = (LinearLayout) findViewById(R.id.manin_lin);
        operatorImage = (AppCompatImageView) findViewById(R.id.operatorImage);
        rlCancel = (RelativeLayout) findViewById(R.id.rl_cancel);
        llVia = (LinearLayout) findViewById(R.id.ll_via);
        tvTxnStatus = findViewById(R.id.tv_txstatus);
        rlCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        address = (TextView) findViewById(R.id.address);
        accountTitle = (TextView) findViewById(R.id.accountTitle);
        AppUserListResponse companyData = new Gson().fromJson(UtilMethods.INSTANCE.getCompanyProfile(this), AppUserListResponse.class);

        if (companyData != null) {
            String company = "";
            if (!companyData.getCompanyProfile().getName().isEmpty()) {
                company = companyData.getCompanyProfile().getName() + "\n";
            }
            if (!companyData.getCompanyProfile().getAddress().isEmpty()) {
                company = company + Html.fromHtml(companyData.getCompanyProfile().getAddress()) + "\n";
            }
            if (!companyData.getCompanyProfile().getPhoneNo().isEmpty()) {
                company = company + "Landline No : " + companyData.getCompanyProfile().getPhoneNo() + "\n";
            }
            if (!companyData.getCompanyProfile().getMobileNo().isEmpty()) {
                company = company + "Mobile No : " + companyData.getCompanyProfile().getMobileNo() + "\n";
            }
            if (!companyData.getCompanyProfile().getEmailId().isEmpty()) {
                company = company + "Email : " + companyData.getCompanyProfile().getEmailId();
            }
            address.setText(company);
        } else {
            address.setVisibility(View.GONE);
        }
        tvAmount = (TextView) findViewById(R.id.tv_amount);
        tvRechargeMobileNo = (TextView) findViewById(R.id.tv_RechargeMobileNo);
        tvliveId = (TextView) findViewById(R.id.tv_liveId);
        tvoperatorname = (TextView) findViewById(R.id.tv_operatorname);
        tvpdate = (TextView) findViewById(R.id.tv_pdate);
        tvptime = (TextView) findViewById(R.id.tv_ptime);

        tvShare = (TextView) findViewById(R.id.tv_share);
        tvShare = (TextView) findViewById(R.id.tv_share);
        tvtxid = (TextView) findViewById(R.id.tv_txid);

       /* if (typerecharge.equalsIgnoreCase("SUCCESS")) {
            manin_lin.setVisibility(View.VISIBLE);

        } else {
            manin_lin.setVisibility(View.GONE);

        }*/

        if (imageurl != null) {
            Glide.with(this).load(imageurl)
                    .thumbnail(0.5f)
                    .transition(new DrawableTransitionOptions().crossFade())
                    .apply(new RequestOptions().placeholder(R.drawable.rnd_placeholder).error(R.drawable.rnd_placeholder).diskCacheStrategy(DiskCacheStrategy.ALL))
                    .into(operatorImage);
        } else {
            //operatorImage.operatorImage.setImageResource(R.drawable.ic_operator_default_icon);
        }

        tvAmount.setText(getString(R.string.rupiya) + " " + amount);
        tvRechargeMobileNo.setText(RechargeMobileNo);
        tvliveId.setText(liveId);
        tvpdate.setText(pdate);
        tvptime.setText(ptime);
        tvoperatorname.setText(operatorname);
        tvtxid.setText(txid);
        tvTxnStatus.setText(txStatus);

        accountTitle.setText(intentTransactionRemark+"");

        tvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareit();
            }
        });
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

        View v1 = getWindow().getDecorView().getRootView();
        v1.setDrawingCacheEnabled(true);
        myBitmap = Bitmap.createBitmap(v1.getDrawingCache());
        v1.setDrawingCacheEnabled(false);
        saveBitmap(myBitmap);

    }

    public void saveBitmap(Bitmap bitmap) {
        // Create a media file name
        /*Log.v("first", "first");
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());*/

        String filePath = getExternalCacheDir()
                + "/Recharge_Slip.jpg";
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
                "Recharge Receipt");
        emailIntent.putExtra(Intent.EXTRA_TEXT,
                "Receipt");
        emailIntent.setType("image/png");
        Uri myUri = Uri.parse("file://" + path);
        emailIntent.putExtra(Intent.EXTRA_STREAM, myUri);
        startActivity(Intent.createChooser(emailIntent, "Share via..."));
    }

}
