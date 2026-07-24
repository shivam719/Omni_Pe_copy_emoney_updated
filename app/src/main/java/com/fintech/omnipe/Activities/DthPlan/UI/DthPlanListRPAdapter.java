package com.fintech.omnipe.Activities.DthPlan.UI;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fintech.omnipe.Activities.DthPlan.dto.PlanRPResponse;
import com.fintech.omnipe.R;

import java.util.List;


public class DthPlanListRPAdapter extends RecyclerView.Adapter<DthPlanListRPAdapter.MyViewHolder> {

    private List<PlanRPResponse> operatorList;
    private Context mContext;

    public DthPlanListRPAdapter(List<PlanRPResponse> operatorList, Context mContext) {
        this.operatorList = operatorList;
        this.mContext = mContext;
    }

    @Override
    public DthPlanListRPAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dth_plan_list, parent, false);

        return new DthPlanListRPAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final DthPlanListRPAdapter.MyViewHolder holder, final int position) {
        final PlanRPResponse operator = operatorList.get(position);

        holder.amount.setText(mContext.getResources().getString(R.string.rupiya) + " " + operator.getRechargeAmount());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((DthPlanInfoActivity) mContext).ItemClick("" + operator.getRechargeAmount(), "" + operator.getDetails());
            }
        });
        holder.planName.setText(operator.getRechargeValidity());
        if (operator.getDetails() != null && operator.getDetails().length() > 0)
            holder.description.setText(operator.getDetails());
        else
            holder.description.setText("N/A");

    }

    @Override
    public int getItemCount() {
        return operatorList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView planName;
        public TextView amount;
        public TextView description;
        View itemView;

        public MyViewHolder(View view) {
            super(view);
            itemView = view;
            planName = view.findViewById(R.id.planName);
            amount = view.findViewById(R.id.amount);
            description = view.findViewById(R.id.description);

        }
    }

}
