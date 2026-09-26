/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.asesorespecausa.system.utils;

public class Sesion {

    private static String idUsuarioActual;
    private static String nombreUsuarioActual;

    private Sesion() {
    }

    public static void iniciarSesion(String idUsuario, String nombreUsuario) {
        idUsuarioActual = idUsuario;
        nombreUsuarioActual = nombreUsuario;
    }

    public static void cerrarSesion() {
        idUsuarioActual = null;
        nombreUsuarioActual = null;
    }

    public static String getIdUsuarioActual() {
        return idUsuarioActual;
    }

    public static String getNombreUsuarioActual() {
        return nombreUsuarioActual;
    }

    public static boolean haySesionActiva() {
        return idUsuarioActual != null;
    }
}