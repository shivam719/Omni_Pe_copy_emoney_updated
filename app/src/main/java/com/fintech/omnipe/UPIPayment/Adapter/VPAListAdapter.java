package com.fintech.omnipe.UPIPayment.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.fintech.omnipe.UPIPayment.Activity.UPIListActivity;
import com.fintech.omnipe.UPIPayment.dto.VPAList;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.ApplicationConstant;
import com.fintech.omnipe.Util.UtilMethods;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class VPAListAdapter extends RecyclerView.Adapter<VPAListAdapter.MyViewHolder> implements Filterable {

    private List<VPAList> benisFilterList, listItem,filterListItem;
    private Context mContext;
    private RequestOptions requestOptions;
    public VPAListAdapter(List<VPAList> listItem, Context mContext) {
        this.benisFilterList = listItem;
        this.listItem = listItem;
        this.filterListItem = listItem;
        this.mContext = mContext;
        requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.error(R.drawable.ic_upi_icon);
        requestOptions.placeholder(R.drawable.ic_upi_icon);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_vpalist, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final VPAList vpaListResponse = filterListItem.get(position);
        if(vpaListResponse!=null){

            holder.accountHolderTv.setText(vpaListResponse.getAccountHolder()+"");
            holder.vpaTv.setText(vpaListResponse.getVpa()+"");


            Glide.with(mContext)
                    .load(ApplicationConstant.INSTANCE.UPIIconUrl + UtilMethods.INSTANCE.getUPILogoFromVPAStr(vpaListResponse.getVpa()) + ".png")
                    .apply(requestOptions)
                    .into(holder.upiIconIv);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mContext instanceof UPIListActivity)
                    ((UPIListActivity)mContext).sendMoneyClick(vpaListResponse);

            }
        });

    }

    @Override
    public int getItemCount() {
        return filterListItem.size();
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());

        List<VPAList> filterList = new ArrayList<>();
        if (charText.length() == 0) {
            filterList.addAll(benisFilterList);
        } else {
            for (VPAList item : benisFilterList) {
                if ((item.getVpa() + "").toLowerCase(Locale.getDefault()).contains(charText) ||
                        (item.getAccountHolder() + "").toLowerCase(Locale.getDefault()).contains(charText))
                {
                    filterList.add(item);
                }
            }
        }

        listItem = filterList;
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filterListItem = listItem;
                } else {
                    ArrayList<VPAList> filteredList = new ArrayList<>();
                    for (VPAList row : listItem) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getVpa().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getAccountHolder().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getSenderNo().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    filterListItem = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filterListItem;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filterListItem = (ArrayList<VPAList>) filterResults.values;
                notifyDataSetChanged();


                if (mContext instanceof UPIListActivity) {
                    if (filterListItem.size() == 0) {
                        ((UPIListActivity) mContext).noDataView.setVisibility(View.VISIBLE);
                    } else {
                        ((UPIListActivity) mContext).noDataView.setVisibility(View.GONE);
                    }

                }

            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView accountHolderTv,vpaTv;
        public ImageView upiIconIv;

        public MyViewHolder(View view) {
            super(view);
            accountHolderTv = (TextView) view.findViewById(R.id.accountHolderTv);
            vpaTv = (TextView) view.findViewById(R.id.vpaTv);
            upiIconIv = (ImageView) view.findViewById(R.id.upiIconIv);
        }
    }

}
