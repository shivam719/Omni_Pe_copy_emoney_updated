package com.fintech.omnipe.CommissionSlab.ui;

import android.content.Context;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fintech.omnipe.Api.Object.RoleCommission;
import com.fintech.omnipe.R;

import java.util.ArrayList;

/**
 * Created by Lalit on 26-04-2017.
 */

public class CommissionRoleAdapter extends RecyclerView.Adapter<CommissionRoleAdapter.MyViewHolder> {

    String charText = "";
    private ArrayList<RoleCommission> transactionsList;
    private Context mContext;
    int size = 0;

    public CommissionRoleAdapter(ArrayList<RoleCommission> transactionsList, Context mContext, int size) {
        this.transactionsList = transactionsList;
        this.size = size;
        this.mContext = mContext;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public AppCompatTextView userRoleTv;


        public MyViewHolder(View view) {
            super(view);
            userRoleTv = (AppCompatTextView) view.findViewById(R.id.userRoleTv);

           /* commissionTv = (AppCompatTextView) view.findViewById(R.id.commisionTv);

            commissionTypeTv = (AppCompatTextView) view.findViewById(R.id.commisionTypeTv);*/
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.commission_role_adapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final RoleCommission operator = transactionsList.get(position);

        String comType, comm;
        if(size==1 ) {
            if (operator.getCommType() == 0) {
                comType = "(COMMISSION)";
            } else {
                comType = "(SURCHARGE)";
            }

            if (operator.getAmtType() == 0) {
                comm = operator.getComm() + " %";
            } else {
                comm = mContext.getResources().getString(R.string.rupiya) + " " + operator.getComm();
            }

        }else  if( size==2) {
            if (operator.getCommType() == 0) {
                comType = "(COMMISSION)";
            } else {
                comType = "(SURCHARGE)";
            }

            if (operator.getAmtType() == 0) {
                comm = operator.getComm() + " %";
            } else {
                comm = mContext.getResources().getString(R.string.rupiya) + " " + operator.getComm();
            }
            holder.userRoleTv.setText(operator.getPrefix() + " : " + comm + " " + comType);
        }
        else{
            if (operator.getCommType() == 0) {
                comType = "" + "(COM)";
            } else {
                comType = "" + "(SUR)";
            }

            if (operator.getAmtType() == 0) {
                comm = operator.getComm() + " %";
            } else {
                comm = mContext.getResources().getString(R.string.rupiya) + " " + operator.getComm();
            }

        }
        if(size==1 ){
            holder.userRoleTv.setText(operator.getRole() + " : " + comm + " " + comType);
        }else{
            holder.userRoleTv.setText(operator.getPrefix() + " : " + comm + " " + comType);
        }


    }


    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

}