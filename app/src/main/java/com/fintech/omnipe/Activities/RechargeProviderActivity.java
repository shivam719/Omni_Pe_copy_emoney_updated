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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.fintech.omnipe.DTHSubscription.Activity.DTHSubscriptionActivity;
import com.google.gson.Gson;
import com.fintech.omnipe.Api.Response.NumberListResponse;
import com.fintech.omnipe.Fragments.Adapter.SelectProviderAdapter;
import com.fintech.omnipe.Fragments.dto.OperatorList;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.ApplicationConstant;

import java.util.ArrayList;

public class RechargeProviderActivity extends AppCompatActivity {

    //Declare Native Adds...
    private static String LOG_TAG = "EXAMPLE";
    RecyclerView rcSelectProvider;
    SelectProviderAdapter mAdapter;
    NumberListResponse NumberList = new NumberListResponse();
    ArrayList<OperatorList> operatorArray = new ArrayList<>();
    String from = "DTH";
    int fromId;
    LinearLayout llComingSoon;
    boolean fromPhoneRecharge;
    private Intent clickIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_recharge_provider);
        getId();
    }

    private void getId() {
        rcSelectProvider = findViewById(R.id.rc_select_provider);
        llComingSoon = findViewById(R.id.ll_coming_soon);
        // Set toolbar icon in ...
        Toolbar toolBar = findViewById(R.id.toolbar);
        toolBar.setTitle("Select Provider");
        toolBar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolBar);
        toolBar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        fromPhoneRecharge = getIntent().getBooleanExtra("fromPhoneRecharge", false);
        from = getIntent().getExtras().getString("from");
        fromId = getIntent().getExtras().getInt("fromId");
        Log.e("from", from);

        getOperator(fromId);

        if (operatorArray != null && operatorArray.size() > 0) {
            llComingSoon.setVisibility(View.GONE);
            mAdapter = new SelectProviderAdapter(operatorArray, this);
            rcSelectProvider.setLayoutManager(new GridLayoutManager(this, 2));
            rcSelectProvider.setItemAnimator(new DefaultItemAnimator());
            rcSelectProvider.setAdapter(mAdapter);
        } else {
            llComingSoon.setVisibility(View.VISIBLE);
        }

    }

    private void getOperator(int op_Type) {
        SharedPreferences prefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String response = prefs.getString(ApplicationConstant.INSTANCE.numberListPref, "");
        Gson gson = new Gson();
        NumberList = gson.fromJson(response, NumberListResponse.class);
        for (OperatorList op : NumberList.getData().getOperators()) {
            if (op.getOpType()==op_Type && op.isActive()) {
                operatorArray.add(op);
            }

        }
    }

    public void ItemClick(OperatorList operator) {

        openRechargeScreen(operator);
    }

    private void openRechargeScreen(OperatorList operator)
    {
        if (fromPhoneRecharge) {
             clickIntent = new Intent();
            clickIntent.putExtra("selected", operator.getName());
            clickIntent.putExtra("selectedId", operator.getOid());
            clickIntent.putExtra("Max", operator.getMax());
            clickIntent.putExtra("Min", operator.getMin());
            clickIntent.putExtra("from", from);
            clickIntent.putExtra("Length", operator.getLength());
            clickIntent.putExtra("MaxLength", operator.getLengthMax());
            clickIntent.putExtra("IsAccountNumeric", operator.isAccountNumeric());
            clickIntent.putExtra("IsPartial", operator.isPartial());
            clickIntent.putExtra("BBPS", operator.isBBPS());
            clickIntent.putExtra("AccountName", operator.getAccountName());
            clickIntent.putExtra("AccountRemark", operator.getAccountRemak());
            clickIntent.putExtra("Icon", operator.getImage());
            clickIntent.putExtra("isBilling", operator.isBilling());
            clickIntent.putExtra("StartWith", operator.getStartWith());
            setResult(RESULT_OK, clickIntent);
            finish();
        } else {

            if (fromId == 35 || fromId == 36) {
                clickIntent = new Intent(RechargeProviderActivity.this, DTHSubscriptionActivity.class);
            }
            else {
                clickIntent = new Intent(RechargeProviderActivity.this, SecondRechargeActivity.class);
            }
            clickIntent.putExtra("operatorList", operator);
            clickIntent.putExtra("from",from);
            clickIntent.putExtra("fromId",fromId);
            startActivity(clickIntent);
        }

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
                Log.e("query", newText);
                //  mAdapter.filter(newText);
                newText = newText.toLowerCase();
                ArrayList<OperatorList> newlist = new ArrayList<>();
                for (OperatorList op : operatorArray) {
                    String getName = op.getName().toLowerCase();
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
