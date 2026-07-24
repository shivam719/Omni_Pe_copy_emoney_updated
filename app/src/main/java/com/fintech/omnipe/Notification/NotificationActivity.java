package com.fintech.omnipe.Notification;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import android.speech.tts.TextToSpeech;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fintech.omnipe.R;

public class NotificationActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private boolean isTTSInit;
    private TextToSpeech tts;
    private String msgSpeak = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_notification);
        Toolbar toolbar = findViewById(R.id.toolbar);
        tts = new TextToSpeech(this, NotificationActivity.this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Notification");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        String title = getIntent().getStringExtra("Title");
        String msg = getIntent().getStringExtra("Message");
       /* msgSpeak = msg;
        playVoice();*/
        String imageUrl = getIntent().getStringExtra("Image");
        final String url = getIntent().getStringExtra("Url");
        final String time = getIntent().getStringExtra("Time");

        TextView titleTv = findViewById(R.id.title);
        TextView msgTv = findViewById(R.id.message);
        final ImageView imageIv = findViewById(R.id.banner);
        TextView detailBtn = findViewById(R.id.detailBtn);
        TextView timeTv = findViewById(R.id.time);
        timeTv.setText(time + "");


        titleTv.setText(title);
        msgTv.setText(msg);
        if (imageUrl != null && !imageUrl.isEmpty() && URLUtil.isValidUrl(imageUrl)) {
            imageIv.setVisibility(View.VISIBLE);
            Glide.with(this)
                    .load(imageUrl)
                    .apply(RequestOptions.placeholderOf(R.drawable.no_image_error))
                    .into(imageIv);
        } else {
            imageIv.setVisibility(View.GONE);
        }
        if (url != null && !url.isEmpty() && URLUtil.isValidUrl(url)) {
            detailBtn.setVisibility(View.VISIBLE);
            detailBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openBrowser(url);
                }
            });
        } else {
            detailBtn.setVisibility(View.GONE);
        }


    }

    private void playVoice() {
        if (tts != null && isTTSInit) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                tts.speak(msgSpeak, TextToSpeech.QUEUE_FLUSH, null, null);
            } else {
                tts.speak(msgSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
            msgSpeak = "";
        }
    }

    void openBrowser(String url) {
        url = url.replaceAll(" ", "");
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        } catch (ActivityNotFoundException anfe) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            } catch (ActivityNotFoundException anfe2) {

            }
        }

    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            isTTSInit = true;
            if (msgSpeak != null && !msgSpeak.isEmpty()) {
                playVoice();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Init failed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(msgSpeak!=null){
            playVoice();
        }
        else
        {
            playVoice();
            finish();
        }
    }
}
