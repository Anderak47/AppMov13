package com.example.practica;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.practica.Adapters.NameAdapter;
import com.example.practica.Entitis.Pokemon;
import com.example.practica.Service.PokemonService;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListaActivity extends AppCompatActivity {
    RecyclerView rvLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        rvLista = findViewById(R.id.rvListaSimple);
        rvLista.setLayoutManager(new LinearLayoutManager(this));
        actualizarLista();
    }
    @Override
    protected void onResume(){
        super.onResume();
        actualizarLista();
    }
    private void actualizarLista(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://647788dc9233e82dd53bd0e9.mockapi.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PokemonService service = retrofit.create(PokemonService.class);
        Call<List<Pokemon>> call = service.getAllUser();
        call.enqueue(new Callback<List<Pokemon>>() {
            @Override
            public void onResponse(Call<List<Pokemon>> call, Response<List<Pokemon>> response) {
                Log.i("MAIN_APP", String.valueOf(response.code()));
                if (response.isSuccessful()) {
                    List<Pokemon> data = response.body();
                    Log.i("MAIN_APP", new Gson().toJson(data));
                    NameAdapter adapter = new NameAdapter(data);
                    rvLista.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Pokemon>> call, Throwable t) {
                // Manejar el error en caso de fallo en la solicitud
            }
        });
    }
}