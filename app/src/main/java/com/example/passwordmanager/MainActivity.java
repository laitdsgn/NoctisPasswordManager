package com.example.passwordmanager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    HashGenerator hashGenerator;
    TextView out;
    Button btn;
    EditText passwordText;
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
        btn = findViewById(R.id.button);
        out = findViewById(R.id.passout);
        passwordText = findViewById(R.id.pass1);


        btn.setOnClickListener(View -> {
            String newSalt = saltGenerator.GenerateSalt();
            String fullHashWithSalt;
            try {
               fullHashWithSalt = hashGenerator.GenerateHashWithSalt(passwordText.getText().toString(), newSalt);
               out.setText("Hash: " + fullHashWithSalt + "\nSalt: " + newSalt + "Decrypt: " + hashGenerator.GenerateHashWithSalt(passwordText.getText().toString(), newSalt));

            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }

        });




    }

}