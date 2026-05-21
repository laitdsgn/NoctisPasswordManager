package com.example.passwordmanager;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    HashGenerator hashGenerator;
    Button loginButton;
    EditText emailInput;
    TextView loginLogs;
    EditText passwordInput;
    LinearLayout loginLayout;
    LinearLayout mainLayout;
    SaltGenerator saltGenerator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        hashGenerator = new HashGenerator();
        saltGenerator = new SaltGenerator();
        loginButton = findViewById(R.id.btnLogin);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginLogs = findViewById(R.id.loginLogs);
        loginLayout = findViewById(R.id.layoutLogin);
        mainLayout = findViewById(R.id.layoutManager);

        String temporaryGeneratedSalt = saltGenerator.GenerateSalt();
        String temporaryPasswordTest = "johndoe123!";
        String temporaryHash;
        try {
             temporaryHash = hashGenerator.GenerateHashWithSalt(temporaryPasswordTest, temporaryGeneratedSalt);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        loginButton.setOnClickListener(v -> {
            if (emailInput.getText().toString().isEmpty() || passwordInput.getText().toString().isEmpty()) {
                loginLogs.setTextColor(Color.parseColor("#f55142"));
                loginLogs.setText("Please enter both email and password.");
                return;
            } else {
                try {
                    if(Objects.equals(temporaryHash, hashGenerator.GenerateHashWithSalt(passwordInput.getText().toString(), temporaryGeneratedSalt))) {
                        loginLogs.setTextColor(Color.parseColor("#42f56c"));
                        loginLogs.setText("Login Successful!");
                        loginLayout.setVisibility(LinearLayout.GONE);
                        mainLayout.setVisibility(LinearLayout.VISIBLE);
                    } else {
                        loginLogs.setTextColor(Color.parseColor("#f55142"));
                        loginLogs.setText("Wrong email or password.");


                    }
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }
            }
        });





    }

}