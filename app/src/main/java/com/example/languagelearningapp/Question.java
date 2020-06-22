package com.example.languagelearningapp;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Question {

    private String polishWord, englishWord;
    private ArrayList<Word> wordsList;
    private HashMap<String, String> wordsMap;
    private List<String> keysAsArray, quizKeysArray;
    private int questionQuantity;

    Question() {
        Vocabulary vocabulary = new Vocabulary();
        JSONObject JSONWords = vocabulary.getJSONObjectFromFile();
        wordsMap = new HashMap<>();
        wordsList = new ArrayList<>();
        loadJSONObjectToMap(JSONWords);
        keysAsArray = new ArrayList<>(wordsMap.keySet());
        questionQuantity = keysAsArray.size();
        if(!keysAsArray.isEmpty()) {
            setWordsList();
            createQuizKeysArray(questionQuantity);
            randQuestion();
        }
    }

    public ArrayList<Word> getWordsList() {
        return wordsList;
    }

    public void setWordsList() {
        for (String key : keysAsArray) {
            Word word = new Word(key, wordsMap.get(key));
            wordsList.add(word);
        }
    }

    private void loadJSONObjectToMap(JSONObject jsonObject) {
        Iterator<String> keys = jsonObject.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            try {
                wordsMap.put(key, jsonObject.getString(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void createQuizKeysArray(int numberOfQuestions) {
        Collections.shuffle(keysAsArray);
        quizKeysArray = new ArrayList<>();
        for(int i=0; i<numberOfQuestions; i++) {
            quizKeysArray.add(keysAsArray.get(i));
        }
    }

    public void setNextQuizQuestion(int index) {
        polishWord = quizKeysArray.get(index);
        englishWord = wordsMap.get(quizKeysArray.get(index));
    }

    protected int getQuestionQuantity() {
        return questionQuantity;
    }

    protected void randQuestion() {
        Random r = new Random();
        int randomIndex = r.nextInt(keysAsArray.size());
        polishWord = keysAsArray.get(randomIndex);
        englishWord = wordsMap.get(keysAsArray.get(randomIndex));
    }

    public String getPolishWord() {
        return polishWord;
    }

    public String getEnglishWord() {
        return englishWord;
    }

    public List<String> getKeysAsArray() {
        return keysAsArray;
    }
}
