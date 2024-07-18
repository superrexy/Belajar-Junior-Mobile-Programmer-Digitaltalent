package com.dts.haripertama.DailyNote;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.dts.haripertama.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.util.ArrayList;

public class DetailDailyNote extends AppCompatActivity {

    static final String NOTE_ID = "note_id";

    NoteModel note;

    void deleteNote(int noteID) {
        NoteModel note = DailyNote.getNoteById(this, noteID);
        if (note == null) Toast.makeText(this, "Note not found", Toast.LENGTH_SHORT).show();

        final ArrayList<NoteModel> noteModels = DailyNote.getFiles(this);
        try {
            FileOutputStream fileOutputStream = this.openFileOutput("notes", Context.MODE_PRIVATE);
            JSONArray jsonArray = new JSONArray();
            for (NoteModel noteModel : noteModels) {
                if (noteModel.getId() == noteID) continue;

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", noteModel.getId());
                jsonObject.put("title", noteModel.getTitle());
                jsonObject.put("description", noteModel.getDescription());
                jsonObject.put("date", noteModel.getDate());
                jsonObject.put("author", "bintangrezeka");
                jsonArray.put(jsonObject);
            }

            fileOutputStream.write(jsonArray.toString().getBytes());
            fileOutputStream.close();
        } catch (Exception e) {
            Log.e("DetailDailyNote", "deleteNote: ", e);
            Toast.makeText(this, "Failed to delete note", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_daily_note);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton btnBack = findViewById(R.id.backButton);
        btnBack.setOnClickListener(view -> finish());


        Bundle extras = getIntent().getExtras();
        assert extras != null;
        int noteID = extras.getInt(NOTE_ID);
        try {
            note = DailyNote.getNoteById(this, noteID);

            TextView tvTitle = findViewById(R.id.txtDailyNote);
            tvTitle.setText(note.getTitle());

            TextView tvDescription = findViewById(R.id.txtDescription);
            tvDescription.setText(note.getDescription());
        } catch (Exception e) {
            Toast.makeText(this, "Note not found", Toast.LENGTH_SHORT).show();
            finish();
        }

        Button btnDeleteNote = findViewById(R.id.btnDeleteNote);
        btnDeleteNote.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Delete Note");
            builder.setMessage("Are you sure you want to delete this note?");
            builder.setPositiveButton("Yes", (dialogInterface, i) -> {
                deleteNote(noteID);
                finish();
            });

            builder.setNegativeButton("No", (dialogInterface, i) -> {
                dialogInterface.dismiss();
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        });

        Button btnEditNote = findViewById(R.id.btnUpdateNote);
        btnEditNote.setOnClickListener(view -> {
            Intent intent = new Intent(this, FormDailyNote.class);
            intent.putExtra(FormDailyNote.FORM_STATE, "update");
            intent.putExtra(FormDailyNote.NOTE_ID, noteID);

            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        Bundle extras = getIntent().getExtras();
        assert extras != null;
        int noteID = extras.getInt(NOTE_ID);
        try {
            note = DailyNote.getNoteById(this, noteID);

            TextView tvTitle = findViewById(R.id.txtDailyNote);
            tvTitle.setText(note.getTitle());

            TextView tvDescription = findViewById(R.id.txtDescription);
            tvDescription.setText(note.getDescription());
        } catch (Exception e) {
            Toast.makeText(this, "Note not found", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}