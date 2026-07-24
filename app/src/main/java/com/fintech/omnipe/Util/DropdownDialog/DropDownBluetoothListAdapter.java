package com.fintech.omnipe.Util.DropdownDialog;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.fintech.omnipe.R;

import java.util.List;

public class DropDownBluetoothListAdapter extends RecyclerView.Adapter<DropDownBluetoothListAdapter.MyViewHolder> {

    private static ClickListner mClickListner;
    Context context;
    int selectdPosition = -1;
    private List<BluetoothDevice> mList;

    public DropDownBluetoothListAdapter(Context context, List<BluetoothDevice> mList, int selectdPosition, ClickListner mClickListner) {
        this.mList = mList;
        this.context = context;
        this.mClickListner = mClickListner;
        this.selectdPosition = selectdPosition;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_drop_down_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final BluetoothDevice item = mList.get(position);
        holder.text.setText(item.getName() + "");


        if (selectdPosition == -1 || position != selectdPosition) {
            holder.text.setTextColor(Color.BLACK);
            holder.radioBtn.setChecked(false);
        } else {
            holder.text.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            holder.radioBtn.setChecked(true);
        }
        holder.itemView.setOnClickListener(v -> {
            if (mClickListner != null) {
                selectdPosition = position;
                notifyDataSetChanged();

                mClickListner.onItemClick(position, item.getName(), item);


            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public interface ClickListner {
        void onItemClick(int clickPosition, String value, Object object);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView text, amount;
        View itemView;
        RadioButton radioBtn;

        public MyViewHolder(View view) {
            super(view);
            itemView = view;
            text = view.findViewById(R.id.text);
            amount = view.findViewById(R.id.amount);
            radioBtn = view.findViewById(R.id.radioBtn);

            amount.setVisibility(View.GONE);
            radioBtn.setVisibility(View.VISIBLE);
        }


    }
}
