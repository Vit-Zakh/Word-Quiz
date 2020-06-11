package com.sab.wordquiz.models;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class QuizMechanism {
    Random random = new Random();
    Stack<Integer> pushedButtons = new Stack<>();
    private static final String TAG = "QuizzMechanism";
    String wordToShuffle;

    public QuizMechanism() {
    }

    public char[] shuffleTheWord(String wordToShuffle) {
        char[] mArr = wordToShuffle.toCharArray();
        List<Character> mList = new ArrayList<>();
        for (char a : mArr) mList.add(a);
        Collections.shuffle(mList);
        char[] shaffledArr = new char[mList.size()];
        for (int i = 0; i < mList.size(); i++) {
            shaffledArr[i] = mList.get(i);
        }
        return shaffledArr;
    }

    public void shuffleFunction(ViewGroup grid, String wordShuffle, final EditText answerField) {
        if (wordShuffle != null) {
            grid.removeAllViews();
            answerField.setText("");
            wordToShuffle = wordShuffle;
            Log.d(TAG, "onClick: " + wordToShuffle);
            setTheGrid(grid, wordToShuffle, answerField);
        } else
            Toast.makeText(grid.getContext(), "Enter the word to shuffle", Toast.LENGTH_SHORT).show();
    }

    public void setTheGrid(ViewGroup viewGroup, String word, final EditText editText) {
        viewGroup.removeAllViews();
        char[] arr = shuffleTheWord(word);
        for (int i = 0; i < arr.length; i++) {
            Button b = new Button(viewGroup.getContext());
            b.setId(i);
            pushedButtons.add(i);
            b.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT));
            b.setText(String.valueOf(arr[i]));
            b.setOnClickListener(v -> {
                Button b1 = (Button) v;
                editText.append(b1.getText().toString());
                pushedButtons.add(b1.getId());
                b1.setEnabled(false);
                Log.d(TAG, "onClick: " + b1.getId() + " " + v.getId());
            });
            viewGroup.addView(b);
        }

    }

    public void checkAnswer(EditText answerField, String answer) {
        if (TextUtils.isEmpty(answerField.getText().toString()))
            Toast.makeText(answerField.getContext(), "provide input", Toast.LENGTH_SHORT).show();
        else {
            String temp = answerField.getText().toString().trim();
            if (answer.length() == temp.length())
                Toast.makeText(answerField.getContext(), String.valueOf(answer.equals(temp)), Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(answerField.getContext(), "Your answer isn't ready", Toast.LENGTH_SHORT).show();
        }
    }

    public void undo(EditText editText, ViewGroup viewGroup) {
        if (!TextUtils.isEmpty(editText.getText())) {
            String temp = editText.getText().toString();
            viewGroup.getChildAt(pushedButtons.pop()).setEnabled(true);
            editText.setText(temp.substring(0, temp.length() - 1));
        } else Toast.makeText(editText.getContext(), "nothing to undo", Toast.LENGTH_SHORT).show();
    }

}

