package com.fintech.omnipe.Activities.DthCustomerInfo.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.fintech.omnipe.Activities.DthCustomerInfo.dto.DthCustomerInfoData;
import com.fintech.omnipe.R;

import java.util.List;


public class DthInfoRNAdapter extends RecyclerView.Adapter<DthInfoRNAdapter.MyViewHolder> {

    private List<DthCustomerInfoData> dthInfoList;
    private Context mContext;

    public DthInfoRNAdapter(Context mContext, List<DthCustomerInfoData> dthInfoList) {
        this.dthInfoList = dthInfoList;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_customer_rn_info, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final DthCustomerInfoData operator = dthInfoList.get(position);

        if(operator.isAmount() && operator.getKeyVal()!=null && !operator.getKeyVal().isEmpty()){
            holder.amtValueTv.setVisibility(View.VISIBLE);
            holder.valueTv.setVisibility(View.GONE);
            holder.keyTv.setText(operator.getKeyName());
            holder.amtValueTv.setText(mContext.getResources().getString(R.string.rupiya) + " " + operator.getKeyVal());
        }else{
            if(operator.getKeyVal()!=null && !operator.getKeyVal().isEmpty())
            {
                holder.valueTv.setVisibility(View.VISIBLE);
                holder.amtValueTv.setVisibility(View.GONE);
                holder.mainView.setVisibility(View.VISIBLE);
                holder.keyTv.setText(operator.getKeyName());
                holder.valueTv.setText(operator.getKeyVal());
            }
            else
                holder.mainView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return dthInfoList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView keyTv;
        public TextView valueTv,amtValueTv;
        View itemView,mainView;

        public MyViewHolder(View view) {
            super(view);
            itemView = view;
            keyTv = view.findViewById(R.id.keyTv);
            valueTv = view.findViewById(R.id.valueTv);
            amtValueTv = view.findViewById(R.id.amtValueTv);
            mainView = view.findViewById(R.id.mainView);

        }
    }

}
