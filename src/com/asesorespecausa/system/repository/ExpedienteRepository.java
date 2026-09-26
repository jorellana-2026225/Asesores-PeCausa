/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.asesorespecausa.system.repository;

import com.asesorespecausa.system.config.ConexionDB;
import com.asesorespecausa.system.model.Expediente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExpedienteRepository {

    private ConexionDB conexionDB = ConexionDB.getInstanciaConexionDB();

    public Expediente buscarPorNumero(String numeroExpediente) {

        String sql = "SELECT e.id_expediente, e.numero_expediente, c.nombre_completo "
                + "FROM expediente e "
                + "INNER JOIN cliente c ON c.id_cliente = e.id_cliente "
                + "WHERE e.numero_expediente = ?";

        Connection conn = conexionDB.getConnection();
        Expediente expediente = null;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, numeroExpediente);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    expediente = new Expediente(
                            rs.getString("id_expediente"),
                            rs.getString("numero_expediente"),
                            rs.getString("nombre_completo")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar expediente");
            e.printStackTrace();
        }

        return expediente;
    }
}