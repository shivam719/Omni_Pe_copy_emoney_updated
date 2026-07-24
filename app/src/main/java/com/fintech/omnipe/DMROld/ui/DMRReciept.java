package com.fintech.omnipe.DMROld.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.fintech.omnipe.Api.Response.DMTReceiptResponse;
import com.fintech.omnipe.Auth.dto.LoginResponse;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.ListOblect;
import com.fintech.omnipe.Util.UtilMethods;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class DMRReciept extends AppCompatActivity {
    String response, flag;
    TextView Name, Email, BankName, senderNo, AccountNo, ifsc, Date,outletDetail;
    ImageView cancel;
    RecyclerView list;
    DMTReceiptResponse dmtReceiptResponse;
    List<ListOblect> listOblects;
    List<ListOblect> listOblectsnew;
    DmrRecieptAdapter mAdapter;
    View tv_share;

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.dmr_reciept);
        response = getIntent().getStringExtra("response");
        flag = getIntent().getStringExtra("flag");
        UtilMethods.INSTANCE.setAppLogoIconUI(this,findViewById(R.id.logoIv));
        getID();
        try {
            if (flag.equals("All")) {
                dataparse();

            } else {

                dataparse2();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        setOutletDetail();

    }


    private void dataparse() throws ParseException {

        Gson gson = new Gson();
        dmtReceiptResponse = gson.fromJson(response, DMTReceiptResponse.class);
        listOblects = dmtReceiptResponse.getTransactionDetail().getLists();
        if (listOblects.size() > 0) {
            if (flag.equals("All")) {
                mAdapter = new DmrRecieptAdapter(listOblects, DMRReciept.this);
            } else {
                listOblectsnew = new ArrayList<>();
                for (int i = 0; i < listOblects.size(); i++) {
                    String status = listOblects.get(i).getStatuscode() + "";
                    if (status.equals("2")) {
                        listOblectsnew.add(listOblects.get(i));
                    }
                }
                mAdapter = new DmrRecieptAdapter(listOblectsnew, DMRReciept.this);
            }
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            list.setLayoutManager(mLayoutManager);
            list.setItemAnimator(new DefaultItemAnimator());
            list.setAdapter(mAdapter);
        }
        Name.setText(dmtReceiptResponse.getTransactionDetail().getBeneName());
        BankName.setText(dmtReceiptResponse.getTransactionDetail().getBankName());
        senderNo.setText(dmtReceiptResponse.getTransactionDetail().getSenderNo());
        AccountNo.setText(dmtReceiptResponse.getTransactionDetail().getAccount());
        Date.setText("Your Transaction Detail On " + dmtReceiptResponse.getTransactionDetail().getEntryDate());
        ifsc.setText(dmtReceiptResponse.getTransactionDetail().getIfsc());
        Email.setText(dmtReceiptResponse.getTransactionDetail().getEmail());
    }

    private void dataparse2() {
        Gson gson = new Gson();
        listOblectsnew = new ArrayList<>();
        dmtReceiptResponse = gson.fromJson(response, DMTReceiptResponse.class);
        listOblects = dmtReceiptResponse.getTransactionDetail().getLists();
        for (int i = 0; i < listOblects.size(); i++) {
            String status = listOblects.get(i).getStatuscode() + "";
            if (status.equals("2")) {
                listOblectsnew.add(listOblects.get(i));
            }
        }
        if (listOblectsnew.size() > 0) {
            mAdapter = new DmrRecieptAdapter(listOblectsnew, DMRReciept.this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            list.setLayoutManager(mLayoutManager);
            list.setItemAnimator(new DefaultItemAnimator());
            list.setAdapter(mAdapter);
            Name.setText(dmtReceiptResponse.getTransactionDetail().getBeneName());
            BankName.setText(dmtReceiptResponse.getTransactionDetail().getBankName());
            senderNo.setText(dmtReceiptResponse.getTransactionDetail().getSenderNo());
            AccountNo.setText(dmtReceiptResponse.getTransactionDetail().getAccount());
            Date.setText("Your Transaction Detail On " + dmtReceiptResponse.getTransactionDetail().getEntryDate());
            ifsc.setText(dmtReceiptResponse.getTransactionDetail().getIfsc());
            Email.setText(dmtReceiptResponse.getTransactionDetail().getEmail());

        } else {
            UtilMethods.INSTANCE.Error(this, "No Transaction to print");
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

    private void getID() {
        outletDetail= findViewById(R.id.outletDetail);
        Name = findViewById(R.id.Name);
        Email = findViewById(R.id.Email);
        BankName = findViewById(R.id.BankName);
        senderNo = findViewById(R.id.senderNo);
        AccountNo = findViewById(R.id.AccountNo);
        Date = findViewById(R.id.Date);
        ifsc = findViewById(R.id.ifsc);
        list = findViewById(R.id.list);
        tv_share = findViewById(R.id.tv_share);
        cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (Build.VERSION.SDK_INT >= 24) {
            try {
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        tv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareit();
            }
        });
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
        /*String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());*/
        String filePath = getExternalCacheDir()
                + "/DMR_Slip.jpg";
        /*String filePath = Environment.getExternalStorageDirectory().toString()
                + "/" + timeStamp + ".jpg";*/
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

    /*public void sendMail() {
        final Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,
                "Money Transaction");
        emailIntent.putExtra(Intent.EXTRA_TEXT,
                "Receipt");
        emailIntent.setType("image/png");
        Uri myUri = Uri.parse("file://" + "");
        emailIntent.putExtra(Intent.EXTRA_STREAM, myUri);
        final List<ResolveInfo> activities = getPackageManager().queryIntentActivities(emailIntent, 0);
        List<DialogItem> appNames = new ArrayList<DialogItem>();

        for (ResolveInfo info : activities) {
            appNames.add(new DialogItem(info.loadLabel(getPackageManager()).toString(),
                    info.loadIcon(getPackageManager())));
        }
        final List<DialogItem> newItem = appNames;
        ShareAdapter adapter = new ShareAdapter(newItem, this, activities, DMRReciept.this, lv_share);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 5);
        lv_share.setLayoutManager(gridLayoutManager);
        lv_share.setAdapter(adapter);
    }*/
}
