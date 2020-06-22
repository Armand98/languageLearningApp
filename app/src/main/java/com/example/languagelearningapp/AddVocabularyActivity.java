package com.example.languagelearningapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddVocabularyActivity extends AppCompatActivity {

    TextView tvWordsCounter;
    EditText etPolishWord, etEnglishWord;
    Button btnAddWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vocabulary);

        etPolishWord = findViewById(R.id.inputPolishWord);
        etEnglishWord = findViewById(R.id.inputEnglishWord);
        tvWordsCounter = findViewById(R.id.textViewWordsCounter);
        btnAddWord = findViewById(R.id.btnAddWord);

        final Question question = new Question();
        final int questionQuantity = question.getQuestionQuantity();
        tvWordsCounter.setText(String.valueOf(questionQuantity));

        btnAddWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String polishWord = etPolishWord.getText().toString().trim().toLowerCase();
                String englishWord = etEnglishWord.getText().toString().trim().toLowerCase();

                if(!polishWord.isEmpty() && !englishWord.isEmpty()) {
                    Vocabulary vocabulary = new Vocabulary(AddVocabularyActivity.this);
                    if(vocabulary.addNewWord(polishWord, englishWord)) {
                        tvWordsCounter.setText(String.valueOf(Integer.parseInt(tvWordsCounter.getText().toString())+1));
                    }
                    etPolishWord.setText("");
                    etEnglishWord.setText("");
                } else {
                    Toast.makeText(AddVocabularyActivity.this, "Uzupełnij pola by dodać fiszkę.", Toast.LENGTH_LONG ).show();
                }
            }
        });
    }
}
