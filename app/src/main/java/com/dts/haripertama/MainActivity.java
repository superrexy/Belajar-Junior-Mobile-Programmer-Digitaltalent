package com.dts.haripertama;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.dts.haripertama.Buku.Buku;
import com.dts.haripertama.Calculator.Calculator;
import com.dts.haripertama.DailyNote.DailyNote;
import com.dts.haripertama.RestAPI.RestAPI;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Button btnKalkulator = findViewById(R.id.btn_kalkulator);
        Button btnDailyNote = findViewById(R.id.btn_daily_note);
        Button btnBuku = findViewById(R.id.btn_buku);
        Button btnRestAPI = findViewById(R.id.btn_rest_api);

        TextView username = findViewById(R.id.tvUsername);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnKalkulator.setOnClickListener(v -> {
            startActivity(new Intent(this, Calculator.class));
        });

        btnDailyNote.setOnClickListener(v -> {
            startActivity(new Intent(this, DailyNote.class));
        });

        btnBuku.setOnClickListener(v -> {
            startActivity(new Intent(this, Buku.class));
        });

        btnRestAPI.setOnClickListener(v -> {
            startActivity(new Intent(this, RestAPI.class));
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("username");
            username.setText(value);
        }
    }


}