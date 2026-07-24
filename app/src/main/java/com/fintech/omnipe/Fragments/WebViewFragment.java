package com.fintech.omnipe.Fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.fintech.omnipe.Util.ApplicationConstant;
import com.fintech.omnipe.BuildConfig;
import com.fintech.omnipe.R;


public class WebViewFragment extends Fragment {

    String from = "about";
    TextView tvVersion;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_web_view, container, false);

        if (getArguments() != null) {
            from = getArguments().getString("from");
            Log.e("fromm", from);
        }

        WebView webView = (WebView) v.findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        if (from.equals("about")) {
            webView.loadUrl(ApplicationConstant.INSTANCE.aboutUsUrl);
        } else if (from.equals("contact")) {
            webView.loadUrl(ApplicationConstant.INSTANCE.contactUsUrl);
        }

        webView.setWebViewClient(new WebViewClient());

        tvVersion = (TextView) v.findViewById(R.id.tv_version);
        tvVersion.setText("Version :" + BuildConfig.VERSION_NAME);

        return v;
    }

}
