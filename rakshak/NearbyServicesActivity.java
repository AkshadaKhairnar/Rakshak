package com.example.rakshak;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class NearbyServicesActivity extends AppCompatActivity {

    Button sos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_services);

        sos = findViewById(R.id.sosButton);

        sos.setOnClickListener(v ->
                Toast.makeText(this, "🚨 SOS Alert Sent to Emergency Services!", Toast.LENGTH_LONG).show()
        );
    }
}
