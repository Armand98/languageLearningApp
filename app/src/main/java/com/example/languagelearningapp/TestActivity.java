package com.example.languagelearningapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TestActivity extends AppCompatActivity {

    TextView tvTranslationMode, tvWordToTranslate, tvQuestionCounter, tvPoints;
    Button btnNextQuestion;
    EditText etInputWord;
    String strWordToTranslate, strAnswer;
    int questionQuantity, questionIndex, correctAnswers;
    boolean endOfGameStatus;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        tvTranslationMode = findViewById(R.id.textViewTestTranslationMode);
        tvQuestionCounter = findViewById(R.id.textViewQuestionsCounter);
        tvWordToTranslate = findViewById(R.id.textViewWordToTranslate);
        tvPoints = findViewById(R.id.textViewPointsCounter);
        etInputWord = findViewById(R.id.inputTestWord);
        btnNextQuestion = findViewById(R.id.btnNextQuestion);

        Intent incomingIntent = getIntent();
        final String incomingTranslationMode = incomingIntent.getStringExtra("language");
        String translationModeText = "Przetłumacz na " + incomingTranslationMode + ":";
        tvTranslationMode.setText(translationModeText);

        final Question question = new Question();
        questionQuantity = question.getQuestionQuantity();
        questionIndex = 0;
        correctAnswers = 0;

        if(!question.getKeysAsArray().isEmpty()) {
            if(questionQuantity > 10  && questionQuantity <= 20) {
                questionQuantity = 10;
            } else if (questionQuantity > 20) {
                questionQuantity = 15;
            }
            endOfGameStatus = false;
            assert incomingTranslationMode != null;
            setNextQuestion(incomingTranslationMode, question, questionIndex++);
            updateQuestionCounter();

            btnNextQuestion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String inputWordStr = etInputWord.getText().toString().trim().toLowerCase();
                    if(inputWordStr.equals(strAnswer)) {
                        correctAnswers++;
                        if(questionIndex < questionQuantity) {
                            setNextQuestion(incomingTranslationMode, question, questionIndex++);
                            updateQuestionCounter();
                        } else {
                            showEndgameScreen(correctAnswers, questionQuantity);
                        }
                    } else if(inputWordStr.isEmpty()) {
                        Toast.makeText(TestActivity.this, "Uzupełnij pole", Toast.LENGTH_SHORT).show();
                    } else {
                        if(questionIndex < questionQuantity) {
                            setNextQuestion(incomingTranslationMode, question, questionIndex++);
                            updateQuestionCounter();
                        } else {
                            showEndgameScreen(correctAnswers, questionQuantity);
                        }
                    }
                }
            });
        } else {
            tvWordToTranslate.setText("Nie masz żadnych fiszek");
            tvPoints.setText("");
            tvTranslationMode.setText("");
            tvQuestionCounter.setText("");
            etInputWord.setEnabled(false);
            btnNextQuestion.setEnabled(false);
        }
    }

    @SuppressLint("SetTextI18n")
    void showEndgameScreen(int correctAnswers, int questionQuantity) {
        tvTranslationMode.setVisibility(View.INVISIBLE);
        tvWordToTranslate.setVisibility(View.INVISIBLE);
        tvQuestionCounter.setVisibility(View.INVISIBLE);
        if(correctAnswers > (questionQuantity/2)) {
            tvPoints.setTextColor(Color.parseColor("#00ff00"));
        }
        tvPoints.setText("Twój wynik: " + correctAnswers + "/" + questionQuantity);
        tvPoints.setVisibility(View.VISIBLE);
        btnNextQuestion.setEnabled(false);
        endOfGameStatus = true;
        etInputWord.setEnabled(false);
        createAlertDialog("Koniec testu", "Czy chcesz zrobić kolejny test?", false);
    }

    void setNextQuestion(String incomingTranslationMode, Question question, int index) {
        question.setNextQuizQuestion(index);
        if(incomingTranslationMode.equals("Polski")) {
            strWordToTranslate = question.getEnglishWord();
            strAnswer = question.getPolishWord();
            tvWordToTranslate.setText(strWordToTranslate);
        } else {
            strAnswer = question.getEnglishWord();
            strWordToTranslate = question.getPolishWord();
            tvWordToTranslate.setText(strWordToTranslate);
        }
        etInputWord.setText("");
    }

    @SuppressLint("SetTextI18n")
    void updateQuestionCounter() {
        tvQuestionCounter.setText(questionIndex + "/" + questionQuantity);
    }

    // if isEndOfGame is true, then it's end of game dialog
    // if isEndOfGame is false, then it's exit action in the middle of the exam
    void createAlertDialog(String title, String msg, final boolean isEndOfGame) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(title);
        alert.setMessage(msg);
        alert.setPositiveButton("Tak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(isEndOfGame) {
                    TestActivity.super.onBackPressed();
                } else {
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }

            }
        });
        alert.setNegativeButton("Nie", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(isEndOfGame) {
                    Toast.makeText(TestActivity.this, "Dobry wybór :)", Toast.LENGTH_LONG).show();
                } else {
                    TestActivity.super.onBackPressed();
                }
            }
        });
        alert.create().show();
    }

    @Override
    public void onBackPressed() {
        if(questionQuantity == 0) {
            super.onBackPressed();
        } else {
            if(endOfGameStatus) {
                createAlertDialog("Koniec testu", "Czy chcesz zrobić kolejny test?", false);
            } else {
                createAlertDialog("Zakończenie testu", "Czy na pewno chcesz przerwać test?", true);
            }
        }
    }
}
