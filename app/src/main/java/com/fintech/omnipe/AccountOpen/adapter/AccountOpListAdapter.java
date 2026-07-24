package com.fintech.omnipe.AccountOpen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.fintech.omnipe.Fragments.dto.OperatorList;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.ApplicationConstant;


import java.util.ArrayList;

public class AccountOpListAdapter extends RecyclerView.Adapter<AccountOpListAdapter.MyViewHolder> {

    private ArrayList<OperatorList> operatorList;
    private Context mContext;
    private RequestOptions requestOptions;
    private AccountListCallBack accountListCallBack;
    private AlertDialog alertDialog;

    public AccountOpListAdapter(ArrayList<OperatorList> operatorList, Context mContext, AccountListCallBack accountListCallBack , AlertDialog alertDialog) {
        this.operatorList = operatorList;
        this.mContext = mContext;
        this.accountListCallBack = accountListCallBack;
        this.alertDialog = alertDialog;
        requestOptions = new RequestOptions();
        requestOptions.placeholder(R.mipmap.ic_launcher);
        requestOptions.error(R.mipmap.ic_launcher);
        requestOptions.circleCrop();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
        requestOptions.skipMemoryCache(true);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.account_oplist_adapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final OperatorList operator = operatorList.get(position);
        holder.title.setText(operator.getName()+"");

        Glide.with(mContext)
                .load(ApplicationConstant.INSTANCE.baseIconUrl + operator.getOid() + ".png")
                .apply(requestOptions)
                .into(holder.opImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(accountListCallBack!=null)
                    accountListCallBack.onIconClick(operator.getOid(),operator.getRedirectURL());
                alertDialog.dismiss();
            }
        });

    }

    @Override
    public int getItemCount() {
        return operatorList.size();
    }




    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView opImage;


        public MyViewHolder(View view) {
            super(view);
            title =view.findViewById(R.id.opNameTv);
            opImage =  view.findViewById(R.id.opIconIv);

        }
    }

    public interface AccountListCallBack {
        void onIconClick(int oId,String Url);

    }

}
