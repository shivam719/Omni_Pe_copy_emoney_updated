package com.fintech.omnipe.UPIATM;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.fintech.omnipe.Api.Object.BalanceType;
import com.fintech.omnipe.Api.Response.NumberListResponse;
import com.fintech.omnipe.Auth.dto.LoginResponse;
import com.fintech.omnipe.Fragments.dto.OperatorList;
import com.google.gson.Gson;
import com.roundpay.shoppinglib.Util.DropdownDialog.DropDownDialog;
import com.roundpay.shoppinglib.Util.DropdownDialog.DropDownModel;
import com.fintech.omnipe.Api.Object.BalanceData;
import com.fintech.omnipe.Api.Object.WalletType;
import com.fintech.omnipe.Api.Response.BalanceResponse;
import com.fintech.omnipe.Api.Response.WalletTypeResponse;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.ApplicationConstant;
import com.fintech.omnipe.Util.GetLocation;
import com.fintech.omnipe.Util.UtilMethods;
import com.fintech.omnipe.usefull.CustomLoader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class UPIATMActivity extends AppCompatActivity {

    private View walletView;
    private TextView walletTv, walletAmountTv;
    private LinearLayout upiBtn;
    private ImageView arrowIv;
    private EditText amountEt;
    private CustomLoader loader;
    private LoginResponse mLoginDataResponse;
    private String deviceId, deviceSerialNum;
    private double latitude, longitude;

    private BalanceResponse balanceCheckResponse;
    private WalletTypeResponse mWalletTypeResponse;
    private final HashMap<String, Integer> walletIdMap = new HashMap<>();
    private final ArrayList<DropDownModel> mDropDownModels = new ArrayList<>();
    private DropDownDialog mDropDownDialog;
    private int selectedWalletId = 1;
    private int selectedWalletPos = 0;

    private String currentTransactionId;
    private final Handler statusHandler = new Handler(Looper.getMainLooper());
    private Runnable statusRunnable;
    private boolean isPolling = false;
    private int Oid;

    private Dialog qrDialog;
    private CountDownTimer qrTimer;
    private TextView dialogTimerTv, dialogStatusTv;
    private Bitmap qrBitmap;
    ArrayList<BalanceType> mBalanceTypes = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppThemeMaterial);
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        EdgeToEdge.enable(this);
        controller.setAppearanceLightStatusBars(true);
        setContentView(R.layout.activity_upiatmactivity);
        View clMain = findViewById(R.id.clMain);
        if (clMain != null) {
            ViewCompat.setOnApplyWindowInsetsListener(clMain, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

        initView();
        initData();
    }

    private void initView() {
        walletView = findViewById(R.id.walletView);
        walletTv = findViewById(R.id.walletTv);
        walletAmountTv = findViewById(R.id.walletAmountTv);
        arrowIv = findViewById(R.id.arrowIv);
        amountEt = findViewById(R.id.amountEt);
        upiBtn = findViewById(R.id.upiBtn);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("UPI ATM");
        }

        walletView.setOnClickListener(this::showPoupWindow);

        upiBtn.setOnClickListener(v -> {
            String amountStr = amountEt.getText().toString().trim();
            if (amountStr.isEmpty()) {
                amountEt.setError("Please enter amount");
                amountEt.requestFocus();
                return;
            }
            double amount = Double.parseDouble(amountStr);
            generateQr(amount);
        });

        setupQuickAmountButtons();
    }

    private void setupQuickAmountButtons() {
        int[] ids = {R.id.amt100, R.id.amt200, R.id.amt300, R.id.amt500, R.id.amt1000, R.id.amt2000};
        for (int id : ids) {
            TextView btn = findViewById(id);
            if (btn != null) {
                btn.setOnClickListener(v -> amountEt.setText(btn.getText().toString()));
            }
        }
    }

    private void initData() {
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        mDropDownDialog = new DropDownDialog(this);
        String LoginResponse = UtilMethods.INSTANCE.getLoginPref(this);
        mLoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
        deviceId = UtilMethods.INSTANCE.getIMEI(this);
        deviceSerialNum = UtilMethods.INSTANCE.getSerialNo(this);

        GetLocation getLocation = new GetLocation(this, loader);
        getLocation.startLocationUpdates((lat, lon) -> {
            latitude = lat;
            longitude = lon;
        });

        if (getIntent() != null && getIntent().getExtras() != null) {
            Oid = getIntent().getExtras().getInt("fromId", 0);
        }
        SharedPreferences prefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String response = prefs.getString(ApplicationConstant.INSTANCE.numberListPref, "");
        NumberListResponse  mOperatorList=new Gson().fromJson(response, NumberListResponse.class);
        if (mOperatorList != null) {
            ArrayList<OperatorList> operatorsList = mOperatorList.getData().getOperators();
            if (operatorsList != null && !operatorsList.isEmpty()) {
                for (OperatorList op : operatorsList) {
                    if (op.isActive() && op.getOpType() == Oid) {
                        Oid = op.getOid();
                        break;
                    }

                }
            }
        }
        showWalletListPopupWindow();
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
    void setWalletIds() {
        if (mWalletTypeResponse != null && mWalletTypeResponse.getWalletTypes() != null && !mWalletTypeResponse.getWalletTypes().isEmpty()) {
            int count = 0;
            for (WalletType object : mWalletTypeResponse.getWalletTypes()) {
                if (object.getInFundProcess()) {
                    for (int i = 0; i < mDropDownModels.size(); i++) {
                        BalanceType mBalanceType = ((BalanceType) mDropDownModels.get(i).getDataObject());
                        if (mBalanceType.getName().contains(object.getName())) {
                            walletIdMap.put(mBalanceType.getName(), object.getId());
                            if (count == 0) {
                                walletTv.setText(mBalanceType.getName());
                                walletAmountTv.setText(String.format("₹ %s", mBalanceType.getAmount()));
                                selectedWalletId = object.getId();
                            }
                            count++;
                        }
                    }
                }
            }
        } else {
            mWalletTypeResponse = new Gson().fromJson(UtilMethods.INSTANCE.getWalletType(this), WalletTypeResponse.class);
            if (mWalletTypeResponse != null && mWalletTypeResponse.getWalletTypes() != null && mWalletTypeResponse.getWalletTypes().size() > 0) {
                setWalletIds();
            } else {
                UtilMethods.INSTANCE.WalletType(this, loader, new UtilMethods.ApiCallBack() {
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

    private void showPoupWindow(View anchor) {
        if (!mDropDownModels.isEmpty()) {
            mDropDownDialog.showDropDownPopup(anchor, selectedWalletPos, mDropDownModels, (clickPosition, value, object) -> {
                selectedWalletPos = clickPosition;
                walletTv.setText(String.valueOf(value));
                walletAmountTv.setText(String.format("₹ %s", ((BalanceType) object).getAmount()));
                Integer id = walletIdMap.get(String.valueOf(value));
                if (id != null) {
                    selectedWalletId = id;
                }
            });
        }
    }

    private void generateQr(double amount) {
        UtilMethods.INSTANCE.GenerateUpiAtmQr(this, latitude, longitude, amount, loader, mLoginDataResponse, deviceId, deviceSerialNum, Oid, object -> {
            UpiAtmResponse response = (UpiAtmResponse) object;
            if (response.getData() != null) {
                currentTransactionId = response.getData().getTransactionId();
                showQrDialog(response.getData().getQrString(), response.getData().getExpireAt(), amount);
                startStatusPolling();
            }
        });
    }

    private void showQrDialog(String base64Qr, String expireAt, double amount) {
        qrDialog = new Dialog(this);
        qrDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        qrDialog.setContentView(R.layout.dialog_qr_upi_atm);
        qrDialog.setCancelable(false);

        if (qrDialog.getWindow() != null) {
            qrDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            qrDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        }

        ImageView dialogQrIv = qrDialog.findViewById(R.id.qrIv);
        dialogTimerTv = qrDialog.findViewById(R.id.timerTv);
        dialogStatusTv = qrDialog.findViewById(R.id.statusTv);
        TextView payViaUpiBtn = qrDialog.findViewById(R.id.payViaUpiBtn);
        TextView amountTv = qrDialog.findViewById(R.id.amountTv);
        ImageView closeIv = qrDialog.findViewById(R.id.closeIv);

        if (amountTv != null) {
            amountTv.setText(String.format(Locale.getDefault(), "Amount: ₹ %.2f", amount));
        }

        try {
            byte[] decodedString = Base64.decode(base64Qr, Base64.DEFAULT);
            qrBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            dialogQrIv.setImageBitmap(qrBitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (payViaUpiBtn != null) {
            payViaUpiBtn.setOnClickListener(v -> {
                if (qrBitmap != null) {
                    shareQr(qrBitmap);
                }
            });
        }

        closeIv.setOnClickListener(v -> {
            stopStatusPolling();
            if (qrTimer != null) qrTimer.cancel();
            qrDialog.dismiss();
        });

        startQrTimer(expireAt);
        qrDialog.show();
    }

    private void startQrTimer(String expireAt) {

        if (qrTimer != null) {
            qrTimer.cancel();
            qrTimer = null;
        }

        if (expireAt == null || expireAt.trim().isEmpty()) {
            handleQrExpired();
            return;
        }

        long expiryTime = parseExpiryTime(expireAt);

        if (expiryTime <= 0) {
            Log.e("QR_TIMER", "Invalid expireAt: " + expireAt);

            if (dialogTimerTv != null) {
                dialogTimerTv.setVisibility(View.GONE);
            }

            return;
        }

        long remainingTime = expiryTime - System.currentTimeMillis();

        Log.d("QR_TIMER", "expireAt = " + expireAt);
        Log.d("QR_TIMER", "remaining = " + remainingTime + " ms");

        if (remainingTime <= 0) {
            handleQrExpired();
            return;
        }

        if (dialogTimerTv != null) {
            dialogTimerTv.setVisibility(View.VISIBLE);
        }

        qrTimer = new CountDownTimer(remainingTime, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {

                long totalSeconds = millisUntilFinished / 1000;

                long minutes = totalSeconds / 60;
                long seconds = totalSeconds % 60;

                if (dialogTimerTv != null) {
                    dialogTimerTv.setText(String.format(Locale.getDefault(), "Expires in: %02d:%02d", minutes, seconds));
                }
            }

            @Override
            public void onFinish() {
                qrTimer = null;
                handleQrExpired();
            }

        }.start();
    }

    /**
     * Supports multiple common API date-time formats.
     */
    private long parseExpiryTime(String expireAt) {

        String value = expireAt.trim();

        // Remove unnecessary spaces
        value = value.replace(" ", " ");

        String[] formats = {

                // 2026-08-20 11:30:00
                "yyyy-MM-dd HH:mm:ss",

                // 2026-08-20 11:30:00.000
                "yyyy-MM-dd HH:mm:ss.SSS",

                // 2026-08-20T11:30:00
                "yyyy-MM-dd'T'HH:mm:ss",

                // 2026-08-20T11:30:00.000
                "yyyy-MM-dd'T'HH:mm:ss.SSS",

                // 20-08-2026 11:30:00
                "dd-MM-yyyy HH:mm:ss",

                // 20/08/2026 11:30:00
                "dd/MM/yyyy HH:mm:ss",

                // 2026/08/20 11:30:00
                "yyyy/MM/dd HH:mm:ss"};

        /*
         * ISO formats having timezone/offset:
         *
         * 2026-08-20T11:30:00Z
         * 2026-08-20T11:30:00+05:30
         */
        try {

            Instant instant = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                instant = Instant.parse(value);
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                return instant.toEpochMilli();
            }

        } catch (Exception ignored) {
        }

        try {

            OffsetDateTime offsetDateTime = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                offsetDateTime = OffsetDateTime.parse(value, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                return offsetDateTime.toInstant().toEpochMilli();
            }

        } catch (Exception ignored) {
        }

        /*
         * Local date-time formats.
         * If API doesn't provide timezone,
         * device timezone will be used.
         */
        for (String format : formats) {

            try {

                SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);

                sdf.setLenient(false);

                Date date = sdf.parse(value);

                if (date != null) {
                    return date.getTime();
                }

            } catch (Exception ignored) {
            }
        }

        return -1;
    }


    /**
     * QR expiry handling.
     */
    @SuppressLint("SetTextI18n")
    private void handleQrExpired() {

        if (qrTimer != null) {
            qrTimer.cancel();
            qrTimer = null;
        }

        // Stop API status polling
        stopStatusPolling();

        if (dialogTimerTv != null) {

            dialogTimerTv.setVisibility(View.VISIBLE);

            dialogTimerTv.setText("QR Expired");

            dialogTimerTv.setTextColor(ContextCompat.getColor(UPIATMActivity.this, android.R.color.holo_red_dark));
        }

        if (!isFinishing() && !isDestroyed()) {

            Toast.makeText(UPIATMActivity.this, "QR Code Expired. Please generate again.", Toast.LENGTH_LONG).show();
        }

        new Handler(Looper.getMainLooper()).postDelayed(() -> {

            if (qrDialog != null && qrDialog.isShowing()) {
                qrDialog.dismiss();
            }

        }, 2000);
    }


    private void shareQr(Bitmap bitmap) {
        try {
            File imagesFolder = new File(getFilesDir(), "images");
            if (!imagesFolder.exists()) {
                imagesFolder.mkdirs();
            }
            File file = new File(imagesFolder, "qr_code.png");
            FileOutputStream stream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            stream.flush();
            stream.close();

            Uri uri = FileProvider.getUriForFile(this, getPackageName() + ".smart-image-picker-provider", file);

            if (uri != null) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.putExtra(Intent.EXTRA_STREAM, uri);
                intent.setType("image/png");
                intent.putExtra(Intent.EXTRA_SUBJECT, "UPI QR Code");
                intent.putExtra(Intent.EXTRA_TEXT, "Scan this QR code to pay.");
                startActivity(Intent.createChooser(intent, "Share QR via"));
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error sharing QR code", Toast.LENGTH_SHORT).show();
        }
    }



    private void startStatusPolling() {
        if (isPolling) return;
        isPolling = true;
        statusRunnable = new Runnable() {
            @Override
            public void run() {
                if (!isPolling) return;
                checkStatus();
                statusHandler.postDelayed(this, 5000);
            }
        };
        statusHandler.post(statusRunnable);
    }

    private void stopStatusPolling() {
        isPolling = false;
        if (statusRunnable != null) {
            statusHandler.removeCallbacks(statusRunnable);
        }
    }

    private void checkStatus() {
        UtilMethods.INSTANCE.CheckUpiAtmStatus(this, currentTransactionId, mLoginDataResponse, deviceId, deviceSerialNum, Oid, object -> {
            UpiAtmResponse response = (UpiAtmResponse) object;
            if (response.getData() != null) {
                int status = response.getData().getStatus();
                if (status == 2) { // Success
                    stopStatusPolling();
                    if (qrTimer != null) qrTimer.cancel();
                    if (qrDialog != null && qrDialog.isShowing()) {
                        dialogStatusTv.setText("Payment Successful!");
                        dialogStatusTv.setTextColor(ContextCompat.getColor(this, R.color.green));
                        new Handler(Looper.getMainLooper()).postDelayed(() -> {
                            if (qrDialog != null && qrDialog.isShowing()) {
                                qrDialog.dismiss();
                            }
                            openReceipt(response.getData());
                        }, 2000);
                    }else {
                        openReceipt(response.getData());
                    }
                    UtilMethods.INSTANCE.Successful(UPIATMActivity.this, "Payment Successful!");
                    UtilMethods.INSTANCE.BalancecheckNew(this, loader, null, null, new UtilMethods.ApiCallBack() {
                        @Override
                        public void onSucess(Object object) {
                            balanceCheckResponse = (BalanceResponse) object;
                            if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null) {
                                showWalletListPopupWindow();
                            }

                        }
                    });
                } else if (status == 3) {
                    stopStatusPolling();
                    if (qrTimer != null) qrTimer.cancel();
                    if (qrDialog != null && qrDialog.isShowing()) {
                        String errorMsg = response.getMsg() != null ? response.getMsg() : "Payment Failed";
                        dialogStatusTv.setText(errorMsg);
                        dialogStatusTv.setTextColor(ContextCompat.getColor(this, R.color.red));
                        new Handler(Looper.getMainLooper()).postDelayed(() -> {
                            if (qrDialog != null && qrDialog.isShowing()) {
                                qrDialog.dismiss();
                            }
                            openReceipt(response.getData());
                        }, 3000);
                    } else {
                        openReceipt(response.getData());
                    }
                    UtilMethods.INSTANCE.Error(UPIATMActivity.this, response.getMsg());
                } else if (status == 1) {
                    if (qrDialog != null && qrDialog.isShowing()) {
                        dialogStatusTv.setText("Waiting for payment...");
                        dialogStatusTv.setTextColor(ContextCompat.getColor(this, R.color.yellow_dark));
                    }else{
                        openReceipt(response.getData());
                    }
                }
            }
        });
    }

    private void openReceipt(UpiAtmResponse.UpiAtmData data) {
        Intent intent = new Intent(this, UPIATMReceiptActivity.class);
        intent.putExtra("data", data);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        stopStatusPolling();
        if (qrTimer != null) qrTimer.cancel();
        if (qrDialog != null && qrDialog.isShowing()) qrDialog.dismiss();
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
