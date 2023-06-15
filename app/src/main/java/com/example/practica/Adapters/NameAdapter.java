package com.example.practica.Adapters;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practica.DetallePokemonActivity;
import com.example.practica.Entitis.Coordenadas;
import com.example.practica.Entitis.Pokemon;
import com.example.practica.MapsActivity;
import com.example.practica.R;
import com.example.practica.Service.PokemonService;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NameAdapter  extends RecyclerView.Adapter{
    private List<Pokemon> items;
    public NameAdapter(List<Pokemon> items){this.items = items;}
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_string, parent, false);
        NameViewHolder viewHolder = new NameViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
       Pokemon item = items.get(position);
       View view = holder.itemView;

       TextView ediNombre = view.findViewById(R.id.editNombre);
       TextView ediTipo = view.findViewById(R.id.editTipo);
       ImageView imgFoto = view.findViewById(R.id.imgFoto);

        ediNombre.setText(item.nombre);
        ediTipo.setText(item.tipo);
        Picasso.get().load(item.getFoto()).into(imgFoto);

        Button btnLlevar = view.findViewById(R.id.btnLlevarInfo);
        btnLlevar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    Pokemon item = items.get(adapterPosition);
                    String name = item.nombre;
                    String tipo = item.tipo;
                    String foto = item.foto;
                    int id = item.id;
                    // Obtener el contexto a través de la vista
                    Context context = v.getContext();

                    // Crear un Intent para iniciar la nueva actividad
                    Intent intent = new Intent(context, DetallePokemonActivity.class);
                    intent.putExtra("id", id); // Agregar el ID a los extras
                    // Pasar los datos a través de los extras del Intent
                    intent.putExtra("name", name);
                    intent.putExtra("tipo", tipo);
                    intent.putExtra("foto", foto);
                    //intent.putExtra("avatar", imageUrl);

                    // Iniciar la actividad
                    context.startActivity(intent);
                }
            }
        });

        Button btnVerUbicacion = view.findViewById(R.id.btnVerUbicacion);
        btnVerUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clickedPosition = holder.getAdapterPosition();
                Pokemon clickedItem = items.get(clickedPosition);

                // Crea una instancia de Retrofit y la interfaz del servicio
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://647788dc9233e82dd53bd0e9.mockapi.io/") // Reemplaza con la URL de tu API
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                PokemonService Service = retrofit.create(PokemonService.class);

                // Realiza la solicitud para obtener los datos de latitud y longitud
                Call<Coordenadas> call = Service.getCoordenadas(clickedItem.getId());
                call.enqueue(new Callback<Coordenadas>() {
                    @Override
                    public void onResponse(Call<Coordenadas> call, Response<Coordenadas> response) {
                        if (response.isSuccessful()) {
                            Coordenadas cordenadas = response.body();
                            if (cordenadas != null) {
                                double latitude = cordenadas.getLatitud();
                                double longitude = cordenadas.getLongitud();
                                String name = item.nombre;
                                // Realiza la redirección a MapsActivity y pasa los datos de latitud y longitud
                                // Realiza la redirección a MapsActivity y pasa los datos de latitud y longitud
                                Intent intent = new Intent(holder.itemView.getContext(), MapsActivity.class);
                                intent.putExtra("name", name);
                                intent.putExtra("latitud", latitude);
                                intent.putExtra("longitud", longitude);
                                holder.itemView.getContext().startActivity(intent);
                            }
                        } else {
                            // Maneja el caso de respuesta no exitosa de la API
                        }
                    }

                    @Override
                    public void onFailure(Call<Coordenadas> call, Throwable t) {
                        // Maneja el caso de error de la solicitud a la API
                    }
                });
            }
        });
        
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public class NameViewHolder extends RecyclerView.ViewHolder{

        public NameViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
