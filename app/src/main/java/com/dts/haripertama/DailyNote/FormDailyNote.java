package com.dts.haripertama.DailyNote;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class FormDailyNote extends AppCompatActivity {
    static final String FORM_STATE = "form_state";
    static final String NOTE_ID = "note_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form_daily_note);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //        Forms
        TextView tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText("Tambah Catatan");

        EditText etJudul = findViewById(R.id.etJudul);
        EditText etDeskripsi = findViewById(R.id.etDeskripsi);
        Button btnSave = findViewById(R.id.btn_tambah_note);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            if (extras.getString("form_state", "update").equals("update")) {
                int noteID = extras.getInt(NOTE_ID);
                NoteModel note = DailyNote.getNoteById(this, noteID);

                if (note == null) {
                    Toast.makeText(this, "Note not found", Toast.LENGTH_SHORT).show();
                    finish();
                }

                etJudul.setText(note.getTitle());
                etDeskripsi.setText(note.getDescription());
                btnSave.setText("Ubah Catatan");
                tvTitle.setText("Ubah Catatan");
            }
        }


        ImageButton btnBack = findViewById(R.id.backButton);
        btnBack.setOnClickListener(view -> finish());

        btnSave.setOnClickListener(view -> {
            String judul = etJudul.getText().toString();
            String deskripsi = etDeskripsi.getText().toString();
            Date date = new Date();
            String formattedDate = date.toString();
            if (judul.isEmpty()) etJudul.setError("Judul tidak boleh kosong");
            if (deskripsi.isEmpty()) etDeskripsi.setError("Deskripsi tidak boleh kosong");

            NoteModel noteModel = new NoteModel(0, judul, deskripsi, formattedDate, "bintangrezeka");
            if (extras != null) {
                if (extras.getString("form_state", "update").equals("update")) {
                    int noteID = extras.getInt(NOTE_ID);
                    saveNote(noteModel, true, noteID);
                }
            } else {
                saveNote(noteModel, false, 0);
            }

            finish();
        });
    }

    private void saveNote(@NonNull NoteModel note, boolean isUpdate, int noteID) {
        final ArrayList<NoteModel> noteModels = DailyNote.getFiles(this);

        try {
            FileOutputStream fileOutputStream = this.openFileOutput("notes", Context.MODE_PRIVATE);
            JSONArray jsonArray = new JSONArray();
            if (isUpdate) {
                for (NoteModel noteModel : noteModels) {
                    if (noteModel.getId() == noteID) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("id", note.getId());
                        jsonObject.put("title", note.getTitle());
                        jsonObject.put("description", note.getDescription());
                        jsonObject.put("date", note.getDate());
                        jsonObject.put("author", "bintangrezeka");
                        jsonArray.put(jsonObject);
                    } else {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("id", noteModel.getId());
                        jsonObject.put("title", noteModel.getTitle());
                        jsonObject.put("description", noteModel.getDescription());
                        jsonObject.put("date", noteModel.getDate());
                        jsonObject.put("author", "bintangrezeka");
                        jsonArray.put(jsonObject);
                    }
                }
            } else {
                for (NoteModel noteModel : noteModels) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", noteModel.getId());
                    jsonObject.put("title", noteModel.getTitle());
                    jsonObject.put("description", noteModel.getDescription());
                    jsonObject.put("date", noteModel.getDate());
                    jsonObject.put("author", "bintangrezeka");
                    jsonArray.put(jsonObject);
                }

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", note.getId());
                jsonObject.put("title", note.getTitle());
                jsonObject.put("description", note.getDescription());
                jsonObject.put("date", note.getDate());
                jsonObject.put("author", "bintangrezeka");
                jsonArray.put(jsonObject);
            }

            fileOutputStream.write(jsonArray.toString().getBytes());
            fileOutputStream.close();
        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }


    }
}