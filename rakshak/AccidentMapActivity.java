package com.example.rakshak;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import android.util.Log;


public class AccidentMapActivity extends AppCompatActivity {
    private WebView webView;
    private final double PROXIMITY_RADIUS = 900; // meters
    private final ArrayList<double[]> accidentLocations = new ArrayList<>();
    private MediaPlayer mediaPlayer;
    private EditText locationInput;
    private Button checkButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accident_map);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1002);
        }

        webView = findViewById(R.id.webView);
        locationInput = findViewById(R.id.locationInput);
        checkButton = findViewById(R.id.checkButton);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/map.html");

        accidentLocations.add(new double[]{19.993073700000004, 73.8037119}); //  Dwarka
        accidentLocations.add(new double[]{19.9972292, 73.78198309999999}); // Trimbak Naka
        accidentLocations.add(new double[]{20.001142299999998, 73.78327}); // CBS Nashik
        accidentLocations.add(new double[]{19.944143, 73.77405259999999}); // Pathardi Phata

        checkButton.setOnClickListener(v -> {
            String locationName = locationInput.getText().toString().trim();
            if (!locationName.isEmpty()) {
                getLocationFromName(locationName);
            } else {
                Toast.makeText(this, "Please enter a location", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getLocationFromName(String locationName) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocationName(locationName, 1);
            if (addresses != null && !addresses.isEmpty()) {
                double lat = addresses.get(0).getLatitude();
                double lng = addresses.get(0).getLongitude();

                Log.d("LocationCheck", "Lat: " + lat + " | Lng: " + lng);

                checkProximity(lat, lng);
            } else {
                Toast.makeText(this, "Location not found", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    private void checkProximity(double userLat, double userLng) {
        boolean isDanger = false;
        for (double[] location : accidentLocations) {
            float[] results = new float[1];
            Location.distanceBetween(userLat, userLng, location[0], location[1], results);
            if (results[0] <= PROXIMITY_RADIUS) {
                isDanger = true;
                break;
            }
        }

        if (isDanger) {
            sendNotification();
            playAlert();
            Toast.makeText(this, "⚠️ Danger Zone Nearby!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "✅ Location is Safe", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendNotification() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = "alert_channel";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Accident Alerts", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setContentTitle("Alert!")
                .setContentText("You are near an accident-prone area.")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        Intent intent = new Intent(this, AccidentMapActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        builder.setContentIntent(pendingIntent);

        notificationManager.notify(1, builder.build());
    }

    private void playAlert() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.alert);
        }
        mediaPlayer.start();
    }

    @Override
    protected void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        super.onDestroy();
    }
}
