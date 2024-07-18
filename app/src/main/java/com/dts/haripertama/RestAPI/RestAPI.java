package com.dts.haripertama.RestAPI;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dts.haripertama.R;
import com.dts.haripertama.RestAPI.Repository.ApiClient;
import com.dts.haripertama.RestAPI.Repository.RestAPIRepository;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestAPI extends AppCompatActivity {
    List<PostModel> posts = new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_rest_api);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton btnBack = findViewById(R.id.backButton);
        btnBack.setOnClickListener(view -> finish());

        recyclerView = findViewById(R.id.rvPost);

        RestAPIRepository service = ApiClient.getRetrofitInstance().create(RestAPIRepository.class);
        Call<List<PostModel>> call = service.getPosts();
        call.enqueue(new Callback<List<PostModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<PostModel>> call, @NonNull Response<List<PostModel>> response) {
                posts = response.body();
                RestAPIRVAdapter adapter = new RestAPIRVAdapter(posts);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(RestAPI.this));
            }

            @Override
            public void onFailure(@NonNull Call<List<PostModel>> call, @NonNull Throwable throwable) {
                Toast.makeText(RestAPI.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });


    }
}