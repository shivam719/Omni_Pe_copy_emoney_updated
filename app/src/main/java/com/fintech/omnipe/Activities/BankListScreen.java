package com.fintech.omnipe.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.fintech.omnipe.Activities.Adapter.BankListScreenAdapter;
import com.fintech.omnipe.Api.Object.BankListObject;
import com.fintech.omnipe.Api.Response.BankListResponse;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.ApplicationConstant;

import java.util.ArrayList;




public class BankListScreen extends AppCompatActivity {

    RecyclerView recycler_view;
    TextView noData;
    BankListScreenAdapter mAdapter;
    ArrayList<BankListObject> operator = new ArrayList<>();
    BankListResponse operatorList = new BankListResponse();
    Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_list_screen);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_icon);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Bank List");

        recycler_view = findViewById(R.id.recycler_view);
        noData = findViewById(R.id.noData);

        getOperatorList();

        if (operator != null && operator.size() > 0) {
            noData.setVisibility(View.GONE);

            mAdapter = new BankListScreenAdapter(operator, BankListScreen.this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recycler_view.setLayoutManager(mLayoutManager);
            recycler_view.setItemAnimator(new DefaultItemAnimator());
            recycler_view.setAdapter(mAdapter);

        } else {
            noData.setVisibility(View.VISIBLE);
        }
    }

    public void getOperatorList() {

        SharedPreferences prefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String response = prefs.getString(ApplicationConstant.INSTANCE.bankListPref, null);

        Gson gson = new Gson();
        operatorList = gson.fromJson(response, BankListResponse.class);
        operator = operatorList.getBankMasters();

    }

    public void ItemClick(BankListObject operator) {
        Intent clickIntent = new Intent();
        clickIntent.putExtra("bankName", operator.getBankName());
        clickIntent.putExtra("bankId", operator.getId());
        clickIntent.putExtra("accVerification", operator.getIsACVerification());
        clickIntent.putExtra("shortCode", operator.getCode());
        clickIntent.putExtra("ifsc", operator.getIfsc());
        clickIntent.putExtra("neft", operator.getIsNEFT());
        clickIntent.putExtra("imps", operator.getIsIMPS());
        clickIntent.putExtra("accLmt", operator.getAccountLimit());
        clickIntent.putExtra("ekO_BankID", operator.getEkO_BankID());
        setResult(4, clickIntent);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        if (searchMenuItem == null) {
            return true;
        }

        SearchView searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setQueryHint("Search");
        searchMenuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                }
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
                }
                return true;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                Log.e("query", query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                newText = newText.toLowerCase();
                ArrayList<BankListObject> newlist = new ArrayList<>();
                for (BankListObject op : operator) {
                    String getName = op.getBankName().toLowerCase();
                    if (getName.contains(newText)) {
                        newlist.add(op);
                    }
                }
                mAdapter.filter(newlist);
                return true;
            }
        });

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}

/*
public class BankListScreen extends AppCompatActivity {

    RecyclerView recycler_view;
    TextView noData;
    BankListScreenAdapter mAdapter;
    ArrayList<BankListObject> operator = new ArrayList<>();
    BankListResponse operatorList = new BankListResponse();
    Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_screen);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Bank List");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        noData = (TextView) findViewById(R.id.noData);

        getOperatorList();

        if (operator != null && operator.size() > 0) {
            noData.setVisibility(View.GONE);

            mAdapter = new BankListScreenAdapter(operator, BankListScreen.this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recycler_view.setLayoutManager(mLayoutManager);
            recycler_view.setItemAnimator(new DefaultItemAnimator());
            recycler_view.setAdapter(mAdapter);

        } else {
            noData.setVisibility(View.VISIBLE);
        }
    }

    public void getOperatorList() {

        SharedPreferences prefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String response = prefs.getString(ApplicationConstant.INSTANCE.bankListPref, null);

        Gson gson = new Gson();
        operatorList = gson.fromJson(response, BankListResponse.class);
        operator = operatorList.getBanks();
    }

    public void ItemClick(String id, String name, String accVerification, String shortCode) {
        Intent clickIntent = new Intent();
        clickIntent.putExtra("bankName", name);
        clickIntent.putExtra("bankId", id);
        clickIntent.putExtra("accVerification", accVerification);
        clickIntent.putExtra("shortCode", shortCode);
        setResult(1, clickIntent);
        finish();
    }

}
*/
