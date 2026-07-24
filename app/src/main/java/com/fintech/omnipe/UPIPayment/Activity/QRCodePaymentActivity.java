package com.fintech.omnipe.UPIPayment.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.fintech.omnipe.Api.Response.AppUserListResponse;
import com.fintech.omnipe.Api.Response.BasicResponse;
import com.fintech.omnipe.Auth.dto.LoginResponse;
import com.fintech.omnipe.BuildConfig;
import com.fintech.omnipe.UPIPayment.dto.MapQRToUserRequest;
import com.fintech.omnipe.UPIPayment.dto.UserQRInfo;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.ApiClient;
import com.fintech.omnipe.Util.ApplicationConstant;
import com.fintech.omnipe.Util.CustomAlertDialog;
import com.fintech.omnipe.Util.EndPointInterface;
import com.fintech.omnipe.Util.UtilMethods;
import com.fintech.omnipe.usefull.CustomLoader;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import retrofit2.Call;
import retrofit2.Callback;

public class QRCodePaymentActivity extends AppCompatActivity {

    TextView custCare, upiId, OutletName,detail;
    ImageView qrcode, cameraView,appLogoIv;
    private CustomLoader loader;
    LinearLayout shareView;
    private int REQUEST_PERMISSIONS = 1234;
    private Snackbar mSnackBar;
    String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    boolean isDownload;
    View btnView;
    int INTENT_SCAN = 4343;
    private LoginResponse LoginDataResponse;
    private boolean isECollectEnable;
    private View titleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_qr_code_payment);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        custCare = findViewById(R.id.custCare);
        upiId = findViewById(R.id.upiId);
        qrcode = findViewById(R.id.qrcode);
        cameraView = findViewById(R.id.cameraView);
        shareView = findViewById(R.id.shareView);
        btnView = findViewById(R.id.btnView);
        OutletName = findViewById(R.id.OutletName);
        appLogoIv = findViewById(R.id.appLogoIv);
        detail = findViewById(R.id.detail);
        titleView = findViewById(R.id.titleView);
        cameraView.setVisibility(View.GONE);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        String mLoginResponse = UtilMethods.INSTANCE.getLoginPref(this);
        LoginDataResponse = new Gson().fromJson(mLoginResponse, LoginResponse.class);
        OutletName.setText(LoginDataResponse.getData().getName() + "");
        isECollectEnable=getIntent().getBooleanExtra("isECollectEnable",false);
        boolean isQRMappedToUser = getIntent().getBooleanExtra("isQRMappedToUser", false);
        boolean isBulkQRGeneration = getIntent().getBooleanExtra("isBulkQRGeneration", false);

        UtilMethods.INSTANCE.setAppLogoIconUI(this,appLogoIv);

        if (isECollectEnable) {
            UtilMethods.INSTANCE.GetVADetails(this, loader,  object -> {
                        UserQRInfo mUserQRInfo = (UserQRInfo) object;
                        if (mUserQRInfo != null) {
                            titleView.setVisibility(View.GONE);
                            detail.setVisibility(View.VISIBLE);
                            detail.setText("BANK : " + mUserQRInfo.getBankName() + "\n" +
                                    "IFSC : " + mUserQRInfo.getIfsc() + "\n" +
                                    "Virtual Account : " + mUserQRInfo.getVirtualAccount());


                        } else {
                            titleView.setVisibility(View.VISIBLE);
                            detail.setVisibility(View.GONE);
                        }
                    });
        } else {
            if (isBulkQRGeneration && !isQRMappedToUser) {
                cameraView.setVisibility(View.VISIBLE);
            }else {
                cameraView.setVisibility(View.GONE);
            }
            titleView.setVisibility(View.VISIBLE);
            detail.setVisibility(View.GONE);
        }

        setQRCode();
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
        }
        else {
            custCare.setVisibility(View.GONE);
        }

        cameraView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(QRCodePaymentActivity.this, QRScannerActivity.class), INTENT_SCAN);
            }
        });
        findViewById(R.id.download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDownload = true;
                shareIt();
            }
        });

        findViewById(R.id.share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDownload = false;
                shareIt();
            }
        });

    }
    public void shareIt() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_PERMISSIONS);

            } else {
                getBitmap();
            }
        } else {
            getBitmap();
        }
    }

    private void getBitmap() {
        shareView.setDrawingCacheEnabled(true);
        Bitmap myBitmap = shareView.getDrawingCache();
        saveImage(isDownload, myBitmap);
        shareView.setDrawingCacheEnabled(false);
    }

    private void saveImage(boolean isDownload, Bitmap bitmap) {
        if (android.os.Build.VERSION.SDK_INT >= 30) {
            ContentValues values = contentValues();
            values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/" + getString(R.string.app_name));
            values.put(MediaStore.Images.Media.IS_PENDING, true);
            values.put(MediaStore.Images.Media.DISPLAY_NAME, "QR_CODE");

            Uri uri = this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            if (uri != null) {
                try {
                    saveImageToStream(bitmap, this.getContentResolver().openOutputStream(uri));
                    values.put(MediaStore.Images.Media.IS_PENDING, false);
                    this.getContentResolver().update(uri, values, null, null);
                    if (isDownload) {
                        Toast.makeText(this, "Successfully Download", Toast.LENGTH_SHORT).show();
                        MediaScannerConnection.scanFile(this, new String[]{uri.getPath()}, new String[]{"image/png"}, null);
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
            String fileName = "QR_CODE.png";
            File file = new File(directory, fileName);
            try {
                saveImageToStream(bitmap, new FileOutputStream(file));
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
                this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                if (isDownload) {
                    Toast.makeText(this, "Successfully Download", Toast.LENGTH_SHORT).show();
                    MediaScannerConnection.scanFile(this, new String[]{file.getPath()}, new String[]{"image/png"}, null);
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
                "QR CODE");
        emailIntent.putExtra(Intent.EXTRA_TEXT,
                "QR CODE");
        emailIntent.setType("image/png");
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

                getBitmap();
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
                            ActivityCompat.requestPermissions(QRCodePaymentActivity.this, PERMISSIONS, REQUEST_PERMISSIONS);
                        }

                    }
                });

        mSnackBar.setActionTextColor(getResources().getColor(R.color.colorPrimary));
        TextView mainTextView = (TextView) (mSnackBar.getView()).
                findViewById(com.google.android.material.R.id.snackbar_text);
        mainTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen._12sdp));
        mainTextView.setMaxLines(4);
        mSnackBar.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == INTENT_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                String codeValue = data.getStringExtra("codeValue");
                Uri mUri = Uri.parse(codeValue);
                String pa = mUri.getQueryParameter("pa");
                String pn = mUri.getQueryParameter("pn");
                String tr = mUri.getQueryParameter("tr");
                String mc = mUri.getQueryParameter("mc");
                if (pa != null && mc != null && tr != null && pn != null) {

                    new CustomAlertDialog(this, true).WarningWithDoubleBtnCallBack(pn + " : " + pa, "QR Detais", "Link", true, new CustomAlertDialog.DialogCallBack() {
                        @Override
                        public void onPositiveClick() {
                            MapQRToUser(QRCodePaymentActivity.this,codeValue,loader);
                        }

                        @Override
                        public void onNegativeClick() {

                        }
                    });
                }
            }


        }
    }


    public void MapQRToUser(final Activity context, String qrData, final CustomLoader loader) {
        try {
            loader.show();
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<BasicResponse> call = git.MapQRToUser(new MapQRToUserRequest(qrData,
                    LoginDataResponse.getData().getUserID() + "", LoginDataResponse.getData().getLoginTypeID() + "",
                    ApplicationConstant.INSTANCE.APP_ID,
                    UtilMethods.INSTANCE.getIMEI(context),
                    "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(context),
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<BasicResponse>() {

                @Override
                public void onResponse(Call<BasicResponse> call, retrofit2.Response<BasicResponse> response) {

                    if (loader.isShowing())
                        loader.dismiss();

                    if (response.body() != null) {
                        if (response.body().getStatuscode() == 1) {
                            UtilMethods.INSTANCE.Successful(context, response.body().getMsg() + "");
                            cameraView.setVisibility(View.GONE);
                           setQRCode();
                        } else {
                            if (response.body().getVersionValid() == false) {
                                UtilMethods.INSTANCE.versionDialog(context);
                            } else {
                                UtilMethods.INSTANCE.Error(context, response.body().getMsg() + "");
                            }
                        }

                    }
                }

                @Override
                public void onFailure(Call<BasicResponse> call, Throwable t) {

                    if (loader.isShowing())
                        loader.dismiss();

                    UtilMethods.INSTANCE.apiFailureError(context,t);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            UtilMethods.INSTANCE.Error(context, e.getMessage());
        }

    }


   void setQRCode(){
       RequestOptions requestOptions = new RequestOptions();
       requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
       requestOptions.skipMemoryCache(true);
       requestOptions.placeholder(R.drawable.nodata);
       requestOptions.error(R.drawable.nodata);
       Glide.with(QRCodePaymentActivity.this).load(ApplicationConstant.INSTANCE.baseQrImageUrl + LoginDataResponse.getData().getUserID() +
               "&appid=" + ApplicationConstant.INSTANCE.APP_ID + "&imei=" + UtilMethods.INSTANCE.getIMEI(QRCodePaymentActivity.this) + "&regKey=&version=" + BuildConfig.VERSION_NAME +
               "&serialNo=" + UtilMethods.INSTANCE.getSerialNo(QRCodePaymentActivity.this) + "&sessionID=" + LoginDataResponse.getData().getSessionID() +
               "&session=" + LoginDataResponse.getData().getSession() + "&loginTypeID=" + LoginDataResponse.getData().getLoginTypeID()).
               apply(requestOptions)
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
               .into(qrcode);
   }

}
