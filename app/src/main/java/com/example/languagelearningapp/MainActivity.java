package com.example.languagelearningapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int READ_STORAGE_PERMISSION_REQUEST_CODE = 1;
    TextView tvLanguage, tvAboutProgram;
    Switch languageSwitch;
    Button btnAddNewVocab, btnLearn, btnTest, btnShowWordsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_STORAGE_PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == READ_STORAGE_PERMISSION_REQUEST_CODE) {
            for (int i = 0; i < permissions.length; i++) {
                String permission = permissions[i];
                int grantResult = grantResults[i];

                if (permission.equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    if (grantResult == PackageManager.PERMISSION_GRANTED) {
                        loadMainActivity();
                    } else {
                        Toast.makeText(this, "Aplikacja nie może działać\nbez dostępu do karty SD!", Toast.LENGTH_SHORT).show();
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_STORAGE_PERMISSION_REQUEST_CODE);
                    }
                }
            }
        }
    }

    void loadMainActivity() {
        languageSwitch = findViewById(R.id.switchLanguage);
        tvLanguage = findViewById(R.id.textViewLanguage);
        tvAboutProgram = findViewById(R.id.textViewAboutProgram);

        btnAddNewVocab = findViewById(R.id.btnAddVocab);
        btnLearn = findViewById(R.id.btnLearnMode);
        btnTest = findViewById(R.id.btnTestMode);
        btnShowWordsList = findViewById(R.id.btnShowWordsList);

        languageSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    tvLanguage.setText("Polski");
                } else {
                    tvLanguage.setText("Angielski");
                }
            }
        });

        btnAddNewVocab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddVocabularyActivity.class);
                startActivity(intent);
            }
        });

        btnShowWordsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListWordsActivity.class);
                startActivity(intent);
            }
        });

        btnLearn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LearnActivity.class);
                intent.putExtra("language", tvLanguage.getText().toString());
                startActivity(intent);
            }
        });

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TestActivity.class);
                intent.putExtra("language", tvLanguage.getText().toString());
                startActivity(intent);
            }
        });

        tvAboutProgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AboutProgram.class);
                startActivity(intent);
            }
        });
    }
}
