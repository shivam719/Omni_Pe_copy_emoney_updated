package com.fintech.omnipe.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.fintech.omnipe.Activities.Adapter.SelectZoneOptionAdapter;
import com.fintech.omnipe.Api.Object.NumberList;
import com.fintech.omnipe.Api.Response.CircleList;
import com.fintech.omnipe.Api.Response.NumberListResponse;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.ApplicationConstant;

import java.util.ArrayList;

public class SelectZoneOption extends AppCompatActivity {

    RecyclerView recycler_view;
    TextView noData;
    SelectZoneOptionAdapter mAdapter;
    ArrayList<CircleList> operator = new ArrayList<>();
    ArrayList<NumberList> operatorSelection = new ArrayList<>();
    ArrayList<String> rechargeType = new ArrayList<>();
    Toolbar toolbar;
    String from = "";
    String operatorId = "";
    String opListName = "";
    String opListNameCode = "";
    int opList = 0;

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.plan_option_layout);

        from = getIntent().getExtras().getString("from");
        operatorId = getIntent().getExtras().getString("operatorId");
        opList = getIntent().getExtras().getInt("opList");
        if (opList == 1)
            opListName = getIntent().getExtras().getString("opListName");

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if (from.equalsIgnoreCase("operator"))
            getSupportActionBar().setTitle("Operator's");
        else
            getSupportActionBar().setTitle("Select Circle");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        recycler_view = findViewById(R.id.recycler_view);
        noData = findViewById(R.id.noData);

        SharedPreferences prefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String response = prefs.getString(ApplicationConstant.INSTANCE.numberListPref, null);
        NumberListResponse numberListResponse = new Gson().fromJson(response, NumberListResponse.class);
        operator = numberListResponse.getData().getCirlces();

        if (operator != null && operator.size() > 0) {
            noData.setVisibility(View.GONE);

            mAdapter = new SelectZoneOptionAdapter(operator, SelectZoneOption.this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recycler_view.setLayoutManager(mLayoutManager);
            recycler_view.setItemAnimator(new DefaultItemAnimator());
            recycler_view.setAdapter(mAdapter);

        } else {
            noData.setVisibility(View.VISIBLE);
        }
    }

    public void ItemClick(String name, String id, String circle) {
        Intent clickIntent = new Intent();
        clickIntent.putExtra("selectedCircleName", circle);
        clickIntent.putExtra("selectedCircleId", id);
        setResult(3, clickIntent);
        finish();
    }

}

