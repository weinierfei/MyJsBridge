package com.dragon.jsbridge;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.alibaba.fastjson.JSON;
import com.dragon.jsbridge.jsbridge.Action;
import com.dragon.jsbridge.jsbridge.JsRegisterList;
import com.dragon.jsbridge.jsbridge.StringUtil;
import com.dragon.jsbridge.jsbridge.SystemUtils;
import com.dragon.jsbridge.jsbridge.WVJBWebViewClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Description:
 *
 * @author: guoyongping
 * @date: 2017/3/22 21:04
 */

public class TestWebViewActivity extends AppCompatActivity {
    private static final String TAG = "TestWebViewActivity";

    @Bind(R.id.webview) WebView webView;
    @Bind(R.id.progressBar) ProgressBar progressBar;

    MyWebViewClient myWebViewClient;
    List<String> handList = new ArrayList<>();
    private String intentType = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_webview);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClients());
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setSaveFormData(false);
        webView.getSettings().setSupportZoom(false);

        myWebViewClient = new MyWebViewClient(webView);

        myWebViewClient.enableLogging();
        webView.setWebViewClient(myWebViewClient);
    }

    private void initData() {

        webView.loadUrl("file:///android_asset/ExampleApp.html");
    }

    public static DeviesInfo getDeviesInfo() {
        return new DeviesInfo("华为荣耀8", "android", "7.0");
    }

    private class MyWebViewClient extends WVJBWebViewClient {

        public MyWebViewClient(WebView webView) {
            super(webView, new WVJBWebViewClient.WVJBHandler() {
                @Override
                public void request(Object data, WVJBResponseCallback callback) {
                    callback.callback("Response for message from Android!");
                }
            });

            // not support js send
            registerHandler("returnCommonParams", new WVJBWebViewClient.WVJBHandler() {
                @Override
                public void request(Object data, WVJBResponseCallback callback) {
                    Log.e(TAG, "returnCommonParams.data====" + data.toString());
                    String json = JSON.toJSONString(getDeviesInfo());
                    callback.callback(json);
                }
            });

            //            //调用登录
//            registerHandler("remoteShowLogin", new WVJBHandler() {
//                @Override
//                public void request(Object data, WVJBResponseCallback callback) {
//                    Log.i(TAG, "remoteShowLogin.date==" + JSON.toJSONString(data));
//                    Intent intent = new Intent();
//                    //                    intent.setClass(TestWebViewActivity.this,LoginActivity.class);
//                    String className = "com.dragon.jsbridge.LoginActivity";
//
//                    intent.setClassName(getPackageName(),className);
//
//                    startActivity(intent);
//                    callback.callback("response payTableViewController");
//                }
//            });


            // not support js send  getJSRegisterInfoHandler
            callHandler("getJSRegisterInfoHandler", getDeviesInfo(), new WVJBResponseCallback() {
                @Override
                public void callback(Object data) {
                    Log.e(TAG, "---data=" + data.toString());
                    //Toast.makeText(WebViewActivity.this, "Android got response! :" + data, Toast.LENGTH_LONG)
                    // .show();
                    JsRegisterList jsRegisters = JSON.parseObject(data.toString(), JsRegisterList.class);
                    for (final Action action : jsRegisters.getJsRegisterList()) {
                        // not support js send
                        if (handList.contains(action.getAction())) {
                            return;
                        }
                        handList.add(action.getAction());
                        registerHandler(action.getAction(), new WVJBWebViewClient.WVJBHandler() {
                            @Override
                            public void request(Object data, WVJBResponseCallback callback) {
                                //Intent intent = new Intent(IntentAction.COURSE_LIST_ACTIVITY,url);

                                Intent intent = new Intent();
                                intentType = action.getTarget(); //标示跳转到那个activity
                                //                                intent.setClassName(SystemUtils.getPackageName
                                // (getApplicationContext()), action
                                //                                        .getPackageName());
//                                intent.setClassName(TestWebViewActivity.this, intentType);
                                intent.setClassName(SystemUtils.getPackageName(getApplicationContext()), intentType);
                                if (!StringUtil.isEmpty(data.toString())) { //这是传过来的参数值
                                    try {
                                        JSONObject jsonObject = new JSONObject(data.toString());
                                        Iterator it = jsonObject.keys();
                                        // 遍历jsonObject数据，添加到Map对象
                                        while (it.hasNext()) {
                                            String key = String.valueOf(it.next());
                                            String value = jsonObject.optString(key);
                                            intent.putExtra(key, value);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                startActivity(intent);

                                callback.callback("from android to web  :    call login successes");
                            }
                        });

                    }
                }
            });
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            //TODO 显示loading
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            //TODO 显示正文

        }
    }


    private class WebChromeClients extends WebChromeClient {

        @Override
        public void onReceivedTitle(WebView view, String title) {

        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (progressBar != null) {
                if (newProgress < 100) {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(newProgress);
                }
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);
                }
            }

        }
    }

}
