package com.asesorespecausa.system.repository;

import com.asesorespecausa.system.config.ConexionDB;
import com.asesorespecausa.system.model.Actuacion;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class ActuacionRepository {

    private ConexionDB conexionDB = ConexionDB.getInstanciaConexionDB();

    public void create(Actuacion actuacion) {

        String sql = "{call sp_crear_actuacion(?,?,?,?,?,?)}";

        try (
            Connection conexion = conexionDB.getConnection();
            CallableStatement procedimiento = conexion.prepareCall(sql)
        ) {

            procedimiento.setString(1, actuacion.getIdExpediente());
            procedimiento.setString(2, actuacion.getIdUsuario());
            procedimiento.setString(3, actuacion.getTitulo());
            procedimiento.setString(4, actuacion.getDescripcion());
            procedimiento.setDate(5, java.sql.Date.valueOf(actuacion.getFechaActuacion()));
            procedimiento.setString(6, actuacion.getArchivoAdjunto());

            procedimiento.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}