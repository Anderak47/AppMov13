package com.example.practica.Entitis;

public class Paisaje {
    public int id;
    public String nombre ;
    public String tipo;
    public String foto;
    public Double latitud;
    public Double longitud;

    public Paisaje(String nombre, String tipo, String foto, Double latitud, Double longitud) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.foto = foto;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public Paisaje(String nombre, String tipo) {
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public Paisaje() {

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

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }
}
