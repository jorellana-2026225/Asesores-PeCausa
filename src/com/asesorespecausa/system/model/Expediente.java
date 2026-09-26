/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.asesorespecausa.system.model;

public class Expediente {

    private String idExpediente;
    private String numeroExpediente;
    private String nombreCliente;

    public Expediente() {
    }

    public Expediente(String idExpediente, String numeroExpediente, String nombreCliente) {
        this.idExpediente = idExpediente;
        this.numeroExpediente = numeroExpediente;
        this.nombreCliente = nombreCliente;
    }

    public String getIdExpediente() { return idExpediente; }
    public void setIdExpediente(String idExpediente) { this.idExpediente = idExpediente; }

    public String getNumeroExpediente() { return numeroExpediente; }
    public void setNumeroExpediente(String numeroExpediente) { this.numeroExpediente = numeroExpediente; }

    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }
}