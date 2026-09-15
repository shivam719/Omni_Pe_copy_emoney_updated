package com.fintech.omnipe.DMTNew.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.fintech.omnipe.Api.Response.BenisObject;
import com.fintech.omnipe.DMTNew.networkAPI.UtilsMethodDMTNew;
import com.fintech.omnipe.DMTNew.ui.BeneficiaryListScreenNew;
import com.fintech.omnipe.DMTNew.ui.MoneyTransferNew;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.GetLocation;
import com.fintech.omnipe.Util.UtilMethods;
import com.fintech.omnipe.usefull.CustomLoader;

import java.util.ArrayList;

public class BeneficiaryAdapterNew extends RecyclerView.Adapter<BeneficiaryAdapterNew.MyViewHolder> {

    int resourceId = 0;
    CustomLoader loader;
    Dialog dialog = null;
    Activity activity;
    int oid;
    String sid,senderNumber;
    private ArrayList<BenisObject> operatorList;
    GetLocation mGetLocation;

    public BeneficiaryAdapterNew(ArrayList<BenisObject> operatorList, Activity activity, int oid, String sid,String senderNumber,CustomLoader loader,GetLocation mGetLocation) {
        this.operatorList = operatorList;

        this.activity = activity;
        this.oid = oid;
        this.sid = sid;
        this.senderNumber = senderNumber;
        this.loader = loader;
        this.mGetLocation = mGetLocation;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.beneficiary_list_adapter, parent, false);



        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final BenisObject operator = operatorList.get(position);
        holder.beneName.setText(operator.getBeneName());
        holder.beneAccountNumber.setText(operator.getAccountNo());
        holder.beneBank.setText(operator.getBankName());
        holder.beneIFSC.setText(operator.getIfsc());

        /*holder.beneName.setText(operator.getNAME());
        holder.beneAccountNumber.setText(operator.getACCOUNT());
        holder.beneBank.setText(operator.getBANK());
        holder.beneIFSC.setText(operator.getIFSC());*/

        holder.transferLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent transferIntent = new Intent(activity, MoneyTransferNew.class);
                transferIntent.putExtra("OID", oid);
                transferIntent.putExtra("SID", sid);
                transferIntent.putExtra("name", operatorList.get(position).getBeneName());
                transferIntent.putExtra("bankAccount", operatorList.get(position).getAccountNo());
                transferIntent.putExtra("bank", operatorList.get(position).getBankName());
                transferIntent.putExtra("bankId", operatorList.get(position).getBankID());
                transferIntent.putExtra("beneficiaryCode", operatorList.get(position).getBeneID());
                transferIntent.putExtra("beneficiaryMobile", operatorList.get(position).getMobileNo());
                transferIntent.putExtra("ifsc", operatorList.get(position).getIfsc());
                transferIntent.putExtra("SenderNumber",senderNumber);
                activity.startActivity(transferIntent);
                activity.finish();


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
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.confirm_dialog, null);

        final TextView name = (TextView) view.findViewById(R.id.name);
        final TextView number = (TextView) view.findViewById(R.id.number);
        final AppCompatButton okButton = (AppCompatButton) view.findViewById(R.id.okButton);
        final AppCompatButton cancelButton = (AppCompatButton) view.findViewById(R.id.cancelButton);
        final LinearLayout llNumber = (LinearLayout) view.findViewById(R.id.ll_number);

        okButton.setBackgroundColor(okButton.getContext().getResources().getColor(R.color.colorPrimary));
        cancelButton.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
        okButton.setTextColor(activity.getResources().getColor(R.color.white));
        cancelButton.setTextColor(activity.getResources().getColor(R.color.white));
        name.setText("" + beneName);
        number.setText("" + beneNumber);
        dialog = new Dialog(activity);// R.style.alert_dialog_light
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
                dialog.dismiss();

                if (UtilMethods.INSTANCE.isNetworkAvialable(activity)) {
                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(false);
                    UtilsMethodDMTNew.INSTANCE.deleteBeneficiary(activity, oid, sid, senderNumber, id, loader,mGetLocation, new UtilsMethodDMTNew.ApiCallBack() {
                        @Override
                        public void onSuccess(Object object) {
                             if (activity instanceof BeneficiaryListScreenNew) {
                                ((BeneficiaryListScreenNew) activity).refresh();
                            }
                        }
                    });
                } else {
                    UtilMethods.INSTANCE.NetworkError(activity);
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
        public TextView beneName;
        public TextView beneAccountNumber;
        public TextView beneBank;
        public TextView beneIFSC;

        public RelativeLayout transferLayout;
        public RelativeLayout deleteLayout;


        public MyViewHolder(View view) {
            super(view);
            beneName = (TextView) view.findViewById(R.id.beneName);
            beneAccountNumber = (TextView) view.findViewById(R.id.beneAccountNumber);
            beneBank = (TextView) view.findViewById(R.id.beneBank);
            beneIFSC = (TextView) view.findViewById(R.id.beneIFSC);
            transferLayout = (RelativeLayout) view.findViewById(R.id.transferLayout);
            deleteLayout = (RelativeLayout) view.findViewById(R.id.deleteLayout);

        }
    }


}
