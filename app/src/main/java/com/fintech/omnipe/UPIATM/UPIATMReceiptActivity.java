package com.fintech.omnipe.UPIATM;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fintech.omnipe.AEPS.FingPay.Adapter.ReceiptDetailListAdapter;
import com.fintech.omnipe.AEPS.FingPay.dto.ReceiptObject;
import com.fintech.omnipe.Api.Response.AppUserListResponse;
import com.fintech.omnipe.Auth.dto.LoginResponse;
import com.google.gson.Gson;

import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.UtilMethods;
import com.roundpay.emoneylib.Utils.Utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * UPI ATM Receipt screen.
 * Reference: NSDLStatusActivity
 * Every field is null / empty checked before being shown — agar data
 * missing hoga toh us row ko silently skip kar diya jaayega, activity
 * kabhi crash nahi hogi.
 */
public class UPIATMReceiptActivity extends AppCompatActivity {

    private AppCompatImageView operatorImage;
    private TextView tvTxnStatus;
    private TextView tvOperatorName, txnId;
    private RecyclerView recyclerView;
    private TextView outletDetail;
    private TextView companyNameTv, address;
    private View btShare, btWhatsapp, btRepeat;
    private View headerLayout;
    private LinearLayout shareView;
    private View rlCancel;
    private ImageView statusIcon;

    private boolean isFailed;
    private AppCompatTextView companyName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppThemeMaterial);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upiatmreceipt);
        getIds();
        setUiData();
    }

    private void getIds() {
        headerLayout = findViewById(R.id.headerLayout);
        outletDetail = findViewById(R.id.outletDetail);
        statusIcon = findViewById(R.id.statusIcon);
        shareView = findViewById(R.id.shareView);
        operatorImage = findViewById(R.id.operatorImage);
        rlCancel = findViewById(R.id.rl_cancel);
        txnId = findViewById(R.id.txnId);
        tvTxnStatus = findViewById(R.id.tv_txstatus);
        companyNameTv = findViewById(R.id.companyName);
        address = findViewById(R.id.address);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tvOperatorName = findViewById(R.id.tv_operatorname);
        btShare = findViewById(R.id.bt_share);
        btWhatsapp = findViewById(R.id.bt_whatsapp);
        btRepeat = findViewById(R.id.bt_repeat);

        if (rlCancel != null) {
            rlCancel.setOnClickListener(v -> {
                setResult(RESULT_OK);
                finish();
            });
        }

        if (btShare != null) btShare.setOnClickListener(v -> shareit(false));

        if (btRepeat != null) {
            btRepeat.setOnClickListener(v -> {
                setResult(RESULT_CANCELED);
                finish();
            });
        }

        if (btWhatsapp != null) {
            if (UtilMethods.INSTANCE.isPackageInstalled("com.whatsapp", getPackageManager())) {
                btWhatsapp.setVisibility(View.VISIBLE);
                btWhatsapp.setOnClickListener(v -> shareit(true));
            } else {
                btWhatsapp.setVisibility(View.GONE);
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private void setUiData() {
        setOutletDetail();
        setCompanyDetail(UtilMethods.INSTANCE.getCompanyProfileDetails(this));
        // ---- UPI ATM logo instead of Glide operator icon ----
        if (operatorImage != null) {
            operatorImage.setVisibility(View.VISIBLE);
            operatorImage.setImageResource(R.drawable.ic_upi_atm);
        }
        if (tvOperatorName != null) {
            tvOperatorName.setText("UPI ATM");
        }
        ArrayList<ReceiptObject> mReceiptObjects = new ArrayList<>();

        UpiAtmResponse.UpiAtmData detailsItem = null;
        Intent intent = getIntent();
        if (intent != null) {
            Object serial = intent.getSerializableExtra("data");
            if (serial instanceof UpiAtmResponse.UpiAtmData) {
                detailsItem = (UpiAtmResponse.UpiAtmData) serial;
            }
        }
        String timeStamp = (intent != null) ? intent.getStringExtra("timeStamp") : null;

        if (detailsItem == null) {
            // No data at all — show a generic failed / empty state and bail out safely
            setStatusUi("Status Unavailable", R.drawable.ic_cross_mark, R.color.color_red);
            if (btRepeat != null) btRepeat.setVisibility(View.VISIBLE);
            recyclerView.setAdapter(new ReceiptDetailListAdapter(mReceiptObjects, this));
            return;
        }

        int status = detailsItem.getStatus();

        // ---- Status text / icon / color ----
        if (status == 2) { // Success
            setStatusUi("Payment Successful", R.drawable.ic_check_mark, R.color.green);
            isFailed = false;
        } else if (status == 1) { // Pending
            setStatusUi("Payment Pending", R.drawable.ic_pending, R.color.color_orange);
            isFailed = false;
        } else { // Failed / anything else
            String liveId = safe(detailsItem.getLiveId());
            String statusText = "Payment Failed";
            if (!liveId.isEmpty()) {
                statusText = statusText + "\n(" + liveId + ")";
            }
            setStatusUi(statusText, R.drawable.ic_cross_mark, R.color.color_red);
            isFailed = true;
        }

        if (btRepeat != null) {
            btRepeat.setVisibility(isFailed ? View.VISIBLE : View.GONE);
        }

        // ---- Receipt rows — null / empty safe ----
        addIfNotEmpty(mReceiptObjects, "Payer Name", detailsItem.getPayerName());
        addIfNotEmpty(mReceiptObjects, "Payer VPA", detailsItem.getPayerVPA());
        addIfNotEmpty(mReceiptObjects, "Payer Mobile", detailsItem.getPayerMobile());

        if (detailsItem.getAmount() > 0) {
            mReceiptObjects.add(new ReceiptObject("Amount",
                    Utility.INSTANCE.formatedAmountWithRupees(String.valueOf(detailsItem.getAmount()))));
        }

        addIfNotEmpty(mReceiptObjects, "Transaction Id", detailsItem.getTransactionId());

        addIfNotEmpty(mReceiptObjects,
                isFailed ? "Reason / Live Id" : "Bank Rrn",
                detailsItem.getLiveId());

//        if (!isEmpty(timeStamp)) {
//            mReceiptObjects.add(new ReceiptObject("Transaction Time", Utility.INSTANCE.formatedDate3(timeStamp)));
//        } else {
//            addIfNotEmpty(mReceiptObjects, "Expires At", detailsItem.getExpireAt());
//        }

        // ---- Ref no on top ----
        String txnIdVal = safe(detailsItem.getTransactionId());
        if (txnId != null) {
            if (txnIdVal.length() > 4) {
                txnId.setText("Ref:- " + txnIdVal);
                txnId.setVisibility(View.VISIBLE);
            } else {
                txnId.setVisibility(View.GONE);
            }
        }

        recyclerView.setAdapter(new ReceiptDetailListAdapter(mReceiptObjects, this));
    }

    private void setStatusUi(String text, int iconRes, int colorRes) {
        if (tvTxnStatus != null) {
            tvTxnStatus.setText(text);
        }
        if (statusIcon != null) {
            statusIcon.setImageResource(iconRes);
        }
        if (headerLayout != null) {
            headerLayout.setBackgroundColor(ContextCompat.getColor(this, colorRes));
        }
    }

    private void addIfNotEmpty(ArrayList<ReceiptObject> list, String label, String value) {
        if (!isEmpty(value)) {
            list.add(new ReceiptObject(label, value.trim()));
        }
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }

    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    void setCompanyDetail(AppUserListResponse companyData) {

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

    // ---------------- Share / WhatsApp (same as NSDLStatusActivity) ----------------

    public void shareit(boolean isWhatsapp) {
        if (shareView == null || shareView.getWidth() == 0 || shareView.getHeight() == 0) return;
        try {
            Bitmap myBitmap = Bitmap.createBitmap(shareView.getWidth(), shareView.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas c = new Canvas(myBitmap);
            shareView.layout(0, 0, shareView.getWidth(), shareView.getHeight());
            shareView.draw(c);
            saveBitmap(myBitmap, isWhatsapp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveBitmap(Bitmap bitmap, boolean isWhatsapp) {
        if (bitmap == null) return;
        try {
            if (Build.VERSION.SDK_INT >= 30) {
                ContentValues values = contentValues();
                values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/" + getString(R.string.app_name));
                values.put(MediaStore.Images.Media.IS_PENDING, true);

                Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                if (uri != null) {
                    saveImageToStream(bitmap, getContentResolver().openOutputStream(uri));
                    values.put(MediaStore.Images.Media.IS_PENDING, false);
                    getContentResolver().update(uri, values, null, null);
                    if (isWhatsapp) openWhatsapp(uri);
                    else sendMail(uri);
                }
            } else {
                File directory = new File(Environment.getExternalStorageDirectory().toString() + "/Pictures/" + getString(R.string.app_name));
                if (!directory.exists() && !directory.mkdirs()) return;

                String fileName = System.currentTimeMillis() + ".png";
                File file = new File(directory, fileName);
                saveImageToStream(bitmap, new FileOutputStream(file));

                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
                Uri pathUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                if (isWhatsapp) openWhatsapp(pathUri);
                else sendMail(pathUri);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
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
        if (outputStream == null) return;
        try {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (IOException ignored) {
            }
        }
    }

    public void sendMail(Uri myUri) {
        if (myUri == null) return;
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "UPI ATM Receipt");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Receipt");
        emailIntent.setType("image/png");
        emailIntent.putExtra(Intent.EXTRA_STREAM, myUri);
        try {
            startActivity(Intent.createChooser(emailIntent, "Share via..."));
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    void openWhatsapp(Uri myUri) {
        if (myUri == null) return;
        try {
            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, "UPI ATM Receipt");
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
        setResult(RESULT_OK);
        super.onBackPressed();
    }
}