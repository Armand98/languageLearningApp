package com.example.languagelearningapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LearnActivity extends AppCompatActivity {

    TextView tvTranslationMode, tvWordToTranslate;
    Button btnCheckAnswer;
    EditText etInputWord;
    String strWordToTranslate, strAnswer;
    boolean coveredAnswer;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);

        tvTranslationMode = findViewById(R.id.textViewLearnTranslationMode);
        tvWordToTranslate = findViewById(R.id.textViewWordToTranslate);
        etInputWord = findViewById(R.id.inputLearnWord);
        btnCheckAnswer = findViewById(R.id.btnCheckAnswer);
        coveredAnswer = true;

        Intent incomingIntent = getIntent();
        final String incomingTranslationMode = incomingIntent.getStringExtra("language");
        String translationModeText = "Przetłumacz na " + incomingTranslationMode + ":";
        tvTranslationMode.setText(translationModeText);

        final Question question = new Question();

        if(!question.getKeysAsArray().isEmpty()) {
            assert incomingTranslationMode != null;
            setNewRandomQuestion(incomingTranslationMode, question);

            tvWordToTranslate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(coveredAnswer) {
                        tvWordToTranslate.setText(strAnswer);
                        coveredAnswer = false;
                    } else {
                        tvWordToTranslate.setText(strWordToTranslate);
                        coveredAnswer = true;
                    }
                }
            });

            btnCheckAnswer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String inputWordStr = etInputWord.getText().toString().trim().toLowerCase();
                    if(inputWordStr.equals(strAnswer)) {
                        Toast.makeText(LearnActivity.this, "Dobrze :)", Toast.LENGTH_SHORT).show();
                        etInputWord.setText("");
                        setNewRandomQuestion(incomingTranslationMode, question);
                    } else if(inputWordStr.isEmpty()) {
                        Toast.makeText(LearnActivity.this, "Nie, odpowiedź to nie jest NULL :)", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LearnActivity.this, "Źle. Kliknij na tekst by zobaczyć tłumaczenie.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            tvTranslationMode.setText("");
            tvWordToTranslate.setText("Nie masz żadnych fiszek");
            etInputWord.setEnabled(false);
            btnCheckAnswer.setEnabled(false);
        }
    }

    void setNewRandomQuestion(String incomingTranslationMode, Question question) {
        question.randQuestion();
        if(incomingTranslationMode.equals("Polski")) {
            strWordToTranslate = question.getEnglishWord();
            strAnswer = question.getPolishWord();
            tvWordToTranslate.setText(strWordToTranslate);
        } else {
            strAnswer = question.getEnglishWord();
            strWordToTranslate = question.getPolishWord();
            tvWordToTranslate.setText(strWordToTranslate);
        }
    }
}
