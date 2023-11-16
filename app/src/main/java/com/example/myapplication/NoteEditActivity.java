package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class NoteEditActivity extends AppCompatActivity {

    EditText ThemeEditText;
    EditText NoteEditText;

    int Position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);

        Intent fromMainActivityIntent = getIntent();

        String NoteText = fromMainActivityIntent.getExtras().getString(MainActivity.KEY_NOTE_TEXT);
        String NoteTheme = fromMainActivityIntent.getExtras().getString(MainActivity.KEY_THEME);

        //создание фрагмента
        NoteEditFragment fragment = new NoteEditFragment();

        Bundle bundle = new Bundle();
        bundle.putString(MainActivity.KEY_NOTE_TEXT,NoteText);
        bundle.putString(MainActivity.KEY_THEME,NoteTheme);
        fragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView,fragment)
                .commit();

        Position = fromMainActivityIntent.getIntExtra(MainActivity.KEY_POSITION,-1);

    }

    public void BackData(String theme,String note)
    {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(MainActivity.KEY_THEME,theme);
        returnIntent.putExtra(MainActivity.KEY_NOTE_TEXT,note);
        returnIntent.putExtra(MainActivity.KEY_POSITION,Position);
        setResult(RESULT_OK,returnIntent);
        finish();
    }
}