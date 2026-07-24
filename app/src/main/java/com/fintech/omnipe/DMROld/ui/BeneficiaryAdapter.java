package com.fintech.omnipe.DMROld.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fintech.omnipe.Api.Response.BenisObject;
import com.fintech.omnipe.Api.Response.RechargeReportResponse;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.ApplicationConstant;
import com.fintech.omnipe.Util.UtilMethods;
import com.fintech.omnipe.usefull.CustomLoader;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;



public class BeneficiaryAdapter extends RecyclerView.Adapter<BeneficiaryAdapter.MyViewHolder> {


    CustomLoader loader;
    Dialog dialog = null;
    Activity activity;
    private ArrayList<BenisObject> operatorList;
    private Activity mContext;

    public BeneficiaryAdapter(ArrayList<BenisObject> operatorList, Activity mContext, Activity activity) {
        this.operatorList = operatorList;
        this.mContext = mContext;
        this.activity = activity;
    }

    @Override
    public BeneficiaryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.beneficiary_list_adapter, parent, false);

        loader = new CustomLoader(mContext, android.R.style.Theme_Translucent_NoTitleBar);

        return new BeneficiaryAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final BeneficiaryAdapter.MyViewHolder holder, final int position) {
        final BenisObject operator = operatorList.get(position);
        holder.beneName.setText(operator.getBeneName());
        holder.beneAccountNumber.setText(operator.getAccountNo());
        holder.beneBank.setText(operator.getBankName());
        holder.beneIFSC.setText(operator.getIfsc());

         if(!operator.isVerified()){
          holder.sendmoney.setText("Verify Beneficiary");
         }

        holder.transferLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(operator.isVerified()){
                    Intent transferIntent = new Intent(mContext, MoneyTransfer.class);
                    transferIntent.putExtra("name", operatorList.get(position).getBeneName());
                    transferIntent.putExtra("bankAccount", operatorList.get(position).getAccountNo());
                    transferIntent.putExtra("bank", operatorList.get(position).getBankName());
                    transferIntent.putExtra("beneficiaryCode", operatorList.get(position).getBeneID());
                    transferIntent.putExtra("benemobile", operatorList.get(position).getMobileNo());
                    transferIntent.putExtra("bankId", operatorList.get(position).getBankID());
                    transferIntent.putExtra("ifsc", operatorList.get(position).getIfsc());
                    mContext.startActivity(transferIntent);
                    activity.finish();
                }else {
                    SharedPreferences prefs = activity.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, activity.MODE_PRIVATE);
                    String senderno = prefs.getString(ApplicationConstant.INSTANCE.senderNumberPref, null);//
                    UtilMethods.INSTANCE.GenerateBeneficiaryOTP(activity, senderno, new UtilMethods.ApiCallBack() {
                        @Override
                        public void onSucess(Object object) {

                            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            View view = inflater.inflate(R.layout.verifyotp, null);

                            final TextInputLayout otpTextLayout = (TextInputLayout) view.findViewById(R.id.til_mobile_otp);
                            final EditText otp = (EditText) view.findViewById(R.id.ed_mobile_otp);
                            final AppCompatButton okButton = (AppCompatButton) view.findViewById(R.id.okButton);
                            final AppCompatButton cancelButton = (AppCompatButton) view.findViewById(R.id.cancelButton);

                            final Dialog dialog = new Dialog(activity);

                            dialog.setCancelable(false);
                            dialog.setContentView(view);
                            okButton.setEnabled(true);

                            cancelButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });


                            okButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    if (!otp.getText().toString().isEmpty()) {

                                        UtilMethods.INSTANCE.ValidateBeneficiary(activity, operator.getMobileNo(), operator.getAccountNo(), otp.getText().toString().trim(), new UtilMethods.ApiCallBack() {
                                            @Override
                                            public void onSucess(Object object) {
                                                RechargeReportResponse data = (RechargeReportResponse) object;
                                                dialog.dismiss();
                                                UtilMethods.INSTANCE.Successful(activity, data.getMsg() + "");

                                                SharedPreferences prefs = activity.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, Context.MODE_PRIVATE);
                                                loader.isShowing();
                                                loader.setCancelable(false);

                                                UtilMethods.INSTANCE.GetBeneficiary(activity, prefs.getString(ApplicationConstant.INSTANCE.senderNumberPref, ""), loader, new UtilMethods.ApiCallBack() {
                                                    @Override
                                                    public void onSucess(Object object) {
                                                        if(loader!=null && loader.isShowing())
                                                            loader.dismiss();
                                                        notifyDataSetChanged();
                                                    }
                                                });
                                            }
                                        }, loader);
                                    } else {
                                        otpTextLayout.setError("Enter a valid otp!!");
                                        otpTextLayout.setErrorEnabled(true);
                                    }
                                }
                            });

                            dialog.show();
                        }
                    }, loader);
                }
            }
        });

        holder.deleteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmationDialog(operatorList.get(position).getBeneID(), operatorList.get(position).getBeneName(), ""/* operatorList.get(position).getMOBILENO()*/);
            }
        });
    }

    @Override
    public int getItemCount() {
        return operatorList.size();
    }

    public void confirmationDialog(final String id, final String beneName, final String beneNumber) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.confirm_dialog, null);

        final TextView name = view.findViewById(R.id.name);
        final TextView number = view.findViewById(R.id.number);
        final AppCompatButton okButton = view.findViewById(R.id.okButton);
        final AppCompatButton cancelButton = view.findViewById(R.id.cancelButton);
        final LinearLayout llNumber = view.findViewById(R.id.ll_number);

        okButton.setBackgroundColor(okButton.getContext().getResources().getColor(R.color.colorPrimary));
        cancelButton.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
        okButton.setTextColor(mContext.getResources().getColor(R.color.white));
        cancelButton.setTextColor(mContext.getResources().getColor(R.color.white));
        SharedPreferences prefs = mContext.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String senderNumber = prefs.getString(ApplicationConstant.INSTANCE.senderNumberPref, null);
        name.setText("" + beneName);
        number.setText("" + beneNumber);
        dialog = new Dialog(mContext);
        dialog.setCancelable(false);
        dialog.setContentView(view);
        if (beneNumber != null && beneNumber.length() > 0) {
            llNumber.setVisibility(View.VISIBLE);
        } else {
            llNumber.setVisibility(View.GONE);
        }
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = mContext.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
                String senderNumber = prefs.getString(ApplicationConstant.INSTANCE.senderNumberPref, null);
                if (UtilMethods.INSTANCE.isNetworkAvialable(mContext)) {
                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(false);
                    UtilMethods.INSTANCE.Deletebeneficiary(mContext, senderNumber, id, loader);
                } else {
                    UtilMethods.INSTANCE.NetworkError(mContext, mContext.getResources().getString(R.string.network_error_title),
                            mContext.getResources().getString(R.string.network_error_message));
                }
            }
        });
        dialog.show();
    }

    public void deleteDone() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView beneName,sendmoney;
        public TextView beneAccountNumber;
        public TextView beneBank;
        public TextView beneIFSC;

        public RelativeLayout transferLayout;
        public RelativeLayout deleteLayout;


        public MyViewHolder(View view) {
            super(view);
            beneName = view.findViewById(R.id.beneName);
            sendmoney = (TextView) view.findViewById(R.id.sendMoneyBtn);
            beneAccountNumber = view.findViewById(R.id.beneAccountNumber);
            beneBank = view.findViewById(R.id.beneBank);
            beneIFSC = view.findViewById(R.id.beneIFSC);
            transferLayout = view.findViewById(R.id.transferLayout);
            deleteLayout = view.findViewById(R.id.deleteLayout);

        }
    }


}
