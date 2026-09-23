package com.asesorespecausa.system.repository;

import com.asesorespecausa.system.config.ConexionDB;
import com.asesorespecausa.system.model.Actuacion;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ActuacionRepository {

    private ConexionDB conexionDB = ConexionDB.getInstanciaConexionDB();

    public void create(Actuacion actuacion) {

        String sql = "{call sp_crear_actuacion(?,?,?,?,?,?)}";

        // IMPORTANTE: la Connection es compartida (singleton) y NO se debe
        // cerrar aquí, solo el CallableStatement. Cerrar la Connection
        // rompe el resto de la aplicación porque solo existe una.
        Connection conexion = conexionDB.getConnection();

        try (CallableStatement procedimiento = conexion.prepareCall(sql)) {

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

    public void eliminar(String idActuacion) {

        String sql = "{call sp_eliminar_actuacion(?)}";

        Connection conexion = conexionDB.getConnection();

        try (CallableStatement procedimiento = conexion.prepareCall(sql)) {

            procedimiento.setString(1, idActuacion);
            procedimiento.execute();

            System.out.println("Actuación eliminada de la base de datos.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editar(Actuacion actuacion) {

        String sql = "{call sp_editar_actuacion(?,?,?,?,?,?,?)}";

        Connection conexion = conexionDB.getConnection();

        try (CallableStatement procedimiento = conexion.prepareCall(sql)) {

            procedimiento.setString(1, actuacion.getIdActuacion());
            procedimiento.setString(2, actuacion.getIdExpediente());
            procedimiento.setString(3, actuacion.getIdUsuario());
            procedimiento.setString(4, actuacion.getTitulo());
            procedimiento.setString(5, actuacion.getDescripcion());
            procedimiento.setDate(6, java.sql.Date.valueOf(actuacion.getFechaActuacion()));
            procedimiento.setString(7, actuacion.getArchivoAdjunto());

            procedimiento.execute();

            System.out.println("Actuación editada en la base de datos.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<String[]> listarPorExpediente(String idExpediente) {

        ObservableList<String[]> lista = FXCollections.observableArrayList();

        String sql = "SELECT id_actuacion, fecha_actuacion, titulo, descripcion "
                + "FROM actuaciones "
                + "WHERE id_expediente = ? "
                + "ORDER BY fecha_actuacion ASC";

        Connection conexion = conexionDB.getConnection();

        try (PreparedStatement consulta = conexion.prepareStatement(sql)) {

            consulta.setString(1, idExpediente);

            try (ResultSet resultado = consulta.executeQuery()) {

                while (resultado.next()) {

                    lista.add(new String[]{
                        resultado.getString("id_actuacion"),
                        resultado.getString("fecha_actuacion"),
                        resultado.getString("titulo"),
                        resultado.getString("descripcion")
                    });
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public ObservableList<String[]> buscarPorTitulo(String idExpediente, String texto) {

        ObservableList<String[]> lista = FXCollections.observableArrayList();

        String sql = "SELECT id_actuacion, fecha_actuacion, titulo, descripcion "
                + "FROM actuaciones "
                + "WHERE id_expediente = ? AND titulo LIKE ? "
                + "ORDER BY fecha_actuacion ASC";

        Connection conexion = conexionDB.getConnection();

        try (PreparedStatement consulta = conexion.prepareStatement(sql)) {

            consulta.setString(1, idExpediente);
            consulta.setString(2, "%" + texto + "%");

            try (ResultSet resultado = consulta.executeQuery()) {

                while (resultado.next()) {

                    lista.add(new String[]{
                        resultado.getString("id_actuacion"),
                        resultado.getString("fecha_actuacion"),
                        resultado.getString("titulo"),
                        resultado.getString("descripcion")
                    });
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}