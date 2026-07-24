package com.fintech.omnipe.Auth;

import android.app.Activity;
import android.os.Bundle;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fintech.omnipe.Api.Object.ChildRoles;
import com.fintech.omnipe.Api.Object.UserCreateSignup;
import com.fintech.omnipe.Api.Request.GetRoleForReferralRequest;
import com.fintech.omnipe.Api.Request.SignupRequest;
import com.fintech.omnipe.Auth.dto.LoginResponse;
import com.fintech.omnipe.BuildConfig;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.ApiClient;
import com.fintech.omnipe.Util.ApplicationConstant;
import com.fintech.omnipe.Util.CustomFilterDialogUtils.CustomFilterDialog;
import com.fintech.omnipe.Util.EndPointInterface;
import com.fintech.omnipe.Util.UtilMethods;
import com.fintech.omnipe.usefull.CustomLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;

public class SignupActivity extends AppCompatActivity {
    private LinearLayout rr1;
    private LinearLayout llSignup;
    private TextInputLayout tilName;
    private AutoCompleteTextView etName;
    private TextInputLayout tilOutletName;
    private AutoCompleteTextView etOutletName;
    private TextInputLayout tilRoll;
    private AutoCompleteTextView etRoll;
    private TextInputLayout tilMobile;
    private AutoCompleteTextView etMobile;
    private TextInputLayout tilEmail;
    private AutoCompleteTextView etEmail;
    private TextInputLayout tilAddress;
    private AutoCompleteTextView etAddress;
    private TextInputLayout tilPincode;
    private AutoCompleteTextView etPincode;
    private TextInputLayout tilReferral;
    private AutoCompleteTextView etReferral;
    private Button btLogin;
    private TextView tvLogin;
    CustomLoader loader;
    private HashMap<String, Integer> rollsMap = new HashMap<>();
    private String[] rollArray;
    CustomFilterDialog mCustomFilterDialog;
    private String apiCalledReferal;
    String referralId = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_signup);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        mCustomFilterDialog = new CustomFilterDialog(this);
        findViews();


    }

    private void findViews() {
        rr1 = (LinearLayout) findViewById(R.id.rr_1);
        llSignup = (LinearLayout) findViewById(R.id.ll_signup);
        tilName = (TextInputLayout) findViewById(R.id.til_name);
        etName = (AutoCompleteTextView) findViewById(R.id.et_name);
        tilOutletName = (TextInputLayout) findViewById(R.id.til_outletName);
        etOutletName = (AutoCompleteTextView) findViewById(R.id.et_outletName);
        tilRoll = (TextInputLayout) findViewById(R.id.til_rollid);
        etRoll = (AutoCompleteTextView) findViewById(R.id.et_roll);
        tilMobile = (TextInputLayout) findViewById(R.id.til_mobile);
        etMobile = (AutoCompleteTextView) findViewById(R.id.et_mobile);
        tilEmail = (TextInputLayout) findViewById(R.id.til_email);
        etEmail = (AutoCompleteTextView) findViewById(R.id.et_email);
        tilAddress = (TextInputLayout) findViewById(R.id.til_address);
        etAddress = (AutoCompleteTextView) findViewById(R.id.et_address);
        tilPincode = (TextInputLayout) findViewById(R.id.til_pincode);
        etPincode = (AutoCompleteTextView) findViewById(R.id.et_pincode);
        etReferral = (AutoCompleteTextView) findViewById(R.id.et_referral);
        tilReferral = findViewById(R.id.til_referral);
        etReferral.setText(UtilMethods.INSTANCE.getReferrerId(this) + "");
        btLogin = (Button) findViewById(R.id.bt_login);
        tvLogin = (TextView) findViewById(R.id.tv_login);

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        etRoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setRoll();
            }
        });

        etReferral.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (apiCalledReferal != null && !apiCalledReferal.equalsIgnoreCase(s.toString())) {
                    etRoll.setText("");
                }
            }
        });
    }


    void setRoll() {

        if (etReferral.getText().toString().isEmpty()) {
            referralId = "1";
        } else {
            referralId = etReferral.getText().toString();
        }
        if (rollArray != null && rollArray.length > 0) {
            if (apiCalledReferal != null && !apiCalledReferal.equalsIgnoreCase(referralId)) {
                Role(this);
            } else {
                selectRole();
            }

        } else {
            Role(this);
        }
    }


    public void Role(final Activity context) {
        try {
            loader.show();
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);

            Call<LoginResponse> call = git.GetRoleForReferral(new GetRoleForReferralRequest(referralId,
                    ApplicationConstant.INSTANCE.Domain,
                    ApplicationConstant.INSTANCE.APP_ID,
                    UtilMethods.INSTANCE.getIMEI(context),
                    "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(context)));


            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, final retrofit2.Response<LoginResponse> response) {

                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    try {
                        if (response.body() != null && response.body().getStatuscode() != null) {
                            if (response.body().getStatuscode().equalsIgnoreCase("1")) {
                                ArrayList<ChildRoles> mChildRolesArray = response.body().getChildRoles();
                                if (mChildRolesArray != null && mChildRolesArray.size() > 0) {
                                    apiCalledReferal = referralId;
                                    // private String[] rollArray = {"Sub Admin", "Master Distributor", "Distributor", "Retailer", "API User"};
                                    rollArray = new String[mChildRolesArray.size()];
                                    rollsMap.clear();
                                    for (int i = 0; i < mChildRolesArray.size(); i++) {
                                        rollArray[i] = mChildRolesArray.get(i).getRole() + "";
                                        rollsMap.put(mChildRolesArray.get(i).getRole() + "", mChildRolesArray.get(i).getId());
                                    }
                                    selectRole();
                                }
                            } else if (response.body().getStatuscode().equalsIgnoreCase("-1")) {
                                if (response.body().getIsVersionValid() != null && response.body().getIsVersionValid().equalsIgnoreCase("false")) {
                                    UtilMethods.INSTANCE.versionDialog(context);
                                } else {
                                    UtilMethods.INSTANCE.Error(context, response.body().getMsg() + "");
                                }
                            }
                        } else {
                            UtilMethods.INSTANCE.Error(context, getString(R.string.some_thing_error) + "");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {

                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    if (t.getMessage().contains("No address associated with hostname")) {
                        UtilMethods.INSTANCE.Error(context, context.getResources().getString(R.string.network_error));
                    } else {
                        UtilMethods.INSTANCE.Error(context, t.getMessage());
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void selectRole() {
        int selectedIndex = 0;
        if (etRoll.getText().toString().length() == 0) {
            selectedIndex = -1;
        } else {
            selectedIndex = Arrays.asList(rollArray).indexOf(etRoll.getText().toString());
        }
        mCustomFilterDialog.showSingleChoiceAlert(rollArray, selectedIndex, "Date Type", "Choose Date Type", new CustomFilterDialog.SingleChoiceDialogCallBack() {
            @Override
            public void onPositiveClick(int index) {
                etRoll.setText(rollArray[index]);
            }

            @Override
            public void onNegativeClick() {

            }
        });
    }

    void signup() {
        tilReferral.setErrorEnabled(false);
        tilRoll.setErrorEnabled(false);
        tilName.setErrorEnabled(false);
        tilOutletName.setErrorEnabled(false);
        tilMobile.setErrorEnabled(false);
        tilEmail.setErrorEnabled(false);
        tilAddress.setErrorEnabled(false);
        tilPincode.setErrorEnabled(false);

        if (etRoll.getText().toString().isEmpty()) {
            tilRoll.setError(getString(R.string.err_empty_field));
            etRoll.requestFocus();
            return;
        } else if (etName.getText().toString().isEmpty()) {
            tilName.setError(getString(R.string.err_empty_field));
            etName.requestFocus();
            return;
        } else if (etOutletName.getText().toString().isEmpty()) {
            tilOutletName.setError(getString(R.string.err_empty_field));
            etOutletName.requestFocus();
            return;
        } else if (etMobile.getText().toString().isEmpty()) {
            tilMobile.setError(getString(R.string.err_empty_field));
            etMobile.requestFocus();
            return;
        } else if (etMobile.getText().toString().length() != 10) {
            tilMobile.setError(getString(R.string.err_msg_mobile_length));
            etMobile.requestFocus();
            return;
        } else if (etEmail.getText().toString().isEmpty()) {
            tilEmail.setError(getString(R.string.err_empty_field));
            etEmail.requestFocus();
            return;
        } else if (!etEmail.getText().toString().contains("@") || !etEmail.getText().toString().contains(".")) {
            tilEmail.setError(getString(R.string.err_msg_email));
            etEmail.requestFocus();
            return;
        } else if (etAddress.getText().toString().isEmpty()) {
            tilAddress.setError(getString(R.string.err_empty_field));
            etAddress.requestFocus();
            return;
        } else if (etPincode.getText().toString().isEmpty()) {
            tilPincode.setError(getString(R.string.err_empty_field));
            etPincode.requestFocus();
            return;
        } else if (etPincode.getText().toString().length() != 6) {
            tilPincode.setError(getString(R.string.pincode_error));
            etPincode.requestFocus();
            return;
        }

        Register(this);
    }


    public void Register(final Activity context) {
        try {
            loader.show();
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            UserCreateSignup mUserCreateSignup = new UserCreateSignup(referralId, etAddress.getText().toString(), etMobile.getText().toString(),
                    etName.getText().toString(), etOutletName.getText().toString(), etEmail.getText().toString(), rollsMap.get(etRoll.getText().toString()),
                    etPincode.getText().toString());
            Call<LoginResponse> call = git.AppUserSignup(new SignupRequest(referralId,
                    ApplicationConstant.INSTANCE.Domain,
                    ApplicationConstant.INSTANCE.APP_ID,
                    UtilMethods.INSTANCE.getIMEI(context),
                    "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(context), mUserCreateSignup));


            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, final retrofit2.Response<LoginResponse> response) {

                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    try {
                        if (response.body() != null && response.body().getStatuscode() != null) {
                            if (response.body().getStatuscode().equalsIgnoreCase("1")) {
                                UtilMethods.INSTANCE.SuccessfulWithFinsh(context, response.body().getMsg() + "");
                            } else if (response.body().getStatuscode().equalsIgnoreCase("-1")) {
                                if (response.body().getIsVersionValid() != null && response.body().getIsVersionValid().equalsIgnoreCase("false")) {
                                    UtilMethods.INSTANCE.versionDialog(context);
                                } else {
                                    UtilMethods.INSTANCE.Error(context, response.body().getMsg() + "");
                                }
                            }
                        } else {
                            UtilMethods.INSTANCE.Error(context, getString(R.string.some_thing_error) + "");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {

                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    if (t.getMessage().contains("No address associated with hostname")) {
                        UtilMethods.INSTANCE.Error(context, context.getResources().getString(R.string.network_error));
                    } else {
                        UtilMethods.INSTANCE.Error(context, t.getMessage());
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
