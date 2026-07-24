package com.fintech.omnipe.Util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;

import com.fintech.omnipe.R;
import com.fintech.omnipe.usefull.CustomLoader;
import com.google.android.material.textfield.TextInputLayout;

public class ChangePassUtils {
    Activity mActivity;
    private CustomLoader loader;
    private Dialog dialog;

    public ChangePassUtils(Activity mActivity) {
        this.mActivity = mActivity;
        loader = new CustomLoader(mActivity, android.R.style.Theme_Translucent_NoTitleBar);
    }


    public void changePassword(final boolean isPin, boolean isCancelable) {
        if (dialog != null && dialog.isShowing()) {
            return;
        }
        LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.change_password, null);

        TextView title = view.findViewById(R.id.title);
        final TextInputLayout currentPasswordLayout = view.findViewById(R.id.currentPasswordLayout);
        final EditText currentPassword = view.findViewById(R.id.currentPassword);
        final TextInputLayout newPasswordLayout = view.findViewById(R.id.newPasswordLayout);
        final EditText newPassword = view.findViewById(R.id.newPassword);
        final TextInputLayout confirmPasswordLayout = view.findViewById(R.id.confirmPasswordLayout);
        final EditText confirmPassword = view.findViewById(R.id.confirmPassword);
        if (isPin) {
            title.setText("Change Pin Password");
            currentPasswordLayout.setHint("Enter Otp");
            newPasswordLayout.setHint("New Pin Password");
            confirmPasswordLayout.setHint("Confirm Pin Password");
        }
        final AppCompatButton okButton = view.findViewById(R.id.okButton);
        final AppCompatButton cancelButton = view.findViewById(R.id.cancelButton);
        if (isCancelable) {
            cancelButton.setVisibility(View.VISIBLE);
        } else {
            cancelButton.setVisibility(View.GONE);
        }
        dialog = new Dialog(mActivity, R.style.alert_dialog_light);

        dialog.setTitle("Forgot Password");
        dialog.setCancelable(isCancelable);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int flag = 0;

                if (currentPassword.getText() != null && currentPassword.getText().toString().trim().isEmpty()) {
                    flag++;
                    currentPassword.setError(mActivity.getResources().getString(R.string.password_error));
                    currentPassword.requestFocus();
                } else if (newPassword.getText() != null && newPassword.getText().toString().trim().isEmpty()) {
                    flag++;
                    newPassword.setError(mActivity.getResources().getString(R.string.password_error));
                    newPassword.requestFocus();
                } else if (newPassword.getText().toString().trim().equalsIgnoreCase(currentPassword.getText().toString().trim())) {
                    flag++;
                    newPassword.setError(mActivity.getResources().getString(R.string.samepass_error));
                    newPassword.requestFocus();
                } else if (confirmPassword.getText() != null && newPassword.getText() != null && !newPassword.getText().toString().trim().equalsIgnoreCase(confirmPassword.getText().toString().trim())) {
                    flag++;
                    confirmPassword.setError(mActivity.getResources().getString(R.string.newpass_error));
                    confirmPassword.requestFocus();
                }


                if (flag == 0) {
                    if (UtilMethods.INSTANCE.isNetworkAvialable(mActivity)) {
                        loader.show();
                        loader.setCancelable(false);
                        loader.setCanceledOnTouchOutside(false);
                        UtilMethods.INSTANCE.ChangePinPassword(mActivity, isPin, currentPassword.getText().toString().trim(), newPassword.getText().toString().trim(), confirmPassword.getText().toString().trim(), loader, dialog);
                    } else {

                        UtilMethods.INSTANCE.NetworkError(mActivity, mActivity.getResources().getString(R.string.err_msg_network_title), mActivity.getResources().getString(R.string.err_msg_network));
                    }
                }

            }
        });
        dialog.show();
    }

    public void forgetPassword(final boolean isForgotPass, boolean isCancelable) {
        if (dialog != null && dialog.isShowing()) {
            return;
        }
        LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.forgot_password, null);

        TextView title = view.findViewById(R.id.title);
        final TextInputLayout tilMobileFwp = view.findViewById(R.id.til_mobile_fwp);
        final AutoCompleteTextView edMobileFwp = view.findViewById(R.id.ed_mobile_fwp);

        final AppCompatButton okButton = view.findViewById(R.id.okButton);
        final AppCompatButton cancelButton = view.findViewById(R.id.cancelButton);
        if (isCancelable) {
            cancelButton.setVisibility(View.VISIBLE);
        } else {
            cancelButton.setVisibility(View.GONE);
        }
        dialog = new Dialog(mActivity, R.style.alert_dialog_light);

        dialog.setTitle("Forgot Password");
        dialog.setCancelable(isCancelable);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        cancelButton.setOnClickListener(v -> dialog.dismiss());
        okButton.setOnClickListener(v -> {
            int flag = 0;
            if (edMobileFwp.getText().toString().trim().isEmpty()) {
                flag++;
                tilMobileFwp.setError(mActivity.getString(R.string.err_msg_mobile));
            } else if (edMobileFwp.getText() != null && edMobileFwp.getText().toString().trim().length() < 10) {
                flag++;
                tilMobileFwp.setError(mActivity.getString(R.string.err_msg_mobile_length));
            } else if (!(UtilMethods.INSTANCE.isValidMobile(edMobileFwp.getText().toString().trim()) && (edMobileFwp.getText().toString().trim().charAt(0) == '6' || edMobileFwp.getText().toString().trim().charAt(0) == '7' || edMobileFwp.getText().toString().trim().charAt(0) == '8' || edMobileFwp.getText().toString().trim().charAt(0) == '9'))) {
                flag++;
                tilMobileFwp.setError(mActivity.getString(R.string.err_msg_valid_mobile));
            } else if ((edMobileFwp.getText() != null && edMobileFwp.getText().toString().trim().length() > 0 && UtilMethods.INSTANCE.isValidMobile(edMobileFwp.getText().toString().trim()) && !(edMobileFwp.getText().toString().trim().length() < 10) && (edMobileFwp.getText().toString().trim().charAt(0) == '6' || edMobileFwp.getText().toString().trim().charAt(0) == '7' || edMobileFwp.getText().toString().trim().charAt(0) == '8' || edMobileFwp.getText().toString().trim().charAt(0) == '9'))) {
                tilMobileFwp.setErrorEnabled(false);
                okButton.setEnabled(true);
            }
            if (flag == 0) {
                if (UtilMethods.INSTANCE.isNetworkAvialable(mActivity)) {
                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(false);
                    UtilMethods.INSTANCE.ForgotPass(mActivity, edMobileFwp.getText().toString().trim(),"","",UtilMethods.getLattitude + "", UtilMethods.getLongitude + "", loader,null);
                } else {
                    UtilMethods.INSTANCE.NetworkError(mActivity, mActivity.getResources().getString(R.string.err_msg_network_title), mActivity.getResources().getString(R.string.err_msg_network));
                }
            }
        });
        dialog.show();
    }
}
