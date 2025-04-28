package com.example.rakshak;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText nameField, emailField, passwordField;
    private Button userSignupBtn, adminSignupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        nameField = findViewById(R.id.fullName);
        emailField = findViewById(R.id.email);
        passwordField = findViewById(R.id.editTextTextPassword);
        userSignupBtn = findViewById(R.id.userSignupButton);
        adminSignupBtn = findViewById(R.id.adminSignupButton);

        userSignupBtn.setOnClickListener(v -> {
            String name = nameField.getText().toString().trim();
            String email = emailField.getText().toString().trim();
            String password = passwordField.getText().toString().trim();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(SignUpActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, "Signup successful! Please login.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUpActivity.this, SigninActivity.class));
                            finish();
                        } else {
                            Toast.makeText(SignUpActivity.this, "Signup failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        adminSignupBtn.setOnClickListener(v -> {
            Toast.makeText(SignUpActivity.this, "Admin sign-up is not allowed.", Toast.LENGTH_SHORT).show();
        });
    }
}
