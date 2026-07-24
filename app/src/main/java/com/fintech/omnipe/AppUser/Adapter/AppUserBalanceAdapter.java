package com.fintech.omnipe.AppUser.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.fintech.omnipe.Api.Object.BalanceType;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.UtilMethods;

import java.util.ArrayList;

public class AppUserBalanceAdapter extends RecyclerView.Adapter<AppUserBalanceAdapter.MyViewHolder> {

    private ArrayList<BalanceType> transactionsList;
    private Context mContext;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTv,balanceTv;


        public MyViewHolder(View view) {
            super(view);
            nameTv =  view.findViewById(R.id.name);
            balanceTv =  view.findViewById(R.id.balance);

        }
    }

    public AppUserBalanceAdapter(Context mContext, ArrayList<BalanceType> transactionsList) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.app_user_balance_adapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final BalanceType operator = transactionsList.get(position);
        holder.nameTv.setText(operator.getName());
        holder.balanceTv.setText("\u20B9 " + UtilMethods.INSTANCE.formatedAmount(operator.getAmount()+""));



    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

    public interface onClick {
        void onClick(int id);
    }
}
