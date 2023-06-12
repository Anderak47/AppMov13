package com.example.practica.Entitis;

public class Pokemon {
    public int id;
    public String nombre ;
    public String tipo;
    public String foto;

    public Pokemon(String nombre, String tipo, String foto) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.foto = foto;
    }

    public Pokemon(String nombre, String tipo) {
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public Pokemon() {

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
