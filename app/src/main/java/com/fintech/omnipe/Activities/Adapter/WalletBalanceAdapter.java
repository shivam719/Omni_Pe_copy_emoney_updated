package com.fintech.omnipe.Activities.Adapter;

import android.content.Context;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fintech.omnipe.Api.Object.BalanceType;
import com.fintech.omnipe.R;

import java.util.ArrayList;


public class WalletBalanceAdapter extends RecyclerView.Adapter<WalletBalanceAdapter.MyViewHolder> {

    int layout;
    private ArrayList<BalanceType> mList;
    private Context mContext;

    public WalletBalanceAdapter(Context mContext, ArrayList<BalanceType> mList, int layout) {
        this.mList = mList;
        this.mContext = mContext;
        this.layout = layout;
    }

    @Override
    public WalletBalanceAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(layout, parent, false);

        return new WalletBalanceAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final WalletBalanceAdapter.MyViewHolder holder, int position) {
        final BalanceType operator = mList.get(position);

        holder.name.setText(operator.getName());
        holder.amount.setText("" + mContext.getResources().getString(R.string.rupiya) + " " + operator.getAmount());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public AppCompatTextView name, amount;


        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            amount = view.findViewById(R.id.amount);

        }
    }
}
