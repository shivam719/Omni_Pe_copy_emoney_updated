package com.fintech.omnipe.Fragments.Adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.fintech.omnipe.Fragments.dto.OperatorList;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.ApplicationConstant;
import com.fintech.omnipe.Util.ListScreen;

import java.util.ArrayList;



public class ListScreenAdapter extends RecyclerView.Adapter<ListScreenAdapter.MyViewHolder> {

    int resourceId = 0;
    private ArrayList<OperatorList> operatorList;
    private Context mContext;

    public ListScreenAdapter(ArrayList<OperatorList> operatorList, Context mContext) {
        this.operatorList = operatorList;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.operator_list_adapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final OperatorList operator = operatorList.get(position);
        holder.title.setText(operator.getName());
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ListScreen) mContext).ItemClick(operator.getName(), operator.getOid(),
                        operator.getImage(), operator.getMax(), operator.getMin(), operator.getLength(), operator.getLengthMax(), operator.isAccountNumeric(), operator.getStartWith(), operator.isPartial(), operator.getAccountName(), operator.getAccountRemak());
            }
        });

        if (operator.getImage() != null && operator.getImage().length() > 0) {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
            requestOptions.placeholder(R.mipmap.ic_launcher_round);
            Glide.with(mContext)
                    .load(ApplicationConstant.INSTANCE.baseIconUrl + operator.getImage())
                    .apply(requestOptions)
                    .into(holder.opImage);
        } else {
            holder.opImage.setImageResource(R.drawable.ic_operator_default_icon);
        }
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
            title = view.findViewById(R.id.title);
            opImage = view.findViewById(R.id.opImage);

        }
    }

}
