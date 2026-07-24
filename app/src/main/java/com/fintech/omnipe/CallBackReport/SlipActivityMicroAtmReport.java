package com.fintech.omnipe.CallBackReport;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.google.gson.Gson;
import com.fintech.omnipe.Api.Response.AppUserListResponse;
import com.fintech.omnipe.Auth.dto.LoginResponse;
import com.fintech.omnipe.Util.UtilMethods;
import com.fintech.omnipe.R;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SlipActivityMicroAtmReport extends AppCompatActivity {
    TextView  outletDetail;


    TextView  tv_txntype;
    TextView  tv_bankmessage;
    TextView  tv_amount;
    TextView  tv_invoiceno;
    TextView  tv_txnid;
    TextView  tv_referenceid;
    TextView  tv_pdate;
    TextView  tv_time;
    TextView  tv_txnamount;
    TextView  tv_rrn;
    TextView  tv_bankname,tv_cardType,tv_cardno,tv_terminalId;
    public AppCompatImageView operatorImage;

    TextView tvShare, address;

    RelativeLayout rlCancel;

    LinearLayout manin_lin,ll_cardType,ll_cardno,ll_terminalId;
    LinearLayout llVia;



    LinearLayout ll_rrn;
    LinearLayout ll_txnamount;
    LinearLayout ll_date;
    LinearLayout ll_reffrenceid;
    LinearLayout ll_txnid;
    LinearLayout ll_invoiceno;
    LinearLayout ll_Amount;
    LinearLayout ll_bankmessage;
    LinearLayout ll_bankname;
    LinearLayout ll_txntype;

    String requesttxn =  "" ;//Type of transaction
    String bankremarks =  "" ;//Bank message
    String refstan =  "" ;// Mahagram Stan Number
    String cardno =  "" ;//Atm card number
    String date =  "" ;//Date of transaction
    String amount = "";
    String invoicenumber =  "" ;//Invoice Number
    String mid =  "" ;//Mid
    String tid =  "" ;//Tid
    String clientrefid =  "" ;//Your reference Id
    String vendorid =  "" ;//Your define value
    String udf1 =  "" ;//Your define value
    String udf2 =  "" ;//Your define value
    String udf3 =  "" ;//Your define value
    String udf4 =  "" ;//Your define value
    String txnamount =  "" ;//Transaction amount (0 in case of balanceinquiry and transaction amount in cash withdrawal
    String rrn =  "" ,bankName="",cardType="",terminalId="",fpId="";
    int type;
    AppUserListResponse profileResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_slip_micro_atm_report);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        UtilMethods.INSTANCE.setAppLogoIconUI(this,findViewById(R.id.logoIv));
        getIds();

        tvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareit();
            }
        });
    }

    private void getIds() {


        requesttxn = getIntent().getExtras().getString("requesttxn ");//Type of transaction
        bankremarks = getIntent().getExtras().getString("msg");//Bank message
        refstan = getIntent().getExtras().getString("refstan");// Mahagram Stan Number
        cardno = getIntent().getExtras().getString("cardno");//Atm card number
        date = getIntent().getExtras().getString("date");//Date of transaction
        amount = getIntent().getExtras().getString("amount");//Account Balance
        invoicenumber = getIntent().getExtras().getString("invoicenumber");//Invoice Number
        mid = getIntent().getExtras().getString("mid");//Mid
        tid = getIntent().getExtras().getString("tid");//Tid
        clientrefid = getIntent().getExtras().getString("clientrefid");//Your reference Id
        vendorid = getIntent().getExtras().getString("vendorid");//Your define value
        udf1 = getIntent().getExtras().getString("udf1");//Your define value
        udf2 = getIntent().getExtras().getString("udf2");//Your define value
        udf3 = getIntent().getExtras().getString("udf3");//Your define value
        udf4 = getIntent().getExtras().getString("udf4");//Your define value
        txnamount = getIntent().getExtras().getString("txnamount");//Transaction amount (0 in case of balanceinquiry and transaction amount in cash withdrawal)
        rrn = getIntent().getExtras().getString("rrn");

        bankName = getIntent().getExtras().getString("bankName");
        cardType = getIntent().getExtras().getString("cardType");
        terminalId = getIntent().getExtras().getString("terminalId");
        fpId = getIntent().getExtras().getString("fpId");
        type = getIntent().getExtras().getInt("type");


        outletDetail = (TextView) findViewById(R.id.outletDetail);
        setOutletDetail();
        manin_lin = (LinearLayout) findViewById(R.id.manin_lin);
        operatorImage = (AppCompatImageView) findViewById(R.id.operatorImage);
        rlCancel = (RelativeLayout) findViewById(R.id.rl_cancel);
        llVia = (LinearLayout) findViewById(R.id.ll_via);
        ll_cardType = (LinearLayout) findViewById(R.id.ll_cardType);
        ll_cardno = (LinearLayout) findViewById(R.id.ll_cardno);
        ll_terminalId = (LinearLayout) findViewById(R.id.ll_terminalId);


        tvShare = (TextView) findViewById(R.id.tv_share);
        tv_txntype = (TextView) findViewById(R.id.tv_txntype);
        tv_bankmessage = findViewById(R.id.tv_bankmessage);
        tv_amount = findViewById(R.id.tv_amount);
        tv_invoiceno = findViewById(R.id.tv_invoiceno);
        tv_txnid = findViewById(R.id.tv_txnid);
        tv_referenceid = findViewById(R.id.tv_referenceid);
        tv_pdate = findViewById(R.id.tv_date);
        tv_time = findViewById(R.id.tv_time);
        tv_txnamount = findViewById(R.id.tv_txnamount);
        tv_rrn = findViewById(R.id.tv_rrn);
        tv_bankname = findViewById(R.id.tv_bankname);
        tv_cardType = findViewById(R.id.tv_cardType);
        tv_cardno = findViewById(R.id.tv_cardno);
        tv_terminalId = findViewById(R.id.tv_terminalId);


        ll_rrn = findViewById(R.id.ll_rrn);
        ll_txnamount = findViewById(R.id.ll_txnamount);
        ll_date = findViewById(R.id.ll_date);
        ll_reffrenceid = findViewById(R.id.ll_reffrenceid);
        ll_txnid = findViewById(R.id.ll_txnid);
        ll_invoiceno = findViewById(R.id.ll_invoiceno);
        ll_Amount = findViewById(R.id.ll_Amount);
        ll_txntype = findViewById(R.id.ll_txntype);
        ll_bankmessage = findViewById(R.id.ll_bankmessage);
        ll_bankname = findViewById(R.id.ll_bankname);



        rlCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        address = (TextView) findViewById(R.id.address);

        AppUserListResponse profileResponse= new Gson().fromJson(UtilMethods.INSTANCE.getCompanyProfile(this),AppUserListResponse.class);

       // CompanyProfileResponse profileResponse= UtilMethods.INSTANCE.getCompanyProfile(this);
        String company="";
        if(profileResponse!=null)
        {
             company = profileResponse.getCompanyProfile().getName() + "\n" + Html.fromHtml(profileResponse.getCompanyProfile().getAddress());
            if (profileResponse.getCompanyProfile().getPhoneNo() != null && !profileResponse.getCompanyProfile().getPhoneNo().isEmpty()) {
                company = company + "\nLandline No : " + profileResponse.getCompanyProfile().getPhoneNo();
            }
            if (profileResponse.getCompanyProfile().getMobileNo() != null && !profileResponse.getCompanyProfile().getMobileNo().isEmpty()) {
                company = company + "\nMobile No : " + profileResponse.getCompanyProfile().getMobileNo();
            }
            if (profileResponse.getCompanyProfile().getEmailId() != null && !profileResponse.getCompanyProfile().getEmailId().isEmpty()) {
                company = company + "\nEmail : " + profileResponse.getCompanyProfile().getEmailId();
            }

        }
        address.setText(company);


        try{

            if (terminalId!=null && !terminalId.isEmpty())
            {
                tv_terminalId.setText(terminalId);
            }else {
                ll_terminalId.setVisibility(View.GONE);
            }

            if (cardno!=null && !cardno.isEmpty())
            {
                tv_cardno.setText(cardno);
            }else {
                ll_cardno.setVisibility(View.GONE);
            }

            if (cardType!=null && !cardType.isEmpty())
            {
                tv_cardType.setText(cardType);
            }else {
                ll_cardType.setVisibility(View.GONE);
            }

            if (rrn!=null && !rrn.isEmpty())
            {
                tv_rrn.setText(rrn);
            }else {
                ll_rrn.setVisibility(View.GONE);
            }


            if (requesttxn!=null && !requesttxn.isEmpty())
            {
                tv_txntype.setText(requesttxn);
            }else {
                ll_txntype.setVisibility(View.GONE);
            }

            if (bankName!=null && !bankName.isEmpty())
            {
                tv_bankname.setText(bankName);
            }else {
                ll_bankname.setVisibility(View.GONE);
            }


            if (txnamount!=null && !txnamount.isEmpty())
            {
                tv_txnamount.setText(txnamount);
            }else {

                ll_txnamount.setVisibility(View.GONE);
            }


            if (bankremarks!=null && !bankremarks.isEmpty())
            {
                tv_bankmessage.setText(bankremarks);
            }else {

                ll_bankmessage.setVisibility(View.GONE);
            }
            if (date!=null && !date.equalsIgnoreCase(""))
            {
                tv_pdate.setText(date);
            }else {

                ll_date.setVisibility(View.GONE);
            }

            if (amount!=null && !amount.isEmpty())
            {
                tv_amount.setText(getString(R.string.rupiya) + " " + amount);
            }else {

                ll_Amount.setVisibility(View.GONE);
            }
            if (clientrefid!=null && !clientrefid.isEmpty())
            {
                tv_referenceid.setText(clientrefid);
            }else {

                ll_reffrenceid.setVisibility(View.GONE);
            }


            if (tid!=null && !tid.isEmpty())
            {
                tv_txnid.setText(tid);
            }else {

                ll_txnid.setVisibility(View.GONE);
            }



            if (invoicenumber!=null && !invoicenumber.isEmpty())
            {
                tv_invoiceno.setText(invoicenumber);

            }else {

                ll_invoiceno.setVisibility(View.GONE);
            }


        }catch (Exception e){
            e.printStackTrace();
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

        View v1 = getWindow().getDecorView().getRootView();
        v1.setDrawingCacheEnabled(true);
        myBitmap = Bitmap.createBitmap(v1.getDrawingCache());
        v1.setDrawingCacheEnabled(false);
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
                "Recharge Receipt");
        emailIntent.putExtra(Intent.EXTRA_TEXT,
                "Receipt");
        emailIntent.setType("image/png");
        Uri myUri = Uri.parse("file://" + path);
        emailIntent.putExtra(Intent.EXTRA_STREAM, myUri);
        startActivity(Intent.createChooser(emailIntent, "Share via..."));
    }

}
