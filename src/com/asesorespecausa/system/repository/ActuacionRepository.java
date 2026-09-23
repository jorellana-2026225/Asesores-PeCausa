```java
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
}
```
