package com.example.overtime_scheduling;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ContactUsActivity extends AppCompatActivity {
    EditText etEmail, etDescription;
    Button btnSubmit, btnCancel;
    String userId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        Intent intent = getIntent();
        if(intent.hasExtra("USER_ID")) {
            userId = intent.getStringExtra("USER_ID");
        }

        etEmail = findViewById(R.id.etEmail);
        etDescription = findViewById(R.id.etDescription);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnCancel = findViewById(R.id.btnCancel);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ContactUsActivity.this, "Reported Successfully", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(ContactUsActivity.this, MainActivity.class);
                i.putExtra("USER_ID", userId);
                startActivity(i);
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ContactUsActivity.this, MainActivity.class);
                i.putExtra("USER_ID", userId);
                startActivity(i);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);
        finish();
    }
}