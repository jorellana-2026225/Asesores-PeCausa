
package com.asesorespecausa.system.repository;
    
import com.asesorespecausa.system.model.User;
import com.asesorespecausa.system.config.ConexionDB;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class AuthenticationRepository {

    public User loginDB(String nombre_usuario, String clave) {
 
        try {
            PreparedStatement prepararLlamada = ConexionDB
                    .getInstanciaConexionDB()
                    .getConnection()
                    .prepareCall("{call sp_Login(?,?)}");
            prepararLlamada.setString(1, nombre_usuario);
            prepararLlamada.setString(2, clave);
 
            ResultSet resultados = prepararLlamada.executeQuery();
            User objeto = new User();
            while (resultados.next()) {
 
                objeto.setName(resultados.getString("nombre_usuario"));
                objeto.setPassword(resultados.getString("clave"));
 
            }
            return objeto;
        } catch (SQLException errorSQL) {
            System.out.println("Error al Guardar el Resultado :D");
 
        } catch (Exception errorPadre) {
            System.out.println("Error Padre :D");
 
        }
        return null;
  
    }
}
