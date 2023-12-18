package com.example.evaluacion3;
public class Cancion {
    private String id;
    private String nombre;

    public Cancion() {
        // Constructor vacío requerido por Firebase
    }

    public Cancion(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
}
