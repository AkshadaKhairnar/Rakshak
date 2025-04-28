package com.example.rakshak;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.widget.Toast;

public class AdminDashboardActivity extends AppCompatActivity {

    CardView manageReportsCard, analyticsCard, notifyUsersCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        manageReportsCard = findViewById(R.id.manageReportsCard);
        analyticsCard = findViewById(R.id.analyticsCard);
        notifyUsersCard = findViewById(R.id.notifyUsersCard);

        manageReportsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open Manage Reports Activity
                startActivity(new Intent(AdminDashboardActivity.this, ManageReportsActivity.class));
            }
        });

        analyticsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open Analytics Activity
                startActivity(new Intent(AdminDashboardActivity.this, ReportsAnalyticsActivity.class));
            }
        });

        notifyUsersCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open Notification Broadcaster
                startActivity(new Intent(AdminDashboardActivity.this, NotificationBroadcastActivity.class));
            }
        });
    }
}
