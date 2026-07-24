package com.fintech.omnipe.Util.CustomFilterDialogUtils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;

import com.fintech.omnipe.Fragments.dto.OperatorList;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SwitchCompat;
import android.text.Html;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.fintech.omnipe.Api.Response.WalletTypeResponse;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.UtilMethods;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class CustomFilterDialog {
    private final int currentYear, currentMonth, currentDay;
    Activity mActivity;
    private Calendar myCalendarTo, myCalendarFrom;
    private long to_Mill, from_mill;
    private HashMap<String, Integer> debitCreditTypesMap = new HashMap<>();
    private String[] debitCreditArray = {"Fund Credit", "Fund Debit"};

    private HashMap<String, Integer> walletTypesMap = new HashMap<>();
    private String[] walletTypesArray = {};
    private int selectedIndex;

    private AlertDialog singleChoiceDialog;


    private String[] ledgerChooseTopArray = {"50", "100", "200", "500", "1000", "1500", "ALL"};
    private HashMap<String, Integer> fundOrderStatusMap = new HashMap<>();
    private String[] fundOrderStatusArray = {":: Select Status ::", "REQUESTED", "ACCEPTED", "REJECTED"};

    private HashMap<String, Integer> disputeStatusMap = new HashMap<>();
    private String[] disputeStatusArray = {":: Select Status ::", "UNDER REVIEW", "REFUNDED", "REJECTED"};

    private HashMap<String, Integer> rechargeStatusMap = new HashMap<>();
    private String[] rechargeStatusArray = {":: Select Status ::", "SUCCESS", "FAILED", "REFUND", "PENDING"};

    private HashMap<String, Integer> disputeDateTypeMap = new HashMap<>();
    private String[] disputeDateTypeArray = {"Accept Reject Date", "Recharge Date", "Request Date"};

    private HashMap<String, Integer> disputeCriteriaMap = new HashMap<>();
    private String[] disputeCriteriaArray = {":: Select Criteria ::", "Account No.", "Transaction ID"};

    private HashMap<String, Integer> chooseModeMap = new HashMap<>();
    private String[] chooseModeArray = {":: Select Mode ::", "NEFT", "RTGS", "Third Party Transfer", "Cheque", "IMPS", "EC-Exchange", "CASH DEPOSIT", "GCC (Green Channel Card)"};

    private HashMap<String, Integer> dthSubscriptionBookingStatusMap = new HashMap<>();
    public String[] dthSubscriptionBookingStatusArray = {":: Select Status ::", "Requested", "ForwardToEngineer", "Installing", "Completed", "Rejected"};

    private HashMap<String, Integer> dthSubscriptionStatusMap = new HashMap<>();
    public String[] dthSubscriptionStatusArray = {":: Select Status ::", "PENDING", "SUCCESS", "FAILED", "REFUND", "REQUESTSENT"};

    private String rollID;
    //Move To Bank
    private String[] chooseMoveToBankModeArray={};
    private Map<String,Integer> chooseMoveToBankModeMap=new HashMap<>();


    public CustomFilterDialog(Activity mActivity) {
        this.mActivity = mActivity;
        setMapData();
        final Calendar currentCalendar = Calendar.getInstance(TimeZone.getDefault());

        currentYear = currentCalendar.get(Calendar.YEAR);
        currentMonth = currentCalendar.get(Calendar.MONTH) + 1;
        currentDay = currentCalendar.get(Calendar.DAY_OF_MONTH);
        from_mill = currentCalendar.getTimeInMillis();
        to_Mill = from_mill;
        myCalendarTo = Calendar.getInstance();
        myCalendarFrom = Calendar.getInstance();

        rollID=UtilMethods.INSTANCE.getRoleId(mActivity);
    }


    void setMapData() {
        debitCreditTypesMap.put("Fund Credit", 4);
        debitCreditTypesMap.put("Fund Debit", 5);

        chooseModeMap.put(":: Select Mode ::", 0);
        chooseModeMap.put("NEFT", 1);
        chooseModeMap.put("RTGS", 2);
        chooseModeMap.put("Third Party Transfer", 3);
        chooseModeMap.put("Cheque", 4);
        chooseModeMap.put("IMPS", 5);
        chooseModeMap.put("EC-Exchange", 6);
        chooseModeMap.put("CASH DEPOSIT", 7);
        chooseModeMap.put("GCC (Green Channel Card)", 8);


        fundOrderStatusMap.put(":: Select Status ::", 0);
        fundOrderStatusMap.put("REQUESTED", 1);
        fundOrderStatusMap.put("ACCEPTED", 2);
        fundOrderStatusMap.put("REJECTED", 3);


        disputeStatusMap.put(":: Select Status ::", 0);
        disputeStatusMap.put("UNDER REVIEW", 2);
        disputeStatusMap.put("REFUNDED", 3);
        disputeStatusMap.put("REJECTED", 4);

        rechargeStatusMap.put(":: Select Status ::", 0);
        rechargeStatusMap.put("SUCCESS", 2);
        rechargeStatusMap.put("FAILED", 3);
        rechargeStatusMap.put("REFUND", 4);
        rechargeStatusMap.put("PENDING", 1);

        disputeDateTypeMap.put("Accept Reject Date", 1);
        disputeDateTypeMap.put("Recharge Date", 2);
        disputeDateTypeMap.put("Request Date", 3);

        disputeCriteriaMap.put(":: Select Criteria ::", 0);
        disputeCriteriaMap.put("Account No.", 2);
        disputeCriteriaMap.put("Transaction ID", 4);

        dthSubscriptionBookingStatusMap.put(":: Select Status ::", 0);
        dthSubscriptionBookingStatusMap.put("Requested", 1);
        dthSubscriptionBookingStatusMap.put("ForwardToEngineer", 2);
        dthSubscriptionBookingStatusMap.put("Installing", 3);
        dthSubscriptionBookingStatusMap.put("Completed", 4);
        dthSubscriptionBookingStatusMap.put("Rejected", 5);


        dthSubscriptionStatusMap.put(":: Select Status ::", 0);
        dthSubscriptionStatusMap.put("PENDING", 1);
        dthSubscriptionStatusMap.put("SUCCESS", 2);
        dthSubscriptionStatusMap.put("FAILED", 3);
        dthSubscriptionStatusMap.put("REFUND", 4);
        dthSubscriptionStatusMap.put("REQUESTSENT", 5);
    }

    public void openFundDCFilter(final String fromDate, String toDate, String filterMobieNum, boolean isSelfFilter, String filterOtherMobile, String filterService, String filterWalletType, String serviceTypeTitleStr, final FundDebitCreditCallBack mFundDebitCreditCallBack) {
        final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(mActivity);

        View sheetView = mActivity.getLayoutInflater().inflate(R.layout.fund_debit_credit_filter, null);
        LinearLayout container = sheetView.findViewById(R.id.container);
        final LinearLayout walletTypeView = sheetView.findViewById(R.id.walletTypeView);
        final AppCompatTextView walletTypeChooser = sheetView.findViewById(R.id.walletTypeChooser);
        LinearLayout startDateView = sheetView.findViewById(R.id.startDateView);
        final AppCompatTextView startDate = sheetView.findViewById(R.id.startDate);
        startDate.setText(fromDate);

        LinearLayout endDateView = sheetView.findViewById(R.id.endDateView);
        final AppCompatTextView endDate = sheetView.findViewById(R.id.endDate);
        endDate.setText(toDate);

        final AppCompatEditText mobileNoEt = sheetView.findViewById(R.id.mobileNoEt);
        mobileNoEt.setText(filterMobieNum);
        final SwitchCompat isSelfSwitch = sheetView.findViewById(R.id.isSelfSwitch);
        isSelfSwitch.setChecked(isSelfFilter);


        final AppCompatTextView debitCreditChooser = sheetView.findViewById(R.id.debitCreditChooser);
        if (filterService != null && !filterService.isEmpty()) {
            debitCreditChooser.setText(filterService);
        } else {
            debitCreditChooser.setText(debitCreditArray[0]);
        }
        final TextView receivedByTitle = sheetView.findViewById(R.id.receivedByTitle);
        receivedByTitle.setText(serviceTypeTitleStr);

        final AppCompatEditText receivedByEt = sheetView.findViewById(R.id.receivedByEt);
        receivedByEt.setHint("Enter " + serviceTypeTitleStr);
        receivedByEt.setText(filterOtherMobile);
        Button filter = sheetView.findViewById(R.id.filter);
        debitCreditChooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedIndex = 0;
                if (debitCreditChooser.getText().toString().length() == 0) {
                    selectedIndex = 0;
                } else {
                    selectedIndex = Arrays.asList(debitCreditArray).indexOf(debitCreditChooser.getText().toString());
                }
                showSingleChoiceAlert(debitCreditArray, selectedIndex, "Debit/Credit", "Choose Transaction Type", new SingleChoiceDialogCallBack() {
                    @Override
                    public void onPositiveClick(int index) {
                        debitCreditChooser.setText(debitCreditArray[index]);
                        if (index == 0) {
                            receivedByTitle.setText("Received By");
                            receivedByEt.setHint("Enter Received By");
                        } else {
                            receivedByTitle.setText("Fund Transfered To");
                            receivedByEt.setHint("Enter Fund Transfered To");
                        }
                    }

                    @Override
                    public void onNegativeClick() {

                    }
                });
            }
        });
        if (UtilMethods.INSTANCE.getWalletType(mActivity) != null && !UtilMethods.INSTANCE.getWalletType(mActivity).isEmpty()) {
            WalletTypeResponse mWalletTypeResponse = new Gson().fromJson(UtilMethods.INSTANCE.getWalletType(mActivity), WalletTypeResponse.class);
            if (mWalletTypeResponse != null && mWalletTypeResponse.getWalletTypes() != null && mWalletTypeResponse.getWalletTypes().size() > 0) {
                walletTypesArray = new String[mWalletTypeResponse.getWalletTypes().size()];
                for (int i = 0; i < mWalletTypeResponse.getWalletTypes().size(); i++) {
                    walletTypesMap.put(mWalletTypeResponse.getWalletTypes().get(i).getName(), mWalletTypeResponse.getWalletTypes().get(i).getId());
                    walletTypesArray[i] = mWalletTypeResponse.getWalletTypes().get(i).getName();
                }
                if (filterWalletType != null && !filterWalletType.isEmpty()) {
                    walletTypeChooser.setText(filterWalletType);
                } else {
                    walletTypeChooser.setText(walletTypesArray[0]);
                }
            } else {
                walletTypeView.setVisibility(View.GONE);
                UtilMethods.INSTANCE.WalletType(mActivity, null, null);
            }
        } else {
            walletTypeView.setVisibility(View.GONE);
            UtilMethods.INSTANCE.WalletType(mActivity, null, null);
        }
        walletTypeChooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedIndex = 0;
                if (walletTypeChooser.getText().toString().length() == 0) {
                    selectedIndex = 0;
                } else {
                    selectedIndex = Arrays.asList(walletTypesArray).indexOf(walletTypeChooser.getText().toString());
                }
                showSingleChoiceAlert(walletTypesArray, selectedIndex, "Wallet Type", "Choose Wallet Type", new SingleChoiceDialogCallBack() {
                    @Override
                    public void onPositiveClick(int index) {
                        walletTypeChooser.setText(walletTypesArray[index]);
                    }

                    @Override
                    public void onNegativeClick() {

                    }
                });
            }
        });
        startDateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDCFromDate(startDate, endDate);
            }
        });
        endDateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDCToDate(startDate, endDate);
            }
        });
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
                if (mFundDebitCreditCallBack != null) {
                    mFundDebitCreditCallBack.onSubmitClick(startDate.getText().toString(), endDate.getText().toString(), mobileNoEt.getText().toString()
                            , isSelfSwitch.isChecked(), walletTypeView.getVisibility() == View.VISIBLE ? walletTypesMap.get(walletTypeChooser.getText().toString()) : 0, walletTypeChooser.getText().toString(),
                            debitCreditTypesMap.get(debitCreditChooser.getText().toString()), debitCreditChooser.getText().toString(), receivedByEt.getText().toString());
                }
            }
        });
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();

    }


    public void openDthSubscriptionFilter(String rollID, final String filterFromDate, String filterToDate,
                                          String filterMobileNo, String filterTransactionId, String filterChildMobileNo, String filterAccountNo, int filterTopValue, String filterStatus, String filterBookingStatus, final DthSubscriptionFilterCallBack mDthSubscriptionFilterCallBack) {
        final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(mActivity);

        View sheetView = mActivity.getLayoutInflater().inflate(R.layout.dth_subscription_filter, null);
        final LinearLayout walletTypeView = sheetView.findViewById(R.id.walletTypeView);
        final AppCompatTextView walletTypeChooser = sheetView.findViewById(R.id.walletTypeChooser);
        LinearLayout container = sheetView.findViewById(R.id.container);
        ImageView mobileNumberLeftIcon = sheetView.findViewById(R.id.mobileNumberLeftIcon);
        LinearLayout startDateView = sheetView.findViewById(R.id.startDateView);
        AppCompatTextView mobileNumTitle = sheetView.findViewById(R.id.mobileNumTitle);
        final AppCompatTextView startDate = sheetView.findViewById(R.id.startDate);
        startDate.setText(filterFromDate);
        LinearLayout endDateView = sheetView.findViewById(R.id.endDateView);
        final AppCompatTextView endDate = sheetView.findViewById(R.id.endDate);
        endDate.setText(filterToDate);
        LinearLayout mobileView = sheetView.findViewById(R.id.mobileView);
        final AppCompatEditText mobileNoEt = sheetView.findViewById(R.id.mobileNoEt);
        mobileNoEt.setText(filterMobileNo);
        LinearLayout transactionIdView = sheetView.findViewById(R.id.transactionIdView);
        final AppCompatEditText transactionIdEt = sheetView.findViewById(R.id.transactionIdEt);
        transactionIdEt.setText(filterTransactionId);
        LinearLayout childMobileView = sheetView.findViewById(R.id.childMobileView);
        final AppCompatEditText childMobileNoEt = sheetView.findViewById(R.id.childMobileNoEt);
        childMobileNoEt.setText(filterChildMobileNo);
        LinearLayout accountNoView = sheetView.findViewById(R.id.accountNoView);
        TextView accountNoTitle = sheetView.findViewById(R.id.accountNoTitle);
        final AppCompatEditText accountNoEt = sheetView.findViewById(R.id.accountNoEt);
        accountNoEt.setText(filterAccountNo);
        LinearLayout topChooserView = sheetView.findViewById(R.id.topChooserView);
        final AppCompatTextView topChooser = sheetView.findViewById(R.id.topChooser);
        AppCompatTextView statusTitleTv = sheetView.findViewById(R.id.statusTitleTv);
        AppCompatTextView modeTitleTv = sheetView.findViewById(R.id.modeTitle);
        topChooser.setText(filterTopValue + "");
        TextView dateTypeTitle = sheetView.findViewById(R.id.dateTypeTitle);
        LinearLayout refundStatusChooserView = sheetView.findViewById(R.id.refundStatusChooserView);
        final AppCompatTextView refundStatusChooser = sheetView.findViewById(R.id.refundStatusChooser);
        LinearLayout dateTypeChooserView = sheetView.findViewById(R.id.dateTypeChooserView);
        final AppCompatTextView dateTypeChooser = sheetView.findViewById(R.id.dateTypeChooser);
        LinearLayout modeChooserView = sheetView.findViewById(R.id.modeChooserView);
        final AppCompatTextView modeChooser = sheetView.findViewById(R.id.modeChooser);
        LinearLayout criteriaChooserView = sheetView.findViewById(R.id.criteriaChooserView);
        final AppCompatTextView criteriaChooser = sheetView.findViewById(R.id.criteriaChooser);
        LinearLayout criteriaView = sheetView.findViewById(R.id.criteriaView);
        final TextView criteriaTitle = sheetView.findViewById(R.id.criteriaTitle);
        final AppCompatEditText criteriaEt = sheetView.findViewById(R.id.criteriaEt);
        Button filter = sheetView.findViewById(R.id.filter);

        //Condition accouding filter
        mobileView.setVisibility(View.GONE);
        walletTypeView.setVisibility(View.GONE);
        transactionIdView.setVisibility(View.VISIBLE);
        accountNoView.setVisibility(View.VISIBLE);
        refundStatusChooserView.setVisibility(View.VISIBLE);
        modeChooserView.setVisibility(View.VISIBLE);
        if (rollID.equalsIgnoreCase("3") || rollID.equalsIgnoreCase("2")) {
            childMobileView.setVisibility(View.GONE);
        } else {
            childMobileView.setVisibility(View.VISIBLE);
        }
        statusTitleTv.setText("Choose Subscription Status");
        refundStatusChooser.setHint("Choose Subscription Status");
        modeTitleTv.setText("Choose Status");
        modeChooser.setHint("Choose Status");
        accountNoTitle.setText("Account Number");
        accountNoEt.setHint("Account Number");
        dateTypeChooserView.setVisibility(View.GONE);
        criteriaChooserView.setVisibility(View.GONE);
        criteriaView.setVisibility(View.GONE);
        refundStatusChooser.setText(filterBookingStatus != null && !filterBookingStatus.isEmpty() ? filterBookingStatus : dthSubscriptionBookingStatusArray[0]);
        modeChooser.setText(filterStatus != null && !filterStatus.isEmpty() ? filterStatus : dthSubscriptionStatusArray[0]);



        topChooser.setOnClickListener(v -> {
            int selectedIndex = 0;
            if (topChooser.getText().toString().length() == 0) {
                selectedIndex = 0;
            } else {
                selectedIndex = Arrays.asList(ledgerChooseTopArray).indexOf(topChooser.getText().toString());
            }
            showSingleChoiceAlert(ledgerChooseTopArray, selectedIndex, "Select Top", "Choose Top", new SingleChoiceDialogCallBack() {
                @Override
                public void onPositiveClick(int index) {
                    topChooser.setText(ledgerChooseTopArray[index]);
                }

                @Override
                public void onNegativeClick() {

                }
            });
        });

        modeChooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedIndex = 0;
                if (modeChooser.getText().toString().length() == 0) {
                    selectedIndex = 0;
                } else {
                    selectedIndex = Arrays.asList(dthSubscriptionStatusArray).indexOf(modeChooser.getText().toString());
                }
                showSingleChoiceAlert(dthSubscriptionStatusArray, selectedIndex, "Status", "Choose Status", new SingleChoiceDialogCallBack() {
                    @Override
                    public void onPositiveClick(int index) {
                        modeChooser.setText(dthSubscriptionStatusArray[index]);
                    }

                    @Override
                    public void onNegativeClick() {

                    }
                });
            }
        });

        refundStatusChooser.setOnClickListener(v -> {

            int selectedIndex = 0;
            if (refundStatusChooser.getText().toString().length() == 0) {
                selectedIndex = 0;
            } else {
                selectedIndex = Arrays.asList(dthSubscriptionBookingStatusArray).indexOf(refundStatusChooser.getText().toString());
            }
            showSingleChoiceAlert(dthSubscriptionBookingStatusArray, selectedIndex, "DTH Subscription Status", "Choose DTH Subscription Status", new SingleChoiceDialogCallBack() {
                @Override
                public void onPositiveClick(int index) {
                    refundStatusChooser.setText(dthSubscriptionBookingStatusArray[index]);
                }

                @Override
                public void onNegativeClick() {

                }
            });


        });
        startDateView.setOnClickListener(v -> setDCFromDate(startDate, endDate));
        endDateView.setOnClickListener(v -> setDCToDate(startDate, endDate));

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
                if (mDthSubscriptionFilterCallBack != null)
                {
                    mDthSubscriptionFilterCallBack.onSubmitClick(
                            startDate.getText().toString(),
                            endDate.getText().toString(),
                            mobileNoEt.getText().toString(),
                            transactionIdEt.getText().toString(),
                            childMobileNoEt.getText().toString(),
                            accountNoEt.getText().toString(),
                            topChooser.getText().toString().equalsIgnoreCase("ALL") ? 5000 : Integer.parseInt(topChooser.getText().toString()),
                            !modeChooser.getText().toString().isEmpty() ? (dthSubscriptionStatusMap.get(modeChooser.getText().toString())) : 0,
                            modeChooser.getText().toString(),
                            !refundStatusChooser.getText().toString().isEmpty() ? (dthSubscriptionBookingStatusMap.get(refundStatusChooser.getText().toString())) : 0,
                            refundStatusChooser.getText().toString());

                }

            }
        });

        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();

    }

    public void openReportFosFilter(final String filterFromDate, String filterToDate,
                                    int filterTopValue,
                                    final LedgerReportFosFilterCallBack mLedgerFilterCallBack) {
        final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(mActivity);

        View sheetView = mActivity.getLayoutInflater().inflate(R.layout.ledger_filter, null);
        LinearLayout childMobileView = sheetView.findViewById(R.id.childMobileView);
        childMobileView.setVisibility(View.GONE);

        LinearLayout mobileView = sheetView.findViewById(R.id.mobileView);
        mobileView.setVisibility(View.GONE);

        LinearLayout accountNoView = sheetView.findViewById(R.id.accountNoView);
        accountNoView.setVisibility(View.GONE);

        LinearLayout transactionIdView = sheetView.findViewById(R.id.transactionIdView);
        transactionIdView.setVisibility(View.GONE);

        LinearLayout walletTypeView = sheetView.findViewById(R.id.walletTypeView);
        walletTypeView.setVisibility(View.GONE);

        LinearLayout refundStatusChooserView = sheetView.findViewById(R.id.refundStatusChooserView);
        refundStatusChooserView.setVisibility(View.GONE);

        LinearLayout dateTypeChooserView = sheetView.findViewById(R.id.dateTypeChooserView);
        dateTypeChooserView.setVisibility(View.GONE);

        LinearLayout modeChooserView = sheetView.findViewById(R.id.modeChooserView);
        modeChooserView.setVisibility(View.GONE);

        LinearLayout criteriaView = sheetView.findViewById(R.id.criteriaView);
        criteriaView.setVisibility(View.GONE);

        LinearLayout criteriaChooserView = sheetView.findViewById(R.id.criteriaChooserView);
        criteriaChooserView.setVisibility(View.GONE);




        LinearLayout startDateView = sheetView.findViewById(R.id.startDateView);
        final AppCompatTextView startDate = sheetView.findViewById(R.id.startDate);
        startDate.setText(filterFromDate);
        LinearLayout endDateView = sheetView.findViewById(R.id.endDateView);
        final AppCompatTextView endDate = sheetView.findViewById(R.id.endDate);
        endDate.setText(filterToDate);


        LinearLayout topChooserView = sheetView.findViewById(R.id.topChooserView);
        final AppCompatTextView topChooser = sheetView.findViewById(R.id.topChooser);
        topChooser.setText(filterTopValue + "");



        Button filter = sheetView.findViewById(R.id.filter);



        topChooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedIndex = 0;
                if (topChooser.getText().toString().length() == 0) {
                    selectedIndex = 0;
                } else {
                    selectedIndex = Arrays.asList(ledgerChooseTopArray).indexOf(topChooser.getText().toString());
                }
                showSingleChoiceAlert(ledgerChooseTopArray, selectedIndex, "Select Top", "Choose Top", new SingleChoiceDialogCallBack() {
                    @Override
                    public void onPositiveClick(int index) {
                        topChooser.setText(ledgerChooseTopArray[index]);
                    }

                    @Override
                    public void onNegativeClick() {

                    }
                });
            }
        });
        startDateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDCFromDate(startDate, endDate);
            }
        });
        endDateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDCToDate(startDate, endDate);
            }
        });



        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
                if (mLedgerFilterCallBack != null) {
                    mLedgerFilterCallBack.onSubmitClick(startDate.getText().toString(),
                            endDate.getText().toString(),topChooser.getText().toString().equalsIgnoreCase("ALL") ? 5000 : Integer.parseInt(topChooser.getText().toString()));


                }
            }
        });
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();

    }

    public void openMoveToBankFilterDialog(String roleId, String filterFromDate, String filterToDate, String filterTransactionId, String filterChildMobileNo, int filterTopValue, String filterModeValue, String filterStatusValue, ArrayList<OperatorList> mOperatorLists, int opTypeId, MoveToBankReportFilterCallBack moveToBankReportFilterCallBack){
        final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(mActivity);
        View sheetView = mActivity.getLayoutInflater().inflate(R.layout.ledger_filter, null);


        /*--Start date--*/
        LinearLayout startDateView = sheetView.findViewById(R.id.startDateView);
        final AppCompatTextView startDate = sheetView.findViewById(R.id.startDate);
        startDate.setText(filterFromDate);

        //End Date
        LinearLayout endDateView = sheetView.findViewById(R.id.endDateView);
        final AppCompatTextView endDate = sheetView.findViewById(R.id.endDate);
        endDate.setText(filterToDate);

        //TransactionId
        LinearLayout transactionIdView = sheetView.findViewById(R.id.transactionIdView);
        final AppCompatEditText transactionIdEt = sheetView.findViewById(R.id.transactionIdEt);
        transactionIdEt.setText(filterTransactionId);

        //ChildId
        LinearLayout childMobileView = sheetView.findViewById(R.id.childMobileView);
        final AppCompatEditText childMobileNoEt = sheetView.findViewById(R.id.childMobileNoEt);
        childMobileNoEt.setText(filterChildMobileNo);

        //Top Chooser
        LinearLayout topChooserView = sheetView.findViewById(R.id.topChooserView);
        final AppCompatTextView topChooser = sheetView.findViewById(R.id.topChooser);
        topChooser.setText(filterTopValue + "");

        //Mode View
        LinearLayout modeChooserView = sheetView.findViewById(R.id.modeChooserView);
        final AppCompatTextView modeChooser = sheetView.findViewById(R.id.modeChooser);

        //Refund Status
        LinearLayout refundStatusChooserView = sheetView.findViewById(R.id.refundStatusChooserView);
        final AppCompatTextView refundStatusChooser = sheetView.findViewById(R.id.refundStatusChooser);
        final TextView statusTitleTv = sheetView.findViewById(R.id.statusTitleTv);

        statusTitleTv.setText("Choose Status");
        refundStatusChooser.setHint("Choose Status");

        if(roleId.equalsIgnoreCase("3") || roleId.equalsIgnoreCase("2")){
            childMobileView.setVisibility(View.GONE);
        }else{
            childMobileView.setVisibility(View.VISIBLE);
        }

        //Hide View
        final LinearLayout walletTypeView = sheetView.findViewById(R.id.walletTypeView);
        LinearLayout accountNoView = sheetView.findViewById(R.id.mobileView);
        LinearLayout dateTypeChooserView = sheetView.findViewById(R.id.dateTypeChooserView);
        LinearLayout criteriaChooserView = sheetView.findViewById(R.id.criteriaChooserView);
        LinearLayout criteriaView = sheetView.findViewById(R.id.criteriaView);
        walletTypeView.setVisibility(View.GONE);
        accountNoView.setVisibility(View.GONE);
        dateTypeChooserView.setVisibility(View.GONE);
        criteriaChooserView.setVisibility(View.GONE);
        criteriaView.setVisibility(View.GONE);

        Button filter = sheetView.findViewById(R.id.filter);


        /*---Bind Filter Mode Data-----*/

        if (mOperatorLists != null && mOperatorLists.size() > 0) {
            ArrayList<String> modeArray = new ArrayList<>();

            modeArray.add(":: Select Mode ::");
            chooseMoveToBankModeMap.put(":: Select Mode ::", 0);
            for (OperatorList op : mOperatorLists) {
                if (op.getOpType() == opTypeId && op.isActive()) {
                    modeArray.add(op.getName());
                    chooseMoveToBankModeMap.put(op.getName(), op.getOid());
                }
            }
            chooseMoveToBankModeArray = modeArray.toArray(new String[modeArray.size()]);

            modeChooser.setText(filterModeValue != null && !filterModeValue.isEmpty() ? filterModeValue : chooseMoveToBankModeArray[0]);

        }

        refundStatusChooser.setText(filterStatusValue != null && !filterStatusValue.isEmpty() ? filterStatusValue : disputeStatusArray[0]);
        topChooser.setOnClickListener(v -> {
            int selectedIndex = 0;
            if (topChooser.getText().toString().length() == 0) {
                selectedIndex = 0;
            } else {
                selectedIndex = Arrays.asList(ledgerChooseTopArray).indexOf(topChooser.getText().toString());
            }
            showSingleChoiceAlert(ledgerChooseTopArray, selectedIndex, "Select Top", "Choose Top", new CustomFilterDialog.SingleChoiceDialogCallBack() {
                @Override
                public void onPositiveClick(int index) {
                    topChooser.setText(ledgerChooseTopArray[index]);
                }

                @Override
                public void onNegativeClick() {

                }
            });
        });
        modeChooser.setOnClickListener(v -> {
            int selectedIndex = 0;
            if (modeChooser.getText().toString().length() == 0) {
                selectedIndex = 0;
            } else {
                selectedIndex = Arrays.asList(chooseMoveToBankModeArray).indexOf(modeChooser.getText().toString());
            }
            showSingleChoiceAlert(chooseMoveToBankModeArray, selectedIndex, "Select Mode", "Choose Mode", new CustomFilterDialog.SingleChoiceDialogCallBack() {
                @Override
                public void onPositiveClick(int index) {
                    modeChooser.setText(chooseMoveToBankModeArray[index]);
                }

                @Override
                public void onNegativeClick() {

                }
            });
        });
        refundStatusChooser.setOnClickListener(v -> {

            int selectedIndex = 0;
            if (refundStatusChooser.getText().toString().length() == 0) {
                selectedIndex = 0;
            } else {
                selectedIndex = Arrays.asList(rechargeStatusArray).indexOf(refundStatusChooser.getText().toString());
            }
            showSingleChoiceAlert(rechargeStatusArray, selectedIndex, "Status", "Choose Status", new CustomFilterDialog.SingleChoiceDialogCallBack() {
                @Override
                public void onPositiveClick(int index) {
                    refundStatusChooser.setText(rechargeStatusArray[index]);
                }

                @Override
                public void onNegativeClick() {

                }
            });


        });
        startDateView.setOnClickListener(v -> setDCFromDate(startDate, endDate));
        endDateView.setOnClickListener(v -> setDCToDate(startDate, endDate));

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
                if(moveToBankReportFilterCallBack!=null)
                {
                    moveToBankReportFilterCallBack.onSubmitClick(
                            startDate.getText().toString(),
                            endDate.getText().toString(),
                            childMobileNoEt.getText().toString(),
                            transactionIdEt.getText().toString(),
                            topChooser.getText().toString().equalsIgnoreCase("ALL") ? 5000 : Integer.parseInt(topChooser.getText().toString()),
                            !refundStatusChooser.getText().toString().isEmpty() ? (rechargeStatusMap.get(refundStatusChooser.getText().toString())) : 0,
                            refundStatusChooser.getText().toString(),
                            !modeChooser.getText().toString().isEmpty()?(chooseMoveToBankModeMap.get(modeChooser.getText().toString())): 0,
                            modeChooser.getText().toString()

                    );
                }
            }
        });

        mBottomSheetDialog.setContentView(sheetView);

        BottomSheetBehavior
                .from(mBottomSheetDialog.findViewById(R.id.design_bottom_sheet))
                .setState(BottomSheetBehavior.STATE_EXPANDED);

        mBottomSheetDialog.show();
    }

    public void setDCFromDate(final TextView fromDateTv, final TextView toDateTv) {
        DatePickerDialog mDatePicker = new DatePickerDialog(mActivity, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendarFrom.set(Calendar.YEAR, year);
                myCalendarFrom.set(Calendar.MONTH, monthOfYear);
                myCalendarFrom.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd MMM yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                fromDateTv.setText(sdf.format(myCalendarFrom.getTime()));
                Date date1 = myCalendarFrom.getTime();
                from_mill = date1.getTime();

                if (from_mill > to_Mill) {
                    toDateTv.setText(fromDateTv.getText());
                    myCalendarTo.setTime(myCalendarFrom.getTime());
                } else if (currentDay == dayOfMonth && currentMonth == (monthOfYear + 1) && currentYear == year) {
                    toDateTv.setText(fromDateTv.getText());
                    myCalendarTo.setTime(myCalendarFrom.getTime());
                } else if (currentDay != dayOfMonth && currentMonth == (monthOfYear + 1) && currentYear == year) {
                    Calendar cal = Calendar.getInstance(TimeZone.getDefault());
                    cal.add(Calendar.DAY_OF_YEAR, -1);
                    toDateTv.setText(sdf.format(cal.getTime()));
                    myCalendarTo.setTime(cal.getTime());
                } else if (currentMonth != (monthOfYear + 1) && currentYear == year || currentMonth == (monthOfYear + 1) && currentYear != year || currentMonth != (monthOfYear + 1) && currentYear != year) {
                    Calendar cal = Calendar.getInstance(TimeZone.getDefault());
                    cal.setTime(myCalendarFrom.getTime());
                    cal.add(Calendar.MONTH, 1);
                    cal.set(Calendar.DAY_OF_MONTH, 1);
                    cal.add(Calendar.DATE, -1);
                    toDateTv.setText(sdf.format(cal.getTime()));
                    myCalendarTo.setTime(cal.getTime());
                }
            }

        }, myCalendarFrom
                .get(Calendar.YEAR), myCalendarFrom.get(Calendar.MONTH),
                myCalendarFrom.get(Calendar.DAY_OF_MONTH));
        mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
        mDatePicker.show();
    }

    public void setDCToDate(final TextView fromDateTv, final TextView toDateTv) {
        DatePickerDialog mDatePicker = new DatePickerDialog(mActivity, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendarTo.set(Calendar.YEAR, year);
                myCalendarTo.set(Calendar.MONTH, monthOfYear);
                myCalendarTo.set(Calendar.DAY_OF_MONTH, dayOfMonth);


                String myFormat = "dd MMM yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                toDateTv.setText(sdf.format(myCalendarTo.getTime()));
                Date date1 = myCalendarTo.getTime();
                to_Mill = date1.getTime();

                if (from_mill > to_Mill) {
                    fromDateTv.setText(toDateTv.getText());
                    myCalendarFrom.setTime(myCalendarTo.getTime());
                } else if (currentDay == dayOfMonth && currentMonth == (monthOfYear + 1) && currentYear == year) {
                    fromDateTv.setText(toDateTv.getText());
                    myCalendarFrom.setTime(myCalendarTo.getTime());
                } else if (currentDay != dayOfMonth && currentMonth == (monthOfYear + 1) && currentYear == year || currentMonth != (monthOfYear + 1) && currentYear == year || currentMonth == (monthOfYear + 1) && currentYear != year || currentMonth != (monthOfYear + 1) && currentYear != year) {
                    Calendar cal = Calendar.getInstance(TimeZone.getDefault());
                    cal.setTime(myCalendarTo.getTime());
                    cal.set(Calendar.DAY_OF_MONTH, 1);
                    fromDateTv.setText(sdf.format(cal.getTime()));
                    myCalendarFrom.setTime(cal.getTime());
                }
            }

        }, myCalendarTo
                .get(Calendar.YEAR), myCalendarTo.get(Calendar.MONTH),
                myCalendarTo.get(Calendar.DAY_OF_MONTH));
        mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
        mDatePicker.show();
    }


    public void showSingleChoiceAlert(String[] singleChoiceItems, int itemSelected, String title, String msg, final SingleChoiceDialogCallBack singleChoiceDialogCallBack) {

        selectedIndex = itemSelected;
        if (singleChoiceDialog != null && singleChoiceDialog.isShowing()) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity/*, themeResId*/);

        builder.setTitle(title);
        if (singleChoiceItems.length == 0) {
            builder.setMessage(Html.fromHtml("<font color='#646464'>" + msg + "</font>"));
        }
        builder.setSingleChoiceItems(singleChoiceItems, selectedIndex, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int clickIndex) {
                selectedIndex = clickIndex;

            }
        });
        builder.setPositiveButton("Select", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (singleChoiceDialogCallBack != null) {
                    singleChoiceDialogCallBack.onPositiveClick(selectedIndex);
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (singleChoiceDialogCallBack != null) {
                    singleChoiceDialogCallBack.onNegativeClick();
                }
            }
        });
        singleChoiceDialog = builder.create();
        singleChoiceDialog.show();


    }

    public interface SingleChoiceDialogCallBack {
        void onPositiveClick(int index);

        void onNegativeClick();
    }

    public interface FundDebitCreditCallBack {

        void onSubmitClick(String fromDate, String toDate, String mobileNo, boolean isSelf, int walletTypeId, String walletType, int serviceTypeId, String serviceType, String otherMobile);
    }

   /* public interface LedgerFilterCallBack {

        void onSubmitClick(String fromDate, String toDate, String mobileNo, String transactionId, String childMobileNo,
                           String accountNo, int topValue, int statusId, String statusValue, int dateTypeId,
                           String dateTypeValue, int modeId,
                           String modeValue, int chooseCriteriaId, String chooseCriteriaValue, String enterCriteriaValue,
                           int walletTypeId, String walletType);
    }*/


    public interface LedgerFilterCallBack {

        void onSubmitClick(String fromDate, String toDate, String mobileNo, int topValue, int statusId, String statusValue, int dateTypeId, String dateTypeValue, int chooseCriteriaId, String chooseCriteriaValue, String enterCriteriaValue, int walletTypeId, String walletType,String mobileNoNew);
    }

    public interface FundOrderFilterCallBack {

        void onSubmitClick(String fromDate, String toDate, String mobileNo, String transactionId, int topValue, int statusId, String statusValue, int modeId, String modeValue);
    }



   /* public void openAEPSRechargeDMRFilter(final String dialogType, final String filterFromDate, String filterToDate, String filterChildNumber, final String filterMobileNo,final String transcationId, int filterTopValue,final AEPSDMRAndRechargeFilterCallBack filterCallBack)
    {
        final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(mActivity);
        View sheetView = mActivity.getLayoutInflater().inflate(R.layout.ledger_filter, null);
        LinearLayout container = (LinearLayout) sheetView.findViewById(R.id.container);
        LinearLayout childNumLayout = (LinearLayout) sheetView.findViewById(R.id.ll_mobileNoFilter);
        ImageView mobileNumberLeftIcon = sheetView.findViewById(R.id.mobileNumberLeftIcon);
        LinearLayout startDateView = (LinearLayout) sheetView.findViewById(R.id.startDateView);
        AppCompatTextView accountNumTitle = sheetView.findViewById(R.id.mobileNumTitle);
        final AppCompatTextView startDate = (AppCompatTextView) sheetView.findViewById(R.id.startDate);
        startDate.setText(filterFromDate);
        LinearLayout endDateView = (LinearLayout) sheetView.findViewById(R.id.endDateView);
        final AppCompatTextView endDate = (AppCompatTextView) sheetView.findViewById(R.id.endDate);
        endDate.setText(filterToDate);
        final LinearLayout walletTypeView = (LinearLayout) sheetView.findViewById(R.id.walletTypeView);
        final AppCompatTextView walletTypeChooser = (AppCompatTextView) sheetView.findViewById(R.id.walletTypeChooser);
        final LinearLayout mobileView = (LinearLayout) sheetView.findViewById(R.id.mobileView);
        final AppCompatEditText accountNumET = (AppCompatEditText) sheetView.findViewById(R.id.mobileNoEt);
        *//*accountNumET.setText(filterMobileNo);*//*
        final AppCompatEditText childNumET = (AppCompatEditText) sheetView.findViewById(R.id.et_mobileNoEt);
        *//*childNumET.setText(filterMobileNo);*//*
        LinearLayout topChooserView = (LinearLayout) sheetView.findViewById(R.id.topChooserView);
        final AppCompatTextView topChooser = (AppCompatTextView) sheetView.findViewById(R.id.topChooser);
        topChooser.setText(filterTopValue + "");
        LinearLayout refundStatusChooserView = (LinearLayout) sheetView.findViewById(R.id.refundStatusChooserView);
        final AppCompatTextView refundStatusChooser = (AppCompatTextView) sheetView.findViewById(R.id.refundStatusChooser);
        LinearLayout dateTypeChooserView = (LinearLayout) sheetView.findViewById(R.id.dateTypeChooserView);
        final AppCompatTextView dateTypeChooser = (AppCompatTextView) sheetView.findViewById(R.id.dateTypeChooser);
        LinearLayout criteriaChooserView = (LinearLayout) sheetView.findViewById(R.id.criteriaChooserView);
        final AppCompatTextView criteriaChooser = (AppCompatTextView) sheetView.findViewById(R.id.criteriaChooser);
        LinearLayout criteriaView = (LinearLayout) sheetView.findViewById(R.id.criteriaView);
        final TextView criteriaTitle = (TextView) sheetView.findViewById(R.id.criteriaTitle);
        final TextView childNumTitle = (TextView) sheetView.findViewById(R.id.tv_headertitle);
        final AppCompatEditText criteriaEt = (AppCompatEditText) sheetView.findViewById(R.id.criteriaEt);

        final LinearLayout transactionIdView=sheetView.findViewById(R.id.transactionIdView);
        final EditText et_transactionId=sheetView.findViewById(R.id.et_transactionId);
        final TextView transactionIdTitle=sheetView.findViewById(R.id.transactionIdTitle);

        Button filter = (Button) sheetView.findViewById(R.id.filter);


        if (dialogType.equalsIgnoreCase("AEPS"))
        {
            refundStatusChooserView.setVisibility(View.GONE);
            criteriaChooserView.setVisibility(View.GONE);
            dateTypeChooserView.setVisibility(View.GONE);
            criteriaView.setVisibility(View.GONE);
            mobileView.setVisibility(View.VISIBLE);
            transactionIdView.setVisibility(View.VISIBLE);
            accountNumTitle.setText("Account No/Recharge No."); //accountNo
            accountNumET.setHint("Enter Account No/Recharge No.");

            transactionIdTitle.setText("Transaction Id"); //Transaction ID
            et_transactionId.setHint("Enter Transaction Id");

            if(rollID!=null && rollID.equalsIgnoreCase("3"))//Retailer
            {
                childNumLayout.setVisibility(View.GONE);

            }
            else
            {
                childNumLayout.setVisibility(View.VISIBLE);
                childNumTitle.setText("Child Mobile No.");
                childNumET.setHint("Enter Child Mobile No.");

            }
        }
        else  if (dialogType.equalsIgnoreCase("RechargeReport") ||dialogType.equalsIgnoreCase("DMT") )
        {
            refundStatusChooserView.setVisibility(View.GONE);
            criteriaChooserView.setVisibility(View.GONE);
            dateTypeChooserView.setVisibility(View.GONE);
            criteriaView.setVisibility(View.GONE);
            mobileView.setVisibility(View.VISIBLE);
            transactionIdView.setVisibility(View.VISIBLE);
            accountNumTitle.setText("Account No/Recharge No."); //accountNo
            accountNumET.setHint("Enter Account No/Recharge No.");

            transactionIdTitle.setText("Transaction Id"); //Transaction ID
            et_transactionId.setHint("Enter Transaction Id");

            if(rollID!=null && rollID.equalsIgnoreCase("3"))//Retailer
            {
                childNumLayout.setVisibility(View.GONE);

            }
            else
            {
                childNumLayout.setVisibility(View.VISIBLE);
                childNumTitle.setText("Child Mobile No.");
                childNumET.setHint("Enter Child Mobile No.");

            }
        }


        topChooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedIndex = 0;
                if (topChooser.getText().toString().length() == 0) {
                    selectedIndex = 0;
                } else {
                    selectedIndex = Arrays.asList(ledgerChooseTopArray).indexOf(topChooser.getText().toString());
                }
                showSingleChoiceAlert(ledgerChooseTopArray, selectedIndex, "Select Top", "Choose Top", new SingleChoiceDialogCallBack() {
                    @Override
                    public void onPositiveClick(int index) {
                        topChooser.setText(ledgerChooseTopArray[index]);
                    }

                    @Override
                    public void onNegativeClick() {

                    }
                });
            }
        });

        startDateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDCFromDate(startDate, endDate);
            }
        });
        endDateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDCToDate(startDate, endDate);
            }
        });
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
                if (filterCallBack != null) {
                    if (dialogType.equalsIgnoreCase("AEPS")) {
                        filterCallBack.onSubmitClick(
                                startDate.getText().toString(),
                                endDate.getText().toString(),
                                childNumET.getText().toString(),
                                accountNumET.getText().toString(),
                                et_transactionId.getText().toString(),
                                topChooser.getText().toString().equalsIgnoreCase("ALL") ? 5000 : Integer.parseInt(topChooser.getText().toString())
                        );

                        //RechargeReport
                    }
                    else if (dialogType.equalsIgnoreCase("RechargeReport") || dialogType.equalsIgnoreCase("DMT")) {
                        filterCallBack.onSubmitClick(
                                startDate.getText().toString(),
                                endDate.getText().toString(),
                                childNumET.getText().toString(),
                                accountNumET.getText().toString(),
                                et_transactionId.getText().toString(),
                                topChooser.getText().toString().equalsIgnoreCase("ALL") ? 5000 : Integer.parseInt(topChooser.getText().toString())
                        );

                        //RechargeReport
                    }
                    else  if (dialogType.equalsIgnoreCase("")) {
                        filterCallBack.onSubmitClick(
                                startDate.getText().toString(),
                                endDate.getText().toString(),
                                childNumET.getText().toString(),
                                accountNumET.getText().toString(),
                                et_transactionId.getText().toString(),
                                topChooser.getText().toString().equalsIgnoreCase("ALL") ? 5000 : Integer.parseInt(topChooser.getText().toString())
                        );
                    }
                }
            }
        });
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();

    }*/


                    /*------------------------------Updated -----------------------*/


    public void openDisputeFilter(final String dialogType, final String filterFromDate, String filterToDate, String filterMobileNo, int filterTopValue, String filterStatus, String filterDateType, int filterChooseCriteriaId, String filterChooseCriteria, String filterEnterCriteria, String filterWalletType,final String filterMobileNoNew, final LedgerFilterCallBack mLedgerFilterCallBack) {
        final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(mActivity);

        View sheetView = mActivity.getLayoutInflater().inflate(R.layout.ledger_filter, null);

        LinearLayout container = (LinearLayout) sheetView.findViewById(R.id.container);
        LinearLayout ll_mobileNoFilter = (LinearLayout) sheetView.findViewById(R.id.childMobileView);
        ImageView mobileNumberLeftIcon = sheetView.findViewById(R.id.mobileNumberLeftIcon);
        LinearLayout startDateView = (LinearLayout) sheetView.findViewById(R.id.startDateView);
        AppCompatTextView mobileNumTitle = sheetView.findViewById(R.id.mobileNumTitle);
        final AppCompatTextView startDate = (AppCompatTextView) sheetView.findViewById(R.id.startDate);
        startDate.setText(filterFromDate);
        LinearLayout endDateView = (LinearLayout) sheetView.findViewById(R.id.endDateView);
        final AppCompatTextView endDate = (AppCompatTextView) sheetView.findViewById(R.id.endDate);
        endDate.setText(filterToDate);
        final LinearLayout walletTypeView = (LinearLayout) sheetView.findViewById(R.id.walletTypeView);
        final AppCompatTextView walletTypeChooser = (AppCompatTextView) sheetView.findViewById(R.id.walletTypeChooser);
        final LinearLayout mobileView = (LinearLayout) sheetView.findViewById(R.id.mobileView);
        final AppCompatEditText mobileNoEt = (AppCompatEditText) sheetView.findViewById(R.id.mobileNoEt);
        mobileNoEt.setText(filterMobileNo);
        final AppCompatEditText et_mobileNoEt = (AppCompatEditText) sheetView.findViewById(R.id.childMobileNoEt);
        et_mobileNoEt.setText(filterMobileNoNew);
        LinearLayout topChooserView = (LinearLayout) sheetView.findViewById(R.id.topChooserView);
        final AppCompatTextView topChooser = (AppCompatTextView) sheetView.findViewById(R.id.topChooser);
        topChooser.setText(filterTopValue + "");
        LinearLayout refundStatusChooserView = (LinearLayout) sheetView.findViewById(R.id.refundStatusChooserView);
        final AppCompatTextView refundStatusChooser = (AppCompatTextView) sheetView.findViewById(R.id.refundStatusChooser);
        LinearLayout dateTypeChooserView = (LinearLayout) sheetView.findViewById(R.id.dateTypeChooserView);
        final AppCompatTextView dateTypeChooser = (AppCompatTextView) sheetView.findViewById(R.id.dateTypeChooser);
        LinearLayout criteriaChooserView = (LinearLayout) sheetView.findViewById(R.id.criteriaChooserView);
        final AppCompatTextView criteriaChooser = (AppCompatTextView) sheetView.findViewById(R.id.criteriaChooser);
        LinearLayout criteriaView = (LinearLayout) sheetView.findViewById(R.id.criteriaView);
        final TextView criteriaTitle = (TextView) sheetView.findViewById(R.id.criteriaTitle);
        final TextView tv_headertitle = (TextView) sheetView.findViewById(R.id.tv_headertitle);
        final AppCompatEditText criteriaEt = (AppCompatEditText) sheetView.findViewById(R.id.criteriaEt);

        final LinearLayout transactionIdView=sheetView.findViewById(R.id.transactionIdView);
        final EditText et_transactionId=sheetView.findViewById(R.id.transactionIdEt);
        final TextView transactionIdTitle=sheetView.findViewById(R.id.transactionIdTitle);

        Button filter = (Button) sheetView.findViewById(R.id.filter);


        if (dialogType.equalsIgnoreCase("RechargeReport"))
        {
            ll_mobileNoFilter.setVisibility(View.GONE);
            mobileView.setVisibility(View.VISIBLE);
            criteriaChooserView.setVisibility(View.GONE);
            topChooserView.setVisibility(View.GONE);
            dateTypeChooserView.setVisibility(View.GONE);
            criteriaChooserView.setVisibility(View.GONE);
            criteriaView.setVisibility(View.GONE);

        }
        else if (dialogType.equalsIgnoreCase("DthSubscription"))
        {
            mobileView.setVisibility(View.GONE);
            walletTypeView.setVisibility(View.GONE);
            transactionIdView.setVisibility(View.VISIBLE);
            // accountNoView.setVisibility(View.VISIBLE);
            refundStatusChooserView.setVisibility(View.VISIBLE);
            if (rollID.equalsIgnoreCase("3") || rollID.equalsIgnoreCase("2")) {
                //   childMobileView.setVisibility(View.GONE);
            } else {
                //  childMobileView.setVisibility(View.VISIBLE);
            }
            // statusTitleTv.setText("Choose Subscription Status");
            refundStatusChooser.setText("Choose Subscription Status");
            // accountNoTitle.setText("Account Number");
            // accountNoEt.setHint("Account Number");
            dateTypeChooserView.setVisibility(View.GONE);
            //modeChooserView.setVisibility(View.GONE);
            criteriaChooserView.setVisibility(View.GONE);
            criteriaView.setVisibility(View.GONE);
            //refundStatusChooser.setText(filterStatus != null && !filterStatus.isEmpty() ? filterStatus : dthSubscriptionStatusArray[0]);



        }
        else if (dialogType.equalsIgnoreCase("Recharge")) {
            mobileView.setVisibility(View.GONE);
            ll_mobileNoFilter.setVisibility(View.GONE);
            refundStatusChooser.setText(filterStatus != null && !filterStatus.isEmpty() ? filterStatus : disputeStatusArray[0]);
            dateTypeChooser.setText(filterDateType != null && !filterDateType.isEmpty() ? filterDateType : disputeDateTypeArray[0]);
            criteriaChooser.setText(filterChooseCriteria != null && !filterChooseCriteria.isEmpty() ? filterChooseCriteria : disputeCriteriaArray[0]);
            criteriaTitle.setText(filterChooseCriteriaId != 0 ? "Enter " + filterChooseCriteria : "Enter Criteria");
            criteriaEt.setText(filterEnterCriteria);
            criteriaEt.setHint(criteriaTitle.getText());
            walletTypeView.setVisibility(View.GONE);

        }
        else if (dialogType.equalsIgnoreCase("Dispute")) {
            mobileView.setVisibility(View.GONE);
            ll_mobileNoFilter.setVisibility(View.GONE);
            refundStatusChooser.setText(filterStatus != null && !filterStatus.isEmpty() ? filterStatus : disputeStatusArray[0]);
            dateTypeChooser.setText(filterDateType != null && !filterDateType.isEmpty() ? filterDateType : disputeDateTypeArray[0]);
            criteriaChooser.setText(filterChooseCriteria != null && !filterChooseCriteria.isEmpty() ? filterChooseCriteria : disputeCriteriaArray[0]);
            criteriaTitle.setText(filterChooseCriteriaId != 0 ? "Enter " + filterChooseCriteria : "Enter Criteria");
            //criteriaEt.setText(filterEnterCriteria);
            criteriaEt.setHint(criteriaTitle.getText());
            walletTypeView.setVisibility(View.GONE);

        }
        else if (dialogType.equalsIgnoreCase("W2R")) {
            mobileView.setVisibility(View.GONE);
            ll_mobileNoFilter.setVisibility(View.GONE);
            ll_mobileNoFilter.setVisibility(View.GONE);
            refundStatusChooser.setText(filterStatus != null && !filterStatus.isEmpty() ? filterStatus : disputeStatusArray[0]);
            dateTypeChooser.setText(filterDateType != null && !filterDateType.isEmpty() ? filterDateType : disputeDateTypeArray[0]);
            criteriaChooser.setText(filterChooseCriteria != null && !filterChooseCriteria.isEmpty() ? filterChooseCriteria : disputeCriteriaArray[0]);
            criteriaTitle.setText(filterChooseCriteriaId != 0 ? "Enter " + filterChooseCriteria : "Enter Criteria");
            criteriaEt.setText(filterEnterCriteria);
            criteriaEt.setHint(criteriaTitle.getText());
            walletTypeView.setVisibility(View.GONE);

        }
        else if (dialogType.equalsIgnoreCase("Ledger")) {
            mobileView.setVisibility(View.VISIBLE);
            ll_mobileNoFilter.setVisibility(View.VISIBLE);
            mobileNumberLeftIcon.setImageResource(R.drawable.ic_number);
            mobileNumTitle.setText("Transaction Id");
            mobileNoEt.setHint("Enter Transaction Id");
            mobileNoEt.setInputType(InputType.TYPE_CLASS_TEXT);
            refundStatusChooserView.setVisibility(View.GONE);
            dateTypeChooserView.setVisibility(View.GONE);
            criteriaChooserView.setVisibility(View.GONE);
            criteriaView.setVisibility(View.GONE);

            if (UtilMethods.INSTANCE.getWalletType(mActivity) != null && !UtilMethods.INSTANCE.getWalletType(mActivity).isEmpty()) {
                WalletTypeResponse mWalletTypeResponse = new Gson().fromJson(UtilMethods.INSTANCE.getWalletType(mActivity), WalletTypeResponse.class);
                if (mWalletTypeResponse != null && mWalletTypeResponse.getWalletTypes() != null && mWalletTypeResponse.getWalletTypes().size() > 0) {
                    walletTypesArray = new String[mWalletTypeResponse.getWalletTypes().size()];
                    for (int i = 0; i < mWalletTypeResponse.getWalletTypes().size(); i++) {
                        walletTypesMap.put(mWalletTypeResponse.getWalletTypes().get(i).getName(), mWalletTypeResponse.getWalletTypes().get(i).getId());
                        walletTypesArray[i] = mWalletTypeResponse.getWalletTypes().get(i).getName();
                    }
                    if (filterWalletType != null && !filterWalletType.isEmpty()) {
                        walletTypeChooser.setText(filterWalletType);
                    } else {
                        walletTypeChooser.setText(walletTypesArray[0]);
                    }
                    walletTypeView.setVisibility(View.VISIBLE);
                } else {
                    walletTypeView.setVisibility(View.GONE);
                    UtilMethods.INSTANCE.WalletType(mActivity, null,null);
                }
            } else {
                walletTypeView.setVisibility(View.GONE);
                UtilMethods.INSTANCE.WalletType(mActivity, null,null);
            }
            walletTypeChooser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int selectedIndex = 0;
                    if (walletTypeChooser.getText().toString().length() == 0) {
                        selectedIndex = 0;
                    } else {
                        selectedIndex = Arrays.asList(walletTypesArray).indexOf(walletTypeChooser.getText().toString());
                    }
                    showSingleChoiceAlert(walletTypesArray, selectedIndex, "Wallet Type", "Choose Wallet Type", new SingleChoiceDialogCallBack() {
                        @Override
                        public void onPositiveClick(int index) {
                            walletTypeChooser.setText(walletTypesArray[index]);
                        }

                        @Override
                        public void onNegativeClick() {

                        }
                    });
                }
            });


        }


        criteriaChooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedIndex = 0;
                if (criteriaChooser.getText().toString().length() == 0) {
                    selectedIndex = 0;
                } else {
                    selectedIndex = Arrays.asList(disputeCriteriaArray).indexOf(criteriaChooser.getText().toString());
                }
                showSingleChoiceAlert(disputeCriteriaArray, selectedIndex, "Choose Criteria", "Choose Any Criteria", new SingleChoiceDialogCallBack() {
                    @Override
                    public void onPositiveClick(int index) {
                        criteriaChooser.setText(disputeCriteriaArray[index]);
                        if (index == 0) {
                            criteriaTitle.setText("Enter Criteria");
                            criteriaEt.setHint("Enter Criteria");
                        } else {
                            criteriaTitle.setText("Enter " + disputeCriteriaArray[index]);
                            criteriaEt.setHint("Enter " + disputeCriteriaArray[index]);
                        }
                    }

                    @Override
                    public void onNegativeClick() {

                    }
                });
            }
        });

        topChooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedIndex = 0;
                if (topChooser.getText().toString().length() == 0) {
                    selectedIndex = 0;
                } else {
                    selectedIndex = Arrays.asList(ledgerChooseTopArray).indexOf(topChooser.getText().toString());
                }
                showSingleChoiceAlert(ledgerChooseTopArray, selectedIndex, "Select Top", "Choose Top", new SingleChoiceDialogCallBack() {
                    @Override
                    public void onPositiveClick(int index) {
                        topChooser.setText(ledgerChooseTopArray[index]);
                    }

                    @Override
                    public void onNegativeClick() {

                    }
                });
            }
        });
        dateTypeChooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedIndex = 0;
                if (dateTypeChooser.getText().toString().length() == 0) {
                    selectedIndex = 0;
                } else {
                    selectedIndex = Arrays.asList(disputeDateTypeArray).indexOf(dateTypeChooser.getText().toString());
                }
                showSingleChoiceAlert(disputeDateTypeArray, selectedIndex, "Date Type", "Choose Date Type", new SingleChoiceDialogCallBack() {
                    @Override
                    public void onPositiveClick(int index) {
                        dateTypeChooser.setText(disputeDateTypeArray[index]);
                    }

                    @Override
                    public void onNegativeClick() {

                    }
                });
            }
        });

        refundStatusChooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedIndex = 0;
                if (refundStatusChooser.getText().toString().length() == 0) {
                    selectedIndex = 0;
                } else {
                    selectedIndex = Arrays.asList(disputeStatusArray).indexOf(refundStatusChooser.getText().toString());
                }
                showSingleChoiceAlert(disputeStatusArray, selectedIndex, "Status", "Choose Status", new SingleChoiceDialogCallBack() {
                    @Override
                    public void onPositiveClick(int index) {
                        refundStatusChooser.setText(disputeStatusArray[index]);
                    }

                    @Override
                    public void onNegativeClick() {

                    }
                });
            }
        });
        startDateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDCFromDate(startDate, endDate);
            }
        });
        endDateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDCToDate(startDate, endDate);
            }
        });
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
                if (mLedgerFilterCallBack != null) {

                    if (dialogType.equalsIgnoreCase("Dispute")) {
                        mLedgerFilterCallBack.onSubmitClick(startDate.getText().toString(),
                                endDate.getText().toString(),
                                mobileNoEt.getText().toString(),
                                topChooser.getText().toString().equalsIgnoreCase("ALL") ? 5000 : Integer.parseInt(topChooser.getText().toString()),
                                disputeStatusMap.get(refundStatusChooser.getText().toString()),
                                refundStatusChooser.getText().toString(),
                                disputeDateTypeMap.get(dateTypeChooser.getText().toString()),
                                dateTypeChooser.getText().toString(),
                                disputeCriteriaMap.get(criteriaChooser.getText().toString()),
                                criteriaChooser.getText().toString(),
                                criteriaEt.getText().toString(), walletTypeView.getVisibility() == View.VISIBLE ? walletTypesMap.get(walletTypeChooser.getText().toString()) : 0, walletTypeChooser.getText().toString(),"");
                    }
                    else if (dialogType.equalsIgnoreCase("W2R")) {
                        mLedgerFilterCallBack.onSubmitClick(startDate.getText().toString(),
                                endDate.getText().toString(),
                                mobileView.getVisibility()==View.VISIBLE?mobileNoEt.getText().toString():"",
                                topChooser.getText().toString().equalsIgnoreCase("ALL") ? 5000 : Integer.parseInt(topChooser.getText().toString()),
                                disputeStatusMap.get(refundStatusChooser.getText().toString()),
                                refundStatusChooser.getText().toString(),
                                disputeDateTypeMap.get(dateTypeChooser.getText().toString()),
                                dateTypeChooser.getText().toString(),
                                disputeCriteriaMap.get(criteriaChooser.getText().toString()),
                                criteriaChooser.getText().toString(),
                                criteriaEt.getText().toString(),
                                walletTypeView.getVisibility() == View.VISIBLE ? walletTypesMap.get(walletTypeChooser.getText().toString()) : 0, walletTypeChooser.getText().toString(),"");
                    }
                    else if (dialogType.equalsIgnoreCase("Ledger")) {
                        mLedgerFilterCallBack.onSubmitClick(startDate.getText().toString(),
                                endDate.getText().toString(),
                                mobileNoEt.getText().toString(),
                                topChooser.getText().toString().equalsIgnoreCase("ALL") ? 5000 : Integer.parseInt(topChooser.getText().toString()),
                                0, "", 0, "", 0, "", "", walletTypeView.getVisibility() == View.VISIBLE ? walletTypesMap.get(walletTypeChooser.getText().toString()) : 0, walletTypeChooser.getText().toString(),et_mobileNoEt.getText().toString());
                    }
                    else if (dialogType.equalsIgnoreCase("AEPS")) {
                        mLedgerFilterCallBack.onSubmitClick(startDate.getText().toString(),
                                endDate.getText().toString(),
                                mobileNoEt.getText().toString(),
                                topChooser.getText().toString().equalsIgnoreCase("ALL") ? 5000 : Integer.parseInt(topChooser.getText().toString()),
                                0, "", 0, "", 0, "", "", walletTypeView.getVisibility() == View.VISIBLE ? walletTypesMap.get(walletTypeChooser.getText().toString()) : 0, walletTypeChooser.getText().toString(),et_mobileNoEt.getText().toString());
                    }
                }
            }
        });
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();

    }


    public void openAEPSRechargeDMRFilter(final String dialogType, final String filterFromDate, String filterToDate, String filterChildNumber, final String filterMobileNo,final String transcationId, int filterTopValue,final AEPSDMRAndRechargeFilterCallBack filterCallBack)
    {
        final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(mActivity);
        View sheetView = mActivity.getLayoutInflater().inflate(R.layout.ledger_filter, null);
        LinearLayout container = (LinearLayout) sheetView.findViewById(R.id.container);
        LinearLayout childNumLayout = (LinearLayout) sheetView.findViewById(R.id.childMobileView);
        ImageView mobileNumberLeftIcon = sheetView.findViewById(R.id.mobileNumberLeftIcon);
        LinearLayout startDateView = (LinearLayout) sheetView.findViewById(R.id.startDateView);
        AppCompatTextView accountNumTitle = sheetView.findViewById(R.id.mobileNumTitle);
        final AppCompatTextView startDate = (AppCompatTextView) sheetView.findViewById(R.id.startDate);
        startDate.setText(filterFromDate);
        LinearLayout endDateView = (LinearLayout) sheetView.findViewById(R.id.endDateView);
        final AppCompatTextView endDate = (AppCompatTextView) sheetView.findViewById(R.id.endDate);
        endDate.setText(filterToDate);
        final LinearLayout walletTypeView = (LinearLayout) sheetView.findViewById(R.id.walletTypeView);
        final AppCompatTextView walletTypeChooser = (AppCompatTextView) sheetView.findViewById(R.id.walletTypeChooser);
        final LinearLayout mobileView = (LinearLayout) sheetView.findViewById(R.id.mobileView);
        final AppCompatEditText accountNumET = (AppCompatEditText) sheetView.findViewById(R.id.mobileNoEt);
        /*accountNumET.setText(filterMobileNo);*/
        final AppCompatEditText childNumET = (AppCompatEditText) sheetView.findViewById(R.id.childMobileNoEt);
        /*childNumET.setText(filterMobileNo);*/
        LinearLayout topChooserView = (LinearLayout) sheetView.findViewById(R.id.topChooserView);
        final AppCompatTextView topChooser = (AppCompatTextView) sheetView.findViewById(R.id.topChooser);
        topChooser.setText(filterTopValue + "");
        LinearLayout refundStatusChooserView = (LinearLayout) sheetView.findViewById(R.id.refundStatusChooserView);
        final AppCompatTextView refundStatusChooser = (AppCompatTextView) sheetView.findViewById(R.id.refundStatusChooser);
        LinearLayout dateTypeChooserView = (LinearLayout) sheetView.findViewById(R.id.dateTypeChooserView);
        final AppCompatTextView dateTypeChooser = (AppCompatTextView) sheetView.findViewById(R.id.dateTypeChooser);
        LinearLayout criteriaChooserView = (LinearLayout) sheetView.findViewById(R.id.criteriaChooserView);
        final AppCompatTextView criteriaChooser = (AppCompatTextView) sheetView.findViewById(R.id.criteriaChooser);
        LinearLayout criteriaView = (LinearLayout) sheetView.findViewById(R.id.criteriaView);
        final TextView criteriaTitle = (TextView) sheetView.findViewById(R.id.criteriaTitle);
        final TextView childNumTitle = (TextView) sheetView.findViewById(R.id.tv_headertitle);
        final AppCompatEditText criteriaEt = (AppCompatEditText) sheetView.findViewById(R.id.criteriaEt);

        final LinearLayout transactionIdView=sheetView.findViewById(R.id.transactionIdView);
        final EditText et_transactionId=sheetView.findViewById(R.id.transactionIdEt);
        final TextView transactionIdTitle=sheetView.findViewById(R.id.transactionIdTitle);

        Button filter = (Button) sheetView.findViewById(R.id.filter);


        if (dialogType.equalsIgnoreCase("AEPS"))
        {
            refundStatusChooserView.setVisibility(View.GONE);
            criteriaChooserView.setVisibility(View.GONE);
            dateTypeChooserView.setVisibility(View.GONE);
            criteriaView.setVisibility(View.GONE);
            mobileView.setVisibility(View.VISIBLE);
            transactionIdView.setVisibility(View.VISIBLE);
            accountNumTitle.setText("Account No/Recharge No."); //accountNo
            accountNumET.setHint("Enter Account No/Recharge No.");

            transactionIdTitle.setText("Transaction Id"); //Transaction ID
            et_transactionId.setHint("Enter Transaction Id");

            if(rollID!=null && rollID.equalsIgnoreCase("3"))//Retailer
            {
                childNumLayout.setVisibility(View.GONE);

            }
            else
            {
                childNumLayout.setVisibility(View.VISIBLE);
                childNumTitle.setText("Child Mobile No.");
                childNumET.setHint("Enter Child Mobile No.");

            }
        }
        else  if (dialogType.equalsIgnoreCase("RechargeReport") ||dialogType.equalsIgnoreCase("DMT") )
        {
            refundStatusChooserView.setVisibility(View.GONE);
            criteriaChooserView.setVisibility(View.GONE);
            dateTypeChooserView.setVisibility(View.GONE);
            criteriaView.setVisibility(View.GONE);
            mobileView.setVisibility(View.VISIBLE);
            transactionIdView.setVisibility(View.VISIBLE);
            accountNumTitle.setText("Account No/Recharge No."); //accountNo
            accountNumET.setHint("Enter Account No/Recharge No.");

            transactionIdTitle.setText("Transaction Id"); //Transaction ID
            et_transactionId.setHint("Enter Transaction Id");

            if(rollID!=null && rollID.equalsIgnoreCase("3"))//Retailer
            {
                childNumLayout.setVisibility(View.GONE);

            }
            else
            {
                childNumLayout.setVisibility(View.VISIBLE);
                childNumTitle.setText("Child Mobile No.");
                childNumET.setHint("Enter Child Mobile No.");

            }
        }


        topChooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedIndex = 0;
                if (topChooser.getText().toString().length() == 0) {
                    selectedIndex = 0;
                } else {
                    selectedIndex = Arrays.asList(ledgerChooseTopArray).indexOf(topChooser.getText().toString());
                }
                showSingleChoiceAlert(ledgerChooseTopArray, selectedIndex, "Select Top", "Choose Top", new SingleChoiceDialogCallBack() {
                    @Override
                    public void onPositiveClick(int index) {
                        topChooser.setText(ledgerChooseTopArray[index]);
                    }

                    @Override
                    public void onNegativeClick() {

                    }
                });
            }
        });

        startDateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDCFromDate(startDate, endDate);
            }
        });
        endDateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDCToDate(startDate, endDate);
            }
        });
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
                if (filterCallBack != null) {
                    if (dialogType.equalsIgnoreCase("AEPS")) {
                        filterCallBack.onSubmitClick(
                                startDate.getText().toString(),
                                endDate.getText().toString(),
                                childNumET.getText().toString(),
                                accountNumET.getText().toString(),
                                et_transactionId.getText().toString(),
                                topChooser.getText().toString().equalsIgnoreCase("ALL") ? 5000 : Integer.parseInt(topChooser.getText().toString())
                        );

                        //RechargeReport
                    }
                    else if (dialogType.equalsIgnoreCase("RechargeReport") || dialogType.equalsIgnoreCase("DMT")) {
                        filterCallBack.onSubmitClick(
                                startDate.getText().toString(),
                                endDate.getText().toString(),
                                childNumET.getText().toString(),
                                accountNumET.getText().toString(),
                                et_transactionId.getText().toString(),
                                topChooser.getText().toString().equalsIgnoreCase("ALL") ? 5000 : Integer.parseInt(topChooser.getText().toString())
                        );

                        //RechargeReport
                    }
                    else  if (dialogType.equalsIgnoreCase("")) {
                        filterCallBack.onSubmitClick(
                                startDate.getText().toString(),
                                endDate.getText().toString(),
                                childNumET.getText().toString(),
                                accountNumET.getText().toString(),
                                et_transactionId.getText().toString(),
                                topChooser.getText().toString().equalsIgnoreCase("ALL") ? 5000 : Integer.parseInt(topChooser.getText().toString())
                        );
                    }
                }
            }
        });
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();

    }


    public interface AEPSDMRAndRechargeFilterCallBack {

        void onSubmitClick(String fromDate, String toDate, String childNo,String accountNo,String transactionID,int topRows);
    }

    public interface DthSubscriptionFilterCallBack {

        void onSubmitClick(String fromDate, String toDate, String mobileNo, String transactionId, String childMobileNo,
                           String accountNo, int topValue, int statusId, String statusValue, int bookingStatusId,
                           String bookingStatusValue);
    }

    public interface LedgerReportFosFilterCallBack {

        void onSubmitClick(String fromDate, String toDate, int topValue);
    }

    public interface MoveToBankReportFilterCallBack {

        void onSubmitClick(String fromDate, String toDate, String childMobileNo, String transactionId, int topValue, int statusId, String statusValue, int modeId, String modeValue);
    }

}
