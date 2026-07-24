package com.fintech.omnipe.AEPS.FingPay.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.fintech.omnipe.AEPS.FingPay.dto.ReceiptObject;
import com.fintech.omnipe.R;

import java.util.List;


public class ReceiptDetailListAdapter extends RecyclerView.Adapter<ReceiptDetailListAdapter.MyViewHolder> {

    private List<ReceiptObject> operatorList;
    private Activity mContext;

    public ReceiptDetailListAdapter(List<ReceiptObject> operatorList, Activity mContext) {
        this.operatorList = operatorList;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_receipt_details, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final ReceiptObject operator = operatorList.get(position);
        holder.name.setText(operator.getName() + "");
        holder.value.setText(operator.getValue() + "");


    }

    @Override
    public int getItemCount() {
        return operatorList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private AppCompatTextView name, value;
        View itemView;

        public MyViewHolder(View view) {
            super(view);
            itemView = view;
            name = view.findViewById(R.id.name);

            value = view.findViewById(R.id.value);
        }
    }
}



