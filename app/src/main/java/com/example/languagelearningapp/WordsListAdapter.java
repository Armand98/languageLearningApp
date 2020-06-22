package com.example.languagelearningapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.Objects;

public class WordsListAdapter extends ArrayAdapter<Word> {

    private Context mContext;
    private int mResource;

    public WordsListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Word> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String polishWord = Objects.requireNonNull(getItem(position)).getPolishWord();
        String englishWord = Objects.requireNonNull(getItem(position)).getEnglishWord();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvPolishName = convertView.findViewById(R.id.textViewPolishWordAdapterItem);
        TextView tvEnglishName = convertView.findViewById(R.id.textViewEnglishWordAdapterItem);

        tvPolishName.setText(polishWord);
        tvEnglishName.setText(englishWord);

        return convertView;
    }
}
