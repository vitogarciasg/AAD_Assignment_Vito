package com.example.aad_vitogarcia;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashSet;

public class NotesActivty extends AppCompatActivity {

    int noteID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_activty);

        Intent intent = getIntent();

        EditText noteText = (EditText) findViewById(R.id.noteText);

        noteID = intent.getIntExtra("noteID", -1);

        if (noteID != -1){

            noteText.setText(NotesMain.notes.get(noteID));

        } else {

            NotesMain.notes.add("");

            noteID = NotesMain.notes.size() - 1;

            NotesMain.arrayAdapter.notifyDataSetChanged();
        }

        noteText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                NotesMain.notes.set(noteID, String.valueOf(s));

                NotesMain.arrayAdapter.notifyDataSetChanged();

                SharedPreferences sharedPreferences = getApplication().getSharedPreferences("com.example.aad_vitogarcia", Context.MODE_PRIVATE);

                HashSet<String> set = new HashSet<>(NotesMain.notes);

                sharedPreferences.edit().putStringSet("notes", set).apply();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }
}
