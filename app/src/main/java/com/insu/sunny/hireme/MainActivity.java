package com.insu.sunny.hireme;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    private WebView mWebView;
    public String TAG = this.getClass().getSimpleName();
    public String[] linkArray;

    @SuppressLint({"ClickableViewAccessibility", "RestrictedApi"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWebView = findViewById(R.id.mWebView);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.loadUrl("https://sprakash57.github.io/portfolio");
        mWebView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                WebView.HitTestResult hr = ((WebView)v).getHitTestResult();
                if (hr.getExtra() != null) {
                    linkArray = hr.getExtra().split("#/");
                    Log.i(TAG, "getExtra = "+ linkArray[1]);
                }
                return false;
            }
        });
//        mWebView.setWebViewClient(new WebViewClient(){
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                // Use an external email program if the link begins with "mailto:".
//                if (url.startsWith("mailto:")) {
//                    // We use `ACTION_SENDTO` instead of `ACTION_SEND` so that only email programs are launched.
//                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
//
//                    // Parse the url and set it as the data for the `Intent`.
//                    emailIntent.setData(Uri.parse(url));
//
//                    // `FLAG_ACTIVITY_NEW_TASK` opens the email program in a new task instead as part of this application.
//                    emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//                    // Make it so.
//                    startActivity(emailIntent);
//                    return true;
//                } else {
//                    // Returning false causes WebView to load the URL while preventing it from adding URL redirects to the WebView history.
//                    return false;
//                }
//            }
//        });
        FloatingActionButton fab = findViewById(R.id.fab);
        if (linkArray != null && linkArray[1] == "contact") {
            Log.i(TAG, "get contact hide");
            fab.setVisibility(View.INVISIBLE);
        } else {
            Log.i(TAG, "get contact show");
            fab.setVisibility(View.VISIBLE);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent feedIntent = new Intent(MainActivity.this, FeedActivity.class);
                    startActivity(feedIntent);
                }
            });
        }
    }

    @Override
    public void onBackPressed() {

        if(mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

}
