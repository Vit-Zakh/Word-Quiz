package com.sab.wordquiz.persistence;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.sab.wordquiz.models.Word;

@Database(entities = {Word.class}, version = 1)
public abstract class WordsDatabase extends RoomDatabase {

    private static WordsDatabase instance;

    public abstract WordDao wordDao();

    public static synchronized WordsDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    WordsDatabase.class,
                    "word_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new InitiateDatabaseAsyncTask(instance).execute();
        }
    };

    private static class InitiateDatabaseAsyncTask extends AsyncTask<Void, Void, Void> {
        private WordDao wordDao;

        public InitiateDatabaseAsyncTask(WordsDatabase database) {
            this.wordDao = database.wordDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            wordDao.insert(new Word("no"));
            wordDao.insert(new Word("internet"));
            wordDao.insert(new Word("connection"));
            return null;
        }
    }

}
