package com.fintech.omnipe.Notification.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.fintech.omnipe.Api.Object.NotificationData;
import com.fintech.omnipe.Notification.NotificationActivity;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.ApplicationConstant;

import java.util.ArrayList;
import java.util.List;



public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.MyViewHolder> {

    int resourceId = 0;
    private List<NotificationData> mList;
    private Context mContext;
    CountCallBack mCountCallBack;
    public NotificationListAdapter(Context mContext, List<NotificationData> mList,CountCallBack mCountCallBack) {
        this.mList = mList;
        this.mContext = mContext;
        this.mCountCallBack=mCountCallBack;
    }

    @Override
    public NotificationListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_notification, parent, false);

        return new NotificationListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final NotificationListAdapter.MyViewHolder holder, final int position) {
        final NotificationData operator = mList.get(position);
        holder.title.setText(operator.getTitle());
        holder.msg.setText(operator.getMessage().replace("\n\n\n", "").replaceAll("\n\n", "\n").replaceAll("\r\n\r\n", "\n"));
        holder.date.setText(operator.getEntryDate());

        if (!operator.isSeen()) {
            holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.aboutpagecolour));
        } else {
            holder.itemView.setBackgroundColor(Color.WHITE);
        }

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.placeholder(R.drawable.notification);
        Glide.with(mContext)
                .load(ApplicationConstant.INSTANCE.baseUrl + "/Image/Notification/" + operator.getImageUrl())
                .apply(requestOptions)
                .into(holder.logo);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, NotificationActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("Title", operator.getTitle());
                intent.putExtra("Message", operator.getMessage());
                intent.putExtra("Image", (operator.getImageUrl() != null && !operator.getImageUrl().isEmpty()) ? ApplicationConstant.INSTANCE.baseUrl + "/Image/Notification/" + operator.getImageUrl() : "");
                intent.putExtra("Url", operator.getUrl());
                intent.putExtra("Time", operator.getEntryDate());
                mContext.startActivity(intent);
                if (!operator.isSeen()) {
                    if(mCountCallBack!=null){
                        mCountCallBack.onClickNotification(position);
                    }
                    /*if(mContext instanceof NotificationListActivity){
                        ((NotificationListActivity) mContext).setReadNotification(position);
                    }*/

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void filter(ArrayList<NotificationData> newList) {
        mList = new ArrayList<>();
        mList.addAll(newList);
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, msg, date;
        public ImageView logo;
        View itemView;

        public MyViewHolder(View view) {
            super(view);
            itemView = view;
            title = view.findViewById(R.id.title);
            msg = view.findViewById(R.id.msg);
            date = view.findViewById(R.id.date);
            logo = view.findViewById(R.id.logo);

        }
    }

    public interface CountCallBack{
        void onClickNotification(int position);
    }

}




