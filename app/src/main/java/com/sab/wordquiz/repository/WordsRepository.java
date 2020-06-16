package com.sab.wordquiz.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.sab.wordquiz.models.Word;
import com.sab.wordquiz.persistence.WordDao;
import com.sab.wordquiz.persistence.WordsDatabase;

import java.util.List;

public class WordsRepository {
    private static WordDao wordDao;
    private static LiveData<List<Word>> allWords;

    private static WordsRepository instance;

    public static WordsRepository getInstance(Application application){
        if(instance == null){
            instance = new WordsRepository(application);
        }
        return instance;
    }
    private WordsRepository(Application application){
        WordsDatabase database = WordsDatabase.getInstance(application);
        wordDao = database.wordDao();
        allWords = wordDao.getAllWords();
    }

//    public WordsRepository(Application application) {
//        WordsDatabase database = WordsDatabase.getInstance(application);
//        wordDao = database.wordDao();
//        allWords = wordDao.getAllWords();
//    }

    public static void insert(final Word word) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                wordDao.insert(word);
                return null;
            }
        }.execute();
    }

    public static void delete(Word word) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                wordDao.delete(word);
                return null;
            }
        }.execute();

    }

    public LiveData<List<Word>> getAllWords() {
        return wordDao.getAllWords();
    }

    public static void deleteAll() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                wordDao.deleteAll();
                return null;
            }
        }.execute();
    }

    public LiveData<Word> getWordById(int position) {
        return wordDao.getWordById(position);

    }

}


