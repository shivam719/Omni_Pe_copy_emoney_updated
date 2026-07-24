package com.fintech.omnipe.MoveToWallet.UI;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fintech.omnipe.Activities.Adapter.BalanceTypeAdapter;
import com.fintech.omnipe.Activities.Adapter.CommissionChargeDetailAdapter;
import com.fintech.omnipe.Activities.Adapter.MoveToWalletAdapter;
import com.fintech.omnipe.Api.Object.BalanceType;
import com.fintech.omnipe.Api.Object.WalletType;
import com.fintech.omnipe.Api.Response.NumberListResponse;
import com.fintech.omnipe.Api.Response.WalletTypeResponse;
import com.fintech.omnipe.CommissionSlab.dto.RSlabRangDetailResponse;
import com.fintech.omnipe.CommissionSlab.dto.SlabRangeDetail;
import com.fintech.omnipe.Fragments.dto.OperatorList;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.UtilMethods;
import com.fintech.omnipe.usefull.CustomLoader;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MoveToWalletActivity extends AppCompatActivity {

    Spinner  spinTransactionMode, spinSelectSource, spinSelectDestination;
    RecyclerView walletBalance;
    View cv_TransactionMode;
    public AppCompatTextView viewChargesTv,tv_select_mode;
    CustomLoader loader;
    EditText amount;
    Button submit;
    String actiontype = "1";
    String TransMode = "NEFT";
    private int OID;
    Map<String, OperatorList> hmForTransactionMode = new HashMap<>();
    private String transactionCode = "";
    ArrayList<OperatorList> transactionModesList;
    ArrayList<WalletType> moveToArrayListSelectSource = new ArrayList<>();
    HashMap<Integer, ArrayList<WalletType>> mMapDestObject = new HashMap<>();
    private WalletTypeResponse walletTypeResponse;
    private List<WalletType> walletTypesList = new ArrayList<>();
    private int mtwid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_move_to_wallet);

        setToolbar();
        getIds();
        gettingMoveToDataselectsource();
        adaptDataInRecyclerView(walletBalance, getIntent().getExtras().getString("items", ""));
        setListeners();
    }

    private void gettingMoveToDataselectsource() {

        String responseWalletType = UtilMethods.INSTANCE.getWalletType(this);
        if (responseWalletType != null) {
            walletTypeResponse = new Gson().fromJson(responseWalletType, WalletTypeResponse.class);
            if (walletTypeResponse != null) {
                walletTypesList = walletTypeResponse.getMoveToWalletMappings();
                if (walletTypesList != null && walletTypesList.size() > 0) {
                    ArrayList<WalletType> moveToSelectDestinationStrList = new ArrayList<>();
                    ArrayList<Integer> addedFromId = new ArrayList<>();
                    for (WalletType item : walletTypesList) {
                        if (!addedFromId.contains(item.getFromWalletID())) {
                            moveToArrayListSelectSource.add(item);
                            addedFromId.add(item.getFromWalletID());
                            moveToSelectDestinationStrList = new ArrayList<>();
                        }

                        moveToSelectDestinationStrList.add(item);
                        mMapDestObject.put(item.getFromWalletID(), moveToSelectDestinationStrList);

                    }
                    adaptDataInSpinner(spinSelectSource, moveToArrayListSelectSource, true);

                }
            }
        }


    }


    private void setListeners() {

        viewChargesTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hitCommissionApi();
            }
        });

        spinSelectSource.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                WalletType clickedItem = (WalletType) parent.getItemAtPosition(position);
                int fromWalletId = clickedItem.getFromWalletID();
                if (mMapDestObject != null && mMapDestObject.containsKey(fromWalletId)) {
                    adaptDataInSpinner(spinSelectDestination, mMapDestObject.get(fromWalletId), false);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

        spinSelectDestination.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Toast.makeText(MoveToWalletActivity.this, "destination", Toast.LENGTH_SHORT).show();
                WalletType clickedItem = (WalletType) parent.getItemAtPosition(position);
                mtwid = clickedItem.getId();

                if (clickedItem.getToWalletID()==3){
                    gettingTransactionModeData();
                }else {
                    cv_TransactionMode.setVisibility(View.GONE);
                    viewChargesTv.setVisibility(View.GONE);
                    tv_select_mode.setVisibility(View.GONE);
                    transactionCode = "";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //   Toast.makeText(MoveToWalletActivity.this, "222", Toast.LENGTH_SHORT).show();

            }
        });

        spinTransactionMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TransMode = spinTransactionMode.getSelectedItem().toString();
                if (TransMode != null && !TransMode.isEmpty() && hmForTransactionMode != null) {

                    displayModeData(hmForTransactionMode.get(TransMode));
                }else{
                    OID=0;
                }

            }

            private void displayModeData(OperatorList modeList) {
                OID=modeList.getOid();
                //  tv_maxMinCharge.setText("Min Amount : " + min + " Rs." + "\n" + "Max Amount : " + max + " Rs." + "\n" + "Minimum Charges for " + "'" + TransMode + "'" + " is " + charge + " Rs.");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(amount.getText().toString().isEmpty())
                {
                    amount.setError(getResources().getString(R.string.err_empty_field));
                    amount.requestFocus();
                    return;
                }

                if (UtilMethods.INSTANCE.isNetworkAvialable(MoveToWalletActivity.this)) {
                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(false);
                    UtilMethods.INSTANCE.MoveToWallet(MoveToWalletActivity.this, actiontype, transactionCode, amount.getText().toString(), mtwid, OID,loader);
                } else {
                    UtilMethods.INSTANCE.NetworkError(MoveToWalletActivity.this, getResources().getString(R.string.err_msg_network_title), getResources().getString(R.string.err_msg_network));
                }

            }
        });
    }

    private void adaptDataInRecyclerView(RecyclerView recyclerView, String string) {
        Gson gson = new Gson();
        ArrayList<BalanceType> balanceTypes = gson.fromJson(string, new TypeToken<ArrayList<BalanceType>>() {
        }.getType());

        if (balanceTypes != null && balanceTypes.size() > 0) {
            BalanceTypeAdapter mAdapter = new BalanceTypeAdapter(balanceTypes, MoveToWalletActivity.this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
        }
    }

    public void gettingTransactionModeData() {
        try {
            NumberListResponse listResponse= new Gson().fromJson(UtilMethods.INSTANCE.getNumberList(MoveToWalletActivity.this), NumberListResponse.class);
            transactionModesList=listResponse.getData().getOperators();
            if (transactionModesList != null && transactionModesList.size() > 0) {
                ArrayList<String> arrayTranMode = new ArrayList<>();
                for (OperatorList operatorList: transactionModesList) {
                    if(operatorList.getOpType()==42){
                        arrayTranMode.add(operatorList.getName());
                        hmForTransactionMode.put(operatorList.getName(), operatorList);
                    }
                }
                if(arrayTranMode!=null && arrayTranMode.size()>0){
                    adaptDataInSpinner(spinTransactionMode, arrayTranMode);
                    cv_TransactionMode.setVisibility(View.VISIBLE);
                    viewChargesTv.setVisibility(View.VISIBLE);
                }


            }
        }catch (Exception exception){

        }


    }

    public void adaptDataInSpinner(Spinner spinner, ArrayList<WalletType> mWalletType, boolean isSource) {

        MoveToWalletAdapter arrayAdapter = new MoveToWalletAdapter(this, mWalletType, isSource);
        spinner.setAdapter(arrayAdapter);
    }


    public void adaptDataInSpinner(Spinner spinner, ArrayList<String> stringArray) {
        ArrayAdapter arrayAdapter= new ArrayAdapter(this, android.R.layout.simple_spinner_item, stringArray);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
    }

    private void getIds() {

        spinSelectSource = findViewById(R.id.spin_select_source);
        spinSelectDestination = findViewById(R.id.spin_select_destination);
        spinTransactionMode = findViewById(R.id.spin_TransactionMode);

        walletBalance = findViewById(R.id.walletBalance);
        cv_TransactionMode = findViewById(R.id.cv_TransactionMode);
        cv_TransactionMode.setVisibility(View.GONE);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        amount =findViewById(R.id.amount);
        submit =  findViewById(R.id.bt_moveWallet);
        viewChargesTv = findViewById(R.id.viewChargesTv);
        tv_select_mode = findViewById(R.id.tv_select_mode);
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitle("Move To Wallet");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void hitCommissionApi() {

        if(UtilMethods.INSTANCE.isNetworkAvialable(this)){
            loader.show();
            UtilMethods.INSTANCE.RealTimeCommission(this, loader, new UtilMethods.ApiCallBack() {
                @Override
                public void onSucess(Object object) {
                    if(object!=null && object instanceof RSlabRangDetailResponse){
                        RSlabRangDetailResponse slabRangDetailResponse=(RSlabRangDetailResponse)object;
                        if(slabRangDetailResponse!=null && slabRangDetailResponse.getData()!=null && slabRangDetailResponse.getData().size()>0){
                            RealCommissionDialogShow(slabRangDetailResponse.getData());

                        }
                    }else{
                        UtilMethods.INSTANCE.Error(MoveToWalletActivity.this,"Slab Range Data not found.");
                    }
                }




            });
        }
    }

    private void RealCommissionDialogShow(List<SlabRangeDetail> dataList) {

        AlertDialog.Builder dialogBuilder;
        dialogBuilder = new AlertDialog.Builder(MoveToWalletActivity.this);
        AlertDialog alertDialog = dialogBuilder.create();
        LayoutInflater inflater =getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_slab_range_details, null);
        alertDialog.setView(dialogView);
        View fixedCommView,operatorView;
        TextView maxCommissionLabelTv;
        ImageView closeView = dialogView.findViewById(R.id.iv_cancleView);
        maxCommissionLabelTv = dialogView.findViewById(R.id.maxCommissionLabelTv);
        maxCommissionLabelTv.setText("Operators");
        fixedCommView = dialogView.findViewById(R.id.fixedCommView);
        operatorView = dialogView.findViewById(R.id.operatorView);
        RecyclerView slabRangeRecyclerView = dialogView.findViewById(R.id.rv_slabRange);
        slabRangeRecyclerView.setLayoutManager(new LinearLayoutManager(MoveToWalletActivity.this));

        fixedCommView.setVisibility(View.GONE);
        operatorView.setVisibility(View.GONE);

        CommissionChargeDetailAdapter chargeDetailAdapter=new CommissionChargeDetailAdapter(dataList,MoveToWalletActivity.this);
        slabRangeRecyclerView.setAdapter(chargeDetailAdapter);

        closeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }
}
