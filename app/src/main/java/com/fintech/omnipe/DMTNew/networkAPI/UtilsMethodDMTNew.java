package com.fintech.omnipe.DMTNew.networkAPI;

import android.app.Activity;
import android.widget.TextView;

import com.fintech.omnipe.Auth.dto.LoginResponse;
import com.google.gson.Gson;
import com.fintech.omnipe.Api.Response.RechargeReportResponse;
import com.fintech.omnipe.BuildConfig;
import com.fintech.omnipe.DMTNew.dto.DMTParam;
import com.fintech.omnipe.DMTNew.dto.GetBeneficiaryResponse;
import com.fintech.omnipe.DMTNew.dto.SenderRequest;
import com.fintech.omnipe.DMTNew.dto.SenderResponse;
import com.fintech.omnipe.Util.ApiClient;
import com.fintech.omnipe.Util.ApplicationConstant;
import com.fintech.omnipe.Util.GetLocation;
import com.fintech.omnipe.Util.UtilMethods;
import com.fintech.omnipe.usefull.CustomLoader;

import retrofit2.Call;
import retrofit2.Callback;

public enum UtilsMethodDMTNew {

    INSTANCE;


    public void getSenderCasa(Activity context, int oid, String MobileNumber, String sidValue, CustomLoader loader,
                              GetLocation mGetLocation, ApiCallBack mApiCallBack) {
        try {
            String loginPref = UtilMethods.INSTANCE.getLoginPref(context);
            LoginResponse mLoginDataResponse = new Gson().fromJson(loginPref, LoginResponse.class);
            EndPointInterfaceDMTNew git = ApiClient.getClient().create(EndPointInterfaceDMTNew.class);
            Call<SenderResponse> call = git.GetSenderCASA(new SenderRequest(UtilMethods.getLattitude, UtilMethods.getLattitude,
                    UtilMethods.getLongitude, oid, MobileNumber, sidValue, new DMTParam(MobileNumber, oid, UtilMethods.getLattitude,
                    UtilMethods.getLattitude, UtilMethods.getLongitude),
                    mLoginDataResponse.getData().getUserID(), mLoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, UtilMethods.INSTANCE.getIMEI(context), "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(context),
                    mLoginDataResponse.getData().getSessionID(), mLoginDataResponse.getData().getSession(), ""));

            call.enqueue(new Callback<SenderResponse>() {

                @Override
                public void onResponse(Call<SenderResponse> call, retrofit2.Response<SenderResponse> response) {

                    if (loader.isShowing())
                        loader.dismiss();
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getStatuscode() == 1) {
                                if (mApiCallBack != null) {
                                    mApiCallBack.onSuccess(response.body());
                                }

                            } else if (response.body().getStatuscode() == -1) {
                                if (mApiCallBack != null) {
                                    mApiCallBack.onSuccess(response.body());
                                }

                            } else {
                                UtilMethods.INSTANCE.getErrorHandler(context, response.body().isVersionValid(),
                                        true, response.body().getMsg(),
                                        mGetLocation, loader, () -> getSender(context, oid, MobileNumber, sidValue, loader,
                                                mGetLocation, mApiCallBack));
                            }

                        }
                    } else {
                        UtilMethods.INSTANCE.apiErrorHandle(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<SenderResponse> call, Throwable t) {

                    if (loader.isShowing())
                        loader.dismiss();
                    UtilMethods.INSTANCE.apiFailureError(context, t);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader.isShowing())
                loader.dismiss();
            UtilMethods.INSTANCE.Error(context, e.getMessage());
        }

    }

    public void getSender(Activity context, int oid, String MobileNumber, String sidValue, CustomLoader loader,
                          GetLocation mGetLocation, ApiCallBack mApiCallBack) {
        try {
            String loginPref = UtilMethods.INSTANCE.getLoginPref(context);
            LoginResponse mLoginDataResponse = new Gson().fromJson(loginPref, LoginResponse.class);
            EndPointInterfaceDMTNew git = ApiClient.getClient().create(EndPointInterfaceDMTNew.class);
            Call<SenderResponse> call = git.getSender(new SenderRequest(UtilMethods.getLattitude, UtilMethods.getLattitude,
                    UtilMethods.getLongitude, oid, MobileNumber, sidValue, new DMTParam(MobileNumber, oid, UtilMethods.getLattitude,
                    UtilMethods.getLattitude, UtilMethods.getLongitude),
                    mLoginDataResponse.getData().getUserID(), mLoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, UtilMethods.INSTANCE.getIMEI(context), "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(context),
                    mLoginDataResponse.getData().getSessionID(), mLoginDataResponse.getData().getSession(), ""));

            call.enqueue(new Callback<SenderResponse>() {

                @Override
                public void onResponse(Call<SenderResponse> call, retrofit2.Response<SenderResponse> response) {

                    if (loader.isShowing())
                        loader.dismiss();
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getStatuscode() == 1) {
                                if (mApiCallBack != null) {
                                    mApiCallBack.onSuccess(response.body());
                                }

                            } else {
                                UtilMethods.INSTANCE.getErrorHandler(context, response.body().isVersionValid(),
                                        true, response.body().getMsg(),
                                        mGetLocation, loader, () -> getSender(context, oid, MobileNumber, sidValue, loader,
                                                mGetLocation, mApiCallBack));
                            }

                        }
                    } else {
                        UtilMethods.INSTANCE.apiErrorHandle(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<SenderResponse> call, Throwable t) {

                    if (loader.isShowing())
                        loader.dismiss();
                    UtilMethods.INSTANCE.apiFailureError(context, t);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader.isShowing())
                loader.dismiss();
            UtilMethods.INSTANCE.Error(context, e.getMessage());
        }

    }

    public void doSenderEKYC(Activity context, int oid, String MobileNumber, String aadharNo, String pidData, String otp, String refid,
                             String sidValue, CustomLoader loader, GetLocation mGetLocation, ApiCallBackTwoMethod mApiCallBack) {
        try {
            String loginPref = UtilMethods.INSTANCE.getLoginPref(context);
            LoginResponse mLoginDataResponse = new Gson().fromJson(loginPref, LoginResponse.class);
            EndPointInterfaceDMTNew git = ApiClient.getClient().create(EndPointInterfaceDMTNew.class);
            Call<SenderResponse> call = git.doSenderEKYC(new SenderRequest(UtilMethods.getLattitude, UtilMethods.getLattitude,
                    UtilMethods.getLongitude, oid, MobileNumber, sidValue, new DMTParam(MobileNumber, aadharNo, pidData, oid,
                    UtilMethods.getLattitude, UtilMethods.getLattitude, UtilMethods.getLongitude, otp,
                    refid, refid),
                    mLoginDataResponse.getData().getUserID(), mLoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, UtilMethods.INSTANCE.getIMEI(context), "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(context),
                    mLoginDataResponse.getData().getSessionID(), mLoginDataResponse.getData().getSession(), ""));

            call.enqueue(new Callback<SenderResponse>() {

                @Override
                public void onResponse(Call<SenderResponse> call, retrofit2.Response<SenderResponse> response) {

                    if (loader.isShowing())
                        loader.dismiss();
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getStatuscode() == 1) {
                                if (mApiCallBack != null) {
                                    mApiCallBack.onSuccess(response.body());
                                }

                            } else {
                                if (mApiCallBack != null) {
                                    mApiCallBack.onError(response.body());
                                }
                                /*UtilMethods.INSTANCE.getErrorHandler(context, response.body().isVersionValid(),
                                        true, response.body().getMsg(),
                                        mGetLocation, loader, () -> doSenderEKYC( context,  oid,  MobileNumber,  aadharNo,  pidData, otp, refid,  sidValue,  loader,
                                         mGetLocation,  mApiCallBack));*/
                            }

                        }
                    } else {
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(response.message());
                        }
                        UtilMethods.INSTANCE.apiErrorHandle(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<SenderResponse> call, Throwable t) {
                    if (mApiCallBack != null) {
                        mApiCallBack.onError(t.getMessage());
                    }
                    if (loader.isShowing())
                        loader.dismiss();
                    UtilMethods.INSTANCE.apiFailureError(context, t);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader.isShowing())
                loader.dismiss();
            if (mApiCallBack != null) {
                mApiCallBack.onError(e.getMessage());
            }
            UtilMethods.INSTANCE.Error(context, e.getMessage());
        }

    }

    public void createSender(final Activity context, final int oid, final String MobileNumber, String sidValue, String firstName, String lastName,
                             final String otp, String refrenceId, final CustomLoader loader, GetLocation mGetLocation, ApiCallBack mApiCallBack) {
        try {
            String loginPref = UtilMethods.INSTANCE.getLoginPref(context);
            LoginResponse mLoginDataResponse = new Gson().fromJson(loginPref, LoginResponse.class);

            EndPointInterfaceDMTNew git = ApiClient.getClient().create(EndPointInterfaceDMTNew.class);
            Call<SenderResponse> call = git.createSender(new SenderRequest(UtilMethods.getLattitude, UtilMethods.getLattitude,
                    UtilMethods.getLongitude, oid, MobileNumber, sidValue, new DMTParam(MobileNumber,
                    oid, UtilMethods.getLattitude, UtilMethods.getLattitude, UtilMethods.getLongitude,
                    firstName, lastName, otp, refrenceId, refrenceId),
                    mLoginDataResponse.getData().getUserID(), mLoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, UtilMethods.INSTANCE.getIMEI(context), "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(context),
                    mLoginDataResponse.getData().getSessionID(), mLoginDataResponse.getData().getSession(), ""));

            call.enqueue(new Callback<SenderResponse>() {

                @Override
                public void onResponse(Call<SenderResponse> call, retrofit2.Response<SenderResponse> response) {
                    if (loader.isShowing())
                        loader.dismiss();
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getStatuscode() == 1) {
                                if (mApiCallBack != null) {
                                    mApiCallBack.onSuccess(response.body());
                                }
                            } else {
                                UtilMethods.INSTANCE.getErrorHandler(context, response.body().isVersionValid(),
                                        true, response.body().getMsg(),
                                        mGetLocation, loader, () -> createSender(context, oid, MobileNumber, sidValue, firstName, lastName,
                                                otp, refrenceId, loader, mGetLocation, mApiCallBack));
                            }

                        }
                    } else {
                        UtilMethods.INSTANCE.apiErrorHandle(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<SenderResponse> call, Throwable t) {

                    if (loader.isShowing())
                        loader.dismiss();
                    UtilMethods.INSTANCE.apiFailureError(context, t);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void getBeneficiary(final Activity context, int oid, String mobileNumber, String sidValue, final CustomLoader loader,
                               GetLocation mGetLocation,
                               ApiCallBack mApiCallBack) {
        try {
            String LoginResponse = UtilMethods.INSTANCE.getLoginPref(context);
            LoginResponse mLoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            EndPointInterfaceDMTNew git = ApiClient.getClient().create(EndPointInterfaceDMTNew.class);
            Call<GetBeneficiaryResponse> call = git.getBeneficiary(new SenderRequest(UtilMethods.getLattitude, UtilMethods.getLattitude,
                    UtilMethods.getLongitude, oid, mobileNumber, sidValue, new DMTParam(mobileNumber, oid, UtilMethods.getLattitude,
                    UtilMethods.getLattitude, UtilMethods.getLongitude),
                    mLoginDataResponse.getData().getUserID(), mLoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, UtilMethods.INSTANCE.getIMEI(context), "", BuildConfig.VERSION_NAME,
                    UtilMethods.INSTANCE.getSerialNo(context),
                    mLoginDataResponse.getData().getSessionID(), mLoginDataResponse.getData().getSession(), ""));

            call.enqueue(new Callback<GetBeneficiaryResponse>() {

                @Override
                public void onResponse(Call<GetBeneficiaryResponse> call, retrofit2.Response<GetBeneficiaryResponse> response) {

                    if (loader.isShowing())
                        loader.dismiss();
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getStatuscode() == 1) {
                                UtilMethods.INSTANCE.setBeneficiaryList(context, new Gson().toJson(response.body()));
                                if (mApiCallBack != null) {
                                    mApiCallBack.onSuccess(response.body());
                                }

                            } else {
                                UtilMethods.INSTANCE.getErrorHandler(context, response.body().isVersionValid(),
                                        true, response.body().getMsg(),
                                        mGetLocation, loader, () -> getBeneficiary(context, oid, mobileNumber, sidValue,
                                                loader, mGetLocation, mApiCallBack));
                            }

                        }
                    } else {
                        UtilMethods.INSTANCE.apiErrorHandle(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<GetBeneficiaryResponse> call, Throwable t) {

                    if (loader.isShowing())
                        loader.dismiss();
                    UtilMethods.INSTANCE.apiFailureError(context, t);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void verifyAccount(Activity context, int oid, String senderNo, String sidValue, String ifsc, String accountNo, String beneName,
                              String bankName, int bankId, CustomLoader loader, GetLocation mGetLocation, ApiCallBack mApiCallBack) {
        try {
            String LoginResponse = UtilMethods.INSTANCE.getLoginPref(context);
            LoginResponse mLoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            EndPointInterfaceDMTNew git = ApiClient.getClient().create(EndPointInterfaceDMTNew.class);
            Call<SenderResponse> call = git.verifyAccount(new SenderRequest(UtilMethods.getLattitude, UtilMethods.getLattitude,
                    UtilMethods.getLongitude, oid, senderNo, sidValue, new DMTParam(senderNo, oid,
                    UtilMethods.getLattitude, UtilMethods.getLattitude, UtilMethods.getLongitude,
                    beneName, accountNo, ifsc, bankId, bankName),
                    mLoginDataResponse.getData().getUserID(), mLoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, UtilMethods.INSTANCE.getIMEI(context), "", BuildConfig.VERSION_NAME,
                    UtilMethods.INSTANCE.getSerialNo(context),
                    mLoginDataResponse.getData().getSessionID(), mLoginDataResponse.getData().getSession(), ""));

            call.enqueue(new Callback<SenderResponse>() {

                @Override
                public void onResponse(Call<SenderResponse> call, retrofit2.Response<SenderResponse> response) {
                    if (loader.isShowing())
                        loader.dismiss();
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getStatuscode() == 2) {
                                UtilMethods.INSTANCE.Successful(context, "Verifications successfully done.");
                                if (mApiCallBack != null) {
                                    mApiCallBack.onSuccess(response.body());
                                }
                            } else {
                                UtilMethods.INSTANCE.getErrorHandler(context, response.body().isVersionValid(),
                                        true, response.body().getMsg(),
                                        mGetLocation, loader, () -> verifyAccount(context, oid, senderNo, sidValue, ifsc, accountNo, beneName,
                                                bankName, bankId, loader, mGetLocation, mApiCallBack));
                            }

                        }
                    } else {
                        UtilMethods.INSTANCE.apiErrorHandle(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<SenderResponse> call, Throwable t) {
                    if (loader.isShowing())
                        loader.dismiss();
                    UtilMethods.INSTANCE.apiFailureError(context, t);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void addBeneficiary(final Activity context, int oid, String sid, String senderNO, String dob, String address,
                               String pinCode, String beniName, String ifsc, String accountNo, int bankId, final CustomLoader loader,
                               GetLocation mGetLocation, ApiCallBack mApiCallBack) {
        try {
            String LoginResponse = UtilMethods.INSTANCE.getLoginPref(context);
            LoginResponse mLoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            EndPointInterfaceDMTNew git = ApiClient.getClient().create(EndPointInterfaceDMTNew.class);
            Call<SenderResponse> call = git.createBeneficiary(new SenderRequest(UtilMethods.getLattitude,
                    UtilMethods.getLattitude, UtilMethods.getLongitude,
                    oid, senderNO, sid,
                    new DMTParam(senderNO, oid, UtilMethods.getLattitude, UtilMethods.getLattitude, UtilMethods.getLongitude,
                            beniName, accountNo, ifsc, dob, address, pinCode, bankId),
                    mLoginDataResponse.getData().getUserID(), mLoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, UtilMethods.INSTANCE.getIMEI(context), "", BuildConfig.VERSION_NAME,
                    UtilMethods.INSTANCE.getSerialNo(context), mLoginDataResponse.getData().getSessionID(),
                    mLoginDataResponse.getData().getSession(), ""));

            call.enqueue(new Callback<SenderResponse>() {

                @Override
                public void onResponse(Call<SenderResponse> call, retrofit2.Response<SenderResponse> response) {
                    if (loader.isShowing())
                        loader.dismiss();
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getStatuscode() == 1) {
                                if (mApiCallBack != null) {
                                    mApiCallBack.onSuccess(response.body());
                                }
                                UtilMethods.INSTANCE.Successfulok(response.body().getMsg(), context);
                            } else {
                                UtilMethods.INSTANCE.getErrorHandler(context, response.body().isVersionValid(),
                                        true, response.body().getMsg(),
                                        mGetLocation, loader, () -> addBeneficiary(context, oid, sid, senderNO, dob, address,
                                                pinCode, beniName, ifsc, accountNo, bankId, loader,
                                                mGetLocation, mApiCallBack));
                            }

                        }
                    } else {
                        UtilMethods.INSTANCE.apiErrorHandle(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<SenderResponse> call, Throwable t) {

                    if (loader.isShowing())
                        loader.dismiss();
                    UtilMethods.INSTANCE.apiFailureError(context, t);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void getChargedAmount(final Activity context, final int oid, final String Amount,
                                 final CustomLoader loader, GetLocation mGetLocation, ApiCallBack mApiCallBack) {
        try {
            String LoginResponse = UtilMethods.INSTANCE.getLoginPref(context);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            EndPointInterfaceDMTNew git = ApiClient.getClient().create(EndPointInterfaceDMTNew.class);
            Call<RechargeReportResponse> call = git.getChargedAmount(new GetChargedAmountRequestNew(oid + "", Amount,
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    UtilMethods.INSTANCE.getIMEI(context),
                    "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(context), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<RechargeReportResponse>() {

                @Override
                public void onResponse(Call<RechargeReportResponse> call, retrofit2.Response<RechargeReportResponse> response) {

                    if (loader.isShowing())
                        loader.dismiss();
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getStatuscode().equalsIgnoreCase("1")) {
                               /* double amount = Double.parseDouble(Amount);
                                double charged = Double.parseDouble(response.body().getChargedAmount());
                                double totalAmount = amount + charged;*/
                                if (mApiCallBack != null) {
                                    mApiCallBack.onSuccess(response.body());
                                }

                            } else {
                                boolean isValidVersion = response.body().getIsVersionValid().equalsIgnoreCase("true");
                                UtilMethods.INSTANCE.getErrorHandler(context, isValidVersion,
                                        true, response.body().getMsg(),
                                        mGetLocation, loader, () -> getChargedAmount(context, oid, Amount,
                                                loader, mGetLocation, mApiCallBack));
                            }

                        }
                    } else {
                        UtilMethods.INSTANCE.apiErrorHandle(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<RechargeReportResponse> call, Throwable t) {

                    if (loader.isShowing())
                        loader.dismiss();
                    UtilMethods.INSTANCE.apiFailureError(context, t);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void sendMoneyOTP(final Activity context, int oid, String senderNumber, String sidValue, String otp, String otpRefId, String securityKey,
                             String ifsc, String beneId, String beneName, int bankId, String bankName,
                             String amount, String accountNo, String channel, CustomLoader loader, GetLocation mGetLocation, ApiCallBack mApiCallBack) {
        try {
            String loginPref = UtilMethods.INSTANCE.getLoginPref(context);
            LoginResponse mLoginDataResponse = new Gson().fromJson(loginPref, LoginResponse.class);
            EndPointInterfaceDMTNew git = ApiClient.getClient().create(EndPointInterfaceDMTNew.class);
            Call<SenderResponse> call = git.sendTransactionOTP(new SenderRequest(UtilMethods.getLattitude, UtilMethods.getLattitude, UtilMethods.getLongitude,
                    oid, senderNumber, sidValue, new DMTParam(senderNumber, oid,
                    UtilMethods.getLattitude, UtilMethods.getLattitude, UtilMethods.getLongitude,
                    beneId, beneName, accountNo, ifsc,
                    bankId, bankName, securityKey, amount, otp, otpRefId, otpRefId, channel),
                    mLoginDataResponse.getData().getUserID(), mLoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, UtilMethods.INSTANCE.getIMEI(context), "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(context),
                    mLoginDataResponse.getData().getSessionID(), mLoginDataResponse.getData().getSession(), securityKey));

            call.enqueue(new Callback<SenderResponse>() {

                @Override
                public void onResponse(Call<SenderResponse> call, retrofit2.Response<SenderResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {

                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 1) {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSuccess(response.body());
                                    }

                                } else {
                                    UtilMethods.INSTANCE.getErrorHandler(context, response.body().isVersionValid(),
                                            true, response.body().getMsg(),
                                            mGetLocation, loader, () -> sendMoneyOTP(context, oid, senderNumber, sidValue, otp, otpRefId, securityKey,
                                                    ifsc, beneId, beneName, bankId, bankName,
                                                    amount, accountNo, channel, loader, mGetLocation, mApiCallBack));
                                }

                            }
                        } else {
                            UtilMethods.INSTANCE.apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        UtilMethods.INSTANCE.Error(context, e.getMessage());
                    }

                }

                @Override
                public void onFailure(Call<SenderResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                    try {
                        UtilMethods.INSTANCE.apiFailureError(context, t);

                    } catch (IllegalStateException ise) {
                        UtilMethods.INSTANCE.Error(context, ise.getMessage());

                    }
                }
            });

        } catch (Exception e) {
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            UtilMethods.INSTANCE.Error(context, e.getMessage());
        }
    }

    public void sendMoney(final Activity context, int oid, String securityKey, String beneID, String senderNo, String sidValue,
                          String ifsc, String accountNo, String amount, String channel, String bank, int bankId, String otp, String otpRefId,
                          String beneName, final CustomLoader loader, GetLocation mGetLocation, final TextView submitButton) {
        try {
            submitButton.setEnabled(false);
            String loginPref = UtilMethods.INSTANCE.getLoginPref(context);
            LoginResponse mLoginDataResponse = new Gson().fromJson(loginPref, LoginResponse.class);
            EndPointInterfaceDMTNew git = ApiClient.getClient().create(EndPointInterfaceDMTNew.class);
            Call<SenderResponse> call = git.sendMoney(new SenderRequest(UtilMethods.getLattitude, UtilMethods.getLattitude, UtilMethods.getLongitude,
                    oid, senderNo, sidValue, new DMTParam(senderNo, oid,
                    UtilMethods.getLattitude, UtilMethods.getLattitude, UtilMethods.getLongitude,
                    beneID, beneName, accountNo, ifsc,
                    bankId, bank, securityKey, amount, otp, otpRefId, otpRefId, channel),
                    mLoginDataResponse.getData().getUserID(), mLoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, UtilMethods.INSTANCE.getIMEI(context), "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(context),
                    mLoginDataResponse.getData().getSessionID(), mLoginDataResponse.getData().getSession(), securityKey));

            call.enqueue(new Callback<SenderResponse>() {

                @Override
                public void onResponse(Call<SenderResponse> call, retrofit2.Response<SenderResponse> response) {

                    if (loader.isShowing())
                        loader.dismiss();
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            submitButton.setEnabled(true);
                            if (response.body().getStatuscode() == 2) {
                                // Successfulok(context, response.body().getMsg(), activity);
                                if (response.body().getData() != null) {
                                    if (response.body().getData().getGroupID() != null && !response.body().getData().getGroupID().isEmpty()) {
                                        UtilMethods.INSTANCE.GetDMTReceipt(context, response.body().getData().getGroupID(), "All", loader);

                                    } else {
                                        UtilMethods.INSTANCE.Successfulok(response.body().getMsg(), context);
                                    }
                                } else {
                                    UtilMethods.INSTANCE.Successfulok(response.body().getMsg(), context);
                                }

                            } else if (response.body().getStatuscode() == 1) {
                                UtilMethods.INSTANCE.Processing(context, response.body().getMsg());
                            } else if (response.body().getStatuscode() == 3) {

                                if (response.body().getData() != null) {
                                    if (response.body().getData().getGroupID() != null && !response.body().getData().getGroupID().isEmpty()) {

                                        UtilMethods.INSTANCE.GetDMTReceipt(context, response.body().getData().getGroupID(), "All", loader);

                                    } else {
                                        UtilMethods.INSTANCE.Error(context, response.body().getMsg());
                                    }
                                } else {
                                    UtilMethods.INSTANCE.Error(context, response.body().getMsg());
                                }


                            } else {
                                UtilMethods.INSTANCE.getErrorHandler(context, response.body().isVersionValid(),
                                        true, response.body().getMsg(),
                                        mGetLocation, loader, () -> sendMoney(context, oid, securityKey, beneID, senderNo, sidValue,
                                                ifsc, accountNo, amount, channel, bank, bankId, otp, otpRefId,
                                                beneName, loader, mGetLocation, submitButton));
                            }

                        }
                    } else {
                        UtilMethods.INSTANCE.apiErrorHandle(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<SenderResponse> call, Throwable t) {

                    submitButton.setEnabled(true);
                    if (loader.isShowing())
                        loader.dismiss();
                    try {
                        UtilMethods.INSTANCE.apiFailureError(context, t);
                    } catch (IllegalStateException ise) {
                        UtilMethods.INSTANCE.Error(context, ise.getMessage());

                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void deleteBeneficiary(final Activity context, int oid, String sidValue, final String senderNumberStr,
                                  String beneID, final CustomLoader loader, GetLocation mGetLocation, final ApiCallBack mApiCallBack) {
        try {
            String LoginResponse = UtilMethods.INSTANCE.getLoginPref(context);
            LoginResponse mLoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            EndPointInterfaceDMTNew git = ApiClient.getClient().create(EndPointInterfaceDMTNew.class);
            Call<SenderResponse> call = git.deleteBeneficiary(new SenderRequest(UtilMethods.getLattitude, UtilMethods.getLattitude,
                    UtilMethods.getLongitude, oid, senderNumberStr, sidValue,
                    new DMTParam(senderNumberStr, oid, UtilMethods.getLattitude, UtilMethods.getLattitude, UtilMethods.getLongitude, beneID),
                    mLoginDataResponse.getData().getUserID(), mLoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, UtilMethods.INSTANCE.getIMEI(context), "", BuildConfig.VERSION_NAME,
                    UtilMethods.INSTANCE.getSerialNo(context),
                    mLoginDataResponse.getData().getSessionID(), mLoginDataResponse.getData().getSession(), ""));

            call.enqueue(new Callback<SenderResponse>() {

                @Override
                public void onResponse(Call<SenderResponse> call, retrofit2.Response<SenderResponse> response) {
                    if (loader.isShowing())
                        loader.dismiss();
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getStatuscode() == 1) {

                                UtilMethods.INSTANCE.Successful(context, response.body().getMsg());
                                if (mApiCallBack != null) {
                                    mApiCallBack.onSuccess(response.body());
                                }

                            } else {
                                UtilMethods.INSTANCE.getErrorHandler(context, response.body().isVersionValid(),
                                        true, response.body().getMsg(),
                                        mGetLocation, loader, () -> deleteBeneficiary(context, oid, sidValue, senderNumberStr,
                                                beneID, loader, mGetLocation, mApiCallBack));
                            }

                        }
                    } else {
                        UtilMethods.INSTANCE.apiErrorHandle(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<SenderResponse> call, Throwable t) {

                    if (loader.isShowing())
                        loader.dismiss();
                    UtilMethods.INSTANCE.apiFailureError(context, t);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public interface ApiCallBack {
        void onSuccess(Object object);
    }

    public interface ApiCallBackTwoMethod {
        void onSuccess(Object object);

        void onError(Object object);
    }
}
