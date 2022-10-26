package com.migros.mkolay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.migros.mkolay.assistant.AppShared;

public class NewCardActivity extends AppCompatActivity {
    String link;
    Toolbar toolbar;
    WebView moneyPayWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_card);
        moneyPayWebView = (WebView) findViewById(R.id.moneyPayWebView);

        new AppShared(NewCardActivity.this).setDecor(NewCardActivity.this,toolbar, getResources().getString(R.string.app_name), getResources().getColor(R.color.addNewCardActivityColor));

        link = getResources().getString(R.string.new_card_link);




        final WebSettings settings = moneyPayWebView.getSettings();
        settings.setDisplayZoomControls(false);
        settings.setAppCacheEnabled(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setBuiltInZoomControls(false);
        settings.setPluginState(WebSettings.PluginState.ON);
        settings.setJavaScriptEnabled(true);
        settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);


        moneyPayWebView.setBackgroundColor(Color.TRANSPARENT);


        moneyPayWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

        });

        moneyPayWebView.loadUrl(link);


    }
}