package com.sab.wordquiz.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sab.wordquiz.databinding.ListItemBinding;
import com.sab.wordquiz.models.Word;

import java.util.ArrayList;
import java.util.List;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordHolder> {
    private List<Word> wordsList = new ArrayList<>();
    private OnDeleteBtnListener onDeleteBtnListener;

    public WordListAdapter(OnDeleteBtnListener onDeleteBtnListener) {
        this.onDeleteBtnListener = onDeleteBtnListener;
    }

    @NonNull
    @Override
    public WordHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemBinding binding = ListItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new WordHolder(binding, onDeleteBtnListener);
    }

    @Override
    public void onBindViewHolder(@NonNull WordHolder holder, int position) {
        Word currentWord = wordsList.get(position);
        holder.binding.ListItemWord.setText(currentWord.getValue());
    }

    public void setWordsList(List<Word> wordsList) {
        this.wordsList = wordsList;
        notifyDataSetChanged();
    }

    public Word getWordAt(int position) {
        return wordsList.get(position);
    }

    @Override
    public int getItemCount() {
        return wordsList.size();
    }

    public class WordHolder extends RecyclerView.ViewHolder {
        ListItemBinding binding;
        OnDeleteBtnListener onDeleteBtnListener;

        public WordHolder(@NonNull ListItemBinding binding, OnDeleteBtnListener onDeleteBtnListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.onDeleteBtnListener = onDeleteBtnListener;
            binding.itemDeleteBtn.setOnClickListener(v -> onDeleteBtnListener.onDeleteBtnClick(getAdapterPosition()));
        }
    }

    public interface OnDeleteBtnListener {
        void onDeleteBtnClick(int position);
    }

}
