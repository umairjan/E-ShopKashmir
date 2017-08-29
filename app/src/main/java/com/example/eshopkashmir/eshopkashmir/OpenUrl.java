package com.example.eshopkashmir.eshopkashmir;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;

import im.delight.android.webview.AdvancedWebView;

public class OpenUrl extends BaseActivity implements AdvancedWebView.Listener {

    private static final String URL_ACCOUNT = "http://eshopkashmir.com/customer/account/login/";
    ProgressDialog progressDialog = null;
    AdvancedWebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        progressDialog = new ProgressDialog(this);
        setContentView(R.layout.activity_open_url);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String url = getIntent().getExtras().getString("url");
        mWebView = (AdvancedWebView) findViewById(R.id.urlWebView);
        mWebView.setListener(this,this);
        mWebView.setGeolocationEnabled(true);
        mWebView.setMixedContentAllowed(true);
        mWebView.setCookiesEnabled(true);
        mWebView.setThirdPartyCookiesEnabled(true);
        mWebView.getSettings().setJavaScriptEnabled(true);

        mWebView.setWebChromeClient(new WebChromeClient(){


            @Override
            public void onReceivedTitle(WebView view, String title) {
                progressDialog.dismiss();
            }

            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {
                progressDialog.dismiss();
            }
        });
        if(isInternetPresent()){
            mWebView.loadUrl(url);
        } else {
            Toast.makeText(OpenUrl.this,"Check Your Internet Connection",Toast.LENGTH_LONG).show();
            return;
        }

    }



    @Override
    public void onPageStarted(String url, Bitmap favicon) {
        if (!url.equals(URL_ACCOUNT)) {
            progressDialog = ProgressDialog.show(OpenUrl.this, getString(R.string.loading), getString(R.string.wait), false, false);
            progressDialog.setCancelable(true);
        } else {
//            Toast.makeText(OpenUrl.this,"Loading Account Page",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onPageFinished(String url) {
        progressDialog.dismiss();
    }



    @Override
    public void onPageError(int errorCode, String description, String failingUrl) {
        Toast.makeText(this,"There was some error",Toast.LENGTH_SHORT).show();
        Log.e("error",description);

    }

    @Override
    public void onDownloadRequested(String url, String suggestedFilename, String mimeType, long contentLength, String contentDisposition, String userAgent) {

    }

    @Override
    public void onExternalPageRequest(String url) {

    }

    private boolean isInternetPresent(){
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return  networkInfo != null && networkInfo.isConnected();
    }

}
