package com.fintech.omnipe.CommissionSlab.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.fintech.omnipe.Api.Object.SlabDetailDisplayLvl;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.ApplicationConstant;
import com.fintech.omnipe.Util.UtilMethods;
import com.fintech.omnipe.usefull.CustomLoader;

import java.util.ArrayList;
import java.util.Locale;


public class CommissionAdapter extends RecyclerView.Adapter<CommissionAdapter.MyViewHolder> {

    String charText = "";
    private ArrayList<SlabDetailDisplayLvl> rechargeStatus;
    private ArrayList<SlabDetailDisplayLvl> transactionsList;
    private Context mContext;
    int resourceId = 0;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public AppCompatTextView operator,minMaxRange,viewChargesTv;
        ImageView icon;
        RecyclerView recycler_view;

        public MyViewHolder(View view) {
            super(view);
            operator = (AppCompatTextView) view.findViewById(R.id.operator);
            minMaxRange = (AppCompatTextView) view.findViewById(R.id.tv_minMaxRange);
            viewChargesTv = (AppCompatTextView) view.findViewById(R.id.tv_viewCharges);
            recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);
            if(transactionsList.size()>0 && transactionsList.get(0).getRoleCommission().size()>0) {
                recycler_view.setLayoutManager(new GridLayoutManager(mContext, transactionsList.get(0).getRoleCommission().size()));
            }else{
                recycler_view.setLayoutManager(new LinearLayoutManager(mContext));
            }
            recycler_view.setRecycledViewPool(new RecyclerView.RecycledViewPool());
            icon = (ImageView) view.findViewById(R.id.icon);
        }
    }

    public CommissionAdapter(ArrayList<SlabDetailDisplayLvl> transactionsList, Context mContext) {
        this.transactionsList = transactionsList;
        this.rechargeStatus = new ArrayList<>();
        this.rechargeStatus.addAll(transactionsList);
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.commision_adapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final SlabDetailDisplayLvl operator = transactionsList.get(position);
        holder.operator.setText("" + operator.getOperator());
        holder.minMaxRange.setText("Min : " + operator.getMin() + " \u20B9" +" - " + " Max : " + operator.getMax() + " \u20B9");

        if (operator.getCommSettingType() == 2) {
            holder.recycler_view.setVisibility(View.GONE);
            holder.viewChargesTv.setVisibility(View.VISIBLE);
        } else {
            holder.recycler_view.setVisibility(View.VISIBLE);
            holder.recycler_view.setAdapter(new CommissionRoleAdapter(operator.getRoleCommission(),mContext,operator.getRoleCommission().size()));
            holder.viewChargesTv.setVisibility(View.GONE);
        }

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(mContext).load(ApplicationConstant.INSTANCE.baseIconUrl + operator.getOid() + ".png").
                apply(requestOptions).into(holder.icon);
        //holder.operator.setText(getname(operator.getOid()));
        String faqsearchDescription = operator.getOperator();
        if (faqsearchDescription.contains(charText)) {
            int startPos = faqsearchDescription.indexOf(charText);
            int endPos = startPos + charText.length();
            Spannable spanText = Spannable.Factory.getInstance().newSpannable(operator.getOperator()); // <- EDITED: Use the original string, as `country` has been converted to lowercase.
            spanText.setSpan(new ForegroundColorSpan(Color.BLUE), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spanText.setSpan(new StyleSpan(Typeface.ITALIC), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.operator.setText(spanText, TextView.BufferType.SPANNABLE);

        } else {
            holder.operator.setText(operator.getOperator());
        }

        holder.viewChargesTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mContext instanceof CommissionScreen){
                    CustomLoader loader=((CommissionScreen)mContext).loader;
                    loader.show();
                    UtilMethods.INSTANCE.viewRangeClick(operator,loader,(Activity) mContext);
                }
            }
        });

    }

    // Filter Class
    public void filter(String charText) {
        this.charText = charText.toLowerCase(Locale.getDefault());
        transactionsList.clear();
        if (charText.length() == 0) {
            transactionsList.addAll(rechargeStatus);
        } else {
            for (SlabDetailDisplayLvl wp : rechargeStatus) {
                if (wp.getOperator().toLowerCase(Locale.getDefault()).contains(charText)) {
                    transactionsList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

}