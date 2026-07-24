package com.fintech.omnipe.MoveToWallet.UI;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;


import com.fintech.omnipe.Auth.dto.LoginResponse;
import com.fintech.omnipe.MoveToWallet.Api.MoveToWalletReportData;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.CustomAlertDialog;
import com.fintech.omnipe.Util.UtilMethods;

import java.util.ArrayList;
import java.util.Locale;


public class MoveToBankReportAdapter extends RecyclerView.Adapter<MoveToBankReportAdapter.MyViewHolder> {

    public Button okButton;
    public Button cancelButton;
    private ArrayList<MoveToWalletReportData> transactionsList;
    private ArrayList<MoveToWalletReportData> rechargeStatus;
    private Activity mContext;
    CustomAlertDialog mCustomAlertDialog;
    LoginResponse mLoginDataResponse;

    public MoveToBankReportAdapter(ArrayList<MoveToWalletReportData> transactionsList, Activity mContext, LoginResponse mLoginDataResponse) {
        this.transactionsList = transactionsList;
        this.rechargeStatus = transactionsList;
        this.mContext = mContext;
        this.mLoginDataResponse = mLoginDataResponse;
        mCustomAlertDialog = new CustomAlertDialog(mContext,true);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_movetobank_report, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final MoveToWalletReportData operator = transactionsList.get(position);

        holder.debit.setText(UtilMethods.INSTANCE.formatedAmountWithRupees(operator.getAmount() + ""));
        holder.charges.setText(UtilMethods.INSTANCE.formatedAmountWithRupees(operator.getCharge() + ""));

        holder.bankName.setText( operator.getBankName() + " ("+operator.getIfsc()+")");
        holder.transactionMode.setText("" + operator.getTransMode() + "");


        holder.outletName.setText("" + operator.getUserName());
        holder.outletNo.setText("" + operator.getMobile());
        holder.accountNumber.setText("" + operator.getAccountNumber());
        holder.operatorName.setText("" + operator.getAccountHolder());
        holder.txnId.setText("" + operator.getTransactionId());


        holder.liveID.setText("" + operator.getBankRRN() + "");

        holder.createdDate.setText("" + operator.getEntryDate());
        holder.acceptRejectDate.setText("" + operator.getApprovalDate());
        holder.source.setText("" + operator.getRemark()+"");


        if (operator.getStatus() == 1) {
            holder.share.setVisibility(View.GONE);
            //  holder.print.setVisibility(View.GONE);
            holder.status.setText("Requested");
            holder.acceptRejectDateLabel.setText("Requested Date");
            holder.statusIcon.setImageResource(R.drawable.ic_pending);
            holder.status.setTextColor(mContext.getResources().getColor(R.color.color_amber));
        } else if (operator.getStatus() == 2) {
            holder.share.setVisibility(View.VISIBLE);
            //  holder.print.setVisibility(View.VISIBLE);
            holder.status.setText("Approved");
            holder.acceptRejectDateLabel.setText("Approved Date");
            holder.statusIcon.setImageResource(R.drawable.ic_succsess);
            holder.status.setTextColor(mContext.getResources().getColor(R.color.green));
        } else if (operator.getStatus() == 3) {
            holder.share.setVisibility(View.GONE);
            //  holder.print.setVisibility(View.GONE);
            holder.status.setText("Rejected");
            holder.acceptRejectDateLabel.setText("Rejected Date");
            holder.statusIcon.setImageResource(R.drawable.failure_icon);
            holder.status.setTextColor(mContext.getResources().getColor(R.color.color_red));
        } else {
            holder.status.setText("Not Applied");
            holder.acceptRejectDateLabel.setText("Not Applied Date");
            holder.share.setVisibility(View.GONE);
            //   holder.print.setVisibility(View.GONE);
            holder.status.setTextColor(mContext.getResources().getColor(R.color.bottommenu));
        }

    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView debit;;
        private AppCompatTextView bankName;
        private AppCompatTextView accountNumber;
        private AppCompatTextView operatorName;
        private AppCompatTextView txnId;
        private AppCompatTextView transactionMode;
        private AppCompatTextView liveID;
        private AppCompatTextView outletName;
        private AppCompatTextView outletNo;
        private AppCompatTextView createdDate,charges;
        private AppCompatTextView acceptRejectDateLabel;
        private AppCompatTextView acceptRejectDate;
        private LinearLayout slabCommView;
        private AppCompatTextView source;
        private LinearLayout share;
        ImageView statusIcon;
        private AppCompatTextView status;


        public MyViewHolder(View view) {
            super(view);
            debit = (AppCompatTextView) view.findViewById(R.id.debit);
            bankName = (AppCompatTextView) view.findViewById(R.id.bankName);
            accountNumber = (AppCompatTextView) view.findViewById(R.id.accountNumber);
            operatorName = (AppCompatTextView) view.findViewById(R.id.operatorName);
            txnId = (AppCompatTextView) view.findViewById(R.id.txnId);
            transactionMode = (AppCompatTextView) view.findViewById(R.id.transactionMode);
            liveID = (AppCompatTextView) view.findViewById(R.id.liveID);charges = (AppCompatTextView) view.findViewById(R.id.charges);
            outletName = (AppCompatTextView) view.findViewById(R.id.outletName);
            outletNo = (AppCompatTextView) view.findViewById(R.id.outletNo);
            createdDate = (AppCompatTextView) view.findViewById(R.id.createdDate);
            acceptRejectDateLabel = (AppCompatTextView) view.findViewById(R.id.acceptRejectDateLabel);
            acceptRejectDate = (AppCompatTextView) view.findViewById(R.id.acceptRejectDate);
            slabCommView = (LinearLayout) view.findViewById(R.id.slabCommView);
            slabCommView.setVisibility(View.GONE);
            source = (AppCompatTextView) view.findViewById(R.id.source);
            share = view.findViewById(R.id.share);
            status = (AppCompatTextView) view.findViewById(R.id.status);
            statusIcon = view.findViewById(R.id.statusIcon);

        }
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());

        ArrayList<MoveToWalletReportData> filterList = new ArrayList<>();
        if (charText.length() == 0) {
            filterList.addAll(rechargeStatus);
        } else {
            for (MoveToWalletReportData wp : rechargeStatus) {
                if ((wp.getBankRRN() + "").toLowerCase(Locale.getDefault()).contains(charText) ||
                        (wp.getAccountNumber() + "").toLowerCase(Locale.getDefault()).contains(charText) ||
                        (wp.getTransactionId() + "").toLowerCase(Locale.getDefault()).contains(charText) ||
                        (wp.getMobile() + "").toLowerCase(Locale.getDefault()).contains(charText) ||
                        (wp.getUserName() + "").toLowerCase(Locale.getDefault()).contains(charText) ||
                        (wp.getTransMode() + "").toLowerCase(Locale.getDefault()).contains(charText) ||
                        (wp.getBankName() + "").toLowerCase(Locale.getDefault()).contains(charText) ||
                        (wp.getAmount() + "").toLowerCase(Locale.getDefault()).contains(charText) ||
                        (wp.getCharge() + "").toLowerCase(Locale.getDefault()).contains(charText) ||
                        (wp.getIfsc() + "").toLowerCase(Locale.getDefault()).contains(charText) ||
                        (wp.getTid() + "").toLowerCase(Locale.getDefault()).contains(charText) ) {
                    filterList.add(wp);
                }
            }
        }

        transactionsList = filterList;
        notifyDataSetChanged();
    }
}
