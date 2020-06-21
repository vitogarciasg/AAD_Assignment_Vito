package com.example.aad_vitogarcia;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;

public class NotesMain extends AppCompatActivity {

    static ArrayList<String> notes = new ArrayList<String>();

    static ArrayAdapter<String> arrayAdapter;

    public void callSecondActivity(int position) {

        Intent intent = new Intent(getApplicationContext(), NotesActivty.class);

        intent.putExtra("noteID", position);

        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.createNote) {

            callSecondActivity(-1);

            return true;

        }

        return false;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_activity);

        final ListView notesList = (ListView) findViewById(R.id.notesList);

        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("com.example.aad_vitogarcia", Context.MODE_PRIVATE);

        HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("notes", null);

        if (set == null) {

            notes.add("Example Note");

        } else {

            notes = new ArrayList<>(set);

        }
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, notes);

        notesList.setAdapter(arrayAdapter);

        notesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                callSecondActivity(position);
            }
        });

        notesList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                new AlertDialog.Builder(NotesMain.this)
                        .setTitle("Delete Note")
                        .setMessage("Are you sure?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                notes.remove(position);
                                arrayAdapter.notifyDataSetChanged();

                                SharedPreferences sharedPreferences = getApplication().getSharedPreferences("com.example.aad_vitogarcia", Context.MODE_PRIVATE);

                                HashSet<String> set = new HashSet<>(NotesMain.notes);

                                sharedPreferences.edit().putStringSet("notes", set).apply();

                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

                return true;
            }
        });
    }
}
