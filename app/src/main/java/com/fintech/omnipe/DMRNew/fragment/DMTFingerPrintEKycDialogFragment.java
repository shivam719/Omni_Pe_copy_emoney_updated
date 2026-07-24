package com.fintech.omnipe.DMRNew.fragment;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.widget.TextViewCompat;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.snackbar.Snackbar;
import com.fintech.omnipe.AEPS.FingPay.dto.Resp;
import com.fintech.omnipe.R;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class DMTFingerPrintEKycDialogFragment extends DialogFragment {


    TextView errorTv, clickView;
    View closeBtn, loaderView;
    EditText etAadharNo;
    LinearLayout mantraView, mantraL1View, morphoView, morphoL1View, tatvikView, startekView, precisionView, secugenView;
    TextView sender_num, mantraTv, mantraL1Tv, morphoTv, morphoL1Tv, tatvikTv, startekTv, precisionTv, secugenTv;
    Activity mActivity;
    /*String msgIs2f;*/


    private int INTENT_READ_DEVICE = 876;

    private int selectedDevicePos = -1;
    private String senderNumber="";
    private String wadhValue;
    private BottomSheetCallBack mBottomSheetCallBack;


    /*public static AEPSFingerPrintEKycActivity newInstance() {
        return new AEPSFingerPrintEKycActivity();
    }*/

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.dialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_d_m_t_finger_print_e_kyc_dialog, container, false);

        setCancelable(false);
        clickView = v.findViewById(R.id.clickView);
        loaderView = v.findViewById(R.id.loaderView);

        errorTv = v.findViewById(R.id.errorTv);

        closeBtn = v.findViewById(R.id.closeBtn);
        sender_num = v.findViewById(R.id.sender_num);
        etAadharNo = v.findViewById(R.id.etAadharNo);
        mantraView = v.findViewById(R.id.mantraView);
        mantraL1View = v.findViewById(R.id.mantraL1View);
        morphoView = v.findViewById(R.id.morphoView);
        morphoL1View = v.findViewById(R.id.morphoL1View);
        tatvikView = v.findViewById(R.id.tatvikView);
        startekView = v.findViewById(R.id.startekView);
        precisionView = v.findViewById(R.id.precisionView);
        secugenView = v.findViewById(R.id.secugenView);
        mantraTv = v.findViewById(R.id.mantraTv);
        mantraL1Tv = v.findViewById(R.id.mantraL1Tv);
        morphoTv = v.findViewById(R.id.morphoTv);
        morphoL1Tv = v.findViewById(R.id.morphoL1Tv);
        tatvikTv = v.findViewById(R.id.tatvikTv);
        startekTv = v.findViewById(R.id.startekTv);
        precisionTv = v.findViewById(R.id.precisionTv);
        secugenTv = v.findViewById(R.id.secugenTv);
        sender_num.setText(senderNumber);


        closeBtn.setOnClickListener(view -> dismiss());
        clickView.setOnClickListener(view -> fetchDetaill());


        mantraView.setOnClickListener(view -> {
            if (selectedDevicePos != 0) {
                selectedDevicePos = 0;
                errorTv.setVisibility(View.GONE);
                mantraView.setBackgroundResource(R.drawable.rounded_primary_border);

                morphoView.setBackgroundResource(0);
                tatvikView.setBackgroundResource(0);
                startekView.setBackgroundResource(0);
                precisionView.setBackgroundResource(0);
                secugenView.setBackgroundResource(0);

                mantraTv.setTextColor(getResources().getColor(R.color.colorPrimary));
                morphoTv.setTextColor(getResources().getColor(R.color.grey));
                tatvikTv.setTextColor(getResources().getColor(R.color.grey));
                startekTv.setTextColor(getResources().getColor(R.color.grey));
                precisionTv.setTextColor(getResources().getColor(R.color.grey));
                secugenTv.setTextColor(getResources().getColor(R.color.grey));

                mantraL1View.setBackgroundResource(0);
                mantraL1Tv.setTextColor(getResources().getColor(R.color.grey));
                morphoL1View.setBackgroundResource(0);
                morphoL1Tv.setTextColor(getResources().getColor(R.color.grey));
            }

        });
        mantraL1View.setOnClickListener(view -> {
            if (selectedDevicePos != 1) {
                selectedDevicePos = 1;
                errorTv.setVisibility(View.GONE);
                mantraL1View.setBackgroundResource(R.drawable.rounded_primary_border);
                mantraView.setBackgroundResource(0);
                morphoView.setBackgroundResource(0);
                tatvikView.setBackgroundResource(0);
                startekView.setBackgroundResource(0);
                precisionView.setBackgroundResource(0);
                secugenView.setBackgroundResource(0);


                mantraL1Tv.setTextColor(getResources().getColor(R.color.colorPrimary));
                mantraTv.setTextColor(getResources().getColor(R.color.grey));
                morphoTv.setTextColor(getResources().getColor(R.color.grey));
                tatvikTv.setTextColor(getResources().getColor(R.color.grey));
                startekTv.setTextColor(getResources().getColor(R.color.grey));
                precisionTv.setTextColor(getResources().getColor(R.color.grey));
                secugenTv.setTextColor(getResources().getColor(R.color.grey));

                morphoL1View.setBackgroundResource(0);
                morphoL1Tv.setTextColor(getResources().getColor(R.color.grey));
            }

        });
        morphoView.setOnClickListener(view -> {
            if (selectedDevicePos != 2) {
                selectedDevicePos = 2;
                errorTv.setVisibility(View.GONE);
                morphoView.setBackgroundResource(R.drawable.rounded_primary_border);
                mantraView.setBackgroundResource(0);
                tatvikView.setBackgroundResource(0);
                startekView.setBackgroundResource(0);
                precisionView.setBackgroundResource(0);
                secugenView.setBackgroundResource(0);

                morphoTv.setTextColor(getResources().getColor(R.color.colorPrimary));
                mantraTv.setTextColor(getResources().getColor(R.color.grey));
                tatvikTv.setTextColor(getResources().getColor(R.color.grey));
                startekTv.setTextColor(getResources().getColor(R.color.grey));
                precisionTv.setTextColor(getResources().getColor(R.color.grey));
                secugenTv.setTextColor(getResources().getColor(R.color.grey));
                mantraL1View.setBackgroundResource(0);
                mantraL1Tv.setTextColor(getResources().getColor(R.color.grey));
                morphoL1View.setBackgroundResource(0);
                morphoL1Tv.setTextColor(getResources().getColor(R.color.grey));

            }
        });
        morphoL1View.setOnClickListener(view -> {
            if (selectedDevicePos != 3) {
                selectedDevicePos = 3;
                errorTv.setVisibility(View.GONE);
                morphoL1View.setBackgroundResource(R.drawable.rounded_primary_border);
                morphoView.setBackgroundResource(0);
                mantraView.setBackgroundResource(0);
                tatvikView.setBackgroundResource(0);
                startekView.setBackgroundResource(0);
                precisionView.setBackgroundResource(0);
                secugenView.setBackgroundResource(0);


                morphoL1Tv.setTextColor(getResources().getColor(R.color.colorPrimary));
                morphoTv.setTextColor(getResources().getColor(R.color.grey));
                mantraTv.setTextColor(getResources().getColor(R.color.grey));
                tatvikTv.setTextColor(getResources().getColor(R.color.grey));
                startekTv.setTextColor(getResources().getColor(R.color.grey));
                precisionTv.setTextColor(getResources().getColor(R.color.grey));
                secugenTv.setTextColor(getResources().getColor(R.color.grey));

                mantraL1View.setBackgroundResource(0);
                mantraL1Tv.setTextColor(getResources().getColor(R.color.grey));

            }
        });
        tatvikView.setOnClickListener(view -> {
            if (selectedDevicePos != 4) {
                selectedDevicePos = 4;
                errorTv.setVisibility(View.GONE);
                tatvikView.setBackgroundResource(R.drawable.rounded_primary_border);
                morphoView.setBackgroundResource(0);
                mantraView.setBackgroundResource(0);
                startekView.setBackgroundResource(0);
                precisionView.setBackgroundResource(0);
                secugenView.setBackgroundResource(0);


                tatvikTv.setTextColor(getResources().getColor(R.color.colorPrimary));
                morphoTv.setTextColor(getResources().getColor(R.color.grey));
                mantraTv.setTextColor(getResources().getColor(R.color.grey));
                startekTv.setTextColor(getResources().getColor(R.color.grey));
                precisionTv.setTextColor(getResources().getColor(R.color.grey));
                secugenTv.setTextColor(getResources().getColor(R.color.grey));


                mantraL1View.setBackgroundResource(0);
                mantraL1Tv.setTextColor(getResources().getColor(R.color.grey));
                morphoL1View.setBackgroundResource(0);
                morphoL1Tv.setTextColor(getResources().getColor(R.color.grey));
            }
        });
        startekView.setOnClickListener(view -> {
            if (selectedDevicePos != 5) {
                selectedDevicePos = 5;
                errorTv.setVisibility(View.GONE);
                startekView.setBackgroundResource(R.drawable.rounded_primary_border);
                morphoView.setBackgroundResource(0);
                tatvikView.setBackgroundResource(0);
                mantraView.setBackgroundResource(0);
                precisionView.setBackgroundResource(0);
                secugenView.setBackgroundResource(0);


                startekTv.setTextColor(getResources().getColor(R.color.colorPrimary));
                morphoTv.setTextColor(getResources().getColor(R.color.grey));
                tatvikTv.setTextColor(getResources().getColor(R.color.grey));
                mantraTv.setTextColor(getResources().getColor(R.color.grey));
                precisionTv.setTextColor(getResources().getColor(R.color.grey));
                secugenTv.setTextColor(getResources().getColor(R.color.grey));


                mantraL1View.setBackgroundResource(0);
                mantraL1Tv.setTextColor(getResources().getColor(R.color.grey));
                morphoL1View.setBackgroundResource(0);
                morphoL1Tv.setTextColor(getResources().getColor(R.color.grey));
            }
        });
        precisionView.setOnClickListener(view -> {
            if (selectedDevicePos != 6) {
                selectedDevicePos = 6;
                errorTv.setVisibility(View.GONE);
                precisionView.setBackgroundResource(R.drawable.rounded_primary_border);
                morphoView.setBackgroundResource(0);
                tatvikView.setBackgroundResource(0);
                startekView.setBackgroundResource(0);
                mantraView.setBackgroundResource(0);
                secugenView.setBackgroundResource(0);

                precisionTv.setTextColor(getResources().getColor(R.color.colorPrimary));
                morphoTv.setTextColor(getResources().getColor(R.color.grey));
                tatvikTv.setTextColor(getResources().getColor(R.color.grey));
                startekTv.setTextColor(getResources().getColor(R.color.grey));
                mantraTv.setTextColor(getResources().getColor(R.color.grey));
                secugenTv.setTextColor(getResources().getColor(R.color.grey));


                mantraL1View.setBackgroundResource(0);
                mantraL1Tv.setTextColor(getResources().getColor(R.color.grey));
                morphoL1View.setBackgroundResource(0);
                morphoL1Tv.setTextColor(getResources().getColor(R.color.grey));
            }
        });
        secugenView.setOnClickListener(view -> {
            if (selectedDevicePos != 7) {
                selectedDevicePos = 7;
                errorTv.setVisibility(View.GONE);
                secugenView.setBackgroundResource(R.drawable.rounded_primary_border);
                morphoView.setBackgroundResource(0);
                tatvikView.setBackgroundResource(0);
                startekView.setBackgroundResource(0);
                precisionView.setBackgroundResource(0);
                mantraView.setBackgroundResource(0);

                secugenTv.setTextColor(getResources().getColor(R.color.colorPrimary));
                morphoTv.setTextColor(getResources().getColor(R.color.grey));
                tatvikTv.setTextColor(getResources().getColor(R.color.grey));
                startekTv.setTextColor(getResources().getColor(R.color.grey));
                precisionTv.setTextColor(getResources().getColor(R.color.grey));
                mantraTv.setTextColor(getResources().getColor(R.color.grey));

                mantraL1View.setBackgroundResource(0);
                mantraL1Tv.setTextColor(getResources().getColor(R.color.grey));
                morphoL1View.setBackgroundResource(0);
                morphoL1Tv.setTextColor(getResources().getColor(R.color.grey));
            }
        });


        /*if (msgIs2f != null && !msgIs2f.isEmpty()) {
            errorTv.setVisibility(View.VISIBLE);
            errorTv.setText(msgIs2f);
        }*/
        return v;
    }


    void fetchDetaill() {
        if (selectedDevicePos == -1) {
            errorTv.setVisibility(View.VISIBLE);
            errorTv.setText("Please select any one device");
            return;
        }
        errorTv.setVisibility(View.GONE);


        if (selectedDevicePos == 0) {
            // mantra();
            clickView.setVisibility(View.GONE);
            loaderView.setVisibility(View.VISIBLE);
            readDevice("com.mantra.rdservice", "com.mantra.rdservice.RDServiceActivity",
                    "Mantra", null);
        }
        if (selectedDevicePos == 1) {
            // mantraL1();
            clickView.setVisibility(View.GONE);
            loaderView.setVisibility(View.VISIBLE);
            readDevice("com.mantra.mfs110.rdservice", "com.mantra.mfs110.rdservice.RDServiceActivity",
                    "Mantra L1", null);
        } else if (selectedDevicePos == 2) {
            // marpho();
            clickView.setVisibility(View.GONE);
            loaderView.setVisibility(View.VISIBLE);
            String pidData;
            if (wadhValue != null && !wadhValue.isEmpty()) {
                pidData = "<?xml version=\"1.0\"?> <PidOptions ver=\"1.0\"> <Opts fCount=\"1\" fType=\"2\" iCount=\"0\" pCount=\"0\" format=\"0\" pidVer=\"2.0\" timeout=\"10000\" otp=\"\" wadh=\"" + wadhValue + "\" posh=\"UNKNOWN\" env=\"P\" /> <CustOpts><Param name=\"marphokey\" value=\"\" /></CustOpts> </PidOptions>";
            } else {
                pidData = "<?xml version=\"1.0\"?> <PidOptions ver=\"1.0\"> <Opts fCount=\"1\" fType=\"2\" iCount=\"0\" pCount=\"0\" format=\"0\" pidVer=\"2.0\" timeout=\"10000\" otp=\"\"  posh=\"UNKNOWN\" env=\"P\" /> <CustOpts><Param name=\"marphokey\" value=\"\" /></CustOpts> </PidOptions>";

            }
            readDevice("com.scl.rdservice", "com.scl.rdservice.FingerCaptureActivity",
                    "Marpho", pidData);
        } else if (selectedDevicePos == 3) {
            // marpho L1();
            clickView.setVisibility(View.GONE);
            loaderView.setVisibility(View.VISIBLE);
            String pidData;
            if (wadhValue != null && !wadhValue.isEmpty()) {
                pidData = "<?xml version=\"1.0\"?> <PidOptions ver=\"1.0\"> <Opts fCount=\"1\" fType=\"2\" iCount=\"0\" pCount=\"0\" format=\"0\" pidVer=\"2.0\" timeout=\"10000\" otp=\"\" wadh=\"" + wadhValue + "\" posh=\"UNKNOWN\" env=\"P\" /> <CustOpts><Param name=\"marphokey\" value=\"\" /></CustOpts> </PidOptions>";

            } else {
                pidData = "<?xml version=\"1.0\"?> <PidOptions ver=\"1.0\"> <Opts fCount=\"1\" fType=\"2\" iCount=\"0\" pCount=\"0\" format=\"0\" pidVer=\"2.0\" timeout=\"10000\" otp=\"\"  posh=\"UNKNOWN\" env=\"P\" /> <CustOpts><Param name=\"marphokey\" value=\"\" /></CustOpts> </PidOptions>";

            }
            readDevice("com.idemia.l1rdservice", "com.morpho.registerdeviceservice.CaptureAndInfoActivity", "Marpho L1", pidData);
        } else if (selectedDevicePos == 4) {
            // tatvik();
            clickView.setVisibility(View.GONE);
            loaderView.setVisibility(View.VISIBLE);
            readDevice("com.tatvik.bio.tmf20", "com.tatvik.bio.tmf20.RDMainActivity",
                    "Tatvik", null);
        } else if (selectedDevicePos == 5) {
            // startek();
            clickView.setVisibility(View.GONE);
            loaderView.setVisibility(View.VISIBLE);
            readDevice("com.acpl.registersdk", "com.acpl.registersdk.MainActivity",
                    "Startek", null);
        } else if (selectedDevicePos == 6) {
            //  precision();
            Toast.makeText(getActivity(), "Coming Soon", Toast.LENGTH_SHORT).show();

            /*clickView.setVisibility(View.GONE);
            loaderView.setVisibility(View.VISIBLE);
            String pidData = "<PidOptions ver=\"1.0\"> <Opts env=\"P\" fCount=\"1\" fType=\"0\" format=\"0\" pidVer=\"2.0\" wadh=\"E0jzJ/P8UopUHAieZn8CKqS4WPMi5ZSYXgfnlfkWjrc=\" posh=\"UNKNOWN\" timeout=\"10000\" /> </PidOptions>";
            readDevice("com.precision.pb510.rdservice", "com.precision.rdservice.CaptureActivity",
                    "Precision", pidData);*/
        } else if (selectedDevicePos == 7) {
            // secugen();
            clickView.setVisibility(View.GONE);
            loaderView.setVisibility(View.VISIBLE);
            readDevice("com.secugen.rdservice", "com.secugen.rdservice.Capture", "Secugen", null);
        }


    }


    private void readDevice(String packageName, String serviceName, String name, String piddata) {
        errorTv.setVisibility(View.GONE);
        if (NUL(packageName, getActivity().getPackageManager())) {
            String pidData;
            if (wadhValue != null && !wadhValue.isEmpty()) {
                //E0jzJ/P8UopUHAieZn8CKqS4WPMi5ZSYXgfnlfkWjrc=
                pidData = "<?xml version=\"1.0\"?> <PidOptions ver=\"1.0\"> <Opts fCount=\"1\" fType=\"2\" iCount=\"0\" pCount=\"0\" pgCount=\"2\" format=\"0\" pidVer=\"2.0\" timeout=\"10000\" pTimeout=\"20000\" wadh=\"" + wadhValue + "\" posh=\"UNKNOWN\" env=\"P\" ></Opts> <CustOpts><Param name=\"" + name + "key\" value=\"\" /></CustOpts> </PidOptions>";

            } else {
                pidData = "<?xml version=\"1.0\"?> <PidOptions ver=\"1.0\"> <Opts fCount=\"1\" fType=\"2\" iCount=\"0\" pCount=\"0\" pgCount=\"2\" format=\"0\" pidVer=\"2.0\" timeout=\"10000\" pTimeout=\"20000\"  posh=\"UNKNOWN\" env=\"P\" ></Opts> <CustOpts><Param name=\"" + name + "key\" value=\"\" /></CustOpts> </PidOptions>";

            }
            if (piddata != null && !piddata.isEmpty()) {
                pidData = piddata;
            }
            Intent localIntent = new Intent();
            localIntent.setComponent(new ComponentName(packageName, serviceName));
            localIntent.setAction("in.gov.uidai.rdservice.fp.CAPTURE");
            localIntent.putExtra("PID_OPTIONS", pidData);
            startActivityForResult(localIntent, INTENT_READ_DEVICE);

        } else {
            clickView.setVisibility(View.VISIBLE);
            loaderView.setVisibility(View.GONE);
            openServiceOnPlay(name, packageName);
        }
    }

    void openServiceOnPlay(String name, String packageName) {
        new AlertDialog.Builder(getActivity())
                .setTitle("Get Service")
                .setMessage(name + " RD Services Not Found.Click OK to Download Now.")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    try {
                        startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)));

                    } catch (Exception localException) {
                        errorTv.setVisibility(View.VISIBLE);
                        errorTv.setText("Something went wrong. Please try again later.");
                        localException.printStackTrace();
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                /*.setIcon(android.R.drawable.ic_dialog_alert)*/
                .show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == INTENT_READ_DEVICE) {
            clickView.setVisibility(View.VISIBLE);
            loaderView.setVisibility(View.GONE);
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    errorTv.setVisibility(View.GONE);
                    String pidData = data.getStringExtra("PID_DATA");
                    pidData(pidData);
                } else {
                    errorTv.setVisibility(View.VISIBLE);
                    errorTv.setText("Didn't receive any data");
                }
            } else {
                errorTv.setVisibility(View.VISIBLE);
                errorTv.setText("Canceled");
            }
        }
    }


    public void pidData(String paramString) {

        Resp mResp = new Resp();

        try {
            if (paramString != null && !paramString.isEmpty() && paramString.contains("<PidData>")) {
                DocumentBuilderFactory localDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder localDocumentBuilder = localDocumentBuilderFactory.newDocumentBuilder();
                Document localDocument = localDocumentBuilder.parse(new ByteArrayInputStream(paramString.getBytes("UTF-8")));
                NodeList localNodeList1 = localDocument.getElementsByTagName("Resp");
                Element localElement2 = (Element) localNodeList1.item(0);
                mResp.setErrCode(localElement2.getAttribute("errCode"));
                mResp.setErrInfo(localElement2.getAttribute("errInfo"));

                if (mResp.getErrCode().equalsIgnoreCase("0") && paramString.contains("<Hmac>")) {
                    // NUL(paramRelativeLayout, "Finger Captured Successfully!", getResources().getColor(R.color.green));
                    Toast.makeText(getActivity(), "Finger Captured Successfully!", Toast.LENGTH_SHORT).show();
                    //dismiss();

                    loaderView.setVisibility(View.VISIBLE);
                    errorTv.setVisibility(View.GONE);
                    if (mBottomSheetCallBack != null) {
                        mBottomSheetCallBack.onClickCapture(etAadharNo.getText().toString().trim(), paramString);
                    }
                } else {
                    errorTv.setVisibility(View.VISIBLE);
                    errorTv.setText("Error Code : " + mResp.getErrCode() + "\n" +
                            "Error Message : " + mResp.getErrInfo() + "");
                }
            } else {
                errorTv.setVisibility(View.VISIBLE);
                errorTv.setText("Didn't receive any data");
            }
        } catch (Exception e) {
            errorTv.setVisibility(View.VISIBLE);
            errorTv.setText(e.getMessage() + "");
            e.printStackTrace();
        }

    }


    void NUL(String s) {
        new AlertDialog.Builder(getActivity())
                .setTitle("Mini ATM Response")
                .setMessage(s)
                /* .setPositiveButton(android.R.string.yes, (dialog, which) -> finish())*/
                .setNegativeButton(android.R.string.yes, null)
                /*.setIcon(android.R.drawable.ic_dialog_alert)*/
                .show();
    }

    public void NUL(View paramView, String paramString, int paramInt) {
        Snackbar localSnackbar = Snackbar.make(paramView, "" + paramString, Snackbar.LENGTH_LONG);
        View snackBarView = localSnackbar.getView();
        snackBarView.setBackgroundColor(paramInt);
        TextView mainTextView = snackBarView.findViewById(com.google.android.material.R.id.snackbar_text);
        mainTextView.setMaxLines(5);
        TextViewCompat.setTextAppearance(mainTextView, R.style.TextAppearance_AppCompat_Body2);
        localSnackbar.show();

    }

    private String NUL(String paramString, Element paramElement) {
        NodeList localNodeList = paramElement.getElementsByTagName(paramString).item(0).getChildNodes();
        Node localNode = localNodeList.item(0);
        return localNode.getNodeValue();
    }

    public static boolean NUL(String paramString, PackageManager paramPackageManager) {
        try {
            paramPackageManager.getPackageInfo(paramString, 0);
            return true;
        } catch (PackageManager.NameNotFoundException localNameNotFoundException) {
        }
        return false;
    }


    public void setData(final Activity context, String senderNumber, String wadhValue, BottomSheetCallBack mBottomSheetCallBack) {

        this.mActivity = context;
        this.senderNumber = senderNumber;
        this.wadhValue = wadhValue;
        this.mBottomSheetCallBack = mBottomSheetCallBack;

    }


    public void hideLoader() {
        loaderView.setVisibility(View.GONE);
        errorTv.setVisibility(View.GONE);
    }

    public void setError(String msg) {

        loaderView.setVisibility(View.GONE);
        errorTv.setVisibility(View.VISIBLE);
        errorTv.setText(msg);
    }
}
