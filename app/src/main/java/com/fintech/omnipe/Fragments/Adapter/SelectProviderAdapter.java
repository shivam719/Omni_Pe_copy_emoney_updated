package com.fintech.omnipe.Fragments.Adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.fintech.omnipe.Activities.RechargeProviderActivity;
import com.fintech.omnipe.Fragments.dto.OperatorList;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.ApplicationConstant;

import java.util.ArrayList;
import java.util.Locale;


public class SelectProviderAdapter extends RecyclerView.Adapter<SelectProviderAdapter.MyViewHolder> {


    private ArrayList<OperatorList> operatorList;
    private Context mContext;

    public SelectProviderAdapter(ArrayList<OperatorList> operatorList, Context mContext) {
        this.operatorList = operatorList;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.select_provider_adapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final OperatorList operator = operatorList.get(position);
        holder.title.setText(operator.getName());
        holder.ll_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((RechargeProviderActivity) mContext).ItemClick(operator);
            }
        });

        if (operator.getImage() != null && operator.getImage().length() > 0) {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(mContext).load(ApplicationConstant.INSTANCE.baseIconUrl + operator.getImage()).
                    apply(requestOptions).into(holder.opImage);

        } else {
            holder.opImage.setImageResource(R.drawable.ic_operator_default_icon);
        }
    }

    @Override
    public int getItemCount() {
        return operatorList.size();
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        // operatorList.clear();
        if (charText.length() == 0) {
            operatorList.addAll(operatorList);
        } else {
            for (OperatorList op : operatorList) {
                if (op.getName().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    operatorList.add(op);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void filter(ArrayList<OperatorList> newList) {
        operatorList = new ArrayList<>();
        operatorList.addAll(newList);
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView opImage;
        LinearLayout ll_top;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            opImage = view.findViewById(R.id.opImage);
            ll_top = view.findViewById(R.id.ll_top);

        }
    }

}
