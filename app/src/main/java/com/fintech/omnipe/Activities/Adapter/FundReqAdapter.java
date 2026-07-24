package com.fintech.omnipe.Activities.Adapter;

import android.content.Context;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fintech.omnipe.Api.Response.FundOrderReportObject;
import com.fintech.omnipe.R;

import java.util.ArrayList;
import java.util.Locale;


public class FundReqAdapter extends RecyclerView.Adapter<FundReqAdapter.MyViewHolder> {
    String charText = "";
    private ArrayList<FundOrderReportObject> rechargeStatus;
    private ArrayList<FundOrderReportObject> transactionsList;
    private Context mContext;

    public FundReqAdapter(ArrayList<FundOrderReportObject> transactionsList, Context mContext) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
        this.rechargeStatus = new ArrayList<>();
        this.rechargeStatus.addAll(transactionsList);
    }

    @Override
    public FundReqAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fund_req_adapter, parent, false);

        return new FundReqAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final FundReqAdapter.MyViewHolder holder, int position) {
        final FundOrderReportObject operator = transactionsList.get(position);


        String bankDetails = "";
        if (operator.getBank() != null && !operator.getBank().isEmpty()) {
            bankDetails = bankDetails + operator.getBank();
        }
        if (operator.getAccountHolder() != null && !operator.getAccountHolder().isEmpty()) {
            bankDetails = bankDetails+"\n" + operator.getAccountHolder();
        }
        if (operator.getAccountNo() != null && !operator.getAccountNo().isEmpty()) {
            bankDetails = bankDetails+"\n" + operator.getAccountNo();
        }
        holder.from.setText(bankDetails);

        holder.balance.setText("" + mContext.getResources().getString(R.string.rupiya) + " " + operator.getAmount());
        holder.Amount.setText(" " + operator.getTransactionId());
        holder.mode.setText(operator.getMode());
        holder.date.setText(operator.getEntryDate());
        holder.tid.setText(operator.getStatus());
//        String faqsearchDescription = operator.getDescription().toLowerCase(Locale.getDefault()) + "";
//        if (faqsearchDescription.contains(charText)) {
//            int startPos = faqsearchDescription.indexOf(charText);
//            int endPos = startPos + charText.length();
//            Spannable spanText = Spannable.Factory.getInstance().newSpannable(operator.getDescription()); // <- EDITED: Use the original string, as `country` has been converted to lowercase.
//            spanText.setSpan(new ForegroundColorSpan(Color.BLUE), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            spanText.setSpan(new StyleSpan(Typeface.ITALIC), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            holder.from.setText(spanText, TextView.BufferType.SPANNABLE);
//
//        } else {
//            if (operator.getDescription() != null)
//                holder.from.setText(" " + operator.getDescription() + "");
//            else
//                holder.from.setText("Fund Recieved from Admin");
//        }
    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

    // Filter Class
    public void filter(String charText) {
        this.charText = charText.toLowerCase(Locale.getDefault());

        transactionsList.clear();
        if (charText.length() == 0) {
            transactionsList.addAll(rechargeStatus);
        } else {
            for (FundOrderReportObject wp : rechargeStatus) {
                if (wp.getDescription().toLowerCase(Locale.getDefault()).contains(charText)) {
                    transactionsList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public AppCompatTextView from, fromLabel, balance, date, tidLabel, tid, Amount, mode;


        public MyViewHolder(View view) {
            super(view);
            from = view.findViewById(R.id.from);
            fromLabel = view.findViewById(R.id.fromLabel);
            balance = view.findViewById(R.id.balance);
            date = view.findViewById(R.id.date);
            tidLabel = view.findViewById(R.id.tidLabel);
            tid = view.findViewById(R.id.tid);
            Amount = view.findViewById(R.id.Amount);
            mode = view.findViewById(R.id.mode);
        }
    }
}
