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
        ThemeEditText = findViewById(R.id.themeEditText);
        NoteEditText = findViewById(R.id.noteEditText);

        Intent fromMainActivityIntent = getIntent();

        ThemeEditText.setText(fromMainActivityIntent.getExtras().getString(MainActivity.KEY_THEME));
        NoteEditText.setText(fromMainActivityIntent.getExtras().getString(MainActivity.KEY_NOTE_TEXT));
        Position = fromMainActivityIntent.getIntExtra(MainActivity.KEY_POSITION,-1);

        if(Position == -1)
        {
            Log.d("Note activity","Invalid position");
        }


    }

    public void OnBackButtonClick(View view)
    {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(MainActivity.KEY_THEME,ThemeEditText.getText().toString());
        returnIntent.putExtra(MainActivity.KEY_NOTE_TEXT,NoteEditText.getText().toString());
        returnIntent.putExtra(MainActivity.KEY_POSITION,Position);
        setResult(RESULT_OK,returnIntent);
        finish();

    }
}