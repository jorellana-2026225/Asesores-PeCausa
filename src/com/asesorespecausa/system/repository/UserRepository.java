package com.asesorespecausa.system.repository;

import com.asesorespecausa.system.config.ConexionDB;
import com.asesorespecausa.system.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserRepository {
    
    private ConexionDB conexionDB = ConexionDB.getInstanciaConexionDB();
    
        public User searchByName(String name) {
        User user = null;
        String sql = "select * from usuario where nombre_usuario = ?";

        Connection conn = conexionDB.getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    user = new User(
                            rs.getString("nombre_usuario"),
                            rs.getString("clave")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar usuario");
            e.printStackTrace();
        }

        return user;
    }
}
