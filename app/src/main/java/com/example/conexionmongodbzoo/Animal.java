package com.example.conexionmongodbzoo;

import java.util.ArrayList;

public class Animal {
    private String nombre,particularidad,origen;
    private ArrayList<Animal> animales = new ArrayList<>();


    public Animal(String particularidad,String nombre, String origen) {
        this.nombre = nombre;
        this.particularidad = particularidad;
        this.origen = origen;
    }

    public ArrayList getAnimales() {
        return animales;
    }

    public void setAnimales(ArrayList animales) {
        this.animales = animales;
    }

    @Override
    public String toString() {
        return "Nombre = " + nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public String getParticularidad() {
        return particularidad;
    }

    public void setParticularidad(String particularidad) {
        this.particularidad = particularidad;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }
}
