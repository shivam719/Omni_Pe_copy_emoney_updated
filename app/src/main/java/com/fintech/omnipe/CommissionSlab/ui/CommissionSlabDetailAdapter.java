package com.fintech.omnipe.CommissionSlab.ui;

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


public class CommissionSlabDetailAdapter extends RecyclerView.Adapter<CommissionSlabDetailAdapter.MyViewHolder> {

    private List<SlabRangeDetail> transactionsList;
    private Context mContext;
    private View maxCommView,fixedCommView;

    public CommissionSlabDetailAdapter(List<SlabRangeDetail> transactionsList, Context mContext, View maxCommView, View fixedCommView) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
        this.fixedCommView = fixedCommView;
        this.maxCommView = maxCommView;
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
            amtType = UtilMethods.INSTANCE.formatedAmountWithRupees(operator.getComm() + "");
        } else {
            amtType = UtilMethods.INSTANCE.formatedAmount(operator.getComm() + "") + " %";
        }

        if(operator.getDmrModelID()==2 ||operator.getDmrModelID()==3||
                operator.getDmrModelID()==4){
            holder.fixedCommadapterView.setVisibility(View.VISIBLE);
            //holder.fixed.setBackgroundColor(mContext.getResources().getColor(R.color.white));
            fixedCommView.setVisibility(View.VISIBLE);
            holder.maxCommadapterView.setVisibility(View.GONE);
            maxCommView.setVisibility(View.GONE);
        }else if(operator.getDmrModelID()==1){
            holder.fixedCommadapterView.setVisibility(View.GONE);
            holder.maxCommadapterView.setVisibility(View.GONE);
            maxCommView.setVisibility(View.GONE);
            fixedCommView.setVisibility(View.GONE);
            //holder.maxCom.setBackgroundColor(mContext.getResources().getColor(R.color.white));
            //holder.maxCom.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }else{
            holder.fixedCommadapterView.setVisibility(View.GONE);
            fixedCommView.setVisibility(View.GONE);
            holder.maxCommadapterView.setVisibility(View.VISIBLE);
            //holder.maxCom.setBackground(mContext.getResources().getColor(R.color.white),null);
            //holder.maxCom.setBackgroundColor(mContext.getResources().getColor(R.color.white));
            maxCommView.setVisibility(View.VISIBLE);
        }

        holder.comSur.setText(amtType + " " + comType);
        //holder.fixed.setText(operator.getFixedCharge() + "");
        holder.fixed.setText(UtilMethods.INSTANCE.formatedAmountWithRupees(operator.getFixedCharge() + ""));
        holder.maxCom.setText(UtilMethods.INSTANCE.formatedAmount(operator.getMaxComm() + ""));



    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public AppCompatTextView comSur, maxCom, range, fixed;
        public View fixedCommadapterView,maxCommadapterView;


        public MyViewHolder(View view) {
            super(view);
            fixed = (AppCompatTextView) view.findViewById(R.id.tv_fixedComm);
            comSur = (AppCompatTextView) view.findViewById(R.id.tv_comm);
            maxCom = (AppCompatTextView) view.findViewById(R.id.tv_maxComm);
            range = (AppCompatTextView) view.findViewById(R.id.tv_minMax);
            fixedCommadapterView = view.findViewById(R.id.fixedCommView);
            maxCommadapterView = view.findViewById(R.id.maxCommView);
        }
    }

}