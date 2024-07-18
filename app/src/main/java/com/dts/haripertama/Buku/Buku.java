package com.dts.haripertama.Buku;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.dts.haripertama.DailyNote.DailyNotesLVAdapter;
import com.dts.haripertama.DailyNote.DetailDailyNote;
import com.dts.haripertama.DailyNote.NoteModel;
import com.dts.haripertama.R;

import java.util.ArrayList;

public class Buku extends AppCompatActivity {

    private ArrayList<BukuModel> listBuku = new ArrayList<>();
    private BukuRepository bukuRepository = new BukuRepository(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_buku);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton btnBack = findViewById(R.id.backButton);
        btnBack.setOnClickListener(view -> finish());

        ImageButton btnTambahBuku = findViewById(R.id.addButton);
        btnTambahBuku.setOnClickListener(view -> startActivity(new Intent(this, FormBuku.class)));

        listBuku = bukuRepository.getAllBuku();

        ListView listView = findViewById(R.id.lvBuku);
        listView.setAdapter(new ListBukuLVAdapter(this, listBuku));
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(this, DetailBuku.class);
            intent.putExtra(DetailBuku.BUKU_ID, listBuku.get(i).getId());
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        listBuku = bukuRepository.getAllBuku();

        ListView listView = findViewById(R.id.lvBuku);
        listView.setAdapter(new ListBukuLVAdapter(this, listBuku));
    }
}