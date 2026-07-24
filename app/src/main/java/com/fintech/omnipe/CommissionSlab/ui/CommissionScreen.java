package com.fintech.omnipe.CommissionSlab.ui;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import com.fintech.omnipe.Api.Object.SlabDetailDisplayLvl;
import com.fintech.omnipe.Api.Response.SlabCommissionResponse;
import com.fintech.omnipe.Api.Response.SlabCommtObject;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.ActivityActivityMessage;
import com.fintech.omnipe.Util.GlobalBus;
import com.fintech.omnipe.Util.UtilMethods;
import com.fintech.omnipe.usefull.CustomLoader;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;


public class CommissionScreen extends AppCompatActivity  implements View.OnClickListener {

    CustomLoader loader;
    RecyclerView recycler_view;
    CommissionAdapter mAdapter;
    String response = "";
    LinearLayoutManager mLayoutManager;
    ArrayList<SlabDetailDisplayLvl> transactionsObjects = new ArrayList<>();
    ArrayList<SlabCommtObject> transactionsNewObjects = new ArrayList<>();

    SlabCommissionResponse transactions = new SlabCommissionResponse();
    SearchView searchView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.commission_report_layout);

        loader = new CustomLoader(CommissionScreen.this, android.R.style.Theme_Translucent_NoTitleBar);

        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        searchView =  findViewById(R.id.search_all);
        searchView.setIconified(false);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitle("Commission Slab");
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if(UtilMethods.INSTANCE.getCommList(this)!=null&&!UtilMethods.INSTANCE.getCommList(this).isEmpty())
        {
            Gson gson = new Gson();
            transactions = gson.fromJson(UtilMethods.INSTANCE.getCommList(this), SlabCommissionResponse.class);
            dataParse(transactions);

        }
        HitApi();

    }

    private void HitApi() {
        if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {

            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);

            UtilMethods.INSTANCE.MyCommission(this, loader, new UtilMethods.ApiCallBack() {
                @Override
                public void onSucess(Object object) {
                    SlabCommissionResponse data=(SlabCommissionResponse)object;
                    if(data!=null)
                    {
                        dataParse(data);

                    }
                }
            });

        } else {
            UtilMethods.INSTANCE.NetworkError(this, getResources().getString(R.string.err_msg_network_title),
                    getResources().getString(R.string.err_msg_network));
        }
    }

    public void dataParse(SlabCommissionResponse response) {

        transactionsObjects = response.getSlabDetailDisplayLvl();//////////////////
        if (transactionsObjects != null && transactionsObjects.size() > 0) {
            mAdapter = new CommissionAdapter(transactionsObjects, this);
            mLayoutManager = new LinearLayoutManager(this);
            recycler_view.setLayoutManager(mLayoutManager);
            recycler_view.setItemAnimator(new DefaultItemAnimator());
            recycler_view.setAdapter(mAdapter);
            recycler_view.setVisibility(View.VISIBLE);

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    // TODO Auto-generated method stub

                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    // TODO Auto-generated method stub
                    mAdapter.filter(newText);
                    return false;
                }
            });
        } else {
            recycler_view.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            GlobalBus.getBus().register(this);
        }
    }

    @Override
    public void onDestroy() {
        // Unregister the registered event.
        GlobalBus.getBus().unregister(this);
        /*if (mAdView != null) {
            mAdView.destroy();
        }*/
        super.onDestroy();
    }

    @Subscribe
    public void onActivityActivityMessage(ActivityActivityMessage activityFragmentMessage) {

    }


}

