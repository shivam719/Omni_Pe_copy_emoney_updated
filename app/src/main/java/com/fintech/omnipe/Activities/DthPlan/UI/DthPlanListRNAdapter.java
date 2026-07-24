package com.fintech.omnipe.Activities.DthPlan.UI;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.fintech.omnipe.Activities.DthPlan.dto.PDetails;
import com.fintech.omnipe.Activities.DthPlan.dto.PlanValidity;
import com.fintech.omnipe.R;

import java.util.ArrayList;
import java.util.List;

public class DthPlanListRNAdapter extends RecyclerView.Adapter<DthPlanListRNAdapter.MyViewHolder> {

    private List<PDetails> operatorDetailRN;
    private Context mContext;
    private String amount="";

    public DthPlanListRNAdapter(List<PDetails> operatorDetailRN, Context mContext) {
        this.operatorDetailRN = operatorDetailRN;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dth_plan_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final PDetails operator = operatorDetailRN.get(position);
        List<PlanValidity> mPlanValidities = new ArrayList<>();

        if (operator.getPrice()!=null && operator.getPrice().get1MONTHS() != null && !operator.getPrice().get1MONTHS().isEmpty()) {
            holder.amount.setVisibility(View.GONE);
            holder.amount.setText(mContext.getResources().getString(R.string.rupiya) + " " + operator.getPrice().get1MONTHS());
            amount=operator.getPrice().get1MONTHS();
            mPlanValidities.add(new PlanValidity(operator.getPrice().get1MONTHS(), "1 Month",operator.getpDescription()));
        }

        if (operator.getPrice()!=null && operator.getPrice().get3MONTHS() != null && !operator.getPrice().get3MONTHS().isEmpty()) {
            holder.amount.setVisibility(View.GONE);
            amount=operator.getPrice().get3MONTHS();
            mPlanValidities.add(new PlanValidity(operator.getPrice().get3MONTHS(), "4 Months",operator.getpDescription()));
        }
        if (operator.getPrice()!=null && operator.getPrice().get6MONTHS() != null && !operator.getPrice().get6MONTHS().isEmpty()) {
            holder.amount.setVisibility(View.GONE);
            amount=operator.getPrice().get6MONTHS();
            mPlanValidities.add(new PlanValidity(operator.getPrice().get6MONTHS(), "6 Months",operator.getpDescription()));
        }

        if (operator.getPrice()!=null && operator.getPrice().get1YEAR() != null && !operator.getPrice().get1YEAR().isEmpty()) {
            holder.amount.setVisibility(View.GONE);
            amount=operator.getPrice().get1YEAR();
            mPlanValidities.add(new PlanValidity(operator.getPrice().get1YEAR(), "1 Year",operator.getpDescription()));
        }

        if (operator.getPrice()!=null && mPlanValidities != null && mPlanValidities.size() > 0) {
            holder.rsGrid.setVisibility(View.VISIBLE);
            DthPlanValidityGridAdapter mDthPlanValidityGridAdapter = new DthPlanValidityGridAdapter(mContext, mPlanValidities);
            //holder.rsGrid.setLayoutManager(new LinearLayoutManager(mContext));
            holder.rsGrid.setAdapter(mDthPlanValidityGridAdapter);
        }else{
            holder.rsGrid.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((DthPlanInfoActivity) mContext).ItemClick("" + amount, "" + operator.getPackageName());
            }
        });
        if (operator.getPackageName() != null && operator.getPackageName().length() > 0)
        {
            holder.planName.setText(operator.getPackageName()+"");
        }
        else
            holder.planName.setText("N/A");

        if (operator.getpDescription() != null && operator.getpDescription().length() > 0)
        {
            Spanned html = Html.fromHtml(operator.getpDescription());
            holder.description.setText(HtmlCompat.fromHtml(String.valueOf(html), HtmlCompat.FROM_HTML_MODE_LEGACY));
        }
        else
            holder.description.setText("N/A");

    }

    @Override
    public int getItemCount() {
        return operatorDetailRN.size();
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
