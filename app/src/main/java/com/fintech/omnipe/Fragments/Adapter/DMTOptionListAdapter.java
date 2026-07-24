package com.fintech.omnipe.Fragments.Adapter;

import android.app.Activity;

import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fintech.omnipe.Activities.Adapter.HomeOptionListAdapter;
import com.fintech.omnipe.Fragments.dto.OperatorList;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.CustomAlertDialog;
import com.fintech.omnipe.Util.DataOpType;

import java.util.List;

/**
 * Created by Vishnu on 18-01-2017.
 */



public class DMTOptionListAdapter extends RecyclerView.Adapter<DMTOptionListAdapter.MyViewHolder> {

    ClickView mClickView;
    int layout;
    private List<OperatorList> operatorList;
    private Activity mContext;
    private DataOpType data;
    CustomAlertDialog mCustomAlertDialog;

    public DMTOptionListAdapter(List<OperatorList> operatorList, Activity mContext, ClickView mClickView, int layout) {
        this.operatorList = operatorList;
        this.mContext = mContext;
        this.mClickView = mClickView;
        this.layout = layout;
        mCustomAlertDialog = new CustomAlertDialog(mContext, true);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final OperatorList operator = operatorList.get(position);


        holder.name.setText(layout == R.layout.adapter_dashboard_option ? operator.getName().replaceAll("\n", " ") : operator.getName());
        //holder.icon.setImageResource(ServiceIcon.INSTANCE.serviceIcon(operator.getOpType()));
        setIcon(operator.getOpType(), holder);

        holder.itemView.setOnClickListener(v -> {

            if (mClickView != null) {

                mClickView.onClick(operator);
            }


        });

    }

    @Override
    public int getItemCount() {
        return operatorList.size();
    }

    void setIcon(int id, MyViewHolder holder) {

        if (id == 1) {
            holder.bgView.setImageResource(R.drawable.ic_cell_smartphone);
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.white));
            holder.icon.setImageResource(0);
        }
        if (id == 2) {
            holder.bgView.setImageResource(R.drawable.ic_cell_mobile);
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.white));
            holder.icon.setImageResource(0);
        }

        if (id == 6) {
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.color_mark));
            holder.icon.setImageResource(R.drawable.ic_gas);
        }
        if (id == 7) {
            holder.icon.setImageResource(R.drawable.red_circle);
            holder.icon.setImageResource(0);
        }
        if (id == 8) {
            holder.icon.setImageResource(R.drawable.red_circle);
            holder.icon.setImageResource(0);
        }
        if (id == 9) {
            holder.icon.setImageResource(R.drawable.red_circle);
            holder.icon.setImageResource(0);
        }
        if (id == 10) {
            holder.icon.setImageResource(R.drawable.red_circle);
            holder.icon.setImageResource(0);
        }
        if (id == 11) {
            holder.icon.setImageResource(R.drawable.red_circle);
            holder.icon.setImageResource(0);
        }
        if (id == 12) {
            holder.icon.setImageResource(R.drawable.red_circle);
            holder.icon.setImageResource(0);
        }
        if (id == 13) {
            holder.icon.setImageResource(R.drawable.red_circle);
            holder.icon.setImageResource(0);
        }
        if (id == 14) {
            holder.icon.setImageResource(R.drawable.ic_money);
         //   ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.reddishBrown));
        }
        if (id == 15) {
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.reddishBrown));
            holder.icon.setImageResource(R.drawable.red_circle);
        }
        if (id == 16) {
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.pressed));
          //  holder.icon.setImageResource(R.drawable.ic_broadband);
        }
        if (id == 17) {
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.color_cyan));
          //  holder.icon.setImageResource(R.drawable.ic_cell_water);
        }
        if (id == 18) { //Train
            holder.icon.setImageResource(R.drawable.ic_train);
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.color_brown));
        }
        if (id == 19) {
            holder.icon.setImageResource(R.drawable.red_circle);
            holder.icon.setImageResource(0);
        }
        if (id == 20) {
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.darkGreen));
            holder.icon.setImageResource(R.drawable.ic_customer_service);
        }
        if (id == 22) { //For AEPS
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.reddishBrown));
            holder.icon.setImageResource(R.drawable.aeps);
        }

        if (id == 24) { //For PSA
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.black));
          //  holder.icon.setImageResource(R.drawable.psa);
        }
        if (id == 26) {
            holder.icon.setImageResource(R.drawable.ic_gas);
          //  ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.color_deep_orange));

        }
        if (id == 25) {
          //  holder.icon.setImageResource(R.drawable.ic_loan);
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.reddishBrown));
        }
        if (id == 27) {
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.color_light_green));
            holder.icon.setImageResource(R.drawable.ic_life_insurance);
        }
        if (id == 28) {
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.grey));
         //   holder.icon.setImageResource(R.drawable.ic_bikeinsuarance);
        }

        if (id == 29) {
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.red));
            //  holder.icon.setImageResource(R.drawable.ic_car_insurance);
        }

        if (id == 19) {
            holder.icon.setImageResource(R.drawable.red_circle);
            holder.icon.setImageResource(0);
        }
        if (id == 35) {
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.red));
            // holder.icon.setImageResource(R.drawable.ic_hd_box);
            // return R.drawable.ic_hd_box;
        }
        if (id == 36) {
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.red));
            // holder.icon.setImageResource(R.drawable.ic_sd_box);
            // return R.drawable.ic_sd_box;
        }
        if (id == 38) {
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.color_blue_grey));
            holder.icon.setImageResource(R.drawable.ic_fastag);
        }
        if (id == 39) {
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.color_green));
          ///  holder.icon.setImageResource(R.drawable.ic_cabel_tv);
        }

        if (id == 50) {
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.color_green));
            //    holder.icon.setImageResource(R.drawable.ic_wallet);
        }

        if (id == 100) {
      //      ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.dark_blue));
            holder.icon.setImageResource(R.drawable.ic_commission_slab);
        }
        if (id == 101) {
            holder.imageContainer.getLayoutParams().height = (int) mContext.getResources().getDimension(R.dimen._38sdp);
            holder.imageContainer.getLayoutParams().width = (int) mContext.getResources().getDimension(R.dimen._38sdp);
            holder.bgView.setImageResource(R.drawable.ic_recharge_report_24dp);
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.white));
            holder.icon.setImageResource(0);

        }
        if (id == 102) {
            holder.imageContainer.getLayoutParams().height = (int) mContext.getResources().getDimension(R.dimen._38sdp);
            holder.imageContainer.getLayoutParams().width = (int) mContext.getResources().getDimension(R.dimen._38sdp);
          //  holder.bgView.setImageResource(R.drawable.ic_ledger_black_24dp);
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.white));
            holder.icon.setImageResource(0);
        }
        if (id == 103) {
            holder.imageContainer.getLayoutParams().height = (int) mContext.getResources().getDimension(R.dimen._38sdp);
            holder.imageContainer.getLayoutParams().width = (int) mContext.getResources().getDimension(R.dimen._38sdp);
            holder.bgView.setImageResource(R.drawable.ic_dmr_transaction_24dp);
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.white));
            holder.icon.setImageResource(0);
        }
        if (id == 104) {
            holder.imageContainer.getLayoutParams().height = (int) mContext.getResources().getDimension(R.dimen._38sdp);
            holder.imageContainer.getLayoutParams().width = (int) mContext.getResources().getDimension(R.dimen._38sdp);
            holder.bgView.setImageResource(R.drawable.ic_dispute);
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.white));
            holder.icon.setImageResource(0);
        }
        if (id == 105) {
            holder.imageContainer.getLayoutParams().height = (int) mContext.getResources().getDimension(R.dimen._38sdp);
            holder.imageContainer.getLayoutParams().width = (int) mContext.getResources().getDimension(R.dimen._38sdp);
         //   holder.bgView.setImageResource(R.drawable.ic_file);
            holder.bgView.setPadding(4, 6, 4, 8);
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.white));
            holder.icon.setImageResource(0);
        }
        if (id == 106) {
            holder.imageContainer.getLayoutParams().height = (int) mContext.getResources().getDimension(R.dimen._38sdp);
            holder.imageContainer.getLayoutParams().width = (int) mContext.getResources().getDimension(R.dimen._38sdp);
            holder.bgView.setImageResource(R.drawable.ic_exchnage_fund_24dp);
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.blue));
            holder.bgView.setPadding(4, 6, 4, 8);
            holder.icon.setImageResource(0);
        }
        if (id == 107) {
            holder.imageContainer.getLayoutParams().height = (int) mContext.getResources().getDimension(R.dimen._38sdp);
            holder.imageContainer.getLayoutParams().width = (int) mContext.getResources().getDimension(R.dimen._38sdp);
            holder.bgView.setImageResource(R.drawable.ic_fund_receive_black_24dp);
            holder.bgView.setPadding(4, 6, 4, 8);
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.white));
            holder.icon.setImageResource(0);
        }
        if (id == 108) {
            holder.imageContainer.getLayoutParams().height = (int) mContext.getResources().getDimension(R.dimen._38sdp);
            holder.imageContainer.getLayoutParams().width = (int) mContext.getResources().getDimension(R.dimen._38sdp);
            holder.bgView.setImageResource(R.drawable.ic_user_daybook_black_24dp);
            ImageViewCompat.setImageTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.dark_grey));
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.white));
            holder.icon.setImageResource(0);
        }
        if (id == 109) {
            holder.imageContainer.getLayoutParams().height = (int) mContext.getResources().getDimension(R.dimen._38sdp);
            holder.imageContainer.getLayoutParams().width = (int) mContext.getResources().getDimension(R.dimen._38sdp);
         //   holder.bgView.setImageResource(R.drawable.ic_report_pending);
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.white));
            holder.icon.setImageResource(0);
        }
        if (id == 110) {

            holder.icon.setImageResource(R.drawable.ic_commission_slab);
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.color_yellow));
            /* *//* holder.icon.setImageResource(R.drawable.ic_commission_slab);
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.white));
            holder.icon.setImageResource(0);*//*
            holder.imageContainer.getLayoutParams().height = (int) mContext.getResources().getDimension(R.dimen._40sdp);
            holder.imageContainer.getLayoutParams().width = (int) mContext.getResources().getDimension(R.dimen._40sdp);
            holder.bgView.setImageResource(R.drawable.ic_commission_slab);
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.grey));
            ImageViewCompat.setImageTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.style_color_accent));
            holder.icon.setImageResource(0);*/
        }
        if (id == 111) {
            holder.imageContainer.getLayoutParams().height = (int) mContext.getResources().getDimension(R.dimen._38sdp);
            holder.imageContainer.getLayoutParams().width = (int) mContext.getResources().getDimension(R.dimen._38sdp);
            holder.bgView.setImageResource(R.drawable.ic_create_user);
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.white));
            holder.icon.setImageResource(0);
        }
        if (id == 116) {
            holder.imageContainer.getLayoutParams().height = (int) mContext.getResources().getDimension(R.dimen._38sdp);
            holder.imageContainer.getLayoutParams().width = (int) mContext.getResources().getDimension(R.dimen._38sdp);
        //    holder.bgView.setImageResource(R.drawable.ic_create_fos);
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.white));
            holder.icon.setImageResource(0);
        }
        if (id == 112) {
            holder.imageContainer.getLayoutParams().height = (int) mContext.getResources().getDimension(R.dimen._38sdp);
            holder.imageContainer.getLayoutParams().width = (int) mContext.getResources().getDimension(R.dimen._38sdp);
         //   holder.bgView.setImageResource(R.drawable.ic_multi_user);
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.white));
            holder.icon.setImageResource(0);
        }
        if (id == 113) {
            holder.imageContainer.getLayoutParams().height = (int) mContext.getResources().getDimension(R.dimen._38sdp);
            holder.imageContainer.getLayoutParams().width = (int) mContext.getResources().getDimension(R.dimen._38sdp);
            holder.bgView.setImageResource(R.drawable.ic_account_balance_wallet_24dp);
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.white));
            holder.icon.setImageResource(0);
        }
        if (id == 114) {
            holder.imageContainer.getLayoutParams().height = (int) mContext.getResources().getDimension(R.dimen._38sdp);
            holder.imageContainer.getLayoutParams().width = (int) mContext.getResources().getDimension(R.dimen._38sdp);
         ///   holder.bgView.setImageResource(R.drawable.ic_wrong_right);
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.white));
            holder.icon.setImageResource(0);
        }

        if (id == 115) {
            holder.imageContainer.getLayoutParams().height = (int) mContext.getResources().getDimension(R.dimen._38sdp);
            holder.imageContainer.getLayoutParams().width = (int) mContext.getResources().getDimension(R.dimen._38sdp);
       //     holder.bgView.setImageResource(R.drawable.ic_aeps_report1);
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.white));
            holder.icon.setImageResource(0);
        }

        if (id == 117) {

        //    holder.icon.setImageResource(R.drawable.ic_scan_pay);
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.color_teal));
            /*holder.bgView.setImageResource(R.drawable.ic_scan_pay);
             *//*ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.grey));*//*
            holder.icon.setImageResource(0);*/
        }
    }

    void parentIcon(int parentId, HomeOptionListAdapter.MyViewHolder holder) {

        if (parentId == 19) {


        }


        if (parentId == 30) {
          //  ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.color_deep_orange));
          //  holder.icon.setImageResource(R.drawable.ic_cell_satellite_dish);

           /* holder.bgView.setImageResource(R.drawable.ic_cell_satellite_dish);//ic_dth_subscription
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.color_deep_orange));
            holder.icon.setImageResource(0);*/
            // return R.drawable.ic_dth_subscription;
        }

        if (parentId == 31) {
            // add money
          //  ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.color_teal));
            // holder.icon.setImageResource(R.drawable.ic_wallet);
        }
        //   return R.drawable.red_circle;
    }

    public interface ClickView {
        void onClick(OperatorList mOperatorList);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, comingsoonTv;
        public ImageView icon, bgView;
        RelativeLayout imageContainer;
        View itemView;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            /*comingsoonTv = view.findViewById(R.id.comingsoonTv);*/
            icon = view.findViewById(R.id.icon);
            imageContainer = view.findViewById(R.id.imageContainer);
            bgView = view.findViewById(R.id.bgView);
            itemView = view;

        }
    }
}


/*
public class DMTOptionListAdapter extends RecyclerView.Adapter<DMTOptionListAdapter.MyViewHolder> {

    ClickView mClickView;
    int layout;
    private List<OperatorList> operatorList;
    private Activity mContext;
    private DataOpType data;
    CustomAlertDialog mCustomAlertDialog;

    public DMTOptionListAdapter(List<OperatorList> operatorList, Activity mContext, ClickView mClickView, int layout) {
        this.operatorList = operatorList;
        this.mContext = mContext;
        this.mClickView = mClickView;
        this.layout = layout;
        mCustomAlertDialog = new CustomAlertDialog(mContext, true);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final OperatorList operator = operatorList.get(position);


            holder.name.setText(layout == R.layout.adapter_dashboard_option ? operator.getName().replaceAll("\n", " ") : operator.getName());
            holder.icon.setImageResource(R.drawable.ic_dmt);



        holder.itemView.setOnClickListener(v -> {

                if (mClickView != null) {

                    mClickView.onClick(operator);
                }


        });
       */
/* if (operator.getName() != null && operator.getName().toString().length() > 0) {
            RequestOptions requestOptions=new RequestOptions();
            requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(mContext).load(ApplicationConstant.INSTANCE.baseIconUrl+operator.getImage()).
                    apply(requestOptions).into(holder.opImage);

        } else {
            holder.opImage.setImageResource(R.drawable.ic_operator_default_icon);
        }*//*

    }

    @Override
    public int getItemCount() {
        return operatorList.size();
    }

    public interface ClickView {
        void onClick(OperatorList mOperatorList);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name*/
/*, comingsoonTv*//*
;
        public ImageView icon, bgView;
        RelativeLayout imageContainer;
        View itemView;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
          //  comingsoonTv = view.findViewById(R.id.comingsoonTv);
            icon = view.findViewById(R.id.icon);
            imageContainer = view.findViewById(R.id.imageContainer);
            bgView = view.findViewById(R.id.bgView);
            itemView = view;

        }
    }
}
*/
