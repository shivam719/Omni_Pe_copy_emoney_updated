package com.fintech.omnipe.DashBoard.UI;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fintech.omnipe.Activities.AEPSReportActivity;
import com.fintech.omnipe.Activities.Adapter.WalletBalanceAdapter;
import com.fintech.omnipe.Activities.DMRReport;
import com.fintech.omnipe.Activities.DisputeReport;
import com.fintech.omnipe.Activities.FundOrderPendingActivity;
import com.fintech.omnipe.Activities.FundRecReport;
import com.fintech.omnipe.Activities.FundReqReport;
import com.fintech.omnipe.Activities.LedgerReport;
import com.fintech.omnipe.Activities.RechargeHistory;
import com.fintech.omnipe.Activities.ShareActivity;
import com.fintech.omnipe.Activities.UserDayBookActivity;
import com.fintech.omnipe.Activities.W2RHistory;
import com.fintech.omnipe.Api.Object.BalanceType;
import com.fintech.omnipe.Api.Object.UserList;
import com.fintech.omnipe.Api.Response.AppUserListResponse;
import com.fintech.omnipe.Api.Response.BalanceResponse;
import com.fintech.omnipe.Api.Response.WalletTypeResponse;
import com.fintech.omnipe.AppUser.Activity.AccountStatementReportActivity;
import com.fintech.omnipe.Auth.dto.LoginResponse;
import com.fintech.omnipe.CommissionSlab.ui.CommissionScreen;
import com.fintech.omnipe.DTHSubscription.Activity.DthSubscriptionReportActivity;
import com.fintech.omnipe.DashBoard.CustomAllTypeService;
import com.fintech.omnipe.DashBoard.Interface.RefreshOpTypeCallBack;
import com.fintech.omnipe.Fragments.HomeFragment;
import com.fintech.omnipe.Fragments.ProfileFragment;
import com.fintech.omnipe.Fragments.WebViewFragment;
import com.fintech.omnipe.MoveToWallet.UI.MoveToWalletActivity;
import com.fintech.omnipe.MoveToWallet.UI.MoveToWalletReportActivity;
import com.fintech.omnipe.Notification.NotificationFragment;
import com.fintech.omnipe.UPIPayment.Activity.QRCodePaymentActivity;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.ApplicationConstant;
import com.fintech.omnipe.Util.ChangePassUtils;
import com.fintech.omnipe.Util.CustomAlertDialog;
import com.fintech.omnipe.Util.GetBackFragment;
import com.fintech.omnipe.Util.UpgradePackageCallBack;
import com.fintech.omnipe.Util.UtilMethods;
import com.fintech.omnipe.usefull.CustomLoader;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class DashBoardLayout extends AppCompatActivity implements View.OnClickListener {

    public boolean isOpImgDrawn;
    private ResideMenu resideMenu;
    private DashBoardLayout mContext;
    private boolean isBulkQRGeneration,isQRMappedToUser;
    public RefreshOpTypeCallBack refreshOpTypeCallBack;

    private ResideMenuItem itemHome;
    private ResideMenuItem itemProfile;
    private ResideMenuItem itemAboutUs;
    private ResideMenuItem itemInvite;
    private ResideMenuItem itemLogout;

    private ResideMenuItem itemRechargeReport;
    private ResideMenuItem itemAEPSRechargeReport;
    private ResideMenuItem itemDTHhSubsReport;
    private ResideMenuItem itemLedgerReport;
    private ResideMenuItem itemMoveBankReport;
    private ResideMenuItem itemDisputeReport;
    private ResideMenuItem itemFundOrderReport, itemFundOrderPending;
    private ResideMenuItem itemDMRTransaction;
    private ResideMenuItem itemUserDayBook, itemDMTDayBook;
    private ResideMenuItem itemCommission;
    private ResideMenuItem itemUserW2R;
    private ResideMenuItem itemFOSList;
    private ResideMenuItem itemDebitCreditReport;
    private ResideMenuItem itemAccountStatement;
    private TextView tvTitle;
    private TextView tvBalance;
    private FloatingActionButton fab;
    View LayoutNotification,qrIv,virtualIv,shareIv;

    int exit_check = 0;
    CustomLoader loader;
    public UpgradePackageCallBack mUpgradePackageCallBack;
    TextView ViewCount;
    SharedPreferences myPrefs;
    SharedPreferences.Editor editor;

    public static String Mobile = "";
    private int notificationCount;
    private ChangePassUtils mChangePassUtils;
    private CustomAlertDialog customAlertDialog;
    private BalanceResponse balanceCheckResponse;
    private LoginResponse loginPrefResponse;
    private String userRoleId;
    private int walletCount;
    private View walletArrowIcon;
    private int INTENT_UPGRADE_PACKAGE = 3951;
   // public boolean isDMTWithPipe;
    private CustomAllTypeService apiData;
    private ArrayList<ResideMenuItem> leftMenu;
    private ArrayList<ResideMenuItem> rightMenu;
    private ImageView wallet_to_wallet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_dashboard_layout);
        mContext = this;
        customAlertDialog = new CustomAlertDialog(this, true);
        mChangePassUtils = new ChangePassUtils(this);

        UtilMethods.INSTANCE.InviteReferralDialog(this, false);


        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        myPrefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNameLoginPref, MODE_PRIVATE);
        editor = myPrefs.edit();
        loginPrefResponse = new Gson().fromJson(myPrefs.getString(ApplicationConstant.INSTANCE.LoginPref, ""), LoginResponse.class);
        userRoleId=loginPrefResponse.getData().getRoleID();
        getIDS();
        apiData= new Gson().fromJson(UtilMethods.INSTANCE.getActiveService(mContext),CustomAllTypeService.class);
        balanceCheckResponse = new Gson().fromJson(UtilMethods.INSTANCE.getBalance(mContext), BalanceResponse.class);
        Handler handler = new Handler();
        Runnable mRunnable = new Runnable() {
            public void run() {

                checkNumberList();
            }
        };
        handler.postDelayed(mRunnable, 2500);
        setStoredData();
        seUpDrawerMenu();

        if (savedInstanceState == null)
            changeFragment(new HomeFragment());

        hitApi(false);
        SetBalance();
        findViewById(R.id.walletView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWalletListPopupWindow();
            }
        });

        UtilMethods.INSTANCE.AppPopup(this, loader);
        LocalBroadcastManager.getInstance(this).registerReceiver(mNewNotificationReciver,
                new IntentFilter("New_Notification_Detect"));
    }

    private void getIDS() {
        walletArrowIcon = findViewById(R.id.walletArrowIcon);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        shareIv =findViewById(R.id.title_bar_share);
        qrIv = findViewById(R.id.qrIv);
        virtualIv = findViewById(R.id.virtualIv);
        tvBalance = (TextView) findViewById(R.id.tv_balance);
        ViewCount = (TextView) findViewById(R.id.view_alert_count_textview);
        LayoutNotification = (View) findViewById(R.id.layout_notification);
        LayoutNotification.setOnClickListener(this);
        wallet_to_wallet=findViewById(R.id.wallet_to_wallet);
       /* wallet_to_wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoardLayout.this, WalletToWalletTransferActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
            }
        });*/
        fab =findViewById(R.id.fab);
        qrIv.setVisibility(View.GONE);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UtilMethods.INSTANCE.isNetworkAvialable(mContext)) {
                    RotateAnimation rotate = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    rotate.setDuration(500);
                    rotate.setRepeatCount(Animation.INFINITE);
                    rotate.setInterpolator(new LinearInterpolator());
                    fab.startAnimation(rotate);
                    hitApi(true);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            fab.clearAnimation();
                        }
                    }, 5000);
                } else {
                    UtilMethods.INSTANCE.NetworkError(mContext, getResources().getString(R.string.err_msg_network_title),
                            getResources().getString(R.string.err_msg_network));
                }
            }
        });

    }

    private void checkNumberList() {
        if (UtilMethods.INSTANCE.getNumberList(mContext) != null
                && UtilMethods.INSTANCE.getNumberList(mContext).isEmpty()) {
            loader.show();
            UtilMethods.INSTANCE.NumberList(mContext, loader,null);
        }
    }

    private void setStoredData() {

        if(UtilMethods.INSTANCE.getIsReferral(this))
            shareIv.setVisibility(View.VISIBLE);
        else
            shareIv.setVisibility(View.GONE);

        if(apiData!=null && apiData.isUPIQR()){
            qrIv.setVisibility(View.VISIBLE);
        }else{
            qrIv.setVisibility(View.GONE);
        }

        if(apiData!=null && apiData.isECollection()){
            virtualIv.setVisibility(View.VISIBLE);
        }else{
            virtualIv.setVisibility(View.GONE);
        }
        virtualIv.setOnClickListener(this);
        findViewById(R.id.title_bar_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DashBoardLayout.this, ShareActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
            }
        });
        findViewById(R.id.qrIv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DashBoardLayout.this, QRCodePaymentActivity.class);
                i.putExtra("isQRMappedToUser",isQRMappedToUser);
                i.putExtra("isBulkQRGeneration",isBulkQRGeneration);
                i.putExtra("isECollectEnable",false);
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
            }
        });

        wallet_to_wallet.setOnClickListener(v->{
            startActivity(new Intent(DashBoardLayout.this, WalletToWalletTransferActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                }
        );


    }

    private void seUpDrawerMenu(){
        // attach to current activity;

        if(resideMenu==null){
            resideMenu = new ResideMenu(mContext);

            resideMenu.setBackground(R.color.colorPrimary);

            resideMenu.setScaleValue(0.6f);

            resideMenu.attachToActivity(mContext);

            /*--Left Side Menu--*/

            // create menu items;
            itemHome = new ResideMenuItem(this, R.drawable.ic_home_white_24dp, "Home");
            itemProfile = new ResideMenuItem(this, R.drawable.ic_person_icon, "Profile");
            itemAboutUs = new ResideMenuItem(this, R.drawable.ic_about_us_24dp, "About Us");
            /*itemInvite = new ResideMenuItem(this, R.drawable.ic_share_white, "Invite");*/
            itemLogout = new ResideMenuItem(this, R.drawable.ic_logout_icon, "Logout");
            resideMenu.addMenuItem(itemHome,ResideMenu.DIRECTION_LEFT);
            resideMenu.addMenuItem(itemProfile,ResideMenu.DIRECTION_LEFT);
            resideMenu.addMenuItem(itemAboutUs,ResideMenu.DIRECTION_LEFT);
            /*resideMenu.addMenuItem(itemInvite,ResideMenu.DIRECTION_LEFT);*/
            resideMenu.addMenuItem(itemLogout,ResideMenu.DIRECTION_LEFT);

            itemHome.setOnClickListener(this);
            itemProfile.setOnClickListener(this);
            itemAboutUs.setOnClickListener(this);
            /*itemInvite.setOnClickListener(this);*/
            itemLogout.setOnClickListener(this);

        }

          /*--Right Side Menu--*/

        itemRechargeReport = new ResideMenuItem(this, R.drawable.ic_recharge_report, "Recharge Report");
        itemDMRTransaction = new ResideMenuItem(this, R.drawable.ic_dmt_report, "DMT Report");
        itemDMTDayBook = new ResideMenuItem(this, R.drawable.ic_daybook_dmt, "DMT Day Book");
        itemAEPSRechargeReport = new ResideMenuItem(this, R.drawable.ic_aeps_report, "AEPS Report");
        itemDTHhSubsReport = new ResideMenuItem(this, R.drawable.ic_dth_subscription_report, "Subscription \n Report");
        itemLedgerReport = new ResideMenuItem(this, R.drawable.ic_ledger_report, "Ledger Report");
        itemMoveBankReport = new ResideMenuItem(this, R.drawable.ic_move_to_bank_report, "Move To Bank\n Report");
        itemCommission = new ResideMenuItem(this, R.drawable.ic_commission_slab_report, "Commission\n Slab");
        itemDisputeReport = new ResideMenuItem(this, R.drawable.ic_dispute_report, "Dispute Report");
        itemFundOrderReport = new ResideMenuItem(this, R.drawable.ic_fund_order_report, "Fund Order Report");
        itemFundOrderPending = new ResideMenuItem(this, R.drawable.ic_fund_pending_report, "Fund Order Pending");
        itemDebitCreditReport = new ResideMenuItem(this, R.drawable.ic_fund_debit_credit_report, "Fund Debit Credit");
        itemUserDayBook = new ResideMenuItem(this, R.drawable.ic_user_day_book_report, "User Day Book");
        itemUserW2R = new ResideMenuItem(this, R.drawable.ic_wr_report, "W2R Report");
        itemAccountStatement = new ResideMenuItem(this, R.drawable.ic_account_statement_report, "Account Statement");
        itemFOSList = new ResideMenuItem(this, R.drawable.ic_fos_user_list, "FOS List");



        rightMenu=new ArrayList<>();

        if(userRoleId.equalsIgnoreCase("8")){
            rightMenu.add(itemFOSList);
            rightMenu.add(itemLedgerReport);
        }else{

            rightMenu.add(itemRechargeReport);
            if(apiData!=null){
                if(apiData.isDMTEnable()){
                    rightMenu.add(itemDMRTransaction);
                }
                if(apiData.isAEPSEnable()){
                    rightMenu.add(itemAEPSRechargeReport);
                }
                if(apiData.isDTHSubscriptionEnable()){
                    rightMenu.add(itemDTHhSubsReport);
                }
                if(apiData.isMoveToBankEnable()){
                    rightMenu.add(itemMoveBankReport);
                }
            }

            rightMenu.add(itemLedgerReport);
            rightMenu.add(itemDisputeReport);
            rightMenu.add(itemFundOrderReport);
            rightMenu.add(itemDebitCreditReport);
            rightMenu.add(itemUserDayBook);
            rightMenu.add(itemUserW2R);
            if(loginPrefResponse.isAccountStatement())
            rightMenu.add(itemAccountStatement);
        }


        itemFOSList.setOnClickListener(this);
        itemRechargeReport.setOnClickListener(this);
        itemDMRTransaction.setOnClickListener(this);
        itemAEPSRechargeReport.setOnClickListener(this);
        itemDTHhSubsReport.setOnClickListener(this);
        itemLedgerReport.setOnClickListener(this);
        itemDisputeReport.setOnClickListener(this);
        itemFundOrderReport.setOnClickListener(this);
        itemFundOrderPending.setOnClickListener(this);
        itemDebitCreditReport.setOnClickListener(this);
        itemUserDayBook.setOnClickListener(this);
        itemDMTDayBook.setOnClickListener(this);
        itemCommission.setOnClickListener(this);
        itemUserW2R.setOnClickListener(this);
        itemAccountStatement.setOnClickListener(this);
        itemMoveBankReport.setOnClickListener(this);

        resideMenu.setMenuItems(rightMenu,ResideMenu.DIRECTION_RIGHT);

        findViewById(R.id.title_bar_left_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });
        findViewById(R.id.title_bar_right_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_RIGHT);
            }
        });

    }

    public void hitApi(boolean isRefresh) {
        if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {
            UtilMethods.INSTANCE.GetActiveService(this, new UtilMethods.ApiCallBackTwoMethod() {
                @Override
                public void onSucess(Object object) {
                    if(object instanceof CustomAllTypeService){
                        apiData= (CustomAllTypeService)object;
                        setStoredData();
                        seUpDrawerMenu();
                        if(refreshOpTypeCallBack!=null){
                            refreshOpTypeCallBack.onRefresh(apiData);
                        }
                    }

                }

                @Override
                public void onError(Object object) {

                }
            });
            UtilMethods.INSTANCE.WalletType(this, loader, new UtilMethods.ApiCallBack() {
                @Override
                public void onSucess(Object object) {
                    WalletTypeResponse mWalletTypeResponse = (WalletTypeResponse) object;
                    walletCount = 0;
                    for (int i = 0; i < mWalletTypeResponse.getWalletTypes().size(); i++) {
                        if (mWalletTypeResponse.getWalletTypes().get(i).getActive()) {
                            walletCount++;
                        }
                    }
                    /*if (walletCount > 1) {
                        walletArrowIcon.setVisibility(View.VISIBLE);
                    } else {
                        walletArrowIcon.setVisibility(View.GONE);
                    }*/
                }
            });
            UtilMethods.INSTANCE.GetNotifications(this, new UtilMethods.ApiCallBack() {
                @Override
                public void onSucess(Object object) {
                    notificationCount = ((int) object);
                    setNotificationCount();
                }
            });

            AppUserListResponse companyProfileData = new Gson().fromJson(UtilMethods.INSTANCE.getCompanyProfile(this), AppUserListResponse.class);
            if (companyProfileData == null || companyProfileData != null && companyProfileData.getCompanyProfile() == null) {
                UtilMethods.INSTANCE.GetCompanyProfile(this, null);
            }

            if(isRefresh){
                UtilMethods.INSTANCE.GetCompanyProfile(mContext, null);
                UtilMethods.INSTANCE.NumberList(mContext, null,null);
                UtilMethods.INSTANCE.MyCommission(mContext, null,null);
            }


        } else {
            UtilMethods.INSTANCE.NetworkError(this, getResources().getString(R.string.err_msg_network_title),
                    getResources().getString(R.string.err_msg_network));
        }
    }

    private BroadcastReceiver mNewNotificationReciver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            UtilMethods.INSTANCE.GetNotifications(DashBoardLayout.this, new UtilMethods.ApiCallBack() {
                @Override
                public void onSucess(Object object) {
                    notificationCount = ((int) object);
                    setNotificationCount();
                }
            });
        }
    };


    private void setNotificationCount() {
        if (notificationCount != 0) {
            ViewCount.setVisibility(View.VISIBLE);

            if (notificationCount > 99) {
                ViewCount.setText("99+");

            } else {
                ViewCount.setText(notificationCount + "");

            }
        } else {
            ViewCount.setVisibility(View.GONE);

        }
    }


    public void addVABank() {
        /*if (!resideMenu.getMenuItems(ResideMenu.DIRECTION_RIGHT).contains(itemVABank)) {
            resideMenu.addMenuItem(itemVABank, ResideMenu.DIRECTION_RIGHT);
        }*/
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return resideMenu.dispatchTouchEvent(ev);
    }

    @Override
    public void onClick(View view) {

        if(view==itemMoveBankReport){
            Intent intent = new Intent(this, MoveToWalletReportActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);

        }
        else if(view==itemAccountStatement){
            Intent intent = new Intent(this, AccountStatementReportActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
        else if (view == itemHome) {
            changeFragment(new HomeFragment());
            tvTitle.setText("Home");
        }
        else if (view == itemProfile) {
            changeFragment(new ProfileFragment());
            tvTitle.setText("My Profile");
        }  else if (view == itemAboutUs) {

            Bundle bundle = new Bundle();
            bundle.putString("from", "about");

            WebViewFragment frag = new WebViewFragment();
            frag.setArguments(bundle);

            changeFragment(new WebViewFragment());
            tvTitle.setText("About us");
        } else if (view == itemInvite) {
            Intent i = new Intent(DashBoardLayout.this, ShareActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
            /*shareIt();*/
        }
        else if (view == itemLogout) {
            new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Are you sure?")
                    .setCancelText("No,cancel!")
                    .setConfirmText("Yes,logout!")
                    .showCancelButton(true)
                    .setConfirmButtonTextColor(getResources().getColor(R.color.white))
                    .setNeutralButtonTextColor(getResources().getColor(R.color.white))
                    .setCancelButtonTextColor(getResources().getColor(R.color.white))
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(final SweetAlertDialog sDialog) {
                            sDialog.setTitleText("Cancelled!")
                                    .setConfirmText("OK")
                                    .showCancelButton(false)
                                    .setCancelClickListener(null)
                                    .setConfirmClickListener(null)
                                    .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                        }
                    })
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(final SweetAlertDialog sDialog) {
                            sDialog.setTitleText("Logout Successfully!")
                                    .setConfirmText("OK")
                                    .showCancelButton(false)
                                    .setCancelClickListener(null)
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            UtilMethods.INSTANCE.logout(mContext);
                                            UtilMethods.INSTANCE.setSenderNumber(mContext, "", "", "", "");
                                            UtilMethods.INSTANCE.setSenderInfo(mContext, "", "", false, null);
                                            UtilMethods.INSTANCE.setBeneficiaryList(mContext, "");
                                            sDialog.dismiss();
                                        }
                                    })
                                    .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                        }
                    })
                    .show();
        }
        else if (view == itemRechargeReport) {
            Intent intent = new Intent(this, RechargeHistory.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        } else if (view == itemAEPSRechargeReport) {
            Intent intent = new Intent(this, AEPSReportActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
        else if (view == virtualIv) {
            Intent i = new Intent(this, QRCodePaymentActivity.class);
            i.putExtra("isQRMappedToUser",isQRMappedToUser);
            i.putExtra("isBulkQRGeneration",isBulkQRGeneration);
            i.putExtra("isECollectEnable",true);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        } else if (view == itemLedgerReport) {
            Intent intent = new Intent(this, LedgerReport.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        } else if (view == itemDisputeReport) {
            Intent n = new Intent(this, DisputeReport.class);
            n.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(n);
        } else if (view == itemFundOrderReport) {
            Intent i = new Intent(this, FundReqReport.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        } else if (view == itemDMRTransaction) {

            Intent i = new Intent(this, DMRReport.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        } else if (view == itemUserDayBook) {
            Intent i = new Intent(this, UserDayBookActivity.class);
            i.putExtra("Type", "UserDayBook");
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        } else if (view == itemDMTDayBook) {
            Intent i = new Intent(this, UserDayBookActivity.class);
            i.putExtra("Type", "UserDayBookDMT");
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        } else if (view == itemDebitCreditReport) {
            Intent i = new Intent(this, FundRecReport.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        } else if (view == itemFundOrderPending) {
            Intent i = new Intent(this, FundOrderPendingActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        } else if (view == itemCommission) {
            Intent n = new Intent(this, CommissionScreen.class);
            n.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(n);
        } else if (view == itemFOSList) {
            Intent i=new Intent(this, UserList.class);
            i.putExtra("page","2");
            startActivity(i);
        }
        else if (view == LayoutNotification) {
            NotificationFragment mNotificationFragment = new NotificationFragment();
            mNotificationFragment.show(getSupportFragmentManager(), "Dialog");
            mNotificationFragment.setCallBack(new NotificationFragment.SetReadNotification() {
                @Override
                public void setReadCount(int readCount) {
                    notificationCount = readCount;
                    setNotificationCount();
                }
            });


        }
        else if(view==itemUserW2R){
            Intent n = new Intent(this, W2RHistory.class);
            n.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(n);
        }else if(view==itemDTHhSubsReport){
            Intent i = new Intent(this, DthSubscriptionReportActivity.class);
            i.putExtra("fromId", 0);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
             startActivity(i);
        }
        resideMenu.closeMenu();
    }

    private void shareIt() {
        if (Build.VERSION.SDK_INT >= 24) {
            try {
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            // AppUserListResponse companyData = new Gson().fromJson(UtilMethods.INSTANCE.getCompanyProfile(this), AppUserListResponse.class);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
                String shareMessage = /*companyData.getCompanyProfile().getAddress() + "\n\n*/"Let me recommend you this application\n\n";
                shareMessage = shareMessage + ApplicationConstant.INSTANCE.inviteUrl + loginPrefResponse.getData().getUserID() + "\n\n";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "choose one"));

            } else {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
                String shareMessage = /*companyData.getCompanyProfile().getAddress() + "\n\n*/"Let me recommend you this application\n\n";
                shareMessage = shareMessage + ApplicationConstant.INSTANCE.inviteUrl + loginPrefResponse.getData().getUserID() + "\n\n";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "choose one"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
        @Override
        public void openMenu() {
            // Toast.makeText(mContext, "Menu is opened!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void closeMenu() {
            // Toast.makeText(mContext, "Menu is closed!", Toast.LENGTH_SHORT).show();
        }
    };

    private void changeFragment(Fragment targetFragment) {
        resideMenu.clearIgnoredViewList();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    // What good method is to access resideMenu？
    public ResideMenu getResideMenu() {
        return resideMenu;
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INTENT_UPGRADE_PACKAGE && resultCode == RESULT_OK) {
            if (mUpgradePackageCallBack != null) {
                mUpgradePackageCallBack.onUpgrade();
            }
        } else{
            Fragment myFragment = getSupportFragmentManager().findFragmentByTag("Home");
            if (myFragment != null && myFragment.isVisible()) {
                HomeFragment fragment = (HomeFragment) myFragment;
                if (fragment.mGetLocation != null) {
                    fragment.mGetLocation.onActivityResult(requestCode, resultCode, data);

                }
            }else{
                List<Fragment> fragments = getSupportFragmentManager().getFragments();
                for (Fragment f : fragments) {
                    if (f instanceof ProfileFragment) {
                        f.onActivityResult(requestCode, resultCode, data);
                    }
                }
            }

        }
    }

    public void SetBalance() {
        try {
            if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null) {
                double prepaidWallet = balanceCheckResponse.getBalanceData().getBalance();
                isBulkQRGeneration=balanceCheckResponse.isBulkQRGeneration();
                isQRMappedToUser=balanceCheckResponse.getBalanceData().isQRMappedToUser();
                tvBalance.setText(getString(R.string.rupiya) + " " + prepaidWallet);
                isOpImgDrawn=balanceCheckResponse.isDrawOpImage();
            }
        } catch (Exception e) {

        }

    }


    private void showWalletListPopupWindow() {
        ArrayList<BalanceType> mBalanceTypes = new ArrayList<>();
        if (balanceCheckResponse != null) {
            if (balanceCheckResponse.getBalanceData().isBalance()) {
                mBalanceTypes.add(new BalanceType(balanceCheckResponse.getBalanceData().getPrepaidWalletName(), balanceCheckResponse.getBalanceData().getBalance() + ""));
            }
            if (balanceCheckResponse.getBalanceData().isUBalance()) {
                mBalanceTypes.add(new BalanceType(balanceCheckResponse.getBalanceData().getUtilityWalletName(), balanceCheckResponse.getBalanceData().getuBalance() + ""));
            }
            if (balanceCheckResponse.getBalanceData().isBBalance()) {
                mBalanceTypes.add(new BalanceType(balanceCheckResponse.getBalanceData().getBankWalletName(), balanceCheckResponse.getBalanceData().getbBalance() + ""));
            }
            if (balanceCheckResponse.getBalanceData().isCBalance()) {
                mBalanceTypes.add(new BalanceType(balanceCheckResponse.getBalanceData().getCardWalletName(), balanceCheckResponse.getBalanceData().getcBalance() + ""));
            }
            if (balanceCheckResponse.getBalanceData().isIDBalance()) {
                mBalanceTypes.add(new BalanceType(balanceCheckResponse.getBalanceData().getRegIDWalletName(), balanceCheckResponse.getBalanceData().getIdBalnace() + ""));
            }
            if (balanceCheckResponse.getBalanceData().isAEPSBalance()) {
                mBalanceTypes.add(new BalanceType("Aeps Wallet", balanceCheckResponse.getBalanceData().getAepsBalnace() + ""));
            }
            if (balanceCheckResponse.getBalanceData().isPacakgeBalance()) {
                mBalanceTypes.add(new BalanceType(balanceCheckResponse.getBalanceData().getPackageWalletName(), balanceCheckResponse.getBalanceData().getPacakgeBalance()+""));
            }
            if(loginPrefResponse.isAccountStatement()){
                mBalanceTypes.add(new BalanceType("Outstanding Wallet", balanceCheckResponse.getBalanceData().getOsBalance()+""));
            }
        } else {
            SharedPreferences myPreferences = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
            String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.balancePref, "");
            balanceCheckResponse = new Gson().fromJson(balanceResponse, BalanceResponse.class);
            showWalletListPopupWindow();
            return;
        }
        WalletTypeDialog(mBalanceTypes);
    }

    private void WalletTypeDialog(final ArrayList<BalanceType> mBalanceTypes) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_wallets_balance, null);

        final RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new WalletBalanceAdapter(this, mBalanceTypes, R.layout.adapter_wallet_balance));
        final TextView okButton = view.findViewById(R.id.okBtn);
        final TextView moveWalletBtn = view.findViewById(R.id.moveWalletBtn);

        if (apiData.isMoveToBankEnable())
            moveWalletBtn.setVisibility(View.VISIBLE);
        else
            moveWalletBtn.setVisibility(View.GONE);

        final Dialog dialog = new Dialog(this, R.style.alert_dialog_light);

        dialog.setCancelable(false);
        dialog.setContentView(view);
        moveWalletBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                startActivity(new Intent(DashBoardLayout.this, MoveToWalletActivity.class)
                .putExtra("items", "" + new Gson().toJson(mBalanceTypes, new TypeToken<ArrayList<BalanceType>>() {
                }.getType()))
                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
            }
        });
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onDestroy() {

        LocalBroadcastManager.getInstance(this).unregisterReceiver(mNewNotificationReciver);
        super.onDestroy();


    }

    @Override
    protected void onResume() {

        super.onResume();
        UtilMethods.INSTANCE.BalancecheckNew(this,null, customAlertDialog, mChangePassUtils, new UtilMethods.ApiCallBack() {
            @Override
            public void onSucess(Object object) {
                if(object instanceof BalanceResponse)
                {
                    balanceCheckResponse=(BalanceResponse)object;

                    SetBalance();
                }

            }
        });

    }







    public void displayView(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                GetBackFragment.Addpos(position);
                //  fragment = new ChangePassword();
                tvTitle.setText("Change Password");
                break;

            default:
                break;
        }

        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_fragment, fragment, "fragment").addToBackStack(fragment.toString())
                    .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();

            // ====to clear unused memory==
            System.gc();
        }
    }

    @Override
    public void onBackPressed() {
        try {
            int ast = GetBackFragment.Lastpos();

            if (ast == -1) {
                if (exit_check == 0) {
                    Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
                    exit_check = 1;
                } else {
                    finish();
                    finishAffinity();
                }

            } else {
                if (ast == 1) {
                    long g = GetBackFragment.LastUUID();
                    if (g != 0) {

                    }
                }
                displayView(ast);
                GetBackFragment.Removepos();
                GetBackFragment.Removepos();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

