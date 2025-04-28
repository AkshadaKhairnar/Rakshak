package com.example.rakshak;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.Arrays;
import java.util.List;


public class SigninActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Sign Up as User
        TextView signUpUser = findViewById(R.id.signUpUser);
        signUpUser.setOnClickListener(v -> {
            Toast.makeText(SigninActivity.this, "Sign Up as User", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SigninActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

        // Log In as User
        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(v -> {
            String email = ((EditText) findViewById(R.id.email)).getText().toString();
            String password = ((EditText) findViewById(R.id.password)).getText().toString();
            if (!email.isEmpty() && !password.isEmpty()) {
                loginUser(email, password);  // Call login method
            } else {
                Toast.makeText(SigninActivity.this, "Please enter email and password.", Toast.LENGTH_SHORT).show();
            }
        });

        // Log In as Admin
        Button loginButton1 = findViewById(R.id.loginButton1);
        loginButton1.setOnClickListener(v -> {
            String email = ((EditText) findViewById(R.id.email)).getText().toString();
            String password = ((EditText) findViewById(R.id.password)).getText().toString();
            if (!email.isEmpty() && !password.isEmpty()) {
                loginAdmin(email, password);  // Call login method for admin
            } else {
                Toast.makeText(SigninActivity.this, "Please enter email and password.", Toast.LENGTH_SHORT).show();
            }
        });

        // Sign Up as Admin
        TextView signUpAdmin = findViewById(R.id.signUpAdmin);
        signUpAdmin.setOnClickListener(v -> {
            Toast.makeText(SigninActivity.this, "Sign Up as Admin", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SigninActivity.this, SignupAdminActivity.class);
            startActivity(intent);
        });
    }

    // Method for User login
    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign-in success, navigate to User Dashboard
                        FirebaseUser user = mAuth.getCurrentUser();
                        Intent intent = new Intent(SigninActivity.this, UserDashboardActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Sign-in failed, show a message to the user
                        Toast.makeText(SigninActivity.this, "Authentication failed. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Method for Admin login
    private void loginAdmin(String email, String password) {
        // Replace with your actual admin emails
        List<String> allowedAdmins = Arrays.asList("admin1@gmail.com", "admin2@gmail.com");
        String adminPassword = "admin123";
        if (!allowedAdmins.contains(email)) {
            Toast.makeText(this, "You are not authorized as Admin!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.equals(adminPassword)) {
            Toast.makeText(this, "Incorrect admin password!", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        startActivity(new Intent(SigninActivity.this, AdminDashboardActivity.class));
                        finish();
                    } else {
                        Toast.makeText(SigninActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
