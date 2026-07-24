package com.fintech.omnipe.DTHSubscription.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.fintech.omnipe.DTHSubscription.Adapter.DthChannelAdapter;
import com.fintech.omnipe.DTHSubscription.dto.DthChannels;
import com.fintech.omnipe.DTHSubscription.dto.DthPackage;
import com.fintech.omnipe.DTHSubscription.dto.GetDthPackageResponse;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.RecyclerViewStickyHeader.HeaderItemDecoration;
import com.fintech.omnipe.Util.UtilMethods;
import com.fintech.omnipe.usefull.CustomLoader;

import java.util.ArrayList;

public class DthChannelActivity extends AppCompatActivity {

    CustomLoader loader;
    public TextView validity;
    public TextView amount, name;
    public TextView description, bookingAmt;
    RecyclerView recycler_view;
    private EditText searchView;
    View noData;
    private DthPackage mDthPackage;
    private DthChannelAdapter mAdapter;
    ArrayList<DthChannels> channelsArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_dth_channel);
        Toolbar toolBar = findViewById(R.id.toolbar);
        toolBar.setTitle("DTH Channel");
        toolBar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolBar);
        toolBar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                DthChannelActivity.this.onBackPressed();
            }
        });
        loader = loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);

        mDthPackage = (DthPackage) getIntent().getSerializableExtra("DTH_PACKAGE");
        searchView = (EditText) findViewById(R.id.search_all);
        amount = findViewById(R.id.amount);
        description = findViewById(R.id.description);
        validity = findViewById(R.id.validity);
        name = findViewById(R.id.name);
        bookingAmt = findViewById(R.id.bookingAmt);
        recycler_view = findViewById(R.id.recycler_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recycler_view.setLayoutManager(gridLayoutManager);


        noData = findViewById(R.id.noData);
        description.setText(mDthPackage.getDescription() + "");
        name.setText(mDthPackage.getPackageName() + "");
        validity.setText(mDthPackage.getValidity() + " Days");
        bookingAmt.setText("Booking Amount : " + getString(R.string.rupiya) + " " + UtilMethods.INSTANCE.formatedAmount(mDthPackage.getBookingAmount() + ""));

        amount.setText(getString(R.string.rupiya) + " " + UtilMethods.INSTANCE.formatedAmount(mDthPackage.getPackageMRP() + ""));

        findViewById(R.id.clearIcon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setText("");
            }
        });

        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {


                if (mAdapter != null && mAdapter.getItemViewType(position) == DthChannelAdapter.Header) {
                    return 2;
                }

                return 1;

            }
        });
        getDthPackage();
    }


    void getDthPackage() {
        UtilMethods.INSTANCE.GetDthChannel(DthChannelActivity.this, mDthPackage.getId() + "", mDthPackage.getOid() + "", loader, object -> {
            GetDthPackageResponse mGetDthPackageResponse = (GetDthPackageResponse) object;
            dataParse(mGetDthPackageResponse);
        });
    }

    public void dataParse(GetDthPackageResponse response) {


        if (response != null) {
            channelsArrayList = response.getDthChannels();

            if (channelsArrayList != null && channelsArrayList.size() > 0) {

                int categoryId = 0;
                for (int i = 0; i < channelsArrayList.size(); i++) {
                    if (categoryId != channelsArrayList.get(i).getCategoryID()) {

                        channelsArrayList.add(i, new DthChannels(channelsArrayList.get(i).getCategoryName(), channelsArrayList.get(i).getId(),
                                channelsArrayList.get(i).getChannelName(), channelsArrayList.get(i).getCategoryID(), channelsArrayList.get(i).getCategoryName(),
                                channelsArrayList.get(i).getCategories(), channelsArrayList.get(i).isDel(), channelsArrayList.get(i).isActive()));

                        categoryId = channelsArrayList.get(i).getCategoryID();
                    }
                }

                mAdapter = new DthChannelAdapter(channelsArrayList, this);
                recycler_view.setAdapter(mAdapter);
                new HeaderItemDecoration(mAdapter);
                noData.setVisibility(View.GONE);
                recycler_view.setVisibility(View.VISIBLE);
                searchView.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        mAdapter.filter(s.toString());
                    }
                });

            } else {
                noData.setVisibility(View.VISIBLE);
                recycler_view.setVisibility(View.GONE);
            }
        } else {
            noData.setVisibility(View.VISIBLE);
            recycler_view.setVisibility(View.GONE);
        }

    }

}
