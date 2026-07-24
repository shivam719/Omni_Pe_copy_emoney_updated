package com.fintech.omnipe.AppUser.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.fintech.omnipe.AppUser.Activity.FosAreaReportActivity;
import com.fintech.omnipe.AppUser.dto.AreaMaster;
import com.fintech.omnipe.R;

import java.util.ArrayList;

public class FosReportAreaListScreenAdapter extends RecyclerView.Adapter<FosReportAreaListScreenAdapter.MyViewHolder>
{


        private ArrayList<AreaMaster> operatorList = new ArrayList<>();
        private Context mContext;
        RequestOptions requestOptions;




        public FosReportAreaListScreenAdapter(ArrayList<AreaMaster> operatorList, Context mContext) {
            this.operatorList = operatorList;
            this.mContext = mContext;
            requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.placeholder_square);
            requestOptions.error(R.drawable.placeholder_square);
            requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        }



        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_area_list, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            final AreaMaster operator = operatorList.get(position);

            holder.title.setText(operator.getArea());


             holder.itemView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     if(mContext instanceof FosAreaReportActivity) {
                         ((FosAreaReportActivity) mContext).setArea(operator);
                     }

                 }
             });


        }


    @Override
        public int getItemCount() {
            return operatorList.size();
        }

        public void filter(ArrayList<AreaMaster> newList) {
            operatorList = new ArrayList<>();
            operatorList.addAll(newList);
            notifyDataSetChanged();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView title;
            ImageView areakarrow;


            public MyViewHolder(View view) {
                super(view);
                title = view.findViewById(R.id.title);
                areakarrow = view.findViewById(R.id.rightArrowIv);

            }
        }

    }
