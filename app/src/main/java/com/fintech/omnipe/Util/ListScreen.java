package com.fintech.omnipe.Util;

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
import com.fintech.omnipe.Api.Response.NumberListResponse;
import com.fintech.omnipe.Fragments.Adapter.ListScreenAdapter;
import com.fintech.omnipe.Fragments.dto.OperatorList;
import com.fintech.omnipe.R;

import java.util.ArrayList;



public class ListScreen extends AppCompatActivity {

    RecyclerView recycler_view;
    TextView noData;
    ListScreenAdapter mAdapter;
    NumberListResponse NumberList = new NumberListResponse();
    ArrayList<OperatorList> operatorArray = new ArrayList<>();
    Toolbar toolbar;
    String opType, from;

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_list_screen);

        from = getIntent().getExtras().getString("from");
        opType = getIntent().getExtras().getString("opType");

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Select Operator");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        recycler_view = findViewById(R.id.recycler_view);
        noData = findViewById(R.id.noData);

        getOperatorList();

        if (operatorArray != null && operatorArray.size() > 0) {
            noData.setVisibility(View.GONE);
            mAdapter = new ListScreenAdapter(operatorArray, ListScreen.this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recycler_view.setLayoutManager(mLayoutManager);
            recycler_view.setItemAnimator(new DefaultItemAnimator());
            recycler_view.setAdapter(mAdapter);

        } else {
            noData.setVisibility(View.VISIBLE);
        }


    }

    public void getOperatorList() {

        if (from.equalsIgnoreCase("mobile")) {
            if (opType.equals("1"))
                getOperator(1);
            else if (opType.equals("2"))
                getOperator(2);

        } else if (from.equalsIgnoreCase("dth")) {
            getOperator(3);
        } else if (from.equalsIgnoreCase("landline")) {
            getOperator(4);
        } else if (from.equalsIgnoreCase("electricity")) {
            getOperator(5);
        } else if (from.equalsIgnoreCase("insurance")) {
            //  getOperator("2");
        } else if (from.equalsIgnoreCase("gas")) {
            getOperator(6);
        } else {

        }

    }

    private void getOperator(int op_Type) {
        SharedPreferences prefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String response = prefs.getString(ApplicationConstant.INSTANCE.numberListPref, "");
        Gson gson = new Gson();
        NumberList = gson.fromJson(response, NumberListResponse.class);
        for (OperatorList op : NumberList.getData().getOperators()) {
            if (op.getOpType()==op_Type) {
                operatorArray.add(op);
            }
        }
    }

    public void ItemClick(String name, int id, String Icon, double Max, double Min,
                          int length, int maxLength, boolean isAccountNumeric, String startwith, boolean isPartial, String AccountName, String AccountRemark) {
        Intent clickIntent = new Intent();
        clickIntent.putExtra("selected", name);
        clickIntent.putExtra("selectedId", id);
        clickIntent.putExtra("startwith", startwith);
        clickIntent.putExtra("length", length);
        clickIntent.putExtra("maxLength", maxLength);
        clickIntent.putExtra("IsAccountNumeric", isAccountNumeric);
        clickIntent.putExtra("Min", Min);
        clickIntent.putExtra("Max", Max);
        clickIntent.putExtra("Icon", Icon);
        clickIntent.putExtra("isPartial", isPartial);
        clickIntent.putExtra("AccountName", AccountName);
        clickIntent.putExtra("AccountRemark", AccountRemark);
        setResult(1, clickIntent);
        finish();
    }

}
