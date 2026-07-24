package com.fintech.omnipe.AccountSettlement.Adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.fintech.omnipe.AccountSettlement.API.SettlementAccountData;
import com.fintech.omnipe.AccountSettlement.UI.SettlementBankListActivity;
import com.fintech.omnipe.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SettlementAccountAdapter extends RecyclerView.Adapter<SettlementAccountAdapter.MyViewHolder>  {

    private Activity mActivity;
    private String charText;
    private List<SettlementAccountData> mBankListObjects;
    private List<SettlementAccountData> filterListItem;
    private boolean  isSettlementAccountVerify;

    public SettlementAccountAdapter(List<SettlementAccountData> mBankListObjects,boolean  isSettlementAccountVerify, Activity mActivity) {
        this.mActivity=mActivity;
        this.mBankListObjects=mBankListObjects;
        this.isSettlementAccountVerify=isSettlementAccountVerify;
        filterListItem=new ArrayList<>();
        filterListItem.addAll(mBankListObjects);

    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_bank_account_settlement, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        SettlementAccountData mItem= mBankListObjects.get(position);

        if(mItem.getAccountHolder()!=null && !mItem.getAccountHolder().isEmpty())
                holder.getUserNameTv().setText(mItem.getAccountHolder()+"");
        else
            holder.getUserNameTv().setText("NA");

        if(mItem.getBankName()!=null && !mItem.getBankName().isEmpty())
            holder.getBankNameTv().setText(mItem.getBankName()+"");
        else
            holder.getBankNameTv().setText("NA");

        if(mItem.getAccountNumber()!=null && !mItem.getAccountNumber().isEmpty())
            holder.getAccountNoTv().setText(mItem.getAccountNumber()+"");
        else
            holder.getAccountNoTv().setText("NA");


        if(mItem.getIfsc()!=null && !mItem.getIfsc().isEmpty())
            holder.getIfscTv().setText(mItem.getIfsc()+"");
        else
            holder.getIfscTv().setText("NA");

        if(mItem.getApprovalText()!=null && !mItem.getApprovalText().isEmpty())
            holder.getApprovalStatusTv().setText(mItem.getApprovalText()+"");
        else
            holder.getApprovalStatusTv().setText("NA");


        if(mItem.getApprovalText().equalsIgnoreCase("Not Applied")){
            holder.getApprovalStatusTv().setTextColor(Color.BLUE);
        }
        else if(mItem.getApprovalText().equalsIgnoreCase("Requested"))
        {
            holder.getApprovalStatusTv().setTextColor(Color.YELLOW);
        } else if(mItem.getApprovalText().equalsIgnoreCase("Approved")){
            holder.getApprovalStatusTv().setTextColor(Color.GREEN);
        } else if(mItem.getApprovalText().equalsIgnoreCase("Rejected")){
            holder.getApprovalStatusTv().setTextColor(Color.RED);
        }



        if(mItem.getApprovalStatus()==2)
           holder.getSwitchView().setVisibility(View.VISIBLE);
        else
            holder.getSwitchView().setVisibility(View.GONE);

        if(mItem.isDefault())
            holder.getActiveSwitch().setChecked(true);
        else
            holder.getActiveSwitch().setChecked(false);


        if(isSettlementAccountVerify)
        {
            holder.getVerificationStatusView().setVisibility(View.VISIBLE);
            if(mItem.getVerificationText()!=null && !mItem.getVerificationText().isEmpty())
                holder.getVerifyStatusTv().setText(mItem.getVerificationText()+"");
            else
                holder.getVerifyStatusTv().setText("NA");

            if(mItem.getApprovalText().equalsIgnoreCase("Not Applied")){
                holder.getVerifyStatusTv().setTextColor(Color.BLUE);
            }
            else if(mItem.getApprovalText().equalsIgnoreCase("Requested"))
            {
                holder.getVerifyStatusTv().setTextColor(Color.YELLOW);
            } else if(mItem.getApprovalText().equalsIgnoreCase("Approved")){
                holder.getVerifyStatusTv().setTextColor(Color.GREEN);
            } else if(mItem.getApprovalText().equalsIgnoreCase("Rejected")){
                holder.getVerifyStatusTv().setTextColor(Color.RED);
            }

            if(mItem.getVerificationStatus()==1){
                holder.getVerifyBtn().setVisibility(View.VISIBLE);
                holder.getVerifyBtn().setText("Update UTR");
            } else if(mItem.getVerificationStatus()==0){
                holder.getVerifyBtn().setVisibility(View.VISIBLE);
                holder.getVerifyBtn().setText("Verify");
            }else {
                holder.getVerifyBtn().setVisibility(View.GONE);
            }
        }
        else {
            holder.getVerifyBtn().setVisibility(View.GONE);
            holder.getVerificationStatusView().setVisibility(View.GONE);
        }


        holder.updateBtn.setOnClickListener(v -> {
            if (mActivity instanceof SettlementBankListActivity) {
                ((SettlementBankListActivity) mActivity).update(mItem);
            }

        });

        holder.verifyBtn.setOnClickListener(v -> {
            if(mItem.getVerificationStatus()==0){ //verify
                if (mActivity instanceof SettlementBankListActivity) {
                    ((SettlementBankListActivity) mActivity).verifyAccountUser(mItem.getId());
                }
            }else if(mItem.getVerificationStatus()==1){ //update UTR
                if (mActivity instanceof SettlementBankListActivity) {
                    ((SettlementBankListActivity) mActivity).updateUTR(mItem);
                }
            }
        });
        holder.deleteBtn.setOnClickListener(v -> {
            if (mActivity instanceof SettlementBankListActivity) {
                ((SettlementBankListActivity) mActivity).confirmationDialog(mItem);
            }

        });

        holder.switchView.setOnClickListener(v -> {
            if (mActivity instanceof SettlementBankListActivity) {
                ((SettlementBankListActivity) mActivity).setDefaultAccount(mItem.getId());
            }

        });


    }

    @Override
    public int getItemCount() {
        return mBankListObjects.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private AppCompatTextView userNameTv,bankNameTv,accountNoTv,ifscTv,approvalStatusTv,verifyStatusTv,activeTv;
        private View switchView,verificationStatusView;
        private SwitchCompat activeSwitch;
        private Button verifyBtn,updateBtn,deleteBtn;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            userNameTv=itemView.findViewById(R.id.userNameTv);
            bankNameTv=itemView.findViewById(R.id.bankNameTv);
            accountNoTv=itemView.findViewById(R.id.accountNoTv);
            ifscTv=itemView.findViewById(R.id.ifscTv);
            approvalStatusTv=itemView.findViewById(R.id.approvalStatusTv);
            verifyStatusTv=itemView.findViewById(R.id.verifyStatusTv);
            activeTv=itemView.findViewById(R.id.activeTv);

             //Layout View
            switchView=itemView.findViewById(R.id.switchView);
            verificationStatusView=itemView.findViewById(R.id.verificationStatusView);

            activeSwitch=itemView.findViewById(R.id.activeSwitch);

            verifyBtn=itemView.findViewById(R.id.verifyBtn);
            updateBtn=itemView.findViewById(R.id.updateBtn);
            deleteBtn=itemView.findViewById(R.id.deleteBtn);
        }

        public Button getVerifyBtn() {
            return verifyBtn;
        }

        public Button getUpdateBtn() {
            return updateBtn;
        }

        public Button getDeleteBtn() {
            return deleteBtn;
        }

        public View getVerificationStatusView() {
            return verificationStatusView;
        }

        public SwitchCompat getActiveSwitch() {
            return activeSwitch;
        }

        public AppCompatTextView getUserNameTv() {
            return userNameTv;
        }

        public AppCompatTextView getBankNameTv() {
            return bankNameTv;
        }

        public AppCompatTextView getAccountNoTv() {
            return accountNoTv;
        }

        public AppCompatTextView getIfscTv() {
            return ifscTv;
        }

        public AppCompatTextView getApprovalStatusTv() {
            return approvalStatusTv;
        }

        public AppCompatTextView getVerifyStatusTv() {
            return verifyStatusTv;
        }

        public AppCompatTextView getActiveTv() {
            return activeTv;
        }

        public View getSwitchView() {
            return switchView;
        }
    }


    // Filter Class
    public void filter(String charText) {
        this.charText = charText.toLowerCase(Locale.getDefault());

        mBankListObjects.clear();
        if (charText.length() == 0) {
            mBankListObjects.addAll(filterListItem);
        } else {
            for (SettlementAccountData wp : filterListItem) {
                if (wp.getAccountHolder().toLowerCase(Locale.getDefault()).contains(charText) || wp.getAccountNumber().toLowerCase(Locale.getDefault()).contains(charText) || wp.getIfsc().toLowerCase(Locale.getDefault()).contains(charText) || wp.getBankName().toLowerCase(Locale.getDefault()).contains(charText) || wp.getApprovalText().toLowerCase(Locale.getDefault()).contains(charText) || wp.getVerificationText().toLowerCase(Locale.getDefault()).contains(charText)) {
                    mBankListObjects.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
