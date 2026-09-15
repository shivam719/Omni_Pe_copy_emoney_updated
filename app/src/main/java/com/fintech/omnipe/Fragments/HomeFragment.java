package com.fintech.omnipe.Fragments;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowMetrics;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;


import com.fintech.omnipe.DMTNew.ui.DMTLoginNew;
import com.google.gson.Gson;
import com.paysprint.onboardinglib.activities.HostActivity;
import com.roundpay.emoneylib.EMoneyLoginActivity;
import com.roundpay.emoneylib.Object.MiniStatements;
import com.roundpay.emoneylib.Utils.KeyConstant;
import com.roundpay.shoppinglib.Shopping.Activity.ShoppingDashboardActivity;
import com.roundpay.shoppinglib.Util.KeyContants;
import com.fintech.omnipe.AEPS.FingPay.UI.AEPSDashboardActivity;
import com.fintech.omnipe.AccountOpen.adapter.AccountOpListAdapter;
import com.fintech.omnipe.AccountOpen.dto.AccountOpData;
import com.fintech.omnipe.AccountOpen.dto.AccountOpenListResponse;
import com.fintech.omnipe.AccountOpen.ui.AccountOpenActivity;
import com.fintech.omnipe.Activities.AEPSReportActivity;
import com.fintech.omnipe.Activities.AchievedTargetActivity;
import com.fintech.omnipe.Activities.Adapter.HomeOptionListAdapter;
import com.fintech.omnipe.Activities.AddMoneyActivity;
import com.fintech.omnipe.Activities.AepsCashWithDrawlAtivity;
import com.fintech.omnipe.Activities.CreateUserActivity;
import com.fintech.omnipe.Activities.DMRReport;
import com.fintech.omnipe.Activities.DisputeReport;
import com.fintech.omnipe.Activities.FundOrderPendingActivity;
import com.fintech.omnipe.Activities.FundRecReport;
import com.fintech.omnipe.Activities.FundReqReport;
import com.fintech.omnipe.Activities.LedgerReport;
import com.fintech.omnipe.Activities.PaymentRequest;
import com.fintech.omnipe.Activities.RechargeActivity;
import com.fintech.omnipe.Activities.RechargeHistory;
import com.fintech.omnipe.Activities.RechargeProviderActivity;
import com.fintech.omnipe.Activities.SupportActivity;
import com.fintech.omnipe.Activities.UpgradePackageActivity;
import com.fintech.omnipe.Activities.UserDayBookActivity;
import com.fintech.omnipe.Activities.W2RHistory;
import com.fintech.omnipe.Api.Object.AssignedOpType;
import com.fintech.omnipe.Api.Object.BcResponse;
import com.fintech.omnipe.Api.Object.TargetAchieved;
import com.fintech.omnipe.Api.Object.UserDaybookReport;
import com.fintech.omnipe.Api.Request.BasicRequest;
import com.fintech.omnipe.Api.Response.AppBannerResponse;
import com.fintech.omnipe.Api.Response.AppUserListResponse;
import com.fintech.omnipe.Api.Response.BalanceResponse;
import com.fintech.omnipe.Api.Response.NumberListResponse;
import com.fintech.omnipe.Api.Response.OnboardingResponse;
import com.fintech.omnipe.AppUser.Activity.AccountStatementReportActivity;
import com.fintech.omnipe.AppUser.Activity.AppUserListActivity;
import com.fintech.omnipe.AppUser.Activity.ChannelReportActivity;
import com.fintech.omnipe.AppUser.Activity.FosReportActivity;
import com.fintech.omnipe.AppUser.Activity.FosUserListActivity;
import com.fintech.omnipe.AppUser.dto.AppGetAMResponse;
import com.fintech.omnipe.Auth.dto.LoginResponse;
import com.fintech.omnipe.BuildConfig;
import com.fintech.omnipe.CallBackReport.AEPSBalanceActivity;
import com.fintech.omnipe.CallBackReport.MiniStatementActivity;
import com.fintech.omnipe.CallBackReport.SlipActivityMicroAtmReport;
import com.fintech.omnipe.CommissionSlab.ui.CommissionScreen;
import com.fintech.omnipe.DTHSubscription.Activity.DthSubscriptionReportActivity;
import com.fintech.omnipe.DashBoard.CustomAllTypeService;
import com.fintech.omnipe.DashBoard.Interface.RefreshOpTypeCallBack;
import com.fintech.omnipe.DashBoard.UI.DashBoardLayout;
import com.fintech.omnipe.Fragments.Adapter.CustomPagerAdapter;
import com.fintech.omnipe.Fragments.dto.OperatorList;
import com.fintech.omnipe.GetAdvertisement.activity.GetAdvertisementByCity;
import com.fintech.omnipe.MicroATM.PAYSPRINT.PaySprintMATMActivity;
import com.fintech.omnipe.MicroATM.UI.MicroAtmActivity;
import com.fintech.omnipe.MicroATM.dto.InitiateMiniATMReq;
import com.fintech.omnipe.MicroATM.dto.InitiateMiniBankATMRes;
import com.fintech.omnipe.MicroATM.dto.UpdateMiniBankStatusReq;
import com.fintech.omnipe.MoveToWallet.UI.MoveToWalletReportActivity;
import com.fintech.omnipe.R;
import com.fintech.omnipe.UPIPayment.Activity.QRScannerActivity;
import com.fintech.omnipe.UPIPayment.Activity.UPISenderLoginActivity;
import com.fintech.omnipe.Util.ApiClient;
import com.fintech.omnipe.Util.ApplicationConstant;
import com.fintech.omnipe.Util.CustomAlertDialog;
import com.fintech.omnipe.Util.EndPointInterface;
import com.fintech.omnipe.Util.GetLocation;
import com.fintech.omnipe.Util.UpgradePackageCallBack;
import com.fintech.omnipe.Util.UtilMethods;
import com.fintech.omnipe.usefull.CustomLoader;

import org.egram.aepslib.DashboardActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;

public class HomeFragment extends Fragment implements UpgradePackageCallBack, RefreshOpTypeCallBack, HomeOptionListAdapter.ClickView {

    public static TextView[] mDotsText;
    private CustomLoader loader;
    private int opID;
    private ArrayList<OperatorList> accountOpList;
    private SharedPreferences myPrefs;
    private CustomPagerAdapter mCustomPagerAdapter;
    private boolean isScreenOpen;
    private HomeOptionListAdapter mDashboardOptionListAdapter;
    private RecyclerView rechargeRecyclerView, fundBankingRecyclerView;
    private WebView newsWeb;
    private ViewPager mViewPager;
    private View pagerContainer, bankingFundView, pendingView, failedView;

    private Handler handler;
    private Integer mDotsCount;
    private LinearLayout dotsCount;
    private ImageView ivCustomerCare;
    private CustomAlertDialog customAlertDialog;
    private LoginResponse loginPrefResponse;
    private BalanceResponse mBalanceResponse;
    private AppGetAMResponse mAppGetAMResponse;
    private String userRoleId;
    private String fromDate, toDate, currentDate;
    private Runnable mRunnable;
    private boolean isLoaderShouldShow;
    private View llCustomerCare;
    private TextView tvsupportEmail, tvSupportNo, tvWhatsappNo, titleTv;
    private AppCompatTextView remainTargetTv, pendingAmountTv, failedAmountTv, salesTargetTv, todaySalesTv, commissionAmountTv, successAmountTv;
    private View custEmailView, custPhoneView, custWhatsappView, quickLinksView, targetView, packageUpgradeView, callBackReqView;
    private ArrayList<MiniStatements> miniStatements;
    private SdkDetail sdkDetail;
    private String aPIStatus;
    private boolean isOpImgDrawn;
    private int sdkType;
    private SdkDetail mSdkDetail;
    private final Integer REQUEST_CODE_RDP_MINI_ATM = 123;
    private final Integer REQUEST_CODE_ROUNDPAY_AEPS = 124;
    private final Integer REQUEST_CODE_MAHAGRAM_MINI_ATM = 125;
    private final int INTENT_CODE_MAHAGRAM_APES = 126;
    private final int INTENT_UPGRADE_PACKAGE = 127;
    private final Integer INTENT_MINI_ATM_PAYS_PRINT = 1001;
    private final Integer INTENT_AEPS_PAYS_PRINT = 1002;
    public GetLocation mGetLocation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for getActivity() fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        UtilMethods.INSTANCE.setDashboardStatus(getActivity(), true);
        customAlertDialog = new CustomAlertDialog(getActivity(), true);
        myPrefs = getActivity().getSharedPreferences(ApplicationConstant.INSTANCE.prefNameLoginPref, MODE_PRIVATE);
        loginPrefResponse = new Gson().fromJson(myPrefs.getString(ApplicationConstant.INSTANCE.LoginPref, ""), LoginResponse.class);
        userRoleId = loginPrefResponse.getData().getRoleID();
        SimpleDateFormat sdfToday = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        currentDate = sdfToday.format(new Date());
        fromDate = sdfToday.format(new Date());
        toDate = fromDate;

        getID(v);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!isScreenOpen) {
            isScreenOpen = true;
            mGetLocation = new GetLocation(getActivity(), loader);
            if (UtilMethods.getLattitude == 0 || UtilMethods.getLongitude == 0) {
                mGetLocation.startLocationUpdatesIfSettingEnable((lattitude, longitude) -> {
                    UtilMethods.getLattitude = lattitude;
                    UtilMethods.getLongitude = longitude;
                });
            }
            mBalanceResponse = new Gson().fromJson(myPrefs.getString(ApplicationConstant.INSTANCE.balancePref, ""), BalanceResponse.class);
            try {
                isOpImgDrawn = ((DashBoardLayout) getActivity()).isOpImgDrawn;
            } catch (NullPointerException npe) {

            }
            if (userRoleId.equalsIgnoreCase("3") || userRoleId.equalsIgnoreCase("2")) {
                failedView.setVisibility(View.VISIBLE);
                pendingView.setVisibility(View.VISIBLE);
                packageUpgradeView.setVisibility(View.VISIBLE);
                callBackReqView.setVisibility(View.VISIBLE);
                quickLinksView.setVisibility(View.VISIBLE);
            } else if (userRoleId.equalsIgnoreCase("8")) {
                quickLinksView.setVisibility(View.GONE);
            } else {
                failedView.setVisibility(View.GONE);
                pendingView.setVisibility(View.GONE);
                packageUpgradeView.setVisibility(View.GONE);
                callBackReqView.setVisibility(View.GONE);
                quickLinksView.setVisibility(View.VISIBLE);
            }

            if (!UtilMethods.INSTANCE.getIsNumberListFetch(getActivity())) {
                UtilMethods.INSTANCE.NumberList(getActivity(), null, null);
            }

            setDashboardData(new Gson().fromJson(UtilMethods.INSTANCE.getActiveService(getActivity()), CustomAllTypeService.class));
            if (targetView.getVisibility() == View.VISIBLE) {
                achieveTargetParse(new Gson().fromJson(UtilMethods.INSTANCE.getTotalTargetData(getActivity()), AppUserListResponse.class));
            }
            DashboardApi();
        }
    }

    private void getID(View v) {
        loader = new CustomLoader(getActivity(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        tvsupportEmail = v.findViewById(R.id.tv_fos_no);
        tvSupportNo = v.findViewById(R.id.tv_support_no);
        tvWhatsappNo = v.findViewById(R.id.tv_whatsapp_no);
        custEmailView = v.findViewById(R.id.custEmailView);
        custPhoneView = v.findViewById(R.id.custPhoneView);
        custWhatsappView = v.findViewById(R.id.custWhatsappView);

        quickLinksView = v.findViewById(R.id.quickLinksView);
        targetView = v.findViewById(R.id.targetContainerView);
        callBackReqView = v.findViewById(R.id.callBackReqView);
        packageUpgradeView = v.findViewById(R.id.packageUpgradeView);

        successAmountTv = v.findViewById(R.id.successAmountTv);
        commissionAmountTv = v.findViewById(R.id.commissionAmountTv);
        failedAmountTv = v.findViewById(R.id.failedAmountTv);
        pendingAmountTv = v.findViewById(R.id.pendingAmountTv);
        remainTargetTv = v.findViewById(R.id.remainTargetTv);
        salesTargetTv = v.findViewById(R.id.salesTargetTv);
        todaySalesTv = v.findViewById(R.id.todaySalesTv);

        ivCustomerCare = v.findViewById(R.id.iv_Customer_Care);
        llCustomerCare = v.findViewById(R.id.ll_customer_care);
        titleTv = v.findViewById(R.id.titleTv);
        newsWeb = v.findViewById(R.id.News);
        newsWeb.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        rechargeRecyclerView = v.findViewById(R.id.rechargeRecyclerView);
        fundBankingRecyclerView = v.findViewById(R.id.fundBankingRecyclerView);
        bankingFundView = v.findViewById(R.id.bankingFundView);
        rechargeRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        fundBankingRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        mViewPager = v.findViewById(R.id.pager);
        pagerContainer = v.findViewById(R.id.pagerContainer);
        dotsCount = v.findViewById(R.id.image_count);

        failedView = v.findViewById(R.id.failedContainerView);
        pendingView = v.findViewById(R.id.pendingContainerView);

        v.findViewById(R.id.targetIv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AchievedTargetActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));

            }
        });

        v.findViewById(R.id.commissionSlabView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), CommissionScreen.class);
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                getActivity().startActivity(i);
            }
        });

        v.findViewById(R.id.callBackReqView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack();
            }
        });

        v.findViewById(R.id.packageUpgradeView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), UpgradePackageActivity.class)
                        .putExtra("UID", loginPrefResponse.getData().getUserID())
                        .putExtra("BENE_NAME", loginPrefResponse.getData().getName() + " (" + loginPrefResponse.getData().getRoleName() + ")")
                        .putExtra("BENE_MOBILE", loginPrefResponse.getData().getMobileNo())
                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), INTENT_UPGRADE_PACKAGE);
            }
        });

        v.findViewById(R.id.moreServiceView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SupportActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
            }
        });

        ivCustomerCare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (llCustomerCare.getVisibility() == View.GONE) {
                    llCustomerCare.setVisibility(View.VISIBLE);
                    llCustomerCare.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in
                    ));

                } else {
                    llCustomerCare.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out));
                    llCustomerCare.setVisibility(View.GONE);

                }
            }
        });

        AppUserListResponse companyProfileData = new Gson().fromJson(UtilMethods.INSTANCE.getCompanyProfile(getActivity()), AppUserListResponse.class);
        if (companyProfileData != null && companyProfileData.getCompanyProfile() != null) {
            setSupportData(companyProfileData);
        } else {
            UtilMethods.INSTANCE.GetCompanyProfile(getActivity(), new UtilMethods.ApiCallBack() {
                @Override
                public void onSucess(Object object) {
                    AppUserListResponse companyProfileData = (AppUserListResponse) object;
                    setSupportData(companyProfileData);
                }
            });
        }
    }

    private void setSupportData(AppUserListResponse companyProfileData) {
        if (!companyProfileData.getCompanyProfile().getCustomerCareEmailIds().isEmpty()) {
            tvsupportEmail.setText(companyProfileData.getCompanyProfile().getCustomerCareEmailIds());
            custEmailView.setVisibility(View.VISIBLE);
        } else {
            custEmailView.setVisibility(View.GONE);
        }

        if (!companyProfileData.getCompanyProfile().getCustomerCareMobileNos().isEmpty()) {
            tvSupportNo.setText(companyProfileData.getCompanyProfile().getCustomerCareMobileNos());
            custPhoneView.setVisibility(View.VISIBLE);
        } else {
            custPhoneView.setVisibility(View.GONE);
        }
        if (!companyProfileData.getCompanyProfile().getCustomerPhoneNos().isEmpty()) {
            if (tvSupportNo.getText().toString().length() > 0) {
                tvSupportNo.setText(tvSupportNo.getText() + "\n" + companyProfileData.getCompanyProfile().getCustomerPhoneNos());
            } else {
                tvSupportNo.setText(companyProfileData.getCompanyProfile().getCustomerPhoneNos());
            }

            custPhoneView.setVisibility(View.VISIBLE);
        } else {
            if (tvSupportNo.getText().toString().length() == 0) {
                custPhoneView.setVisibility(View.GONE);
            }
        }
        if (!companyProfileData.getCompanyProfile().getCustomerWhatsAppNos().isEmpty()) {
            tvWhatsappNo.setText(companyProfileData.getCompanyProfile().getCustomerWhatsAppNos());
            custWhatsappView.setVisibility(View.VISIBLE);
        } else {
            custWhatsappView.setVisibility(View.GONE);
        }

    }

    private void setDashboardData(CustomAllTypeService mActiveServiceData) {

        if (mActiveServiceData != null) {
            if (userRoleId.equalsIgnoreCase("3") || userRoleId.equalsIgnoreCase("2")) {
                setOptionListData(mActiveServiceData.getDashBoardRTBillRechargeList(), rechargeRecyclerView, 1, R.layout.adapter_dashboard_option);
                bankingFundView.setVisibility(View.VISIBLE);
                titleTv.setText(getString(R.string.recharge_paybills));
                setOptionListData(mActiveServiceData.getDashBoardRTFundBankingList(), fundBankingRecyclerView, 2, R.layout.adapter_dashboard_option_grid);

            } else if (userRoleId.equalsIgnoreCase("8")) {
                titleTv.setText("Services");
                setOptionListData(mActiveServiceData.getDashBoardFosList(), rechargeRecyclerView, 3, R.layout.adapter_dashboard_option_fos);
                bankingFundView.setVisibility(View.GONE);
            } else {
                titleTv.setText("Reports & Services");
                setOptionListData(mActiveServiceData.getDashBoardOtherList(), rechargeRecyclerView, 1, R.layout.adapter_dashboard_option);
                bankingFundView.setVisibility(View.VISIBLE);
                setOptionListData(mActiveServiceData.getDashBoardOtherFundBankingList(), fundBankingRecyclerView, 2, R.layout.adapter_dashboard_option_grid);
            }

        } else {
            UtilMethods.INSTANCE.GetActiveService(getActivity(), new UtilMethods.ApiCallBackTwoMethod() {
                @Override
                public void onSucess(Object object) {
                    setDashboardData((CustomAllTypeService) object);
                }

                @Override
                public void onError(Object object) {

                }
            });
        }
    }

    private void setOptionListData(ArrayList<AssignedOpType> opTypeArrayList, RecyclerView recyclerView, int type, int layout) {
        mDashboardOptionListAdapter = new HomeOptionListAdapter(opTypeArrayList, getActivity(), layout, this);
        recyclerView.setAdapter(mDashboardOptionListAdapter);
        recyclerView.setNestedScrollingEnabled(false);

    }

    private void callBack() {
        if (customAlertDialog != null) {
            customAlertDialog.sendReportDialog(3, loginPrefResponse.getData().getMobileNo(), new CustomAlertDialog.DialogSingleCallBack() {
                @Override
                public void onPositiveClick(String mobile, String emailId) {
                    UtilMethods.INSTANCE.GetCallMeUserReq(getActivity(), mobile, loader);
                }
            });
        }
    }

    private void openNewScreen(AssignedOpType mAssignedOpType) {
        int id = mAssignedOpType.getServiceID();
        int parentId = mAssignedOpType.getParentID();
        String name = mAssignedOpType.getName().toUpperCase();
        if (id == 1) {
            if (UtilMethods.INSTANCE.CheckActiveOperator(getActivity(), id)) {
                Intent clickIntent = new Intent(getActivity(), RechargeActivity.class);
                clickIntent.putExtra("fromId", id);
                clickIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(clickIntent);
            } else {
                UtilMethods.INSTANCE.Error(getActivity(), getResources().getString(R.string.NotAuthorized));
            }
        } else if (parentId == 1 || parentId == 11) {
            if (UtilMethods.INSTANCE.CheckActiveOperator(getActivity(), id)) {

                Intent i = new Intent(getActivity(), RechargeProviderActivity.class);
                i.putExtra("from", name);
                i.putExtra("fromId", id);
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);

            } else {
                UtilMethods.INSTANCE.Error(getActivity(), getResources().getString(R.string.NotAuthorized));
            }
        } else if (id == 20) {
            customAlertDialog.showToUserMessage("Dear " + loginPrefResponse.getData().getName(), "You can use this service only on web portal.");
        } else if (id == 14) {
            UtilMethods.INSTANCE.CallOnboarding(getActivity(), false, id, "", "0", "", 0, false, getActivity().getSupportFragmentManager(), null, null, null, UtilMethods.getLattitude, UtilMethods.getLongitude, loader, new UtilMethods.ApiCallBackOnBoardingMethod() {
                @Override
                public void onSuccess(Object object) {
                    // if(((DashBoardLayout)getActivity()).isDMTWithPipe){
                    ArrayList<OperatorList> operatorsList = UtilMethods.INSTANCE.getDMTOperatorList(getActivity());
                    if (operatorsList != null && !operatorsList.isEmpty()) {
                        if (operatorsList.size() > 1) {
                            customAlertDialog.dmtListDialog("Select DMT", operatorsList, mOperatorList -> {
                                Intent i = new Intent(getActivity(), DMTLoginNew.class);
                                i.putExtra("OpType", mOperatorList.getOpType());
                                i.putExtra("OID", mOperatorList.getOid());
                                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(i);
                            });
                        } else {
                            Intent i = new Intent(getActivity(), DMTLoginNew.class);
                            i.putExtra("OpType", operatorsList.get(0).getOpType());
                            i.putExtra("OID", operatorsList.get(0).getOid());
                            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(i);
                        }
                    } else {
                        Intent i = new Intent(getActivity(), DMTLoginNew.class);
                        i.putExtra("OpType", 0);
                        i.putExtra("OID", 0);
                        i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(i);
                    }


                }

                @Override
                public void onError(Object object) {

                }

                @Override
                public void onOnBoarding(Object object) {

                }
            });
        } else if (id == 19) { //SHopping

        } else if (id == 22) { //AEPS

            if (UtilMethods.INSTANCE.CheckActiveOperator(getActivity(), id)) {

                hitCallOnboarding(id, false);

            } else {
                UtilMethods.INSTANCE.Error(getActivity(), getResources().getString(R.string.NotAuthorized));
            }

        } else if (id == 24) {

            if (UtilMethods.INSTANCE.isNetworkAvialable(getActivity())) {
                hitCallOnboarding(id, true);
            } else {
                UtilMethods.INSTANCE.NetworkError(getActivity(), "Network Error!", getActivity().getResources().getString(R.string.network_error));
            }

        } else if (id == 28) {

            if (UtilMethods.INSTANCE.CheckActiveOperator(getActivity(), id)) {
                hitCallOnboarding(id, false);
            } else {
                UtilMethods.INSTANCE.Error(getActivity(), getResources().getString(R.string.NotAuthorized));
            }
        } else if (id == 29) {
            if (UtilMethods.INSTANCE.CheckActiveOperator(getActivity(), id)) {
                hitCallOnboarding(id, false);
            } else {
                UtilMethods.INSTANCE.Error(getActivity(), getResources().getString(R.string.NotAuthorized));
            }
        } else if (parentId == 30) {
            if (UtilMethods.INSTANCE.CheckActiveOperator(getActivity(), id)) {
                Intent i = new Intent(getActivity(), RechargeProviderActivity.class/*RechargeProviderActivity.class*/);
                i.putExtra("from", name);
                i.putExtra("fromId", id);
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
            } else {
                UtilMethods.INSTANCE.Error(getActivity(), getResources().getString(R.string.NotAuthorized));
            }
        } else if (id == 37) {
            Intent i = new Intent(getActivity(), AddMoneyActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        } else if (id == 44) {

            if (UtilMethods.INSTANCE.CheckActiveOperator(getActivity(), id)) {

                hitCallOnboarding(id, false);

            } else {
                UtilMethods.INSTANCE.Error(getActivity(), getResources().getString(R.string.NotAuthorized));
            }

        } else if (id == 45) {
            startActivity(new Intent(getActivity(), ShoppingDashboardActivity.class)
                    .putExtra(KeyContants.APP_ID, ApplicationConstant.INSTANCE.APP_ID)
                    .putExtra(KeyContants.IMEI, UtilMethods.INSTANCE.getIMEI(getActivity()))
                    .putExtra(KeyContants.LOGIN_TYPE_ID, Integer.parseInt(loginPrefResponse.getData().getLoginTypeID()))//Integer value
                    .putExtra(KeyContants.WID, loginPrefResponse.getData().getWid())
                    .putExtra(KeyContants.APPLICATION_PACKAGE, BuildConfig.APPLICATION_ID)
                    .putExtra(KeyContants.REG_KEY, UtilMethods.INSTANCE.getFCMRegKey(getActivity()))
                    .putExtra(KeyContants.LOGO, R.drawable.rnd_logo)
                    .putExtra(KeyContants.SERIAL_NO, UtilMethods.INSTANCE.getSerialNo(getActivity()))
                    .putExtra(KeyContants.SESSION, loginPrefResponse.getData().getSession())
                    .putExtra(KeyContants.SESSION_ID, loginPrefResponse.getData().getSessionID())
                    .putExtra(KeyContants.USER_ID, loginPrefResponse.getData().getUserID())
                    .putExtra(KeyContants.MOBILE_NUM, loginPrefResponse.getData().getMobileNo())
                    .putExtra(KeyContants.VERSION_NAME, BuildConfig.VERSION_NAME)
                    .putExtra(KeyContants.BASE_URL, ApplicationConstant.INSTANCE.baseShoppingUrl));
        } else if (id == 62) {
            startActivity(new Intent(getActivity(), UPISenderLoginActivity.class)
                    .putExtra("BalanceData", mBalanceResponse)
                    .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));

        } else if (id == 63) {
            startActivity(new Intent(getActivity(), QRScannerActivity.class)
                    .putExtra("upiPay", true)
                    .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            );
        } else if (parentId == 47) {
            if (UtilMethods.INSTANCE.CheckActiveOperator(getActivity(), id)) {
                if (isOpImgDrawn) {
                    bindAccountListOp(id);
                } else {
                    startActivity(new Intent(getActivity(), AccountOpenActivity.class)
                            /*.putExtra("AccountList", mAccountOpenListResponse.getAccountOpeningDeatils())*/
                            .putExtra("SERVICE_ID", id)
                            .putExtra("SERVICE_NAME", mAssignedOpType.getName().toUpperCase())
                            .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                }
            } else {
                UtilMethods.INSTANCE.Error(getActivity(), getResources().getString(R.string.NotAuthorized));
            }
        }

        /*--Reports--*/
        else if (id == 100) {
            Intent i = new Intent(getActivity(), PaymentRequest.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            getActivity().startActivity(i);
        } else if (id == 101) {
            Intent intent = new Intent(getActivity(), RechargeHistory.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        } else if (id == 102) {
            Intent intent = new Intent(getActivity(), LedgerReport.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        } else if (id == 103) {
            Intent i = new Intent(getActivity(), FundReqReport.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        } else if (id == 104) {
            Intent n = new Intent(getActivity(), DisputeReport.class);
            n.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(n);
        } else if (id == 105) {
            Intent i = new Intent(getActivity(), DMRReport.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);

        } else if (id == 106) {
            Intent i = new Intent(getActivity(), MoveToWalletReportActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);

        } else if (id == 107) {
            Intent i = new Intent(getActivity(), FundRecReport.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);

        } else if (id == 108) {
            Intent i = new Intent(getActivity(), FundOrderPendingActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);

        } else if (id == 109) {
            Intent i = new Intent(getActivity(), UserDayBookActivity.class);
            i.putExtra("Type", "UserDayBook");
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            getActivity().startActivity(i);
        } else if (id == 110) {
            Intent i = new Intent(getActivity(), CommissionScreen.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            getActivity().startActivity(i);
        } else if (id == 111) { //W2R Report
            Intent n = new Intent(getActivity(), W2RHistory.class);
            n.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(n);
        } else if (id == 112) { //AEPS
            Intent i = new Intent(getActivity(), AEPSReportActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            getActivity().startActivity(i);
        } else if (id == 113) {
            Intent i = new Intent(getActivity(), DthSubscriptionReportActivity.class);
            i.putExtra("fromId", 0);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            getActivity().startActivity(i);
        } else if (id == 114) {
            Intent i = new Intent(getActivity(), CreateUserActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            i.putExtra("KeyFor", "User");
            getActivity().startActivity(i);
        } else if (id == 115) {
            Intent i = new Intent(getActivity(), CreateUserActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            i.putExtra("KeyFor", "FOS");
            getActivity().startActivity(i);
        } else if (id == 116) {
            Intent i = new Intent(getActivity(), AppUserListActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            getActivity().startActivity(i);
        } else if (id == 117) {
            Intent i = new Intent(getActivity(), FosUserListActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            getActivity().startActivity(i);
        } else if (id == 118) { // FOS Areas

            Intent intent = new Intent(getActivity(), ChannelReportActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
        if (id == 121) { //Account Statement Report

            Intent intent = new Intent(getActivity(), AccountStatementReportActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        } else if (id == 122) //FOS Outstanding Report
        {
            Intent intent = new Intent(getActivity(), FosReportActivity.class);
            startActivity(intent);
        } else if (id == 11100) {
            Intent intent = new Intent(getActivity(), GetAdvertisementByCity.class);
            startActivity(intent);
        } else if (id == 123) //Channel Outstanding Report
        {
            if (loginPrefResponse.isAreaMaster()) {
                if (mAppGetAMResponse == null || mAppGetAMResponse.getAreaMaster() == null) {
                    mAppGetAMResponse = new Gson().fromJson(UtilMethods.INSTANCE.getAreaListPref(getActivity()), AppGetAMResponse.class);
                    if (mAppGetAMResponse == null || mAppGetAMResponse.getAreaMaster() == null) {
                        if (UtilMethods.INSTANCE.isNetworkAvialable(getActivity())) {
                            loader.show();
                            UtilMethods.INSTANCE.GetArealist(getActivity(), loader, loginPrefResponse,
                                    new UtilMethods.ApiCallBack() {
                                        @Override
                                        public void onSucess(Object object) {
                                            mAppGetAMResponse = (AppGetAMResponse) object;
                                            if (mAppGetAMResponse != null && mAppGetAMResponse.getAreaMaster() != null && mAppGetAMResponse.getAreaMaster().size() > 0) {
                                                customAlertDialog.channelAreaListDialog(getActivity(), mAppGetAMResponse.getAreaMaster());
                                            }
                                        }


                                    });


                        } else {
                            UtilMethods.INSTANCE.NetworkError(getActivity());
                        }
                    } else {
                        customAlertDialog.channelAreaListDialog(getActivity(), mAppGetAMResponse.getAreaMaster());
                    }
                } else {
                    customAlertDialog.channelAreaListDialog(getActivity(), mAppGetAMResponse.getAreaMaster());
                }

            } else {
                Intent intent = new Intent(getActivity(), ChannelReportActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        } else if (id == 125) //Voucher Entry
        {
            customAlertDialog.channelFosListDialog(getActivity(), loader, loginPrefResponse);
        }
    }

    private void showBCDetail(BcResponse bcDetail) {
        try {
            String mobileno = bcDetail.getMobileno();
            String secretKey = bcDetail.getSecretKey();
            String saltKey = bcDetail.getSaltKey();
            String bcid = bcDetail.getBcid();
            String userId = bcDetail.getUserId();
            String cpid = bcDetail.getCpid() != null && bcDetail.getCpid().length() > 0 ? bcDetail.getCpid() : "";
            String emailId = bcDetail.getEmailId();


            if (bcid != null && bcid.length() > 0) {
                Intent intent = new Intent(getActivity(), DashboardActivity.class);
                intent.putExtra("saltKey", saltKey);
                intent.putExtra("secretKey", secretKey);
                intent.putExtra("BcId", bcid);
                intent.putExtra("UserId", userId);
                intent.putExtra("bcEmailId", emailId);
                intent.putExtra("Phone1", mobileno);
                intent.putExtra("cpid", cpid);
                startActivityForResult(intent, INTENT_CODE_MAHAGRAM_APES);
            } else {
                UtilMethods.INSTANCE.Error(getActivity(), getResources().getString(R.string.AepsNotApproved));
            }

        } catch (Exception ex) {
            UtilMethods.INSTANCE.Error(getActivity(), getActivity().getResources().getString(R.string.some_thing_error) + "due to " + ex.getMessage());
        }
    }

    private void getLocation(SdkDetail sdkDetail, boolean isAEPSOnBoard) {
        if (UtilMethods.getLattitude != 0 && UtilMethods.getLongitude != 0) {
            onBoardingSubmit(isAEPSOnBoard, sdkDetail, UtilMethods.getLattitude, UtilMethods.getLongitude);
        } else {
            if (mGetLocation != null) {
                mGetLocation.startLocationUpdates((lattitude, longitude) -> {
                    UtilMethods.getLattitude = lattitude;
                    UtilMethods.getLongitude = longitude;
                    onBoardingSubmit(isAEPSOnBoard, sdkDetail, lattitude, longitude);
                });
            } else {
                mGetLocation = new GetLocation(getActivity(), loader);
                mGetLocation.startLocationUpdates((lattitude, longitude) -> {
                    UtilMethods.getLattitude = lattitude;
                    UtilMethods.getLongitude = longitude;
                    onBoardingSubmit(isAEPSOnBoard, sdkDetail, lattitude, longitude);
                });
            }
        }
    }

    private void onBoardingSubmit(boolean isAEPSOnBoard, SdkDetail sdkDetail, double lattitude, double longitude) {
        String pId = "", pApiKey = "", mCode = "", mobileNo = "", firm = "", email = "";
        mobileNo = loginPrefResponse.getData().getMobileNo();
        try {
            pId = sdkDetail.getApiPartnerID();
        } catch (NumberFormatException nfe) {
            UtilMethods.INSTANCE.Error(getActivity(), "Invalid partner Id ");
        }

        try {
            pApiKey = sdkDetail.getServiceOutletPIN();
        } catch (NumberFormatException nfe) {
            UtilMethods.INSTANCE.Error(getActivity(), "Invalid API Key provided in credential");
        }

        try {
            mCode = sdkDetail.getApiOutletID();
        } catch (NumberFormatException nfe) {
            UtilMethods.INSTANCE.Error(getActivity(), "Invalid API OutletID");
        }

        try {
            if (sdkDetail.getApiOutletMob() != null && !sdkDetail.getApiOutletMob().isEmpty())
                mobileNo = sdkDetail.getApiOutletMob();
        } catch (NumberFormatException nfe) {
            UtilMethods.INSTANCE.Error(getActivity(), "Invalid Mobile Number");
        }

        try {
            firm = sdkDetail.getOutletName();
        } catch (NumberFormatException nfe) {
            UtilMethods.INSTANCE.Error(getActivity(), "Invalid Outlet Name");
        }

        try {
            email = sdkDetail.getEmailID();
        } catch (NumberFormatException nfe) {
            UtilMethods.INSTANCE.Error(getActivity(), "Invalid Email Id");
        }


        if (pId != null && pId.isEmpty()) {
            UtilMethods.INSTANCE.Error(getActivity(), "Invalid or null partner Id");
            return;
        } else if (pApiKey != null && pApiKey.isEmpty()) {
            UtilMethods.INSTANCE.Error(getActivity(), "Invalid or null ApiKey");
            return;
        } else if (mCode != null && mCode.isEmpty()) {
            UtilMethods.INSTANCE.Error(getActivity(), "Invalid or null Merchant Code");
            return;
        }

        startActivityForResult(new Intent(getActivity(), HostActivity.class)
                .putExtra("pId", pId)//partner Id provided in credential
                .putExtra("pApiKey", pApiKey)//JWT API Key provided in credential
                .putExtra("mCode", mCode)  //Merchant Code
                .putExtra("mobile", mobileNo) // merchant mobile number
                .putExtra("lat", lattitude + "")
                .putExtra("lng", longitude + "")
                .putExtra("firm", firm)
                .putExtra("email", email)
                .addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION), isAEPSOnBoard ? INTENT_AEPS_PAYS_PRINT : INTENT_MINI_ATM_PAYS_PRINT);
    }

    private void handleMiniATMPaySprintSDK() {
        Intent i = new Intent(getActivity(), PaySprintMATMActivity.class);
        i.putExtra("SDKType", sdkType + "");
        i.putExtra("OID", opID + "");
        i.putExtra("SDKDetails", mSdkDetail);
        startActivity(i);

    }

    private void handleAepsPaySprintSDK() {

        startActivity(new Intent(getActivity(), AEPSDashboardActivity.class)
                .putExtra("SDKDetail", mSdkDetail)
                .putExtra("InterfaceType", sdkType)
                .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));

    }

    private void bindAccountListOp(int opTypeId) {

        NumberListResponse opList = new Gson().fromJson(UtilMethods.INSTANCE.getNumberList(getActivity()), NumberListResponse.class);
        if (opList != null) {
            if (opList.getData().getOperators() != null && opList.getData().getOperators().size() > 0) {
                accountOpList = new ArrayList<>();
                for (OperatorList op : opList.getData().getOperators()) {
                    if (op.getOpType() == opTypeId && op.isActive()) {
                        accountOpList.add(op);
                    }
                }
            }

            if (accountOpList != null && accountOpList.size() > 0) {
                customAlertDialog.accountOpListDialog(accountOpList, new AccountOpListAdapter.AccountListCallBack() {
                    @Override
                    public void onIconClick(int oId, String Url) {
                        try {
                            getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Url)));
                        } catch (ActivityNotFoundException anfe) {

                        }
                    }

                });
            }
        }
    }

    private void hitAccountOpenListAPI(int opTypeId, String name) {

        if (UtilMethods.INSTANCE.isNetworkAvialable(getActivity())) {
            if (ApplicationConstant.INSTANCE.isLoaderShownPref) {
                loader.show();
                loader.setCancelable(false);
                loader.setCanceledOnTouchOutside(false);
            }

            UtilMethods.INSTANCE.GetAccountOpenlist(getActivity(), opTypeId, loader, new UtilMethods.ApiCallBack() {
                        @Override
                        public void onSucess(Object object) {
                            if (object != null && object instanceof AccountOpenListResponse) {
                                ApplicationConstant.INSTANCE.isLoaderShownPref = false;
                                AccountOpenListResponse mAccountOpenListResponse = (AccountOpenListResponse) object;
                                if (mAccountOpenListResponse.isDrawOpImage()) {
                                    bindAccountListOp(opTypeId, mAccountOpenListResponse);
                                } else {
                                    if (mAccountOpenListResponse != null && mAccountOpenListResponse.getAccountOpeningDeatils() != null && mAccountOpenListResponse.getAccountOpeningDeatils().size() > 0) {

                                        startActivity(new Intent(getActivity(), AccountOpenActivity.class)
                                                .putExtra("AccountList", mAccountOpenListResponse.getAccountOpeningDeatils()
                                                ).putExtra("SERVICE_ID", opTypeId)
                                                .putExtra("SERVICE_NAME", name)
                                                .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));

                                    } else {
                                        UtilMethods.INSTANCE.Error(getActivity(), "list not found!");
                                    }
                                }

                            }


                        }

                        private void bindAccountListOp(int opTypeId, AccountOpenListResponse mOpenListResponse) {

                            NumberListResponse opList = new Gson().fromJson(UtilMethods.INSTANCE.getNumberList(getActivity()), NumberListResponse.class);
                            if (opList != null) {
                                if (opList.getData().getOperators() != null && opList.getData().getOperators().size() > 0) {
                                    accountOpList = new ArrayList<>();
                                    for (OperatorList op : opList.getData().getOperators()) {
                                        if (op.getOpType() == opTypeId && op.isActive()) {
                                            accountOpList.add(op);
                                        }
                                    }
                                }

                                if (accountOpList != null && accountOpList.size() > 0) {
                                    if (accountOpList != null && accountOpList.size() > 0) {
                                        customAlertDialog.accountOpListDialog(accountOpList, new AccountOpListAdapter.AccountListCallBack() {
                                            @Override
                                            public void onIconClick(int oId, String Url) {

                                                if (mOpenListResponse != null && mOpenListResponse.getAccountOpeningDeatils() != null && mOpenListResponse.getAccountOpeningDeatils().size() > 0) {
                                                    for (AccountOpData opDataItem : mOpenListResponse.getAccountOpeningDeatils()) {
                                                        if (opDataItem.getOid() == oId) {
                                                            try {
                                                                getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(opDataItem.getRedirectURL())));
                                                            } catch (ActivityNotFoundException anfe) {

                                                            }
                                                        }
                                                    }

                                                }
                                            }

                                        });
                                    }
                                }
                            }
                        }
                    }
            );
        } else {
            UtilMethods.INSTANCE.NetworkError(getActivity(), getResources().getString(R.string.err_msg_network_title), getResources().getString(R.string.err_msg_network));
        }
    }

    private void newsAPi() {
        newsWeb.getSettings().setJavaScriptEnabled(true);
        String news = "Welcome To " + getString(R.string.app_name);
        String summary = "<html><body><MARQUEE>" + news + "</MARQUEE></body></html>";
        newsWeb.loadData(summary, "text/html", "utf-8");

        UtilMethods.INSTANCE.NewsApi(getActivity(), false, newsWeb, new UtilMethods.ApiCallBack() {
            @Override
            public void onSucess(Object object) {
                newsWeb.loadData(summary, "text/html", "utf-8");
            }
        });
    }

    public void BannerApi() {
        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);

            Call<AppBannerResponse> call = git.GetAppBanner(new BasicRequest(
                    loginPrefResponse.getData().getUserID(), loginPrefResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    UtilMethods.INSTANCE.getIMEI(getActivity()),
                    "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(getActivity()), loginPrefResponse.getData().getSessionID(), loginPrefResponse.getData().getSession()));

            call.enqueue(new Callback<AppBannerResponse>() {
                @Override
                public void onResponse(Call<AppBannerResponse> call, final retrofit2.Response<AppBannerResponse> response) {
                    try {
                        if (response.body() != null && response.body().getStatuscode() == 1) {

                            if (UtilMethods.INSTANCE.getAppLogoUrl(getActivity()) == null) {
                                UtilMethods.INSTANCE.setAppLogoURl(getActivity(), response.body().getAppLogoUrl());
                            } else if (!UtilMethods.INSTANCE.getAppLogoUrl(getActivity()).equalsIgnoreCase(response.body().getAppLogoUrl())) {
                                UtilMethods.INSTANCE.setAppLogoURl(getActivity(), response.body().getAppLogoUrl());
                            }

                            if (response.body().getBanners() != null && response.body().getBanners().size() > 0) {
                                pagerContainer.setVisibility(View.VISIBLE);
                                mCustomPagerAdapter = new CustomPagerAdapter(response.body().getBanners(), getActivity(), 1);
                                mViewPager.setAdapter(mCustomPagerAdapter);
                                mViewPager.setOffscreenPageLimit(mCustomPagerAdapter.getCount());
                                mDotsCount = mViewPager.getAdapter().getCount();
                                mDotsText = new TextView[mDotsCount];

                                //here we set the dots
                                dotsCount.removeAllViews();
                                if (handler != null && mRunnable != null) {
                                    handler.removeCallbacks(mRunnable);
                                }
                                for (int i = 0; i < mDotsCount; i++) {
                                    mDotsText[i] = new TextView(getActivity());
                                    mDotsText[i].setText(".");
                                    mDotsText[i].setTextSize(50);
                                    mDotsText[i].setTypeface(null, Typeface.BOLD);
                                    mDotsText[i].setTextColor(getResources().getColor(R.color.light_grey));
                                    dotsCount.addView(mDotsText[i]);
                                }


                                mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                    @Override
                                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                                        for (int i = 0; i < mDotsCount; i++) {
                                            mDotsText[i].setTextColor(getResources().getColor(R.color.light_grey));
                                        }
                                        mDotsText[position].setTextColor(getResources().getColor(R.color.colorPrimary));
                                    }

                                    @Override
                                    public void onPageSelected(int position) {

                                    }

                                    @Override
                                    public void onPageScrollStateChanged(int state) {

                                    }
                                });

                                postDelayedScrollNext();

                            }

                        }
                    } catch (Exception e) {

                    }

                }

                @Override
                public void onFailure(Call<AppBannerResponse> call, Throwable t) {


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void postDelayedScrollNext() {
        handler = new Handler();
        mRunnable = new Runnable() {
            public void run() {

                if (mViewPager.getAdapter() != null) {
                    if (mViewPager.getCurrentItem() == mViewPager.getAdapter().getCount() - 1) {
                        mViewPager.setCurrentItem(0);
                        postDelayedScrollNext();
                        return;
                    }
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                    // postDelayedScrollNext(position+1);
                    postDelayedScrollNext();
                }

                // onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
            }
        };
        handler.postDelayed(mRunnable, 2500);

    }

    private void hitCallOnboarding(int opTypes, boolean isPan) {
        SharedPreferences prefs = getActivity().getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String response = prefs.getString(ApplicationConstant.INSTANCE.numberListPref, "");
        Gson gson = new Gson();
        NumberListResponse NumberList = gson.fromJson(response, NumberListResponse.class);
        if (NumberList != null) {
            ArrayList<OperatorList> operatorsList = NumberList.getData().getOperators();
            if (operatorsList != null && operatorsList.size() > 0) {
                for (OperatorList op : operatorsList) {
                    if (op.getOpType() == opTypes && op.isActive()) { //For Aeps opType is 22,14 for DMR
                        opID = op.getOid();
                        break;
                    }
                }
            }
        }
        if (UtilMethods.INSTANCE.isNetworkAvialable(getActivity())) {
            try {
                if (UtilMethods.getLattitude != 0 && UtilMethods.getLongitude != 0) {
                    UtilMethods.INSTANCE.CallOnboarding(getActivity(), isPan, opID, "", "0", "", 0, false, getActivity().getSupportFragmentManager(), null, null, null, UtilMethods.getLattitude, UtilMethods.getLongitude, loader, new UtilMethods.ApiCallBackOnBoardingMethod() {
                        @Override
                        public void onSuccess(Object object) {
                            if (object != null && object instanceof OnboardingResponse) {
                                OnboardingResponse mOnboardingResponse = (OnboardingResponse) object;
                                sdkType = mOnboardingResponse.getSdkType();
                                if (opTypes == 22) //AEPS
                                {
                                    if ((mOnboardingResponse.getSdkType() == 1 || mOnboardingResponse.getSdkType() == 7) && (mOnboardingResponse.getSdkDetail() != null)) {

                                        mSdkDetail = mOnboardingResponse.getSdkDetail();
                                        handleAepsPaySprintSDK();
                                    } else if (mOnboardingResponse.getSdkType() == 2 || mOnboardingResponse.getSdkType() == 4 && mOnboardingResponse.getBcResponse() != null) {

                                        if (mOnboardingResponse.getBcResponse().size() > 0) {
                                            if (mOnboardingResponse.getBcResponse().get(0).getErrorCode() == 0) {

                                                showBCDetail(mOnboardingResponse.getBcResponse().get(0));
                                            } else {
                                                UtilMethods.INSTANCE.Error(getActivity(), "Something went wrong!!");
                                            }
                                        } else {
                                            UtilMethods.INSTANCE.Error(getActivity(), "BcDetails Data not found!!");
                                        }

                                    } else if (mOnboardingResponse.getSdkType() == 2 || mOnboardingResponse.getSdkType() == 4 && mOnboardingResponse.getSdkDetail() != null && mOnboardingResponse.getSdkDetail().getBcResponse() != null) {

                                    } else if (mOnboardingResponse.getSdkType() == 3 && mOnboardingResponse.getSdkDetail() != null) {
                                        SdkDetail sdkDetail = mOnboardingResponse.getSdkDetail();
                                        if (sdkDetail != null) {
                                            int partnerid = 0;
                                            int outletId = 0;
                                            try {
                                                partnerid = Integer.parseInt(mOnboardingResponse.getSdkDetail().getApiPartnerID());
                                            } catch (NumberFormatException nfe) {
                                                UtilMethods.INSTANCE.Error(getActivity(), "Invalid partner id its should be integer value");
                                            }

                                            try {
                                                outletId = Integer.parseInt(mOnboardingResponse.getSdkDetail().getApiOutletID());
                                            } catch (NumberFormatException nfe) {
                                                UtilMethods.INSTANCE.Error(getActivity(), "Invalid outlet id its should be integer value");
                                            }

                                            startActivityForResult(new Intent(getActivity(), EMoneyLoginActivity.class)
                                                    .putExtra(KeyConstant.USER_ID, partnerid)
                                                    .putExtra(KeyConstant.OUTLET_ID, outletId)
                                                    .putExtra(KeyConstant.PARTNER_ID, 0)
                                                    .putExtra(KeyConstant.LATITUDE, UtilMethods.getLattitude)
                                                    .putExtra(KeyConstant.LONGITUDE, UtilMethods.getLongitude)
                                                    .putExtra(KeyConstant.PIN, mOnboardingResponse.getSdkDetail().getApiOutletPassword())
                                                    .putExtra(KeyConstant.SERVICE_TYPE, KeyConstant.AEPS), REQUEST_CODE_ROUNDPAY_AEPS);
                                        } else {
                                            UtilMethods.INSTANCE.Error(getActivity(), " Required Data not found !! ");
                                        }
                                    } else {
                                        UtilMethods.INSTANCE.Error(getActivity(), "SDK Type Error !! ");
                                    }


                                } else if (opTypes == 44) {
                                    if (mOnboardingResponse.getSdkType() == 1 && mOnboardingResponse.getSdkDetail() != null) { //FingPay

                                        Intent i = new Intent(getActivity(), MicroAtmActivity.class);
                                        i.putExtra("SDKType", mOnboardingResponse.getSdkType() + "");
                                        i.putExtra("OID", opID);
                                        i.putExtra("SDKDetails", mOnboardingResponse.getSdkDetail());
                                        startActivity(i);

                                    } else if (mOnboardingResponse.getSdkType() == 2 || mOnboardingResponse.getSdkType() == 4)//Mahagram
                                    {
                                        if (mOnboardingResponse.getBcResponse() != null && mOnboardingResponse.getBcResponse().size() > 0) {
                                            BcResponse bcResponse = mOnboardingResponse.getBcResponse().get(0);
                                            MahagramMicroAtm(bcResponse);
                                        } else if (mOnboardingResponse.getSdkDetail() != null && mOnboardingResponse.getSdkDetail().getBcResponse() != null) {
                                            if (mOnboardingResponse.getSdkDetail().getBcResponse().size() > 0) {
                                                if (mOnboardingResponse.getSdkDetail().getBcResponse().get(0).getErrorCode() == 0) {
                                                    MahagramMicroAtm(mOnboardingResponse.getBcResponse().get(0));
                                                } else {
                                                    UtilMethods.INSTANCE.Error(getActivity(), "Something went wrong!!");
                                                }
                                            }

                                        } else {
                                            UtilMethods.INSTANCE.Error(getActivity(), "BcDetails Data not found!!");
                                        }
                                    } else if (mOnboardingResponse.getSdkType() == 3 && mOnboardingResponse.getSdkDetail() != null) { //RoundPay
                                        sdkDetail = mOnboardingResponse.getSdkDetail();
                                        int partnerid = 0;
                                        int outletId = 0;
                                        try {
                                            partnerid = Integer.parseInt(sdkDetail.getApiPartnerID());
                                        } catch (NumberFormatException nfe) {
                                            UtilMethods.INSTANCE.Error(getActivity(), "Invalid partner id its should be integer value");
                                        }

                                        try {
                                            outletId = Integer.parseInt(sdkDetail.getApiOutletID());
                                        } catch (NumberFormatException nfe) {
                                            UtilMethods.INSTANCE.Error(getActivity(), "Invalid outlet id its should be integer value");
                                        }

                                        String apiOutletPassword = sdkDetail.getApiOutletPassword();

                                        startActivityForResult(new Intent(getActivity(), EMoneyLoginActivity.class)
                                                .putExtra(KeyConstant.USER_ID, partnerid)
                                                .putExtra(KeyConstant.OUTLET_ID, outletId)
                                                .putExtra(KeyConstant.PARTNER_ID, 0)
                                                .putExtra(KeyConstant.LATITUDE, UtilMethods.getLattitude)
                                                .putExtra(KeyConstant.LONGITUDE, UtilMethods.getLongitude)
                                                .putExtra(KeyConstant.PIN, apiOutletPassword)
                                                .putExtra(KeyConstant.SERVICE_TYPE, KeyConstant.MICRO_ATM), REQUEST_CODE_RDP_MINI_ATM);
                                    } else if (sdkType == 7 && mOnboardingResponse.getSdkDetail() != null) {
                                        mSdkDetail = mOnboardingResponse.getSdkDetail();
                                        handleMiniATMPaySprintSDK();
                                    } else {
                                        UtilMethods.INSTANCE.Error(getActivity(), "Merchant details is not found or SDK not integrated");
                                    }
                                }
                            }

                        }

                        @Override
                        public void onError(Object object) {

                        }

                        @Override
                        public void onOnBoarding(Object object) {

                            if (object != null && object instanceof OnboardingResponse) {
                                OnboardingResponse response = (OnboardingResponse) object;
                                sdkType = response.getSdkType();
                                boolean isAEPS = opTypes == 22;
                                if (sdkType == 7 && response.getSdkDetail() != null) {
                                    mSdkDetail = response.getSdkDetail();
                                    getLocation(mSdkDetail, isAEPS);
                                }
                            }
                        }

                        private void MahagramMicroAtm(BcResponse bcDetail) {
                            String saltKey, secretKey, mobileno, bcid, userId, cpid, emailId;
                            try {
                                mobileno = bcDetail.getMobileno();
                                secretKey = bcDetail.getSecretKey();
                                saltKey = bcDetail.getSaltKey();
                                bcid = bcDetail.getBcid();
                                userId = bcDetail.getUserId();
                                cpid = bcDetail.getCpid() != null && bcDetail.getCpid().length() > 0 ? bcDetail.getCpid() : "";
                                emailId = bcDetail.getEmailId();
                                String clientUniqueId = "";
                                try {
                                    clientUniqueId = UtilMethods.INSTANCE.getUniqueId(loginPrefResponse.getData().getUserID());
                                } catch (Exception exception) {
                                    UtilMethods.INSTANCE.Error(getActivity(), getResources().getString(R.string.unique_not_found));
                                }

                                if (bcid != null && bcid.length() > 0) {
                                    PackageManager packageManager = getActivity().getPackageManager();
                                    if (UtilMethods.INSTANCE.isPackageInstalled("org.egram.microatm", packageManager)) {
                                        Intent intent = new Intent();
                                        intent.setComponent(new
                                                ComponentName("org.egram.microatm", "org.egram.microatm.BluetoothMacSearchActivity"));
                                        intent.putExtra("saltkey", saltKey);
                                        intent.putExtra("secretkey", secretKey);
                                        intent.putExtra("bcid", bcid);
                                        intent.putExtra("userid", userId);
                                        intent.putExtra("bcemailid", emailId);
                                        intent.putExtra("phone1", mobileno);
                                        intent.putExtra("clientrefid", clientUniqueId);
                                        intent.putExtra("vendorid", "");
                                        intent.putExtra("udf1", "");
                                        intent.putExtra("udf2", "");
                                        intent.putExtra("udf3", "");
                                        intent.putExtra("udf4", "");
                                        startActivityForResult(intent, REQUEST_CODE_MAHAGRAM_MINI_ATM);

                                    } else {
                                        final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(getActivity());
                                        alertDialog.setTitle("Get Service");
                                        alertDialog.setMessage("MicroATM Service not installed. Click OK to download .");
                                        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                try {
                                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=org.egram.microatm")));
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        });
                                        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int i) {
                                                dialog.dismiss();
                                            }
                                        });
                                        alertDialog.show();
                                    }

                                } else {
                                    UtilMethods.INSTANCE.Error(getActivity(), getResources().getString(R.string.microATMApproved));
                                }
                            } catch (Exception nfe) {
                                UtilMethods.INSTANCE.Error(getActivity(), nfe.getMessage());
                            }


                        }
                    });

                } else {
                    if (mGetLocation != null) {
                        mGetLocation.startLocationUpdates((lattitude, longitude) -> {
                            UtilMethods.getLattitude = lattitude;
                            UtilMethods.getLongitude = longitude;
                            UtilMethods.INSTANCE.CallOnboarding(getActivity(), isPan, opID, "", "0", "", 0, false, getActivity().getSupportFragmentManager(), null, null, null, UtilMethods.getLattitude, UtilMethods.getLongitude, loader, new UtilMethods.ApiCallBackOnBoardingMethod() {
                                @Override
                                public void onSuccess(Object object) {
                                    if (object != null && object instanceof OnboardingResponse) {
                                        OnboardingResponse mOnboardingResponse = (OnboardingResponse) object;
                                        sdkType = mOnboardingResponse.getSdkType();
                                        if (opTypes == 22) //AEPS
                                        {
                                            if ((mOnboardingResponse.getSdkType() == 1 || mOnboardingResponse.getSdkType() == 7) && (mOnboardingResponse.getSdkDetail() != null)) {

                                                mSdkDetail = mOnboardingResponse.getSdkDetail();
                                                handleAepsPaySprintSDK();
                                            } else if (mOnboardingResponse.getSdkType() == 2 || mOnboardingResponse.getSdkType() == 4 && mOnboardingResponse.getBcResponse() != null) {

                                                if (mOnboardingResponse.getBcResponse().size() > 0) {
                                                    if (mOnboardingResponse.getBcResponse().get(0).getErrorCode() == 0) {

                                                        showBCDetail(mOnboardingResponse.getBcResponse().get(0));
                                                    } else {
                                                        UtilMethods.INSTANCE.Error(getActivity(), "Something went wrong!!");
                                                    }
                                                } else {
                                                    UtilMethods.INSTANCE.Error(getActivity(), "BcDetails Data not found!!");
                                                }

                                            } else if (mOnboardingResponse.getSdkType() == 2 || mOnboardingResponse.getSdkType() == 4 && mOnboardingResponse.getSdkDetail() != null && mOnboardingResponse.getSdkDetail().getBcResponse() != null) {

                                            } else if (mOnboardingResponse.getSdkType() == 3 && mOnboardingResponse.getSdkDetail() != null) {
                                                SdkDetail sdkDetail = mOnboardingResponse.getSdkDetail();
                                                if (sdkDetail != null) {
                                                    int partnerid = 0;
                                                    int outletId = 0;
                                                    try {
                                                        partnerid = Integer.parseInt(mOnboardingResponse.getSdkDetail().getApiPartnerID());
                                                    } catch (NumberFormatException nfe) {
                                                        UtilMethods.INSTANCE.Error(getActivity(), "Invalid partner id its should be integer value");
                                                    }

                                                    try {
                                                        outletId = Integer.parseInt(mOnboardingResponse.getSdkDetail().getApiOutletID());
                                                    } catch (NumberFormatException nfe) {
                                                        UtilMethods.INSTANCE.Error(getActivity(), "Invalid outlet id its should be integer value");
                                                    }

                                                    startActivityForResult(new Intent(getActivity(), EMoneyLoginActivity.class)
                                                            .putExtra(KeyConstant.USER_ID, partnerid)
                                                            .putExtra(KeyConstant.OUTLET_ID, outletId)
                                                            .putExtra(KeyConstant.PARTNER_ID, 0)
                                                            .putExtra(KeyConstant.LATITUDE, UtilMethods.getLattitude)
                                                            .putExtra(KeyConstant.LONGITUDE, UtilMethods.getLongitude)
                                                            .putExtra(KeyConstant.PIN, mOnboardingResponse.getSdkDetail().getApiOutletPassword())
                                                            .putExtra(KeyConstant.SERVICE_TYPE, KeyConstant.AEPS), REQUEST_CODE_ROUNDPAY_AEPS);
                                                } else {
                                                    UtilMethods.INSTANCE.Error(getActivity(), " Required Data not found !! ");
                                                }
                                            } else {
                                                UtilMethods.INSTANCE.Error(getActivity(), "SDK Type Error !! ");
                                            }


                                        } else if (opTypes == 44) {
                                            if (mOnboardingResponse.getSdkType() == 1 && mOnboardingResponse.getSdkDetail() != null) { //FingPay

                                                Intent i = new Intent(getActivity(), MicroAtmActivity.class);
                                                i.putExtra("SDKType", mOnboardingResponse.getSdkType() + "");
                                                i.putExtra("OID", opID);
                                                i.putExtra("SDKDetails", mOnboardingResponse.getSdkDetail());
                                                startActivity(i);

                                            } else if (mOnboardingResponse.getSdkType() == 2 || mOnboardingResponse.getSdkType() == 4)//Mahagram
                                            {
                                                if (mOnboardingResponse.getBcResponse() != null && mOnboardingResponse.getBcResponse().size() > 0) {
                                                    BcResponse bcResponse = mOnboardingResponse.getBcResponse().get(0);
                                                    MahagramMicroAtm(bcResponse);
                                                } else if (mOnboardingResponse.getSdkDetail() != null && mOnboardingResponse.getSdkDetail().getBcResponse() != null) {
                                                    if (mOnboardingResponse.getSdkDetail().getBcResponse().size() > 0) {
                                                        if (mOnboardingResponse.getSdkDetail().getBcResponse().get(0).getErrorCode() == 0) {
                                                            MahagramMicroAtm(mOnboardingResponse.getBcResponse().get(0));
                                                        } else {
                                                            UtilMethods.INSTANCE.Error(getActivity(), "Something went wrong!!");
                                                        }
                                                    }

                                                } else {
                                                    UtilMethods.INSTANCE.Error(getActivity(), "BcDetails Data not found!!");
                                                }
                                            } else if (mOnboardingResponse.getSdkType() == 3 && mOnboardingResponse.getSdkDetail() != null) { //RoundPay
                                                sdkDetail = mOnboardingResponse.getSdkDetail();
                                                int partnerid = 0;
                                                int outletId = 0;
                                                try {
                                                    partnerid = Integer.parseInt(sdkDetail.getApiPartnerID());
                                                } catch (NumberFormatException nfe) {
                                                    UtilMethods.INSTANCE.Error(getActivity(), "Invalid partner id its should be integer value");
                                                }

                                                try {
                                                    outletId = Integer.parseInt(sdkDetail.getApiOutletID());
                                                } catch (NumberFormatException nfe) {
                                                    UtilMethods.INSTANCE.Error(getActivity(), "Invalid outlet id its should be integer value");
                                                }

                                                String apiOutletPassword = sdkDetail.getApiOutletPassword();

                                                startActivityForResult(new Intent(getActivity(), EMoneyLoginActivity.class)
                                                        .putExtra(KeyConstant.USER_ID, partnerid)
                                                        .putExtra(KeyConstant.OUTLET_ID, outletId)
                                                        .putExtra(KeyConstant.PARTNER_ID, 0)
                                                        .putExtra(KeyConstant.LATITUDE, UtilMethods.getLattitude)
                                                        .putExtra(KeyConstant.LONGITUDE, UtilMethods.getLongitude)
                                                        .putExtra(KeyConstant.PIN, apiOutletPassword)
                                                        .putExtra(KeyConstant.SERVICE_TYPE, KeyConstant.MICRO_ATM), REQUEST_CODE_RDP_MINI_ATM);
                                            } else if (sdkType == 7 && mOnboardingResponse.getSdkDetail() != null) {
                                                mSdkDetail = mOnboardingResponse.getSdkDetail();
                                                handleMiniATMPaySprintSDK();
                                            } else {
                                                UtilMethods.INSTANCE.Error(getActivity(), "Merchant details is not found or SDK not integrated");
                                            }
                                        }
                                    }

                                }

                                @Override
                                public void onError(Object object) {

                                }

                                @Override
                                public void onOnBoarding(Object object) {

                                    if (object != null && object instanceof OnboardingResponse) {
                                        OnboardingResponse response = (OnboardingResponse) object;
                                        sdkType = response.getSdkType();
                                        boolean isAEPS = opTypes == 22;
                                        if (sdkType == 7 && response.getSdkDetail() != null) {
                                            mSdkDetail = response.getSdkDetail();
                                            getLocation(mSdkDetail, isAEPS);
                                        }
                                    }
                                }

                                private void MahagramMicroAtm(BcResponse bcDetail) {
                                    String saltKey, secretKey, mobileno, bcid, userId, cpid, emailId;
                                    try {
                                        mobileno = bcDetail.getMobileno();
                                        secretKey = bcDetail.getSecretKey();
                                        saltKey = bcDetail.getSaltKey();
                                        bcid = bcDetail.getBcid();
                                        userId = bcDetail.getUserId();
                                        cpid = bcDetail.getCpid() != null && bcDetail.getCpid().length() > 0 ? bcDetail.getCpid() : "";
                                        emailId = bcDetail.getEmailId();
                                        String clientUniqueId = "";
                                        try {
                                            clientUniqueId = UtilMethods.INSTANCE.getUniqueId(loginPrefResponse.getData().getUserID());
                                        } catch (Exception exception) {
                                            UtilMethods.INSTANCE.Error(getActivity(), getResources().getString(R.string.unique_not_found));
                                        }

                                        if (bcid != null && bcid.length() > 0) {
                                            PackageManager packageManager = getActivity().getPackageManager();
                                            if (UtilMethods.INSTANCE.isPackageInstalled("org.egram.microatm", packageManager)) {
                                                Intent intent = new Intent();
                                                intent.setComponent(new
                                                        ComponentName("org.egram.microatm", "org.egram.microatm.BluetoothMacSearchActivity"));
                                                intent.putExtra("saltkey", saltKey);
                                                intent.putExtra("secretkey", secretKey);
                                                intent.putExtra("bcid", bcid);
                                                intent.putExtra("userid", userId);
                                                intent.putExtra("bcemailid", emailId);
                                                intent.putExtra("phone1", mobileno);
                                                intent.putExtra("clientrefid", clientUniqueId);
                                                intent.putExtra("vendorid", "");
                                                intent.putExtra("udf1", "");
                                                intent.putExtra("udf2", "");
                                                intent.putExtra("udf3", "");
                                                intent.putExtra("udf4", "");
                                                startActivityForResult(intent, REQUEST_CODE_MAHAGRAM_MINI_ATM);

                                            } else {
                                                final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(getActivity());
                                                alertDialog.setTitle("Get Service");
                                                alertDialog.setMessage("MicroATM Service not installed. Click OK to download .");
                                                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        try {
                                                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=org.egram.microatm")));
                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                });
                                                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int i) {
                                                        dialog.dismiss();
                                                    }
                                                });
                                                alertDialog.show();
                                            }

                                        } else {
                                            UtilMethods.INSTANCE.Error(getActivity(), getResources().getString(R.string.microATMApproved));
                                        }
                                    } catch (Exception nfe) {
                                        UtilMethods.INSTANCE.Error(getActivity(), nfe.getMessage());
                                    }


                                }
                            });

                        });
                    } else {
                        mGetLocation = new GetLocation(requireActivity(), loader);
                        mGetLocation.startLocationUpdates((lattitude, longitude) -> {
                            UtilMethods.getLattitude = lattitude;
                            UtilMethods.getLongitude = longitude;
                            UtilMethods.INSTANCE.CallOnboarding(getActivity(), isPan, opID, "", "0", "", 0, false, getActivity().getSupportFragmentManager(), null, null, null, UtilMethods.getLattitude, UtilMethods.getLongitude, loader, new UtilMethods.ApiCallBackOnBoardingMethod() {
                                @Override
                                public void onSuccess(Object object) {
                                    if (object != null && object instanceof OnboardingResponse) {
                                        OnboardingResponse mOnboardingResponse = (OnboardingResponse) object;
                                        sdkType = mOnboardingResponse.getSdkType();
                                        if (opTypes == 22) //AEPS
                                        {
                                            if ((mOnboardingResponse.getSdkType() == 1 || mOnboardingResponse.getSdkType() == 7) && (mOnboardingResponse.getSdkDetail() != null)) {

                                                mSdkDetail = mOnboardingResponse.getSdkDetail();
                                                handleAepsPaySprintSDK();
                                            } else if (mOnboardingResponse.getSdkType() == 2 || mOnboardingResponse.getSdkType() == 4 && mOnboardingResponse.getBcResponse() != null) {

                                                if (mOnboardingResponse.getBcResponse().size() > 0) {
                                                    if (mOnboardingResponse.getBcResponse().get(0).getErrorCode() == 0) {

                                                        showBCDetail(mOnboardingResponse.getBcResponse().get(0));
                                                    } else {
                                                        UtilMethods.INSTANCE.Error(getActivity(), "Something went wrong!!");
                                                    }
                                                } else {
                                                    UtilMethods.INSTANCE.Error(getActivity(), "BcDetails Data not found!!");
                                                }

                                            } else if (mOnboardingResponse.getSdkType() == 2 || mOnboardingResponse.getSdkType() == 4 && mOnboardingResponse.getSdkDetail() != null && mOnboardingResponse.getSdkDetail().getBcResponse() != null) {

                                            } else if (mOnboardingResponse.getSdkType() == 3 && mOnboardingResponse.getSdkDetail() != null) {
                                                SdkDetail sdkDetail = mOnboardingResponse.getSdkDetail();
                                                if (sdkDetail != null) {
                                                    int partnerid = 0;
                                                    int outletId = 0;
                                                    try {
                                                        partnerid = Integer.parseInt(mOnboardingResponse.getSdkDetail().getApiPartnerID());
                                                    } catch (NumberFormatException nfe) {
                                                        UtilMethods.INSTANCE.Error(getActivity(), "Invalid partner id its should be integer value");
                                                    }

                                                    try {
                                                        outletId = Integer.parseInt(mOnboardingResponse.getSdkDetail().getApiOutletID());
                                                    } catch (NumberFormatException nfe) {
                                                        UtilMethods.INSTANCE.Error(getActivity(), "Invalid outlet id its should be integer value");
                                                    }

                                                    startActivityForResult(new Intent(getActivity(), EMoneyLoginActivity.class)
                                                            .putExtra(KeyConstant.USER_ID, partnerid)
                                                            .putExtra(KeyConstant.OUTLET_ID, outletId)
                                                            .putExtra(KeyConstant.PARTNER_ID, 0)
                                                            .putExtra(KeyConstant.LATITUDE, UtilMethods.getLattitude)
                                                            .putExtra(KeyConstant.LONGITUDE, UtilMethods.getLongitude)
                                                            .putExtra(KeyConstant.PIN, mOnboardingResponse.getSdkDetail().getApiOutletPassword())
                                                            .putExtra(KeyConstant.SERVICE_TYPE, KeyConstant.AEPS), REQUEST_CODE_ROUNDPAY_AEPS);
                                                } else {
                                                    UtilMethods.INSTANCE.Error(getActivity(), " Required Data not found !! ");
                                                }
                                            } else {
                                                UtilMethods.INSTANCE.Error(getActivity(), "SDK Type Error !! ");
                                            }


                                        } else if (opTypes == 44) {
                                            if (mOnboardingResponse.getSdkType() == 1 && mOnboardingResponse.getSdkDetail() != null) { //FingPay

                                                Intent i = new Intent(getActivity(), MicroAtmActivity.class);
                                                i.putExtra("SDKType", mOnboardingResponse.getSdkType() + "");
                                                i.putExtra("OID", opID);
                                                i.putExtra("SDKDetails", mOnboardingResponse.getSdkDetail());
                                                startActivity(i);

                                            } else if (mOnboardingResponse.getSdkType() == 2 || mOnboardingResponse.getSdkType() == 4)//Mahagram
                                            {
                                                if (mOnboardingResponse.getBcResponse() != null && mOnboardingResponse.getBcResponse().size() > 0) {
                                                    BcResponse bcResponse = mOnboardingResponse.getBcResponse().get(0);
                                                    MahagramMicroAtm(bcResponse);
                                                } else if (mOnboardingResponse.getSdkDetail() != null && mOnboardingResponse.getSdkDetail().getBcResponse() != null) {
                                                    if (mOnboardingResponse.getSdkDetail().getBcResponse().size() > 0) {
                                                        if (mOnboardingResponse.getSdkDetail().getBcResponse().get(0).getErrorCode() == 0) {
                                                            MahagramMicroAtm(mOnboardingResponse.getBcResponse().get(0));
                                                        } else {
                                                            UtilMethods.INSTANCE.Error(getActivity(), "Something went wrong!!");
                                                        }
                                                    }

                                                } else {
                                                    UtilMethods.INSTANCE.Error(getActivity(), "BcDetails Data not found!!");
                                                }
                                            } else if (mOnboardingResponse.getSdkType() == 3 && mOnboardingResponse.getSdkDetail() != null) { //RoundPay
                                                sdkDetail = mOnboardingResponse.getSdkDetail();
                                                int partnerid = 0;
                                                int outletId = 0;
                                                try {
                                                    partnerid = Integer.parseInt(sdkDetail.getApiPartnerID());
                                                } catch (NumberFormatException nfe) {
                                                    UtilMethods.INSTANCE.Error(getActivity(), "Invalid partner id its should be integer value");
                                                }

                                                try {
                                                    outletId = Integer.parseInt(sdkDetail.getApiOutletID());
                                                } catch (NumberFormatException nfe) {
                                                    UtilMethods.INSTANCE.Error(getActivity(), "Invalid outlet id its should be integer value");
                                                }

                                                String apiOutletPassword = sdkDetail.getApiOutletPassword();

                                                startActivityForResult(new Intent(getActivity(), EMoneyLoginActivity.class)
                                                        .putExtra(KeyConstant.USER_ID, partnerid)
                                                        .putExtra(KeyConstant.OUTLET_ID, outletId)
                                                        .putExtra(KeyConstant.PARTNER_ID, 0)
                                                        .putExtra(KeyConstant.PIN, apiOutletPassword)
                                                        .putExtra(KeyConstant.LATITUDE, UtilMethods.getLattitude)
                                                        .putExtra(KeyConstant.LONGITUDE, UtilMethods.getLongitude)
                                                        .putExtra(KeyConstant.SERVICE_TYPE, KeyConstant.MICRO_ATM), REQUEST_CODE_RDP_MINI_ATM);
                                            } else if (sdkType == 7 && mOnboardingResponse.getSdkDetail() != null) {
                                                mSdkDetail = mOnboardingResponse.getSdkDetail();
                                                handleMiniATMPaySprintSDK();
                                            } else {
                                                UtilMethods.INSTANCE.Error(getActivity(), "Merchant details is not found or SDK not integrated");
                                            }
                                        }
                                    }

                                }

                                @Override
                                public void onError(Object object) {

                                }

                                @Override
                                public void onOnBoarding(Object object) {

                                    if (object != null && object instanceof OnboardingResponse) {
                                        OnboardingResponse response = (OnboardingResponse) object;
                                        sdkType = response.getSdkType();
                                        boolean isAEPS = opTypes == 22;
                                        if (sdkType == 7 && response.getSdkDetail() != null) {
                                            mSdkDetail = response.getSdkDetail();
                                            getLocation(mSdkDetail, isAEPS);
                                        }
                                    }
                                }

                                private void MahagramMicroAtm(BcResponse bcDetail) {
                                    String saltKey, secretKey, mobileno, bcid, userId, cpid, emailId;
                                    try {
                                        mobileno = bcDetail.getMobileno();
                                        secretKey = bcDetail.getSecretKey();
                                        saltKey = bcDetail.getSaltKey();
                                        bcid = bcDetail.getBcid();
                                        userId = bcDetail.getUserId();
                                        cpid = bcDetail.getCpid() != null && bcDetail.getCpid().length() > 0 ? bcDetail.getCpid() : "";
                                        emailId = bcDetail.getEmailId();
                                        String clientUniqueId = "";
                                        try {
                                            clientUniqueId = UtilMethods.INSTANCE.getUniqueId(loginPrefResponse.getData().getUserID());
                                        } catch (Exception exception) {
                                            UtilMethods.INSTANCE.Error(getActivity(), getResources().getString(R.string.unique_not_found));
                                        }

                                        if (bcid != null && bcid.length() > 0) {
                                            PackageManager packageManager = getActivity().getPackageManager();
                                            if (UtilMethods.INSTANCE.isPackageInstalled("org.egram.microatm", packageManager)) {
                                                Intent intent = new Intent();
                                                intent.setComponent(new
                                                        ComponentName("org.egram.microatm", "org.egram.microatm.BluetoothMacSearchActivity"));
                                                intent.putExtra("saltkey", saltKey);
                                                intent.putExtra("secretkey", secretKey);
                                                intent.putExtra("bcid", bcid);
                                                intent.putExtra("userid", userId);
                                                intent.putExtra("bcemailid", emailId);
                                                intent.putExtra("phone1", mobileno);
                                                intent.putExtra("clientrefid", clientUniqueId);
                                                intent.putExtra("vendorid", "");
                                                intent.putExtra("udf1", "");
                                                intent.putExtra("udf2", "");
                                                intent.putExtra("udf3", "");
                                                intent.putExtra("udf4", "");
                                                startActivityForResult(intent, REQUEST_CODE_MAHAGRAM_MINI_ATM);

                                            } else {
                                                final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(getActivity());
                                                alertDialog.setTitle("Get Service");
                                                alertDialog.setMessage("MicroATM Service not installed. Click OK to download .");
                                                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        try {
                                                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=org.egram.microatm")));
                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                });
                                                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int i) {
                                                        dialog.dismiss();
                                                    }
                                                });
                                                alertDialog.show();
                                            }

                                        } else {
                                            UtilMethods.INSTANCE.Error(getActivity(), getResources().getString(R.string.microATMApproved));
                                        }
                                    } catch (Exception nfe) {
                                        UtilMethods.INSTANCE.Error(getActivity(), nfe.getMessage());
                                    }


                                }
                            });

                        });
                    }
                }
            } catch (Exception ex) {
                Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_SHORT).show();

            }

        } else {
            UtilMethods.INSTANCE.NetworkError(getActivity(), getResources().getString(R.string.err_msg_network_title), getResources().getString(R.string.err_msg_network));
        }
    }

    private void DashboardApi() {
        if (UtilMethods.INSTANCE.isNetworkAvialable(getActivity())) {
            try {
                hitDayReportApi();
                BannerApi();
                newsAPi();

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            UtilMethods.INSTANCE.NetworkError(getActivity(), getResources().getString(R.string.err_msg_network_title),
                    getResources().getString(R.string.err_msg_network));
        }
    }



    public int getAdWidth() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int adWidthPixels = displayMetrics.widthPixels;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowMetrics windowMetrics = requireActivity().getWindowManager().getCurrentWindowMetrics();
            adWidthPixels = windowMetrics.getBounds().width();
        }

        float density = displayMetrics.density;
        return (int) (adWidthPixels / density);
    }

    private void hitDayReportApi() {
        if (UtilMethods.INSTANCE.isNetworkAvialable(getActivity())) {

            String childNumber;
            if (userRoleId.equalsIgnoreCase("3") || userRoleId.equalsIgnoreCase("2")) {
                childNumber = "";
            } else {
                childNumber = loginPrefResponse.getData().getMobileNo();
            }
            if (loginPrefResponse.isTargetShow()) {
                UtilMethods.INSTANCE.UserAchieveTarget(getActivity(), true, null, loginPrefResponse,
                        new UtilMethods.ApiCallBackTwoMethod() {
                            @Override
                            public void onSucess(Object object) {

                                achieveTargetParse((AppUserListResponse) object);
                            }

                            @Override
                            public void onError(Object object) {
                                targetView.setVisibility(View.GONE);
                            }


                        });
            } else {
                targetView.setVisibility(View.GONE);
            }

            if (isLoaderShouldShow) {
                loader.show();
            }
            UtilMethods.INSTANCE.UserDayBook(getActivity(), childNumber, fromDate, toDate, isLoaderShouldShow ? loader : null,
                    new UtilMethods.ApiCallBackTwoMethod() {
                        @Override
                        public void onSucess(Object object) {

                            dataDayBookParse((AppUserListResponse) object);
                        }

                        @Override
                        public void onError(Object object) {

                        }


                    });

        } else {
            UtilMethods.INSTANCE.NetworkError(getActivity());
        }
    }

    private void achieveTargetParse(AppUserListResponse response) {
        try {
            if (response != null) {
                List<TargetAchieved> transactionsObjects = response.getTargetAchieveds();
                if (transactionsObjects != null && transactionsObjects.size() > 0) {
                    targetView.setVisibility(View.VISIBLE);
                    int remainigTarget = (int) (transactionsObjects.get(0).getTarget() - transactionsObjects.get(0).getTargetTillDate());
                    if (remainigTarget < 0) {
                        remainTargetTv.setTextColor(getResources().getColor(R.color.darkGreen));
                    }
                    remainTargetTv.setText(" Remain Target \n " + getString(R.string.rupiya) + " " + remainigTarget);
                    salesTargetTv.setText(" Sale Target \n " + getString(R.string.rupiya) + " " + (int) transactionsObjects.get(0).getTarget());
                    todaySalesTv.setText(" Today Sales \n " + getString(R.string.rupiya) + " " + (int) transactionsObjects.get(0).getTodaySale());
                } else {
                    targetView.setVisibility(View.GONE);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    private void dataDayBookParse(AppUserListResponse response) {

        if (response == null) {
            isLoaderShouldShow = true;
            return;
        }

        double successAmount = 0;
        double pendingAmount = 0;
        double failedAmount = 0;
        double commissionAmount = 0;

        int successHit = 0;
        int pendingHit = 0;
        int failedHit = 0;
        int commissionHit = 0;

        List<UserDaybookReport> list = response.getUserDaybookReport();

        if (list != null && !list.isEmpty()) {

            for (UserDaybookReport item : list) {

                // SUCCESS
                successAmount += Double.parseDouble(item.getSuccessAmount());
                successHit += Integer.parseInt(item.getSuccessHits());

                // COMMISSION
                if ("3".equalsIgnoreCase(userRoleId) ||
                        "2".equalsIgnoreCase(userRoleId)) {

                    commissionAmount += Double.parseDouble(item.getCommission());

                } else {

                    commissionAmount += item.getSelfCommission();
                }

                commissionHit += Integer.parseInt(item.getTotalHits());

                // PENDING
                pendingAmount += Double.parseDouble(item.getPendingAmount());
                pendingHit += Integer.parseInt(item.getPendingHits());

                // FAILED
                failedAmount += Double.parseDouble(item.getFailedAmount());
                failedHit += Integer.parseInt(item.getFailedHits());
            }
        }

        // SET TEXT

        successAmountTv.setText(
                UtilMethods.INSTANCE.formatedAmountWithRupees(
                        String.format("%.2f", successAmount)
                ) + " (" + successHit + ")"
        );

        failedAmountTv.setText(
                UtilMethods.INSTANCE.formatedAmountWithRupees(
                        String.format("%.2f", failedAmount)
                ) + " (" + failedHit + ")"
        );

        pendingAmountTv.setText(
                UtilMethods.INSTANCE.formatedAmountWithRupees(
                        String.format("%.2f", pendingAmount)
                ) + " (" + pendingHit + ")"
        );

        commissionAmountTv.setText(
                UtilMethods.INSTANCE.formatedAmountWithRupees(
                        String.format("%.2f", commissionAmount)
                ) + " (" + commissionHit + ")"
        );
    }

    private void getInitiateMiniATM(String txnAMT, final String vendorId, String remark, final String resCode) {
        final String userID;
        final String sessionID;
        final String session;
        final String appid;
        final String imei;
        final String regKey;
        final String version;
        final String serialNo;
        final String loginTypeID, outletID;
        final String[] apiRemark = {remark};
        final String[] txnID = new String[1];


        if (loginPrefResponse != null) {
            outletID = loginPrefResponse.getData().getOutletID();
            userID = loginPrefResponse.getData().getUserID();
            sessionID = loginPrefResponse.getData().getSessionID();
            session = loginPrefResponse.getData().getSession();
            appid = ApplicationConstant.INSTANCE.APP_ID;
            imei = UtilMethods.INSTANCE.getIMEI(getActivity());
            regKey = UtilMethods.INSTANCE.getFCMRegKey(getActivity());
            version = BuildConfig.VERSION_NAME;
            serialNo = UtilMethods.INSTANCE.getSerialNo(getActivity());
            loginTypeID = loginPrefResponse.getData().getLoginTypeID();

            InitiateMiniATMReq initiateMiniATMReq = new InitiateMiniATMReq(sdkType + "", opID + "", txnAMT, userID, sessionID, session, appid, imei, regKey, version, serialNo, loginTypeID);
            try {
                loader.show();
                EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
                Call<InitiateMiniBankATMRes> call = git.initiateMiniBank(initiateMiniATMReq);
                call.enqueue(new Callback<InitiateMiniBankATMRes>() {
                    @Override
                    public void onResponse(Call<InitiateMiniBankATMRes> call, retrofit2.Response<InitiateMiniBankATMRes> response) {

                        if (loader.isShowing())
                            loader.dismiss();
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 1) {
                                    InitiateMiniBankATMRes bankATMRes = response.body();
                                    txnID[0] = bankATMRes.getTid() != null ? bankATMRes.getTid() : "";
                                    //apiStatus[0] =bankATMRes.getStatuscode()+"";
                                    if (apiRemark[0] == null) {
                                        apiRemark[0] = "Success";
                                    }

                                    hitUpdateMiniBankApi();

                                } else {
                                    if (!response.body().isVersionValid()) {
                                        UtilMethods.INSTANCE.versionDialog(getActivity());
                                    } else {
                                        UtilMethods.INSTANCE.Error(getActivity(), response.body().getMsg());
                                    }
                                }

                            }
                        } else {
                            UtilMethods.INSTANCE.apiErrorHandle(getActivity(), response.code(), response.message());
                        }
                    }

                    private void hitUpdateMiniBankApi() {
                        try {
                            UpdateMiniBankStatusReq updateMiniBankStatusRequest = new UpdateMiniBankStatusReq(txnID[0], vendorId, resCode, apiRemark[0], userID, sessionID, session, appid, imei, regKey, version, serialNo, loginTypeID);
                            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
                            Call<InitiateMiniBankATMRes> call = git.updateMiniBankStatus(updateMiniBankStatusRequest);

                            call.enqueue(new Callback<InitiateMiniBankATMRes>() {

                                @Override
                                public void onResponse(Call<InitiateMiniBankATMRes> call, retrofit2.Response<InitiateMiniBankATMRes> response) {

                                    if (loader != null && loader.isShowing())
                                        loader.dismiss();

                                    try {
                                        if (response.body() != null) {
                                            if (response.body().getStatuscode() == 1) {


                                            } else if (response.body().getStatuscode() == -1) {
                                                if (!response.body().isVersionValid()) {
                                                    UtilMethods.INSTANCE.versionDialog(getActivity());
                                                } else {
                                                    UtilMethods.INSTANCE.Error(getActivity(), response.body().getMsg());
                                                }
                                            }

                                        }
                                    } catch (Exception error) {
                                        if (loader != null && loader.isShowing())
                                            loader.dismiss();

                                    }


                                }

                                @Override
                                public void onFailure(Call<InitiateMiniBankATMRes> call, Throwable error) {

                                    if (loader != null && loader.isShowing())
                                        loader.dismiss();
                                    UtilMethods.INSTANCE.apiFailureError(getActivity(), error);
                                }
                            });

                        } catch (Exception e) {
                            e.printStackTrace();
                            if (loader != null && loader.isShowing())
                                loader.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<InitiateMiniBankATMRes> call, Throwable t) {

                        if (loader.isShowing())
                            loader.dismiss();
                        UtilMethods.INSTANCE.apiFailureError(getActivity(), t);
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
                if (loader.isShowing())
                    loader.dismiss();
            }


        }

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((DashBoardLayout) context).mUpgradePackageCallBack = this;
        ((DashBoardLayout) context).refreshOpTypeCallBack = this;
    }

    @Override
    public void onUpgrade() {
        UtilMethods.INSTANCE.GetActiveService(getActivity(), new UtilMethods.ApiCallBackTwoMethod() {
            @Override
            public void onSucess(Object object) {
                if (object instanceof CustomAllTypeService) {
                    setDashboardData((CustomAllTypeService) object);
                }
            }

            @Override
            public void onError(Object object) {

            }
        });
    }

    @Override
    public void onRefresh(CustomAllTypeService customAllTypeService) {
        setDashboardData(customAllTypeService);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INTENT_CODE_MAHAGRAM_APES && resultCode == RESULT_OK) {
            if (resultCode == RESULT_OK) {
                String TransactionType = data.getStringExtra("TransactionType"); //to get transaction name
                data.getStringExtra("Response"); //to get response
                String s = data.getStringExtra("StatusCode"); //to get status code
                data.getStringExtra("Message"); //to get response message


                String response = data.getStringExtra("Response");

                JSONArray jsonarray = null;
                try {
                    jsonarray = new JSONArray(response);
                    for (int i = 0; i < jsonarray.length(); i++) {
                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                        String Message = jsonobject.getString("Message");
                        String StatusCode = jsonobject.getString("StatusCode");
                        String RRN = jsonobject.getString("RRN");
                        String CustNo = jsonobject.getString("CustNo");
                        String bankmessage = jsonobject.getString("bankmessage");
                        String Stan_no = jsonobject.getString("Stan_no");
                        String Balance_Details = "";
                        String Amount = "";

                        if (StatusCode.equalsIgnoreCase("001")) {
                            if (TransactionType.equalsIgnoreCase("BalanceInquiryActivity")) {
                                if (jsonobject.has("Balance_Details")) {
                                    Balance_Details = jsonobject.getString("Balance_Details");
                                    new AlertDialog.Builder(getActivity())
                                            .setTitle(Message)
                                            .setMessage("RRN :" + RRN + "\n" +
                                                    "CustNo :" + CustNo + "\n" +
                                                    "Balance :" + Balance_Details + "\n" +
                                                    "Stan_no :" + Stan_no + "\n" +
                                                    "bankmessage :" + bankmessage)
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    dialogInterface.dismiss();

                                                }
                                            })
                                            .show();
                                }
                            } else {
                                if (jsonobject.has("Amount")) {
                                    Amount = jsonobject.getString("Amount");
                                    new AlertDialog.Builder(getActivity())
                                            .setTitle(Message)
                                            .setMessage("RRN :" + RRN + "\n" +
                                                    "CustNo :" + CustNo + "\n" +
                                                    "Amount :" + Amount + "\n" +
                                                    "Stan_no :" + Stan_no + "\n" +
                                                    "bankmessage :" + bankmessage)
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    dialogInterface.dismiss();

                                                }
                                            })
                                            .show();
                                   /* new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                                            .setContentText("RRN :" + RRN + "\n" +
                                                    "CustNo :" + CustNo + "\n" +
                                                    "Amount :" + Amount + "\n" +
                                                    "Stan_no :" + Stan_no + "\n" +
                                                    "bankmessage :" + bankmessage)
                                            .setTitleText(Message)
                                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                    sweetAlertDialog.dismiss();
                                                }
                                            })
                                            .show();*/
                                }
                            }
                        } else if (StatusCode.equalsIgnoreCase("009")) {
                            new AlertDialog.Builder(getActivity())
                                    .setTitle(Message)
                                    .setMessage("RRN :" + RRN + "\n" +
                                            "CustNo :" + CustNo + "\n" +
                                            "Stan_no :" + Stan_no + "\n" +
                                            "bankmessage :" + bankmessage)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();

                                        }
                                    })
                                    .show();
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                data.getStringExtra("StatusCode"); //to get status code
                data.getStringExtra("Message"); //to get response message
            }
        } else if (requestCode == INTENT_UPGRADE_PACKAGE && resultCode == RESULT_OK) {
            onUpgrade();
        } else if (requestCode == REQUEST_CODE_ROUNDPAY_AEPS && resultCode == RESULT_OK) {
            boolean status = data.getBooleanExtra(KeyConstant.TRANS_STATUS, false);
            int txn_status = data.getIntExtra(KeyConstant.TXN_STATUS, 0);
            String response = data.getStringExtra(KeyConstant.MESSAGE);
            String bank_msg = data.getStringExtra(KeyConstant.BANK_MESSAGE);
            String transType = data.getStringExtra(KeyConstant.TRANS_TYPE);
            String aadharNum = data.getStringExtra(KeyConstant.AADHAR_NUM);
            String bankName = data.getStringExtra(KeyConstant.BANK_NAME);
            String stanNo = data.getStringExtra(KeyConstant.STAN_NO);
            String deviceName = data.getStringExtra(KeyConstant.DEVICE_NAME);
            String bankRrn = data.getStringExtra(KeyConstant.RRN);
            int error_code = data.getIntExtra(KeyConstant.ERROR_CODE, 0);
            String error_msg = data.getStringExtra(KeyConstant.ERROR_MSG);
            String status_code = data.getStringExtra(KeyConstant.STATUS_CODE);
            String bcName = data.getStringExtra(KeyConstant.BC_NAME);
            String bcCode = data.getStringExtra(KeyConstant.BC_CODE);
            String uidlcode = data.getStringExtra(KeyConstant.UIDAL_CODE);
            String bcLoc = data.getStringExtra(KeyConstant.BC_LOC);
            String customerNum = data.getStringExtra(KeyConstant.CUSTOMER_NUM);
            String time = data.getStringExtra(KeyConstant.TRANS_TIME);
            int type = data.getIntExtra(KeyConstant.TYPE, 0);
            double balAmount = data.getDoubleExtra(KeyConstant.BALANCE_AMOUNT, 0);

            if (status) {
                if (type == KeyConstant.CASH_WITHDRAWAL) {

                    String roundpayLiveId = data.getStringExtra(KeyConstant.RNP_LIVE_ID);
                    String roundpayTransactionId = data.getStringExtra(KeyConstant.RNP_TRANS_ID);
                    String transId = data.getStringExtra(KeyConstant.TRANS_ID);
                    double transAmount = data.getDoubleExtra(KeyConstant.TRANS_AMOUNT, 0);

                    Intent intent = new Intent(getActivity(), AepsCashWithDrawlAtivity.class);
                    intent.putExtra("time", time);
                    intent.putExtra("rnpLiveId", roundpayLiveId);
                    intent.putExtra("rnpTransactionId", roundpayTransactionId);
                    intent.putExtra("transId", transId);
                    intent.putExtra("transAmount", transAmount);
                    intent.putExtra("balAmount", balAmount);
                    intent.putExtra("bankMsg", bank_msg);
                    intent.putExtra("customerNum", customerNum);
                    intent.putExtra("bcLoc", bcLoc);
                    intent.putExtra("bcCode", bcCode);
                    intent.putExtra("bcName", bcName);
                    intent.putExtra("bankRrn", bankRrn);
                    intent.putExtra("deviceName", deviceName);
                    intent.putExtra("bankName", bankName);
                    intent.putExtra("aadharNum", aadharNum);
                    intent.putExtra("stanNo", stanNo);
                    intent.putExtra("transType", transType);
                    intent.putExtra("uidlcode", uidlcode);
                    intent.putExtra("txn_status", txn_status);
                    startActivity(intent);


                } else if (type == KeyConstant.MINI_STATEMENT) {
                    miniStatements = (ArrayList<MiniStatements>) data.getSerializableExtra(KeyConstant.MINI_STATEMENT_LIST);
                    if (miniStatements != null && miniStatements.size() > 0) {
                        Intent intent = new Intent(getActivity(), MiniStatementActivity.class);
                        intent.putExtra("MINI_STATEMENT", miniStatements);
                        startActivity(intent);
                    }
                } else if (type == KeyConstant.BALANCE_ENQUIRY) {

                    startActivity(new Intent(getActivity(), AEPSBalanceActivity.class)
                            .putExtra("balAmount", balAmount)
                            .putExtra("bankName", bankName));
                }

            } else {
                UtilMethods.INSTANCE.Failed(getActivity(), error_code + "\n" + error_msg);
            }
        } else if (requestCode == REQUEST_CODE_RDP_MINI_ATM && resultCode == RESULT_OK) {
            boolean status = data.getBooleanExtra(KeyConstant.TRANS_STATUS, false);
            String response = data.getStringExtra(KeyConstant.MESSAGE);
            double transAmount = data.getDoubleExtra(KeyConstant.TRANS_AMOUNT, 0);
            double balAmount = data.getDoubleExtra(KeyConstant.BALANCE_AMOUNT, 0);
            String bankRrn = data.getStringExtra(KeyConstant.RRN);
            String transType = data.getStringExtra(KeyConstant.TRANS_TYPE);
            int type = data.getIntExtra(KeyConstant.TYPE, 0);
            String cardNum = data.getStringExtra(KeyConstant.CARD_NUM);
            String bankName = data.getStringExtra(KeyConstant.BANK_NAME);
            String cardType = data.getStringExtra(KeyConstant.CARD_TYPE);
            String terminalId = data.getStringExtra(KeyConstant.TERMINAL_ID);
            String fpId = data.getStringExtra(KeyConstant.FP_TRANS_ID);
            String transId = data.getStringExtra(KeyConstant.TRANS_ID);
            int error_code = data.getIntExtra(KeyConstant.ERROR_CODE, 0);
            String error_msg = data.getStringExtra(KeyConstant.ERROR_MSG);

            if (status) {
                Intent reportActivity = new Intent(getActivity(), SlipActivityMicroAtmReport.class);
                reportActivity.putExtra("requesttxn", transType);
                reportActivity.putExtra("msg", response);
                reportActivity.putExtra("refstan", "");
                reportActivity.putExtra("cardno", cardNum);
                reportActivity.putExtra("date", "");
                reportActivity.putExtra("amount", "" + balAmount);
                reportActivity.putExtra("invoicenumber", "");
                reportActivity.putExtra("mid", "");
                reportActivity.putExtra("tid", transId);
                reportActivity.putExtra("clientrefid", "");
                reportActivity.putExtra("vendorid", "");
                reportActivity.putExtra("udf1", "");
                reportActivity.putExtra("udf2", "");
                reportActivity.putExtra("udf3", "");
                reportActivity.putExtra("udf4", "");
                reportActivity.putExtra("txnamount", "" + transAmount);
                reportActivity.putExtra("rrn", bankRrn);

                reportActivity.putExtra("bankName", bankName);
                reportActivity.putExtra("cardType", cardType);
                reportActivity.putExtra("terminalId", terminalId);
                reportActivity.putExtra("fpId", fpId);
                reportActivity.putExtra("type", type + "");
                getActivity().startActivity(reportActivity);


            } else {
                UtilMethods.INSTANCE.Failed(getActivity(), error_code + "\n" + error_msg);
            }
        } else if (requestCode == REQUEST_CODE_MAHAGRAM_MINI_ATM) {
            if (resultCode == RESULT_OK) {
                final String requestTxn, refStan, txnAmount, mID, tID, clientRefID, vendorID, udf1, udf2, udf3, udf4, date, bankRemarks, cardNo, amount, invoiceNumber, rrn;
                final String respCode = data.getStringExtra("respcode");
                requestTxn = data.getStringExtra("requesttxn ");//Type of transaction
                refStan = data.getStringExtra("refstan");// Mahagram Stan Number
                txnAmount = data.getStringExtra("txnamount");//Transaction amount (0 in case of balance inquiry and transaction amount in cash withdrawal)
                mID = data.getStringExtra("mid");//Mid
                tID = data.getStringExtra("tid");//Tid
                clientRefID = data.getStringExtra("clientrefid");//Your reference Id
                vendorID = data.getStringExtra("vendorid");//Your define value
                udf1 = data.getStringExtra("udf1");//Your define value
                udf2 = data.getStringExtra("udf2");//Your define value
                udf3 = data.getStringExtra("udf3");//Your define value
                udf4 = data.getStringExtra("udf4");//Your define value
                date = data.getStringExtra("date");//Date of transaction
                cardNo = data.getStringExtra("cardno");//Atm card number
                invoiceNumber = data.getStringExtra("invoicenumber");//Invoice Number
                rrn = data.getStringExtra("rrn");//Bank RRN number
                bankRemarks = data.getStringExtra("msg");//Bank Message
                amount = data.getStringExtra("amount");//Account Balance

                Intent reportActivity = new Intent(getActivity(), SlipActivityMicroAtmReport.class);
                reportActivity.putExtra("requesttxn", requestTxn);
                reportActivity.putExtra("msg", bankRemarks);
                reportActivity.putExtra("refstan", refStan);
                reportActivity.putExtra("cardno", cardNo);
                reportActivity.putExtra("date", date);
                reportActivity.putExtra("amount", amount);
                reportActivity.putExtra("invoicenumber", invoiceNumber);
                reportActivity.putExtra("mid", mID);
                reportActivity.putExtra("tid", tID);
                reportActivity.putExtra("clientrefid", clientRefID);
                reportActivity.putExtra("vendorid", vendorID);
                reportActivity.putExtra("udf1", udf1);
                reportActivity.putExtra("udf2", udf2);
                reportActivity.putExtra("udf3", udf3);
                reportActivity.putExtra("udf4", udf4);
                reportActivity.putExtra("txnamount", txnAmount);
                reportActivity.putExtra("rrn", rrn);
                reportActivity.putExtra("bankName", "");
                reportActivity.putExtra("cardType", "");
                reportActivity.putExtra("terminalId", "");
                reportActivity.putExtra("fpId", "");
                reportActivity.putExtra("type", 1);

                if (respCode != null && respCode.equals("00")) //Success
                {
                    aPIStatus = "2";
                    getActivity().startActivity(reportActivity);
                } else if (respCode != null && respCode.equals("999")) //Pending
                {
                    aPIStatus = "1";
                    getActivity().startActivity(reportActivity);
                } else {
                    aPIStatus = "3";
                    getActivity().startActivity(reportActivity);
                }

                // Calling Service From here...
                final Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        getInitiateMiniATM(txnAmount, refStan, bankRemarks, aPIStatus);

                    }
                }, 1000);


            } else {
                data.getStringExtra("statuscode"); //to get status code
                data.getStringExtra("message"); //to get response message
                if (data.getStringExtra("statuscode").equals("111")) {
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse("https://play.google.com/store/apps/details?id=org.egram.microatm")));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    UtilMethods.INSTANCE.Error(getActivity(), data.getStringExtra("message"));
                }
                aPIStatus = "1";
                final String bankRemarks = data.getStringExtra("message");
                final String refStan = data.getStringExtra("refstan"); // Mahagram Stan Number
                final String txnAmount = data.getStringExtra("txnamount");

                // Calling Service From here...
                final Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something after 100ms
                        getInitiateMiniATM(txnAmount != null ? txnAmount : "0", refStan != null ? refStan : "NA", bankRemarks, aPIStatus);
                    }
                }, 1000);

            }
        } else if (requestCode == INTENT_MINI_ATM_PAYS_PRINT) {
            if (resultCode == RESULT_OK && data != null) {

                boolean status = data.getBooleanExtra("status", false);
                int status_code = data.getIntExtra("response", 0);
                String status_msg = data.getStringExtra("message");

                String msg = "Status " + status + " Status code " + status_code + " Message " + status_msg;


                if (status && status_msg != null) {
                    handleMiniATMPaySprintSDK();
                }
                if (!status && status_msg != null) {
                    UtilMethods.INSTANCE.Error(getActivity(), "StatusCode - " + status_code + "\nMessage- " + status_msg);
                } else {
                    UtilMethods.INSTANCE.Error(getActivity(), "Canceled");
                }
            } else {
                UtilMethods.INSTANCE.Error(getActivity(), "Failed");
            }
        } else if (requestCode == INTENT_AEPS_PAYS_PRINT) {
            if (resultCode == RESULT_OK && data != null) {

                boolean status = data.getBooleanExtra("status", false);
                int status_code = data.getIntExtra("response", 0);
                String status_msg = data.getStringExtra("message");
                String msg = "Status " + status + " Status code " + status_code + " Message " + status_msg;
                if (status && status_msg != null) {
                    handleAepsPaySprintSDK();
                }
                if (!status && status_msg != null) {
                    UtilMethods.INSTANCE.Error(getActivity(), "StatusCode - " + status_code + "\nMessage- " + status_msg);
                }
                if (mGetLocation != null) {
                    mGetLocation.onActivityResult(requestCode, resultCode, data);
                } else {
                    UtilMethods.INSTANCE.Error(getActivity(), "Canceled");
                }
            } else {
                UtilMethods.INSTANCE.Error(getActivity(), "Failed");
            }
        }
    }


    @Override
    public void onClick(AssignedOpType mAssignedOpType, ArrayList<AssignedOpType> subOpTypeList) {
        if (subOpTypeList != null && subOpTypeList.size() > 0) {
            customAlertDialog.serviceListDialog(mAssignedOpType.getService(), subOpTypeList, new CustomAlertDialog.DialogServiceListCallBack() {
                @Override
                public void onIconClick(AssignedOpType mAssignedOpType) {
                    if (UtilMethods.getLattitude > 0 && UtilMethods.getLongitude > 0) {
                        openNewScreen(mAssignedOpType);
                    } else {
                        if (mGetLocation != null) {
                            mGetLocation = new GetLocation(getActivity(), loader);
                        }
                        mGetLocation.startLocationUpdates((lattitude, longitude) -> {
                            UtilMethods.getLattitude = lattitude;
                            UtilMethods.getLongitude = longitude;
                            openNewScreen(mAssignedOpType);
                        });
                    }
                }
            });
        } else {
            if (UtilMethods.getLattitude > 0 && UtilMethods.getLongitude > 0) {
                openNewScreen(mAssignedOpType);
            } else {
                if (mGetLocation != null) {
                    mGetLocation = new GetLocation(getActivity(), loader);
                }
                mGetLocation.startLocationUpdates((lattitude, longitude) -> {
                    UtilMethods.getLattitude = lattitude;
                    UtilMethods.getLongitude = longitude;
                    openNewScreen(mAssignedOpType);
                });
            }
        }
    }

    @Override
    public void onPackageUpgradeClick() {
        startActivityForResult(new Intent(getActivity(), UpgradePackageActivity.class)
                .putExtra("UID", loginPrefResponse.getData().getUserID())
                .putExtra("BENE_NAME", loginPrefResponse.getData().getName() + " (" + loginPrefResponse.getData().getRoleName() + ")")
                .putExtra("BENE_MOBILE", loginPrefResponse.getData().getMobileNo())
                .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), INTENT_UPGRADE_PACKAGE);
    }
/*    public void destroyBanner() {
        if (adView != null) {
            View parentView = (View) adView.getParent();
            if (parentView instanceof ViewGroup) {
                ((ViewGroup) parentView).removeView(adView);
            }
            adView.destroy();
        }
        adView = null;
    }*/

    @Override
    public void onStop() {
        super.onStop();

    }
}
