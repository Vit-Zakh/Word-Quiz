package com.sab.wordquiz;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.sab.wordquiz.models.Word;
import com.sab.wordquiz.repository.WordsRepository;

import java.util.List;

public class WordViewModel extends AndroidViewModel {
    private WordsRepository repository;
    private LiveData<List<Word>> allWords;

    public WordViewModel(@NonNull Application application) {
        super(application);
//        repository = new WordsRepository(application);
        repository = WordsRepository.getInstance(application);
        allWords = repository.getAllWords();
    }

    public void insert(Word word) {
        repository.insert(word);
    }

    public void delete(Word word) {
        repository.delete(word);
    }

    public LiveData<List<Word>> getAllWords() {
        return allWords;
    }

}
