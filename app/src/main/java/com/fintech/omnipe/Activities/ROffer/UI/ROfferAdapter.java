package com.fintech.omnipe.Activities.ROffer.UI;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fintech.omnipe.Activities.ROffer.dto.ROfferObject;
import com.fintech.omnipe.R;

import java.util.ArrayList;


public class ROfferAdapter extends RecyclerView.Adapter<ROfferAdapter.MyViewHolder> {


    private ArrayList<ROfferObject> transactionsList;
    private Context mContext;

    public ROfferAdapter(ArrayList<ROfferObject> transactionsList, Context mContext) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
    }

    @Override
    public ROfferAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.offer_adapter, parent, false);

        return new ROfferAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ROfferAdapter.MyViewHolder holder, int position) {
        final ROfferObject operator = transactionsList.get(position);

        holder.Description.setText( operator.getDesc()+"");
        holder.Amount.setText( mContext.getResources().getString(R.string.rupiya) + " " + operator.getRs());

        holder.Amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ROffer) mContext).ItemClick("" + operator.getRs(), operator.getDesc());
            }
        });
    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView Description, Amount;


        public MyViewHolder(View view) {
            super(view);
            Description = view.findViewById(R.id.tv_description);
            Amount = view.findViewById(R.id.tv_amount);
        }
    }

}
