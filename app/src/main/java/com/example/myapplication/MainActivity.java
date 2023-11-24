package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.Manifest;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    final public static String KEY_THEME = "theme";
    final public static String KEY_NOTE_TEXT = "note";
    final public static String KEY_POSITION = "position";

    private static final int MY_PERMISSIONS_REQUEST_INTERNET = 123; // или любое другое уникальное число
    private static final String SERVICE_ADDRESS = "http://37.77.105.18/api/Note";

    ListView ThemesListView;

    ArrayList<Note> notes;
    ArrayAdapter<String> noteAdapter;
    ServerAccessor serverAccessor = new ServerAccessor(SERVICE_ADDRESS);

    private ExecutorService executorService = Executors.newSingleThreadExecutor();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ThemesListView = findViewById(R.id.ListView);

        noteAdapter = AdapterUpdate(new ArrayList<Note>());
        Intent NoteIntent = new Intent(this, NoteEditActivity.class);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            // Разрешение не предоставлено, запросить его у пользователя
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, MY_PERMISSIONS_REQUEST_INTERNET);
        }

        //Запуск фоновой задачи
        ProgressTask progressTask = new ProgressTask();
        executorService.submit(progressTask);
    }

    /**
     * Обновляет listView путем установки нового адаптера
     * @return Адаптер для обновления listView
     */
    private ArrayAdapter<String> AdapterUpdate(ArrayList<Note> list) {

        ArrayList<String> stringList = serverAccessor.getStringListFromNoteList(list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1,
                    stringList);
        // установить адаптер в listview
        ThemesListView.setAdapter(adapter);
        return adapter;
    }

    /**
     * класс фоновой задачи
     */
    class ProgressTask implements Runnable {
        String connectionError = null;

        @Override
        public void run() {
            try {
                // выполнение в фоне
                notes = serverAccessor.getData();

                // Обновление UI осуществляется в основном потоке
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (connectionError == null) {
                            noteAdapter = AdapterUpdate(notes);
                        } else {
                            //проблемы с интернетом
                        }
                    }
                });

            } catch (Exception ex) {
                connectionError = ex.getMessage();
            }
        }
    }
}