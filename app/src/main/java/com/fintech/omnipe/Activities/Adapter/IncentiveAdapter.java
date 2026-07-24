package com.fintech.omnipe.Activities.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.fintech.omnipe.Api.Object.IncentiveDetails;
import com.fintech.omnipe.R;

import java.util.ArrayList;

public class IncentiveAdapter extends RecyclerView.Adapter<IncentiveAdapter.MyViewHolder> {


    private ArrayList<IncentiveDetails> operatorList;
    private Context mContext;
    private MyInterface listener ;

    public IncentiveAdapter(ArrayList<IncentiveDetails> operatorList, Context mContext, MyInterface listener ) {
        this.operatorList = operatorList;
        this.mContext = mContext;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.incentive_adapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final IncentiveDetails operator = operatorList.get(position);
        holder.denomination.setText("\u20B9 " + operator.getDenomination());
        holder.comm.setText(operator.getComm() + (operator.isAmtType() ? " \u20B9" : " %"));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences=mContext.getSharedPreferences("amountvalue",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                editor.putString("amt",operator.toString());
                if(listener!=null)
                {
                    listener.myAction(operator);
                }
                /*if (mContext instanceof RechargeActivity) {
                    ((RechargeActivity) mContext).setCashBackAmount(operator);
                }*/
            }
        });

    }

    @Override
    public int getItemCount() {
        return operatorList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView denomination, comm;
        View itemView;

        public MyViewHolder(View view) {
            super(view);
            itemView = view;

            denomination = (TextView) view.findViewById(R.id.denomination);
            comm = (TextView) view.findViewById(R.id.comm);


        }
    }

    public interface MyInterface
    {
        void myAction(IncentiveDetails operator) ;
    }

}

