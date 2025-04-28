package com.example.rakshak;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class UserDashboardActivity extends AppCompatActivity {

    TextView welcomeText;
    WebView youtubeWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        welcomeText = findViewById(R.id.welcomeText);
        String username = getIntent().getStringExtra("username");
        welcomeText.setText("Welcome User  👋");
        // Load YouTube Video
        youtubeWebView = findViewById(R.id.youtubeWebView);
        youtubeWebView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = youtubeWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        String videoHTML = "<iframe width=\"100%\" height=\"100%\" " +
                "src=\"https://www.youtube.com/embed/zOF4I2I0mH4\" " +
                "frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>";

        youtubeWebView.loadData(videoHTML, "text/html", "utf-8");
    }

    public void openAccidentMap(View view) {
        startActivity(new Intent(this, AccidentMapActivity.class));
    }
    public void openReportAccident(View view) {
        startActivity(new Intent(this, ReportAccidentActivity.class));
    }

    public void openNearbyAlerts(View view) {
        startActivity(new Intent(this, NearbyServicesActivity.class));
    }
    public void Tips(View view) {
        startActivity(new Intent(this, SafetyTipsActivity.class));
    }
    public void Chat(View view) {
        startActivity(new Intent(this, ChatbotActivity.class));
    }


}
