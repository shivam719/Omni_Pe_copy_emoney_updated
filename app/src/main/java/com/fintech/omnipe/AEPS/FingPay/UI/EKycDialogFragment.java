package com.fintech.omnipe.AEPS.FingPay.UI;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.widget.TextViewCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.fintech.omnipe.AEPS.FingPay.dto.PidDataResp;
import com.fintech.omnipe.Auth.dto.LoginResponse;
import com.fintech.omnipe.R;
import com.fintech.omnipe.Util.GetLocation;
import com.fintech.omnipe.Util.UtilMethods;
import com.fintech.omnipe.usefull.CustomLoader;
import com.google.android.material.snackbar.Snackbar;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import static android.app.Activity.RESULT_OK;

public class EKycDialogFragment extends DialogFragment {

    TextView errorTv, clickView;
    View closeBtn, loaderView;
    LinearLayout deviceContainerView,mantraView, morphoView, tatvikView, startekView, precisionView, secugenView, evoluteView;
    CardView mantraCardView,tatvikCardView,morphoCardView,evoluteCardView,starteCardkView,precisionCardView,secugenCardView;
    TextView mantraTv, morphoTv, tatvikTv, startekTv, precisionTv, secugenTv, evoluteTv;
    Activity contextCOB;
    FragmentManager fragmentManagerCOB;
    int  oIdCOB;
    String otpRefIDCOB = "0";
    CustomLoader mProgressDialogCOB;
    LoginResponse LoginDataResponseCOB;
    private int bioAuthTypeCOB;
    UtilMethods.ApiCallBackOnBoardingMethod mApiCallBackCOB;

    private final int INTENT_READ_DEVICE =876;
    private int selectedDevicePos = -1;
    private GetLocation mGetLocation;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setStyle(DialogFragment.STYLE_NORMAL, R.style.dialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ekyc_dialog, container, false);

        setCancelable(false);
        deviceContainerView = v.findViewById(R.id.deviceContainerView);
        clickView = v.findViewById(R.id.clickView);
        loaderView = v.findViewById(R.id.loaderView);
        errorTv = v.findViewById(R.id.errorTv);
        closeBtn = v.findViewById(R.id.closeBtn);
        mantraView = v.findViewById(R.id.mantraView);
        mantraCardView = v.findViewById(R.id.mantraCardView);
        morphoView = v.findViewById(R.id.morphoView);
        morphoCardView = v.findViewById(R.id.morphoCardView);
        tatvikCardView = v.findViewById(R.id.tatvikCardView);
        tatvikView = v.findViewById(R.id.tatvikView);
        startekView = v.findViewById(R.id.startekView);
        starteCardkView = v.findViewById(R.id.starteCardkView);
        evoluteView = v.findViewById(R.id.evoluteView);
        evoluteCardView = v.findViewById(R.id.evoluteCardView);
        precisionView = v.findViewById(R.id.precisionView);
        precisionCardView = v.findViewById(R.id.precisionCardView);
        secugenView = v.findViewById(R.id.secugenView);
        secugenCardView = v.findViewById(R.id.secugenCardView);

        mantraTv = v.findViewById(R.id.mantraTv);
        morphoTv = v.findViewById(R.id.morphoTv);
        tatvikTv = v.findViewById(R.id.tatvikTv);
        startekTv = v.findViewById(R.id.startekTv);
        precisionTv = v.findViewById(R.id.precisionTv);
        secugenTv = v.findViewById(R.id.secugenTv);
        evoluteTv = v.findViewById(R.id.evoluteTv);

        mGetLocation = new GetLocation(requireActivity(), null);
        closeBtn.setOnClickListener(view -> dismiss());
        clickView.setOnClickListener(view -> fetchDetaill());


        mantraView.setOnClickListener(view -> {
            if (selectedDevicePos != 0) {
                selectedDevicePos = 0;
                errorTv.setVisibility(View.GONE);

                setClickedView(mantraView,mantraCardView);

                morphoCardView.setMaxCardElevation(0);
                tatvikCardView.setMaxCardElevation(0);
                evoluteCardView.setMaxCardElevation(0);
                starteCardkView.setMaxCardElevation(0);
                precisionCardView.setMaxCardElevation(0);
                secugenCardView.setMaxCardElevation(0);


            }

        });
        morphoView.setOnClickListener(view -> {
            if (selectedDevicePos != 1) {
                selectedDevicePos = 1;
                errorTv.setVisibility(View.GONE);

                setClickedView(morphoView,morphoCardView);

                mantraCardView.setMaxCardElevation(0);
                tatvikCardView.setMaxCardElevation(0);
                evoluteCardView.setMaxCardElevation(0);
                starteCardkView.setMaxCardElevation(0);
                precisionCardView.setMaxCardElevation(0);
                secugenCardView.setMaxCardElevation(0);

            }
        });
        tatvikView.setOnClickListener(view -> {
            if (selectedDevicePos != 2) {
                selectedDevicePos = 2;
                errorTv.setVisibility(View.GONE);
                setClickedView(tatvikView,tatvikCardView);

                mantraCardView.setMaxCardElevation(0);
                morphoCardView.setMaxCardElevation(0);
                evoluteCardView.setMaxCardElevation(0);
                starteCardkView.setMaxCardElevation(0);
                precisionCardView.setMaxCardElevation(0);
                secugenCardView.setMaxCardElevation(0);
            }
        });
        startekView.setOnClickListener(view -> {
            if (selectedDevicePos != 3) {
                selectedDevicePos = 3;
                errorTv.setVisibility(View.GONE);

                setClickedView(startekView,starteCardkView);

                mantraCardView.setMaxCardElevation(0);
                morphoCardView.setMaxCardElevation(0);
                evoluteCardView.setMaxCardElevation(0);
                precisionCardView.setMaxCardElevation(0);
                secugenCardView.setMaxCardElevation(0);
            }
        });
        precisionView.setOnClickListener(view -> {
            if (selectedDevicePos != 4) {
                selectedDevicePos = 4;
                errorTv.setVisibility(View.GONE);

                setClickedView(precisionView,precisionCardView);
                tatvikCardView.setMaxCardElevation(0);
                mantraCardView.setMaxCardElevation(0);
                morphoCardView.setMaxCardElevation(0);
                evoluteCardView.setMaxCardElevation(0);
                starteCardkView.setMaxCardElevation(0);
                secugenCardView.setMaxCardElevation(0);

            }
        });
        secugenView.setOnClickListener(view -> {
            if (selectedDevicePos != 5) {
                selectedDevicePos = 5;
                errorTv.setVisibility(View.GONE);
                setClickedView(secugenView,secugenCardView);
                tatvikCardView.setMaxCardElevation(0);
                mantraCardView.setMaxCardElevation(0);
                morphoCardView.setMaxCardElevation(0);
                evoluteCardView.setMaxCardElevation(0);
                starteCardkView.setMaxCardElevation(0);
                precisionCardView.setMaxCardElevation(0);

            }
        });
        evoluteView.setOnClickListener(view -> {
            if (selectedDevicePos != 6) {
                selectedDevicePos = 6;
                errorTv.setVisibility(View.GONE);
                setClickedView(evoluteView,evoluteCardView);

                tatvikCardView.setMaxCardElevation(0);
                mantraCardView.setMaxCardElevation(0);
                morphoCardView.setMaxCardElevation(0);
                secugenCardView.setMaxCardElevation(0);
                starteCardkView.setMaxCardElevation(0);
                precisionCardView.setMaxCardElevation(0);

            }
        });

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
            readDevice("com.mantra.mfs110.rdservice", "com.mantra.mfs110.rdservice.RDServiceActivity",
                    "Mantra L1", null);
        } else if (selectedDevicePos == 1) {
            // marpho();
            clickView.setVisibility(View.GONE);
            loaderView.setVisibility(View.VISIBLE);

            String pidData = getPidDataMorphoL1();
            readDevice("com.idemia.l1rdservice", "com.morpho.registerdeviceservice.CaptureAndInfoActivity",
                    "Marpho L1", pidData);
        } else if (selectedDevicePos == 2) {
            // tatvik();
            clickView.setVisibility(View.GONE);
            loaderView.setVisibility(View.VISIBLE);
            readDevice("com.tatvik.bio.tmf20", "com.tatvik.bio.tmf20.RDMainActivity",
                    "Tatvik", null);
        } else if (selectedDevicePos == 3) {
            // startek();
            clickView.setVisibility(View.GONE);
            loaderView.setVisibility(View.VISIBLE);
            readDevice("com.acpl.registersdk_l1", "com.acpl.registersdk_l1.MainActivity",
                    "Startek L1", null);
        } else if (selectedDevicePos == 4) {
            //  precision();
            Toast.makeText(getActivity(), "Coming Soon", Toast.LENGTH_SHORT).show();

            /*clickView.setVisibility(View.GONE);
            loaderView.setVisibility(View.VISIBLE);
            String pidData = "<PidOptions ver=\"1.0\"> <Opts env=\"P\" fCount=\"1\" fType=\"0\" format=\"0\" pidVer=\"2.0\" wadh=\"E0jzJ/P8UopUHAieZn8CKqS4WPMi5ZSYXgfnlfkWjrc=\" posh=\"UNKNOWN\" timeout=\"10000\" /> </PidOptions>";
            readDevice("com.precision.pb510.rdservice", "com.precision.rdservice.CaptureActivity",
                    "Precision", pidData);*/
        } else if (selectedDevicePos == 5) {
            // secugen();
            clickView.setVisibility(View.GONE);
            loaderView.setVisibility(View.VISIBLE);
            readDevice("com.secugen.rdservice", "com.secugen.rdservice.Capture", "Secugen", null);
        } else if (selectedDevicePos == 6) {
            // evolute();
            clickView.setVisibility(View.GONE);
            loaderView.setVisibility(View.VISIBLE);
            readDevice("com.evolute.rdservice", "com.evolute.rdservice.RDserviceActivity", "Evolute", null);
        }
    }

    @NonNull
    private String getPidDataMorphoL1() {
        String pidData;
        if (bioAuthTypeCOB == 2) {
            pidData = "<?xml version=\"1.0\"?> <PidOptions ver=\"1.0\"> <Opts fCount=\"1\" fType=\"2\" iCount=\"0\" pCount=\"0\" format=\"0\" pidVer=\"2.0\" timeout=\"10000\" otp=\"\" wadh=\"E0jzJ/P8UopUHAieZn8CKqS4WPMi5ZSYXgfnlfkWjrc=\" posh=\"UNKNOWN\" env=\"P\" /> <CustOpts><Param name=\"marphokey\" value=\"\" /></CustOpts> </PidOptions>";

        } else {
            pidData = "<?xml version=\"1.0\"?> <PidOptions ver=\"1.0\"> <Opts fCount=\"1\" fType=\"2\" iCount=\"0\" pCount=\"0\" format=\"0\" pidVer=\"2.0\" timeout=\"10000\" otp=\"\" posh=\"UNKNOWN\" env=\"P\" /> <CustOpts><Param name=\"marphokey\" value=\"\" /></CustOpts> </PidOptions>";

        }
        return pidData;
    }

    private void readDevice(String packageName, String serviceName, String name, String piddata) {
        errorTv.setVisibility(View.GONE);
        if (NUL(packageName, getActivity().getPackageManager())) {
            String pidData="<?xml version=\"1.0\"?> <PidOptions ver=\"1.0\"> <Opts fCount=\"1\" fType=\"2\" iCount=\"0\" pCount=\"0\" pgCount=\"2\" format=\"0\" pidVer=\"2.0\" timeout=\"10000\" pTimeout=\"20000\" posh=\"UNKNOWN\" env=\"P\" ></Opts> <CustOpts><Param name=\"" + name + "key\" value=\"\" /></CustOpts> </PidOptions>";
            if(bioAuthTypeCOB==2){
                pidData = "<?xml version=\"1.0\"?> <PidOptions ver=\"1.0\"> <Opts fCount=\"1\" fType=\"2\" iCount=\"0\" pCount=\"0\" pgCount=\"2\" format=\"0\" pidVer=\"2.0\" timeout=\"10000\" pTimeout=\"20000\" wadh=\"E0jzJ/P8UopUHAieZn8CKqS4WPMi5ZSYXgfnlfkWjrc=\" posh=\"UNKNOWN\" env=\"P\" ></Opts> <CustOpts><Param name=\"" + name + "key\" value=\"\" /></CustOpts> </PidOptions>";
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

        new AlertDialog.Builder(requireActivity())
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

    /*private void marpho() {

        if (NUL("com.scl.rdservice", getPackageManager())) {
            String pidData = "<?xml version=\"1.0\"?> <PidOptions ver=\"1.0\"> <Opts fCount=\"1\" fType=\"0\" iCount=\"0\" pCount=\"0\" format=\"0\" pidVer=\"2.0\" timeout=\"10000\" posh=\"UNKNOWN\" env=\"P\" /> <CustOpts><Param name=\"mantrakey\" value=\"\" /></CustOpts> </PidOptions>";

            //String pidData = "<?xml version=\"1.0\"?> <PidOptions ver=\"1.0\"> <Opts fCount=\"1\" fType=\"0\" iCount=\"0\" pCount=\"0\" pgCount=\"2\" format=\"0\"   pidVer=\"2.0\" timeout=\"10000\" pTimeout=\"20000\" posh=\"UNKNOWN\" env=\"P\" ></Opts> <CustOpts><Param name=\"mantrakey\" value=\"\" /></CustOpts> </PidOptions>";

            Intent localIntent = new Intent();
            localIntent.setComponent(new ComponentName("com.scl.rdservice", "com.scl.rdservice.FingerCaptureActivity"));
            localIntent.setAction("in.gov.uidai.rdservice.fp.CAPTURE");
            localIntent.putExtra("PID_OPTIONS", pidData);
            startActivityForResult(localIntent, INTENT_READ_DEVICE);

        } else {
            loader.dismiss();
            new AlertDialog.Builder(this)
                    .setTitle("Get Service")
                    .setMessage("Morpho RD Services Not Found.Click OK to Download Now.")
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                        try {
                            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=com.scl.rdservice")));
                        } catch (Exception localException) {
                            NUL(findViewById(R.id.mainView), "Something went wrong. Please try again later.", Color.RED);
                            localException.printStackTrace();
                        }
                    })
                    .setNegativeButton(android.R.string.no, null)

                    .show();


        }
    }

    private void mantra() {

        String pidData = "<?xml version=\"1.0\"?> <PidOptions ver=\"1.0\"> <Opts fCount=\"1\" fType=\"0\" iCount=\"0\" pCount=\"0\" pgCount=\"2\" format=\"0\" pidVer=\"2.0\" timeout=\"10000\" pTimeout=\"20000\" posh=\"UNKNOWN\" env=\"P\" ></Opts> <CustOpts><Param name=\"mantrakey\" value=\"\" /></CustOpts> </PidOptions>";
        if (NUL("com.mantra.rdservice", getPackageManager())) {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.mantra.rdservice", "com.mantra.rdservice.RDServiceActivity"));
            intent.setAction("in.gov.uidai.rdservice.fp.CAPTURE");
            intent.putExtra("PID_OPTIONS", pidData);
            startActivityForResult(intent, INTENT_READ_DEVICE);
        } else {
            loader.dismiss();
            new AlertDialog.Builder(this)
                    .setTitle("Get Service")
                    .setMessage("Mantra RD Services Not Found.Click OK to Download Now.")
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                        try {
                            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=com.mantra.rdservice")));
                        } catch (Exception localException) {
                            NUL(findViewById(R.id.mainView), "Something went wrong. Please try again later.", Color.RED);
                            localException.printStackTrace();
                        }
                    })
                    .setNegativeButton(android.R.string.no, null)

                    .show();


        }
    }

    private void tatvik() {

        String pidData = "<?xml version=\"1.0\"?> <PidOptions ver=\"1.0\"> <Opts fCount=\"1\" fType=\"0\" iCount=\"0\" pCount=\"0\" pgCount=\"2\" format=\"0\" pidVer=\"2.0\" timeout=\"10000\" pTimeout=\"20000\" posh=\"UNKNOWN\" env=\"P\" ></Opts> <CustOpts><Param name=\"mantrakey\" value=\"\" /></CustOpts> </PidOptions>";
        if (NUL("com.tatvik.bio.tmf20", getPackageManager())) {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.tatvik.bio.tmf20", "com.tatvik.bio.tmf20.RDMainActivity"));
            intent.setAction("in.gov.uidai.rdservice.fp.CAPTURE");
            intent.putExtra("PID_OPTIONS", pidData);
            startActivityForResult(intent, INTENT_READ_DEVICE);
        } else {
            loader.dismiss();
            new AlertDialog.Builder(this)
                    .setTitle("Get Service")
                    .setMessage("Tatvik RD Services Not Found.Click OK to Download Now.")
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                        try {
                            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=com.tatvik.bio.tmf20")));
                        } catch (Exception localException) {
                            NUL(findViewById(R.id.mainView), "Something went wrong. Please try again later.", Color.RED);
                            localException.printStackTrace();
                        }
                    })
                    .setNegativeButton(android.R.string.no, null)

                    .show();


        }
    }


    private void startek() {

        String pidData = "<?xml version=\"1.0\"?> <PidOptions ver=\"1.0\"> <Opts fCount=\"1\" fType=\"0\" iCount=\"0\" pCount=\"0\" pgCount=\"2\" format=\"0\" pidVer=\"2.0\" timeout=\"10000\" pTimeout=\"20000\" posh=\"UNKNOWN\" env=\"P\" ></Opts> <CustOpts><Param name=\"startekkey\" value=\"\" /></CustOpts> </PidOptions>";
        if (NUL("com.acpl.registersdk", getPackageManager())) {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.acpl.registersdk", "com.acpl.registersdk.MainActivity"));
            intent.setAction("in.gov.uidai.rdservice.fp.CAPTURE");
            intent.putExtra("PID_OPTIONS", pidData);
            startActivityForResult(intent, INTENT_READ_DEVICE);
        } else {
            loader.dismiss();
            new AlertDialog.Builder(this)
                    .setTitle("Get Service")
                    .setMessage("Startek RD Services Not Found.Click OK to Download Now.")
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                        try {
                            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=com.acpl.registersdk")));
                        } catch (Exception localException) {
                            NUL(findViewById(R.id.mainView), "Something went wrong. Please try again later.", Color.RED);
                            localException.printStackTrace();
                        }
                    })
                    .setNegativeButton(android.R.string.no, null)

                    .show();


        }
    }*/

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

        PidDataResp mResp = new PidDataResp();

        try {
            if (paramString != null && paramString.contains("<PidData>")) {
                DocumentBuilderFactory localDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder localDocumentBuilder = localDocumentBuilderFactory.newDocumentBuilder();
                Document localDocument = localDocumentBuilder.parse(new ByteArrayInputStream(paramString.getBytes(StandardCharsets.UTF_8)));
                NodeList localNodeList1 = localDocument.getElementsByTagName("Resp");
                Element localElement2 = (Element) localNodeList1.item(0);
                mResp.setErrCode(localElement2.getAttribute("errCode"));
                mResp.setErrInfo(localElement2.getAttribute("errInfo"));

                if (mResp.getErrCode().equalsIgnoreCase("0") && paramString.contains("<Hmac>")) {
                    // NUL(paramRelativeLayout, "Finger Captured Successfully!", getResources().getColor(R.color.green));
                    Toast.makeText(getActivity(), "Finger Captured Successfully!", Toast.LENGTH_SHORT).show();
                    dismiss();
                    if (mProgressDialogCOB != null) {
                        mProgressDialogCOB.show();
                    }

                    if (UtilMethods.getLattitude != 0 && UtilMethods.getLongitude != 0) {
                        UtilMethods.INSTANCE.CallOnboarding(contextCOB,false,oIdCOB, "", otpRefIDCOB, paramString,bioAuthTypeCOB,true ,fragmentManagerCOB,null,null,null, UtilMethods.getLattitude, UtilMethods.getLongitude, mProgressDialogCOB, mApiCallBackCOB);
                    } else {
                        if (mGetLocation != null) {
                            mGetLocation.startLocationUpdates((lattitude, longitude) -> {
                                UtilMethods.getLattitude = lattitude;
                                UtilMethods.getLongitude = longitude;
                                UtilMethods.INSTANCE.CallOnboarding(contextCOB,false,oIdCOB, "", otpRefIDCOB, paramString,bioAuthTypeCOB,true ,fragmentManagerCOB,null,null,null, UtilMethods.getLattitude, UtilMethods.getLongitude,mProgressDialogCOB, mApiCallBackCOB);
                            });
                        } else {
                            mGetLocation = new GetLocation(requireActivity(), null);
                            mGetLocation.startLocationUpdates((lattitude, longitude) -> {
                                UtilMethods.getLattitude = lattitude;
                                UtilMethods.getLongitude = longitude;
                                UtilMethods.INSTANCE.CallOnboarding(contextCOB,false,oIdCOB, "", otpRefIDCOB, paramString,bioAuthTypeCOB,true ,fragmentManagerCOB,null,null,null, UtilMethods.getLattitude, UtilMethods.getLongitude,mProgressDialogCOB, mApiCallBackCOB);
                            });
                        }
                    }



                } else {
                    errorTv.setVisibility(View.VISIBLE);
                    errorTv.setText("Error Code : " + mResp.getErrCode() + "\n" +
                            "Error Message : " + mResp.getErrInfo());
                }
            } else {
                errorTv.setVisibility(View.VISIBLE);
                errorTv.setText("Didn't receive any data");
            }
        } catch (Exception e) {
            errorTv.setVisibility(View.VISIBLE);
            errorTv.setText(e.getMessage());
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
        Snackbar localSnackbar = Snackbar.make(paramView, paramString, Snackbar.LENGTH_LONG);
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


    public void setOnBoardingData(final Activity context, FragmentManager fragmentManager, int oid,
                                  String otpRefID, CustomLoader mProgressDialog,
                                  LoginResponse LoginDataResponse, int bioAuthType, UtilMethods.ApiCallBackOnBoardingMethod mApiCallBack) {

        contextCOB = context;
        fragmentManagerCOB = fragmentManager;
        oIdCOB = oid;
        otpRefIDCOB = otpRefID;
        mProgressDialogCOB = mProgressDialog;
        LoginDataResponseCOB = LoginDataResponse;
        bioAuthTypeCOB = bioAuthType;
        mApiCallBackCOB = mApiCallBack;

    }

    @SuppressLint("Range")
    private void setClickedView(LinearLayout selectedView, CardView selectedCardView) {

        for (int selId = 0; selId < deviceContainerView.getChildCount(); selId++) {
            View child = deviceContainerView.getChildAt(selId);
            if (child instanceof CardView) {
                CardView cardView = (CardView) child;
                if (cardView == selectedCardView) {
                    selectedView.setBackgroundResource(R.drawable.rounded_border_primary);
                    selectedView.setAlpha(1.0f);
                    selectedCardView.setMaxCardElevation(6);
                    selectedCardView.setCardElevation(6);
                    if (selectedView.getChildCount() > 1 && selectedView.getChildAt(1) instanceof TextView) {
                        ((TextView) selectedView.getChildAt(1)).setTextColor(ContextCompat.getColor(requireContext(), R.color.colorPrimary));
                    }
                } else {
                    cardView.setMaxCardElevation(0);
                    cardView.setCardElevation(0);
                    if (cardView.getChildCount() > 0 && cardView.getChildAt(0) instanceof LinearLayout) {
                        LinearLayout unselectedView = (LinearLayout) cardView.getChildAt(0);
                        unselectedView.setBackgroundResource(R.drawable.rounded_primary);
                        unselectedView.setAlpha(1.0f);
                        if (unselectedView.getChildCount() > 1 && unselectedView.getChildAt(1) instanceof TextView) {
                            ((TextView) unselectedView.getChildAt(1)).setTextColor(ContextCompat.getColor(requireContext(), R.color.white));
                        }
                    }
                }
            }
        }
    }

}
