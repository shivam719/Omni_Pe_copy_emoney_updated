package com.fintech.omnipe.AEPS.FingPay.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.fintech.omnipe.AEPS.FingPay.dto.MiniStatements;
import com.fintech.omnipe.R;

import java.util.ArrayList;


public class AEPSMiniStatementAdapter extends RecyclerView.Adapter<AEPSMiniStatementAdapter.MyViewHolder> {


    private ArrayList<MiniStatements> mList;
    private Context mContext;


    public AEPSMiniStatementAdapter(ArrayList<MiniStatements> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_mini_statement, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final MiniStatements operator = mList.get(position);
        holder.date.setText(operator.getTransactionDate());
        holder.narration.setText(operator.getNarration());
        holder.amt.setText(operator.getAmount() + "");

        if (operator.getTransactionType().toLowerCase().equalsIgnoreCase("cr")) {
            holder.amt.setTextColor(Color.DKGRAY);
        } else {
            holder.amt.setTextColor(Color.RED);
        }
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView date, amt, narration;
        View itemView;

        public MyViewHolder(View view) {
            super(view);
            itemView = view;
            date = view.findViewById(R.id.date);
            amt = view.findViewById(R.id.amt);
            narration = view.findViewById(R.id.narration);

        }
    }

}



