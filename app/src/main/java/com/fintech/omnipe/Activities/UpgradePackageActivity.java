package com.fintech.omnipe.Activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.fintech.omnipe.Activities.Adapter.UpgradePackageAdapter;
import com.fintech.omnipe.Api.Object.PackageDetails;
import com.fintech.omnipe.Api.Response.GetAvailablePackageResponse;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.UtilMethods;
import com.fintech.omnipe.usefull.CustomLoader;

import java.util.ArrayList;

public class UpgradePackageActivity extends AppCompatActivity {


    RecyclerView mRecyclerView;
    private CustomLoader loader;
    private String uid;
    ArrayList<PackageDetails> mPackageDetails = new ArrayList<>();
    private UpgradePackageAdapter mUpgradePackageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_upgrade_package);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitle("Upgrade Package");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        TextView beneName=findViewById(R.id.beneName);
        TextView beneMobile=findViewById(R.id.beneMobile);
        beneName.setText(getIntent().getStringExtra("BENE_NAME")+"");
        beneMobile.setText(getIntent().getStringExtra("BENE_MOBILE")+"");

        uid = getIntent().getStringExtra("UID");
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        hitApi();
    }


    void hitApi() {
        loader.show();
        UtilMethods.INSTANCE.GetAvailablePackage(this, loader, new UtilMethods.ApiCallBack() {
            @Override
            public void onSucess(Object object) {
                GetAvailablePackageResponse mGetAvailablePackageResponse = (GetAvailablePackageResponse) object;
                mPackageDetails = mGetAvailablePackageResponse.getPackageDetail();
                mUpgradePackageAdapter = new UpgradePackageAdapter(UpgradePackageActivity.this, mPackageDetails);
                mRecyclerView.setAdapter(mUpgradePackageAdapter);
            }
        });
    }

    public void upgradePackage(PackageDetails operator, final int pos) {
        loader.show();
        UtilMethods.INSTANCE.UpgradePackage(this, uid, operator.getPackageId(), loader, new UtilMethods.ApiCallBack() {
            @Override
            public void onSucess(Object object) {
                mPackageDetails.get(pos).setDefault(true);
                mUpgradePackageAdapter.notifyDataSetChanged();
                setResult(RESULT_OK);
            }
        });
    }
}
