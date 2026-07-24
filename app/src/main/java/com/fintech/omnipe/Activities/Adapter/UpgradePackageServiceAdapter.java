package com.fintech.omnipe.Activities.Adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fintech.omnipe.Api.Object.PackageService;
import com.fintech.omnipe.R;

import java.util.List;

public class UpgradePackageServiceAdapter extends RecyclerView.Adapter<UpgradePackageServiceAdapter.MyViewHolder> {


    private List<PackageService> transactionsList;
    private Context mContext;

    public UpgradePackageServiceAdapter(Context mContext, List<PackageService> transactionsList) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
    }

    @Override
    public UpgradePackageServiceAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_upgrade_package_service, parent, false);

        return new UpgradePackageServiceAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final UpgradePackageServiceAdapter.MyViewHolder holder, final int position) {
        final PackageService operator = transactionsList.get(position);
        holder.text.setText(operator.getServiceName() + "");


    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView text;

        public MyViewHolder(View view) {
            super(view);
            text = view.findViewById(R.id.text);

        }
    }
}
