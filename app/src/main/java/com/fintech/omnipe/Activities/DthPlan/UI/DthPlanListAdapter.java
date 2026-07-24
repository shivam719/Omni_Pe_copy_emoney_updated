package com.fintech.omnipe.Activities.DthPlan.UI;

import android.content.Context;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fintech.omnipe.Activities.DthPlan.dto.PlanInfoPlan;
import com.fintech.omnipe.Activities.DthPlan.dto.PlanValidity;
import com.fintech.omnipe.R;

import java.util.ArrayList;
import java.util.List;


public class DthPlanListAdapter extends RecyclerView.Adapter<DthPlanListAdapter.MyViewHolder> {

    private List<PlanInfoPlan> operatorList;
    private Context mContext;

    public DthPlanListAdapter(List<PlanInfoPlan> operatorList, Context mContext) {
        this.operatorList = operatorList;
        this.mContext = mContext;
    }

    @Override
    public DthPlanListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dth_plan_list, parent, false);

        return new DthPlanListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final DthPlanListAdapter.MyViewHolder holder, final int position) {
        final PlanInfoPlan operator = operatorList.get(position);
        List<PlanValidity> mPlanValidities = new ArrayList<>();

        if (operator.getRs().get1MONTHS() != null && !operator.getRs().get1MONTHS().isEmpty()) {
            holder.planName.setVisibility(View.VISIBLE);
            holder.amount.setVisibility(View.VISIBLE);
            holder.amount.setText(mContext.getResources().getString(R.string.rupiya) + " " + operator.getRs().get1MONTHS().trim());
            mPlanValidities.add(new PlanValidity(operator.getRs().get1MONTHS(), "1 Month",operator.getDesc()));
        }
        if (operator.getRs().get3MONTHS() != null && !operator.getRs().get3MONTHS().isEmpty()) {
            holder.planName.setVisibility(View.GONE);
            holder.amount.setVisibility(View.GONE);
            mPlanValidities.add(new PlanValidity(operator.getRs().get3MONTHS(), "3 Months",operator.getDesc()));
        }
        if (operator.getRs().get6MONTHS() != null && !operator.getRs().get6MONTHS().isEmpty()) {
            holder.planName.setVisibility(View.GONE);
            holder.amount.setVisibility(View.GONE);
            mPlanValidities.add(new PlanValidity(operator.getRs().get6MONTHS(), "6 Months",operator.getDesc()));
        }
        if (operator.getRs().get9MONTHS() != null && !operator.getRs().get9MONTHS().isEmpty()) {
            holder.planName.setVisibility(View.GONE);
            holder.amount.setVisibility(View.GONE);
            mPlanValidities.add(new PlanValidity(operator.getRs().get9MONTHS(), "9 Months",operator.getDesc()));
        }
        if (operator.getRs().get1YEAR() != null && !operator.getRs().get1YEAR().isEmpty()) {
            holder.planName.setVisibility(View.GONE);
            holder.amount.setVisibility(View.GONE);
            mPlanValidities.add(new PlanValidity(operator.getRs().get1YEAR(), "1 Year",operator.getDesc()));
        }
        if (operator.getRs().get5YEAR() != null && !operator.getRs().get5YEAR().isEmpty()) {
            holder.planName.setVisibility(View.GONE);
            holder.amount.setVisibility(View.GONE);
            mPlanValidities.add(new PlanValidity(operator.getRs().get5YEAR(), "5 Years",operator.getDesc()));
        }

        if (mPlanValidities != null && mPlanValidities.size() > 1) {
            holder.rsGrid.setVisibility(View.VISIBLE);
            DthPlanValidityGridAdapter mDthPlanValidityGridAdapter = new DthPlanValidityGridAdapter(mContext, mPlanValidities);
            holder.rsGrid.setAdapter(mDthPlanValidityGridAdapter);
        }else{
            holder.rsGrid.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((DthPlanInfoActivity) mContext).ItemClick("" + operator.getRs().get1MONTHS(), "" + operator.getDesc());
            }
        });
        if (operator.getDesc() != null && operator.getDesc().length() > 0)
            holder.description.setText(operator.getDesc());
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
        RecyclerView rsGrid;
        View itemView;

        public MyViewHolder(View view) {
            super(view);
            itemView = view;
            planName = view.findViewById(R.id.planName);
            amount = view.findViewById(R.id.amount);
            description = view.findViewById(R.id.description);
            rsGrid = view.findViewById(R.id.rsGrid);
            rsGrid.setLayoutManager(new GridLayoutManager(mContext, 3));

        }
    }

}
