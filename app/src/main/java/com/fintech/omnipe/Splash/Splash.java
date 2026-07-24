package com.fintech.omnipe.Splash;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.google.android.material.snackbar.Snackbar;
import com.fintech.omnipe.Auth.LoginActivity;
import com.fintech.omnipe.DashBoard.UI.DashBoardLayout;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.InstallReferral;
import com.fintech.omnipe.Util.UtilMethods;


public class Splash extends AppCompatActivity {

    private static final int REQUEST_PERMISSIONS = 1;
    private String[] PERMISSIONS;
    private Snackbar mSnackBar;
    private boolean isSettingClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.splash);
        ReadPhoneStatePermission();
        if (!UtilMethods.INSTANCE.isReferrerIdSetData(this)) {
            new InstallReferral(this).InstllReferralData();
        }
    }

    public void dashboardpage() {
        Thread timerThread = new Thread() {
            public void run() {
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {

                    startDashboard();
                }
            }
        };
        timerThread.start();
    }

    public void loginpage() {

        Thread timerThread = new Thread() {
            public void run() {
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {

                    startLogin();
                }
            }
        };
        timerThread.start();
    }

    public void startDashboard() {
        Intent intent = new Intent(Splash.this, DashBoardLayout.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    public void startLogin() {
        Intent intent = new Intent(Splash.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void ReadPhoneStatePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            PERMISSIONS = new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE)
                    || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)
                    || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                showWarningSnack(R.string.str_ShowOnPermisstion, "Allow", false);

            } else {
                ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_PERMISSIONS);

            }
        } else {
            PERMISSIONS = new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE)
                    || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)
                    || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                showWarningSnack(R.string.str_ShowOnPermisstion, "Allow", false);

            } else {
                ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_PERMISSIONS);

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSIONS) {

            int permissionCheck = PackageManager.PERMISSION_GRANTED;
            for (int permission : grantResults) {
                permissionCheck = permissionCheck + permission;
            }
            if ((grantResults.length > 0) && permissionCheck == PackageManager.PERMISSION_GRANTED) {

                goToNextScreen();
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
                            isSettingClick = true;
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            intent.addCategory(Intent.CATEGORY_DEFAULT);
                            intent.setData(Uri.parse("package:" + getPackageName()));
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                            startActivity(intent);
                        } else {
                            ActivityCompat.requestPermissions(Splash.this, PERMISSIONS, REQUEST_PERMISSIONS);
                        }

                    }
                });

        mSnackBar.setActionTextColor(getResources().getColor(R.color.colorPrimary));
        TextView mainTextView = (mSnackBar.getView()).
                findViewById(R.id.snackbar_text);
        mainTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen._12sdp));
        mainTextView.setMaxLines(4);
        mSnackBar.show();
    }


    @Override
    protected void onResume() {
        super.onResume();

        if (isSettingClick) {
            isSettingClick = false;

            ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_PERMISSIONS);
        }
    }

    void goToNextScreen() {
        if (UtilMethods.INSTANCE.getIsLogin(this) == 1) {
            dashboardpage();
        } else {
            loginpage();
        }

    }


}
