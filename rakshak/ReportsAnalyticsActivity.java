package com.example.rakshak;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;  // Correct import

import com.google.firebase.firestore.*;

import java.util.*;

public class ReportsAnalyticsActivity extends AppCompatActivity {

    private TextView totalReportsText;
    private BarChart barChart;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports_analytics);

        totalReportsText = findViewById(R.id.totalReportsText);
        barChart = findViewById(R.id.barChart);
        db = FirebaseFirestore.getInstance();

        loadReportData();
    }

    private void loadReportData() {
        db.collection("accident_reports")
                .addSnapshotListener((snapshots, error) -> {
                    if (error != null || snapshots == null) {
                        Toast.makeText(this, "Failed to fetch reports", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    int total = snapshots.size();
                    totalReportsText.setText("Total Reports Submitted: " + total);

                    Map<String, Integer> locationCount = new HashMap<>();
                    for (DocumentSnapshot doc : snapshots) {
                        String location = doc.getString("location");
                        if (location != null) {
                            locationCount.put(location, locationCount.getOrDefault(location, 0) + 1);
                        }
                    }

                    displayChart(locationCount);
                });
    }

    private void displayChart(Map<String, Integer> locationData) {
        List<BarEntry> entries = new ArrayList<>();
        List<String> labels = new ArrayList<>();

        int index = 0;
        for (Map.Entry<String, Integer> entry : locationData.entrySet()) {
            entries.add(new BarEntry(index, entry.getValue()));
            labels.add(entry.getKey());
            index++;
        }

        BarDataSet dataSet = new BarDataSet(entries, "Reports by Location");
        dataSet.setColor(Color.parseColor("#FF5722"));
        dataSet.setValueTextSize(14f);

        BarData barData = new BarData(dataSet);
        barChart.setData(barData);
        barChart.getDescription().setEnabled(false);
        barChart.getLegend().setEnabled(true);  // Enable the legend if you want

        // Use an anonymous class to implement ValueFormatter without @Override
        barChart.getXAxis().setValueFormatter(new ValueFormatter() {
            public String getFormattedValue(float value, XAxis axis) {
                // Convert the float value to an int and use it as the index
                return labels.get((int) Math.round(value) % labels.size()); // Cast the rounded value to int
            }
        });

        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getXAxis().setGranularity(1f);
        barChart.getXAxis().setLabelRotationAngle(-45);
        barChart.getAxisRight().setEnabled(false);  // Disable the right axis for better appearance
        barChart.invalidate();  // Refresh the chart
    }
}
