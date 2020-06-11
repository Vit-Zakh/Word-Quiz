package com.sab.wordquiz.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sab.wordquiz.R;
import com.sab.wordquiz.WordViewModel;
import com.sab.wordquiz.adapters.WordListAdapter;
import com.sab.wordquiz.databinding.FragmentSettingsBinding;
import com.sab.wordquiz.models.Word;

import java.util.List;

public class SettingsFragment extends Fragment implements WordListAdapter.OnDeleteBtnListener {
    FragmentSettingsBinding binding;
    WordViewModel wordViewModel;
    WordListAdapter adapter;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        adapter = new WordListAdapter(this::onDeleteBtnClick);
        binding.wordsList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.wordsList.setAdapter(adapter);

        wordViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(this.getActivity().getApplication())).get(WordViewModel.class);
        wordViewModel.getAllWords().observe(getViewLifecycleOwner(), new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                adapter.setWordsList(words);
            }
        });

        binding.addTheWord.setOnClickListener(v -> {
            String inputString = binding.editText.getText().toString().trim();
            if (!TextUtils.isEmpty(inputString)) {
                Word newWord = new Word(inputString);
                wordViewModel.insert(newWord);
                binding.editText.setText("");
                Toast.makeText(v.getContext(), inputString, Toast.LENGTH_SHORT).show();
            } else Toast.makeText(v.getContext(), "Enter the word", Toast.LENGTH_SHORT).show();
        });
        return binding.getRoot();
    }

    @Override
    public void onDeleteBtnClick(int position) {
        wordViewModel.delete(adapter.getWordAt(position));
    }
}
