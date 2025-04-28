package com.example.rakshak;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.util.HashMap;

public class ReportAccidentActivity extends AppCompatActivity {

    private EditText locationNameEditText, descriptionEditText;
    private Button submitBtn;
    private ImageView imagePreview;
    private Button selectImageBtn;
    private Uri imageUri;
    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_accident);

        // Initialize the views
        locationNameEditText = findViewById(R.id.locationName);
        descriptionEditText = findViewById(R.id.description);
        submitBtn = findViewById(R.id.submitBtn);
        imagePreview = findViewById(R.id.imagePreview);
        selectImageBtn = findViewById(R.id.selectImageBtn);

        // Firebase instances
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseStorage storage = FirebaseStorage.getInstance();

        // Handle the submit button click
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String location = locationNameEditText.getText().toString().trim();
                String description = descriptionEditText.getText().toString().trim();

                // Check if fields are empty
                if (location.isEmpty() || description.isEmpty()) {
                    Toast.makeText(ReportAccidentActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Handle image upload to Firebase if an image is selected
                if (imageUri != null) {
                    StorageReference imageRef = storage.getReference().child("accident_photos/" + System.currentTimeMillis() + ".jpg");
                    imageRef.putFile(imageUri)
                            .addOnSuccessListener(taskSnapshot -> imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                                String imageUrl = uri.toString();
                                // Save the report to Firestore with image
                                saveReportToFirestore(db, location, description, imageUrl);
                            }))
                            .addOnFailureListener(e -> {
                                Toast.makeText(ReportAccidentActivity.this, "Image upload failed", Toast.LENGTH_SHORT).show();
                            });
                } else {
                    // Save the report to Firestore without image
                    saveReportToFirestore(db, location, description, null);
                }
            }
        });

        // Handle image selection from gallery
        selectImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });
    }

    // Function to save the report to Firestore
    private void saveReportToFirestore(FirebaseFirestore db, String location, String description, String imageUrl) {
        HashMap<String, Object> report = new HashMap<>();
        report.put("location", location);
        report.put("description", description);
        report.put("imageUrl", imageUrl);
        report.put("timestamp", System.currentTimeMillis());

        // Add the report to Firestore
        db.collection("accident_reports")
                .add(report)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "✅ Report submitted successfully", Toast.LENGTH_SHORT).show();
                    // Reset the form
                    locationNameEditText.setText("");
                    descriptionEditText.setText("");
                    imagePreview.setImageResource(0); // Clear image preview
                    imageUri = null; // Clear the image URI
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Failed to submit report", Toast.LENGTH_SHORT).show();
                });
    }

    // Handle the image selection result (gallery image)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                imagePreview.setImageBitmap(bitmap); // Display selected image in ImageView
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
