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
import com.example.practica.Entitis.Pokemon;
import com.example.practica.R;
import com.squareup.picasso.Picasso;

import java.util.List;

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
