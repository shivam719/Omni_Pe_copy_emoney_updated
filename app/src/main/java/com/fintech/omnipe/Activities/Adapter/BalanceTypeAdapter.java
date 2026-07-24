package com.fintech.omnipe.Activities.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fintech.omnipe.Api.Object.BalanceType;
import com.fintech.omnipe.R;

import java.util.ArrayList;


public class BalanceTypeAdapter extends RecyclerView.Adapter<BalanceTypeAdapter.MyViewHolder> {
    ArrayList<BalanceType> balanceTypes;
    Context context;
    public BalanceTypeAdapter(ArrayList<BalanceType> balanceTypes, Context context) {
        this.balanceTypes=balanceTypes;
        this.context=context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup,int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.balance_view,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder,int i) {
        myViewHolder.wallet.setText(balanceTypes.get(i).getName());
        myViewHolder.balance.setText(context.getResources().getString(R.string.rupiya)+" "+balanceTypes.get(i).getAmount());
    }

    @Override
    public int getItemCount() {
        return balanceTypes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView wallet, balance;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            wallet=(TextView) itemView.findViewById(R.id.wallet);
            balance=(TextView) itemView.findViewById(R.id.balance);
        }
    }
}
