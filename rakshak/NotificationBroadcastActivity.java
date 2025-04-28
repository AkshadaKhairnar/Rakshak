package com.example.rakshak;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class NotificationBroadcastActivity extends AppCompatActivity {

    private EditText alertTitle, alertMessage;
    private Button sendBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_broadcast);

        alertTitle = findViewById(R.id.alertTitle);
        alertMessage = findViewById(R.id.alertMessage);
        sendBtn = findViewById(R.id.sendBtn);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = alertTitle.getText().toString().trim();
                String message = alertMessage.getText().toString().trim();

                if (title.isEmpty() || message.isEmpty()) {
                    Toast.makeText(NotificationBroadcastActivity.this, "Please enter both title and message", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 🔔 Replace this with Firebase Cloud Messaging logic later
                Toast.makeText(NotificationBroadcastActivity.this, "Alert Sent:\n" + title + "\n" + message, Toast.LENGTH_LONG).show();

                alertTitle.setText("");
                alertMessage.setText("");
            }
        });
    }
}
