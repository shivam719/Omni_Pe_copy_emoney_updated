package com.fintech.omnipe.AppUser.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.fintech.omnipe.Activities.Adapter.WalletTypeAdapter;
import com.fintech.omnipe.Activities.UpgradePackageActivity;
import com.fintech.omnipe.Api.Object.BalanceData;
import com.fintech.omnipe.Api.Object.BalanceType;
import com.fintech.omnipe.Api.Object.UserList;
import com.fintech.omnipe.Api.Object.WalletType;
import com.fintech.omnipe.Api.Request.FundTransferRequest;
import com.fintech.omnipe.Api.Response.AppUserListResponse;
import com.fintech.omnipe.Api.Response.BasicResponse;
import com.fintech.omnipe.Api.Response.WalletTypeResponse;
import com.fintech.omnipe.AppUser.Activity.AppUserListActivity;
import com.fintech.omnipe.AppUser.AppUserListFragment;
import com.fintech.omnipe.Auth.dto.LoginResponse;
import com.fintech.omnipe.BuildConfig;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.ApiClient;
import com.fintech.omnipe.Util.ApplicationConstant;
import com.fintech.omnipe.Util.EndPointInterface;
import com.fintech.omnipe.Util.UtilMethods;
import com.fintech.omnipe.usefull.CustomLoader;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;


public class AppUserListAdapter extends RecyclerView.Adapter<AppUserListAdapter.MyViewHolder> implements Filterable {

    private ArrayList<UserList> listItem;
    private ArrayList<UserList> filterListItem;
    private Activity mContext;
    private AlertDialog alertDialogFundTransfer;
    private boolean oType;
    private int walletType = 1;
    FundTransferCallBAck mFundTransferCallBAck;
    double calculatedVal = 0.0;
    BalanceData mBalanceData;
    boolean isAccountStatement;
    AppUserListFragment appUserListFragment;
    CustomLoader loader; LoginResponse mLoginResponse;
    public AppUserListAdapter(Activity mContext, ArrayList<UserList> mUserLists, BalanceData mBalanceData,
                              boolean isAccountStatement, FundTransferCallBAck mFundTransferCallBAck,
                              AppUserListFragment appUserListFragment, CustomLoader loader, LoginResponse mLoginResponse) {
        this.listItem = mUserLists;
        this.filterListItem = mUserLists;
        this.mFundTransferCallBAck = mFundTransferCallBAck;
        this.mContext = mContext;
        this.mBalanceData = mBalanceData;
        this.appUserListFragment = appUserListFragment;
        this.isAccountStatement = isAccountStatement;
        this.loader = loader;
        this.mLoginResponse = mLoginResponse;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout mainView;
        private LinearLayout outletNameView;
        private AppCompatTextView ouyletNameTv;
        private LinearLayout mobileView;
        private AppCompatTextView mobileTv;
        private LinearLayout slabView;
        private AppCompatTextView slabTv;
        private LinearLayout joinByView, switchView;
        private AppCompatTextView joinByTv;
        private AppCompatImageView calanderIcon;
        private AppCompatTextView dateTv;
        private LinearLayout rightView;
        private SwitchCompat activeSwitch;
        private AppCompatTextView activeTv;
        private AppCompatTextView otpStatusTv;
        private AppCompatTextView kycStatusTv, cappingTv;

        private AppCompatButton editBtn;
        private AppCompatButton fundTransferBtn, collectionBtn;
        private AppCompatButton upgradePackageBtn;
        private RecyclerView balRecycerView;
        private View cappingView;

        public MyViewHolder(View view) {
            super(view);
            mainView = (RelativeLayout) view.findViewById(R.id.mainView);
            outletNameView = (LinearLayout) view.findViewById(R.id.outletNameView);
            ouyletNameTv = (AppCompatTextView) view.findViewById(R.id.ouyletNameTv);
            mobileView = (LinearLayout) view.findViewById(R.id.mobileView);
            mobileTv = (AppCompatTextView) view.findViewById(R.id.mobileTv);
            slabView = (LinearLayout) view.findViewById(R.id.slabView);
            slabTv = (AppCompatTextView) view.findViewById(R.id.slabTv);
            joinByView = (LinearLayout) view.findViewById(R.id.joinByView);
            joinByTv = (AppCompatTextView) view.findViewById(R.id.joinByTv);
            calanderIcon = (AppCompatImageView) view.findViewById(R.id.calanderIcon);
            dateTv = (AppCompatTextView) view.findViewById(R.id.dateTv);
            rightView = (LinearLayout) view.findViewById(R.id.rightView);
            activeSwitch = (SwitchCompat) view.findViewById(R.id.activeSwitch);
            activeTv = (AppCompatTextView) view.findViewById(R.id.activeTv);
            otpStatusTv = (AppCompatTextView) view.findViewById(R.id.otpStatusTv);
            kycStatusTv = (AppCompatTextView) view.findViewById(R.id.kycStatusTv);
            cappingTv = view.findViewById(R.id.cappingTv);

            editBtn = (AppCompatButton) view.findViewById(R.id.editBtn);
            switchView = view.findViewById(R.id.switchView);
            cappingView = view.findViewById(R.id.cappingView);
            fundTransferBtn = (AppCompatButton) view.findViewById(R.id.fundTransferBtn);
            collectionBtn = (AppCompatButton) view.findViewById(R.id.collectionBtn);
            upgradePackageBtn = (AppCompatButton) view.findViewById(R.id.upgradePackageBtn);
            balRecycerView = view.findViewById(R.id.balRecycerView);
            balRecycerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.app_user_adapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final UserList mItem = filterListItem.get(position);
        holder.dateTv.setText(mItem.getJoinDate());
        holder.joinByTv.setText(mItem.getJoinBy());

        if (mItem.getRoleID()==3) {
            holder.upgradePackageBtn.setVisibility(View.VISIBLE);
        } else {
            holder.upgradePackageBtn.setVisibility(View.GONE);
        }
        if (mItem.roleID == 8 && isAccountStatement) {
            holder.collectionBtn.setVisibility(View.VISIBLE);
        } else {
            holder.collectionBtn.setVisibility(View.GONE);
        }
        if (mItem.getbCapping() != 0) {
            holder.cappingTv.setText(UtilMethods.INSTANCE.formatedAmount(mItem.getbCapping() + ""));
        } else {
            holder.cappingView.setVisibility(View.GONE);
        }
        if (mItem.getBalanceTypes() != null && mItem.getBalanceTypes().size() > 0) {
            holder.balRecycerView.setAdapter(new AppUserBalanceAdapter(mContext, mItem.getBalanceTypes()));
        } else {
            ArrayList<BalanceType> mBalanceTypes = new ArrayList<>();
            if (mBalanceData.isBalance()) {
                mBalanceTypes.add(new BalanceType(mBalanceData.getPrepaidWalletName(), mItem.getBalance()+""));
            }
            if (mBalanceData.isUBalance()) {
                mBalanceTypes.add(new BalanceType(mBalanceData.getUtilityWalletName(), mItem.getuBalance()+""));
            }
            if (mBalanceData.isBBalance()) {
                mBalanceTypes.add(new BalanceType(mBalanceData.getBankWalletName(), mItem.getbBalance()+""));
            }
            if (mBalanceData.isCBalance()) {
                mBalanceTypes.add(new BalanceType(mBalanceData.getCardWalletName(), mItem.getcBalance()+""));
            }
            if (mBalanceData.isIDBalance()) {
                mBalanceTypes.add(new BalanceType(mBalanceData.getRegIDWalletName(), mItem.getIdBalnace()+""));
            }
            if (mBalanceData.isPacakgeBalance() &&
                    (mItem.getRoleID() != 3 ||
                            !mBalanceData.isPackageDeducionForRetailor() &&
                                    mItem.getRoleID() == 3)) {
                mBalanceTypes.add(new BalanceType(mBalanceData.getPackageWalletName(), mItem.getPacakgeBalance()+""));
            }
            if (isAccountStatement) {
                mBalanceTypes.add(new BalanceType("Outstanding Balance", mItem.getOsBalance()+""));
            }
            mItem.setBalanceTypes(mBalanceTypes);
            holder.balRecycerView.setAdapter(new AppUserBalanceAdapter(mContext, mBalanceTypes));
        }

        holder.slabTv.setText(mItem.getSlab());
        holder.mobileTv.setText(mItem.getMobileNo());
        holder.ouyletNameTv.setText(mItem.getOutletName() + "[ " + mItem.getPrefix() + mItem.getId() + " ]");

        holder.kycStatusTv.setText("KYC\n" + mItem.getKycStatusStr());
        GradientDrawable bgShape = (GradientDrawable) holder.kycStatusTv.getBackground();

        if (mItem.getKycStatus() == 1) {
            bgShape.setColor(mContext.getResources().getColor(R.color.grey));
        } else if (mItem.getKycStatus() == 2) {
            bgShape.setColor(mContext.getResources().getColor(R.color.colorlink));
        } else if (mItem.getKycStatus() == 3) {
            bgShape.setColor(mContext.getResources().getColor(R.color.darkGreen));
        } else if (mItem.getKycStatus() == 4) {
            bgShape.setColor(mContext.getResources().getColor(R.color.color_amber));
        } else {
            bgShape.setColor(mContext.getResources().getColor(R.color.red));
        }
        if (mItem.isOTP()) {
            holder.otpStatusTv.setBackgroundResource(R.drawable.rounded_green);
            holder.otpStatusTv.setText("OTP Enable");
        } else {
            holder.otpStatusTv.setBackgroundResource(R.drawable.rounded_red);
            holder.otpStatusTv.setText("OTP Disable");
        }
        if (mItem.isStatus()) {
            holder.activeSwitch.setChecked(true);
            holder.activeTv.setTextColor(mContext.getResources().getColor(R.color.green));
            holder.activeTv.setText("Active");
        } else {
            holder.activeSwitch.setChecked(false);
            holder.activeTv.setTextColor(mContext.getResources().getColor(R.color.red));
            holder.activeTv.setText("Inactive");
        }



        holder.collectionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appUserListFragment.DistributorCollectionFromFosActivity(mItem);

            }
        });
        holder.upgradePackageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, UpgradePackageActivity.class);
                mContext.startActivity(intent);
            }
        });

        holder.fundTransferBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UtilMethods.INSTANCE.getIsFlatCommission(mContext)) {

                    UtilMethods.INSTANCE.GeUserCommissionRate((Activity) mContext, mItem.getId(), loader, mLoginResponse, object -> {
                        BasicResponse mBasicResponse = (BasicResponse) object;
                        showFundTransferDialog(mItem.getRoleID(), mItem.getId(), holder.ouyletNameTv.getText().toString(), mItem.getMobileNo(), mBasicResponse.getCommRate());

                    });
                } else {

                    showFundTransferDialog(mItem.getRoleID(), mItem.getId(), holder.ouyletNameTv.getText().toString(), mItem.getMobileNo(), mItem.getCommRate());
                }

            }
        });

        holder.switchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeStatusApi("", mItem.getId(), position, holder.activeSwitch, holder.activeTv, holder.ouyletNameTv.getText().toString());
            }
        });

    }

    @Override
    public int getItemCount() {
        return filterListItem.size();
    }

    private void showFundTransferDialog(int roleId, final int uId, final String name, String mobile, final double commission) {
        try {
            if (alertDialogFundTransfer != null && alertDialogFundTransfer.isShowing()) {
                return;
            }

            AlertDialog.Builder dialogBuilder;
            dialogBuilder = new AlertDialog.Builder(mContext);
            alertDialogFundTransfer = dialogBuilder.create();
            alertDialogFundTransfer.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            LayoutInflater inflater = mContext.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_fund_transfer, null);
            alertDialogFundTransfer.setView(dialogView);

            AppCompatImageView closeIv = (AppCompatImageView) dialogView.findViewById(R.id.closeIv);
            LinearLayout changetTypeView = dialogView.findViewById(R.id.changetTypeView);
            final AppCompatTextView prepaidTv = (AppCompatTextView) dialogView.findViewById(R.id.prepaidTv);
            final AppCompatTextView utilityTv = (AppCompatTextView) dialogView.findViewById(R.id.utilityTv);
            final AppCompatTextView creditTv = (AppCompatTextView) dialogView.findViewById(R.id.creditTv);
            final AppCompatTextView debitTv = (AppCompatTextView) dialogView.findViewById(R.id.debitTv);
            AppCompatTextView nameTv = (AppCompatTextView) dialogView.findViewById(R.id.nameTv);
            AppCompatTextView mobileTv = (AppCompatTextView) dialogView.findViewById(R.id.mobileTv);
            final AppCompatEditText amountEt = (AppCompatEditText) dialogView.findViewById(R.id.amountEt);
            final AppCompatTextView amountTv = (AppCompatTextView) dialogView.findViewById(R.id.amountTv);
            amountTv.setVisibility(View.GONE);
            amountEt.setVisibility(View.VISIBLE);
            AppCompatEditText commisionTv = (AppCompatEditText) dialogView.findViewById(R.id.commisionTv);
            final AppCompatEditText remarksEt = (AppCompatEditText) dialogView.findViewById(R.id.remarksEt);
            final AppCompatTextView amountTxtTv = (AppCompatTextView) dialogView.findViewById(R.id.amountTxtTv);
            AppCompatTextView cancelBtn = (AppCompatTextView) dialogView.findViewById(R.id.cancelBtn);
            AppCompatTextView fundTransferBtn = (AppCompatTextView) dialogView.findViewById(R.id.fundTransferBtn);
            final AppCompatTextView pinPassTv = (AppCompatTextView) dialogView.findViewById(R.id.pinPassTv);
            final AppCompatEditText pinPassEt = (AppCompatEditText) dialogView.findViewById(R.id.pinPassEt);
            if (UtilMethods.INSTANCE.getDoubleFactorPref(mContext)) {
                pinPassTv.setVisibility(View.VISIBLE);
                pinPassEt.setVisibility(View.VISIBLE);
            } else {
                pinPassTv.setVisibility(View.GONE);
                pinPassEt.setVisibility(View.GONE);
            }

            View creditDebitView = dialogView.findViewById(R.id.creditDebitView);
            String LoginResponse = UtilMethods.INSTANCE.getLoginPref(mContext);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);

            if (LoginDataResponse.getData().isCanDebit()) {
                creditDebitView.setVisibility(View.VISIBLE);
            } else {
                creditDebitView.setVisibility(View.GONE);
                oType = false;
            }
            SwitchCompat actCreditSwitch= dialogView.findViewById(R.id.actCreditSwitch);
            if(LoginDataResponse.isAccountStatement()){
                actCreditSwitch.setVisibility(View.VISIBLE);
                actCreditSwitch.setChecked(true);
            }else {
                actCreditSwitch.setVisibility(View.GONE);
            }

            View walletTypeView = dialogView.findViewById(R.id.walletTypeView);
            RecyclerView recyclerView = dialogView.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            WalletTypeResponse mWalletTypeResponse = new Gson().fromJson(UtilMethods.INSTANCE.getWalletType(mContext), WalletTypeResponse.class);

            if (mWalletTypeResponse != null && mWalletTypeResponse.getWalletTypes() != null && mWalletTypeResponse.getWalletTypes().size() > 0) {
                ArrayList<WalletType> mWalletTypesList = new ArrayList<>();
                for (WalletType mWalletType : mWalletTypeResponse.getWalletTypes()) {
                    if (mWalletType.getInFundProcess()) {
                        if (roleId == 3) {
                            if (mWalletType.getId() == 6) {
                                if (mWalletType.isPackageDedectionForRetailor()) {
                                    mWalletTypesList.add(mWalletType);
                                }
                            } else {
                                mWalletTypesList.add(mWalletType);
                            }
                        } else {
                            mWalletTypesList.add(mWalletType);
                        }


                    }
                }
                if (mWalletTypesList.size() <= 2 && creditDebitView.getVisibility() == View.VISIBLE) {
                    changetTypeView.setOrientation(LinearLayout.HORIZONTAL);
                } else {
                    changetTypeView.setOrientation(LinearLayout.VERTICAL);
                }

                walletTypeView.setVisibility(View.GONE);
                recyclerView.setAdapter(new WalletTypeAdapter(mContext, mWalletTypesList, new WalletTypeAdapter.onClick() {
                    @Override
                    public void onClick(int id) {
                        walletType = id;
                    }
                }));
            } else {
                walletTypeView.setVisibility(View.VISIBLE);
            }


            nameTv.setText(name);
            mobileTv.setText(mobile);
            commisionTv.setText(commission + "");
            oType = false;
            walletType = 1;

            prepaidTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    prepaidTv.setBackgroundResource(R.drawable.rounded_light_green);
                    prepaidTv.setTextColor(Color.WHITE);
                    utilityTv.setBackgroundResource(0);
                    utilityTv.setTextColor(mContext.getResources().getColor(R.color.lightDarkGreen));
                    walletType = 1;
                }
            });

            utilityTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    utilityTv.setBackgroundResource(R.drawable.rounded_light_green);
                    utilityTv.setTextColor(Color.WHITE);
                    prepaidTv.setBackgroundResource(0);
                    prepaidTv.setTextColor(mContext.getResources().getColor(R.color.lightDarkGreen));
                    walletType = 2;
                }
            });

            creditTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    creditTv.setBackgroundResource(R.drawable.rounded_light_red);
                    creditTv.setTextColor(Color.WHITE);
                    debitTv.setBackgroundResource(0);
                    debitTv.setTextColor(mContext.getResources().getColor(R.color.red));
                    oType = false;
                }
            });

            debitTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    debitTv.setBackgroundResource(R.drawable.rounded_light_red);
                    debitTv.setTextColor(Color.WHITE);
                    creditTv.setBackgroundResource(0);
                    creditTv.setTextColor(mContext.getResources().getColor(R.color.red));
                    oType = true;
                }
            });


            closeIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialogFundTransfer.dismiss();
                }
            });

            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialogFundTransfer.dismiss();
                }
            });

            fundTransferBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (amountEt.getText().toString().isEmpty()) {
                        amountEt.setError(mContext.getResources().getString(R.string.err_empty_field));
                        amountEt.requestFocus();
                        return;
                    } else if (pinPassEt.getVisibility() == View.VISIBLE && pinPassEt.getText().toString().isEmpty()) {
                        pinPassEt.setError("Please Enter Pin Password");
                        pinPassEt.requestFocus();
                        return;
                    }

                    ((AppUserListActivity) mContext).fundTransferApi(actCreditSwitch.isChecked(),pinPassEt.getText().toString(), oType, uId, remarksEt.getText().toString(), walletType, amountEt.getText().toString(), name, alertDialogFundTransfer, new AppUserListActivity.FundTransferCallBAck() {
                        @Override
                        public void onSucess() {
                            if (mFundTransferCallBAck != null) {
                                mFundTransferCallBAck.onSucessFund();
                            }
                        }
                    });
                }
            });

            amountEt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() > 0) {
                        try {
                            long amountVal = Long.parseLong(s.toString());

                            calculatedVal = amountVal + ((amountVal * commission) / 100);
                            amountTxtTv.setText("Transferable Amount is \u20B9 " + calculatedVal);
                        } catch (NumberFormatException nfe) {

                        }
                    }
                }
            });

            alertDialogFundTransfer.show();

        } catch (IllegalStateException ise) {

        } catch (IllegalArgumentException iae) {

        } catch (Exception e) {
            UtilMethods.INSTANCE.Error(mContext,e.getMessage());
        }
    }



    public void changeStatusApi(String securityKey, int uid, final int position, final SwitchCompat switchView, final TextView activeText, final String name) {
        try {

            String LoginResponse = UtilMethods.INSTANCE.getLoginPref(mContext);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<AppUserListResponse> call = git.ChangeUserStatus(new FundTransferRequest(false,securityKey, false, uid, "", 0, 0, "", LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    UtilMethods.INSTANCE.getIMEI(mContext),
                    "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(mContext), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<AppUserListResponse>() {

                @Override
                public void onResponse(Call<AppUserListResponse> call, retrofit2.Response<AppUserListResponse> response) {
//                     //Log.e("Payment response", "hello response : " + new Gson().toJson(response.body()).toString());

                    AppUserListResponse data = response.body();
                    if (data != null) {
                        if (data.getStatuscode() == 1) {
                            UtilMethods.INSTANCE.Successful(mContext, data.getMsg().replace("{User}", name));
                            if (switchView.isChecked()) {
                                switchView.setChecked(false);
                                activeText.setText("Inactive");
                                activeText.setTextColor(mContext.getResources().getColor(R.color.red));
                                filterListItem.get(position).setStatus(false);
                                for (int i = 0; i < listItem.size(); i++) {
                                    if (listItem.get(i).getId() == filterListItem.get(position).getId()) {
                                        listItem.get(i).setStatus(false);
                                        break;
                                    }
                                }
                            } else {
                                switchView.setChecked(true);
                                activeText.setTextColor(mContext.getResources().getColor(R.color.green));
                                activeText.setText("Active");
                                filterListItem.get(position).setStatus(true);
                                for (int i = 0; i < listItem.size(); i++) {
                                    if (listItem.get(i).getId() == filterListItem.get(position).getId()) {
                                        listItem.get(i).setStatus(true);
                                        break;
                                    }
                                }
                            }
                            notifyDataSetChanged();
                        } else if (response.body().getStatuscode() == -1) {

                            UtilMethods.INSTANCE.Error(mContext, data.getMsg().replace("{User}", name));
                        } else if (response.body().getStatuscode() == 0) {

                            UtilMethods.INSTANCE.Error(mContext, data.getMsg().replace("{User}", name));
                        } else {

                            UtilMethods.INSTANCE.Error(mContext, data.getMsg().replace("{User}", name));
                        }

                    } else {

                        UtilMethods.INSTANCE.Error(mContext, mContext.getResources().getString(R.string.some_thing_error));
                    }
                }

                @Override
                public void onFailure(Call<AppUserListResponse> call, Throwable t) {
                    //Log.e("response", "error ");

                    try {

                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                            if (t.getMessage().contains("No address associated with hostname")) {

                                UtilMethods.INSTANCE.Error(mContext, mContext.getResources().getString(R.string.err_msg_network));


                            } else {

                                UtilMethods.INSTANCE.Error(mContext, t.getMessage());


                            }

                        } else {

                            UtilMethods.INSTANCE.Error(mContext, mContext.getResources().getString(R.string.some_thing_error));
                        }
                    } catch (IllegalStateException ise) {

                        UtilMethods.INSTANCE.Error(mContext, mContext.getResources().getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();

            UtilMethods.INSTANCE.Error(mContext, mContext.getResources().getString(R.string.some_thing_error));
        }

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
                    ArrayList<UserList> filteredList = new ArrayList<>();
                    for (UserList row : listItem) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getMobileNo().toLowerCase().contains(charString.toLowerCase()) || row.getOutletName().toLowerCase().contains(charString.toLowerCase()) ||
                                (row.getBalance() + "").toLowerCase().contains(charString.toLowerCase()) || row.getSlab().toLowerCase().contains(charString.toLowerCase()) || row.getJoinBy().toLowerCase().contains(charString.toLowerCase())) {
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
                filterListItem = (ArrayList<UserList>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface FundTransferCallBAck {
        void onSucessFund();

        void onSucessEdit();
    }
}
