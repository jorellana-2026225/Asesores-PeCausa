/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.asesorespecausa.system.model;
import java.sql.Timestamp;

public class Cliente {

    private String id_cliente;
    private String tipo_persona;
    private String nombre_completo;
    private String documento_identidad;
    private String nit_empresa;
    private String telefono_principal;
    private String correo_electronico;
    private String direccion_fisica;
    private Timestamp fecha_registro;

    public Cliente() {
        
    }
    
     public Cliente(String id_cliente, String tipo_persona, String nombre_completo,
                   String documento_identidad, String nit_empresa, String telefono_principal,
                   String correo_electronico, String direccion_fisica, Timestamp fecha_registro) {
        this.id_cliente = id_cliente;
        this.tipo_persona = tipo_persona;
        this.nombre_completo = nombre_completo;
        this.documento_identidad = documento_identidad;
        this.nit_empresa = nit_empresa;
        this.telefono_principal = telefono_principal;
        this.correo_electronico = correo_electronico;
        this.direccion_fisica = direccion_fisica;
        this.fecha_registro = fecha_registro;
    }
    
    public String getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(String id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getTipo_persona() {
        return tipo_persona;
    }

    public void setTipo_persona(String tipo_persona) {
        this.tipo_persona = tipo_persona;
    }

    public String getNombre_completo() {
        return nombre_completo;
    }

    public void setNombre_completo(String nombre_completo) {
        this.nombre_completo = nombre_completo;
    }

    public String getDocumento_identidad() {
        return documento_identidad;
    }

    public void setDocumento_identidad(String documento_identidad) {
        this.documento_identidad = documento_identidad;
    }

    public String getNit_empresa() {
        return nit_empresa;
    }

    public void setNit_empresa(String nit_empresa) {
        this.nit_empresa = nit_empresa;
    }

    public String getTelefono_principal() {
        return telefono_principal;
    }

    public void setTelefono_principal(String telefono_principal) {
        this.telefono_principal = telefono_principal;
    }

    public String getCorreo_electronico() {
        return correo_electronico;
    }

    public void setCorreo_electronico(String correo_electronico) {
        this.correo_electronico = correo_electronico;
    }

    public String getDireccion_fisica() {
        return direccion_fisica;
    }

    public void setDireccion_fisica(String direccion_fisica) {
        this.direccion_fisica = direccion_fisica;
    }

    public Timestamp getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(Timestamp fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id_cliente='" + id_cliente + '\'' +
                ", tipo_persona='" + tipo_persona + '\'' +
                ", nombre_completo='" + nombre_completo + '\'' +
                ", documento_identidad='" + documento_identidad + '\'' +
                ", nit_empresa='" + nit_empresa + '\'' +
                ", telefono_principal='" + telefono_principal + '\'' +
                ", correo_electronico='" + correo_electronico + '\'' +
                ", direccion_fisica='" + direccion_fisica + '\'' +
                ", fecha_registro=" + fecha_registro +
                '}';
    }
}






    

