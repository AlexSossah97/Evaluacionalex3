package com.example.evaluacion3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class CancionesAdapter extends FirebaseRecyclerAdapter<Cancion, CancionesAdapter.ViewHolder> {

    // Constructor que recibe las opciones y el contexto
    public CancionesAdapter(@NonNull FirebaseRecyclerOptions<Cancion> options, Context context) {
        super(options);
    }

    // Método para crear nuevos objetos ViewHolder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla la vista del elemento de la canción desde el layout XML
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cancion, parent, false);
        return new ViewHolder(view); // Retorna un nuevo ViewHolder asociado a la vista
    }

    // Método llamado por RecyclerView para mostrar datos en una posición específica
    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Cancion model) {
        // Establece el nombre de la canción en el TextView del ViewHolder
        holder.nombreTextView.setText(model.getNombre());
    }

    // Clase estática que representa un elemento de la interfaz de usuario
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombreTextView;

        // Constructor que asigna el TextView del layout al campo de la clase
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreTextView = itemView.findViewById(R.id.nombreCancionTextView);
        }
    }
}
