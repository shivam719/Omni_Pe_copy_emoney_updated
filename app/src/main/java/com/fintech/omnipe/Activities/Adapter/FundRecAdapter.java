package com.fintech.omnipe.Activities.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fintech.omnipe.Api.Response.FundDCObject;
import com.fintech.omnipe.R;

import java.util.ArrayList;
import java.util.Locale;


public class FundRecAdapter extends RecyclerView.Adapter<FundRecAdapter.MyViewHolder> {
    public int Prepaid = 1;
    public int Utility = 2;
    public int Bank = 3;
    public int Card = 4;
    public int RegID = 5;
    public int Package = 6;
    String charText = "";
    String ServiceType;
    private ArrayList<FundDCObject> rechargeStatus;
    private ArrayList<FundDCObject> transactionsList;
    private Context mContext;

    public FundRecAdapter(ArrayList<FundDCObject> transactionsList, Context mContext, String ServiceType) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
        this.rechargeStatus = new ArrayList<>();
        this.rechargeStatus.addAll(transactionsList);
        this.ServiceType = ServiceType;
    }

    @Override
    public FundRecAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fund_rec_adapter, parent, false);

        return new FundRecAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final FundRecAdapter.MyViewHolder holder, int position) {
        final FundDCObject operator = transactionsList.get(position);

        holder.fromLabel.setText("Description : ");
        holder.from.setText(" " + operator.getDescription() + "");
        holder.balance.setText("" + mContext.getResources().getString(R.string.rupiya) + " " + operator.getCurrentAmount());
        holder.Amount.setText("" + mContext.getResources().getString(R.string.rupiya) + " " + operator.getAmount() + "");
        holder.date.setText(operator.getEntryDate() + "");

        holder.receivedByLabel.setText(ServiceType + " : ");

        holder.receivedBy.setText(operator.getOtherUser());
        holder.walletType.setText(GetWalletType(operator.getWalletID()));
        if (operator.getRemark() != null && !operator.getRemark().isEmpty()) {
            holder.remark.setVisibility(View.VISIBLE);
            holder.remarkLabel.setVisibility(View.VISIBLE);
            holder.remark.setText(operator.getRemark() + "");
        } else {
            holder.remark.setVisibility(View.GONE);
            holder.remarkLabel.setVisibility(View.GONE);
        }
        holder.tidLabel.setText("TID : ");
        holder.tid.setText(operator.getTransactionID());
        String faqsearchDescription = operator.getDescription().toLowerCase(Locale.getDefault()) + "";
        if (faqsearchDescription.contains(charText)) {
            int startPos = faqsearchDescription.indexOf(charText);
            int endPos = startPos + charText.length();
            Spannable spanText = Spannable.Factory.getInstance().newSpannable(operator.getDescription()); // <- EDITED: Use the original string, as `country` has been converted to lowercase.
            spanText.setSpan(new ForegroundColorSpan(Color.BLUE), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spanText.setSpan(new StyleSpan(Typeface.ITALIC), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.from.setText(spanText, TextView.BufferType.SPANNABLE);

        } else {
            holder.from.setText(" " + operator.getDescription() + "");
        }
    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

    public String GetWalletType(int T) {
        if (T == Prepaid)
            return "Prepaid";
        if (T == Utility)
            return "Utility";
        if (T == Bank)
            return "Bank";
        if (T == Card)
            return "Card";
        if (T == RegID)
            return "RegID";
        if (T == Package)
            return "Package";
        return "";
    }

    // Filter Class
    public void filter(String charText) {
        this.charText = charText.toLowerCase(Locale.getDefault());

        transactionsList.clear();
        if (charText.length() == 0) {
            transactionsList.addAll(rechargeStatus);
        } else {
            for (FundDCObject wp : rechargeStatus) {
                if (wp.getDescription().toLowerCase(Locale.getDefault()).contains(charText)) {
                    transactionsList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public AppCompatTextView remarkLabel, remark, from, fromLabel, balance, date, tidLabel, tid, Amount, receivedByLabel, receivedBy, walletType;


        public MyViewHolder(View view) {
            super(view);
            from = view.findViewById(R.id.from);
            fromLabel = view.findViewById(R.id.fromLabel);
            balance = view.findViewById(R.id.balance);
            date = view.findViewById(R.id.date);
            tidLabel = view.findViewById(R.id.tidLabel);
            tid = view.findViewById(R.id.tid);
            Amount = view.findViewById(R.id.Amount);
            remarkLabel = view.findViewById(R.id.remarkLabel);
            remark = view.findViewById(R.id.remark);
            receivedByLabel = view.findViewById(R.id.receivedByLabel);
            receivedBy = view.findViewById(R.id.receivedBy);
            walletType = view.findViewById(R.id.walletType);
        }
    }
}
