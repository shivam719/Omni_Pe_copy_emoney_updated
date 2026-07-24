package com.fintech.omnipe.Activities.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;


import com.fintech.omnipe.Api.Object.TargetAchieved;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.CustomAlertDialog;

import java.util.List;

public class AchievedTargetAdapter extends RecyclerView.Adapter<AchievedTargetAdapter.MyViewHolder> {

    CustomAlertDialog mCustomAlertDialog;
    private List<TargetAchieved> operatorList;
    private Activity mContext;

    public AchievedTargetAdapter(List<TargetAchieved> operatorList, Activity mContext) {
        this.operatorList = operatorList;
        this.mContext = mContext;
        mCustomAlertDialog = new CustomAlertDialog(mContext, true);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_achieved_target, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final TargetAchieved operator = operatorList.get(position);
        holder.service.setText(operator.getService()+"");

        //  holder.todaySales.setText(operator.getIfscCode());
        int remainigTarget = (int) (operator.getTarget() - operator.getTargetTillDate());
        if(remainigTarget<0){
            holder.remainTarget.setTextColor(mContext.getResources().getColor(R.color.darkGreen));
        }
        holder.remainTarget.setText(mContext.getResources().getString(R.string.rupiya) + " " +  remainigTarget);
        holder.salesTarget.setText(mContext.getResources().getString(R.string.rupiya) + " " + (int)operator.getTarget());
        holder.todaySales.setText(mContext.getResources().getString(R.string.rupiya) + " " + (int) operator.getTodaySale());
    }

    @Override
    public int getItemCount() {
        return operatorList.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {

        private AppCompatTextView service;
        private AppCompatTextView salesTarget;
        private AppCompatTextView todaySales;
        private AppCompatTextView remainTarget;



        public MyViewHolder(View view) {
            super(view);
            service = view.findViewById(R.id.service);
            salesTarget = view.findViewById(R.id.salesTarget);
            todaySales = view.findViewById(R.id.todaySales);
            remainTarget = view.findViewById(R.id.remainTarget);
        }
    }
}





