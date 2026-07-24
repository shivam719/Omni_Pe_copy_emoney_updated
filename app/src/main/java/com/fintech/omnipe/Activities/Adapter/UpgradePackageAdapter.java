package com.fintech.omnipe.Activities.Adapter;

import android.content.Context;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fintech.omnipe.Activities.UpgradePackageActivity;
import com.fintech.omnipe.Api.Object.PackageDetails;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.UtilMethods;

import java.util.List;

public class UpgradePackageAdapter extends RecyclerView.Adapter<UpgradePackageAdapter.MyViewHolder> {


    private List<PackageDetails> transactionsList;
    private Context mContext;

    public UpgradePackageAdapter(Context mContext, List<PackageDetails> transactionsList) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
    }

    @Override
    public UpgradePackageAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_upgrade_package, parent, false);

        return new UpgradePackageAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final UpgradePackageAdapter.MyViewHolder holder, final int position) {
        final PackageDetails operator = transactionsList.get(position);
        holder.packageNameTv.setText(operator.getPackageName() + "");
        holder.amountTv.setText("You have to pay \u20B9 " + UtilMethods.INSTANCE.formatedAmount(operator.getPackageCost() + ""));
        holder.recyclerView.setAdapter(new UpgradePackageServiceAdapter(mContext, operator.getServices()));
        holder.upgradeSwitch.setChecked(operator.isDefault());
        if (operator.isDefault()) {
            holder.switchView.setClickable(false);
        } else {
            holder.switchView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mContext instanceof UpgradePackageActivity) {
                        ((UpgradePackageActivity) mContext).upgradePackage(operator,position);
                    }
                }
            });
            holder.switchView.setClickable(true);
        }

    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public AppCompatTextView packageNameTv, amountTv;
        SwitchCompat upgradeSwitch;
        RecyclerView recyclerView;
        View switchView;

        public MyViewHolder(View view) {
            super(view);
            amountTv = view.findViewById(R.id.amountTv);
            packageNameTv = view.findViewById(R.id.packageNameTv);
            upgradeSwitch = view.findViewById(R.id.upgradeSwitch);
            switchView = view.findViewById(R.id.switchView);
            recyclerView = view.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
            recyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        }
    }
}
