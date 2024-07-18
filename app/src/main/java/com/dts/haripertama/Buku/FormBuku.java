package com.dts.haripertama.Buku;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.dts.haripertama.DailyNote.DailyNote;
import com.dts.haripertama.DailyNote.NoteModel;
import com.dts.haripertama.R;

public class FormBuku extends AppCompatActivity {
    static final String FORM_STATE = "form_state";
    static final String BOOK_ID = "book_id";

    private BukuRepository bukuRepository = new BukuRepository(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form_buku);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton btnBack = findViewById(R.id.backButton);
        btnBack.setOnClickListener(view -> finish());

        TextView tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText("Tambah Buku");

        //      FORM
        EditText etJudul = findViewById(R.id.etJudul);
        EditText etDeskripsi = findViewById(R.id.etDeskripsi);

        Button btnSave = findViewById(R.id.btn_simpan);
        btnSave.setOnClickListener(view -> {
            String judul = etJudul.getText().toString();
            String deskripsi = etDeskripsi.getText().toString();

            if (judul.isEmpty()) {
                etJudul.setError("Judul tidak boleh kosong");
                etJudul.requestFocus();
                return;
            }

            if (deskripsi.isEmpty()) {
                etDeskripsi.setError("Deskripsi tidak boleh kosong");
                etDeskripsi.requestFocus();
                return;
            }

            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                if (extras.getString("form_state", "update").equals("update")) {
                    int noteID = extras.getInt(BOOK_ID);
                    updateBuku(judul, deskripsi, noteID);

                }
            } else {
                saveBuku(judul, deskripsi);
            }

            finish();
        });

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            if (extras.getString("form_state", "update").equals("update")) {
                int noteID = extras.getInt(BOOK_ID);
                BukuModel buku = bukuRepository.getBukuById(noteID);

                if (buku == null) {
                    Toast.makeText(this, "Note not found", Toast.LENGTH_SHORT).show();
                    finish();
                }

                etJudul.setText(buku.getJudul());
                etDeskripsi.setText(buku.getDeskripsi());
                btnSave.setText("Ubah Catatan");
                tvTitle.setText("Ubah Catatan");
            }
        }


    }

    private void saveBuku(String judul, String deskripsi) {
        BukuModel bukuModel = new BukuModel();
        bukuModel.setJudul(judul);
        bukuModel.setDeskripsi(deskripsi);
        bukuRepository.addBuku(bukuModel);

        Toast.makeText(this, "Buku berhasil ditambahkan", Toast.LENGTH_SHORT).show();
    }

    private void updateBuku(String judul, String deskripsi, int bukuID) {
        BukuModel bukuModel = new BukuModel();
        bukuModel.setJudul(judul);
        bukuModel.setDeskripsi(deskripsi);
        bukuModel.setId(bukuID);
        bukuRepository.updateBuku(bukuModel);

        Toast.makeText(this, "Buku berhasil diubah", Toast.LENGTH_SHORT).show();
    }
}