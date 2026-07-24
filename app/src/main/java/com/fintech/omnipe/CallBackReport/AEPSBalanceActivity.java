package com.fintech.omnipe.CallBackReport;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;


import com.fintech.omnipe.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AEPSBalanceActivity extends AppCompatActivity {
    String bankName;
    Double balAmount;
    TextView tv_saving_amt,tv_account_name,tv_date_time;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_balance_enquiry);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        // toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        //  toolbar.setTitle("Dispute Report");
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tv_saving_amt=findViewById(R.id.tv_saving_amt);
        tv_date_time=findViewById(R.id.tv_date_time);
        // tv_account_name=findViewById(R.id.tv_account_name);

        balAmount=getIntent().getExtras().getDouble("balAmount");
        bankName=getIntent().getExtras().getString("bankName");
        // tv_account_name.setText(bankName);
        if(balAmount!=null)
        {
            tv_saving_amt.setText(balAmount+"");
        }
        else
        {
            tv_saving_amt.setText("0.00");
        }


        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy  hh.mm aa");
        String formattedDate = dateFormat.format(new Date()).toString();
        tv_date_time.setText(formattedDate);

    }
}
