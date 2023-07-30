package com.example.mywebbrowser;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btnBrowser;
    private Button btnWeather;
    private Button btnGo;

    private EditText editUrl;
    private WebView webView;

    private String url;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        btnBrowser = findViewById(R.id.button_web);
        btnWeather = findViewById(R.id.button_weather);
        btnGo = findViewById(R.id.button_go);
        editUrl = findViewById(R.id.editUrl);
        webView = findViewById(R.id.webView);

        WebSettings ws = webView.getSettings();
        ws.setJavaScriptEnabled(true);
        ws.setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

                view.loadUrl(String.valueOf(request.getUrl()));
                return true;
            }
        });
        webView.loadUrl("naver.com");


        editUrl.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if ( keyCode==KeyEvent.KEYCODE_ENTER )
                    onClickButtonGo(view);
                return false;
            }
        });
    }


    public void onClickButtonGo(View view)
    {
        if ( editUrl.getText().length() > 0 )
        {
            url = String.valueOf(editUrl.getText());
            webView.loadUrl(url);
            hideKeyboard();

//            Toast toast = Toast.makeText(this, editUrl.getText(), Toast.LENGTH_SHORT);
//            toast.show();
        }
    }


    private void hideKeyboard()
    {
        InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void onClickButtonWeather(View view)
    {
        Intent weatherIntent = new Intent(MainActivity.this, WeatherActivity.class);
        startActivity(weatherIntent);
        //finish();
    }

    public void onClickButtonCityList(View view)
    {
        Intent cityIntent = new Intent(MainActivity.this, CityListActivity.class);
        startActivity(cityIntent);
    }

}