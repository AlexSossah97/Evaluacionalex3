package com.example.evaluacion3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    // Declaración de variables miembro
    private EditText nombreCancionEditText;
    private Button agregarCancionButton;
    private Spinner spinnerCanciones;
    private RecyclerView recyclerView;
    private Button btnCerrarSesion;
    private DatabaseReference referenciaCanciones;
    private FirebaseRecyclerOptions<Cancion> options;
    private CancionesAdapter cancionesAdapter;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicialización de Firebase
        mAuth = FirebaseAuth.getInstance();

        nombreCancionEditText = findViewById(R.id.nombreCancionEditText);
        agregarCancionButton = findViewById(R.id.agregarCancionButton);
        spinnerCanciones = findViewById(R.id.spinnerCanciones);
        recyclerView = findViewById(R.id.recyclerView);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);

        // Obtención de referencia a la base de datos de Firebase
        referenciaCanciones = FirebaseDatabase.getInstance().getReference().child("canciones");

        // Configuración del RecyclerView y el adaptador usando FirebaseRecyclerOptions
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        options = new FirebaseRecyclerOptions.Builder<Cancion>()
                .setQuery(referenciaCanciones, Cancion.class)
                .build();
        cancionesAdapter = new CancionesAdapter(options, this);
        recyclerView.setAdapter(cancionesAdapter);

        // Configuración del Spinner
        ArrayAdapter<Cancion> adapterSpinner = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCanciones.setAdapter(adapterSpinner);

        // Configuración del evento de clic para el botón de agregar canción
        agregarCancionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agregarCancion();
            }
        });

        // Configuración del evento de clic para el botón de cerrar sesión
        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cerrarSesion();
            }
        });

        cancionesAdapter.startListening();
    }

    // Método para agregar una nueva canción a la base de datos de Firebase
    private void agregarCancion() {
        String nombreCancion = nombreCancionEditText.getText().toString().trim();

        if (!nombreCancion.isEmpty()) {
            String clave = referenciaCanciones.push().getKey();
            Cancion nuevaCancion = new Cancion(clave, nombreCancion);
            referenciaCanciones.child(clave).setValue(nuevaCancion);
            nombreCancionEditText.setText("");


        }
    }

    // Método para cerrar la sesión actual y redirigir a la pantalla de inicio de sesión
    private void cerrarSesion() {
        mAuth.signOut();

        // Creación de un intent para redirigir a LoginActivity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish(); // Asegurar que la actividad actual se cierre
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Detener la escucha del adaptador cuando la actividad se detiene
        cancionesAdapter.stopListening();
    }
}
