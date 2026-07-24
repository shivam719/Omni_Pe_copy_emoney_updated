package com.fintech.omnipe.AppUser.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.fintech.omnipe.AppUser.Activity.VoucherEntryUserListActivity;
import com.fintech.omnipe.AppUser.dto.AscReport;
import com.fintech.omnipe.R;

import java.util.ArrayList;

public class VoucherEntryUserListAdapter extends RecyclerView.Adapter<VoucherEntryUserListAdapter.MyViewHolder> implements Filterable {

    private ArrayList<AscReport> filterListItem = new ArrayList<>();
    private ArrayList<AscReport> listItem = new ArrayList<>();
    private Context mContext;

    public VoucherEntryUserListAdapter(ArrayList<AscReport> operatorList, Context mContext) {
        this.filterListItem = operatorList;
        this.listItem = operatorList;
        this.mContext = mContext;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_voucher_entry_user_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final AscReport operator = filterListItem.get(position);

        holder.title.setText(operator.getOutletName()+"");
        holder.mobile.setText(operator.getMobile()+"");


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((VoucherEntryUserListActivity) mContext).selectUser(operator);

            }
        });


//            holder.title.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v)
//                {
//                    Toast.makeText(mContext, ""+operator.getAreaID(), Toast.LENGTH_SHORT).show();
//                }
//            });

    }

    @Override
    public int getItemCount() {
        return filterListItem.size();
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
                    ArrayList<AscReport> filteredList = new ArrayList<>();
                    for (AscReport row : listItem) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if ((row.getMobile() + "").toLowerCase().contains(charString.toLowerCase()) ||
                                (row.getOutletName() + "").toLowerCase().contains(charString.toLowerCase()) ||
                                (row.getBalance() + "").toLowerCase().contains(charString.toLowerCase()) ||
                                (row.getArea() + "").toLowerCase().contains(charString.toLowerCase())
                        ) {
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
                filterListItem = (ArrayList<AscReport>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, mobile;


        public MyViewHolder(View view) {
            super(view);


            title = view.findViewById(R.id.title);
            mobile = view.findViewById(R.id.mobile);


        }
    }

}
