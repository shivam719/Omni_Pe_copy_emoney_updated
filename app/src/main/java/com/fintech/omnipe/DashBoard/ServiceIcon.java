package com.fintech.omnipe.DashBoard;

import android.widget.ImageView;

import com.fintech.omnipe.R;

public enum ServiceIcon {
    INSTANCE;

    public void serviceIcon(int id, ImageView bgView) {

        if (id == 1) {

            bgView.setImageResource(R.drawable.ic_phone);

        }
        if (id == 2) {
            bgView.setImageResource(R.drawable.ic_postpaid);

        }
        if (id == 3) {
            bgView.setImageResource(R.drawable.ic_dth);

        }
        if (id == 4) {
            bgView.setImageResource(R.drawable.ic_landline);

        }
        if (id == 5) {
            bgView.setImageResource(R.drawable.ic_electricity);

        }
        if (id == 6) { //ic_piped_gas
            bgView.setImageResource(R.drawable.ic_piped_gas);

        }
        if (id == 7) { //Domestic Hotel
            bgView.setImageResource(R.drawable.ic_domestic_hotel);

        }
        if (id == 8) { //InterNation Hotel
            bgView.setImageResource(R.drawable.ic_international_hotel);

        }
        if (id == 9) { //Domestic Flight
            bgView.setImageResource(R.drawable.ic_domestic_flight);

        }
        if (id == 10) { //International Flight
            bgView.setImageResource(R.drawable.ic_domestic_flight);

        }
        if (id == 11) {  //Bus
            bgView.setImageResource(R.drawable.ic_bus);

        }
        if (id == 12) { //Connection
            bgView.setImageResource(R.drawable.ic_router);

        }
        if (id == 13) { //MPOS

            bgView.setImageResource(R.drawable.ic_mpos);
        }
        if (id == 14) { //DMT
            bgView.setImageResource(R.drawable.ic_money_transfer);

        }
        if (id == 15) { //DMR Charge

            bgView.setImageResource(R.drawable.ic_money_transfer);
        }
        if (id == 16) { //Broadband
            bgView.setImageResource(R.drawable.ic_broadband);
        }
        if (id == 17) { //Water
            bgView.setImageResource(R.drawable.ic_water);

        }
        if (id == 18) { //Train
            bgView.setImageResource(R.drawable.ic_train);

        }
        if (id == 19) {
            bgView.setImageResource(R.drawable.ic_shopping);
        }
        if (id == 20) {

            bgView.setImageResource(R.drawable.ic_pes);
        }
        if (id == 22) { //For AEPS
            bgView.setImageResource(R.drawable.ic_aeps);
        }

        if (id == 24) { //For PSA
            bgView.setImageResource(R.drawable.ic_psa);

        }

        if (id == 25) { //Loan Repayment
            bgView.setImageResource(R.drawable.ic_loan_repayment);

        }
        if (id == 26) { //Gas Cylinder Booking
            bgView.setImageResource(R.drawable.ic_gas_cylinder);

        }
        if (id == 27) { //LifeInsurance Premium
            bgView.setImageResource(R.drawable.ic_life_insurance);

        }

        if (id == 28) { //Bike Insurance

            bgView.setImageResource(R.drawable.ic_bike_insurance);
        }

        if (id == 29) { //Car Insurance

            bgView.setImageResource(R.drawable.ic_car__insurance);
        }

        if (id == 35) {//ic_hd_box
            bgView.setImageResource(R.drawable.ic_hd_box);

        }
        if (id == 36) {//ic_sd_box
            bgView.setImageResource(R.drawable.ic_sd_box);
        }

        if (id == 37) {//Add Money

            bgView.setImageResource(R.drawable.ic_add_money);
        }
        if (id == 38) { //FASTag
            bgView.setImageResource(R.drawable.ic_fastag);
        }
        if (id == 39) { //Cable TV
            bgView.setImageResource(R.drawable.ic_cable_tv);

        }

        if (id == 44) { //For Mini ATM
            bgView.setImageResource(R.drawable.ic_mini_atm);
        }

        if (id == 45) { //Shopping

            bgView.setImageResource(R.drawable.ic_shopping);

        }

        if (id == 46) { //Municipal Taxes
            bgView.setImageResource(R.drawable.ic_municiple_tax);

        }

        if (id == 47) { //Education Fees
            bgView.setImageResource(R.drawable.ic_education_fee);
        }

        if (id == 48) { //Housing Society
            bgView.setImageResource(R.drawable.ic_housing_society);

        }
        if (id == 49) { //credit card
            bgView.setImageResource(R.drawable.ic_credit_card);
        }

        if (id == 52) { //Hospital
            bgView.setImageResource(R.drawable.ic_hospital);
        }
        if (id ==54) { //Subscription
            bgView.setImageResource(R.drawable.ic_subscription);
        }
        if (id == 62) { // UPI Pay
            bgView.setImageResource(R.drawable.ic_upi_payment);
        }

        if (id == 63) { // UPI Scanning
            bgView.setImageResource(R.drawable.ic_scan_nd_pay);
        }
        if (id ==64) { //club association
            bgView.setImageResource(R.drawable.ic_club_association);
        }
        if (id ==69) { //bar association
            bgView.setImageResource(R.drawable.ic_bar_association);
        }

        if (id ==74) { // Account Open
            bgView.setImageResource(R.drawable.ic_bank_account_opening);
        }

        if (id ==75) { //Coupon Voucher
            bgView.setImageResource(R.drawable.ic_coupon_voucher);
        }

        if (id ==80) { //Loan Application
            bgView.setImageResource(R.drawable.ic_loan_application);
        }

        if (id == 100) {
            bgView.setImageResource(R.drawable.ic_fund_request);
        }
        if(id==101){
            bgView.setImageResource(R.drawable.ic_recharge_report);
        }
        if(id==102){
            bgView.setImageResource(R.drawable.ic_ledger_report);
        }
        if(id==103){
            bgView.setImageResource(R.drawable.ic_fund_order_report);
        }
        if(id==104){
            bgView.setImageResource(R.drawable.ic_dispute_report);
        }
        if(id==105){
            bgView.setImageResource(R.drawable.ic_dmt_report);
        }
        if(id==106){
            bgView.setImageResource(R.drawable.ic_move_to_bank_report);
        }
        if(id==107){
            bgView.setImageResource(R.drawable.ic_fund_debit_credit_report);
        }
        if(id==108){
            bgView.setImageResource(R.drawable.ic_fund_pending_report);
        }
        if(id==109){
            bgView.setImageResource(R.drawable.ic_user_day_book_report);
        }
        if(id==110){
            bgView.setImageResource(R.drawable.ic_commission_slab_report);
        }
        if(id==111){
            bgView.setImageResource(R.drawable.ic_wr_report);
        }
        if(id==112){
            bgView.setImageResource(R.drawable.ic_aeps_report);
        }
        if(id==113){
            bgView.setImageResource(R.drawable.ic_dth_subscription_report);
        }

        if (id == 114) {
            bgView.setImageResource(R.drawable.ic_create_user);

        }if (id == 115) {
            bgView.setImageResource(R.drawable.ic_create_fos);

        }if (id == 116) {
            bgView.setImageResource(R.drawable.ic_user_list);
        }
        if (id == 117) {

            bgView.setImageResource(R.drawable.ic_fos_user_list);
        } if (id == 118) {
            bgView.setImageResource(R.drawable.ic_fos_areas);

        }
        if (id == 121) {

            bgView.setImageResource(R.drawable.ic_account_statement_report);
        }
        if (id == 122) {

            bgView.setImageResource(R.drawable.ic_fos_outstanding);
        }

        if (id == 123) {

            bgView.setImageResource(R.drawable.ic_channel_outstanding);
        }

        if (id == 125) {

            bgView.setImageResource(R.drawable.ic_voucher_entry);
        }
    }

    public void parentIcon(int id,ImageView bgView){
        if (id == 11) {
            bgView.setImageResource(R.drawable.ic_bill_payment);
        }
        if (id == 19) {
            bgView.setImageResource(R.drawable.ic_vehicle_insurance);
        }
        if (id == 30) {
            bgView.setImageResource(R.drawable.ic_dth_subscription);
        }
    }
}
