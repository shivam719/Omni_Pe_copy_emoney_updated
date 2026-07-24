package com.fintech.omnipe.GetAdvertisement.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.fintech.omnipe.Api.Response.Promotion;
import com.fintech.omnipe.R;

import java.util.ArrayList;
import java.util.List;


public class GetAdvertisementByCityAdapter extends RecyclerView.Adapter<GetAdvertisementByCityAdapter.MyViewHolder>  implements Filterable {
    List<Promotion> promotionList;
    List<Promotion> promotionListF;
    private final Activity mContext;

    public GetAdvertisementByCityAdapter(List<Promotion> transactionsList, Activity mContext) {
        this.promotionList = transactionsList;
        promotionListF =transactionsList;
        this.mContext = mContext;
    }

    @Override
    public GetAdvertisementByCityAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_advertisement, parent, false);

        return new GetAdvertisementByCityAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final GetAdvertisementByCityAdapter.MyViewHolder holder, int position) {
        final Promotion operator = promotionList.get(position);
        if (operator.getCityName() != null && !operator.getCityName().isEmpty()) {
            holder.cityName.setVisibility(View.VISIBLE);
            holder.cityName.setText(operator.getCityName());
        } else {
            holder.cityName.setVisibility(View.GONE);
        }
        if (operator.getImgUrl() != null && !operator.getImgUrl().isEmpty()) {
            RequestOptions requestOptions = new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.rnd_logo)
                    .error(R.drawable.rnd_logo);
            Glide.with(mContext)
                    .load(operator.getImgUrl())
                    .apply(requestOptions)
                    .into(holder.imgUrl);
        } else {
            holder.imgUrl.setImageResource(R.drawable.rnd_logo);
        }

        if (operator.getIsActive()) {
            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(operator.getLinkUrl()));
                mContext.startActivity(intent);
            });
        }

    }

    @Override
    public int getItemCount() {
        return promotionListF.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                List<Promotion> filteredList;

                if (charString.isEmpty()) {
                    filteredList = promotionList;
                } else {
                    filteredList = new ArrayList<>();
                    for (Promotion row : promotionList) {
                        if (row.getCityName() != null && row.getCityName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                promotionListF = (ArrayList<Promotion>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public AppCompatImageView imgUrl;
        private AppCompatTextView cityName;

        public MyViewHolder(View view) {
            super(view);
            imgUrl = view.findViewById(R.id.imgUrl);
            cityName = view.findViewById(R.id.cityName);
        }
    }

}

