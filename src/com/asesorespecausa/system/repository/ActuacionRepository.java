package com.asesorespecausa.system.repository;

import com.asesorespecausa.system.config.ConexionDB;
import com.asesorespecausa.system.model.Actuacion;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ActuacionRepository {

    private ConexionDB conexionDB = ConexionDB.getInstanciaConexionDB();

    public void create(Actuacion actuacion) {

        String sql = "{call sp_crear_actuacion(?,?,?,?,?,?)}";

        Connection conn = conexionDB.getConnection();

        try (CallableStatement llamada = conn.prepareCall(sql)) {

            llamada.setString(1, actuacion.getIdExpediente());
            llamada.setString(2, actuacion.getIdUsuario());
            llamada.setString(3, actuacion.getTitulo());
            llamada.setString(4, actuacion.getDescripcion());
            llamada.setDate(5, java.sql.Date.valueOf(actuacion.getFechaActuacion()));
            llamada.setString(6, actuacion.getArchivoAdjunto());

            llamada.execute();

        } catch (SQLException e) {
            System.out.println("Error al crear actuación");
            e.printStackTrace();
        }
    }

    /**
     * Trae todas las actuaciones que pertenecen a un expediente,
     * para poder mostrarlas en la tabla del historial.
     */
    public List<Actuacion> obtenerPorExpediente(String idExpediente) {

        List<Actuacion> actuaciones = new ArrayList<>();

        String sql = "SELECT id_actuacion, id_expediente, id_usuario, titulo, "
                + "descripcion, fecha_actuacion, archivo_adjunto "
                + "FROM actuaciones "
                + "WHERE id_expediente = ? "
                + "ORDER BY fecha_actuacion ASC";

        Connection conn = conexionDB.getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, idExpediente);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Actuacion actuacion = new Actuacion(
                            rs.getString("id_actuacion"),
                            rs.getString("id_expediente"),
                            rs.getString("id_usuario"),
                            rs.getString("titulo"),
                            rs.getString("descripcion"),
                            rs.getDate("fecha_actuacion").toLocalDate(),
                            rs.getString("archivo_adjunto")
                    );
                    actuaciones.add(actuacion);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al cargar actuaciones");
            e.printStackTrace();
        }

        return actuaciones;
    }

    public void eliminar(String idActuacion) {

        String sql = "{call sp_eliminar_actuacion(?)}";

        Connection conn = conexionDB.getConnection();

        try (CallableStatement llamada = conn.prepareCall(sql)) {

            llamada.setString(1, idActuacion);
            llamada.execute();

        } catch (SQLException e) {
            System.out.println("Error al eliminar actuación");
            e.printStackTrace();
        }
    }

    public void editar(Actuacion actuacion) {

        String sql = "{call sp_editar_actuacion(?,?,?,?,?,?,?)}";

        Connection conn = conexionDB.getConnection();

        try (CallableStatement llamada = conn.prepareCall(sql)) {

            llamada.setString(1, actuacion.getIdActuacion());
            llamada.setString(2, actuacion.getIdExpediente());
            llamada.setString(3, actuacion.getIdUsuario());
            llamada.setString(4, actuacion.getTitulo());
            llamada.setString(5, actuacion.getDescripcion());
            llamada.setDate(6, java.sql.Date.valueOf(actuacion.getFechaActuacion()));
            llamada.setString(7, actuacion.getArchivoAdjunto());

            llamada.execute();

        } catch (SQLException e) {
            System.out.println("Error al editar actuación");
            e.printStackTrace();
        }
    }
}