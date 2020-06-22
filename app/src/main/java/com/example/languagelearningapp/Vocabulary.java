package com.example.languagelearningapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;


@SuppressLint("Registered")
public class Vocabulary extends AppCompatActivity {

    private Context mContext;
    private static final String FILE_NAME = "fiszki.txt";
    private static final String FOLDER_NAME = "/Download/";

    Vocabulary(Context context) {
        mContext = context;
    }

    Vocabulary() { }

    private boolean isJSONValid(String test) {
        try {
            new JSONObject(test);
        } catch (JSONException ex) {
            try {
                new JSONArray(test);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }

    private JSONObject createJSONFromString(String jsonString) {
        if(isJSONValid(jsonString)) {
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(jsonString);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonObject;
        } else {
            return new JSONObject();
        }
    }

    protected boolean addNewWord(String polishWord, String englishWord) {
        String jsonString = readJsonStringFromFile();
        JSONObject jsonObject = createJSONFromString(jsonString);
        String value;
        boolean valueExists = false;

        for (Iterator<String> iterator = jsonObject.keys(); iterator.hasNext(); ) {
            String key = iterator.next();
            try {
                value = jsonObject.getString(key);
                if(value.equals(englishWord)) {
                    valueExists = true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        try {
            if(jsonObject.has(polishWord) || valueExists) {
                Toast.makeText(mContext, "Masz już fiszkę z tym słówkiem :)", Toast.LENGTH_LONG).show();
                return false;
            } else {
                jsonObject.put(polishWord, englishWord);
                saveFicheToFileOnSD(jsonObject.toString());
                Toast.makeText(mContext, "Dodano nową fiszkę :)", Toast.LENGTH_LONG).show();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(mContext, "Dodawanie fiszki nie powiodło się :(", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    public void deleteWord(String key) {
        String jsonString = readJsonStringFromFile();
        JSONObject jsonObject = createJSONFromString(jsonString);
        jsonObject.remove(key);
        saveFicheToFileOnSD(jsonObject.toString());
    }

    protected JSONObject getJSONObjectFromFile() {
        return createJSONFromString(readJsonStringFromFile());
    }

    private Boolean isFileCreated() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + FOLDER_NAME;
        File file = new File(path, FILE_NAME );
        if (file.exists()) {
            return true;
        } else {
            return false;
        }
    }

    private void saveFicheToFileOnSD(String fiche) {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + FOLDER_NAME;
        File textFile = new File(path, FILE_NAME);
        try {
            FileOutputStream fos = new FileOutputStream(textFile);
            fos.write(fiche.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readJsonStringFromFile() {
        if(isFileCreated())
        {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + FOLDER_NAME;
            File textFile = new File(path, FILE_NAME);
            StringBuilder jsonString = new StringBuilder();
            try {
                BufferedReader br = new BufferedReader(new FileReader(textFile));
                String line;
                while ((line = br.readLine()) != null) {
                    jsonString.append(line);
                }
                br.close();
            } catch (IOException e) {
                Toast.makeText(this, "Nie dano uprawnień dostępu do karty SD", Toast.LENGTH_SHORT).show();
            }
            return jsonString.toString();
        } else {
            return "";
        }
    }

}
