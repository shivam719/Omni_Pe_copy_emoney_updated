package com.fintech.omnipe.GetAdvertisement.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.activity.ComponentActivity;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.fintech.omnipe.Api.Response.BasicResponse;
import com.fintech.omnipe.Api.Response.Promotion;
import com.fintech.omnipe.Auth.dto.LoginResponse;
import com.fintech.omnipe.GetAdvertisement.adapter.GetAdvertisementByCityAdapter;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.UtilMethods;
import com.fintech.omnipe.usefull.CustomLoader;

import java.util.ArrayList;
import java.util.List;

public class GetAdvertisementByCity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CustomLoader loader;
    private LoginResponse mLoginDataResponse;
    private List<Promotion> promotionList=new ArrayList<>();
    private GetAdvertisementByCityAdapter mAdapter;
    private AppCompatEditText search_all;
    private AppCompatImageView clearIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable((ComponentActivity) this);
        setContentView(R.layout.activity_get_advertisement_by_city);
        mLoginDataResponse = new Gson().fromJson(UtilMethods.INSTANCE.getLoginPref(this), LoginResponse.class);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        loader.setCancelable(false);
        loader.setCanceledOnTouchOutside(false);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Advertisement");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        search_all = findViewById(R.id.search_all);
        clearIcon = findViewById(R.id.clearIcon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        clearIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_all.setText("");
            }
        });
        search_all.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence newText, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    clearIcon.setVisibility(View.VISIBLE);
                } else {
                    clearIcon.setVisibility(View.GONE);
                }
                mAdapter.getFilter().filter(s);
            }
        });
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        hitApi();

    }

    private void hitApi() {
        if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {
            loader.show();
            UtilMethods.INSTANCE.GetAdvertisementByCityApi(this, mLoginDataResponse, loader, new UtilMethods.ApiResponseCallBack() {
                @Override
                public void onSuccess(Object object) {
                    if(object instanceof BasicResponse){
                        BasicResponse response=(BasicResponse)object;
                        if(response.getPromotionlist() != null && !response.getPromotionlist().isEmpty()){
                            promotionList=response.getPromotionlist();
                            mAdapter = new GetAdvertisementByCityAdapter(promotionList,GetAdvertisementByCity.this);
                            recyclerView.setAdapter(mAdapter);
                        }else{
                            UtilMethods.INSTANCE.Error(GetAdvertisementByCity.this, "No Record Found !");
                        }
                    }
                }

                @Override
                public void onError(int error) {
                    UtilMethods.INSTANCE.NetworkError(GetAdvertisementByCity.this);
                }
            });
        }else{
            UtilMethods.INSTANCE.NetworkError(GetAdvertisementByCity.this);
        }
    }
}