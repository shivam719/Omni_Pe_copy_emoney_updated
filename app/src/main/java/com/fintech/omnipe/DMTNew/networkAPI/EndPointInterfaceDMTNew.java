package com.fintech.omnipe.DMTNew.networkAPI;

import com.fintech.omnipe.Api.Response.RechargeReportResponse;
import com.fintech.omnipe.DMTNew.dto.GetBeneficiaryResponse;
import com.fintech.omnipe.DMTNew.dto.SenderRequest;
import com.fintech.omnipe.DMTNew.dto.SenderResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface EndPointInterfaceDMTNew {

    @Headers("Content-Type: application/json")
    @POST("App/GetSenderCASA")
    Call<SenderResponse> GetSenderCASA(@Body SenderRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/GetSender")
    Call<SenderResponse> getSender(@Body SenderRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/DoSenderEKYC")
    Call<SenderResponse> doSenderEKYC(@Body SenderRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/CreateSender?")
    Call<SenderResponse> createSender(@Body SenderRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/GetBeneficiaries")
    Call<GetBeneficiaryResponse> getBeneficiary(@Body SenderRequest request);


    @Headers("Content-Type: application/json")
    @POST("App/CreateBeneficiary")
    Call<SenderResponse> createBeneficiary(@Body SenderRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/VerifyAccount")
    Call<SenderResponse> verifyAccount(@Body SenderRequest request);



    @Headers("Content-Type: application/json")
    @POST("App/GetChargedAmountP")
    Call<RechargeReportResponse> getChargedAmount(@Body GetChargedAmountRequestNew request);

    @Headers("Content-Type: application/json")
    @POST("App/SendTransactionOTP")
    Call<SenderResponse>sendTransactionOTP(@Body SenderRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/DMTTransaction")
    Call<SenderResponse> sendMoney(@Body SenderRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/DeleteBeneficiary")
    Call<SenderResponse> deleteBeneficiary(@Body SenderRequest request);

}
