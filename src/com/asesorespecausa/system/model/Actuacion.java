/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */



package com.asesorespecausa.system.model;

import java.time.LocalDate;

public class Actuacion {

    private String idActuacion;
    private String idExpediente;
    private String idUsuario;
    private String titulo;
    private String descripcion;
    private LocalDate fechaActuacion;
    private String archivoAdjunto;

    public Actuacion() {
    }

    public Actuacion(String idActuacion, String idExpediente, String idUsuario,
            String titulo, String descripcion, LocalDate fechaActuacion,
            String archivoAdjunto) {

        this.idActuacion = idActuacion;
        this.idExpediente = idExpediente;
        this.idUsuario = idUsuario;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaActuacion = fechaActuacion;
        this.archivoAdjunto = archivoAdjunto;
    }

    public String getIdActuacion() {
        return idActuacion;
    }

    public void setIdActuacion(String idActuacion) {
        this.idActuacion = idActuacion;
    }

    public String getIdExpediente() {
        return idExpediente;
    }

    public void setIdExpediente(String idExpediente) {
        this.idExpediente = idExpediente;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaActuacion() {
        return fechaActuacion;
    }

    public void setFechaActuacion(LocalDate fechaActuacion) {
        this.fechaActuacion = fechaActuacion;
    }

    public String getArchivoAdjunto() {
        return archivoAdjunto;
    }

    public void setArchivoAdjunto(String archivoAdjunto) {
        this.archivoAdjunto = archivoAdjunto;
    }
}

