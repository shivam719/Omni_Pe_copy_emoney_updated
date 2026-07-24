package com.fintech.omnipe.Activities.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;


import com.fintech.omnipe.CommissionSlab.dto.SlabRangeDetail;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.UtilMethods;

import java.util.List;


public class CommissionChargeDetailAdapter extends RecyclerView.Adapter<CommissionChargeDetailAdapter.MyViewHolder> {

    private List<SlabRangeDetail> transactionsList;
    private Context mContext;

    public CommissionChargeDetailAdapter(List<SlabRangeDetail> transactionsList, Context mContext) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_slab_range_details, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final SlabRangeDetail operator = transactionsList.get(position);
        holder.range.setText(operator.getMinRange() + " - " + operator.getMaxRange());
        String comType, amtType;
        if (operator.isCommType()) {
            comType = "(SUR.)";

        } else {
            comType = "(COMM.)";
        }

        if (operator.isAmtType()) {
            amtType = mContext.getResources().getString(R.string.rupiya) + " " + UtilMethods.INSTANCE.formatedAmount(operator.getComm() + "");


        } else {
            amtType = UtilMethods.INSTANCE.formatedAmount(operator.getComm() + "") + " %";
        }

        holder.operator.setText(operator.getOperator() + "");
        holder.comSur.setText(amtType + " " + comType);
        
    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public AppCompatTextView comSur, operator, range;
        public View fixedCommView;


        public MyViewHolder(View view) {
            super(view);
            operator = (AppCompatTextView) view.findViewById(R.id.tv_maxComm);
            comSur = (AppCompatTextView) view.findViewById(R.id.tv_comm);
            range = (AppCompatTextView) view.findViewById(R.id.tv_minMax);
            fixedCommView = view.findViewById(R.id.fixedCommView);
            fixedCommView.setVisibility(View.GONE);
        }
    }

}