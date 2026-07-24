package com.fintech.omnipe.CallBackReport;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.roundpay.emoneylib.Object.MiniStatements;
import com.fintech.omnipe.R;


import java.util.ArrayList;

public class MiniStatementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_mini_statement);

        RecyclerView recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<MiniStatements> miniStatements= (ArrayList<MiniStatements>) getIntent().getSerializableExtra("MINI_STATEMENT");
        recyclerView.setAdapter(new MiniStatementAdapter(miniStatements,this));
    }
}