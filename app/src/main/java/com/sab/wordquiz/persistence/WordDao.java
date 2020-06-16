package com.sab.wordquiz.persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.sab.wordquiz.models.QuizMechanism;
import com.sab.wordquiz.models.Word;

import java.util.List;

@Dao
public interface WordDao {

    @Insert
    void insert(Word word);

    @Delete
    void delete(Word word);

    @Query("DELETE FROM words_table")
    void deleteAll();

    @Query("SELECT * FROM words_table ORDER BY id ASC")
    LiveData<List<Word>> getAllWords();

    @Query("SELECT * FROM words_table WHERE id= :id")
    LiveData<Word> getWordById(int id);

    @Query("SELECT value FROM words_table LIMIT 1")
    String getValue();

    @Query("SELECT * FROM words_table")
    List<Word>getOfflineData();

}
