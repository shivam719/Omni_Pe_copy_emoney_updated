package com.fintech.omnipe.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fintech.omnipe.Api.Response.DmtReportObject;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.UtilMethods;
import com.fintech.omnipe.usefull.CustomLoader;

import java.util.ArrayList;


public class DMRReportAdapter extends RecyclerView.Adapter<DMRReportAdapter.MyViewHolder> {

    ProgressDialog mProgressDialog = null;
    private ArrayList<DmtReportObject> transactionsList;
    private Activity mContext;

    public DMRReportAdapter(ArrayList<DmtReportObject> transactionsList, Activity mContext) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
    }

    @Override
    public DMRReportAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dmr_report_adapter, parent, false);

        mProgressDialog = new ProgressDialog(mContext);

        return new DMRReportAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final DMRReportAdapter.MyViewHolder holder, int position) {
        final DmtReportObject operator = transactionsList.get(position);
        holder.status.setText("" + operator.getType_());
        holder.opening.setText("" + operator.getOpening());
        holder.amount.setText("" + operator.getRequestedAmount());
        holder.debit.setText("" + operator.getAmount());
        holder.balance.setText("" + operator.getBalance());
        holder.bankName.setText("" + operator.getOptional1());
        holder.outletName.setText("" + operator.getOutlet());
        holder.accountNumber.setText("" + operator.getAccount());
        holder.operatorName.setText("" + operator.getOperator() + "( " + operator.getOptional4() + " }");
        holder.operatorId.setText("TxID : " + operator.getTransactionID());
        holder.operatorliveId.setText("LiveID : " + operator.getLiveID());
        holder.createdDate.setText("" + operator.getEntryDate());
        holder.responseDate.setText("" + operator.getModifyDate());
        holder.senderNo.setText("SenderNo.: " + operator.getOptional2());
        holder.source.setText("Source : " + operator.getRequestMode());
        holder.action.setText("Dispute");
        if (operator.getRefundStatus().equalsIgnoreCase("1")) {
            holder.action.setVisibility(View.VISIBLE);
        } else {
            holder.action.setVisibility(View.GONE);
        }
        if (operator.get_Type().equals("2")) {
            holder.action.setVisibility(View.GONE);
            holder.share.setVisibility(View.VISIBLE);
        } else {
            holder.action.setVisibility(View.GONE);
            holder.share.setVisibility(View.GONE);
        }

        holder.action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (UtilMethods.INSTANCE.isNetworkAvialable(mContext)) {
//
//                    mProgressDialog.setIndeterminate(true);
//                    mProgressDialog.setMessage("Loading...");
//                    mProgressDialog.show();
//
//                    UtilMethods.INSTANCE.Refund(mContext, operator.getSenderMobileNo(), operator.getVenderID(), operator.getID(), mProgressDialog);
//                } else {
//                    UtilMethods.INSTANCE.NetworkError(mContext, mContext.getString(R.string.err_msg_network_title),
//                            mContext.getString(R.string.err_msg_network));
//                }
            }
        });
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UtilMethods.INSTANCE.isNetworkAvialable(mContext)) {

                    CustomLoader loader = new CustomLoader(mContext, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
                    loader.show();
                    UtilMethods.INSTANCE.GetDMTReceipt(mContext, operator.getGroupID(), "Specific", loader);
                } else {
                    UtilMethods.INSTANCE.NetworkError(mContext, mContext.getString(R.string.err_msg_network_title),
                            mContext.getString(R.string.err_msg_network));
                }


            }
        });
    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView remark, amount, bankName, status, outletName, accountNumber, source, senderNo, operatorName, operatorId, createdDate, responseDate, action, opening, debit, balance, operatorliveId;
        ImageView share;

        public MyViewHolder(View view) {
            super(view);
            amount = view.findViewById(R.id.amount);
            opening = view.findViewById(R.id.opening);
            debit = view.findViewById(R.id.debit);
            balance = view.findViewById(R.id.balance);
            status = view.findViewById(R.id.status);
            outletName = view.findViewById(R.id.outletName);
            accountNumber = view.findViewById(R.id.accountNumber);
            operatorName = view.findViewById(R.id.operatorName);
            operatorliveId = view.findViewById(R.id.operatorliveId);
            source = view.findViewById(R.id.source);
            senderNo = view.findViewById(R.id.senderNo);
            operatorId = view.findViewById(R.id.operatorId);
            createdDate = view.findViewById(R.id.createdDate);
            responseDate = view.findViewById(R.id.responseDate);
            action = view.findViewById(R.id.action);
            share = view.findViewById(R.id.share);
            bankName = view.findViewById(R.id.bankName);
        }
    }

}
