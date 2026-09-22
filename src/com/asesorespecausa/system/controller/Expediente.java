package com.asesorespecausa.system.controller;

import java.time.LocalDate;

public class Expediente {

    private String numero;
    private String nombre;
    private LocalDate fecha;
    private String estado;
    private String descripcion;

    public Expediente(String numero, String nombre, LocalDate fecha,
                      String estado, String descripcion) {

        this.numero = numero;
        this.nombre = nombre;
        this.fecha = fecha;
        this.estado = estado;
        this.descripcion = descripcion;
    }

    public String getNumero() {
        return numero;
    }

    public String getNombre() {
        return nombre;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public String getEstado() {
        return estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
}

