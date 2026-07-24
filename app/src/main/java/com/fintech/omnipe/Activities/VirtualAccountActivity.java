package com.fintech.omnipe.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fintech.omnipe.UPIPayment.dto.UserQRInfo;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import com.fintech.omnipe.Api.Response.AppUserListResponse;
import com.fintech.omnipe.Auth.dto.LoginResponse;
import com.fintech.omnipe.BuildConfig;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.ApplicationConstant;
import com.fintech.omnipe.Util.UtilMethods;
import com.fintech.omnipe.usefull.CustomLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class VirtualAccountActivity extends AppCompatActivity {

    TextView custCare, upiId;
    ImageView qrcode;
    private CustomLoader loader;
    TextView detail;
    LinearLayout shareView;
    private int REQUEST_PERMISSIONS = 1234;
    private Snackbar mSnackBar;
    String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    boolean isDownload;
    View btnView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_vertual_account);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        detail = findViewById(R.id.detail);
        custCare = findViewById(R.id.custCare);
        upiId = findViewById(R.id.upiId);
        qrcode = findViewById(R.id.qrcode);
        shareView = findViewById(R.id.shareView);
        btnView = findViewById(R.id.btnView);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        String LoginResponse = UtilMethods.INSTANCE.getLoginPref(this);
        LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
        TextView OutletName = findViewById(R.id.OutletName);
        OutletName.setText(LoginDataResponse.getData().getName() + "");

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
        requestOptions.skipMemoryCache(true);
        requestOptions.error(R.drawable.nodata);
        Glide.with(this).load(ApplicationConstant.INSTANCE.baseQrImageUrl + LoginDataResponse.getData().getUserID() +
                "&appid=" + ApplicationConstant.INSTANCE.APP_ID + "&imei=" + UtilMethods.INSTANCE.getIMEI(this) + "&regKey=&version=" + BuildConfig.VERSION_NAME +
                "&serialNo=" + UtilMethods.INSTANCE.getSerialNo(this) + "&sessionID=" + LoginDataResponse.getData().getSessionID() +
                "&session=" + LoginDataResponse.getData().getSession() + "&loginTypeID=" + LoginDataResponse.getData().getLoginTypeID())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        btnView.setVisibility(View.VISIBLE);
                        return false;
                    }
                })
                .apply(requestOptions).into(qrcode);

        AppUserListResponse companyProfileData = new Gson().fromJson(UtilMethods.INSTANCE.getCompanyProfile(this), AppUserListResponse.class);
        if (companyProfileData != null && companyProfileData.getCompanyProfile() != null) {
            String value = "";
            if (companyProfileData.getCompanyProfile().getCustomerCareMobileNos() != null && !companyProfileData.getCompanyProfile().getCustomerCareMobileNos().isEmpty()) {
                value = " -" + companyProfileData.getCompanyProfile().getCustomerCareMobileNos();
            }
            if (companyProfileData.getCompanyProfile().getCustomerPhoneNos() != null && !companyProfileData.getCompanyProfile().getCustomerPhoneNos().isEmpty()) {
                value = value + " -" + companyProfileData.getCompanyProfile().getCustomerPhoneNos();
            }
            if (companyProfileData.getCompanyProfile().getCustomerWhatsAppNos() != null && !companyProfileData.getCompanyProfile().getCustomerWhatsAppNos().isEmpty()) {
                value = value + " -" + companyProfileData.getCompanyProfile().getCustomerWhatsAppNos();
            }

            custCare.setText("CUSTOMER CARE" + value);
        } else {
            custCare.setVisibility(View.GONE);
        }
        findViewById(R.id.download).setOnClickListener(v -> {
            isDownload = true;
            shareIt();
        });

        findViewById(R.id.share).setOnClickListener(v -> {
            isDownload = false;
            shareIt();
        });

        UtilMethods.INSTANCE.GetVADetails(this, loader, object -> {
            UserQRInfo mUserQRInfo = (UserQRInfo) object;
            if (mUserQRInfo != null) {
                detail.setText("BANK : " + mUserQRInfo.getBankName() + "\n" +
                        "IFSC : " + mUserQRInfo.getIfsc() + "\n" +
                        "Virtual Account : " + mUserQRInfo.getVirtualAccount());


            } else {
                detail.setVisibility(View.GONE);
            }
        });
    }

    public void shareIt() {
        Bitmap myBitmap = Bitmap.createBitmap(shareView.getWidth(), shareView.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(myBitmap);
        shareView.layout(0, 0, shareView.getWidth(), shareView.getHeight());
        shareView.draw(c);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            saveBitmap(isDownload, myBitmap);
        }else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_PERMISSIONS);
            } else {
                saveBitmap(isDownload, myBitmap);
            }
        }

    }

    public void saveBitmap(boolean isDownload, Bitmap bitmap) {

        File filePath = new File(Environment.getExternalStorageDirectory().toString() + "/" + getString(R.string.app_name));

        if (!filePath.exists()) {
            filePath.mkdir();
        }
        File imagePath = new File(filePath + "/Virtual_Account.jpg");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            if (isDownload) {
                Toast.makeText(this, "Successfully Download", Toast.LENGTH_SHORT).show();
                MediaScannerConnection.scanFile(this, new String[]{imagePath.getPath()}, new String[]{"image/jpeg"}, null);
            } else {
                sendMail(imagePath.toString());
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
    }

    public void sendMail(String path) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,
                "Virtual Account");
        emailIntent.putExtra(Intent.EXTRA_TEXT,
                "Virtual Account");
        emailIntent.setType("image/png");
        Uri myUri = Uri.parse("file://" + path);
        emailIntent.putExtra(Intent.EXTRA_STREAM, myUri);
        startActivity(Intent.createChooser(emailIntent, "Share via..."));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSIONS) {

            int permissionCheck = PackageManager.PERMISSION_GRANTED;
            for (int permission : grantResults) {
                permissionCheck = permissionCheck + permission;
            }
            if ((grantResults.length > 0) && permissionCheck == PackageManager.PERMISSION_GRANTED) {

                shareIt();
            } else {
                showWarningSnack(R.string.str_ShowOnPermisstionDenied, "Enable", true);
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }


    void showWarningSnack(int stringId, String btn, final boolean isForSetting) {
        if (mSnackBar != null && mSnackBar.isShown()) {
            return;
        }
        mSnackBar = Snackbar.make(findViewById(android.R.id.content), stringId,
                Snackbar.LENGTH_INDEFINITE).setAction(btn,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isForSetting) {
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            intent.addCategory(Intent.CATEGORY_DEFAULT);
                            intent.setData(Uri.parse("package:" + getPackageName()));
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                            startActivity(intent);
                        } else {
                            ActivityCompat.requestPermissions(VirtualAccountActivity.this, PERMISSIONS, REQUEST_PERMISSIONS);
                        }

                    }
                });

        mSnackBar.setActionTextColor(getResources().getColor(R.color.colorPrimary));
        TextView mainTextView = (TextView) (mSnackBar.getView()).
                findViewById(R.id.snackbar_text);
        mainTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen._12sdp));
        mainTextView.setMaxLines(4);
        mSnackBar.show();
    }
}
