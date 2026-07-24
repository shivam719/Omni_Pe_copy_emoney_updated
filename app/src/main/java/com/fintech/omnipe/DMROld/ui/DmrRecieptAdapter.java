package com.fintech.omnipe.DMROld.ui;

import android.app.Dialog;
import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.ListOblect;
import com.fintech.omnipe.usefull.CustomLoader;

/**
 * Created by Administrator on 29-12-2017.
 */

public class DmrRecieptAdapter extends RecyclerView.Adapter<DmrRecieptAdapter.MyViewHolder> {

    int resourceId = 0;
    CustomLoader loader;
    Dialog dialog = null;
    int i = 0;
    private java.util.List<ListOblect> operatorList;
    private Context mContext;

    public DmrRecieptAdapter(java.util.List<ListOblect> operatorList, Context mContext) {
        this.operatorList = operatorList;
        this.mContext = mContext;
    }

    @Override
    public DmrRecieptAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dmrreceipt_list, parent, false);

        loader = new CustomLoader(mContext, android.R.style.Theme_Translucent_NoTitleBar);

        return new DmrRecieptAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final DmrRecieptAdapter.MyViewHolder holder, final int position) {
        final ListOblect operator = operatorList.get(position);
        i = i + 1;
        holder.sn.setText(i + ".");
        holder.desc.setText(operator.getTransactionID() + "");
        String Status = operator.getStatuscode() + "";
        if (Status.equals("2")) {
            holder.status.setImageResource(R.drawable.ic_check_circle_black_24dp);
        } else if (Status.equals("3")) {
            holder.status.setImageResource(R.drawable.ic_cancel_black_24dp);
        } else if (Status.equals("4")) {
            holder.status.setImageResource(R.drawable.ic_status_refund);
        } else {
            holder.status.setImageResource(R.drawable.ic_pending_black_24dp);
        }
        holder.amount.setText(mContext.getResources().getString(R.string.rupiya) + " " + operator.getRequestedAmount() + "");

    }

    @Override
    public int getItemCount() {
        return operatorList.size();
    }

    public void deleteDone() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView sn;
        public TextView desc;
        public ImageView status;
        public TextView amount;

        public RelativeLayout transferLayout;
        public RelativeLayout deleteLayout;

        public MyViewHolder(View view) {
            super(view);
            sn = view.findViewById(R.id.sn);
            desc = view.findViewById(R.id.desc);
            status = view.findViewById(R.id.status);
            amount = view.findViewById(R.id.amount);

        }
    }


}
