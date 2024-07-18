package com.dts.haripertama.DailyNote;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.dts.haripertama.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DailyNote extends AppCompatActivity {

    ArrayList<NoteModel> notes = new ArrayList<>();

    static @NonNull JSONArray getJson(File file) throws IOException, JSONException {
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        StringBuilder stringBuilder = new StringBuilder();
        String line = bufferedReader.readLine();

        while (line != null) {
            stringBuilder.append(line).append("\n");
            line = bufferedReader.readLine();
        }

        bufferedReader.close();

        String response = stringBuilder.toString();

        JSONArray json = new JSONArray(response);
        return json;
    }

    static ArrayList<NoteModel> getFiles(Context context) {
        // Get files from storage
        File file = new File(context.getFilesDir(), "notes");
//        Create file if not exist
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    FileOutputStream fileOutputStream = context.openFileOutput("notes", Context.MODE_PRIVATE);
                    fileOutputStream.write("{}".getBytes());
                    fileOutputStream.close();

                    return new ArrayList<>();
                } else {
                    Log.d("DailyNote::STORAGE", "File not created");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                JSONArray jsonObject = getJson(file);
                ArrayList<NoteModel> noteModels = new ArrayList<>();

                for (int i = 0; i < jsonObject.length(); i++) {
                    JSONObject note = jsonObject.getJSONObject(i);
                    noteModels.add(new NoteModel(
                            note.getInt("id"),
                            note.getString("title"),
                            note.getString("description"),
                            note.getString("date"),
                            note.getString("author")
                    ));
                }

                return noteModels;

            } catch (IOException | JSONException e) {
                throw new RuntimeException(e);
            }
        }

        return new ArrayList<>();
    }

    static NoteModel getNoteById(Context context, int id) {
        ArrayList<NoteModel> notes = getFiles(context);
        for (NoteModel note : notes) {
            if (note.getId() == id) {
                return note;
            }
        }

        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        notes = getFiles(this);

        ListView listView = findViewById(R.id.lvNotes);
        listView.setAdapter(new DailyNotesLVAdapter(this, notes));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_daily_note);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

//        Button
        ImageButton btnBack = findViewById(R.id.backButton);
        ImageButton btnAddNote = findViewById(R.id.addButton);

        notes = getFiles(this);

        ListView listView = findViewById(R.id.lvNotes);
        listView.setAdapter(new DailyNotesLVAdapter(this, notes));
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(this, DetailDailyNote.class);
            intent.putExtra(DetailDailyNote.NOTE_ID, notes.get(i).getId());
            startActivity(intent);
        });

        btnBack.setOnClickListener(view -> finish());
        btnAddNote.setOnClickListener(view -> startActivity(new Intent(this, FormDailyNote.class)));

    }
}