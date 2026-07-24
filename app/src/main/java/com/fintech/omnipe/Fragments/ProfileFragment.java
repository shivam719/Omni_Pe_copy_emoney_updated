package com.fintech.omnipe.Fragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.snackbar.Snackbar;
import com.fintech.omnipe.AccountSettlement.UI.SettlementBankListActivity;
import com.fintech.omnipe.Activities.ImagePickerActivity;
import com.google.android.material.textfield.TextInputLayout;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SwitchCompat;

import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.fintech.omnipe.Activities.Adapter.WalletBalanceAdapter;
import com.fintech.omnipe.Activities.UpdateProfileActivity;
import com.fintech.omnipe.Api.Object.BalanceType;
import com.fintech.omnipe.Api.Request.BasicRequest;
import com.fintech.omnipe.Api.Response.BalanceResponse;
import com.fintech.omnipe.Api.Response.DFStatusResponse;
import com.fintech.omnipe.BuildConfig;
import com.fintech.omnipe.Fragments.dto.GetUserResponse;
import com.fintech.omnipe.Auth.dto.LoginResponse;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.ApiClient;
import com.fintech.omnipe.Util.ApplicationConstant;
import com.fintech.omnipe.Util.ChangePassUtils;
import com.fintech.omnipe.Util.CustomAlertDialog;
import com.fintech.omnipe.Util.EndPointInterface;
import com.fintech.omnipe.Util.UtilMethods;
import com.fintech.omnipe.usefull.CustomLoader;
import com.roundpay.imagepicker.ImagePicker;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;


public class ProfileFragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {


    View editProfile;
    CustomLoader loader;
    private ChangePassUtils mChangePassUtils;
    LoginResponse LoginPrefResponse;
    BalanceResponse balanceCheckResponse;
    SharedPreferences myPrefs;
    RequestOptions requestOptions;
    GetUserResponse mGetUserResponse;
    private TextView outletName,editProfileTxt;
    private LinearLayout addressView, outletNameView;
    private TextView address;
    private LinearLayout pincodeView;
    private TextView pincode;
    private LinearLayout stateView;
    private TextView state;
    private LinearLayout cityView;
    private TextView city;
    private LinearLayout gstinView;
    private TextView gstin;
    private LinearLayout aadharView;
    private TextView aadhar;
    private LinearLayout panView;
    private TextView pan/*, doubleFactorLabel*/;
    private TextView name/*, userName*/;
    private TextView mobile;
    private TextView email, role;
    private ImageView editProfileIv,userProfileIcon;
    private View doubleFactorLayoutContainer,realApiLayoutContainer,userIconView;
    private SwitchCompat toggleDoubleFactor,toggleRealApi;
    private boolean isProfileEdit;
    private boolean isDoubleFactorEnable,isRealApiEnable;
    private DFStatusResponse mDfStatusResponse;
    private Dialog otpDialog;
    TextView tvBalance;
    View rlChangePin, rlChangePass;
    RecyclerView balanceRecyclerView;
    private ImagePicker imagePicker;
    private final int INTENT_PERMISSION_IMAGE = 4621;
    private final int INTENT_PERMISSION_PROFILE =578;
    private final int REQUEST_PERMISSIONS_PROFILE=101;
    private Snackbar mSnackBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for getActivity() fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        UtilMethods.INSTANCE.setDashboardStatus(getActivity(), true);
        loader = new CustomLoader(getActivity(), android.R.style.Theme_Translucent_NoTitleBar);
        UtilMethods.INSTANCE.setDashboardStatus(getActivity(), false);
        mChangePassUtils = new ChangePassUtils(getActivity());
        myPrefs = getActivity().getSharedPreferences(ApplicationConstant.INSTANCE.prefNameLoginPref, MODE_PRIVATE);
        String getLoginPref = myPrefs.getString(ApplicationConstant.INSTANCE.LoginPref, "");
        LoginPrefResponse = new Gson().fromJson(getLoginPref, LoginResponse.class);
        SharedPreferences myPreferences = getActivity().getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.balancePref, null);
        balanceCheckResponse = new Gson().fromJson(balanceResponse, BalanceResponse.class);
        getID(v);
        ImagePickerActivity.clearCache(getActivity());
        setUserProfile();
        return v;
    }

    private void setUserProfile() {
        requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_user_icon);
        requestOptions.error(R.drawable.ic_user_icon);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
        requestOptions.skipMemoryCache(true);
        requestOptions.transform(new CircleCrop());
        Glide.with(getActivity())
                .load(ApplicationConstant.INSTANCE.baseProfilePicUrl + LoginPrefResponse.getData().getUserID() + ".png")
                .apply(requestOptions)
                .into(userProfileIcon);
        userIconView.setOnClickListener(this);

        imagePicker = new ImagePicker(requireActivity(), this, imageUri -> {
            Uri imgUri = imageUri;
            File selectedImageFile = new File(imgUri.getPath());
            uploadPicApi(selectedImageFile);
        }).setWithImageCrop();
    }


    void getID(View v) {
        name = v.findViewById(R.id.name);
        userIconView = v.findViewById(R.id.userIconView);
        userProfileIcon = v.findViewById(R.id.userProfileIcon);
        editProfileTxt = v.findViewById(R.id.editProfileTxt);
        editProfileIv = v.findViewById(R.id.iv_editProfile);
        // userName = v.findViewById(R.id.userName);
        // doubleFactorLabel = v.findViewById(R.id.doubleFactorLabel);
        balanceRecyclerView = v.findViewById(R.id.balanceRecyclerView);
        balanceRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        rlChangePin = v.findViewById(R.id.rl_change_pin);
        rlChangePass = v.findViewById(R.id.rl_change_pass);
        toggleDoubleFactor = v.findViewById(R.id.toggleDoubleFactor);
        toggleRealApi = v.findViewById(R.id.toggleRealApi);
        tvBalance = v.findViewById(R.id.tv_pre_bal);
        doubleFactorLayoutContainer = v.findViewById(R.id.doubleFactorLayoutContainer);
        realApiLayoutContainer = v.findViewById(R.id.realApiLayoutContainer);
        editProfile = v.findViewById(R.id.editProfile);
        outletName = v.findViewById(R.id.outletName);
        outletNameView = v.findViewById(R.id.outletNameView);
        addressView = v.findViewById(R.id.addressView);
        address = v.findViewById(R.id.address);
        pincodeView = v.findViewById(R.id.pincodeView);
        pincode = v.findViewById(R.id.pincode);
        stateView = v.findViewById(R.id.stateView);
        state = v.findViewById(R.id.state);
        cityView = v.findViewById(R.id.cityView);
        city = v.findViewById(R.id.city);
        gstinView = v.findViewById(R.id.gstinView);
        gstin = v.findViewById(R.id.gstin);
        aadharView = v.findViewById(R.id.aadharView);
        aadhar = v.findViewById(R.id.aadhar);
        panView = v.findViewById(R.id.panView);
        pan = v.findViewById(R.id.pan);
        mobile = v.findViewById(R.id.mobile);


        email = v.findViewById(R.id.email);
        role = v.findViewById(R.id.role);

        doubleFactorLayoutContainer.setOnClickListener(this);
        realApiLayoutContainer.setOnClickListener(this);
        editProfile.setOnClickListener(this);
        rlChangePin.setOnClickListener(this);
        rlChangePass.setOnClickListener(this);

        mGetUserResponse = new Gson().fromJson(UtilMethods.INSTANCE.getUserDataPref(getActivity()), GetUserResponse.class);
        initialValues();
        setUserData();
        getUserDetail();
    }


    private void initialValues() {
        if (balanceCheckResponse.getBalanceData().getBalance() != null && balanceCheckResponse.getBalanceData().getBalance() != 0) {
            tvBalance.setText(balanceCheckResponse.getBalanceData().getBalance() + "");
        }
        showWalletData();
        if (LoginPrefResponse.getData().getMobileNo() != null && LoginPrefResponse.getData().getMobileNo().length() > 0) {
            mobile.setText(LoginPrefResponse.getData().getMobileNo() + "");
        }
        if (LoginPrefResponse.getData().getEmailID() != null && LoginPrefResponse.getData().getEmailID().length() > 0) {
            email.setText(LoginPrefResponse.getData().getEmailID() + "");
        }

        if (LoginPrefResponse.getData().getName() != null && LoginPrefResponse.getData().getName().length() > 0) {
            name.setText(LoginPrefResponse.getData().getName() + "");
            //  userName.setText(LoginPrefResponse.getData().getName() + "");
        }
        if (LoginPrefResponse.getData().getRoleName() != null && LoginPrefResponse.getData().getRoleName().length() > 0) {
            role.setText(LoginPrefResponse.getData().getRoleName() + "");
        }


    }
    private void showWalletData() {
        ArrayList<BalanceType> mBalanceTypes = new ArrayList<>();
        if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData()!=null) {
            if (balanceCheckResponse.getBalanceData().isBalance()) {
                mBalanceTypes.add(new BalanceType(balanceCheckResponse.getBalanceData().getPrepaidWalletName(), balanceCheckResponse.getBalanceData().getBalance() + ""));
            }
            if (balanceCheckResponse.getBalanceData().isUBalance()) {
                mBalanceTypes.add(new BalanceType(balanceCheckResponse.getBalanceData().getUtilityWalletName(), balanceCheckResponse.getBalanceData().getuBalance() + ""));
            }
            if (balanceCheckResponse.getBalanceData().isBBalance()) {
                mBalanceTypes.add(new BalanceType(balanceCheckResponse.getBalanceData().getBankWalletName(), balanceCheckResponse.getBalanceData().getbBalance() + ""));
            }
            if (balanceCheckResponse.getBalanceData().isCBalance()) {
                mBalanceTypes.add(new BalanceType(balanceCheckResponse.getBalanceData().getCardWalletName(), balanceCheckResponse.getBalanceData().getcBalance() + ""));
            }
            if (balanceCheckResponse.getBalanceData().isIDBalance()) {
                mBalanceTypes.add(new BalanceType(balanceCheckResponse.getBalanceData().getRegIDWalletName(), balanceCheckResponse.getBalanceData().getIdBalnace() + ""));
            }
            if (balanceCheckResponse.getBalanceData().isAEPSBalance()) {
                mBalanceTypes.add(new BalanceType("Aeps Wallet", balanceCheckResponse.getBalanceData().getAepsBalnace() + ""));
            }
            if (balanceCheckResponse.getBalanceData().isPacakgeBalance()) {
                mBalanceTypes.add(new BalanceType(balanceCheckResponse.getBalanceData().getPackageWalletName(), balanceCheckResponse.getBalanceData().getPacakgeBalance()+""));
            }
            if(LoginPrefResponse.isAccountStatement()){
                mBalanceTypes.add(new BalanceType("Outstanding Wallet", balanceCheckResponse.getBalanceData().getOsBalance()+""));
            }

        }
        balanceRecyclerView.setAdapter(new WalletBalanceAdapter(getActivity(), mBalanceTypes, R.layout.adapter_balance));
    }

    @Override
    public void onClick(View v) {
        if (v == editProfile) {
            if(isProfileEdit){
                startActivityForResult(new Intent(getActivity(), UpdateProfileActivity.class)
                        /*.putExtra("UserData", mGetUserResponse)*/
                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), INTENT_PERMISSION_PROFILE);
            }else{
                startActivity(new Intent(getActivity(), SettlementBankListActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP));
            }
        }
        else if (v == doubleFactorLayoutContainer) {

            otpApi(!isDoubleFactorEnable, "", "");
        }
        else if (v == rlChangePass) {
            mChangePassUtils.changePassword(false, true);
        }
        else if (v == rlChangePin) {
          //  mChangePassUtils.changePassword(true, true);
            UtilMethods.INSTANCE.ChangePinPassword(getActivity(), true, "","0000","0000", loader, null);

        }
        else if(v==userIconView){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISSIONS_PROFILE);
                } else {
                    imagePicker.choosePicture(true);
                }
            } else {
                if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA}, REQUEST_PERMISSIONS_PROFILE);

                } else {
                    imagePicker.choosePicture(true);
                }

            }
           /* Dexter.withActivity(getActivity())
                    .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .withListener(new MultiplePermissionsListener() {
                        @Override
                        public void onPermissionsChecked(MultiplePermissionsReport report) {
                            if (report.areAllPermissionsGranted()) {
                                showImagePickerOptions();
                            }

                            if (report.isAnyPermissionPermanentlyDenied()) {
                                showSettingsDialog();
                            }
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                            token.continuePermissionRequest();
                        }


                    }).check();*/
        }

    }

    private void showImagePickerOptions() {
        ImagePickerActivity.showImagePickerOptions(getActivity(), new ImagePickerActivity.PickerOptionListener() {
            @Override
            public void onTakeCameraSelected() {
                launchCameraIntent();
            }

            @Override
            public void onChooseGallerySelected() {
                launchGalleryIntent();
            }

            @Override
            public void onChooseFileSelected() {

            }
        });
    }

    private void launchCameraIntent() {
        Intent intent = new Intent(getActivity(), ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);

        // setting maximum bitmap width and height
        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000);
        startActivityForResult(intent, INTENT_PERMISSION_IMAGE);
    }

    private void launchGalleryIntent() {
        Intent intent = new Intent(getActivity(), ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);
        startActivityForResult(intent, INTENT_PERMISSION_IMAGE);
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.dialog_permission_title));
        builder.setMessage(getString(R.string.dialog_permission_message));
        builder.setPositiveButton(getString(R.string.go_to_settings), (dialog, which) -> {
            dialog.cancel();
            openSettings();
        });
        builder.setNegativeButton(getString(android.R.string.cancel), (dialog, which) -> dialog.cancel());
        builder.show();

    }
    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    private void openOTPDialog() {
        if (otpDialog != null && otpDialog.isShowing()) {
            return;
        }
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.verifyotp, null);

        final EditText edMobileOtp = view.findViewById(R.id.ed_mobile_otp);
        edMobileOtp.setInputType(InputType.TYPE_CLASS_TEXT);
        final TextInputLayout tilMobileOtp = view.findViewById(R.id.til_mobile_otp);
        final Button okButton = view.findViewById(R.id.okButton);
        final Button cancelButton = view.findViewById(R.id.cancelButton);
        final Button resendButton = view.findViewById(R.id.resendButton);
        resendButton.setVisibility(View.VISIBLE);
        otpDialog = new Dialog(getActivity(), R.style.alert_dialog_light);
        otpDialog.setCancelable(false);
        otpDialog.setContentView(view);
        otpDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otpDialog.dismiss();
            }
        });

        edMobileOtp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() < 6) {
                    tilMobileOtp.setError(getString(R.string.err_msg_otp));
                    okButton.setEnabled(false);
                } else {
                    tilMobileOtp.setErrorEnabled(false);
                    okButton.setEnabled(true);
                }
            }
        });

        resendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mDfStatusResponse != null) {
                    otpApi(!isDoubleFactorEnable, "OTP", mDfStatusResponse.getRefID());
                } else {
                    otpApi(!isDoubleFactorEnable, "OTP", "");
                }
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edMobileOtp.getText() != null && edMobileOtp.getText().length() == 6) {
                    tilMobileOtp.setErrorEnabled(false);
                    if (mDfStatusResponse != null) {
                        otpApi(!isDoubleFactorEnable, edMobileOtp.getText().toString(), mDfStatusResponse.getRefID());
                    } else {
                        otpApi(!isDoubleFactorEnable, edMobileOtp.getText().toString(), "");
                    }

                } else {
                    tilMobileOtp.setError(getString(R.string.err_msg_otp));
                }
            }
        });
        otpDialog.show();
    }

    private void otpApi(boolean isDoubleFactor, String otp, final String refId) {
        if (UtilMethods.INSTANCE.isNetworkAvialable(getActivity())) {
            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);

            UtilMethods.INSTANCE.DoubleFactorOtp(getActivity(), isDoubleFactor, otp, refId, loader, new UtilMethods.ApiCallBack() {
                @Override
                public void onSucess(Object object) {
                    mDfStatusResponse = (DFStatusResponse) object;

                    if (mDfStatusResponse.isOTPRequired()) {
                        openOTPDialog();
                    } else if (mDfStatusResponse.getRefID() == null && !mDfStatusResponse.isOTPRequired() || mDfStatusResponse.getRefID().isEmpty() && !mDfStatusResponse.isOTPRequired()) {
                        UtilMethods.INSTANCE.Successful(getActivity(), mDfStatusResponse.getMsg() + "");
                        if (otpDialog != null && otpDialog.isShowing()) {
                            otpDialog.dismiss();
                        }
                        isDoubleFactorEnable = !isDoubleFactorEnable;
                        UtilMethods.INSTANCE.setDoubleFactorPref(getActivity(), isDoubleFactorEnable);
                        if (isDoubleFactorEnable) {
                            //doubleFactorLabel.setText("Disable Double Factor");
                            toggleDoubleFactor.setChecked(true);
                        } else {
                            //  doubleFactorLabel.setText("Enable Double Factor");
                            toggleDoubleFactor.setChecked(false);
                        }
                    } else {
                        UtilMethods.INSTANCE.Successful(getActivity(), mDfStatusResponse.getMsg() + "");
                    }
                }
            });

        } else {
            UtilMethods.INSTANCE.NetworkError(getActivity(), getString(R.string.err_msg_network_title),
                    getString(R.string.err_msg_network));
        }
    }

    private void enableDisableRealApi() {
        if (UtilMethods.INSTANCE.isNetworkAvialable(getActivity())) {
            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);
            UtilMethods.INSTANCE.EnableDisableRealApi(getActivity(), isRealApiEnable ? false : true, loader, new UtilMethods.ApiCallBack() {
                @Override
                public void onSucess(Object object) {
                    isRealApiEnable = isRealApiEnable ? false : true;
                    UtilMethods.INSTANCE.setRealApiPref(getActivity(), isRealApiEnable);
                    toggleRealApi.setChecked(isRealApiEnable ? false : true);

                }
            });

        } else {
            UtilMethods.INSTANCE.NetworkError(getActivity(), getActivity().getResources().getString(R.string.err_msg_network_title),
                    getActivity().getResources().getString(R.string.err_msg_network));
        }
    }

    public void getUserDetail() {
        try {
            loader.show();
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            String LoginResponse = UtilMethods.INSTANCE.getLoginPref(getActivity());
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            Call<GetUserResponse> call = git.GetProfile(new BasicRequest(
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    UtilMethods.INSTANCE.getIMEI(getActivity()),
                    "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(getActivity()), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<GetUserResponse>() {

                @Override
                public void onResponse(Call<GetUserResponse> call, retrofit2.Response<GetUserResponse> response) {
                    if (loader.isShowing())
                        loader.dismiss();
                    mGetUserResponse = response.body();
                    if (mGetUserResponse != null) {
                        if (mGetUserResponse.getPasswordExpired()) {
                            CustomAlertDialog customAlertDialog = new CustomAlertDialog(getActivity(), true);
                            customAlertDialog.WarningWithCallBack(getString(R.string.password_expired_msg), "Change Password", false, new CustomAlertDialog.DialogCallBack() {
                                @Override
                                public void onPositiveClick() {
                                    new ChangePassUtils(getActivity()).changePassword(false, false);
                                }

                                @Override
                                public void onNegativeClick() {
                                }
                            });
                        }
                        if (mGetUserResponse.getStatuscode() == 1) {
                            UtilMethods.INSTANCE.setUserDataPref(getActivity(), new Gson().toJson(mGetUserResponse));
                            UtilMethods.INSTANCE.setDoubleFactorPref(getActivity(), mGetUserResponse.getUserInfo().isDoubleFactor());
                            setUserData();


                        } else if (response.body().getStatuscode() == -1) {

                            UtilMethods.INSTANCE.Error(getActivity(), mGetUserResponse.getMsg() + "");
                        } else if (response.body().getStatuscode() == 0) {

                            UtilMethods.INSTANCE.Error(getActivity(), mGetUserResponse.getMsg() + "");
                        } else {

                            UtilMethods.INSTANCE.Error(getActivity(), mGetUserResponse.getMsg() + "");
                        }

                    } else {

                        UtilMethods.INSTANCE.Error(getActivity(), getString(R.string.some_thing_error));
                    }
                }

                @Override
                public void onFailure(Call<GetUserResponse> call, Throwable t) {
                    Log.e("response", "error ");

                    try {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                            if (t.getMessage().contains("No address associated with hostname")) {

                                UtilMethods.INSTANCE.NetworkError(getActivity(), getString(R.string.network_error_title), getString(R.string.err_msg_network));


                            } else {

                                UtilMethods.INSTANCE.Error(getActivity(), t.getMessage());


                            }

                        } else {

                            UtilMethods.INSTANCE.Error(getActivity(), getString(R.string.some_thing_error));
                        }
                    } catch (IllegalStateException ise) {
                        loader.dismiss();

                        UtilMethods.INSTANCE.Error(getActivity(), getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();

            UtilMethods.INSTANCE.Error(getActivity(), getString(R.string.some_thing_error));
        }

    }

    private void setUserData() {

        if (mGetUserResponse != null && mGetUserResponse.getUserInfo() != null) {
            if (mGetUserResponse.getUserInfo().getKycStatus() == 2 || mGetUserResponse.getUserInfo().getKycStatus() == 3) {
                editProfileTxt.setText("Update Account Settlement");
                editProfileIv.setImageResource(R.drawable.icon_bank);
                isProfileEdit=false;
            } else {
                editProfileTxt.setText("Update Profile");
                editProfileIv.setImageResource(R.drawable.ic_edit);
                isProfileEdit=true;
            }
            if (mGetUserResponse.getUserInfo().getName() != null && !mGetUserResponse.getUserInfo().getName().isEmpty()) {
                name.setText(mGetUserResponse.getUserInfo().getName());
                //userName.setText(LoginPrefResponse.getData().getName() + "");
            }
            if (mGetUserResponse.getUserInfo().getRole() != null && !mGetUserResponse.getUserInfo().getRole().isEmpty()) {
                role.setText(mGetUserResponse.getUserInfo().getRole());
            }
            if (mGetUserResponse.getUserInfo().getOutletName() != null && !mGetUserResponse.getUserInfo().getOutletName().isEmpty()) {
                outletName.setText(mGetUserResponse.getUserInfo().getOutletName());
            } else {
                outletNameView.setVisibility(View.GONE);
            }
            if (mGetUserResponse.getUserInfo().getAddress() != null && !mGetUserResponse.getUserInfo().getAddress().isEmpty()) {
                address.setText(mGetUserResponse.getUserInfo().getAddress());
            } else {
                addressView.setVisibility(View.GONE);
            }
            if (mGetUserResponse.getUserInfo().getPincode() != null && !mGetUserResponse.getUserInfo().getPincode().isEmpty()) {
                pincode.setText(mGetUserResponse.getUserInfo().getPincode());
            } else {
                pincodeView.setVisibility(View.GONE);
            }
            if (mGetUserResponse.getUserInfo().getStateName() != null && !mGetUserResponse.getUserInfo().getStateName().isEmpty()) {
                state.setText(mGetUserResponse.getUserInfo().getStateName());
            } else {
                stateView.setVisibility(View.GONE);
            }
            if (mGetUserResponse.getUserInfo().getCity() != null && !mGetUserResponse.getUserInfo().getCity().isEmpty()) {
                city.setText(mGetUserResponse.getUserInfo().getCity());
            } else {
                cityView.setVisibility(View.GONE);
            }
            if (mGetUserResponse.getUserInfo().getGstin() != null && !mGetUserResponse.getUserInfo().getGstin().isEmpty()) {
                gstin.setText(mGetUserResponse.getUserInfo().getGstin());
            } else {
                gstinView.setVisibility(View.GONE);
            }
            if (mGetUserResponse.getUserInfo().getAadhar() != null && !mGetUserResponse.getUserInfo().getAadhar().isEmpty()) {
                aadhar.setText(mGetUserResponse.getUserInfo().getAadhar());
            } else {
                aadharView.setVisibility(View.GONE);
            }
            if (mGetUserResponse.getUserInfo().getPan() != null && !mGetUserResponse.getUserInfo().getPan().isEmpty()) {
                pan.setText(mGetUserResponse.getUserInfo().getPan());
            } else {
                panView.setVisibility(View.GONE);
            }

            if (UtilMethods.INSTANCE.getDoubleFactorPref(getActivity())) {
                isDoubleFactorEnable = true;
                //doubleFactorLabel.setText("Disable Double Factor");
                toggleDoubleFactor.setChecked(true);
            } else {
                isDoubleFactorEnable = false;
                // doubleFactorLabel.setText("Enable Double Factor");
                toggleDoubleFactor.setChecked(false);
            }

            if (!UtilMethods.INSTANCE.getRealApiPref(getActivity())) {
                realApiLayoutContainer.setVisibility(View.VISIBLE);

            } else {
                realApiLayoutContainer.setVisibility(View.GONE);
            }

            toggleRealApi.setOnCheckedChangeListener (null);
            toggleRealApi.setOnCheckedChangeListener (this);

        }



    }

    private void uploadPicApi(File selectedImageFile) {
        if (UtilMethods.INSTANCE.isNetworkAvialable(getActivity())) {
            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);

            UtilMethods.INSTANCE.UploadProfilePic(getActivity(), selectedImageFile, loader, LoginPrefResponse
                    , object -> {

                        if(object!=null)
                        {
                            Glide.with(getActivity())
                                    .load(ApplicationConstant.INSTANCE.baseProfilePicUrl + LoginPrefResponse.getData().getUserID() + ".png")
                                    .apply(requestOptions)
                                    .into(userProfileIcon);
                        }
                    });

        } else {
            UtilMethods.INSTANCE.NetworkError(getActivity());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == INTENT_PERMISSION_PROFILE && resultCode == RESULT_OK) {
            getUserDetail();
        }
        else if (requestCode == INTENT_PERMISSION_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getParcelableExtra("path");
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                    File selectedImageFile = new File(uri.getPath());
                    uploadPicApi(selectedImageFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else {
            imagePicker.handleActivityResult(resultCode, requestCode, data);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            isRealApiEnable=true;
        } else {
            isRealApiEnable=false;
        }
        enableDisableRealApi();
    }


    /*@Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("photopath", ImagePicker.currentCameraFileName);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable  Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("photopath") && ImagePicker.currentCameraFileName.length() < 5) {
                ImagePicker.currentCameraFileName = savedInstanceState.getString("photopath");
            }
        }
        super.onViewStateRestored(savedInstanceState);
    }
*/

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS_PROFILE) {

            int permissionCheck = PackageManager.PERMISSION_GRANTED;
            for (int permission : grantResults) {
                permissionCheck = permissionCheck + permission;
            }
            if ((grantResults.length > 0) && permissionCheck == PackageManager.PERMISSION_GRANTED) {
                imagePicker.handlePermission(requestCode, grantResults);
            } else {
                showWarningSnack(R.string.str_ShowOnPermisstionDenied, "Enable", true);
            }
        }else {
            imagePicker.handlePermission(requestCode, grantResults);
        }
    }

    void showWarningSnack(int stringId, String btn, final boolean isForSetting) {
        if (mSnackBar != null && mSnackBar.isShown()) {
            return;
        }

        mSnackBar = Snackbar.make(requireActivity().findViewById(android.R.id.content), stringId,
                Snackbar.LENGTH_INDEFINITE).setAction(btn,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isForSetting) {
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            intent.addCategory(Intent.CATEGORY_DEFAULT);
                            intent.setData(Uri.parse("package:" + requireActivity().getPackageName()));
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                            startActivity(intent);
                        } else {
                            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSIONS_PROFILE);
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
