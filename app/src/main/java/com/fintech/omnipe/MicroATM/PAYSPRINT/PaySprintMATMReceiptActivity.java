package com.fintech.omnipe.MicroATM.PAYSPRINT;

import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.fintech.omnipe.AEPS.FingPay.Adapter.ReceiptDetailListAdapter;
import com.fintech.omnipe.AEPS.FingPay.dto.ReceiptObject;
import com.fintech.omnipe.Api.Response.AppUserListResponse;
import com.fintech.omnipe.Auth.dto.LoginResponse;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.ApplicationConstant;
import com.fintech.omnipe.Util.UtilMethods;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PaySprintMATMReceiptActivity extends AppCompatActivity {
    private LinearLayout shareView;
    private ImageView statusIcon;
    private TextView statusTv;
    private TextView statusMsg;
    private TextView companyName;
    private TextView address;
    private RecyclerView recyclerView;
    private TextView outletDetail;
    private LinearLayout btRepeat;
    private LinearLayout btShare;
    private LinearLayout btWhatsapp;
    private ImageView closeIv;
    private String responseMsg,response,remarks;
    private String transType;
    private String bankRrn;
    private String txnid;
    private String deviceManufacturerId;
    String cardNum;
    String bankName;
    String cardType;
    String txnrefrenceNo;
    String time;
    String invoiceNum;
    String mid;
    String clientrefid;
    String vendorId;
    String stanNo;
    String transAmount;
    double balAmount;
    boolean status;
    ArrayList<ReceiptObject> mReceiptObjects = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_pay_sprint_micro_atm_slip);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        findViews();
        setUiData();
        closeIv.setOnClickListener(v -> finish());
        btShare.setOnClickListener(v -> shareit(false));
        btRepeat.setOnClickListener(v -> finish());

        if (UtilMethods.INSTANCE.isPackageInstalled("com.whatsapp", getPackageManager())) {
            btWhatsapp.setVisibility(View.VISIBLE);
            btWhatsapp.setOnClickListener(view -> shareit(true));
        } else {
            btWhatsapp.setVisibility(View.GONE);
        }

    }



    private void findViews() {
        shareView = (LinearLayout) findViewById(R.id.shareView);
        //titleTv = findViewById(R.id.titleTv);
        statusIcon = (ImageView) findViewById(R.id.statusIcon);
        statusTv = (TextView) findViewById(R.id.statusTv);
        statusMsg = (TextView) findViewById(R.id.statusMsg);
        companyName = (TextView) findViewById(R.id.companyName);
        address = (TextView) findViewById(R.id.address);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        outletDetail = (TextView) findViewById(R.id.outletDetail);
        btRepeat = (LinearLayout) findViewById(R.id.bt_repeat);
        btShare = (LinearLayout) findViewById(R.id.bt_share);
        btWhatsapp = (LinearLayout) findViewById(R.id.bt_whatsapp);
        closeIv = (ImageView) findViewById(R.id.closeIv);

        ImageView logoIv = findViewById(R.id.appLogo);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.rnd_logo);
        requestOptions.error(R.drawable.rnd_logo);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        String appLogoUrl = UtilMethods.INSTANCE.getAppLogoUrl(this);
        if (appLogoUrl != null && !appLogoUrl.isEmpty()) {
            Glide.with(this)
                    .load(appLogoUrl)
                    .apply(requestOptions)
                    .into(logoIv);
        } else {
            int wid = UtilMethods.INSTANCE.getWIDPref(this);
            if (wid > 0) {

                Glide.with(this)
                        .load(ApplicationConstant.INSTANCE.baseAppIconUrl + wid + "/logo.png")
                        .apply(requestOptions)
                        .into(logoIv);
            }
        }
    }

    private void setUiData() {
        status= getIntent().getBooleanExtra("status",false);
        responseMsg = getIntent().getStringExtra("message");
        response = getIntent().getStringExtra("data:response");
        bankName = getIntent().getStringExtra("data:bankName");
        cardNum = getIntent().getStringExtra("data:cardNumber");
        bankRrn = getIntent().getStringExtra("data:bankRrn");
        balAmount = Double.parseDouble(getIntent().getStringExtra("data:balAmount"));
        transAmount =getIntent().getStringExtra("data:transAmount");
        txnid =getIntent().getStringExtra("data:txnid");
        txnrefrenceNo = getIntent().getStringExtra("referenceNumber");
        cardType = getIntent().getStringExtra("data:cardType");
        transType = getIntent().getStringExtra("data:transType");
        remarks = getIntent().getStringExtra("remarks");
        try {
            deviceManufacturerId = getIntent().getStringExtra("deviceManufacturerId");
        }catch (Exception exception){
            deviceManufacturerId = String.valueOf(getIntent().getIntExtra("deviceManufacturerId",0));
        }

        recyclerView.setAdapter(new ReceiptDetailListAdapter(mReceiptObjects, this));
        setCompanyDetail(UtilMethods.INSTANCE.getCompanyProfileDetails(this));
        setOutletDetail();


        if (status) {
            statusIcon.setImageResource(R.drawable.ic_check_circle_black_24dp);
            ImageViewCompat.setImageTintList(statusIcon, AppCompatResources.getColorStateList(this, R.color.green));
            statusTv.setTextColor(getResources().getColor(R.color.green));
            statusTv.setText("Success");
            if (txnid != null && !txnid.isEmpty()) {
                statusMsg.setText("Transaction with TxnId " + txnid + " completed successfully");
            }
            else if (txnrefrenceNo != null && !txnrefrenceNo.isEmpty()) {
                statusMsg.setText("Transaction with TxnReferenceNo " + txnrefrenceNo + " completed successfully");
            } else if (bankRrn != null && !bankRrn.isEmpty()) {
                statusMsg.setText("Transaction with bank rrn " + bankRrn + " completed successfully");
            }  else {
                statusMsg.setText("Transaction completed successfully");
            }
        }
        else if(status){
            statusIcon.setImageResource(R.drawable.ic_cross_mark);
            ImageViewCompat.setImageTintList(statusIcon, AppCompatResources.getColorStateList(this, R.color.color_red));
            statusTv.setTextColor(getResources().getColor(R.color.color_red));
            statusTv.setText("Failed");
            if (responseMsg != null && !responseMsg.isEmpty()) {
                statusMsg.setText(responseMsg + "");
            }  else {
                statusMsg.setText("Sorry, Transaction Failed, Please try after some time");
            }
        }

        if (transAmount!=null && !transAmount.equalsIgnoreCase("0")) {
            mReceiptObjects.add(new ReceiptObject("Transaction Amount", "\u20B9 " + UtilMethods.INSTANCE.formatedAmount(transAmount + "")));
        }

        if (balAmount!=0) {
            mReceiptObjects.add(new ReceiptObject("Balance Amount", "\u20B9 " + UtilMethods.INSTANCE.formatedAmount(balAmount + "")));
        }

        if (remarks != null && !remarks.isEmpty()) {
            mReceiptObjects.add(new ReceiptObject("Remarks ", remarks));
        }

        if (txnrefrenceNo != null && !txnrefrenceNo.isEmpty()) {
            mReceiptObjects.add(new ReceiptObject("Transaction ReferenceNo", txnrefrenceNo));
        }

        /*if (txnid != null && !txnid.isEmpty()) {
            mReceiptObjects.add(new ReceiptObject("Txn ID", txnid));
        }*/
        if (bankName != null && !bankName.isEmpty()) {
            mReceiptObjects.add(new ReceiptObject("Bank Name ", bankName));
        }
        if (bankRrn != null && !bankRrn.isEmpty()) {
            mReceiptObjects.add(new ReceiptObject("Bank RRN", bankRrn));
        }
       /* if (cardType != null && !cardType.isEmpty()) {
            mReceiptObjects.add(new ReceiptObject("Card Type ", cardType));
        }*/

        if (cardNum != null && !cardNum.isEmpty()) {

            if (cardType != null && !cardType.isEmpty()) {
                mReceiptObjects.add(new ReceiptObject("Card Number", cardNum + " [" + cardType + "]"));

            } else {
                mReceiptObjects.add(new ReceiptObject("Card Number", cardNum + ""));
            }
        }

        if (transType != null && !transType.isEmpty()) {
            mReceiptObjects.add(new ReceiptObject("Transaction Type", transType));
        }


        if (deviceManufacturerId!=null && deviceManufacturerId.equalsIgnoreCase("1")) {
            mReceiptObjects.add(new ReceiptObject("Device ", getString(R.string.device_AF60s)));
        }

        if (deviceManufacturerId!=null &&deviceManufacturerId.equalsIgnoreCase("2")) {
            mReceiptObjects.add(new ReceiptObject("Device ", getString(R.string.device_MP63)));
        }


        if (invoiceNum != null && !invoiceNum.isEmpty()) {
            mReceiptObjects.add(new ReceiptObject("Invoice Number", invoiceNum));
        }
        if (mid != null && !mid.isEmpty()) {
            mReceiptObjects.add(new ReceiptObject("Merchant Id", mid));
        }
        if (clientrefid != null && !clientrefid.isEmpty()) {
            mReceiptObjects.add(new ReceiptObject("Client Ref Id", clientrefid));
        }
        if (vendorId != null && !vendorId.isEmpty()) {
            mReceiptObjects.add(new ReceiptObject("Vendor Id", vendorId));
        }
        if (stanNo != null && !stanNo.isEmpty()) {
            mReceiptObjects.add(new ReceiptObject("Stan Number", stanNo));
        }

        if (time != null && !time.isEmpty()) {
            mReceiptObjects.add(new ReceiptObject("Transaction Date", time + ""));
        } else {
            SimpleDateFormat sdfDate = new SimpleDateFormat("dd MMM yyyy hh:mm aa");
            try {
                String dateStr = sdfDate.format(new Date());
                mReceiptObjects.add(new ReceiptObject("Transaction Date", dateStr + ""));
            } catch (Exception e) {

            }
        }

    }

    private void setCompanyDetail(AppUserListResponse companyData) {

        if (companyData != null && companyData.getCompanyProfile() != null) {
            companyName.setText(companyData.getCompanyProfile().getName() + "");
            String company = "" + Html.fromHtml(companyData.getCompanyProfile().getAddress());
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
            UtilMethods.INSTANCE.GetCompanyProfile(this,  object -> {
                AppUserListResponse companyData1 = (AppUserListResponse) object;
                if (companyData1 != null && companyData1.getCompanyProfile() != null) {
                    setCompanyDetail(companyData1);
                }
            });
        }
    }

    private void setOutletDetail() {

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

    public void shareit(boolean isWhatsapp) {
        Bitmap myBitmap = Bitmap.createBitmap(shareView.getWidth(), shareView.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(myBitmap);
        shareView.layout(0, 0, shareView.getWidth(), shareView.getHeight());
        shareView.draw(c);
        saveImage(myBitmap, isWhatsapp);

    }

    private void saveImage(Bitmap bitmap, boolean isWhatsapp) {
        if (Build.VERSION.SDK_INT >= 30) {
            ContentValues values = contentValues();
            values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/" + getString(R.string.app_name));
            values.put(MediaStore.Images.Media.IS_PENDING, true);

            Uri uri = this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            if (uri != null) {
                try {
                    saveImageToStream(bitmap, this.getContentResolver().openOutputStream(uri));
                    values.put(MediaStore.Images.Media.IS_PENDING, false);
                    this.getContentResolver().update(uri, values, null, null);
                    if (isWhatsapp) {
                        openWhatsapp(uri);
                    } else {
                        sendMail(uri);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        } else {
            File directory = new File(Environment.getExternalStorageDirectory().toString() + "/Pictures/" + getString(R.string.app_name));

            if (!directory.exists()) {
                directory.mkdirs();
            }
            String fileName = System.currentTimeMillis() + ".png";
            File file = new File(directory, fileName);
            try {
                saveImageToStream(bitmap, new FileOutputStream(file));
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
                this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                if (isWhatsapp) {
                    openWhatsapp(Uri.parse("file://" + file));
                } else {
                    sendMail(Uri.parse("file://" + file));
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    private ContentValues contentValues() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
        values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        }
        return values;
    }

    private void saveImageToStream(Bitmap bitmap, OutputStream outputStream) {
        if (outputStream != null) {
            try {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                outputStream.close();
            } catch (FileNotFoundException e) {
                Log.e("GREC", e.getMessage(), e);
            } catch (IOException e) {
                Log.e("GREC", e.getMessage(), e);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMail(Uri myUri) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,
                "Recharge Receipt");
        emailIntent.putExtra(Intent.EXTRA_TEXT,
                "Receipt");
        emailIntent.setType("image/png");
        emailIntent.putExtra(Intent.EXTRA_STREAM, myUri);
        startActivity(Intent.createChooser(emailIntent, "Share via..."));
    }

    public void openWhatsapp(Uri myUri) {

        try {
            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Recharge Receipt");
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Receipt");
            sendIntent.setType("image/png");
            sendIntent.putExtra(Intent.EXTRA_STREAM, myUri);
            sendIntent.setPackage("com.whatsapp");
            startActivity(sendIntent);
        } catch (ActivityNotFoundException e) {

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=com.whatsapp"));
            startActivity(intent);


        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
