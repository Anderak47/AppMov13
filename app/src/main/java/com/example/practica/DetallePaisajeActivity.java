package com.example.practica;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.practica.Entitis.Paisaje;
import com.example.practica.Service.PaisajeService;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetallePaisajeActivity extends AppCompatActivity {
    private int id;
    Paisaje paisaje = new Paisaje();
    RecyclerView rvLista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_pokemon);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);
        String nombre = intent.getStringExtra("name");
        String tipo = intent.getStringExtra("tipo");
        String foto = intent.getStringExtra("foto");

        EditText tvName = findViewById(R.id.ediDetallePoke);
        EditText tvTipo = findViewById(R.id.ediDestalleTipo);
        ImageView tvFoto = findViewById(R.id.detalleImgFoto);

        tvName.setText(nombre);
        tvTipo.setText(tipo);
        Picasso.get().load(foto).into(tvFoto);

        Button btnEliminar = findViewById(R.id.btnEliminar);
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://647788dc9233e82dd53bd0e9.mockapi.io/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                PaisajeService userService = retrofit.create(PaisajeService.class);

                Call<Void> call = userService.delete(id);

                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            // Eliminación exitosa
                            // Realiza cualquier acción adicional o actualización de la interfaz aquí

                            // Actualiza la lista de usuarios llamando a getAllUser() nuevamente
                            Call<List<Paisaje>> userListCall = userService.getAllUser();
                            userListCall.enqueue(new Callback<List<Paisaje>>() {
                                @Override
                                public void onResponse(Call<List<Paisaje>> call, Response<List<Paisaje>> response) {
                                    if (response.isSuccessful()) {
                                        List<Paisaje> userList = response.body();
                                        // Actualiza tu RecyclerView o cualquier otra vista con la lista actualizada

                                    } else {
                                        // Error al obtener la lista actualizada
                                        // Maneja el error de acuerdo a tus necesidades
                                    }
                                }

                                @Override
                                public void onFailure(Call<List<Paisaje>> call, Throwable t) {
                                    // Error en la solicitud
                                    // Maneja el error de acuerdo a tus necesidades
                                }
                            });

                            // Finaliza la actividad de ContactoActivity
                            finish();
                        } else {
                            // Ocurrió un error en la eliminación
                            // Maneja el error de acuerdo a tus necesidades
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        // Error en la solicitud
                        // Maneja el error de acuerdo a tus necesidades
                    }
                });
            }
        });
        Button btnGuardar = findViewById(R.id.btnGuardarPublicacion);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String descripcion = tvName.getText().toString();
                String comentario = tvTipo.getText().toString();


                Paisaje updatedUser = new Paisaje(descripcion, comentario);
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://647788dc9233e82dd53bd0e9.mockapi.io/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                PaisajeService userService = retrofit.create(PaisajeService.class);
                Call<Paisaje> call = userService.update(id, updatedUser);
                call.enqueue(new Callback<Paisaje>() {
                    @Override
                    public void onResponse(Call<Paisaje> call, Response<Paisaje> response) {
                        if (response.isSuccessful()) {
                            // Actualización exitosa
                            Paisaje updatedUser = response.body();
                            Call<List<Paisaje>> userListCall = userService.getAllUser();
                            userListCall.enqueue(new Callback<List<Paisaje>>() {
                                @Override
                                public void onResponse(Call<List<Paisaje>> call, Response<List<Paisaje>> response) {
                                    if (response.isSuccessful()) {
                                        List<Paisaje> userList = response.body();
                                        // Actualiza tu RecyclerView o cualquier otra vista con la lista actualizada

                                    } else {
                                        // Error al obtener la lista actualizada
                                        // Maneja el error de acuerdo a tus necesidades
                                    }
                                }

                                @Override
                                public void onFailure(Call<List<Paisaje>> call, Throwable t) {
                                    // Error en la solicitud
                                    // Maneja el error de acuerdo a tus necesidades
                                }
                            });


                            //Toast.makeText(ContactoActivity.this, "Contacto actualizado", Toast.LENGTH_SHORT).show();
                        } else {
                            // Error en la actualización
                            // Maneja el error de acuerdo a tus necesidades
                            //Toast.makeText(ContactoActivity.this, "Error al actualizar el contacto", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Paisaje> call, Throwable t) {
                        // Error en la solicitud
                        // Maneja el error de acuerdo a tus necesidades
                        //Toast.makeText(ContactoActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                    }

                });
                finish();
            }
        });
    }

}