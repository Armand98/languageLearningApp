package com.example.languagelearningapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

public class ListWordsActivity extends AppCompatActivity {

    TextView tvInfo;
    ListView list;
    WordsListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_words);

        tvInfo = findViewById(R.id.textViewInfo);
        list = findViewById(R.id.WordsList);
        final Question question = new Question();

        if (!question.getKeysAsArray().isEmpty()) {
            final ArrayList<Word> wordsList = question.getWordsList();
            adapter = new WordsListAdapter(this, R.layout.adapter_view_layout, wordsList);
            list.setAdapter(adapter);

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                    final String key = wordsList.get(position).getPolishWord();
                    final AlertDialog.Builder alert = new AlertDialog.Builder(ListWordsActivity.this);
                    alert.setTitle("Usunąć tę fiszkę?");
                    alert.setMessage("Czy na pewno chcesz usunąć \"" + key + "\" z bazy fiszek?");
                    alert.setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Vocabulary vocabulary = new Vocabulary();
                            vocabulary.deleteWord(key);
                            wordsList.remove(position);
                            adapter.notifyDataSetChanged();
                        }
                    });
                    alert.setNegativeButton("Nie", null);
                    alert.create().show();
                }
            });
        } else {
            tvInfo.setText("Nie masz żadnych fiszek");
        }
    }
}
