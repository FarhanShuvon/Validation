package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button registerButton;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register); // Ensure you have this layout file

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        registerButton = findViewById(R.id.registerButton);
        databaseHelper = new DatabaseHelper(this);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // Check if fields are empty
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please enter both username and password", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validate username: can't start with a number
                if (username.isEmpty() || Character.isDigit(username.charAt(0))) {
                    Toast.makeText(RegisterActivity.this, "Username can't start with a number", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validate password: must be at least 6 characters, contain at least 1 digit and 1 special character
                if (!password.matches("^(?=.*[0-9])(?=.*[!@#$%^&*()_+=-])[A-Za-z0-9!@#$%^&*()_+=-]{6,}$")) {
                    Toast.makeText(RegisterActivity.this, "Password must be at least 6 characters long and contain at least 1 digit and 1 special character", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Insert user into the database
                if (databaseHelper.insertUser(username, password)) {
                    Toast.makeText(RegisterActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                    // Clear input fields after successful registration
                    usernameEditText.setText("");
                    passwordEditText.setText("");
                    // Navigate back to MainActivity
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
