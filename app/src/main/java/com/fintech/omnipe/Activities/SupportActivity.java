package com.fintech.omnipe.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fintech.omnipe.Activities.Adapter.SupportDataListAdapter;
import com.fintech.omnipe.Api.Object.SupportDataItem;
import com.fintech.omnipe.Api.Request.BasicRequest;
import com.fintech.omnipe.Api.Response.AppBannerResponse;
import com.fintech.omnipe.AppUser.WebViewUrlActivity;
import com.fintech.omnipe.Auth.dto.LoginResponse;
import com.fintech.omnipe.Fragments.Adapter.CustomPagerAdapter;
import com.fintech.omnipe.Util.ApiClient;
import com.fintech.omnipe.Util.ApplicationConstant;
import com.fintech.omnipe.Util.EndPointInterface;
import com.fintech.omnipe.usefull.CustomLoader;
import com.google.gson.Gson;
import com.fintech.omnipe.Api.Response.AppUserListResponse;
import com.fintech.omnipe.BuildConfig;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.UtilMethods;

import java.lang.reflect.Method;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class SupportActivity extends AppCompatActivity {

    private CustomLoader loader;
    private View Mobiletollfree, DTHtollfree, bankView, ll_contactus,inviteReferralView,pagerContainer;
    private TextView currentVersion,inviteReferTv;
    private ImageView Share, playstorelink;
    private ViewPager mViewPager;
    private Handler handler;
    private Integer mDotsCount;
    private LinearLayout dotsCount;
    public  TextView[] mDotsText;
    private Runnable mRunnable;
    private LinearLayout customerCareView, custCareWhatsAppView;
    private LinearLayout custCareCallUsView;
    private LinearLayout custCareEmailView;
    private LinearLayout paymentInqueryView;
    private LinearLayout paymentInqueryCallUsView;
    private LinearLayout paymentInqueryEmailView, paymentInqueryWhatsAppView;
    private LinearLayout facebookView;
    private TextView facebbokLink;
    private LinearLayout instagramView;
    private TextView instaLink;
    private LinearLayout twitterView;
    private TextView twitterLink;
    private LinearLayout addressView;
    private TextView address;
    private LinearLayout websiteView;
    private TextView website;
    RecyclerView customerCareNumberRecyclerView, customerCareWhatsappRecyclerView, customerCareEmailRecyclerView, paymentInqueryNumberRecyclerView, paymentInqueryWhatsappRecyclerView, paymentInqueryEmailRecyclerView;
    private String websiteLink, fbLink, instagramLink, twiLink;
    private LoginResponse loginPrefResponse;
    private String userRoleId,referralContent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_support);
        UtilMethods.INSTANCE.setDashboardStatus(this, false);
        loginPrefResponse = new Gson().fromJson(getSharedPreferences(ApplicationConstant.INSTANCE.prefNameLoginPref, MODE_PRIVATE).getString(ApplicationConstant.INSTANCE.LoginPref, ""), LoginResponse.class);
        userRoleId=loginPrefResponse.getData().getRoleID();
        getIds();
        setContactData(new Gson().fromJson(UtilMethods.INSTANCE.getCompanyProfile(this), AppUserListResponse.class));
        getDetail();
    }

    private void getIds() {
        Mobiletollfree = findViewById(R.id.Mobiletollfree);
        playstorelink = findViewById(R.id.playstorelink);
        DTHtollfree = findViewById(R.id.DTHtollfree);
        bankView = findViewById(R.id.bankView);
        ll_contactus = findViewById(R.id.ll_contactus);
        currentVersion = findViewById(R.id.currentVersion);
        inviteReferTv = findViewById(R.id.inviteReferTv);
        mViewPager =findViewById(R.id.pager);
        pagerContainer =findViewById(R.id.pagerContainer);
        dotsCount =findViewById(R.id.image_count);
        inviteReferralView = findViewById(R.id.inviteReferralView);
        currentVersion.setText("Current Version : " + BuildConfig.VERSION_NAME);

        findViewById(R.id.term_and_privacy_txt).setOnClickListener(v -> {
            Intent intent = new Intent(SupportActivity.this, WebViewUrlActivity.class);
            intent.putExtra("url", ApplicationConstant.INSTANCE.privacyUrl);
            intent.putExtra("title", "Privacy Policy");
            startActivity(intent);
        });
       findViewById(R.id.term_txt).setOnClickListener(v -> {
           Intent intent = new Intent(SupportActivity.this, WebViewUrlActivity.class);
           intent.putExtra("url", ApplicationConstant.INSTANCE.termsUrl);
           intent.putExtra("title", "Terms and Conditions");
           startActivity(intent);
        });

        Share = findViewById(R.id.Share);

        customerCareNumberRecyclerView = findViewById(R.id.customerCareNumberRecyclerView);
        customerCareNumberRecyclerView.setLayoutManager(new LinearLayoutManager(SupportActivity.this));


        customerCareWhatsappRecyclerView = findViewById(R.id.customerCareWhatsappRecyclerView);
        customerCareWhatsappRecyclerView.setLayoutManager(new LinearLayoutManager(SupportActivity.this));

        customerCareEmailRecyclerView = findViewById(R.id.customerCareEmailRecyclerView);
        customerCareEmailRecyclerView.setLayoutManager(new LinearLayoutManager(SupportActivity.this));

        paymentInqueryNumberRecyclerView = findViewById(R.id.paymentInqueryNumberRecyclerView);
        paymentInqueryNumberRecyclerView.setLayoutManager(new LinearLayoutManager(SupportActivity.this));

        paymentInqueryWhatsappRecyclerView = findViewById(R.id.paymentInqueryWhatsappRecyclerView);
        paymentInqueryWhatsappRecyclerView.setLayoutManager(new LinearLayoutManager(SupportActivity.this));

        paymentInqueryEmailRecyclerView = findViewById(R.id.paymentInqueryEmailRecyclerView);
        paymentInqueryEmailRecyclerView.setLayoutManager(new LinearLayoutManager(SupportActivity.this));

        customerCareView = findViewById(R.id.customerCareView);
        custCareWhatsAppView = findViewById(R.id.custCareWhatsAppView);
        custCareCallUsView = findViewById(R.id.custCareCallUsView);
        custCareEmailView = findViewById(R.id.custCareEmailView);
        paymentInqueryView = findViewById(R.id.paymentInqueryView);
        paymentInqueryCallUsView = findViewById(R.id.paymentInqueryCallUsView);
        paymentInqueryEmailView = findViewById(R.id.paymentInqueryEmailView);
        paymentInqueryWhatsAppView = findViewById(R.id.paymentInqueryWhatsAppView);
        facebookView = findViewById(R.id.facebookView);
        facebbokLink = findViewById(R.id.facebbokLink);
        instagramView = findViewById(R.id.instagramView);
        instaLink = findViewById(R.id.instaLink);
        twitterView = findViewById(R.id.twitterView);
        twitterLink = findViewById(R.id.twitterLink);
        addressView = findViewById(R.id.addressView);
        address = findViewById(R.id.address);
        websiteView = findViewById(R.id.websiteView);
        website = findViewById(R.id.website);

        if(UtilMethods.INSTANCE.getIsReferral(this))
        {
            loader = new CustomLoader(SupportActivity.this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
            loader.show();
            loader.setCancelable(false);
            ReferralBannerApi();

        }
        else
            inviteReferralView.setVisibility(View.GONE);

        findViewById(R.id.inviteBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareIt();
               /* Intent intent = new Intent(SupportActivity.this, ShareActivity.class);
                intent.putExtra("referralContent",referralContent);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                SupportActivity.this.startActivity(intent);*/
            }
        });
        websiteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                browseLink(websiteLink);
            }
        });
        twitterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                browseLink(twiLink);
            }
        });
        instagramView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                browseLink(instagramLink);
            }
        });
        facebookView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                browseLink(fbLink);
            }
        });
        Mobiletollfree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SupportActivity.this, DthSupportActivity.class);
                i.putExtra("From", "Prepaid");
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);


            }
        });
        Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareIt();
            }
        });
        playstorelink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + BuildConfig.APPLICATION_ID)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID)));
                }
            }
        });
        DTHtollfree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SupportActivity.this, DthSupportActivity.class);
                i.putExtra("From", "DTH");
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
            }
        });
        bankView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SupportActivity.this, BankDetailActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
            }
        });
    }

    private void browseLink(String mLink) {
        try {
            Intent dialIntent = new Intent(Intent.ACTION_VIEW);
            dialIntent.setData(Uri.parse(mLink + ""));
            startActivity(dialIntent);
        } catch (Exception e) {
            Intent dialIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mLink + ""));
            startActivity(dialIntent);
        }
    }

    private void getDetail() {
        if (UtilMethods.INSTANCE.isNetworkAvialable(SupportActivity.this)) {
            try {
                UtilMethods.INSTANCE.GetCompanyProfile(SupportActivity.this, new UtilMethods.ApiCallBack() {
                    @Override
                    public void onSucess(Object object) {
                        AppUserListResponse data = (AppUserListResponse) object;
                        setContactData(data);
                    }
                });


            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            UtilMethods.INSTANCE.NetworkError(SupportActivity.this, getResources().getString(R.string.err_msg_network_title),
                    getResources().getString(R.string.err_msg_network));
        }
    }

    private ArrayList<SupportDataItem> getListData(int type, String editableData) {
        int icon = R.drawable.ic_arrow_right;
        if (type == 1) {
            icon = R.drawable.ic_smartphone;
        }
        if (type == 2) {
            icon = R.drawable.ic_telephone;
        }
        if (type == 3) {
            icon = R.drawable.ic_whatsapp;
        }
        if (type == 4) {
            icon = R.drawable.ic_email;
        }
        ArrayList<SupportDataItem> mSupportDataItems = new ArrayList<>();
        if (editableData.contains(",")) {
            String[] emailArray = editableData.split(",");
            if (emailArray.length > 0) {
                for (String value : emailArray) {
                    mSupportDataItems.add(new SupportDataItem(type, value.trim(), icon));
                }
            }
        } else {
            mSupportDataItems.add(new SupportDataItem(type, editableData.trim(), icon));
        }
        return mSupportDataItems;
    }

    private void setContactData(AppUserListResponse mContactData) {
        if (mContactData != null && mContactData.getCompanyProfile() != null) {
            ll_contactus.setVisibility(View.VISIBLE);
            ArrayList<SupportDataItem> mSupportDataCustomerCallItems = new ArrayList<>();

            if (mContactData.getCompanyProfile().getCustomerCareMobileNos() != null && !mContactData.getCompanyProfile().getCustomerCareMobileNos().isEmpty()) {
                mSupportDataCustomerCallItems.addAll(getListData(1, mContactData.getCompanyProfile().getCustomerCareMobileNos()));
            }

            if (mContactData.getCompanyProfile().getCustomerPhoneNos() != null && !mContactData.getCompanyProfile().getCustomerPhoneNos().isEmpty()) {
                mSupportDataCustomerCallItems.addAll(getListData(2, mContactData.getCompanyProfile().getCustomerPhoneNos()));
            }

            if (mSupportDataCustomerCallItems.size() > 0) {
                customerCareView.setVisibility(View.VISIBLE);
                custCareCallUsView.setVisibility(View.VISIBLE);
                SupportDataListAdapter mSupportDataListAdapter = new SupportDataListAdapter(SupportActivity.this, mSupportDataCustomerCallItems);
                customerCareNumberRecyclerView.setAdapter(mSupportDataListAdapter);
            }


            if (mContactData.getCompanyProfile().getCustomerWhatsAppNos() != null && !mContactData.getCompanyProfile().getCustomerWhatsAppNos().isEmpty()) {
                customerCareView.setVisibility(View.VISIBLE);
                custCareWhatsAppView.setVisibility(View.VISIBLE);

                ArrayList<SupportDataItem> mSupportDataItems = getListData(3, mContactData.getCompanyProfile().getCustomerWhatsAppNos());
                if (mSupportDataItems.size() > 0) {
                    SupportDataListAdapter mSupportDataListAdapter = new SupportDataListAdapter(SupportActivity.this, mSupportDataItems);
                    customerCareWhatsappRecyclerView.setAdapter(mSupportDataListAdapter);
                }

            }

            if (mContactData.getCompanyProfile().getCustomerCareEmailIds() != null && !mContactData.getCompanyProfile().getCustomerCareEmailIds().isEmpty()) {
                customerCareView.setVisibility(View.VISIBLE);
                custCareEmailView.setVisibility(View.VISIBLE);

                ArrayList<SupportDataItem> mSupportDataItems = getListData(4, mContactData.getCompanyProfile().getCustomerCareEmailIds());
                if (mSupportDataItems.size() > 0) {
                    SupportDataListAdapter mSupportDataListAdapter = new SupportDataListAdapter(SupportActivity.this, mSupportDataItems);
                    customerCareEmailRecyclerView.setAdapter(mSupportDataListAdapter);
                }
            }

            ArrayList<SupportDataItem> mSupportDataAccountCallItems = new ArrayList<>();
            if (mContactData.getCompanyProfile().getAccountMobileNo() != null && !mContactData.getCompanyProfile().getAccountMobileNo().isEmpty()) {
                mSupportDataAccountCallItems.addAll(getListData(1, mContactData.getCompanyProfile().getAccountMobileNo()));
            }

            if (mContactData.getCompanyProfile().getAccountPhoneNos() != null && !mContactData.getCompanyProfile().getAccountPhoneNos().isEmpty()) {
                mSupportDataAccountCallItems.addAll(getListData(2, mContactData.getCompanyProfile().getAccountPhoneNos()));
            }

            if (mSupportDataAccountCallItems.size() > 0) {
                paymentInqueryView.setVisibility(View.VISIBLE);
                paymentInqueryCallUsView.setVisibility(View.VISIBLE);
                SupportDataListAdapter mSupportDataListAdapter = new SupportDataListAdapter(SupportActivity.this, mSupportDataAccountCallItems);
                paymentInqueryNumberRecyclerView.setAdapter(mSupportDataListAdapter);
            }


            if (mContactData.getCompanyProfile().getAccountWhatsAppNos() != null && !mContactData.getCompanyProfile().getAccountWhatsAppNos().isEmpty()) {
                paymentInqueryView.setVisibility(View.VISIBLE);
                paymentInqueryWhatsAppView.setVisibility(View.VISIBLE);

                ArrayList<SupportDataItem> mSupportDataItems = getListData(3, mContactData.getCompanyProfile().getAccountWhatsAppNos());
                if (mSupportDataItems.size() > 0) {
                    SupportDataListAdapter mSupportDataListAdapter = new SupportDataListAdapter(SupportActivity.this, mSupportDataItems);
                    paymentInqueryWhatsappRecyclerView.setAdapter(mSupportDataListAdapter);
                }

            }
            if (mContactData.getCompanyProfile().getAccountEmailId() != null && !mContactData.getCompanyProfile().getAccountEmailId().isEmpty()) {
                paymentInqueryView.setVisibility(View.VISIBLE);
                paymentInqueryEmailView.setVisibility(View.VISIBLE);

                ArrayList<SupportDataItem> mSupportDataItems = getListData(4, mContactData.getCompanyProfile().getAccountEmailId());
                if (mSupportDataItems.size() > 0) {
                    SupportDataListAdapter mSupportDataListAdapter = new SupportDataListAdapter(SupportActivity.this, mSupportDataItems);
                    paymentInqueryEmailRecyclerView.setAdapter(mSupportDataListAdapter);
                }

            }

            if (mContactData.getCompanyProfile().getAddress() != null && !mContactData.getCompanyProfile().getAddress().isEmpty()) {
                addressView.setVisibility(View.VISIBLE);
                address.setText(Html.fromHtml(mContactData.getCompanyProfile().getAddress()));
            }

            if (mContactData.getCompanyProfile().getWebsite() != null && !mContactData.getCompanyProfile().getWebsite().isEmpty()) {
                websiteView.setVisibility(View.VISIBLE);
                websiteLink = mContactData.getCompanyProfile().getWebsite();
                UtilMethods.INSTANCE.setTextViewHTML(SupportActivity.this, website, "<a href=" + mContactData.getCompanyProfile().getWebsite() + ">Open Website</a>");
            }
            if (mContactData.getCompanyProfile().getFacebook() != null && !mContactData.getCompanyProfile().getFacebook().isEmpty()) {
                facebookView.setVisibility(View.VISIBLE);
                fbLink = mContactData.getCompanyProfile().getFacebook();
                UtilMethods.INSTANCE.setTextViewHTML(SupportActivity.this, facebbokLink, "<a href=" + mContactData.getCompanyProfile().getFacebook() + ">Follow Us</a>");
            }
            if (mContactData.getCompanyProfile().getTwitter() != null && !mContactData.getCompanyProfile().getTwitter().isEmpty()) {
                twitterView.setVisibility(View.VISIBLE);
                twiLink = mContactData.getCompanyProfile().getTwitter();
                UtilMethods.INSTANCE.setTextViewHTML(SupportActivity.this, twitterLink, "<a href=" + mContactData.getCompanyProfile().getTwitter() + ">Follow Us</a>");
            }
            if (mContactData.getCompanyProfile().getInstagram() != null && !mContactData.getCompanyProfile().getInstagram().isEmpty()) {
                instagramView.setVisibility(View.VISIBLE);
                instagramLink = mContactData.getCompanyProfile().getInstagram();
                UtilMethods.INSTANCE.setTextViewHTML(SupportActivity.this, instaLink, "<a href=" + mContactData.getCompanyProfile().getInstagram() + ">Follow Us</a>");
            }
        }
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
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
                String shareMessage = "";

                if (UtilMethods.INSTANCE.getCompanyProfileDetails(this) != null) {
                    shareMessage = UtilMethods.INSTANCE.getCompanyProfileDetails(this).getCompanyProfile().getAddress() + "\n\nLet me recommend you this application\n\n";
                }
                shareMessage = shareMessage + ApplicationConstant.INSTANCE.inviteUrl + loginPrefResponse.getData().getUserID() + "\n\n";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "Choose one"));



            } else {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
                String shareMessage = "";

                if (UtilMethods.INSTANCE.getCompanyProfileDetails(this) != null) {
                    shareMessage = UtilMethods.INSTANCE.getCompanyProfileDetails(this).getCompanyProfile().getAddress() + "\n\nLet me recommend you this application\n\n";
                }


                shareMessage = shareMessage + ApplicationConstant.INSTANCE.inviteUrl + loginPrefResponse.getData().getUserID() + "\n\n";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "choose one"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ReferralBannerApi() {
        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<AppBannerResponse> call = git.GetAppRefferalContent(new BasicRequest(
                    loginPrefResponse.getData().getUserID(), loginPrefResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    UtilMethods.INSTANCE.getIMEI(SupportActivity.this),
                    "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(SupportActivity.this), loginPrefResponse.getData().getSessionID(), loginPrefResponse.getData().getSession()));

            call.enqueue(new Callback<AppBannerResponse>() {
                @Override
                public void onResponse(Call<AppBannerResponse> call, final retrofit2.Response<AppBannerResponse> response) {
                         if(loader!=null && loader.isShowing())
                             loader.dismiss();
                    try {
                        if (response.isSuccessful() && response.body() != null && response.body().getStatuscode() == 1) {

                            inviteReferTv.setText(Html.fromHtml(UtilMethods.INSTANCE.formatedShareContent(response.body().getRefferalContent())));

                            inviteReferralView.setVisibility(View.VISIBLE);

                            if (response.body().getRefferalImage() != null && response.body().getRefferalImage().size() > 0) {
                                pagerContainer.setVisibility(View.VISIBLE);
                                CustomPagerAdapter mCustomPagerAdapter = new CustomPagerAdapter(response.body().getRefferalImage(), SupportActivity.this,2);
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
                                    mDotsText[i] = new TextView(SupportActivity.this);
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
                        if(loader!=null && loader.isShowing())
                            loader.dismiss();
                        inviteReferralView.setVisibility(View.GONE);
                    }

                }

                @Override
                public void onFailure(Call<AppBannerResponse> call, Throwable t) {
                    if(loader!=null && loader.isShowing())
                        loader.dismiss();
                    inviteReferralView.setVisibility(View.GONE);

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if(loader!=null && loader.isShowing())
                loader.dismiss();
            inviteReferralView.setVisibility(View.GONE);
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

    @Override
    public void onDestroy() {
        if (handler != null && mRunnable != null) {
            handler.removeCallbacks(mRunnable);
        }
        super.onDestroy();
    }

}
