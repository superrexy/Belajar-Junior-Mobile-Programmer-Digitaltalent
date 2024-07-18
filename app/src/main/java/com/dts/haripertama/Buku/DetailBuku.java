package com.dts.haripertama.Buku;

import android.content.Intent;
import android.os.Bundle;
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

import com.dts.haripertama.DailyNote.FormDailyNote;
import com.dts.haripertama.R;

public class DetailBuku extends AppCompatActivity {
    static final String BUKU_ID = "buku_id";

    BukuRepository bukuRepository = new BukuRepository(this);
    BukuModel buku;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_buku);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton btnBack = findViewById(R.id.backButton);
        btnBack.setOnClickListener(view -> finish());

        int bukuID = getIntent().getIntExtra(BUKU_ID, 0);
        buku = bukuRepository.getBukuById(bukuID);
        if (buku == null) {
            Toast.makeText(this, "Buku not found", Toast.LENGTH_SHORT).show();
            finish();
        }

        TextView tvTitle = findViewById(R.id.txtDailyNote);
        tvTitle.setText(buku.getJudul());

        TextView tvDescription = findViewById(R.id.txtDescription);
        tvDescription.setText(buku.getDeskripsi());

        Button btnDeleteBook = findViewById(R.id.btnDeleteBook);
        btnDeleteBook.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Delete Book");
            builder.setMessage("Are you sure you want to delete this book?");
            builder.setPositiveButton("Yes", (dialogInterface, i) -> {
                deleteBuku(bukuID);
                finish();
            });

            builder.setNegativeButton("No", (dialogInterface, i) -> {
                dialogInterface.dismiss();
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        });

        Button btnEdit = findViewById(R.id.btnUpdateBook);
        btnEdit.setOnClickListener(view -> {
            Intent intent = new Intent(this, FormBuku.class);
            intent.putExtra(FormBuku.FORM_STATE, "update");
            intent.putExtra(FormBuku.BOOK_ID, bukuID);

            startActivity(intent);
        });


    }

    private void deleteBuku(int bukuID) {
        BukuModel buku = bukuRepository.getBukuById(bukuID);
        if (buku == null) {
            Toast.makeText(this, "Buku not found", Toast.LENGTH_SHORT).show();
        }

        bukuRepository.deleteBuku(bukuID);
        finish();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        int bukuID = getIntent().getIntExtra(BUKU_ID, 0);
        buku = bukuRepository.getBukuById(bukuID);
        if (buku == null) {
            Toast.makeText(this, "Buku not found", Toast.LENGTH_SHORT).show();
            finish();
        }

        TextView tvTitle = findViewById(R.id.txtDailyNote);
        tvTitle.setText(buku.getJudul());

        TextView tvDescription = findViewById(R.id.txtDescription);
        tvDescription.setText(buku.getDeskripsi());
    }


}