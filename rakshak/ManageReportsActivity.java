package com.example.rakshak;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Button;
import android.app.AlertDialog;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ManageReportsActivity extends AppCompatActivity {

    private ListView reportsListView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> reports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_reports);

        reportsListView = findViewById(R.id.reportsListView);
        Button addReportButton = findViewById(R.id.addReportButton);

        // Dummy data
        reports = new ArrayList<>();
        reports.add("📍 CBS, Nashik\n⚠️ Accident reported on 04-Apr at 5:30 PM");
        reports.add("📍 Trimbak Naka\n⚠️ Major incident reported on 03-Apr at 8:00 AM");
        reports.add("📍 Pathardi Phata\n⚠️ Minor incident on 02-Apr at 11:45 AM");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, reports);
        reportsListView.setAdapter(adapter);

        addReportButton.setOnClickListener(v -> showAddReportDialog());
    }

    private void showAddReportDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add New Report");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 40, 50, 10);

        EditText inputLocation = new EditText(this);
        inputLocation.setHint("Location name");
        layout.addView(inputLocation);

        EditText inputDescription = new EditText(this);
        inputDescription.setHint("Incident description & time");
        layout.addView(inputDescription);

        builder.setView(layout);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String location = inputLocation.getText().toString().trim();
            String description = inputDescription.getText().toString().trim();

            if (!location.isEmpty() && !description.isEmpty()) {
                String newReport = "📍 " + location + "\n⚠️ " + description;
                reports.add(0, newReport); // add to top
                adapter.notifyDataSetChanged();
                Toast.makeText(this, "Report added", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Please enter valid data", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }
}
