package com.fintech.omnipe.Activities.Adapter;

import android.app.Activity;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fintech.omnipe.DashBoard.ServiceIcon;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Api.Object.AssignedOpType;
import com.fintech.omnipe.Util.CustomAlertDialog;

import java.util.ArrayList;
import java.util.List;



public class HomeOptionListAdapter extends RecyclerView.Adapter<HomeOptionListAdapter.MyViewHolder> {

    private final CustomAlertDialog mCustomAlertDialog;
    private int layout;
    private ClickView mClickView;
    private List<AssignedOpType> operatorList;
    private Activity mContext;

    public HomeOptionListAdapter(List<AssignedOpType> operatorList, Activity mContext,int layout, ClickView mClickView) {
        this.operatorList = operatorList;
        this.mContext = mContext;
        this.mClickView = mClickView;
        this.layout = layout;
        mCustomAlertDialog = new CustomAlertDialog(mContext,true);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final AssignedOpType operator = operatorList.get(position);

        if (operator.getSubOpTypeList() != null && operator.getSubOpTypeList().size() > 0) {
            ServiceIcon.INSTANCE.parentIcon(operator.getParentID(),holder.icon);
            holder.name.setText(operator.getService().replaceAll(" ","\n"));

        }else {
            if(operator.getServiceID()==11100){
                holder.comingSoonTv.setVisibility(View.VISIBLE);
            }
            holder.name.setText(operator.getName().replaceAll(" ","\n"));
            ServiceIcon.INSTANCE.serviceIcon(operator.getServiceID(),holder.icon);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!operator.getIsServiceActive()) {
                    Toast.makeText(mContext, "Coming Soon", Toast.LENGTH_SHORT).show();
                } else if (!operator.getIsActive()) {
                    String conytactTxt = "";
                    if (operator.getUpline() != null && !operator.getUpline().isEmpty() && operator.getUplineMobile() != null && !operator.getUplineMobile().isEmpty()) {
                        conytactTxt = operator.getUpline() + " : " + operator.getUplineMobile();
                    }
                    if (operator.getCcContact() != null && !operator.getCcContact().isEmpty()) {
                        conytactTxt = conytactTxt + "\nCustomer Care : " + operator.getCcContact();
                    }
                    mCustomAlertDialog.upgradePackageDialog(conytactTxt, new CustomAlertDialog.DialogSingleCallBack() {
                        @Override
                        public void onPositiveClick(String mobile, String emailId) {
                            if (mClickView != null) {
                                mClickView.onPackageUpgradeClick();
                            }
                        }
                    });
                } else {
                    if (mClickView != null) {
                        mClickView.onClick(operator,operator.getSubOpTypeList());
                    }
                }

            }
        });

    }



    @Override
    public int getItemCount() {
        return operatorList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,comingSoonTv;
        public ImageView icon;
        View itemView;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            comingSoonTv = view.findViewById(R.id.comingsoonTv);
            icon = view.findViewById(R.id.icon);

            itemView = view;

        }
    }

    public interface ClickView {
        void onClick(AssignedOpType mAssignedOpType, ArrayList<AssignedOpType> subOpTypeList);
        void onPackageUpgradeClick();
    }


}
