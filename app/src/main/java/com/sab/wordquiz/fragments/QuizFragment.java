package com.sab.wordquiz.fragments;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.sab.wordquiz.R;
import com.sab.wordquiz.databinding.FragmentQuizBinding;
import com.sab.wordquiz.models.AuthorizationMechanism;
import com.sab.wordquiz.models.QuizMechanism;
import com.sab.wordquiz.models.Word;
import com.sab.wordquiz.persistence.WordsDatabase;
import com.sab.wordquiz.repository.WordsRepository;
import com.sab.wordquiz.requests.WordServiceGenerator;

import java.io.IOException;
import java.util.List;


import retrofit2.Call;
import retrofit2.Response;

public class QuizFragment extends Fragment {
    private static final String TAG = "Quizz";

    FragmentQuizBinding binding;
    QuizMechanism mQuizMechanism;
    AuthorizationMechanism mAuthorizationMechanism;
    String responseString;
    int counter = 0;

    public QuizFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mQuizMechanism = new QuizMechanism();
        mAuthorizationMechanism = new AuthorizationMechanism();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentQuizBinding.inflate(inflater, container, false);
        new AsyncRequest().execute();
        binding.shuffleButton.setOnClickListener(v -> {
            binding.answerField.setText("");
            new AsyncRequest().execute();
        });

        binding.submitButton.setOnClickListener(v -> mQuizMechanism.checkAnswer(binding.answerField, responseString));
        binding.retryButton.setOnClickListener(v -> {
            binding.answerField.setText("");
            for (int i = 0; i < binding.mGrid.getChildCount(); i++) {
                binding.mGrid.getChildAt(i).setEnabled(true);
            }
        });
        binding.undoBtn.setOnClickListener(v -> mQuizMechanism.undo(binding.answerField, binding.mGrid));
        binding.hintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Correct answer: " + responseString, Toast.LENGTH_SHORT).show();
            }
        });
        return binding.getRoot();

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.quiz_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        mAuthorizationMechanism.showDialog(binding.getRoot());
        return true;
    }

    class AsyncRequest extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            Call<JsonArray> arrayCall = WordServiceGenerator.getRandomWordApi().oneRandomWord(1);
            try {
                Response<JsonArray> response = arrayCall.execute();
                responseString = response.body().get(0).getAsString();
                return responseString;

            } catch (NullPointerException | IOException e) {
                e.printStackTrace();
            }
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d(TAG, "onPostExecute:" + s);
            String[] noConnectionStrings = new String[]{"no", "internet", "message"};
            if (TextUtils.isEmpty(s)) {
                s = noConnectionStrings[counter];
                responseString = s;
                counter++;
                if (counter == noConnectionStrings.length) {
                    counter = 0;
                }
            }
            mQuizMechanism.setTheGrid(binding.mGrid, s, binding.answerField);
        }
    }
}
